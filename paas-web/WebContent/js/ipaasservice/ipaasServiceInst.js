
$(function(){
	var ipaasServiceId = $.getUrlParam('ipaasServiceId');
	$('#queryIpaasServiceInstList').jqGrid({
		url:'/paas/ipaasInstance/queryIpaasServiceInstsById.action',
		datatype: "json",
		width: '100%',
		autowidth:true,
		height: "100%",
		postData:{
			ipaasServiceId : ipaasServiceId.toString()
		},
		jsonReader: {
			  repeatitems: false,
			  root: 'result',
			  ipaasServiceId: 'ipaasServiceId',
			  repeatitems: false,
			  page:  function(obj) {return obj.pageNo; },
			  total: function(obj) { return obj.totalPages; },
			  records: function(obj) { return obj.totalCount; },
			  userdata: "userdata"
	    },
	   	colNames:['实例名称', '节点IP', '创建时间', '上一次启动时间', '状态', '操作', '', ''],
	   	colModel:[      
	   		{
	   			name : "instanceId",
	   			index : "instanceId",
	   			align : 'center',
	   		},
	   		{
	   			name : "hostIP",
	   			index : "hostIP",
	   			align : 'center',
	   		},
	   		{
	   			name : "createTime",
	   			index : "createTime",
	   			align : 'center',
	   		},
	   		{
	   			name : "lastTime",
	   			index : "lastTime",
	   			align : 'center',
	   		},
	   		{
	   			name : "status",
	   			index : "status",
	   			title : false,
	   			formatter:function(val,options,rowObject){
	   				var _status = rowObject['status'];
	   				var _suggestMsg = rowObject['suggestMsg'];
	   				var _nbsp = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	   				if(_status == 'running'){
	   					return _nbsp+'<img src="../../imgs/running.png" width="16" height="16" border="none" title="'+_suggestMsg+'"/>'+_status;
	   				}else if(_status == 'waiting'){
	   					return _nbsp+'<img src="../../imgs/waiting.png" width="16" height="16" border="none" title="'+_suggestMsg+'"/>'+_status;
	   				}else if(_status == 'termination'){
	   					return _nbsp+'<img src="../../imgs/termination.png" width="16" height="16" border="none" title="'+_suggestMsg+'"/>'+_status;
	   				}else if(_status == 'unassigned'){
	   					return _nbsp+'<img src="../../imgs/unassigned.png" width="16" height="16" border="none" title="'+_suggestMsg+'"/>'+_status;
	   				}else if(_status == 'unknown'){
	   					return _nbsp+'<img src="../../imgs/unknown.png" width="16" height="16" border="none" title="'+_suggestMsg+'"/>'+_status;
	   				}else{
	   					return _status;
	   				}
	   			},
	   			align : 'center'
	   		},
	   		{	name:"operation", 
	   			index:"operation", 
	   			formatter:function(val,options,rowObject){
		   			var _status = rowObject['status'];
		   			var _link="";
		   			if(_status == 'waiting' || _status == 'termination'){
		   				_link = '<a href="JavaScript:diagnosis('+"'"+ipaasServiceId+"'"+')" style="color:#666" title="诊断"><img src="../../imgs/diagnosis.png" width="16" height="16" border="none" />诊断</a>';
		   			}
		   			return _link;
	   			},
	   		   align : 'center'
	   		},
	   		{name: 'containerId',index:'containerId',hidden: true },
	   		{name: 'suggestMsg',index:'suggestMsg',hidden: true }
	   	],
	   	multiselect: false ,
	   	rowNum:8,
	   	loadonce:true,
		altRows:true,
		altclass:'r1',
		forceFit:false,
	   	pager: '#pagerBar',
	   	sortable : false,
	   	sortname: 'createTime',
	    viewrecords: true,
	    sortorder: "desc",
	    caption:"",
	    rownumbers:true,
	    gridComplete : function() {
	    	var result = $(this).getGridParam('userData');
			if (typeof(result['errorCode'])!='undefined') {
				parent.parent.alertError(result['errorCode'],result['errorMsg']);
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
});
$(function(){
    $(window).resize(function(){   
    	$("#queryIpaasServiceInstList").setGridWidth($(window).width()*0.99);
});
}); 
function reloadResults(totalPage){
	var page = $('#queryIpaasServiceInstList').getGridParam('page'); 
    var rows = $('#queryIpaasServiceInstList').getGridParam('rows'); 
    var sidx = $('#queryIpaasServiceInstList').getGridParam('sidx'); 
    var sord = $('#queryIpaasServiceInstList').getGridParam('sord'); 
    jQuery("#queryIpaasServiceInstList").jqGrid('setGridParam', {
        url : '/paas/ipaasInstance/queryIpaasServiceInstsById.action',
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).trigger("reloadGrid");
}



function reloadResult(){
	$("#queryIpaasServiceInstList").jqGrid()
		.setGridParam({page:1,rowNum:8,mtype:"POST",datatype: "json",url : '/paas/ipaasInstance/queryIpaasServiceInstsById.action'})
		.trigger("reloadGrid");
}

function diagnosis(ipaasServiceId){
	parent.parent.openDialog("/paas/jsp/ipaasservice/diagnosResult.jsp?ipaasServiceId=" + ipaasServiceId, "诊断结果", 555, 300);
}




function refreshInstance(){
	parent.parent.closeAlert();
	reloadResult();
}
var timer = setInterval("refreshInstance()","10000");
