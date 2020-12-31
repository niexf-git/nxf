<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/dailog.css" rel="stylesheet" />
<script src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script src="${ctx}/js/grid.locale-cn.js"></script>
<script src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script src="${ctx}/js/jquery.md5.js"></script>
<script src="${ctx}/js/systemmanager/user.js"></script>
<%
String errorInfo = (String)request.getAttribute("errorMsg");         // 获取错误属性
if(errorInfo != null) {
%>
<script type="text/javascript">
parent.parent.alertError("<%=errorInfo%>");                                            // 弹出错误信息
</script>	
<%
}
%>
<form id="submitForm" method="post">
	<input type="hidden" id="id" name="user.id" value="${user.id}" />
	<div class="User-dialog">
		<div class="User-dialog-main">
			<div id="main-four70">
				<strong>*</strong>用户账号：
				<input type="text" id="loginName" name="user.loginName" value="${user.loginName}" maxlength="15" required="required"/>
			</div>
			<div id="main-four70">
				<strong>*</strong>真实姓名：
				<input type="text" id="userName" name="user.userName" maxlength="100" value="${user.userName}" required="required"/>
			</div>
			<div id="main-four70"><strong>*</strong>新&nbsp;&nbsp;密&nbsp;&nbsp;码：
				<input type="password" id="password" name="user.password" required="required"/>
			</div>
			<div id="main-four70"><strong>*</strong>确认密码：
				<input type="password" id="rePassword" required="required"/>
			</div>
			<div class="bottom">
				<input name="" type="button" value="确定" onclick="updateUser()"/>
			</div>
		</div>
	</div>
</form> 