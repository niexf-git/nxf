<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/dailog.css" rel="stylesheet" />
<link href="${ctx}/css/ztree/demo.css" rel="stylesheet" />
<link href="${ctx}/css/ztree/zTreeStyle.css" rel="stylesheet" />

<script src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/ztree/jquery.ztree.core-3.5.js"></script>
<script src="${ctx}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script src="${ctx}/js/systemmanager/role.js"></script>

<style type="text/css">
	*{
		margin:0px;padding:0px;font-size:12px;
	}
	input{  appgroup
		width:100px;height:20px;border:1px solid #ccc;
	}
</style>

<body onload="loadPerTree()">
	<form id="submitForm" method="post">
		<div class="User-dialog">
			<div class="User-dialog-main">
				<div id="main-four" style="font-size: 12px; font-family: '微软雅黑';">
					<strong>*</strong>角色名称： <input type="text" id="roleName"
						name="roleName" maxlength="15" required="required" />
				</div>
				<div id="main-four" style="font-size: 12px; font-family: '微软雅黑';">
					&nbsp;&nbsp;角色描述： <input type="text" maxlength="120" id="description"
						name="description" />
				</div>
				<div id="js-main-four">
					<span style="font-size: 12px; font-family: '微软雅黑';">&nbsp;&nbsp;操作权限：&nbsp;&nbsp;&nbsp;</span>
					<div class="zTreeDemoBackground left" style="height: 160px">
						<ul style="width: 150px; height: 160px" id="oprTree" class="ztree"></ul>
					</div>
				</div>
				<div class="bottom">
					<input name="" type="button" value="确定" onclick="createRole()" />
				</div>
			</div>
		</div>
	</form>
</body>