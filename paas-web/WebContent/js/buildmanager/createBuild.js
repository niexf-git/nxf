/**
 *  创建、修改构建js
 */

var url;
var type;
var repositoryCount;
var commandCount;

//初始化页面
$(function(){
	$("#buildName").focus();
	banBackSpace();
	var buildId = $("#buildId").val();
	//构建类型 1-svn，2-git
	type = $.trim($("#type").val());
	
	if($("#repositoryCount").val() == undefined){
		repositoryCount = 0;
	}else{
		repositoryCount = Number($("#repositoryCount").val());
	}
	if($("#commandCount").val() == undefined){
		commandCount = 0;
	}else{
		commandCount = Number($("#commandCount").val());
	}
	
	//修改构建
	if(buildId){
		$("#buildName").attr('readonly','readonly');
		url = "/paas/build/modifyBuild.action";
		if(type == "2"){//git
			for(var i=0;i<repositoryCount;i++){
				//由于git api有连接次数限制，修改的时候就不去后台调用，只在前台下拉框展示当前的分支
				//queryBranches(i);
				var branchName = $.trim($("#branchName"+i).val());//当前分支，从隐藏域中取值
				$("#selectBranchName"+i).append("<option value='" + branchName + "' selected>" + branchName + "</option>");
				$("#selectBranchName"+i).attr('disabled','disabled');
			}
		}
	}else{//创建构建
		url = "/paas/build/createBuild.action";
		//初始化创建页面时，不自动填充用户名密码
		if(type == "1"){
			$("#storePath0").val(" ");
		}else if(type == "2"){
			$("#name0").val(" ");
		}
	}
});

//添加一个svn库
function addRepository(){
	if(type == "1"){
		$("#addRepositoryDiv").append('<div class="code-main" id="repositoryDiv'+repositoryCount+'">'+
				'<input name="buildEntity.repositoryInfo['+repositoryCount+'].id" id="id'+repositoryCount+'" type="hidden" />' +
				'<ul>' +
					'<li style="width:250px;"><span style="margin-left:20px;"><strong>*</strong>svn地址：</span><input name="buildEntity.repositoryInfo['+repositoryCount+'].url" id="url'+repositoryCount+'" type="text"  style="width:120px;" onblur="getRepositoryInfo('+repositoryCount+');verifyRepositoryInfo('+repositoryCount+')" onmouseover="toolTips(this);"/></li>' +
					'<li><span><strong>*</strong>用户名：</span><input name="buildEntity.repositoryInfo['+repositoryCount+'].name" id="name'+repositoryCount+'" type="text" onblur="verifyRepositoryInfo('+repositoryCount+')"/></li>'+
					'<li style="width:250px;"><span style="width:90px;"><strong>*</strong>代码目录：</span><input name="buildEntity.repositoryInfo['+repositoryCount+'].storePath" id="storePath'+repositoryCount+'" type="text"  style="width:120px;" onblur="checkCodeStorePath('+repositoryCount+');"/></li>'+
					'<li style="width:200px;"><span><strong>*</strong>密码：</span><input name="buildEntity.repositoryInfo['+repositoryCount+'].password" id="password'+repositoryCount+'" type="password" onblur="verifyRepositoryInfo('+repositoryCount+')"/></li>'+
					'<li style="width:68px;"><input name="" type="button" value="删除" onclick="deleteRepository('+repositoryCount+')" style="width:55px; height:22px; float:left; background:#666666; border:none; color:#fff; border-radius:5px;" /></li>'+
				'</ul>'+
				'</div>');
	}else if(type == "2"){
		$("#addRepositoryDiv").append('<div class="svn-code-main" id="repositoryDiv'+repositoryCount+'">'+
				'<input name="buildEntity.repositoryInfo['+repositoryCount+'].id" id="id'+repositoryCount+'" type="hidden" />' +
				'<input name="buildEntity.repositoryInfo['+repositoryCount+'].branchName" id="branchName'+repositoryCount+'" type="hidden" />' +
				'<input id="queryBranchesFlag'+repositoryCount+'" type="hidden" />' +
				'<input id="verifyRepositoryInfoFlag'+repositoryCount+'" type="hidden" />' +
				'<ul>' +
					'<li style="width:450px;"><span style="margin-left:17px;"><strong>*</strong>git仓库：</span><input name="buildEntity.repositoryInfo['+repositoryCount+'].url" id="url'+repositoryCount+'" type="text" style="width:333px;" onblur="getRepositoryInfo('+repositoryCount+');verifyRepositoryInfo('+repositoryCount+')" onmouseover="toolTips(this);"/></li>'+
	                '<li style="width:250px;"><span style="width:90px;"><strong>*</strong>代码目录：</span><input name="buildEntity.repositoryInfo['+repositoryCount+'].storePath" id="storePath'+repositoryCount+'" type="text" style="width:120px;" placeholder="代码下载到此目录" onblur="checkCodeStorePath('+repositoryCount+');"/></li>'+
	                '<li><span><strong>*</strong>用户名：</span><input name="buildEntity.repositoryInfo['+repositoryCount+'].name" id="name'+repositoryCount+'" type="text" onblur="verifyRepositoryInfo('+repositoryCount+')"/></li>'+
	                '<li style="width:250px;"><span style="width:90px;"><strong>*</strong>分支名称：</span><select id="selectBranchName'+repositoryCount+'" style="width:120px;"><option value="">请选择分支名称</option></select></li>'+
	                '<li style="width:200px;"><span><strong>*</strong>密码：</span><input name="buildEntity.repositoryInfo['+repositoryCount+'].password" id="password'+repositoryCount+'" type="password" onblur="verifyRepositoryInfo('+repositoryCount+')" autocomplete="off"/></li>'+
	                '<li style="width:68px;"><input name="" type="button" value="删除" onclick="deleteRepository('+repositoryCount+')" style="width:55px; height:22px; float:left; background:#666666; border:none; color:#fff; border-radius:5px;" /></li>'+
				'</ul>'+
		'</div>');
	}else{
		parent.parent.alertError("未知的构建类型！");
	}
	repositoryCount++;
}
//删除一个svn库
function deleteRepository(index){
	$("#repositoryDiv"+index).hide();
	$("#id"+index).attr("disabled","disabled");
	$("#url"+index).attr("disabled","disabled");
	$("#name"+index).attr("disabled","disabled");
	$("#storePath"+index).attr("disabled","disabled");
	$("#password"+index).attr("disabled","disabled");
	
	if(type == "2"){//git
		$("#branchName"+index).attr("disabled","disabled");
		$("#selectBranchName"+index).attr("disabled","disabled");
		$("#flag"+index).attr("disabled","disabled");
	}
}
//根据url地址获取曾用过的代码库用户名和密码
function getRepositoryInfo(index){
	var url = $.trim($("#url"+index).val());
	if(url != "" && url != null){
		var paramData = "repositoryInfo.url="+url+"&repositoryInfo.type="+type;
		$.ajax({
			type : "POST",
			async: false,
			dataType : "json",
			data : paramData,
			url : '/paas/build/fillRepository.action',
			success : function(result) {
				if (result['resultCode'] == 'success') {
					var repositoryInfo = JSON.parse(result['resultMessage']);
					if(repositoryInfo && repositoryInfo != "null"){
						$("#id"+index).val(repositoryInfo['id']);
						$("#name"+index).val(repositoryInfo['name']);
						$("#password"+index).val(repositoryInfo['password']);
					}
				} else {
					parent.closeAlert();
					parent.alertError(result['resultCode'],result['resultMessage']);
			    }
			}
		});
	}
}
//截取svn地址最后一个/后面的字符串作为代码目录
//已经不使用
function getStorePath(index){
	var url = $.trim($("#url"+index).val());
	if(url != "" && url != null){
		var v_arr = url.split('/');
		var last = v_arr[v_arr.length-1];
		//url地址最后一部分是否存在.,如果存在就截取.之前的部分作为代码目录
		if(last.indexOf('.') > -1){
			last = last.substring(0,last.indexOf('.'));
		}
		$("#storePath"+index).val(last);
		if(index == 0){
			$("#dockerFilePath").val(last+"/Dockerfile");
		}
	}else{
		$("#storePath"+index).val("");
	}
}
//验证输入的代码库用户名和密码
function verifyRepositoryInfo(index){
	var url = $.trim($("#url"+index).val());
	var userName = $.trim($("#name"+index).val());
	var password = $.trim($("#password"+index).val());
	var flag = $.trim($("#verifyRepositoryInfoFlag"+index).val());//获取标识位
	//用url+用户名判断是否需要调用后台方法查询(需唯一)，svn调用此方法未报错（暂时没有对svn做处理）
	if(flag == $.sha1(url + userName + password)){
		flag = "1";//不用调用后台程序去查询分支
	}
	if(url !="" && userName !="" && password !="" && flag !="1"){//svn类型flag不会等于1，标识元素不存在也不会报错，所以这里没有区分处理也不会有问题
		var paramData = "repositoryInfo.url="+url+"&repositoryInfo.name="+userName+"&repositoryInfo.password="+password+"&repositoryInfo.type="+type;
		$.ajax({
			type : "POST",
			async: false,
			dataType : "json",
			data : paramData,
			url : '/paas/build/verifyRepositoryCertificate.action',
			success : function(result) {
				if (result['resultCode'] == 'success') {
					var id = result['resultMessage'];
					if(id && id != ""){
						$("#id"+index).val(id);
					}
					
					if(type == "2"){//git
						$("#verifyRepositoryInfoFlag"+index).val($.sha1(url + userName + password));
						queryBranches(index);
					}
				} else {
					$("#id"+index).val("");
					$("#verifyRepositoryInfoFlag"+index).val("");
					parent.closeAlert();
					parent.alertError(result['resultCode'],result['resultMessage']);
			    }
			}
		});
	}
}
//查询分支名称
function queryBranches(index){
	var url = $.trim($("#url"+index).val());
	var userName = $.trim($("#name"+index).val());
	var password = $.trim($("#password"+index).val());
	var branchName = $.trim($("#branchName"+index).val());//当前分支，从隐藏域中取值
	var flag = $.trim($("#queryBranchesFlag"+index).val());//获取标识位
	//用url+用户名判断是否需要调用后台方法查询(需唯一)
	if(flag == (url + userName)){
		flag = "1";//不用调用后台程序去查询分支
	}
	if(url !="" && userName !="" && password !="" && flag !="1"){
		var paramData = "repositoryInfo.url="+url+"&repositoryInfo.name="+userName+"&repositoryInfo.password="+password+"&repositoryInfo.type="+type;
		$.ajax({
			type : "POST",
			dataType : "json",
			data : paramData,
			url : '/paas/build/queryBranches.action',
			success : function(result) {
				if (result['resultCode'] == 'success') {
					var branches = JSON.parse(result['resultMessage']);
					if(branches == null){
						parent.alertWarn("没有查询到可用的分支！");
						return;
					}
					//清空分支名称下拉框
					document.getElementById("selectBranchName"+index).innerHTML = "";
					$("#selectBranchName"+index).append("<option value = ''>请选择分支名称</option>");
					
					//循环添加所有分支选项
					$.each(branches, function(i, val) {
						$("#selectBranchName"+index).append("<option value='" + val['value'] + "'>" + val['text'] + "</option>");
						if(val['value'] == branchName){//选中当前的分支
							$("#selectBranchName"+index).val(branchName);
						}
					});
					$("#queryBranchesFlag"+index).val(url + userName);
				} else {
					$("#queryBranchesFlag"+index).val("");
					parent.closeAlert();
					parent.alertError(result['resultCode'],result['resultMessage']);
			    }
			}
		});
	}
}
//添加一个执行命令
function addExecuteCommand(){
	$("#executeCommandDiv").append('<div class="step-main" id="commandDiv'+commandCount+'">'+
			'<div class="name70"><strong>*</strong>执行命令：</div><textarea name="buildEntity.executeCommand" id="command'+commandCount+'" placeholder="例如：ant  -f  test_code/build.xml"></textarea>' +
			'<div class="structure-step-delete"><input name="" type="button" value="删除" onclick="deleteExecuteCommand('+commandCount+');"/></div>' +
		    '</div>'
	);
	commandCount++;
}
//删除一个执行命令
function deleteExecuteCommand(index){
	$("#commandDiv"+index).hide();
	$("#command"+index).attr("disabled","disabled");
}

//保存构建
var butclick=false;
function saveBuild(){
	//校验参数
	var result = validateParameter();
	if(!result){
		return ;
	}
	if(!butclick){
		butclick=true;
		setTimeout(function () { butclick = false }, 5000);
		$.ajax({
			type : "POST",
			dataType : "json",
			data : $('#buildForm').serialize(),
			url : url,
			success : function(result) {
				if (result['resultCode'] == 'success') {
					parent.alertConfirm("操作成功！是否立即执行构建？",function(){
						executeBuild(result['resultMessage']);
					},function(){
						$("#mainIframe",window.parent.document)[0].contentWindow.document.getElementById("refresh").click("return false");
						parent.close();
					});
				} else {
					parent.alertError(result['resultCode'],result['resultMessage']);
			    }
			}
		});
	}
}

//页面输入参数校验
function validateParameter(){
	parent.closeAlert();
	//构建名称
	var buildName = $("#buildName").val();
	if(!buildName){
		parent.alertWarn('构建名称不能为空!');
		$("#buildName").focus();//不起作用
		return false;
	}else{
		if(!/^[A-Za-z][A-Za-z0-9]*$/.test(buildName) || buildName.length > 16){
			parent.alertWarn('构建名称由英文字母开头，数字、英文组合，并且最长16个字符！');
			return false;
		}
	}
	//Dockerfile文件路径
	var dockerFilePath = $.trim($("#dockerFilePath").val());
	var flag = false;
	//var isStorePath = false;
	//var effectiveCount = 0;//有效的svn库
	//代码库
	for(var i=0; i<repositoryCount; i++){
		var v_url = $("#url"+i);
		//隐藏的div中的元素不需要校验
		if(v_url.attr("disabled") == undefined){
			//url地址
			if($.trim(v_url.val()) == ""){
				parent.alertWarn('代码库地址不能为空!');
				return false;
			}
			//用户名
			var v_name = $("#name"+i).val();
			if($.trim(v_name) == ""){
				parent.alertWarn('用户名不能为空!');
				return false;
			}
			//密码
			var v_password = $("#password"+i).val();
			if($.trim(v_password) == ""){
				parent.alertWarn('密码不能为空!');
				return false;
			}
			//git分支
			if(type == "2"){
				$("#branchName"+i).val($("#selectBranchName"+i).val());
				var v_branchName = $("#branchName"+i).val();
				if($.trim(v_branchName) == ""){
					parent.alertWarn('请选择分支名称!');
					return false;
				}
			}
			//防止直接点击确定按钮导致没有进行最后修改后的验证
			verifyRepositoryInfo(i);
			//url id
			var v_id = $("#id"+i).val();
			if($.trim(v_id) == ""){
				parent.closeAlert();
				parent.alertWarn('代码地址:'+v_url.val()+'验证没有通过,请输入正确的代码库信息!');
				return false;
			}
			//代码目录
			var v_storePath = $.trim($("#storePath"+i).val());
			if(v_storePath == ""){
				parent.alertWarn('代码目录不能为空!');
				return false;
			}
			if(v_storePath.charAt(v_storePath.length-1) != "/"){
				v_storePath = v_storePath + "/";
			}
			
			var v_dockerFilePath = dockerFilePath;
			if(v_dockerFilePath.charAt(v_dockerFilePath.length-1) != "/"){
				v_dockerFilePath = v_dockerFilePath + "/";
			}
			//Dockerfile文件位置
			if(v_dockerFilePath.indexOf(v_storePath) == 0){
				flag = true;
			}

			//effectiveCount++;
			var checkResult = checkCodeStorePath(i);
			if(checkResult == false){
				return false;
			}
		}
	}
	if(dockerFilePath == ""){
		parent.alertWarn('Dockerfile文件路径不能为空!');
		return false;
	}
	if(!flag){
		parent.alertWarn("Dockerfile文件必须放到代码工程目录中！");
		return false;
	}
//	if(effectiveCount > 1 && isStorePath){
//		parent.alertWarn("多个svn库信息时，代码目录不能存在空的 ！");
//		return false;
//	}
	//构建步骤
	for(var i=0; i<commandCount; i++){
		var v_command = $("#command"+i);
		if(v_command.attr("disabled") == undefined){
			if($.trim(v_command.val()) == ""){
				parent.alertWarn('执行命令不能为空!');
				return false;
			}
		}
	}
	return true;
}

//执行构建
function executeBuild(id){
	$.ajax({
		type : "POST",
		dataType : "json",
		data: {
			"buildId" : id.toString()
		},
		url : '/paas/build/executeBuild.action',
		success : function(result) {
			if (result['resultCode'] == 'success') {
				$("#mainIframe",window.parent.document)[0].contentWindow.document.getElementById("refresh").click("return false");
				parent.close();
			} else {
				parent.alertError(result['resultCode'], result['resultMessage']);
			}
		}
	});
}

//当鼠标移动到输入框上方时提示输入框里的内容
function toolTips(element){
	element.title = element.value;
}

//代码存放目录不能存在包含关系
function checkCodeStorePath(index){
	//只有一个svn库直接返回
	if(repositoryCount == 1){
		return true;
	}else{
		var currentStorePath = $.trim($("#storePath"+index).val()) ;
		if(currentStorePath.charAt(currentStorePath.length-1) != "/"){
			currentStorePath = currentStorePath  + "/";
		}
		for(var i=0; i<repositoryCount; i++){
			var _storePath = $("#storePath"+i);
			if(i!= index && _storePath.attr("disabled") == undefined){//排除自己和隐藏的
				var otherStorePath = $.trim(_storePath.val()) ;
				if(otherStorePath.charAt(otherStorePath.length-1) != "/"){
					otherStorePath = otherStorePath + "/";
				}
				if(otherStorePath.indexOf(currentStorePath)==0 || currentStorePath.indexOf(otherStorePath)==0){
					parent.closeAlert();
					parent.alertWarn("代码目录之间不能存在一个目录包含另外一个目录！");
					return false;
				}
			}
		}
	}
}