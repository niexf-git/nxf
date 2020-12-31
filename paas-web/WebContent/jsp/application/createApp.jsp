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
<body onload="controlRoleDisplayOrNot()">
<form action="" method="post" id="createAppForm">
	<div class="dialog">
  
    <div class="dialog-main" style="background:none;">
    <div class="applist">
    <table width="680" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:15px;">
  <tr>
    <td><strong>*</strong></td>
    <td width="64" height="40">应用名称：</td>
    <td height="40"><input type="text" id="appName" name="appUtil.appName" value=""/></td>
  </tr>
  <tr>
    <td><strong></strong></td>
    <td width="64">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</td>
    <td><textarea rows="3" cols="61" id="desc" name="appUtil.desc" style="resize:none; margin-left:5px;border-radius:5px; border:1px solid #c0c0c0; width:568px; height:51px;"></textarea></td>
  </tr>
</table>
	<c:set var="isProduct" value="${currentContext}" scope="session"></c:set>
	<input type="hidden" id="isProduct" value="${isProduct}"/>
     </div>
      <div class="applist-content">
      <table width="680" border="0" cellspacing="0" cellpadding="0">
						<tr>
      <c:choose>  
		   <c:when test="${isProduct}"> 
							<td width="363"><table width="345" border="0"
									cellspacing="0" cellpadding="0">
									<tr>
										<td width="19" height="30" align="left" valign="top"><strong>*</strong></td>
										<td width="47" align="right" valign="top">角色：</td>
										<td width="20" align="right" valign="top"><input
											type="checkbox" name="appUtil.role" id="roleDevelopmentBox"
											value="Development" onchange="roleDevelopmentFather()" style="width:13px;"/></td>
										<td width="27" align="right" valign="top">开发</td>
										<td width="10" align="right" valign="top"></td>
										<td width="195"
											style="border: 1px solid #c0c0c0; border-radius: 5px; margin-left: 10px;">
											<div
												style="overflow-x: none; overflow-y: auto; height: 100px; overflow: -Scroll; overflow-x: hidden;">
												<table width="195" border=0 cellpadding=0 cellspacing=0 id="roleDevTab">
													<c:forEach items="${createAppInfo.roles[0]}" var="role">
														<tr>
															<td width="32" height="26" align="center" valign="middle">
																<input type="checkbox" name="appUtil.roleDevelopment"
																id="roleDevelopment" value="${role.id}" onchange="linkageroleDevelopmentCheckBox()" style="width:13px;"/>
																<input type="hidden" id="hasOperType" name="hasOperType" value="${role.hasOperType}"/>
															</td>
															<td width="121" height="26" valign="middle"><a
																href="#">${role.roleName}</a></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</td>

									</tr>
								</table></td>
    <td width="300" align="right"><table width="305" border="0"
									cellspacing="0" cellpadding="0">
									<tr>
										<td width="30" align="right" valign="top">&nbsp;</td>
										<td width="26" align="right" valign="top"><input
											type="checkbox" name="appUtil.role" id="roleTestBox"
											value="Test" onchange="roleTestFather()" style="width:13px;"/></td>
										<td width="35" align="right" valign="top">测试</td>
										<td width="7" align="right" valign="top"></td>
										<td width="195"
											style="border: 1px solid #c0c0c0; border-radius: 5px; margin-left: 10px;">
											<div
												style="overflow-x: none; overflow-y: auto; height: 100px; overflow: -Scroll; overflow-x: hidden;">
												<table width="195" border=0 cellpadding=0 cellspacing=0 id="roleTestTab">
													<c:forEach items="${createAppInfo.roles[1]}" var="role">
														<tr>
															<td width="32" height="26" align="center" valign="middle">
																<input type="checkbox" name="appUtil.roleTest"
																id="checkbox" value="${role.id}"  onchange="linkageroleDevelopmentCheckBox()" style="width:13px;"/>
																<input type="hidden" id="hasOperType" name="hasOperType" value="${role.hasOperType}"/>
															</td>
															<td width="121" height="26" valign="middle"><a
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
				<td width="366"><table width="280" border="0"
									cellspacing="0" cellpadding="0">
									<tr>
										<td width="19" height="30" align="left" valign="top"><strong>*</strong></td>
										<td width="45" align="right" valign="top">角色：</td>
										<td width="26" align="right" valign="top"><input
											type="checkbox" name="appUtil.role" id="roleProductionBox"
											value="Production" onchange="roleProductinFather()" /></td>
										<td width="35" align="right" valign="top">生产</td>
										<td width="7" align="right" valign="top"></td>
										<td width="138"
											style="border: 1px solid #c0c0c0; border-radius: 5px; margin-left: 10px;">
											<div
												style="overflow-x: none; overflow-y: auto; height: 100px; overflow: -Scroll; overflow-x: hidden;">
												<table width="153" border=0 cellpadding=0 cellspacing=0>
													<c:forEach items="${createAppInfo.roles[0]}" var="role">
														<tr>
															<td width="32" height="26" align="center" valign="middle">
																<input type="checkbox" name="appUtil.roleProduction" value="${role.id}"  onchange="linkageroleDevelopmentCheckBox()"/>
															</td>
															<td width="121" height="26" valign="middle"><a
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
  <table width="680" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <c:choose>
    <c:when test="${isProduct}">
     	<td width="345"><table width="345" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <td width="19" height="30" align="left" valign="top"><strong>*</strong></td>
    <td width="47" align="right" valign="top">集群：</td>
    <td width="20" align="right" valign="top"><input type="checkbox" name="appUtil.colony" id="colonyDevelopmentBox"
											value="Development" onchange="colonyDevelopmentFather()" style="width:13px;" /></td>
    <td width="27" align="right" valign="top">开发</td>
    <td width="15" align="right" valign="top"></td>
    <td width="216" style="border:1px solid #c0c0c0; border-radius:5px; margin-left:10px;">
   
    	<table width="216" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="49" height="25" align="right" style="border-bottom:1px solid #dadada;">数据中心</td>
    <td width="94" height="25" align="left" valign="middle"  style="border-bottom:1px solid #dadada;">
      <label for="select"  style="width:100px; height:18px;"></label>
      <select name="appUtil.deveDataCoreId" id="colonyDeve"  style="width:100px;  height:18px;" onchange="dataCenterChange('colonyDeve')">
      		<c:forEach var="deveDataCenter" items="${createAppInfo.dataCenterInfos[0]}">
      			<option <c:if test="${deveDataCenter.checked}">selected</c:if> value="${deveDataCenter.id}">${deveDataCenter.name}</option>
      		</c:forEach>
      </select>
    </td>
  </tr>
  
</table>
<div style="height:128px; width:216px; float:left;">
<div id="colonyDeveColumnDiv">
<c:if test="${not empty  createAppInfo.clusterInfos[0] }">
<table width="216" border="0" cellspacing="0" cellpadding="0" id="colonyDeveColumn">
	<tr>
    	<td width="90" height="26" align="center" valign="middle">名称</td>
    	<td width="55" height="26" valign="middle">类型</td>
    </tr>
</table>
</c:if>
</div>
 <div style="overflow-x: none; overflow-y: auto; height: 100px;overflow:-Scroll;overflow-x:hidden;">
        <table width="216" border=0 cellpadding=0 cellspacing=0 id="colonyDeveTab">
        <c:forEach var="cluster" items="${createAppInfo.clusterInfos[0]}">
   	 			<tr>
    			<td width="32" height="26" align="center" valign="middle"><input name="appUtil.colonyDevelopment" type="checkbox" value="${cluster.id}"  onchange="linkageroleDevelopmentCheckBox()"/></td>
    			<td width="65" height="26" valign="middle"><a href="#">${cluster.name}</a></td>
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
    <td width="300"><table width="290" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="10" align="right" valign="top">&nbsp;</td>
    <td width="26" align="right" valign="top"><input type="checkbox" name="appUtil.colony" id="colonyTestBox"
											value="Test" onchange="colonyTestFather()" style="width:13px;"/></td>
    <td width="35" align="right" valign="top">测试</td>
    <td width="7" align="right" valign="top"></td>
    <td width="211" style="border:1px solid #c0c0c0; border-radius:5px; margin-left:10px;">
    
    	<table width="211" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="50" height="25" align="right" style="border-bottom:1px solid #dadada;">数据中心</td>
    <td width="93" height="25" align="left" valign="middle"  style="border-bottom:1px solid #dadada;">
      <label for="select"  style="width:100px; height:18px;"></label>
      <select name="appUtil.testDataCoreId" id="colonyTest"  style="width:100px;  height:18px;" onchange="dataCenterChange('colonyTest')">
      		<c:forEach var="deveDataCenter" items="${createAppInfo.dataCenterInfos[1]}">
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
    			<td width="32" height="26" align="center" valign="middle"><input name="appUtil.colonyTest" type="checkbox" value="${cluster.id}"  onchange="linkageroleDevelopmentCheckBox()"/></td>
    			<td width="65" height="26" valign="middle"><a href="#">${cluster.name}</a></td>
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
    
    	<td width="345"><table width="345" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="68" align="right" valign="top">集群：</td>
    <td width="20" align="right" valign="top"><input type="checkbox" name="appUtil.colony" id="colonyProductionBox"
											value="Production" onchange="colonyProductionFather()"/></td>
    <td width="27" align="right" valign="top">生产</td>
    <td width="10" align="right" valign="top"></td>
    <td width="211" style="border:1px solid #c0c0c0; border-radius:5px; margin-left:10px;">
    <div style="overflow-x: none; overflow-y: auto; height: 100px;overflow:-Scroll;overflow-x:hidden;">
    	<table width="211" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="49" height="25" align="right" style="border-bottom:1px solid #dadada;">数据中心</td>
    <td width="94" height="25" align="left" valign="middle"  style="border-bottom:1px solid #dadada;">
      <label for="select"  style="width:100px; height:18px;"></label>
      <select name="appUtil.deveDataCoreId" id="colonyPro"  style="width:100px;  height:18px;" onchange="dataCenterChange('colonyPro')">
      		<c:forEach var="deveDataCenter" items="${createAppInfo.dataCenterInfos[0]}">
      			<option <c:if test="${deveDataCenter.checked}">selected</c:if> value="${deveDataCenter.id}">${deveDataCenter.name}</option>
      		</c:forEach>
      </select>
     </td>
  </tr>
</table>
<div style="height:128px; width:211px; float:left;">
<div id="colonyProTabColumnDiv">
  <c:if test="${not empty  createAppInfo.clusterInfos[0] }">
<table width="211" border="0" cellspacing="0" cellpadding="0" id="colonyProTabColumn">
	<tr>
    	<td width="90" height="26" align="center" valign="middle">名称</td>
    	<td width="55" height="26" valign="middle">类型</td>
    	<td width="64" height="26" valign="middle" align="left">是否容灾</td>
    </tr>
</table>
</c:if>
</div>
        <table width="211" border=0 cellpadding=0 cellspacing=0 id="colonyProTab">
		<c:forEach var="cluster" items="${createAppInfo.clusterInfos[0]}">
      			<tr>
    			<td width="32" height="26" align="center" valign="middle"><input name="appUtil.colonyProduction" type="checkbox" value="${cluster.id}"  onchange="linkageroleDevelopmentCheckBox()"/></td>
    			<td width="121" height="26" valign="middle"><a href="#">${cluster.name}</a></td>
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
    <td width="300"><table width="290" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="10" align="right" valign="top">&nbsp;</td>
    <td width="26" align="right" valign="top"><input type="checkbox"  name="appUtil.colony" id="colonyTransferBox"
											value="ProductionTransfer" onchange="colonyTransferBoxFather()"/></td>
    <td width="35" align="right" valign="top">迁移</td>
    <td width="7" align="right" valign="top"></td>
    <td width="211" style="border:1px solid #c0c0c0; border-radius:5px; margin-left:10px;">
    <div style="overflow-x: none; overflow-y: auto; height: 100px;overflow:-Scroll;overflow-x:hidden;">
    	<table width="211" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="50" height="25" align="right" style="border-bottom:1px solid #dadada;">数据中心</td>
    <td width="93" height="25" align="left" valign="middle"  style="border-bottom:1px solid #dadada;">
      <label for="select"  style="width:100px; height:18px;"></label>
      <select name="appUtil.testDataCoreId" id="colonyTransfer"  style="width:100px;  height:18px;" onchange="dataCenterChange('colonyTransfer')">
      		<c:forEach var="deveDataCenter" items="${createAppInfo.dataCenterInfos[1]}">
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
        <table width="211" border=0 cellpadding=0 cellspacing=0 id="colonyTransferTab">
		<c:forEach var="cluster" items="${createAppInfo.clusterInfos[0]}">
      			<tr>
    			<td width="32" height="26" align="center" valign="middle">
    			<c:if test="${cluster.type=='1'}"><input name="appUtil.colonyTransferByIpaas" type="checkbox" value="${cluster.id}"  onclick="onClickCheckBox(this)" style="float:left;"/></c:if>
    			<c:if test="${cluster.type=='2'}"><input name="appUtil.colonyTransferByApaas" type="checkbox" value="${cluster.id}"  onclick="onClickCheckBox(this)" style="float:left;"/></c:if>
    			</td>
    			<td width="121" height="26" valign="middle"><a href="#">${cluster.name}</a></td>
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
    </c:otherwise>
</c:choose>
    
   
  </tr>
  </table>
      </div>
     <div class="bottom" style="padding-left:130px;"><input name="" type="button" onclick="onCreateAppSubmit()"
						value="确定" /><input name="" type="button" value="复位"
						onclick="initData()" /><input name="" type="button"
						onclick="JavaScript:parent.close();" value="取消" /></div> 
</div>
</div>
<div id="loadImg" style="display:none"><img alt="" src="${ctx}/imgs/load.gif"></img></div>
</form>
</body>
</html>