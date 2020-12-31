<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/dailog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script>
<script type="text/javascript" src="${ctx}/js/appservicegray/grayrelease.js"></script>
<script type="text/javascript" src="${ctx}/js/ion.rangeSlider.min.js"></script>
<link href="${ctx}/css/rangeSlider/ion.rangeSlider.skinHTML5.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/rangeSlider/ion.rangeSlider.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(function(){
	var instance_num = $("#instance_num").val();
	function setInstanceNum(obj){
		$("#grayInstanceNum").val(obj.from);
    	$("#totalInstanceNum").val(obj.to);
	}
	$('#rangeSlider').ionRangeSlider({
		type:'double',
	    min: 0,
	    max: instance_num*2,
	    from: 0,
	    to: instance_num,
	    onStart : setInstanceNum,
	    onChange: setInstanceNum,
	    onFinish:function finish(obj){
	    	var maxNumber = (obj.max*2);
	    	var maxNumber2 = (obj.max/2);
	    	if(obj.to==obj.max){
	    		slider.update({
	    			max : maxNumber>100?100:maxNumber
	    		});
	    		slider.reset();
	    	} else if(obj.to < maxNumber2){
	    		slider.update({
	    			max : (obj.to*2)
	    		});
	    		slider.reset();
	    	}
	    }
	}); 
	var slider = $("#rangeSlider").data("ionRangeSlider");
});
</script>
<!-- 单元格内容换行 -->
<style>
	.ui-jqgrid tr.jqgrow td {
	  	white-space: normal !important;
	  	word-wrap:break-word;
	  	
	 }
	 html,body{ overflow-x:hidden;}
</style>

<body>
<!-- 灰度版本区 -->
<div class="dialog" style="">
<form id="createGrayVersionForm">
<input type="hidden" name="appId" id="appId" value="<%=request.getParameter("id")%>"/>
<input type="hidden" id="id" value="<%=request.getParameter("appId")%>"/>
<input type="hidden" id="operType" value="<%=request.getParameter("operType")%>"/>
<input type="hidden" id="runVersion" name="runVersion" value="<%=request.getParameter("runVersion")%>"/>
<input type="hidden" id="instance_num" value="<%=request.getParameter("instance_num")%>"/>
<input type="hidden" id="appServiceName" value="<%=request.getParameter("appServiceName")%>"/>
<input type="hidden" id="oper" value="create"/>
  <div class="dialog-title">
  	<div class="title-mc">
    	<ul>
    	  <li id="li1" style="width:123px; background:#ccc; height:30px;"><a href="javascript:void(0);">创建灰度版本</a></li>
          <li id="li3" style="border-right: none;"><a href="javascript:void(0);">配置文件</a></li>
          <li id="li2" style="border-right: none;"><a href="javascript:void(0);">环境变量</a></li>
       </ul>
   </div>
   </div>
    <div class="dialog-main" style="background:none;">
  	<table width="500" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="69" height="40"><strong>*</strong>镜像类型：</td>
	    <td width="168" height="40"><select name="imageType" id="imageType" onchange="imageTypeChange();">
	      <option value="2">私有镜像</option>
	      <option value="1">公有镜像</option>
	    </select></td>
	    <td width="35" height="40">&nbsp;</td>
	    <td width="69" height="40"><strong>*</strong>镜像名称：</td>
	    <td width="159" height="40"><select id="image_name" name="imageId" onchange="imageNameChange();">
	      <option selected="selected">请选择</option>
	      <option>paasontrller/jtg</option>
	    </select></td>
	  </tr>
  <tr>
    <td height="30"><strong>*</strong>镜像版本：</td>
    <td height="30">
    <select id="image_version" name="imageVersionId">
    	<option selected="selected">请选择</option>
    </select>
    </td>
    <td height="30">&nbsp;</td>
    <td height="30"><strong>*</strong>日志路径：</td>
    <td height="30"><input name="logDir" type="text" id="log_dir"/></td>
  </tr>
</table>
<input type="hidden" name="grayInstanceNum" id="grayInstanceNum"/>
<input type="hidden" name="totalInstanceNum" id="totalInstanceNum"/>
  <table width="500" border="0" cellspacing="0" cellpadding="0">
	  <tr>
       <td height="40" align="right" valign="middle" style="padding-top: 40px;">灰度实例数/总实例数：</td>
       <td height="40" valign="middle"><div style="width: 350px;height: 50px;">
<div id="rangeSlider" ></div>
</div></td>
      </tr>
  </table>

  	 <div class="bottom" style="padding-left:160px;">
	  <blockquote>
	    <p>
        <input name="" type="button" value="下一步" onclick="showPlane('配置文件');"/>
	      <input name="" type="button" value="保存" onclick="saveGrayVersion('创建灰度版本',1)"/>
        </p>
      </blockquote>
	</div> 
</div>


    <div class="dialog-configure">
    	<table width="560" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="373"><span><form id="form2" name="form2"
								enctype="multipart/form-data" method="post">
								<label for="envsConfFile"></label> <input
									style="width: 198px; cursor: pointer" type="file"
									name="envsConfFile" id="envsConfFile" accept=".properties" />
							</form></span></td>
            <td align="left"><input type="button"  value="导入" onclick="importEnvsFile()" style="background:#444; width:67px; height:26px; border:none; font-family:'微软雅黑'; font-size:14px; color:#fff; cursor:pointer;"/></td>
            <td align="left"><input type="button"  value="添加" onclick="addEnvs();" style="background:#444; width:67px; height:26px; border:none; font-family:'微软雅黑'; font-size:14px; color:#fff; cursor:pointer;"/></td>
            <td align="left"><input type="button"  value="清除" onclick="deleteEnvs();" style="background:#444; width:67px; height:26px; border:none; font-family:'微软雅黑'; font-size:14px; color:#fff; cursor:pointer;"/></td>
            <td align="left"><a
						href="javaScript:exportEnvsFile()" id="exportEnvsFile"
						style="background:url(${ctx}/imgs/exporticon.png) no-repeat; float:left;" /></a></td>
          </tr>
        </table>
      <table width="555px" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px">
						<tr>
							<td width="110" height="26" align="center" valign="middle"
								style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">Key</td>
							<td width="200" height="26" align="center" valign="middle"
								style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">Value</td>
							<td width="70" height="26" align="center" valign="middle"
								style="font-weight: bold; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada;">操作</td>
						</tr>
	   </table>
	  <div class="configure-list-main">
 <table width="555" border="0" cellspacing="0" cellpadding="0" class="configure-list" id="envsConfTab">
</table>
</div>
<div class="bottom" style="padding-left:160px;"><input name="" type="button" value="上一步" style="margin-right:10px;" onclick="showPlane('配置文件');"/><input name="" type="button" value="保存" onclick="saveGrayVersion('环境变量',1)"/></div> 
  </div>



<div class="dialog-main" id="configFilePlane" style="background:none;">
	<%-- <table width="500" border="0" cellspacing="0" cellpadding="0">
 		<tr valign="middle">
    			<td width="107" height="36" style="font-size:14px;"> <img src="${ctx}/imgs/tips.png" />&nbsp;配置文件路径为空时不能上传。</td>
    	</tr>			
    </table> --%>
	<table width="500" border="0" cellspacing="0" cellpadding="0">
 		<tr valign="middle">
    						
    						<td width="107" height="36" style="font-size:14px;">导入配置文件：</td>
    						<td>
    						  <form id="form3" name="form3" enctype="multipart/form-data" method="post">
 								<label for="configFile"></label>
 								<input type="file" name="configFile" id="configFile" style="background:none; border:none; width:310px; margin-left:0px; " accept=".conf,.cfg,.txt,.properties" onchange="fileSelected();"/>
  							  </form>
	    					</td>
                          <td><input name="" id="uploadBtnId" type="button" value="上传" onclick="uploadConfigFile()" style=" background:#444; width:67px; height:26px; border:none; font-family:'微软雅黑'; font-size:14px; color:#fff; cursor:pointer;" /></td>
      </tr>			
</table>
     <table width="500" border="0" cellspacing="0" cellpadding="0" style="margin:10px 0;">
     	 <tr>
    <td width="107" height="30" style="font-size:14px;">配置文件路径：</td>
     <td width="393"><input name="configFilePath" readonly="readonly" type="text" value="aaaaa" id="configFile_path" style=" width:390px; height:25px; margin-left:0px;" /></td>
  </tr>
     </table>
     <table width="500" border="0" cellspacing="0" cellpadding="0">
     
  <tr>
    <td width="107" height="30" valign="top" style="font-size:14px;">配置文件信息：</td>
    <td><textarea id="configFileInfo" name="configFileInfo" cols="" rows="" style="width:385px; height:80px; resize:none; border:1px solid #c0c0c0;"></textarea></td>
  </tr>
</table>

     
     <div class="bottom" style="padding-left:160px;"><input name="" type="button" value="上一步" onclick="showPlane('创建灰度版本');" style="margin-right:10px;" /><input name="" type="button" value="保存" onclick="saveGrayVersion('配置文件',1)"/><input name="" type="button" value="下一步" onclick="showPlane('环境变量');" style="margin-right:10px;" /></div> 
</div>
</form>
</div>
<div id="loadImg" style="display:none"><img alt="" src="${ctx}/imgs/load.gif"></img></div>


</body>


