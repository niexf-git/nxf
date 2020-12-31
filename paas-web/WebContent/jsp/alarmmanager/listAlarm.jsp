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
	<link rel="stylesheet" type="text/css" href="${ctx}/js/appeasyui/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/js/appeasyui/themes/icon.css" />
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/appeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lhgcalendar.min.js"></script>
<link href="${ctx}/css/lhgcalendar/lhgcalendar.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${ctx}/js/alarmmanager/alarm.js"></script>

<!-- 单元格内容换行 -->
<style>
.ui-jqgrid tr.jqgrow td {
	white-space: normal !important;
	word-wrap:break-word;
}
html,body{ overflow-x:hidden;}
.ui-jqgrid tr.jqgrow td {font-weight: normal; overflow: hidden;  height: 37px; border:none;border-style:none}
</style>
<script type="text/javascript">
	$(function() {
		var appNames = $("#appText", window.parent.document).html();
		var operTypes = $("#operTypeText", window.parent.document).html();
		if (appNames != "全部") {
			document.getElementById("appNamess").hidden=true;
		}
		if (operTypes != "全部") {
			document.getElementById("operTypess").hidden=true;
		}
		if (appNames != "全部" && operTypes != "全部") {
			document.getElementById("tables").hidden=true;
		}
		initTime();
		initAppName();
		initOperType();

	});

	function initOperType() {
		$.ajax({
			type : "POST",
			contentType : "application/json;utf-8",
			dataType : "json",
			url : "/paas/alarm/queryOperTypeList.action",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					var json = eval(result['resultMessage']);
					$.each(json, function() {
						$("#operType").append(
								"<option value=" + this.value + ">" + this.text
										+ "</option>\r\n");
					});
				}
			}
		});

	}
	function initAppName() {
		$.ajax({
			type : "POST",
			contentType : "application/json;utf-8",
			dataType : "json",
			url : "/paas/alarm/queryAppList.action",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					var json = eval(result['resultMessage']);
					$.each(json, function() {
						$("#appName").append(
								"<option value=" + this.value + ">" + this.text
										+ "</option>\r\n");
					});
				}
			}
		});

	}
	function initTime() {
		$.ajax({
			type : "POST",
			contentType : "application/json;utf-8",
			dataType : "json",
			url : "/paas/alarm/initQueryDateTime.action",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					var dateTime = $.parseJSON(result['resultMessage']);
					$('#insertTimeStart').val(dateTime['minDate']);
					$('#insertTimeEnd').val(dateTime['maxDate']);
					$('#insertTimeStart').calendar({
						format : 'yyyy-MM-dd HH:mm:ss'
					});
					$('#insertTimeEnd').calendar({
						format : 'yyyy-MM-dd HH:mm:ss'
					});
				}
			}
		});
	}

	function init() {
		initTime();
		$("#appName > option:first").attr("selected", true);
		$("#operType > option:first").attr("selected", true);
		$('#keyWord').val("");
	}
</script>


<div class="log-main searchBox">
	<table width="1000" border="0" cellspacing="0" cellpadding="0"
		class="log-main-input">
		<tr>
			<td width="76" valign="middle">时间区间：</td>
			<td width="320" valign="middle"><input type="text"
				style="border:1px solid #ddd;background:url(${ctx}/css/lhgcalendar/images/iconDate.gif) center right no-repeat #ffffff;cursor:pointer;font:12px tahoma,arial;height:23px;width:150px;"
				id="insertTimeStart" name="insertTimeStart" />-<input type="text"
				id="insertTimeEnd" name="insertTimeEnd"
				style="border:1px solid #ddd;background:url(${ctx}/css/lhgcalendar/images/iconDate.gif) center right no-repeat #ffffff;cursor:pointer;font:12px tahoma,arial;height:23px;width:150px;" /></td>
			<td width="200" valign="middle">实例名称：<input onkeyup="value=value.replace(/[^A-Za-z0-9\-\_\/\.]/g,'')"
				name="keyWord" placeholder="支持模糊查询" id="keyWord" type="text"
				maxlength="120"
				style="width: 120px; height: 22px; border: 1px solid #dadada; background: #fff; border-radius: 5px;" /></td>
			<td width="83" height="24" align="left" valign="bottom"><input
				name="" id="search" type="button" value=""
				style="background:url(${ctx}/imgs/queryLog.png) no-repeat; width:68px; height:24px; border:none;cursor:pointer;"
				onclick="javaScript:reloadResult()" /></td>
			<td height="24" valign="bottom"><input name=""
				type="submit" value=""
				style="background:url(${ctx}/imgs/reseticon.png) no-repeat; width:74px; width:68px; height:24px; border:none;cursor:pointer;"
				onclick="javaScript:init()" /></td>
		</tr>
	</table>
	<table width="800" border="0" cellspacing="0" cellpadding="0"
		class="log-main-input70" id="tables">
		<tr>

			<td width="250" height="30" valign="bottom" id="appNamess">应用名称：<select
				name="appName" id="appName" style="margin-left: 5px;">
			</select></td>
			<td width="250" height="30" valign="bottom" id="operTypess">操作类型：<select
				name="operType" id="operType" style="width: 79px; margin-left: 5px;"></select></td>
			<td width="86" height="30" valign="bottom"></td>
			<td width="334" height="30" valign="bottom"></td>
		</tr>
	</table>
</div>

<div class="main-Content">
	<div class="content-tab">
		<table cellspacing="0" id="alarmList"></table>
		<div id="pagerBar"></div>
	</div>

</div>
