
var roleType; //角色类型
var opertype;
var currentContext;
var colNames;
var colModel;

$(function(){
	roleType = $("#roleType").val(); //管理员：0，普通用户：1
	opertype = $("#opertype").val(); //操作类型，如开发1，测试2，运维3，只有应用才有操作类型
	currentContext = $("#currentContext").val(); // 当前网络环境标示(development-true;product-false)
	var publicImageId = $.getUrlParam('publicImageId');
	
	/**
	 * 管理员：列表中展示on/off状态，操作区展示发布生产
	 * 普通用户：列表中不展示on/off状态，操作区不展示发布生产，只能查看状态为on的公共镜像
	 */
	setColNamesAndModel(roleType, opertype);
	
	$("#publicImageName").keydown(function(e){
		if((e.keyCode || e.which)==13){
			reloadResult();
		}
	});

	//公共镜像列表
	$('#publicImageList').jqGrid({
		url:'/paas/publicImage/queryPublicImageList.action',
		datatype: "json",
		width: '100%',
		autowidth:true,
		height:"100%",
		cellEdit : true, //启用或者禁用单元格编辑功能
		cellurl : '/paas/publicImage/updateDescription.action', //单元格提交的URL
		postData:{
			publicImageId : publicImageId
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
			var page = $(this).getGridParam('page');
			var lastPage = $(this).getGridParam('lastpage');
			$("#page").val(page);
			$("#pageText").text(page + "/" +lastPage);
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
 * 根据开发测试、生产环境，角色类型和操作类型的不同
 * 分别定义列表所展示的字段
 * @param roleType 角色类型（管理员-0，普通用户-1）
 * @param opertype 操作类型（开发-1，测试-2，运维-3，只有应用才有操作类型）
 */
function setColNamesAndModel(roleType, opertype){
	
	if(opertype=="1,2" || opertype=="1" || opertype=="2"){ //开发测试环境
		if(roleType == "0"){
			devEnvAdminPubImgList();
		}else{
			devEnvNormalUserPubImgList();
		}
	} else if(opertype=="3"){ //生产环境
		if(roleType == "0"){
			prodEnvAdminPubImgList();
		}else{
			prodEnvNormalUserPubImgList();
		}
	} else { // 当环境初始化的时候，操作类型为空（opertype==""），只好用currentContext来区分环境
		if(currentContext == "true"){ // 开发测试环境
			if(roleType == "1"){ // 普通用户
				devEnvNormalUserPubImgList();
			} else { // 管理员
				devEnvAdminPubImgList();
			}
		} else { // 生产环境
			if(roleType == "1"){ // 普通用户
				prodEnvNormalUserPubImgList();
			} else { // 管理员
				prodEnvAdminPubImgList();
			}
		}
	}
}

/**
 * 开发测试环境管理员公共镜像列表
 */
function devEnvAdminPubImgList(){
//	loadList4Manager(); //管理员：列表中展示on/off状态，操作区展示发布生产
	var operType = $("#operTypeText",window.parent.document).html();
   	colNames = ['镜像名称','镜像url','版本','状态','配置文件路径','日志路径','启动命令','描述','操作',''];
   	colModel = [
   		{
   			name : "name",
   			index : "name",
   			align : 'center',
   		},
   		{
   			name : "registryUrl",
   			index : "registryUrl",
   			align : 'center',
//   			formatter : function(val, options, rowObject) {
//				return val.indexOf(":")>-1?val.replace(':','<br/>:'):val;
//			}
   		},
   		{
   			name : "version",
   			index : "version",
   			align : 'center',
   		},
   		{
   			name : "status",
   			index : "status",
   			align : 'center',
   			formatter : function(val, options, rowObject) {
   				var _id = rowObject["id"];
   				var _status = rowObject["status"];
   				if(_status == "1"){ //可见状态on
   					return '<a href="JavaScript:changeStatus(' +_id+ ','+_status+')" style="color:#666">'+
								'<img src="/paas/imgs/on.jpg" width="55" height="23" alt="on"/>'+
							'</a>';
   				}else if(_status == "0"){ //不可见状态off
   					return '<a href="JavaScript:changeStatus(' +_id+ ','+_status+')" style="color:#666">'+
								'<img src="/paas/imgs/off.jpg" width="55" height="23" alt="off"/>'+
							'</a>';
   				}
   			}
   		},
   		{
   			name : "configFilePath",
   			index : "configFilePath",
   			align : 'center',
//   			formatter : function(val, options, rowObject) {
//   				return val.indexOf("/")>-1?val.replace(val[val.lastIndexOf("/")+1],'<br/>'+val[val.lastIndexOf("/")+1]+''):val;
////   				alert(val.lastIndexOf("/"));
////   				alert(val[val.lastIndexOf("/")]);
////   				alert(val.replace(val[val.lastIndexOf("/")+1],'<br/>/'+val[val.lastIndexOf("/")+1]+''));
////   				return;
//			}
   		},
   		{
   			name : "logDir",
   			index : "logDir",
   			align : 'center',
   		},
   		{
   			name : "startCmd",
   			index : "startCmd",
   			align : 'center',
   		},
   		{
   			name : "description",
   			index : "description",
   			align : 'center',
   			editable : true, //单元格是否可编辑
   		},
   		{
   			name : 'id',
   			loginName : 'id', 
   			align : 'center',
   			formatter : function(val, options, rowObject) {
   				var _v = "'" +val+ "'";
	   			var _link = '';
   				var _status = rowObject["status"];
   				var versionId = rowObject["versionId"];
   				var type = '';
   				if(_status == "1"){ //可见状态on
   					if(operType != '运维'){
//   						type = '<a href="JavaScript:openPublish2Product(' +_v+','+versionId+')" style="color:#666">'+
   						type = '<a href="JavaScript:publish2Product(' +_v+','+versionId+')" style="color:#666">'+
						'<img src="/paas/imgs/production.png" width="22" height="22" border="none" title="发布生产"/>'+
						'</a>&nbsp;&nbsp;&nbsp;';
   					}
   					_link = type +
							'<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+val+'_&id='+val+'&image_type=1" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
								'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/>'+
							'</a>&nbsp;&nbsp;&nbsp;'+
							'<a href="JavaScript:openUpdatePublicImage(' +_v+ ')" style="color:#666">'+
								'<img src="/paas/imgs/modify.png" width="22" height="22" border="none" title="修改"/>'+
							'</a>&nbsp;&nbsp;&nbsp;';
   				}else if(_status == "0"){ //不可见状态off
   					_link = 
//   							'<a href="JavaScript:openUpdateImage(' +_v+')" style="color:#666">'+
//								'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/>'+
//							'</a>&nbsp;&nbsp;&nbsp;'+
							'<a href="JavaScript:openUpdatePublicImage(' +_v+ ')" style="color:#666">'+
								'<img src="/paas/imgs/modify.png" width="22" height="22" border="none" title="修改"/>'+
							'</a>&nbsp;&nbsp;&nbsp;';
   				}
	   			return _link;
   			}
   		},
   		{   
   			name: 'versionId',
   			index:'versionId',
   			hidden: true
   		}
//   		,{   
//   			name: 'id',
//   			index:'id',
//   			hidden: true
//   		}
   	];
}

/**
 * 开发测试环境普通用户公共镜像列表
 */
function devEnvNormalUserPubImgList(){
//	loadList4User(); //普通用户：列表中不展示on/off状态，操作区不展示发布生产，只能查看状态为on的公共镜像
   	colNames = ['镜像名称','镜像url','版本','配置文件路径','日志路径','启动命令','描述','操作'];
   	colModel = [
   		{
   			name : "name",
   			index : "name",
   			align : 'center',
   		},
   		{
   			name : "registryUrl",
   			index : "registryUrl",
   			align : 'center',
//			formatter : function(val, options, rowObject) {
//				return val.indexOf(":")>-1?val.replace(':','<br/>:'):val;
//			}
   		},
   		{
   			name : "version",
   			index : "version",
   			align : 'center',
   		},
   		{
   			name : "configFilePath",
   			index : "configFilePath",
   			align : 'center',
   		},
   		{
   			name : "logDir",
   			index : "logDir",
   			align : 'center',
   		},
   		{
   			name : "startCmd",
   			index : "startCmd",
   			align : 'center',
   		},
   		{
   			name : "description",
   			index : "description",
   			align : 'center',
   			editable : true, //单元格是否可编辑
   		},
   		{
   			name : 'id',
   			loginName : 'id', 
   			align : 'center',
   			formatter : function(val, options, rowObject) {
//	   			var _v = "'" +val+ "'";
	   			var _link = '';
//	   				_link = '<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+val+'&id='+val+'&image_type=1" onclick="linkPrivateImageVersionList()" style="color:#666">'+
//								'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/>'+
//							'</a>&nbsp;&nbsp;&nbsp;'+
//							'<a href="javaScript:openUpdatePublicImage(' +_v+ ')" style="color:#666">'+
//								'<img src="/paas/imgs/modify.png" width="22" height="22" border="none" title="修改"/>'+
//							'</a>&nbsp;&nbsp;&nbsp;';
	   			_link = '<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+val+'&id='+val+'&image_type=1" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
							'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/>'+
						'</a>&nbsp;&nbsp;&nbsp;';
	   			return _link;
   			}
   		}
//   		,{   
//   			name: 'id',
//   			index:'id',
//   			hidden: true
//   		}
   	];
}

/**
 * 生产环境管理员公共镜像列表
 */
function prodEnvAdminPubImgList(){
//	loadList4Manager(); //管理员：列表中展示on/off状态，操作区展示发布生产
//	var operType = $("#operTypeText",window.parent.document).html();
   	colNames = ['镜像名称','镜像url','版本','状态','镜像是否导入','配置文件路径','日志路径','启动命令','描述','操作',''];
   	colModel = [
   		{
   			name : "name",
   			index : "name",
   			align : 'center',
   		},
   		{
   			name : "registryUrl",
   			index : "registryUrl",
   			align : 'center',
//   			formatter : function(val, options, rowObject) {
//				return val.indexOf(":")>-1?val.replace(':','<br/>:'):val;
//			}
   		},
   		{
   			name : "version",
   			index : "version",
   			align : 'center',
   		},
   		{
   			name : "status",
   			index : "status",
   			align : 'center',
   			formatter : function(val, options, rowObject) {
   				var _id = rowObject["id"];
   				var _status = rowObject["status"];
   				if(_status == "1"){ //可见状态on
   					return '<a href="JavaScript:changeStatus(' +_id+ ','+_status+')" style="color:#666">'+
								'<img src="/paas/imgs/on.jpg" width="55" height="23" alt="on"/>'+
							'</a>';
   				}else if(_status == "0"){ //不可见状态off
   					return '<a href="JavaScript:changeStatus(' +_id+ ','+_status+')" style="color:#666">'+
								'<img src="/paas/imgs/off.jpg" width="55" height="23" alt="off"/>'+
							'</a>';
   				}
   			}
   		},
   		{
   			name : "isImported",
   			index : "isImported",
   			align : 'center',
   			formatter : function(val, options, rowObject) {
				var _s = rowObject['isImported'];													
				if (_s == "0") {
					return '未导入';
				} else if (_s == "1") {
					return '已导入';
				}
			}
   		},
   		{
   			name : "configFilePath",
   			index : "configFilePath",
   			align : 'center',
//   			formatter : function(val, options, rowObject) {
//   				return val.indexOf("/")>-1?val.replace(val[val.lastIndexOf("/")+1],'<br/>'+val[val.lastIndexOf("/")+1]+''):val;
////   				alert(val.lastIndexOf("/"));
////   				alert(val[val.lastIndexOf("/")]);
////   				alert(val.replace(val[val.lastIndexOf("/")+1],'<br/>/'+val[val.lastIndexOf("/")+1]+''));
////   				return;
//			}
   		},
   		{
   			name : "logDir",
   			index : "logDir",
   			align : 'center',
   		},
   		{
   			name : "startCmd",
   			index : "startCmd",
   			align : 'center',
   		},
   		{
   			name : "description",
   			index : "description",
   			align : 'center',
   			editable : true, //单元格是否可编辑
   		},
   		{
   			name : 'id',
   			loginName : 'id', 
   			align : 'center',
   			formatter : function(val, options, rowObject) {
   				var _v = "'" +val+ "'";
	   			var _link = '';
   				var _status = rowObject["status"];
   				var versionId = rowObject["versionId"];
   				var isImported = rowObject["isImported"];
   				var type = '';
   				var imported2Deploy = '';
   				if(_status == "1"){ //可见状态on
//   					if(operType != '运维'){
////   						type = '<a href="JavaScript:openPublish2Product(' +_v+','+versionId+')" style="color:#666">'+
//   						type = '<a href="JavaScript:publish2Product(' +_v+','+versionId+')" style="color:#666">'+
//						'<img src="/paas/imgs/production.png" width="22" height="22" border="none" title="发布生产"/>'+
//						'</a>&nbsp;&nbsp;&nbsp;';
//   					}
   					if(isImported == "1"){
   						imported2Deploy = '<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+val+'_&id='+val+'&image_type=1" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
						'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/>'+
						'</a>&nbsp;&nbsp;&nbsp;';
   					}
   					_link = type + imported2Deploy +
							'<a href="JavaScript:openUpdatePublicImage(' +_v+ ')" style="color:#666">'+
								'<img src="/paas/imgs/modify.png" width="22" height="22" border="none" title="修改"/>'+
							'</a>&nbsp;&nbsp;&nbsp;';
   				}else if(_status == "0"){ //不可见状态off
   					_link = '<a href="JavaScript:openUpdatePublicImage(' +_v+ ')" style="color:#666">'+
								'<img src="/paas/imgs/modify.png" width="22" height="22" border="none" title="修改"/>'+
							'</a>&nbsp;&nbsp;&nbsp;';
   				}
	   			return _link;
   			}
   		},
   		{   
   			name: 'versionId',
   			index:'versionId',
   			hidden: true
   		}
//   		,{   
//   			name: 'id',
//   			index:'id',
//   			hidden: true
//   		}
   	];
}

/**
 * 生产环境普通用户公共镜像列表
 */
function prodEnvNormalUserPubImgList(){
//	loadList4User(); //普通用户：列表中不展示on/off状态，操作区不展示发布生产，只能查看状态为on的公共镜像
   	colNames = ['镜像名称','镜像url','版本','镜像是否导入','配置文件路径','日志路径','启动命令','描述','操作'];
   	colModel = [
   		{
   			name : "name",
   			index : "name",
   			align : 'center',
   		},
   		{
   			name : "registryUrl",
   			index : "registryUrl",
   			align : 'center',
//			formatter : function(val, options, rowObject) {
//				return val.indexOf(":")>-1?val.replace(':','<br/>:'):val;
//			}
   		},
   		{
   			name : "version",
   			index : "version",
   			align : 'center',
   		},
   		{
   			name : "isImported",
   			index : "isImported",
   			align : 'center',
   			formatter : function(val, options, rowObject) {
				var _s = rowObject['isImported'];													
				if (_s == "0") {
					return '未导入';
				} else if (_s == "1") {
					return '已导入';
				}
			}
   		},
   		{
   			name : "configFilePath",
   			index : "configFilePath",
   			align : 'center',
   		},
   		{
   			name : "logDir",
   			index : "logDir",
   			align : 'center',
   		},
   		{
   			name : "startCmd",
   			index : "startCmd",
   			align : 'center',
   		},
   		{
   			name : "description",
   			index : "description",
   			align : 'center',
   			editable : true, //单元格是否可编辑
   		},
   		{
   			name : 'id',
   			loginName : 'id', 
   			align : 'center',
   			formatter : function(val, options, rowObject) {
   				var isImported = rowObject["isImported"];
	   			var _link = '';
	   			if(isImported == "1"){
	   				_link = '<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+val+'&id='+val+'&image_type=1" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
								'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/>'+
							'</a>&nbsp;&nbsp;&nbsp;';
	   			}
	   			return _link;
   			}
   		}
//   		,{   
//   			name: 'id',
//   			index:'id',
//   			hidden: true
//   		}
   	];
}

/**
 * 切换公共镜像可见状态on/off
 * @param id
 * @param status
 */
function changeStatus(id, status){
	$.ajax({
		type:"POST",
		dataType:"json",
		url:"/paas/publicImage/changeStatus.action",
        data:{
        	id : id,
        	status : status
        },
		success:function(result){
			if(result['resultCode'] == 'success'){
				parent.alertSuccess();
				parent.closedl("updatePublicImage","");
			}
			else{
				parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	});
}

/**
 * 公共镜像-发布生产
 */
//function publish2Product(){
function publish2Product(publicImageId, versionId){
	
//	var publicImageId = $("#publicImageId").val();
//	var versionId = $("#versionId").val();
//	var dataCenterIds = $("#dataCenterInfoId option:selected").val(); //数据中心ID
	
	// 去掉数据中心参数
//	var dataCenterIds = "";
//    $("input[name='dataCenterInfoId']:checkbox").each(function(){ 
//        if($(this).attr("checked")){
//        	if(dataCenterIds == ""){
//        		dataCenterIds += $(this).val();
//        	}else{
//        		dataCenterIds += "," + $(this).val();
//        	}
//        }
//    });
//    if(dataCenterIds == ""){
//    	parent.alertWarn("请选择数据中心");
//    	return;
//    }
	
//	parent.alertConfirm("确认发布生产?",function(){
//		showLoad(); //显示转圈等待
//		$.ajax({
//			type:"POST",
//			dataType:"json",
//			data: {
//				"publicImageId" : publicImageId,
//				"versionId" : versionId,
//				"dataCenterIds" : dataCenterIds
//		    },
//			url:"/paas/publicImage/publish2Product.action",
//			success:function(result){
//				closeLoad(); //关闭转圈
//				if(result['resultCode'] == 'success'){
//					parent.alertSuccess();
//					parent.closedl("publish2Product", "");
//				}
//				else{
//					parent.alertError(result['resultCode'], result['resultMessage']);
//				}
//			}
//		});
//	});
    
//	//显示中间消息（长连接）
//	parent.parent.alertConfirm("确认发布生产?",function(){
//		var opra_url = publicImageId + "|" + versionId + "|" + dataCenterIds;
//		var websocketUrl = "ws://"+window.location.host+"/paas/websocket/publicImage";
//        this.parent.parent.alertProgressConfirm(opra_url, websocketUrl, function(){
////        	window.location.reload();
//        	window.parent[0].location.reload();
//	    });
//	});
    
	//显示中间消息（长连接）
	parent.parent.alertConfirm("确认发布生产?",function(){
		var opra_url = publicImageId + "%7C" + versionId;
		var websocketUrl = "ws://"+window.location.host+"/paas/websocket/publicImage";
        this.parent.parent.alertProgressConfirm(opra_url, websocketUrl, function(){
        	window.parent[0].location.reload();
	    });
	});
}

/**
 * 打开公共镜像-发布生产
 * @param id
 * @param versionId
 */
//function openPublish2Product(id, versionId){
//	
//	parent.openDialog("/paas/publicImage/loadDataCenterInfo.action?publicImageId="+id+"&versionId="+versionId,"发布生产",600,200);
//}

/**
 * 打开修改镜像弹出框
 * @param id
 */
function openUpdatePublicImage(id){
	parent.openDialog("/paas/publicImage/queryPublicImageById.action?id="+id,"修改公共镜像",555,300);
}

/**
 * 提交修改的公共镜像信息
 */
function updatePublicImageSumbit(){
	
	var configFilePath = $.trim($("#configFilePath").val());
	if(configFilePath != ""){
		$("#configFilePath").val(configFilePath);
	}
	
	var startCmd = $.trim($("#startCmd").val());
	if(startCmd != ""){
		$("#startCmd").val(startCmd);
	}
	
	var logDir = $.trim($("#logDir").val());
	if(logDir != ""){
		$("#logDir").val(logDir);
	}
	
	var description = $.trim($("#description").val());
	if(description != ""){
		$("#description").val(description);
	}
	
	$.ajax({
		type:"POST",
		dataType:"json",
		url:"/paas/publicImage/updatePublicImage.action",
        data:$('#submitForm').serialize(),
		success:function(result){
			if(result['resultCode'] == 'success'){
				parent.alertSuccess();
				parent.closedl("updatePublicImage","");
			}
			else{
				parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	});
}

//通过名称模糊查询公有镜像
function reloadResult(){
	var o = $.trim($('#publicImageName').val());
	//通过循环，去掉字符串包含的所有空格
//	while(o.indexOf(" ") != -1){
//		o = o.replace(" ","");
//	}
	$("#publicImageList").jqGrid()
		.setGridParam({page:1,rowNum:10,mtype:"POST",datatype: "json",url : '/paas/publicImage/queryPublicImageList.action'})
		.setGridParam({postData:{'name':o}})
		.trigger("reloadGrid");
}
$(function(){
    $(window).resize(function(){   
    	$("#publicImageList").setGridWidth($(window).width()*0.99);
});
});
//当输入的跳转页数大于总页数是,自动查询最后一页
function reloadResults(totalPage){
    var rows = $('#publicImageList').getGridParam('rows'); 
    var sidx = $('#publicImageList').getGridParam('sidx'); 
    var sord = $('#publicImageList').getGridParam('sord'); 
    jQuery("#publicImageList").jqGrid('setGridParam', {
        url : '/paas/publicImage/queryPublicImageList.action',
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).trigger("reloadGrid");
}
//链接到服务管理菜单样式切换
//function linkPrivateImageVersionList(){
//	parent.forOldPageNav();
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
