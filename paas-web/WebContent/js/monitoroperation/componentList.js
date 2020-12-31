function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

var minionIp = getQueryString("minionIp");
var minionName =  $.getUrlParam('minionName');
$(function() {
	$(window).resize(function() {
		$("#componentList").setGridWidth($(window).width());
	});
	$('#componentList')
			.jqGrid(
					{
						url : '/paas/component/queryComponentInfoList.action',
						datatype : "json",
						width : "812px",
						height : "100%",
						autowidth : true,
						jsonReader : {
							repeatitems : false,
							root : 'result',
							id : 'id',
							repeatitems : false,
							page : function(obj) {
								return obj.page;
							},
							total : function(obj) {
								return obj.total;
							},
							records : function(obj) {
								return obj.records;
							},
							userdata : "userdata"
						},
						colNames : [ '组件名称', '状态', '操作', '' ],
						colModel : [
								{
									name : "name",
									index : "name",
									align : 'center',
									width : '250px'
								},
								{
									name : "status",
									index : "status",
									align : 'center',
									width : '250px',
									formatter : function(val, options,
											rowObject) {
										var _status = rowObject['status'];
										if (_status == "active") {
											return '<img src="../../imgs/run.png" width="10" height="10" border="none" title="正常"/>'
													+ ' 正常';
										} else if (_status == "inactive") {
											return '<img src="../../imgs/stopFlag.png" width="10" height="10" border="none" title="异常"/>'
													+ ' 异常 ';
										} else if (_status == "on") {
											return '<img src="../../imgs/stopFlag.png" width="10" height="10" border="none" title="部署成功"/>'
													+ ' 部署成功 ';
										} else if (_status == "off") {
											return '<img src="../../imgs/stopFlag.png" width="10" height="10" border="none" title="部署失败"/>'
													+ ' 部署失败 ';
										} else if (_status == "deploying") {
											return '<img src="../../imgs/building.gif" width="10" height="10" border="none" title="部署中"/>'
													+ ' 部署中 ';
										} else if (_status == "stop") {
											return '<img src="../../imgs/stopFlag.png" width="10" height="10" border="none" title="停止"/>'
													+ ' 停止 ';
										}
										;
									}
								},
								{
									name : "",
									index : "",
									align : 'center',
									width : '250px',
									formatter : function(val, options,
											rowObject) {
										var _name = rowObject['name'];
										var _status = rowObject['status'];
										var _nodeId = rowObject['nodeId'];
										var _link = '';
											_link += "<a href=\"JavaScript:start('"
													+ _name
													+ "','"
													+ _nodeId
													+ "')\" style='color:#666' title='启动'><img src='../../imgs/start.png' width='22' height='22' border='none'/></a>";
										
											_link += "<a href=\"JavaScript:restart('"
													+ _name
													+ "','"
													+ _nodeId
													+ "')\" style='color:#666' title='重启'><img src='../../imgs/IconRestart.png' width='22' height='22' border='none'/></a>";
											_link += "<a href=\"JavaScript:stop('"
													+ _name
													+ "','"
													+ _nodeId
													+ "')\" style='color:#666' title='停止'><img src='../../imgs/IconStop.png' width='22' height='22' border='none'/></a>";
											_link += "<a href=\"JavaScript:operDetails('"
												+ _name
												+ "')\" style='color:#666' title='操作详情'><img src='../../imgs/operDetails.png' width='22' height='22' border='none'/></a>";
											
											_link += "<i style='color:#666;' title='更多' onclick=\"changeImg('"+_name+"')\" onmouseover=\"mouseOverFunc('"+_name+"')\"  onmouseout=\"mouseOutFunc('"+_name+"')\" id='showOrHide"+_name+"'>"
													+"<img src='../../imgs/anticon-down.png' width='22' height='22' border='none'/></i>"
													+"<div class=\"testBOx\" id=\"thediv"+_name+"\" style=\"display:none\" onmouseover=\"mouseOverFunc('"+_name+"')\"  onmouseout=\"mouseOutFunc('"+_name+"')\">"
													+"<a href=\"JavaScript:reinstall('"
												+ _name
												+ "','"
												+ _nodeId
												+ "')\" style='color:#666' title='重装' id='reset"+_name+"'><img src='../../imgs/reset.png' width='22' height='22' border='none'/></a></div>";
										return _link;
									}
								}, {
									name : "id",
									index : "id",
									hidden : true
								} ],
						postData : {
							"minionIp" : minionIp
						},
						multiselect : false,
						rowNum : 10,
						loadonce : false,
						altRows : true,
						altclass : 'r1',
						forceFit : true,
						pager : '#pagerBar',
						sortname : 'id',
						viewrecords : true,
						sortorder : "desc",
						rownumbers : true,
						rownumWidth : 50,
						caption : "",
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
								if (totalPage == 1) {
									reloadResult();
								} else {
									var page = $("input[class='ui-pg-input']")
											.val();
									// 字符串数字转换
									if (totalPage == "") {
										$("input[class='ui-pg-input']").val(0);
									}
									// 字符串数字转换
									if (Number(page) > Number(totalPage)) {
										reloadResults(totalPage);
									} else if (Number(page) < 1
											&& totalPage != "") {
										reloadResult();
									}
								}
							}
						}
					});
});

//function changeImg(name){
//	var odiv = document.getElementById("thediv"+name);
//	show_Hidden(odiv);
//}

function mouseOverFunc(name){
	var odiv = document.getElementById("thediv"+name);
	show_Hidden(odiv);
}

function mouseOutFunc(name){
	var odiv = document.getElementById("thediv"+name);
	show_Hidden(odiv);
}

function show_Hidden(obj){
	if(obj.style.display == "block"){
		obj.style.display='none';
	} else {
		obj.style.display='block';
	}
}

function reloadResults(totalPage) {
	var page = $('#componentList').getGridParam('page');
	var rows = $('#componentList').getGridParam('rows');
	var sidx = $('#componentList').getGridParam('sidx');
	var sord = $('#componentList').getGridParam('sord');
	jQuery("#componentList").jqGrid('setGridParam', {
		url : "/paas/component/queryComponentInfoList.action",
		page : totalPage,
		rows : rows,
		sidx : sidx,
		sord : sord,
	}).trigger("reloadGrid");
}
function reloadResult() {
	$("#componentList").jqGrid().setGridParam({
		page : 1,
		rowNum : 10,
		width : '100px',
		mtype : "POST",
		datatype : "json",
		url : '/paas/component/queryComponentInfoList.action'
	}).setGridParam({
		postData : {
			"minionIp" : minionIp
		}
	}).trigger("reloadGrid");
}
function start(name, nodeId) {
	parent.parent.parent.alertConfirm("确认启动组件？", function() {
		parent.parent.parent.showLoad();
		$.ajax({
			type : "POST",
			data : {
				"name" : name,
				"minionIp" : nodeId,
				"command" : "start"
			},
			url : "/paas/component/startComponent.action",
			dataType : "json",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					window.parent.parent.parent.alertSuccess();
					reloadResult();
				} else {
					parent.parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
				}
				parent.parent.parent.closeLoad();
			}
		});
	});
}

function operDetails(name){
	
	$.ajax({
		type : "GET",
		data : {
			"nodeIp" : minionName,
			"compName" : name
		},
		dataType: "text",
		url : "/paas/host/queryHostAndCompDetails.action",
		success : function(result) {
			parent.parent.parent.alertWarn(result);
		}
	});
	
	
}
function stop(name, nodeId) {
	parent.parent.parent.alertConfirm("确认停止组件？", function() {
		parent.parent.parent.showLoad();
		$.ajax({
			type : "POST",
			data : {
				"name" : name,
				"minionIp" : nodeId,
				"command" : "stop"
			},
			url : "/paas/component/stopComponent.action",
			dataType : "json",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					window.parent.parent.parent.alertSuccess();
					reloadResult();
				} else {
					parent.parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
				}
				parent.parent.parent.closeLoad();
			}
		});
	});
}
function restart(name, nodeId) {
	parent.parent.parent.alertConfirm("确认重启组件？", function() {
		parent.parent.parent.showLoad();
		$.ajax({
			type : "POST",
			data : {
				"name" : name,
				"minionIp" : nodeId,
				"command" : "restart"
			},
			url : "/paas/component/restartComponent.action",
			dataType : "json",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					window.parent.parent.parent.alertSuccess();
					reloadResult();
				} else {
					parent.parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
				}
				parent.parent.parent.closeLoad();
			}
		});
	});
}
function reinstall(name, nodeId) {
	parent.parent.parent.alertConfirm("确认重装组件？", function() {
		parent.parent.parent.showLoad();
		$.ajax({
			type : "POST",
			data : {
				"name" : name,
				"minionIp" : nodeId,
				"command" : "reinstall"
			},
			url : "/paas/component/reinstallComponent.action",
			dataType : "json",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					window.parent.parent.parent.alertSuccess();
					reloadResult();
				} else {
					parent.parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
				}
				parent.parent.parent.closeLoad();
			}
		});
	});
}