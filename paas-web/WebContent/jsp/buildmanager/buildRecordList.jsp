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
<script type="text/javascript" src="${ctx}/js/buildmanager/buildRecord.js"></script>

<style type="text/css">
			
			.box{
				width:1022px;
				height:auto;				
				max-height:400px;
				float:left;
				background:#333;
				overflow:auto;
				overflow-x:hidden;
				color:#fff;
				padding-left:4px;
			}
			.box-main{
				float:left;
				width:1024px;
				height:auto;
				line-height:22px;
				color:#fff;
			}
</style>
<div class="build-right">
<div class="Establish">	
   	<div class="Establish-left">
  	  	<div class="name">查询镜像版本：</div>
        <div class="input1">
        	<input id="token" type="text" maxlength="120" placeholder="支持模糊查询"/>
        	<span><input id="search" type="button" onclick="queryBuildRecordList();"/></span>
        </div>
        <div class="input3" id="buildBtnDiv"><a href="javaScript:executeBuild();">立即构建</a></div>
     </div>
</div>

<div class="main-Content">
	<div class="content-tab">
		<table cellspacing="0" id="queryBuildRecordList"></table>
		<div id="pagerBar"></div>
	</div>
</div>
</div>