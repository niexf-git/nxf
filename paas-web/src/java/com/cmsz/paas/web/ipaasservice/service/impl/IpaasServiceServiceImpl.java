package com.cmsz.paas.web.ipaasservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.constant.Constant;
import com.cmsz.paas.common.model.controller.entity.ClusterEntity;
import com.cmsz.paas.common.model.controller.entity.DataCenterEntity;
import com.cmsz.paas.common.model.controller.entity.EnvConfigEntity;
import com.cmsz.paas.common.model.controller.entity.IpaasServiceEntity;
import com.cmsz.paas.common.model.controller.entity.IpaasToAppServiceEntity;
import com.cmsz.paas.common.model.controller.entity.PublicImageEntity;
import com.cmsz.paas.common.model.controller.entity.PublishEnvEntity;
import com.cmsz.paas.common.model.controller.request.AppIdList;
import com.cmsz.paas.common.model.controller.response.IdValue;
import com.cmsz.paas.common.model.controller.response.IpaasServiceConfig;
import com.cmsz.paas.common.model.controller.response.IpaasServiceDetail;
import com.cmsz.paas.common.model.controller.response.IpaasServiceList;
import com.cmsz.paas.common.model.controller.response.PublishEnvList;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.web.appservice.model.EnvConfig;
import com.cmsz.paas.web.appserviceinst.model.Instance;
import com.cmsz.paas.web.appserviceinst.service.InstanceService;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.ipaasservice.dao.IpaasServiceDao;
import com.cmsz.paas.web.ipaasservice.model.AppServices;
import com.cmsz.paas.web.ipaasservice.model.IpaasService;
import com.cmsz.paas.web.ipaasservice.service.IpaasServiceService;

/**
 * 
 * @author fubl
 * 
 */
@Service("ipaasServiceService")
public class IpaasServiceServiceImpl implements IpaasServiceService {

	private static final Logger logger = LoggerFactory
			.getLogger(IpaasServiceServiceImpl.class);

	@Autowired
	private IpaasServiceDao ipaasServiceDao;
	
	@Autowired
	private InstanceService instanceService;

	@Override
	public List<IpaasService> queryIpaasServiceList(AppIdList appIdList,
			String token, int serviceType) throws PaasWebException {
		List<IpaasService> ipaasServiceList = new ArrayList<IpaasService>();
		String msg = "";
		String retCode = "";
		IpaasServiceList ipaasServiceListJson = new IpaasServiceList();
		String url = RestUtils.restUrlTransform("queryIpaasServiceListUrl",
				token, serviceType);
		logger.debug("开始调用查询基础用服务的rest接口：" + url);

		// IpaasServiceEntity ipaasServiceEntity = new IpaasServiceEntity();
		// ipaasServiceList.add(genIpaasService(ipaasServiceEntity));

		try {
			ResponseInfo responseInfo = ipaasServiceDao.create(url, appIdList,
					IpaasServiceList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			ipaasServiceListJson = (IpaasServiceList) responseInfo.getData();
			logger.debug("调用查询基础服务的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (!ipaasServiceListJson.equals(null)) {
					for (IpaasServiceEntity ipaasServiceEntity : ipaasServiceListJson
							.getIpaasList()) {
						ipaasServiceList
								.add(genIpaasService(ipaasServiceEntity));
					}
				}

				logger.info("调用查询基础服务的rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询基础服务列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_IPAASSERVICELIST_ERROR
						+ "]查询基础服务列表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_IPAASSERVICELIST_ERROR, ex.getMessage());
			}
		}
		return ipaasServiceList;
	}

	/**
	 * 解析基础服务详情并生成页面展示model.
	 * 
	 * @param appServiceEntity
	 *            the app service entity
	 * @return the app service
	 */
	public IpaasService genIpaasService(IpaasServiceEntity ipaasServiceEntity) {
		IpaasService ipaasService = new IpaasService();

		ipaasService.setId(ipaasServiceEntity.getId() + "");
		ipaasService.setName(ipaasServiceEntity.getName());

		ipaasService.setApp_id(ipaasServiceEntity.getAppId() + "");
		ipaasService.setApp_name(ipaasServiceEntity.getAppName());
		ipaasService.setOper_type(ipaasServiceEntity.getOperateType() + "");

		ipaasService.setCpu(ipaasServiceEntity.getCpu() + "");
		ipaasService.setMaxCpu(ipaasServiceEntity.getPeakCpu() + "");
		ipaasService.setMem(ipaasServiceEntity.getMem() + "");
		ipaasService.setMaxMem(ipaasServiceEntity.getPeakMem() + "");
		ipaasService.setConfig(ipaasServiceEntity.getConfig());

		ipaasService.setInstance_num(ipaasServiceEntity.getNodeNum() + "");
		ipaasService.setRunning_Inst_num(ipaasServiceEntity.getRunningNode()
				+ "");

		ipaasService.setStatus(ipaasServiceEntity.getStatus() + "");
		ipaasService
				.setConfig_effect(ipaasServiceEntity.getConfigEffect() + "");
		ipaasService
				.setUser_id(String.valueOf(ipaasServiceEntity.getCreateBy())
						+ "");
		ipaasService.setCreate_time(DateUtil.tranformDate(ipaasServiceEntity
				.getCreateTime().toString()) + "");
		ipaasService.setDescription(ipaasServiceEntity.getDescription() + "");
		ipaasService.setService_type(ipaasServiceEntity.getServiceType() + "");
		List<PublishEnvEntity> pubList = ipaasServiceEntity.getPublishEnvsList();
		if(pubList.size()>0){
			for(PublishEnvEntity pe : pubList){
				if("USER".equalsIgnoreCase(pe.getConfigKey())){
					ipaasService.setUser(pe.getConfigValue());
				}
				if("PWD".equalsIgnoreCase(pe.getConfigKey())){
					ipaasService.setPwd(pe.getConfigValue());
				}
			}
		}
		
		PublicImageEntity publicImage = ipaasServiceEntity.getPublicImage();
		if (publicImage != null) {
			ipaasService.setImage_id(publicImage.getId() + "");
			ipaasService.setImage_name(publicImage.getName() + "");
			ipaasService.setImage_version(publicImage.getRunningVersion()
					.getVersion());
			ipaasService.setImage_version_id(publicImage.getRunningVersion()
					.getId() + "");
		}

		// 外部服务
		List<PublishEnvEntity> envList = ipaasServiceEntity
				.getPublishEnvsList();
		String serviceUrl = "";
		String pwd = "";
		if (envList != null) {
			for (PublishEnvEntity env : envList) {
				if (env.getConfigKey().equals(Constants.IPAAS_ENVS_CONN)) {
					serviceUrl = env.getConfigValue();

				} else if (env.getConfigKey().equals(Constants.IPAAS_ENVS_PWD)) {
					pwd = env.getConfigValue();
				}
			}
		}
		ipaasService.setService_url(serviceUrl);
		ipaasService.setService_pwd(pwd);

		// ipaas
		List<AppServices> appServiceList = genAppServiceList(ipaasServiceEntity
				.getAppServices());
		ipaasService.setAppServices(appServiceList);
		ipaasService.setAppService_configJson(JackJson.fromObjectToJson(appServiceList));
		
		//实例列表
		List<com.cmsz.paas.common.model.controller.entity.Instance> instanceList = ipaasServiceEntity.getInsts();
		if (instanceList != null) {
			List<Instance> instances = new ArrayList<Instance>();
			for (int i = 0; i < instanceList.size(); i++) {
				Instance inst = instanceService.translateInsts(instanceList.get(i));
				instances.add(inst);
			}
			ipaasService.setInstances(instances);
		}
		
		DataCenterEntity dataCenterEntity = ipaasServiceEntity.getDataCenterEntity();
		if(dataCenterEntity != null){
			ipaasService.setLogServerIp(dataCenterEntity.getFlumeIp());
		}
		
		ClusterEntity cluster = ipaasServiceEntity.getCluster();
		if(cluster!=null){
			ipaasService.setClusterId(cluster.getId());
			ipaasService.setClusterName(cluster.getName());
		}
		
		return ipaasService;
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
	 * 应用服务列表信息转换
	 * 
	 * @param listconfig
	 *            配置集合
	 * @return
	 */
	private List<AppServices> genAppServiceList(
			List<IpaasToAppServiceEntity> ipaasList) {
		logger.debug("开始转换配置信息ipaas...");
		List<AppServices> list = null;
		if (ipaasList != null) {
			AppServices appService;
			list = new ArrayList<AppServices>();
			for (IpaasToAppServiceEntity e : ipaasList) {
				appService = new AppServices();
				appService.setId(e.getAppServiceId());
				appService.setName(e.getAppService().getName());
				appService.setBindStr(e.getBindStr());

				list.add(appService);
			}
		}
		logger.debug("转换应用服务列表信息成功！");
		return list;
	}

	@Override
	public IpaasService queryIpaasServiceById(String ipaasServiceId)
			throws PaasWebException {
		IpaasService ipaasService = null;
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryIpaasServiceById",
				ipaasServiceId);
		logger.debug("开始调用查询基础服务详情的rest接口：" + url);
		// IpaasServiceEntity ipaasServiceEntity = new IpaasServiceEntity();
		// ipaasService = genIpaasService(ipaasServiceEntity);
		try {
			ResponseInfo responseInfo = ipaasServiceDao.get(url,
					IpaasServiceDetail.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用查询基础服务详情的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			IpaasServiceDetail ipaasServiceJson = (IpaasServiceDetail) responseInfo
					.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (ipaasServiceJson != null) {
					IpaasServiceEntity ipaasServiceEntity = ipaasServiceJson
							.getIpaasServiceDetail();
					ipaasService = genIpaasService(ipaasServiceEntity);
				}
				logger.info("调用查询基础服务详情Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询基础服务详情出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_IPAASSERVICEDETAIL_ERROR
						+ "]查询基础服务详情出错", ex);
				throw new PaasWebException(
						Constants.QUERY_IPAASSERVICEDETAIL_ERROR,
						ex.getMessage());
			}
		}
		return ipaasService;
	}

	@Override
	public void deleteIpaasService(String ipaasServiceId)
			throws PaasWebException {
		String msg = "";
		String retCode = "";
		try {
			String url = RestUtils.restUrlTransform("deleteIpaasServiceUrl",
					ipaasServiceId);
			logger.info("开始调用删除应用组的rest接口: " + url);
			ResponseInfo responseInfo = ipaasServiceDao.delete(url,
					ResponseInfo.class);
			msg = responseInfo.getMsg();
			retCode = responseInfo.getRetCode();
			logger.info("调用删除基础服务的rest接口返回码:" + retCode + ", 返回信息:" + msg);
			if (!retCode.equals(Constants.REST_CODE_SUCCESS)) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("删除基础服务异常", ex);
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]删除基础服务异常", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.DELETE_IPAASSERVICE_ERROR
						+ "]删除基础服务出错", ex);
				throw new PaasWebException(Constants.DELETE_IPAASSERVICE_ERROR,
						ex.getMessage());
			}
		}

	}
	
	@Override
	public void checkIpaasRelaAppService(String ipaasServiceId) throws PaasWebException {
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("checkIpaasRelaAppService", ipaasServiceId);
			logger.debug("开始调用检查的rest接口：" + url);
			ResponseInfo responseInfo = ipaasServiceDao.get(url, ResponseInfo.class);
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
				logger.error("[" + Constants.DELETE_APPSERVICE_ERROR
						+ "]检查出错", ex);
				throw new PaasWebException(Constants.DELETE_APPSERVICE_ERROR,
						ex.getMessage());
			}
		}
	}

	/*
	 * 启动，重启，停止，强制停止，强制重启服务
	 */
	@Override
	public RestResult startOrStopIpaasService(String ipaasServiceId,
			String operateId) throws PaasWebException {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform(
					"startOrStopIpaasServiceUrl", ipaasServiceId, operateId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = ipaasServiceDao.update(url,
					ResponseInfo.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();

			if (Constants.REST_CODE_SUCCESS.equals(retCode) || Constants.REST_CODE_COMPLETE.equals(retCode)) {
				result.setMsg(msg);
				result.setRetCode(retCode);
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				if (operateId == Constant.START) {
					throw new PaasWebException(
							Constants.START_IPAASERVICE_ERROR, ex.getMessage());
				} else if (operateId == Constant.STOP) {
					throw new PaasWebException(
							Constants.STOP_IPAASERVICE_ERROR, ex.getMessage());
				} else if (operateId == Constant.FORCE_STOP) {
					throw new PaasWebException(
							Constants.FORCESTOP_IPAASERVICE_ERROR, ex.getMessage());
				} else if (operateId == Constant.FORCE_RESTART) {
					throw new PaasWebException(
							Constants.FORCED_RESTART_INSTANCE_ERROR, ex.getMessage());
				} else {
					throw new PaasWebException(
							Constants.RESTART_IPAASERVICE_ERROR,
							ex.getMessage());
				}
			}
		}
		return result;
	}

	@Override
	public RestResult queryDefaultConfigByIpaasServiceTpye(
			String ipaasServiceType) throws PaasWebException {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryDefaultConfigUrl",
					ipaasServiceType);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = ipaasServiceDao.get(url,
					IpaasServiceConfig.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			IpaasServiceConfig ipaasServiceConfig = (IpaasServiceConfig) responseInfo
					.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				result.setRetCode(retCode);
				result.setMsg(msg);
				if (ipaasServiceConfig != null) {
					result.setData(ipaasServiceConfig.getConfig());
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("根据基础服务类型查询默认配置错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_DEFAULT_CONFIG_ERROR, ex.getMessage());// ?
			}
		}
		return result;
	}

	@Override
	public RestResult createIpaasService(IpaasService ipaasService)
			throws PaasWebException {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		try {
			IpaasServiceEntity ipaasServiceEntity = translateToIpaasServiceEntity(ipaasService);
			String url = RestUtils.restUrlTransform("createIpaasServiceUrl");
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = ipaasServiceDao.create(url,
					ipaasServiceEntity, IdValue.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			IdValue ipaasServiceId = (IdValue) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (ipaasServiceId != null) {
					result.setMsg(msg);
					result.setRetCode(retCode);
					result.setData(ipaasServiceId.getId() + "");
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("创建基础服务错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.CREATE_IPAAS_SERVICE_ERROR, ex.getMessage());
			}
		}
		return result;
	}

	/*
	 * 把前台数据转换成控制中心需要的bean
	 */
	private IpaasServiceEntity translateToIpaasServiceEntity(
			IpaasService ipaasService) {
		IpaasServiceEntity ipaasServiceEntity = new IpaasServiceEntity();
		ipaasServiceEntity.setClusterId(ipaasService.getClusterId());
		ipaasServiceEntity.setName(ipaasService.getName());
		// 修改时不需要传给控制中心
		if (StringUtils.isNotBlank(ipaasService.getService_type())) {
			ipaasServiceEntity.setServiceType(Integer.parseInt(ipaasService
					.getService_type()));
		}
		String imageId = ipaasService.getImage_id();
		if (imageId != null) {
			String[] split = imageId.split("_");
			ipaasServiceEntity.setPublicImageId(Long.parseLong(split[0]));
			ipaasServiceEntity.setRunningVersion(Long.parseLong(split[1]));
		}
		ipaasServiceEntity.setCpu(Double.parseDouble(ipaasService.getCpu()));
		ipaasServiceEntity.setPeakCpu(Double.parseDouble(ipaasService
				.getMaxCpu()));
		ipaasServiceEntity.setMem(Integer.parseInt(ipaasService.getMem()));
		ipaasServiceEntity
				.setPeakMem(Integer.parseInt(ipaasService.getMaxMem()));
		// 修改时不需要传给控制中心
		if (StringUtils.isNotBlank(ipaasService.getInstance_num())) {
			ipaasServiceEntity.setNodeNum(Integer.parseInt(ipaasService
					.getInstance_num()));
		}
		ipaasServiceEntity.setConfig(ipaasService.getConfig());
		ipaasServiceEntity.setDescription(ipaasService.getDescription());
		// 以下三个参数在修改时不需要传给控制中心
		if (StringUtils.isNotBlank(ipaasService.getApp_id())) {
			ipaasServiceEntity
					.setAppId(Long.parseLong(ipaasService.getApp_id()));
		}
		if (StringUtils.isNotBlank(ipaasService.getOper_type())) {
			ipaasServiceEntity.setOperateType(Integer.parseInt(ipaasService
					.getOper_type()));
		}
		if (StringUtils.isNotBlank(ipaasService.getUser_id())) {
			ipaasServiceEntity.setCreateBy(ipaasService.getUser_id());
		}
		if (StringUtils.isNotBlank(ipaasService.getUser())) {
			ipaasServiceEntity.setUser(ipaasService.getUser());
		}
		if (StringUtils.isNotBlank(ipaasService.getPwd())) {
			ipaasServiceEntity.setPwd(ipaasService.getPwd());
		}
		
		return ipaasServiceEntity;
	}

	@Override
	public List<EnvConfig> queryEnvsByIpaasServiceId(String ipaasServiceId)
			throws PaasWebException {
		List<EnvConfig> ipaasEnvsList = null;
		String msg = "";
		String retCode = "";
		String url = RestUtils.restUrlTransform("queryEnvsByIpaasServiceId",
				ipaasServiceId);
		logger.debug("开始调用根据基础服务id查询发布的环境变量的rest接口：" + url);

		try {
			ResponseInfo responseInfo = ipaasServiceDao.get(url,
					PublishEnvList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用根据基础服务id查询发布的环境变量的rest接口返回码：" + retCode + ", 返回信息："
					+ msg);
			PublishEnvList ipaasEnvsJson = (PublishEnvList) responseInfo
					.getData();
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (ipaasEnvsJson != null) {
					List<PublishEnvEntity> publishEnvEntityList = ipaasEnvsJson
							.getEnvs();
					ipaasEnvsList = genConfigList(publishEnvEntityList);

				}
				logger.info("调用根据基础服务id查询发布的环境变量Rest接口成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]根据基础服务id查询发布的环境变量出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_IPAASSERVICEENV_ERROR
						+ "]根据基础服务id查询发布的环境变量出错", ex);
				throw new PaasWebException(
						Constants.QUERY_IPAASSERVICEENV_ERROR, ex.getMessage());
			}
		}
		return ipaasEnvsList;
	}

	@Override
	public RestResult modifyIpaasService(IpaasService ipaasService)
			throws PaasWebException {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		try {
			IpaasServiceEntity ipaasServiceEntity = translateToIpaasServiceEntity(ipaasService);
			String url = RestUtils.restUrlTransform("modifyIpaasServiceUrl",
					ipaasService.getId());
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = ipaasServiceDao.update(url,
					ipaasServiceEntity, ResponseInfo.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				result.setRetCode(retCode);
				result.setMsg(msg);
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("修改基础服务错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.MODIFY_IPAAS_SERVICE_ERROR, ex.getMessage());
			}
		}
		return result;
	}

	@Override
	public List<IpaasService> queryIpaasServiceAndInstance(String minionIp)
			throws PaasWebException {
		List<IpaasService> list = new ArrayList<IpaasService>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryIpaasServiceInstsListUrl", minionIp);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = ipaasServiceDao.get(url,IpaasServiceList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			IpaasServiceList ipaasServiceList = (IpaasServiceList) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (ipaasServiceList != null && ipaasServiceList.getIpaasList() != null) {
					for (int i = 0; i < ipaasServiceList.getIpaasList().size(); i++) {
						IpaasServiceEntity ipaasServiceEntity = ipaasServiceList.getIpaasList().get(i);
						IpaasService ipaasService = genIpaasService(ipaasServiceEntity);
						list.add(ipaasService);
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询minion上的基础服务（包括实例）错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_IPAASSERVICE_INST_ERROR,
						ex.getMessage());
			}
		}
		return list;
	}

	@Override
	public List<IpaasService> queryNewIpaasServiceAndInstance(String minionIp,
			String appIds, String operType) throws PaasWebException {
		List<IpaasService> list = new ArrayList<IpaasService>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryNewIpaasServiceInstsListUrl", minionIp,appIds,operType);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = ipaasServiceDao.get(url,IpaasServiceList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			IpaasServiceList ipaasServiceList = (IpaasServiceList) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (ipaasServiceList != null && ipaasServiceList.getIpaasList() != null) {
					for (int i = 0; i < ipaasServiceList.getIpaasList().size(); i++) {
						IpaasServiceEntity ipaasServiceEntity = ipaasServiceList.getIpaasList().get(i);
						IpaasService ipaasService = genIpaasService(ipaasServiceEntity);
						list.add(ipaasService);
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询minion上的基础服务（包括实例）错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_IPAASSERVICE_INST_ERROR,
						ex.getMessage());
			}
		}
		return list;
	}

}