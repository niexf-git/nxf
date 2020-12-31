<%@ page import="com.cmsz.paas.web.constants.Constants"%>
<%@ page import="com.cmsz.ws.dao.SafeSwitch"%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="java.net.InetAddress"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/js/crypto-js/crypto-js.js"></script>
<script type="text/javascript" src="${ctx}/js/crypto-js/aes.js"></script>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	Object user = session.getAttribute(Constants.CURRENT_USER);
	if (user != null) {
		session.removeAttribute(Constants.CURRENT_USER);
		response.getWriter()
				.print("<script>location='" + basePath
						+ "login.jsp'</script>");
	}

	InetAddress address = InetAddress.getLocalHost();
	String localIp = address.getHostAddress() + "";
%>

<%
	int switchValue = SafeSwitch.getSwitchValue();

	if (switchValue == 0) { // 转到4A平台的登录页面
%>

<html>
<head>
<title>中国移动通信 - 深圳云计算PAAS管理平台</title>
<link href="${ctx}/css/login.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0">
	<h1 align="center">该系统已被4A平台接管，请通过4A平台登录!</h1>
</body>
</html>

<%
	} else {
		// 从原来的方式登录
%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>中国移动通信 - 深圳云计算PAAS管理平台</title>
<link href="${ctx}/css/login.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.md5.js"></script>
<script type="text/javascript" src="${ctx}/js/aesUtils.js"></script>
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/jquery-ui-1.8.23.custom.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>

<script type="text/javascript">
	if (top != self) {
		if (top.location != self.location)
			top.location = self.location;
	}

	function formSumbit() {
		//账号
		var loginName = $.trim($("#loginName").val());
		if (loginName == '') {
			alert("请输入您的账号");
			$("#loginName").focus();
			return;
		}
		//保存用户名到sessionStorage，给修改时候使用
		window.sessionStorage.setItem("loginName", loginName);
		//密码
		var password = $.trim($("#password").val());
		if (password == '') {
			alert("请输入您的密码");
			$("#password").focus();
			return;
		}

		var reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%\^\&\*\(\)])[\da-zA-Z!@#\$%\^\&\*\(\)]{8,}$/;
		if (password.match(reg) == null) {
			alert("密码由8位以上，且同时包含大小写字母、数字、及特殊字符组成");
			$("#password").focus();
			return;
		}

		//验证码
		var checkCode = $.trim($("#checkCode").val());
		if (checkCode == '') {
			alert("请输入验证码");
			$("#checkCode").focus();
			return;
		}
		//验证登录参数
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "/paas/user/verifyLoginParams.action",
			data : {
				"loginName" : loginName,
				"password" : $.md5(password + loginName), //验证登录参数的时候需要md5加密
				"checkCode" : checkCode
			},
			success : function(result) {
				if (result['resultCode'] == 'true') {
					//登录参数验证通过后，还是回到表单提交
					$("#loginForm").submit();
				} else if (result['resultCode'] == 'false') {
					clearCheckCode(); //清空验证码
					clearPwd(); //清空密码
					refresh(); //刷新验证码
					//验证登录参数，返回错误信息显示
					$("#loginErrorText").text(result['resultMessage']);
				}
			}
		});
	}

	// 清除用户名
	function clearLoginName() {
		$("#loginName").val("");
	}
	// 清除密码
	function clearPwd() {
		$("#password").val("");
	}
	// 清空验证码
	function clearCheckCode() {
		$("#checkCode").val("");
	}

	function refresh() {
		//IE存在缓存,需要new Date()实现更换路径的作用    
		document.getElementById("image").src = "${ctx}/user/getImage.action?+ Math.random()"
				+ new Date();
	}

	function is4aLogin() {
				$.ajax({
					type : "GET",
					dataType : "json",
					url : "/paas/group4a/is4aLogin.action",
					success : function(result) {
						if (result['resultCode'] == 'success') {
							if (result['resultMessage'] == 'true') {
										$.ajax({
											type : "GET",
											dataType : "json",
											url : "/paas/group4a/isEmergency.action",
											success : function(result) {
												if (result['resultCode'] == 'success') {
													if (result['resultMessage'] == 'true') {
														document.getElementById("login").style.display = "none";
														document.getElementById("login4A").style.display = "none";
														document.getElementById("emergency4ALogin").style.display = "";
													} else {
														document.getElementById("login").style.display = "none";
														document.getElementById("login4A").style.display = "";
														document.getElementById("emergency4ALogin").style.display = "none";
													}
												} else {
													parent.alertError(result['resultCode'],result['resultMessage']);
												}
											}
										});

							} else {
								document.getElementById("login").style.display = "";
								document.getElementById("login4A").style.display = "none";
								document.getElementById("emergency4ALogin").style.display = "none";
							}
						} else {
							parent.alertError(result['resultCode'],result['resultMessage']);
						}
					}
				});
	};
	
	function getcheckCode() {
		var loginName = $.trim($("#loginName1").val());
		if (loginName == '') {
			alert("请输入您的账号");
			$("#loginName1").focus();
			return;
		}
		//保存用户名到sessionStorage，给修改时候使用
		//window.sessionStorage.setItem("loginName1", loginName);
		//密码
		var password = $.trim($("#password1").val());
		if (password == '') {
			alert("请输入您的密码");
			$("#password1").focus();
			return;
		}

		var reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%\^\&\*\(\)])[\da-zA-Z!@#\$%\^\&\*\(\)]{8,}$/;
		if (password.match(reg) == null) {
			alert("密码由8位以上，且同时包含大小写字母、数字、及特殊字符组成");
			$("#password1").focus();
			return;
		}
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "/paas/group4a/getNotekey.action",
			data : {
				"loginName" : loginName,
				"password" : $.aesEncrypt(password).toString(),//验证登录密码需要加密
			},
			success : function(result) {
				if (result['resultCode'] == 'success') {
					alert("获取验证码成功！");
				} else {
					$("#loginErrorText1").text(result['resultMessage']);
				}
			}
		});

	}

	function emergency4ALogin() {
		var loginName = $.trim($("#loginName2").val());
		if (loginName == '') {
			alert("请输入您的账号");
			$("#loginName2").focus();
			return;
		}
		//保存用户名到sessionStorage，给修改时候使用
		//window.sessionStorage.setItem("loginName1", loginName);
		//密码
		var password = $.trim($("#password2").val());
		if (password == '') {
			alert("请输入您的密码");
			$("#password2").focus();
			return;
		}

		var reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%\^\&\*\(\)])[\da-zA-Z!@#\$%\^\&\*\(\)]{8,}$/;
		if (password.match(reg) == null) {
			alert("密码由8位以上，且同时包含大小写字母、数字、及特殊字符组成");
			$("#password2").focus();
			return;
		}
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "/paas/group4a/emergency4ALogin.action",
			data : {
				"loginName" : loginName,
				"password" : $.aesEncrypt(password).toString(),//验证登录密码需要加密
			},
			success : function(result) {
				if (result['resultCode'] == 'success') {
					openDialog("/paas/jsp/account.jsp", "请选择从帐号登录", 380, 130);
				} else {
					$("#loginErrorText2").text(result['resultMessage']);
				}
			}
		});

	}

	function checkNotekey() {
		//账号
		var loginName = $.trim($("#loginName1").val());
		if (loginName == '') {
			alert("请输入您的账号");
			$("#loginName1").focus();
			return;
		}
		//保存用户名到sessionStorage，给修改时候使用
		window.sessionStorage.setItem("loginName1", loginName);
		//密码
		var password = $.trim($("#password1").val());
		if (password == '') {
			alert("请输入您的密码");
			$("#password1").focus();
			return;
		}

		var reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%\^\&\*\(\)])[\da-zA-Z!@#\$%\^\&\*\(\)]{8,}$/;
		if (password.match(reg) == null) {
			alert("密码由8位以上，且同时包含大小写字母、数字、及特殊字符组成");
			$("#password1").focus();
			return;
		}

		//验证码
		var checkCode = $.trim($("#checkCode1").val());
		if (checkCode == '') {
			alert("请输入验证码");
			$("#checkCode1").focus();
			return;
		}
		//验证登录参数
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "/paas/group4a/checkNotekey.action",
			data : {
				"loginName" : loginName,
				"password" : $.aesEncrypt(password).toString(),//验证登录密码需要加密
				"checkCode" : checkCode
			},
			success : function(result) {
				if (result['resultCode'] == 'success') {
					openDialog("/paas/jsp/account.jsp", "请选择从帐号登录", 380, 130);
				} else {
					$("#loginErrorText1").text(result['resultMessage']);
				}
			}
		});
	}

	function selfLogin(userName) {
		$('#userName').val(userName);
		$("#loginForm4").submit();

	}

	function update4APassword() {
		openDialog("/paas/jsp/update4APassword.jsp", "修改密码", 480, 303);
	}
	function reset4APassword() {
		openDialog("/paas/jsp/reset4APassword.jsp", "忘记密码", 480, 200);
	}
	function openDialog(url, titleStr, widthValue, heightValue) {
		$("<iframe id='dialogIf' frameborder='0' src='" + url + "'/>\"")
				.dialog({
					title : titleStr,
					autoOpen : true,
					draggable : false,
					resizable : false,
					closeOnEscape : false,
					/* open:function(event,ui){$(".ui-dialog-titlebar").hide();}, */
					modal : true,
					height : heightValue,
					close : function() {
						$("#dialogIf").remove();
					},
					position : 'center',
					width : widthValue
				}).width(widthValue).height(heightValue);
	}

	function close() {
		$("#dialogIf").remove();
	}

	function getIndex() {
		window.location.href = "/paas/index.html";
	}

	$(function() {
		is4aLogin();
		$("#loginName").focus();
		$("#loginName").val("");
		$("#checkCode").val("");
		//$("#loginName,#password").keydown(function(e){
		$("#password").keydown(function(e) {
			if ((e.keyCode || e.which) == 13) {
				formSumbit();
			}
		}).focus(function(e) {

		}).blur(function(e) {
		});

		$("#checkCode").keydown(function(e) {
			if ((e.keyCode || e.which) == 13) {
				formSumbit();
			}
		}).focus(function(e) {

		}).blur(function(e) {
		});
	});
</script>
</head>

<body>
	<div class="autoHeightImg">
		<div class="login">
			<div class="login-main">
				<div class="login-main-top"></div>
				<div class="login-main-content">
					<!-- PAAS云登录 -->
					<div id="login" style="display: none">
						<form id="loginForm" action="${ctx}/user/login" method="post">
							<input id="localIp" name="localIp" value="<%=localIp%>" hidden="true">
							<div class="login-User">
								<strong><img src="${ctx}/imgs/loginUser.png" width="14" height="14"></strong> 
								<input type="text" id="loginName" name="loginName" placeholder="请输入账号" /> 
								<span onclick="javascript:clearLoginName();"></span>
							</div>
							<div class="login-ClosedLock">
								<strong><img src="${ctx}/imgs/password.png" width="14"height="14"></strong> 
								<input type="password" id="password" placeholder="请输入密码" /> 
								<span onclick="javascript:clearPwd();"></span>
							</div>
							<div class="login-verification">
								<input type="text" id="checkCode" name="checkCode" placeholder="验证码" /> 
								<span><img alt="点击刷新" id="image"onclick="refresh()" src="${ctx}/user/getImage.action"></span> 
								<a href="javascript:void(0);" onclick="refresh()">看不清？换一张 </a>
							</div>
							<!-- 验证登录参数，返回错误信息显示 -->
							<div style="float: left; color: #c41200; margin-top: 2px;">
								<font id="loginErrorText"></font>
							</div>
							<p class="login-submit">
								<input  name="" type="button" value="登录" onclick="formSumbit();" />
							</p>
						</form>
					</div>
					<!-- 4A登录 -->
					<div id="login4A" style="display: none">
						<input id="localIp1" name="localIp" value="<%=localIp%>" hidden="true">
						<div class="login-User">
							<strong><img src="${ctx}/imgs/loginUser.png" width="14" height="14"></strong> 
							<input type="text" id="loginName1" name="loginName" placeholder="请输入4A账号" /> 
							<span onclick="javascript:clearLoginName();"></span>
						</div>
						<div class="login-ClosedLock">
							<strong><img src="${ctx}/imgs/password.png" width="14" height="14"></strong> 
							<input type="password" id="password1" placeholder="请输入密码" /> 
							<span onclick="javascript:clearPwd();"></span>
						</div>
						<div class="login-verification1">
							<input type="text" id="checkCode1" name="checkCode" placeholder="验证码" /> 
						    <input style="margin-left: 16px;"name="" type="button" value="获取短信验证码"onclick="getcheckCode()" />
						</div>
						<!-- 验证登录参数，返回错误信息显示 -->
						<div style="float: left; color: #c41200; margin-top: 2px;">
							<font id="loginErrorText1"></font>
						</div>
						<p class="login-submit" style="margin-top: 40px;">
							<input name="" type="button" value="登录" onclick="checkNotekey();" />
						</p>
						<div>
							<input style="margin-left: 45px;margin-top: 10px;cursor:pointer;border:none;" name="" type="button" value="修改密码" onclick="update4APassword();" /> 
							<input style="margin-left: 65px;margin-top: 10px;cursor:pointer;border:none;"name="" type="button" value="忘记密码" onclick="reset4APassword();" />
						</div>
					</div>
					<!-- 4A应急登录 -->
					<div id="emergency4ALogin" style="display: none">
						<input id="localIp2" name="localIp" value="<%=localIp%>" hidden="true">
						<div class="login-User" style="margin-top: 20px;">
							<strong><img src="${ctx}/imgs/loginUser.png" width="14" height="14"></strong> 
							<input type="text" id="loginName2" name="loginName" placeholder="请输入4A账号" /> 
							<%-- <span onclick="javascript:clearLoginName();"></span> --%>
						</div>
						<div class="login-ClosedLock" style="margin-top: 20px;">
							<strong><img src="${ctx}/imgs/password.png" width="14"height="14"></strong> 
							<input type="password" id="password2" placeholder="请输入密码" /> 
							<%-- <span onclick="javascript:clearPwd();"></span> --%>
						</div>
						<!-- 验证登录参数，返回错误信息显示 -->
						<div style="float: left; color: #c41200; margin-top: 2px;">
							<font id="loginErrorText2"></font>
						</div>
						<p class="login-submit" style="margin-top: 30px;">
							<input name="" type="button" value="登录" onclick="emergency4ALogin();" />
						</p>
						<div>
							<input style="margin-left: 45px;margin-top: 10px;cursor:pointer;border:none;" name="" type="button" value="修改密码" onclick="update4APassword();" /> 
							<input style="margin-left: 65px;margin-top: 10px;cursor:pointer;border:none;"name="" type="button" value="忘记密码" onclick="reset4APassword();" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="login-tips">©2014-2017中国移动. All rights reserved.</div>
	</div>
	<form id="loginForm4" action="${ctx}/group4a/selfLogin.action" method="post" hidden="true">
		<input id="userName" name="userName" type="text" />
	</form>
</body>
</html>
<%
	}
%>