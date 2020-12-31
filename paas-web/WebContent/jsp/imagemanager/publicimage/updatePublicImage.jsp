<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/css/dailog.css" rel="stylesheet" />

<script src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/imagemanager/publicimage/publicimage.js"></script>

<form id="submitForm" method="post">
	<input type="hidden" id="id" name="publicImage.id" value="${publicImage.id }" />
	<div class="dialog">
		<div class="dialog-main" style="width:553px;">
			<table width="500" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="105" height="40" align="right" valign="middle"><strong>*</strong>镜像名称：</td>
    <td width="370" height="40" valign="middle"><input type="text" id="name" name="publicImage.name" value="${publicImage.name }" disabled="disabled" style="width:220px;" /></td>
  </tr>
  <tr>
    <td height="40" align="right" valign="middle">&nbsp;&nbsp;配置文件路径：</td>
    <td height="40" valign="middle"><input type="text" id="configFilePath" name="publicImage.configFilePath" value="${publicImage.configFilePath }"  style="width:220px;"/></td>
  </tr>
  <tr>
    <td height="40" align="right" valign="middle">&nbsp;&nbsp;日志路径：</td>
    <td height="40" valign="middle"><input type="text" id="logDir" name="publicImage.logDir" value="${publicImage.logDir }"  style="width:220px;"/></td>
  </tr>
  <tr>
    <td height="40" align="right" valign="middle">&nbsp;&nbsp;启动命令：</td>
    <td height="40" valign="middle"><input type="text" id="startCmd" name="publicImage.startCmd" value="${publicImage.startCmd }"  style="width:220px;"/></td>
  </tr>
  <tr>
    <td height="40" align="right" valign="middle">&nbsp;&nbsp;&nbsp;描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</td>
    <td height="40" valign="middle"><input type="text" id="description" name="publicImage.description" value="${publicImage.description }"  style="width:220px;"/></td>
  </tr>
</table>
			<div class="bottom">
				<input name="" type="button" value="确定" onclick="updatePublicImageSumbit()" />
			</div>
		</div>
	</div>
</form>
