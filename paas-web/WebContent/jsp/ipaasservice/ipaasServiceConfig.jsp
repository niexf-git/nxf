<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/dailog.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/ipaasservice/ipaasServiceConfig.js"></script>
<%
	String errorInfo = (String)request.getAttribute("errorMsg");// 获取错误属性
	if(errorInfo != null) {
%>
<script type="text/javascript">
parent.parent.alertError("<%=errorInfo%>");
</script>	
<%
	}
%>
<input type="hidden" id="appPerSelectedId" value="${ipaasService.app_id}"/>
<input type="hidden" id="cId" value="${ipaasService.clusterId}"/>
<form id ="ipaasServiceConfigForm">
	<div class="main-Content">
    	<div class="Operation-main">
    		<input id ="serviceTypeHidden" value="${ipaasService.service_type }" type="hidden" />
    		<input id ="nodeNumberHidden" value="${ipaasService.instance_num }" type="hidden" />
    		<input id ="imageIdHidden" value="${ipaasService.image_id }" type="hidden" />
    		<input id ="imageVersionIdHidden" value="${ipaasService.image_version_id }" type="hidden" />
    		<input name="ipaasService.id" value="${ipaasService.id }" type="hidden"/>
  	 		<table width="900" border="0" cellspacing="0" cellpadding="0" class="ipaasServiceConfig">
		         <tr>
		            <td width="451">
		            	<table width="251" border="0" cellspacing="0" cellpadding="0">
		                      <tr>
		                        <td height="30" align="right" valign="middle">服务类型：</td>
		                        <td height="30" align="left" valign="middle">
		                        <c:choose>
		                        	<c:when test="${ipaasService.service_type eq 1}">&nbsp;&nbsp; zookeeper </c:when>
		                        	<c:when test="${ipaasService.service_type eq 2}">&nbsp;&nbsp; redis </c:when>
		                        	<c:when test="${ipaasService.service_type eq 3}">&nbsp;&nbsp; activemq </c:when>
		                        	<c:otherwise>${ipaasService.service_type}</c:otherwise>
		                        </c:choose>
		                        	<%-- <select id="serviceType" name="ipaasService.service_type" onchange="changeServiceType();" disabled="disabled"></select> --%>
		                        </td>
		                      </tr>
		                      <c:choose>
		                        	<c:when test="${ipaasService.service_type eq 3}">
				                      <tr>
				                        <td height="30" align="right" valign="middle">用户名称：</td>
				                        <td height="30" align="left" valign="middle">
				                       <input id="user" name="ipaasService.user" type="text" value="${ipaasService.user}" style="width:139px;"/>
				                        </td>
				                      </tr>
		                      		</c:when>
		                      </c:choose>
		                      <tr>
		                        <td height="30" align="right" valign="middle">节点规模：</td>
		                        <td height="30" align="left" valign="middle">&nbsp;&nbsp; ${ipaasService.instance_num}
		                        	<%-- <select id="nodeNumber" name="ipaasService.instance_num" disabled="disabled"></select> --%>
		                        </td>
		                      </tr>
		                      <tr>
		                        <td height="30" align="right" valign="middle"><strong>*</strong>镜像名称：</td>
		                        <td height="30" align="left" valign="middle">
		                        	<select id="imageId" name="ipaasService.image_id"></select>
		                        </td>
		                      </tr>
		                      <tr>
		                        <td height="30" align="right" valign="middle"></td>
		                        <td height="30" align="left" valign="middle"></select>
		                        </td>
		                      </tr>
		                </table>
		            </td>
		            <td width="329">
			           	<table width="271" border="0" cellspacing="0" cellpadding="0" >
		                      <tr>
		                        <td height="30" width="78" align="right" valign="middle"><strong>*</strong>集群名称：</td>
		                        <td height="30" align="left" valign="middle">
		                        	<select id="clusterId" name="ipaasService.clusterId" style="width:149px;"></select>
		                        </td>
		                      </tr>
		                      <tr>
		                        <td width="61" height="30" align="right" valign="middle"><strong>*</strong>cpu：</td>
		                        <td width="190" height="30" align="left" valign="middle">
		                        <input id="avgCpu" name="ipaasService.cpu" type="text" value="${ipaasService.cpu}" />
		                       -<input id="peakCpu" name="ipaasService.maxCpu" type="text" value="${ipaasService.maxCpu}" /></td>
		                      </tr>
		                      <c:choose>
		                        	<c:when test="${ipaasService.service_type eq 3}">
				                      <tr>
				                        <td width="61" height="30" align="right" valign="middle"><strong>*</strong>密码：</td>
				                        <td width="190" height="30" align="left" valign="middle">
				                        <input id="pwd" name="ipaasService.pwd" type="password" value="${ipaasService.pwd}" style="width:147px;"/></td>
				                      </tr>
				                     </c:when>
				               </c:choose>
		                      <tr>
		                        <td height="30" align="right" valign="middle"><strong>*</strong>内存：</td>
		                        <td width="190" height="30" align="left" valign="middle">
		                        <input id="avgMemory" name="ipaasService.mem" type="text"  value="${ipaasService.mem}" />
		                       -<input id="peakMemory" name="ipaasService.maxMem" type="text" value="${ipaasService.maxMem}" /></td>
		                      </tr>                      
		                      <tr>
		                        <td height="30" align="right" valign="middle">描述：</td>
		                        <td height="30" align="left" valign="bottom">&nbsp;&nbsp;<textarea name="ipaasService.description" maxlength="120" placeholder="最多输入120个字符" style="width:148px; height:30px; resize: none;border:1px solid #c0c0c0;border-radius:5px; margin-top:5px;">${ipaasService.description}</textarea></td>
		                      </tr>                      
			            </table>
		            </td>
		        </tr>
		</table>
		<table width="500" border="0" cellspacing="0" cellpadding="0">
	 		<tr valign="middle">
				<td width="10" height="36" style="font-size:14px;">&nbsp;</td>
				<td width="238" height="36" style="font-size:14px;">导入配置文件：</td>
                <td></td>
		    </tr>
			<tr>
				<td width="10" height="26" valign="top">&nbsp;</td>
				<td height="40" valign="top">
					<form id="form2" name="form2" enctype="multipart/form-data" method="post" action="">
				    	<label for="configFile"></label>
				  		<input type="file" name="configFile" id="configFile" style=" background:none; border:none; width:280px;" accept=".conf,.cfg,.txt,.properties" onchange="fileSelected();"/>
					</form>
				</td>
                <td height="40" valign="top">
                	<input type="button" value="" onclick="uploadConfigFile();" style="background:url(${ctx}/imgs/import-Alarm.png) no-repeat; width:68px; height:24px; border:none; font-family:'微软雅黑'; font-size:14px; color:#fff; cursor:pointer;" />
                </td>
		    </tr>
		</table>
        <table width="500" border="0" cellspacing="0" cellpadding="0">
			<tr>
			    <td height="30" style="font-size:14px;"><strong>*</strong>配置文件信息：</td>
			</tr>
			<tr>
			    <td><textarea id="configInfo" name="ipaasService.config" style="width:735px; height:120px; resize: none; border:1px solid #c0c0c0;border-radius:5px;">${ipaasService.config}</textarea></td>
		    </tr>
		    <tr>
	    		<td height="30" style="font-size:14px; color:#1791d5;"><img src="${ctx}/imgs/tips.png"/>日志路径、数据路径、客户端连接端口不允许修改，即使修改了也不生效！</td>
	  		</tr>
		</table>
	    <div class="bottom" style="padding-left:0px;"><input type="button" value="保存" onclick="saveIpaasService();"/></div> 
    </div>
  </div>
</form>


