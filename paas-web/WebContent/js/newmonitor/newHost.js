/**
 * 主机列表js
 */

// 初始化主机列表
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
				url : '/paas/newmonitor/queryHostList.action'
			}).setGridParam({
			}).trigger("reloadGrid");
			}},"10000");
	
		});

// 初始化时导入主机列表
var loadHostList = function() {
	var clusterId = $.getUrlParam('clusterId');
	$('#hostList')
			.jqGrid(
					{
						url : '/paas/newmonitor/queryHostList.action',
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
						colNames : [ 'IP', '状态', '创建时间', '' ],
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



// 输入条件查询主机列表
function queryHostList() {
	// $('#token').val($.trim($('#token').val()));
	// var token = $.trim($('#token').val());
	$("#hostList").jqGrid().setGridParam({
		page : 1,
		rowNum : 9,
		mtype : "POST",
		datatype : "json",
		url : '/paas/newmonitor/queryHostList.action'
	}).setGridParam({
	// postData : {
	// "token" : token
	// }
	}).trigger("reloadGrid");
}


// 翻页时调用，或者输入页码回车
function reloadResults(totalPage) {
	var rows = $('#hostList').getGridParam('rows');
	var sidx = $('#hostList').getGridParam('sidx');
	var sord = $('#hostList').getGridParam('sord');
	jQuery("#hostList").jqGrid('setGridParam', {
		url : '/paas/newmonitor/queryHostList.action',
		page : totalPage,
		rows : rows,
		sidx : sidx,
		sord : sord,
	}).trigger("reloadGrid");
}




