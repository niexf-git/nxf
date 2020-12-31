/**
 * 添加非node主机（部署Paas平台的主机）
 */
// 初始化构建列表
$(function() {
	
	var clusterType = $.getUrlParam('clusterType');
	var clusterId = $.getUrlParam('clusterId');
	var dataCenterType = $.getUrlParam('dataCenterType');

	$("#clusterId").val(clusterId);
	queryDeploySchemeList(dataCenterType, clusterType);
	queryDeployComponentList();
	
});

// 查询部署方案
function queryDeploySchemeList(dataCenterType, clusterType) {
	$.ajax({
		type : "POST",
		dataType : "json",
		async : false,
		data : {
			"dataCenterType" : dataCenterType.toString(),
			"clusterType" : clusterType.toString()
		},
		url : '/paas/host/queryDeploySchemeList.action',
		success : function(result) {
			if (result['resultCode'] == 'success') {
				var deploySchemeList = JSON.parse(result['resultMessage']);
				$.each(deploySchemeList, function(i, val) {
					$("#deploySchemeId").append(
							"<option value='" + val['id'] + "'>" + val['name']
									+ "</option>");
				});
			} else {
				parent.parent.parent.alertError(result['resultCode'],
						result['resultMessage']);
			}
		}
	});
}
function addNum(){
	var len = $('#hostsConfTab tr').length;
	var num=0;
    for(var i = 0;i<len;i++){
    	if(!$('#hostsConfTab tr:eq('+i+')').is(":hidden")){
    		num++;
    		$('#hostsConfTab tr:eq('+i+') td:first').html(num);
    	}
    }
}
// 根据选择的部署方案查询部署组件列表
function queryDeployComponentList() {
	var deploySchemeId = $("#deploySchemeId").val();
	deleteAppendTrHosts();
	$.ajax({
		type : "POST",
		dataType : "json",
		async : "false",
		data : {
			"deploySchemeId" : deploySchemeId.toString(),
			"flag" : "1"
		},
		url : '/paas/host/queryDeployComponentList.action',
		success : function(result) {
			if (result['resultCode'] == 'success') {
				var deployComponentList = JSON.parse(result['resultMessage']);
				//alert(result['resultMessage']);
				$.each(deployComponentList, function(i, val) {
					addOneTrHost(val['id'], val['components'],
							val['description'],val['hainfo']);
				});
				addNum();
			} else {
				parent.parent.alertError(result['resultCode'],
						result['resultMessage']);
			}
		}
	});
}
function parseDomponents(components){
	var componentsArr = components.split(",");
	var comp="";
	var mIndex=0;
	var comp1="";
	for(var i=0;i<componentsArr.length;i++){
		var temp = componentsArr[i];
		if((comp+temp).length<=25){
			comp+=temp+",";
		}else{
			mIndex++;
			if(mIndex==1){
				comp+="</br>"+temp+",";
			}else{
				comp1+=componentsArr[i-1]+",";
				if((comp1+temp).length>25){
					comp+="</br>";
					comp1="";
				}
				comp+=temp+",";
			}
		}
	}
	return comp.slice(0,-1);
}
var index=0;//单选框问题处理,显示，隐藏多条处理,ha处理
var rowIndex = 0;
// 添加一行环境变量
function addOneTrHost(deployId, components, description,hainfo) {
	components = parseDomponents(components);
	var haHtml="";
	if(hainfo){
		haHtml = 'HA部署:<input name="RadioGroup1" type="checkbox" id="RadioGroup1_0" style="width:13px; height:13px;" />';      
	};
	$("#hostsConfTab")
	.append(
			'<tr id="hostsTabTr'
					+ rowIndex
					+ '" class="_tr" >'
					+'<td width="40" height="30" align="center" valign="middle" ></td>'
					+ '<td width="190" height="30" style="padding-right:10px;" align="left" valign="middle" >'
					+ components
					+ '</td>'
					+ '<td width="190"  style="padding-right:3px;" height="30" align="left" valign="middle" >'
					+ description
					+ '</td>'
					+ '<td width="110" height="30" valign="middle" align="left"><input id="ip" style="width: 100px;margin-left:0px;"  name="hostList['
					+ rowIndex
					+ '].hostIP" type="text" /></td>'
					+ '<td width="90" height="30" valign="middle" align="left" style="padding-left:10px;" ><input id="password" style="width: 80px;margin-left:0px;" name="hostList['
					+ rowIndex
					+ '].password" type="text" onfocus="changeAttr(this)" /></td>'
					+ '<td width="160" style="padding-left:10px;" height="30" valign="middle" align="left">'
					+haHtml
					+'<input name="hostList['+ rowIndex + '].deployId" type="hidden" value="'+ deployId + '"/>'
					+'</td></tr>');
	var arr = [];
	if(hainfo){
		index++;
		arr.push(index);
		var componentsHa,descriptionHa,dId;
		$('#hostsConfTab').on('click' , 'input[type="checkbox"]' , function(){
			var next = $(this).closest("tr").next("tr");
			if(this.checked){	
				addNum();
				next.show();
				next.find("input").attr("disabled",false);
			}else{
				addNum();
				next.find("input[name*=hostIP]").val("");
				next.find("input[name*=password]").val("");
				next.find("input[name*=floatIp]").val("");
				next.hide();
				next.find("input").attr("disabled",true);
			}
		});
	
		$.ajax({
			type : "GET",
			dataType : "json",
			async : "false",
			data : {
				"deploySchemeId" : hainfo.toString(),
				"flag" : "1"
			},
			url : '/paas/host/queryDeployComponentList.action',
			success : function(result) {
				if (result['resultCode'] == 'success') {
					var deployComponentList = JSON.parse(result['resultMessage']);
					componentsHa = deployComponentList[0].components;
					descriptionHa = deployComponentList[0].description;
					dId = deployComponentList[0].id;
					componentsHa = parseDomponents(componentsHa);
					for(var i=0;i<arr.length;i++){
						$(".componentsHa"+arr[i]).html(componentsHa);
						$(".descriptionHa"+arr[i]).html(descriptionHa);
						$(".deployIdHa"+arr[i]).val(dId);
					}
				}
			}
		});
		$("#hostsConfTab")
		.append(
				'<tr class="hide'+index+'" style="display:none;">'
						+'<td width="40" height="30" align="center" valign="middle"></td>'
						+ '<td width="190" style="padding-right:10px;"  class="componentsHa'+index+'" height="30" align="left" valign="middle" >'
						+ '</td>'
						+ '<td width="190" style="padding-right:3px;" class="descriptionHa'+index+'" height="30" align="left" valign="middle" >'
						+ '</td>'
						+ '<td width="110" height="30" valign="middle"><input id="ip" style="width: 100px;margin-left:0px;" name="hostListHA[' 
						+ (index-1)
						+ '].hostIP" type="text" /></td>'
						+ '<td width="90" height="30" valign="middle" style="padding-left:10px;"><input id="password" style="width: 80px;margin-left:0px;" name="hostListHA['
						+ (index-1)
						+ '].password" type="text" onfocus="changeAttr(this)" /></td>'
						+ '<td width="160" style="padding-left:7.5px;" height="30" valign="middle" align="left">'
						+'&nbsp;&nbsp;浮动IP:<input  name="hostListHA['+(index-1)+'].floatIp" style="width:100px;" />'
						+'<input name="hostListHA['+ (index-1) + '].deployId" class ="deployIdHa'+index+'" type="hidden" disabled="disabled"/> '
						+'<input class ="mainIpHa'+index+'" name="hostListHA['+ (index-1) + '].mainIp" type="hidden" disabled="disabled"/>'
						+'</td></tr>');
	}
	$(".hide"+index).find("input").attr("disabled",true);
	rowIndex++;
}
function changeAttr($this){
	$this.setAttribute("type", "password");
}
// 清除添加的所有行
function deleteAppendTrHosts() {
	rowIndex=0;
	$("#hostsConfTab").find("tr").remove();
}

// 页面参数校验
function validateParameter() {
	
	var tabObj = document.getElementById("hostsConfTab");
	var tabRows = $(tabObj).find("tr").size(); // 行数
	var ipArr = [];
	// ip地址
	var re = /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;
	for (var i = 0; i < tabRows; i++) {
		if(!$(tabObj).find("tr").eq(i).is(":hidden")){
			var ip =$.trim($(tabObj).find("tr").eq(i).find("td").eq(3).find("input[name*=hostIP]").val());
			var password = $.trim($(tabObj).find("tr").eq(i).find("td").eq(4).find("input[name*=password]").val());
			var floatIp = $.trim($(tabObj).find("tr").eq(i).find("td").eq(5).find("input[name*=floatIp]").val());
			if(ip){
				ipArr.push(ip);
			}
			if(floatIp){
				ipArr.push(floatIp);
			}
			
			if (ip == null || ip == "") {
				parent.parent.alertWarn("ip地址不能为空");
				return false;
			}
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
			//password
			 if (password == null || password == "") {
					parent.parent.alertWarn("root密码不能为空");
					return false;
			}
			//floatip
			 if($(tabObj).find("tr").eq(i).prev("tr").find("td").eq(5).find("input[type='checkbox']").is(":checked")){
				 if (floatIp == null || floatIp == "") {
						parent.parent.alertWarn("floatip地址不能为空");
						return false;
					}
				 if (floatIp.indexOf(",") != -1) {
						var arr = floatIp.split(",");
						for (i = 0; i < arr.length; i++) {
							if (!re.test(arr[i])) {
								parent.parent.alertWarn("floatip地址格式不正确，请修改");
								return false;
							}
						}
				} else {
					if (!re.test(floatIp)) {
						parent.parent.alertWarn("floatip地址格式不正确，请修改");
						return false;
					}
				}
			 }
		}
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
function showLoad() {
	$('#loadImg').css({
		"width" : "100%",
		"height" : "100%"
	});
	$('#loadImg').show();
}
function closeLoad() {
	$('#loadImg').hide();
}
// 部署按钮提交表单
function deploy() {
	for(var i=1;i<=index;i++){
		var mainIp = $("[class='mainIpHa"+i+"']").closest("td").closest("tr").prev().find("[name*=hostIP]").val();
		$("[class='mainIpHa"+i+"']").val(mainIp);//用于后台匹配相同的IP，set floatIP。。
	}
	
	// 校验参数
	var result = validateParameter();
	if (!result) {
		return;
	}
	showLoad();
	$
			.ajax({
				type : "POST",
				dataType : "json",
				data : $('#addNodeHostForm').serialize(),
				url : '/paas/host/createHost.action',
				success : function(result) {
					if (result['resultCode'] == 'success') {
						closeLoad();
						// 新增PaaS主机成功后刷新左侧树
						$("#mainIframe", window.parent.document)[0].contentWindow.frames["left"]
								.refreshNode();

						parent.parent.alertSuccess(); // 弹出成功提示框

						// 新增PaaS主机成功后刷新列表
						$("#mainIframe", window.parent.document)[0].contentWindow.frames["right"]
								.queryHostList();

						parent.parent.close(); // 关闭新增PaaS主机弹出窗口
					} else {
						parent.parent.alertError(result['resultCode'],
								result['resultMessage']);
					}
					closeLoad();
				}
			});
}
