<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/index.css" rel="stylesheet" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet" />

<script src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script src="${ctx}/js/grid.locale-cn.js"></script>
<script src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script src="${ctx}/js/imagemanager/privateimage/privateimageversion.js"></script>
<script src="${ctx}/js/utils.js"></script><!-- getUrlParam页面传参 -->
<!-- 单元格内容换行 -->
<style>
	.ui-jqgrid tr.jqgrow td {
	  	white-space: normal !important;
	    //height:auto;
	 }
</style>
<%
	//获取当前用户角色类型：
	String roleType = session.getAttribute("roleType")+"";
	String opertype = session.getAttribute("opertype")+"";
	String selectedOpertype = session.getAttribute("selectedOpertype") + "";
%>
		<input id="roleType" name="roleType" value="${roleType }" hidden="true" >
		<input id="opertype" name="opertype" value="${opertype }" hidden="true" >
		<input id="selectedOpertype" name="selectedOpertype" value="${selectedOpertype }" hidden="true" >
		<!-- 
    	<div class="image-menu">
        	<div class="image-menu-content">
            	<ul>
                	<li style="background:#000000; border-radius:10px 0 0 10px;">
                		<a href="${ctx}/jsp/imagemanager/privateimage/listPrivateImage.jsp" id="privateImage" target="aaa">私有镜像</a>
                	</li>
                    <li style="border-right:none;">
                    	<a href="${ctx}/jsp/imagemanager/publicimage/listPublicImage.jsp" target="aaa">公共镜像</a>
                    </li>
               </ul>
          	</div>
         </div>
          -->
    	<div class="Establish">
        	<div class="Establish-left">
	       	  	<div class="name">私有镜像版本：</div>
	            <div class="queryService">
	            	<input type="text" id="privateImageVersion" name="privateImageVersion" maxlength="120" placeholder="支持模糊查询" onkeyup="value=value.replace(/[^A-Za-z0-9\-\_\/\.]/g,'')" />
	            	<span><input name="" type="button" value="" onclick="reloadResult()" /></span>
	            </div>
            </div>
      	</div>
		
		<div class="main-Content">
			<div class="content-tab">
				<table cellspacing="0" id="privateImageVersionList"></table>
				<div id="pagerBar"></div>
			</div>
		</div>

 
