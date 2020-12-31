
$(function(){
	var appServiceId = $.getUrlParam('id');
	$('#queryAppInstList').jqGrid({
		url:'/paas/appServiceInstance/queryInst.action',
		datatype: "json",
		width: '1106px',
		height: "100%",
		postData:{
			appServiceId : appServiceId.toString()
		},
		jsonReader: {
			  repeatitems: false,
			  root: 'result',
			  id: 'appServiceId',
			  repeatitems: false,
			  page:  function(obj) {return obj.pageNo; },
			  total: function(obj) { return obj.totalPages; },
			  records: function(obj) { return obj.totalCount; },
			  userdata: "userdata"
	    },
	   	colNames:['实例名称', '节点IP', '创建时间', '启动时间', '状态', '原因','版本', '操作', '', '', '',''],
	   	colModel:[      
	   		{
	   			name : "instanceId",
	   			index : "instanceId",
	   			align : 'center',
	   			width : '120px',
	   		},
	   		{
	   			name : "hostIP",
	   			index : "hostIP",
	   			align : 'center',
	   			width : '115px',
	   		},
	   		{
	   			name : "createTime",
	   			index : "createTime",
	   			align : 'center',
	   			width : '90px',
	   		},
	   		{
	   			name : "lastTime",
	   			index : "lastTime",
	   			align : 'center',
	   			width : '90px',
	   		},
	   		{
	   			name : "status",
	   			index : "status",
	   			title : false,
	   			formatter:function(val,options,rowObject){
	   				var _status = rowObject['status'];
	   				var _nbsp = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	   				if(_status == 'running'){
	   					return _nbsp+'<img src="../../imgs/running.png" width="16" height="16" border="none" />'+_status;
	   				}else if(_status == 'waiting'){
	   					return _nbsp+'<img src="../../imgs/waiting.png" width="16" height="16" border="none" />'+_status;
	   				}else if(_status == 'termination'){
	   					return _nbsp+'<img src="../../imgs/termination.png" width="16" height="16" border="none" />'+_status;
	   				}else if(_status == 'unassigned'){
	   					return _nbsp+'<img src="../../imgs/unassigned.png" width="16" height="16" border="none" />'+_status;
	   				}else if(_status == 'unknown'){
	   					return _nbsp+'<img src="../../imgs/unknown.png" width="16" height="16" border="none" />'+_status;
	   				}else{
	   					return _status;
	   				}
	   			},
	   			align : 'center',
	   			width : '115px'
	   		},{
	   			name : "suggestMsg",
	   			index : "suggestMsg",
	   			formatter:function(val,options,rowObject){
	   				var statusMsg = '';
	   				if(val.length>=10){
	   					var tempVal = val.substring(0,7);
	   					statusMsg = '<span title="'+val+'">'+tempVal+'...</span>';
	   				}else{
	   					statusMsg = '<span title="'+val+'">'+val+'</span>';
	   				}
	   				return statusMsg;
	   			},
	   			align : 'center',
	   			width : '115px',
	   		},
	   		{
	   			name : "type",
	   			index : "type",
	   			formatter:function(val,options,rowObject){
	   				if(val == '2'){
	   					return '灰度版本';
	   				}else if(val == '1'){
	   					return '运行版本';
	   				}
	   			},
	   			align : 'center',
	   			width : '110px',
	   		},
	   		{	name:"operation", 
	   			index:"operation", 
	   			formatter:function(val,options,rowObject){
		   			var _instanceId = "'" +rowObject['instanceId']+ "'";
		   			var _containerId = "'" +rowObject['containerId']+ "'";
		   			var _hostIP = "'" +rowObject['hostIP']+ "'";
		   			var _status = rowObject['status'];
		   			var websshUrl = rowObject['websshUrl'];
		   			var _link ='<a href="JavaScript:restartInst(' +_instanceId+','+rowObject['type']+')" style="color:#666" title="重启"><img src="../../imgs/instanceRestart.png" width="16" height="16" border="none" />重启</a>'
		   			
		   			if(_status == 'running'){
		   				_link += '&nbsp;&nbsp;<a href="JavaScript:exeCommand('+"'http://"+websshUrl+"'"+')" style="color:#666" title="ssh"><img src="../../imgs/ssh.png" width="16" height="16" border="none" />SSH</a>';
		   			}
		   			if(_status == 'waiting' || _status == 'termination'){
		   				_link += '&nbsp;&nbsp;<a href="JavaScript:diagnosisAppService('+"'"+appServiceId+"'"+','+rowObject['type']+')" style="color:#666" title="诊断"><img src="../../imgs/diagnosis.png" width="16" height="16" border="none" />诊断</a>';
		   			}
		   			_link += '<a href="/paas/appServiceInstance/queryPodDetail.action?namespace='+rowObject['namespace']+'&podName='+rowObject['instanceId']+'" style="color:#666" title="详情"><img src="../../imgs/Icondetails.png" width="16" height="16" border="none" />详情</a>';
		   			//_link += '&nbsp;&nbsp;<a href="JavaScript:dialogContainerDetails('+_containerId+','+_hostIP+')" style="color:#666" title="容器详情"><img src="../../imgs/container.png" width="16" height="16" border="none" />容器详情</a>';
		   			return _link;
	   			},
	   		   align : 'center',
	   		   width : '222px',
	   		},
	   		{name: 'containerId',index:'containerId',hidden: true },
	   		{name: 'suggestMsg',index:'suggestMsg',hidden: true },
	   		{name: 'websshUrl',index:'websshUrl',hidden: true },
	   		{name: 'namespace',index:'namespace',hidden: true }
	   	],
	   	multiselect: false ,
	   	rowNum:8,
	   	loadonce:true,
		altRows:false,
		altclass:'r1',
		forceFit:false,
	   	pager: '#pagerBar',
	   	sortable : true,
	   	sortname: 'createTime',
	    viewrecords: true,
	    sortorder: "desc",
	    caption:"",
	    rownumbers:true,
	    sortable : false,
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
					if(Number(page) > Number(totalPage)){
						reloadResults(totalPage);
					}
				}
			}
		}
	});
});

function reloadResults(totalPage){
	var page = $('#imageList').getGridParam('page'); 
    var rows = $('#imageList').getGridParam('rows'); 
    var sidx = $('#imageList').getGridParam('sidx'); 
    var sord = $('#imageList').getGridParam('sord'); 
    jQuery("#imageList").jqGrid('setGridParam', {
        url : '/paas/appServiceInstance/queryInst.action',
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).trigger("reloadGrid");
}

function restartInst(instanceId,type) {
	var appServiceId = $.getUrlParam('id');
	
	parent.parent.alertConfirm("确认重启实例？", function(){
		parent.parent.showLoad();
		clearInterval(timer);
		$.ajax({
			type:"POST",
			dataType:"json",
			data: {
				"instanceId":instanceId.toString(),
				"appServiceId" : appServiceId.toString(),
				'type' : type.toString()
		        },
			url:"/paas/appServiceInstance/restartInst.action",
			success:function(result){
				parent.parent.closeLoad();
				timer = setInterval("refreshInstance()","10000");
				if(result['resultCode'] == 'success'){
					reloadResult();
					parent.parent.alertSuccess();
				}
				else{
					parent.parent.alertError(result['resultCode'],result['resultMessage']);
				}
			}
		});
	});
}

function reloadResult(){
	$("#queryAppInstList").jqGrid()
		.setGridParam({page:1,rowNum:8,mtype:"POST",datatype: "json",url : '/paas/appServiceInstance/queryInst.action'})
		.trigger("reloadGrid");
}

function diagnosisAppService(appServiceId,type){
	parent.parent.openDialog("/paas/jsp/appservice/diagnosResult.jsp?appServiceId=" + appServiceId+'&type='+type, "诊断结果", 555, 300);
}

function dialogContainerDetails(containerId, hostIP){
	parent.parent.openDialog("/paas/jsp/appservice/dialogContainerDetails.jsp?containerId=" + containerId + "&hostIP=" + hostIP, "容器详情", 555, 300);
}

function exeCommand(websshURL){
	openWindow(websshURL, "命令控制台");
}

function openWindow(url,titleStr){
	//window.screen.height获得屏幕的高，window.screen.width获得屏幕的宽   
	var iHeight=400;
	var iWidth=750;
	var iTop = (window.screen.height-30-iHeight)/2; //获得窗口的垂直位置;   
	var iLeft = (window.screen.width-10-iWidth)/2; //获得窗口的水平位置;   
	window.open(url, titleStr, "height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft+",toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no, titlebar=no");
}
function refreshInstance(){
	parent.parent.closeAlert();
	reloadResult();
}
var timer = setInterval("refreshInstance()","10000");
