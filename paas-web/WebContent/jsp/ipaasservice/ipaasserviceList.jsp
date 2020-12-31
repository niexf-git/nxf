<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/js/appeasyui/easyui.css" rel="stylesheet"
	type="text/css" />
	
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/js/appeasyui/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/js/appeasyui/themes/icon.css" />
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/appeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/ipaasservice/ipaasservice.js"></script>
<script type="text/javascript">	
</script>

<div class="Establish">	
   	<div class="Establish-left">
   	  	<div class="name">查询基础服务：</div>
        <div class="queryService"><input name="" type="text" id="token" placeholder="支持模糊查询" onkeyup="value=value.replace(/[^A-Za-z0-9\-\_\/\.]/g,'')"/>
        <span><input id="search"  name="" type="button" onclick="queryIpaasServiceListByToken();" /></span></div>
      
        <div class="input3" id="createIpaasService" onclick="createIpaasService();">
	        <img src="${ctx}/imgs/createServie.png" width="11" height="11" />
	        <a href="#">新增服务</a>
        </div>
        <div id="switchAppInfoMsg" style="color: red;font-size: 12px; float:left; line-height:30px; margin-left:3px;">
		</div>
    </div>  
</div>	

<div class="main-Content">
    <div class="content-tab" id="ipaasserviceListDiv">
          <table cellspacing="0" id="queryIpaasserviceList" ></table>
		  <div id="pagerBar"></div>
	</div>
</div>

