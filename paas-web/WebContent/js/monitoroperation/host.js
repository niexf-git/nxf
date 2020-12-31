/**
 * 主机列表js
 */

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

var clusterType = getQueryString("clusterType");
var total = "0";
var isQuery = true;//标记，删除全部主机过程中不出发定时函数
// 初始化构建列表
$(function() {
	$(window).resize(function() {
		$("#hostList").setGridWidth($(window).width());
	});
	loadHostList();
	setInterval(function(){
		if(isQuery){
			$("#hostList").jqGrid().setGridParam({
				page : 1,
				rowNum : 9,
				mtype : "POST",
				datatype : "json",
				url : '/paas/host/queryHostList.action'
			}).setGridParam({
			}).trigger("reloadGrid");
			}},"10000");
	
		});

// 初始化时导入构建列表
var loadHostList = function() {
	var clusterId = $.getUrlParam('clusterId');
	$('#hostList')
			.jqGrid(
					{
						url : '/paas/host/queryHostList.action',
						datatype : "json",
						width : '800px',
						height : "100%",
						autowidth : true,
						postData : {
							clusterId : clusterId.toString()
						},
						jsonReader : {
							repeatitems : false,
							root : 'result',
							id : 'id',
							page : function(obj) {
								return obj.pageNo;
							},
							total : function(obj) {
								return obj.totalPages;
							},
							records : function(obj) {
								total = obj.totalCount;
								return obj.totalCount;
							},
							userdata : "userdata"
						},
						colNames : [ 'IP', '状态', '创建时间', '操作', '' ],
						colModel : [
								{
									name : "hostIP",
									index : "hostIP",
									align : 'center',
									width : '200px',
								},
								{
									name : "status",
									index : "status",
									align : "center",
									width : '200px',
									formatter : function(val, options,
											rowObject) {
										var _status = rowObject['status'];
										if (_status == "faild") {
											return '<img src="../../imgs/stopFlag.png" width="10" height="10" border="none" title="不可用"/>'
													+ ' 不可用 ';
										} else if (_status == "on") {
											return '<img src="../../imgs/run.png" width="10" height="10" border="none" title="成功"/>'
													+ ' 成功';
										} else if (_status == "off") {
											return '<img src="../../imgs/stopFlag.png" width="10" height="10" border="none" title="初始状态"/>'
													+ ' 初始状态';
										} else {
											return '-';
										}
									}
								},
								{
									name : "createTime",
									index : "createTime",
									align : 'center',
									width : '150px',
								},
								{
									name : "operation",
									index : "operation",
									align : 'center',
									width : '218px',
									formatter : function(val, options,
											rowObject) {
										var _status = rowObject['status'];
										var _nodeId = rowObject['id'];
										var _hostIp = rowObject['hostIP'];
										var _link = '';
											_link += '<a href="javaScript:start('
													+ _nodeId
													+ ')" style="color:#666" title="启动"><img src="../../imgs/start.png"  width="22" height="22" border="none"/></a>';
											_link += '<a href="javaScript:restart('
													+ _nodeId
													+ ')" style="color:#666" title="重启"><img src="../../imgs/IconRestart.png"  width="22" height="22" border="none"/></a>';
											_link += '<a href="javaScript:stop('
													+ _nodeId
													+ ')" style="color:#666" title="停止"><img src="../../imgs/IconStop.png"  width="22" height="22" border="none"/></a>';
											
											_link += "<a href=\"JavaScript:operDetails('"
												+ _hostIp
												+ "')\" style='color:#666' title='操作详情'><img src='../../imgs/operDetails.png' width='22' height='22' border='none'/></a>";
											
											_link += '<i id="link'+_nodeId+'" onclick="changeImage('+_nodeId+')" style="color:#666;" title="更多" onmouseover="mouseOverFunc('+_nodeId+')"  onmouseout="mouseOutFunc('+_nodeId+')">'
													+'<img src="../../imgs/anticon-down.png"  width="22" height="22" border="none"/></i>'
													+ '<div class="testBOx" id="thediv'+_nodeId+'" style="display:none;" onmouseover="mouseOverFunc('+_nodeId+')"  onmouseout="mouseOutFunc('+_nodeId+')"><a href="javaScript:reinstall('
													+ _nodeId
													+ ')" style="color:#666" title="重装" id="reset'+_nodeId+'"><img src="../../imgs/reset.png"  width="22" height="22" border="none"/></a><a href="javaScript:deleteHost('
													+ _nodeId
													+ ')" style="color:#666" title="删除" id="delete'+_nodeId+'"><img src="../../imgs/delete.png"  width="22" height="22" border="none"/></a></div>';
										return _link;
									}
								}, {
									name : 'id',
									index : 'id',
									hidden : true
								}, ],
						multiselect : false,
						rowNum : 9,
						loadonce : true,
						altRows : true,
						altclass : 'r1',
						forceFit : true,
						pager : '#pagerBar',
						sortname : 'createTime',
						viewrecords : true,
						sortorder : "desc",
						caption : "",
						rownumbers : true,
						gridComplete : function() {
							var result = $(this).getGridParam('userData');
							if (typeof (result['errorCode']) != 'undefined') {
								parent.parent.parent
										.alertError(result['errorCode'],
												result['errorMsg']);
							}
						},
						onPaging : function(pgButton) {
							if (pgButton == 'user') {
								var totalPage = document
										.getElementById("sp_1_pagerBar").innerHTML;
								// 通过循环，去掉字符串包含的所有空格
								while (totalPage.indexOf(" ") != -1) {
									totalPage = totalPage.replace(" ", "");
								}

								var page = $("input[class='ui-pg-input']")
										.val();
								// 字符串数字转换
								if (Number(page) > Number(totalPage)) {
									reloadResults(totalPage);
								}
							}
						}
					});
};

function mouseOverFunc(nodeId){
//	$("#showOrHide"+nodeId).hide();
//	$("#reset"+nodeId).show();
//	$("#delete"+nodeId).show();
	var odiv = document.getElementById("thediv"+nodeId);
	show_Hidden(odiv);
}

function mouseOutFunc(nodeId){
//	$("#showOrHide"+nodeId).show();
//	$("#reset"+nodeId).hide();
//	$("#delete"+nodeId).hide();
	var odiv = document.getElementById("thediv"+nodeId);
	show_Hidden(odiv);
}

function show_Hidden(obj){
	if(obj.style.display == "block"){
		obj.style.display='none';
	} else {
		obj.style.display='block';
	}
	
}

//function changeImage(nodeId){
////	var olink = document.getElementById("link");
//	var odiv = document.getElementById("thediv"+nodeId);
//	show_Hidden(odiv);
//}

// 输入条件查询主机列表
function queryHostList() {
	// $('#token').val($.trim($('#token').val()));
	// var token = $.trim($('#token').val());
	$("#hostList").jqGrid().setGridParam({
		page : 1,
		rowNum : 9,
		mtype : "POST",
		datatype : "json",
		url : '/paas/host/queryHostList.action'
	}).setGridParam({
	// postData : {
	// "token" : token
	// }
	}).trigger("reloadGrid");
}

// 创建主机
function addHost() {
	var title = "添加主机";

	var dataCenterType = $("#dataCenterType").val();
	var clusterType = $("#clusterType").val();
	var clusterId = $("#clusterId").val();

	// 集群类型，包括1.ipaas集群，2.apaas集群，3.paas平台
	if (clusterType == 3) {
		if (total == "0") {
			parent.parent.parent.openDialog(
					"/paas/jsp/monitoroperation/createPaasHost.jsp?clusterType="
							+ clusterType + "&clusterId=" + clusterId
							+ "&dataCenterType=" + dataCenterType, title, 840,
					410);
		} else {
			parent.parent.parent.alertWarn("paas平台集群已有主机，不允许新增主机！");
		}
	} else if (clusterType == 1 || clusterType == 2) {
		parent.parent.parent.openDialog(
				"/paas/jsp/monitoroperation/createNodeHost.jsp?clusterType="
						+ clusterType + "&clusterId=" + clusterId, title, 622,
				410);
	} else {
		parent.parent.parent.alertError("集群类型错误！");
	}
}

// 删除主机
function deleteHost(id) {
	parent.parent.parent.alertConfirm("确认删除主机？", function() {
		parent.parent.parent.showLoad();
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				"hostId" : id
			},
			url : '/paas/host/deleteHost.action',
			success : function(result) {
				if (result['resultCode'] == 'success') {
					queryHostList();
					// 删除主机成功后刷新左侧树
					window.parent.frames["left"].refreshNode();
					parent.parent.parent.alertSuccess();
				} else {
					parent.parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
				}
				parent.parent.parent.closeLoad();
			}
		});
	});
}

// 删除集群下所有主机
function deleteAllHost(clusterId) {
	parent.parent.parent.alertConfirm("确认删除全部主机？", function() {
		isQuery = false;
		var websocketUrl = "ws://" + window.location.host
				+ "/paas/websocket/host";
		parent.parent.alertProgressConfirm(clusterId, websocketUrl, function() {
			queryHostList();
			isQuery = true;
			// 删除主机成功后刷新左侧树
			window.parent.frames["left"].refreshNode();
		});
	});
}
//刷新
function refresh(){
	queryHostList();
}
// 从主页展示进度条
function showLoad() {
	$('#loadImg').css({
		"width" : "100%",
		"height" : "100%"
	});
	$('#loadImg').show();
}
function closeLoad() {
	$('#loadImg').hide();
}
// 翻页时调用，或者输入页码回车
function reloadResults(totalPage) {
	var rows = $('#hostList').getGridParam('rows');
	var sidx = $('#hostList').getGridParam('sidx');
	var sord = $('#hostList').getGridParam('sord');
	jQuery("#hostList").jqGrid('setGridParam', {
		url : '/paas/host/queryHostList.action',
		page : totalPage,
		rows : rows,
		sidx : sidx,
		sord : sord,
	}).trigger("reloadGrid");
}

function operDetails(hostIp){
	$.ajax({
		type : "GET",
		data : {
			"nodeIp" : hostIp,
			"compName" : ''
		},
		dataType: "text",
		url : "/paas/host/queryHostAndCompDetails.action",
		success : function(result) {
			parent.parent.parent.alertWarn(result);
		}
	});
}

function start(nodeId) {
	parent.parent.parent.alertConfirm("确认启动主机？", function() {
		parent.parent.parent.showLoad();
		$.ajax({
			type : "POST",
			data : {
				"minionIp" : nodeId,
				"command" : "start"
			},
			url : "/paas/component/startComponent.action",
			dataType : "json",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					queryHostList();
					window.parent.parent.parent.alertSuccess();
				} else {
					parent.parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
				}
				parent.parent.parent.closeLoad();
			}
		});
	});
}

function stop(nodeId) {
	parent.parent.parent.alertConfirm("确认停止主机？", function() {
		parent.parent.parent.showLoad();
		$.ajax({
			type : "POST",
			data : {
				"minionIp" : nodeId,
				"command" : "stop"
			},
			url : "/paas/component/stopComponent.action",
			dataType : "json",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					queryHostList();
					window.parent.parent.parent.alertSuccess();
				} else {
					parent.parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
				}
				parent.parent.parent.closeLoad();
			}
		});
	});
}

function restart(nodeId) {
	parent.parent.parent.alertConfirm("确认重启主机？", function() {
		parent.parent.parent.showLoad();
		$.ajax({
			type : "POST",
			data : {
				"minionIp" : nodeId,
				"command" : "restart"
			},
			url : "/paas/component/restartComponent.action",
			dataType : "json",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					queryHostList();
					window.parent.parent.parent.alertSuccess();
				} else {
					parent.parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
				}
				parent.parent.parent.closeLoad();
			}
		});
	});
}

function reinstall(nodeId) {
	parent.parent.parent.alertConfirm("确认重装主机？", function() {
		parent.parent.parent.showLoad();
		$.ajax({
			type : "POST",
			data : {
				"minionIp" : nodeId,
				"command" : "reinstall"
			},
			url : "/paas/component/reinstallComponent.action",
			dataType : "json",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					queryHostList();
					window.parent.parent.parent.alertSuccess();
				} else {
					parent.parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
				}
				parent.parent.parent.closeLoad();
			}
		});
	});
}
