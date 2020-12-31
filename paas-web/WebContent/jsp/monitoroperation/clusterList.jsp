<!DOCTYPE html>
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
<script type="text/javascript" src="${ctx}/js/monitoroperation/cluster.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script><!-- getUrlParam页面传参 -->

<input id="dataCenterName" name="dataCenterName" value="<%=new String(request.getParameter("dataCenterName").getBytes("UTF-8")) %>" hidden="true" >
<style>
body,html { overflow-x:hidden;}
</style>


		<div class="Monitor-main">
			<div class="Monitor-main-right">
				<div class="input3" style=" margin:15px;">
					<img src="${ctx}/imgs/createServie.png" width="11" height="11" />
					<a href="javascript:openCreateCluster()">新增集群</a>
				</div>
				<div class="Monitor-right-tab">
					<div class="content-tab">
						<table cellspacing="0" id="clusterList"></table>
						<div id="pagerBar"></div>
					</div>
				</div>
			</div>
		</div>

