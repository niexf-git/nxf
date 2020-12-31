/**
 * 构建记录列表js
 */
var buildId;
var buildName;
//初始化构建列表 
$(function() {
	 banBackSpace();
	 buildId = $.getUrlParam('buildId');
	 buildName = $.getUrlParam('buildName');
	//查询条件输入字符串回车
	$("#token").keydown(function(e) {
		if ((e.keyCode || e.which) == 13) {
			queryBuildRecordList();
		}
	});
	loadBuildRecordList();
});

//初始化时导入构建列表
var loadBuildRecordList = function(){
	$('#queryBuildRecordList').jqGrid({
				url : '/paas/build/queryBuildRecordList.action',
				datatype : "json",
				width: '1094px',
				height : "100%",
				postData:{
					buildId : buildId.toString(),
					buildName : buildName.toString()
				},
				jsonReader : {
					repeatitems : false,
					root : 'result',
					id : 'id2',//不能使用id(实体中的id)
					page:  function(obj) {return obj.pageNo; },
					total: function(obj) { return obj.totalPages; },
					records: function(obj) { return obj.totalCount; },
				    userdata: "userdata"
				},
				colNames : [ '构建名称', '状态', '镜像版本', '代码版本', '开始时间', '结束时间', '操作用户', '' ],
				colModel : [
						{
							 name : "name",
							index : "name",
							align : 'center',
							width : '140px',
						},
						{
							 name : "status",
							index : "status",
							align : "center",
							width : '120px',
							formatter : function(val, options, rowObject) {
								var _status = rowObject['status'];
								if (_status == "1") {
									return '构建中 '+'<img src="../../imgs/building.gif" width="16" height="7" border="none" title="构建中"/>';
								} else if (_status == "2") {
									return '<img src="../../imgs/build_success.png" width="10" height="10" border="none" title="成功"/>'+' 成功';
								} else if (_status == "3"){
									return '<img src="../../imgs/build_call_fail.png" width="10" height="10" border="none" title="调度失败"/>'+' 调度失败';
								} else if (_status == "4"){
									return '<img src="../../imgs/build_fail.png" width="10" height="10" border="none" title="失败"/>'+' 失败';
								} else{
									return '-';
								}
							}
						},
						{
							 name : "imageVersion",
							index : "imageVersion",
							align : 'center',
							width : '130px',
						}, 
						{
							 name : "svnVersion",
							index : "svnVersion",
							align : 'center',
							width : '120px',
						}, 
						{
							 name : "startTime",
							index : "startTime",
							align : 'center',
							width : '180px',
						},
						{
							 name : "endTime",
							index : "endTime",
							align : 'center',
							width : '180px',
						},
						{
							 name : "operator",
							index : "operator",
							align : 'center',
							width : '130px',
						},
						{    name : 'id',	
							index : 'id', 
							hidden : true 
						}
				],
				multiselect : false,
				rowNum : 10,
				loadonce : true,
				altRows : true,
				altclass : 'r1',
				forceFit : true,
				pager : '#pagerBar',
				sortname : 'startTime',
				viewrecords : true,
				sortorder : "desc",
				caption : "",
				rownumbers : true,
				
				subGrid : true,
				//点击展开时调用
				subGridRowExpanded : function(subgrid_id, row_id) {
					//alert("subgrid_id="+subgrid_id+", row_id="+row_id);
					var rowData = jQuery("#queryBuildRecordList").jqGrid('getRowData',row_id);
					var buildRecordId = rowData["id"];
					//根据状态判断是用长连接还是直接调用查询构建日志
					if(row_id == 1 && rowData["status"].indexOf("构建中") > -1){
						$("#" + subgrid_id).html('<div class="box" id="mainDiv'+row_id+'"></div>');
						buildLogWebSocket(row_id, buildId, buildRecordId);
					}else{
						$.ajax({
							type:"POST",
							dataType:"json",
							data: {
								"buildId" : buildId,
								"buildRecordId" : buildRecordId
							},	
							url : '/paas/build/queryBuildRecordLog.action',
							success:function(result){
								if(result['resultCode'] == 'success'){
									$("#" + subgrid_id).html('<div class="box" id="mainDiv'+row_id+'">'+result['resultMessage'] + '</div>');
								}else{
									parent.parent.alertError(result['resultCode'],result['resultMessage']);
								}
							}
						});
					}
				},
				//收拢时调用
				subGridRowColapsed : function(subgrid_id, row_id) {
					if(row_id == 1 && socket != undefined && socket.readyState == 1){
					      socket.close();
					}
					//$("#" + subgrid_id).remove(); //框架自己会移除
				},
				//选择某一条时触发
				onSelectRow : function(row_id){
					var v_select = "#" + row_id + " a";//每一条前面的展开收拢超链接
					var v_a = $(v_select);
					if(v_a != undefined) {
						v_a.click();
					}else{
						alert("error!");
					}
				},
				
				gridComplete : function() {
					var result = $(this).getGridParam('userData');
					if (typeof(result['errorCode'])!='undefined') {
						parent.parent.alertError(result['errorCode'],result['errorMsg']);
					}
					//有构建记录才考虑展开日志
					var totalPage = document.getElementById("sp_1_pagerBar").innerHTML;
					if(totalPage > 0){
						//默认展开状态是构建中的最后一条构建记录的日志
						var v_a = $("#1 a");
						var rowData = jQuery("#queryBuildRecordList").jqGrid('getRowData',1);
						//alert(rowData["status"]);
						if(v_a != undefined && rowData["status"] !=undefined && rowData["status"].indexOf("构建中") > -1) {
							var buildRecordId = rowData["id"];
							$("#buildBtnDiv").hide();
							v_a.click();
							buildStatusWebSocket(1, buildId, buildRecordId);
						}
					}
				},
				onPaging : function(pgButton){
					if(pgButton == 'user'){
						var totalPage = document.getElementById("sp_1_pagerBar").innerHTML;
						//通过循环，去掉字符串包含的所有空格
						while(totalPage.indexOf(" ") != -1){
							totalPage = totalPage.replace(" ","");
						}
						
						var page = $("input[class='ui-pg-input']").val();
						//字符串数字转换
						if(Number(page) > Number(totalPage)){
							reloadResults(totalPage);
						}
					}
				}
			});
};

//输入条件查询构建记录列表
function queryBuildRecordList() {
	$('#token').val($.trim($('#token').val()));
	var token = $.trim($('#token').val());
	$("#queryBuildRecordList").jqGrid().setGridParam({
		page : 1,
		rowNum : 10,
		mtype : "POST",
		datatype : "json",
		url : '/paas/build/queryBuildRecordList.action'
	}).setGridParam({
		postData : {
			"buildId" : buildId,
			"buildName" : buildName,
			"token" : token
		}
	}).trigger("reloadGrid");
}

//执行构建
function executeBuild(){
	$.ajax({
		type : "POST",
		dataType : "json",
		data: {
			"buildId" : buildId
		},
		url : '/paas/build/executeBuild.action',
		success : function(result) {
			if (result['resultCode'] == 'success') {
				//alertSuccess();
				queryBuildRecordList();
			} else {
				parent.parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	});
}

var socket;
//构建记录日志WebSocket
var buildLogWebSocket = function(row_id, buildId, buildRecordId) {
	  if (window.WebSocket) {
		  if(socket != undefined && socket.readyState == 1){
			  //正常通信时状态为1，调用close方法关闭后状态为2
		      socket.close();
		  }
	      socket = new WebSocket('ws://'+window.location.host+'/paas/websocket/buildLog');
	      socket.onmessage = function(event) {
	    	    var result = JSON.parse(event.data);
	    	    if(result['resultCode'].indexOf('PAAS-00') > -1){//中间码
		          	$("#mainDiv"+row_id).append('<div class="box-main">' + result['resultMessage'] + '</div>');
		  			//滚动条拉倒div最底端
		  			$('#mainDiv'+row_id).scrollTop( $('#mainDiv'+row_id)[0].scrollHeight );
	    	    }else if(result['resultCode'] == "0"){
	    	    	socket.close();
	    	    }else{
	    	    	socket.close();
					parent.parent.alertError(result['resultCode'], result['resultMessage']);
				}
	      };
	      socket.onopen = function(event) {
	      	//alert('长连接打开!');
	      };
	      socket.onclose = function(event) {
	      	//alert('长连接关闭!');
	      };
	      
	      socket.onerror = function(event) {
	      	alert('长连接错误!');
	      };
	      //200毫秒后发送数据
		  setTimeout(function() {
		  	socket.send(buildId + "_" + buildRecordId);
		  }, 200);
	  } else {
	  	alert("您的浏览器不支持 webSocket长连接！");
	  }
};

//构建记录状态WebSocket
var buildStatusWebSocket = function(row_id, buildId, buildRecordId) {
	if (window.WebSocket) {
		var statusSocket = new WebSocket('ws://'+window.location.host+'/paas/websocket/buildStatus');
		statusSocket.onmessage = function(event) {
			var result = JSON.parse(event.data);
			if(result['resultCode'].indexOf('PAAS-00') > -1){//中间码
				var buildRecord = JSON.parse(result['resultMessage']);
				var _status = buildRecord['status'];
				var _imageVersion = buildRecord['imageVersion'];
				var _svnVersion = buildRecord['svnVersion'];
				var _endTime = buildRecord['endTime'];
				var dataRow = {status:_status, imageVersion:_imageVersion, svnVersion:_svnVersion, endTime:_endTime};
				//更新状态是构建中记录的数据
				jQuery("#queryBuildRecordList").jqGrid('setRowData', row_id , dataRow);
				$("#buildStatusId").val(_status);
			}else if(result['resultCode'] == "0"){
				$("#buildBtnDiv").show();
				statusSocket.close();
			}else{
				statusSocket.close();
				parent.parent.alertError(result['resultCode'], result['resultMessage']);
			}
		};
		
		statusSocket.onerror = function(event) {
	    	alert('长连接错误!');
	    };
	    //200毫秒后发送数据
	    setTimeout(function() {
	    	statusSocket.send(buildId + "_" + buildRecordId);
	    }, 200);
	    
	} else {
	  	alert("您的浏览器不支持 webSocket长连接！");
	}
};

//翻页时调用，或者输入页码回车
function reloadResults(totalPage){
    var rows = $('#queryBuildRecordList').getGridParam('rows'); 
    var sidx = $('#queryBuildRecordList').getGridParam('sidx'); 
    var sord = $('#queryBuildRecordList').getGridParam('sord'); 
    jQuery("#queryBuildRecordList").jqGrid('setGridParam', {
        url : '/paas/build/queryBuildRecordList.action',
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).trigger("reloadGrid");
}

