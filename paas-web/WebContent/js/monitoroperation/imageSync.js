$(function() {
	//jqGrid随窗口大小变化自适应宽度
	$(window).resize(function(){
		$("#imageSyncList").setGridWidth($(window).width());
	});
	
	initTime();
	initApps();
	initDataCenter();
	var startTime = $("#insertTimeStart").val();
	var endTime = $("#insertTimeEnd").val();
	var appId = $('#apps').val(); //应用
	var dataCenterId = $('#dataCenter').val(); //数据中心
	var status = $('#status').val(); //状态
	$('#imageSyncList').jqGrid(
			{
				url : '/paas/imageSync/queryImageSyncList.action',
				datatype : "json",
				width : "680px",
				height : "100%",
				//如果为true时，则当表格在首次被创建时会根据父元素比例重新调整表格宽度。
				//如果父元素宽度改变，为了使表格宽度能够自动调整则需要实现函数：setGridWidth
				autowidth : true,
				postData : {
					'appId' : appId,
					'dataCenterId' : dataCenterId,
					'status' : status,
					'startTime' : startTime,
					'endTime' : endTime
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
				colNames : ['镜像名称', '镜像版本', '状态', '目标数据中心', '操作', '创建时间', '更新时间'],
				colModel : [ {
					name : "repository",
					index : "repository",
					align : 'center',
					width : '140px',
				}, {
					name : "imageVersion",
					index : "imageVersion",
					align : 'center',
					width : '80px',
				}, {
					name : "status",
					index : "status",
					align : 'center',
					width : '80px',
					formatter : function(val, options, rowObject) { //加上小图标，到harbor上去找
						var _s = rowObject['status'];
						if (_s == "pending") {
							return '等待中';
						} else if (_s == "running") {
							return '进行中';
						} else if (_s == "error") {
							return '错误';
						} else if (_s == "retrying") {
							return '重试中';
						} else if (_s == "stopped") {
							return '已终止';
						} else if (_s == "finished") {
							return '已完成';
						} else if (_s == "canceled") {
							return '已取消';
						}
					}
				}, {
					name : "dataCenter",
					index : "dataCenter",
					align : 'center',
					width : '100px',
				}, {
					name : "operation",
					index : "operation",
					align : 'center',
					width : '80px',
					formatter : function(val, options, rowObject) {
						var _s = rowObject['operation'];
						if (_s == "delete") {
							return '删除';
						}
						else if (_s == "transfer") {
							return '复制';
						}
					}
				}, 
				{
					name : "creationTime",
					index : "creationTime",
					align : 'center',
					width : '80px',
				},
				{
					name : "updateTime",
					index : "updateTime",
					align : 'center',
					width : '80px',
				}],
				multiselect : false,
				rowNum : 10,
				loadonce : true,
				altRows : true,
				altclass : 'r1',
				forceFit : true,
				pager : '#pagerBar',
				sortname : 'id',
				viewrecords : true,
				sortorder : "desc",
				rownumbers : true,
				caption : "",
				gridComplete : function() {
					var result = $(this).getGridParam('userData');
					if (typeof (result['errorCode']) != 'undefined') {
						parent.parent.parent.alertError(result['errorCode'], result['errorMsg']);
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
							var page = $("input[class='ui-pg-input']").val();
							// 字符串数字转换
							if (totalPage == "") {
								$("input[class='ui-pg-input']").val(0);
							}
							// 字符串数字转换
							if (Number(page) > Number(totalPage)) {
								reloadResults(totalPage);
							} else if (Number(page) < 1 && totalPage != "") {
								reloadResult();
							}
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
	var page = $('#imageSyncList').getGridParam('page');
	var rows = $('#imageSyncList').getGridParam('rows');
	var sidx = $('#imageSyncList').getGridParam('sidx');
	var sord = $('#imageSyncList').getGridParam('sord');
	jQuery("#imageSyncList").jqGrid('setGridParam', {
		url : "/paas/imageSync/queryImageSyncList.action",
		page : totalPage,
		rows : rows,
		sidx : sidx,
		sord : sord,
	}).trigger("reloadGrid");
}

/**
 * 查询
 */
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
	var appId = $('#apps').val();
	if(appId == ""){
		parent.parent.parent.alertWarn("请选择应用！");
		return;
	}
	var dataCenterId = $('#dataCenter').val();
	if(dataCenterId == ""){
		parent.parent.parent.alertWarn("请选择目标数据中心！");
		return;
	}
	var status = $('#status').val();
	
	$("#imageSyncList").jqGrid().setGridParam({
		page : 1,
		rowNum : 10,
		width : '100px',
		mtype : "POST",
		datatype : "json",
		url : '/paas/imageSync/queryImageSyncList.action'
	}).setGridParam({
		postData : {
			'appId' : appId,
			'dataCenterId' : dataCenterId,
			'status' : status,
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
		url : "/paas/imageSync/initQueryDateTime.action",
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

// 初始化数据中心
function initDataCenter() {
	$.ajax({
		type : "POST",
		contentType : "application/json;utf-8",
		dataType : "json",
		url : "/paas/imageSync/queryDataCenterList.action",
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
 * 初始化应用
 */
function initApps() {
	$.ajax({
		type : "POST",
		contentType : "application/json;utf-8",
		dataType : "json",
		url : "/paas/imageSync/queryAppsList.action",
		success : function(result) {
			if (result['resultCode'] == 'success') {
				var json = eval(result['resultMessage']);
				$.each(json, function() {
					$("#apps").append(
							"<option value=" + this.value + ">" + this.text
									+ "</option>\r\n");
				});
			}
		}
	});
}

/**
 * 加载公共镜像（基础镜像）
 */
function loadPublicImages() {
	parent.parent.parent.alertConfirm("确认加载公共镜像?",function(){
		parent.parent.parent.showLoad(); //显示转圈等待
		$.ajax({
			type:"POST",
			dataType:"json",
			url:"/paas/imageSync/loadPublicImages.action",
			success:function(result){
				parent.parent.parent.closeLoad(); //关闭转圈
				if(result['resultCode'] == 'success'){
					parent.parent.parent.alertSuccess();
				}
				else{
					parent.parent.parent.alertError(result['resultCode'], result['resultMessage']);
				}
			}
		});
	});
}
