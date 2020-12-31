<%@page import="com.cmsz.paas.web.constants.Constants"%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中国移动通信-深圳云计算PAAS管理平台</title>
<link href="${ctx}/css/alert.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script language="JavaScript" type="text/javascript">
	</script>
</head>
<body>
	<div class="alert">
		<div class="alert-main-no" style="background:none;">
			<div class="alert-main-title">
				<h1><img src="${ctx}/imgs/Check-failure.png" width="36" height="30" style="margin-right:5px; margin-bottom:-5px;"/>操作提示！</h1>
				<span>
					<a href="JavaScript:parent.closeAlertConfirmOnClickFork();" style="padding-right:10px">
						<img src="${ctx}/imgs/alert-close.png" width="22" height="22" border="0" />
					</a>
				</span>
			</div>
			<div class="alert-main-content" >
				<%=request.getParameter("msg")%>
			</div>
			<div class="alert-main-input">
			</div>
		</div>
	</div>
	<div style="clear:both;"></div>
</body>
</html>

