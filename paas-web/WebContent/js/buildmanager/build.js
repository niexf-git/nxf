/**
 * 构建列表js
 */
var isLoadComplete = false;//列表是否加载完成
//初始化构建列表 
$(function() {
	$("#createBuildId").focus();
	banBackSpace();
	//查询条件输入字符串回车
	$("#token").keydown(function(e) {
		if ((e.keyCode || e.which) == 13) {
			queryBuildList();
		}
	});
	loadBuildList();
	setInterval("refreshBuildList()","10000");
});

//初始化时导入构建列表
var loadBuildList = function(){
	$('#queryBuildList').jqGrid({
				ajaxGridOptions : {async : false},//Ajax同步请求
				url : '/paas/build/queryBuildList.action',
				datatype : "json",
				width: '1094px',
				height : "100%",
				jsonReader : {
					repeatitems : false,
					root : 'result',
					id : 'id',
					page:  function(obj) {return obj.pageNo; },
					total: function(obj) { return obj.totalPages; },
					records: function(obj) { return obj.totalCount; },
				    userdata: "userdata"
				},
				colNames : [ '构建名称', '类型', '应用名称', '状态', '上次构建时间', '结束时间', '构建次数','镜像名称', '创建人', '创建时间','操作', '', ''],
				colModel : [
						{
							 name : "name",
							index : "name",
							align : 'center',
							width : '130px',
							formatter : function(val, options, rowObject) {
								var id = rowObject["id"];
								var _link = '<a href="buildRecordList.jsp?buildId=' + id
										+ '&buildName='+rowObject["name"] 
										+ '" style="color:#0088A8">'
										+ rowObject["name"] + '</a>';
								return _link;
							}
						},
						{
							 name : "type",
							index : "type",
							align : 'center',
							width : '40px',
							formatter : function(val, options, rowObject) {
								var type = rowObject["type"];
								if(type == "1"){
									return "svn";
								}else if(type == "2"){
									return "git";
								}else{
									return "未知";
								}
							}
						},
						{
							 name : "appName",
							index : "appName",
							align : 'center',
							width : '110px',
						},
						{
							 name : "status",
							index : "status",
							align : "center",
							width : '80px',
							formatter : function(val, options, rowObject) {
								var _status = rowObject['status'];
								if (_status == "1") {
									return '构建中 '+'<img src="../../imgs/building.gif" width="16" height="7" border="none" title="构建中"/>';
								} else if (_status == "2") {
									return '<img src="../../imgs/build_success.png" width="10" height="10" border="none" title="成功"/>'+' 成功';
								} else if (_status == "3"){
									return '<img src="../../imgs/build_call_fail.png" width="10" height="10" border="none" title="调度失败"/>'+' 调度失败';
								} else if (_status == "4"){
									return '<img src="../../imgs/build_fail.png" width="10" height="10" border="none" title="失败"/>'+' 失败';
								} else{
									return '-';
								}
							}
						},
						{
							 name : "lastStartTime",
							index : "lastStartTime",
							align : 'center',
							width : '85px',
						},
						{
							 name : "lastEndTime",
							index : "lastEndTime",
							align : 'center',
							width : '85px',
						},
						{
							 name : 'buildNumber',
							index : 'buildNumber',
							align : 'center',
							width : '80px',
							sorttype:'integer',
						},
						{
							 name : "imageName",
							index : "imageName",
							align : 'center',
							width : '130px',
							formatter : function(val, options, rowObject) {
								var imageId = rowObject["imageId"];
								var imageName = rowObject["imageName"];
								var _link = "无";
								if(imageId && imageId != "null"){
									imageName = imageName.indexOf("/")>-1?imageName.replace('/','/<br/>'):imageName;
									_link = '<a href="/paas/jsp/imagemanager/privateimage/listPrivateImage.jsp?imageId='+imageId+'"'
											+ ' onclick="linkPrivateImageVersionList();" style="color:#0088A8">'
											+ imageName + '</a>';
								}
								return _link;
							}
						}, 
						{
							 name : "creator",
							index : "creator",
							align : 'center',
							width : '80px',
						}, 
						{
							 name : "createTime",
							index : "createTime",
							align : 'center',
							width : '80px',
						}, 
						{	  name:"operation", 
				   			 index:"operation", 
				   		     align:'left',
				   		     width : '115px',
				   			 formatter:function(val,options,rowObject){
				   				var _id = "'" +rowObject['id']+ "'";
				   				var _status = rowObject['status'];
				   				var _link = '';
				   				if(_status != "1"){  
				   				    _link ='&nbsp;&nbsp;&nbsp;<a href="JavaScript:createOrUpdateBuild('+_id+')" style="color:#666" title="修改"><img src="../../imgs/modify.png" width="22" height="22" border="none"/></a>&nbsp;&nbsp;&nbsp;';
				   					_link +='<a href="javaScript:deleteBuild('+_id+')" style="color:#666" title="删除"><img src="../../imgs/delete.png"  width="22" height="22" border="none"/></a>&nbsp;&nbsp;&nbsp;';
				   				    _link +='<a href="javaScript:executeBuild('+_id+')" style="color:#666" title="构建"><img src="../../imgs/execute_build.png"  width="22" height="22" border="none"/></a>';
				   				}
				   				return _link;
				   			 },
					   	},
						{ name : 'id', index : 'id', hidden : true },
						// 缓存imageId,链接到镜像版本列表
						{ name : 'imageId',	index : 'imageId', hidden : true }
				],
				multiselect : false,
				rowNum : 10,
				loadonce : true,
				altRows : true,
				altclass : 'r1',
				forceFit : true,
				pager : '#pagerBar',
				sortname : 'createTime',
				viewrecords : true,
				sortorder : "desc",
				caption : "",
				rownumbers : true,
				gridComplete : function() {
					var result = $(this).getGridParam('userData');
					if (typeof(result['errorCode'])!='undefined') {
						parent.parent.alertError(result['errorCode'],result['errorMsg']);
					}
					isLoadComplete = true;
				},
				onPaging : function(pgButton){
					if(pgButton == 'user'){
						var totalPage = document.getElementById("sp_1_pagerBar").innerHTML;
						//通过循环，去掉字符串包含的所有空格
						while(totalPage.indexOf(" ") != -1){
							totalPage = totalPage.replace(" ","");
						}
						
						var page = $("input[class='ui-pg-input']").val();
						//字符串数字转换
						if(Number(page) > Number(totalPage)){
							reloadResults(totalPage);
						}
					}
				}
			});
};

//输入条件查询构建列表
function queryBuildList() {
	$('#token').val($.trim($('#token').val()));
	var token = $.trim($('#token').val());
	$("#queryBuildList").jqGrid().setGridParam({
		page : 1,
		rowNum : 10,
		mtype : "POST",
		datatype : "json",
		url : '/paas/build/queryBuildList.action'
	}).setGridParam({
		postData : {
			"token" : token
		}
	}).trigger("reloadGrid");
}

//获取当前页码
function getCurrPage(){
	var totalPage = document.getElementById("sp_1_pagerBar").innerHTML;
	//通过循环，去掉字符串包含的所有空格
	while(totalPage.indexOf(" ") != -1){
		totalPage = totalPage.replace(" ","");
	}
	var page = $.trim($("input[class='ui-pg-input']").val());
	//字符串数字转换
	if(Number(page) > Number(totalPage)){
		page = totalPage;
	}else if(Number(page) < 1){
		page = 1;
	}
	return page;
}

//模拟按下回车键
function simulationEnter(currPage){
	$("input[class='ui-pg-input']").val(currPage);
	var e = jQuery.Event("keypress"); //模拟一个键盘事件
    e.keyCode = 13;  //keyCode=13是回车
    $("input[class='ui-pg-input']").trigger(e);
}

//刷新当前页列表数据
function refreshPageList(){
	var currPage = getCurrPage();
	queryBuildList();
	if(isLoadComplete){
		simulationEnter(currPage);
		isLoadComplete = false;
	}
	//等页面加载完才刷新
//	setTimeout(function() {
//		//列表有变化
//		var totalPage = document.getElementById("sp_1_pagerBar").innerHTML;
//		//通过循环，去掉字符串包含的所有空格
//		while(totalPage.indexOf(" ") != -1){
//			totalPage = totalPage.replace(" ","");
//		}
//		if(Number(currPage) > Number(totalPage)){
//			currPage = 1;
//		}else if(Number(currPage) < 1){
//			currPage = 1;
//		}
//		//模拟按下回车键
//		simulationEnter(currPage);
//	},1000);
}

//创建或者更新构建
function createOrUpdateBuild(id) {
	var title = "创建构建";
	if(id){
		title = "更新构建";
		parent.openDialog("/paas/build/queryBuild.action?buildId="+id, title, 622, 480);
	}else{
		var application = $("#appText",window.parent.document).html();
		if(application == "全部"){
			parent.switchAppInfoDialog('请选择应用名称');
			return;
		}
		var operType = $("#operTypeText",window.parent.document).html();
		if(operType == "全部"){
			parent.switchAppInfoDialog('请选择操作类型');
			return;
		}
		operType = $("#operTypeText",window.parent.document).html();
		if(operType != "开发"){
			parent.switchAppInfoDialog('只有开发人员可以创建构建，操作类型请选择开发！');
			return;
		}
		parent.openDialog("/paas/jsp/buildmanager/chooseRepository.jsp", "创建构建", 622, 480);
	}
}

//删除构建
function deleteBuild(id){
	parent.parent.alertConfirm("确认删除构建？",function(){
		$.ajax({
			type : "POST",
			dataType : "json",
			data: {
				"buildId" : id.toString()
			},
			url : '/paas/build/deleteBuild.action',
			success : function(result) {
				if (result['resultCode'] == 'success') {
					parent.parent.alertSuccess();
					queryBuildList();
				} else {
					parent.parent.alertError(result['resultCode'], result['resultMessage']);
				}
			}
		});
	  });
}

//执行构建
function executeBuild(id){
	$.ajax({
		type : "POST",
		dataType : "json",
		data: {
			"buildId" : id.toString()
		},
		url : '/paas/build/executeBuild.action',
		success : function(result) {
			if (result['resultCode'] == 'success') {
				//parent.parent.alertSuccess();
				refreshPageList();
			} else {
				parent.parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	});
}

//链接到私有镜像列表页面
function linkPrivateImageVersionList(){
	parent.linkImageUpdateCss(3, 4);
}

//翻页时调用，或者输入页码回车
function reloadResults(totalPage){
    var rows = $('#queryBuildList').getGridParam('rows'); 
    var sidx = $('#queryBuildList').getGridParam('sidx'); 
    var sord = $('#queryBuildList').getGridParam('sord'); 
    jQuery("#queryBuildList").jqGrid('setGridParam', {
        url : '/paas/build/queryBuildList.action',
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).trigger("reloadGrid");
}
//刷新构建列表
function refreshBuildList(){
	var ids = $('#queryBuildList').getDataIDs();
	for(var i=0;i<ids.length;i++){
		var rowBuild = $('#queryBuildList').getRowData(ids[i]);//获取当前的数据行  
		var status = rowBuild['status'];
		if(status.indexOf("构建中") > -1){
			refreshPageList();
			break;
		}
	}
}

