/**
 * 基础服务配置（修改）js
 */

//初始化页面
$(function(){
	//queryServiceType();
	//queryNodeNumber();
	queryImagesByIpaasServiceTpye();
	queryClusterByIdAndType();
});

//切换服务类型(修改用不上)
function changeServiceType(){
	queryImagesByIpaasServiceTpye();
	queryDefaultConfigByIpaasServiceTpye();
};

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
	// alert(dataCenterId);
	 $.ajax({
			dataType : "json",
			async:true, 
			data : {appId:dataCenterId},
			url : '/paas/ipaasservice/queryClusterByIdAndType.action',
			success : function(result) {
				for(var i in result){
					 var item = new Option(result[i].name, result[i].id);    
					$("#clusterId").append(item);
					$("#clusterId").val($("#cId").val());
				}
			}
		 });
}

//初始化服务类型
function queryServiceType(){
	var json = eval('[{"value":"1","text":"zookeeper"},{"value":"2","text":"redis"}]');
	var serviceType = $("#serviceTypeHidden").val();
	$.each(json, function(i,val) {
		if(serviceType && serviceType == val['value']){
			$("#serviceType").append("<option selected='selected' value=" + this.value + ">" + this.text	+ "</option>");
		}else{
			$("#serviceType").append("<option value=" + this.value + ">" + this.text	+ "</option>");
		}
	});
}

//节点规模下拉框
function queryNodeNumber(){
	var serviceType = $("#serviceType").val();
	var nodeNumber = $("#nodeNumberHidden").val();
	document.getElementById("nodeNumber").innerHTML = ""; //清空之前的选项
	if(serviceType == "1"){
		var json = eval('[{"value":"1","text":"1"},{"value":"3","text":"3"},{"value":"5","text":"5"}]');
		$.each(json, function(i,val) {
			if(nodeNumber && nodeNumber == val['value']){
				$("#nodeNumber").append("<option selected='selected' value=" + this.value + ">" + this.text	+ "</option>");
			}else{
				$("#nodeNumber").append("<option value=" + this.value + ">" + this.text	+ "</option>");
			}
		});
	}else if(serviceType == "2"){
		var json = eval('[{"value":"1","text":"1"},{"value":"2","text":"2"},{"value":"6","text":"6"}]');
		$.each(json, function(i,val) {
			if(nodeNumber && nodeNumber == val['value']){
				$("#nodeNumber").append("<option selected='selected' value=" + this.value + ">" + this.text	+ "</option>");
			}else{
				$("#nodeNumber").append("<option value=" + this.value + ">" + this.text	+ "</option>");
			}
		});
	}
}

//根据基础服务类型查询镜像
function queryImagesByIpaasServiceTpye(){
	var serviceType = $("#serviceTypeHidden").val();
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
				var imageId = $("#imageIdHidden").val();
				var imageVersionId = $("#imageVersionIdHidden").val();
				imageId = imageId +"_" + imageVersionId; //imageId的值是 镜像id_镜像版本id
				$.each(json, function(i,val) {
					if(imageId && imageId == val['value']){
						$("#imageId").append("<option selected='selected' value=" + this.value + ">" + this.text	+ "</option>");
					}else{
						$("#imageId").append("<option value=" + this.value + ">" + this.text	+ "</option>");
					}
				});
			} else {
				parent.parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	});
}

//根据基础服务类型查询默认配置(修改用不上)
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

var fileTypeError = false;
var fileTypeErrorInfo = '支持的文件类型: conf,cfg,txt,properties。请重新选择文件';
function fileSelected() {
	var file = document.getElementById('configFile').files[0]; 
	if (file) {
		 var extend = file.name.substring(file.name.lastIndexOf(".")+1); 
	     if(extend != 'conf' && extend != 'cfg' 
	    	 && extend != 'txt' && extend != 'properties'){
	    	  parent.parent.alertWarn(fileTypeErrorInfo);
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
		parent.parent.alertWarn(fileTypeErrorInfo);
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
	 parent.parent.alertWarn("请选择文件!");
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
	parent.parent.alertWarn("上传文件错误！");
}
function uploadCanceled(evt) {
	parent.parent.alertWarn("用户取消文件上传!");
}

//保存基础构建
function saveIpaasService(){
	//校验参数
	var result = validateParameter();
	if(!result){
		return ;
	}
	$.ajax({
		type : "POST",
		dataType : "json",
		data : $('#ipaasServiceConfigForm').serialize(),
		url : '/paas/ipaasservice/modifyIpaasService.action',
		success : function(result) {
			if (result['resultCode'] == 'success') {
				parent.parent.alertSuccess();
				window.location.reload();
			} else {
				parent.parent.alertError(result['resultCode'],result['resultMessage']);
		    }
		}
	});
}

//重启基础服务
function restartIpaasService(ipaasServiceId){
	$.ajax({
		type:"POST",
		dataType:"json",
		data: {
			"ipaasServiceId":ipaasServiceId
	        },
		url:'/paas/ipaasservice/restartIpaasService.action',
		success:function(result){
			if(result['resultCode'] == 'success'){
				parent.parent.alertSuccess();
				$("#mainIframe",window.parent.document)[0].contentWindow.document.getElementById("search").click("return false");
				parent.close();
			} else{
				parent.parent.alertError(result['resultCode'],result['resultMessage']);
			}
		}
	});
}
//参数校验
function validateParameter(){
	//镜像名称
	var imageId = $.trim($("#imageId").val());
	if(!imageId){
		parent.parent.alertWarn('镜像名称不能为空!');
		return false;
	}
	//CPU均值
	var avgCpu = $.trim($("#avgCpu").val());
	if (avgCpu == "") {
		parent.parent.alertWarn("CPU均值不能为空！");
		return false;
	}else if(!(avgCpu<=64 && avgCpu>=0.01)){
		parent.parent.alertWarn("CPU均值在0.01到64之间，并且只能填写数字！");
		return false;
	}
	//CPU最大值
	var peakCpu = $.trim($("#peakCpu").val());
	if (peakCpu == "") {
		$("#peakCpu").focus();
		parent.parent.alertWarn("CPU最大值不能为空！");
		return false;
	}else if(!(peakCpu<=64 && peakCpu>=0.01)){
		parent.parent.alertWarn("CPU最大值在0.01到64之间，并且只能填写数字！");
		return false;
	}
	if(parseFloat(avgCpu) > parseFloat(peakCpu)){
		parent.parent.alertWarn("CPU的均值不能大于最大值！");
		return false;
	}
	//用户密码校验
	var user = $('#user').val();
	var pwd = $('#pwd').val();
	var patrnUser = /^[a-zA-Z]\w{3,9}$/;
	var patrnPwd = /^[0-9a-zA-Z]+$/;
	if($("#serviceTypeHidden").val() == '3'){
		if(!user){
			parent.parent.alertWarn('用户名不能为空!');
			return false;
		}
		if(!patrnUser.test(user)){
			parent.parent.alertWarn('用户名只能包含英文字符、数字和下划线，并且只能以英文开头的4-10位！');
			return false;
		}
		if(!pwd){
			parent.parent.alertWarn('密码不能为空!');
			return false;
		}
		if(pwd.match(patrnPwd)==null||pwd.length<6||pwd.length>16){
			parent.parent.alertWarn('密码由6-16位数字或字母或两者组合！');
			return false;
		}
	}
	
	//内存均值
	var avgMemory = $.trim($("#avgMemory").val());
	if (avgMemory == "") {
		parent.parent.alertWarn("内存均值不能为空！");
		return false;
	}else if(!(avgMemory<=20480 && avgMemory>=4) || avgMemory.match(/^[1-9]\d*$/)==null){
		parent.parent.alertWarn("内存均值在4M到20G之间，并且只能填写整数！");
		return false;
	}
	//内存最大值校验
	var peakMemory = $.trim($("#peakMemory").val());
	if (peakMemory == "") {
		$('#peakMemory').focus();
		parent.parent.alertWarn("内存最大值不能为空！");
		return false;
	}else if(!(peakMemory<=20480 && peakMemory>=4) || peakMemory.match(/^[1-9]\d*$/)==null){
		parent.parent.alertWarn("内存最大值在4M到20G之间，并且只能填写整数！");
		return false;
	}
	if(parseFloat(avgMemory) > parseFloat(peakMemory)){
		parent.parent.alertWarn("内存的均值不能大于最大值！");
		return false;
	}
	//配置文件内容
	var configInfo =  $.trim($("#configInfo").val());
	if(configInfo == ""){
		parent.parent.alertWarn("配置文件内容不能为空！");
		return false;
	}
	return true;
}