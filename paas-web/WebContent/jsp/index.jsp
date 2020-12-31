<%@page import="com.cmsz.paas.web.constants.Constants"%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>中国移动通信 - 深圳云计算PAAS管理平台</title>
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/jquery-ui-1.8.23.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/index.js"></script>
<script language="JavaScript" type="text/javascript">
//左边菜单区登录后缺省展示
$(function(){
	$('.main-left ul li').each(function(){
		  $(this).click(function(){			    
		    $('.main-left ul li').css({"background":"none","color":"#000"});//清空所有的背景
		    
		    $('.main-left ul li').find("img").each(function(i,value){//alert(value)
		    	var bb= $(this).attr("src").split(".");
		    	 var newImg = bb[0]+".png";    		
				    //if(bb[0].indexOf("application")>1){//最后一个菜单
				   //	newImg =bb[0].replace("_hover","")+".png";				    	
				   // }else{
				    	newImg = bb[0]+".png";				    	
				    //}
				   
				    $(this).attr("src",newImg);
		    });
		 
		    $(this).css({"background":"url(../imgs/menu-hover.jpg)","color":"#fff"});//当前按钮加背景
		    
		  
		    var imgs = $(this).find("img").attr("src");
		    var bb = imgs.split(".");
		    var newImg="";
		    if(bb[0].indexOf("")>1){
		    	newImg =bb[0]+".png";
		    }else{
		    	newImg = bb[0]+".png";
		    }
		    
		    $(this).find("img").attr("src",newImg);
		  });
		});

	$('.main-left ul li:first').click();
	
	// 当点击其它页面时,清除进度条图片 
	$("a").click(function(){
		closeLoad();
	});
	
	var timeShow = "";
	var timeHide = "";
	function doSearch(){
		clearTimeout(timeHide);
		clearInterval(timeShow);
		timeShow = setInterval(function(){
			//do ajax
			$.ajax({
				type : "get",
				dataType : "json",
				url : '/paas/alarm/queryAlarmByTime.action',
				success : function(result) {
					if(result['resultMessage']){
						var bToObj=JSON.parse(result['resultMessage']);
						var resultStr = "";
						$.each(bToObj, function(i,val){//此处设置需要展示的内容，以podName为例
							resultStr += val['podName'] +"在"+val['insertTime']+val['eventItem']+ "<br/>";
						});
						if(resultStr){
							$('#message').show();
							$("#messageContent").html(resultStr);
							timeHide = setTimeout(function(){
								$('#message').fadeOut('slow');
								doSearch();
							},1000*3);
						}
					}
				}
			});
		},1000*10);
	}
	doSearch();
	
	$('#message').hover(function(){
		clearTimeout(timeHide);
		clearInterval(timeShow);
	});
	$('#message').live("mouseout", function () {
	  	var s = event.toElement || event.relatedTarget; 
        if (!this.contains(s)) {
			doSearch();
        }
	});
	switchAppInfo();
	
});


function switchAppInfo(){
	var appPermissionIds = '<%=session.getAttribute("appPerSelectedId")%>';
	var appPermissionNames ='<%=session.getAttribute("appPerSelectedName")%>';
	var opertypes = '<%=session.getAttribute("selectedOpertype")%>';
	if(appPermissionIds.indexOf(",")>-1 || appPermissionIds == ''){
		$("#appText").text("全部");
	}else{
		$("#appText").text(appPermissionNames);
	}
	
	if(isNaN(opertypes)){
		$("#operTypeText").text("全部");
	}else{
		if(opertypes == 1){
			opertypes = "开发";
		}else if(opertypes ==  2){
			opertypes = "测试";
		}else if(opertypes == 3){
			opertypes = "运维";
		}else{
			opertypes = "全部";
		}
		$("#operTypeText").text(opertypes);
	}
	
}
function switchAppInfoDialog(tips){
	openDialog("/paas/jsp/switchAppInfo.jsp?tips="+tips,"应用切换",600,200);
}
//从主页展示进度条
function showLoad(){	
	var width = document.documentElement.scrollWidth+"px";
	var height = document.documentElement.scrollHeight+"px";
	
	$('#loadImg').css({"width":"100%","height":"90%"});
	$('#loadImg').show();
}
function closeLoad(){
	$('#loadImg').hide();
}
function closeMessage(){
	$('#message').fadeOut('slow');
}

$(document).ready(function () {  
    reSizeDiv();
});

function reSizeDiv() {  
    var s = document.body.offsetWidth;  //(带浏览器边框的宽度)  
    //var s = document.body.clientWidth; //(不带浏览器边框的宽度)  
    if (isFirefox = navigator.userAgent.indexOf("Firefox") > 0)  
        s = window.innerWidth;  

    var a = 1024;  //要变换的临界点  
    var w = "1022px"; //DIV宽度（像素）  
    var w1 = "100%";  //DIV宽度（百分比）  
    var d = document.getElementById("div_d1");  
    if (s > a) {  
        //d.style.width = w;  
    }  
    else {  
        d.style.width = w1;  
    }  
}  
//从构建列表链接到镜像列表，左侧菜单样式修改
function linkImageUpdateCss(source, target){
	$("#"+source).css({"background":"none","color":"#000"});
	$("#"+target).css({"background":"url(../imgs/menu-hover.jpg)","color":"#fff"});
}

<!--关闭浏览器   
var flag = true;  
window.onbeforeunload = function () {
    if (flag) {
       var userAgent = navigator.userAgent;  
       if (userAgent.indexOf("MSIE") > 0) {
           var n = window.event.screenX - window.screenLeft;  
           var b = n > document.documentElement.scrollWidth - 20;  
           if (b && window.event.clientY < 0 || window.event.altKey) {  
               window.event.returnValue = ("该操作将会导致非正常退出系统！");
           }else {
        	   clear4Close(); //关闭浏览器标签页的时候，清除缓存
               //return ("该操作将会导致非正常退出系统！");
           }
       } else if (userAgent.indexOf("Firefox") > 0) {
    	   clear4Close();
       } else if (userAgent.indexOf("Chrome") > 0) {
    	   clear4Close();
       }
    }
}
-->
</script>
</head>

<body>

<div class="top">
  <div class="top-left"><img src="${ctx}/imgs/logo.png" width="200" height="34" /></div>
  <div class="top-con"><a href="javascript:void(0);" id="appText" onclick="switchAppInfoDialog('');">全部</a><!-- |<a href="javascript:void(0);" id="operTypeText" onclick="switchAppInfoDialog('')">全部</a> --></div>
  <div class="top-right">
    	<h1><span><img src="${ctx}/imgs/user.png" width="23" height="23" /></span><strong style="font-size:14px;">${sessionScope.loginName}</strong> 欢迎登录使用!</h1>
        <ul>
       	  <li><a href="javascript:updatePassword()"><span><img src="${ctx}/imgs/top-modify.png" width="25" height="25" /></span>修改密码</a></li>
          <li style="width:101px;"><a href="javascript:openwinll()" style="width:101px;"> <span><img src="${ctx}/imgs/logout.png" width="25" height="25" /></span>注销</a></li>
        </ul>
    </div>
</div>

<div class="main">
	<!-- 左边菜单区 -->
	<div class="main-left">
     	<ul>
     		<c:forEach var="menu" items="${operPermission}" varStatus="status">	     		    	
	        	 <a href="${ctx}${menu.url}" target="aaa">	        	
	        	 <c:choose>  
	                <c:when test="${status.last}"><!-- 去掉最后一个菜单下面的横线-->
	                	<c:if test="${menu.id eq 9}"><!-- 管理员有九个菜单，去掉最后一个菜单下面的横线 -->
	                		<li id="${menu.id}" style="border-bottom:0px;">
			               		 <span>
				        			<img src="${ctx}/${menu.img}" width="30" height="30" border="0" align="middle" />
				        		 </span>
			        		     <h1 style="font-size:14px; margin-top:5px; font-weight:normal;">${menu.name}</h1>		        		
			        	    </li>
	                	</c:if>
	               		<c:if test="${menu.id ne 9}"><!-- 普通用户小于九个菜单，不去掉最后一个菜单下面的横线 -->
	                		<li id="${menu.id}">
			               		 <span>
				        			<img src="${ctx}/${menu.img}" width="30" height="30" border="0" align="middle" />
				        		 </span>
			        		     <h1 style="font-size:14px; margin-top:5px; font-weight:normal;">${menu.name}</h1>		        		
			        	    </li>
	                	</c:if>
	                </c:when>  
	                <c:otherwise>
	                	<li id="${menu.id}">
		                	<span>
			        			<img src="${ctx}/${menu.img}" width="30" height="30" border="0" align="middle" />
			        		</span>
			        		<h1 style="font-size:14px; margin-top:5px; font-weight:normal;">${menu.name}</h1>		        		
		        		</li>
	                </c:otherwise>  
           		 </c:choose>  	 	
	        	
	        	</a>
		       </c:forEach>
         </ul>
    </div>
    
    <!-- 右边工作区 -->
    <div class="main-right">
      <div class="main-right-main">
      	<div><iframe id="mainIframe" src="${ctx}/jsp/appservice/appserviceList.jsp?name=全部" width="100%"  frameborder="0" name="aaa" scrolling="auto"></iframe></div>
     	<div id="loadImg" style="display:none"><img alt="" src="${ctx}/imgs/load.gif"></img></div>
      </div>   
    </div>
</div>
<div id="message" style="display:none;">
	<h4>&nbsp;系统告警<a href="javascript:closeMessage()" style="float:right">关闭&nbsp;</a></h4>
	<div id="messageContent"></div>
</div>
</body>
</html>
