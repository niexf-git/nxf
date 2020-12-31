function reloadResult() {
	var startTime=$("#insertTimeStart").val();
	var endTime=$("#insertTimeEnd").val();
	//时间为空校验
	if (startTime == '') {
		parent.alertWarn("请选择开始时间！");
		return;
	}
	if (endTime == '') {
		parent.alertWarn("请选择结束时间！");
		return;
	}
	//时间格式校验
	var objRegExp = /^(\d{4})\-(\d{2})\-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/;
	if(!objRegExp.test(startTime)) {
		parent.alertWarn("开始时间格式错误，请输入正确的格式：yyyy-mm-dd hh:mm:ss");
		return;
	}
	if(!objRegExp.test(endTime)) {
		parent.alertWarn("结束时间格式错误，请输入正确的格式：yyyy-mm-dd hh:mm:ss");
		return;
	}
	//时间大小校验
	var start=new Date(startTime.replace("-", "/").replace("-", "/"));	
	var end=new Date(endTime.replace("-", "/").replace("-", "/"));
	if (end < start) {
		parent.alertWarn("开始时间不能大于结束时间！");
		return;
	}
	
	var insertTimeStart = $('#insertTimeStart').val();
	var insertTimeEnd = $('#insertTimeEnd').val();
	var keyWord = $('#keyWord').val();
	var operateResult = $('#operateResult').val();
	var operator = $('#operator').val();
		$("#sysLogList").jqGrid().setGridParam({
			rowNum : 10,
			page : 1,
			mtype : "POST",
			datatype : "json",
			url : '/paas/log/querySysLog.action'
		}).setGridParam({
			postData : {
				'insertTimeStart' : insertTimeStart,
				'insertTimeEnd' : insertTimeEnd,
				'keyWord' : keyWord.trim(),
				'operateResult' : operateResult,
				'operator' : operator
			}
		}).trigger("reloadGrid");
	}
$(function(){
    $(window).resize(function(){   
    	$("#sysLogList").setGridWidth($(window).width()*0.99);
});
});
function reloadResults(totalPage){
	var page = $('#sysLogList').getGridParam('page'); 
    var rows = $('#sysLogList').getGridParam('rows'); 
    var sidx = $('#sysLogList').getGridParam('sidx'); 
    var sord = $('#sysLogList').getGridParam('sord'); 
    jQuery("#sysLogList").jqGrid('setGridParam', {
        url : "/paas/log/querySysLog.action",
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).trigger("reloadGrid");
}

var loadSysLog = function() {
		var operator = $('#operator').val();
		$('#sysLogList').jqGrid(
				{
					url : '/paas/log/querySysLog.action',
					datatype : "json",
					width: "100%",
					autowidth:true,
					height : "100%",
					postData:{
						'operator' : operator
					},
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
					colNames : [ '操作用户', '用户IP', '操作行为', '操作功能',
							'操作结果', '操作时间', '详情','描述', '', ''],
					colModel : [ {
						width: "60px",
						name : "operator",
						index : "operator",
						align : 'center',
					}, {
						name : "clientIp",
						index : "clientIp",
						align : 'center',
					}, {
						width: "60px",
						name : "operateType",
						index : "operateType",
						align : 'center',
					}, {
						width: "70px",
						name : "operateFunc",
						index : "operateFunc",
						align : 'center',
					}, {
						width: "70px",
						name : "operateResult",
						index : "operateResult",
						align : 'center',
					}, {
						name : "operateTime",
						index : "operateTime",
						align : 'center',
					},{
						name : "detail",
						index : "detail",
						align : 'center',						
					},{
						name : "description",
						index : "description",
						align : 'center',					
					}, {
						name : 'id',
						index : 'id',
						hidden : true
					} , {
						name : 'tet',
						index : 'tet',
						width:"10px"
						}],
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
					rownumWidth : 30,
					caption : "",
					gridComplete : function() {
				    	var result = $(this).getGridParam('userData');
						if (typeof(result['errorCode'])!='undefined') {
							parent.alertError(result['errorCode'],result['errorMsg']);
						}
					},
					onPaging : function(pgButton){
						if(pgButton == 'user'){
							var totalPage = document.getElementById("sp_1_pagerBar").innerHTML;
							//通过循环，去掉字符串包含的所有空格
							while(totalPage.indexOf(" ") != -1){
								totalPage = totalPage.replace(" ","");
							}
							if(totalPage == 1){
								reloadResult();
							}else{
								var page = $("input[class='ui-pg-input']").val();
								//字符串数字转换
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
	};
	
	
	$(function() {
		initTime();
		$.ajax({
			type : "POST",
			contentType : "application/json;utf-8",
			dataType : "json",
			url : "/paas/log/queryUserList.action",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					var json = eval(result['resultMessage']);
					$.each(json, function() {
						$("#operator").append(
								"<option value=" + this.value + ">" + this.text
										+ "</option>\r\n");
					});
					loadSysLog();
				}
			}
		});
		$("#keyWord").keydown(function(e){
			if((e.keyCode || e.which)==13){
				reloadResult();
			}
		});
	});
	
	function initTime() {
		$.ajax({
			type : "POST",
			contentType : "application/json;utf-8",
			dataType : "json",
			url : "/paas/alarm/initQueryDateTime.action",
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
	
	function init() {
		initTime();
		$("#operator > option:first").attr("selected", true);
		$("#operateResult > option:first").attr("selected", true);
		$('#keyWord').val("");
	}