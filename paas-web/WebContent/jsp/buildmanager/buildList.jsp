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
<script type="text/javascript" src="${ctx}/js/buildmanager/build.js"></script>
<!-- 单元格内容换行 -->
<style>
	.ui-jqgrid tr.jqgrow td {
	  	white-space: normal !important;
	    //height:auto;
	 }
</style>

<div class="Establish">	
   	<div class="Establish-left">
  	    <div class="name70">查询构建：</div>
        <div class="input1"><input id="token"  type="text"   maxlength="120" placeholder="支持模糊查询"/>
                      <span><input id="search" type="button" onclick="queryBuildList();"/></span>
        </div>
        <div class="input3"><a href="javaScript:createOrUpdateBuild('')" id="createBuildId">创建构建</a></div>
    </div>
   	<div class="structure-right">
   		
   		<a href="javaScript:refreshPageList();" id="refresh" style="float:left; color:#000;"><span style="float:left;"><img src="${ctx}/imgs/batchRefresh.png" width="25" height="25" /></span>&nbsp;刷新</a>
   	</div>
</div>

<div class="main-Content">
	<div class="content-tab">
		<table cellspacing="0" id="queryBuildList"></table>
		<div id="pagerBar"></div>
	</div>
</div>