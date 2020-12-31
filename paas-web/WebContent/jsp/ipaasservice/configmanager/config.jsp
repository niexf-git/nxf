<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/appmanager/configmanager/config.js"></script>
<%
String errorInfo = (String)request.getAttribute("errorMsg");// 获取错误属性
if(errorInfo != null) {
%>
<script type="text/javascript">
parent.parent.alertError("<%=errorInfo%>");

</script>	
<%
}
%>
<form id ="configForm">
<div class="pz">
		<h1 style="font-size:14px;margin-bottom:15px;color:#1791d5;display:none;" id="restartconfig" >&nbsp;
			<img src="${ctx}/imgs/tips.png"/>&nbsp;配置未生效，可重启应用生效</h1>
	<div class="pz-left">
		<div class="pz-title">
			<input name ="appId" value="${app.id }" type="hidden" />
			<input id="appConfigId" name ="id" value="${app.id }" type="hidden" />
			<input id="appLogDir" name ="log_dir" value="${app.log_dir}" type="hidden" />
			<input id="appStartCommand" name ="start_command" value="${app.start_command}" type="hidden" />
			<input id="configEffect" name ="config_effect" value="${app.config_effect }" type="hidden" />			
			<input id="appClusterId" value='${app.cluster_id}' type="hidden" />
			<input id="serviceFlag" name="serviceFlag" value='${app.serviceFlag}' type="hidden" />
			<input id="extServiceType" name="extServiceType" value='${app.extServiceType}' type="hidden" />
			<img src="${ctx}/imgs/fz.png" width="16" height="17">基础配置</img><strong>*号为必填项</strong>
		</div>
		<ul>
			<li><strong>*</strong>&nbsp;&nbsp;&nbsp;&nbsp;CPU:<input
				name="cpu" type="text" value="${app.cpu}" id="cpu" placeholder="均值" style="width:62px;height:18px;border:1px solid #DADADA;border-radius:5px;background:#fff;">
				-<input name="maxCpu" type="text" value="${app.maxCpu}" id="maxCpu" placeholder="最大值" style="width:62px;height:18px;border:1px solid #DADADA;border-radius:5px;background:#fff;margin-left:5px;">Core</li>
			<li><strong>*</strong>&nbsp;内&nbsp;&nbsp;&nbsp;存:<input
				name="mem" type="text" value="${app.mem }"  id="mem" placeholder="均值" style="width:62px;height:18px;border:1px solid #DADADA;border-radius:5px;background:#fff;">
				-<input name="maxMem" type="text" value="${app.maxMem }"  id="maxMem" placeholder="最大值" style="width:62px;height:18px;border:1px solid #DADADA;border-radius:5px;background:#fff;margin-left:5px;">MB</li>
			<li><strong>*</strong>&nbsp;实例数:<input name="instance_num"
				type="text" value="${app.instance_num }" id="instance_num"></li>
		</ul>
	</div>
	<div class="pz-right">
		<div class="pz-title">
			<div style="float: left; width: 90px;">
				<img src="${ctx}/imgs/sffw.png" width="17" height="17">是否服务: 
			</div>
			<div>
				<form id="form1" name="form1" method="post" action="">
					<label><input type="radio" name="ceffect" value="1" id="ceffect_1" onclick="displayServiceInfo();">是</label>
					<label><input type="radio" name="ceffect" value="0" id="ceffect_2" onclick="undisplayServiceInfo();">否</label>
				</form>
			</div>
		</div>
		<div class="pz-main">
			<ul>
				<li><strong>*</strong>应用端口:<input name="containerPort" id="containerPort" value="${app.containerPort }" type="text"/></li>	
				<li class="service-main">&nbsp;&nbsp;&nbsp;是否外部服务:<input name="serviceType" id="serviceType"  type="checkbox"/></li>		
				<li id="serviceUrl" ><strong>&nbsp;&nbsp;</strong>服务链接:&nbsp;&nbsp;<a href="${app.serviceUrl}" style="color:#0088A8">${app.serviceUrl}</a></li>			
			</ul>
		</div>		
	</div>
	
	<div style="font-size:14px;color:#1791d5; margin-top:20px;float:left;" id="tips" >&nbsp;
			<img src="${ctx}/imgs/tips.png"/>&nbsp;运行状态增加或修改实例数为立即生效，修改服务配置为延时生效，最大延时时长为60秒。</div>
	<div class="next">&nbsp;&nbsp;<input onClick="saveConfig();" id="saveConfigButton" type="button" value="保存"/></div>
</div>
</form>
