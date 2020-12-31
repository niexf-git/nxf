<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/dailog.css" rel="stylesheet" />
<link href="${ctx}/css/ztree/zTreeStyle.css" rel="stylesheet" />
<link href="${ctx}/css/ztree/demo.css" rel="stylesheet" />

<script src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/ztree/jquery.ztree.core-3.5.js"></script>
<script src="${ctx}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script src="${ctx}/js/systemmanager/user.js"></script>

<body onload="loadTree()">
	<form id="submitForm" method="post">
		<input type="hidden" value="${param.id}" name="id" id="id" />
		<div class="dialog-main">
			<div id="main-four" style="font-size: 12px; font-family: '微软雅黑';">
				用户名称： <strong style="font-size: 14px; color: #007abb; font-family: '微软雅黑';">
					<%
						String x = request.getParameter("loginName");
						out.print(x);
					%>
				</strong>
				<span id="userOperType"></span>
			</div>
			<div id="js-main-four">
				<span style="font-size: 12px; font-family: '微软雅黑';">拥有角色：</span>
				<div class="zTreeDemoBackground left" style="height: 160px">
					<ul style="width: 275px; height: 150px" id="tree" class="ztree"></ul>
				</div>
			</div>
			<div class="bottom">
				<input name="" type="button" value="确定" onclick="updateUserRole()" />
			</div>
		</div>
	</form>
</body>