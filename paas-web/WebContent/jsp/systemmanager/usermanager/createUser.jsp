<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/dailog.css" rel="stylesheet" />
<script src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/jquery.md5.js"></script>
<script src="${ctx}/js/systemmanager/user.js"></script>

<script type="text/javascript">
	function clearInputValue(){
		$("#userName").val(" ");
	}
</script>
<style>
	input[required]:invalid, input:focus:invalid, textarea[required]:invalid, textarea:focus:invalid{box-shadow: none;}
</style>

<body onload="clearInputValue()">
	<form id="submitForm" method="post">
		<div class="User-dialog">
			<div class="User-dialog-main">
				<div id="main-four70">
					<strong>*</strong>用户账号：
					<input type="text" id="loginName" name="user.loginName" maxlength="15" required="required"/>
				</div>
				<div id="main-four70">
					<strong>*</strong>真实姓名：
					<input type="text" id="userName" name="user.userName" maxlength="100" required="required"/>
				</div>
				<div id="main-four70">
					<strong>*</strong>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：
					<input type="password" id="password" name="user.password" required="required"/>
				</div>
				<div id="main-four70">
					<strong>*</strong>确认密码：
					<input type="password" id="rePassword" required="required"/>
				</div>
				<div class="bottom">
					<input name="" type="button" value="确定" onclick="createUserSumbit()"/>
				</div>
			</div>
		</div>
	</form>
</body>