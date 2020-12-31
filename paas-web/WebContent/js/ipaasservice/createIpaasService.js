/**
 *  创建基础服务 js
 */

//初始化页面
$(function(){
	changeServiceType();
	queryClusterByIdAndType();
});
 function getDataCenterIdByAppId(){
	 var dataCenterId = 0;
	 var appId = $("#appPerSelectedId").val();//当前选中的应用
	 $.ajax({
		dataType : "json",
		async:false, 
		data : {id:appId},
		url : '/paas/application/queryDnsAndClusterById.action',
		success : function(result) {
			dataCenterId = result.clusters[0].cluster.dataCenterId;
		}
	 });
	 return dataCenterId;
 }
 //查询集群下拉框
 function queryClusterByIdAndType(){
	 var dataCenterId = getDataCenterIdByAppId();
	 $.ajax({
			dataType : "json",
			async:true, 
			data : {appId:dataCenterId},
			url : '/paas/ipaasservice/queryClusterByIdAndType.action',
			success : function(result) {
				for(var i in result){
					 var item = new Option(result[i].name, result[i].id);    
					$("#clusterId").append(item);
				}
			}
		 });
 }
//根据基础服务类型查询镜像
function queryImagesByIpaasServiceTpye(){
	var serviceType = $("#serviceType").val();
	$.ajax({
		type : "POST",
		dataType : "json",
		data: {
			"serviceType" : serviceType
		},
		url : '/paas/ipaasservice/queryImagesByIpaasServiceTpye.action',
		success : function(result) {
			if (result['resultCode'] == 'success') {
				document.getElementById("imageId").innerHTML = "";
				var json = eval(result['resultMessage']);
				$.each(json, function() {
					$("#imageId").append("<option value=" + this.value + ">" + this.text	+ "</option>");
				});
			} else {
				parent.parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	});
}

//根据基础服务类型查询默认配置
function queryDefaultConfigByIpaasServiceTpye(){
	var serviceType = $("#serviceType").val();
	//参数
	$.ajax({
		type : "POST",
		dataType : "json",
		data: {
			"serviceType" : serviceType
		},
		url : '/paas/ipaasservice/queryDefaultConfigByIpaasServiceTpye.action',
		success : function(result) {
			if (result['resultCode'] == 'success') {
				$("#configInfo").val(result['resultMessage']);
			} else {
				parent.parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	});
}

//节点规模下拉框
function queryNodeNumber(){
	var serviceType = $("#serviceType").val();
	document.getElementById("nodeNumber").innerHTML = ""; //清空之前的选项
	if(serviceType == "1"){
		var json = eval('[{"value":"1","text":"1"},{"value":"3","text":"3"},{"value":"5","text":"5"}]');
		$.each(json, function() {
			$("#nodeNumber").append("<option value=" + this.value + ">" + this.text	+ "</option>");
		});
	}else if(serviceType == "2"||serviceType == "3"){
		var json = eval('[{"value":"1","text":"1"},{"value":"2","text":"2"},{"value":"6","text":"6"}]');
		$.each(json, function() {
			$("#nodeNumber").append("<option value=" + this.value + ">" + this.text	+ "</option>");
		});
	}
}
function loadUser(){
	var serviceType = $("#serviceType").val();
	var pwd = $('#pwd');
	var user = $('#user');
	if(serviceType == '3'){
		user.val('');
		pwd.val('');
		user.closest('tr').show();
		pwd.closest('tr').show();
	}else{
		user.closest('tr').hide();
		pwd.closest('tr').hide();
	}
}

//切换服务类型
function changeServiceType(){
	loadUser();
	queryNodeNumber();
	queryImagesByIpaasServiceTpye();
	queryDefaultConfigByIpaasServiceTpye();
};

var fileTypeError = false;
var fileTypeErrorInfo = '支持的文件类型: conf,cfg,txt,properties。请重新选择文件';
function fileSelected() {
	var file = document.getElementById('configFile').files[0]; 
	if (file) {
		 var extend = file.name.substring(file.name.lastIndexOf(".")+1); 
	     if(extend != 'conf' && extend != 'cfg' 
	    	 && extend != 'txt' && extend != 'properties'){
		   	  parent.alertWarn(fileTypeErrorInfo);
		   	  fileTypeError = true; 
		   	  return;
	     }
	     fileTypeError = false;
	}
}

var config_file_name = "";
//上传配置文件
function uploadConfigFile(){
	if(fileTypeError){
		parent.alertWarn(fileTypeErrorInfo);
		return;
	}
	
	var file = document.getElementById('configFile').files[0]; 
	if (file) {
		config_file_name = file.name;
		var fd = new FormData();
	    fd.append("configFile", document.getElementById('configFile').files[0]);
	    fd.append("configFileName", document.getElementById('configFile').files[0].name);
	    var xhr = new XMLHttpRequest();
	    xhr.upload.addEventListener("progress", null, false);
	    xhr.addEventListener("load", uploadComplete, false);
	    xhr.addEventListener("error", uploadFailed, false);
	    xhr.addEventListener("abort", uploadCanceled, false);
	    xhr.open("POST", "/paas/ipaasservice/uploadConfigFile.action");
	    xhr.send(fd);
   }else{
	   parent.alertWarn("请选择文件!");
   }
}
//导入配置文件成功后 把文件内容展示到页面
function uploadComplete(evt) {
	var result = JSON.parse(evt.target.responseText);
	if (result['resultCode'] == 'success') {
		$("#configInfo").val(result['resultMessage']);
		parent.parent.alertSuccess();
	}else{
		parent.parent.alertError(result['resultCode'], result['resultMessage']);
	}
}
function uploadFailed(evt) {
    parent.alertWarn("上传文件错误！");
}
function uploadCanceled(evt) {
    parent.alertWarn("用户取消文件上传!");
}

//保存基础构建
function saveIpaasService(){
	//校验参数
	var result = validateParameter();
	if(!result){
		return ;
	}
	showLoad();
	$.ajax({
		type : "POST",
		dataType : "json",
		data : $('#ipaasServiceForm').serialize(),
		url : '/paas/ipaasservice/createIpaasService.action',
		success : function(result) {
			closeLoad();
			if (result['resultCode'] == 'success') {
				parent.parent.alertConfirm("操作成功！是否启动基础服务？",function(){
					showLoad();
					startIpaasService(result['resultMessage']);
				},function(){
					$("#mainIframe",window.parent.document)[0].contentWindow.document.getElementById("search").click("return false");
					parent.close();
				});
			} else {
				parent.alertError(result['resultCode'],result['resultMessage']);
		    }
		}
	});
}

//参数校验
function validateParameter(){
	//基础服务名称
	var serviceName = $.trim($("#serviceName").val());
	if(!serviceName){
		parent.alertWarn('服务名称不能为空!');
		return false;
	}else{
		var reg = /^[a-zA-Z][a-zA-Z0-9]*$/;	
		if (serviceName.match(reg) == null || serviceName.length>16) {
			parent.alertWarn("服务名称由英文字母开头，数字、英文组合，并且最长16个字符！");
			return false;
		}
	}
	//基础服务类型
	var serviceType = $.trim($("#serviceType").val());
	if(!serviceType){
		parent.alertWarn('服务类型不能为空!');
		return false;
	}
	//集群名称
	var clusterId = $.trim($("#clusterId").val());
	if(!clusterId){
		parent.alertWarn('集群不能为空!');
		return false;
	}
	//用户名密码校验
	var user = $('#user').val();
	var pwd = $('#pwd').val();
	var patrnUser = /^[a-zA-Z]\w{3,9}$/;
	var patrnPwd = /^[0-9a-zA-Z]+$/;
	if($("#serviceType").val() == '3'){
		if(!user){
			parent.alertWarn('用户名不能为空!');
			return false;
		}
		if(!patrnUser.test(user)){
			parent.alertWarn('用户名只能包含英文字符、数字和下划线，并且只能以英文开头的4-10位！');
			return false;
		}
		if(!pwd){
			parent.alertWarn('密码不能为空!');
			return false;
		}
		if(pwd.match(patrnPwd)==null||pwd.length<6||pwd.length>16){
			parent.alertWarn('密码由6-16位数字或字母或两者组合！');
			return false;
		}
	}
	
	
	//节点规模
	var nodeNumber = $.trim($("#nodeNumber").val());
	if(!nodeNumber){
		parent.alertWarn('节点规模不能为空!');
		return false;
	}
	//镜像名称
	var imageId = $.trim($("#imageId").val());
	if(!imageId){
		parent.alertWarn('镜像名称不能为空!');
		return false;
	}
	//CPU均值
	var avgCpu = $.trim($("#avgCpu").val());
	if (avgCpu == "") {
		parent.alertWarn("CPU均值不能为空！");
		return false;
	}else if(!(avgCpu<=64 && avgCpu>=0.01)){
		parent.alertWarn("CPU均值在0.01到64之间，并且只能填写数字！");
		return false;
	}
	//CPU最大值
	var peakCpu = $.trim($("#peakCpu").val());
	if (peakCpu == "") {
		$("#peakCpu").focus();
		parent.alertWarn("CPU最大值不能为空！");
		return false;
	}else if(!(peakCpu<=64 && peakCpu>=0.01)){
		parent.alertWarn("CPU最大值在0.01到64之间，并且只能填写数字！");
		return false;
	}
	if(parseFloat(avgCpu) > parseFloat(peakCpu)){
		parent.alertWarn("CPU的均值不能大于最大值！");
		return false;
	}
	//内存均值
	var avgMemory = $.trim($("#avgMemory").val());
	if (avgMemory == "") {
		parent.alertWarn("内存均值不能为空！");
		return false;
	}else if(!(avgMemory<=20480 && avgMemory>=4) || avgMemory.match(/^[1-9]\d*$/)==null){
		parent.alertWarn("内存均值在4M到20G之间，并且只能填写整数！");
		return false;
	}
	//内存最大值校验
	var peakMemory = $.trim($("#peakMemory").val());
	if (peakMemory == "") {
		$('#peakMemory').focus();
		parent.alertWarn("内存最大值不能为空！");
		return false;
	}else if(!(peakMemory<=20480 && peakMemory>=4) || peakMemory.match(/^[1-9]\d*$/)==null){
		parent.alertWarn("内存最大值在4M到20G之间，并且只能填写整数！");
		return false;
	}
	if(parseFloat(avgMemory) > parseFloat(peakMemory)){
		parent.alertWarn("内存的均值不能大于最大值！");
		return false;
	}
	//配置文件内容
	var configInfo =  $.trim($("#configInfo").val());
	if(configInfo == ""){
		parent.alertWarn("配置文件内容不能为空！");
		return false;
	}
	return true;
}
//从主页展示进度条
function showLoad(){	
	$('#loadImg').css({"width":"100%","height":"100%"});
	$('#loadImg').show();
}
function closeLoad(){
	$('#loadImg').hide();
}
//启动基础服务
function startIpaasService(ipaasServiceId){
	$.ajax({
		type:"POST",
		dataType:"json",
		data: {
			"ipaasServiceId":ipaasServiceId
	        },
		url:'/paas/ipaasservice/startIpaasService.action',
		success:function(result){
			closeLoad();
			if(result['resultCode'] == 'success'){
				parent.alertSuccess();
				$("#mainIframe",window.parent.document)[0].contentWindow.document.getElementById("search").click("return false");
				parent.close();
			} else{
				parent.alertError(result['resultCode'],result['resultMessage']);
			}
		}
	});
}