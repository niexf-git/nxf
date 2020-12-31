<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

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
	src="${ctx}/js/application/application.js"></script>
</head>
<style>
html,body{ overflow-x:hidden; font-size:12px;}
#changeColor{background:#656565; border-radius:0px 5px 5px 0px; color:#fff;}
#changeColora{background:#656565; border-radius:5px 0px 0px 5px; color:#fff;}
.ui-jqgrid tr.jqgrow td {font-weight: normal; overflow: hidden;  height: 37px; border:none;border-style:none}
</style>
<body>
	<input type="hidden" id="url" value="/paas/application/queryAppList.action">
	<input type="hidden" id="name" value="appName">
	<input type="hidden" id="gridId" value="appList">
	<c:set var="isProduct" value="${currentContext}" scope="session"></c:set>
	<input type="hidden" id="isProduct" value="${isProduct}"/>
	<div class="Establish">
		<div class="Establish-left">
			<div class="name70">应用名称：</div>
			<div class="queryService">
				<input name="appName" id="appName" type="text" placeholder="支持模糊查询" onkeyup="value=value.replace(/[^A-Za-z0-9\-\_\/\.]/g,'')" />
				<span>
					<input name="" type="button" value="" id= "search" onclick="javascript:reloadResult()" />
				</span>
			</div>
			<div class="input3">
				<form action="/paas/application/queryAppInfo.action" method="post"></form>
				<img src="${ctx}/imgs/createServie.png" width="11" height="11" />
				<a href="javascript:void(0);" onclick="openAddAppNew();">新增应用</a>
			</div>
			<div class="recovery" style="display:none;">
				<ul>
					<li onclick="disasterRecoveryAll(this)">容灾</li>
					
				</ul>
			</div>
			<div class="applist-input3">
				<form action="/paas/application/queryAppInfo.action" method="post"></form>
				<a href="javascript:void(0);" onclick="importApp();">导入数据</a>
			</div>
		</div>

	</div>
	<div class="main-Content">
		<table cellspacing="0" style="" id="appList">
		</table>
	</div>
	<div id="pagerBar"></div>

</body>
</html>
