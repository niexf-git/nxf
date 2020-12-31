package com.cmsz.paas.web.appservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.util.JSONUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.constant.Constant;
import com.cmsz.paas.common.model.controller.entity.AppServiceEntity;
import com.cmsz.paas.common.model.controller.entity.AppServiceVersionEntity;
import com.cmsz.paas.common.model.controller.entity.ClusterEntity;
import com.cmsz.paas.common.model.controller.entity.ConfigFileEntity;
import com.cmsz.paas.common.model.controller.entity.DataCenterEntity;
import com.cmsz.paas.common.model.controller.entity.EnvConfigEntity;
import com.cmsz.paas.common.model.controller.entity.IpaasToAppServiceEntity;
import com.cmsz.paas.common.model.controller.entity.K8sServiceEntity;
import com.cmsz.paas.common.model.controller.entity.Node;
import com.cmsz.paas.common.model.controller.entity.PrivateImageEntity;
import com.cmsz.paas.common.model.controller.entity.PublicImageEntity;
import com.cmsz.paas.common.model.controller.entity.PublishEnvEntity;
import com.cmsz.paas.common.model.controller.entity.ScalerEntity;
import com.cmsz.paas.common.model.controller.entity.ServicePortEntity;
import com.cmsz.paas.common.model.controller.request.AppIdList;
import com.cmsz.paas.common.model.controller.response.AppServiceDetail;
import com.cmsz.paas.common.model.controller.response.AppServiceList;
import com.cmsz.paas.common.model.controller.response.ClusterList;
import com.cmsz.paas.common.model.controller.response.IdValue;
import com.cmsz.paas.common.model.controller.response.NodeList;
import com.cmsz.paas.common.model.controller.response.TrueOrFalse;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.utils.BasePropertiesUtil;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.common.utils.JsonUtil;
import com.cmsz.paas.web.application.model.ClusterInfo;
import com.cmsz.paas.web.appservice.dao.AppServiceDao;
import com.cmsz.paas.web.appservice.model.AppService;
import com.cmsz.paas.web.appservice.model.EnvConfig;
import com.cmsz.paas.web.appservice.model.Ipaas;
import com.cmsz.paas.web.appservice.service.ApplicationService;
import com.cmsz.paas.web.appserviceinst.model.Instance;
import com.cmsz.paas.web.appserviceinst.service.InstanceService;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.monitoroperation.model.Cluster;
import com.google.gson.JsonArray;

/**
 * 
 * @author fubl
 * 
 */
@Service("appServiceService")
public class ApplicationServiceImpl implements ApplicationService {

	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationServiceImpl.class);

	@Autowired
	private AppServiceDao appServiceDao;
	
	@Autowired
	private InstanceService instanceService;
	
	/**
	 * 解析应用服务详情并生成页面展示model.
	 * 
	 * @param appServiceEntity
	 *            the app service entity
	 * @return the app service
	 */
	public AppService genAppService(AppServiceEntity appServiceEntity) {
		AppService appService = new AppService();
		appService.setGrayInstanceNum(appServiceEntity.getGrayInstanceNum());
		appService.setId(appServiceEntity.getId() + "");
		appService.setName(appServiceEntity.getName());

		appService.setApp_id(appServiceEntity.getAppId() + "");
		appService.setApp_name(appServiceEntity.getAppName());
		appService.setOper_type(appServiceEntity.getOperateType() + "");
		appService.setDeployType(appServiceEntity.getDeployMode());
		appService.setCpu(appServiceEntity.getCpu() + "");
		appService.setMaxCpu(appServiceEntity.getPeakCpu() + "");
		appService.setMem(appServiceEntity.getMem() + "");
		appService.setMaxMem(appServiceEntity.getPeakMem() + "");
		appService.setExistGray(appServiceEntity.getExistGray());//查询服务列表时有值
		// 实例伸缩
		String inst_num = appServiceEntity.getTotalInstanceNum() + "";
		ScalerEntity scaler = appServiceEntity.getScaler();
		if (inst_num.equals("0") && scaler != null) {
			appService.setCpu_target(scaler.getCpuTarget() + "");
			appService.setInst_min(scaler.getInstMin() + "");
			appService.setInst_max(scaler.getInstMax() + "");
			appService.setInst_scale_type("2");
		} else {
			appService.setInstance_num(inst_num);
			appService.setInst_scale_type("1");
		}

		appService.setRunning_Inst_num(appServiceEntity.getRunningInstanceNum()	+ "");
		appService.setStatus(appServiceEntity.getStatus() + "");
		appService.setConfig_effect(appServiceEntity.getConfigEffect() + "");
		appService.setUser_id(String.valueOf(appServiceEntity.getCreateBy()));
		appService.setCreate_time(DateUtil.tranformDate(appServiceEntity
				.getCreateTime().toString()) + "");
		appService.setDescription(appServiceEntity.getDescription() + "");
		
		DataCenterEntity ataCenterEntity = appServiceEntity.getDataCenterEntity();
		if(ataCenterEntity != null){
			appService.setLogServerIp(ataCenterEntity.getFlumeIp());
		}

		ClusterEntity cluster = appServiceEntity.getCluster();
		if (cluster != null) {
			appService.setCluster_name(cluster.getName());
			appService.setCluster_id(cluster.getId() + "");
		}
		
		//服务版本列表
		List<AppServiceVersionEntity> appServiceVersionList = appServiceEntity.getVersions();
		if(appServiceVersionList != null){
			for(AppServiceVersionEntity appServiceVersionEntity : appServiceVersionList){
				//1-正式版本，2-灰度版本
				if(appServiceVersionEntity.getType() == 1){
					//日志路径
					appService.setLog_dir(appServiceVersionEntity.getLogDir());
					//配置文件
					ConfigFileEntity configFileEntity = appServiceVersionEntity.getConfigFile();
					if(configFileEntity != null){
						appService.setConfigFilePath(configFileEntity.getConfigDir());
						appService.setConfigFileInfo(configFileEntity.getContent());
					}
					// 环境变量
					List<EnvConfig> genConfigList = genConfigList(appServiceVersionEntity.getEnvs());
					appService.setEnvConfig(genConfigList);
					appService.setConfigJson(JackJson.fromObjectToJson(genConfigList));
					
					int imageType = appServiceVersionEntity.getImageType();
					if (imageType == 1) {// 公共镜像
						PublicImageEntity publicImage = appServiceVersionEntity.getPublicImage();
						if (publicImage != null) {
							appService.setImage_id(publicImage.getId() + "");
							appService.setImage_name(publicImage.getName() + "");
							appService.setImage_type("1");
							appService.setImage_version(publicImage.getRunningVersion()
									.getVersion());
							appService.setImage_version_id(publicImage.getRunningVersion()
									.getId() + "");
						}
					}else if (imageType == 2) {// 私有镜像
						PrivateImageEntity privateImage = appServiceVersionEntity.getPrivateImage();
						if (privateImage != null) {
							appService.setImage_id(privateImage.getPid() + "");
							appService.setImage_name(privateImage.getName() + "");
							appService.setImage_type("2");
							appService.setImage_version(privateImage.getRunningVersion()
									.getVersion());
							appService.setImage_version_id(privateImage.getRunningVersion()
									.getId() + "");
						}
					} 
				}else if(appServiceVersionEntity.getType() == 2){//2-灰度版本
					appService.setExistGray(1);//查询详情里控制中心existGray字段没有赋值(默认都是0)，查询列表有赋值（但没有把灰度版本放里面，所以这个分支不会执行）
					int imageType = appServiceVersionEntity.getImageType();
					if (imageType == 1) {// 公共镜像
						PublicImageEntity publicImage = appServiceVersionEntity.getPublicImage();
						if (publicImage != null) {
							appService.setGrayImageType("1");
							appService.setGrayImageId(publicImage.getId() + "");
							appService.setGrayImageVersionId(publicImage.getRunningVersion()
									.getId() + "");
						}
					}else if (imageType == 2) {// 私有镜像
						PrivateImageEntity privateImage = appServiceVersionEntity.getPrivateImage();
						if (privateImage != null) {
							appService.setGrayImageType("2");
							appService.setGrayImageId(privateImage.getPid() + "");
							appService.setGrayImageVersionId(privateImage.getRunningVersion()
									.getId() + "");
						}
					}
				}
			}
		}
		//指定IP
		appService.setHostIpFlag(String.valueOf(appServiceEntity.getModel()));
		appService.setHostIp(appServiceEntity.getIp());
		
		// 外部服务
		K8sServiceEntity service = appServiceEntity.getK8sService();
		if (service != null) {
			appService.setServiceFlag("2");
			appService.setContainerPort(service.getPorts().get(0).getContainerPort() + "");
			appService.setProtocolType(service.getProtocolType());
			appService.setService_url(service.getUrl());
			appService.setExtServiceType(service.getIsExternal() + "");
		} else {
			appService.setServiceFlag("1");// 不是服务
		}

		// ipaas
		List<Ipaas> ipaasList = genIpaasList(appServiceEntity.getIpaases());
		appService.setIpaas(ipaasList);
		appService.setIpaas_configJson(JackJson.fromObjectToJson(ipaasList));
		
		// 实例列表
		List<com.cmsz.paas.common.model.controller.entity.Instance> instanceList = appServiceEntity.getInstances();
		if (instanceList != null) {
			List<Instance> instances = new ArrayList<Instance>();
			for (int i = 0; i < instanceList.size(); i++) {
				Instance inst = instanceService.translateInsts(instanceList.get(i));
				instances.add(inst);
			}
			appService.setInstances(instances);
		}
		return appService;
	}

	/**
	 * 环境变量配置转换，包括应用服务和ipaas服务的
	 * 
	 * @param listconfig
	 *            配置集合
	 * @return
	 */
	private List<EnvConfig> genConfigList(List<?> listconfig) {
		logger.debug("开始转换环境变量信息...");
		List<EnvConfig> list = null;
		if (listconfig != null && listconfig.size() != 0) {
			EnvConfig envConfig;
			list = new ArrayList<EnvConfig>();
			if (listconfig.get(0) instanceof EnvConfigEntity) {// 应用服务的环境变量
				for (Object e : listconfig) {
					envConfig = new EnvConfig();

					envConfig
							.setConfigKey(((EnvConfigEntity) e).getConfigKey());
					envConfig.setConfigValue(((EnvConfigEntity) e)
							.getConfigValue());
					list.add(envConfig);
				}
			}
			if (listconfig.get(0) instanceof PublishEnvEntity) {// ipaas服务的环境变量
				for (Object e : listconfig) {
					envConfig = new EnvConfig();

					envConfig.setConfigKey(((PublishEnvEntity) e)
							.getConfigKey());
					envConfig.setConfigValue(((PublishEnvEntity) e)
							.getConfigValue());
					list.add(envConfig);
				}
			}

		}
		logger.debug("转换环境变量信息成功！");
		return list;
	}

	/**
	 * Ipaas服务信息配置转换
	 * 
	 * @param listconfig
	 *            配置集合
	 * @return
	 */
	private List<Ipaas> genIpaasList(List<IpaasToAppServiceEntity> ipaasList) {
		logger.debug("开始转换配置信息ipaas...");
		List<Ipaas> list = null;
		if (ipaasList != null) {
			Ipaas ipaas;
			list = new ArrayList<Ipaas>();
			for (IpaasToAppServiceEntity e : ipaasList) {
				ipaas = new Ipaas();
				ipaas.setId(e.getIpaasServiceId());
				ipaas.setName(e.getIpaas().getName());
				ipaas.setBindStr(e.getBindStr());
				ipaas.setType(e.getIpaas().getServiceType() + "");
				ipaas.setEnvs(JackJson.fromObjectToJson(genConfigList(e
						.getIpaas().getPublishEnvsList())));// 将环境变量list转换成json串

				list.add(ipaas);
			}
		}
		logger.debug("转换配置信息ipaas成功！");
		return list;
	}

	@Override
	public List<AppService> queryAppServiceList(AppIdList appIdList,
			String token) throws PaasWebException {
		List<AppService> appServiceList = new ArrayList<AppService>();
		String msg = "";
		String retCode = "";
		AppServiceList appServiceListJson = new AppServiceList();
		String url = RestUtils
				.restUrlTransform("queryAppServiceListUrl", token);
		logger.debug("开始调用查询应用服务的rest接口：" + url);

		try {
			ResponseInfo responseInfo = appServiceDao.create(url, appIdList,
					AppServiceList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			appServiceListJson = (AppServiceList) responseInfo.getData();
			logger.debug("调用查询应用服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (!appServiceListJson.equals(null)) {
					for (AppServiceEntity appServiceEntity : appServiceListJson
							.getAppServiceList()) {
						appServiceList.add(genAppService(appServiceEntity));
					}
				}

				logger.info("调用查询应用服务的rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询应用服务列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_APPSERVICELIST_ERROR
						+ "]查询应用服务列表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_APPSERVICELIST_ERROR, ex.getMessage());
			}
		}
		return appServiceList;
	}

	@Override
	public AppService queryAppServiceById(String appServiceId)
			throws PaasWebException {
		AppService appService = null;
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryAppServiceById",
				appServiceId);
		logger.debug("开始调用查询应用服务详情的rest接口：" + url);
		try {
			ResponseInfo responseInfo = appServiceDao.get(url,
					AppServiceDetail.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询应用服务详情的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			AppServiceDetail appServiceJson = (AppServiceDetail) responseInfo
					.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (appServiceJson != null) {
					AppServiceEntity appServiceEntity = appServiceJson
							.getAppServiceDetail();
					appService = genAppService(appServiceEntity);
				}
				logger.info("调用查询应用服务详情Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询应用服务详情出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_APPSERVICEDETAIL_ERROR
						+ "]查询应用服务详情出错", ex);
				throw new PaasWebException(
						Constants.QUERY_APPSERVICEDETAIL_ERROR, ex.getMessage());
			}
		}
		return appService;
	}

	@Override
	public void batchStartAppServices(String appServiceIds)
			throws PaasWebException {
		String retCode = "";
		String msg = "";
		String url = RestUtils.restUrlTransform("batchOperationAppServiceUrl",
				Constant.START, appServiceIds);

		logger.debug("开始调用批量启动应用服务的rest接口：" + url);
		try {
			ResponseInfo responseInfo = appServiceDao.update(url,
					ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用批量启动应用服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (!retCode.equals(Constants.REST_CODE_SUCCESS)) {
				throw new PaasWebException(retCode, msg);
			}
			logger.info("调用批量启动应用服务的rest接口成功！");
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]批量启动应用服务出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.BATCH_START_APPSERVICE_ERROR
						+ "]批量启动应用服务出错", ex);
				throw new PaasWebException(
						Constants.BATCH_START_APPSERVICE_ERROR, ex.getMessage());
			}
		}
	}

	@Override
	public void batchStopAppServices(String appServiceIds)
			throws PaasWebException {
		String retCode = "";
		String msg = "";
		String url = RestUtils.restUrlTransform("batchOperationAppServiceUrl",
				Constant.STOP, appServiceIds);

		logger.debug("开始调用批量停止应用服务的rest接口：" + url);
		try {
			ResponseInfo responseInfo = appServiceDao.update(url,
					ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用批量停止应用服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (!retCode.equals(Constants.REST_CODE_SUCCESS)) {
				throw new PaasWebException(retCode, msg);
			}
			logger.info("调用批量停止应用服务的rest接口成功！");
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]批量停止应用服务出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.BATCH_STOP_APPSERVICE_ERROR
						+ "]批量停止应用服务出错", ex);
				throw new PaasWebException(
						Constants.BATCH_STOP_APPSERVICE_ERROR, ex.getMessage());
			}
		}
	}

	@Override
	public void forcedRestartAppServices(String appServiceIds)
			throws PaasWebException {
		String retCode = "";
		String msg = "";
		String url = RestUtils.restUrlTransform("batchOperationAppServiceUrl",
				Constant.RESTART, appServiceIds);

		logger.debug("开始调用批量重启应用服务的rest接口：" + url);
		try {
			ResponseInfo responseInfo = appServiceDao.update(url,
					ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用批量重启应用服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (!retCode.equals(Constants.REST_CODE_SUCCESS)) {
				throw new PaasWebException(retCode, msg);
			}
			logger.info("调用批量重启应用服务的rest接口成功！");
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]批量重启应用服务出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.BATCH_RESTART_APPSERVICE_ERROR
						+ "]批量重启应用服务出错", ex);
				throw new PaasWebException(
						Constants.BATCH_RESTART_APPSERVICE_ERROR,
						ex.getMessage());
			}
		}
	}

	@Override
	public void batchDeleteAppServices(String appServiceIds)
			throws PaasWebException {
		String retCode = "";
		String msg = "";
		String url = RestUtils.restUrlTransform("batchOperationAppServiceUrl",
				Constant.DELETE, appServiceIds);

		logger.debug("开始调用批量删除应用服务的rest接口：" + url);
		try {
			ResponseInfo responseInfo = appServiceDao.update(url,
					ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用批量删除应用服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (!retCode.equals(Constants.REST_CODE_SUCCESS)) {
				throw new PaasWebException(retCode, msg);
			}
			logger.info("调用批量删除应用服务的rest接口成功！");
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]批量删除应用服务出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.BATCH_DELETE_APPSERVICE_ERROR
						+ "]批量删除应用服务出错", ex);
				throw new PaasWebException(
						Constants.BATCH_DELETE_APPSERVICE_ERROR,
						ex.getMessage());
			}
		}
	}

	@Override
	public RestResult sendSignal2AppService(String appServiceIds, String signal)
			throws PaasWebException {
		RestResult result = new RestResult();
		String url = RestUtils.restUrlTransform("sendSignal2AppServiceUrl",
				appServiceIds, signal);
		logger.debug("开始调用发送消息的rest接口：" + url);
		String retCode = "";
		String msg = "";
		try {
			ResponseInfo responseInfo = appServiceDao.update(url,
					ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用发送消息的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				result.setMsg(msg);
				result.setRetCode(retCode);
				logger.info("调用发送消息Rest成功！");
				return result;
			} else {
				throw new PaasWebException(retCode, msg);
			}

		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]发送消息出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.SEND_SIGNAL_TO_APPSERVICE_ERROR
						+ "]发送消息出错", ex);
				throw new PaasWebException(
						Constants.SEND_SIGNAL_TO_APPSERVICE_ERROR,
						ex.getMessage());
			}
		}
	}

	@Override
	public void deleteAppService(String appServiceId) throws PaasWebException {
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("deleteAppServiceUrl",
					appServiceId);
			logger.debug("开始调用删除应用服务的rest接口：" + url);
			ResponseInfo responseInfo = appServiceDao.delete(url,
					ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用删除应用服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (!retCode.equals(Constants.REST_CODE_SUCCESS)) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]删除应用服务出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.DELETE_APPSERVICE_ERROR
						+ "]删除应用服务出错", ex);
				throw new PaasWebException(Constants.DELETE_APPSERVICE_ERROR,
						ex.getMessage());
			}
		}
	}

	@Override
	public IdValue createAppService(AppService appService)
			throws PaasWebException {
		logger.info("收到创建应用服务请求，appService：" + appService.toString());
		IdValue idValue = new IdValue();
		AppServiceEntity appServiceEntity = genAppServiceEntity(appService);
		String url = RestUtils.restUrlTransform("creatAppServiceUrl", "");
		ResponseInfo responseInfo;
		String retCode = "";
		String msg = "";
		try {
			logger.debug("开始调用创建应用服务的rest接口：" + url);
			responseInfo = appServiceDao.create(url, appServiceEntity,
					IdValue.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.CONTROLLER_CONN_ERROR,
						"无法访问创建应用服务的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用创建应用服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				idValue = (IdValue) responseInfo.getData();
				logger.info("调用创建应用服务的rest接口返回成功！应用服务ID:" + idValue.getId());

			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]创建应用服务出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.CREATE_APPSERVICE_ERROR
						+ "]创建应用服务出错", ex);
				throw new PaasWebException(Constants.CREATE_APPSERVICE_ERROR,
						ex.getMessage());
			}
		}
		return idValue;
	}

	/**
	 * 根据界面传值的应用服务model转换成控制中心需要的应用服务entity
	 * 
	 * @param appService
	 * @return
	 */
	@Override
	public AppServiceEntity genAppServiceEntity(AppService appService) {
		AppServiceEntity appServiceEntity = new AppServiceEntity();
		if (appService.getApp_id() == null) {
			appServiceEntity.setAppId(-1);
		} else {
			appServiceEntity.setAppId(Long.parseLong(appService.getApp_id()));
		}
		if (appService.getOper_type() == null) {
			appServiceEntity.setOperateType(-1);
		} else {
			appServiceEntity.setOperateType(Integer.valueOf(appService
					.getOper_type()));
		}
		
		appServiceEntity.setFlowId(appService.getFlowId());

		appServiceEntity.setName(appService.getName());
		appServiceEntity.setCreateBy(appService.getUser_id());
		appServiceEntity.setDescription(appService.getDescription());
		appServiceEntity.setClusterId(Long.valueOf(appService.getCluster_id()));// 集群id
		// cpu、内存
		appServiceEntity.setMem(Integer.valueOf(appService.getMem()));
		appServiceEntity.setPeakMem(Integer.valueOf(appService.getMaxMem()));
		appServiceEntity.setCpu(Double.valueOf(appService.getCpu()));
		appServiceEntity.setPeakCpu(Double.valueOf(appService.getMaxCpu()));

		// 服务
		String serviceFlag = appService.getServiceFlag();
		if (serviceFlag.equals("2")) {// 2表示外部服务
			K8sServiceEntity k8sService = new K8sServiceEntity();
			k8sService.setIsExternal(Integer.valueOf(appService
					.getExtServiceType()));
			List<ServicePortEntity> ports = new ArrayList<ServicePortEntity>();
			ServicePortEntity portEntity = new ServicePortEntity();
			portEntity.setContainerPort(Integer.valueOf(appService
					.getContainerPort()));
			ports.add(portEntity);
			k8sService.setPorts(ports);
			k8sService.setProtocolType(appService.getProtocolType());
			appServiceEntity.setK8sService(k8sService);
		} else if (serviceFlag.equals("1")) {// 1表示内部服务
			appServiceEntity.setK8sService(null);
		}
		//指定主机IP
		String hostIpFlag = appService.getHostIpFlag();
		if(hostIpFlag.equals("1")){
			appServiceEntity.setIp(appService.getHostIp());
		}
		appServiceEntity.setModel(Integer.valueOf(hostIpFlag));
		// 实例数
		String instScaleType = appService.getInst_scale_type();
		if (instScaleType.equals("1")) {// 固定-1
			appServiceEntity.setTotalInstanceNum(Integer.valueOf(appService
					.getInstance_num()));
		} else if (instScaleType.equals("2")) {// 动态-2
			ScalerEntity scaler = new ScalerEntity();
			scaler.setInstMin(Integer.valueOf(appService.getInst_min()));
			scaler.setInstMax(Integer.valueOf(appService.getInst_max()));
			scaler.setCpuTarget(Integer.valueOf(appService.getCpu_target()));
			appServiceEntity.setScaler(scaler);
		}

		// 基础服务
		if (appService.getIpaas() != null) {
			IpaasToAppServiceEntity ipaasEntity;
			List<IpaasToAppServiceEntity> ipaasList = new ArrayList<IpaasToAppServiceEntity>();
			for (Ipaas ipaas : appService.getIpaas()) {
				ipaasEntity = new IpaasToAppServiceEntity();
				ipaasEntity.setBindStr(ipaas.getBindStr());
				ipaasEntity.setIpaasServiceId(ipaas.getName());

				ipaasList.add(ipaasEntity);
			}
			appServiceEntity.setIpaases(ipaasList);
		} else {
			appServiceEntity.setIpaases(null);
		}
		
		//应用服务版本信息，包括：镜像信息和环境变量
		List<AppServiceVersionEntity> AppServiceVersionList = new ArrayList<AppServiceVersionEntity>();
		AppServiceVersionEntity appServiceVersionEntity = new AppServiceVersionEntity();
		appServiceVersionEntity.setType(1);//1-正式版本，2-灰度版本
		//镜像信息
		appServiceVersionEntity.setImageType(Integer.valueOf(appService.getImage_type()));
		appServiceVersionEntity.setImageId(Long.valueOf(appService.getImage_name()));
		appServiceVersionEntity.setRunningVersion(Long.valueOf(appService.getImage_version()));
		appServiceVersionEntity.setLogDir(appService.getLog_dir());
		// 环境变量
		if (appService.getEnvConfig() != null) {
			EnvConfigEntity envEntity;
			List<EnvConfigEntity> envsList = new ArrayList<EnvConfigEntity>();
			for (EnvConfig envConfig : appService.getEnvConfig()) {
				if(envConfig== null)continue;
				envEntity = new EnvConfigEntity();
				envEntity.setConfigKey(envConfig.getConfigKey());
				envEntity.setConfigValue(envConfig.getConfigValue());

				envsList.add(envEntity);
			}
			appServiceVersionEntity.setEnvs(envsList);
		} else {
			appServiceVersionEntity.setEnvs(null);
		}
		//配置文件
		ConfigFileEntity configFileEntity = new ConfigFileEntity();
		configFileEntity.setConfigDir(appService.getConfigFilePath());
		configFileEntity.setContent(appService.getConfigFileInfo());
		
		if("".equals(appService.getConfigFilePath()) && "".equals(appService.getConfigFileInfo())){
			configFileEntity = null;
		}
		appServiceVersionEntity.setConfigFile(configFileEntity);
		AppServiceVersionList.add(appServiceVersionEntity);
		appServiceEntity.setVersions(AppServiceVersionList);
		
		return appServiceEntity;
	}

	@Override
	public void checkAppServiceRelaIpaas(String appServiceId) throws PaasWebException {
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("checkAppServiceRelaIpaas", appServiceId);
			logger.debug("开始调用检查的rest接口：" + url);
			ResponseInfo responseInfo = appServiceDao.get(url, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用检查的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (!(retCode.equals(Constants.REST_CODE_SUCCESS) || retCode.equals(Constants.REST_CODE_COMPLETE))) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]检查出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.CHECK_IPAAS_ERROR
						+ "]检查出错", ex);
				throw new PaasWebException(Constants.CHECK_IPAAS_ERROR,
						ex.getMessage());
			}
		}
	}
	
	@Override
	public void startAppService(String appServiceId) throws PaasWebException {
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("operationAppServiceUrl",
					appServiceId,Constant.START);
			logger.debug("开始调用启动应用服务的rest接口：" + url);
			ResponseInfo responseInfo = appServiceDao.update(url,
					ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用启动应用服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (!(retCode.equals(Constants.REST_CODE_SUCCESS) || retCode.equals(Constants.REST_CODE_COMPLETE))) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]启动应用服务出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.DELETE_APPSERVICE_ERROR
						+ "]启动应用服务出错", ex);
				throw new PaasWebException(Constants.DELETE_APPSERVICE_ERROR,
						ex.getMessage());
			}
		}
	}
	
	/**
	 * 一键启动应用服务和绑定的基础服务
	 */
	@Override
	public void allStartAppService(String appServiceId) throws PaasWebException {
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("operationAppServiceUrl",
					appServiceId,Constant.ALL_START);
			logger.debug("开始调用一键启动应用服务的rest接口：" + url);
			ResponseInfo responseInfo = appServiceDao.update(url,
					ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用一键启动应用服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (!(retCode.equals(Constants.REST_CODE_SUCCESS) || retCode.equals(Constants.REST_CODE_COMPLETE))) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]一键启动应用服务出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.DELETE_APPSERVICE_ERROR
						+ "]一键启动应用服务出错", ex);
				throw new PaasWebException(Constants.DELETE_APPSERVICE_ERROR,
						ex.getMessage());
			}
		}
	}
	
	/**
	 * 启动应用服务自身
	 */
	@Override
	public void selfStartAppService(String appServiceId) throws PaasWebException {
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("operationAppServiceUrl",
					appServiceId,Constant.SELF_START);
			logger.debug("开始调用启动应用服务的rest接口：" + url);
			ResponseInfo responseInfo = appServiceDao.update(url,
					ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用启动应用服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (!(retCode.equals(Constants.REST_CODE_SUCCESS) || retCode.equals(Constants.REST_CODE_COMPLETE))) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]启动应用服务出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.DELETE_APPSERVICE_ERROR
						+ "]启动应用服务出错", ex);
				throw new PaasWebException(Constants.DELETE_APPSERVICE_ERROR,
						ex.getMessage());
			}
		}
	}

	@Override
	public List<Cluster> queryClusterList(String appId, String operType,String type,String appSelectedId,String operTypes)
			throws PaasWebException {
		List<Cluster> list = new ArrayList<Cluster>();
		String retCode = "";
		String msg = "";
		try {
			if("".equals(appId)){
				return list;
			}
			String url = RestUtils.restUrlTransform("queryClusterByAppUrls", appId, operType);
			if("dataCenter".equals(type)){
				//此appId为数据中心ID
				url = RestUtils.restUrlTransform("queryClusterListUrls", appId, operType,appSelectedId,operTypes);
			}
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = appServiceDao.get(url,ClusterList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			ClusterList clusterList = (ClusterList) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (clusterList != null && clusterList.getClusterList() != null) {
					List<Cluster> list1 = new ArrayList<Cluster>();
					for (int i = 0; i < clusterList.getClusterList().size(); i++) {
						ClusterEntity clusterEntity = clusterList.getClusterList().get(i);
						Cluster cluster = translateCluster(clusterEntity);
						if (cluster.getType().equals("2")) {
							list1.add(cluster);
						} else if (cluster.getType().equals("1")) {
							list.add(cluster);
						}
					}
					for (int i = 0; i < list1.size(); i++) {
						list.add(list1.get(i));
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询集群错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_CLUSTER_LIST_ERROR,
						ex.getMessage());
			}
		}
		return list;
	}
	
	/**
	 * 转换集群.
	 * 
	 * @param clusterEntity
	 *            控制中心集群bean
	 * @return 前台使用的集群bean
	 */
	private Cluster translateCluster(ClusterEntity clusterEntity) {
		Cluster cluster = new Cluster();
		cluster.setId(clusterEntity.getId()+"");
		cluster.setName(clusterEntity.getName());
		cluster.setLabel(clusterEntity.getLabel());
		cluster.setDescription(clusterEntity.getDescription());
		cluster.setType(clusterEntity.getType()+"");
		return cluster;
	}

	@Override
	public List<AppService> queryAppServiceAndInstance(String minionIp)
			throws PaasWebException {
		List<AppService> list = new ArrayList<AppService>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryAppServiceInstsListUrl", minionIp);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = appServiceDao.get(url, AppServiceList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			AppServiceList appServiceList = (AppServiceList) responseInfo
					.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (appServiceList != null
						&& appServiceList.getAppServiceList() != null) {
					for (int i = 0; i < appServiceList.getAppServiceList().size(); i++) {
						AppServiceEntity appServiceEntity = appServiceList.getAppServiceList().get(i);
						AppService appService = genAppService(appServiceEntity);
						list.add(appService);
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询minion上的应用服务（包括实例）错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_APPSERVICE_INST_ERROR, ex.getMessage());
			}
		}
		return list;
	}

	@Override
	public List<String> queryCluserHostIpList(long cluserId)
			throws PaasWebException {
		List<String> list = new ArrayList<String>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryCluserHostIpUrl", cluserId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = appServiceDao.get(url, NodeList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			NodeList nodes = (NodeList) responseInfo
					.getData();
			
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (nodes != null && nodes.getNodeList() != null) {
					for (Node node : nodes.getNodeList()) {
						list.add(node.getHostIP());
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询集群下的主机错误:", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_CLUSER_HOST_IP_ERROR, ex.getMessage());
			}
		}
		return list;
	}

	@Override
	public List<AppService> queryNewAppServiceAndInstance(String minionIp,
			String appIds, String operType) throws PaasWebException {
		List<AppService> list = new ArrayList<AppService>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryNewAppServiceInstsListUrl", minionIp,appIds,operType);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = appServiceDao.get(url, AppServiceList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			AppServiceList appServiceList = (AppServiceList) responseInfo
					.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (appServiceList != null
						&& appServiceList.getAppServiceList() != null) {
					for (int i = 0; i < appServiceList.getAppServiceList().size(); i++) {
						AppServiceEntity appServiceEntity = appServiceList.getAppServiceList().get(i);
						AppService appService = genAppService(appServiceEntity);
						list.add(appService);
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询minion上的应用服务（包括实例）错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_APPSERVICE_INST_ERROR, ex.getMessage());
			}
		}
		return list;
	}

	@Override
	public void batchUpgradeServiceVersions(String ids) throws PaasWebException{

		String retCode = "";
		String msg = "";
		String url = RestUtils.restUrlTransform("batchUpgradeServiceVersionsUrl",ids);
		System.out.println("一键升级URL："+url);
		logger.info("开始调用一键升级服务版本的rest接口：" + url);
		try {
			ResponseInfo responseInfo = appServiceDao.get(url, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用一键升级服务版本的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (!retCode.equals(Constants.REST_CODE_SUCCESS)) {
				throw new PaasWebException(retCode, msg);
			}
			logger.info("调用一键升级服务版本的rest接口成功！");
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]一键升级服务版本出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.BATCH_UPGRADE_APPSERVICE_VERSION_ERROR
						+ "]一键升级服务版本出错", ex);
				throw new PaasWebException(
						Constants.BATCH_UPGRADE_APPSERVICE_VERSION_ERROR, ex.getMessage());
			}
		}
	
	}

	@Override
	public boolean isCheckIp(String appServiceId,String ip) throws PaasWebException {
		boolean flag = false;
		String retCode = "";
		String msg = "";
		String url = RestUtils.restUrlTransform("isCheckIpUrl",ip,appServiceId);
		System.out.println("校验IP是否冲突URL："+url);
		logger.info("开始校验Ip是否冲突的rest接口：" + url);
		try {
			ResponseInfo responseInfo = appServiceDao.get(url, TrueOrFalse.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用校验IP是否冲突的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				TrueOrFalse tf = (TrueOrFalse) responseInfo.getData();
				flag = tf.isBool();
			}
			logger.info("调用校验IP是否冲突的rest接口成功！");
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]校验IP是否冲突出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.IS_CHECK_IP_ERROR
						+ "]校验IP是否冲突出错", ex);
				throw new PaasWebException(
						Constants.IS_CHECK_IP_ERROR, ex.getMessage());
			}
		}
	   return flag;
	}

	@Override
	public String isExcess(String type, String instNumber, String appIds,String types, String cpuNumber, String MemNumber,String appServiceId) throws PaasWebException {
		String result = "";
		String retCode = "";
		String msg = "";
		String url = RestUtils.restUrlTransform("isExcessUrl",type,instNumber,appIds,types,cpuNumber,MemNumber,appServiceId);
		System.out.println("校验CPU、内存是否超额URL："+url);
		logger.info("开始调用校验CPU、内存是否超额的rest接口：" + url);
		try {
			ResponseInfo responseInfo = appServiceDao.get(url, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用校验CPU、内存是否超额的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if(retCode.equals(Constants.REST_CODE_SUCCESS)) {
				result = "0";
			}else {
				result = BasePropertiesUtil.getValue(retCode);
			}
			logger.info("调用校验CPU、内存是否超额的rest接口成功！");
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]校验CPU、内存是否超额出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.IS_EXCESS_ERROR
						+ "]校验CPU、内存是否超额出错", ex);
				throw new PaasWebException(
						Constants.IS_EXCESS_ERROR, ex.getMessage());
			}
		}
		return result;
	}
}