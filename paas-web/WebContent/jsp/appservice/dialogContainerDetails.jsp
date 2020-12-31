<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" />
<link href="${ctx}/css/alert.css" rel="stylesheet" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet" />
<link href="${ctx}/css/lhgcalendar/lhgcalendar.css" rel="stylesheet" />
<script src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script src="${ctx}/js/grid.locale-cn.js"></script>
<script src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script src="${ctx}/js/utils.js"></script>
<script type="text/javascript">
	$(function() {
		var containerId = $.getUrlParam('containerId');
		var hostIP = $.getUrlParam('hostIP');
		
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				"containerId" : containerId,
				"hostIP" : hostIP
			},
			url : "/paas/appServiceInstance/dialogContainerDetails.action",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					$("#loadImage").css('display', 'none');
					$("#mainContent").css('display', 'block');
					$("#diagnosMsg").val(result['resultMessage']);
				} else {
					parent.parent.alertError(result['resultCode'], result['resultMessage']);
				}
			}
		});
	});

	function closeDialog() {
		parent.parent.parent.close();
	}
</script>
<div id="loadImage" style="text-align: center; margin-top: 110px;">
	<img src="../../imgs/load.gif" alt="图片飞了" />
</div>
<div id="mainContent" style="display: none">
	<div>
		<textarea id='diagnosMsg' name="" cols="" rows=""
			style="background: #f1f1f1; width: 550px; height: 220px; resize: none; border: none;"
			readonly="readonly">正在查询，请稍等...</textarea>
	</div>
	<div class="alert-main-input" style="margin: 0">
		<input type="button" value="确定" id="sub" onclick="closeDialog();"/>
	</div>
</div>
