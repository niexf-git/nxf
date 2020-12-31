<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.apache.struts2.ServletActionContext"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/index.css" rel="stylesheet" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"	/>

<script src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script src="${ctx}/js/grid.locale-cn.js"></script>
<script src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script src="${ctx}/js/appservice/configAppService.js"></script>
<%
	String errorInfo = (String) request.getAttribute("errorMsg");// 获取错误属性
	String appServiceId = (String) request.getAttribute("appServiceId");// 获取应用服务id
	if (errorInfo != null) {
%>
<script type="text/javascript">
parent.parent.alertError("<%=errorInfo%>");
</script>
<%
	}
%>
<style>
	html,body{ overflow-x:hidden;}
</style>

<form id="configForm">
	<input id="grayInstanceNum" value="${appService.grayInstanceNum}" type="hidden" />
	<input id="appId" name="appId" value="${appService.app_id}"	type="hidden" /> 
	<input id="operType" name="operType" value="${appService.oper_type}" type="hidden" /> 
	<input id="appServiceId" name="id" value="<%=appServiceId%>" type="hidden" />
	<input id="envsConfFileName" name="envsConfFileName" type="hidden" />
	<!-- 用于导出文件 -->
	<input id="clusterId" name="clusterId" value="${appService.cluster_id}" type="hidden" /> 
	<input id="cluster_name" name="cluster_name" value="${appService.cluster_name}" type="hidden" /> 
	<input id="image_id" name="image_id" value="${appService.image_id}"	type="hidden" /> 
	<input id="image_version_id" name="image_version_id" value="${appService.image_version_id}" type="hidden" /> 
	<input id="service_flag" name="serviceFlag" value="${appService.serviceFlag}" type="hidden" /> 
	<input id="hostIpFlag" name="hostIpFlag" value="${appService.hostIpFlag}" type="hidden"/>
	<input id="extService_type" name="extServiceType" value="${appService.extServiceType}" type="hidden" /> 
	<input id="inst_scale_type" name="inst_scale_type"	value="${appService.inst_scale_type}" type="hidden" /> 
	<input id="protocolType" name="protocolType" value="${appService.protocolType}" type="hidden" />
	<input id="image_type2" name="image_type2" value="${appService.image_type}"	type="hidden" /> 
	<input id="submitJson" name="submitJson" type="hidden" />
	<input id="existGray" name="existGray" value="${appService.existGray}" type="hidden" />
	<input id="grayImageType" name="grayImageType" value="${appService.grayImageType}" type="hidden" />
	<input id="grayImageId" name="grayImageId" value="${appService.grayImageId}" type="hidden" />
	<input id="grayImageVersionId" name="grayImageVersionId" value="${appService.grayImageVersionId}" type="hidden" />
	<input id="name" name="name" value="${appService.name}"	type="hidden"></input>
	<input id="ip" name="ip" value="${appService.hostIp}" type="hidden"/>
	<input id="deployType" name="deployType" value="${appService.deployType}" type="hidden"/>
	<div class="main-Content">
		<div class="Operation-image-menu">
			<div class="Operation-menu-content">
				<ul>
					<li style="font-weight:  bold;"><a href="JavaScript:showInfo();">基础配置</a></li>
					<li><a href="JavaScript:showConfigFile();">配置文件</a></li>
					<li style="border-right: none;"><a href="JavaScript:showEnvs();">环境变量</a></li>
				</ul>
			</div>
			<div class="config-tips">
				<c:if test="${appService.config_effect eq '0'}">
					<c:choose>
						<c:when test="${appService.status eq '2'}">
							<img src="${ctx}/imgs/tips.png" />&nbsp;配置未生效，请重启生效 </c:when>
						<c:when test="${appService.status eq '1'}">
							<img src="${ctx}/imgs/tips.png" />&nbsp;配置未生效，请启动生效</c:when>
					</c:choose>
				</c:if>
			</div>
		</div>

		<div id="appServiceInfo" class="cut-Operation-main">
			<table style="border-bottom: 1px solid #dadada; width: 1077px; border: 0; cellspacing: 0; cellpadding: 0;">
				<tr>
					<td width="76" height="24" align="right" valign="middle"><strong></strong>服务名称：</td>
					<td width="140" height="24" align="left" valign="middle">
						${appService.name}
					</td>

					<td width="85" align="right" valign="middle"><strong>*</strong>镜像类型：</td>
					<td width="140" align="left" valign="middle"><span><select
							id="image_type" name="image_type" onchange="imageTypeChange();">
								<option value="2">私有镜像</option>
								<option value="1">公有镜像</option>
						</select></span></td>
					<td width="83" height="30" align="right" valign="middle"><strong>*</strong>镜像名称：</td>
					<td width="140" height="30" align="left" valign="middle"><span><select
							id="image_name" name="image_name" onchange="imageNameChange();">
						</select></span></td>
					<td width="46" height="40" align="right" valign="middle"><strong>*</strong>内存：</td>

					<td width="192" align="left" valign="middle"><input
						type="text" name="mem" id="mem" placeholder="均值"
						value="${appService.mem}" style="width: 60px;" value="256" onblur="isExcess(2)"/> -<input
						type="text" name="maxMem" id="maxMem" value="${appService.maxMem}"
						placeholder="最大值" style="width: 60px;" value="1024" /> MB</td>
					<td width="46" height="30" align="right" valign="middle">描述：</td>
					<td width="150" height="30" align="left" valign="top" rowspan="3"><textarea
							name="description" id="description" cols="" rows=""
							style="height: 50px; border: 1px solid #dadada; width: 145px; resize: none; margin-top:10px;">${appService.description}</textarea></td>

				</tr>

				<tr>
					<td width="76" height="24" align="right" valign="middle"><strong></strong>集群名称：</td>
					<td width="140" height="24" align="left" valign="middle"><span>
							<!-- <select id="cluster_name" name="cluster_name" onchange="clusterChange();"></select> -->
							<select id="cluster_name_config" name="cluster_id" onchange="changeCluster();"></select>
							<!-- ${appService.cluster_name} -->
					</span></td>

					<td height="30" align="right" valign="middle"><strong>*</strong>镜像版本：</td>
					<td height="30" align="left" valign="middle"><span><select
							id="image_version" name="image_version" onchange="imageVersionIsOK()">
								<option value="">请选择</option>
						</select></span></td>
					<td height="30" align="right" valign="middle"><strong> </strong>日志路径：</td>
					<td height="30" align="left" valign="middle"><span><input
							type="text" id="log_dir" name="log_dir"
							value="${appService.log_dir}" type="text" readonly="readonly"/></span></td>
					<td width="46" height="30" align="right" valign="middle">&nbsp;<strong>*</strong>cpu：
					</td>
					<td align="left" valign="middle"><input type="text" name="cpu"
						id="cpu" value="${appService.cpu}" placeholder="均值"
						style="width: 60px;" value="0.5" onblur="isExcess(1);"/> -<input type="text"
						name="maxCpu" id="maxCpu" value="${appService.maxCpu}"
						placeholder="最大值" style="width: 60px;" value="1" /></td>

				</tr>
				<tr>
					<td height="9" colspan="10" align="left" valign="middle"></td>
				</tr>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-bottom: 1px solid #dadada;">
				<tr>
					<td width="584" height="70">
						<table width="582" border="0" cellspacing="0" cellpadding="0">
							<tr valign="middle">
								<td width="108" height="40" align="right" >
									<span style="font-size: 14px; font-weight: bold;">
										<img src="${ctx}/imgs/checked.png" width="14" height="17" style="margin-right:10px;" />是否服务：
									</span>
								</td>
								<td width="110" height="40" align="left">
									<form id="form1" name="form1" method="post" action="">
										<label>
											<input id="RadioGroup1_yes" name="serviceRadio" onclick="displayServiceInfo();" 
												type="radio" style="width: 13px; height: 13px;" value="是" /> 是
										</label>
										<label>
											<input id="RadioGroup1_no" type="radio" onclick="undisplayServiceInfo();" name="serviceRadio"
												value="否" style="width: 13px; height: 13px;" /> 否
										</label><br />
									</form>
								</td>
								<td width="121" height="40" align="left">&nbsp;</td>
								<td width="55" height="40" align="left">&nbsp;</td>
								<td width="55" height="40" align="left">&nbsp;</td>
								<td width="77" height="40" align="left">&nbsp;</td>
								<td width="56" height="40" align="left">&nbsp;</td>

							</tr>
							<tr id="appServicePortTr">

								<td height="24" align="right" valign="middle"><strong
									style="color: #cc3c3b; font-size: 12px; margin-right: 5px;">*</strong>应用端口：</td>
								<td height="24" align="left" valign="middle"><input
									name="containerPort" id="containerPort"
									value="${appService.containerPort}" type="text"
									style="width: 80px; height: 22px; border-radius: 5px; border: 1px solid #dadada; margin-right: 8px;" /></td>
								<td height="24" align="left" valign="middle">是否外部服务：</td>
								<td height="24" align="left" valign="middle" width="50"><input
									id="isExtServiceType" type="checkbox"
									style="width: 15px; height: 15px;" onchange="protocolChange()"
									value="" /></td>
								<td align="left" valign="middle" id="protocol" width="50"
									style="display: none;"><strong
									style="color: #cc3c3b; font-size: 12px; margin-right: 5px;">*</strong>协议：</td>
								<td align="left" valign="middle" id="protocolSelect" width="70"
									style="display: none;"><select style="width: 60px;"
									id="protocolValue"><option value="1">http</option>
										<option value="2">tcp</option></select></td>

								<td width="50" height="30" align="left">&nbsp;</td>
							</tr>
							<tr id="protocolUrlName" style="display: none;">
								<td height="30" align="right" valign="top" id="protocolUrlName">URL：</td>
								<td width="500" colspan="6" id="protocolUrl"
									style="display: none;" height="30" valign="top" align="left">${appService.service_url}</td>
							</tr>
						</table>
						
					</td>
					<td width="596" height="50">
						<table width="500" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="110" height="40" align="right" valign="middle"><span
									style="font-size: 14px; font-weight: bold;"><img
										src="${ctx}/imgs/instanceicon.png" width="15" height="15" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实例数：</span></td>
								<td width="202" height="40" align="left" valign="middle"><form
										id="form2" name="form2" method="post" action="">

										<label> <input name="instRadio" type="radio"
											onclick="displayFixInstInfo();" id="RadioGroup1_fixed"
											style="width: 13px; height: 13px;" value="是" /> 固定
										</label> <label> <input type="radio" name="instRadio"
											onclick="displayDynamicInstInfo();" value="否"
											id="RadioGroup1_dynamic" style="width: 12px; height: 12px;" />
											动态（Beta版）
										</label><br />
										</p>
									</form></td>
								<td width="71" height="40" align="left" valign="middle">&nbsp;</td>
								<td width="124" height="40" align="left" valign="middle">&nbsp;</td>
							</tr>
							<tr id="instScaleTr">
								<td height="24" align="right" valign="middle"><strong
									style="color: #cc3c3b; font-size: 12px; margin-right: 5px;">*</strong>实例数：&nbsp;&nbsp;
								</td>
								<td height="24" align="left" valign="middle"><input
									type="text" id="inst_min" name="inst_min"
									value="${appService.inst_min}" style="width: 50px;" value="0.5" />最小
									-<input type="text" id="inst_max" name="inst_max"
									value="${appService.inst_max}" style="width: 50px;" value="1" />最大</td>
								<td height="24" align="left" valign="middle" width="100"><strong
									style="color: #cc3c3b; font-size: 12px; margin-right: 5px;">*</strong>cpu目标值：</td>
								<td height="24" align="left" valign="middle"><input
									type="text" id="cpu_target" name="cpu_target"
									value="${appService.cpu_target}" style="width: 50px;"
									value="0.5" />%</td>
							</tr>
							<tr id="instNumTr">
								<td height="24" align="right" valign="middle"><strong
									style="color: #cc3c3b; font-size: 12px; margin-right: 5px;">*</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实例数：
								</td>
								<td height="24" align="left" valign="middle"><input
									type="text" id="instance_num" name="instance_num"
									value="${appService.instance_num}" style="width: 60px;"
									value="1" /></td>
							</tr>
						</table>
					</td>
				</tr>

			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding:10px 0 20px 10px; display:block; width:560px;">
      <tr valign="middle" align="left">
        <td width="104" height="30" align="right"><span style="font-size:14px; width:120px; font-weight:bold;">
        <img src="${ctx}/imgs/ip.png" width="10" height="14" style="margin-right:10px;" />是否指定IP：</span></td>
        <td width="120" height="30" align="left"><form id="form1" name="form1" method="post" action="">
            	    
            	      <label>
            	        <input id="RadioGroup2_yes" name="specifyIpRadio" onclick="displaySerivceHostInfo('${appService.hostIp}');" type="radio"  style="width:13px; height:13px;" value="是"  />
            	        是</label>
            	      
            	      <label>
            	        <input id="RadioGroup2_no" type="radio" onclick="undisplayServiceHostInfo();" name="specifyIpRadio" value="否" style="width:13px; height:13px;" checked="checked"/>
            	        否</label>
            	      <br />
          	      </p>
          	    </form></td>
        
      </tr>
      <tr id="appServiceHostTr">
        <td height="20" align="right" valign="middle" width="90" ><strong style="color:#cc3c3b; font-size:12px; margin-right:5px;">*</strong>IP地址：</td>
        <td height="20" align="left" valign="middle"><select name="hostIp" id="hostIps" style="width:140px; height:22px; border-radius:5px; border:1px solid #dadada; margin-right:8px;" onchange="isCheckIp();"></select></td>
        
      </tr>      
    </table>
			<div class="add-service" id="addServiceDiv">
				<div class="add-service-title">
					<a onclick="addIpaasService();"><img
						src="${ctx}/imgs/structure-icon.png" width="22" height="22" /><strong
						style="font-size: 14px; color: #333;">添加基础服务</strong></a>
				</div>
				<c:forEach var="ipaas" items="${appService.ipaas}"
					varStatus="status">
					<div id="ipaasDiv${status.index}">
						<div class="add-service-main">
							<div class="add-service-content">
								<table width="900" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="30">&nbsp;</td>
										<td width="263" height="30" align="left"><strong
											style="color: #cc3c3b; font-size: 12px; margin-right: 5px;">*</strong>服务类型：
											<select id="iPaaSType${status.index}" name="ipaasType"
											onchange="ipaasTypeChange(${status.index});"><option>请选择</option>
												<c:choose>
													<c:when test="${ipaas.type eq '3'}">
														<option value="3" selected="selected">activemq</option>
														<option value="2">redis</option>
														<option value="1">zookeeper</option>
													</c:when>
													<c:when test="${ipaas.type eq '2'}">
														<option value="3">activemq</option>
														<option value="2" selected="selected">redis</option>
														<option value="1">zookeeper</option>
													</c:when>
													<c:otherwise>
														<option value="3">activemq</option>
														<option value="2">redis</option>
														<option value="1" selected="selected">zookeeper</option>
													</c:otherwise>
												</c:choose>
										</select></td>
										<td width="216" height="30"><strong
											style="color: #cc3c3b; font-size: 12px; margin-right: 5px;">*</strong>服务名称：
											<select id="iPaaSName${status.index}" name="ipaasName"
											onchange="ipaasNameChange(${status.index});"
											style="width: 80px;">
										</select> <input id="ipaasServiceId${status.index}" value="${ipaas.id}"
											type="hidden" /></td>
										<td></td>
										<td height="40" align="left">
											<strong style="color: #cc3c3b; font-size: 12px; margin-right: 5px;">*</strong>绑定名称：
											<input type="text" id="ipaasBindStr${status.index}" name="ipaasBindStr" 
												onchange="ipaasNameChange(${status.index});"
												onblur = "checkIpaasBindStr('ipaasBindStr${status.index}');" 
												value="${ipaas.bindStr}" style="width: 80px;" />
										</td>
										<td width="216" height="40" align="center"><input name=""
											type="button" value="删除"
											onclick="deleteIpaasService(${status.index});"
											style="width: 60px; height: 26px; background: #666666; border: none; color: #fff; border-radius: 5px; cursor: pointer;" />
										</td>
									</tr>
								</table>
							</div>
							<div class="add-service-key">
								<table style="width: 900px; border: 0; cellspacing: 0; cellpadding: 0;">
									<tr>
										<td width="50%" height="30" align="center" bgcolor="#e2e2e2"
											style="background: #e2e2e2; border-top: 1px solid #dadada; font-weight: bold;">key</td>
										<td width="50%" height="30" align="center" bgcolor="#e2e2e2"
											style="background: #e2e2e2; border-top: 1px solid #dadada; font-weight: bold;">Value</td>
									</tr>
								</table>
								<table id="ipaasEnvsTab${status.index}" width="900" border="0"
									cellspacing="0" cellpadding="0"></table>
							</div>
						</div>
					</div>
					<c:if test="${status.last}">
						<input name="ipaasCount" id="ipaasCount" type="hidden"
							value="${status.count}" />
					</c:if>
				</c:forEach>
			</div>

			<div
				style="font-size: 14px; color: #1791d5; margin-top: 20px; float: left;"
				id="tips">
				&nbsp; <img src="${ctx}/imgs/tips.png" />&nbsp;运行状态增加或修改实例数为立即生效，修改服务配置为延时生效，最大延时时长为60秒。
			</div>
			<div class="bottom">
				<input id="nextButtonId" type="button" value="下一步"	onclick="showConfigFile();" />
				<input id="saveConf" type="button" value="保存" onclick="saveAppService();" />
			</div>
		</div>

	   <div class="config-file" id="appServiceConfigFile" style="display:none">
		 <%-- <table width="500" border="0" cellspacing="0" cellpadding="0">
	 		<tr valign="middle">
	    			<td width="107" height="20" style="font-size:14px;"> <img src="${ctx}/imgs/tips.png" />&nbsp;配置文件路径为空时不能上传。</td>
	    	</tr>			
         </table> --%>
		 <table width="1000" border="0" cellspacing="0" cellpadding="0">
	 		<tr valign="middle">
		
				<td width="100" height="36" style="font-size:14px;">导入配置文件：</td>
				<td width="2" height="26"></td>
	 		    <td width="590">
	 				<form id="form3" name="form3" enctype="multipart/form-data" method="post">
	 					<label for="configFile"></label>
	 					<input type="file" name="configFile" id="configFile" style="background:none; border:none; width:180px;" accept=".conf,.cfg,.txt,.properties" onchange="fileSelected();"/>
	  				</form>
	  			</td>
	            <td><input id="uploadBtnId" name="" type="button" value="上传" onclick="uploadConfigFile();" style="border-radius:5px;background:#444; width:67px; height:26px; border:none; font-family:'微软雅黑'; font-size:14px; color:#fff; cursor:pointer;" /></td>
	      	</tr>
		  	
	  						
		 </table>
	     <table  border="0" cellspacing="0" cellpadding="0" style="margin:10px 0;">
	     	 <tr>
			    <td width="107" height="30" style="font-size:14px;">配置文件路径：</td>
			    <td width="393"><input id="configFile_path" name="configFilePath" type="text" value="${appService.configFilePath}" readonly="readonly" style=" width:560px; height:25px;" /></td>
			 </tr>
	     </table>
	     <table border="0" cellspacing="0" cellpadding="0">
			 <tr>
			    <td  width="107"  height="30" style="font-size:14px;">配置文件信息：</td>
			     <td><textarea id="configFileInfo" name="configFileInfo" cols="" rows="" style="width:560px; height:80px; resize:none;">${appService.configFileInfo}</textarea></td>
			 </tr>
			 
		 </table>
	     <div class="bottom" style="padding-left:106px;">
		     <input name="" type="button" value="上一步" style="margin-right:10px;" onclick="showInfo();"/>
		     <input name="" type="button" value="下一步" onclick="showEnvs();" />
		     <input name="" type="button" value="保存" onclick="saveAppService();" />
	     </div> 
	   </div>

		<div class="dialog-configure" id="appServiceEnvs"
			style="display: none; padding-left: 0px; width: 1077px;">
			<table width="1077" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="800"><span><form id="form2" name="form2"
								enctype="multipart/form-data" method="post">
								<label for="envsConfFile"></label> <input
									style="width: 198px; cursor: pointer" type="file"
									name="envsConfFile" id="envsConfFile" accept=".properties" />
							</form></span></td>
					<td width="88" align="left"><input type="button"
						onclick="importEnvsFile();" value=""
						style="background:url(${ctx}/imgs/import-Alarm.png) no-repeat;" /></td>
					<td width="88" align="left"><input type="button"
						onclick="addEnvs();" value=""
						style="background:url(${ctx}/imgs/add-Alarm.png) no-repeat;" /></td>
					<td width="88" align="left"><input type="button"
						onclick="deleteEnvs();" value=""
						style="background:url(${ctx}/imgs/Log-delete.png) no-repeat;" /></td>
					<td width="369" align="left"><a
						href="javaScript:exportEnvsFile()" id="exportEnvsFile"
						style="background:url(${ctx}/imgs/exporticon.png) no-repeat; float:left;" /></a></td>
				</tr>
			</table>
			<table id="envsConfTab" width="1077" border="0" cellspacing="0"
				cellpadding="0" style="margin-top: 10px" class="configure-list">
				<tr>
					<td width="230" height="30" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #c0c0c0;">Key</td>
					<td width="580" height="30" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #c0c0c0;">Value</td>
					<td width="180" height="30" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #c0c0c0;">操作</td>

				</tr>
				<c:forEach var="conf" items="${appService.envConfig}"
					varStatus="status">
					<tr id="envsTabTr${status.index}" class="_tr">
						<td width="230" height="30" align="center" valign="middle"><span><input
								type="text" name="appConfKey" id="envsConfKey${status.index}"
								value="${conf.configKey}" style="widht: 200px;" /></span></td>
						<td width="580" height="30" align="center" valign="middle"><span><input
								type="text" name="appConfValue"
								id="envsConfValue${status.index}" value="${conf.configValue}"
								style="widht: 200px;" /></span></td>
						<td width="180" height="30" align="center" valign="middle">
							<center>
								<a href="JavaScript:deleteEnvsConf(${status.index});"><img
									src='/paas/imgs/delete.png' width='22' height='22'
									border='none' /></a>
							</center>
						</td>
					</tr>
				</c:forEach>
			</table>
			 <div style="clear:both"></div>
			<!-- 
	      <table id="envsConfTab" width="1077" border="0" cellspacing="0" cellpadding="0" class="configure-list">		  	 
		  </table> -->
			<div class="bottom" style="margin-left: 20px;">
				<input type="button" id="upinfo" onclick="showConfigFile();" value="上一步" />
				<input type="button" value="保存" id="sub" onclick="saveAppService();" />
			</div>
		</div>
	</div>
</form>
