<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script>
<script type="text/javascript">
	var appServiceId ="<%=request.getParameter("appServiceId")%>";
	$(function(){
		document.getElementById("runing").click();
	});
	function show(s){		
		if (document.getElementById("runing").checked == true) {
			showLoad();
			$.ajax({
					type : "POST",
					dataType : "json",
					data : {
					"appServiceId" : appServiceId,
					"type" : "runing"
					},
					url : "/paas/appservice/queryGrafana.action",
					success : function(result) {
					if (result['resultCode'] == 'success') {
					document.getElementById("mainIframe").src = result['resultMessage'];
					closeLoad();
					//window.location.href = result['resultMessage'];
					} else {
						parent.parent.alertError(result['resultCode'],result['resultMessage']);
						}
					}
					});
		} else if (document.getElementById("all").checked == true) {
			showLoad();
			$.ajax({
				type : "POST",
				dataType : "json",
				data : {
				"appServiceId" : appServiceId
				},
				url : "/paas/appservice/queryGrafana.action",
				success : function(result) {
				if (result['resultCode'] == 'success') {
				document.getElementById("mainIframe").src = result['resultMessage'];
				closeLoad();
				//window.location.href = result['resultMessage'];
				} else {
					parent.parent.alertError(result['resultCode'],result['resultMessage']);
					}
				}
				});
		}
	}
	//从主页展示进度条
	function showLoad(){	
		$('#grafana-loadImg').css({"width":"100%","height":"100%",});
		$('#grafana-loadImg').show();
	}
	function closeLoad(){
		$('#grafana-loadImg').hide();
	}
</script>
<style>
html,body{ overflow-y:hidden;}
</style>
<div style="margin-bottom:10px;">
	<input type="radio" id="runing" name="radio" checked="checked" onclick="show('a')"/>当前运行实例
	<input type="radio" id="all" name="radio" onclick="show('b')"/>所有实例
</div>
<div class="main-content">
	<iframe id="mainIframe" src="" width="1070" height="420"
		frameborder="0" name="aaa" scrolling="auto"></iframe>
	<div id="grafana-loadImg" style="display:none"><img alt="" src="${ctx}/imgs/load.gif"></img></div>
</div>






