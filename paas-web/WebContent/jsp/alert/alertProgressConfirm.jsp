<%@page import="com.cmsz.paas.web.constants.Constants"%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中国移动通信-深圳云计算PAAS管理平台</title>
<link href="${ctx}/css/alert.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script language="JavaScript" type="text/javascript">
var opra_url = null;
/* Websocket
 * 自动建立与服务的连接
 * webSocket.send：发送消息到服务端
 * webSocket.onmessage：获取从服务端返回的消息
 */

//var webSocket = new WebSocket('ws://localhost:8081/paas/websocket');
var webSocket = new WebSocket('<%=request.getParameter("websocketUrl") %>');
var sendMsgCxt;
$(function() {
	//alert(webSocket);
	if('<%=request.getParameter("opra_url") %>' == 'config-app-update'){
		opra_url = sessionStorage.getItem("opra_url");
		if(opra_url != null || opra_url != ""){
			sessionStorage.removeItem("opra_url");
		}
	}else{
		opra_url = '<%=request.getParameter("opra_url") %>';
	}
	sendMsgCxt = opra_url;
	
	//sendMsgCxt = sendMsgCxt + '_' + '<%=session.getAttribute("loginName") %>'; 	
	
	//alert(sendMsgCxt)	
	//alert(webSocket);
   // sendMsg(text);
	//window.setTimeout(sendMsg,2000); 
	
});

function sendMsg(){
	webSocket.send(sendMsgCxt);	
}

webSocket.onerror = function(event) {
	onError(event);
};

webSocket.onopen = function(event) {	
	onOpen(event);
};

webSocket.onmessage = function(event) {
	onMessage(event);
};

webSocket.onclose = function(event) {
	onClose(event);
};

function onMessage(event) {
	var retMsg = event.data;	
	var result = JSON.parse(retMsg); 
	
	if(result['resultCode']=='0'){//成功
		document.getElementById("progressGif").innerHTML = "";//去掉进度显示图片
		document.getElementById("title").innerHTML = "<img src='${ctx}/imgs/succeed.png' width='29' height='30' style='margin-right:5px; margin-bottom:-5px;'/>操作成功";
		document.getElementById("loadText").innerHTML = result['resultMessage'];
		parent.showConfirmButton();
		$("#closeSpan").hide();
    }else if(result['resultCode']=='1'){//完成
		document.getElementById("progressGif").innerHTML = "";//去掉进度显示图片
		document.getElementById("title").innerHTML = "<img src='${ctx}/imgs/succeed.png' width='29' height='30' style='margin-right:5px; margin-bottom:-5px;'/>操作完成";
		document.getElementById("loadText").innerHTML = result['resultMessage'];
		parent.showConfirmButton();
		$("#closeSpan").hide();
    }else if(result['resultCode'].indexOf('PAAS-00') > -1){//中间返回结果
    	document.getElementById("loadText").innerHTML = result['resultMessage'];
	}else{//出错	
		document.getElementById("progressGif").innerHTML = "";//去掉进度显示图片
		document.getElementById("title").innerHTML = "<img src='${ctx}/imgs/runtime-error.png' width='29' height='30' style='margin-right:5px; margin-bottom:-5px;'/>操作失败";
		document.getElementById("loadText").innerHTML = result['resultMessage'];
		parent.showCloseButton();
	}
}

function onOpen(event) {
	//if(event.data!=null){
	//	sendMsg();
	//}else{alert(event.data)
		//document.getElementById('loadText').innerHTML = '正在建立连接...';	
		window.setTimeout(sendMsg,2000); 
	//}
}

function onError(event) {
	alert('长连接错误!');
}

function onClose(event){
	//alert(event.data);
}

	</script>
</head>
<body>
	<div class="alert">
		
			<div class="alert-main-title">
				<h1 id="title"><img src="${ctx}/imgs/succeed.png" width="29" height="30" style="margin-right:5px; margin-bottom:-5px;"/>请稍后...</h1>
				<span id="closeSpan">
					<a href="JavaScript:parent.closeAlertProgres();" style="padding-right:10px">
						<img  src="${ctx}/imgs/alert-close.png" width="22" height="22" border="0" />
					</a>
				</span>
			</div>
			<div align="center" id="progressGif" style="height:15px;padding-top:30px;"><img  alt="" src="${ctx}/imgs/load.gif"></img></div>
			<div align="left" id="loadImg" class="alert-main-content">			  
			  <div id="loadText" style="color:#000;">正在建立连接，请稍后...</div>   
			</div>			
			<div class="alert-main-input">
			</div>
			
		</div>
		<div style="clear:both;"></div>
</body>
</html>

