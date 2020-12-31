//var roleType; //角色类型
var opertype;
var currentContext;
var colNames;
var colModel;

$(function(){
	
	//通过名称模糊查询私有镜像，回车查询
	$("#privateImageName").keydown(function(e){
		if((e.keyCode || e.which)==13){
			reloadResult();
		}
	});
	
	opertype = $("#opertype").val(); //操作类型，如开发1，测试2，运维3
	currentContext = $("#currentContext").val(); // 当前网络环境标示(development-true;product-false)
	
	setColNamesAndModel(opertype);
	
	//获取构建页面传过来的镜像Id
	var privateImageId = $.getUrlParam('imageId');
	
	//查询私有镜像列表
	$('#privateImageList').jqGrid({
		url:'/paas/privateImage/queryPrivateImageList.action',
		datatype: "json",
		width: '100%',
		autowidth:true,
		height:"100%",
		cellEdit : true, //启用或者禁用单元格编辑功能
		cellurl : '/paas/privateImage/updateDescription.action', //单元格提交的URL
		postData:{
			privateImageId : privateImageId
		},
		jsonReader: {
			  repeatitems: false,
			  root: 'result',
			  id: 'id',
			  repeatitems: false,
			  page:  function(obj) {return obj.pageNo; },
			  total: function(obj) { return obj.totalPages; },
			  records: function(obj) { return obj.totalCount; },
			  userdata: "userdata"
	    },
	   	colNames: colNames,
	   	colModel: colModel,
	   	multiselect: false ,
	   	rowNum:10,
	   	loadonce:true,
		altRows:true,
		altclass:'r1',
		forceFit:true,
	   	pager: '#pagerBar',
	    viewrecords: true,
	    rownumbers:true,
	    caption:"",
	    //【描述】字段单元格可编辑，保存值做限制
		beforeSaveCell : function(rowid,celname,value,iRow,iCol) {
			if(value.length > 1024){
				parent.parent.alertWarn("描述信息最多只能保存1024个字符！");
				return value.substring(0,1024);
			}
		},
	    gridComplete : function() {
	    	var result = $(this).getGridParam('userData');
			if (typeof(result['errorCode'])!='undefined') {
				parent.alertError(result['errorCode'],result['errorMsg']);
			}
		},
		//数据提交到服务器并返回信息后触发
		//防止SQL注入
		afterSubmitCell: function(serverresponse, rowid, cellname, value, iRow, iCol){
			var json = serverresponse.responseText;
			//判断是否为object，不是则将其转换成object
			if(typeof json === 'object'){
				result = json;
			}else{
				result = eval("("+json+")");
			}
			if(result["resultCode"] != "success"){
				return [false, '【' +result['resultCode']+'】'+result['resultMessage']];
			}else{
				return [true, ""];
			}
		},
		onPaging : function(pgButton){
			if(pgButton == 'user'){
				var totalPage = document.getElementById("sp_1_pagerBar").innerHTML;
				//通过循环，去掉字符串包含的所有空格
				while(totalPage.indexOf(" ") != -1){
					totalPage = totalPage.replace(" ","");
				}
				if(totalPage == 1){
					reloadResult();
				}else{
					var page = $("input[class='ui-pg-input']").val();
					//字符串数字转换
					if(Number(page) > Number(totalPage)){
						reloadResults(totalPage);
					}
					if(Number(page) < 1){ //当翻页搜索是小数时
						reloadResult();
					}
				}
			}
		}
	});
	
}); 

/**
 * 根据开发测试、生产环境，操作类型的不同
 * 分别定义列表所展示的字段
 * @param opertype 操作类型（开发-1，测试-2，运维-3，只有应用才有操作类型）
 */
function setColNamesAndModel(opertype){
	if(opertype=="1,2" || opertype=="1" || opertype=="2"){ //开发测试环境
		devEvnPriImgList();
	} else if(opertype=="3"){ //生产环境
		prodEvnPriImgList();
	} else { // 当环境初始化的时候，操作类型为空（opertype==""），只好用currentContext来区分环境，只在最后这个判断用，前两个保持不变，防止currentContext被去掉
		if(currentContext == "true"){ // 开发测试环境
			devEvnPriImgList();
		} else { // 生产环境
			prodEvnPriImgList();
		}
	}
}

/**
 * 开发测试环境私有镜像列表
 */
function devEvnPriImgList(){
   	colNames = ['镜像名称','仓库用户名和密码','应用名称','创建时间','描述','操作','',''];
   	colModel = [
   		{
   			name : "name",
   			index : "name",
   			align : 'center',
				formatter : function(val, options, rowObject) { //点击私有镜像名称链接进入私有镜像版本页面
				var _link = '<a href="listPrivateImageVersion.jsp?id=' + rowObject["id"]
								+ '&appName='+rowObject["appName"]+'&appId='+rowObject["appId"]+'" style="color:#0088A8">'
								+ rowObject["name"] + '</a>';
				return _link;
			}
   		},
   		{
   			name : "registryUser",
   			index : "registryUser",
   			align : 'center'
   		},
   		{
   			name : "appName",
   			index : "appName",
   			align : 'center'
   		},
   		{
   			name : "createTime",
   			index : "createTime",
   			align : 'center',
   		},
   		{
   			name : "description",
   			index : "description",
   			align : 'center',
   			editable : true, //单元格是否可编辑
   		},
   		{
   			name:'id',
   			loginName:'id',
   			align : 'center',
   			formatter:function(val, options, rowObject){
   				var _v = "'" +val+ "'";
	   			var _link;
	   				_link = '<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+val+'_'+rowObject["appName"]+'_2&id='+val+'&versionId=maxId&image_type=2" onclick="linkPrivateImageVersionList(this);" style="color:#666">'+
								'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/>'+
							'</a>&nbsp;&nbsp;&nbsp;'+
							'<a href="JavaScript:deletePrivateImage(' +_v+ ')" style="color:#666">'+
								'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/>'+
							'</a>&nbsp;&nbsp;&nbsp;';
	   			return _link;
   			}
   		},
   		{
   			name: 'id',
   			index:'id',
   			hidden: true 
   		},
   		{
   			name: 'appId',
   			index:'appId',
   			hidden: true 
   		}
   	];
}

/**
 * 生产环境私有镜像列表
 */
function prodEvnPriImgList(){
   	colNames = ['镜像名称','仓库用户名和密码','应用名称','创建时间','描述','操作','',''];
   	colModel = [
   		{
   			name : "name",
   			index : "name",
   			align : 'center',
				formatter : function(val, options, rowObject) { //点击私有镜像名称链接进入私有镜像版本页面
				var _link = '<a href="listPrivateImageVersion.jsp?id=' + rowObject["id"]
								+ '&appName='+rowObject["appName"]+'&appId='+rowObject["appId"]+'" style="color:#0088A8">'
								+ rowObject["name"] + '</a>';
				return _link;
			}
   		},
   		{
   			name : "registryUser",
   			index : "registryUser",
   			align : 'center'
   		},
   		{
   			name : "appName",
   			index : "appName",
   			align : 'center',
   		},
   		{
   			name : "createTime",
   			index : "createTime",
   			align : 'center',
   		},
   		{
   			name : "description",
   			index : "description",
   			align : 'center',
   			editable : true, //单元格是否可编辑
   		},
   		{
   			name:'id',
   			loginName:'id',
   			align : 'center',
   			formatter:function(val, options, rowObject){
   				var _v = "'" +val+ "'";
	   			var _link;
	   				_link = '<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+val+'_'+rowObject["appName"]+'_2&id='+val+'&versionId=maxId&image_type=2" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
								'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/>'+
							'</a>&nbsp;&nbsp;&nbsp;'+
							'<a href="JavaScript:deletePrivateImage(' +_v+ ')" style="color:#666">'+
								'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/>'+
							'</a>&nbsp;&nbsp;&nbsp;';
	   			return _link;
   			}
   		},
   		{
   			name: 'id',
   			index:'id',
   			hidden: true 
   		},
   		{
   			name: 'appId',
   			index:'appId',
   			hidden: true 
   		}
   	];
}

/**
 * 删除私有镜像
 * @param id
 */
function deletePrivateImage(id){
	
	parent.alertConfirm("确认删除私有镜像?",function(){
		$.ajax({
			type:"POST",
			dataType:"json",
			data: {
				"id":id
		    },
			url:"/paas/privateImage/deletePrivateImage.action",
			success:function(result){
				if(result['resultCode'] == 'success'){
					reloadResult();
					parent.alertSuccess();
				}
				else{
					parent.alertError(result['resultCode'], result['resultMessage']);
				}
			}
		});
	});
}

//通过名称模糊查询私有镜像
function reloadResult(){
	var o = $.trim($('#privateImageName').val());
	//通过循环，去掉字符串包含的所有空格
//	while(o.indexOf(" ") != -1){
//		o = o.replace(" ","");
//	}
	$("#privateImageList").jqGrid()
		.setGridParam({page:1,rowNum:10,mtype:"POST",datatype: "json",url : '/paas/privateImage/queryPrivateImageList.action'})
		.setGridParam({postData:{'name':o}})
		.trigger("reloadGrid");
}

$(function(){
    $(window).resize(function(){
    	$("#privateImageList").setGridWidth($(window).width()*0.99);
    });
});

//当输入的跳转页数大于总页数是,自动查询最后一页
function reloadResults(totalPage){
    var rows = $('#privateImageList').getGridParam('rows');
    var sidx = $('#privateImageList').getGridParam('sidx');
    var sord = $('#privateImageList').getGridParam('sord');
    jQuery("#privateImageList").jqGrid('setGridParam', {
        url : '/paas/privateImage/queryPrivateImageList.action',
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).trigger("reloadGrid");
}

//链接到服务管理菜单样式切换
//function linkPrivateImageVersionList(val, options, rowObject){
//	alert(rowObject["appName"]);
//	return ;
//	parent.linkImageUpdateCss(4, 1);
//}

function linkPrivateImageVersionList(obj) {
//	parent.forOldPageNav();
	// 当应用选中"全部"的时候，提示选择应用
//	if ($(".ng-binding",parent.document)[0].innerText == "全部") {
	if(JSON.parse(sessionStorage.getItem('appInfo')) == null || JSON.parse(sessionStorage.getItem('appInfo')).appName == "全部"){
		$(obj).attr('href', '#');
		parent.parent.alertWarn("请选择应用名称！");
	} else {
		parent.forOldPageNav();
		parent.linkImageUpdateCss(4, 1);
	}
}
