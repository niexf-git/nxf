function openCreateUser(){ 
	parent.openDialog("/paas/jsp/systemmanager/usermanager/createUser.jsp","创建用户",600, 200);
	/* $('<iframe id="dialogIf" frameborder="0" src="${ctx}/jsp/usermanager/createUser.jsp"/>').dialog({
		title:"创建用户",
		autoOpen: true,
		draggable:false,
		resizable:false,
		modal:true,
		height: 300,
		position: 'center',
		width: 700}).width(700).height(300);  */
	/*var returnValue = window.showModalDialog("${ctx}/jsp/usermanager/createUser.jsp",new Object(), "dialogWidth=750px;dialogHeight=700px;status:no;scroll:no;");
	 if(returnValue == "close") {
		 reloadResult();
	 } */
}

function toUpdateUser(id){
	$.ajax({
		type:"POST",
		dataType:"json",
		data: {
			"id":id
	        },
		url:"/paas/user/queryCurrentUser.action",
		success:function(result){
			if(result['resultCode'] == 'success'){
				parent.openDialog("/paas/user/queryUserById.action?id="+id,"修改用户",600, 200);
				//parent.openDialog("${ctx}/userManager/toUpdateUser.action?userName="+userName,"修改用户信息",610,300);
			}
			else{
				parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	});
}

function grantRole(id,name){
	if(id == "1"){
		parent.alertWarn("超级管理员用户不能被重新授予角色");
		return;
	}else{
		parent.openDialog("/paas/jsp/systemmanager/usermanager/grantRole.jsp?id="+ id +"&loginName="+name,"用户授权",620,330);
	}
}

/**
 * 删除用户
 * @param userId
 */
function deleteUser(userId) {
	if(userId=="1"){
		parent.alertWarn("超级管理员用户不能被删除");
		return;
	}
	parent.alertConfirm("确认删除用户?",function(){
		$.ajax({
			type:"POST",
			dataType:"json",
			data: {
				"id":userId
	        },
			url:"/paas/user/deleteUser.action",
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
	var o = $.trim($('#loginName').val());
	if(o=="搜索用户"){
		o="";
	}
	$("#queryUserList").jqGrid()
		.setGridParam({page:1,rowNum:10,mtype:"POST",datatype: "json",url : '/paas/user/queryUserList.action'})
		.setGridParam({postData:{'loginName':o}})
		.trigger("reloadGrid");
}

$(function(){
	$("#loginName").keydown(function(e){
		if((e.keyCode || e.which)==13){
			reloadResult();
		}
	});
	//
	$('#queryUserList').jqGrid({
		url:'/paas/user/queryUserList.action',
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
	    colNames:['用户账号','真实姓名','角色名称','操作',''],
	   	colModel:[      
	   		{
	   			name : "loginName",
	   			index : "loginName",
	   			align : 'center',
	   		},
	   		{
	   			name : "userName",
	   			index : "userName",
	   			align : 'center',
	   		},
	   		{
	   			name : "roleName",
	   			index : "roleName",
	   			align : 'center',
	   		},
	   		{
	   			name:'id',
	   			loginName:'id',
	   			align : 'center',
	   			formatter:function(val,options,rowObject){
	   			var _v = "'" +val+ "'";
	   			var name="'" +rowObject["loginName"]+"'";
	   			var _link = '<a href="JavaScript:toUpdateUser(' +_v+')" style="color:#666">'+
	   							'<img src="../../../imgs/modify.png" width="22" height="22" border="none" title="修改" />'+
	   						'</a>&nbsp;&nbsp;&nbsp;'+
	   						'<a href="javaScript:deleteUser(' +_v+ ')" style="color:#666">'+
	   							'<img src="../../../imgs/delete.png" width="22" height="22" border="none" title="删除" />'+
	   						'</a>&nbsp;&nbsp;&nbsp;'+
	   						'<a href="javaScript:grantRole(' +_v+','+name+ ')" style="color:#666">'+
	   							'<img src="../../../imgs/grant.png" width="23" height="23" border="none" title="授予角色" /></a>';
	   			return _link;
	   		}},
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
	   	rowNum:10,
	   	rownumbers:true,
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
    	$("#queryUserList").setGridWidth($(window).width()*0.99);
});
});
function reloadResults(totalPage){
	var page = $('#queryUserList').getGridParam('page'); 
    var rows = $('#queryUserList').getGridParam('rows'); 
    var sidx = $('#queryUserList').getGridParam('sidx'); 
    var sord = $('#queryUserList').getGridParam('sord'); 
    jQuery("#queryUserList").jqGrid('setGridParam', {
        url : '/paas/user/queryUserList.action',
        page:totalPage,
        rows:rows,
        sidx:sidx,
        sord:sord,
    }).trigger("reloadGrid");
}

/**
 * 新增用户
 */
function createUserSumbit(){
	/** 用户账号 */
	var userLoginName = $.trim($("#loginName").val());
	if(userLoginName==""){
		parent.alertWarn("用户账号不能为空");
		$("#loginName").focus();
		return;
	}
    //验证规则：字母、数字、下划线组成，字母开头，4-16位
    var regLoginName = /^[a-zA-Z]\w{0,15}$/;
    if(!regLoginName.test(userLoginName)){
    	parent.alertWarn("用户账号只能包含英文字符、数字和下划线，并且只能以英文开头！");
		$("#loginName").focus();
		return;
    }
    $("#loginName").val(userLoginName);
    
    /** 真实姓名 */
    var userName = $.trim($("#userName").val());
	if(userName==""){
		parent.alertWarn("真实姓名不能为空");
		$("#userName").focus();
		return;
	}
	$("#userName").val(userName);
	
	/** 密码 */
	var password = $.trim($("#password").val());
	if(password==""){
		parent.alertWarn("密码不能为空");
		$("#password").focus();
		return;
	}
	
	var reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%\^\&\*\(\)])[\da-zA-Z!@#\$%\^\&\*\(\)]{8,}$/;
	if(password.match(reg) == null){
		parent.alertWarn("密码由8位以上，且同时包含大小写字母、数字、及特殊字符组成");
	    $("#password").focus();
	    return;
	}
	/*var reg = /^[0-9a-zA-Z]+$/;
	if(password.length<6 || password.match(reg)==null){
		parent.alertWarn("密码由5位以上数字或字母或两者组合");
	    $("#password").focus();
	    return;
	}*/
	
	/** 确认密码 */
	var rePassword = $.trim($("#rePassword").val());
	if(rePassword==""){
		parent.alertWarn("确认密码不能为空");
		$("#rePassword").focus();
		return;
	}
	if(rePassword != password){
		parent.alertWarn("确认密码必须和密码保持一致");
		$("#rePassword").focus();
		return;
	}
	
	$.ajax({
		type:"POST",
		dataType:"json",
		url:"/paas/user/addUser.action",
//	    data:$('#submitForm').serialize(),
		data:{
			"loginName" : userLoginName,
			"userName" : userName,
			"password" : $.md5(password+userLoginName) //密码由原来的后台MD5加密改为前台md5加密
		//	"password" : $.md5(password)
		},
		success:function(result){
			if(result['resultCode'] == 'success'){
				parent.alertSuccess();
				parent.closedl("createUser","");
			}
			else{
				parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	}); 
}

/**
 * 修改用户
 */
function updateUser(){
	/** 用户账号 */
	var userLoginName = $.trim($("#loginName").val());
	if(userLoginName==""){
		parent.alertWarn("用户账号不能为空");
		$("#loginName").focus();
		return;
	}
	//验证规则：字母、数字、下划线组成，字母开头，4-16位
    var regLoginName = /^[a-zA-Z]\w{0,15}$/;
    if(!regLoginName.test(userLoginName)){
    	parent.alertWarn("用户账号只能包含英文字符、数字和下划线，并且只能以英文开头！");
		$("#loginName").focus();
		return;
    }
    $("#loginName").val(userLoginName);
    
    /** 真实姓名 */
    var userName = $.trim($("#userName").val());
	if(userName==""){
		parent.alertWarn("真实姓名不能为空");
		$("#userName").focus();
		return;
	}
	$("#userName").val(userName);
	
	/** 密码 */
	var password = $.trim($("#password").val()); // 新密码
	var rePassword = $.trim($("#rePassword").val()); // 确认密码
	if(password==""){
		parent.alertWarn("新密码不能为空");
		$("#password").focus();
		return;
	}
	if(rePassword==""){
		parent.alertWarn("确认密码不能为空");
		$("#rePassword").focus();
		return;
	}
	if(password!=""){
		var reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%\^\&\*\(\)])[\da-zA-Z!@#\$%\^\&\*\(\)]{8,}$/;
		if(password.match(reg) == null){
			parent.alertWarn("密码由8位以上，且同时包含大小写字母、数字、及特殊字符组成");
		    $("#password").focus();
		    return;
		}
		
		if(rePassword != password){
			parent.alertWarn("确认密码必须和新密码保持一致");
			 $("#rePassword").focus();
			return;
		}
	}
	/*if(password!=""){
		var reg = /^[0-9a-zA-Z]+$/;
		if(password.length<6 || password.match(reg)==null){
			parent.alertWarn("密码由5位以上数字或字母或两者组合");
		    $("#password").focus();
		    return;
		}
		if(rePassword != password){
			parent.alertWarn("确认密码必须和新密码保持一致");
			 $("#rePassword").focus();
			return;
		}
	}*/
	
	$.ajax({
		type:"POST",
		dataType:"json",
		url:"/paas/user/updateUser.action",
		data:$('#submitForm').serialize(),
		data:{
			"id" : $("#id").val(),
			"loginName" : userLoginName,
			"userName" : userName,
			"password" : $.md5(password+userLoginName) //密码由原来的后台MD5加密改为前台md5加密
			//"password" : $.md5(password)
		},
		success:function(result){
			if(result['resultCode'] == 'success'){
				parent.alertSuccess();
				parent.closedl("updateUser",result['resultMessage']);
			}
			else{
				parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	}); 
}
//修改密码
function updatePassword(){
	
	var loginName = $("#loginName").val();
	
	//新密码
	var password = $.trim($("#password").val());
	//确认密码
	var rePassword = $.trim($("#rePassword").val());
	//旧密码
	var oldPassword = $.trim($("#oldPassword").val());
	
	if(password == ""){
		parent.alertWarn("新密码不能为空");
		 $("#password").focus();
		return;
	}
	if(rePassword == ""){
		parent.alertWarn("确认密码不能为空");
		 $("#rePassword").focus();
		return;
	}
	if(oldPassword == ""){
		parent.alertWarn("旧密码不能为空");
		 $("#oldPassword").focus();
		return;
	}
	/*var reg = /^[0-9a-zA-Z]+$/;
	if(password.length<6 || password.match(reg)==null){
		parent.alertWarn("密码由5位以上数字或字母或两者组合");
	    $("#password").focus();
	    return;
	}*/
	var reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%\^\&\*\(\)])[\da-zA-Z!@#\$%\^\&\*\(\)]{8,}$/;
	if(password.match(reg) == null){
		parent.alertWarn("密码由8位以上，且同时包含大小写字母、数字、及特殊字符组成");
	    $("#password").focus();
	    return;
	}
	if(rePassword != password){
		parent.alertWarn("确认密码必须和新密码保持一致");
		$("#rePassword").focus();
		return;
	}
	$.ajax({
		type:"POST",
		dataType:"json",
		url:"/paas/user/updatePassword.action",
//		data:$('#submitForm').serialize(),
		data:{
			"password" : $.md5(password+loginName), //密码由原来的后台MD5加密改为前台md5加密
			"oldPassword" : $.md5(oldPassword+loginName)
		},
		success:function(result){
			if(result['resultCode'] == 'success'){
				parent.alertSuccess();
				parent.closedl("updatePassword",result['resultMessage']);
			}
			else{
				parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	}); 
}
//用户授权
var setting = {
		check: {
			enable: true,
//			chkStyle: "radio", //默认是复选框，这里指定用单选按钮
			radioType: "level"
		},
		data: {
			simpleData: {
				enable: false
			}
		}
};
/**
 * 授予用户角色，加载角色信息
 */
var loadTree = function(){
	var id = $("#id").val();
	$.post('/paas/user/queryUserRoleTree.action?id='+id,
			function(data){
		     	$.fn.zTree.init($("#tree"), setting, eval(data));
		     	
		    	// 给用户标明操作类型（1-开发，2-测试，3-运维）
		    	$.ajax({
		    		type:"POST",
		    		dataType:"json",
		    		 url:"/paas/user/queryUserOperTypeByUserId.action",
		             data:{
		            	"id":id
		             },
		    		success:function(result){
		    			if(result == "1"){
		    				$("#userOperType").html("(开发)");
//		    				hideNoneOperType();
		    				
//		    				var roleTree = $("#tree")[0];
//		    				for(var i = 0; i < roleTree.childElementCount; i++){
//		    					var roleTreeChildNode = roleTree.childNodes[i].innerText;
//		    					if(roleTreeChildNode.indexOf("(开发)") == -1){ // 不包含
//		    						$("#tree_"+(i+1)+".level0").hide();
//		    					}
//		    				}
		    				showTheSameOperType(result);
		    				
		    			} else if(result == "2"){
		    				$("#userOperType").html("(测试)");
		    				
//		    				var roleTree = $("#tree")[0];
//		    				for(var i = 0; i < roleTree.childElementCount; i++){
//		    					var roleTreeChildNode = roleTree.childNodes[i].innerText;
//		    					if(roleTreeChildNode.indexOf("(测试)") == -1){
//		    						$("#tree_"+(i+1)+".level0").hide();
//		    					}
//		    				}
		    				showTheSameOperType(result);
		    				
//		    				hideNoneOperType();
		    			} else if(result == "3"){
		    				$("#userOperType").html("(运维)");
//		    				hideNoneOperType();
		    				
//		    				var roleTree = $("#tree")[0];
//		    				for(var i = 0; i < roleTree.childElementCount; i++){
//		    					var roleTreeChildNode = roleTree.childNodes[i].innerText;
//		    					if(roleTreeChildNode.indexOf("(运维)") == -1){
//		    						$("#tree_"+(i+1)+".level0").hide();
//		    					}
//		    				}
		    				showTheSameOperType(result);
		    			} 
//		    			else {
//		    				// 用户未分配操作类型的时候，只显示未分配的角色给予选择
//		    			 	var roleTree = $("#tree")[0];
//		    				for(var i = 0; i < roleTree.childElementCount; i++){
//		    					var roleTreeChildNode = roleTree.childNodes[i].innerText;
//		    					if(roleTreeChildNode.indexOf("(") != -1){
//		    						$("#tree_"+(i+1)+".level0").hide();
//		    					}
//		    				}
//		    			}
		    		}
		    	});
			},
			"json"
	);

};

/**
 * 筛选和当前用户一样操作类型的角色显示
 */
function showTheSameOperType(operType){
 	var roleTree = $("#tree")[0];
	for(var i = 0; i < roleTree.childElementCount; i++){
		var roleTreeChildNode = roleTree.childNodes[i].innerText;
		
		if(operType == "1"){
			if(roleTreeChildNode.indexOf("(开发)") == -1){ // 不包含
				$("#tree_"+(i+1)+".level0").hide();
			}
		} else if(operType == "2"){
			if(roleTreeChildNode.indexOf("(测试)") == -1){
				$("#tree_"+(i+1)+".level0").hide();
			}
		} else if(operType == "3"){
			if(roleTreeChildNode.indexOf("(运维)") == -1){
				$("#tree_"+(i+1)+".level0").hide();
			}
		}
		
//		if(roleTreeChildNode.indexOf("(开发)") == -1){ // 不包含
//			$("#tree_"+(i+1)+".level0").hide();
//		} else if(roleTreeChildNode.indexOf("(测试)") == -1){
//			$("#tree_"+(i+1)+".level0").hide();
//		} else if(roleTreeChildNode.indexOf("(运维)") == -1){
//			$("#tree_"+(i+1)+".level0").hide();
//		}
	}
}

function hideNoneOperType(){
 	// 还没有操作类型（开发/测试）的角色不显示
 	var roleTree = $("#tree")[0];
	for(var i = 0; i < roleTree.childElementCount; i++){
		var roleTreeChildNode = roleTree.childNodes[i].innerText;
		if(roleTreeChildNode.indexOf("(") == -1){ // 不包含左括号的隐藏
			$("#tree_"+(i+1)+".level0").hide();
		}
	}
}

/**
 * 授予用户角色
 * 1.系统只有admin用户和普通用户,admin给普通用户授予角色；
 * 2.一个用户可以授予多个角色；
 */
function updateUserRole(){
	var treeObj = $.fn.zTree.getZTreeObj("tree");
	var nodes = treeObj.getCheckedNodes(true);
	//一个用户只能被授予一个角色
//	if(nodes.length==0){
//		parent.alertWarn("请为用户选择一个角色！");
//		return;
//	}
	for(var i=0; i<nodes.length; i++) {
		if(nodes[i].name.indexOf("(") == -1){ // 不包含左括号
			parent.alertWarn("您选中了还没确定操作类型的角色，请为用户授予确定操作类型的角色");
			return;
		}
	}
	 var selectNodeIds = "";
	 for(var i=0;i<nodes.length;i++) {
		 if(i==0){
			 selectNodeIds += nodes[i].id;
		 }else{
			 selectNodeIds += "," + nodes[i].id;
		 }
	 }
	$.ajax({
		type:"POST",
		dataType:"json",
		 url:"/paas/user/updateUserRole.action",
         data:{
        	"selectNode":selectNodeIds,
			"id":$("#id").val()
         },
		success:function(result){
			if(result['resultCode'] == 'success'){
				parent.alertSuccess();
				parent.closedl("updateUserRole",result['resultMessage']);
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