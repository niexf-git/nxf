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
<script type="text/javascript" src="${ctx}/js/utils.js"></script>
<script type="text/javascript"
	src="${ctx}/js/appeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/monitoroperation/componentList.js"></script>
	
<%
	String minionName = request.getParameter("minionName");
%>
	<style>
.ui-jqgrid .ui-jqgrid-bdiv {position: static; margin: 0em; padding:0; overflow-y: auto; overflow-x:hidden; max-height:380px;  text-align:left; widht:100%;}
html,body{ position: static; }
</style>
<input id="minionName" name ="minionName" type="hidden" value="<%=minionName%>"/>
<div class="input3" style="background:#ff6633; margin:15px;">
		<img src="${ctx}/imgs/iconRefresh.png" width="11" height="11" />
			<span><a href="javascript:reloadResult();">刷新列表</a></span>
		</div>
<div class="Monitor-right-tab">
	<div class="content-tab">
		<table cellspacing="0" id="componentList"></table>
		<div id="pagerBar"></div>
	</div>
</div>




