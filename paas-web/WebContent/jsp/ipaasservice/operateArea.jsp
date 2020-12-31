<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/appeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/ipaasservice/ipaasservice.js"></script>
<link href="${ctx}/js/appeasyui/icon.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/js/appeasyui/easyui.css" rel="stylesheet"
	type="text/css" />

<%
	String errorInfo = (String) request.getAttribute("errorMsg");// 获取错误属性
	String errorCode = (String) request.getAttribute("errorCode");
	if (errorInfo != null) {
%>
<script type="text/javascript">
parent.parent.alertError("<%=errorCode%>","<%=errorInfo%>"); // 弹出错误信息
</script>
<%
	}
%>
<style>
	 html,body{ overflow-x:hidden; overflow-y:auto;}
</style>
<form id="submitForm" method="post" style="margin: 0px;">
	<input type="hidden" id="id" name="ipaasService.id"
		value="${ipaasService.id}" /> <input type="hidden" id="status"
		name="ipaasService.status" value="${ipaasService.status}" />

	<div class="main-Content">
		<div class="ipaas-Operation">
			<ul>
				<li
					style="width: 100px; height: 20px; border-right: 1px solid #dadada; border-bottom: 1px solid #dadada; padding-bottom: 10px;"><img
					src="${ctx}/imgs/start-up.png" width="14" height="18" />启动服务：</li>
				<li style="margin-top: 20px;"><input name="startOrStop"
					type="button" value="启动"
					onclick="startIpaasService('${ipaasService.id}','${ipaasService.status}')" /><span><input
						name="startOrStop" type="button" value="重启"
						onclick="restartIpaasService('${ipaasService.id}','${ipaasService.status}')" /></span></li>
			</ul>
			<ul>
				<li
					style="width: 100px; height: 20px; border-right: 1px solid #dadada; border-bottom: 1px solid #dadada; padding-bottom: 10px;"><img
					src="${ctx}/imgs/Operationstop.png" width="15" height="16" />停止服务：</li>
				<li style="margin-top: 20px;"><span><input
						name="startOrStop" type="button" value="停止"
						onclick="stopIpaasService('${ipaasService.id}','${ipaasService.status}')" /></span></li>
			</ul>
			<ul>
				<li
					style="width: 100px; height: 20px; border-right: 1px solid #dadada; border-bottom: 1px solid #dadada; padding-bottom: 10px;"><img
					src="${ctx}/imgs/Operationdelete.png" width="13" height="18" />删除服务：</li>
				<li style="margin-top: 20px;"><span><input name=""
						type="button" value="删除" onclick="del('${ipaasService.id}')" /></span></li>
			</ul>

		</div>
		<div class="Operation-main">
			<c:if test="${ipaasService.config_effect eq '0'}">
				<c:choose>
					<c:when test="${ipaasService.status eq '2'}">
						<h1 style="margin-bottom: 10px; margin-top: 10px;">
							<img src="${ctx}/imgs/tips.png" width="14" height="14"
								style="margin-right: 5px;" />配置未生效，请重启生效
						</h1>
					</c:when>
					<c:when test="${ipaasService.status eq '1'}">
						<h1 style="margin-bottom: 10px; margin-top: 10px;">
							<img src="${ctx}/imgs/tips.png" width="14" height="14"
								style="margin-right: 5px;" />配置未生效，请启动生效
						</h1>
					</c:when>
				</c:choose>
			</c:if>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="word-break: break-all; word-wrap: break-word;">
				<tr>
					<td width="3%"></td>
					<td width="44%" valign="top"><table width="362" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<td width="71" height="30" align="left" valign="middle">服务名称:
								</td>
								<td width="291" height="30" align="left" valign="middle">${ipaasService.name}</td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle">所属应用:</td>
								<td height="30" align="left" valign="middle">${ipaasService.app_name}</td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle">操作类型:</td>
								<td height="30" align="left" valign="middle"><c:choose>
										<c:when test="${ipaasService.oper_type eq '1'}">开发</c:when>
										<c:when test="${ipaasService.oper_type eq '2'}">测试</c:when>
										<c:when test="${ipaasService.oper_type eq '3'}">生产</c:when>
										<c:otherwise>${ipaasService.oper_type}</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle">部署集群:</td>
								<td height="30" align="left" valign="middle">${ipaasService.clusterName}</td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle">服务类型:</td>
								<td height="30" align="left" valign="middle"><c:choose>
										<c:when test="${ipaasService.service_type eq '0'}">全部</c:when>
										<c:when test="${ipaasService.service_type eq '1'}">zookeeper</c:when>
										<c:when test="${ipaasService.service_type eq '2'}">redis</c:when>
										<c:when test="${ipaasService.service_type eq '3'}">avtivemq</c:when>
										<c:otherwise>${ipaasService.service_type}</c:otherwise>
									</c:choose></td>
							</tr>

							<tr>
								<td height="30" align="left" valign="middle">创  建  人:</td>
								<td height="30" align="left" valign="middle">${ipaasService.user_id}</td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle">创建时间:</td>
								<td height="30" align="left" valign="middle">${ipaasService.create_time}</td>
							</tr>						
						</table></td>
					<td width="550" valign="top"><table width="550" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<td width="70" height="30" align="left" valign="middle">服务状态:</td>
								<td width="400" height="30" align="left" valign="middle"><c:choose>
										<c:when test="${ipaasService.status eq '2'}">
											<img src="${ctx}/imgs/run.png" width="12" height="12"
												style="margin-right: 5px;" />正常 </c:when>
										<c:when test="${ipaasService.status eq '1'}">
											<img src="${ctx}/imgs/stopFlag.png" width="12" height="12"
												style="margin-right: 5px;" />停止</c:when>
										<c:otherwise>${ipaasService.status}</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<td width="68" height="30" align="left" valign="middle">实例个数:</td>
								<td width="400" height="30" align="left" valign="middle">${ipaasService.running_Inst_num}/${ipaasService.instance_num}  (运行个数/总个数)</td>
							</tr>
							<tr>
								<td width="68" height="30" align="left" valign="middle">url:</td>
								<td width="450" height="30" align="left" valign="middle"
									style="width: 450px;"><div id="operateArea-url" style="line-height:15px;">${ipaasService.service_url}</div></td>
							</tr>
							<c:choose>
								<c:when test="${ipaasService.service_type eq '3'}">
									<tr>
										<td width="68" height="30" align="left" valign="middle">user:</td>
										<td width="400" height="30" align="left" valign="middle">${ipaasService.user}</td>
									</tr>
								</c:when>
							</c:choose>
							
							<tr>
								<td width="68" height="30" align="left" valign="middle">pwd:</td>
								<td width="400" height="30" align="left" valign="middle">${ipaasService.service_pwd}</td>
							</tr>
							<tr>
								<td width="68" height="30" align="left" valign="middle"
									style="font-weight: bold;">镜像名称:</td>
								<td width="400" height="30" align="left" valign="middle"
									style="font-weight: bold;"><a
									href="/paas/jsp/imagemanager/publicimage/listPublicImage.jsp?publicImageId=${ipaasService.image_id}"
									target="oldPage" onclick="parent.parent.forImagePageNav('/paas/jsp/imagemanager/publicimage/listPublicImage.jsp','公共镜像');parent.parent.linkImageUpdateCss(2,4);"
									style="color: black">${ipaasService.image_name}</a></td>
							</tr>
							<tr>
								<td width="68" height="30" align="left" valign="middle">镜像版本:</td>
								<td width="400" height="30" align="left" valign="middle"><div
										style="width: 400px;">${ipaasService.image_version}</div></td>
							</tr>
							<tr>
								<td width="68" height="30" align="left" valign="middle">描述:</td>
								<td width="400" height="30" align="left" valign="middle">
								<c:choose>
										<c:when test="${ipaasService.description eq null}">无</c:when>
										<c:when test="${ipaasService.description eq ''}">无</c:when>
										<c:otherwise>${ipaasService.description}</c:otherwise>
									</c:choose></td>
							</tr>
						</table></td>
				</tr>
			</table>
			<div class="box">
				<h1
					style="margin-bottom: 10px; margin-top: 10px; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;关联的服务：</h1>
				<table width="560" border="0" cellspacing="0" cellpadding="0"
					style="border: 1px solid #c0c0c0;">
					<tr>
						<td width="94" height="30" align="center" valign="middle"
							style="border-bottom: 1px solid #c0c0c0; border-right: 1px solid #c0c0c0;">序号</td>
						<td width="11">&nbsp;</td>
						<td width="293" height="30" align="left" valign="middle">服务名称</td>
					</tr>

					<c:forEach var="appService" items="${ipaasService.appServices}"
						varStatus="status">
						<tr>
							<td height="30" align="center" valign="middle"
								style="border-bottom: 1px solid #c0c0c0; border-right: 1px solid #c0c0c0;">${status.count}</td>
							<td>&nbsp;</td>
							<td height="30" align="left" valign="middle">${appService.name}</td>
						</tr>
					</c:forEach>

				</table>
			</div>
		</div>
	</div>

</form>