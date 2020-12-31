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
<script type="text/javascript" src="${ctx}/js/logmanager/syslog.js"></script>

<script type="text/javascript">
	
</script>
<!-- 单元格内容换行 -->
<style>
/*.ui-jqgrid tr.jqgrow td {
	white-space: normal !important;
	word-wrap:break-word;
}*/
/*.ui-jqgrid-bdiv{
	overflow: scroll !important;
}*/
</style>


<div class="log-main searchBox">
	<table width="400" border="0" cellspacing="0" cellpadding="0"
		class="log-main-input">
		<tr>
			<td width="76" valign="middle">时间区间:</td>
			<td width="324" valign="middle"><input type="text"
				style="border:1px solid #ddd;background:url(${ctx}/css/lhgcalendar/images/iconDate.gif) center right no-repeat #ffffff;cursor:pointer;font:12px tahoma,arial;height:21px;width:150px;"
				id="insertTimeStart" name="insertTimeStart" />-<input type="text"
				id="insertTimeEnd" name="insertTimeEnd"
				style="border:1px solid #ddd;background:url(${ctx}/css/lhgcalendar/images/iconDate.gif) center right no-repeat #ffffff;cursor:pointer;font:12px tahoma,arial;height:21px;width:150px;" /></td>
		</tr>
	</table>
	<table width="800" border="0" cellspacing="0" cellpadding="0"
		class="log-main-input">
		<tr>
			<td width="200" height="30" valign="bottom">操作结果:<select
				id="operateResult" name="operateResult" style="margin-left: 15px;">
					<option value="" selected="selected">全部</option>
					<option value="1">成功</option>
					<option value="0">失败</option>
			</select></td>
			<td width="190" height="30" valign="bottom">操作用户:<select
				name="operator" id="operator" style="margin-left: 5px;">
			</select></td>
			<td width="153" height="30" valign="bottom">关键字:<input
				name="keyWord" placeholder="支持模糊查询" id="keyWord" type="text"
				maxlength="120" onkeyup="value=value.replace(/[^A-Za-z0-9\-\_\/\.\u4e00-\u9fa5]/g,'')"
				style="width: 100px; height: 22px; border: 1px solid #dadada; background: #fff; border-radius: 5px;margin-left: 5px;" /></td>
			<td width="68" height="30" valign="bottom"><input name=""
				type="submit" value=""
				style="background:url(${ctx}/imgs/queryLog.png) no-repeat; width:68px;height:24px;border:none;cursor:pointer;"
				onclick="javaScript:reloadResult()" /></td>
			<td width="68" height="30" valign="bottom"><input name=""
				type="submit" value=""
				style="background:url(${ctx}/imgs/reseticon.png) no-repeat; width:68px;height:24px;border:none;cursor:pointer;"
				onclick="javaScript:init()" /></td>
		</tr>
	</table>
</div>
<div class="main-Content">
	<div class="content-tab">
		<table cellspacing="0" id="sysLogList" ></table>
		<div id="pagerBar"></div>
	</div>

</div>


