<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/js/appeasyui/easyui.css" rel="stylesheet"	type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/appeasyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/appeasyui/themes/icon.css" />

<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/appeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/ipaasservice/ipaasservice.js"></script> 
<script type="text/javascript">
	var ipaasServiceId;
	var ipaasServiceName;
	var jspUrl;
	$(function() {
		ipaasServiceId = "<%=request.getParameter("ipaasServiceId") %>";
		ipaasServiceName = "<%=request.getParameter("ipaasServiceName") %>";
		setAhref();		
		$('.ipaas-service-menu-main ul li').each(function() {//todo
			$(this).click(function() {
				$('.ipaas-service-menu-main ul li').css({"background":"none","color":"#000"});//清空所有的背景			
								
				$('.ipaas-service-menu-main ul li').find("img").each(function(i){
			    	var bb= $(this).attr("src").split(".");
			    	 var newImg="";
					    if(bb[0].indexOf("-hover")>1){//将之前点击的菜单里面的图片换成无点击的图片
					    	newImg =bb[0].replace("-hover","")+".png";
					    }else{//如果之前菜单没有点击过，里面图片也统一换成无点击的图片
					    	newImg = bb[0]+".png";
					    }
					   
					    $(this).attr("src",newImg);

			    });
				
				$(this).css({"background":"#333","color":"#fff"});//当前菜单加背景				
				
				//最后将当前点击菜单的图片换成点击后的图片
				var imgs = $(this).find("img").attr("src");//alert(imgs)
			    var bb = imgs.split(".");
			    var newImg=bb[0]+"-hover.png";			    
			    $(this).find("img").attr("src",newImg);
			});
		});
		
		// 给所有超链接动态加入事件 
		$("a").click(function(){
			jspUrl = $(this).attr("id");
		});
	});
	$(document).ready(function () {
		var unLoaded = true;
		$('#a').find('img').click();
	});
	function setAhref(){//to-do
		var a = document.getElementById("a"); 
		a.href = "${ctx}/ipaasservice/queryIpaasServiceById.action?ipaasServiceId="+ipaasServiceId+"&functionModule=operationArea";
		var b = document.getElementById("b"); 
		b.href = "${ctx}/jsp/ipaasservice/instList.jsp?ipaasServiceId="+ipaasServiceId;
		var c = document.getElementById("c"); 		
		c.href = "${ctx}/ipaasservice/queryIpaasServiceById.action?ipaasServiceId="+ipaasServiceId+"&functionModule=configure";
		var d = document.getElementById("d"); 		
		//d.href = "/paas-grafana/appMonitor.html#/dashboard/file/" + ipaasServiceId + ".json";//todo
		d.href ="${ctx}/jsp/ipaasservice/ipaasGrafana.jsp?ipaasServiceId="+ipaasServiceId;
		var e = document.getElementById("e"); 
		e.href = "${ctx}/jsp/ipaasservice/ipaasServiceLog.jsp?id="+ipaasServiceId;
		
		// 判断是否停留在某个页面,如果是页面不能跳转 
		/* if(jspUrl=="" || jspUrl==null){
			a.click("return false");
		}else{
			var aa = document.getElementById(jspUrl);
			aa.click("return false");
		} */
	}
</script>
<style>
	 html,body{ overflow-x:hidden; overflow-y:auto;}
</style>
<div class="ipaas-service-menu">
  	<div class="ipaas-service-menu-main">
      	<ul>
         	  <a href="" target="bbb" id="a"><li><img src="${ctx}/imgs/Operation.png" width="29" height="29" /><strong>操作区</strong><span>Operation area</span></li></a>
              <a href="" target="bbb" id="b"><li><img src="${ctx}/imgs/Example.png" width="29" height="29" /><strong>实例</strong><span>Instance</span></li></a>
              <a href="" target="bbb" id="c"><li><img src="${ctx}/imgs/configure.png" width="29" height="29" /><strong>配置</strong><span>Configure</span></li></a>
              <a href="" target="bbb" id="d"><li><img src="${ctx}/imgs/ServiceMonitor.png" width="29" height="29" /><strong>监控</strong><span>Monitor</span></li></a>
              <a href="" target="bbb" id="e"><li style="border-right:0px;"><img src="${ctx}/imgs/Log.png" width="29" height="29" /><strong>日志</strong><span>Log</span></li></a>
        </ul>
	</div>
</div>
<div class="main-Content">
	<iframe id="mainIframe" src=""
		width="100%" height="450" frameborder="0" name="bbb" scrolling="auto"></iframe>
</div>
