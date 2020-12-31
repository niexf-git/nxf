package com.cmsz.paas.web.group4a.log;

public enum OperLogEnum {
	/** name操作日志内容,code编码系统管理. 角色管理+用户管理10001开始   **/
	createRole("角色管理创建","10001"),updateRole("角色管理更新","10002"),deleteRole("角色管理删除","10003")
	,addUser("用户管理新增","10004"),updateUser("用户管理更新","10005"),deleteUser("用户管理删除","10006")
	,roleGrantUser("角色管理人员分配","10007")
	,checkNotekey("4A管理验证","10013")
	,selfLogin("4A管理自登录","10015"),emergency4ALogin("4A管理紧急登录","10016"),verifyLoginParams("用户管理验证登录参数","10017")
	,login("用户管理登录","10018"),changeAppInfo("用户管理切换","10019")
	,queryUserList("用户管理查询","10020"),singleSignOn("4A管理单点登录","10021")
	
	
	/** 应用管理+服务管理+基础服务管理+实例 20001开始 */
	,createAppInfo("应用管理创建","20001"),updateAppInfo("应用管理更新","20002"),deleteAppInfo("应用管理删除","20003")
	,createAppService("服务管理创建","20004"),startAppService("服务管理启动","20005"),deleteAppService("服务管理删除","20006")
	,allStartAppService("服务管理一键启动","20007"),stopAppServices("服务管理停止","20010")
	,forcedRestartAppServices("服务管理强制重启","20011"),sendSignal2AppService("服务管理发送信号","20012")
	,batchUpgradeServiceVersions("服务管理一键升级","20013"),exportEnvsFile("服务管理导出","20014")
	,importEnvsFile("服务管理导入","20015"),uploadConfigFile("服务管理上传","20016"),modifyDeploymentType("灰度版本修改","20017")
	,deleteIpaasService("基础服务删除","20018"),startIpaasService("基础服务启动","20019"),stopIpaasService("基础服务停止","20020")
	,forceStopIpaasService("基础服务强制停止","20021"),forceRestartIpaasService("基础服务强制重启","20022")
	,restartIpaasService("基础服务重启","20023"),createIpaasService("基础服务创建","20024"),modifyIpaasService("基础服务修改","20025")
	,queryAppList("应用管理查询","20026"),importApp("应用管理导入","20027")
	,queryAppServiceList("服务管理查询","20028"),queryGray("灰度版本查询","20029")
	,uploadConfigFileGray("灰度版本上传","20030"),importEnvsFileGray("灰度版本导入","20031"),exportEnvsFileGray("灰度版本导出","20032")
	,deleteFileGray("灰度版本删除","20033"),queryIpaasServiceList("基础服务查询","20034")
	,queryInst("服务实例查询","20035"),restartInst("服务实例重启","20036"),forcedRestartInst("服务实例强制重启","20037")
	,dialogContainerDetails("服务实例实例详情","20036"),queryIpaasServiceInstsById("基础服务实例查询","20037")
	,diagnosisIpass("基础服务实例实例诊断","20038")
	
	
	
	/** CI/CD 30001开始 */
	,deleteBuild("构建管理删除","30001"),executeBuild("流水管理执行","30002"),modifyBuild("构建管理修改","30003")
	,createBuild("构建管理创建","30004"),createFlow("流水管理创建","30005"),updateFlow("流水管理更新","30006")
	,deleteFlow("流水管理删除","30007"),stopFlow("流水管理停止","30008"),copyFlow("流水管理复制","30009")
	,updateAutoTest("自动化测试更新","30011"),updateInteTest("集成测试更新","30012")
	,modifyCompileBuild("编译构建修改","30013"),modifyRelease("发布修改","30014"),modifyDepScan("部署扫描修改","30015")
	,updatePerformanceTest("性能测试更新","30016")
	,modifyIsCheckSecurity("修改是否安全状态","30017"),modifyCodeDownloadAndCheck("代码下载审查修改","30018")
	,modifyDep("部署修改","30019")
	,queryBuild("构建管理查询","30020"),queryFlowList("流水管理查询","30021")
	,queryAutoTest("自动化测试查询","30022"),queryCodeDetailsList("代码详情查询","30023")
	,queryFlowExcuteRecordList("流水执行记录查询","30024"),querySecurityScanReport("查看报告查询","30025")
	
	/** 镜像管理 40001 */
	,deletePrivateImage("私有镜像删除","40001"),updatePublicImage("公共镜像更新","40002")
	,changeStatus("公共镜像切换","40004")
	,publish2Test("私有镜像发布","40005"),updateImageDescription("私有镜像更新","40008")
	,publish2Product("公共镜像发布","40006")
	,queryPrivateImageList("私有镜像查询","40007"),queryPublicImageList("公共镜像查询","40009")
	
	/** 审计日志  50001开始*/
	,downloadChLogFile("日志管理下载","50003"),deleteFile("日志管理删除","50005")
	,queryAppLog("日志管理查询","50006")
	
	
	/** 监控运维  60001开始*/
	,startComponent("组件管理启动","60001"),restartComponent("组件管理重启","60002")
	,stopComponent("组件管理停止","60003"),reinstallComponent("组件管理重装","60004")
	,queryComponentInfoList("组件管理查询","60007")
	,modifyAlarmCondition("系统告警修改","60005"),queryAlarmList("系统告警查询","60006")
	
	/** 主机管理+应用告警  70001开始*/
	,createHost("主机管理创建","70001"),deleteHost("主机管理删除","70002")
	,queryAlarmByTime("应用告警查询","70003")
	,queryHostList("主机管理查询","70004")
	
	
	/** 数据中心 80001开始**/
	,createDataCenter("数据中心创建"," 80001"),deleteDataCenter("数据中心删除"," 80002")
	,queryDataCenterList("数据中心查询"," 80003"),updateDataCenterDescription("数据中心更新"," 80004")
	/** 集群管理  90001*/
	,createCluster("集群管理创建","90001"),deleteCluster("集群管理删除","90002")
	,queryClusterList("集群管理查询","90003")
	,updateDescription("集群管理更新","90003")
	;
	private String name;
	private String code;
	OperLogEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}
	public static String getNameByCode(String name){
		for(OperLogEnum ol:OperLogEnum.values()){
			if(name.equals(ol.getName())){
				return ol.getCode();
			}
		}
		return "99999";
	}
	public String getName() {
		return name;
	}
	protected void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
    protected void setCode(String code) {
		this.code = code;
	}
}

