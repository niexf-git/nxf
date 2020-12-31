<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/dailog.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.md5.js"></script>
<script type="text/javascript" src="${ctx}/js/systemmanager/user.js"></script>

<script type="text/javascript">
	function clearInputValue(){
		$("#useless").val(" ");
	}
</script>
<style>
	input[required]:invalid, input:focus:invalid, textarea[required]:invalid, textarea:focus:invalid{box-shadow: none;}
</style>

<body onload="clearInputValue()">
	<form id="submitForm" method="post">
		<div class="User-dialog">
			<div class="User-dialog-main">
				<input type="text" id="useless" hidden="true">
				<input type="hidden" value="'<%=session.getAttribute("loginName") %>'" id="loginName">
				<div id="main-four70"><strong>*</strong>新&nbsp;&nbsp;密&nbsp;&nbsp;码：
					<input type="password" id="password" name="password" required="required" placeholder="字母，数字，至少6位"/>
				</div>
			  	<div id="main-four70"><strong>*</strong>确认密码：
			    	<input type="password" id="rePassword" required="required"/>
			    </div>
			  	<div id="main-four70"><strong>*</strong>旧&nbsp;&nbsp;密&nbsp;&nbsp;码：
			    	<input type="password" id="oldPassword" name="oldPassword" required="required"/>
			    </div>
				<div class="bottom"><input name="" type="button" value="确定" onclick="updatePassword()"/></div>
			</div>
		</div>
	</form>
</body>