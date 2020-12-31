<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/css/lhgcalendar/lhgcalendar.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script>
<script type="text/javascript">
	$(function() {
		var ipaasServiceId = $.getUrlParam('ipaasServiceId');
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				"ipaasServiceId" : ipaasServiceId.toString()
			},
			url : "/paas/ipaasInstance/diagnosisIpass.action",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					$("#loadImage").css('display', 'none');
					$("#mainContent").css('display', 'block');
					$("#diagnosMsg").val(result['resultMessage']);
				} else {
					parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
				}
			}
		});
	});

	function closeDialog() {
		parent.parent.parent.close();
	}
</script>
<style>
.alert-main-input{
	text-align: center;
}
.alert-main-input input{
	border:none;
	border:1xp solid #dadada;
	background:#333; 
	border-radius:10px;
	width:59px;
	height:30px;
	font-size:12px;
	font-family:"微软雅黑";
	color:#fff;
	cursor: pointer;
	outline:none;
}
</style>
<div id="loadImage" style="text-align: center; margin-top: 110px;">
	<img src="../../imgs/load.gif" alt="图片飞了" />
</div>
<div id="mainContent" style="display: none">
	<div>
		<textarea id='diagnosMsg' name="" cols="" rows=""
			style="background: #f1f1f1; width: 550px; height: 220px; resize: none; border: none;"
			readonly="readonly">正在进行诊断，请稍等...</textarea>
	</div>
	<div class="alert-main-input">
		<input type="button" value="确定" id="sub" onclick="closeDialog();"
			style="margin-top: 25px;" />
	</div>
</div>
