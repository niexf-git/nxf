<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<html>
<head>
<style>
html,body{ margin:0px; padding:0px; word-wrap: break-word; word-break:break-all;}
</style>
<title></title>
</head>

	<TABLE width="100%" height="510" border=0 align=left cellpadding="0" cellspacing="0" style="border: 1px solid #dadada;" >
		<TR>
			<TD id="hideTree" status="open" width="19%" align=left valign=top  style="border-right: 1px solid #dadada; position:fixed;">
				<iframe	name="left" id="leftMain" src="asyncTree.jsp" frameborder="0"
					scrolling="no"  height="510" allowtransparency="true" width="100%"></iframe>
			</TD>
			<TD width="81%" align=left valign=top style=" margin-left:210px;">
				<iframe	name="right" id="rightMain" src="rightMain.jsp" frameborder="0" scrolling="auto"
					 height="510" allowtransparency="true" width="100%"></iframe>
			<div id="show" style="position:absolute; width:23px; height:207px; top:240px;"><img id="ztree-left" src="${ctx}/imgs/ztree-left.png" width="9" height="14" style = "display:none;"/></div>
			</TD>
		</TR>
	</TABLE>
<script type="text/javascript">
 	//普通用户监控运维菜单
 	$.ajax({
		type : "GET",
		contentType : "application/json;utf-8",
		dataType : "json",
		url : "/paas/permission/queryUserRoleAndOperType.action",
		success : function(result) {
			if (result['resultMessage'] == 'success') {
				var obj = JSON.parse(result.data);
				var loginName = obj.loginName;
				if(loginName!="admin"){
					$("#hideTree").find("iframe").attr("src","/paas/jsp/newmonitor/newAsyncTree.jsp");
					$("#rightMain").attr("src","/paas/jsp/newmonitor/newRightMain.jsp");
				}
			}
		}
	});
	//左边的树展开功能
	function openTree(){
		var obj = $('#hideTree');
   		var status = obj.attr("status");
   		if(status == 'close'){
   	   		$('#ztree-left').show();
   			$('#ztree-left').click(function(){
   				obj.show();
   				$('#show').unbind('hover');
   				$('#ztree-left').hide();
   			});
   		}
	}
</script>
</html>
