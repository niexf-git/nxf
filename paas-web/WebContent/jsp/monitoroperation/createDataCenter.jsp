<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/dailog.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/ztree/demo.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/js/monitoroperation/datacenter.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script><!-- getUrlParam页面传参 -->

<style type="text/css">
	*{
		margin:0px;padding:0px;font-size:12px;
	}
	input{  appgroup
		width:100px;height:20px;border:1px solid #ccc;
	}
</style>

<body>
	<form id="submitForm" method="post">
		<div class="dialog">
			<div class="dialog-main" style="background:none;">
				<div id="image-main-four70" style="font-size: 12px; font-family: '微软雅黑';">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>*</strong>&nbsp;名称： 
					<input type="text" id="dataCenterName" name="dataCenterName" maxlength="15" placeholder="最多15个字符" required="required" style="width:150px;" />
				</div>
				<div id="creat-data" style="font-size: 12px; font-family: '微软雅黑'; margin-bottom:10px; height:20px;">
					<span style="float:left;">主数据中心：</span> <input type="checkbox" name="isMainDataCenter" id="isMainDataCenter" value="" onchange="" style="margin-left:15px; width:18px; float:left;"/>
				</div>
				<div id="image-main-four70" style="float: left;">
					<span style="font-size: 12px; font-family: '微软雅黑'; float: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;描述：&nbsp;&nbsp;&nbsp;</span>
					<textarea rows="3" cols="61"
						style="resize: none; margin-left: 5px; border-radius: 5px; border: 1px solid #c0c0c0; width: 463px; height: 51px;"
						id="desc" name="desc" maxlength="255" placeholder="最多255个字符"></textarea>
				</div>
				<div class="bottom">
					<input name="" type="button" value="确定" onclick="createDataCenter()" />
				</div>
				<div id="loadImg" style="display:none"><img alt="" src="${ctx}/imgs/load.gif"></img></div>
			</div>
		</div>
	</form>
</body>