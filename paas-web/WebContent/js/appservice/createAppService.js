//----------------创建应用服务 - 基础信息-------------------------------------

var env_file_name = "";//用来判断环境变量文件是否已经导入过一次
//var env_file_random = "";//界面上每次导入环境变量文件，或者导出环境变量文件，都会生成一个带当前实际毫秒数的文件名
var ipaasCount=0; //添加的ipaas服务个数
/**
 * 显示应用服务基本信息页面
 */
function showInfo() {
	$("#appServiceInfo").show();
	$("#appServiceEnvs").hide();
	$("#appServiceConfigFile").hide();
	$("#li1").removeAttr('style');
	$("#li2").removeAttr('style');
	$("#li3").removeAttr('style');
	$("#li1").attr('style','width:123px; height:30px; background-color:#ccc;');
}


/**
 * 应用服务基本信息页面参数校验
 */
function validateParameter(){
	//名称校验
	var name = $.trim($("#name").val());	
	if (!name) {
		showInfo();		
		parent.alertWarn("请输入服务名称！");		
		return;
	}else{
		var reg = /^[a-zA-Z][a-zA-Z0-9]*$/;	
		if (name.match(reg) == null || name.length>15) {
			showInfo();
			parent.alertWarn("服务名称由英文字母开头，数字、英文组合，并且最长15个字符！");
			return;
		}
	}	
	
	//集群名称校验	
	if($("#cluster_name").val()=="请选择"){
		showInfo();		
		parent.alertWarn("请选择集群名称！");
		return;
	}
	//没有可用集群校验
	if($("#cluster_name").text()==""){
		showInfo();		
		parent.alertWarn("没有可用集群！");
		return;
	}
	
	//镜像名称校验
	if($("#image_name").val()=="请选择"){
		showInfo();		
		parent.alertWarn("请选择镜像名称！");
		return;
	}
	
	//镜像版本校验
	if($("#image_version").val()=="请选择"){
		showInfo();		
		parent.alertWarn("请选择镜像版本！");
		return;
	}
	
	//没有可用镜像版本校验
	if($("#image_version").text()==""){
		showInfo();
		parent.alertWarn("没有可用镜像版本！");
		return;
	}
	
	/*//日志路径校验
	var log_dir = $.trim($("#log_dir").val());
	if (!log_dir) {
		showInfo();		
		parent.alertWarn("请输入日志路径！");
		return;
	}*/
	
	/*if(log_dir.indexOf("/")!=0 && image_type == 2){
		showInfo();		
		parent.alertWarn("日志路径不合法,必须为绝对路径(例如 /xx/xx)");
		return;
	}*/
	
	//内存均值和最大值校验
	if ($("#mem").val() == "") {
		showInfo();		
		parent.alertWarn("内存均值不能为空！");
		return;
	}else if(!($("#mem").val()<=20480 && $("#mem").val()>=4) || $("#mem").val().match(/^[1-9]\d*$/)==null){
		showInfo();		
		parent.alertWarn("内存均值在4M到20G之间，并且只能填写整数！");
		return;
	}
	if ($("#maxMem").val() == "") {
		showInfo();		
		parent.alertWarn("内存最大值不能为空！");
		return;
	}else if(!($("#maxMem").val()<=20480 && $("#maxMem").val()>=4) || $("#maxMem").val().match(/^[1-9]\d*$/)==null){
		showInfo();		
		parent.alertWarn("内存最大值在4M到20G之间，并且只能填写整数！");
		return;
	}
	if(parseFloat($("#mem").val()) > parseFloat($("#maxMem").val())){		
		showInfo();
		parent.alertWarn("内存的均值不能大于最大值！");
		return;
	}
	
	//CPU均值和最大值校验
	if ($("#cpu").val() == "") {
		showInfo();		
		parent.alertWarn("CPU均值不能为空！");
		return;
	}else if(!($("#cpu").val()<=64 && $("#cpu").val()>=0.01)){
		showInfo();		
		parent.alertWarn("CPU均值在0.01到64之间，并且只能填写数字！");
		return;
	}
	if ($("#maxCpu").val() == "") {
		showInfo();		
		parent.alertWarn("CPU最大值不能为空！");
		return;
	}else if(!($("#maxCpu").val()<=64 && $("#maxCpu").val()>=0.01)){
		showInfo();		
		parent.alertWarn("CPU最大值在0.01到64之间，并且只能填写数字！");
		return;
	}
	if(parseFloat($("#cpu").val()) > parseFloat($("#maxCpu").val())){
		showInfo();
		parent.alertWarn("CPU的均值不能大于最大值！");
		return;
	}
	
	//外部服务校验
	var serviceRadio = $('input:radio[name=serviceRadio]:checked').val();
	if(serviceRadio=="是"){		
		if ($("#containerPort").val()) {
			var test = /^[1-9]\d*$/;
			var instance_num = $("#containerPort").val();
			if(instance_num.match(test)==null || parseInt(instance_num)>65535){
				showInfo();				
				parent.parent.alertWarn("应用端口范围是1-65535的整数！");
				return ;
			}
		}else{
			showInfo();			
			parent.parent.alertWarn("应用端口不能为空！");
			return ;
		}
	}	
	
	//实例数校验
	var instRadio = $('input:radio[name=instRadio]:checked').val();
	if(instRadio=="是"){
		if ($("#instance_num").val()) {
			var test = /^[1-9]\d*$/;
			var instance_num = $("#instance_num").val();
			if(instance_num.match(test)==null || parseInt(instance_num)>500){
				showInfo();				
				parent.parent.alertWarn("实例数只能是1-500的整数");
				return ;
			}
		}else{
			showInfo();			
			parent.parent.alertWarn("实例数不能为空！");
			return ;
		}
	}else{
		if ($("#inst_min").val()) {
			var test = /^[1-9]\d*$/;
			var inst_min = $("#inst_min").val();
			if(inst_min.match(test)==null || parseInt(inst_min)>500){
				showInfo();				
				parent.parent.alertWarn("最小实例数只能是1-500的整数");
				return ;
			}
		}else{
			showInfo();			
			parent.parent.alertWarn("最小实例数不能为空！");
			return ;
		}
		
		if ($("#inst_max").val()) {
			var test = /^[1-9]\d*$/;
			var inst_max = $("#inst_max").val();
			if(inst_max.match(test)==null || parseInt(inst_max)>500){
				showInfo();				
				parent.parent.alertWarn("最大实例数只能是1-500的整数");
				return ;
			}
		}else{
			showInfo();			
			parent.parent.alertWarn("最大实例数不能为空！");
			return ;
		}
		
		if(parseFloat($("#inst_min").val()) > parseFloat($("#inst_max").val())){
			showInfo();
			parent.alertWarn("最小实例数不能大于最大值！");
			return;
		}	
		
		if ($("#cpu_target").val()) {
			var test = /^[1-9]\d*$/;
			var inst_max = $("#cpu_target").val();
			if(inst_max.match(test)==null || parseInt(inst_max)>100){
				showInfo();				
				parent.parent.alertWarn("cpu目标值只能是1-100的整数");
				return ;
			}
		}else{
			showInfo();			
			parent.parent.alertWarn("cpu目标值不能为空！");
			return ;
		}
	}
	
	var arrayObj = new Array(); //创建一个数组用于校验绑定名称是否重复
	
	var arrayIPaaSName = new Array(); //创建一个数组用于校验服务名称是否重复
	//基础服务校验
	for(var i=0; i<ipaasCount; i++){		
		if($("#iPaaSType"+i).val()=="请选择" || $("#iPaaSType"+i).val() == null || $("#iPaaSType"+i).val() == ""){
			showInfo();
			parent.alertWarn("请选择第"+(i+1)+"个基础服务的服务类型！");
			return;
		}
		
		if($("#iPaaSName"+i).val()=="请选择" || $("#iPaaSName"+i).val()== null || $("#iPaaSName"+i).val()==""){
			showInfo();
			parent.alertWarn("请选择第"+(i+1)+"个基础服务的服务名称！");
			return;
		}
		
		if($("#ipaasBindStr"+i).val()=="" || $("#ipaasBindStr"+i).val()==null){
			showInfo();
			parent.alertWarn("第"+(i+1)+"个基础服务的绑定名称不能为空！");
			return;
		} else if (!/^[A-Za-z_][A-Za-z0-9_]*$/.test($("#ipaasBindStr"+i).val())){
			parent.parent.alertWarn("绑定名称只能包含字母、数字和下划线，并且只能以字母和下划线开头！");
			return;
		}
		if($("#ipaasBindStr"+i).val().length>15){
			parent.parent.alertWarn("绑定名称长度不能超过15个字符！");
			return;
		}
		arrayObj.push($("#ipaasBindStr"+i).val());
		arrayIPaaSName.push($("#iPaaSName"+i).val());
	}
	
	var iPSN = arrayIPaaSName.sort();
	for(var i=0; i<arrayIPaaSName.length;i++){
		if (iPSN[i]==iPSN[i+1]){
			parent.parent.alertWarn("服务名称不能重复！");
			return;
		}
	}
	
	var nary = arrayObj.sort();
	for(var i=0; i<arrayObj.length; i++){
		if (nary[i]==nary[i+1]){
			parent.parent.alertWarn("绑定名称不能重复！");
			return;
		}
	}
	
	return true;
}

/**
 * 显示应用服务环境变量页面
 */
function showEnvs() {
	//校验参数
	var result = validateParameter();	
	if(result == true){
		$("#appServiceEnvs").show();
		$("#appServiceInfo").hide();
		$("#appServiceConfigFile").hide();
		$("#li1").removeAttr('style');
		$("#li2").removeAttr('style');
		$("#li3").removeAttr('style');
		$("#li3").attr('style','width:123px; height:30px; background-color:#ccc;');
	}else{
		return;
	}
}

/**
 * 显示应用服务配置文件页面
 */
function showConfigFile(){
	//校验参数
	var result = validateParameter();	
	if(result == true){
		$("#appServiceConfigFile").show();
		$("#appServiceEnvs").hide();
		$("#appServiceInfo").hide();
		$("#li1").removeAttr('style');
		$("#li2").removeAttr('style');
		$("#li3").removeAttr('style');
		$("#li2").attr('style','width:123px; height:30px; background-color:#ccc;');
	}else{
		return;
	}
//	var configFile_path = $("#configFile_path").val();
//	//如果配置文件路径为空，上传按钮失效、配置文件内容编辑框只读
//	if(configFile_path == "" || configFile_path == null){
//		$("#uploadBtnId").attr('disabled','disabled');
//		$("#configFileInfo").val('');
//		$("#configFileInfo").attr('readonly','readonly');
//	}else{
//		$("#uploadBtnId").removeAttr('disabled');
//		$("#configFileInfo").removeAttr('readonly');
//	}
}

/**
 * 创建服务弹出框初始加载函数
 */
$(function(){
	$.ajax({
		cache:false,
		type : "get",
		dataType : "json",
		url : '/paas/appservice/queryClusterList.action',
		async: false,
		success : function(result) {			
			if(result['result'] == null){
				parent.alertWarn("没有可用的集群！");
				return;
			}
			$.each(result['result'], function(i, val) {
				$("#cluster_name").append("<option value='" + val['id'] + "' type='"+val['type']+"'>" + val['name'] + "</option>");
			});
		}
	});
	clusterChange();//根据集群类型展示镜像类型
	var image_type = getUrlParam('image_type');
	if(image_type == 1){
		$("#image_type")[0][1].selected = true;//选中公共镜像
		imageTypeChange();
		imageNameChange();
	}else if(image_type == 2){
		$("#image_type")[0][0].selected = true;//选中私有镜像
		imageTypeChange();//根据镜像类型获取镜像名称列表，默认镜像类型为：私有镜像
		imageNameChange();
	}else{
		imageTypeChange();//根据镜像类型获取镜像名称列表，默认镜像类型为：私有镜像
	}
	undisplayServiceInfo();//默认不是服务
	undisplayServiceHostInfo();//默认不指定IP
	displayFixInstInfo();//默认显示固定实例数配置
	//$("#log_dir").attr('readonly','readonly');//公共镜像的日志路径必须是只读
	$("#image_version").change(function(){
		if($("#image_type").val() == 2){
			imageVserionChange($("#image_version").val());
		}
	});
	$("#description").val("");
});

/**
 * 集群名称选择后，联动选中相应的镜像类型
 */
function clusterChange(){
	 var obj=document.getElementById("cluster_name");    
     var index=obj.selectedIndex; 
     if(index != -1){
    	 var type=obj.options[index].getAttribute("type");      
         
         if(type=="1"){//ipaas   	 
        	 $("#image_type").val("1");
         }else if(type=="2"){//appcluster
        	 $("#image_type").val("2");
         }
         imageTypeChange();
         imageNameChange();
     }
}

function getUrlParam(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	try {//火狐浏览器取的对象不同  兼容判断
		parent[0].Object;
	} catch (e) {
		return null;
	}
	//if(location.search!=""){
	var r = parent[0].location.search.substr(1).match(reg);
	if (r!= null) 
		return unescape(r[2]);
	//}
	return null;
}

/**
 * 镜像类型选择后，联动查出镜像名称
 */
var image_type="";
function imageTypeChange(){
	var imageId = getUrlParam('id');
	image_type = $("#image_type").val();
	var actionUrl="";
	if(image_type==1){//公有镜像
		actionUrl = '/paas/publicImage/queryPublicImageList.action';
		$("#log_dir").attr('readonly','readonly');//公共镜像的日志路径必须是只读
	}else if(image_type==2){//私有镜像
		actionUrl = '/paas/privateImage/queryPrivateImageList.action';
		$("#log_dir").attr('readonly','readonly');//公共镜像的日志路径必须是只读
	}
	
	document.getElementById("image_name").innerHTML = "";//清空镜像名称下拉框
	$("#image_name").append("<option value = '请选择'>请选择</option>");
	
	document.getElementById("image_version").innerHTML = "";//清空镜像版本下拉框
	$("#image_version").append("<option value = '请选择'>请选择</option>");
	$("#log_dir").val("");
	$("#configFile_path").val("");
	
	$.ajax({
		cache:false,
		type : "get",
		dataType : "json",
		data : {id:imageId,oper:'serviceOper'},
		url : actionUrl,
		async :false,
		success : function(result) {	
			if(result['result'] == null || result['result'] == ""){				
				return;
			}
			$.each(result['result'], function(i, val) {
				if(val['checked']){
					$("#image_name").append("<option selected value='" + val['id'] + "'>" + val['name'] + "</option>");
				}else{
					$("#image_name").append("<option value='" + val['id'] + "'>" + val['name'] + "</option>");
				}
				
//				if(image_type==1){//公有镜像
//					$("#image_version").append("<option value='" + val['id'] + "'>" + val['version'] + "</option>");
//				}
			});
		}
	});
	imageNameChange();
}

var imageVersionNames = new Array();
var imageVersionLogs = new Array();
var imageVersionConfigFilePath = new Array();
/**
 * 镜像名称选择后，联动查出镜像版本
 */
function imageNameChange(){
	var versionId = getUrlParam('versionId');
	document.getElementById("image_version").innerHTML = "";//清空镜像版本下拉框	
	var image_id = $("#image_name").val();	
	
	if(image_id=="请选择"){
		$("#image_version").append("<option value = '请选择'>请选择</option>");
		$("#log_dir").val("");
		$("#configFile_path").val("");
		return;
	}
	
	if(image_type==1){//公有镜像
			
		$.ajax({
			cache:false,
			type : "get",
			dataType : "json",
			async : false,
			data: {
				id : image_id.toString()
			},
			url : '/paas/publicImage/queryPublicImageVersionById.action',
			success : function(result) {				
					var resultMessage = JSON.parse(result['resultMessage']);
					if(resultMessage.code == 0){
						$("#image_version").append("<option value='" + resultMessage.imageVersionId + "'>" + resultMessage.imageVersion + "</option>"); 
						
						if(resultMessage.imageLogDir == ""){
							$("#log_dir").val("");						
							$("#log_dir").attr('readonly','readonly');//公共镜像的日志路径必须是只读
						}else{
							$("#log_dir").val(resultMessage.imageLogDir);
							$("#log_dir").attr('readonly','readonly');//公共镜像的日志路径必须是只读
						}
						$("#configFile_path").val(resultMessage.imageConfigFilePath);//配置文件完整路径
					}
					
			}
		});	
		
	}else if(image_type==2){//私有镜像
		$("#log_dir").val("");//清空日志路径
		$("#configFile_path").val("");
		//$("#log_dir").removeAttr('readonly');
		
		$.ajax({
			cache:false,
			type : "get",
			dataType : "json",
			data: {
				id : image_id.toString(),
				privateImageVersionId : versionId,
				queryType : 'appServiceQuery'
			},
			url : '/paas/privateImage/queryPrivateImageVersionList.action',
			success : function(result) {				
					if(result['result'] == null || result['result'] == ""){				
						return;
					}
					$("#image_version").empty(); 
					/*if(result['result'].length > 1){
						$("#image_version").append("<option value = '请选择'>请选择</option>");
					}*/
					$.each(result['result'], function(i, val) {
						imageVersionNames[i] = val['id'];
						imageVersionLogs[i] = val['logdir'];
						imageVersionConfigFilePath[i] = val['configFilePath'];
						if(val['checked']){
							$("#image_version").append("<option selected value='" + val['id'] + "'>" + val['version'] + "</option>");
						}else{
							$("#image_version").append("<option value='" + val['id'] + "'>" + val['version'] + "</option>");
						}
					});
					imageVserionChange($("#image_version").val());
					
			}
		});	
	}
}

function imageVserionChange(versionId){
	for (var i = 0; i < imageVersionNames.length; i++) {
		if(imageVersionNames[i] == versionId){
			if(imageVersionLogs[i] == ""){
				$("#log_dir").val("");						
			}else{
				$("#log_dir").val(imageVersionLogs[i]);
			}
			$("#configFile_path").val(imageVersionConfigFilePath[i]);//配置文件路径
			break;
		}
	}
}

//当集群值改变，指定IP选中是的时候，显示IP地址
function changeCluster(){
	if($('input:radio[name="specifyIpRadio"]:checked').val() == "是"){
		displaySerivceHostInfo();
	}
}

/**
 * 【单选按钮】禁用或恢复
 * @param radioName
 * @param trueOrFalse
 */
function disableOrRestore(radioName, trueOrFalse){
	var radioElements = document.getElementsByName(radioName);
	for (var i = 0; i < radioElements.length; i++) {
		radioElements[i].disabled = trueOrFalse;
	}
}

//点击【是否服务】的【是】按钮
function displayServiceInfo(){
	//显示服务输入框
	$("#appServicePortTr").show();	
	$("#serviceFlag").val(2);
//	//将【是否指定IP】禁用
//	var specifyIpRadio = document.getElementsByName("specifyIpRadio");
//	for (var i = 0; i < specifyIpRadio.length; i++) {
//		specifyIpRadio[i].disabled = true;
//	}
//	disableOrRestore("specifyIpRadio", true);
}

//点击【是否服务】的【否】按钮
function undisplayServiceInfo(){
	//隐藏服务输入框
	$("#appServicePortTr").hide();	
	$("#serviceFlag").val(1);
	//将【是否指定IP】恢复可用
//	var specifyIpRadio = document.getElementsByName("specifyIpRadio");
//	for (var i = 0; i < specifyIpRadio.length; i++) {
//		specifyIpRadio[i].disabled = false;
//	}
//	disableOrRestore("specifyIpRadio", false);
}

//点击【是否指定IP】的【是】按钮显示IP下拉框
function displaySerivceHostInfo(){
	$("#hostIps").empty();
	var instance = $("#instance_num");
	instance.val(1);
	instance.attr('readonly','readonly');
	$("#RadioGroup1_fixed").click();
	//将【是否服务】禁用
//	var serviceRadio = document.getElementsByName("serviceRadio");
//	for (var i = 0; i < serviceRadio.length; i++) {
//		serviceRadio[i].disabled = true;
//	}
//	disableOrRestore("serviceRadio", true);
	//将【实例数】禁用
//	var instRadio = document.getElementsByName("instRadio");
//	for (var i = 0; i < instRadio.length; i++) {
//		instRadio[i].disabled = true;
//	}
	disableOrRestore("instRadio", true);
//	var cluserId = $("#cluster_id").val();
	var clusterId = $("select option:checked").attr("value"); //获取select下被option选中的value值
	$.ajax({
		cache:false,
		type : "get",
		async : false,
		dataType : "json",
		data: {
			cluserId : clusterId
		},
		url : '/paas/appservice/queryCluserHostIp.action',
		success : function(result) {	
			$.each(result['result'], function(i, val) {
				$("#hostIps").append("<option value='" + val + "'>" + val + "</option>");
			});
			
		}
	});	
	$("#appServiceHostTr").show();	
	$("#hostIpFlag").val(1);
	isCheckIp();
	
}

function isCheckIp(){
	var ip = $("#hostIps").val();
	$.ajax({
		cache:false,
		type : "get",
		dataType : "json",
		data: {
			ip : ip
		},
		url : '/paas/appservice/queryIsCheckIP.action',
		success : function(result) {	
			if(result['data'] == "false" || result['data'] == false){
				parent.parent.alertWarn("此主机上已经部署过指定主机的服务，可能存在端口冲突!");
				return;
			}
		}
	});	
}

//点击【是否指定IP】的【否】按钮
function undisplayServiceHostInfo(){
	var instance = $("#instance_num");
	instance.attr('readonly',false);
	//将【是否服务】恢复可用
//	var serviceRadio = document.getElementsByName("serviceRadio");
//	for (var i = 0; i < serviceRadio.length; i++) {
//		serviceRadio[i].disabled = false;
//	}
//	disableOrRestore("serviceRadio", false);
	//将【实例数】恢复可用
//	var instRadio = document.getElementsByName("instRadio");
//	for (var i = 0; i < instRadio.length; i++) {
//		instRadio[i].disabled = false;
//	}
	disableOrRestore("instRadio", false);
	document.getElementById("hostIps").options.length = 0;//清空数据
	$("#appServiceHostTr").hide();	
	$("#hostIpFlag").val(0);
}

//点击实例数的“是”按钮显示固定实例数输入框
function displayFixInstInfo(){	
	$("#instScaleTr").attr("disabled",true);
	$("#instScaleTr").hide();	
	$("#instNumTr").show();	
	$("#inst_scale_type").val(1);
}

//点击实例数的“否”按钮显示动态伸缩实例数输入框
function displayDynamicInstInfo(){
	$("#instNumTr").attr("disabled",true);
	$("#instNumTr").hide();	
	$("#instScaleTr").show();	
	$("#inst_scale_type").val(2);
}

/**
 * 校验：绑定名称
 * @param ipaasBindStr
 */
function checkIpaasBindStr(ipaasBindStr){
	var ipaasBindStrVal = $.trim($("#"+ipaasBindStr).val());
	if(ipaasBindStrVal == null || ipaasBindStrVal == ""){
		$("#"+ipaasBindStr).val(ipaasBindStrVal);
		parent.parent.alertWarn("绑定名称不能为空！");
		return;
	}
	if(!/^[A-Za-z_][A-Za-z0-9_]*$/.test(ipaasBindStrVal)){
		parent.parent.alertWarn("绑定名称只能包含字母、数字和下划线，并且只能以字母和下划线开头！");
		return;
	}
	if(ipaasBindStrVal.length>15){
		parent.parent.alertWarn("绑定名称长度不能超过15个字符！");
		return;
	}
}

function isExcess(optionType){
	var instNumber = 0;
	var instRadio = $('input:radio[name=instRadio]:checked').val();
	if(instRadio=="是"){
		instNumber = $("#instance_num").val();
	}else{
		instNumber = $("#inst_min").val();
	}
	var cpuNumber = $("#cpu").val();
	
	var memNumber = $("#mem").val();
	
	if(!(cpuNumber<=64 && cpuNumber>=0.01)){
		parent.parent.alertWarn("CPU均值在0.01到64之间，并且只能填写数字！");
		return false;
	}
	
	if(!(memNumber<=20480 && memNumber>=4) || memNumber.match(/^[1-9]\d*$/)==null){
		parent.parent.alertWarn("内存均值在4M到20G之间，并且只能填写整数！");
		return false;
	}
	var flag = false;
	$.ajax({
		cache:false,
		type : "get",
		dataType : "json",
		async : false,
		data: {
			optionTypes : optionType,
			instNumber : instNumber,
			cpuNumber : cpuNumber,
			memNumber : memNumber
		},
		url : '/paas/appservice/queryIsExcess.action',
		success : function(result) {	
			/*if(result['data'] == "false" || result['data'] == false){
				parent.parent.alertWarn("此主机上已经部署过指定主机的服务，可能存在端口冲突!");
				return;
			}*/
			if(result['data'] != "\"0\""){
				var data= JSON.parse(result['data']);
				parent.parent.alertWarn(data);
				flag = false;
			}else{
				flag = true;
			}
		}
	});	
	return flag;
}

//添加基础服务
function addIpaasService(){
//	$(".add-service-main").show();
//	$(".add-service-key").show();
//	$("#addServiceDiv").show();	
	
	$("#addServiceDiv").append('<div id="ipaasDiv'+ipaasCount +'"> <div class="add-service-main"><div class="add-service-content" ><table width="560" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:15px;" >                         <tr>                     <td width="19">&nbsp;</td> '             
			+'<td width="190" height="30" align="left"><strong style="color:#cc3c3b; font-size:12px; margin-right:5px;">*</strong>服务类型：  <select style="width:90px;" id="iPaaSType'+ipaasCount+'" name="appService.ipaas['+ipaasCount+'].type"  onchange="ipaasTypeChange('+ipaasCount+');"><option selected="selected">请选择</option><option value="3">activemq</option><option value="2">redis</option><option value="1">zookeeper</option></select></td>  '
			+'<td width="210" height="30"><div style="float:left;"><strong style="color:#cc3c3b; font-size:12px; margin-right:5px;">*</strong>服务名称： </div><select id="iPaaSName'+ipaasCount+'" name="appService.ipaas['+ipaasCount+'].name" onchange="ipaasNameChange('+ipaasCount+');" style="width:129px; float:left;"> </select></td>                     <td width="93">&nbsp;</td>                   </tr>                   <tr>                     <td></td>  '
			+'<td align="left"><strong style="color:#cc3c3b; font-size:12px; margin-right:5px;">*</strong>绑定名称：  <input type="text" id="ipaasBindStr'+ipaasCount+'" onchange="changeBindStr('+ipaasCount+')" onblur="checkIpaasBindStr('+"'ipaasBindStr"+ipaasCount+"'"+');" name="appService.ipaas['+ipaasCount+'].bindStr" style="width:90px;" />                       </td>                       <td width="174" align="right">&nbsp;</td>        '
			+'<td width="93"><input name="" type="button" value="删除" onclick="deleteIpaasService('+ipaasCount+');" style="width:60px; height:26px; background:#666666; border:none; color:#fff;border-radius:5px; cursor:pointer;" />                       </td>                   </tr>                          </table>               </div>        '
			+'<div class="add-service-key" >  <table width="500" border="0" cellspacing="0" cellpadding="0"><tr><td width="219" height="30" align="center" bgcolor="#e2e2e2" style="background:#e2e2e2; border-top:1px solid #dadada;font-weight: bold;" >key</td>                  <td width="281" height="30" align="center" bgcolor="#e2e2e2"  style="background:#e2e2e2; border-top:1px solid #dadada;font-weight: bold;">Value</td></tr></table>'
			+'<table id="ipaasEnvsTab'+ipaasCount+'" width="500" border="0" cellspacing="0" cellpadding="0"></table></div></div></div>');

	ipaasCount++;
	
}

function changeBindStr(ipaasCount){
	ipaasNameChange(ipaasCount);
}

//删除一个基础服务
function deleteIpaasService(index){
	var divElements = $('[id=ipaasDiv'+index+']');	
	for(var i=0; i<divElements.length; i++){			
		if(divElements[i].getAttribute("disabled") == null){			
			divElements[i].setAttribute("disabled","disabled");
			divElements[i].innerHTML = "";	//清空这个基础服务的div内容
			ipaasCount--;				
		}
	}
}

/**
 * 基础服务类型选择后，联动查出基础服务名称
 */
function ipaasTypeChange(index){
	var ipaas_type = $("#iPaaSType"+index).val();	
	
	document.getElementById("iPaaSName"+index).innerHTML = "";//清空基础服务名称下拉框	
	document.getElementById("ipaasEnvsTab"+index).innerHTML = "";//清空环境变量table
	
	if(ipaas_type != "请选择"){
		queryIpaasNameBytype(ipaas_type,index);//填充ipaas服务名称下拉框
	}
	
	//根据基础服务类型给绑定名称赋初值
	if(ipaas_type==1){
		$("#ipaasBindStr"+index).val("ZK");
	}else if(ipaas_type==2){
		$("#ipaasBindStr"+index).val("REDIS");
	}else if(ipaas_type==3){
		$("#ipaasBindStr"+index).val("MQ");
	}else{
		$("#ipaasBindStr"+index).val("");
	}
}

/**
 * 基础服务名称选择后，联动查出发布的环境变量
 */
function ipaasNameChange(index){
	var ipaas_id = $("#iPaaSName"+index).val();	
	var ipaasBindStr = $("#ipaasBindStr"+index).val();
	
	document.getElementById("ipaasEnvsTab"+index).innerHTML = "";//清空环境变量table	
	if(ipaas_id == '请选择'){
		return;
	}
	//根据基础服务id查询发布的环境变量，并和绑定名称结合展示key
	queryIpaasEnvsById(ipaas_id,index,ipaasBindStr);	
}

//根据基础服务id查询发布的环境变量
function queryIpaasEnvsById(id,index,ipaasBindStr){
	$.ajax({
		cache:false,
		type : "get",
		dataType : "json",
		data: {
			ipaasServiceId : id.toString()
		},
		url : '/paas/ipaasservice/queryEnvsByIpaasServiceId.action',
		success : function(result) {	
			if(result['resultMessage'] == null || result['resultMessage'] == ""){				
				return;
			}
					   
		    //alert(result['resultMessage'])
		    var envsConfJson = JSON.parse(result['resultMessage']);
		    var obj=document.getElementById("ipaasEnvsTab"+index); 
		    //var tr= obj.rows["ipaasEnvsTrHead"+index];
		    //alert(obj.rows["ipaasEnvsTrHead0"])
		    var rowIndex = 0;
		    for(var key in envsConfJson){ 
		  	  //rowIndex = rowIndex+1;    	
		  	  addIpaasEnvsTab(obj,key,envsConfJson[key],rowIndex,ipaasBindStr);       
		    }    
			
		}
	});	
}

//根据基础服务类型查询基础服务列表
function queryIpaasNameBytype(type,index){
	$.ajax({
		cache:false,
		type : "get",
		dataType : "json",
		data: {
			serviceType : type.toString()
		},
		url : '/paas/ipaasservice/queryIpaasServiceList.action',
		success : function(result) {	
			if(result['result'] == null || result['result'] == ""){		
				parent.alertWarn("没有对应的基础服务，请先创建！");
				return;
			}
			if(result['result'].length > 1){
				$("#iPaaSName"+index).append("<option value = '请选择'>请选择</option>");
			}
			
			$.each(result['result'], function(i, val) {				
				$("#iPaaSName"+index).append("<option value='" + val['id'] + "'>" + val['name'] + "</option>");
			});
			ipaasNameChange(index);//再自动联动出环境变量
		}
	});	
}

//动态生成ipaas环境变量table
var ipaasEnvsRowIndex = 1;
function addIpaasEnvsTab(obj,key,value,rowIndex,ipaasBindStr){
	//插入行
	var tr =obj.insertRow(rowIndex); 
	var trId="ipaasEnvsTabTr"+ipaasEnvsRowIndex; 
	tr.setAttribute("id",trId); 	
	
	//插入列
	var td0 = tr.insertCell(0); 
	td0.setAttribute("width","219"); 
	td0.setAttribute("height","30"); 
	td0.setAttribute("align","center"); 
	td0.setAttribute("valign","middle"); 	
	td0.innerHTML = ipaasBindStr +"_"+ key; 
	
	var td1 = tr.insertCell(1); 
	td1.setAttribute("width","281"); 	
	td1.setAttribute("height","30"); 
	td1.setAttribute("align","center"); 
	td1.setAttribute("valign","middle");
	td1.style="word-wrap:break-word;word-break:break-all";
	td1.innerHTML = value;	
	ipaasEnvsRowIndex ++;
}

//////////////////////环境变量/////////////////////////////////////////////////////

/**
 * 导入应用服务环境变量配置文件
 */
var appServiceName= "";//应用服务名称
function importEnvsFile() {
	   appServiceName = $("#name").val();
	   
	   var file = document.getElementById('envsConfFile').files[0]; 
	   if(file==null){
		   parent.alertWarn("请先选择文件！");
		   return;
	   }
	   
	   var fileName = file.name;
	   if(env_file_name==file.name){
			parent.alertWarn("文件已上传,不能重复上传");
			return ;
		}
	   if(file.size == 0){
			parent.alertWarn("上传配置文件为空！");
			return;
		}
	   if (file) {
			 var extend = fileName.substring(fileName.lastIndexOf(".")+1); 
		     if(extend != 'properties'){
			   	  parent.alertWarn("请上传.properties类型的文件");
			   	  return;
		     }
		    
		    env_file_name = fileName;
			var fd = new FormData();
//			env_file_random = new Date().getTime() + "_"+document.getElementById('envsConfFile').files[0].name;//文件名中包含当前时间毫秒数
			//env_file_random = fileName;//文件名中包含当前时间毫秒数
		    fd.append("envsConfFile", file);
		  //fd.append("envsConfFileName", fileName);	
		    fd.append("envsConfFileName", appServiceName);			    
		    var xhr = new XMLHttpRequest();
		    xhr.upload.addEventListener("progress", null, false);
		    xhr.addEventListener("load", importEnvsFileComplete, false);
		    xhr.addEventListener("error", uploadFailed, false);
		    xhr.addEventListener("abort", uploadCanceled, false);
		    xhr.open("POST", "/paas/appservice/importEnvsFile.action");
		    xhr.send(fd);
	   }else{
		   parent.alertWarn("请选择文件");
	   }
}
//导入应用服务环境变量配置文件成功后动态生成配置表
function importEnvsFileComplete(evt) {	
  var result = JSON.parse(evt.target.responseText);
  var envsConfJson = JSON.parse(result.resultMessage);
  var fileName = envsConfJson.fileName;
  var envsConfArray = envsConfJson.conf;
  $("#envsConfFileName").val(fileName);
  
  var obj=document.getElementById("envsConfTab"); 
//  var tr= obj.rows["envsTabTrHead"]; 
//  var rowIndex = tr.rowIndex;
  var rowIndex=0;
  for(var key in envsConfArray){  
  	//rowIndex = rowIndex+1;    
	  var tabRows = obj.rows.length;	
  	  rowIndex = tabRows-1;//减去表头那一行，下标从0开始    	
  	  addEnvsConfTab(key,envsConfArray[key],rowIndex);       
  }      
}

function uploadFailed(evt) {
	$("#loadImg").hide();    
    parent.alertWarn("There was an error attempting to upload the file.");
}

function uploadCanceled(evt) {
	$("#loadImg").hide();  
    parent.alertWarn("The upload has been canceled by the user or the browser dropped the connection.");
}

/**
 * table追加配置tr
 * @param obj
 * @param key
 * @param value
 * @param rowIndex
 */
/*
var row_index = 1;
function addEnvsConfTab1(obj,key,value,rowIndex){
	//插入行
	var tr =obj.insertRow(rowIndex); 
	var trId="envsTabTr"+row_index; 
	tr.setAttribute("id",trId); 
	tr.setAttribute("class","_tr");
	
	//插入列
	var td0 = tr.insertCell(0); 
	td0.setAttribute("width","246"); 
	td0.setAttribute("height","30"); 
	td0.setAttribute("align","center"); 
	td0.setAttribute("valign","middle"); 	
	td0.innerHTML = "<input type='text'  name='appService.envConfig["+rowIndex+"].configKey'  id='envsConfKey"+row_index+"' value='"+key+"'/> "; 
		
	var td1 = tr.insertCell(1); 
	td1.setAttribute("width","238"); 	
	td1.setAttribute("height","30"); 
	td1.setAttribute("align","center"); 
	td1.setAttribute("valign","middle"); 
	td1.innerHTML = "<input type='text'  name='appService.envConfig["+rowIndex+"].configValue'   id='envsConfValue' value='"+value+"'/> "; 
	
	var td2 = tr.insertCell(2); 
	td2.setAttribute("width","71"); 	
	td2.setAttribute("height","30"); 	
	td2.setAttribute("align","center"); 
	td2.setAttribute("valign","middle"); 
	td2.innerHTML = "<center><a href='JavaScript:deleteEnvsConf("+row_index+");' ><img src='/paas/imgs/delete.png' width='22' height='22' border='none' />删除</a></center>";
	
	row_index ++;
}
*/
//添加环境变量
var rowIndex=0;
function addEnvsConfTab(key,value,currentRowIndex){
	rowIndex = currentRowIndex;
	
	$("#envsConfTab").append('<tr id="envsTabTr'+rowIndex+'" class="_tr"><td width="246" height="30" align="center" valign="middle">'
			+'<input type="text"  name="appService.envConfig['+rowIndex+'].configKey"  id="envsConfKey'+rowIndex+'" value="'+key+'"/></td>'
			+'<td width="238" height="30" align="center" valign="middle">'
			+'<input type="text"  name="appService.envConfig['+rowIndex+'].configValue"  id="envsConfValue'+rowIndex+'"  value="'+value+'"/></td>'
			+'<td width="71" height="30" align="center" valign="middle">'
			+'<center><a href="JavaScript:deleteEnvsConf('+rowIndex+');" title="删除"><img src="/paas/imgs/delete.png" width="22" height="22" border="none" /></a></center></td></tr>');

	rowIndex++;	
}

/**
 * 删除一个tr
 * @param rowIndex
 */
function deleteEnvsConf(rowIndex){ 
	$("#envsTabTr"+rowIndex).remove();
}

/**
 * table追加配置信息
 */
function addEnvs(){
	var tabObj=document.getElementById("envsConfTab");
	var tabRows = tabObj.rows.length;
	var rowIndex = tabRows-1;//减去表头那一行，下标从0开始
	//ddEnvsConfTab(tabObj,"","",tabRows);
	addEnvsConfTab("","",rowIndex);
}


/**
 * 清除所有的配置tr
 */
function deleteEnvs(){
	env_file_name="";
	$("._tr").remove();
}

/**
 * 导出环境变量配置文件
 */
function exportEnvsFile(){	
	if(env_file_name == ""){
		parent.alertWarn("没有导入过文件，无法导出！");
		return;
	}
	
	var envsConfFileName = $("#appServiceName").val();
	var a = document.getElementById("exportEnvsFile"); 
	a.href = "/paas/appservice/exportEnvsFile.action?exportEnvsFileName="+envsConfFileName;
	a.click("return false");
}

//保存应用服务
function saveAppService(){
	//校验参数
	var result = validateParameter();	
	if(result != true){
		return false;
	}
	//将当前应用名作为导出文件名，导出文件名始终以envsConfFileName中值为准
	$("#envsConfFileName").val($("#name").val());
	
	//校验是否外部服务
	var extServiceType = $("input[type='checkbox']").is(':checked');	
	if(extServiceType){//type=1表示内部服务，type=2表示外部服务
		$("#extServiceType").val("2");
		$("#protocolType").val($("#protocolValue").val());
	}else{
		$("#extServiceType").val("1");
		$("#protocolType").val(0);
	}
	
//	var serviceFlag = $("#serviceFlag").val();
//	if(serviceFlag == "1"){//不是服务		
//		$("#containerPort").val("");
//		document.getElementById("isExtServiceType").checked = false;
//	}	
	
	//校验环境的key和value
	var obj=document.getElementById("envsConfTab"); 
	var tabRows = obj.children[0].childNodes.length;
	var envsCount = tabRows;//减去表头那一行，下标从0开始    	
	
	var config_error = false;
	var _keys = new Array();
	for(var i=2; i<envsCount; i++){
	//$('input:text[id=envsConfKey]').each(function(){
		var key = $.trim($("#"+obj.children[0].childNodes[i].childNodes[0].firstChild.id).val());
		if(key == null || key == ""){
			parent.parent.alertWarn("Key不能为空");
			showEnvs();
			$(this).focus();
			config_error = true;
			return false;
		}
		if(!/^[A-Za-z_][A-Za-z0-9_]*$/.test(key)){
			parent.parent.alertWarn("Key命名只能以字母或下划线开头，名称中只能包含字母、数字和下划线");
			showEnvs();
			$(this).focus();
			config_error = true;
			return false;
		}else{
			_keys.push(key);
		}
	//});
	}
	if(config_error)
		return;
	if(isRepeat(_keys)){
		parent.parent.alertWarn("Key不能重复");
		return;
	}
	
	for(var i=2; i<envsCount; i++){
	//$('input:text[id=envsConfValue]').each(function(){
		var value = $.trim($("#"+obj.children[0].childNodes[i].childNodes[1].firstChild.id).val());
		if(value == null || value == ""){
			parent.parent.alertWarn("Value不能为空");
			showEnvs();
			$(this).focus();
			config_error = true;
			return false;
		}
		//var value = $.trim($(this).val());
		var reg = /^[^\'\"<]*$/;
		if(reg.test(value)==false){
			parent.parent.alertWarn("Value不能包含单引号,双引号或小于号");
			showEnvs();
			$(this).focus();  
			config_error = true;
			return false;
		}
	//});
	}
	if(config_error)
		return;
	if(!isExcess(0)){
		return;
	}
	showLoad();
	//保存应用服务
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/paas/appservice/createAppService.action",
		data : $('#createAppServcieForm').serialize(),
		success : function(result) {
			closeLoad();
			if (result['resultCode'] == 'success') {
				parent.alertConfirm("创建应用服务成功！是否立即启动？",function(){
					var name = $.trim($("#name").val());
					var appServiceId = result['resultMessage'];	
				    //启动服务				
				    startAppServiceByWebsocket(appServiceId,name);
				},function(){				
					$("#mainIframe",window.parent.document)[0].contentWindow.document.getElementById("refresh").click("return false");
					parent.close();
				});				
				
			} else {
				parent.alertError(result['resultCode'], result['resultMessage']);	
				//parent.close();
			}
			$("#mainIframe",window.parent.document)[0].contentWindow.document.getElementById("refresh").click("return false");
		}
	});	
}

function startAppServiceByWebsocket(id,name){
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				"appServiceId" : id
			},
			url : "/paas/appservice/startAppService.action",
			success : function(result){
				parent.alertSuccess(function(){
					var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
					window.parent[0].document.location = "/paas/jsp/appservice/appServiceDetail.jsp?appServiceId="+id+'&appServiceName='+name;
					setTimeout(function(){
							if(userAgent.indexOf("Firefox") > -1 && userAgent.indexOf("61.0") > -1){
								parent.close()
							}else{
								$('.service-menu-main ul li',window.parent[0].document).css({"background":"none","color":"#000"});//清空所有的背景
								$('.service-menu-main ul li',window.parent[0].document).find('img')[0].src = '/paas/imgs/Operation.png';
								$('.service-menu-main ul li',window.parent[0].document).find('img')[1].src = '/paas/imgs/Example-hover.png';
								$('.service-menu-main ul li',window.parent[0].document)[1].style.cssText = 'background:url(/paas/imgs/menu-hover.jpg);color:white;';
								$('#b',window.parent[0].document)[0].click();
								parent.close();
							}
					}, 500);
					
				});
			}
		});
}

/**
 * 判断数组是否重复.
 * @param arr
 * @returns {Boolean}
 */
function isRepeat(arr){
	var hash = {};
	for(var i in arr) {
		if(hash[arr[i]])
		return true;
		hash[arr[i]] = true;
	}
	return false;
}
//从主页展示进度条
function showLoad(){	
	$('#loadImg').css({"width":"100%","height":"100%"});
	$('#loadImg').show();
}
function closeLoad(){
	$('#loadImg').hide();
}
function protocolChange(){
	if($("#isExtServiceType")[0].checked){
		$("#protocol").show();
		$("#protocolSelect").show();
	}else{
		$("#protocol").hide();
		$("#protocolSelect").hide();
	}
}

var fileTypeError = false;
var fileTypeErrorInfo = '支持的文件类型: conf,cfg,txt,properties。请重新选择文件';
function fileSelected() {
	var file = document.getElementById('configFile').files[0]; 
	if (file) {
		 var index = file.name.lastIndexOf(".");
		 if(index != -1){
			 var extend = file.name.substring(index+1); 
		     if(extend != 'conf' && extend != 'cfg' 
		    	 && extend != 'txt' && extend != 'properties'){
			   	  parent.alertWarn(fileTypeErrorInfo);
			   	  fileTypeError = true; 
			   	  return;
		     }
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
	if(file.size<1){
		parent.parent.alertWarn("请不要上传空文件!");
		return;
	}
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
	    xhr.open("POST", "/paas/appservice/uploadConfigFile.action");
	    xhr.send(fd);
   }else{
	   parent.parent.alertWarn("请选择文件!");
	   return;
   }
	if(file.size == 0){
		parent.parent.alertWarn("上传配置文件为空！");
		return;
	}
}
//导入配置文件成功后 把文件内容展示到页面
function uploadComplete(evt) {
	var result = JSON.parse(evt.target.responseText);
	if (result['resultCode'] == 'success') {
		$("#configFileInfo").val(result['resultMessage']);
		parent.parent.alertSuccess();
	}else{
		parent.parent.alertError(result['resultCode'], result['resultMessage']);
	}
}