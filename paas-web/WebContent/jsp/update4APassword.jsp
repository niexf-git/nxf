<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/dailog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.md5.js"></script>
<script type="text/javascript" src="${ctx}/js/crypto-js/crypto-js.js"></script>
<script type="text/javascript" src="${ctx}/js/crypto-js/aes.js"></script>
<script type="text/javascript" src="${ctx}/js/aesUtils.js"></script>
<style>
input[required]:invalid,input:focus:invalid,textarea[required]:invalid,textarea:focus:invalid
	{
	box-shadow: none;
}
</style>
<body style="overflow-x:hidden;overflow-y:hidden;">
	<form id="submitForm1" method="post">
		<div class="User-dialog">
			<div class="User-dialog-main" >
				<div id="main-four72">
					<strong>*</strong>4A&nbsp;&nbsp;帐&nbsp;&nbsp;号：&nbsp;<input type="text" id="userId1" name="userId" required="required" placeholder="请输入4A账号" />
				</div>
				<div id="main-four72">
					<strong>*</strong>旧&nbsp;&nbsp;密&nbsp;&nbsp;码&nbsp;：&nbsp;<input type="password" id="oldPassword1" name="oldPassword" required="required" placeholder="请输入旧密码" />
				</div>
				<div id="main-four72">
					<strong>*</strong>新&nbsp;&nbsp;密&nbsp;&nbsp;码&nbsp;：&nbsp;<input type="password" id="password1" name="password" required="required" placeholder="请输入新密码"  />
				</div>
				<div id="main-four72">
					<strong>*</strong>确认密码&nbsp;：&nbsp;&nbsp;<input type="password" id="rePassword1" required="required" placeholder="请输确认新密码"/>
				</div>
				<div class="bottom2">
					<input name="" type="button" value="确定" onclick="update4APassword()" />
				</div>
			</div>
		</div>
	</form>
</body>

<script type="text/javascript">
function update4APassword() {
	var loginName = $.trim($("#userId1").val());
	// 新密码
	var password = $.trim($("#password1").val());
	// 确认密码
	var rePassword = $.trim($("#rePassword1").val());
	// 旧密码
	var oldPassword = $.trim($("#oldPassword1").val());

	if (loginName == "") {
		alert("4A帐号不能为空");
		$("#userId1").focus();
		return;
	}
	if (oldPassword == "") {
		alert("旧密码不能为空");
		$("#oldPassword1").focus();
		return;
	}
	if (password == "") {
		alert("新密码不能为空");
		$("#password1").focus();
		return;
	}
	if (rePassword == "") {
		alert("确认密码不能为空");
		$("#rePassword1").focus();
		return;
	}
	var reg = /^(?=.*?[0-9])(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[!#$%])[0-9A-Za-z!#$%]{12,}$/;
	if (password.match(reg) == null) {
		alert("密码由12位以上，且同时包含大小写字母、数字、及特殊字符!#$%组成");
		$("#password1").focus();
		return;
	}
	if (rePassword != password) {
		alert("确认密码必须和新密码保持一致");
		$("#rePassword1").focus();
		return;
	}
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/paas/group4a/update4APwd.action",
		data : {
			"loginName" : loginName,
			"password" : $.aesEncrypt(password).toString(),//密码加密
			"oldPassword" : $.aesEncrypt(oldPassword).toString(),//密码加密
		},
		success : function(result) {
			if (result['resultCode'] == 'success') {
				alert("密码修改成功！");
				parent.close();
			} else {
				alert(result['resultMessage']);
			}
		}
	});
}
</script>
