<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script>	
<!-- 单元格内容换行 -->
<style>
	.ui-jqgrid tr.jqgrow td {
	  	white-space: normal !important;
	  	word-wrap:break-word;
	 }
	 html,body{ overflow-x:hidden;}
</style>
<script type="text/javascript">
	function reloadResult(){
		$("#queryIpaasServiceList").jqGrid()
			.setGridParam({page:1, rowNum:10, mtype:"POST", datatype: "json", url : '/paas/ipaasservice/queryIpaasService'})
			.trigger("reloadGrid");
	}
	
	$(function() {
		$(window).resize(function(){
			$("#queryIpaasServiceList").setGridWidth($(window).width());
		});
		var minionIp = $.getUrlParam('minionIp');
		$('#queryIpaasServiceList').jqGrid({
				url:"/paas/ipaasservice/queryIpaasService",
				datatype : "json",
				width : 826,
				height : "100%",
				autowidth : true,
				postData:{
					minionIp : minionIp.toString()
				},
				jsonReader : {
					repeatitems : false,
					root : 'result',
					id : 'appId',
					page : function(obj) {
						return obj.pageNo;
					},
					total : function(obj) {
						return obj.totalPages;
					},
					records : function(obj) {
						return obj.totalCount;
					},
					userdata : "userdata"
				},
				colNames : [ '服务名称', '状态', '实例数','应用名称','操作类型','创建人', '创建时间','','' ],
				colModel : [
						{
							name : "name",
							index : "name",
							align : 'center',
							width : '135px'
						}, {
							width : '70px',
							align : 'center',
							index : 'status',
							name : 'status',
							formatter : function(val, options, rowObject) {
								var _s = rowObject['status'];
								if (_s == "1") {
									return '<img src="/paas/imgs/stopFlag.png" width="12" height="12" style="margin-right:5px;" />停止';
								} else if (_s == "2") {
									return '<img src="/paas/imgs/run.png" width="12" height="12" style="margin-right:5px;" />运行';
								} else {
									return _s;
								}
							}
						}, {
							name : "nodeNum",
							index : "nodeNum",
							align : 'center',
							width : '100px',
							formatter : function(val, options, rowObject) {																
								var _runningInstCount = rowObject['running_Inst_num'];
								var _instance_num = rowObject['instance_num'];
								return _runningInstCount+"/"+_instance_num;		
							}
						},{
							name : "app_name",
							index : "app_name",
							align : 'center',
							width : '100px'
							
						},{
							name : "oper_type",
							index : "oper_type",
							align : 'center',
							width : '70px',
							formatter : function(val, options, rowObject) {
								var _s = rowObject['oper_type'];
								if (_s == "1") {
									return '开发';
								} else if (_s == "2") {
									return '测试';
								} else if (_s == "3"){
									return '生产';
								}
							}
						},{
							name : "user_id",
							index : "user_id",
							align : 'center',
							width : '100px'
						}, {
							name : "create_time",
							index : "create_time",
							align : 'center',
							width : '135px'
							//width:"280"
						},
						// 缓存runningInstCount,避免翻页无法获取
						{
							name : 'running_Inst_num',
							index : 'running_Inst_num',
							hidden : true
						},
						{
							name : 'instance_num',
							index : 'instance_num',
							hidden : true
						}
						],
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
				caption : "",
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
						if(totalPage == 1){
							reloadResult();
						}else{
							var page = $("input[class='ui-pg-input']").val();
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
	
	//当输入的跳转页数大于总页数是,自动查询最后一页
	function reloadResults(totalPage){
		var page = $('#queryIpaasServiceList').getGridParam('page'); 
	    var rows = $('#queryIpaasServiceList').getGridParam('rows'); 
	    var sidx = $('#queryIpaasServiceList').getGridParam('sidx'); 
	    var sord = $('#queryIpaasServiceList').getGridParam('sord'); 
	    jQuery("#queryIpaasServiceList").jqGrid('setGridParam', {
	        url : "/paas/ipaasservice/queryIpaasService",
	        page:totalPage,
	        rows:rows,
	        sidx:sidx,
	        sord:sord,
	    }).trigger("reloadGrid");
	}
	
</script>
<div>
	<div class="content-tab">
		<table cellspacing="0" id="queryIpaasServiceList"></table>
		<div id="pagerBar"></div>
	</div>
</div>
