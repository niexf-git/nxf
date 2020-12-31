<%@page import="com.cmsz.paas.web.constants.Constants"%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>中国移动通信 - 深圳云计算PAAS管理平台</title>
<link href="${ctx}/css/login.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0">
	<h2 align="center">
	<c:choose>
		<c:when test="${loginMessage!=null}">${loginMessage}</c:when>
	</c:choose>
	</h2>
</body>
</html>
