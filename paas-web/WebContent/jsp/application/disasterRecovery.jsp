<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript"
	src="${ctx}/js/application/application.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>


<div class="main-Content">
<form id="dnsForm">		
<input type="hidden" id="appId" name="dnsModel.appId" value="${dnsModel.appId}">
<input type="hidden" id="appName" name="dnsModel.appName" value="${dnsModel.appName}">
			<div class="dialog-configure" style=" padding-left: 0px; width: 1077px;">
			<table width="1077" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="800"><strong style="margin-right:8px;padding-left:20px; float:left;"><img src="${ctx}/imgs/tips.png" /></strong>
					<span style=" width:500px; float:left;">容灾配置说明，以下三列分别代表含义：第一列为域名，第二列为当前集群IP地址(被容灾环境IP)，第三列为出现问题容灾的集群IP(容灾环境IP)，
					列头命名规则为数据中心_集群名 (如未配置迁移集群 第三列列头为空！)</span></td>
					
					<td width="92" align="left"><input type="button"
						onclick="addEnvs('${dnsModel.appName}');" value=""
						style="background:url(${ctx}/imgs/add-Alarm.png) no-repeat;" /></td>
					<td width="120" align="left"><input type="button"
						onclick="deleteDns();" value=""
						style="background:url(${ctx}/imgs/Log-delete.png) no-repeat;" /></td>
					
				</tr>
			</table>
			<table id="dnsConfTab" width="1077" border="0" cellspacing="0"
				cellpadding="0" style="margin-top: 10px" class="configure-list">
				<tr>
					<td width="243" height="30" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #c0c0c0;">域名</td>
					<td width="344" height="30" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #c0c0c0;">${dnsModel.hostIpName}</td>
					<td width="345" height="30" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #c0c0c0;">${dnsModel.spareIpName}</td>
					<td width="145" height="30" align="center" valign="middle"
						style="font-weight: bold; border-top: 1px solid #c0c0c0;">操作</td>

				</tr>
				<c:choose>
  				 <c:when test="${fn:length(dnsModel.dnsList)==0}"> 
				         <tr  class="_tr0" id="dnsTabTr0">
							<td width="243" height="30" align="center" valign="middle"><input
									type="text" name="dnsDomainName" id="dnsConfKey0"
									value="" style="width: 150px; margin-right:0px;" />.${dnsModel.appName}</td>
							<td width="344" height="30" align="center" valign="middle"><input
									type="text" name="dnsHostIp"
									id="dnsConfValue0" value=""
									style="widht: 200px;" /></td>
						   <td width="345" height="30" align="center" valign="middle"><input
									type="text" name="dnsSpareIp"
									id="dnsConfValue0" value=""
									style="widht:200px;" /></td>
							<td width="145" height="30" align="center" valign="middle">
									<a href="JavaScript:void(0);"><img
										src='/paas/imgs/delete.png' width='22' height='22'
										border='none' /></a>
							</td>
						</tr>
				   </c:when>
				   <c:otherwise>
				 		<c:forEach items="${dnsModel.dnsList}" var="item" varStatus="status">
				 			<tr  class="_tr" id="dnsTabTr${status.index}">
								<td width="243" height="30" align="center" valign="middle"><input
										type="text" name="dnsDomainName" id="dnsConfKey${status.index}"
										value="${item.domainName}" style="width: 150px; margin-right:0px;" />.${dnsModel.appName}</td>
								<td width="344" height="30" align="center" valign="middle"><input
										type="text" name="dnsHostIp"
										id="dnsConfValue${status.index}" value="${item.hostIp}"
										style="widht: 200px;" /></td>
							   <td width="345" height="30" align="center" valign="middle"><input
										type="text" name="dnsSpareIp"
										id="dnsConfValue${status.index}" value="${item.spareIp}"
										style="widht: 200px;" /></td>
								<td width="145" height="30" align="center" valign="middle">
									
										<a href="JavaScript:deleteDnsConf(${status.index});"><img
											src='/paas/imgs/delete.png' width='22' height='22'
											border='none' /></a>
									
								</td>
							</tr>
				 		</c:forEach>
				   </c:otherwise>
				</c:choose>
					
				
			</table>
			<div class="bottom" style="margin-left: 20px;">
				
				<input type="button" value="保存" id="sub" onclick="saveDns();" />
			</div>
			</div>
			<div id="loadImg" style="display:none"><img alt="" src="${ctx}/imgs/load.gif"></img></div>
			</form>
		</div>
