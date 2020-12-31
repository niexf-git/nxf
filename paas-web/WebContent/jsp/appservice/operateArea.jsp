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
<script type="text/javascript" src="${ctx}/js/appservice/appservice.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script>
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
html,body{ overflow-x:hidden;}
</style>
<form id="submitForm" method="post" style="margin: 0px;">

	<input type="hidden" id="id" name="appService.id"
		value="${appService.id}" /> <input type="hidden" id="status"
		name="appService.status" value="${appService.status}" />

	<div class="main-Content">
		<div class="Operation">
			<ul>
				<li
					style="width: 100px; height: 20px; border-right: 1px solid #dadada; border-bottom: 1px solid #dadada; padding-bottom: 10px;"><img
					src="${ctx}/imgs/start-up.png" width="14" height="18" />启动服务：</li>
				<li style="margin-top: 20px;"><input name="" type="button"
					value="启动" onclick="startAppServiceByWebsocket()" /><span><input
						name="" type="button" value="重启"
						onclick="restartAppServiceByWebsocket()" /></span></li>
			</ul>
			<ul>
				<li
					style="width: 100px; height: 20px; border-right: 1px solid #dadada; border-bottom: 1px solid #dadada; padding-bottom: 10px;"><img
					src="${ctx}/imgs/Operationstop.png" width="15" height="16" />停止服务：</li>
				<li style="margin-top: 20px;"><span><input name=""
						type="button" value="停止" onclick="stopAppServiceByWebsocket()" /></span></li>
			</ul>
			<ul>
				<li
					style="width: 100px; height: 20px; border-right: 1px solid #dadada; border-bottom: 1px solid #dadada; padding-bottom: 10px;"><img
					src="${ctx}/imgs/Operationdelete.png" width="13" height="18" />删除服务：</li>
				<li style="margin-top: 20px;"><span><input name=""
						type="button" value="删除" onclick="deleteAppServiceByWebsocket()" /></span></li>
			</ul>
			<ul style="margin-right: 0px;">
				<li
					style="width: 100px; height: 20px; border-right: 1px solid #dadada; border-bottom: 1px solid #dadada; padding-bottom: 10px;"><img
					src="${ctx}/imgs/Signal.png" width="13" height="14" />发送信号：</li>
				<li style="width: 300px; margin-top: 20px;"><input name=""
					id="signal" type="text"
					style="border: 1px solid #c0c0c0; background: #fff; height: 26px; width: 120px; color: #000; cursor: inherit;" /><input
					name="" type="button" value="发送" onclick="sendSignal2AppService()" /></li>
			</ul>
		</div>

		<div class="Operation-main">
			<c:if test="${appService.config_effect eq '0'}">
			<c:choose>
				<c:when test="${appService.status eq '2'}">
									<h1 style="margin-bottom: 10px; margin-top: 10px;">
					<img src="${ctx}/imgs/tips.png" width="14" height="14"
						style="margin-right: 5px;" />配置未生效，请重启生效
				</h1> </c:when>
				<c:when test="${appService.status eq '1'}">
									<h1 style="margin-bottom: 10px; margin-top: 10px;">
					<img src="${ctx}/imgs/tips.png" width="14" height="14"
						style="margin-right: 5px;" />配置未生效，请启动生效
				</h1></c:when>
				</c:choose>
			</c:if>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="word-break: break-all; word-wrap: break-word;">
				<tr>
					<td width="3%"></td>
					<td width="44%"><table width="362" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td width="71" height="30" align="left" valign="middle">服务名称:</td>
								<td width="291" height="30" align="left" valign="middle">${appService.name}</td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle">所属应用:</td>
								<td height="30" align="left" valign="middle">${appService.app_name}</td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle">操作类型:</td>
								<td height="30" align="left" valign="middle"><c:choose>
										<c:when test="${appService.oper_type eq '1'}">开发</c:when>
										<c:when test="${appService.oper_type eq '2'}">测试</c:when>
										<c:when test="${appService.oper_type eq '3'}">生产</c:when>
										<c:otherwise>${appService.oper_type}</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle">部署集群:</td>
								<td height="30" align="left" valign="middle">${appService.cluster_name}</td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle">创  建  人:</td>
								<td height="30" align="left" valign="middle">${appService.user_id}</td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle">创建时间:</td>
								<td height="30" align="left" valign="middle">${appService.create_time}</td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle">描述:</td>
								<td height="30" align="left" valign="middle"><c:choose>
										<c:when test="${appService.description eq null}">无</c:when>
										<c:when test="${appService.description eq ''}">无</c:when>
										<c:otherwise>${appService.description}</c:otherwise>
									</c:choose></td>
							</tr>
						</table></td>
					<td width="53%"><table width="362" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td height="30" align="left" valign="middle">服务状态:</td>
								<td height="30" align="left" valign="middle"><c:choose>
										<c:when test="${appService.status eq '2'}">
											<img src="${ctx}/imgs/run.png" width="12" height="12"
												style="margin-right: 5px;" />正常 </c:when>
										<c:when test="${appService.status eq '1'}">
											<img src="${ctx}/imgs/stopFlag.png" width="12" height="12"
												style="margin-right: 5px;" />停止</c:when>
										<c:otherwise>${appService.status}</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle">实例个数:</td>
								<td height="30" align="left" valign="middle"><c:choose>
										<c:when test="${appService.inst_scale_type eq '1'}">${appService.running_Inst_num}/${appService.instance_num}  (运行个数/总个数) </c:when>
										<c:when test="${appService.inst_scale_type eq '2'}">${appService.running_Inst_num}/${appService.inst_min}~${appService.inst_max} (运行个数/最少~最大个数) </c:when>
										<c:otherwise>${appService.inst_scale_type}</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<td width="71" height="30" align="left" valign="middle">镜像类型:</td>
								<td width="291" height="30" align="left" valign="middle"><c:choose>
										<c:when test="${appService.image_type eq '1'}">公共镜像</c:when>
										<c:when test="${appService.image_type eq '2'}">私有镜像</c:when>
										<c:otherwise>${appService.image_type}</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle"
									style="font-weight: bold;">镜像名称:</td>
								<td height="30" align="left" valign="middle"
									style="font-weight: bold;"><a href=""
									style="color: black; text-decoration: underline;"> <c:choose>
											<c:when test="${appService.image_type eq '1'}">
												<a
													href="/paas/jsp/imagemanager/publicimage/listPublicImage.jsp?publicImageId=${appService.image_id}"
													target="oldPage"
													onclick="parent.parent.forImagePageNav('/paas/jsp/imagemanager/publicimage/listPublicImage.jsp','公共镜像');parent.parent.linkImageUpdateCss(1,4);"
													style="color: black; text-decoration: underline;">${appService.image_name}</a>
											</c:when>
											<c:when test="${appService.image_type eq '2'}">
												<a
													href="/paas/jsp/imagemanager/privateimage/listPrivateImage.jsp?imageId=${appService.image_id}"
													target="oldPage"
													onclick="parent.parent.forImagePageNav('/paas/jsp/imagemanager/privateimage/listPrivateImage.jsp','私有镜像');parent.parent.linkImageUpdateCss(1,4);"
													style="color: black">${appService.image_name}</a>
											</c:when>
											<c:otherwise>${appService.image_name}</c:otherwise>
										</c:choose>
								</a></td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle">镜像版本:</td>
								<td height="30" align="left" valign="middle">${appService.image_version}</td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle">日志路径:</td>
								<td height="30" align="left" valign="middle">${appService.log_dir}</td>
							</tr>
							<tr>
								<td height="30" align="left" valign="middle"></td>
								<td height="30" align="left" valign="middle"></td>
							</tr>
						</table></td>
				</tr>
			</table>
		</div>
	</div>

</form>