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
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/appeasyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/appeasyui/themes/icon.css" />
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/appeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lhgcalendar.min.js"></script>
<link href="${ctx}/css/lhgcalendar/lhgcalendar.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${ctx}/js/monitoroperation/sysAlarm.js"></script>
<style>
body,html {
	overflow-x: hidden;
}
</style>
<div class="Monitor-query" style="border-bottom: 1px solid #dadada; padding: 0px; height: 35px; width:100%;">
	<table width="680" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57" height="35" align="center" style="border-right:1px solid #dadada; background:#ddd;">告警条件</td>
			<td width="15" align="right">cpu：</td>
			<td width="72"><input id="cpu" type="text" style="width: 50px;"
				value="${alarmCondition.cpu}" /> %</td>
			<td width="30">内存：</td>
			<td width="72"><input type="text" name="textfield" id="memory"
				style="width: 50px;" value="${alarmCondition.memory}" />%</td>
			<td width="30">磁盘：</td>
			<td width="72"><input id="filesystem" type="text"
				style="width: 50px;" value="${alarmCondition.filesystem}" />%</td>
			<td width="80" align="center"><input id="edit" type="button"
				onclick="edit()" value="保存"
				style="width: 60px; height: 24px; background: #333; border-radius: 5px; border: 1px solid #000;; color: #fff; cursor: pointer;" /></td>

		</tr>
	</table>

</div>
<div class="Monitor-query">
	<div style="width: 620px; float: left;">
		<span>开始时间：</span><input type="text"
			style="border:1px solid #ddd;background:url(${ctx}/css/lhgcalendar/images/iconDate.gif) center right no-repeat #ffffff;cursor:pointer;font:12px tahoma,arial;height:23px;width:150px;"
			id="insertTimeStart" name="insertTimeStart" />-<input type="text"
			id="insertTimeEnd" name="insertTimeEnd"
			style="border:1px solid #ddd;background:url(${ctx}/css/lhgcalendar/images/iconDate.gif) center right no-repeat #ffffff;cursor:pointer;font:12px tahoma,arial;height:23px;width:150px;" />
	</div>

</div>
<div class="Monitor-query" style="padding-top: 0;">
	<div style="width: 220px; float: left;">
		<span>数据中心：</span><select id="dataCenter"
			onchange="dataCenterChange()"></select>
	</div>
	<div style="width: 190px; float: left;">
		<span>集群：</span><select id="cluster" onchange="clusterChange()">
			<option value="">请选择</option>
		</select>
	</div>
	<div style="width: 220px; float: left;">
		<span>主机：</span><select id="node">
			<option value="">请选择</option>
		</select>
	</div>
	<div>
		<input name="" id="search" type="button" value=""
			style="background:url(${ctx}/imgs/queryLog.png) no-repeat; width:68px; height:24px; border:none;cursor:pointer;"
			onclick="javaScript:reloadResult()" />
	</div>
</div>

<div class="Monitor-right-tab">
	<div class="content-tab">
		<table cellspacing="0" id="alarmList"></table>
		<div id="pagerBar"></div>
	</div>
</div>




