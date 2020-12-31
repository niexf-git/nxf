/**
 * 标准输出日志js
 */

// 初始化实例下拉框
function queryInstance() {
	serviceId = getQueryString('id');
	$.ajax({
		type : "POST",
		contentType : "application/json;utf-8",
		dataType : "json",
		url : "/paas/log/queryInstance.action?serviceId=" + serviceId,
		success : function(result) {
			if (result['resultCode'] == 'success') {
				var chlog = document.getElementById("cHLog").checked;
				var instId = "";
				if(chlog){
					instId = "chInstanceId";
				}else{
					instId = "instanceId";
				}
				document.getElementById(instId).innerHTML = "";
				var json = eval(result['resultMessage']);
				
				$.each(json, function() {
					var value = "";
					if(chlog){
						value = this.text;
					}else{
						value = this.value;
					}
					$("#"+instId).append(
							"<option value=" + value + ">" + this.text
									+ "</option>");
				});
				if(chlog){
					queryChLogs();
				}else{
					queryStdLog();
				}
			} else {
				parent
						.alertError(result['resultCode'],
								result['resultMessage']);
			}
		}
	});
}

function queryChLogs(){
	var namespace = getUrlParam("namespace");
	var instName = $("#chInstanceId").val();
	$.ajax({
		type : "POST",
		dataType : "json",
		data : {
			"namespace" : namespace,
			"instName" : instName
		},
		url : "/paas/log/queryChLogs.action",
		success : function(result) {
			if (result['resultCode'] == 'success') {
				$("#chLogsContext").html(result['resultMessage']);
			}
		}
	});
}

// 初始化时间控件
function initDate() {
	$.ajax({
		type : "POST",
		contentType : "application/json;utf-8",
		dataType : "json",
		url : "/paas/log/initDate.action",
		success : function(result) {
			if (result['resultCode'] == 'success') {
				var dateTime = $.parseJSON(result['resultMessage']);
				$('#startTime').val(dateTime['startTime']);
			}
		}
	});
}

// 拼接参数
function getParams(type) {
	var str = $.trim($('#instanceId').val());
	var instanceId = $.trim(str.split("_")[0]);
	var hostIp = $.trim(str.split("_")[1]);
	var intType = $.trim(str.split("_")[2]);
	var startTime = $.trim($("#startTime").val());
	var params = "";
	if (type == "init") {
		params = "start_" + startTime + "_" + serviceId + "_" + instanceId
				+ "_" + hostIp+ "_" + intType;
	} else if (type == "reconnection") {
		params = serviceId + "_" + instanceId + "_" + hostIp+ "_" + intType;
	}
	return params;
}

// 长连接输出标准输出日志
function queryStdLog() {
	// 校验参数
	if (!validateParameter()) {
		return;
	}
	// 关闭socket
	if (socket != undefined && socket.readyState == 1) {
		socket.close();
	}
	// 参数恢复初始值
	flag = true;
	$('#mainDiv').empty();
	$("#statusId").attr('src', '/paas/imgs/IconStop.png');
	// 新建长连接
	initWebSocket();
	var params = getParams('init');
	setTimeout(function() {
		send(params);
	}, 200);
}

var lastLine;
var socket;
// 初始化WebSocket
var initWebSocket = function() {
	if (window.WebSocket) {
		socket = new WebSocket('ws://' + window.location.host
				+ '/paas/websocket/stdLog');
		socket.onmessage = function(event) {
			var result = JSON.parse(event.data);
			lastLine = result['resultCode'];
			$("#mainDiv").append(
					'<div class="box-main">' + result['resultMessage']
							+ '</div>');
			// 滚动条拉倒div最底端
			$('#mainDiv').scrollTop($('#mainDiv')[0].scrollHeight);
		};
		socket.onopen = function(event) {
			// alert('长连接打开!');
		};
		socket.onclose = function(event) {
			// alert('长连接关闭!');
		};
		socket.onerror = function(event) {
			// alert('长连接错误!');
		};
	} else {
		alert("您的浏览器不支持 webSocket长连接！");
	}
};
// 长连接发送消息
var send = function(msg) {
	var data = function() {
		socket.send(msg);
	};
	// 正常通信时状态为1，调用close方法关闭后状态为2
	if (socket.readyState !== 1) {
		socket.close();
		initWebSocket();
		setTimeout(function() {
			data();
		}, 200);
	} else {
		data();
	}
	;
};

// 下载标准输出日志文件
function downloadStdFile() {
	if (!validateParameter()) {
		return;
	}

	var str = $('#instanceId').val();
	var instanceId = str.split("_")[0];
	var hostIp = str.split("_")[1];
	var intType = str.split("_")[2];
	var startTime = $("#startTime").val();

	var a = document.getElementById("downloadFileId");
	a.href = "/paas/log/downloadStdFile.action?serviceId=" + serviceId
			+ "&instanceId=" + instanceId +"&intType=" + intType + "&hostIp=" + hostIp + "&startTime="
			+ startTime;
	a.click("return false");
	a.href = "javaScript:downloadStdFile();";
}

//下载容器历史日志文件
function downloadChLogFile(){
	var namespace = getUrlParam("namespace");
	var instName = $("#chInstanceId").val();
	
	var a = document.getElementById("downloadChLogFileId");
	a.href = "/paas/log/downloadChLogFile.action?namespace="+namespace+"&instName="+instName;
	a.click("return false");
	a.href = "javaScript:downloadChLogFile();";
}


// 页面输入参数校验
function validateParameter() {
	if (serviceId == null || serviceId == "") {
		parent.parent.alertWarn("服务id不能为空！");
		return false;
	}
	var str = $.trim($('#instanceId').val());
	if (str == "") {
		parent.parent.alertWarn("实例名称不能为空！");
		return false;
	}
	var startTime = $.trim($("#startTime").val());
	if (startTime == "") {
		parent.parent.alertWarn("开始时间不能为空！");
		return false;
	} else {
		var start = new Date(startTime.replace("-", "/").replace("-", "/"));
		var end = new Date();
		var millis = end.getTime() - start.getTime(); // 时间差的毫秒数
		var time = 3 * 24 * 60 * 60 * 1000;// 3天
		if (millis > time) {
			parent.parent.alertWarn("开始时间距当前时间不能超过3天！");
			return false;
		}
	}
	return true;
}



var flag = true;
// 暂停/恢复长连接
function changeStatus() {
	flag = !flag;
	// 继续刷新日志
	if (flag) {
		$("#statusId").attr('src', '/paas/imgs/IconStop.png');
		var v_div = $("#mainDiv div:last-child").text();
		var msg;
		if (v_div == null || v_div == "") {
			msg = getParams('init');
		} else {
			var arr = lastLine.split(" ");
			msg = arr[0] + "_" + arr[1] + "_" + getParams('reconnection');
		}
		send(msg);
	} else {// 暂停
		$("#statusId").attr('src', '/paas/imgs/play.png');
		socket.close();
	}
}

// 重连webSocket，检测长连接是否正常,如果断开就重新连接
function reconnection() {
	var data = function(msg) {
		socket.send(msg);
	};
	// 长连接断开并且不是手动暂停
	if (socket.readyState !== 1 && flag) {
		socket.close();
		initWebSocket();
		var v_div = $("#mainDiv div:last-child").text();
		var msg;
		if (v_div == null || v_div == "") {
			msg = getParams('init');
		} else {
			var arr = lastLine.split(" ");
			msg = arr[0] + "_" + arr[1] + "_" + getParams('reconnection');
		}
		setTimeout(function() {
			data(msg);
		}, 250);
	}
}
// 每隔10秒连接一次webSocket服务器
var timer = setInterval("reconnection()", "10000");

function getUrlParam(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r!= null) 
		return unescape(r[2]);
	return null;
}
