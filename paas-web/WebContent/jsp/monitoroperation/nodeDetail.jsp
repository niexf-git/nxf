<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/appeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
$(function(){
	$('.Monitor-right-title ul li a').each(function() {//todo
		$(this).click(function() {
			$('.Monitor-right-title ul li a').css({"background":"none"});//清空所有的背景
			$(this).css({"background":"#999"});//当前菜单加背景				
		});
	});
	var a = document.getElementById("a"); 
	if (a!=null) {
		a.href = "ipaasServiceList.jsp?minionIp=<%=request.getParameter("nodeIp")%>";
		a.click();
	}
	var b = document.getElementById("b"); 
	if (b!=null) {
		b.href = "appServiceList.jsp?minionIp=<%=request.getParameter("nodeIp")%>";	
		b.click();
	}
	var c = document.getElementById("c"); 		
	c.href = "componentList.jsp?minionIp=<%=request.getParameter("minionIp")%>&minionName=<%=request.getParameter("nodeIp")%>";
	if (a==null&&b==null) {
		c.click();
	}
	var d = document.getElementById("d"); 		
	d.href = "minionGrafana.jsp?nodeIp=<%=request.getParameter("nodeIp")%>";
});
</script>

<div class="Monitor-right-title">
	<ul>
		<c:if test="${param.serviceType eq '1'}">
		<li><a target="bbb" id="a" href="">服务列表</a></li>
		</c:if>
		<c:if test="${param.serviceType eq '2'}">
		<li><a target="bbb" id="b" href="">服务列表</a></li>
		</c:if>
		<li><a target="bbb" id="c" href="">组件列表</a></li>
		<li><a target="bbb" id="d" href="">监控</a></li>
	</ul>
</div>
<div class="main-Content">
	<iframe id="mainIframe" src=""
		width="100%" height="474" frameborder="0" name="bbb" scrolling="auto"></iframe>
</div>



