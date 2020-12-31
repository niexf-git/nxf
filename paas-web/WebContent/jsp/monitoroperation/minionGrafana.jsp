<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script>	
<script type="text/javascript">
var nodeIp = $.getUrlParam('nodeIp');
$.ajax({
	type : "POST",
	dataType : "json",
	data : {
		"nodeIp" : nodeIp
	},
	url : "/paas/host/queryGrafana.action",
	success : function(result) {
		if (result['resultCode'] == 'success') {
			window.location.href=result['resultMessage'];
		} else {
			parent.parent.alertError(result['resultCode'],
					result['resultMessage']);
		}
	}
});
</script>




