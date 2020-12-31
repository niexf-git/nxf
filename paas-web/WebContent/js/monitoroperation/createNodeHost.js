/**
 * 添加node主机
 */
//初始化构建列表 
$(function() {
	var clusterType = $.getUrlParam('clusterType');
	var clusterId = $.getUrlParam('clusterId');
	$("#clusterId").val(clusterId);
	queryDeployComponentList(clusterType);
});

//查询部署组件列表
function queryDeployComponentList(clusterType){
	$.ajax({
		type : "POST",
		dataType : "json",
		data: {
			"clusterType" : clusterType.toString(),
			"flag" : "0"
		},
		url : '/paas/host/queryDeployComponentList.action',
		success : function(result) {
			if (result['resultCode'] == 'success') {
				var componentList = JSON.parse(result['resultMessage']);
				$.each(componentList, function(i, val) {
					$('#deployId').val(val['id']);
					$('#components').text(val['components']);
				});
			} else {
				parent.parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	});
}

var rowIndex = 1;
//添加一行环境变量
function addOneTrEnv(){
	$("#hostsConfTab").append(
	  '<tr id="hostsTabTr' + rowIndex + '" class="_tr" >' +
		'<td width="246" height="30" valign="middle"><input id="ip" name="hostList['+rowIndex+'].hostIP" type="text" /></td>'+
	    '<td width="238" height="30" valign="middle"><input id="password" name="hostList['+rowIndex+'].password" type="text" onfocus="changeAttr(this)" /></td>'+
	    '<td width="71"  height="30" valign="middle">'+
	    '<center><a href="JavaScript:deleteOneTrEnv('+rowIndex+');" title="删除">'+
	    '<img src="../../imgs/delete.png" width="22" height="22"/>删除</td>'+
	    '<td width="10"  height="30" valign="middle"><input name="hostList['+rowIndex+'].deployId" id="deployId" type="hidden" /></td>'+
	  '</tr>'
	 );
	rowIndex++;
}
function changeAttr($this){
	$this.setAttribute("type", "password");
}
//删除一行环境变量
function deleteOneTrEnv(rowIndex){ 
	$("#hostsTabTr"+rowIndex).remove();
}

//清除添加的所有环境变量，只保留默认的一行
function deleteAppendTrEnvs(){
	$("._tr").remove();
}

//页面参数校验
function validateParameter(){
	var tabObj = document.getElementById("hostsConfTab");
	var tabRows = tabObj.rows.length;  // 行数
	var ipArr = [];
	//ip地址
	for(var i=0;i<tabRows;i++){
		var ip = $.trim(tabObj.rows[i].cells[0].childNodes[0].value);
		if(ip){
			ipArr.push(ip);
		}
		if(ip == null || ip == ""){
			parent.parent.alertWarn("ip地址不能为空");
			return false;
		}
		var re =  /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;  
		if (ip.indexOf(",") != -1) {
			var arr = ip.split(",");
			for (i = 0; i < arr.length; i++) {
				if (!re.test(arr[i])) {
					parent.parent.alertWarn("ip地址格式不正确，请修改");
					return false;
				}
			}
		} else {
			if (!re.test(ip)) {
				parent.parent.alertWarn("ip地址格式不正确，请修改");
				return false;
			}
		}
	}
	//root密码
	for(var i=0;i<tabRows;i++){
		var password = $.trim(tabObj.rows[i].cells[1].childNodes[0].value);
		if(password == null || password == ""){
			parent.parent.alertWarn("root密码不能为空");
			return false;
		}
	}
	//给每行的隐藏域赋值部署组件id
	for(var i=0;i<tabRows;i++){
		tabObj.rows[i].cells[3].childNodes[0].value = $('#deployId').val();
	}
	
	var ipObj={};
	for(var i=0;i<ipArr.length;i++){
		var flag= ipObj[ipArr[i]];
		if(flag){
			parent.parent.alertWarn("ip不能重复");
			return false;
		}else{
			ipObj[ipArr[i]]=true;
		}
	}
	return true;
}
function showLoad(){	
	$('#loadImg').css({"width":"100%","height":"100%"});
	$('#loadImg').show();
}
function closeLoad(){
	$('#loadImg').hide();
}
//部署按钮提交表单
function deploy(){
	//校验参数
	var result = validateParameter();	
	if(!result){
		return ;
	}
	showLoad();
	$.ajax({
		type : "POST",
		dataType : "json",
		data : $('#addNodeHostForm').serialize(),
		url : '/paas/host/createHost.action',
		success : function(result) {
			if (result['resultCode'] == 'success') {
				closeLoad();
				// 新增Node主机成功后刷新左侧树
				$("#mainIframe",window.parent.document)[0].contentWindow.frames["left"].refreshNode();
				
				parent.parent.alertSuccess(); // 弹出成功提示框
				
				// 新增Node主机成功后刷新列表
				$("#mainIframe",window.parent.document)[0].contentWindow.frames["right"].queryHostList();
				
				parent.parent.close(); // 关闭新增Node主机弹出窗口
			} else {
				parent.parent.alertError(result['resultCode'],result['resultMessage']);
		    }
			closeLoad();
		}
	});
}
