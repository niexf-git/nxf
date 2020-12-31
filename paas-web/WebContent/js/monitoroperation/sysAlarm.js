$(function() {
	
	$(window).resize(function(){
		$("#alarmList").setGridWidth($(window).width());
	});	
	initTime();
	initDataCenter();
	$('#alarmList')
	.jqGrid(
			{
				url : '/paas/sysAlarm/queryAlarmList.action',
				datatype : "json",
				width : '800px',
				height : "100%",
				autowidth : true,
				postData : {
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
						total=obj.totalCount;
						return obj.totalCount;
					},
					userdata : "userdata"
				},
				colNames : [ '数据中心', '集群名称', 'IP', '类型', '名称', '告警时间','描述' ],
				colModel : [ {
					name : "dataCenterName",
					index : "dataCenterName",
					align : 'center',
					width : '80px',
				}, {
					name : "clusterName",
					index : "clusterName",
					align : 'center',
					width : '80px',
				}, {
					name : "nodeIp",
					index : "nodeIp",
					align : 'center',
					width : '110px',
				}, {
					name : "type",
					index : "type",
					align : 'center',
					width : '80px',
					formatter : function(val, options,
							rowObject) {
						var _status = rowObject['type'];
						if (_status == "1") {
							return '组件';
						} else if (_status == "2") {
							return '主机';
						} else {
							return '未知类型';
						}
					}
				}, {
					name : "name",
					index : "name",
					align : 'center',
					width : '80px',
				}, {
					name : "time",
					index : "time",
					align : 'center',
					width : '120px',
				}, {
					name : "description",
					index : "description",
					align : 'center',
					width : '120px',
				}],
				multiselect : false,
				rowNum : 7,
				loadonce : false,
				altRows : true,
				rownumWidth : 50,
				altclass : 'r1',
				forceFit : true,
				pager : '#pagerBar',
				sortname : 'time',
				viewrecords : true,
				sortorder : "desc",
				caption : "",
				rownumbers : true,
				gridComplete : function() {
					var result = $(this).getGridParam('userData');
					if (typeof (result['errorCode']) != 'undefined') {
						parent.parent.parent.alertError(result['errorCode'],
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
						} else if (Number(page) < 1 && totalPage != "") {
							reloadResult();
						}
					}
				}
			});
	$("#keyWord").keydown(function(e) {
		if ((e.keyCode || e.which) == 13) {
			reloadResult();
		}
	});
});

function reloadResults(totalPage) {
	var page = $('#alarmList').getGridParam('page');
	var rows = $('#alarmList').getGridParam('rows');
	var sidx = $('#alarmList').getGridParam('sidx');
	var sord = $('#alarmList').getGridParam('sord');
	jQuery("#alarmList").jqGrid('setGridParam', {
		url : "/paas/sysAlarm/queryAlarmList.action",
		page : totalPage,
		rows : rows,
		sidx : sidx,
		sord : sord,
	}).trigger("reloadGrid");
}
function reloadResult() {
	var startTime = $("#insertTimeStart").val();
	var endTime = $("#insertTimeEnd").val();
	// 时间为空校验
	if (startTime == '') {
		parent.parent.parent.alertWarn("请选择开始时间！");
		return;
	}
	if (endTime == '') {
		parent.parent.parent.alertWarn("请选择结束时间！");
		return;
	}
	// 时间格式校验
	var objRegExp = /^(\d{4})\-(\d{2})\-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/;
	if (!objRegExp.test(startTime)) {
		parent.parent.parent.alertWarn("开始时间格式错误，请输入正确的格式：yyyy-mm-dd hh:mm:ss");
		return;
	}
	if (!objRegExp.test(endTime)) {
		parent.parent.parent.alertWarn("结束时间格式错误，请输入正确的格式：yyyy-mm-dd hh:mm:ss");
		return;
	}
	// 时间大小校验
	var start = new Date(startTime.replace("-", "/").replace("-", "/"));
	var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	if (end < start) {
		parent.parent.parent.alertWarn("开始时间不能大于结束时间！");
		return;
	}
	var clusterId = $('#cluster').val();
	var dataCenterId = $('#dataCenter').val();
	var nodeId = $('#node').val();
	$("#alarmList").jqGrid().setGridParam({
		page : 1,
		rowNum : 7,
		width : '100px',
		mtype : "POST",
		datatype : "json",
		url : '/paas/sysAlarm/queryAlarmList.action'
	}).setGridParam({
		postData : {
			'clusterId' : clusterId,
			'dataCenterId' : dataCenterId,
			'nodeId' : nodeId,
			'startTime' : startTime,
			'endTime' : endTime
		}
	}).trigger("reloadGrid");
}
// 初始化时间
function initTime() {
	$.ajax({
		type : "POST",
		contentType : "application/json;utf-8",
		dataType : "json",
		url : "/paas/sysAlarm/initQueryDateTime.action",
		success : function(result) {
			if (result['resultCode'] == 'success') {
				var dateTime = $.parseJSON(result['resultMessage']);
				$('#insertTimeStart').val(dateTime['minDate']);
				$('#insertTimeEnd').val(dateTime['maxDate']);
				$('#insertTimeStart').calendar({
					format : 'yyyy-MM-dd HH:mm:ss'
				});
				$('#insertTimeEnd').calendar({
					format : 'yyyy-MM-dd HH:mm:ss'
				});
			}
		}
	});
}
// 修改告警条件
function edit() {
	if (!validateParameter()) {
		return;
	}
	parent.parent.parent.alertConfirm("确认修改告警条件？", function() {
		var memory = $('#memory').val();
		var cpu = $('#cpu').val();
		var filesystem = $('#filesystem').val();
		$.ajax({
			type : "POST",
			data : {
				"memory" : memory,
				"cpu" : cpu,
				"filesystem" : filesystem
			},
			url : "/paas/sysAlarm/modifyAlarmCondition.action",
			dataType : "json",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					window.parent.parent.parent.alertSuccess();
				} else {
					parent.parent.parent.alertError(result['resultCode'],
							result['resultMessage']);
				}
			}
		});
	});

}
// 初始化数据中心
function initDataCenter() {
	$.ajax({
		type : "POST",
		contentType : "application/json;utf-8",
		dataType : "json",
		url : "/paas/sysAlarm/queryDataCenterList.action",
		success : function(result) {
			if (result['resultCode'] == 'success') {
				var json = eval(result['resultMessage']);
				$.each(json, function() {
					$("#dataCenter").append(
							"<option value=" + this.value + ">" + this.text
									+ "</option>\r\n");
				});
			}
		}
	});

}
/**
 * 数据中心选择后，联动加载相应的集群
 */
function dataCenterChange() {
	var obj = document.getElementById("dataCenter");
	var index = obj.selectedIndex;
	if (index != -1) {
		var dataCenterId = $("#dataCenter").val();
		$.ajax({
			type : "POST",
			data : {
				"dataCenterId" : dataCenterId,
			},
			dataType : "json",
			url : "/paas/sysAlarm/queryClusterList.action",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					document.getElementById("cluster").innerHTML = "";
					$("#cluster").append("<option value=''>请选择</option>\r\n");
					document.getElementById("node").innerHTML = "";
					$("#node").append("<option value=''>请选择</option>\r\n");
					var json = eval(result['resultMessage']);
					$.each(json, function() {
						$("#cluster").append(
								"<option value=" + this.value + ">" + this.text
										+ "</option>\r\n");
					});
				}
			}
		});
	}
}
/**
 * 集群选择后，联动加载相应的节点
 */
function clusterChange() {
	var obj = document.getElementById("cluster");
	var index = obj.selectedIndex;
	if (index != -1) {
		var clusterId = $("#cluster").val();
		$.ajax({
			type : "POST",
			data : {
				"clusterId" : clusterId,
			},
			dataType : "json",
			url : "/paas/sysAlarm/queryNodeList.action",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					document.getElementById("node").innerHTML = "";
					$("#node").append("<option value=''>请选择</option>\r\n");
					var json = eval(result['resultMessage']);
					$.each(json, function() {
						$("#node").append(
								"<option value=" + this.value + ">" + this.text
										+ "</option>\r\n");
					});
				}
			}
		});
	}
}
// 参数校验
function validateParameter() {
	var r = /^([1]?\d{1,2})$/;
	var cpu = $.trim($("#cpu").val());
	if (cpu == "") {
		parent.parent.parent.alertWarn('CPU百分比不允许为空');
		return false;
	} else if (!r.test(cpu)) {
		parent.parent.parent.alertWarn('CPU百分比必须为0-100之间的正整数');
		return false;
	}
	var memory = $.trim($("#memory").val());
	if (memory == "") {
		parent.parent.parent.alertWarn('内存百分比不允许为空');
		return false;
	} else if (!r.test(memory)) {
		parent.parent.parent.alertWarn('内存百分比必须为0-100之间的正整数');
		return false;
	}
	var filesystem = $.trim($("#filesystem").val());
	if (filesystem == "") {
		parent.parent.parent.alertWarn('磁盘百分比不允许为空');
		return false;
	} else if (!r.test(filesystem)) {
		parent.parent.parent.alertWarn('磁盘百分比必须为0-100之间的正整数');
		return false;
	}

	return true;
}