package com.cmsz.paas.web.ipaasservice.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.constant.Constant;
import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.model.controller.request.AppId;
import com.cmsz.paas.common.model.controller.request.AppIdList;
import com.cmsz.paas.web.appservice.model.EnvConfig;
import com.cmsz.paas.web.appservice.service.ApplicationService;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.model.SelectType;
import com.cmsz.paas.web.base.util.GrafanaJsonFileUtil;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.grafana.service.GrafanaService;
import com.cmsz.paas.web.image.model.PublicImage;
import com.cmsz.paas.web.image.service.PublicImageService;
import com.cmsz.paas.web.ipaasinstance.model.IpaasInstance;
import com.cmsz.paas.web.ipaasinstance.service.IpaasInstanceService;
import com.cmsz.paas.web.ipaasservice.model.IpaasService;
import com.cmsz.paas.web.ipaasservice.model.IpaasServiceEntity;
import com.cmsz.paas.web.ipaasservice.service.IpaasServiceService;
import com.cmsz.paas.web.monitoroperation.model.Cluster;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;
import com.opensymphony.xwork2.ActionContext;

/**
 * 基础服务Action.
 * 
 * @author fubl
 */

public class IpaasServiceAction extends AbstractAction {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(IpaasServiceAction.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The application service. */
	@Autowired
	private IpaasServiceService ipaasServiceService;

	@Autowired
	private PublicImageService publicImageService;
	@Autowired
	private GrafanaService grafanaService;
	
	@Autowired
	private ApplicationService applicationService;

	/** Ipaas 页面展示model */
	private IpaasService ipaasService;

	@Autowired
	private IpaasInstanceService ipaasInstanceService;

	/** 基础服务模糊查询条件 */
	private String token = "";

	/** 基础服务ID */
	private String ipaasServiceId = "";

	/** 基础服务类型 1-zk、2-Redis、0-全部 */
	private int serviceType = 0;

	/** 错误信息. */
	private String errorMsg;

	/** 错误编码. */
	private String errorCode;

	/** 配置文件. */
	private File configFile;

	/** 配置文件名称. */
	private String configFileName;
	/** 监控类型 */
	private String type;
	/** 接收页面输入的数据 */
	private IpaasServiceEntity ipaasServiceEntity;

	private String functionModule;
	/** grafana url */
	private String url;

	private String operType;

	private String appId;

	private String minionIp;
	

	/**
	 * 查询基础服务列表. 查询条件：token，serviceType服务类型
	 */
	@SuppressWarnings("unchecked")
	public void queryIpaasServiceList() {
		logger.info("开始执行查询基础服务列表");
		Page<IpaasService> page = this.getJqGridPage("create_time");

		try {
			List<IpaasService> ipaasServieList = new ArrayList<IpaasService>();
			List<RolePermissionRelation> dataPermission = (List<RolePermissionRelation>) getSessionMap()
					.get("dataPermission");
			AppIdList appIdList = getAppListFromPermission(dataPermission);
			if (appIdList.getAppIdList().size() > 0) {
				ipaasServieList = ipaasServiceService.queryIpaasServiceList(
						appIdList, token, serviceType);
			}

			page.setResult(ipaasServieList);
			page.setTotalCount(ipaasServieList.size());
			this.renderText(JackJson.fromObjectToJson(page));

			logger.info("查询基础服务列表成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询基础服务出错", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 根据minionIp查询基础服务列表
	 * 
	 * @throws Exception
	 */
	public void queryIpaasService() throws Exception {
		Page<IpaasService> page = this.getJqGridPage("createTime");
		logger.info("开始执行系统监控-右侧列表-查询服务，节点Ip：" + minionIp);
		try {
			List<IpaasService> ipaasService = ipaasServiceService
					.queryIpaasServiceAndInstance(minionIp);
			page.setResult(ipaasService);
			page.setTotalCount(ipaasService.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("系统监控-右侧列表-查询服务完成 ，服务数：" + ipaasService.size());
		} catch (PaasWebException ex) {
			logger.error("系统监控-右侧列表-查询服务错误", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 根据minionIp,appIds,operType查询基础服务列表
	 * 
	 * @throws Exception
	 */
	public void queryNewIpaasService() throws Exception {
		Page<IpaasService> page = this.getJqGridPage("createTime");
		logger.info("开始执行系统监控-右侧列表-查询服务，节点Ip：" + minionIp);
		try {
			String appIds = getSessionMap().get("appPermissionId").toString();
			String operType = getSessionMap().get("opertype").toString();
			List<IpaasService> ipaasService = ipaasServiceService
					.queryNewIpaasServiceAndInstance(minionIp,appIds,operType);
			page.setResult(ipaasService);
			page.setTotalCount(ipaasService.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("系统监控-右侧列表-查询服务完成 ，服务数：" + ipaasService.size());
		} catch (PaasWebException ex) {
			logger.error("系统监控-右侧列表-查询服务错误", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 
	 * 删除基础服务
	 * 
	 * */
	public void deleteIpaasService() throws Exception {
		logger.info("开始删除基础服务,服务ID:" + ipaasServiceId);
		try {
			ipaasService = ipaasServiceService
					.queryIpaasServiceById(ipaasServiceId);
		} catch (Exception ex) {
			// 不处理异常
		}
		try {
			// 1.删除基础服务
			ipaasServiceService.deleteIpaasService(ipaasServiceId);
			try {
				// 删除grafana dashboards
				String title = GrafanaJsonFileUtil.getServiceType("1")
						+ "_"
						+ ipaasService.getApp_name()
						+ "_"
						+ GrafanaJsonFileUtil.getOperType(ipaasService
								.getOper_type()) + "_" + ipaasService.getName();
				grafanaService.deleteDashboard(title);
			} catch (Exception e) {
				logger.error("删除grafana dashboard错误", e);
			}
			logger.info("执行删除成功");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getErrorCode() + "] 执行删除基础服务出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/**
	 * 启动服务
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void startIpaasService() throws Exception {
		logger.info("开始执行启动服务，服务id：" + ipaasServiceId);
		String operateIds = Constant.START;
		try {
			RestResult result = ipaasServiceService.startOrStopIpaasService(
					ipaasServiceId, operateIds);

			if (RestResult.SUCCESS_CODE.equals(result.getRetCode())) {

				logger.info("启动服务成功！");

				sendSuccessReslult("SUCCESS");
			} else if (RestResult.COMPLETE_CODE.equals(result.getRetCode())) {

				logger.info("启动服务完成！");

				sendSuccessReslult("SUCCESS");
			} else {

				logger.info("启动服务失败 retCode:" + result.getRetCode() + ",msg:"
						+ result.getMsg());

				this.sendFailResult(result.getRetCode(), result.getMsg());
			}
		} catch (PaasWebException ex) {

			logger.error("启动服务错误 ", ex);

			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 停止服务
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void stopIpaasService() throws Exception {
		logger.info("开始执行停止服务，服务id：" + ipaasServiceId);
		String operateIds = Constant.STOP;
		try {
			RestResult result = ipaasServiceService.startOrStopIpaasService(
					ipaasServiceId, operateIds);

			if (RestResult.SUCCESS_CODE.equals(result.getRetCode())) {

				logger.info("停止服务成功！");

				sendSuccessReslult("SUCCESS");
			} else if (RestResult.COMPLETE_CODE.equals(result.getRetCode())) {

				logger.info("停止服务完成！");

				sendSuccessReslult("SUCCESS");
			} else {

				logger.info("停止服务失败 retCode:" + result.getRetCode() + ",msg:"
						+ result.getMsg());

				this.sendFailResult(result.getRetCode(), result.getMsg());
			}
		} catch (PaasWebException ex) {

			logger.error("停止服务错误 ", ex);

			if (ex.getKey().equals("PAAS-20924")) { // 强制停止基础服务
				this.sendFailResult(ex.getKey(), ex.getMessage());
			} else {
				this.sendFailResult(ex.getKey(), ex.toString());
			}
		}
	}

	/**
	 * 强制停止服务
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void forceStopIpaasService() throws Exception {
		logger.info("开始执行强制停止服务，服务id：" + ipaasServiceId);
		String operateIds = Constant.FORCE_STOP;
		try {
			RestResult result = ipaasServiceService.startOrStopIpaasService(
					ipaasServiceId, operateIds);

			if (RestResult.SUCCESS_CODE.equals(result.getRetCode())) {

				logger.info("强制停止服务成功！");

				sendSuccessReslult("SUCCESS");
			} else if (RestResult.COMPLETE_CODE.equals(result.getRetCode())) {

				logger.info("强制停止服务完成！");

				sendSuccessReslult("SUCCESS");
			} else {

				logger.info("强制停止服务失败 retCode:" + result.getRetCode() + ",msg:"
						+ result.getMsg());

				this.sendFailResult(result.getRetCode(), result.getMsg());
			}
		} catch (PaasWebException ex) {

			logger.error("强制停止服务错误 ", ex);

			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/***
	 * 检查是否有绑定基础服务或者基础服务全部为运行
	 */
	@UnLogging
	public void checkIpaasRelaAppService() {
		logger.info("开始执行检查是否有绑定基础服务或者基础服务全部为运行，应用服务ID：" + ipaasServiceId);
		try {
			ipaasServiceService.checkIpaasRelaAppService(ipaasServiceId);
			logger.info("执行检查成功!");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]检查出错", ex);
			if (ex.getKey().equals("PAAS-20924")) {
				this.sendFailResult(ex.getErrorCode(), ex.getMessage());
			} else {
				this.sendFailResult(ex.getErrorCode(), ex.toString());
			}
		}
	}

	/**
	 * 重启服务
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void restartIpaasService() throws Exception {
		logger.info("开始执行重启服务，服务id：" + ipaasServiceId);
		String operateIds = Constant.RESTART;
		try {
			RestResult result = ipaasServiceService.startOrStopIpaasService(
					ipaasServiceId, operateIds);

			if (RestResult.SUCCESS_CODE.equals(result.getRetCode())) {

				logger.info("重启服务成功！");

				sendSuccessReslult("SUCCESS");
			} else if (RestResult.COMPLETE_CODE.equals(result.getRetCode())) {
				logger.info("重启服务完成！");

				sendSuccessReslult("SUCCESS");
			} else {

				logger.info("重启服务失败 retCode:" + result.getRetCode() + ",msg:"
						+ result.getMsg());

				this.sendFailResult(result.getRetCode(), result.getMsg());
			}
		} catch (PaasWebException ex) {

			logger.error("重启服务错误 ", ex);

			if (ex.getKey().equals("PAAS-20924")) { // 强制停止基础服务
				this.sendFailResult(ex.getKey(), ex.getMessage());
			} else {
				this.sendFailResult(ex.getKey(), ex.toString());
			}
		}
	}

	/**
	 * 强制重启服务
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void forceRestartIpaasService() throws Exception {
		logger.info("开始执行强制重启服务，服务id：" + ipaasServiceId);
		String operateIds = Constant.FORCE_RESTART;
		try {
			RestResult result = ipaasServiceService.startOrStopIpaasService(
					ipaasServiceId, operateIds);

			if (RestResult.SUCCESS_CODE.equals(result.getRetCode())) {

				logger.info("强制重启服务成功！");

				sendSuccessReslult("SUCCESS");
			} else if (RestResult.COMPLETE_CODE.equals(result.getRetCode())) {
				logger.info("强制重启服务完成！");

				sendSuccessReslult("SUCCESS");
			} else {

				logger.info("强制重启服务失败 retCode:" + result.getRetCode() + ",msg:"
						+ result.getMsg());

				this.sendFailResult(result.getRetCode(), result.getMsg());
			}
		} catch (PaasWebException ex) {

			logger.error("强制重启服务错误 ", ex);

			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	private AppIdList getAppListFromPermission(
			List<RolePermissionRelation> dataPermission) {
		if (StringUtils.isNotBlank(operType)) {
			return getAppListFromPermission(dataPermission, 2);
		}
		return getAppListFromPermission(dataPermission, 1);
	}

	/*
	 * 根据session中的数据权限解析出应用列表
	 */
	private AppIdList getAppListFromPermission(
			List<RolePermissionRelation> dataPermission, int type) {

		List<AppId> list = new ArrayList<AppId>();
		AppIdList appIdList = new AppIdList();
		AppId appId;
		String appSelectedId = (String) getSessionMap().get("appPerSelectedId");
		String selectedOpertype = null;
		if (type == 1) {
			selectedOpertype = (String) getSessionMap().get("selectedOpertype");
		} else {
			selectedOpertype = operType;
		}
		if (StringUtils.isNotBlank(this.appId)) {
			appSelectedId = this.appId;
		}
		for (RolePermissionRelation roleDataPer : dataPermission) {
			appId = new AppId();
			appId.setId(Long.valueOf(roleDataPer.getPermissionId()));
			appId.setType(roleDataPer.getOpertype());

			list.add(appId);
		}

		appIdList.setAppIdList(list);

		if (appSelectedId.indexOf(",") == -1) {
			List<AppId> tempAppIds = new ArrayList<AppId>();
			try {
				for (AppId appIds : appIdList.getAppIdList()) {
					if (appSelectedId.equals(String.valueOf(appIds.getId()))) {
						tempAppIds.add(appIds);
					}
				}
			} catch (Exception e) {
				logger.error(new StringBuffer().append("应用服务过滤应用名称异常:")
						.append(e.getMessage()).toString());
			}
			appIdList.setAppIdList(tempAppIds);
		}
		if (selectedOpertype.indexOf(",") == -1) {
			List<AppId> tempAppIds = new ArrayList<AppId>();
			try {
				for (AppId appIds : appIdList.getAppIdList()) {
					if (selectedOpertype
							.equals(String.valueOf(appIds.getType()))) {
						tempAppIds.add(appIds);
					}
				}
			} catch (Exception e) {
				logger.error(new StringBuffer().append("应用服务过滤应用名称异常:")
						.append(e.getMessage()).toString());
			}
			appIdList.setAppIdList(tempAppIds);
		}
		return appIdList;
	}

	/**
	 * 查询基础服务详情. 查询条件：基础服务id
	 */
	public String queryIpaasServiceById() {
		logger.info("开始执行查询基础服务详情，基础服务ID：" + ipaasServiceId);
		try {
			ipaasService = ipaasServiceService
					.queryIpaasServiceById(ipaasServiceId);

			logger.info("执行查询基础服务详情成功！");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询基础服务详情出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
			errorCode = ex.getErrorCode();
			errorMsg = ex.toString();
		}

		return functionModule;
	}

	/**
	 * 根据基础服务类型查询镜像
	 */
	@UnLogging
	public void queryImagesByIpaasServiceTpye() {
		logger.info("开始执行根据基础服务类型查询镜像，基础服务类型：" + serviceType);
		List<SelectType> list = new ArrayList<SelectType>();
		String imageType = "";
		// 页面新建的时候要提交1，2.而这里查询公共镜像列表需要字符串
		if (serviceType == 1) {
			imageType = "zookeeper";
		} else if (serviceType == 2) {
			imageType = "redis";
		} else if (serviceType == 3) {
			imageType = "activemq";
		}
		try {
			List<PublicImage> publicImageList = publicImageService
					.queryPublicImageList("", "2", imageType, "");
			
			publicImageList = publicImageFilter(publicImageList);
			
			for (PublicImage publicImage : publicImageList) {
				SelectType selectType = new SelectType();
				selectType.setValue(publicImage.getId() + "_"
						+ publicImage.getVersionId());
				selectType.setText(publicImage.getName());
				list.add(selectType);
			}
			JSONArray jsonArray = JSONArray.fromObject(list);
			logger.info("镜像下拉框数据初始化完成：" + jsonArray.toString());
			sendSuccessReslult(jsonArray.toString());
		} catch (PaasWebException e) {
			logger.error("根据基础服务类型查询镜像错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	private List<PublicImage> publicImageFilter(List<PublicImage> list){
		String operType = getSessionMap().get("opertype").toString();
		if(operType.indexOf("3")==-1)return list;
		List<PublicImage> tempList = new ArrayList<PublicImage>();
		for (PublicImage l : list) {
			if(!l.getIsImported().equals("0")){
				tempList.add(l);
			}
		}
		return tempList;
		
		
	}

	/**
	 * 根据基础服务类型查询默认配置
	 */
	@UnLogging
	public void queryDefaultConfigByIpaasServiceTpye() {
		logger.info("开始执行根据基础服务类型查询默认配置，基础服务类型：" + serviceType);
		try {
			RestResult result = ipaasServiceService
					.queryDefaultConfigByIpaasServiceTpye(serviceType + "");
			sendSuccessReslult(result.getData());
			logger.info("根据基础服务类型查询默认配置完成！");
		} catch (PaasWebException e) {
			logger.error("根据基础服务类型查询默认配置错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	/**
	 * 导入配置文件
	 */
	public void uploadConfigFile() throws Exception {
		String fileContent = "";
		BufferedReader br = null;
		try {
			if(!configFile.isFile()){
				configFile.createNewFile();
			}
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					configFile), "GBK");
			br = new BufferedReader(isr);
			String lineSeparator = System.getProperty("line.separator");
			String line = null;
			while ((line = br.readLine()) != null) {
				fileContent += line + lineSeparator;
			}
			this.sendSuccessReslult(fileContent);
			logger.info("配置文件：" + configFileName + " 导入成功！ ");
		} catch (Exception e) {
			logger.error("配置文件导入错误！", e);
			this.sendFailResult(Constants.USER_UPLOAD_FILE_IO_EXCEPTION,
					e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("流关闭错误！", e);
				}
			}
		}
	}

	/**
	 * 创建基础服务
	 */
	public void createIpaasService() {
		logger.info("开始执行创建基础服务...");
		try {
			String appId = (String) getSessionMap().get("appPerSelectedId"); // 应用id
			String appName = (String) getSessionMap().get("appPerSelectedName"); // 应用名称
			String operateType = (String) getSessionMap().get(
					"selectedOpertype"); // 操作类型
			String loginName = (String) getSessionMap().get("loginName"); // 登录用户
			ipaasService.setApp_id(appId);
			ipaasService.setApp_name(appName);
			ipaasService.setOper_type(operateType);
			ipaasService.setUser_id(loginName);
			RestResult result = ipaasServiceService
					.createIpaasService(ipaasService);

			sendSuccessReslult(result.getData());
			logger.info("创建基础服务成功！");
		} catch (PaasWebException e) {
			logger.error("创建基础服务错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	/**
	 * 修改基础服务
	 */
	public void modifyIpaasService() {
		logger.info("开始执行修改基础服务,基础服务id：" + ipaasService.getId());
		try {
			ipaasServiceService.modifyIpaasService(ipaasService);
			sendSuccessReslult(ipaasService.getId());
			logger.info("修改基础服务成功！");
		} catch (PaasWebException e) {
			logger.error("修改基础服务错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	/**
	 * 根据基础服务id查询发布的环境变量
	 * 
	 * @return
	 */
	@UnLogging
	public void queryEnvsByIpaasServiceId() {
		logger.info("开始执行根据基础服务id查询发布的环境变量，基础服务id：" + ipaasServiceId);
		JSONObject confobj = new JSONObject();

		try {
			List<EnvConfig> ipaasEnvsList = ipaasServiceService
					.queryEnvsByIpaasServiceId(ipaasServiceId);
			for (EnvConfig envConfig : ipaasEnvsList) {
				String key = envConfig.getConfigKey();
				String value = envConfig.getConfigValue();

				confobj.put(key, value);
			}

			logger.info("根据基础服务id查询发布的环境变量完成：" + confobj.toString());
			sendSuccessReslult(confobj.toString());
		} catch (PaasWebException e) {
			logger.error("根据基础服务id查询发布的环境变量错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	@UnLogging
	public void queryGrafana() {
		logger.info("开始执行查询Grafana路径");
		try {
			ipaasService = ipaasServiceService
					.queryIpaasServiceById(ipaasServiceId);
			String title = GrafanaJsonFileUtil.getServiceType("1")
					+ "_"
					+ ipaasService.getApp_name()
					+ "_"
					+ GrafanaJsonFileUtil.getOperType(ipaasService
							.getOper_type()) + "_" + ipaasService.getName();
			List<IpaasInstance> list = ipaasInstanceService
					.queryIpaasServiceInstsById(ipaasServiceId);

			String json = GrafanaJsonFileUtil.getDashboardsJsonByIpaas(
					ipaasService.getApp_name(), ipaasService.getOper_type(),
					ipaasService.getName(), "1", list, type);
			grafanaService.createDashboard(json);

			url = RestUtils.grafanaUrl("queryGrafanaDashboardsUrl",
					title.toLowerCase());
			sendSuccessReslult(url);
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询Grafana路径出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
			errorCode = ex.getErrorCode();
			errorMsg = ex.toString();
		}
	}
	/**
	 * 根据appId,type获取集群下拉框
	 * 
	 * @return
	 */
	@UnLogging
	public void queryClusterByIdAndType(){
		try {
			String appSelectedId = (String) getSessionMap().get("appPerSelectedId");
			String opertype = (String) getSessionMap().get("selectedOpertype");
			//获取数据中心集合
			List<Cluster> list= applicationService.queryClusterList(appId, "1", "dataCenter",appSelectedId,opertype);
			this.renderText(JackJson.fromObjectToJson(list));
		} catch (PaasWebException e) {
			logger.error("查询DNS与集群异常", e);
			this.sendFailResult2jqGrid(e.getKey(),e.toString());
		}
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIpaasServiceId() {
		return ipaasServiceId;
	}

	public void setIpaasServiceId(String ipaasServiceId) {
		this.ipaasServiceId = ipaasServiceId;
	}

	public IpaasService getIpaasService() {
		return ipaasService;
	}

	public void setIpaasService(IpaasService ipaasService) {
		this.ipaasService = ipaasService;
	}

	public int getServiceType() {
		return serviceType;
	}

	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}

	public File getConfigFile() {
		return configFile;
	}

	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}

	public String getConfigFileName() {
		return configFileName;
	}

	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}

	public IpaasServiceEntity getIpaasServiceEntity() {
		return ipaasServiceEntity;
	}

	public void setIpaasServiceEntity(IpaasServiceEntity ipaasServiceEntity) {
		this.ipaasServiceEntity = ipaasServiceEntity;
	}

	public String getFunctionModule() {
		return functionModule;
	}

	public void setFunctionModule(String functionModule) {
		this.functionModule = functionModule;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMinionIp() {
		return minionIp;
	}

	public void setMinionIp(String minionIp) {
		this.minionIp = minionIp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Map<String, Object> getSessionMap() {
		return ActionContext.getContext().getSession();
	}

}
