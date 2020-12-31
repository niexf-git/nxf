$(function(){
	var appId = $("#appId").val();
	$('#appList')
	.jqGrid(
			{
				url : '/paas/application/queryDetailsList.action?id='+appId,
				datatype : "json",
				width: '100%',
				height : "100%",
				jsonReader : {
					repeatitems : false,
					root : 'result',
					id : 'id',
					repeatitems : false,
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
				colNames : ['apaas集群','','ipaas集群','', '原数据中心','迁移数据中心', '状态', '最后迁移时间' ,''],
				colModel : [
						{
							name : "originalApaasClusterName",
							index : "originalApaasClusterName",
							align : 'center',
							width : '180px',
							formatter : function(val, options, rowObject) {
								debugger;
								var _originalApaasClusterName = rowObject['originalApaasClusterName'];
								var _migrationApaasClusterName = rowObject['migrationApaasClusterName'];
								if (_originalApaasClusterName!=""&&_originalApaasClusterName!=null&&_migrationApaasClusterName!=""&&_migrationApaasClusterName!=null) {
									return _originalApaasClusterName+"->"+_migrationApaasClusterName;
								}
								return "";
							}
						},
						{
							name : "migrationApaasClusterName",
							index : "migrationApaasClusterName",
							hidden : true
						},
						{
							name : "originalIpaasClusterName",
							index : "originalIpaasClusterName",
							align : 'center',
							width : '170px',
							formatter : function(val, options, rowObject) {
								var _originalIpaasClusterName = rowObject['originalIpaasClusterName'];
								var _migrationIpaasClusterName = rowObject['migrationIpaasClusterName'];
								if (_originalIpaasClusterName!=""&&_originalIpaasClusterName!=null&&_migrationIpaasClusterName!=""&&_migrationIpaasClusterName!=null) {
									return _originalIpaasClusterName+"->"+_migrationIpaasClusterName;
								}
								return "";
							}
						},
						{
							name : "migrationIpaasClusterName",
							index : "migrationIpaasClusterName",
							hidden : true
						},
						{
							name : "originalDataCenterName",
							index : "originalDataCenterName",
							align : 'center',
							width : '160px',
						},
						{
							name : "migrationDataCenterName",
							index : "migrationDataCenterName",
							align : 'center',
							width : '180px',
							title:false
						},		
						{
							name : "recordType",
							index : "recordType",
							align : 'center',
							width : '160px',
							formatter : function(val, options, rowObject) {
								var _s = rowObject['recordType'];
								if (_s == "0") {
									return '迁移';
								} else if (_s == "1") {
									return '容灾';
								} else if(_s == "2"){
									return '恢复';
								}else{
									return _s;
								}
							}
						},		
						{
							name : "migrationDate",
							index : "migrationDate",
							align : 'center',
							width : '240px',
							title:false,
							formatter : function(val, options, rowObject) {
								var array = val.split("\n");
								var value = "";
								if(array.length>0){
									value = array[0];
								}
								return "<p style='border:0px solid gray;' title='"+val+"'>"+value+"</p>";
							}
						},
				   		{
				   			name: 'admin',index:'admin',hidden:true
				   		}
//						, {
//							name : 'id',
//							index : 'id',
//							hidden : true
//						} 
						],
				multiselect : false,
				rowNum : 10,
				loadonce : true,
				altRows : true,
				altclass : 'r1',
				forceFit : true,
				pager : '#pagerBar',
				viewrecords : true,
				rownumbers : true,
				caption : "",
				gridComplete : function() {
					var result = $(this).getGridParam('userData');
					if (typeof(result['errorCode'])!='undefined') {
						parent.alertError(result['errorCode'],result['errorMsg']);
					}
					var page = $(this).getGridParam('page');
					var lastPage = $(this).getGridParam('lastpage');
					$("#page").val(page);
					$("#pageText").text(page + "/" +lastPage);
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
							if(Number(page) < 1){
								reloadResult();
							}
						}
					}
				}
			});
	
});