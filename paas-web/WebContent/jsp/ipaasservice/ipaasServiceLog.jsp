<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/css/lhgcalendar/lhgcalendar.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/css/lhgcalendar/lhgcalendar.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lhgcalendar.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/logmanager/ipaasServiceAppLog.js"></script>
<script type="text/javascript"
	src="${ctx}/js/logmanager/ipaasServiceStdLog.js"></script>
<script type="text/javascript">
	var serviceId;
	function init() {
		$('#sdate').calendar({
			format : 'yyyy-MM-dd HH:mm:ss'
		});
		$('#edate').calendar({
			format : 'yyyy-MM-dd HH:mm:ss'
		});
		$('#startTime').calendar({
			format : 'yyyy-MM-dd HH:mm:ss'
		});
		serviceId = getQueryString('id');
	}
	function reset() {
		$('#name').val('');
		$('#sdate').val('');
		$('#edate').val('');
		$('#sdate').calendar({
			format : 'yyyy-MM-dd HH:mm:ss'
		});
		$('#edate').calendar({
			format : 'yyyy-MM-dd HH:mm:ss'
		});
		$('#startTime').calendar({
			format : 'yyyy-MM-dd HH:mm:ss'
		});
		//initTime();
	}

	function show(s) {
		var appCheck = document.getElementById("appLogR").checked;
		var stdCheck = document.getElementById("stdLogR").checked;

		if (appCheck) {
			document.getElementById("std").style.display = "none";
			document.getElementById("app").style.display = "";
			document.getElementById("query").style.display = "";
			queryApp();
		}
		if (stdCheck) {
			document.getElementById("std").style.display = "";
			document.getElementById("app").style.display = "none";
			document.getElementById("query").style.display = "none";
			queryInstance();
			initDate();
		}
	}
</script>
<style type="text/css" media="screen">
.box {
	width: 1000px;
	height: 370px;
	min-height: 200px;
	float: left;
	background: #333;
	overflow: auto;
	overflow-x: hidden;
	color: #fff;
	margin-left: 10px;
}

.box-main {
	float: left;
	width: 700px;
	width: 980px;
	height: auto;
	line-height: 22px;
	color: #fff;
}

.ui-jqgrid tr.jqgrow td {
	white-space: normal !important;
	word-wrap:break-word;
}
</style>
<div class="ServiceLog">
	<span>日志类型：</span> <label> <input type="radio" name="radio"
		id="appLogR" checked="checked" onclick="show('a')" />应用日志
	</label> <label> <input type="radio" name="radio" id="stdLogR"
		onclick="show('b')" />标准输出日志
	</label>
</div>
<div class="ServiceLog-title" id="query">
	<div style="color: #000; float: left;">&nbsp;文&nbsp;件&nbsp;名：</div>
	<div style="float: left;">
		<input type="text" id="name" name="name" placeholder="支持模糊查询"
			style="width: 100px; height: 20px; border: 1px solid #dadada; border-radius: 5px;"
			maxlength="120" onkeyup="value=value.replace(/[^A-Za-z0-9\-\_\/\.\u4e00-\u9fa5]/g,'')"></input>
	</div>
	<div style="color: #000; float: left;">&nbsp;&nbsp;更新时间：</div>
	<div style="float: left;">
		<input class="runcode" type="text" id="sdate" name="sdate"
			style="height: 18px"></input><span style="color: #000;">--</span><input
			class="runcode" type="text" id="edate" name="edate"
			style="height: 18px"> </input>
	</div>
	<div class="ServiceLog-query">
		<a href="javaScript:reloadResult()"></a>
	</div>
	<div class="ServiceLog-reset">
		<a href="javaScript:reset()"></a>
	</div>
</div>
<div class="main-Content" id="app">
	<span style="color: #000; font-size: 14px;">当前路径：</span><label
		id="path" style="font-size: 15px"></label>
	<div class="content-tab" style="margin-top: 10px;">
		<table cellspacing="0" id="appLogList"></table>
		<div id="pagerBar"></div>
	</div>
</div>

<div class="log-main" style="display: none; width: 100%; height: 100%"
	id="std">
	<table width="900" border="0" cellspacing="0" cellpadding="0"
		class="log-main-input">
		<tr>
			<td width="250" height="50" valign="middle">实例名称： <select
				name="instanceId" id="instanceId" onchange=""></select>
			</td>
			<td width="284" height="50" valign="middle">开始时间： <input
				class="runcode" type="text" id="startTime" name="startTime"
				style="height: 20px"></input>
			</td>

			<td width="120" height="50" valign="middle"><span><input
					name="" type="button" value="" onclick="queryStdLog();" /></span></td>
			<td width="121" height="50" valign="middle"><a
				href="javaScript:downloadStdFile();" id="downloadFileId"></a></td>
			<td width="189" height="50" valign="middle"><img id="statusId"
				src="${ctx}/imgs/IconStop.png" width="25" height="25"
				onclick="changeStatus();" style="cursor: pointer;" /></td>
		</tr>
	</table>
	<table width="1079px" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="68px;" valign="top">日志内容:</td>
			<td width="790">
				<div class="box" id="mainDiv"></div>
			</td>
		</tr>
	</table>
</div>
