<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/dailog.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/ipaasservice/createIpaasService.js"></script>
<body>
<input type="hidden" id="appPerSelectedId" value="${appPerSelectedId}"/>
<form id="ipaasServiceForm" >   
   <div class="ipaasServiceDialog" >
   	 <!-- 基础服务基本配置 -->
  	 <table width="550" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="350">
            	  <table width="300" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="71" height="35" align="right" valign="middle"><strong>*</strong>服务名称：</td>
                        <td width="180" height="35" align="left" valign="middle">
                        	<input id="serviceName" name="ipaasService.name" type="text"  style="width:200px;"/>
                        </td>
                      </tr>
                      <tr>
                        <td height="35" align="right" valign="middle"><strong>*</strong>服务类型：</td>
                        <td height="35" align="left" valign="middle">
	                        <select id="serviceType" name="ipaasService.service_type" onchange="changeServiceType();"   style="width:200px;">
	                          <option value="1" selected="selected">zookeeper</option>
	                          <option value="2">redis</option>
	                          <option value="3">activemq</option>
	                        </select>
                        </td>
                      </tr>
                      <tr style = "display:none;">
                        <td width="71" height="35" align="right" valign="middle"><strong>*</strong>用户名称：</td>
                        <td width="180" height="35" align="left" valign="middle">
                        	<input id="user" name="ipaasService.user" type="text"  style="width:200px;"/>
                        </td>
                      </tr>
                      <tr>
                        <td height="35" align="right" valign="middle"><strong>*</strong>节点规模：</td>
                        <td height="35" align="left" valign="middle">
                        	<select id="nodeNumber" name="ipaasService.instance_num"   style="width:200px;"></select>
                        </td>
                      </tr>
                      <tr>
                        <td height="35" align="right" valign="middle"><strong>*</strong>镜像名称：</td>
                        <td height="35" align="left" valign="middle">
                        	<select id="imageId" name="ipaasService.image_id"   style="width:200px;"></select>
                        </td>
                      </tr>
                  </table>
            </td>
            <td width="250">
	           	  <table width="250" border="0" cellspacing="0" cellpadding="0">
	                      <tr>
                           <td height="35" align="right" valign="middle"><strong>*</strong>集群名称：</td>
                           <td height="35" align="left" valign="middle">
                           	<select id="clusterId" name="ipaasService.clusterId"  style="width:143px;">
	                          
	                        </select>
                           </td>
                         </tr> 
	                      <tr>
	                        <td width="91" height="35" align="right" valign="middle"><strong>*</strong>cpu：</td>
	                        <td width="190" height="35" align="left" valign="middle">
		                         <input id="avgCpu" name="ipaasService.cpu" type="text" style="width:60px;" value="0.5" placeholder="均值"/>
		                        -<input id="peakCpu" name="ipaasService.maxCpu" type="text" style="width:60px;" value="1"   placeholder="最大值"/>
	                        </td>
	                      </tr>
	                      <tr>
	                        <td height="35" align="right" valign="middle"><strong>*</strong>内存：</td>
	                        <td width="190" height="35" align="left" valign="middle">
		                         <input id="avgMemory" name="ipaasService.mem" type="text" style="width:60px;" value="256"  placeholder="均值"/>
		                        -<input id="peakMemory" name="ipaasService.maxMem" type="text" style="width:60px;" value="1024" placeholder="最大值"/>
		                    </td>
	                      </tr>  
	                      <tr style = "display:none;">
	                        <td width="61" height="35" align="right" valign="middle"><strong>*</strong>密码：</td>
	                        <td width="190" height="35" align="left" valign="middle">
		                         <input id="pwd" name="ipaasService.pwd" type="password"  style="width:142px;" placeholder="字母，数字，6-16位"/>
	                        </td>
	                      </tr>                    
	          
                              <tr>
	                        <td height="35" align="right" valign="middle">描述：</td>
	                        <td height="35" align="left" valign="middle"><textarea name="ipaasService.description" maxlength="120" placeholder="最多输入120个字符" style="width:139px; height:23px;font-size:12px;  resize: none; border:1px solid #c0c0c0; margin-left:8px;border-radius:5px;"></textarea></td>
	                      </tr>
	                                           
	              </table>
            </td>
          </tr>
	</table>
	<!-- 导入配置文件 -->
	<table width="550" border="0" cellspacing="0" cellpadding="0">
 		<tr valign="middle">
			<td width="10" height="36" style="font-size:14px;">&nbsp;</td>
			<td width="238" height="36" style="font-size:14px;"><img src="${ctx}/imgs/import.png" width="14" height="14" style="margin-right:5px;"  />导入配置文件：</td>
        </tr>
	    <tr>
			<td width="10" height="26" valign="top">&nbsp;</td>
			<td valign="top">
				<form id="form2" name="form2" enctype="multipart/form-data" method="post" action="">
				    <label for="configFile"></label>
				    <input type="file" name="configFile" id="configFile" style="background:none; border:none; width:180px;" accept=".conf,.cfg,.txt,.properties" onchange="fileSelected();"/>
                </form>
            </td>
            <td><input name="" type="button" value="" onclick="uploadConfigFile();" style=" background:url(${ctx}/imgs/import-Alarm.png) no-repeat; width:68px; height:24px; border:none; font-family:'微软雅黑'; font-size:14px; color:#fff; cursor:pointer;" /></td>
	    </tr>
	</table>
	<!-- 展示配置文件 -->
    <table width="550" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td height="30" style="font-size:14px;"><strong>*</strong>配置文件内容：</td>
	  </tr>
	  <tr>
	    <td><textarea id="configInfo" name="ipaasService.config" style="width:537px; height:124px; resize: none; border:1px solid #c0c0c0; border-radius:5px;overflow-x:hidden;"></textarea></td>
	  </tr>
	  <tr>
	    <td height="30" style="font-size:14px; color:#1791d5;"><img src="${ctx}/imgs/tips.png"/>日志路径、数据路径、客户端连接端口不允许修改，即使修改了也不生效！</td>
	  </tr>
	</table>
	<!-- 保存基础服务 -->
    <div class="bottom" style="padding-left:210px;"><input type="button" value="保存" onclick="saveIpaasService();"/></div> 
  </div>
  <div id="loadImg" style="display:none"><img alt="" src="${ctx}/imgs/load.gif"></img></div>
</form>
</body>
