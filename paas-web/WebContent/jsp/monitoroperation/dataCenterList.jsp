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
<script type="text/javascript" src="${ctx}/js/monitoroperation/datacenter.js"></script>


		<div class="Monitor-main">
			<div class="Monitor-main-right">
				<div class="input3" style=" width:134px; margin:15px;">
				    <img src="${ctx}/imgs/createServie.png" width="11" height="11" />
					<a href="javascript:openCreateDataCenter()" style="width:90px; margin-right:0px;">新增数据中心</a>
				</div>
				<div class="Monitor-right-tab">
					<div class="content-tab">
						<table cellspacing="0" id="dataCenterList"></table>
						<div id="pagerBar"></div>
					</div>
				</div>
			</div>
		</div>

