<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<%
	String errorInfo = (String) request.getAttribute("errorMsg");// 获取错误属性
	String appServiceId = (String) request.getAttribute("appServiceId");// 获取应用服务id
	if (errorInfo != null) {
%>
<script type="text/javascript">
parent.parent.alertError("<%=errorInfo%>");
</script>
<%
	}
%>
<script type="text/javascript">
$(function(){
	function setInstanceNum(obj){
		$("#grayInstanceNum").val(obj.from);
    	$("#totalInstanceNum").val(obj.to);
	}
	var to = $("#totalInstanceNum").val();
	var from = $("#grayInstanceNum").val();
	var maxs = to*2;
	maxs = maxs>100?100:maxs;
	$('#rangeSlider').ionRangeSlider({
		type:'double',
	    min: 0,
	    max: maxs,
	    from: from,
	    to: to,
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
<input type="hidden" name="appId" id="appId" value="${grayEntity.appId}"/>
<input type="hidden" id="id" value="${grayEntity.id}"/>
<input type="hidden" id="operType" value="${grayEntity.oper_type}"/>
<input type="hidden" name="runningVersion" value="${grayEntity.runningVersion}"/>
<input type="hidden" id="image_by_id" value="${grayEntity.imageId}"/>
<input type="hidden" id="image_by_version_id" value="${grayEntity.imageVersionId}"/>
<input type="hidden" id="appServiceName" value="${grayEntity.appServiceName}"/>
<input type="hidden" id="oper" value="update"/>
  <div class="dialog-title">
  	<div class="title-mc">
    	<ul>
    	  <li id="li1" style="width:123px; background:#ccc; height:30px;"><a href="javascript:void(0);">修改灰度版本</a></li>
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
	      <option value="2" <c:if test="${grayEntity.imageType eq 2}">selected</c:if>>私有镜像</option>
	      <option value="1" <c:if test="${grayEntity.imageType eq 1}">selected</c:if>>公有镜像</option>
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
    <select id="image_version" name="imageVersionId" onchange="imageVserionChange($('#image_version').val())">
    	<option selected="selected">请选择</option>
    </select>
    </td>
    <td height="30">&nbsp;</td>
    <td height="30"><strong>*</strong>日志路径：</td>
    <td height="30"><input name="logDir" type="text" id="log_dir"/></td>
  </tr>
</table>
<input type="hidden" name="grayInstanceNum" id="grayInstanceNum" value="${grayEntity.grayInstanceNum}"/>
<input type="hidden" name="totalInstanceNum" id="totalInstanceNum" value="${grayEntity.totalInstanceNum}"/>
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
	      <input name="" type="button" value="保存" onclick="saveGrayVersion('创建灰度版本',2)"/>
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
 <table width="555" border="0" cellspacing="0" cellpadding="0" class="configure-list" id="envsConfTab">
 	<c:forEach  var="conf" items="${grayEntity.envConfig}"
					varStatus="status">
				<tr id="envsTabTr${status.index}" class="_tr">
						<td width="246" height="30" align="center" valign="middle">
						<input type="text" name="appConfKey" id="envsConfKey${status.index}" value="${conf.configKey}" /></td>
						<td width="238" height="30" align="center" valign="middle">
						<input type="text" name="appConfValue" id="envsConfValue${status.index}" value="${conf.configValue}" /></td>
						<td width="71" height="30" align="center" valign="middle">
							<center>
								<a href="JavaScript:deleteEnvsConf(${status.index});"><img
									src='/paas/imgs/delete.png' width='22' height='22'
									border='none' />删除</a>
							</center>
						</td>
					</tr>			
	
	</c:forEach>
</table>
<div class="bottom" style="padding-left:160px;"><input name="" type="button" value="上一步" style="margin-right:10px;" onclick="showPlane('配置文件');"/><input name="" type="button" value="保存" onclick="saveGrayVersion('环境变量',2)"/></div> 
  </div>



<div class="dialog-main" id="configFilePlane" style="background:none;">
	<%-- <table width="500" border="0" cellspacing="0" cellpadding="0">
 		<tr valign="middle">
    			<td width="107" height="26" valign="bottom" style="font-size:14px;"> <img src="${ctx}/imgs/tips.png" />&nbsp;配置文件路径为空时不能上传。</td>
    	</tr>			
    </table> --%>
	<table width="500" border="0" cellspacing="0" cellpadding="0">
 		<tr valign="middle">
    						<td width="10" height="36" style="font-size:14px;">&nbsp;</td>
    						<td width="238" height="36" style="font-size:14px;">导入配置文件：</td>
      </tr>
	  <tr>
    						<td width="10" height="26" valign="top">&nbsp;</td>
    						<td valign="top">
    						  <form id="form3" name="form3" enctype="multipart/form-data" method="post">
 								<label for="configFile"></label>
 								<input type="file" name="configFile" id="configFile" style="background:none; border:none; width:180px; " accept=".conf,.cfg,.txt,.properties" onchange="fileSelected();"/>
  							  </form>
	    					</td>
                          <td><input name="" type="button" id="uploadBtnId" value="上传" onclick="uploadConfigFile();" style=" background:#444; width:67px; height:26px; border:none; font-family:'微软雅黑'; font-size:14px; color:#fff; cursor:pointer;" /></td>
				      </tr>
  						
</table>
     <table width="500" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
     	 <tr>
    <td width="107" height="30" style="font-size:14px;">配置文件路径：</td>
     <td width="393"><input name="configFilePath" type="text" value="" id="configFile_path" readonly="readonly" style=" width:340px; height:25px;" /></td>
  </tr>
     </table>
     <table width="500" border="0" cellspacing="0" cellpadding="0">
     
  <tr>
    <td height="30" style="font-size:14px;">配置文件信息：</td>
  </tr>
  <tr>
    <td><textarea id="configFileInfo" name="configFileInfo" cols="" rows="" style="width:465px; height:80px;">${grayEntity.configFileInfo}</textarea></td>
  </tr>
</table>

     
     <div class="bottom" style="padding-left:160px;"><input name="" type="button" value="上一步" onclick="showPlane('修改灰度版本');" style="margin-right:10px;" /><input name="" type="button" value="保存" onclick="saveGrayVersion('配置文件',2)"/><input name="" type="button" value="下一步" onclick="showPlane('环境变量');" style="margin-right:10px;" /></div> 
</div>
</form>
</div>
<div id="loadImg" style="display:none"><img alt="" src="${ctx}/imgs/load.gif"></img></div>


</body>


