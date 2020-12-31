var callback = null;
function openwinll() {
	result = "确认退出当前用户？";
	alertConfirm(result,function(){
		window.location.href = "/paas/user/logout";
   });
}
/**
 * 关闭浏览器标签页的时候，清除缓存
 */
//function clear4Close(){
//	$.ajax({
//		type : "get",
//		dataType : "json",
//		url : '/paas/user/logout'
//	});
//}

function openDialog(url, titleStr, widthValue, heightValue) {
	$("<iframe id='dialogIf' frameborder='0' src='" + url + "'/>\"").dialog({
		title : titleStr,
		autoOpen : true,
		draggable : false,
		resizable : false,
		closeOnEscape: false,
		/* open:function(event,ui){$(".ui-dialog-titlebar").hide();}, */
		modal : true,
		height : heightValue,
		close : function() {
			$("#dialogIf").remove();
		},
		position : 'center',
		width : widthValue
	}).width(widthValue).height(heightValue);
}
function updatePassword() {
	openDialog("./updatePassword.jsp", "修改密码", 610,230);
	/*
	 * $('<iframe id="dialogIf1" frameborder="0"
	 * src="${ctx}/userManager/toUpdateUser.action"/>').dialog({ title:"更改用户信息",
	 * autoOpen: true, modal:true, draggable:false, resizable:false, height:
	 * 300, position: 'center', width: 700 }).width(700).height(300);
	 */
	/*
	 * var returnValue =
	 * window.showModalDialog("${ctx}/userManager/toUpdateUser.action",new
	 * Object(), "dialogWidth=750px;dialogHeight=700px;status:no;scroll:no;");
	 * if(returnValue != undefined && returnValue.length > 1) {
	 * document.getElementById("userName").innerHTML=returnValue; }
	 */
}

function closedl(dialogType, value) {
	if (dialogType != "updatePassword") {
		$("#mainIframe")[0].contentWindow.reloadResult();
	}
	close();
}
function close() {
	$("#dialogIf").remove();
}
// var id;
// function changeCss(obj,img){
// $(id).css({"background":"url(../imgs/menu.jpg) no-repeat","color":"#000"});
// $("#"+obj).css({"background":"url(../imgs/menu-hover.jpg)
// no-repeat","color":"red"});
// id="#"+obj;
// }

function closeAlert() {
	if(callback != null){
		callback.call();
		$("#dialogIfAlert").remove();
	}else{
		$("#dialogIfAlert").remove();
	}
	//window.location.reload();//todo,是否app一个，version一个closeAlert方法
}

function closeAlert2() {
	$("#dialogIfAlert").remove();
	//window.location.reload();//todo,是否app一个，version一个closeAlert方法
}
function closeAlertConfirm() {
	$("#dialogIfAlertConfirm").remove();
}

function closeAlertConfirm4AllAndSelf(){
	$("#dialog4AllAndSelf").remove();
}

function closeAlertConfirmOnClickFork(){
	closeAlertConfirm();
	closeAlertConfirm4AllAndSelf();
	//只要存在这个弹出框就会返回1，循环删除所有
	while($("#dialogIf").length == 1){
		$("#dialogIf").remove();
	}
}
/**
 * 弹出警告层
 * @param msg
 */
function alertWarn(msg) {
	$(
			"<iframe id='dialogIfAlert' frameborder='0' src='/paas/jsp/alert/alertWarn.jsp?msg=" + msg + "'/>\"")
			.dialog({
				autoOpen : true,
				draggable : false,
				resizable : false,
				modal : true,
				open : function(event, ui) {
					$("#dialogIfAlert").prev().hide();
					$(".ui-dialog-content").css("padding", "0px");
					$(".ui-dialog").css("border", "none");
					$(".ui-dialog").css("padding", "0px");
					$("#dialogIfAlert").parent().height(211);
				},
				height : 211,
				close : function() {
					$("#dialogIfAlert").remove();
				},
				position : 'center',
				width : 403
			}).width(403).height(211);
}

function alertWarn(msg,callback){
	alertWarn(msg,callback,false);
}

/**
 * 弹出警告层
 * @param msg
 */
function alertWarn(msg,callback,isClose) {
	$("#dialogIfAlert").remove();
	this.callback = callback;
	$(
			"<iframe id='dialogIfAlert' frameborder='0' src='/paas/jsp/alert/alertWarn.jsp?msg=" + msg + "&isClose="+isClose+"'/>\"")
			.dialog({
				autoOpen : true,
				draggable : false,
				resizable : false,
				modal : true,
				open : function(event, ui) {
					$("#dialogIfAlert").prev().hide();
					$(".ui-dialog-content").css("padding", "0px");
					$(".ui-dialog").css("border", "none");
					$(".ui-dialog").css("padding", "0px");
					$("#dialogIfAlert").parent().height(211);
				},
		        buttons: {
		            "确定": function() {
		            	callback.call();
		                $("#dialogIfAlert").remove();
		            }
		        },
				height : 211,
				close : function() {
					$("#dialogIfAlert").remove();
				},
				position : 'center',
				width : 403
			}).width(403).height(211);
}
/**
 * 弹出错误层
 * @param code
 * @param msg
 */
function alertError(code, msg) {
	$(
			"<iframe id='dialogIfAlert' frameborder='0' src='"
					+ "/paas/jsp/alert/alertError.jsp?code=" + code + "&msg=" + msg + "'/>\"")
			.dialog({
				autoOpen : true,
				draggable : false,
				resizable : false,
				modal : true,
				open : function(event, ui) {
					$("#dialogIfAlert").prev().hide();
					$(".ui-dialog-content").css("padding", "0px");
					$(".ui-dialog").css("border", "none");
					$(".ui-dialog").css("padding", "0px");
					$("#dialogIfAlert").parent().height(211);
				},
				height : 211,
				close : function() {
					$("#dialogIfAlert").remove();
				},
				position : 'center',
				width : 403
			}).width(403).height(211);
}

/**
 * 弹出错误层
 * @param code
 * @param msg
 */
function alertError(code, msg ,callback) {
	this.callback = callback;
	$(
			"<iframe id='dialogIfAlert' frameborder='0' src='"
					+ "/paas/jsp/alert/alertError.jsp?code=" + code + "&msg=" + msg + "'/>\"")
			.dialog({
				autoOpen : true,
				draggable : false,
				resizable : false,
				modal : true,
				closeOnEscape: false,
				open : function(event, ui) {
					$("#dialogIfAlert").prev().hide();
					$(".ui-dialog-content").css("padding", "0px");
					$(".ui-dialog").css("border", "none");
					$(".ui-dialog").css("padding", "0px");
					$("#dialogIfAlert").parent().height(211);
				},
		        buttons: {
		            "确定": function() {
		            	callback.call();
		                $("#dialogIfAlert").remove();
		            }
		        },
				height : 211,
				close : function() {
					$("#dialogIfAlert").remove();
				},
				position : 'center',
				width : 403
			}).width(403).height(211);
}

/**
 * 弹出成功层
 */
function alertSuccess() {
	$(
			"<iframe id='dialogIfAlert' frameborder='0' src='/paas/jsp/alert/alertSuccess.jsp'/>\"")
			.dialog({
				autoOpen : true,
				draggable : false,
				resizable : false,
				modal : true,
				open : function(event, ui) {
					$("#dialogIfAlert").prev().hide();
					$(".ui-dialog-content").css("padding", "0px");
					$(".ui-dialog").css("border", "none");
					$(".ui-dialog").css("padding", "0px");
					$("#dialogIfAlert").parent().height(211);
				},
				height : 211,
				close : function() {
					$("#dialogIfAlert").remove();
				},
				position : 'center',
				width : 403
			}).width(403).height(211);
}

/**
 * 弹出成功层
 */
function alertSuccess(callback) {
	this.callback = callback;
	$(
			"<iframe id='dialogIfAlert' frameborder='0' src='/paas/jsp/alert/alertSuccess.jsp'/>\"")
			.dialog({
				autoOpen : true,
				draggable : false,
				resizable : false,
				modal : true,
				closeOnEscape: false,
		        position:'center',
				open : function(event, ui) {
					$("#dialogIfAlert").prev().hide();
					$(".ui-dialog-content").css("padding", "0px");
					$(".ui-dialog").css("border", "none");
					$(".ui-dialog").css("padding", "0px");
					$("#dialogIfAlert").parent().height(211);
				},
		        buttons: {
		            "确定": function() {
		            	if(callback != null){
		            		callback.call();//方法回调
		            	}
		                $("#dialogIfAlert").remove();
		            }
		        },
				height : 211,
				close : function() {
					$("#dialogIfAlert").remove();
				},
				position : 'center',
				width : 403
			}).width(403).height(211);
}

/**
 * 弹出选择confirm对话层，
 * @param text 显示的内容
 * @param callback4All 一键启动回调
 * * @param callback4Self 点击确定回调
 * @param canelback 点击取消回调
 */
function alertConfirm4AllAndSelf(text, callback4All, callback4Self, canelback) {
    $("<iframe id='dialog4AllAndSelf' frameborder='0' src='/paas/jsp/alert/alertConfirm.jsp?msg=" + text + "'/>\"").dialog({
        modal: true,
        resizable: false,
        closeOnEscape: false,
        position:'center',
        open : function(event, ui) {
			$("#dialog4AllAndSelf").prev().hide();//隐藏titlebar
			
			$("#dialog4AllAndSelf").css("width", "380px");
			$("#dialog4AllAndSelf").parent().css("left", "449px");
			$("#dialog4AllAndSelf").parent().css("top", "240px");//confirm顶距
			$("#dialog4AllAndSelf").parent().css("width", "380px");

			$("#dialog4AllAndSelf").parent().css("background", "#f7f7f7");
			$("#dialog4AllAndSelf").parent().css("border", "5px solid #c6c6c6");
			$("#dialog4AllAndSelf").parent().css("border-top-left-radius", "10px");
			$("#dialog4AllAndSelf").parent().css("border-top-right-radius", "10px");
			$("#dialog4AllAndSelf").parent().css("border-bottom-left-radius", "10px");
			$("#dialog4AllAndSelf").parent().css("border-bottom-right-radius", "10px");
			$("#dialog4AllAndSelf").parent().css("padding-left", "10px");

			$("#dialog4AllAndSelf").next().css("border", "none");
			$("#dialog4AllAndSelf").next().css("background", "none");
			$("#dialog4AllAndSelf").parent().height(211);
		},
        buttons: {
            "一键启动": function() {
            	callback4All.call();//方法回调
                closeAlertConfirm4AllAndSelf();
            },
            "启动应用服务": function() {
            	callback4Self.call();//方法回调
                closeAlertConfirm4AllAndSelf();
            },
            "取消": function() {
            	if(canelback){
            		canelback.call();//取消方法回调
            	}
            	closeAlertConfirm4AllAndSelf();
            }
        }
    });
}

/**
 * 弹出选择confirm对话层，
 * @param text 显示的内容
 * @param callback 点击确定回调
 * @param canelback 点击取消回调
 */
function alertConfirm(text, callback, canelback) {
	closeAlertConfirm();
    $("<iframe id='dialogIfAlertConfirm' frameborder='0' src='/paas/jsp/alert/alertConfirm.jsp?msg=" + text + "'/>\"").dialog({
        modal: true,
        resizable: false,
        closeOnEscape: false,
        position:'center',
        open : function(event, ui) {
			$("#dialogIfAlertConfirm").prev().hide();//隐藏titlebar
			
			$("#dialogIfAlertConfirm").css("width", "380px");
			$("#dialogIfAlertConfirm").parent().css("left", "449px");
			$("#dialogIfAlertConfirm").parent().css("top", "240px");//confirm顶距
			$("#dialogIfAlertConfirm").parent().css("width", "380px");

			$("#dialogIfAlertConfirm").parent().css("background", "#f7f7f7");
			$("#dialogIfAlertConfirm").parent().css("border", "5px solid #c6c6c6");
			$("#dialogIfAlertConfirm").parent().css("border-top-left-radius", "10px");
			$("#dialogIfAlertConfirm").parent().css("border-top-right-radius", "10px");
			$("#dialogIfAlertConfirm").parent().css("border-bottom-left-radius", "10px");
			$("#dialogIfAlertConfirm").parent().css("border-bottom-right-radius", "10px");
			$("#dialogIfAlertConfirm").parent().css("padding-left", "10px");

			$("#dialogIfAlertConfirm").next().css("border", "none");
			$("#dialogIfAlertConfirm").next().css("background", "none");
			$("#dialogIfAlertConfirm").parent().height(211);
		},
        buttons: {
            "确认": function() {
                callback.call();//方法回调
                closeAlertConfirm();
            },
            "取消": function() {
            	if(canelback){
            		canelback.call();//取消方法回调
            	}
            	closeAlertConfirm();
            }
        }
    });
}

/**
 * 弹出进度提示层
 */
function alertProgress(opra_url,websocketUrl) {
	  $(
			"<iframe id='dialogIfAlert' frameborder='0' src='/paas/jsp/alert/alertProgress.jsp?opra_url="+opra_url+"&websocketUrl="+websocketUrl+"'/>\"")
			.dialog({
				autoOpen : true,
				draggable : false,
				resizable : false,
				modal : true,
				open : function(event, ui) {
					$("#dialogIfAlert").prev().hide();
					$(".ui-dialog-content").css("padding", "0px");
					$(".ui-dialog").css("border", "none");
					$(".ui-dialog").css("padding", "0px");
					$("#dialogIfAlert").parent().height(211);
				},
				height : 211,
				close : function() {
					$("#dialogIfAlert").remove();
					window.location.reload();
				},
				position : 'center',
				width : 403
			}).width(403).height(211);
}

function alertProgressConfirm(opra_url,websocketUrl,callback,closeback) {
	//opra_url = opra_url.indexOf("|")>-1?opra_url.replace('|','%7C'):opra_url;
	$(
		"<iframe id='dialogIfAlertProgress' frameborder='0' src='/paas/jsp/alert/alertProgressConfirm.jsp?opra_url="+opra_url+"&websocketUrl="+websocketUrl+"'/>\"")
		.dialog({
			modal: true,
			draggable : false,
	        resizable: false,
	        closeOnEscape: false,
	        position:'center',
	        open : function(event, ui) {
				$("#dialogIfAlertProgress").prev().hide();//隐藏titlebar
	        	//$("#dialogIfAlertProgress").prev().css("background", "#f7f7f7");
	        	//$("#dialogIfAlertProgress").prev().css("border", "none");
	        	
				$("#dialogIfAlertProgress").css("width", "380px");
				$("#dialogIfAlertProgress").parent().css("left", "449px");
				$("#dialogIfAlertProgress").parent().css("top", "240px");//confirm顶距
				$("#dialogIfAlertProgress").parent().css("width", "380px");

				$("#dialogIfAlertProgress").parent().css("background", "#f7f7f7");
				$("#dialogIfAlertProgress").parent().css("border", "5px solid #c6c6c6");
				$("#dialogIfAlertProgress").parent().css("border-top-left-radius", "10px");
				$("#dialogIfAlertProgress").parent().css("border-top-right-radius", "10px");
				$("#dialogIfAlertProgress").parent().css("border-bottom-left-radius", "10px");
				$("#dialogIfAlertProgress").parent().css("border-bottom-right-radius", "10px");
				//$("#dialogIfAlertProgress").parent().css("padding-left", "10px");

				$("#dialogIfAlertProgress").next().css("border", "none");
				$("#dialogIfAlertProgress").next().css("background", "none");
				$("#dialogIfAlertProgress").parent().height(211);
				$(".ui-dialog-buttonpane button").hide(); //隐藏dialog中所有button
			},
			/*close : function() {
			},*/
			//height : 228,
	        buttons: {
	            "确认": function() {
	                callback.call();//方法回调
	                closeAlertProgress();
	            },
	            "关闭": function() {
	            	if(closeback != undefined){
	            		closeAlertProgress();
	            		closeback.call();//方法回调
	            	}else{
	            		closeAlertProgres();
	            	}
	            }
	        }
	    });
}
function showConfirmButton(){
	$(".ui-dialog-buttonpane button").eq(0).show();
}
function showCloseButton(){
	$(".ui-dialog-buttonpane button").eq(1).show();
}
function closeAlertProgres(){
	$("#dialogIfAlertProgress").remove();
}
function closeAlertProgress() {
	$("#dialogIfAlertProgress").remove();
	//只要存在这个弹出框就会返回1，循环删除所有
	while($("#dialogIf").length == 1){
		$("#dialogIf").remove();
	}
}

//长链接js
//var webSocket = new WebSocket("ws://"+window.location.host+"/paas/websocket/userManager");
//var sendMsgCxt = "startUp";
//function sendMsg(){
//	webSocket.send(sendMsgCxt);	
//}
//
//webSocket.onerror = function(event) {
//	onError(event);
//};
//
//webSocket.onopen = function(event) {	
//	onOpen(event);
//};
//
//webSocket.onmessage = function(event) {
//	onMessage(event);
//};
//
//webSocket.onclose = function(event) {
//	onClose(event);
//};
//
//function onMessage(event) {
//	var retMsg = event.data;	
//	if(0!=retMsg){
//		alertWarn(retMsg,function(){
//			  window.localStorage.clear(); //清空缓存
//	          window.sessionStorage.clear();
//	          window.location.replace(window.location.origin + "/paas/jsp/index.jsp");
//		},true);
//	}
//}
//
//function onOpen(event) {
//	window.setTimeout(sendMsg,2000);
//}
//
//function onError(event) {
//	alert('长连接错误!');
//}
//
//function onClose(event){
//	//alert(event.data);
//}
