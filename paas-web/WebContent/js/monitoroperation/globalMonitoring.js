$(function(){
	initDataCenter();
	loadGlobalMonitorList();
	
	$(window).resize(function(){
		$("#globalMonitorTable").setGridWidth($(window).width());
	});
});

//初始化数据中心
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
};
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
					$("#cluster").append(
							"<option value=''>请选择</option>\r\n");
					document.getElementById("node").innerHTML = "";
					$("#node").append("<option value=''>请选择</option>\r\n");
					var json = eval(result['resultMessage']);
					$.each(json, function() {
						$("#cluster").append(
								"<option value=" + this.value + ">"
										+ this.text + "</option>\r\n");
					});
				}
			}
		});
	}
};
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
								"<option value=" + this.value + ">"
										+ this.text + "</option>\r\n");
					});
				}
			}
		});
	}
};
//serch
function search(){
	reloadResult();
}
function loadGlobalMonitorList(){
	$('#globalMonitorTable').jqGrid({
		url:'/paas/globalMonitor/queryGlobalMonitorList.action',
		datatype: "json",
		width: '850px',
		height:"100%",
		autowidth : true,
		jsonReader: {
			  repeatitems: false,
			  root: 'result',
			  id: 'id',
			  repeatitems: false,
			  page:  function(obj) {return obj.pageNo; },
			  total: function(obj) { return obj.totalPages; },
			  records: function(obj) { return obj.totalCount; },
			  userdata: "userdata"
	    },
	   	colNames:['IP','数据中心','集群名称','CPU','内存(M)','磁盘(G)','节点状态','组件状态','','',''],
	   	colModel:[
			{
				name : "nodeIp",
				index : "nodeIp",
				align : 'center',
				width : '115px'
			},
	   		{
	   			name : "dataCenterName",
	   			index : "dataCenterName",
	   			align : 'center',
	   			width : '80px'
	   		},
	   		{
	   			name : "clusterName",
	   			index : "clusterName",
	   			align : 'center',
	   			width : '80px'
	   		},
	   		{
	   			name : "cpu",
	   			index : "cpu",
	   			align : 'center',
	   			width : '80px',
	   			formatter : function(val){
	   				if(val){
	   					return val+'%';
	   				}else{
	   					return val;
	   				}
	   			}
	   		},
	   		{
	   			name : "totalMemory",
	   			index : "totalMemory",
	   			align : 'center',
	   			width : '80px', 
	   			formatter : function(val,xx,result){
	   				var totalMemory = val;
	   				var usageMemory = result['usageMemory'];
	   				if(!totalMemory){
	   					totalMemory = 0;
	   				}
	   				if(!usageMemory){
	   					usageMemory = 0;
	   				}
	   				return usageMemory+'/'+totalMemory;
	   			}
	   		},
	   		{
	   			name : "totalfs",
	   			index : "totalfs",
	   			align : 'center',
	   			width : '90px',
	   			formatter : function(val,xx,result){
	   				var totalfs = val;
	   				var usagefs = result['usagefs'];
	   				if(!totalfs){
	   					totalfs = 0;
	   				}
	   				if(!usagefs){
	   					usagefs = 0;
	   				}
	   				return (usagefs/1024).toFixed(1)+'/'+(totalfs/1024).toFixed(1);
	   			}
	   		},
	   		{
	   			name:'nodeStatus',
	   			index:'nodeStatus',
	   			width:'90px',
	   			align : 'center',
	   			formatter : function(val){
	   				if(val == 'on'){
	   					return '<img src="/paas/imgs/build_success.png" width="12" height="12" style="margin-right:5px;" title="正常"/>正常';
	   				}else if(val == 'off'){
	   					return '<img src="/paas/imgs/build_call_fail.png"; width="12" height="12" style="margin-right:5px;" title="初始状态"/>初始状态';
	   				}else {
	   					return '<img src="/paas/imgs/build_call_fail.png"; width="12" height="12" style="margin-right:5px;" title="不可用"/>不可用';
	   				}
	   			}
	   		},
	   		{
	   			name: 'component_status',
	   			index:'component_status',
	   			width:'100px',
	   			align : 'center',
	   			formatter : function(val){
	   				if(val == 'active'){
	   					return '<img src="/paas/imgs/build_success.png" width="12" height="12" style="margin-right:5px;" title="正常"/>正常';
	   				}else {
	   					return '<img src="/paas/imgs/build_call_fail.png"; width="12" height="12" style="margin-right:5px;" title="异常"/>异常';
	   				}
	   			}
	   		},
	   		{
	   			name : "nodeId",
	   			index : "nodeId",
	   			hidden : true
	   		},{
	   			name : "usageMemory",
	   			index : "usageMemory",
	   			hidden : true
	   		},
	   		{
	   			name : "usagefs",
	   			index : "usagefs",
	   			hidden : true
	   		}
	   		
	   	],
	   	postData : {
			'clusterId' : $('#cluster').val(),
			'dataCenterId' : $('#dataCenter').val(),
			'nodeId' : $('#node').val()
		},
	   	multiselect: false ,
	   	rowNum:9,
	   	loadonce:true,
		altRows:true,
		altclass:'r1',
		forceFit:true,
	   	pager: '#pagerBar',
	    viewrecords: true,
	    rownumbers:true,
	    caption:"",
	    subGrid : true,
		//点击展开时调用
		subGridRowExpanded : function(subgrid_id, row_id) {
			var rowData = jQuery("#globalMonitorTable").jqGrid('getRowData',row_id);
			var nodeId = rowData['nodeId'];
			$("#" + subgrid_id).html("<table id='subgrid_table_id'></table>");			
			$.ajax({
				type : "GET",
				dataType : "json",
				data : {"minionIp":nodeId},
				url : "/paas/component/queryComponentInfoList.action",
				success : function(result) {
					$('#'+subgrid_id).html("");
					result = result['result'];
					var rowHtml = $('#'+subgrid_id);
					rowHtml.append('<div style="margin-left:36px;float:left;">组件名称</div><div style="margin-left:61px;float:left;" >组件状态</div><br>');
					for(var i = 0;i<result.length;i++){
						var _status = result[i].status;
						var vHtml="";
						if (_status == "active") {
							vHtml= '<img src="../../imgs/run.png" width="10" height="10" border="none" title="正常"/>'
									+ ' 正常';
						}else if(_status == "inactive"){
							vHtml='<img src="../../imgs/stopFlag.png" width="10" height="10" border="none" title="异常"/>'
								+ ' 异常 ';
						}else if(_status == "on"){
							vHtml='<img src="../../imgs/stopFlag.png" width="10" height="10" border="none" title="部署成功"/>'
								+ ' 部署成功 ';
						}else if(_status == "off"){
							vHtml='<img src="../../imgs/stopFlag.png" width="10" height="10" border="none" title="部署失败"/>'
								+ ' 部署失败 ';
						}else if(_status == "deploying"){
							vHtml='<img src="../../imgs/stopFlag.png" width="10" height="10" border="none" title="部署中"/>'
								+ ' 部署中 ';
						}else{
							 vHtml='<img src="../../imgs/stopFlag.png" width="10" height="10" border="none" title="停止"/>'
									+ ' 停止 ';
						};
						rowHtml.append('<div style="margin-left:36px;float:left;">'+result[i].name+'</div><div style="position:absolute;left:209px;float:left;" >'+vHtml+'</div><br>');
					}
				}
			});
		},
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
					if(Number(page) > Number(totalPage)){
						reloadResults(totalPage);
					}
					if(Number(page) < 1){ //当翻页搜索是小数时
						reloadResult();
					}
				}
			}
		}
	});
};

//当输入的跳转页数大于总页数是,自动查询最后一页
function reloadResults(totalPage){
    var rows = $('#globalMonitorTable').getGridParam('rows');
    var sidx = $('#globalMonitorTable').getGridParam('sidx');
    var sord = $('#globalMonitorTable').getGridParam('sord');
    jQuery("#globalMonitorTable").jqGrid('setGridParam', {
        url : '/paas/globalMonitor/queryGlobalMonitorList.action',
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).setGridParam({postData:{'dataCenterId':$('#dataCenter').val(),'clusterId':$('#cluster').val(),'nodeId':$('#node').val()}}).trigger("reloadGrid");
}


function reloadResult(){
	$("#globalMonitorTable").jqGrid()
		.setGridParam({page:1,rowNum:9,mtype:"POST",datatype: "json",url : '/paas/globalMonitor/queryGlobalMonitorList.action'})
		.setGridParam({postData:{'dataCenterId':$('#dataCenter').val(),'clusterId':$('#cluster').val(),'nodeId':$('#node').val()}})
		.trigger("reloadGrid");
}
