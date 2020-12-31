<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/dailog.css" rel="stylesheet" />
<link href="${ctx}/css/multiselect2side/jquery.multiselect2side.css" rel="stylesheet" />
<link href="${ctx}/css/ztree/demo.css" rel="stylesheet" />

<script src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/multiselect2side/jquery.multiselect2side.js"></script>
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

<script type="text/javascript">
	//角色管理-人员分配-搜索框回车实现失效
	document.onkeydown=function(event){
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if(e && e.keyCode==13){ //enter键
			return false;
		}
	};
</script>

<body onload="selectInit()">
	<form id="submitForm" method="post">
		<input type="hidden" value="${roleId}" name="roleId" id="roleId" />
		<div id="main-four" style="font-size: 12px; font-family: '微软雅黑';">
			角色名称： <strong style="font-size: 14px; color: #007abb; font-family: '微软雅黑';">
					<%
						String x = request.getParameter("roleName");
						out.print(x);
					%>
				</strong>
				<span id="roleOperType"></span>
		</div>
		<div class="fpei">
			<select name="searchable[]" id='searchable' multiple='multiple'>
				<c:forEach items="${userSelectList}" var="user">
					<c:choose>
						<c:when test="${user.select}">
							<option value="${user.id}" SELECTED>${user.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${user.id}">${user.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
		<div class="bottom">
			<input name="" type="button" value="确定" onclick="roleGrantUser()" />
		</div>

	</form>
</body>