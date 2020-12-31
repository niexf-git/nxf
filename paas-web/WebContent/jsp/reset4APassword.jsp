<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/dailog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.md5.js"></script>
<style>
input[required]:invalid, input:focus:invalid, textarea[required]:invalid,
	textarea:focus:invalid {
	box-shadow: none;
}
</style>
<body style="overflow-x:hidden;overflow-y:hidden;">
	<form id="submitForm2" method="post">
		<div class="User-dialog">
			<div class="User-dialog-main">
				<div id="main-four71" >
					<strong>*</strong>4A&nbsp;&nbsp;帐&nbsp;&nbsp;号： 
					<input type="text" id="userId2" name="userId" required="required" placeholder="请输入4A账号" />
				</div>
				<div id="main-four71">
					<strong>*</strong>验&nbsp;&nbsp;证&nbsp;&nbsp;码&nbsp;： 
					<input type="text" id="checkCode2" name="checkCode"  required="required"  placeholder="请输入验证码"/> 
					<input name="" type="button" value="获取短信验证码" onclick="getResetPwdNotekey()" />
				</div>
				<div class="bottom2">
					<input name="" type="button" value="确定" onclick="checkResetPwdNotekey()" />
				</div>
			</div>
		</div>
	</form>
</body>
<script type="text/javascript">
	function getResetPwdNotekey() {
		var loginName = $.trim($("#userId2").val());
		if (loginName == "") {
			alert("4A帐号不能为空");
			$("#userId2").focus();
			return;
		}
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "/paas/group4a/getResetPwdNotekey.action",
			data : {
				"loginName" : loginName
			},
			success : function(result) {
				if (result['resultCode'] == 'success') {
					alert("获取验证码成功！");
				} else {
					alert(result['resultMessage']);
				}
			}
		});
	}
	function checkResetPwdNotekey() {
		var loginName = $.trim($("#userId2").val());
		var checkCode = $.trim($("#checkCode2").val());
		if (loginName == "") {
			alert("4A帐号不能为空");
			$("#userId2").focus();
			return;
		}
		if (checkCode == "") {
			alert("验证码不能为空");
			$("#checkCode2").focus();
			return;
		}
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "/paas/group4a/checkResetPwdNotekey.action",
			data : {
				"loginName" : loginName,
				"checkCode" : checkCode
			},
			success : function(result) {
				if (result['resultCode'] == 'success') {
					alert("重置密码成功！");
					parent.close();
				} else {
					alert(result['resultMessage']);
				}
			}
		});
	}
</script>
