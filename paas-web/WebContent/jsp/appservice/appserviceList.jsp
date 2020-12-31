<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/index.css" rel="stylesheet" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet" />
<link href="${ctx}/js/appeasyui/easyui.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/js/appeasyui/themes/default/easyui.css" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="styles/font-awesome.css">
<link rel="stylesheet" href="${ctx}/js/appeasyui/themes/icon.css" />

<script src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script src="${ctx}/js/grid.locale-cn.js"></script>
<script src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script src="${ctx}/js/appeasyui/jquery.easyui.min.js"></script>
<script src="${ctx}/js/appservice/appservice.js"></script>
<script src="${ctx}/js/utils.js"></script>

<div class="Establish">
	<div class="Establish-left">
     <!-- <div class="name70">查询服务：</div> -->
		<div class="queryService" style="margin-right:15px;">
			<input name="" type="text" id="token" placeholder="支持模糊查询服务" onkeyup="value=value.replace(/[^A-Za-z0-9\-\_\/\.]/g,'')"/>
			<span><input id="search" type="button" onclick="queryAppServiceListByToken();" /></span>
		</div>
<!--<div class="name70">集群名称：</div> -->
		<div class="queryService">
			<select id="cluster_name" name="appService.cluster_id" onchange="queryAppServiceListByToken();">
				<option value="0" selected="selected">请选择集群名称</option>
			</select>
		</div>
		<div class="input3" onclick="AddServiceDialog();">
			<img src="${ctx}/imgs/createServie.png" width="11" height="11" />
			<a id="createAppService">新增服务</a>
		</div>
		<div id="switchAppInfoMsg" style="color: red;font-size: 12px; float:left; line-height:30px; margin-left:3px;">
		</div>
	</div>
	<div class="Establish-right">
	<div class="operationBox" onclick='opretion()' tabindex="-1" onblur='lose()'><img src="${ctx}/imgs/operationBox.png" width="14" height="10" style="margin-right:5px;"/>批量操作</div>
	<ul id="opre" class="operationBoxMian" style="display:none;">
		    <a href="javascript:void(0);" onclick="batchUpgradeServiceVersions();" class="batchStartAppServices" >
				<li><span><i class="fa fa-arrow-up"></i></span>一键升级</li>
			</a>
			<a href="javascript:void(0);" onclick="batchStartAppServices();" class="batchStartAppServices">
				<li><span><i class="fa fa-caret-right"></i></span>启动</li>
			</a>
			<a href="javascript:void(0);" onclick="batchStopAppServices();">
				<li><span><i class="fa fa-square" style=" color:#999;"></i></span>停止</li>
			</a>
			<a href="javascript:void(0);" onclick="batchRestartAppServices();">
				<li><span><i class="fa fa-refresh"></i></span>重启</li>
			</a>
			<a href="javascript:void(0);" onclick="batchDeleteAppServices();" class="batchDeleteAppServices">
				<li><span><i class="fa fa-trash-o"></i></span>删除</li>
			</a>
			<a href="javascript:void(0);" id="refresh" onclick="queryAppServiceListByToken();">
				<li><span><i class="fa fa-repeat"></i></span>刷新</li>
			</a>
		</ul>
	</div>
</div>

<div class="main-Content">
	<div class="content-tab" id="appserviceListDiv">
		<table cellspacing="0" id="queryAppserviceList"></table>
		<div id="pagerBar"></div>
	</div>
</div>
<div id="loadImg" style="display:none; top:0px; height: 93%;"><img alt="" src="${ctx}/imgs/load.gif"></img></div>

