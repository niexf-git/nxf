
var privateImageId;
var roleType;
var opertype;
var appName;
var appId;
var selectedOpertype; //当前选中的操作类型
var colNames;
var colModel;

$(function(){
	
	$("#privateImageVersion").keydown(function(e){
		if((e.keyCode || e.which)==13){
			reloadResult();
		}
	});
	
	privateImageId = $.getUrlParam('id'); //由私有镜像进入私有镜像版本传过来的id
	appName = $.getUrlParam('appName'); 
	appId = $.getUrlParam('appId'); 
	roleType = $("#roleType").val(); //管理员：0，普通用户：1
	opertype = $("#opertype").val(); //操作类型，如开发1，测试2，运维3，只有应用才有操作类型
	selectedOpertype = $("#selectedOpertype").val();
	
	setColNamesAndModel(opertype);
	
	//私有镜像版本列表
	$('#privateImageVersionList').jqGrid({
		url:'/paas/privateImage/queryPrivateImageVersionList.action',
		datatype: "json",
		width: '100%',
		autowidth:true,
		height:"100%",
//		cellEdit : true, //启用或者禁用单元格编辑功能
//		cellurl : '/paas/privateImage/updateVersion.action', //单元格提交的URL
		postData:{
			id : privateImageId,
			appId : appId
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
	    gridComplete : function() {
	    	var result = $(this).getGridParam('userData');
			if (typeof(result['errorCode'])!='undefined') {
				parent.alertError(result['errorCode'],result['errorMsg']);
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
function setColNamesAndModel(opertype){
	if(opertype=="1,2" || opertype=="1" || opertype=="2"){ //开发测试环境
	   	colNames = ['版本','状态','创建时间','镜像url','操作'];
	   	colModel = [
	   		{
	   			name : "version",
	   			index : "version",
	   			align : 'center',
//	   			editable : true, //单元格是否可编辑
	   		},
	   		{
	   			name : "status",
	   			index : "status",
	   			align : 'center',
	   			formatter : function(val, options, rowObject) {
					var status = rowObject["status"];
					if(status == "1"){ //开发
						return "开发";
					}else if(status == "2"){ //测试
						return "测试";
					}else if(status == "3"){
						return "生产";
					}
				}
	   		},
	   		{
	   			name : "createTime",
	   			index : "createTime",
	   			align : 'center',
	   		},
	   		{
	   			name : "url",
	   			index : "url",
	   			align : 'center',
	   		},
	   		{
	   			name:'id',
	   			loginName:'id', 
	   			align : 'center'
		   		,formatter : function(val, options, rowObject) {
					var status =  rowObject["status"];
					var version = "'" +rowObject["version"]+ "'"; //私有镜像版本号
					var _v = "'" +val+ "'"; //私有镜像版本ID
					var _link = '';
					
					if(roleType == "0"){ //管理员
						if(selectedOpertype == "1,2"){
							if(status == "1"){
								_link = '<a href="JavaScript:publish2Test(' +_v+')" style="color:#666">'+
										'<img src="/paas/imgs/test.png" width="22" height="22" border="none" title="发布测试"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="javaScript:deletePrivateImageVersion(' +_v+ ',' +status+ ')" style="color:#666">'+
										'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/></a>&nbsp;&nbsp;&nbsp;';	
							}else if(status == "2"){
								_link = '<a href="JavaScript:openPublish2Product(' +_v+','+privateImageId+','+version+')" style="color:#666">'+
										'<img src="/paas/imgs/production.png" width="22" height="22" border="none" title="发布生产"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="javaScript:deletePrivateImageVersion(' +_v+ ',' +status+ ')" style="color:#666">'+
										'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/></a>&nbsp;&nbsp;&nbsp;';	
							}else if(status == "3"){
								_link = '<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;';	
							}
						}else if(selectedOpertype == "1"){
							if(status == "1"){ //管理员/开发人员：操作区功能为发布测试
								_link = '<a href="JavaScript:publish2Test(' +_v+')" style="color:#666">'+
										'<img src="/paas/imgs/test.png" width="22" height="22" border="none" title="发布测试"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="javaScript:deletePrivateImageVersion(' +_v+ ',' +status+ ')" style="color:#666">'+
										'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/></a>&nbsp;&nbsp;&nbsp;';	
							}else if(status == "2"){ //管理员/测试人员：操作区功能为发布生产
								_link = '<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;';	
							}else if(status == "3"){ //管理员/生产人员
								_link = '<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;';	
							}
						}else if(selectedOpertype == "2"){
							if(status == "2"){
								_link = '<a href="JavaScript:openPublish2Product(' +_v+','+privateImageId+','+version+')" style="color:#666">'+
										'<img src="/paas/imgs/production.png" width="22" height="22" border="none" title="发布生产"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="javaScript:deletePrivateImageVersion(' +_v+ ',' +status+ ')" style="color:#666">'+
										'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/></a>&nbsp;&nbsp;&nbsp;';	
							}else if(status == "3"){
								_link = '<a href="JavaScript:openPublish2Product(' +_v+','+privateImageId+','+version+')" style="color:#666">'+
										'<img src="/paas/imgs/production.png" width="22" height="22" border="none" title="发布生产"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;';	
							}
						}
					}else{ //普通用户
						if(opertype == "1" || selectedOpertype == "1"){ //开发
							if(status == "1"){ //管理员/开发人员：操作区功能为发布测试
								_link = '<a href="JavaScript:publish2Test(' +_v+')" style="color:#666">'+
										'<img src="/paas/imgs/test.png" width="22" height="22" border="none" title="发布测试"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="javaScript:deletePrivateImageVersion(' +_v+ ',' +status+ ')" style="color:#666">'+
										'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/></a>&nbsp;&nbsp;&nbsp;';	
							}else if(status == "2"){
								_link = '<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;';
							}else if(status == "3"){
								_link = '<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;';
							}
						}else if(opertype == "2" || selectedOpertype == "2"){ //测试
							if(status == "2"){ //管理员/测试人员：操作区功能为发布生产
								_link = '<a href="JavaScript:openPublish2Product(' +_v+','+privateImageId+','+version+')" style="color:#666">'+
										'<img src="/paas/imgs/production.png" width="22" height="22" border="none" title="发布生产"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="javaScript:deletePrivateImageVersion(' +_v+ ',' +status+ ')" style="color:#666">'+
										'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/></a>&nbsp;&nbsp;&nbsp;';	
							}else if(status == "3"){ //管理员/测试人员：操作区功能为发布生产
								_link = '<a href="JavaScript:openPublish2Product(' +_v+','+privateImageId+','+version+')" style="color:#666">'+
										'<img src="/paas/imgs/production.png" width="22" height="22" border="none" title="发布生产"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;';	
							}
						}else if(opertype == "3"){ //运维
							if(status == "3"){ //管理员/生产人员
								_link = '<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="javaScript:deletePrivateImageVersion(' +_v+ ',' +status+ ')" style="color:#666">'+
										'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/></a>&nbsp;&nbsp;&nbsp;';	
							}
						}else if(opertype == "1,2"){ //开发、测试
							if(status == "1"){ //管理员/开发人员：操作区功能为发布测试
								_link = '<a href="JavaScript:publish2Test(' +_v+')" style="color:#666">'+
										'<img src="/paas/imgs/test.png" width="22" height="22" border="none" title="发布测试"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="javaScript:deletePrivateImageVersion(' +_v+ ',' +status+ ')" style="color:#666">'+
										'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/></a>&nbsp;&nbsp;&nbsp;';	
							}else if(status == "2"){ //管理员/测试人员：操作区功能为发布生产
								_link = '<a href="JavaScript:openPublish2Product(' +_v+','+privateImageId+','+version+')" style="color:#666">'+
										'<img src="/paas/imgs/production.png" width="22" height="22" border="none" title="发布生产"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="javaScript:deletePrivateImageVersion(' +_v+ ',' +status+ ')" style="color:#666">'+
										'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/></a>&nbsp;&nbsp;&nbsp;';	
							}else if(status == "3"){ //管理员/测试人员：操作区功能为发布生产
								_link = '<a href="JavaScript:openPublish2Product(' +_v+','+privateImageId+','+version+')" style="color:#666">'+
										'<img src="/paas/imgs/production.png" width="22" height="22" border="none" title="发布生产"/></a>&nbsp;&nbsp;&nbsp;'+
										'<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
										'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;';	
							}
						}
					}
					return _link;
				}
	   		}
//	   		,{
//	   			name: 'id',
//	   			index:'id',
//	   			hidden: true 
//	   		}
	   	];
	} else if(opertype=="3"){ //生产环境
	   	colNames = ['版本','状态','镜像是否导入','创建时间','镜像url','操作'];
	   	colModel = [
	   		{
	   			name : "version",
	   			index : "version",
	   			align : 'center',
//	   			editable : true, //单元格是否可编辑
	   		},
	   		{
	   			name : "status",
	   			index : "status",
	   			align : 'center',
	   			formatter : function(val, options, rowObject) {
					var status = rowObject["status"];
					if(status == "1"){ //开发
						return "开发";
					}else if(status == "2"){ //测试
						return "测试";
					}else if(status == "3"){
						return "生产";
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
	   			name : "createTime",
	   			index : "createTime",
	   			align : 'center',
	   		},
	   		{
	   			name : "url",
	   			index : "url",
	   			align : 'center',
	   		},
	   		{
	   			name:'id',
	   			loginName:'id', 
	   			align : 'center'
		   		,formatter : function(val, options, rowObject) {
					var status =  rowObject["status"];
//					var version = "'" +rowObject["version"]+ "'"; //私有镜像版本号
					var _v = "'" +val+ "'"; //私有镜像版本ID
					var _link = '';
					var isImported = rowObject["isImported"];
					var imported2Deploy = '';
					
					if(roleType == "0"){ //管理员
						if(selectedOpertype == "3"){
							if(status == "3"){
								if(isImported == "1"){
									imported2Deploy = '<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
									'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;';
								}
								_link = imported2Deploy+
										'<a href="javaScript:deletePrivateImageVersion(' +_v+ ',' +status+ ')" style="color:#666">'+
										'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/></a>&nbsp;&nbsp;&nbsp;';	
							}
						}
					}else{ //普通用户
						if(opertype == "3"){ //运维
							if(status == "3"){ //管理员/生产人员
								if(isImported == "1"){
									imported2Deploy = '<a href="/paas/jsp/appservice/appserviceList.jsp?imageId='+status+'_'+appName+'_3&appId='+appId+'&id='+privateImageId+'&versionId='+val+'&image_type=3" onclick="linkPrivateImageVersionList(this)" style="color:#666">'+
									'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/></a>&nbsp;&nbsp;&nbsp;';
								}
								_link = imported2Deploy+
										'<a href="javaScript:deletePrivateImageVersion(' +_v+ ',' +status+ ')" style="color:#666">'+
										'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/></a>&nbsp;&nbsp;&nbsp;';	
							}
						}
					}
					return _link;
				}
	   		}
//	   		,{
//	   			name: 'id',
//	   			index:'id',
//	   			hidden: true 
//	   		}
	   	];
	}
}

/**
 * 私有镜像版本-发布测试
 * @param id
 */
function publish2Test(id){
	
	parent.alertConfirm("确认发布测试?",function(){
		$.ajax({
			type:"POST",
			dataType:"json",
			data: {
				"privateImageVersionId" : id,
				"privateImageId" : privateImageId,
		    },
			url:"/paas/privateImage/publish2Test.action",
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

/**
 * 打开私有镜像版本-发布生产
 * @param versionId
 * @param privateImageId
 * @param version
 */
function openPublish2Product(versionId, privateImageId, version){
	
//	parent.openDialog("/paas/privateImage/loadPrivateImageVersion.action?privateImageId="+privateImageId+"&privateImageVersionId="+versionId+"&version="+version,"发布生产",600,104);
	parent.openDialog("/paas/jsp/imagemanager/privateimage/publish2Product.jsp?privateImageId="+privateImageId+"&privateImageVersionId="+versionId+"&version="+version,"发布生产",600,104);
}

/**
 * 私有镜像版本-发布生产
 */
function publish2Product(){
	
//	parent.alertConfirm("确认发布生产?",function(){
//		$.ajax({
//			type:"POST",
//			dataType:"json",
//			data:$('#submitForm').serialize(), //获取参数
//			url:"/paas/privateImage/publish2Product.action",
//			success:function(result){
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
	var privateImageId = $("#privateImageId").val();
	var privateImageVersionId = $("#privateImageVersionId").val();
	var version = $("#version").val();
	if(version.trim() == ''){
		parent.alertWarn("版本号不能为空！");
		return;
	}
	if(version.indexOf(' ')>-1){
		parent.alertWarn("版本号不能包含空格！");
		return;
	}
	var reg = /^[a-zA-X0-9-_\.]+$/;
	if(version.match(reg) == null){
		parent.alertWarn("版本号只能包含字母、数字、下划线、中划线、点号，不能有其他特殊字符！");
	    $("#version").focus();
	    return;
	}
	if(version.trim().length>20){
		parent.alertWarn("版本号最大长度为20个字符！");
		return;
	}
//	var currentVersion = $("#currentVersion").val(); //获取当前私有镜像版本的版本号
	
	$.ajax({
		type:"POST",
		dataType:"json",
		data: {
			"privateImageVersionId" : privateImageVersionId,
			"privateImageId" : privateImageId,
			"version" : version
	    },
		url:"/paas/privateImage/queryPrivateImageVersion.action",
		success:function(result){
			if(result['resultCode'] == 'success'){ //当前要发布的私有镜像版本的版本号不能和其他版本号相同
				parent.alertWarn("该版本号已经发布过！");
				return;
			}else if(result['resultCode'] == 'error'){
				//显示中间消息（长连接）
				parent.parent.alertConfirm("确认发布生产?",function(){
					var opra_url = privateImageId + "%7C" + privateImageVersionId + "%7C" + version;
					var websocketUrl = "ws://"+window.location.host+"/paas/websocket/privateImage";
			        this.parent.parent.alertProgressConfirm(opra_url, websocketUrl, function(){
			        	window.parent[0].location.reload();
				    });
				});
			}
		}
	});
}

/**
 * 删除私有镜像版本
 * @param id
 */
function deletePrivateImageVersion(id, status){
	
	parent.alertConfirm("确认删除私有镜像版本?",function(){
		$.ajax({
			type:"POST",
			dataType:"json",
			data: {
				"privateImageVersionId" : id,
				"privateImageId" : privateImageId,
				"status" : status
		    },
			url:"/paas/privateImage/deletePrivateImageVersion.action",
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

//通过版本查询
function reloadResult(){
	var o = $.trim($('#privateImageVersion').val());
	//通过循环，去掉字符串包含的所有空格
//	while(o.indexOf(" ") != -1){
//		o = o.replace(" ","");
//	}
	$("#privateImageVersionList").jqGrid()
		.setGridParam({page:1,rowNum:10,mtype:"POST",datatype: "json",url : '/paas/privateImage/queryPrivateImageVersionList.action'})
		.setGridParam({postData:{'version':o}})
		.trigger("reloadGrid");
}

$(function(){
    $(window).resize(function(){
    	$("#privateImageVersion").setGridWidth($(window).width()*0.99);
    });
});

//当输入的跳转页数大于总页数是,自动查询最后一页
function reloadResults(totalPage){
    var rows = $('#privateImageVersionList').getGridParam('rows'); 
    var sidx = $('#privateImageVersionList').getGridParam('sidx'); 
    var sord = $('#privateImageVersionList').getGridParam('sord'); 
    jQuery("#privateImageVersionList").jqGrid('setGridParam', {
        url : '/paas/privateImage/queryPrivateImageVersionList.action',
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).trigger("reloadGrid");
}
//链接到服务管理菜单样式切换
//function linkPrivateImageVersionList(){
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