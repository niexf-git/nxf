function reloadResult() {
//	if ($('#insertTimeStart').val() == null
//			&& $('#insertTimeEnd').val() != null) {
//		parent.alertWarn("请选择起始时间");
//		return;
//	}
//	if ($('#insertTimeStart').val() != null
//			&& $('#insertTimeEnd').val() == null) {
//		parent.alertWarn("请选择结束时间");
//		return;
//	}
//	if ($('#insertTimeStart').val() > $('#insertTimeEnd').val()) {
//		parent.alertWarn("起始时间不能比结束时间晚");
//		return;
//	}
	
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
	var operType="";
	var appName="";
	var appNames=$("#appText",window.parent.document).html();
	var operTypes=$("#operTypeText",window.parent.document).html();
	var type="";
	if (operTypes=="开发") {
		type="1";
	}else if (operTypes=="测试") {
		type="2";
	}else if (operTypes=="运维") {
		type="3";
	}
	if (appNames=="全部") {
		appName = $('#appName').val();
	}else{
		appName=appNames;
	}
	if (operTypes=="全部") {
		operType = $('#operType').val();
	}else{
		operType=type;
	}
	$("#alarmList").jqGrid().setGridParam({
		page : 1,
		rowNum : 10,
		width : '100px',
		mtype : "POST",
		datatype : "json",
		url : '/paas/alarm/queryAlarm.action'
	}).setGridParam({
		postData : {
			'insertTimeStart' : insertTimeStart,
			'insertTimeEnd' : insertTimeEnd,
			'appName': appName,
			'keyWord' : keyWord.trim(),
			'operType':operType
		}
	}).trigger("reloadGrid");
}
$(function() {
	var insertTimeStart = $('#insertTimeStart').val();
	var insertTimeEnd = $('#insertTimeEnd').val();
	var operType="";
	var appName="";
	var appNames=$("#appText",window.parent.document).html();
	var operTypes=$("#operTypeText",window.parent.document).html();
	var type="";
	if (operTypes=="开发") {
		type="1";
	}else if (operTypes=="测试") {
		type="2";
	}else if (operTypes=="运维") {
		type="3";
	}
	if (appNames=="全部") {
		appName = $('#appName').val();
	}else{
		appName=appNames;
	}
	if (operTypes=="全部") {
		operType = $('#operType').val();
	}else{
		operType=type;
	}
	$('#alarmList').jqGrid(
			{
				url : '/paas/alarm/queryAlarm.action',
				datatype : "json",			
				width: "100%",
				autowidth:true,
				height : "100%",
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
				colNames : [ '应用名称', '操作类型', '服务名称', '实例名称', '级别', 'IP信息',
						'告警时间', '描述', '', ''],
				colModel : [ {
					name : "appName",
					index : "appName",
					align : 'center',
				},{
					width: "60px",
					name : "operType",
					index : "operType",
					align : 'center',
				}, {
					name : "serviceName",
					index : "serviceName",
					align : 'center',
				}, {
					name : "podName",
					index : "podName",
					align : 'center',
				}, {
					width: "30px",
					name : "alarmLevel",
					index : "alarmLevel",
					align : 'center',
				},{
					name : "hostip",
					index : "hostip",
					align : 'center',
				}, {
					name : "insertTime",
					index : "insertTime",
					align : 'center',
				
				}, {
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
				postData : {
					'insertTimeStart' : insertTimeStart,
					'insertTimeEnd' : insertTimeEnd,
					'appName': appName,
					'operType':operType
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
				rownumbers:true,
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
	$("#keyWord").keydown(function(e){
		if((e.keyCode || e.which)==13){
			reloadResult();
		}
	});
});
$(function(){
    $(window).resize(function(){   
    	$("#alarmList").setGridWidth($(window).width()*0.99);
});
});
function reloadResults(totalPage){
	var page = $('#alarmList').getGridParam('page'); 
    var rows = $('#alarmList').getGridParam('rows'); 
    var sidx = $('#alarmList').getGridParam('sidx'); 
    var sord = $('#alarmList').getGridParam('sord'); 
    jQuery("#alarmList").jqGrid('setGridParam', {
        url : "/paas/alarm/queryAlarm.action",
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).trigger("reloadGrid");
}
