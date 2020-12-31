<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script>
<script type="text/javascript" src="${ctx}/js/monitoroperation/host.js"></script>
<%
	String dataCenterType = request.getParameter("dataCenterType");
	String clusterType = request.getParameter("clusterType");
	String clusterId = request.getParameter("clusterId");
%>
<style>
.ui-jqgrid .ui-jqgrid-bdiv {position: static; margin: 0em; padding:0; overflow-y: auto; overflow-x:hidden; max-height:380px;  text-align:left; widht:100%;}
html,body{ position: static; }
</style>
<input id="dataCenterType" name ="dataCenterType" type="hidden" value="<%=dataCenterType%>"/>
<input id="clusterType" name ="clusterType" type="hidden" value="<%=clusterType%>"/>
<input id="clusterId" name ="clusterId" type="hidden" value="<%=clusterId%>"/>

<div class="Monitor-main">
	<div class="Monitor-main-right">
		<div class="input3" style=" margin:15px;">
			<img src="${ctx}/imgs/createServie.png" width="11" height="11" />
			<a href="javascript:addHost();">新增主机</a>
			
		</div>
		<div class="input3" style=" background:#ff6633; margin:15px;">
			<img src="${ctx}/imgs/batchDelete.png" width="14" height="14" />
			<span><a href="javascript:deleteAllHost(<%=clusterId%>);">删除主机</a></span>
			
		</div>
		<div class="input3" style="background:#ff6633; margin:15px;">
		<img src="${ctx}/imgs/iconRefresh.png" width="11" height="11" />
			<span><a href="javascript:refresh();">刷新列表</a></span>
		</div>
		<div class="Monitor-right-tab">
			<div class="content-tab">
				<table cellspacing="0" id="hostList"></table>
				<div id="pagerBar"></div>
			</div>
		</div>
		<div id="loadImg" style="display:none"><img alt="" src="${ctx}/imgs/load.gif"></img></div>
	</div>
</div>
