
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
		url:'/paas/cluster/queryClusterList.action',
		datatype: "json",
		width: '1000px',
		height:"100%",
		cellEdit : true, //启用或者禁用单元格编辑功能
		cellurl : '/paas/cluster/updateDescription.action', //单元格提交的URL
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
	   	colNames:['集群名称','类型','label','创建时间','描述','操作',''],
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
	   			name:'id',
	   			loginName:'id',
	   			width:'100px',
	   			align : 'center',
	   			formatter:function(val, options, rowObject){
	   				var _v = "'" +val+ "'";
	   				var clusterType = "'" + rowObject['type'] + "'";
		   			var _link;
		   				_link = '<a href="JavaScript:deleteCluster(' +dataCenterId+',' +_v+ ',' +clusterType+ ')" style="color:#666">'+
									'<img src="/paas/imgs/delete.png" width="22" height="22" border="none" title="删除"/>'+
								'</a>&nbsp;&nbsp;&nbsp;';
		   			return _link;
	   			}
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

/**
 * 打开新增集群窗口
 */
function openCreateCluster() {
	parent.parent.openDialog("/paas/jsp/monitoroperation/createCluster.jsp?dataCenterId="+dataCenterId+"&dataCenterName="+dataCenterName,"新增集群",600, 280);
}

function showLoad(){	
	$('#loadImg').css({"width":"100%","height":"100%"});
	$('#loadImg').show();
}
function closeLoad(){
	$('#loadImg').hide();
}

/**
 * 新增集群
 */
function createCluster(){
	
//	1、数据中心Id
//	2、集群名称
//	3、类型
//	4、描述
//	5、拿到上面4个参数，ajax请求action
	
	var dataCenterId = $.trim($("#dataCenterId").val());
	
	var clusterName = $.trim($("#clusterName").val());
	if(clusterName==""){
		parent.alertWarn("集群名称不能为空");
		return;
	}
	
	var clusterType = $("#clusterType").val();
	
	var label = $.trim($("#label").val());
	if(label==""){
		parent.alertWarn("label不能为空");
		return;
	}
//	(([A-Za-z0-9][-A-Za-z0-9_.]*)?[A-Za-z0-9])? 监控运维的校验
	var regLabel = /^(([A-Za-z][A-Za-z0-9_]*)?[A-Za-z0-9])?$/;
	if(!regLabel.test(label)){
    	parent.alertWarn("label只能包含英文字符、数字和下划线，并且只能以英文开头，英文和数字结尾！");
		$("#label").focus();
		return;
    }
	
	var desc = $.trim($("#desc").val());
	showLoad();
	$.ajax({
		type:"POST",
		dataType:"json",
		 url:"/paas/cluster/createCluster.action",
         data:{
        	"dataCenterId" : dataCenterId,
        	"name" : clusterName,
        	"type" : clusterType,
        	"label" : label,
        	"description" : desc
         },
		success:function(result){
			if(result['resultCode'] == 'success'){
				closeLoad();
				// 新增集群成功后刷新左侧树
				$("#mainIframe",window.parent.document)[0].contentWindow.frames["left"].refreshNode();
				
				parent.alertSuccess(); // 弹出成功提示框
				
				// 新增数据中心成功后刷新列表
//				$("#mainIframe",window.parent.document)[0].contentWindow.frames["right"].frames["mainIframe"].contentWindow.reloadResult();
				
				// 新增集群成功后刷新列表
				$("#mainIframe",window.parent.document)[0].contentWindow.frames["right"].reloadResult();
				
				parent.close(); // 关闭新增集群弹出窗口
				
			}
			else{
				parent.alertError(result['resultCode'], result['resultMessage']);
				closeLoad();
			}
		}
	}); 
}

/**
 * 删除集群
 * @param dataCenterId
 * @param clusterId
 */
function deleteCluster(dataCenterId, clusterId, clusterType){
	
	parent.parent.alertConfirm("确认删除集群?",function(){
		$.ajax({
			type:"POST",
			dataType:"json",
			data: {
				"dataCenterId" : dataCenterId,
				"id" : clusterId,
				"type" : clusterType
		    },
			url:"/paas/cluster/deleteCluster.action",
			success:function(result){
				if(result['resultCode'] == 'success'){
					reloadResult();
					// 删除集群成功后刷新左侧树
					window.parent.frames["left"].refreshNode();
					parent.parent.alertSuccess();
				}
				else{
					parent.parent.alertError(result['resultCode'], result['resultMessage']);
				}
			}
		});
	});
}

function reloadResult(){
	$("#clusterList").jqGrid()
		.setGridParam({page:1,rowNum:10,mtype:"POST",datatype: "json",url : '/paas/cluster/queryClusterList.action'})
		.setGridParam({postData:{'name':''}})
		.trigger("reloadGrid");
}
