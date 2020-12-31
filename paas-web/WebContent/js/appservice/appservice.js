/**
 * 应用服务JS
 * 包括查询应用服务列表、操作区功能等方法
 */
var selectData = new Array();
$(function() {
	var imageId = getUrlParam('imageId');
	if(imageId != null){
		$("#createAppService")[0].click();
	}
	$("#token").keydown(function(e){
		if((e.keyCode || e.which)==13){
			queryAppServiceListByToken();
		}
	});
	
	$.ajax({
		cache:false,
		type : "get",
		dataType : "json",
		url : '/paas/appservice/queryClusterList.action',
		async: false,
		success : function(result) {			
//			if(result['result'] == null){
//				parent.alertWarn("没有可用的集群！");
//				return;
//			}
			$.each(result['result'], function(i, val) {
//				$("#cluster_id").val(val['id']);
//				$("#cluster_name").text(val['name']);
//				$("#cluster_name").append("<option value='" + val['id'] + "' type='"+val['type']+"'>" + val['name'] + "</option>");
				$("#cluster_name").append("<option value='" + val['id'] +"'>" + val['name'] + "</option>");
			});
		}
	});
	
	$('#queryAppserviceList').jqGrid(
			{				
				url : '/paas/appservice/queryAppServiceList.action',
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
				colNames : [ '服务名称', '状态', '实例数', '镜像名称','镜像版本','应用名称','操作类型','灰度版本','集群','创建人', '创建时间', '','','','','' ],
				colModel : [
						{
							name : "name",
							index : "name",
							align : 'center',
							
							formatter : function(val, options, rowObject) {
								var type = rowObject['oper_type'];
								if(type == 1){
									type = 'dev';
								}else if(type == 2){
									type = 'test';
								}else{
									type = 'oprt';
								}
								var _instance_num = rowObject['instance_num'];
								var _inst_scale_type = rowObject['inst_scale_type']; 
								if(_inst_scale_type == "2"){
									_instance_num = rowObject['inst_max'];
								}
								var id = rowObject["id"];
								var _link = '<a href="appServiceDetail.jsp?appServiceId=' + id
										+ '&appServiceName=' + rowObject["name"]
										+ '&runVersion=' + rowObject["image_version"]
										+ '&runVersionId=' + rowObject["image_version_id"]
										+ '&state=' + rowObject["status"]
										+ '&_instance_num=' + _instance_num
										+ '&namespace=' + rowObject["app_name"]+'-'+type
										+ '" style="color:#0088A8">'
										+ rowObject["name"] +'</a>';
								return _link;
							}
						}, {
							
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
								var _inst_scale_type = rowObject['inst_scale_type'];
								if(_inst_scale_type == "1"){
									return _runningInstCount+"/"+_instance_num;
								}else if(_inst_scale_type == "2"){
									var _inst_min = rowObject['inst_min'];
									var _inst_max = rowObject['inst_max'];
									return _runningInstCount+"/"+_inst_min+"~"+_inst_max;
								}
								
							}
						}, {
							name : "image_name",
							index : "image_name",
							align : 'center',
							
//							formatter : function(val, options, rowObject) {
//								return val.indexOf("/")>-1?val.replace('/','<br/>/'):val;
//							}
							
						},{
							name : "image_version",
							index : "image_version",
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
							name : "existGray",
							index : "existGray",
							align : 'center',
							
							formatter : function(val, options, rowObject){
								if(val == 0){
									return '否';
								}else{
									return '是';
								}
							}
						},{
							name : "cluster_name",
							index : "cluster_name",
							align : 'center',
							
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
							//width:"280"
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
						},
						// 缓存inst_scale_type,避免翻页无法获取
						{
							name : 'inst_scale_type',
							index : 'inst_scale_type',
							hidden : true
						},
						// 缓存_inst_min,避免翻页无法获取
						{
							name : 'inst_min',
							index : 'inst_min',
							hidden : true
						},
						// 缓存_inst_max,避免翻页无法获取
						{
							name : 'inst_max',
							index : 'inst_max',
							hidden : true
						}],
				multiselect : true,
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
					var page = $(this).getGridParam('page');
					var lastPage = $(this).getGridParam('lastpage');
					$("#page").val(page);
					$("#pageText").text(page + "/" +lastPage);
				},
				loadComplete : function(xhr){
					//var gridModel = xhr.gridModel;
					for(var i=0;i<selectData.length;i++){
						jQuery("#queryAppserviceList").jqGrid('setSelection', selectData[i]['id']);
					}
				},
				onSelectAll : function(aRowids,status){
					for(var index=0;index<aRowids.length;index++){
						var rowData = jQuery("#queryAppserviceList").jqGrid('getRowData',aRowids[index]);
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
					var rowData = jQuery("#queryAppserviceList").jqGrid('getRowData',aRowids);
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
							queryAppServiceListByToken();
						}else{
							var page = $("input[class='ui-pg-input']").val();
							//字符串数字转换
							if(Number(page) > Number(totalPage)){
								reloadResults(totalPage);
							}
							if(Number(page) < 1){
								queryAppServiceListByToken();
							}
						}
					}
				}
			});
});
$(function(){
    $(window).resize(function(){   
    	$("#queryAppserviceList").setGridWidth($(window).width()*0.99);
});
});
function reloadResults(totalPage){
	var page = $('#queryAppserviceList').getGridParam('page'); 
    var rows = $('#queryAppserviceList').getGridParam('rows'); 
    var sidx = $('#queryAppserviceList').getGridParam('sidx'); 
    var sord = $('#queryAppserviceList').getGridParam('sord'); 
    jQuery("#queryAppserviceList").jqGrid('setGridParam', {
        url : "/paas/appservice/queryAppServiceList.action",
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord
    }).trigger("reloadGrid");
}

/***
 * 刷新选中集合中的状态
 */
function refreshState(){
	var jqgridData = $("#queryAppserviceList")[0].childNodes[0].childNodes;
	for (var j = 0; j < selectData.length; j++) {
		for(var i=1;i<jqgridData.length;i++){
			if(jqgridData[i].id == selectData[j]['id']){
				selectData[j]['status'] = jqgridData[i].childNodes[3].innerHTML;
				break;
			}
		}
	}
}
function opretion(){
	var display =$('#opre').css('display');
	if(display == 'none'){
		$("#opre").css({"display":""});
	}else{
		$("#opre").css({"display":"none"});
	}
	
	
}
function lose(){
	setTimeout(function(){
		$('#opre').hide();
	},500);
}


//根据输入的应用服务名称模糊查询应用服务列表
function queryAppServiceListByToken() {
//	$('#token').val($.trim($('#token').val()));
//	var token = $.trim($('#token').val());
	var token = $.trim($('#token').val());
	var cluster_id = $("select option:checked").attr("value");
	$("#queryAppserviceList").jqGrid().setGridParam({
		page : 1,
		rowNum : 10,
		mtype : "POST",
		datatype : "json",
		url : '/paas/appservice/queryAppServiceList.action',
		loadComplete : function(xhr){
			refreshState();
			for(var i=0;i<selectData.length;i++){
				jQuery("#queryAppserviceList").jqGrid('setSelection', selectData[i]['id']);
			}
			var selectedIds = $("#queryAppserviceList").jqGrid("getGridParam", "selarrrow");
			//获取选中的存在ID  防止删除后不清楚对应ID
			var selectDatas = new Array();
			$.each(selectedIds,function(i,v){
				for(var i=0;i<selectData.length;i++){
					if(selectData[i].id == v){
						selectDatas.push(selectData[i]);
					}
				}
			});
			selectData = selectDatas;
		}
	}).setGridParam({
		postData : {
			"token" : token,
			"cluserId" : cluster_id
		}
	}).trigger("reloadGrid");
}

function AddServiceDialog(){
	$("#switchAppInfoMsg").html('');
//	var appName = parent.document.getElementById("appText").innerText;
//	var operType = parent.document.getElementById("operTypeText").innerText;
//	if(appName == "全部" && operType == "全部"){		
//		parent.switchAppInfoDialog("请先选择一个应用和类型！");
//		return;
//	}else if(appName == "全部"){		
//		parent.switchAppInfoDialog("请先选择一个应用！");
//		return;
//	}else if(operType == "全部"){		
//		parent.switchAppInfoDialog("请先选择一个类型，如开发、测试或者生产！");
//		return;
//	}
	//var imageId = getUrlParam('imageId');
//	if($(".ng-binding",parent.document)[3].innerText == "全部"){
	if(JSON.parse(sessionStorage.getItem('appInfo')) == null || JSON.parse(sessionStorage.getItem('appInfo')).appName == "全部"){
		/*if(imageId != null){
			if(getUrlParam('image_type') == 3){
				var length = 0;
				var appId = getUrlParam('appId');
				var appName = imageId.split("_")[1];
				var typeId = imageId.split("_")[0];
				var typeName = "";
				$.ajax({
					url:'/paas/privateImage/queryPrivateImageInfo.action',
					data:{status:typeId,privateImageType:2,appName:appName},
					async : false,
					success:function(data,v,i){
						var opertype = i.responseText;
						var opertypes = opertype.split(",");
						$("#typeName").empty(); 
						length = opertypes.length;
						if(length == 1){
							//格式处理  后台转回的数据带有双重" 转换成字符串
							typeName = opertype.indexOf(",")==-1 && opertype.indexOf("1")>-1?1:opertype.indexOf(",")==-1 && opertype.indexOf("2")>-1?2:3;
						}else{
							typeName = typeId;
						}
					}
					
				});
				setTimeout(function(){
					if(length == 1){
						$.ajax({
							type : "POST",
							dataType : "json",
							async : false,
							url:'/paas/user/queryAppType.action',
							data:{appId:appId,type:2,typeName:typeName,deveName:appName},
							success:function(result){
								if(result['resultCode']=='success'){
										$("#appText",window.parent.document).html(appName);
										$("#operTypeText",window.parent.document).html(result['resultMessage']==1?"开发":result['resultMessage']==2?"测试":result['resultMessage']==3?"运维":"全部");
										//当前子页的path路径
										var currentPagePathName = window.parent.parent[0].document.location.pathname;
										if(currentPagePathName.indexOf('appserviceList.jsp')>-1){//服务管理
											$("#createAppService",window.parent.parent[0].document)[0].click();
										}
								}else{
									parent.alertError(result['resultCode'],result['resultMessage']);
								}
							}
							
						}); 
					}else{
						parent.switchAppInfoDialog(imageId);
					}
				}, 500);
			}else{
				parent.switchAppInfoDialog(imageId);
			}
			return;
		}else{*/
			//parent.switchAppInfoDialog('请选择应用名称');
			$("#switchAppInfoMsg").html('请选择应用名称');
			return;
		//}
		
	}
	
	/*var operType = $("#operTypeText",window.parent.document).html();
	if(operType == "全部"){
		if(imageId != null){
			if(getUrlParam('image_type') == 3){
				var length = 0;
				var appId = getUrlParam('appId');
				var appName = imageId.split("_")[1];
				var typeId = imageId.split("_")[0];
				$.ajax({
					url:'/paas/privateImage/queryPrivateImageInfo.action',
					data:{status:typeId,privateImageType:2},
					success:function(data,v,i){
						var opertype = i.responseText;
						var opertypes = opertype.split(",");
						$("#typeName").empty(); 
						length = opertypes.length;
					}
					
				});
				setTimeout(function(){
					if(length == 1){
						$.ajax({
							type : "POST",
							dataType : "json",
							url:'/paas/user/queryAppType.action',
							data:{appId:appId,type:2,typeName:typeId,deveName:appName},
							success:function(result){
								if(result['resultCode']=='success'){
										$("#appText",window.parent.document).html(appName);
										$("#operTypeText",window.parent.document).html(result['resultMessage']==1?"开发":result['resultMessage']==2?"测试":result['resultMessage']==3?"运维":"全部");
										//当前子页的path路径
										var currentPagePathName = window.parent.parent[0].document.location.pathname;
										if(currentPagePathName.indexOf('appserviceList.jsp')>-1){//服务管理
											$("#createAppService",window.parent.parent[0].document)[0].click();
										}
								}else{
									parent.alertError(result['resultCode'],result['resultMessage']);
								}
							}
							
						}); 
					}else{
						parent.switchAppInfoDialog(imageId);
					}
				}, 500);
			}else{
				parent.switchAppInfoDialog(imageId);
			}
			return;
		}else{
			parent.switchAppInfoDialog('请选择操作类型');
			return;
		}
		
	}*/
	
	parent.openDialog("/paas/jsp/appservice/createAppService.jsp", "创建服务", 600, 470);
}

//删除数组元素
function remove(val){
	var index = indexOf(val);
	if(index >-1){
		selectData.splice(index, 1);
	}
}
//判断数组元素是否存在，不存在返回-1，存在返回下标
function indexOf(val){
	for(var i=0; i<selectData.length; i++){
		if(selectData[i]["id"] == val["id"]){
			return i;
		}
	}
	return -1;
}


/***
 * 一键升级服务版本
 * @returns
 */
function batchUpgradeServiceVersions(){
	var appArray = selectData;//应用列表选中的应用记录
	
	if (appArray.length == 0) {
		parent.alertWarn("请选择应用服务！",function(){
			parent.closeAlert2();
		});
		return;
	}	
	
	var appIdArray = new Array();
	for(var i=0; i<appArray.length; i++){
		var existGray = appArray[i]["existGray"];
		if(existGray == "是"){
			parent.alertWarn("存在灰度版本的服务不进行更新！",function(){
				parent.closeAlert2();
			});
			return;
		}
		var status = appArray[i]["status"];
		if(status.indexOf("停止") > -1){
			parent.alertWarn("请选择处于运行状态的应用服务进行一键升级！",function(){
				parent.closeAlert2();
			});
			return;
		}
		/*
		var running_version = appArray[i]["running_version"];
		if(running_version == "无"){
			parent.alertWarn("请选择有运行版本的应用服务进行批量启动！",function(){
				parent.closeAlert2();
			});
			return;
		}
		*/
		appIdArray.push(appArray[i]["id"]);
	}	
	var appIds = appIdArray.toLocaleString();//应用id用逗号分隔
	
	parent.parent.alertConfirm("确认一键升级服务运行版本?", function() {
		showLoad();
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				"appServiceId" : appIds
			},
			url : "/paas/appservice/batchUpgradeServiceVersions.action",
			success : function(result) {
				closeLoad();
				if (result['resultCode'] == 'success') {
					parent.alertSuccess();
//					window.location.reload();
					queryAppServiceListByToken();
				} else {
					parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
//					window.location.reload();
					queryAppServiceListByToken();
				}
			}
		});
	});
}

/*
 * 批量启动应用
 * add by fubl
 */
function batchStartAppServices() {
	var appArray = selectData;//应用列表选中的应用记录
	
	if (appArray.length == 0) {
		parent.alertWarn("请选择应用服务！",function(){
			parent.closeAlert2();
		});
		return;
	}	
	
	var appIdArray = new Array();
	for(var i=0; i<appArray.length; i++){
		var status = appArray[i]["status"];
		if(status.indexOf("运行") > -1){
			parent.alertWarn("请选择处于停止状态的应用服务进行批量启动！",function(){
				parent.closeAlert2();
			});
			return;
		}
		var running_version = appArray[i]["running_version"];
		if(running_version == "无"){
			parent.alertWarn("请选择有运行版本的应用服务进行批量启动！",function(){
				parent.closeAlert2();
			});
			return;
		}
		appIdArray.push(appArray[i]["id"]);
	}	
	var appIds = appIdArray.toLocaleString();//应用id用逗号分隔
	
	parent.parent.alertConfirm("确认批量启动应用服务?", function() {
		showLoad();
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				"appServiceId" : appIds
			},
			url : "/paas/appservice/startAppServices.action",
			success : function(result) {
				closeLoad();
				if (result['resultCode'] == 'success') {
					parent.alertSuccess();
//					window.location.reload();
					queryAppServiceListByToken();
				} else {
					parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
//					window.location.reload();
					queryAppServiceListByToken();
				}
			}
		});
	});
}

/*
 * 批量停止应用
 * add by fubl
 */
function batchStopAppServices() {
	var appArray = selectData;
	
	if (appArray.length == 0) {
		parent.alertWarn("请选择应用服务！",function(){
			parent.closeAlert2();
		});
		return;
	}
	
	var appIdArray = new Array();
	for(var i=0; i<appArray.length; i++){
		var status = appArray[i]["status"];
		if(status.indexOf("停止") > -1){
			parent.alertWarn("请选择运行状态的应用服务进行批量停止！",function(){
				parent.closeAlert2();
			});
			return;
		}
		appIdArray.push(appArray[i]["id"]);
	}
	var appIds = appIdArray.toLocaleString();//应用id用逗号分隔
	
	parent.parent.alertConfirm("确认批量停止应用服务?", function() {
		showLoad();
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				"appServiceId" : appIds
			},
			url : "/paas/appservice/stopAppServices.action",
			success : function(result) {
				closeLoad();
				if (result['resultCode'] == 'success') {
					parent.alertSuccess();
//					window.location.reload();
					queryAppServiceListByToken();
				} else {
					parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
//					window.location.reload();
					queryAppServiceListByToken();
				}
			}
		});
	});
}

/*
 * 批量重启应用
 * add by fubl
 */
function batchRestartAppServices() {
	var appArray = selectData;
	
	if (appArray.length == 0) {
		parent.alertWarn("请选择应用服务！",function(){
			parent.closeAlert2();
		});
		return;
	}	
	
	var appIdArray = new Array();
	for(var i=0; i<appArray.length; i++){
		var status = appArray[i]["status"];	
		if(status.indexOf("停止") > -1){
			parent.alertWarn("请选择运行状态的应用服务进行批量重启！",function(){
				parent.closeAlert2();
			});
			return;
		}
		var running_version = appArray[i]["running_version"];
		if(running_version == "无"){
			parent.alertWarn("请选择有运行版本的应用服务进行批量重启！",function(){
				parent.closeAlert2();
			});
			return;			
		}	
		appIdArray.push(appArray[i]["id"]);
	}	
	var appServiceIds = appIdArray.toLocaleString();//应用服务id用逗号分隔
	
	parent.parent.alertConfirm("确认批量重启应用服务?", function() {
		showLoad();
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				"appServiceId" : appServiceIds
			},
			url : "/paas/appservice/forcedRestartAppServices.action",
			success : function(result) {
				closeLoad();
				if (result['resultCode'] == 'success') {
					parent.parent.alertSuccess();
//					window.location.reload();
					queryAppServiceListByToken();
				} else {
					parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
//					window.location.reload();
					queryAppServiceListByToken();
				}
			}
		});
	});
}

/*
 * 批量删除应用
 * add by fubl
 */
function batchDeleteAppServices() {
	var appArray = selectData;//应用列表选中的应用记录
	
	if (appArray.length == 0) {
		parent.alertWarn("请选择应用服务！",function(){
			parent.closeAlert2();
		});
		return;
	}	
	
	var appIdArray = new Array();
	for(var i=0; i<appArray.length; i++){
		var status = appArray[i]["status"];
		/*if(status.indexOf("运行") > -1){
			parent.alertWarn("请选择处于停止状态的应用进行批量删除！",function(){
				parent.closeAlert2();
			});
			return;
		}*/
		/*var running_version = appArray[i]["running_version"];
		if(running_version == "无"){
			parent.alertWarn("请选择有运行版本的应用进行批量启动！");
			return;
		}*/
		appIdArray.push(appArray[i]["id"]);
	}	
	var appServiceIds = appIdArray.toLocaleString();//应用id用逗号分隔
	
	parent.parent.alertConfirm("确认批量删除应用服务?", function() {
		showLoad();
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				"appServiceId" : appServiceIds
			},
			url : "/paas/appservice/deleteAppServices.action",
			success : function(result) {
				closeLoad();
				if (result['resultCode'] == 'success') {
					parent.parent.alertSuccess();
//					window.location.reload();
					queryAppServiceListByToken();
				} else {
					parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
//					window.location.reload();
					queryAppServiceListByToken();
				}
			}
		});
	});
}

function sendSignal2AppService(){
	
	var id = $("#id").val();
	var signal = $("#signal").val();

	if (signal == "") {
		parent.parent.alertWarn("信号不能为空",function(){
			parent.parent.closeAlert2();
		});
		return;
	}
	var reg1 = /^[0-9]+$/;
	/*if (signal.match(reg1) == null) {
		parent.parent.alertWarn("信号只能为非负整数",function(){
			parent.parent.closeAlert2();
		});
		$("#signal").focus();
		return;
	}*/
	if(signal.match(reg1) == null || (signal<1 || signal>64)){
		parent.parent.alertWarn("信号量只能在1-64之间！",function(){
			parent.parent.closeAlert2();
		});
		$("#signal").focus();
		return;
	}
	$.ajax({
		type : "POST",
		dataType : "json",
		data : {
			"appServiceId" : id,
			"signal" : signal
		},
		url : "/paas/appservice/sendSignal2AppService.action",
		success : function(result) {
			if (result['resultCode'] == 'success') {
				parent.parent.alertSuccess();
			} else {
				parent.parent.alertError(result['resultCode'],
						result['resultMessage']);
			}
		}
	});
}


function deleteAppServiceByWebsocket(){
	parent.parent.alertConfirm("确认删除应用服务?", function() {
		var appServiceid = $("#id").val();
		parent.parent.showLoad();
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				"appServiceId" : appServiceid
			},
			url : "/paas/appservice/deleteAppService.action",
			success : function(result){
				
				parent.parent.closeLoad();
				if (result['resultCode'] == 'success') {
					parent.parent.alertSuccess(function(){
//						top.location = "/paas/cicd/index.html";
						$("#mainIframe",window.parent.parent.document)[0].src="/paas/jsp/appservice/appserviceList.jsp?name=全部";
					});
				} else {
					parent.parent.alertError(result['resultCode'],
							result['resultMessage'],function(){
						//防止失败窗体关闭失败，回调清除函数
					});
				}
			}
		});
	});
}

function startAppServiceByWebsocket(){
	var id = $("#id").val();
	var appStatus = $("#status").val();
	if(appStatus == "2"){
		parent.parent.alertWarn("当前应用已经运行，不能执行启动。可以点击重启或者停止后再启动！",function(){
			parent.parent.closeAlert2();
		});
		return;
	}
	
	var flag = $("#restartconfig").text();
	if(flag.indexOf("请选择当前运行版本")!=-1){
		parent.parent.alertConfirm("该应用没有创建版本，无法启动，是否立即创建版本？",function(){
			//创建版本
			parent.parent.openDialog("/paas/appversion/queryVersion.action?appId="+ id+"&src=startApp", "创建版本", 555, 439);
		
		},function(){
//			parent.parent.closedl("createApp",result['resultMessage']);
			parent.parent.close();
		});
		
	}else{
//		parent.parent.alertConfirm("确认启动应用服务?",function(){
			$.ajax({
				type : "POST",
				dataType : "json",
				data : {
					"appServiceId" : id
				},
//				url : "/paas/appservice/startAppService.action",
				url : "/paas/appservice/checkAppServiceRelaIpaas.action",
				success : function(result){
					if (result['resultCode'] == 'success') {
						parent.parent.alertConfirm("确认启动应用服务?",function(){
							var requestUrl = "/paas/appservice/startAppService.action";
							if (requestUrl != "") {
								showLoad();
								$.ajax({
									type:"POST",
									dataType:"json",
									data: {
										"appServiceId":id
								        },
									url:requestUrl,
									success:function(result){
										closeLoad();
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
					} else if (result['resultCode'] == 'PAAS-20314') {
//						startAppService(result['resultMessage'], id);
						startAppService(result['resultMessage'], id);
					} else {
						parent.parent.alertError(result['resultCode'], result['resultMessage']);
					}
				}
			});
			/*
			var opra_url = id + "|" + "start-appService";
			var websocketUrl = "ws://"+window.location.host+"/paas/websocket/appService";
	        this.parent.parent.alertProgressConfirm(opra_url, websocketUrl, function(){
	        	window.location.reload();	        	
		    });*/
//		});
	}	
}

function startAppService(resultMessage, id){
	var requestUrl = "";
	parent.parent.alertConfirm4AllAndSelf(resultMessage, function(){ //一键启动
		requestUrl = "/paas/appservice/allStartAppService.action";
		if (requestUrl != "") {
			showLoad();
			$.ajax({
				type:"POST",
				dataType:"json",
				data: {
					"appServiceId":id
			        },
				url:requestUrl,
				success:function(result){
					closeLoad();
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
	}, function(){ //启动应用服务
		requestUrl = "/paas/appservice/startAppService.action";
		if (requestUrl != "") {
			showLoad();
			$.ajax({
				type:"POST",
				dataType:"json",
				data: {
					"appServiceId":id
			        },
				url:requestUrl,
				success:function(result){
					closeLoad();
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
 * 确定：一键启动应用服务和绑定的基础服务
 * 取消：启动应用服务自身
 */
//function allStartAppService(resultMessage, id){
//	
//	var requestUrl = "";
//	
//	parent.parent.alertConfirm(resultMessage, function(){ //确定
//		requestUrl = "/paas/appservice/allStartAppService.action";
//		if (requestUrl != "") {
//			parent.parent.showLoad();
//			$.ajax({
//				type:"POST",
//				dataType:"json",
//				data: {
//					"appServiceId":id
//			        },
//				url:requestUrl,
//				success:function(result){
//					parent.parent.closeLoad();
//					if(result['resultCode'] == 'success'){
//						parent.parent.alertSuccess();
//						window.location.reload(); 
//					}
//					else{
//						parent.parent.alertError(result['resultCode'],result['resultMessage']);
//					}
//				}
//			});
//		}
//	},
//	function (){ //取消
//		requestUrl = "/paas/appservice/selfStartAppService.action";
//		if (requestUrl != "") {
//			parent.parent.showLoad();
//			$.ajax({
//				type:"POST",
//				dataType:"json",
//				data: {
//					"appServiceId":id
//			        },
//				url:requestUrl,
//				success:function(result){
//					parent.parent.closeLoad();
//					if(result['resultCode'] == 'success'){
//						parent.parent.alertSuccess();
//						window.location.reload(); 
//					}
//					else{
//						parent.parent.alertError(result['resultCode'],result['resultMessage']);
//					}
//				}
//			});
//		}
//	});
//}

function restartAppServiceByWebsocket(){
	var id = $("#id").val();
	var appStatus = $("#status").val();
	if(appStatus == "1"){
		parent.parent.alertWarn("当前应用为停止状态，不能执行重启。可以点击启动！",function(){
			parent.parent.closeAlert2();
		});
		return;
	}
	
	var flag = $("#restartconfig").text();
	if(flag.indexOf("请选择当前运行版本")!=-1){
		parent.parent.alertConfirm("该应用没有创建版本，无法启动，是否立即创建版本？",function(){
			//创建版本
			parent.parent.openDialog("/paas/appversion/queryVersion.action?appId="+ id+"&src=startApp", "创建版本", 555, 439);
		
		},function(){
//			parent.parent.closedl("createApp",result['resultMessage']);
			parent.parent.close();
		});
		
	}else{
//		parent.parent.alertConfirm("确认重启应用服务?",function(){
//			var opra_url = id + "|" + "restart-appService";
//			var websocketUrl = "ws://"+window.location.host+"/paas/websocket/appService";
//	        this.parent.parent.alertProgressConfirm(opra_url, websocketUrl, function(){
//	        	window.location.reload();
//		    });
//		});
		
//		parent.parent.alertConfirm("确认重启应用服务?",function(){
			$.ajax({
				type : "POST",
				dataType : "json",
				data : {
					"appServiceId" : id
				},
				url : "/paas/appservice/checkAppServiceRelaIpaas.action",
				success : function(result){
					if (result['resultCode'] == 'success') {
						parent.parent.alertConfirm("确认重启应用服务?",function(){
							var opra_url = id + "%7C" + "restart-appService";
							var websocketUrl = "ws://"+window.location.host+"/paas/websocket/appService";
					        this.parent.parent.alertProgressConfirm(opra_url, websocketUrl, function(){
					        	window.location.reload();
						    });
						});
					} else if (result['resultCode'] == 'PAAS-20314') {
						restartAppService(result['resultMessage'], id);
					} else {
						parent.parent.alertError(result['resultCode'], result['resultMessage']);
					}
				}
			});
//		});
		
	}	
}

function restartAppService(resultMessage, id){
	parent.parent.alertConfirm4AllAndSelf(resultMessage, function(){ //一键启动
		var opra_url = id + "%7C" + "allrestart-appService";
		var websocketUrl = "ws://"+window.location.host+"/paas/websocket/appService";
        this.parent.parent.alertProgressConfirm(opra_url, websocketUrl, function(){
        	window.location.reload();
	    });
	}, function(){ //启动应用服务
		var opra_url = id + "%7C" + "restart-appService";
		var websocketUrl = "ws://"+window.location.host+"/paas/websocket/appService";
		this.parent.parent.alertProgressConfirm(opra_url, websocketUrl, function(){
        	window.location.reload();
	    });
	});
}
//从主页展示进度条
function showLoad(){	
	$('#loadImg').css({"width":"100%","height":"100%"});
	$('#loadImg').show();
}
function closeLoad(){
	$('#loadImg').hide();
}
function stopAppServiceByWebsocket(){
	var id = $("#id").val();
	var appServiceStatus = $("#status").val();
	if(appServiceStatus == "1"){
		parent.parent.alertWarn("当前应用已经处于停止状态！",function(){
			parent.parent.closeAlert2();
		});
		return;
	}
	parent.parent.alertConfirm("确认停止应用服务?",function(){
		var opra_url = id + "%7C" + "stop-appService";
		var websocketUrl = "ws://"+window.location.host+"/paas/websocket/appService";
        this.parent.parent.alertProgressConfirm(opra_url, websocketUrl, function(){
        	window.location.reload();	        	
	    });
	});
}
function getUrlParam(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r!= null) 
		return unescape(r[2]);
	return null;
}