function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}
function initTime() {
	$.ajax({
		type : "POST",
		contentType : "application/json;utf-8",
		dataType : "json",
		url : "/paas/alarm/initQueryDateTime.action",
		success : function(result) {
			if (result['resultCode'] == 'success') {
				var dateTime = $.parseJSON(result['resultMessage']);
				$('#sdate').val(dateTime['minDate']);
				$('#edate').val(dateTime['maxDate']);
				$('#sdate').calendar({
					format : 'yyyy-MM-dd HH:mm:ss'
				});
				$('#edate').calendar({
					format : 'yyyy-MM-dd HH:mm:ss'
				});
			}
		}
	});
}

function queryApp() {
	var serviceId = getQueryString('id');
	var subPath = getQueryString('subPath');
	var edate = $('#edate').val();
	var sdate = $('#sdate').val();
	var name = $('#name').val();
	$('#appLogList')
			.jqGrid(
					{
						postData : {
							'serviceId' : serviceId,
							'subPath' : subPath,
							'edate' : edate,
							'sdate' : sdate,
							'name' : name
						},
						url : '/paas/log/queryIpaasLog.action',
						datatype : "json",
						width : 1000,
						height : "100%",
						jsonReader : {
							repeatitems : false,
							root : 'result',
							id : 'fileName',
							repeatitems : false,
							page : function(obj) {
								return obj.pageNo;
							},
							total : function(obj) {
								return obj.totalPages;
							},
							records : function(obj) {
								return obj.totalCount;
							},
							userdata : "userdata"
						},
						colNames : [ '', '名称', '更新时间', '大小', '操作', '', '' ],
						colModel : [
								{
									name : "fileName",
									index : "fileName",
									align : 'right',
									sortable : false,
									width : '30px',
									formatter : function(val, options,
											rowObject) {
										if ("1" == rowObject["fileType"]) {
											var _link = '<a href="ipaasServiceLog.jsp?subPath='
													+ rowObject["filePath"]
													+ "/"
													+ rowObject["fileName"]
													+ '&id='
													+ serviceId
													+ '" target="bbb"><img src="/paas/imgs/folder1.png" width="15" height="16" border="none"/></a>';
											return _link;
										}
										if ("0" == rowObject["fileType"]) {
											var _link = '<img src="/paas/imgs/file.png" width="15" height="16" border="none"/>';
											return _link;
										}
										if ("2" == rowObject["fileType"]) {
											var _link = '';
											return _link;
										}

									}
								},
								{
									name : "fileName",
									index : "fileName",
									align : 'center',
									sortable : true,
									width : '150px',
									formatter : function(val, options,
											rowObject) {
										if ("1" == rowObject["fileType"]) {
											var _link = '<a href="ipaasServiceLog.jsp?subPath='
													+ rowObject["filePath"]
													+ "/"
													+ rowObject["fileName"]
													+ '&id='
													+ serviceId
													+ '" style="color:#0088A8" target="bbb">'
													+ rowObject["fileName"]
													+ '</a>';
											return _link;
										}
										if ("0" == rowObject["fileType"]) {
											var _link = '<span>'
													+ rowObject["fileName"]
													+ '</span>';
											return _link;
										}
										if ("2" == rowObject["fileType"]) {
											var path = rowObject["filePath"]
													.substring(
															0,
															rowObject["filePath"]
																	.lastIndexOf("/"));
											var _link = '<a href="ipaasServiceLog.jsp?subPath='
													+ path
													+ '&id='
													+ serviceId
													+ '" target="bbb">'
													+ rowObject["fileName"]
													+ '</a>';
											return _link;
										}
									}
								},
								{
									name : "fileTime",
									index : "fileTime",
									sortable : true,
									width : "200px",
									align : 'center',
								},
								{
									name : "fileSize",
									index : "fileSize",
									sortable : true,
									width : "200px",
									align : 'center',
								},
								{
									name : 'downloadPath',
									loginName : 'downloadPath',
									width : "200px",
									align : 'center',
									sortable : false,
									formatter : function(val, options,
											rowObject) {
										var _link = "";
										if ("0" == rowObject["fileType"]) {
											// 应用日志查看功能没找到解决方法（暂时屏蔽）
											// _link = '<a
											// href="/paas/log/downloadFile.action?downloadPath='
											// + val + '" style="color:#666">'+
											// '<img
											// src="/paas/imgs/Download.png"
											// width="22" height="22"
											// border="none" title="下载"/>'+
											// '</a>&nbsp;&nbsp;&nbsp;'+
											// '<a
											// href="javascript:viewAppLog('+"'"+val+"'"+')"
											// id="downloadAppFile">'+
											// '<img src="/paas/imgs/look.png"
											// width="22" height="22"
											// border="none" title="查看"/>'+
											// '</a>';
											_link = '<a href="/paas/log/downloadFile.action?downloadPath='
												+ val
												+ '&ipaasServiceId='+serviceId+'" style="color:#666">'
												+ '<img src="/paas/imgs/Download.png" width="22" height="22" border="none" title="下载"/>'
												+ '</a>&nbsp;&nbsp;&nbsp;'
												+'<a href="javaScript:deleteFile('+"'"+val+"'"+','+"'"+serviceId+"'"+')" style="color:#666">'
												+ '<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/>'
												+ '</a>&nbsp;&nbsp;&nbsp;';
											return _link;
										} else {
											return _link;
										}
									}
								}, {
									name : "fileType",
									index : "fileType",
									hidden : true
								}, {
									name : "filePath",
									index : "filePath",
									hidden : true
								} ],
						multiselect : false,
						rowNum : 7,
						loadonce : true,
						altRows : true,
						altclass : 'r1',
						forceFit : true,
						pager : '#pagerBar',
						viewrecords : true,
						rownumbers : true,
						caption : "",
						gridComplete : function() {
							var result = $(this).getGridParam('userData');
							if (typeof (result['errorCode']) != 'undefined') {
								parent.parent.alertError(result['errorCode'],
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
									if (totalPage == "") {
										$("input[class='ui-pg-input']").val(0);
									}
									// 字符串数字转换
									if (Number(page) > Number(totalPage)) {
										reloadResults(totalPage);
									} else if (Number(page) < 1
											&& totalPage != "") {
										reloadResults(1);
									}
								}
							}
						}
					});
}

function reloadResults(totalPage) {
	var page = $('#appLogList').getGridParam('page');
	var rows = $('#appLogList').getGridParam('rows');
	var sidx = $('#appLogList').getGridParam('sidx');
	var sord = $('#appLogList').getGridParam('sord');
	jQuery("#appLogList").jqGrid('setGridParam', {
		url : '/paas/log/queryIpaasLog.action',
		page : totalPage,
		rows : rows,
		sidx : sidx,
		sord : sord,
	}).trigger("reloadGrid");
}

$(function() {
	init();
	getPath();
	queryApp();
	$("#name").keydown(function(e) {
		if ((e.keyCode || e.which) == 13) {
			reloadResult();
		}
	});
});

var applicationId;
function queryStd() {
	$("#logCount").val("20000");
	var appId = getQueryString('id');
	applicationId = appId;
	var a = $('#instanceId').val();
	var instanceId = a.split("_")[0];
	var hostIp = a.split("_")[1];
	if (instanceId == null) {
		return;
	}
	var a = document.getElementById("downloadFileId");
	a.href = "/paas/log/downloadStdFile.action?appId=" + applicationId
			+ "&instanceId=" + instanceId + "&hostIp=" + hostIp;
	$('#stdLog').val("正在获取实例" + instanceId + "的日志，请稍等...");
	$.ajax({
		type : "POST",
		dataType : "json",
		data : {
			"instanceId" : instanceId,
			"appId" : appId,
			"hostIp" : hostIp
		},
		url : "/paas/log/queryStdLog.action",
		success : function(result) {
			if (result['resultCode'] == 'success') {
				var json = eval(result['resultMessage']);
				$('#stdLog').val(json);
			} else {
				parent.parent.alertError(result['resultCode'],
						result['resultMessage']);
			}
		}
	});
}

/*
 * 获取当前路径
 */
function getPath() {
	var serviceId = getQueryString('id');
	var subPath = getQueryString('subPath');
	$.ajax({
		type : "POST",
		dataType : "json",
		data : {
			"serviceId" : serviceId,
			"subPath" : subPath
		},
		url : "/paas/log/queryIpaasLogPath.action",
		success : function(result) {
			if (result['resultCode'] == 'success') {
				var json = eval(result['resultMessage']);
				$('#path').text(json);
			} else {
				parent
						.alertError(result['resultCode'],
								result['resultMessage']);
			}
		}
	});
}
function reloadResult() {
	var name = $('#name').val();
	var filePath = $('#path').text();

	// var sdate = $('#sdate').val();
	// var edate = $('#edate').val();
	// if (sdate == '' && edate != '') {
	// parent.parent.alertWarn("时间区间不能有空，请选择起始时间!");
	// return false;
	// }
	// if (sdate != '' && edate == '') {
	// parent.parent.alertWarn("时间区间不能有空，请选择结束时间!");
	// return false;
	// }
	// if(sdate != '' && edate != ''){
	// var start = new Date(sdate.replace("-", "/").replace("-", "/"));
	// var end = new Date(edate.replace("-", "/").replace("-", "/"));
	// if (end < start) {
	// parent.parent.alertWarn("开始时间不能大于结束时间!");
	// return false;
	// }
	// }
	var sdate = $('#sdate').val();
	var edate = $('#edate').val();
	/*
	 * //时间为空校验 if (sdate == '') { parent.parent.alertWarn("请选择开始时间！"); return; }
	 * if (edate == '') { parent.parent.alertWarn("请选择结束时间！"); return; }
	 */
	// 时间格式校验
	if (sdate != '') {
		var objRegExp = /^(\d{4})\-(\d{2})\-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/;
		if (!objRegExp.test(sdate)) {
			parent.parent.alertWarn("开始时间格式错误，请输入正确的格式：yyyy-mm-dd hh:mm:ss");
			return;
		}
		var objRegExp = /^(\d{4})\-(\d{2})\-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/;
		if (!objRegExp.test(edate)) {
			parent.parent.alertWarn("结束时间格式错误，请输入正确的格式：yyyy-mm-dd hh:mm:ss");
			return;
		}

	}
	if (edate != '') {
		var objRegExp = /^(\d{4})\-(\d{2})\-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/;
		if (!objRegExp.test(edate)) {
			parent.parent.alertWarn("结束时间格式错误，请输入正确的格式：yyyy-mm-dd hh:mm:ss");
			return;
		}
		var objRegExp = /^(\d{4})\-(\d{2})\-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/;
		if (!objRegExp.test(sdate)) {
			parent.parent.alertWarn("开始时间格式错误，请输入正确的格式：yyyy-mm-dd hh:mm:ss");
			return;
		}
	}

	// 时间大小校验
	var start = new Date(sdate.replace("-", "/").replace("-", "/"));
	var end = new Date(edate.replace("-", "/").replace("-", "/"));
	if (end < start) {
		parent.parent.alertWarn("开始时间不能大于结束时间！");
		return;
	}

	$("#appLogList").jqGrid().setGridParam({
		page : 1,
		rowNum : 7,
		mtype : "POST",
		datatype : "json",
		url : '/paas/log/queryIpaasLog.action'
	}).setGridParam({
		postData : {
			'name' : name,
			'sdate' : sdate,
			'edate' : edate,
			'filePath' : filePath
		}
	}).trigger("reloadGrid");
}

// 日志条数增加
function addOne() {
	var logCount = $("#logCount").val();
	logCount = parseInt(logCount);
	if (logCount == 20000) {
		alert("请输入1到20000之间的数字");
	} else {
		if (logCount > 19500) {
			$("#logCount").val(20000);
		} else {
			$("#logCount").val(logCount + 500);
		}
	}
}

// 减少日志查询条数
function removeOne() {
	var logCount = $("#logCount").val();
	logCount = parseInt(logCount);
	if (logCount == 0) {
		alert("请输入1到20000之间的数字");
	} else {
		if (logCount < 500) {
			$("#logCount").val(0);
		} else {
			$("#logCount").val(logCount - 500);
		}
	}
}

// 按照日志条数查询日志
function queryLog() {
	var logCount = $("#logCount").val();
	var test = /^[0-9]\d*$/;
	var result = logCount.substr(0, 1);
	if (logCount.match(test) == null || parseInt(logCount) <= 0
			|| parseInt(logCount) > 20000 || parseInt(result) == 0) {
		alert("请输入1到20000之间的数字");
		return;
	} else {
		var appId = getQueryString('id');
		var a = $('#instanceId').val();
		var instanceId = a.split("_")[0];
		var hostIp = a.split("_")[1];
		if (instanceId == null) {
			return;
		}
		$('#stdLog').val("正在获取实例" + instanceId + "的日志，请稍等...");
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				"instanceId" : instanceId,
				"appId" : appId,
				"hostIp" : hostIp,
				"logCount" : logCount
			},
			url : "/paas/log/queryStdLog.action",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					var json = eval(result['resultMessage']);
					$('#stdLog').val(json);
				} else {
					parent.alertError(result['resultCode'],
							result['resultMessage']);
				}
			}
		});
	}
}

function deleteFile(filePath,serviceId){
	parent.parent.alertConfirm("确认删除该日志?", function() {
	$.ajax({
		type : "POST",
		dataType : "json",
		data : {
			"deletePath" : filePath,
			"ipaasServiceId" : serviceId
		},
		url : "/paas/log/deleteFile.action",
		success : function(result) {
			if (result['resultCode'] == 'success') {
				parent.parent.alertSuccess();
				reloadResult();
			} else {
				parent.parent.alertError(result['resultCode'],
						result['resultMessage']);
			}
		}
	});
	});
}

// 查看应用日志
// function viewAppLog(downloadPath){
// openWindow("/paas/jsp/logmanager/viewAppLog.jsp?downloadPath="+downloadPath,
// "查看日志");
// }
//
// function openWindow(url, titleStr){
// var scrWidth = screen.availWidth;
// var scrHeight = screen.availHeight;
// var opt = 'top=0, left=0, toolbar=no, menubar=no, scrollbars=no,
// resizable=no, location=no, status=no, fullscreen=1';
// var self = window.open(url, titleStr, opt);
// self.moveTo(0,0);
// self.resizeTo(scrWidth,scrHeight);
// }
