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
	src="${ctx}/js/application/detailstransfer.js"></script>
</head>
<style>
html,body{ overflow-x:hidden;}
</style>
<body>
	<input type="hidden" id="appId" value="<%=request.getParameter("appId")%>">
	<div class="Establish" style="height:18px;">
		<div class="Establish-left">
			<div style="float:left; width:900px; font-size: 14px;font-weight: bold;">应用名称：<font style="font-size: 14px;font-weight: bold;"><%=request.getParameter("appName")%></font></div>
		</div>

	</div>
	<div class="main-Content">
		<table cellspacing="0" style="" id="appList">
		</table>
	</div>
	<div id="pagerBar"></div>

</body>
</html>
