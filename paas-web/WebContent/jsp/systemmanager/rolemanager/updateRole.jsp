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
<%
	String errorInfo = (String) request.getAttribute("errorMsg"); // 获取错误属性
	if (errorInfo != null) {
%>
<script type="text/javascript">
	parent.parent.alertError("<%=errorInfo%>"); // 弹出错误信息
</script>
<%
	}
%>

<body onload="rolePerTree()">
	<form id="submitForm" method="post">
		<input type="hidden" value="${role.id}" name="role.id" id="roleId" />
		<div class="User-dialog">
			<div class="User-dialog-main">
				<div id="main-four70" style="font-size: 12px; font-family: '微软雅黑';">
					<strong>*</strong>角色名称： <input type="text" id="roleName"
						name="role.roleName" value="${role.roleName}" maxlength="15" required="required" />
				</div>
				<div id="main-four70" style="font-size: 12px; font-family: '微软雅黑';">
					&nbsp;&nbsp;角色描述： <input type="text" id="description"
						name="role.description" maxlength="120" value="${role.description}" />
				</div>
				<div id="js-main-four">
					<span style="font-size: 12px; font-family: '微软雅黑';">&nbsp;&nbsp;操作权限：&nbsp;&nbsp;&nbsp;</span>
					<div class="zTreeDemoBackground left" style="height: 160px">
						<ul style="width: 150px; height: 160px" id="oprTree" class="ztree"></ul>
					</div>
				</div>
				<div class="applist-boxs">
			   		<h1 style="font-size: 12px; font-family: '微软雅黑';">应用权限：</h1>
			   		<table width="550" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="57" height="30" bgcolor="#dadada">&nbsp;</td>
							<td width="85" height="30" align="middle" valign="middle" bgcolor="#dadada">序号</td>
							<td width="251" height="30" align="middle" valign="middle" bgcolor="#dadada">应用</td>
							<td width="155" height="30" align="middle" valign="middle" bgcolor="#dadada">操作类型</td>
						</tr>
						<c:forEach items="${appPerList}" var="appPer" varStatus="status">
							<tr>
								<td width="57" height="30">&nbsp;</td>
								<td width="85" height="30" align="middle" valign="middle">${status.index+1 }</td><!-- status.index是从0开始的 -->
								<td width="251" height="30" align="middle" valign="middle">${appPer.permissionName }</td>
								<c:choose>
									<c:when test="${appPer.opertype eq '1'}">
										<td width="155" height="30" align="middle" valign="middle">开发</td>
									</c:when>
									<c:when test="${appPer.opertype eq '2'}">
										<td width="155" height="30" align="middle" valign="middle">测试</td>
									</c:when>
									<c:when test="${appPer.opertype eq '3'}">
										<td width="155" height="30" align="middle" valign="middle">运维</td>
									</c:when>
								</c:choose>
							</tr>
						</c:forEach>
			       	</table>
			    </div>
				<div class="bottom">
					<input name="" type="button" value="确定" onclick="updateRole()" />
				</div>
			</div>
		</div>
	</form>
</body>