<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script>
<script type="text/javascript">

</script>
<style>
html,body{ overflow-x:hidden;}
.ExampleMain{
	background:#fff;
	padding:20px;
	border-radius:5px;
	box-decoration-break:
	}
.ExampleTit{
	font-size:14px;
	font-weight:bold;
	margin:0px;
	margin-bottom:10px;
}
</style>
<div class="ant-card-bordered"
	style="float: left; margin-top: 0px; width: 100%;">
	<div class="ExampleMain">
		<h1 class="ExampleTit">
			<span
				style="width: 4px; height: 12px; border-radius: 5px; background: #f7b266; display: block; float: left; margin-right: 10px;"></span>POD信息
		</h1>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			style="border: none; border-collapse: inherit;">
			<tr>
				<td width="5%" height="40" align="left" style="color: #666;" nowrap="nowrap">节点IP：</td>
				<td width="23%" height="40" style="color: #000;" nowrap="nowrap">${instanceEntity.nodeName}</td>
				<td width="6.5%" height="40" align="left" style="color: #666;" nowrap="nowrap">容器IP：</td>
				<td width="20%" height="40" width="20%" style="color: #000;" nowrap="nowrap">${instanceEntity.podIP}</td>
				<td width="10%" height="40" align="right" style="color: #666;" nowrap="nowrap">所在集群：</td>
				<td width="30%" height="40" style="color: #000;" nowrap="nowrap">${instanceEntity.nodeSelector}</td>
			</tr>
			<tr>
				<td height="40" width="5%" align="left" style="color: #666;" nowrap="nowrap">状态：</td>
				<td style="color: #000;" nowrap="nowrap">${instanceEntity.phase}</td>
				<td width="6.5%" style="color: #666;" nowrap="nowrap">开始时间：</td>
				<td nowrap="nowrap">${instanceEntity.startTime}</td>
				<td width="17%" align="right" style="color: #666;" nowrap="nowrap">运行时间：</td>
				<td style="color: #000;" nowrap="nowrap">${instanceEntity.runningTime}</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<h1 class="ExampleTit" style="margin-top: 10px; max-height:180px; overflow-x:hidden; overflow-y:auto;">
			<span style="width: 4px; height: 12px; border-radius: 5px; background: #f7b266; display: block; float: left; margin-right: 10px;"></span>事件信息
		</h1>
		<div style="border: none; border-collapse: inherit; background:#ebebeb; padding:15px; max-height:200px; overflow-x:hidden;overflow-y:auto;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
			<td width="10%" height="30" align="center" style="color: #000;">顺序</td>
				<td width="15%" height="30" align="center" style="color: #000;">事件类型</td>
				<td width="15%" height="30" align="center" style="color: #000;">事件</td>
				<td width="50%" height="30" align="center" style="color: #000;">详细描述</td>

			</tr>
			<c:forEach items="${instanceEntity.events}" var="item" varStatus="index">
			<tr style="border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">
			    <td width="10%" height="30" align="center" style="color: #666;"><c:out value="${index.count}"></c:out></td>
				<td width="15%" height="30" align="center" style="color: #666;">${item.type}</td>
				<td height="30" width="15%" align="center" style="color: #666;">${item.reason}</td>
				<td width="50%" align="center" style="color: #666;">${item.message}</td>
			</tr >
			</c:forEach>
		</table>
		</div>
	</div>
</div>