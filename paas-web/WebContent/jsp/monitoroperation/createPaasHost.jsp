<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/dailog.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script>
<script type="text/javascript" src="${ctx}/js/monitoroperation/createPaasHost.js"></script>
<script type="text/javascript" src="${ctx}/js/monitoroperation/host.js"></script>
<body>
<form id="addNodeHostForm" method="post">
	<input id="clusterId" name="clusterId" value=""	type="hidden" />
	<div class="dialog">
	    <div class="dialog-main" style="background:none;">
	      <div id="creat-data">
	   		<strong style="float:left; padding-top:5px;">*</strong><span style="float:left; padding-top:5px;">请选择部署方案：</span>
	   		<select id="deploySchemeId" name="" onchange="queryDeployComponentList();"></select>
	      </div>
	      
	      <div class="date-content" style="width:860px;">
	      	<table width="810px" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px">
				<tr>
					<td width="40" height="26" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">节点</td>
					<td width="200" height="26" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">组件名称</td>
					<td width="193" height="26" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">描述</td>
					<td width="110" height="26" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">ip地址</td>
	                <td width="100" height="26" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">root密码</td>
						<td width="170" height="26" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;"></td>
				</tr>
		   </table>
	       <table id="hostsConfTab" width="810" border="0" cellspacing="0" cellpadding="0" class="configure-list">
		   </table>
	      </div>
	      
	      <div class="bottom" style="padding-left:350px;">
	      	<input name="" type="button" value="确定" onclick="deploy();" />
	      </div> 
	      <div id="loadImg" style="display:none"><img alt="" src="${ctx}/imgs/load.gif"></img></div>
		</div>
	</div>
</form>
</body>