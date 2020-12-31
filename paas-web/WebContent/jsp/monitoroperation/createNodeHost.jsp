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
<script type="text/javascript" src="${ctx}/js/monitoroperation/createNodeHost.js"></script>
<script type="text/javascript" src="${ctx}/js/monitoroperation/host.js"></script>

<body>
<form id="addNodeHostForm" method="post">
	<input id="clusterId" name="clusterId" value=""	type="hidden" /> 
	<input id="deployId"  name="" value=""	type="hidden" /> 
	<div class="dialog">
	    <div class="dialog-configure">
	    	<table width="560" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="100" height="50" valign="top" style="font-size:16px; ">部署组件名称:</td>
	            <td width="400" align="left" valign="top" style="padding-top:2px; font-size:14px;"><label id="components"></label></td>
	          </tr>
	        </table>
	        <table width="300" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            
	            <td width="88" align="left"><input type="button"  value="添加" onclick="addOneTrEnv();"  style="background:#333; color:#fff;"/></td>
	            <td width="209" align="left"><input type="button"  value="删除" onclick="deleteAppendTrEnvs();" style="background:#333; color:#fff;"/></td>
	          </tr>
	        </table>
		    <table width="555px" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px">
				<tr>
					<td width="110" height="26" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">ip地址</td>
					<td width="200" height="26" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">root密码</td>
					<td width="70" height="26" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">操作</td>
				</tr>
			 </table>
			 <div class="host-list">
	         <table id="hostsConfTab" width="555" border="0" cellspacing="0" cellpadding="0" >
				<tr id="hostsTabTr0">
				    <td width="246" height="30" valign="middle"><input id="ip" name="hostList[0].hostIP" type="text" /></td>
				    <td width="238" height="30" valign="middle"><input id="password" name="hostList[0].password" type="text" onfocus="this.type='password'"/></td>
				    <td width="71" height="30" valign="middle"></td>
				    <td width="10" height="30" valign="middle"><input name="hostList[0].deployId" id="deployId" type="hidden" /></td>
				</tr>
			 </table>
			 </div>
			 <div class="bottom">
				<input name="" type="button" value="确定" style="margin-right:10px;" onclick="deploy();"/>
			 </div> 
			 <div id="loadImg" style="display:none"><img alt="" src="${ctx}/imgs/load.gif"></img></div>
	  	</div>
	</div>
</form>
</body>