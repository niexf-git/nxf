<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/dailog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
	var flag = false;
	$(function(){
		var tips = '<%=request.getParameter("tips")%>';
		var appPermissionId = '<%=session.getAttribute("appPermissionId")%>';
		var appPermissionNames ='<%=session.getAttribute("appPermissionName")%>';
		var appNames = appPermissionNames.split(",");
		var opertype = '<%=session.getAttribute("opertype")%>';
		var opertypes = opertype.split(",");
		var appIds = appPermissionId.split(",");
		$("#typeName").empty(); 
		var text = $("#appText",window.parent.document).html();
		document.getElementById("deveName").options.add(new Option("全部", 0, true, true));
		document.getElementById("typeName").options.add(new Option("全部", 0, true, true));
		$.each(appNames,function(i,v){
			if(text == v){
				document.getElementById("deveName").options.add(new Option(v, appIds[i], true, true));
			}else{
				document.getElementById("deveName").options.add(new Option(v, appIds[i], false, false));
			}
		});
		/* if(appNames.length == 1){
			$("#appText")[0].options[1].selected = true;
		} */
		if($("#deveName")[0].selectedOptions[0].text == "全部"){
			$.each(opertypes,function(i,v){
				if(text == v){
					document.getElementById("typeName").options.add(new Option(v==1?"开发":v==2?"测试":v==3?"运维":"全部", v, true, true));
				}else{
					document.getElementById("typeName").options.add(new Option(v==1?"开发":v==2?"测试":v==3?"运维":"全部", v, false, false));
				}
			});
		}
		deveNameChange();
		if(tips != '' && tips != null && tips.indexOf("请选择")>-1){
			$("table tr:first").show();
			$("#tipsText").text(tips);
			flag = true;
		}else if(tips != '' && tips != null && tips.indexOf("_")>-1){
			if(tips.split("_")[2] == 2){
				$.ajax({
					url:'/paas/privateImage/queryPrivateImageInfo.action',
					data:{id:tips.split("_")[0],privateImageType:1,appName:tips.split("_")[1]},
					success:function(data,v,i){
						var opertype = i.responseText;
						var opertypes = opertype.split(",");
						$("#typeName").empty(); 
						$.each(opertypes,function(i,v){
							if(v != ''){
								var type = v.indexOf("1")>-1?"开发":v.indexOf("2")>-1?"测试":v.indexOf("3")>-1?"运维":"全部";
								if(type == text){
									document.getElementById("typeName").options.add(new Option(type,type=="开发"?"1":type=="测试"?"2":type=="运维"?"3":"0",true,true));
								}else{
									document.getElementById("typeName").options.add(new Option(type,type=="开发"?"1":type=="测试"?"2":type=="运维"?"3":"0",false,false));
								}
							}
						});
						if(opertypes.length == 1){
							$("#submitBut")[0].click();
						}
					}
					
				});
				var  deveNameOptions = document.getElementById("deveName").options;
				for (var i = 0; i < deveNameOptions.length; i++) {
					if(deveNameOptions[i].text == tips.split("_")[1]){
						deveNameOptions[i].selected = true;
						 document.getElementById("deveName").disabled=true;
						 flag = true;
						break;
					}
				}
			}else if(tips.split("_")[2] == 3){
				var  deveNameOptions = document.getElementById("deveName").options;
				for (var i = 0; i < deveNameOptions.length; i++) {
					if(deveNameOptions[i].text == tips.split("_")[1]){
						deveNameOptions[i].selected = true;
						 document.getElementById("deveName").disabled=true;
						 flag = true;
						break;
					}
				}
				$.ajax({
					url:'/paas/privateImage/queryPrivateImageInfo.action',
					data:{status:tips.split("_")[0],privateImageType:2},
					success:function(data,v,i){
						var opertype = i.responseText;
						var opertypes = opertype.split(",");
						$("#typeName").empty(); 
						$.each(opertypes,function(i,v){
							if(v != ''){
								var type = v.indexOf("1")>-1?"开发":v.indexOf("2")>-1?"测试":v.indexOf("3")>-1?"运维":"全部";
								if(type == text){
									document.getElementById("typeName").options.add(new Option(type,type=="开发"?"1":type=="测试"?"2":type=="运维"?"3":"0",true,true));
								}else{
									document.getElementById("typeName").options.add(new Option(type,type=="开发"?"1":type=="测试"?"2":type=="运维"?"3":"0",false,false));
								}
							}
						});
						if(opertypes.length == 1){
							$("#submitBut")[0].click();
						}
					}
					
				});
			}else{
				 flag = true;
			}
			
		}else if(tips != '' && tips != null && tips.indexOf("_") == -1 && tips.indexOf("请选择") == -1){
			flag = true;
		}
	});
	
	function deveNameChange(){
		var appId = $("#deveName").val();
		$.ajax({
			url:'/paas/user/queryAppType.action',
			data:{appId:appId,type:1},
			success:function(data,v,i){
				var opertype = i.responseText;
				var opertypes = opertype.split(",");
				$("#typeName").empty(); 
				if(opertypes.length>1){
					document.getElementById("typeName").options.add(new Option("全部", 0, true, true));
				}
				var text = $("#operTypeText",window.parent.document).html();
				$.each(opertypes,function(i,v){
					if(v != ''){
						var type = v==1?"开发":v==2?"测试":v==3?"运维":"全部";
						if(type == text){
							document.getElementById("typeName").options.add(new Option(type,v,true,true));
						}else{
							document.getElementById("typeName").options.add(new Option(type,v,false,false));
						}
					}
				});
			}
			
		});
	}

	function onSubmit(){
		var appId = $("#deveName").val();
		var appName = $("#deveName")[0].selectedOptions[0].text;
		var typeId = $("#typeName").val();
		$.ajax({
			type : "POST",
			dataType : "json",
			url:'/paas/user/queryAppType.action',
			data:{appId:appId,type:2,appId:appId,typeName:typeId,deveName:appName},
			success:function(result){
				if(result['resultCode']=='success'){
						$("#appText",window.parent.document).html(appName==""?"全部":appName);
						$("#operTypeText",window.parent.document).html(typeId==1?"开发":typeId==2?"测试":typeId==3?"运维":"全部");
						
						//所有列表页面公用查询按钮ID
						var searchButtonId = '#search';
						var switchAppInfoMsg = '#switchAppInfoMsg'; 
						
						//判断当前jsp中是否包含公用ID的按钮
						if($(searchButtonId,window.parent.parent[0].document).length>0){
							$(searchButtonId,window.parent.parent[0].document)[0].click();//执行当前子页面的查询
						}
						if($(switchAppInfoMsg,window.parent.parent[0].document).length>0){
 							$(switchAppInfoMsg,window.parent.parent[0].document)[0].textContent = "";
						}
						//当前子页的path路径
						var currentPagePathName = window.parent.parent[0].document.location.pathname;
						if(currentPagePathName.indexOf('appserviceList.jsp')>-1 && flag){//服务管理
							$("#createAppService",window.parent.parent[0].document)[0].click();
						}else if(currentPagePathName.indexOf('ipaasserviceList.jsp')>-1 && flag){//基础服务
							$("#createIpaasService",window.parent.parent[0].document)[0].click();
						}else if(currentPagePathName.indexOf('buildList.jsp')>-1 && flag){//构建管理
							$("#createBuildId",window.parent.parent[0].document)[0].click();
						}else if(currentPagePathName.indexOf('listAlarm.jsp')>-1){
							var appFlag = appName != '全部'?true:false;
							var operFlag = typeId != '0'?true:false;
							var tabFlag = appName != '全部' && typeId != '0' ? true :false ;
							$("#appNamess",window.parent.parent[0].document)[0].hidden = appFlag;
							$("#operTypess",window.parent.parent[0].document)[0].hidden = operFlag;
							$("#tables",window.parent.parent[0].document)[0].hidden = tabFlag;
						}else if(currentPagePathName.indexOf('buildRecordList.jsp')>-1){
							window.parent[0].document.location = $('.main-left a',window.parent.document)[2].href;
						}else if(currentPagePathName.indexOf('listPrivateImageVersion.jsp')>-1){
							$("#privateImage",window.parent.parent[0].document)[0].click();
						}
						
						//【多集群改造-应用服务】切换应用和操作类型之后刷新集群
						$.ajax({
							cache:false,
							type : "get",
							dataType : "json",
							url : '/paas/appservice/queryClusterList.action',
							async: false,
							success : function(result) {			
								if(result['result'] == null){
									parent.alertWarn("没有可用的集群！");
									return;
								}
								//跨页面清空下拉列表的值（switchAppInfo.jsp -> appserviceList.jsp）
								$("#cluster_name",window.parent.parent[0].document).empty();
								//跨页面赋予默认值
								$("#cluster_name",window.parent.parent[0].document).append("<option value=''selected='selected'>请选择</option>");
								$.each(result['result'], function(i, val) {
									$("#cluster_name",window.parent.parent[0].document).append("<option value='" + val['id'] +"'>" + val['name'] + "</option>");
								});
							}
						});
						
						parent.close();
				}else{
					parent.alertError(result['resultCode'],result['resultMessage']);
				}
			}
			
		}); 
	}
	
</script>
</head>
<body>
	<div class="dialog">
		<div class="dialog-configure">
			<table width="500" border="0" cellspacing="0" cellpadding="0">
				<tr style="display: none;">
					<td colspan="4"><img src="${ctx}/imgs/tips.png" width="14" height="14" style="margin-right:5px;" /><span id="tipsText" style="color: red;"></span></td>
				</tr>
				<tr>
					<td width="70"><span class="name70">应用名称：</span></td>
					<td width="190"><select id="deveName" onchange="deveNameChange()" name="deveName"></select></td>
					<td width="65" style="display: none;">操作类型：</td>
					<td width="175" style="display: none;"><select id="typeName" name="typeName"></select></td> 
				</tr>
			</table>
			<div class="bottom">
				<input name="" type="button" onclick="onSubmit()" id="submitBut" value="保存" />
			</div>
		</div>
	</div>
</body>
</html>