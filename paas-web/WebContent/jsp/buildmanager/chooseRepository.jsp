<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/dailog.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script>

<body>
	<div class="dialog" style="padding:80px 0 0 0px;">
		<div class="dialog-configure">
			<table width="400" border="0" align="center" cellpadding="0"
				cellspacing="0" >
				<tr>
					<td width="188" height="40" style="font-size:14px;">请选择代码库类型：</td>
				</tr>
			</table>
			<table width="400" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-bottom: 30px; margin-left:50px;">
				<tr>
					<td width="188"><a
						href="/paas/build/queryBuild.action?buildEntity.type=1"  style="margin-left:30px;"><img
							src="${ctx}/imgs/svn.jpg" width="150" height="102" /></a></td>
					<td width="212" align="right"><a
						href="/paas/build/queryBuild.action?buildEntity.type=2"><img
							src="${ctx}/imgs/git.jpg" width="150" height="102" /></a></td>
				</tr>
			</table>
		</div>
	</div>
</body>