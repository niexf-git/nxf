<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%
	//获取当前用户角色类型：
	String roleType = session.getAttribute("roleType")+"";
	String opertype = session.getAttribute("opertype")+"";
	// 当前网络环境标示
	String currentContext = session.getAttribute("currentContext")+"";
%>

<link href="${ctx}/css/index.css" rel="stylesheet" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet" />

<script src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script src="${ctx}/js/grid.locale-cn.js"></script>
<script src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script src="${ctx}/js/imagemanager/publicimage/publicimage.js"></script>
<script src="${ctx}/js/utils.js"></script><!-- getUrlParam页面传参 -->

<!-- 单元格内容换行 -->
<style>
	.ui-jqgrid tr.jqgrow td {
		//一行数据过多需要自动根据内容换行时，如果遇到在表格中的汉字换行或者空格换行的问题，可以在style标签中插入下面代码：
	  	white-space : normal !important;
	    //height:auto;
        //vertical-align:central;
        //padding-top:2px;
	    word-wrap : break-word;//如果遇到一长串的英文单词，需要在英文单词内部换行（非空格处）问题时，可以在style标签中插入该代码。
	 }
html,body{ overflow-x:hidden;}
</style>

    	<input id="roleType" name="roleType" value="${roleType }" hidden="true" >
    	<input id="opertype" name="opertype" value="${opertype }" hidden="true" >
    	<input id="currentContext" name="currentContext" value="${currentContext }" hidden="true" >
    	<!-- 
    	<div class="image-menu">
        	<div class="image-menu-content">
            	<ul>
                	<li>
                		<a href="${ctx}/jsp/imagemanager/privateimage/listPrivateImage.jsp" target="aaa"
                		 style="border-radius:10px 0px 0px 10px;">私有镜像</a>
                	</li>
                    <li style="border-right:none;background:#000000; border-radius:0px 10px 10px 0px;">
                    	<a href="${ctx}/jsp/imagemanager/publicimage/listPublicImage.jsp" target="aaa">公共镜像</a>
                    </li>
               </ul>
          	</div>
         </div>
          -->
    	<div class="Establish">
        	<div class="Establish-left">
	       	  	<div class="name">公共镜像名称：</div>
	            <div class="queryService">
		            <input type="text" id="publicImageName" name="publicImageName" maxlength="120" placeholder="支持模糊查询" onkeyup="value=value.replace(/[^A-Za-z0-9\-\_\/\.]/g,'')" />
	            	<span><input name="" id="search" type="button" value="" onclick="reloadResult()" /></span>
	            </div>
            </div>
      	</div>
		
		<div class="main-Content">
			<div class="content-tab">
				<table cellspacing="0" id="publicImageList"></table>
				<div id="pagerBar"></div>
			</div>
		</div>

 
