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
<script type="text/javascript" src="${ctx}/js/monitoroperation/globalMonitoring.js"></script>
	
	



<style>
body,html { overflow-x:hidden;}
</style>


	
   	  <div class="Monitor-main">
        <div class="Monitor-main-right">
                <div class="Monitor-query">
                	<div style="width:220px; float:left;"><span>数据中心：</span><select id='dataCenter' onchange = 'dataCenterChange()'></select></div>
                    <div style="width:220px; float:left;"><span>集群：</span><select id='cluster' onchange ='clusterChange()'><option value="">请选择</option></select></div>
                    <div style="width:220px; float:left;"><span>主机：</span><select id='node'><option value="">请选择</option></select></div>
                    <div><input onclick='search()' id="search" type="button" value="" style="background:url(/paas/imgs/queryLog.png) no-repeat; width:68px; height:24px; border:none;cursor:pointer;"></div>
                </div>
          <div class="Monitor-right-tab">
                <div class="content-tab">
                	<table border="0" cellspacing="0" cellpadding="0" id='globalMonitorTable'>    
		             </table>
		             <div id="pagerBar"></div>
                </div>
           </div>
        </div>
      </div>	
