/**
 * 基础服务JS
 * 包括查询基础服务列表、操作区功能等方法
 */

var selectData = new Array();
$(function() {
	
	$("#token").keydown(function(e){
		if((e.keyCode || e.which)==13){
			queryIpaasServiceListByToken();
		}
	});
	
	$('#queryIpaasserviceList').jqGrid(
			{				
				url : '/paas/ipaasservice/queryIpaasServiceList.action',				
				datatype : "json",
				width: '100%',
				autowidth:true,
				height : "100%",
				jsonReader : {
					root : 'result',
					id : 'id',
					repeatitems : false,
					page : function(obj) {
						return obj.pageNo;
					},
					total : function(obj) {
						return obj.totalPages;
					},
					records : function(obj) {
						return obj.totalCount;
					},
					userdata : "userdata"
				},
				colNames : [ '服务名称','集群名称', '状态', '规模', '服务类型','镜像名称','应用名称','操作类型','创建人', '创建时间', '', '' ],
				colModel : [
						{
							name : "name",
							index : "name",
							align : 'center',
							formatter : function(val, options, rowObject) {
								var id = rowObject["id"];
								var _link = '<a href="ipaasServiceDetail.jsp?ipaasServiceId=' + id
										+ '&ipaasServiceName=' + rowObject["name"]
										+ '" style="color:#0088A8">'
										+ rowObject["name"] + '</a>';
								return _link;
							}
						}, 
						{
							name : "clusterName",
							index : "clusterName",
							align : 'center',
							
						},{
							align : 'center',
							index : 'status',
							name : 'status',
							formatter : function(val, options, rowObject) {
								var _s = rowObject['status'];
								if (_s == "1") {
									return '<img src="/paas/imgs/stopFlag.png" width="12" height="12" style="margin-right:5px;" />停止';
								} else if (_s == "2") {
									return '<img src="/paas/imgs/run.png" width="12" height="12" style="margin-right:5px;" />运行';
								} else {
									return _s;
								}
							}
						}, {
							name : "instance_num",
							index : "instance_num",
							align : 'center',
							formatter : function(val, options, rowObject) {																
								var _runningInstCount = rowObject['running_Inst_num'];
								var _instance_num = rowObject['instance_num'];
								
								return _runningInstCount+"/"+_instance_num;
							}
						}, {
							name : "service_type",
							index : "service_type",
							align : 'center',
							formatter : function(val, options, rowObject) {
								var _s = rowObject['service_type'];
								if (_s == "1") {
									return 'zookeeper';
								} else if (_s == "2") {
									return 'redis';
								} else if(_s == "3"){
									return 'activemq';
								}else{
									return _s;
								}
							}
							
						},{
							name : "image_name",
							index : "image_name",
							align : 'center',
							
						},{
							name : "app_name",
							index : "app_name",
							align : 'center',
							
						},{
							name : "oper_type",
							index : "oper_type",
							align : 'center',
							formatter : function(val, options, rowObject) {
								var _s = rowObject['oper_type'];
								if (_s == "1") {
									return '开发';
								} else if (_s == "2") {
									return '测试';
								} else if (_s == "3"){
									return '生产';
								}
							}
						},{
							name : "user_id",
							index : "user_id",
							align : 'center',
						}, {
							name : "create_time",
							index : "create_time",
							align : 'center',
							formatter : function(val, options, rowObject) {
								return val.replace(' ','<br/>');
							}
						}, {
							name : 'id',
							index : 'id',
							hidden : true
						},
						// 缓存runningInstCount,避免翻页无法获取
						{
							name : 'running_Inst_num',
							index : 'running_Inst_num',
							hidden : true
						}],
				multiselect : false,
				rowNum : 10,
				loadonce : true,
				altRows : false,
				altclass : "r1",
				forceFit : false,
				pager : '#pagerBar',
				sortname : "create_time",
				viewrecords : true,
				sortorder : "desc",
				caption : "",
				rownumbers : true,
				sortable : false,
				gridComplete : function() {
					var result = $(this).getGridParam('userData');
					if (typeof (result['errorCode']) != 'undefined') {
						parent.alertError(result['errorCode'],
								result['errorMsg']);
					}
				},
				loadComplete : function(xhr){
					//var gridModel = xhr.gridModel;
					for(var i=0;i<selectData.length;i++){
						jQuery("#queryIpaasserviceList").jqGrid('setSelection', selectData[i]['id']);
					}
				},
				onSelectAll : function(aRowids,status){
					for(var index=0;index<aRowids.length;index++){
						var rowData = jQuery("#queryIpaasserviceList").jqGrid('getRowData',aRowids[index]);
						if(status){
							if(indexOf(rowData) == -1){
								selectData.push(rowData);
							}
						}else{
							remove(rowData);
						}
					}
				},
				onSelectRow : function(aRowids,status){
					var rowData = jQuery("#queryIpaasserviceList").jqGrid('getRowData',aRowids);
					if(status){
						if(indexOf(rowData) == -1){
							selectData.push(rowData);
						}
					}else{
						remove(rowData);
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
							queryIpaasServiceListByToken();
						}else{
							var page = $("input[class='ui-pg-input']").val();
							//字符串数字转换
							if(Number(page) > Number(totalPage)){
								reloadResults(totalPage);
							}
							if(Number(page) < 1){
								queryIpaasServiceListByToken();
							}
						}
					}
				}
			});
});
$(function(){
    $(window).resize(function(){   
    	$("#queryIpaasserviceList").setGridWidth($(window).width()*0.99);
});
}); 
function reloadResults(totalPage){
	var page = $('#queryIpaasserviceList').getGridParam('page'); 
    var rows = $('#queryIpaasserviceList').getGridParam('rows'); 
    var sidx = $('#queryIpaasserviceList').getGridParam('sidx'); 
    var sord = $('#queryIpaasserviceList').getGridParam('sord'); 
    jQuery("#queryIpaasserviceList").jqGrid('setGridParam', {
        url : "/paas/ipaasservice/queryIpaasServiceList.action",
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord
    }).trigger("reloadGrid");
}

//根据输入的基础服务名称模糊查询基础服务列表
function queryIpaasServiceListByToken() {	
	var token = $.trim($('#token').val());
	
	$("#queryIpaasserviceList").jqGrid().setGridParam({
		page : 1,
		rowNum : 10,
		mtype : "POST",
		datatype : "json",
		url : '/paas/ipaasservice/queryIpaasServiceList.action'
	}).setGridParam({
		postData : {
			"token" : token
		}
	}).trigger("reloadGrid");
}

//创建基础服务
function createIpaasService(){
	$("#switchAppInfoMsg").html('');
//	if($(".ng-binding",parent.document)[0].innerText == "全部"){
	if(JSON.parse(sessionStorage.getItem('appInfo')) == null || JSON.parse(sessionStorage.getItem('appInfo')).appName == "全部"){
		$("#switchAppInfoMsg").html('请选择应用名称');
		//parent.switchAppInfoDialog('请选择应用名称');
		return;
	}
	
	/*var operType = $("#operTypeText",window.parent.document).html();
	if(operType == "全部"){
		parent.switchAppInfoDialog('请选择操作类型');
		return;
	}*/
	
//	$.ajax({
//		cache:false,
//		type : "get",
//		dataType : "json",
//		url : '/paas/appservice/queryClusterList.action',
//		async: false,
//		success : function(result) {			
//			if(result['result'] == null){
//				parent.alertWarn("没有可用的集群！");
//				return;
//			}
//			var isHaveIpaasCluster = false;
//			$.each(result['result'], function(i, val) {
//				if(val['type'] == '1'){
//					isHaveIpaasCluster = true;
//				}
//			});
//			if(!isHaveIpaasCluster){
//				parent.alertWarn("没有可用的集群！");
//				return;
//			}
//			parent.openDialog("/paas/jsp/ipaasservice/createIpaasService.jsp", "创建基础服务", 600, 470);
//		}
//	});
	
	parent.openDialog("/paas/jsp/ipaasservice/createIpaasService.jsp", "创建基础服务", 600, 470);
}

/**
 * 启动：基础服务
 * @param ipaasServiceId
 * @param type
 */
function startIpaasService(ipaasServiceId,type){
	var msg = "";
	var requestUrl = "/paas/ipaasservice/startIpaasService.action";
	
	if (type=="1") {
		msg ="确认启动服务？";
	}else{
		parent.parent.alertWarn("服务状态为停止才能启动!");
		return;
	}			
	
	parent.parent.alertConfirm(msg, function(){
		if (requestUrl!="") {
			parent.parent.showLoad();
			$.ajax({
				type:"POST",
				dataType:"json",
				data: {
					"ipaasServiceId":ipaasServiceId
			        },
				url:requestUrl,
				success:function(result){
					parent.parent.closeLoad();
					if(result['resultCode'] == 'success'){
						parent.parent.alertSuccess();
						window.location.reload(); 
					}
					else{
						parent.parent.alertError(result['resultCode'],result['resultMessage']);
					}
				}
			});
		}
	});
}

/**
 * 停止：基础服务
 * @param ipaasServiceId
 * @param type
 */
function stopIpaasService(ipaasServiceId,type){
	//var msg = "";
	//var requestUrl = "/paas/ipaasservice/stopIpaasService.action";
	var requestUrl = "/paas/ipaasservice/checkIpaasRelaAppService.action";
	
	if (type=="2") {
		msg ="确认停止服务？";
	}else{
		parent.parent.alertWarn("服务状态为启动才能停止!");
		return;
	}
	
	//parent.parent.alertConfirm(msg, function(){
		if (requestUrl!="") {
//			parent.parent.showLoad();
			$.ajax({
				type:"POST",
				dataType:"json",
				data: {
					"ipaasServiceId":ipaasServiceId
			        },
				url:requestUrl,
				success:function(result){
					parent.parent.closeLoad();
					if(result['resultCode'] == 'success'){
						//parent.parent.alertSuccess();
						//window.location.reload();
						forceStopIpaasService("确认停止服务？", ipaasServiceId); //基础服务没有被应用服务绑定、基础服务被没有运行状态的应用服务绑定
					}
					else if(result['resultCode'] == 'PAAS-20924'){
						forceStopIpaasService(result['resultMessage'], ipaasServiceId); //基础服务被运行状态的应用服务绑定
					}
					else{
						parent.parent.alertError(result['resultCode'],result['resultMessage']);
					}
				}
			});
		}
	//});
}

/**
 * 基础服务被运行状态的应用服务绑定，或者
 * 基础服务没有被应用服务绑定、基础服务被没有运行状态的应用服务绑定，
 * 其实都调用的都是停止
 * @param resultMessage
 * @param ipaasServiceId
 */
function forceStopIpaasService(resultMessage, ipaasServiceId){
	//var requestUrl = "/paas/ipaasservice/forceStopIpaasService.action";
	var requestUrl = "/paas/ipaasservice/stopIpaasService.action";
	
	parent.parent.alertConfirm(resultMessage, function(){
		if (requestUrl != "") {
			parent.parent.showLoad();
			$.ajax({
				type:"POST",
				dataType:"json",
				data: {
					"ipaasServiceId":ipaasServiceId
			        },
				url:requestUrl,
				success:function(result){
					parent.parent.closeLoad();
					if(result['resultCode'] == 'success'){
						parent.parent.alertSuccess();
						window.location.reload(); 
					}
					else{
						parent.parent.alertError(result['resultCode'],result['resultMessage']);
					}
				}
			});
		}
	});
}

/**
 * 重启：基础服务
 * @param ipaasServiceId
 * @param type
 */
function restartIpaasService(ipaasServiceId,type){
	//var msg = "";
	//var requestUrl = "/paas/ipaasservice/restartIpaasService.action";
	var requestUrl = "/paas/ipaasservice/checkIpaasRelaAppService.action";
	
	if (type=="2") {
		msg ="确认重启服务？";
	}else{
		parent.parent.alertWarn("服务状态为启动才能重启!");
		return;
	}
	
	//parent.parent.alertConfirm(msg, function(){
		if (requestUrl!="") {
//			parent.parent.showLoad();
			$.ajax({
				type:"POST",
				dataType:"json",
				data: {
					"ipaasServiceId":ipaasServiceId
			        },
				url:requestUrl,
				success:function(result){
					parent.parent.closeLoad();
					if(result['resultCode'] == 'success'){
						//parent.parent.alertSuccess();
						//window.location.reload();
						forceRestartIpaasService("确认重启服务？", ipaasServiceId); //基础服务没有被应用服务绑定、基础服务被没有运行状态的应用服务绑定
					}
					else if(result['resultCode'] == 'PAAS-20924'){
						forceRestartIpaasService(result['resultMessage'], ipaasServiceId); //基础服务被运行状态的应用服务绑定
					}
					else{
						parent.parent.alertError(result['resultCode'],result['resultMessage']);
					}
				}
			});
		}
	//});
}

/**
 * 基础服务被运行状态的应用服务绑定，或者
 * 基础服务没有被应用服务绑定、基础服务被没有运行状态的应用服务绑定，
 * 其实都调用的都是重启
 * @param resultMessage
 * @param ipaasServiceId
 */
function forceRestartIpaasService(resultMessage, ipaasServiceId){
	//var requestUrl = "/paas/ipaasservice/forceRestartIpaasService.action";
	var requestUrl = "/paas/ipaasservice/restartIpaasService.action";
	
	parent.parent.alertConfirm(resultMessage, function(){
		if (requestUrl != "") {
			parent.parent.showLoad();
			$.ajax({
				type:"POST",
				dataType:"json",
				data: {
					"ipaasServiceId":ipaasServiceId
			        },
				url:requestUrl,
				success:function(result){
					parent.parent.closeLoad();
					if(result['resultCode'] == 'success'){
						parent.parent.alertSuccess();
						window.location.reload(); 
					}
					else{
						parent.parent.alertError(result['resultCode'],result['resultMessage']);
					}
				}
			});
		}
	});
}

/**
 * 删除：基础服务
 * @param ipaasServiceId
 */
function del(ipaasServiceId){
	var msg = "确认删除服务?";
	var requestUrl = "/paas/ipaasservice/deleteIpaasService.action";			
	parent.parent.alertConfirm(msg, function(){
		parent.parent.showLoad();
		$.ajax({
			type:"POST",
			dataType:"json",
			data: {
				"ipaasServiceId":ipaasServiceId
		        },
			url:requestUrl,
			success:function(result){
				parent.parent.closeLoad();
				if(result['resultCode'] == 'success'){
					parent.parent.alertSuccess();
					$("#mainIframe",window.parent.parent.document)[0].src="/paas/jsp/ipaasservice/ipaasserviceList.jsp";
				}
				else{
					parent.parent.alertError(result['resultCode'],result['resultMessage']);
				}
			}
		});
	});
}