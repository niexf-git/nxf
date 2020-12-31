var flag = true;
var env_file_name = "";//用来判断环境变量文件是否已经导入过一次
//选择导航菜单效果切换
function grayOperation(){
	var main = $(".dialog-main");
	var configure  = $(".dialog-configure");//环境变量对象
	var configFile = $("#configFilePlane");//配置文件对象
	configure.hide();
	configFile.hide();
	
	//给导航选项绑定单击事件
	var li = $(".title-mc");
	if (li.length>0) {
		li = li[0].children[0].children;
		$.each(li,function(i,v){
			var a = v.children[0];
			a.onclick = function(){
				if(a.text == "创建灰度版本" || a.text == "修改灰度版本"){
					if(propertiesValidate("环境变量")){//校验
						main.show();
						configure.hide();
						configFile.hide();
						//给选中选项附背景 清空其他背景色
						$("#li1").attr('style','width:123px; height:30px; background-color:#ccc;');
						$("#li2").attr('style','border-right: none;');
						$("#li3").attr('style','border-right: none;');
					}
				}else if(a.text == "环境变量"){
					if(propertiesValidate("创建灰度版本")){//校验
						main.hide();
						configFile.hide();
						configure.show();
						//给选中选项附背景 清空其他背景色
						$("#li1").attr('style','border-right: none;');
						$("#li2").attr('style','width:123px; height:30px; background-color:#ccc;');
						$("#li3").attr('style','border-right: none;');
					}
				}else if(a.text == "配置文件"){
					if(propertiesValidate("创建灰度版本") && propertiesValidate("环境变量")){//校验
//						var configFile_path = $("#configFile_path").val();
//						if(configFile_path == "" || configFile_path == null){
//							$("#uploadBtnId").attr('disabled','disabled');
//							$("#configFileInfo").val('');
//							$("#configFileInfo").attr('disabled','disabled');
//						}else{
//							$("#uploadBtnId").removeAttr('disabled');
//							$("#configFileInfo").removeAttr('readonly');
//						}
						main.hide();
						configure.hide();
						configFile.show();
						//给选中选项附背景 清空其他背景色
						$("#li1").attr('style','border-right: none;');
						$("#li2").attr('style','border-right: none;');
						$("#li3").attr('style','width:123px; height:30px; background-color:#ccc;');
					}
				}
			};
		});
	}
}

/***
 * 显示面板
 * @param val 面板名称
 */
function showPlane(val){
	var li = $(".title-mc")[0].children[0].children;
	$.each(li,function(i,v){
		var a = v.children[0];
		if(a.text == val){
			a.click();
		}
		
	});
}

/**
 * 导出环境变量配置文件
 */
function exportEnvsFile(){	
//	if(env_file_name == ""){//todo
//		parent.parent.alertWarn("没有导入过文件，无法导出！");
//		return;
//	}
	var appServiceId = $("#appId").val();
	var envsConfFileName = $("#appServiceName").val();
	var a = document.getElementById("exportEnvsFile"); 
	a.href = "/paas/appServiceGray/exportEnvsFile.action?appServiceId="+appServiceId+"&exportEnvsFileName="+envsConfFileName;
	a.click("return false");
}

/**
 * 导入应用服务环境变量配置文件
 */
var appServiceName= "";//应用服务名称
function importEnvsFile() {
	   appServiceName = $("#appServiceName").val();
	   var file = document.getElementById('envsConfFile').files[0]; 
	   if(file==null){
		   parent.parent.alertWarn("请先选择文件！");
		   return;
	   }
	   
	   var fileName = file.name;
	   if(env_file_name==file.name){
			parent.parent.parent.alertWarn("文件已上传,不能重复上传");
			return ;
		}
	   if(file.size == 0){
			parent.alertWarn("上传配置文件为空！");
			return;
		}
	   if (file) {
			 var extend = fileName.substring(fileName.lastIndexOf(".")+1); 
		     if(extend != 'properties'){
			   	  parent.parent.alertWarn("请上传.properties类型的文件");
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
		    xhr.open("POST", "/paas/appServiceGray/importEnvsFile.action");
		    xhr.send(fd);
	   }else{
		   parent.parent.alertWarn("请选择文件");
	   }
}
//导入应用服务环境变量配置文件成功后动态生成配置表
function importEnvsFileComplete(evt) {	
  var result = JSON.parse(evt.target.responseText);
  var envsConfJson = JSON.parse(result.resultMessage);
  //var fileName = envsConfJson.fileName;
  var envsConfArray = envsConfJson.conf;
  $("#envsConfFileName").val(appServiceName);
  
  var obj=document.getElementById("envsConfTab"); 
//  var tr= obj.rows["envsTabTrHead"]; 
//  var rowIndex = tr.rowIndex; 
   
  //var rowIndex=0;
  for(var key in envsConfArray){  
	  var tabRows = obj.rows.length;	
  	  rowIndex = tabRows-1;//减去表头那一行，下标从0开始    	
  	  //addEnvsConfTab(obj,key,envsConfArray[key],rowIndex);    
  	  //addEnvsConfTab(obj,key,envsConfArray[key],rowIndex);   
	  addEnvsConfTab(key,envsConfArray[key],rowIndex);  
  }      
}

function uploadFailed(evt) {
	$("#loadImg").hide();    
    parent.parent.alertWarn("There was an error attempting to upload the file.");
}

function uploadCanceled(evt) {
	$("#loadImg").hide();  
    parent.parent.alertWarn("The upload has been canceled by the user or the browser dropped the connection.");
}

function updateGray(id){
	parent.parent.openDialog("/paas/appServiceGray/queryGrayById.action?appServiceId="+id+"&type=update","修改灰度版本",600, 350);
}

var runVersiones;

//清空背景色
function clearBackground(li){
	$.each(li,function(i,v){
		v.childNodes[0].style="";
	});
}
var app_Id;
var operType;
$(function(){
	app_Id=$("#id").val();
	var appId = $("#appId").val();
	operType = $("#operType").val();
	appServiceName = $("#appServiceName").val();
	$("#newGrayBtn").click(function(){
		var runVersion = $("#runVersion").val();
		var instance_num = $("#instance_num").val();
		var app_id = $("#id").val();
		parent.parent.openDialog("/paas/jsp/appservice/grayrelease/createGray.jsp?id="+appId+"&appServiceName="+appServiceName+"&runVersion="+runVersion+"&instance_num="+instance_num+"&appId="+app_id+"&operType="+operType,"新建灰度版本",600, 350);
	});
	
	$('#queryGrayreleaseList').jqGrid({
		url:"/paas/appServiceGray/queryGray.action?appServiceId="+appId,
		datatype: "json",
		width:1070,
		height: "100%",
		jsonReader: {
			  repeatitems: false,
			  root: 'result',
			  id: 'appId',
			  page:  function(obj) {return obj.pageNo; },
			  total: function(obj) { return obj.totalPages; },
			  records: function(obj) { return obj.totalCount; },
			  userdata: "userdata"
	    },
	    colNames:['镜像名称', '镜像版本', '运行实例数/灰度实例数', '总实例数', '状态', '更新时间', '操作'],
	   	colModel:[      
	   		{
	   			name : "imageUrl",
	   			index : "imageUrl",
	   			align : 'center',
	   			width : 170
	   		},
	   		{
	   			name : "imageversion",
	   			index : "imageversion",
	   			align : 'center'
	   		},
	   		{
	   			name : "runInstance",
	   			index : "runInstance",
	   			align : 'center'
	   		},
	   		{
	   			name : "instanceCount",
	   			index : "instanceCount",
	   			align : 'center'
	   		},
	   		{
	   			name : "state",
	   			index : "state",
	   			align : 'center',formatter:function(val, options, rowObject){
	   				runVersiones = rowObject["imageversion"];
	   				var state = "";
	   				if(rowObject["state"] == 1){
	   					state = "停止";
	   				}else{
	   					state = "启动";
	   				}
		   			return state;
	   			}	   			
	   		},
	   		{
	   			name: 'updateTime',index:'updateTime',align : 'center'
	   		},
	   		{
	   			name: 'id',index:'id',align : 'center',formatter:function(val, options, rowObject){
	   				var _v = "'" +val+ "'";
		   			var _link;
		   				_link = '<a href="JavaScript:upgradeGrayReleaseByWebsocket()" style="color:#666">'+
									'<img src="/paas/imgs/deploy.png" width="22" height="22" border="none" title="部署"/>'+
								'</a>';
				   				if(rowObject["state"] == 1){
				   					_link += '<a href="JavaScript:startAndStopGrayReleaseByWebsocket(1)" style="color:#666">'+
									'<img src="/paas/imgs/grayreleaseStart.png" width="22" height="22" border="none" title="启动"/>'+
									'</a>';
				   				}else{
			   						_link += '<a href="JavaScript:startAndStopGrayReleaseByWebsocket(2)" style="color:#666">'+
									'<img src="/paas/imgs/grayreleaseStop.png" width="22" height="22" border="none" title="停止"/>'+
									'</a>';
				   				}
		   						_link += '<a href="JavaScript:updateGray(' +"'"+appId+"'"+ ')" style="color:#666">'+
									'<img src="/paas/imgs/modify.png" width="22" height="22" border="none" title="修改"/>'+
								'</a>'+
								'<a href="JavaScript:deleteGrayReleaseByWebsocket(' +_v+ ')" style="color:#666">'+
								'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/>'+
								'</a>';
		   			return _link;
	   			}
	   		}
	   	],
	   	multiselect: false ,
	   	rowNum:8,
	   	loadonce:true,
		altRows:true,
		altclass:'r1',
		forceFit:true,
	   	pager: '#pagerBar',
	   	sortname: 'createTime',
	    viewrecords: true,
	    sortorder: "desc",
	    caption:"",
	    rownumbers:true,
	    gridComplete : function() {
	    	var result = $(this).getGridParam('userData');
	    	if (typeof(result['errorCode'])!='undefined') {
				parent.parent.alertError(result['errorCode'],result['errorMsg']);
			}
		},
		onPaging : function(pgButton){
			if(pgButton == 'user'){
				var totalPage = document.getElementById("sp_1_pagerBar").innerHTML;
				if(totalPage == 1){
					reloadResult();
				}else{
					var page = $("input[class='ui-pg-input']").val();
					if (totalPage == "") {
						$("input[class='ui-pg-input']").val(0);
					}
					// 字符串数字转换
					if (Number(page) > Number(totalPage)) {
						reloadResults(totalPage);
					} else if (Number(page) < 1
							&& totalPage != "") {
						reloadResult();
					}
				}
			}
		}
	});
	grayOperation();
	
	imageTypeChange();
});
var image_by_id;
function imageTypeChange(){
	image_by_id = $("#image_by_id").val();
	var image_type = $("#imageType").val();
	var oper = $("#oper").val();//当前操作 create||update
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
		data : {id:app_Id,oper:'serviceOper',privateImageType:operType,checkImageId:image_by_id},
		url : actionUrl,
		async :false,
		success : function(result) {	
			if(result['result'] == null || result['result'] == ""){				
				return;
			}
			$.each(result['result'], function(i, val) {
				if(val['checked'] && oper != "create"){
					$("#image_name").append("<option selected value='" + val['id'] + "'>" + val['name'] + "</option>");
				}else{
					$("#image_name").append("<option value='" + val['id'] + "'>" + val['name'] + "</option>");
				}
				if(image_by_id){
					if(val['id'] == image_by_id){//选中之前保存的镜像名称
						$("#image_name").val(image_by_id);
						imageNameChange();//再自动联动出镜像版本
					}
				}
//				if(image_type==1){//公有镜像
//					$("#image_version").append("<option value='" + val['id'] + "'>" + val['version'] + "</option>");
//				}
			});
		}
	});
}

var imageVersionNames = new Array();
var imageVersionLogs = new Array();
var imageVersionConfigFilePath = new Array();
/**
 * 镜像名称选择后，联动查出镜像版本
 */
function imageNameChange(){
	var image_type = $("#imageType").val();
	document.getElementById("image_version").innerHTML = "";//清空镜像版本下拉框	
	var image_id = $("#image_name").val();	
	var image_version_id = $("#image_by_version_id").val();	
	if(image_id=="请选择"){
		$("#image_version").append("<option value = '请选择'>请选择</option>");
		$("#log_dir").val("");		
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
		});	
		
	}else if(image_type==2){//私有镜像
		$("#log_dir").val("");//清空日志路径
		//$("#log_dir").removeAttr('readonly');
		$.ajax({
			cache:false,
			type : "get",
			dataType : "json",
			data: {
				id : image_id.toString(),
				privateImageVersionId:image_version_id,
				appId : $("#id").val()
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

function addEnvs(){
	var tabObj=document.getElementById("envsConfTab");
	var tabRows = tabObj.rows.length;
	//ddEnvsConfTab(tabObj,"","",tabRows);
	addEnvsConfTab("","",tabRows);
}

//添加环境变量
var rowIndex=0;
function addEnvsConfTab(key,value,currentRowIndex){
	rowIndex = currentRowIndex;
	
	$("#envsConfTab").append('<tr id="envsTabTr'+rowIndex+'" class="_tr"><td width="246" height="30" align="center" valign="middle">'
			+'<input type="text"  name="appConfKey"  id="envsConfKey'+rowIndex+'" value="'+key+'"/></td>'
			+'<td width="238" height="30" align="center" valign="middle">'
			+'<input type="text"  name="appConfValue"  id="envsConfValue'+rowIndex+'"  value="'+value+'"/></td>'
			+'<td width="71" height="30" align="center" valign="middle">'
			+'<center><a href="JavaScript:deleteEnvsConf('+rowIndex+');" title="删除"><img src="/paas/imgs/delete.png" width="22" height="22" border="none" />删除</a></center></td></tr>');

	rowIndex++;	
}
/**
 * 清除所有的配置tr
 */
function deleteEnvs(){
	env_file_name="";
	$("._tr").remove();
}

/**
 * 删除一个tr
 * @param rowIndex
 */
function deleteEnvsConf(rowIndex){ 
	$("#envsTabTr"+rowIndex).remove();
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
function grayVersionValidate(flag){
	
	if(!flag){
		showPlane("环境变量");
	}
}

function propertiesValidate(val){
	if(val != "环境变量"){
		parent.closeAlert2();
	}
	//镜像名称校验
	if($("#image_name").val()=="请选择" && val=="创建灰度版本"){
		parent.alertWarn("请选择镜像名称！");
		//showPlane("创建灰度版本");
		return;
	}
	
	//镜像版本校验
	if($("#image_version").val()=="请选择" && val=="创建灰度版本"){
		parent.alertWarn("请选择镜像版本！");
		//showPlane("创建灰度版本");
		return;
	}
	var runVersion = $("#runningVersionId").val();
	//镜像版本校验
	if($("#image_version").val()== runVersion && val=="创建灰度版本"){
		parent.alertWarn("镜像版本不能和当前运行版本一致！");
		//showPlane("创建灰度版本");
		return;
	}
	
	//灰度实例数不能等于总实例数校验
	if($("#grayInstanceNum").val() == $("#totalInstanceNum").val()){
		parent.closeAlert();
		parent.alertWarn("灰度实例数不能和总实例数一致！");
		//showPlane("创建灰度版本");
		return;
	}
	
	//灰度实例数不能等于总实例数校验
	if($("#grayInstanceNum").val() == 0){
		parent.closeAlert();
		parent.alertWarn("灰度实例数不能等于0!");
		//showPlane("创建灰度版本");
		return;
	}
	
	
	//校验环境的key和value
	var obj=document.getElementById("envsConfTab"); 
	if(obj.children.length != 0 && val=="环境变量"){
		var tabRows = obj.children[0].children.length;
		var envsCount = tabRows;//减去表头那一行，下标从0开始    	
		
		var config_error = false;
		var _keys = new Array();
		for(var i=0; i<envsCount; i++){
		//$('input:text[id=envsConfKey]').each(function(){
			var key = $.trim($("#"+obj.children[0].children[i].children[0].children[0].id).val());
			if(key == null || key == ""){
				parent.parent.closeAlert();
				parent.parent.alertWarn("Key不能为空");
				//showPlane('环境变量');
				$(this).focus();
				config_error = true;
				return false;
			}
			if(!/^[A-Za-z_][A-Za-z0-9_]*$/.test(key)){
				parent.parent.alertWarn("Key命名只能以字母或下划线开头，名称中只能包含字母、数字和下划线");
				//showPlane('环境变量');
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
		
		for(var i=0; i<envsCount; i++){
		//$('input:text[id=envsConfValue]').each(function(){
			var value = $.trim($("#"+obj.children[0].children[i].children[1].children[0].id).val());
			if(value == null || value == ""){
				parent.parent.alertWarn("Value不能为空");
				//showPlane('环境变量');
				$(this).focus();
				config_error = true;
				return false;
			}
			//var value = $.trim($(this).val());
			var reg = /^[^\'\"<]*$/;
			if(reg.test(value)==false){
				parent.parent.alertWarn("Value不能包含单引号,双引号或小于号");
				//showPlane('环境变量');
				$(this).focus();  
				config_error = true;
				return false;
			}
		//});
			if(config_error)
				return;
		}
		
	}
	return true;
}

$.fn.serializeObject = function()    
{    
   var o = {};    
   var a = this.serializeArray();    
   $.each(a, function() {    
       if (o[this.name]) {    
           if (!o[this.name].push) {    
               o[this.name] = [o[this.name]];    
           }    
           o[this.name].push(this.value || '');    
       } else {    
           o[this.name] = this.value || '';    
       }    
   });    
   return o;    
}; 

/***
 * 创建和修改页面的保存按钮
 * @param val
 * @param type
 */
function saveGrayVersion(val,type){
	var operation = "";
	if(type==1){
		operation = "create-grayRelease";
	}else{
		operation = "update-grayRelease";
	}
	if(propertiesValidate(val)){
      var jsonuserinfo = $('#createGrayVersionForm').serializeObject();	
	  var opra_url = JSON.stringify(jsonuserinfo) + "%7C" + operation;
	  var websocketUrl = "ws://"+window.location.host+"/paas/websocket/grayRelease";
	  sessionStorage.setItem("opra_url", opra_url);
      parent.parent.alertProgressConfirm("config-app-update", websocketUrl, function(){
    	  //取到灰度按钮实现刷新
    	  $("#f",parent[0].document)[0].click();
      });     
	}
}

/***
 * 部署方式保存
 * 
 */
function saveDeploymentType(){
//	var grayInstanceNum=$("#grayInstanceNum").val();
//	var totalInstanceNum=$("#totalInstanceNum").val();
	var appServiceId=$("#appId").val();
	var deploymentType = $('input[name="deploymentType"]:checked ').val();
//	if (deploymentType!="2") {
//		grayInstanceNum="";
//	}
	$.ajax({
		url : "/paas/appServiceGray/modifyDeploymentType.action",
		type: "POST",
        dataType: "json",
		data :{
//            "grayInstanceNum":grayInstanceNum,
            "appServiceId":appServiceId,
            "deploymentType":deploymentType
//            "totalInstanceNum":totalInstanceNum
        },
		success : function(result) {
			if (result['resultCode'] == 'success') {
				parent.parent.alertSuccess();
				window.location.reload();
			} else {
				parent.parent.alertError(result['resultCode'],
						result['resultMessage']);
				window.location.reload();
			}
		}
	});    
	
}

//通过应用组名称查询
function reloadResult(){
	var appId = 29;
	$("#queryGrayreleaseList").jqGrid()
	.setGridParam({
					page:1,
					rowNum:10,
					mtype:"POST",
					datatype: "json",
					url : "/paas/appServiceGray/queryGray.action?appServiceId="+appId})
	.trigger("reloadGrid");
	$("#groupName").val("");
}
//当输入的跳转页数大于总页数是,自动查询最后一页
function reloadResults(totalPage){
    var rows = $('#appList').getGridParam('rows'); 
    var sidx = $('#appList').getGridParam('sidx'); 
    var sord = $('#appList').getGridParam('sord'); 
    jQuery("#appList").jqGrid('setGridParam', {
        url : '/paas/app/queryAppList.action',
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).trigger("reloadGrid");
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

function uploadFailed(evt) {
	$("#loadImg").hide();    
    parent.alertWarn("There was an error attempting to upload the file.");
}

function uploadCanceled(evt) {
	$("#loadImg").hide();  
    parent.alertWarn("The upload has been canceled by the user or the browser dropped the connection.");
}
var config_file_name = "";
//上传配置文件
function uploadConfigFile(){
	if(fileTypeError){
		parent.alertWarn(fileTypeErrorInfo);
		return;
	}
	
	var file = document.getElementById('configFile').files[0]; 
	if(file.size == 0){
		parent.alertWarn("上传配置文件为空！");
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
	    xhr.open("POST", "/paas/appServiceGray/uploadConfigFile.action");
	    xhr.send(fd);
 }else{
	   parent.alertWarn("请选择文件!");
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



//启动灰度版本
function startAndStopGrayReleaseByWebsocket(type){
	var str = "";
	if(type == 1){
		str = "确认启动灰度版本?";
	}else{
		str = "确认停止灰度版本?";
	}
	parent.parent.alertConfirm(str, function() {
		var stateNumber = $("#stateNumber").val();
		if(stateNumber == 1){
			parent.parent.alertWarn("服务状态为停止，请先启动服务！");
			return;
		}
		var appId = $("#appId").val();
		var opra_url = appId+","+type+ "%7C" + "start-stop-grayRelease";
		var websocketUrl = "ws://"+window.location.host+"/paas/websocket/grayRelease";
	    this.parent.parent.alertProgressConfirm(opra_url, websocketUrl, function(){
	    	window.location.reload();	        	
	    });
	});
}

//删除灰度版本
function deleteGrayReleaseByWebsocket(id){
	parent.parent.alertConfirm("确认删除灰度版本?", function() {
		var appId = $("#appId").val();
		var opra_url = appId+"%7C" + "delete-grayRelease";
		var websocketUrl = "ws://"+window.location.host+"/paas/websocket/grayRelease";
	    this.parent.parent.alertProgressConfirm(opra_url, websocketUrl, function(){
	    	window.location.reload();	        	
	    });
	});
}

/***
 * 部署/升级Websocket函数
 */
function upgradeGrayReleaseByWebsocket(){
	parent.parent.alertConfirm("确认部署灰度服务?", function() {
		var appId = $("#appId").val();
		var opra_url = appId+"%7C" + "upgrade-grayRelease";
		var websocketUrl = "ws://"+window.location.host+"/paas/websocket/grayRelease";
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