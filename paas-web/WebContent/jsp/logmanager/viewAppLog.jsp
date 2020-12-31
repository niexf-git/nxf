<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script><!-- getUrlParam页面传参 -->
<script type="text/javascript" src="${ctx}/js/logmanager/viewAppLog.js"></script>
<!-- onload="resizeWindow();" 
<script type="text/javascript">
function resizeWindow(){
	if (window.screen) {//判断浏览器是否支持window.screen判断浏览器是否支持screen     
		var myw = screen.availWidth;   //定义一个myw，接受到当前全屏的宽     
		var myh = screen.availHeight;  //定义一个myw，接受到当前全屏的高     
		window.moveTo(0, 0);           //把window放在左上脚     
		window.resizeTo(myw, myh);     //把当前窗体的长宽跳转为myw和myh     
	}
}
</script>
-->
<body>
	<div class="bzrz-there" style="width:100%;height:100%">
		<textarea name="appLog" id="appLog" cols="" rows=""
			style="width: 100%; height: 640px;" readonly="readonly"></textarea>
	</div>
</body>
