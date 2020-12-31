/**
 * 创建角色
 */
function openCreateRole(){
	parent.openDialog("/paas/jsp/systemmanager/rolemanager/createRole.jsp","创建角色",620,370);
}

/**
 * 修改角色
 * @param roleId
 * @param type
 */
function toUpdateRole(roleId,type){
	if(type=="0"){
		parent.alertWarn("超级管理员不能修改");
		return;
	}
	parent.openDialog("/paas/role/queryRole.action?roleId="+roleId,"修改角色",620,370);
}

/**
 * 删除角色
 * @param roleId
 * @param type
 */
function deleteRole(roleId,type) {
	if(type=="0"){
		parent.alertWarn("超级管理员不能删除");
		return;
	}
	parent.alertConfirm("确认删除角色?",function(){
		$.ajax({
			type:"POST",
			dataType:"json",
			data: {
				"roleId":roleId
		    },
			url:"/paas/role/deleteRole.action",
			success:function(result){
				if(result['resultCode'] == 'success'){
					reloadResult();
					parent.alertSuccess();
				}
				else{
					parent.alertError(result['resultCode'], result['resultMessage']);
				}
			}
		});
	});
}
function reloadResult(){
	var o = $.trim($('#roleName').val());
	if(o=="搜索用户"){
		o="";
	}
	$("#queryRoleList").jqGrid()
		.setGridParam({page:1,rowNum:10,mtype:"POST",datatype: "json",url : '/paas/role/queryRoleList.action'})
		.setGridParam({postData:{'roleName':o}})
		.trigger("reloadGrid");
}

$(function(){
	
	$("#roleName").keydown(function(e){
		if((e.keyCode || e.which)==13){
			reloadResult();
		}
	});
	
	$("#roleType").change(function(){
		roleTypeChange();
	});
	
	//查询角色列表
	$('#queryRoleList').jqGrid({
		url:'/paas/role/queryRoleList.action',
		datatype: "json",
		width: '100%',
		autowidth:true,
		height: "100%",
		jsonReader: {
			  repeatitems: false,
			  root: 'rows',
			  id: 'id',
			  repeatitems: false,
			  page:  function(obj) {return obj.page; },
			  total: function(obj) { return obj.total; },
			  records: function(obj) { return obj.records; },
			  userdata: "userdata"
	    },
	   	colNames:['角色名称','角色描述','操作',''],
	   	colModel:[
	   		{
	   			name : "roleName",
	   			index : "roleName",
	   			align : 'center',
	   		},
	   		{
	   			name : "description",
	   			index : "description",
	   			align : 'center',
	   		},
	   		{
	   			name:'id',
	   			index:'id', 
	   			align : 'center',
	   			formatter:function(val,options,rowObject){
		   			var _v = "'" +val+ "'";
		   			var name="'" +rowObject["roleName"]+"'";
		   			var type="'" +rowObject["type"]+"'";
		   			var _link = '<a href="javaScript:toUpdateRole(' +_v+','+type+ ')" style="color:#666">'+
		   							'<img src="../../../imgs/modify.png" width="22" height="22" border="none" title="修改" />'+
		   						'</a>&nbsp;&nbsp;&nbsp;'+
		   						'<a href="javaScript:deleteRole(' +_v+','+type+ ')" style="color:#666">'+
		   							'<img src="../../../imgs/delete.png" width="22" height="22" border="none" title="删除" />'+
		   						'</a>&nbsp;&nbsp;&nbsp;'+
		   						'<a href="javaScript:toGrantUser(' +_v+','+name+ ')" style="color:#666">'+
		   							'<img src="../../../imgs/allot.png" width="23" height="23" border="none" title="人员分配" />'+
		   						'</a>';
		   			return _link;
	   			}
	   		},
	   		{   
	   			name: 'id',
	   			index:'id',
	   			hidden: true 
	   		}
	   	],
		gridComplete : function() {
	    	var result = $(this).getGridParam('userData');
	    	if (typeof(result['errorCode'])!='undefined') {
				parent.alertError(result['errorCode'],result['errorMsg']);
	    	}
		},
	   	multiselect: false ,
	   	rownumbers:true,
	   	rowNum:10,
	   	loadonce:false,
		altRows:true,
		altclass:'r1',
		forceFit:true,
	   	pager: '#pagerBar',
	   	sortname: 'id',
	    viewrecords: true,
	    sortorder: "desc",
	    caption:"",
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
});
$(function(){
    $(window).resize(function(){   
    	$("#queryRoleList").setGridWidth($(window).width()*0.99);
});
});
function reloadResults(totalPage){
//	var page = $('#queryRoleList').getGridParam('page');
    var rows = $('#queryRoleList').getGridParam('rows'); 
    var sidx = $('#queryRoleList').getGridParam('sidx'); 
    var sord = $('#queryRoleList').getGridParam('sord'); 
    jQuery("#queryRoleList").jqGrid('setGridParam', {
        url : '/paas/role/queryRoleList.action',
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).trigger("reloadGrid");
}

function roleTypeChange(){
	initTree("1");
	var type= $("#roleType").children('option:selected').val();//这就是selected的值
	//操作权限
	var oprtreeObj = $.fn.zTree.getZTreeObj("oprTree");
	var treeObj = oprtreeObj.getNodes();
	
	//数据权限
	var datatreeObj = $.fn.zTree.getZTreeObj("dataTree");
	var dataObj = datatreeObj.getNodes();
	
	if(type=="2"){ //普通用户
		for(var i=0;i<treeObj.length;i++){
			var id=treeObj[i].id;
//			 if(id=="2" || id=="3"){
			 if(id=="3" || id=="4"){
				oprtreeObj.checkNode(treeObj[i],false);
			 }
		}
		//重新加载数据权限树，让普通用户显示应用数据
		$.fn.zTree.init($("#dataTree"), setting, zNodes);
		
	} 
	else if(type=="1"){ //普通管理员
		//重新加载数据权限树
		$.fn.zTree.init($("#dataTree"), setting, zNodes);
		//存储重新加载后的数据
		datatreeObj = $.fn.zTree.getZTreeObj("dataTree");
		dataObj = datatreeObj.getNodes();
		//普通管理员不显示应用数据
		for(var i=0; i<dataObj.length; i++){
			for(var j=0; j<dataObj[i].children.length; j++){
				dataObj[i].children[j].children = false;
			}
		}
	}
	
}
//创建角色
function createRole(){
	var roleName = $.trim($("#roleName").val());
	if(roleName==""){
		parent.alertWarn("角色名称不能为空");
		return;
	}
	
	//验证规则：字母、数字组成，字母开头，4-16位
    var regRoleName = /^[a-zA-Z][A-Za-z0-9]{0,15}$/;
    if(!regRoleName.test(roleName)){
    	parent.alertWarn("角色名称只能包含英文字符和数字，并且只能以英文开头！");
		$("#roleName").focus();
		return;
    }
    $("#roleName").val(roleName);
	
	var description = $.trim($("#description").val());
//	var roleType = $("#roleType").val();
	var oprtreeObj = $.fn.zTree.getZTreeObj("oprTree");
//	var datatreeObj = $.fn.zTree.getZTreeObj("dataTree");
	var operIds = getCheckId(oprtreeObj);
//	var dataIds = getCheckId(datatreeObj);
	$.ajax({
		type:"POST",
		dataType:"json",
		 url:"/paas/role/createRole.action",
         data:{
        	"operSelectNode":operIds,
//        	"dataSelectNode":dataIds,
        	"dataSelectNode":"",
        	"roleName":roleName,
        	"description":description,
//        	"roleType":roleType
         },
		success:function(result){
			if(result['resultCode'] == 'success'){
				parent.alertSuccess();
				parent.closedl("createRole",result['resultMessage']);
			}
			else{
				parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	}); 
}
var setting = {
//		view: {
//			showIcon: false,
//		},
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			},
			showTitle:true, //是否显示节点title信息提示 默认为true
		    key: {
	            title:"title" //设置title提示信息对应的属性名称 也就是节点相关的某个属性
	        }
		}
};

//定义存储数据权限树变量，提供给本页面使用而不需要重新从后台获取
var zNodes = null;

var loadPerTree=function(){
	$.post('/paas/permission/queryPerTree.action',
			function(data){
				 $.fn.zTree.init($("#oprTree"), setting, eval(data["operPerTreeList"]));
				 //获取数据权限树
//				 zNodes = eval(data["dataPerTreeList"]);
//			     $.fn.zTree.init($("#dataTree"), setting, zNodes);
			     
//			     roleTypeChange();
			},
			"json"
	);
};
//修改角色
function updateRole(){
	var roleName = $.trim($("#roleName").val());
	if(roleName==""){
		parent.alertWarn("角色名称不能为空");
		return;
	}
	
	//验证规则：字母、数字组成，字母开头，4-16位
    var regRoleName = /^[a-zA-Z][A-Za-z0-9]{0,15}$/;
    if(!regRoleName.test(roleName)){
    	parent.alertWarn("角色名称只能包含英文字符和数字，并且只能以英文开头！");
		$("#roleName").focus();
		return;
    }
    $("#roleName").val(roleName);
    
	var roleId = $("#roleId").val();
	var description = $.trim($("#description").val());
	var oprtreeObj = $.fn.zTree.getZTreeObj("oprTree");
//	var datatreeObj = $.fn.zTree.getZTreeObj("dataTree");
	var operIds = getCheckId(oprtreeObj);
//	var dataIds = getCheckId(datatreeObj);
	$.ajax({
		type:"POST",
		dataType:"json",
		 url:"/paas/role/updateRole.action",
         data:{
        	"operSelectNode":operIds,
//        	"dataSelectNode":dataIds,
        	"dataSelectNode":"",
        	"roleId":roleId,
        	"roleName":roleName,
        	"description":description
         },
		success:function(result){
			if(result['resultCode'] == 'success'){
				parent.alertSuccess();
				parent.closedl("updateRole",result['resultMessage']);
			}
			else{
				parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	}); 
}
function getCheckId(treeObj){
	var dsblNodes = treeObj.getNodesByParam("checked", true);
	var selectNodeIds = "";
	for(var i=0;i<dsblNodes.length;i++){
		if(selectNodeIds==""){
	   		 selectNodeIds += dsblNodes[i].id;
	   	 }else{
	   		 selectNodeIds += "," + dsblNodes[i].id;
	   	 }
	}
    return selectNodeIds;
}
//modify by lilv,$.get->$.post
/**
 * 修改角色 加载操作权限
 */
var rolePerTree = function(){
	var roleId = $("#roleId").val();
	$.post('/paas/role/queryRolePerTree.action?roleId='+roleId,
			function(data){
			     $.fn.zTree.init($("#oprTree"), setting, eval(data["operPerTreeList"]));
//			     $.fn.zTree.init($("#dataTree"), setting, eval(data["dataPerTreeList"]));
//			     initTree("0");
			},
			"json"
	);
};
function initTree(operType){
	var type= $("#roleType").children('option:selected').val();//这就是selected的值
	var oprtreeObj = $.fn.zTree.getZTreeObj("oprTree");
	var treeObj = oprtreeObj.getNodes();
	if(type=="1"){ //普通管理员
		for(var i=0;i<treeObj.length;i++){ 
			if(operType == "1"){
				if(treeObj[i].id != "1"){
					oprtreeObj.setChkDisabled(treeObj[i],false);
					oprtreeObj.checkNode(treeObj[i],false);
			     }
			}
		}
	}else if(type=="2"){ //普通用户
		for(var i=0;i<treeObj.length;i++){
			var id=treeObj[i].id;
//			if(id=="4" || id=="5" || id=="6" || id=="7"){
			//普通用户可供选择的只有：系统告警和审计日志
			if(id=="2" || id=="5" || id=="6" || id=="7"){
//				oprtreeObj.setChkDisabled(treeObj[i],false);
				oprtreeObj.checkNode(treeObj[i],false);
				oprtreeObj.setChkDisabled(treeObj[i],true);
			}else if(id!="1"){
				oprtreeObj.setChkDisabled(treeObj[i],false);
			}
		}
	}
}

//人员分配
function toGrantUser(id,name){
	if(id == "1"){
		parent.alertWarn("超级管理员不能重新分配");
		return;
	}else{
		parent.openDialog("/paas/role/queryRoleUser.action?roleId="+ id+"&roleName="+name,"人员分配",580,320);
	}
}
/**
 * 给角色分配用户，加载用户信息
 */
var selectInit = function(){
	$('#searchable').multiselect2side({
		search: "待分配人员　搜索：" ,
		selectedPosition: 'right',   
		 moveOptions: false,   
		 labelsx: '待分配人员',   
		 labeldx: '已分配人员'
	});
	// 给角色标明操作类型（1-开发，2-测试，3-运维）
	var roleId = $("#roleId").val();
	$.ajax({
		type:"POST",
		dataType:"json",
		 url:"/paas/role/queryRoleOperTypeByRoleId.action",
         data:{
        	"roleId":roleId
         },
		success:function(result){
			if(result == "1"){
				$("#roleOperType").html("(开发)");
			} else if(result == "2"){
				$("#roleOperType").html("(测试)");
			} else if(result == "3"){
				$("#roleOperType").html("(运维)");
			} 
//			else {
//				// 角色未分配操作类型的时候，只显示未分配的用户给予选择
//				var searchAblems2Side = $("#searchablems2side__sx")[0];
//				for(var i = 0; i < searchAblems2Side.length; i++){
//					if(searchAblems2Side[i].innerHTML.indexOf("(") != -1){
//						searchAblems2Side[i].hidden = true;
//					}
//				}
//			}
		}
	});
};
/**
 * 给角色分配用户，保存操作
 */
function roleGrantUser(){
	if($("#roleOperType").html() == ""){
		parent.alertWarn("该角色还没确定操作类型，请先为角色授予所属的应用");
		return;
	}
	var userIdList = "";
	$('#searchable option:selected').each(function(){
		if(userIdList==""){
			userIdList +=$(this).val();
		}else{
			userIdList +=","+$(this).val();
		}
	});
	var roleId = $("#roleId").val();
	$.ajax({
		type:"POST",
		dataType:"json",
		 url:"/paas/role/roleGrantUser.action",
         data:{
        	"userIdList":userIdList,
        	"roleId":roleId
         },
		success:function(result){
			if(result['resultCode'] == 'success'){
				parent.alertSuccess();
				parent.closedl("roleGrantUser",result['resultMessage']);
			}
			else{
				parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	}); 
}

function close(){
	parent.close();
}
