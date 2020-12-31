<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/dailog.css" rel="stylesheet" />

<script src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/imagemanager/publicimage/publicimage.js"></script>

<form id="submitForm" method="post">
	<input type="hidden" id="publicImageId" name="publicImageId" value="${publicImageId }" />
	<input type="hidden" id="versionId" name="versionId" value="${versionId }" />
	<div class="dialog">
		<div class="dialog-main">
			<div id="main-four70">
				<!-- 下拉列表（单选）
				<select name="dataCenterInfoId" id="dataCenterInfoId">
					<c:forEach items="${dataCenterInfo}" var="dataCenterInfo">
						<option value="${dataCenterInfo.id}" <c:if test="${dataCenterInfo.checked}">selected</c:if>>
							${dataCenterInfo.name}
						</option>
					</c:forEach>
				</select>
				 -->
				<div><!-- 复选框（多选） -->
					<table width="253" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="70" height="26" align="left" valign="top"><strong>*</strong>数据中心：</td>
							<td>
								<div style="border: 1px solid #c0c0c0; border-radius: 5px; margin-left: 10px; max-height:100px;  overflow: auto; overflow-x: hidden;">
									<table width="153" border="0" cellpadding="0" cellspacing="0" >
										<c:forEach items="${dataCenterInfo}" var="dataCenterInfo">
											<tr>
												<td width="32" height="26" align="center" valign="middle">
													<input type="checkbox" name="dataCenterInfoId" id="dataCenterInfoId" value="${dataCenterInfo.id}" style="width:14px; height:14px;"/>
												</td>
												<td width="121" height="26" valign="middle">
													<a href="#">${dataCenterInfo.name}</a>
												</td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="bottom">
				<input type="button" value="发布生产" onclick="publish2Product()" />
			</div>
		</div>
	</div>
</form>
