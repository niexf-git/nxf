<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/dailog.css" rel="stylesheet" />

<script src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/imagemanager/privateimage/privateimageversion.js"></script>

<%
	String privateImageId = request.getParameter("privateImageId");
	String privateImageVersionId = request.getParameter("privateImageVersionId");
	String version = request.getParameter("version");
%>

<form id="submitForm" method="post">
	<input type="hidden" id="privateImageId" name="privateImageId" value="<%=privateImageId %>" />
	<input type="hidden" id="privateImageVersionId" name="privateImageVersionId" value="<%=privateImageVersionId %>" />
	<div class="dialog">
		<div class="dialog-main">
			<div id="main-four70">
				<strong>*</strong>版本号：
				<!--  <input type="hidden" id="currentVersion" name="currentVersion" value="<%=version %>" /> -->
				<input type="text" id="version" name="version" value="v1.0.0" />
			</div>
			<div class="bottom">
				<input type="button" value="发布生产" onclick="publish2Product()" />
			</div>
		</div>
	</div>
</form>
