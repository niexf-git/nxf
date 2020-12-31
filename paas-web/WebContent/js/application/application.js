// open新增应用窗口
function openAddAppNew() {
	if (isProduct == "false") {
		parent.alertError("PAAS-10109", "运维环境不能创建应用！");
	} else {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "/paas/application/isAdminCheck.action",
			success : function(result) {
				if (result["resultMessage"] == "true") {
					parent.openDialog("/paas/application/queryAppInfo.action",
							"新增应用", 690, 457);
				} else {
					parent.alertError("PAAS-10108", "非管理员不能创建应用");
				}
			}
		});
	}
}

// 批量容灾
function disasterRecoveryAll() {
	if (isProduct == "false") {
		parent.alertConfirm("确认启动批量容灾？", function() {
			var opra_url = "true%7C" + "disasterRecovery";
			var websocketUrl = "ws://" + window.location.host
					+ "/paas/websocket/application";
			this.parent.parent.alertProgressConfirm(opra_url, websocketUrl,
					function() {
						window.location.reload();
					});
		});
	}
}
function getDisasterStatus(appId) {
	var value = 0;
	$.ajax({
		dataType : "json",
		async : false,
		data : {
			id : appId
		},
		url : '/paas/application/getDisasterStatus.action',
		success : function(result) {
			value = result["id"];
		}
	});
	return value;
}
// 计划容灾
function disasterRecovery(obj, appId) {
	if (isProduct == "false") {
		var isDisaster = getDisasterStatus(appId);// 1是容灾，2是恢复
		if (isDisaster == 1) {
			// parent.parent.alertWarn("已经是容灾状态!");
			return false;
		}

		var isCluster = false;
		var isConfig = false;
		var isDisasterTolerance = false;
		// 获取后台DNS与集群信息
		$
				.ajax({
					dataType : "json",
					async : false,
					data : {
						id : appId
					},
					url : '/paas/application/queryDnsAndClusterById.action',
					success : function(result) {
						var clusterInfos = result.clusters;
						for (var i = 0; i < clusterInfos.length; i++) {
							var type = clusterInfos[i].type;
							var disasterTolerance = clusterInfos[i].isDisasterTolerance;// 配置容灾
							if (disasterTolerance == 1) {
								isDisasterTolerance = true;
							}
							if (type == 4) {
								isCluster = true;// 有配置迁移集群
								// break;
							}
							// if(isDisasterTolerance)
						}
						var dnsInfo = result.dnsInfo;
						if (dnsInfo.length > 0) {
							isConfig = true;// 有配置DNS
						}
					}
				});

		if (!isCluster) {
			parent.parent.alertWarn("请配置迁移集群!");
			return false;
		}
		if (!isDisasterTolerance) {
			parent.parent.alertWarn("请配置容灾信息!");
			return false;
		}
		if (!isConfig) {
			parent.parent.alertWarn("请配置DNS!");
			return false;
		}

		parent.alertConfirm("确认启动计划容灾？", function() {
			var opra_url = appId + "%7C" + "disasterRecovery";
			var websocketUrl = "ws://" + window.location.host
					+ "/paas/websocket/application";
			parent.parent.alertProgressConfirm(opra_url, websocketUrl,
					function() {
						$(obj).attr("id", "changeColora");
						$(obj).siblings("li").attr("id", "");
						window.location.reload();
					});

		});
	}
}
// 计划容灾恢复
function unDisasterRecovery(obj, appId) {
	if (isProduct == "false") {
		var isDisaster = getDisasterStatus(appId);// 1是容灾，2是恢复
		if (isDisaster == 2) {
			// parent.parent.alertWarn("已经是恢复状态!");
			return false;
		}
		parent.alertConfirm("确认从容灾恢复至正常？", function() {
			var opra_url = appId + "%7C" + "unDisasterRecovery";
			var websocketUrl = "ws://" + window.location.host
					+ "/paas/websocket/application";
			parent.parent.alertProgressConfirm(opra_url, websocketUrl,
					function() {
						$(obj).attr("id", "changeColor");
						$(obj).siblings("li").attr("id", "");
						window.location.reload();
					});
		});
	}
}
// 生产导入应用
function importApp() {
	if (isProduct == "false") {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "/paas/application/importApp.action",
			success : function(result) {
				if (result["resultMessage"] == "SUCCESS") {
					parent.alertSuccess();
				} else {
					if (result['resultCode'] == "PAAS-20344") {
						parent.parent.alertWarn("数据正在导入中...");
					} else {
						parent.alertError(result['resultCode'],
								result['resultMessage']);
					}
				}
			}
		});
	}
}

function toUpdateApp(id) {
	parent.openDialog("/paas/application/queryAppById.action?id=" + id, "修改应用",
			690, 477);
}

// 删除应用
function deleteApp(appId) {
	parent.alertConfirm("确认删除该应用?", function() {
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				"id" : appId
			},
			url : "/paas/application/deleteAppInfo.action",
			success : function(result) {
				if (result['resultCode'] == 'success') {
					parent.alertSuccess();
					reloadResult();
				} else {
					parent.alertError(result['resultCode'],
							result['resultMessage']);
				}
			}
		});
	});
}

var idAdmin;
var isProduct;
var colNames;
var colModel;
$(function() {
	isProduct = $("#isProduct").val();
	if (isProduct == "true") {
		$(".applist-input3").hide();
	}
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/paas/application/isAdminCheck.action",
		success : function(result) {
			if (isProduct == "false" && result["resultMessage"]) {
				$(".recovery").show();
			}
		}
	});
	setColAndModel(isProduct);
	$("#appName").keydown(function(e) {
		if ((e.keyCode || e.which) == 13) {
			reloadResult();
		}
	});

	$('#appList').jqGrid(
			{
				url : '/paas/application/queryAppList.action',
				datatype : "json",
				width : '100%',
				autowidth:true,
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
				colNames : colNames,
				colModel : colModel,
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
					if (typeof (result['errorCode']) != 'undefined') {
						parent.alertError(result['errorCode'],
								result['errorMsg']);
					}
					var page = $(this).getGridParam('page');
					var lastPage = $(this).getGridParam('lastpage');
					$("#page").val(page);
					$("#pageText").text(page + "/" + lastPage);
				},
				onPaging : function(pgButton) {
					if (pgButton == 'user') {
						var totalPage = document
								.getElementById("sp_1_pagerBar").innerHTML;
						// 通过循环，去掉字符串包含的所有空格
						while (totalPage.indexOf(" ") != -1) {
							totalPage = totalPage.replace(" ", "");
						}
						if (totalPage == 1) {
							reloadResult();
						} else {
							var page = $("input[class='ui-pg-input']").val();
							// 字符串数字转换
							if (Number(page) > Number(totalPage)) {
								reloadResults(totalPage);
							}
							if (Number(page) < 1) {
								reloadResult();
							}
						}
					}
				}
			});
});

function migrateWebSocket(appId, oData, oCluster, mData, mCluster) {
	if (mData == "null") {
		parent.parent.alertWarn("请配置迁移集群!");
		return;
	}
	parent.parent.alertConfirm("确定从 " + oData + '->' + oCluster + " 集群迁移到 "
			+ mData + '->' + mCluster + " 集群？", function() {
		var opra_url = appId + "%7C" + "migrate-application";
		var websocketUrl = "ws://" + window.location.host
				+ "/paas/websocket/application";
		this.parent.parent.alertProgressConfirm(opra_url, websocketUrl,
				function() {
					window.location.reload();
				});
	});
}

function colonyVerification() {
	var isProduct = $("#isProduct").val();
	var flag = false;
	var checkbox = document.getElementsByName("appUtil.colony");
	for (var i = 0; i < checkbox.length; i++) {
		if (checkbox[i].checked) {
			var checkboxColonyInfo = null;
			if (i == 0 && isProduct == "true") {
				checkboxColonyInfo = document
						.getElementsByName("appUtil.colonyDevelopment");
			} else {
				checkboxColonyInfo = document
						.getElementsByName("appUtil.colonyProduction");
			}

			if (i == 1 && isProduct == "true") {
				checkboxColonyInfo = document
						.getElementsByName("appUtil.colonyTest");
			} else {
				if (i == 1) {
					checkboxColonyInfo = document
							.getElementsByName("appUtil.colonyTransferByIpaas");
					for (var j = 0; j < checkboxColonyInfo.length; j++) {
						if (checkboxColonyInfo[j].checked) {
							flag = true;
							break;
						}
					}
					checkboxColonyInfo = document
							.getElementsByName("appUtil.colonyTransferByApaas");
				}
			}
			for (var j = 0; j < checkboxColonyInfo.length; j++) {
				if (checkboxColonyInfo[j].checked) {
					flag = true;
					break;
				}
			}
		}
		if (i == 1 && !flag) {
			parent.parent.alertWarn("请选择集群信息！");
		}
	}
	return flag;
}

function NameVerification() {
	var isProduct = $("#isProduct").val();
	var flag = true;
	var appName = $("#appName").val();
	if (appName.trim() == '') {
		parent.parent.alertWarn("应用名称为必填项！");
		return false;
	} else {
		// 验证规则：字母、数字、下划线组成，字母开头，8位以内
		var regLoginName = /^[a-zA-Z]+[a-zA-Z0-9]*$/;

		if (regLoginName.test(appName)) {
			if (!(appName.trim().length > 3 && appName.trim().length <= 11)) {
				parent.alertWarn("应用名称只能包含英文字符、数字，并且只能以英文开头，长度为4-11位！");
				return false;
			}
		} else {
			parent.alertWarn("应用名称只能包含英文字符、数字，并且只能以英文开头，长度为4-11位！");
			return false;
		}
	}

	var desc = $("#desc").val().trim();
	if (!(desc.length < 1024)) {
		parent.alertWarn("应用的描述信息长度请在1024位以内！");
		return false;
	}
	if (isProduct == "false") {
		if (getRadioBoxValue("appUtil.colonyTransferByApaas") != "undefined"
				&& getRadioBoxValue("appUtil.DisasterToleranceProByApaas") == "undefined") {
			parent.alertWarn("请选择容灾Apaas集群!");
			return false;
		}

		if (getRadioBoxValue("appUtil.colonyTransferByIpaas") != "undefined"
				&& getRadioBoxValue("appUtil.DisasterToleranceProByIpaas") == "undefined") {
			parent.alertWarn("请选择容灾Ipaas集群!");
			return false;
		}
		var colonyTransferBox = $("#colonyTransferBox")[0].checked;
		if (getRadioBoxValue("appUtil.colonyTransferByIpaas") == "undefined"
				&& getRadioBoxValue("appUtil.DisasterToleranceProByIpaas") != "undefined"
				&& colonyTransferBox) {
			parent.alertWarn("请选择迁移Ipaas集群!");
			return false;
		}
		if (getRadioBoxValue("appUtil.colonyTransferByApaas") == "undefined"
				&& getRadioBoxValue("appUtil.DisasterToleranceProByApaas") != "undefined"
				&& colonyTransferBox) {
			parent.alertWarn("请选择迁移Apaas集群!");
			return false;
		}
	}

	// 判断是否是同一个数据中心 和集群 并且是生产环境
	if ($("#colonyPro").val() == $("#colonyTransfer").val()
			&& getRadioBoxValue("appUtil.DisasterToleranceProByApaas") == getRadioBoxValue("appUtil.colonyTransferByApaas")
			&& isProduct == "false"
			&& getRadioBoxValue("appUtil.DisasterToleranceProByApaas") != "undefined"
			&& getRadioBoxValue("appUtil.colonyTransferByApaas") != "undefined") {
		parent.alertWarn("容灾Apaas集群和迁移Apaas集群不能相同!");
		return false;
	}

	// 判断是否是同一个数据中心 和集群 并且是生产环境
	if ($("#colonyPro").val() == $("#colonyTransfer").val()
			&& getRadioBoxValue("appUtil.DisasterToleranceProByIpaas") == getRadioBoxValue("appUtil.colonyTransferByIpaas")
			&& isProduct == "false"
			&& getRadioBoxValue("appUtil.DisasterToleranceProByIpaas") != "undefined"
			&& getRadioBoxValue("appUtil.colonyTransferByIpaas") != "undefined") {
		parent.alertWarn("容灾Ipaas集群和迁移Ipaas集群不能相同!");
		return false;
	}

	return flag;
}

function getRadioBoxValue(radioName) {
	var obj = document.getElementsByName(radioName); // 这个是以标签的name来取控件
	var val = "";
	for (var i = 0; i < obj.length; i++) {
		if (obj[i].checked) {
			val = obj[i].value;
			if (val.indexOf("t") > 0) {
				val = val.substring(0, val.indexOf("t"));
			}
			if (val.indexOf("f") > 0) {
				val = val.substring(0, val.indexOf("f"));
			}
			return val;
		}
		;
	}
	return "undefined";
}

function VerificationAll() {
	var isProduct = $("#isProduct").val();
	var checkBox = null;
	var isChecked = false;
	if (isProduct == "false") {
		var roleProductionBox = $("#roleProductionBox")[0].checked;// 角色：生产
		var colonyProductionBox = $("#colonyProductionBox")[0].checked;// 集群:生产
		// colonyTransferBox
		var colonyTransferBox = $("#colonyTransferBox")[0].checked;
		if (roleProductionBox) {
			checkBox = document.getElementsByName("appUtil.colonyProduction");
			isChecked = false;
			for (var i = 0; i < checkBox.length; i++) {
				if (checkBox[i].checked) {
					isChecked = true;
					break;
				}
			}
			if (!colonyProductionBox || !isChecked) {
				parent.alertWarn("请选择生产集群！");
				return false;
			}
		} else {
			parent.alertWarn("请选择生产角色！");
			return false;
		}
		if (colonyProductionBox) {
			checkBox = document.getElementsByName("appUtil.roleProduction");
			isChecked = false;
			for (var i = 0; i < checkBox.length; i++) {
				if (checkBox[i].checked) {
					isChecked = true;
					break;
				}
			}
			if (!roleProductionBox || !isChecked) {
				parent.alertWarn("请选择生产角色！");
				return false;
			}
		} else {
			parent.alertWarn("请选择生产集群！");
			return false;
		}
		if (colonyTransferBox) {
			var colonyTransferByApaas = document
					.getElementsByName("appUtil.colonyTransferByApaas");
			var colonyTransferByIpaas = document
					.getElementsByName("appUtil.colonyTransferByIpaas");

			for (var j = 0; j < colonyTransferByApaas.length; j++) {
				if (colonyTransferByApaas[j].checked) {
					return true;
					break;
				}
			}
			for (var j = 0; j < colonyTransferByIpaas.length; j++) {
				if (colonyTransferByIpaas[j].checked) {
					return true;
					break;
				}
			}
			parent.alertWarn("请选择迁移集群！");
			return false;
		}
	} else {
		var roleDevelopmentBox = $("#roleDevelopmentBox")[0].checked;// 角色：开发
		var colonyDevelopmentBox = $("#colonyDevelopmentBox")[0].checked;// 集群:开发
		var roleTestBox = $("#roleTestBox")[0].checked;// 角色：测试
		var colonyTestBox = $("#colonyTestBox")[0].checked;// 集群：测试
		if (roleDevelopmentBox) {
			checkBox = document.getElementsByName("appUtil.colonyDevelopment");
			isChecked = false;
			for (var i = 0; i < checkBox.length; i++) {
				if (checkBox[i].checked) {
					isChecked = true;
					break;
				}
			}
			if (!colonyDevelopmentBox || !isChecked) {
				parent.alertWarn("请选择开发集群！");
				return false;
			}
		}
		if (colonyDevelopmentBox) {
			checkBox = document.getElementsByName("appUtil.roleDevelopment");
			isChecked = false;
			for (var i = 0; i < checkBox.length; i++) {
				if (checkBox[i].checked) {
					isChecked = true;
					break;
				}
			}
			if (!roleDevelopmentBox || !isChecked) {
				parent.alertWarn("请选择开发角色！");
				return false;
			}
		}
		if (roleTestBox) {
			checkBox = document.getElementsByName("appUtil.colonyTest");
			isChecked = false;
			for (var i = 0; i < checkBox.length; i++) {
				if (checkBox[i].checked) {
					isChecked = true;
					break;
				}
			}
			if (!colonyTestBox || !isChecked) {
				parent.alertWarn("请选择测试集群！");
				return false;
			}
		}
		if (colonyTestBox) {
			checkBox = document.getElementsByName("appUtil.roleTest");
			isChecked = false;
			for (var i = 0; i < checkBox.length; i++) {
				if (checkBox[i].checked) {
					isChecked = true;
					break;
				}
			}
			if (!roleTestBox || !isChecked) {
				parent.alertWarn("请选择测试角色！");
				return false;
			}
		}
	}

	return true;
}

function onUpdateAppSubmit() {
	if (NameVerification() && colonyVerification() && VerificationAll()) {
		showLoad();
		$.ajax({
			type : "POST",
			dataType : "json",
			data : $('#updateApp').serialize(),
			url : "/paas/application/updateAppInfo.action",
			success : function(result) {
				reloadResult();
				if (result['resultCode'] == 'success') {
					parent.alertSuccess();
					parent.closedl("updateApp_new", result['resultMessage']);
				} else {
					if (result['resultCode'] == "PAAS-20338") {
						parent.parent.alertWarn(result['resultMessage'],
								function() {
									parent.close();
								});
						if (result['resultCode'] == "PAAS-20338") {
							$("#search", parent[0].document)[0].click();
						}
					} else {
						parent.alertError(result['resultCode'],
								result['resultMessage']);
					}
				}
				closeLoad();
			}
		});
	}
}

function onCreateAppSubmit() {
	if (NameVerification() && colonyVerification() && VerificationAll()) {
		showLoad();
		$.ajax({
			type : "POST",
			dataType : "json",
			data : $('#createAppForm').serialize(),
			url : "/paas/application/createAppInfo.action",
			success : function(result) {
				reloadResult();
				if (result['resultCode'] == 'success') {
					parent.alertSuccess();
					parent.closedl("createApp_new", result['resultMessage']);
				} else {
					if (result['resultCode'] == "PAAS-20338") {
						parent.parent.alertWarn(result['resultMessage'],
								function() {
									parent.close();
								});
						if (result['resultCode'] == "PAAS-20338") {
							$("#search", parent[0].document)[0].click();
						}
					} else {
						parent.alertError(result['resultCode'],
								result['resultMessage']);
					}
				}
				closeLoad();
			}
		});
	}
}
function setColAndModel(flag) {
	if (flag == "true") {
		colNames = [ '应用名称', '仓库用户名和密码', '描述', '创建时间', '操作', '' ];
		colModel = [
				{
					name : "appName",
					index : "appName",
					align : 'center',
				},
				{
					name : "userPwd",
					index : "userPwd",
					align : 'center',
				},
				{
					name : "desc",
					index : "desc",
					align : 'center',
				},
				{
					name : "createTime",
					index : "createTime",
					align : 'center',
					title : false,
					formatter : function(val, options, rowObject) {
						var array = val.split("\n");
						var value = "";
						if (array.length > 0) {
							value = array[0];
						}
						return "<p style='border:0px solid gray;' title='"
								+ val + "'>" + value + "</p>";
					},
				},
				{
					name : 'id',
					index : 'id',
					align : 'center',
					formatter : function(val, options, rowObject) {
						var _v = "'" + val + "'";
						var _link = '';
						idAdmin = rowObject['admin'];
						var oData = "'" + rowObject['originalDataName'] + "'";
						var oCluster = "'" + rowObject['originalCluster'] + "'";
						var mData = "'" + rowObject['migrationDataName'] + "'";
						var mCluster = "'" + rowObject['migrationCluster']
								+ "'";
						if (rowObject['admin']) {
							_link = '<a href="JavaScript:toUpdateApp('
									+ _v
									+ ')" style="color:#666"><img src="/paas/imgs/modify.png" width="22" title="修改" height="22" border="none"/></a>&nbsp;&nbsp;&nbsp;'
									+ '<a href="javaScript:deleteApp('
									+ _v
									+ ')" style="color:#666"><img src="/paas/imgs/delete.png" width="22" height="22" title="删除" border="none"/></a>&nbsp;&nbsp;&nbsp;';
							if (isProduct == "false") {
								_link += '<a href="JavaScript:migrateWebSocket('
										+ _v
										+ ','
										+ oData
										+ ','
										+ oCluster
										+ ','
										+ mData
										+ ','
										+ mCluster
										+ ')" style="color:#666"><img src="/paas/imgs/execute_build.png" width="22" title="迁移" height="22" border="none"/></a>&nbsp;&nbsp;&nbsp;'
										+ '<a href="detailsTransfer.jsp?appId='
										+ val
										+ '&appName='
										+ rowObject['appName']
										+ '" style="color:#666"><img src="/paas/imgs/Historical.png" width="22" height="22" title="历史详情" border="none"/></a>&nbsp;&nbsp;&nbsp;';
							}
						} else {
							if (isProduct == "false") {
								_link = '<a href="JavaScript:migrateWebSocket('
										+ _v
										+ ','
										+ oData
										+ ','
										+ oCluster
										+ ','
										+ mData
										+ ','
										+ mCluster
										+ ')" style="color:#666"><img src="/paas/imgs/execute_build.png" width="22" title="迁移" height="22" border="none"/></a>&nbsp;&nbsp;&nbsp;'
										+ '<a href="detailsTransfer.jsp?appId='
										+ val
										+ '&appName='
										+ rowObject['appName']
										+ '" style="color:#666"><img src="/paas/imgs/Historical.png" width="22" height="22" title="历史详情" border="none"/></a>&nbsp;&nbsp;&nbsp;';
							}
						}
						return _link;
					}
				}, {
					name : 'admin',
					index : 'admin',
					hidden : true
				}
		// , {
		// name : 'id',
		// index : 'id',
		// hidden : true
		// }
		];
	} else {
		colNames = [ '应用名称', '仓库用户名和密码', '生产集群', '迁移集群', '描述', '创建时间', '操作',
				'', '', '', '' ];
		colModel = [
				{
					name : "appName",
					index : "appName",
					align : 'center',
					
				},
				{
					name : "userPwd",
					index : "userPwd",
					align : 'center',
				},
				{
					name : "originalCluster",
					index : "originalCluster",
					align : 'center',
					hiiden : true,
					formatter : function(val, options, rowObject) {
						var cluster = rowObject['originalCluster'];
						var dataName = rowObject['originalDataName'];
						if (cluster == null || cluster == "") {
							return '';
						} else {
							return dataName + "->" + cluster;
						}
					},
				},
				{
					name : "migrationCluster",
					index : "migrationCluster",
					align : 'center',
					hiiden : isProduct,
					formatter : function(val, options, rowObject) {
						var cluster = rowObject['migrationCluster'];
						var dataName = rowObject['migrationDataName'];
						if (cluster == null || cluster == "") {
							return '';
						} else {
							return dataName + "->" + cluster;
						}
					},
				},
				{
					name : "desc",
					index : "desc",
					align : 'center',
				},
				{
					name : "createTime",
					index : "createTime",
					align : 'center',
					title : false,
					formatter : function(val, options, rowObject) {
						var array = val.split("\n");
						var value = "";
						if (array.length > 0) {
							value = array[0];
						}
						return "<p style='border:0px solid gray;' title='"
								+ val + "'>" + value + "</p>";
					},
				},
				{
					name : 'id',
					index : 'id',
					align : 'center',
					width : '250px',
					title : false,
					formatter : function(val, options, rowObject) {
						var _v = "'" + val + "'";
						var _link = '';
						idAdmin = rowObject['admin'];
						var oData = "'" + rowObject['originalDataName'] + "'";
						var oCluster = "'" + rowObject['originalCluster'] + "'";
						var mData = "'" + rowObject['migrationDataName'] + "'";
						var mCluster = "'" + rowObject['migrationCluster']
								+ "'";
						if (rowObject['admin']) {
							_link = '<a href="JavaScript:toUpdateApp('
									+ _v
									+ ')" style="color:#666； float:left; margin-right:5px;"><img src="/paas/imgs/modify.png" width="22" title="修改" height="22" border="none"/></a>&nbsp'
									+ '<a href="javaScript:deleteApp('
									+ _v
									+ ')" style="color:#666； float:left; margin-right:5px;"><img src="/paas/imgs/delete.png" width="22" height="22" title="删除" border="none"/></a>&nbsp';
							if (isProduct == "false") {
								_link += '<a href="JavaScript:migrateWebSocket('
										+ _v
										+ ','
										+ oData
										+ ','
										+ oCluster
										+ ','
										+ mData
										+ ','
										+ mCluster
										+ ')" style="color:#666； float:left; margin-right:5px;"><img src="/paas/imgs/execute_build.png" width="22" title="迁移" height="22" border="none"/></a>&nbsp;'
										+ '<a href="detailsTransfer.jsp?appId='
										+ val
										+ '&appName='
										+ rowObject['appName']
										+ '" style="color:#666； float:left; margin-right:5px;"><img src="/paas/imgs/Historical.png" width="22" height="22" title="历史详情" border="none"/></a>&nbsp;'
										+ '<a href="/paas/application/queryDNSList.action?id='
										+ rowObject['id']
										+ '" style="color:#666； float:left; margin-right:5px;"><img src="/paas/imgs/DNS.png" width="22" height="22" title="容灾配置" border="none"/></a>&nbsp;';
							}
						} else {
							if (isProduct == "false") {
								_link = '<a href="JavaScript:migrateWebSocket('
										+ _v
										+ ','
										+ oData
										+ ','
										+ oCluster
										+ ','
										+ mData
										+ ','
										+ mCluster
										+ ')" style="color:#666； float:left; margin-right:5px;"><img src="/paas/imgs/execute_build.png" width="22" title="迁移" height="22" border="none"/></a>&nbsp;'
										+ '<a href="detailsTransfer.jsp?appId='
										+ val
										+ '&appName='
										+ rowObject['appName']
										+ '" style="color:#666； float:left; margin-right:5px;"><img src="/paas/imgs/Historical.png" width="22" height="22" title="历史详情" border="none"/></a>&nbsp;'
										+ '<a href="/paas/application/queryDNSList.action?id='
										+ rowObject['id']
										+ '" style="color:#666； float:left; margin-right:5px;"><img src="/paas/imgs/DNS.png" width="22" height="22" title="容灾配置" border="none"/></a>&nbsp;';
							}
						}
						if (isProduct == "false") {
							var mrObj = rowObject['migrationRecords'];// getDisasterStatus(val);
							var isDisaster = 0;
							if (mrObj.length > 0) {
								var rstatus = mrObj[0].recordType;
								isDisaster = rstatus;
							}
							if (isDisaster == 1) {
								_link += '<div  class="list-recovery"><ul><li id="changeColora" onclick="disasterRecovery(this'
										+ ","
										+ val
										+ ')" title="容灾">容灾</li>'
										+ '<li  onclick="unDisasterRecovery(this'
										+ ","
										+ val
										+ ')" title="恢复">恢复</li></ul></div>';
							} else {
								_link += '<div  class="list-recovery"><ul><li onclick="disasterRecovery(this'
										+ ","
										+ val
										+ ')" title="容灾">容灾</li>'
										+ '<li id="changeColor" onclick="unDisasterRecovery(this'
										+ ","
										+ val
										+ ')" title="恢复">恢复</li></ul></div>';
							}
						}
						return _link;
					}
				}, {
					name : 'admin',
					index : 'admin',
					hidden : true
				}, {
					name : "originalDataName",
					index : "originalDataName",
					hidden : true
				}, {
					name : "migrationDataName",
					index : "migrationDataName",
					hidden : true
				}, {
					name : "migrationRecords",
					index : "migrationRecords",
					hidden : true
				}
		// , {
		// name : 'id',
		// index : 'id',
		// hidden : true
		// }
		];
	}
}

// 通过应用组名称查询
function reloadResult() {
	sortname = $('#appList').getGridParam('sortname');
	if (sortname != null) {
		sortname = "";
	}

	var name = $.trim($("#appName").val());
	/*
	 * //通过循环，去掉字符串包含的所有空格 while(name.indexOf(" ") != -1){ name = name.replace("
	 * ",""); }
	 */
	$("#appList").jqGrid().setGridParam({
		page : 1,
		rowNum : 10,
		mtype : "POST",
		sortname : sortname,
		datatype : "json",
		url : '/paas/application/queryAppList.action'
	}).setGridParam({
		postData : {
			'appName' : name
		}
	}).trigger("reloadGrid");
	$("#groupName").val("");
}
$(function(){
    $(window).resize(function(){   
    	$("#appList").setGridWidth($(window).width()*0.99);
});
});
// 当输入的跳转页数大于总页数是,自动查询最后一页
function reloadResults(totalPage) {
	var rows = $('#appList').getGridParam('rows');
	var sidx = $('#appList').getGridParam('sidx');
	var sord = $('#appList').getGridParam('sord');
	jQuery("#appList").jqGrid('setGridParam', {
		url : '/paas/app/queryAppList.action',
		page : totalPage,
		rows : rows,
		sidx : sidx,
		sord : sord,
	}).trigger("reloadGrid");
}

function initData() {
	location.reload(true);
}

var roleDevFatherCheckedNum = 0;
var roleTestFatherCheckedNum = 0;

/*******************************************************************************
 * 复选框联动
 * 角色-开发-（全选/反选）复选框
 */
function roleDevelopmentFather() {
	// 角色
	var roleDevelopment = document.getElementsByName("appUtil.roleDevelopment");

	if (roleDevelopment.length == 0) {
		$("#roleDevelopmentBox")[0].checked = false;
		parent.parent.alertWarn("没有可选的角色，请先创建！");
		return;
	}

	// 1、点击左侧：角色-开发-（全选/反选）父的复选框，让显示状态的子复选框选中
	var roleDevTab = $("#roleDevTab tr");
	for (var i = 0; i < roleDevTab.length; i++) {
		if(roleDevTab[i].outerHTML.indexOf("display: none") == -1){ // 显示的
			roleDevelopment[i].checked = $("#roleDevelopmentBox")[0].checked;
		}
	}
	// 2、左侧选中的子复选框对应右侧的子复选框隐藏
	for (var i = 0; i < roleDevelopment.length; i++) {
		var roleTestTabTr = $("#roleTestTab").find("tr").eq(i);
		if (roleDevelopment[i].checked) {
			roleTestTabTr.hide();
			continue;
		} else {
			roleTestTabTr.show();
			continue;
		}
	}
	// 3、右侧角色-测试-（全选/反选）父的复选框显示与否，判断依据：右侧子项显示选中或者显示不选中都要让父项显示
	var roleTest = document.getElementsByName("appUtil.roleTest");
	var roleTestTab = $("#roleTestTab tr");
	for(var i = 0; i < roleTest.length; i++){
		if((roleTest[i].checked && roleTestTab[i].outerHTML.indexOf("display: none") == -1) || 
				(roleTest[i].checked == false && roleTestTab[i].outerHTML.indexOf("display: none") == -1)){
			roleTestFatherCheckedNum = roleTestFatherCheckedNum + 1;
		}
	}
	if(roleTestFatherCheckedNum > 0){
		$("#roleTestBox").show();
		roleTestFatherCheckedNum = 0;
	} else {
		$("#roleTestBox").hide();
		roleTestFatherCheckedNum = 0;
	}
	controlRoleDisplayOrNot();
}
/**
 * 角色-测试-（全选/反选）复选框
 */
function roleTestFather() {
	var roleTest = document.getElementsByName("appUtil.roleTest");

	if (roleTest.length == 0) {
		$("#roleTestBox")[0].checked = false;
		parent.parent.alertWarn("没有可选的角色，请先创建！");
		return;
	}

	var roleDevTab = $("#roleTestTab tr");
	for (var i = 0; i < roleDevTab.length; i++) {
		if(roleDevTab[i].outerHTML.indexOf("display: none") == -1){ // 显示的
			roleTest[i].checked = $("#roleTestBox")[0].checked;
		}
	}
	
	for (var i = 0; i < roleTest.length; i++) {
		if (roleTest[i].checked) {
			$("#roleDevTab").find("tr").eq(i).hide();
			continue;
		} else {
			$("#roleDevTab").find("tr").eq(i).show();
			continue;
		}
	}
	
	var roleDevelopment = document.getElementsByName("appUtil.roleDevelopment");
	var roleDevTab = $("#roleDevTab tr");
	for(var i = 0; i < roleDevelopment.length; i++){
		if((roleDevelopment[i].checked && roleDevTab[i].outerHTML.indexOf("display: none") == -1) || 
				(roleDevelopment[i].checked == false && roleDevTab[i].outerHTML.indexOf("display: none") == -1)){
			roleDevFatherCheckedNum = roleDevFatherCheckedNum + 1;
		} 
	}
	if(roleDevFatherCheckedNum > 0){
		$("#roleDevelopmentBox").show();
		roleDevFatherCheckedNum = 0;
	} else {
		$("#roleDevelopmentBox").hide();
		roleDevFatherCheckedNum = 0;
	}
	controlRoleDisplayOrNot();
}

function roleProductinFather() {
	var roleProduction = document.getElementsByName("appUtil.roleProduction");

	if (roleProduction.length == 0) {
		$("#roleProductionBox")[0].checked = false;
		parent.parent.alertWarn("没有可选的角色，请先创建！");
		return;
	}

	for (var i = 0; i < roleProduction.length; i++) {
		roleProduction[i].checked = $("#roleProductionBox")[0].checked;
	}
}

function colonyDevelopmentFather() {
	var colonyDevelopment = document
			.getElementsByName("appUtil.colonyDevelopment");
	for (var i = 0; i < colonyDevelopment.length; i++) {
		colonyDevelopment[i].checked = $("#colonyDevelopmentBox")[0].checked;
	}
	// colonyDevelopment[0].checked = $("#colonyDevelopmentBox")[0].checked;
}

function colonyTestFather() {
	var colonyTest = document.getElementsByName("appUtil.colonyTest");
	for (var i = 0; i < colonyTest.length; i++) {
		colonyTest[i].checked = $("#colonyTestBox")[0].checked;
	}
	// colonyTest[0].checked = $("#colonyTestBox")[0].checked;
}
function colonyProductionFather() {
	var colonyProduction = document
			.getElementsByName("appUtil.colonyProduction");
	var DisasterToleranceProByIpaas = document
			.getElementsByName("appUtil.DisasterToleranceProByIpaas");
	var DisasterToleranceProByApaas = document
			.getElementsByName("appUtil.DisasterToleranceProByApaas");
	for (var i = 0; i < colonyProduction.length; i++) {
		colonyProduction[i].checked = $("#colonyProductionBox")[0].checked;
		
		var pCIds = $("#productClusterIds").val().split(",");
		
		for (var j = 0; j < pCIds.length; j++) {
			var index = pCIds.indexOf($("#colonyPro").val()+":"+colonyProduction[i].value);
			if (index > -1) {
				pCIds.splice(index, 1);
				$("#productClusterIds").val(pCIds.toString());
			}
		}
		
		
		if (colonyProduction[i].checked) {
			if (i < DisasterToleranceProByIpaas.length) {
				$(DisasterToleranceProByIpaas[i]).attr("disabled", false);
			} else {
				$(
						DisasterToleranceProByApaas[i
								- DisasterToleranceProByIpaas.length]).attr(
						"disabled", false);
			}
		} else {
			if (i < DisasterToleranceProByIpaas.length) {
				setSelectNo(DisasterToleranceProByIpaas[i]);
				$(DisasterToleranceProByIpaas[i]).attr("disabled", true);
			} else {
				setSelectNo(DisasterToleranceProByApaas[i
						- DisasterToleranceProByIpaas.length]);
				$(
						DisasterToleranceProByApaas[i
								- DisasterToleranceProByIpaas.length]).attr(
						"disabled", true);
			}

		}
	}
	// colonyProduction[0].checked = $("#colonyProductionBox")[0].checked;
}
function colonyTransferBoxFather() {
	var colonyTransferByIpaas = document
			.getElementsByName("appUtil.colonyTransferByIpaas");
	var colonyTransferByApaas = document
			.getElementsByName("appUtil.colonyTransferByApaas");
	if (colonyTransferByIpaas.length > 0) {
		for (var i = 0; i < colonyTransferByIpaas.length; i++) {
			if (i == 0) {
				colonyTransferByIpaas[i].checked = $("#colonyTransferBox")[0].checked;
			} else {
				colonyTransferByIpaas[i].checked = false;
			}
		}
	}
	if (colonyTransferByApaas.length > 0) {
		for (var i = 0; i < colonyTransferByApaas.length; i++) {
			if (i == 0) {
				colonyTransferByApaas[i].checked = $("#colonyTransferBox")[0].checked;
			} else {
				colonyTransferByApaas[i].checked = false;
			}
		}
	}

	// colonyProduction[0].checked = $("#colonyTransferBox")[0].checked;
}

function setSelectNo(radioObj) {
	var radioCheck = $(radioObj).val();
	var DisasterToleranceProByIpaas = document
			.getElementsByName("appUtil.DisasterToleranceProByIpaas");
	var DisasterToleranceProByApaas = document
			.getElementsByName("appUtil.DisasterToleranceProByApaas");
	if (radioObj.name == "appUtil.DisasterToleranceProByIpaas") {
		for (var i = 0; i < DisasterToleranceProByIpaas.length; i++) {
			if (DisasterToleranceProByIpaas[i].value != radioObj.value) {
				DisasterToleranceProByIpaas[i].value = DisasterToleranceProByIpaas[i].value
						.replace("t", "f");
			}
		}
	} else {
		for (var i = 0; i < DisasterToleranceProByApaas.length; i++) {
			if (DisasterToleranceProByApaas[i].value != radioObj.value) {
				DisasterToleranceProByApaas[i].value = DisasterToleranceProByApaas[i].value
						.replace("t", "f");
			}
		}
	}
	if (radioCheck != null) {
		if (radioCheck.indexOf("t") > 0) {
			$(radioObj).attr("checked", false);
			$(radioObj).val(radioCheck.replace("t", "f"));
		} else {
			$(radioObj).val(radioCheck.replace("f", "t"));
		}
	}

}
function onClickCheckBox(checkBoxObj) {
	var colonyTransferFlag = false;
	var colonyTransferFlags = checkBoxObj.checked;
	var colonyTransferByApaas = document
			.getElementsByName("appUtil.colonyTransferByApaas");
	var colonyTransferByIpaas = document
			.getElementsByName("appUtil.colonyTransferByIpaas");
	for (var i = 0; i < colonyTransferByApaas.length; i++) {
		if (colonyTransferByApaas[i].checked) {
			colonyTransferFlag = true;
			if (checkBoxObj.name == "appUtil.colonyTransferByApaas") {
				colonyTransferByApaas[i].checked = false;
			}
		}
	}

	for (var i = 0; i < colonyTransferByIpaas.length; i++) {
		if (colonyTransferByIpaas[i].checked) {
			colonyTransferFlag = true;
			if (checkBoxObj.name == "appUtil.colonyTransferByIpaas") {
				colonyTransferByIpaas[i].checked = false;
			}
		}

	}
	$(checkBoxObj).attr("checked", colonyTransferFlags);
	$("#colonyTransferBox")[0].checked = colonyTransferFlag;
}

var showNum = -1;
var roleTestCheckedNum = 0;
var roleDevCheckedNum = 0;
function linkageroleDevelopmentCheckBox() {
	var roleDevelopmentFlag = false;
	var roleTestFlag = false;
	var roleProductionFlag = false;

	var colonyDevelopmentFlag = false;
	var colonyTestFlag = false;
	var colonyProductionFlag = false;
	// var colonyTransferFlag = false;
	var isProduct = $("#isProduct").val();
	if (isProduct == "false") {
		var roleProduction = document
				.getElementsByName("appUtil.roleProduction");
		for (var i = 0; i < roleProduction.length; i++) {
			if (roleProduction[i].checked) {
				roleProductionFlag = true;
				break;
			}
		}
		$("#roleProductionBox")[0].checked = roleProductionFlag;
	} else {
		var roleDevelopment = document.getElementsByName("appUtil.roleDevelopment");
		for (var i = 0; i < roleDevelopment.length; i++) {
			if (roleDevelopment[i].checked) {
				roleDevelopmentFlag = true;
				$("#roleTestTab").find("tr").eq(i).hide();
				showNum = showNum + 1;
				roleDevCheckedNum = roleDevCheckedNum + 1;
				continue;
			} else {
				$("#roleTestTab").find("tr").eq(i).show();
				showNum = 0;
				continue;
			}
		}
		
		var roleTest = document.getElementsByName("appUtil.roleTest");
		for (var i = 0; i < roleTest.length; i++) {
			if (roleTest[i].checked) {
				roleTestFlag = true;
				$("#roleDevTab").find("tr").eq(i).hide();
				showNum = showNum + 1;
				roleTestCheckedNum = roleTestCheckedNum + 1;
				continue;
			} else {
				$("#roleDevTab").find("tr").eq(i).show();
				showNum = 0;
				continue;
			}
		}
		
		if(showNum >= 0){
			$("#roleTestBox").show();
			$("#roleDevelopmentBox").show();
			showNum = -1;
		} else {
			$("#roleTestBox").hide();
			$("#roleDevelopmentBox").hide();
			showNum = -1;
		}
		
		if(roleTestCheckedNum != roleTest.length){
			roleTestCheckedNum = 0;
		} else {
			$("#roleTestBox").show();
			$("#roleDevelopmentBox").hide();
			roleTestCheckedNum = 0;
		}
		
		if(roleDevCheckedNum != roleDevelopment.length){
			roleDevCheckedNum = 0;
		} else {
			$("#roleDevelopmentBox").show();
			$("#roleTestBox").hide();
			roleDevCheckedNum = 0;
		}
		
		controlRoleDisplayOrNot();
		
		$("#roleDevelopmentBox")[0].checked = roleDevelopmentFlag;
		$("#roleTestBox")[0].checked = roleTestFlag;
	}
	if (isProduct == "false") {
		var DisasterToleranceProByIpaas = document
				.getElementsByName("appUtil.DisasterToleranceProByIpaas");
		var DisasterToleranceProByApaas = document
				.getElementsByName("appUtil.DisasterToleranceProByApaas");
		var colonyProduction = document
				.getElementsByName("appUtil.colonyProduction");
		for (var i = 0; i < colonyProduction.length; i++) {
			if (colonyProduction[i].checked) {
				colonyProductionFlag = true;
				var pCIds = $("#productClusterIds").val().split(",");
				var ids = "";
				var isIndexOf = false;
				for (var j = 0; j < pCIds.length; j++) {
					if(pCIds[j] == $("#colonyPro").val()+":"+colonyProduction[i].value){
						isIndexOf = true;
						break;
					}
				}
				if(!isIndexOf){
					ids += $("#colonyPro").val()+":"+colonyProduction[i].value+",";
				}
				//$("#dataIds").val($("#dataIds").val()+","+$("#colonyPro").val());
				if("" != ids){
					$("#productClusterIds").val($("#productClusterIds").val()+ids);
				}
				if (i < DisasterToleranceProByIpaas.length) {
					$(DisasterToleranceProByIpaas[i]).attr("disabled", false);
				} else {
					$(
							DisasterToleranceProByApaas[i
									- DisasterToleranceProByIpaas.length])
							.attr("disabled", false);
				}
			} else {
				var pCIds = $("#productClusterIds").val().split(",");
				
				for (var j = 0; j < pCIds.length; j++) {
					var index = pCIds.indexOf($("#colonyPro").val()+":"+colonyProduction[i].value);
					if (index > -1) {
						pCIds.splice(index, 1);
						$("#productClusterIds").val(pCIds.toString());
					}
				}
				
				if (i < DisasterToleranceProByIpaas.length) {
					setSelectNo(DisasterToleranceProByIpaas[i]);
					$(DisasterToleranceProByIpaas[i]).attr("disabled", true);
				} else {
					setSelectNo(DisasterToleranceProByApaas[i
							- DisasterToleranceProByIpaas.length]);
					$(
							DisasterToleranceProByApaas[i
									- DisasterToleranceProByIpaas.length])
							.attr("disabled", true);
				}

			}
		}

		// var colonyTransfer = document
		// .getElementsByName("appUtil.colonyTransfer");
		// for (var i = 0; i < colonyTransfer.length; i++) {
		// if (colonyTransfer[i].checked) {
		// colonyTransferFlag = true;
		// break;
		// }
		// }
		$("#colonyProductionBox")[0].checked = colonyProductionFlag;
		// $("#colonyTransferBox")[0].checked = colonyTransferFlag;
	} else {
		var colonyDevelopment = document
				.getElementsByName("appUtil.colonyDevelopment");
		for (var i = 0; i < colonyDevelopment.length; i++) {
			if (colonyDevelopment[i].checked) {
				colonyDevelopmentFlag = true;
				break;
			}
		}

		var colonyTest = document.getElementsByName("appUtil.colonyTest");
		for (var i = 0; i < colonyTest.length; i++) {
			if (colonyTest[i].checked) {
				colonyTestFlag = true;
				break;
			}
		}
		$("#colonyDevelopmentBox")[0].checked = colonyDevelopmentFlag;
		$("#colonyTestBox")[0].checked = colonyTestFlag;
	}
}
// 从主页展示进度条
function showLoad() {
	$('#loadImg').css({
		"width" : "100%",
		"height" : "100%"
	});
	$('#loadImg').show();
}
function closeLoad() {
	$('#loadImg').hide();
}

/**
 * table追加配置信息
 */
function addEnvs(appName) {
	var tabObj = document.getElementById("dnsConfTab");
	var tabRows = tabObj.rows.length;
	var rowIndex = tabRows - 1;// 减去表头那一行，下标从0开始
	// addEnvsConfTab(tabObj,"","",rowIndex);
	addEnvsConfTab("", "", rowIndex, appName);
}

// 添加环境变量
var rowIndex = 0;
function addEnvsConfTab(key, value, currentRowIndex, appName) {
	rowIndex = currentRowIndex;

	$("#dnsConfTab")
			.append(
					'<tr id="dnsTabTr'
							+ rowIndex
							+ '" class="_tr"><td width="243" height="30" align="center" valign="middle">'
							+ '<input type="text"  name="dnsDomainName"  id="dnsConfKey'
							+ rowIndex
							+ '" value="'
							+ key
							+ '" style="width: 150px; margin-right:0px;" />.'
							+ appName
							+ '</td>'
							+ '<td width="344" height="30" align="center" valign="middle">'
							+ '<input type="text"  name="dnsHostIp"  id="dnsConfValue'
							+ rowIndex
							+ '"  value="'
							+ value
							+ '"/></td>'
							+ '<td width="345" height="30" align="center" valign="middle">'
							+ '<input type="text"  name="dnsSpareIp"  id="dnsConfValue'
							+ rowIndex
							+ '"  value="'
							+ value
							+ '"/></td>'
							+ '<td width="145" height="30" align="center" valign="middle">'
							+ '<center><a href="JavaScript:deleteDnsConf('
							+ rowIndex
							+ ');" title="删除"><img src="/paas/imgs/delete.png" width="22" height="22" border="none" /></a></center></td></tr>');

	rowIndex++;
}

/**
 * 删除一个tr
 * 
 * @param rowIndex
 */
function deleteDnsConf(rowIndex) {
	$("#dnsTabTr" + rowIndex).remove();
}

/**
 * 清除所有的配置tr
 */
function deleteDns() {
	$("._tr").remove();
}

function dnsConfCheck() {
	var flag = true;
	var obj = document.getElementById("dnsConfTab");
	var tabRows = obj.rows.length;
	var envsCount = tabRows - 1;// 减去表头那一行，下标从0开始
	for (var i = 1; i <= envsCount; i++) {
		var key = $.trim(obj.rows[i].cells[0].firstElementChild.value);
		if (key == null || key == "") {
			parent.parent.alertWarn("域名不能为空", function() {
				parent.parent.closeAlert2();
			});
			flag = false;
			break;
		}
		for (var j = 1; j <= envsCount; j++) {
			var key1 = $.trim(obj.rows[j].cells[0].firstElementChild.value);
			if (i!=j&&key==key1) {
				parent.parent.alertWarn("域名重复，请重新填写！", function() {
					parent.parent.closeAlert2();
				});
				flag = false;
				break;
			}
		}
		var value = $.trim(obj.rows[i].cells[1].firstElementChild.value);
		if (value == null || value == "") {
			parent.parent.alertWarn("主IP不能为空", function() {
				parent.parent.closeAlert2();
			});
			flag = false;
			break;
		}
		var value1 = $.trim(obj.rows[i].cells[2].firstElementChild.value);
		var re =  /^(1\d{2}|2[0-4]\d|25[0-5]|[1-9]\d|[1-9])\.(1\d{2}|2[0-4]\d|25[0-5]|[1-9]\d|\d)\.(1\d{2}|2[0-4]\d|25[0-5]|[1-9]\d|\d)\.(1\d{2}|2[0-4]\d|25[0-5]|[1-9]\d|\d)$/;  

			if (!re.test(value)) {
				parent.parent.alertWarn("被容灾环境ip地址格式不正确，请修改", function() {
					parent.parent.closeAlert2();
				});
				return false;
			}
			if (!re.test(value1) && value1 != "") {
				parent.parent.alertWarn("容灾环境ip地址格式不正确，请修改", function() {
					parent.parent.closeAlert2();
				});
				return false;
			}
			if (value==value1) {
				parent.parent.alertWarn("被容灾环境ip和容灾环境ip不能相同，请修改", function() {
					parent.parent.closeAlert2();
				});
				return false;
			}

	}
	return flag;
}

function saveDns() {
	if (dnsConfCheck()) {
		showLoad();
		$.ajax({
			dataType : "json",
			data : $('#dnsForm').serialize(),
			url : '/paas/application/createDNS.action',
			success : function(result) {
				closeLoad();
				if (result['resultCode'] == 'success') {
					parent.alertConfirm("保存成功,是否返回应用列表?", function() {
						history.go(-1);
					}, function() {
					});
				}
			}
		});
	}
}

function dataCenterChange(text) {
	var dataId = "";
	var tabId = "";
	var rodioName = "";
	var checkName = "";
	var columnName = "";
	var DisasterTolerance = "";
	if (text == "colonyDeve") {
		dataId = $("#" + text).val();
		tabId = "colonyDeveTab";
		rodioName = "appUtil.colonyDevelopment";
		checkName = "colonyDevelopmentBox";
		columnName = "colonyDeveColumn";
	} else if (text == "colonyTest") {
		dataId = $("#" + text).val();
		tabId = "colonyTestTab";
		rodioName = "appUtil.colonyTest";
		checkName = "colonyTestBox";
		columnName = "colonyTestColumn";
	} else if (text == "colonyPro") {
		dataId = $("#" + text).val();
		tabId = "colonyProTab";
		rodioName = "appUtil.colonyProduction";
		checkName = "colonyProductionBox";
		columnName = "colonyProTabColumn";
		DisasterTolerance = "appUtil.DisasterTolerancePro";
	} else if (text == "colonyTransfer") {
		dataId = $("#" + text).val();
		tabId = "colonyTransferTab";
		rodioName = "appUtil.colonyTransfer";
		checkName = "colonyTransferBox";
		columnName = "colonyTransferColumn";
	}
	// 去掉父选项
	$("#" + checkName)[0].checked = false;
	$
			.ajax({
				dataType : "json",
				data : {
					dataId : dataId
				},
				url : '/paas/application/dataCenterChange.action',
				success : function(result) {
					var select = $("#" + tabId);
					select.empty();
					if (result['result'] == null || result['result'] == "") {
						$("#" + columnName).hide();
						return;
					}
					if ($("#" + columnName).length > 0) {
						$("#" + columnName).show();
					} else {
						if (columnName == "colonyProTabColumn") {
							$("#" + columnName + "Div")
									.append(
											"<table width='211' border='0' cellspacing='0' cellpadding='0' "
													+ "id='"
													+ columnName
													+ "'>"
													+ "<tr><td width='90' height='26' align='center' valign='middle'>名称</td><td width='55' height='26' valign=''middle'>类型</td>"
													+ "<td width='64' height='26' valign='middle' align='left'>是否容灾</td></tr></table>");
						} else {
							$("#" + columnName + "Div")
									.append(
											"<table width='211' border='0' cellspacing='0' cellpadding='0' "
													+ "id='"
													+ columnName
													+ "'>"
													+ "<tr><td width='90' height='26' align='center' valign='middle'>名称</td><td width='55' height='26' valign=''middle'>类型</td>"
													+ "</tr></table>");
						}
					}
					$
							.each(
									result['result'],
									function(i, v) {
										var clusterType = "";
										var DisasterTolerance1 = "";
										var rodioName1 = "";
										var type = "";
										if (v.checkeds) {
											type = v.id + "t";
										} else {
											type = v.id + "f";
										}
										if (v.type == "1"
												&& tabId != "colonyTransferTab") {
											clusterType = "ipaas";
											DisasterTolerance1 = DisasterTolerance
													+ "ByIpaas";
										} else if (v.type == "2"
												&& tabId != "colonyTransferTab") {
											clusterType = "apaas";
											DisasterTolerance1 = DisasterTolerance
													+ "ByApaas";
										} else if (v.type == "1"
												&& tabId == "colonyTransferTab") {
											rodioName1 = rodioName + "ByIpaas";
											clusterType = "ipaas";
										} else if (v.type == "2"
												&& tabId == "colonyTransferTab") {
											clusterType = "apaas";
											rodioName1 = rodioName + "ByApaas";
										}

										if (tabId == "colonyDeveTab"
												|| tabId == "colonyTestTab") {
											select
													.append("<tr>"
															+ '<td width="13" height="26" align="center" valign="middle"><input name="'
															+ rodioName
															+ '" type="checkbox" value="'
															+ v.id
															+ '" onchange="linkageroleDevelopmentCheckBox()" /></td>'
															+ '<td width="66" height="26" valign="middle"><a href="#">'
															+ v.name
															+ '</a></td>'
															+ '<td width="55" height="26" valign="middle">'
															+ clusterType
															+ '</td>' + '</tr>');
										}
										if (tabId == "colonyTransferTab") {
											select
													.append("<tr>"
															+ '<td width="13" height="26" align="center" valign="middle"><input name="'
															+ rodioName1
															+ '" type="checkbox" value="'
															+ v.id
															+ '" onclick="onClickCheckBox(this)" /></td>'
															+ '<td width="66" height="26" valign="middle"><a href="#">'
															+ v.name
															+ '</a></td>'
															+ '<td width="55" height="26" valign="middle">'
															+ clusterType
															+ '</td>' + '</tr>');
										}
										if (tabId == "colonyProTab") {
											var pCIds = $("#productClusterIds").val().split(",");
											var isOk = false;
											var checkBox = "";
											var radio = "";
											for (var i = 0; i < pCIds.length; i++) {
												if(v.id == pCIds[i].split(":")[1]){
													checkBox =  '<input name="'
														+ rodioName
														+ '" type="checkbox" checked value="'
														+ v.id
														+ '" onchange="linkageroleDevelopmentCheckBox()" />';
													isOk = true;
													break;
												}else{
													checkBox =  '<input name="'
														+ rodioName
														+ '" type="checkbox" value="'
														+ v.id
														+ '" onchange="linkageroleDevelopmentCheckBox()" />';
												}
											}
											
											select
													.append("<tr>"
															+ '<td width="13" height="26" align="center" valign="middle">'+checkBox+'</td>'
															+ '<td width="66" height="26" valign="middle"><a href="#">'
															+ v.name
															+ '</a></td>'
															+ '<td width="55" height="26" valign="middle">'
															+ clusterType
															+ '</td>'
															+ '<td width="64" height="26" valign="middle" align="center"><input type="radio" disabled="true" name="'
															+ DisasterTolerance1
															+ '" value="'
															+ type
															+ '" onclick="setSelectNo(this)" /></td>'
															+ '</tr>');
											if(isOk){
												linkageroleDevelopmentCheckBox();
											}
										}

									});
				}
			});
}
/**
 * 进入修改应用页面时，控制开发和测试两边角色的显示；
 * 相同的角色名称，当在开发中显示对应测试中要隐藏，
 * 当在测试中显示对应开发中要隐藏
 */
function controlUpdateAppPageRoleDisplayOrNot(){
	var roleDevelopment = document.getElementsByName("appUtil.roleDevelopment");
	for (var i = 0; i < roleDevelopment.length; i++) {
		var roleTestTabTr = $("#roleTestTab").find("tr").eq(i);
		if (roleDevelopment[i].checked) {
			roleTestTabTr.hide();
			continue;
		} else {
			roleTestTabTr.show();
			continue;
		}
	}
	
	var roleTest = document.getElementsByName("appUtil.roleTest");
	for (var i = 0; i < roleTest.length; i++) {
		if (roleTest[i].checked) {
			$("#roleDevTab").find("tr").eq(i).hide();
			continue;
		} else {
			$("#roleDevTab").find("tr").eq(i).show();
			continue;
		}
	}
	
	controlRoleDisplayOrNot();
}
/**
 * 根据hasOperType（角色在应用中被授予的操作类型）的值（1-开发，2-测试）；
 * 控制角色只在开发或测试中显示或隐藏
 */
function controlRoleDisplayOrNot(){
	var hasOperType = document.getElementsByName("hasOperType");
	for (var i = 0; i < hasOperType.length; i++) {
		 if(hasOperType[i].value == 1) {
			$("#roleTestTab").find("tr").eq(i).hide();
			continue;
		} else if (hasOperType[i].value == 2) {
			$("#roleDevTab").find("tr").eq(i).hide();
			continue;
		}
	}
}