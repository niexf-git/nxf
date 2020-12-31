<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/dailog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.md5.js"></script>
<script type="text/javascript">
	function queryAccount() {
		$.ajax({
			type : "GET",
			dataType : "json",
			url : "/paas/group4a/queryAccount.action",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					document.getElementById("account").innerHTML = "";
					var json = eval(result['resultMessage']);
					$.each(json, function() {
						$("#account").append(
								"<option value=" + this.value + ">" + this.text + "</option>");
					});
				} else {
					alert(result['resultMessage']);
				}
			}
		});
	}

	function selfLogin() {
		parent.selfLogin($.trim($('#account').val()));
		parent.close();
	}
</script>
<style>
input[required]:invalid,input:focus:invalid,textarea[required]:invalid,textarea:focus:invalid
	{
	box-shadow: none;
}
</style>
<body onload="queryAccount()" style="overflow-x:hidden;overflow-y:hidden;">
	<div class="User-dialog" >
		<div class="User-dialog-main">
			<div id="main-four70">
				<select name="account" id="account" onchange=""></select>
			</div>
			<div class="bottom1" style="margin-top: 15px;">
				<input style="display:block;margin:0 auto" name="" type="button" value="登录" onclick="selfLogin()" />
			</div>
		</div>
	</div>
</body>