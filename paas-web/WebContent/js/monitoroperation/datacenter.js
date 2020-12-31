
$(function(){
	loadDataCenterList();
	
	//jqGrid随窗口大小变化自适应宽度
	$(window).resize(function(){
		$("#dataCenterList").setGridWidth($(window).width());
	});
});

var loadDataCenterList = function(){
	
	//数据中心列表
	$('#dataCenterList').jqGrid({
		url:'/paas/datacenter/queryDataCenterList.action',
		datatype: "json",
		width: '720px',
		height:"100%",
		autowidth : true,
		cellEdit : true, //启用或者禁用单元格编辑功能
		cellurl : '/paas/datacenter/updateDescription.action', //单元格提交的URL
//		postData:{
//			privateImageId : privateImageId
//		},
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
	   	colNames:['名称','主数据中心','harbor地址','仓库地址','创建时间','描述','操作',''],
	   	colModel:[
	   		{
	   			name : "name",
	   			index : "name",
	   			align : 'center',
	   			width : '120px'
	   		},
	   		{
	   			name : "isMainDataCenter",
	   			index : "isMainDataCenter",
	   			align : 'center',
	   			width : '65px',
	   			formatter : function(val, options, rowObject) {
					var _s = rowObject['isMainDataCenter'];													
					if (_s == "0") {
						return '否';
					} else if (_s == "1") {
						return '是';
					}
				}
	   		},
	   		{
	   			name : "harborUrl",
	   			index : "harborUrl",
	   			align : 'center',
	   			width : '120px'
	   		},
	   		{
	   			name : "registryUrl",
	   			index : "registryUrl",
	   			align : 'center',
	   			width : '120px'
	   		},
	   		{
	   			name : "insertTime",
	   			index : "insertTime",
	   			align : 'center',
	   			width : '80px'
	   		},
	   		{
	   			name : "description",
	   			index : "description",
	   			align : 'center',
	   			width : '150px',
	   			editable : true, //单元格是否可编辑
	   		},
	   		{
	   			name:'id',
	   			loginName:'id',
	   			width:'70px',
	   			align : 'center',
	   			formatter:function(val, options, rowObject){
	   				var _v = "'" +val+ "'";
		   			var _link = "";
		   			var _s = rowObject['isMainDataCenter'];													
					if (_s == "0") {
						_link = '<a href="JavaScript:syncDataCenter('+_v+')" style="color:#666">'+
									'<img src="/paas/imgs/synchronous.png" width="22" height="22" border="none" title="创建Harbor同步策略"/>'+
								'</a>&nbsp;'+
								'<a href="JavaScript:deleteDataCenter(' +_v+ ')" style="color:#666">'+
									'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/>'+
								'</a>';
					} else if (_s == "1") {
						_link = '<a href="JavaScript:deleteDataCenter(' +_v+ ')" style="color:#666">'+
									'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/>'+
								'</a>';
					}
		   			return _link;
	   			}
	   		},
	   		{
	   			name: 'id',
	   			index:'id',
	   			hidden: true 
	   		}
	   	],
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
			if(value.length > 255){
				parent.parent.parent.alertWarn("描述信息最多只能保存255个字符！");
				return value.substring(0,255);
			}
		},
	    gridComplete : function() {
	    	var result = $(this).getGridParam('userData');
			if (typeof(result['errorCode'])!='undefined') {
				parent.parent.parent.alertError(result['errorCode'],result['errorMsg']);
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
};

//当输入的跳转页数大于总页数是,自动查询最后一页
function reloadResults(totalPage){
    var rows = $('#dataCenterList').getGridParam('rows');
    var sidx = $('#dataCenterList').getGridParam('sidx');
    var sord = $('#dataCenterList').getGridParam('sord');
    jQuery("#dataCenterList").jqGrid('setGridParam', {
        url : '/paas/datacenter/queryDataCenterList.action',
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).trigger("reloadGrid");
}

/**
 * 打开新增数据中心窗口
 */
function openCreateDataCenter() {
	parent.parent.parent.openDialog("/paas/jsp/monitoroperation/createDataCenter.jsp","新增数据中心",600, 250);
}

/**
 * 新增数据中心
 */
function createDataCenter(){
	var dataCenterName = $.trim($("#dataCenterName").val());
	if(dataCenterName==""){
		parent.alertWarn("数据中心名称不能为空");
		return;
	}
	
	var isMainDataCenter;
	var isMain = $("#isMainDataCenter")[0].checked; //主数据中心是否选中
	if(isMain == true){
		isMainDataCenter = "1";
	}else{
		isMainDataCenter = "0";
	}
	
	var desc = $.trim($("#desc").val());
	showLoad();
	$.ajax({
		type:"POST",
		dataType:"json",
		 url:"/paas/datacenter/createDataCenter.action",
         data:{
        	"name" : dataCenterName,
        	"isMainDataCenter" : isMainDataCenter,
        	"description" : desc
         },
		success:function(result){
			closeLoad();
			if(result['resultCode'] == 'success'){
				
				// 新增数据中心成功后刷新左侧树
				$("#mainIframe",window.parent.document)[0].contentWindow.frames["left"].refreshNode();
				
				parent.alertSuccess(); // 弹出成功提示框
				
				// 新增数据中心成功后刷新列表
				$("#mainIframe",window.parent.document)[0].contentWindow.frames["right"].frames["mainIframe"].contentWindow.reloadResult();
				
				parent.close(); // 关闭新增数据中心弹出窗口
			}
			else{
				parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	}); 
}

function showLoad(){	
	$('#loadImg').css({"width":"100%","height":"100%"});
	$('#loadImg').show();
}
function closeLoad(){
	$('#loadImg').hide();
}
/**
 * 数据中心-同步数据
 * 为新添加进来的数据中心添加所有项目的同步策略（接口）
 * @param dataCenterId
 */
function syncDataCenter(dataCenterId){
	//显示中间消息（长连接）
	parent.parent.parent.alertConfirm("确认同步数据中心数据?",function(){
		var opra_url = dataCenterId + "%7C";
		var websocketUrl = "ws://"+window.location.host+"/paas/websocket/dataCenter";
        this.parent.parent.parent.alertProgressConfirm(opra_url, websocketUrl, function(){
        	window.parent[0].location.reload();
	    });
	});
}

/**
 * 删除数据中心
 * @param dataCenterId
 */
function deleteDataCenter(dataCenterId){
	parent.parent.parent.alertConfirm("确认删除数据中心?",function(){
		$.ajax({
			type:"POST",
			dataType:"json",
			data: {
				"id":dataCenterId
		    },
			url:"/paas/datacenter/deleteDataCenter.action",
			success:function(result){
				if(result['resultCode'] == 'success'){
					reloadResult();
					// 删除数据中心成功后刷新左侧树
					window.parent.parent.frames["left"].refreshNode();
					parent.parent.parent.alertSuccess();
				}
				else{
					parent.parent.parent.alertError(result['resultCode'], result['resultMessage']);
				}
			}
		});
	});
}

function reloadResult(){
	$("#dataCenterList").jqGrid()
		.setGridParam({page:1,rowNum:10,mtype:"POST",datatype: "json",url : '/paas/datacenter/queryDataCenterList.action'})
		.setGridParam({postData:{'name':''}})
		.trigger("reloadGrid");
}
