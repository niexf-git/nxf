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
		$("#queryAppServiceList").jqGrid()
			.setGridParam({page:1, rowNum:10, mtype:"POST", datatype: "json", url : '/paas/appservice/queryNewAppService.action'})
			.trigger("reloadGrid");
	}
	
	$(function() {
		$(window).resize(function(){
			$("#queryAppServiceList").setGridWidth($(window).width());
		});
		var minionIp = $.getUrlParam('minionIp');
		$('#queryAppServiceList').jqGrid({
				url:"/paas/appservice/queryNewAppService.action",
				datatype : "json",
				width : 820,
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
				colNames : [ '服务名称', '状态', '实例数', '应用名称','操作类型','灰度版本','创建人', '创建时间','','','','','' ],
				colModel : [
						{
							name : "name",
							index : "name",
							align : 'center',
							width : '110px'
						}, {
							width : '65px',
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
							name : "instance_num",
							index : "instance_num",
							align : 'center',
							width : '110px',
							formatter : function(val, options, rowObject) {																
								var _runningInstCount = rowObject['running_Inst_num'];
								var _instance_num = rowObject['instance_num'];
								var _inst_scale_type = rowObject['inst_scale_type'];
								if(_inst_scale_type == "1"){
									return _runningInstCount+"/"+_instance_num;
								}else if(_inst_scale_type == "2"){
									var _inst_min = rowObject['inst_min'];
									var _inst_max = rowObject['inst_max'];
									return _runningInstCount+"/"+_inst_min+"~"+_inst_max;
								}	
							}
						}, {
							name : "app_name",
							index : "app_name",
							align : 'center',
							width : '110px'
							
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
						},
						{
							name : "existGray",
							index : "existGray",
							align : 'center',
							width : '60px',
							formatter : function(val, options, rowObject){
								 if(val == 0){
									return '否';
								}else{
									return '是';
								} 
							}
						},{
							name : "user_id",
							index : "user_id",
							align : 'center',
							width : '90px'
						}, {
							name : "create_time",
							index : "create_time",
							align : 'center',
							width : '170px'
							//width:"280"
						},{
							name : 'running_Inst_num',
							index : 'running_Inst_num',
							hidden : true
						},
						{
							name : 'instance_num',
							index : 'instance_num',
							hidden : true
						},
						// 缓存inst_scale_type,避免翻页无法获取
						{
							name : 'inst_scale_type',
							index : 'inst_scale_type',
							hidden : true
						},
						// 缓存_inst_min,避免翻页无法获取
						{
							name : 'inst_min',
							index : 'inst_min',
							hidden : true
						},
						// 缓存_inst_max,避免翻页无法获取
						{
							name : 'inst_max',
							index : 'inst_max',
							hidden : true
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
		var page = $('#queryAppServiceList').getGridParam('page'); 
	    var rows = $('#queryAppServiceList').getGridParam('rows'); 
	    var sidx = $('#queryAppServiceList').getGridParam('sidx'); 
	    var sord = $('#queryAppServiceList').getGridParam('sord'); 
	    jQuery("#queryAppServiceList").jqGrid('setGridParam', {
	        url : "/paas/appservice/queryNewAppService.action",
	        page:totalPage,
	        rows:rows,
	        sidx:sidx,
	        sord:sord,
	    }).trigger("reloadGrid");
	}
	
</script>
<div>
	<div class="content-tab">
		<table cellspacing="0" id="queryAppServiceList"></table>
		<div id="pagerBar"></div>
	</div>
</div>
