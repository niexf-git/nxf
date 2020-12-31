<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/dailog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/application/application.js"></script>
</head>

<script type="text/javascript">
</script>
<body onload="controlUpdateAppPageRoleDisplayOrNot()">
<form action="" method="post" id="updateApp">
	<div class="applist-dialog">
  <div class="applist-title">
  	<div class="title-mc">
   </div>
   </div>
 
    <div class="applist" style="padding:10px 0 0 10px;]">
    <table width="660" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="15" height="22" valign="middle" style="font-size:14px; color: #1791d5;"><img src="${ctx}/imgs/tips.png" />&nbsp;&nbsp;镜像仓库用户名和密码的作用：用户下载镜像时需要的用户名和密码 </td>
	  </tr>
</table>
    <table width="660" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:8px;">
	  <tr>
	    <td width="15" height="22" valign="middle"><strong>*</strong></td>
	    <td width="98" height="22" valign="middle">应用名称：</td>
	    <td width="175" height="22" valign="middle"><span style="font-weight:bold;">${createAppInfo.name}</span></td>
	    <td width="73" height="22" valign="middle">&nbsp;<input type="hidden" id="appName" name="appUtil.appName" value="${createAppInfo.name}"/></td>
	    <td width="119" height="22" valign="middle">&nbsp;<input
								type="hidden" id="appId" name="createAppInfo.id"
								value="${createAppInfo.id}"></td>
	    <td width="100" height="22" valign="middle">&nbsp;</td>
	  </tr>
	  <tr>
	    <td height="22" valign="middle"><strong>*</strong></td>
	    <td height="22" valign="middle">镜像仓库用户名：</td>
	    <td height="22" valign="middle"><span style=" font-weight:bold;">${createAppInfo.dockerRegistryUser} </span></td>
	    <td height="22" valign="middle">&nbsp;</td>
	    <td height="22" valign="middle">镜像仓库密码：</td>
	    <td height="22" valign="middle"><span style="font-weight:bold;">${createAppInfo.dockerRegistryPwd}</span></td>
	  </tr>
</table>
<table width="660" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="60">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</td>
    <td width="464"><textarea rows="3" cols="62" id="desc" name="appUtil.desc" style="border-radius:5px; border:1px solid #c0c0c0;resize:none; margin-left:5px; width:560px; height:50px;">${createAppInfo.desc}</textarea></td>
  </tr>
</table>

	<c:set var="isProduct" value="${currentContext}" scope="session"></c:set>
	<input type="hidden" id="isProduct" value="${isProduct}"/>
     </div>
      <div class="applist-content" style="margin-top:15px; padding-left:10px;">
      <table width="660" border="0" cellspacing="0" cellpadding="0">
						<tr>
      <c:choose>  
		   <c:when test="${isProduct}"> 
							<td width="358"><table width="338" border="0"
									cellspacing="0" cellpadding="0">
									<tr>
										<td width="19" height="30" align="left" valign="top"><strong>*</strong></td>
										<td width="68" align="right" valign="top">角色：</td>
										<td width="20" align="right" valign="top"><input
											type="checkbox" name="appUtil.role" id="roleDevelopmentBox"
											<c:if test="${createAppInfo.roleCheckeds[0]}">checked</c:if> value="Development" onchange="roleDevelopmentFather()"/></td>
										<td width="27" align="right" valign="top">开发</td>
										<td width="10" align="right" valign="top"></td>
										<td width="200"
											style="border: 1px solid #c0c0c0; border-radius: 5px; margin-left: 10px;">
											<div style="overflow-x: none; overflow-y: auto; height: 100px; overflow: -Scroll; overflow-x: hidden;">
												<table width="192" border=0 cellpadding=0 cellspacing=0 id="roleDevTab">
													<c:forEach items="${createAppInfo.roles[0]}" var="role">
														<tr>
															<td width="13" height="26" align="center" valign="middle">
																<input type="checkbox" name="appUtil.roleDevelopment" <c:if test="${role.checked}">checked</c:if> 
																id="roleDevelopment" value="${role.id}" onchange="linkageroleDevelopmentCheckBox()"/>
																<input type="hidden" id="hasOperType" name="hasOperType" value="${role.hasOperType}"/>
															</td>
															<td height="26" valign="middle"><a
																href="#">${role.roleName}</a></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</td>

									</tr>
								</table></td>
    							<td width="366"><table width="290" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="0" align="right" valign="top">&nbsp;</td>
										<td width="26" align="right" valign="top"><input
											type="checkbox" name="appUtil.role" id="roleTestBox" <c:if test="${createAppInfo.roleCheckeds[1]}">checked</c:if>
											value="Test" onchange="roleTestFather()"/></td>
										<td width="35" align="right" valign="top">测试</td>
										<td width="7" align="right" valign="top"></td>
										<td width="200"
											style="border: 1px solid #c0c0c0; border-radius: 5px; margin-left: 10px;">
											<div style="overflow-x: none; overflow-y: auto; height: 100px; overflow: -Scroll; overflow-x: hidden;">
												<table width="194" border=0 cellpadding=0 cellspacing=0 id="roleTestTab">
													<c:forEach items="${createAppInfo.roles[1]}" var="role">
														<tr>
															<td width="13" height="26" align="center" valign="middle">
																<input type="checkbox" name="appUtil.roleTest" <c:if test="${role.checked}">checked</c:if>
																id="checkbox" value="${role.id}"   onchange="linkageroleDevelopmentCheckBox()"/>
																<input type="hidden" id="hasOperType" name="hasOperType" value="${role.hasOperType}"/>
															</td>
															<td height="26" valign="middle"><a
																href="#">${role.roleName}</a></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</td>

									</tr>
								</table></td>
		   </c:when>  
		   <c:otherwise>
				<td width="366"><table width="340" border="0"
									cellspacing="0" cellpadding="0">
									<tr>
										<td width="19" height="30" align="left" valign="top"><strong>*</strong></td>
										<td width="45" align="right" valign="top">角色：</td>
										<td width="26" align="right" valign="top"><input
											type="checkbox" name="appUtil.role" id="roleProductionBox" <c:if test="${createAppInfo.roleCheckeds[2]}">checked</c:if>
											value="Production" onchange="roleProductinFather()" /></td>
										<td width="35" align="right" valign="top">生产</td>
										<td width="7" align="right" valign="top"></td>
										<td width="211"
											style="border: 1px solid #c0c0c0; border-radius: 5px; margin-left: 10px;">
											<div style="overflow-x: none; overflow-y: auto; height: 100px; overflow: -Scroll; overflow-x: hidden;">
												<table width="211" border=0 cellpadding=0 cellspacing=0>
													<c:forEach items="${createAppInfo.roles[0]}" var="role">
														<tr>
															<td width="13" height="26" align="center" valign="middle">
																<input type="checkbox" name="appUtil.roleProduction" value="${role.id}" <c:if test="${role.checked}">checked</c:if> onchange="linkageroleDevelopmentCheckBox()"/>
															</td>
															<td height="26" valign="middle"><a
																href="#">${role.roleName}</a></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</td>

									</tr>
				</table></td>
  
   		   </c:otherwise>  
	</c:choose>
    
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td width="154">&nbsp;</td>
  </tr>
    </table>
  <table width="660" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <c:choose>
    <c:when test="${isProduct}">
     	<td width="345"><table width="340" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="19" height="30" align="left" valign="top"><strong>*</strong></td>
    <td width="68" align="right" valign="top">集群：</td>
    <td width="20" align="right" valign="top"><input type="checkbox" name="appUtil.colony"  id="colonyDevelopmentBox"
    											<c:if test="${createAppInfo.checkeds[0]}">checked</c:if>
											value="Development" onchange="colonyDevelopmentFather()" /></td>
    <td width="27" align="right" valign="top">开发</td>
    <td width="10" align="right" valign="top"></td>
    <td width="211" style="border:1px solid #c0c0c0; border-radius:5px; margin-left:10px;">
    
    	<table width="211" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="49" height="25" align="right" style="border-bottom:1px solid #dadada;">数据中心</td>
    <td width="94" height="25" align="left" valign="middle"  style="border-bottom:1px solid #dadada;">
      <label for="select"  style="width:100px; height:18px;"></label>
      <select name="appUtil.deveDataCoreId" id="colonyDeve"  style="width:100px;  height:18px;" onchange="dataCenterChange('colonyDeve')">
      		<c:forEach var="deveDataCenter" items="${createAppInfo.dataCenterInfos[1]}">
      			<option <c:if test="${deveDataCenter.checked}">selected</c:if> value="${deveDataCenter.id}">${deveDataCenter.name}</option>
      		</c:forEach>
      </select>
    </td>
  </tr>
  
</table>
<div style="height:128px; width:211px; float:left;">
<div id="colonyDeveColumnDiv">
<c:if test="${not empty  createAppInfo.clusterInfos[1] }">
<table width="211" border="0" cellspacing="0" cellpadding="0" id="colonyDeveColumn">
	<tr>
    	<td width="90" height="26" align="center" valign="middle">名称</td>
    	<td width="55" height="26" valign="middle">类型</td>
    </tr>
</table>
</c:if>
</div>
	<div style="overflow-x: none; overflow-y: auto; height: 100px;overflow:-Scroll;overflow-x:hidden;">
        <table width="211" border=0 cellpadding=0 cellspacing=0 id="colonyDeveTab">
        <c:forEach var="cluster" items="${createAppInfo.clusterInfos[1]}">
      			<tr>
    			<td width="90" height="26" align="left" valign="middle">
			<input name="appUtil.colonyDevelopment" <c:if test="${cluster.checked}">checked</c:if> type="checkbox" value="${cluster.id}" onchange="linkageroleDevelopmentCheckBox()" style="float:left;"/>
			<a href="#" style="width:70%; display:block; float:left;">${cluster.name}</a></td>
    			
    			<td width="64" height="26" valign="middle">
    			<c:if test="${cluster.type=='1'}">ipaas</c:if>
   	 			<c:if test="${cluster.type=='2'}">apaas</c:if>
   	 			</td>	
   	 			</tr>
        </c:forEach>
    	</table>
    </div>
    </div>
</td>
    
  </tr>
</table></td>
    <td width="300"><table width="290" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="0" align="right" valign="top">&nbsp;</td>
    <td width="26" align="right" valign="top"><input type="checkbox" name="appUtil.colony" id="colonyTestBox"
    													<c:if test="${createAppInfo.checkeds[1]}">checked</c:if>
											value="Test" onchange="colonyTestFather()" /></td>
    <td width="35" align="right" valign="top">测试</td>
    <td width="7" align="right" valign="top"></td>
    <td width="211" style="border:1px solid #c0c0c0; border-radius:5px; margin-left:10px;">
    
    	<table width="211" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="50" height="25" align="right" style="border-bottom:1px solid #dadada;">数据中心</td>
    <td width="93" height="25" align="left" valign="middle"  style="border-bottom:1px solid #dadada;">
      <label for="select"  style="width:100px; height:18px;"></label>
      <select name="appUtil.testDataCoreId" id="colonyTest"  style="width:100px;  height:18px;" onchange="dataCenterChange('colonyTest')">
      		<c:forEach var="deveDataCenter" items="${createAppInfo.dataCenterInfos[0]}">
      			<option <c:if test="${deveDataCenter.checked}">selected</c:if> value="${deveDataCenter.id}">${deveDataCenter.name}</option>
      		</c:forEach>
      </select>
    </td>
  </tr>
</table>
<div style="height:128px; width:211px; float:left;">
<div id="colonyTestColumnDiv">
<c:if test="${not empty  createAppInfo.clusterInfos[0] }">
<table width="211" border="0" cellspacing="0" cellpadding="0" id="colonyTestColumn">
	<tr>
    	<td width="90" height="26" align="center" valign="middle">名称</td>
    	<td width="55" height="26" valign="middle">类型</td>
    </tr>
</table>
</c:if>
</div>
<div style="overflow-x: none; overflow-y: auto; height: 100px;overflow:-Scroll;overflow-x:hidden;">
        <table width="211" border=0 cellpadding=0 cellspacing=0 id="colonyTestTab">
		<c:forEach var="cluster" items="${createAppInfo.clusterInfos[0]}">
      			<tr>
    			<td width="90" height="26" align="left" valign="middle">
			<input type="checkbox" name="appUtil.colonyTest" value="${cluster.id}" <c:if test="${cluster.checked}">checked</c:if> onchange="linkageroleDevelopmentCheckBox()"style="float:left;"/><a href="#" style="width:70%; display:block; float:left;">${cluster.name}</a></td>
    			
    			<td width="55" height="26" valign="middle">
    			<c:if test="${cluster.type=='1'}">ipaas</c:if>
   	 			<c:if test="${cluster.type=='2'}">apaas</c:if>
   	 			</td>	
   	 			</tr>
        </c:forEach>
    	</table>
    </div>
    </div>
</td>
    
  </tr>
</table></td>
    </c:when>
    <c:otherwise>
    
    	<td width="366"><table width="340" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="68" align="right" valign="top">集群：</td>
    <td width="20" align="right" valign="top"><input type="checkbox" name="appUtil.colony" id="colonyProductionBox"
    															<c:if test="${createAppInfo.checkeds[0]}">checked</c:if>
											value="Production" onchange="colonyProductionFather()"/></td>
    <td width="27" align="right" valign="top">生产</td>
    <td width="10" align="right" valign="top"></td>
    <td width="211" style="border:1px solid #c0c0c0; border-radius:5px; margin-left:10px;">
    	
    	<table width="211" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="49" height="25" align="right" style="border-bottom:1px solid #dadada;">数据中心</td>
    <td width="94" height="25" align="left" valign="middle"  style="border-bottom:1px solid #dadada;">
      <label for="select"  style="width:100px; height:18px;"></label>
      <input type="hidden" id="productClusterIds" name="appUtil.productClusterIds" value="${createAppInfo.productClusterIds}">
      <select name="appUtil.deveDataCoreId" id="colonyPro"  style="width:100px;  height:18px;" onchange="dataCenterChange('colonyPro')">
      		<c:forEach var="deveDataCenter" items="${createAppInfo.dataCenterInfos[1]}">
      			<option <c:if test="${deveDataCenter.checked}">selected</c:if> value="${deveDataCenter.id}">${deveDataCenter.name}</option>
      		</c:forEach>
      </select>
     </td>
  </tr>
</table>
<div style="height:128px; width:211px; float:left;">
<div id="colonyProTabColumnDiv">
<c:if test="${not empty  createAppInfo.clusterInfos[1] }">
<table width="211" border="0" cellspacing="0" cellpadding="0" id="colonyProTabColumn">
	<tr>
    	<td width="90" height="26" align="center" valign="middle">名称</td>
    	<td width="55" height="26" valign="middle">类型</td>
    	<td width="64" height="26" valign="middle" align="left">是否容灾</td>
    </tr>
</table>
</c:if>
</div>
<div style="overflow-x: none; overflow-y: auto; height: 100px;overflow:-Scroll;overflow-x:hidden;">
        <table width="196" border=0 cellpadding=0 cellspacing=0 id="colonyProTab">
		<c:forEach var="cluster" items="${createAppInfo.clusterInfos[1]}">      			
   	 			<tr>
    			<td width="90" height="26" align="left" valign="middle">
			<input name="appUtil.colonyProduction" type="checkbox" value="${cluster.id}" <c:if test="${cluster.checked}">checked</c:if> onchange="linkageroleDevelopmentCheckBox()"style="float:left;"/>
			<a href="#" style="width:70%; display:block; float:left;">${cluster.name}</a></td>
    			
    			<td width="55" height="26" valign="middle">
    			<c:if test="${cluster.type=='1'}">ipaas</c:if>
   	 			<c:if test="${cluster.type=='2'}">apaas</c:if>
   	 			</td>
    			<c:if test="${cluster.type=='1'}">
    			
    			<c:if test="${cluster.checkeds}">
    			<td width="64" height="26" valign="middle" align="center"><input type="radio" name="appUtil.DisasterToleranceProByIpaas" value="${cluster.id}t" <c:if test="${cluster.checkeds}">checked</c:if> <c:if test="${!cluster.checked}">disabled</c:if>  onclick="setSelectNo(this)" /></td>
    			</c:if>
    			<c:if test="${!cluster.checkeds}">
    			<td width="64" height="26" valign="middle" align="center"><input type="radio" name="appUtil.DisasterToleranceProByIpaas" value="${cluster.id}f" <c:if test="${cluster.checkeds}">checked</c:if> <c:if test="${!cluster.checked}">disabled</c:if>  onclick="setSelectNo(this)" /></td>
    			</c:if>
    			</c:if>
    			<c:if test="${cluster.type=='2'}">
    			
    			<c:if test="${cluster.checkeds}">
    			<td width="64" height="26" valign="middle" align="center"><input type="radio" name="appUtil.DisasterToleranceProByApaas" value="${cluster.id}t" <c:if test="${cluster.checkeds}">checked</c:if> <c:if test="${!cluster.checked}">disabled</c:if>  onclick="setSelectNo(this)" /></td>
    			</c:if>
    			<c:if test="${!cluster.checkeds}">
    			<td width="64" height="26" valign="middle" align="center"><input type="radio" name="appUtil.DisasterToleranceProByApaas" value="${cluster.id}f" <c:if test="${cluster.checkeds}">checked</c:if> <c:if test="${!cluster.checked}">disabled</c:if>  onclick="setSelectNo(this)" /></td>
    			</c:if>
    			</c:if>			
   	 			</tr>
        </c:forEach>
    	</table>
    </div>
</div>
</td>
    
  </tr>
</table></td>
    <td width="346"><table width="290" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="10" align="right" valign="top">&nbsp;</td>
    <td width="26" align="right" valign="top"><input type="checkbox" name="appUtil.colony" id="colonyTransferBox"
    													<c:if test="${createAppInfo.checkeds[1]}">checked</c:if>
											value="ProductionTransfer" onchange="colonyTransferBoxFather()"/></td>
    <td width="35" align="right" valign="top">迁移</td>
    <td width="7" align="right" valign="top"></td>
    <td width="211" style="border:1px solid #c0c0c0; border-radius:5px; margin-left:10px;">
    
    	<table width="211" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="50" height="25" align="right" style="border-bottom:1px solid #dadada;">数据中心</td>
    <td width="93" height="25" align="left" valign="middle"  style="border-bottom:1px solid #dadada;">
      <label for="select"  style="width:100px; height:18px;"></label>
      <select name="appUtil.testDataCoreId" id="colonyTransfer"  style="width:100px;  height:18px;" onchange="dataCenterChange('colonyTransfer')">
      		<c:forEach var="deveDataCenter" items="${createAppInfo.dataCenterInfos[0]}">
      			<option <c:if test="${deveDataCenter.checked}">selected</c:if> value="${deveDataCenter.id}">${deveDataCenter.name}</option>
      		</c:forEach>
      </select>
    </td>
  </tr>
</table>
<div style="height:128px; width:211px; float:left;">
<div id="colonyTransferColumnDiv">
<c:if test="${not empty  createAppInfo.clusterInfos[0] }">
<table width="211" border="0" cellspacing="0" cellpadding="0" id="colonyTransferColumn">
	<tr>
    	<td width="90" height="26" align="center" valign="middle">名称</td>
    	<td width="55" height="26" valign="middle">类型</td>
    </tr>
</table>
</c:if>
</div>
	<div style="overflow-x: none; overflow-y: auto; height: 100px;overflow:-Scroll;overflow-x:hidden;">
        <table width="196" border=0 cellpadding=0 cellspacing=0 id="colonyTransferTab">
		<c:forEach var="cluster" items="${createAppInfo.clusterInfos[0]}">
      			<tr>
    			<td width="90" height="26" align="left" valign="middle">
    			<c:if test="${cluster.type=='1'}"><input name="appUtil.colonyTransferByIpaas" type="checkbox" value="${cluster.id}" <c:if test="${cluster.checked}">checked</c:if> onclick="onClickCheckBox(this)" style="float:left;"/></c:if>
    			<c:if test="${cluster.type=='2'}"><input name="appUtil.colonyTransferByApaas" type="checkbox" value="${cluster.id}" <c:if test="${cluster.checked}">checked</c:if> onclick="onClickCheckBox(this)" style="float:left;"/></c:if>
			<a href="#" style="width:70%; display:block; float:left;">${cluster.name}</a></td>
    			<td width="55" height="26" valign="middle">
    			<c:if test="${cluster.type=='1'}">ipaas</c:if>
   	 			<c:if test="${cluster.type=='2'}">apaas</c:if>
   	 			</td>		
   	 			</tr>
        </c:forEach>
    	</table>
    </div>
    </div>
</td>
    
  </tr>
  
</table>

</td>
    </c:otherwise>
</c:choose>
    
   
  </tr>
   <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td width="154">&nbsp;</td>
  </tr>
  </table>
      </div>
     <div class="bottom" style="padding:0px; padding-left:180px;"><input name="" type="button" onclick="onUpdateAppSubmit()"
						value="确定" /><input name="" type="button" value="复位"
						onclick="initData()" /><input name="" type="button"
						onclick="JavaScript:parent.close();" value="取消" /></div> 
</div>
<div id="loadImg" style="display:none"><img alt="" src="${ctx}/imgs/load.gif"></img></div>
</form>
</body>
</html>