
var dataCenterId;
var dataCenterName;

$(function(){
	dataCenterName = $("#dataCenterName").val();
	loadClusterList();
	
	//jqGrid随窗口大小变化自适应宽度
	$(window).resize(function(){
		$("#clusterList").setGridWidth($(window).width());
	});
});

var loadClusterList = function(){
	
	dataCenterId = $.getUrlParam('dataCenterId'); //点击集群查询所需参数：数据中心Id
	
	//数据中心列表
	$('#clusterList').jqGrid({
		url:'/paas/newcluster/queryClusterList.action',
		datatype: "json",
		width: '1000px',
		height:"100%",
		cellEdit : false, //启用或者禁用单元格编辑功能
		//cellurl : '/paas/cluster/updateDescription.action', //单元格提交的URL
		autowidth : true,
		postData:{
			dataCenterId : dataCenterId
		},
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
	   	colNames:['集群名称','类型','label','创建时间','描述',''],
	   	colModel:[
	   		{
	   			name : "name",
	   			index : "name",
	   			align : 'center',
	   			width : '200px'
	   		},
	   		{
	   			name : "type",
	   			index : "type",
	   			align : 'center',
	   			width : '70px',
	   			formatter : function(val, options, rowObject) {
					var _s = rowObject['type'];													
					if (_s == "1") {
						return 'ipaas集群';
					} else if (_s == "2") {
						return 'apaas集群';
					} else if(_s == "3"){
						return 'paas平台';
					}
				}
	   		},
	   		{
	   			name : "label",
	   			index : "label",
	   			align : 'center',
	   			width : '90px'
	   		},
	   		{
	   			name : "insertTime",
	   			index : "insertTime",
	   			align : 'center',
	   			width : '90px'
	   		},
	   		{
	   			name : "description",
	   			index : "description",
	   			align : 'center',
	   			width : '280px',
	   			editable : true, //单元格是否可编辑
	   		},
	   		{
	   			name: 'id',
	   			index:'id',
	   			hidden: true 
	   		}
	   	],
	   	multiselect: false ,
	   	rowNum:10,
	   	loadonce:true,
		altRows:true,
		altclass:'r1',
		forceFit:true,
	   	pager: '#pagerBar',
	    viewrecords: true,
	    rownumbers:true,
	    caption:"",
	    //【描述】字段单元格可编辑，保存值做限制
		beforeSaveCell : function(rowid,celname,value,iRow,iCol) {
			if(value.length > 255){
				parent.parent.alertWarn("描述信息最多只能保存255个字符！");
				return value.substring(0,255);
			}
		},
	    gridComplete : function() {
	    	var result = $(this).getGridParam('userData');
			if (typeof(result['errorCode'])!='undefined') {
				parent.parent.alertError(result['errorCode'],result['errorMsg']);
			}
		},
		//数据提交到服务器并返回信息后触发
		//防止SQL注入
		afterSubmitCell: function(serverresponse, rowid, cellname, value, iRow, iCol){
			var json = serverresponse.responseText;
			//判断是否为object，不是则将其转换成object
			if(typeof json === 'object'){
				result = json;
			}else{
				result = eval("("+json+")");
			}
			if(result["resultCode"] != "success"){
				return [false, '【' +result['resultCode']+'】'+result['resultMessage']];
			}else{
				return [true, ""];
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
    var rows = $('#privateImageList').getGridParam('rows');
    var sidx = $('#privateImageList').getGridParam('sidx');
    var sord = $('#privateImageList').getGridParam('sord');
    jQuery("#privateImageList").jqGrid('setGridParam', {
        url : '/paas/privateImage/queryPrivateImageList.action',
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).trigger("reloadGrid");
}


function showLoad(){	
	$('#loadImg').css({"width":"100%","height":"100%"});
	$('#loadImg').show();
}
function closeLoad(){
	$('#loadImg').hide();
}


function reloadResult(){
	$("#clusterList").jqGrid()
		.setGridParam({page:1,rowNum:10,mtype:"POST",datatype: "json",url : '/paas/newcluster/queryClusterList.action'})
		.setGridParam({postData:{'name':''}})
		.trigger("reloadGrid");
}
