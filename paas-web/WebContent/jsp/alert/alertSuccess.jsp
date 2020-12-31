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
		<div class="alert-main">
			<div class="alert-main-title">
				<h1><img src="${ctx}/imgs/succeed.png" width="29" height="30" style="margin-right:5px; margin-bottom:-5px;"/>操作成功！</h1>
				<span>
					<a href="JavaScript:parent.closeAlert();">
						<img src="${ctx}/imgs/alert-close.png" width="22" height="22" border="0" />
					</a>
				</span>
			</div>
			<div class="alert-main-content">
			</div>
			<div class="alert-main-input">
				<input name="" type="button" value="确定" onclick="parent.closeAlert();"/>
			</div>
		</div>
	</div>
	<div style="clear:both;"></div>
</body>
</html>

