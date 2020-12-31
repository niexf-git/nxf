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
<script type="text/javascript" src="${ctx}/js/monitoroperation/cluster.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script><!-- getUrlParam页面传参 -->

<style type="text/css">
	*{
		margin:0px;padding:0px;font-size:12px;
	}
	input{  appgroup
		width:100px;height:20px;border:1px solid #ccc;
	}
	body{
	width:auto;
	height:auto;
	font-family:"微软雅黑";
	font-size:12px;
	margin:0px;
	padding:0px;
}
</style>

<body>
	<form id="submitForm" method="post">
		<input id="dataCenterId" name="dataCenterId"
			value="<%=request.getParameter("dataCenterId")%>" hidden="true">
		<div class="dialog-main">
			<div id="image-main-four70">
				<strong>*</strong>&nbsp;数据中心：<strong
					style="font-weight: bold; color: #000; margin-left: 12px;"><%=new String(request.getParameter("dataCenterName")
					.getBytes("UTF-8"))%></strong>
			</div>
			<div id="creat-data" style="margin-bottom: 10px;">
				<strong>*</strong>&nbsp;<span>集群名称：</span> <input type="text"
					id="clusterName" name="clusterName" style="width: 160px;" maxlength="15" placeholder="最多15个字符" />
			</div>
			<div id="creat-data" style="margin-bottom: 10px;">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>*</strong>&nbsp;类型：
				</span> <select id="clusterType" name="clusterType" style="width: 160px;">
					<option value="1">ipaas集群</option>
					<option value="2" selected>apaas集群</option>
				</select>
			</div>
			<div id="creat-data" style="margin-bottom: 10px;">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>*</strong>label：</span>
				<input type="text" id="label" name="label" style="width: 160px;" maxlength="63" placeholder="最多63个字符" />
				<strong>e.g. 'MyValue' or 'my_value'</strong>
			</div>

			<div id="image-main-four70">
				<span style="font-size: 12px; font-family: '微软雅黑'; float: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;描述：&nbsp;&nbsp;&nbsp;</span>
				<textarea
					style="resize: none; margin-left: 5px; border-radius: 5px; border: 1px solid #c0c0c0; width: 463px; height: 51px;"
					id="desc" name="desc" maxlength="255" placeholder="最多255个字符"></textarea>
			</div>
			<div class="bottom">
				<input name="" type="button" value="确定" onclick="createCluster();" />
			</div>
			<div id="loadImg" style="display:none"><img alt="" src="${ctx}/imgs/load.gif"></img></div>
		</div>
	</form>
</body>