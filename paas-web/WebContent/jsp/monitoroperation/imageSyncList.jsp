<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/appeasyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/appeasyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/appeasyui/themes/icon.css" />

<script type="text/javascript" src="${ctx}/js/lhgcalendar.min.js"></script>
<link href="${ctx}/css/lhgcalendar/lhgcalendar.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/js/monitoroperation/imageSync.js"></script>
<div  style="padding-left:20px;padding-top:10px;padding-bottom:7px; border-bottom:1px solid #dadada;">
<h1 style="font-size:14px; font-weight:normal; float:left; margin-right:50px;">加载公共镜像到主仓库</h1>
<input name="" type="submit" value="加载镜像" onclick="javaScript:loadPublicImages()" style="width:80px; height:24px; background:#333;border-radius:5px; border:1px solid #000; color:#fff; cursor:pointer;" /></div>
<div class="Monitor-query" style="padding-top:10px;">
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
		<span>应用名称：</span><select id="apps"></select>
	</div>
	<div style="width: 220px; float: left;">
		<span>目标数据中心：</span><select id="dataCenter"></select>
	</div>
	<div style="width: 220px; float: left;">
		<span>状态：</span><select id="status">
			<option value="">全部</option>
			<option value="pending">等待中</option>
			<option value="running">进行中</option>
			<option value="error">错误</option>
			<option value="retrying">重试中</option>
			<option value="stopped">已终止</option>
			<option value="finished">已完成</option>
			<option value="canceled">已取消</option>
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
		<table cellspacing="0" id="imageSyncList"></table>
		<div id="pagerBar"></div>
	</div>
</div>
