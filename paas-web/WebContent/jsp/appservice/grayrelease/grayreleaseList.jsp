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
<script type="text/javascript" src="${ctx}/js/ion.rangeSlider.min.js"></script>
<link href="${ctx}/css/rangeSlider/ion.rangeSlider.skinHTML5.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/css/rangeSlider/ion.rangeSlider.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript">
	$(function() {
		var appId = $("#appId").val();
		var grayInstanceNums = $("#grayInstanceNums").val();
		$.ajax({
			cache : false,
			type : "get",
			dataType : "json",
			data : {
				appServiceId : appId
			},
			url : "/paas/appServiceGray/queryCheckResult.action",
			async : false,
			success : function(result) {
				if (result["resultMessage"] != "SUCCESS") {
					$(".create125").hide();
					$("#errorText").html(
							"<img src='${ctx}/imgs/tips.png'/>"
									+ result["resultMessage"]);
					if (grayInstanceNums>0) {
						document.getElementById("autoDeploy").disabled=true;
					}
				} else {
					$(".create125").show();
					$("#errorText").text("");
				}
			}
		});
		

// 		var instance_num = $("#instance_num").val();
// 		function setInstanceNum(obj) {
// 			$("#grayInstanceNum").val(obj.from);
// 			$("#totalInstanceNum").val(obj.to);
// 		}
// 		$('#rangeSlider').ionRangeSlider({
// 			type : 'double',
// 			min : 0,
// 			max : instance_num * 2,
// 			from : grayInstanceNums,
// 			to : instance_num,
// 			onStart : setInstanceNum,
// 			onChange : setInstanceNum,
// 			onFinish : function finish(obj) {
// 				var maxNumber = (obj.max * 2);
// 				var maxNumber2 = (obj.max / 2);
// 				if (obj.to == obj.max) {
// 					slider.update({
// 						max : maxNumber > 100 ? 100 : maxNumber
// 					});
// 					slider.reset();
// 				} else if (obj.to < maxNumber2) {
// 					slider.update({
// 						max : (obj.to * 2)
// 					});
// 					slider.reset();
// 				}
// 			}
// 		});
// 		var slider = $("#rangeSlider").data("ionRangeSlider");
// 		var stateNumber = $("#stateNumber").val();
// 		if (stateNumber==1||instance_num==1) {
// 			document.getElementById("autolGray").disabled=true;
// 		}
		
		var deploymentType=$("#deploymentType").val();
// 		if (deploymentType==2) {
// 			$("#autolGray").click();
// 			$("#autolGray").checked = true;
// 		}else 
		if (deploymentType==3) {
			$("#manualGray").click();
			$("#manualGray").checked = true;
		}else {
			$("#autoDeploy").click();
			$("#autoDeploy").checked = true;
		}
	});
	function showAutoDeploy() {

// 		document.getElementById("grayLevel").style.display = "none";
		document.getElementById("grayreleaseList").style.display = "none";
		document.getElementById("prompt").style.display = "none";
	};
	
// 	function showAutolGray() {

// 		document.getElementById("grayLevel").style.display = "";
// 		document.getElementById("grayreleaseList").style.display = "";
// 		document.getElementById("prompt").style.display = "none";
// 	};
	function showManualGray() {
		var isGray=$("#isGray").val();
		if (isGray=="true") {
			$(".create125").show();
		}else{
			$(".create125").hide();
		}
// 		document.getElementById("grayLevel").style.display = "none";
		document.getElementById("grayreleaseList").style.display = "";
		document.getElementById("prompt").style.display = "";
	};
</script>
<script type="text/javascript"
	src="${ctx}/js/appservicegray/grayrelease.js"></script>
<!-- 单元格内容换行 -->
<style>
.ui-jqgrid tr.jqgrow td {
	white-space: normal !important;
	word-wrap: break-word;
}

html,body {
	overflow-x: hidden;
}
</style>

<div class="main-Content">
	<input type="hidden" id="id" value="${grayEntity.id}" /> <input
		type="hidden" id="appId" value="${grayEntity.appId}" /> 
<%-- 	<input type="hidden" id="stateNumber" value="${grayEntity.state}" />  --%>
	<input type="hidden" id="instance_num" value="${grayEntity.totalInstanceNum}" />
	<input type="hidden" id="runVersion" value="${grayEntity.runVersion}" />
	<input type="hidden" id="isGray" value="${grayEntity.gray}" />
	<input type="hidden" id="deploymentType" value="${grayEntity.deploymentType}" />
	<input type="hidden" id="grayInstanceNums" value="${grayEntity.grayInstanceNum}" />
	<input type="hidden" id="operType" value="${grayEntity.oper_type}" /> <input
		type="hidden" id="appServiceName" value="${grayEntity.appServiceName}" />
	<div class="ant-tabs-nav-wrap">
		
			<div class="ant-tabs-nav">
				<div style=" float:left; width:100%">
				<div style="float:left;">
				
				<h1><label>
				    <input type="radio" name="deploymentType" value="3"  id="manualGray"   onclick="showManualGray()"/>
				    人工部署</label></h1>
				    
				<h1><label>
				    <input type="radio"  name="deploymentType" value="1"  id="autoDeploy"   onclick="showAutoDeploy()"/>
				    自动部署</label></h1>
				
<!-- 				  <h1><label> -->
<!-- 				    <input type="radio" name="deploymentType" value="2"  id="autolGray"  onclick="showAutolGray()"/> -->
<!-- 				    自动灰度</label></h1> -->
				 
				</div>
				
				  <div style=" color: #02a0da; width: 300px; float: left; text-align:center; background: none; font-size: 14px;line-height:22px;"
				id="errorText">&nbsp;&nbsp;&nbsp;&nbsp;</div>
				
				<input type="button"  value="保存" onclick="saveDeploymentType()" style="cursor:pointer; width:68px; height:28px; background:#02a0da; color:#fff; border:none;  text-align:center;margin-bottom:5px;"/>
			</div>
			</div>
<!-- 			<div style=" float:left; width:100%"> -->
<!-- 			<div id="grayLevel" style="display: block; margin-bottom:20px; float:left;"> -->
<!-- 					<input type="hidden" name="grayInstanceNum" id="grayInstanceNum" />  -->
<!-- 					<input type="hidden" name="totalInstanceNum" id="totalInstanceNum" /> -->
<!-- 					<table width="500" border="0" cellspacing="0" cellpadding="0"> -->
<!-- 						<tr> -->
<!-- 							<td height="40" align="right" valign="middle" -->
<!-- 								style="padding-top: 40px;">灰度实例数/总实例数：</td> -->
<!-- 							<td height="40" valign="middle"><div -->
<!-- 									style="width: 350px; height: 50px;"> -->
<!-- 									<div id="rangeSlider"></div> -->
<!-- 								</div></td> -->
						  
<!-- 						</tr> -->
<!-- 					</table> -->
<!-- 				  </div> -->
<!-- 			</div> -->
			
		
	</div>
	
	<div class="Establish" id="prompt" style="display: none;">
		<div class="Establish-left">
			<div class="create125" style=" margin-top:15px;margin-left:0px;">
				<img src="${ctx}/imgs/createServie.png" width="11" height="11" />
				<a href="javascript:void(0);" id="newGrayBtn"
					 style="margin-left:0px;">新建灰度版本</a>
			</div>
			<div style="margin-top: 15px; margin-left: 10px; color: #333; float: left; background: none; line-height:32px;width: 200px;"
				id="runVersionText">当前运行版本：${grayEntity.runVersion}</div>
			
		</div>
	</div>

	<div class="main-Content" id="grayreleaseList" style="display: none;">
		<div class="content-tab">
			<table cellspacing="0" id="queryGrayreleaseList"></table>
			<div id="pagerBar"></div>
		</div>
	</div>
</div>



