<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.apache.struts2.ServletActionContext"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/dailog.css" rel="stylesheet" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet" />

<script src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script src="${ctx}/js/grid.locale-cn.js"></script>
<script src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script src="${ctx}/js/appservice/createAppService.js"></script>
<script src="${ctx}/js/utils.js"></script>

<form id="createAppServcieForm">
	<input id="envsConfFileName" name="appService.envsConfFileName" type="hidden" />
	<input id="serviceFlag" name="appService.serviceFlag" type="hidden" />
	<input id="hostIpFlag" name="appService.hostIpFlag" type="hidden" />
	<input id="extServiceType" name="appService.extServiceType" type="hidden" />
	<input id="inst_scale_type" name="appService.inst_scale_type" type="hidden" />
	<input id="protocolType" name="appService.protocolType" type="hidden" />
	<!-- <input id="cluster_id" name="appService.cluster_id" type="hidden" /> -->
	<input id="flowId" name="appService.flowId" type="hidden" value="<%=request.getParameter("flowId")%>">
	<div class="dialog">

		<div class="dialog-title">
			<div class="title-mc">
				<ul>
					<li id="li1" style="width: 123px; background: #ccc; height: 30px;">
						<a href="JavaScript:showInfo();">基础信息</a>
					</li>
					<li id="li2"><a href="JavaScript:showConfigFile();">配置文件</a></li>
					<li id="li3" style="border-right: none;"><a href="JavaScript:showEnvs();">环境变量</a></li>
				</ul>
			</div>
		</div>

		<div id="appServiceInfo" class="dialog-main">
			<table width="560" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="275">
						<table width="250" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="85" height="30" align="right" valign="middle"><strong>*</strong>服务名称：</td>
								<td width="165" height="30" align="left" valign="middle"><input
									id="name" name="appService.name" type="text" /></td>
							</tr>
							<tr>
								<td colspan="2" align="right" valign="middle">
									<table width="250" border="0" cellspacing="0" cellpadding="0">
									</table>
								</td>
							</tr>
						</table>
					</td>
					<td width="305">
						<table width="283" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="24" align="right" valign="middle"><strong>*</strong>集群名称：</td>
								<td width="183" height="24" align="left" valign="middle">
									<!-- <select id="cluster_name" name="appService.cluster_id" onchange="clusterChange();"></select> -->
									<select id="cluster_name" name="appService.cluster_id" onchange="changeCluster();"></select>
									<!-- <label id="cluster_name" style="margin-left: 11px;"></label> -->
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="550" border="0" cellspacing="0" cellpadding="0"
							style="border-radius: 5px; border: 1px solid #dadada;">
							<tr>
								<td width="85" height="30" align="right"><strong>*</strong>镜像类型：</td>
								<td width="190" height="30">
									<select id="image_type" name="appService.image_type" onchange="imageTypeChange();">
										<option selected="selected" value="2">私有镜像</option>
										<option value="1">公有镜像</option>
									</select>
								</td>
								<td width="93" height="30" align="right"><strong>*</strong>镜像名称：</td>
								<td width="182" height="30">
									<select id="image_name" name="appService.image_name" onchange="imageNameChange();">
										<option value="">请选择</option>
									</select>
								</td>
							</tr>
							<tr>
								<td height="30" align="right"><strong>*</strong>镜像版本：</td>
								<td height="30">
									<select id="image_version" name="appService.image_version">
										<option value="">请选择</option>
									</select>
								</td>
								<td height="30" align="right"><strong> </strong>日志路径：</td>
								<td height="30" align="left">
									<input id="log_dir" name="appService.log_dir" readonly="readonly" type="text" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="70" height="40" align="right" valign="middle"><strong>*</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;内存：
						<input type="text" name="appService.mem" id="mem" placeholder="均值"
						style="width: 60px;" value="256"  onblur="isExcess(2)"/> —<input type="text"
						name="appService.maxMem" id="maxMem" placeholder="最大值"
						style="width: 60px;" value="1024" /> MB</td>
					<td width="91" height="30" align="left" valign="middle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>*</strong>cpu：
						<input type="text" name="appService.cpu" id="cpu" placeholder="均值"
						style="width: 60px;" value="0.5" onblur="isExcess(1);"/> —<input type="text"
						name="appService.maxCpu" id="maxCpu" placeholder="最大值"
						style="width: 60px;" value="1" /></td>
				</tr>
			</table>
			<table width="550" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="84" height="30" align="right" valign="bottom">描述：</td>
					<td width="416" height="30" align="left" valign="bottom">
						<textarea name="appService.description" id="description" cols="" rows=""
							style="margin-left: 10px; height: 20px; border: 1px solid #c0c0c0; 
							width: 410px; border-radius: 5px; resize: none;">
						</textarea>
					</td>
				</tr>
			</table>
			<table width="560" border="0" cellspacing="0" cellpadding="0"
				style="border: 1px solid #dadada; border-radius: 5px; padding: 10px 0; margin-top: 10px; display: block; width: 560px;">
				<tr valign="middle" align="left">
					<td width="100" height="30" align="left"  style="padding-left:15px;">
						<span style="font-size: 14px; width: 100px; font-weight: bold;">
							<img src="${ctx}/imgs/checked.png" width="14" height="17" style="margin-right: 5px;" />是否服务:
						</span>
					</td>
					<td width="120" height="30" align="left">
						<form id="form1" name="form1" method="post" action="">
							<label>
								<input id="RadioGroup1_yes" name="serviceRadio" onclick="displayServiceInfo();" type="radio"
									style="width: 13px; height: 13px;" value="是" /> 是
							</label>
							<label>
								<input id="RadioGroup1_no" type="radio" onclick="undisplayServiceInfo();" name="serviceRadio" value="否"
									style="width: 13px; height: 13px;" checked="checked" /> 否
							</label><br />
						</form>
					</td>
					<td width="101" align="left">&nbsp;</td>
					<td width="60" align="left">&nbsp;</td>
					<td align="left">&nbsp;</td>
					<td align="left">&nbsp;</td>
				</tr>
				<tr id="appServicePortTr">
					<td height="20" align="right" valign="middle" width="90"><strong
						style="color: #cc3c3b; font-size: 12px; margin-right: 5px;">*</strong>应用端口：</td>
					<td height="20" align="left" valign="middle"><input
						name="appService.containerPort" id="containerPort" type="text"
						style="width: 80px; height: 22px; border-radius: 5px; border: 1px solid #dadada; margin-right: 8px;" /></td>
					<td align="left" valign="middle" width="100">是否外部服务：</td>
					<td align="left" valign="middle"><input id="isExtServiceType"
						type="checkbox" onchange="protocolChange();"
						style="width: 15px; height: 15px;" value="" /></td>
					<td align="right" valign="middle" id="protocol"
						style="display: none;"><strong
						style="color: #cc3c3b; font-size: 12px; margin-right: 5px;">*</strong>协议：</td>
					<td align="left" valign="middle" id="protocolSelect"
						style="display: none;"><select style="width: 60px;"
						id="protocolValue"><option value="1">http</option>
							<option value="2">tcp</option></select></td>
				</tr>
			</table>
			<table width="560" border="0" cellspacing="0" cellpadding="0"
				style="border: 1px solid #dadada; border-radius: 5px; padding: 10px 0; margin-top: 10px; display: block; width: 560px;">
				<tr valign="middle" align="left">
					<td width="100" height="30" align="left"  style="padding-left:15px;"><span
						style="font-size: 14px; width: 100px; font-weight: bold;"><img src="${ctx}/imgs/ip.png" width="10" height="14" style="margin-right: 5px;" />
						是否指定IP:</span></td>
					<td width="120" height="30" align="left"><form id="form1"
							name="form1" method="post" action="">

							<label> <input id="RadioGroup1_yes" name="specifyIpRadio"
								onclick="displaySerivceHostInfo();" type="radio"
								style="width: 13px; height: 13px;" value="是" /> 是
							</label> <label> <input id="RadioGroup1_no" type="radio"
								onclick="undisplayServiceHostInfo();" name="specifyIpRadio"
								value="否" style="width: 13px; height: 13px;" checked="checked" />
								否
							</label> <br />
							</p>
						</form></td>

				</tr>
				<tr id="appServiceHostTr">
					<td height="20" align="right" valign="middle" width="90"><strong
						style="color: #cc3c3b; font-size: 12px; margin-right: 5px;">*</strong>IP地址：</td>
					<td height="20" align="left" valign="middle"><select
						name="appService.hostIp" id="hostIps"
						style="width: 158px; height: 22px; border-radius: 5px; border: 1px solid #dadada; margin-right: 8px;" onchange="isCheckIp()"></select></td>

				</tr>
			</table>
			<table width="560" border="0" cellspacing="0" cellpadding="0"
				style="border: 1px solid #dadada; border-radius: 5px; margin-top: 15px; padding-bottom: 10px; display: block; width: 560px;">
				<tr>
					<td width="100" height="30" align="left" valign="bottom" style="padding-left:15px;"><img
						src="${ctx}/imgs/instanceicon.png" width="15" height="15"
						style="margin-right: 6px;" /><span
						style="font-size: 14px; font-weight: bold;">实例数：</span></td>
					<td width="218" height="30" align="left" valign="bottom"><form
							id="form1" name="form1" method="post" action="">

							<label> <input name="instRadio" type="radio"
								onclick="displayFixInstInfo();" id="RadioGroup1_fixed"
								style="width: 13px; height: 13px;" value="是" checked="checked" />
								固定
							</label> <label> <input type="radio" name="instRadio"
								onclick="displayDynamicInstInfo();" value="否"
								id="RadioGroup1_dynamic" style="width: 12px; height: 12px;" />
								动态（Beta版）
							</label><br />
							</p>
						</form></td>
					<td align="left" valign="bottom">&nbsp;</td>
					<td align="left" valign="bottom">&nbsp;</td>
				</tr>
				<tr>
					<td align="right" valign="bottom"></td>
					<td height="5" align="left" valign="bottom"></td>
					<td align="left" valign="bottom"></td>
					<td align="left" valign="bottom"></td>
				</tr>
				<tr id="instScaleTr">
					<td height="20" align="right" valign="middle"><strong
						style="color: #cc3c3b; font-size: 12px; margin-right: 5px;">*</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实例数：
					</td>
					<td width="218" height="20" align="left" valign="middle"><input
						type="text" id="inst_min" name="appService.inst_min"
						style="width: 60px;" value="1" />最小 —<input type="text"
						id="inst_max" name="appService.inst_max" style="width: 60px;"
						value="2" />最大</td>
					<td align="right" valign="middle"><strong
						style="color: #cc3c3b; font-size: 12px; margin-right: 5px;">*</strong>cpu目标值：</td>
					<td align="left" valign="middle"><input type="text"
						id="cpu_target" name="appService.cpu_target" style="width: 60px;"
						value="50" />%</td>
				</tr>
				<tr id="instNumTr">
					<td height="20" align="right" valign="middle"><strong
						style="color: #cc3c3b; font-size: 12px; margin-right: 5px;">*</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实例数：
					</td>
					<td height="20" align="left" valign="middle"><input
						type="text" id="instance_num" name="appService.instance_num"
						style="width: 60px;" value="1" /></td>
				</tr>
			</table>

			<div class="add-service" id="addServiceDiv">
				<div class="add-service-title">
					<a onclick="addIpaasService();"><img
						src="${ctx}/imgs/structure-icon.png" width="22" height="22" /><strong
						style="font-size: 14px; color: #333;">添加基础服务</strong></a>
				</div>

			</div>

			<div class="bottom">
				<input id="nextButtonId" type="button" value="下一步"
					onclick="showConfigFile();" /> <input id="nextinfo" type="button"
					value="保存" onclick="saveAppService();" />
			</div>
		</div>

		<div class="dialog-main" id="appServiceConfigFile"
			style="display: none; margin-left: 10px;">
			<%-- <table width="500" border="0" cellspacing="0" cellpadding="0">
				<tr valign="middle">
					<td width="107" height="36" style="font-size: 14px;"><img
						src="${ctx}/imgs/tips.png" />&nbsp;配置文件路径为空时不能上传。</td>
				</tr>
			</table> --%>
			<table width="500" border="0" cellspacing="0" cellpadding="0">
				<tr valign="middle">

					<td width="336" height="36" style="font-size: 14px;">导入配置文件：</td>
					<td width="220" height="36" style="font-size: 14px;">&nbsp;</td>
				</tr>
				<tr>

					<td valign="top">
						<form id="form3" name="form3" enctype="multipart/form-data"
							method="post">
							<label for="configFile"></label> <input type="file"
								name="configFile" id="configFile"
								style="background: none; border: none; width: 180px; height: 25px;"
								accept=".conf,.cfg,.txt,.properties" onchange="fileSelected();" />
						</form>
					</td>
					<td><input id="uploadBtnId" name="" type="button" value="上传"
						onclick="uploadConfigFile();"
						style="background: #444; width: 67px; height: 26px; border: none; font-family: '微软雅黑'; font-size: 14px; color: #fff; cursor: pointer;" /></td>
				</tr>

			</table>
			<table width="560" border="0" cellspacing="0" cellpadding="0"
				style="margin: 10px 0;">
				<tr>
					<td width="107" height="30" style="font-size: 14px;">配置文件路径：</td>
					<td width="450"><input id="configFile_path"
						name="appService.configFilePath" type="text" value=""
						readonly="readonly" style="width: 410px; height: 25px;" /></td>
				</tr>
			</table>
			<table width="500" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="30" style="font-size: 14px;">配置文件信息：</td>
				</tr>
				<tr>
					<td><textarea id="configFileInfo"
							name="appService.configFileInfo" cols="" rows=""
							style="width: 530px; height: 80px; resize: none; border: 1px solid #c0c0c0;"></textarea></td>
				</tr>
			</table>
			<div class="bottom" style="padding-left: 160px;">
				<input name="" type="button" value="上一步" style="margin-right: 10px;"
					onclick="showInfo();" /> <input name="" type="button" value="下一步"
					onclick="showEnvs();" /> <input name="" type="button" value="保存"
					onclick="saveAppService();" />
			</div>
		</div>

		<div class="dialog-configure" id="appServiceEnvs"
			style="display: none">
			<table width="560" border="0" cellspacing="0" cellpadding="0"
				style="margin-bottom: 15px;">
				<tr>
					<td width="373"><span><form id="form2" name="form2"
								enctype="multipart/form-data" method="post">
								<label for="envsConfFile"></label> <input
									style="width: 198px; cursor: pointer" type="file"
									name="envsConfFile" id="envsConfFile" accept=".properties" />
							</form></span></td>
					<td align="left"><input type="button"
						onclick="importEnvsFile();" value=""
						style="background:url(${ctx}/imgs/import-Alarm.png) no-repeat;" /></td>
					<td align="left"><input type="button" onclick="addEnvs();"
						value=""
						style="background:url(${ctx}/imgs/add-Alarm.png) no-repeat;" /></td>
					<td align="left"><input type="button" onclick="deleteEnvs();"
						value=""
						style="background:url(${ctx}/imgs/Log-delete.png) no-repeat;" /></td>
					<td align="left"><a href="javaScript:exportEnvsFile()"
						id="exportEnvsFile"
						style="background:url(${ctx}/imgs/exporticon.png) no-repeat;" /></a></td>
				</tr>
			</table>
			<div class="box-list">
				<table id="envsConfTab" width="555" border="0" cellspacing="0"
					cellpadding="0" class="configure-list">
					<tr>
						<td width="110" height="26" align="center" valign="middle"
							style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">Key</td>
						<td width="200" height="26" align="center" valign="middle"
							style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">Value</td>
						<td width="70" height="26" align="center" valign="middle"
							style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">操作</td>
					</tr>
				</table>
			</div>
			<!--   <table id="envsConfTab" width="555" border="0" cellspacing="0" cellpadding="0" class="configure-list">		  	 
	 </table>-->

			<div class="bottom">
				<input type="button" id="upinfo" onclick="showConfigFile();"
					value="上一步" /> <input type="button" id="nextinfo"
					onclick="saveAppService();" value="保存" />
			</div>
		</div>

		<div id="loadImg" style="display: none">
			<img alt="" src="${ctx}/imgs/load.gif"></img>
		</div>
	</div>
</form>

