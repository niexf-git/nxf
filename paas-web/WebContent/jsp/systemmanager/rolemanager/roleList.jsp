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
<script src="${ctx}/js/systemmanager/role.js"></script>

<!-- 单元格内容换行 
<style>
.ui-jqgrid tr.jqgrow td {
  white-space: normal !important;
  height:auto;
 }
</style>
-->
<style>
html,body{ overflow-x:hidden;}
.ui-jqgrid tr.jqgrow td {font-weight: normal; overflow: hidden;  height: 37px; border:none;border-style:none}
</style>

<!-- 
		<div class="image-menu">
			<div class="image-menu-content">
				<ul>
					<li><a href="${ctx}/jsp/systemmanager/usermanager/userList.jsp" target="aaa">用户管理</a></li>
					<li style="border-right:none;background:#000000; border-radius:0px 10px 10px 0px;">
						<a href="${ctx}/jsp/systemmanager/rolemanager/roleList.jsp" target="aaa">角色管理</a>
					</li>
				</ul>
			</div>
		</div>
		 -->
		<div class="Establish">	
			<div class="Establish-left">
				<div class="name70">角色名称：</div>
				<div class="queryService">
					<input id="roleName" name="roleName" type="text" maxlength="120" placeholder="支持模糊查询" onkeyup="value=value.replace(/[^A-Za-z0-9\-\_\/\.]/g,'')" />
					<span><input name="" type="button" value="" onclick="reloadResult()" /></span>
				</div>
				<div class="input3" onclick="openCreateRole()">
					<img src="${ctx}/imgs/createServie.png" width="11" height="11" />
					<a href="#">创建角色</a>
				</div>
			</div>
		</div>
		
		<div class="main-Content">
			<div class="content-tab">
				<table cellspacing="0" id="queryRoleList"></table>
				<div id="pagerBar"></div>
			</div>
		</div>
		
