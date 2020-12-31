<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/ztree/demo.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/ztree/zTreeStyle.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript">
	var firstClick = true;
	var zTreeObj;
	var setting = {
		async : {
			enable : true,
			url : "${ctx}/monitoroperation/loadTree.action",
			autoParam : [ "id", "level", "dataCenterType", "clusterType",
					"serviceType" ]
		},
		view : {
			dblClickExpand : false,
			fontCss : setFontCss
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		// 回调函数
		callback : {
			onClick : function(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("asyncTree");
				zTree.expandNode(treeNode);
			},
			//捕获异步加载出现异常错误的事件回调函数和成功的回调函数   
			//onAsyncError : zTreeOnAsyncError,
			onAsyncSuccess : function(event, treeId, treeNode, msg) {
				//第一次进入点击第一层的默认节点全部
				if (firstClick) {
					$("#asyncTree_1_span").click();
					firstClick = false;
				}
			}
		}
	};
	function setFontCss(treeId, treeNode) {
		if (treeNode.dataCenterType == "1") {
			return {
				'font-weight' : 'bold'
			};
		}
		if (treeNode.clusterType == "3") {
			return {
				'font-weight' : 'bold'
			};
		}
		return {};
	};
	// 加载错误提示    
	function zTreeOnAsyncError(event, treeId, treeNode, XMLHttpRequest,
			textStatus, errorThrown) {
		parent.parent.alertWarn("节点加载错误！");
	};

	$(document).ready(function() {
		shrinkTree();
		//alert($('#hideTree', parent.document).html());
		zTreeObj = $.fn.zTree.init($("#asyncTree"), setting);
	});

	//刷新当前选中节点 ，只对父节点有效
	function refreshNode() {
		/*根据 treeId 获取 zTree 对象*/
		var zTree = $.fn.zTree.getZTreeObj("asyncTree"), type = "refresh",
		//是否展开子节点：false：展开，true：不展开
		silent = false,
		/*获取 zTree 当前被选中的节点数据集合*/
		nodes = zTree.getSelectedNodes();
		//alert(nodes);
		/*强行异步加载父节点的子节点。[setting.async.enable = true 时有效]*/
		if (nodes.length > 0) {
			zTree.reAsyncChildNodes(nodes[0], type, silent);
		}
	}

	//左边的树收缩功能
	function shrinkTree() {
		var parentObj = $('#hideTree', parent.document);
		var status = parentObj.attr("status");
		if (status == 'open') {
			$('#ztree-right').show();
			$('#ztree-right').click(function() {
				parentObj.hide();
				parent.openTree();
			});
			parentObj.attr({
				"status" : "close"
			});
		}
	}
</script>
<style>
.ztree li a.curSelectedNode {padding-top:0px; background-color:#d5f2ff; color:black; height:16px; border:1px #c7eeff solid; opacity:0.8;}
</style>
<div>
	<ul
		style="max-width: 239; min-height: 500px; margin-top: 0px; border: none; overflow: auto; background: #f9f9f9;"
		id="asyncTree" class="ztree">
	</ul>
	<div id="ztree-right" style="display: none;">
		<img src="${ctx}/imgs/ztree-right.png" width="9" height="14" />
	</div>
</div>


