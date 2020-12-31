<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script>
<script type="text/javascript">
	
</script>
<style>
html,body{ overflow-x:hidden;}
</style>
<div class="example">
      		<h1 style="color:#333; font-size:14px;"><span style="margin-right:5px;"><img src="${ctx}/imgs/fz.png" width="16" height="17" /></span>实例信息</h1>
      		<div class="cz-left">
            	<ul>
                	<li>实例名称:<span>${instance.instanceId }</span></li>
                    
                    <li>创建时间:<span>${instance.createTime }</span></li>
                    
                    <li>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:<span>${instance.status }</span></li>
                </ul>
            </div>
            <div class="cz-right">
            	<ul>
               	  <li>节点ip:<span>${instance.hostIP }</span></li>
                  <li>上一次启动时间:<span>${instance.lastTime }</span></li> 
                <%
                if(request.getParameter("serviceType").equals("2")){
                	String serviceType = request.getParameter("serviceType");
                	com.cmsz.paas.web.appserviceinst.model.Instance ins =(com.cmsz.paas.web.appserviceinst.model.Instance)request.getAttribute("instance");
                	int type = ins.getType();
                	if(type==1){
                		out.println("<li>版本:<span>运行版本</span></li>");
                	}else{
                		out.println("<li>版本:<span>灰度版本</span></li>");
                	}
                }
                %>          
                </ul>
            </div>
</div>


