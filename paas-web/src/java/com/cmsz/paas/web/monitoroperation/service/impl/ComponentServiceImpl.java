package com.cmsz.paas.web.monitoroperation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.AppServiceEntity;
import com.cmsz.paas.common.model.controller.entity.ScalerEntity;
import com.cmsz.paas.common.model.controller.response.AppServiceList;
import com.cmsz.paas.common.model.controller.entity.IpaasServiceEntity;
import com.cmsz.paas.common.model.monitor.entity.ComponentAssemblyEntity;
import com.cmsz.paas.common.model.monitor.request.ComponentAssemblyList;
import com.cmsz.paas.common.model.controller.response.IpaasServiceList;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.web.appserviceinst.model.Instance;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.monitoroperation.dao.ComponentDao;
import com.cmsz.paas.web.monitoroperation.model.AppServiceDetail;
import com.cmsz.paas.web.monitoroperation.model.ComponentInfo;
import com.cmsz.paas.web.monitoroperation.model.IpaasServiceDetail;
import com.cmsz.paas.web.monitoroperation.service.ComponentService;
@Service("componentService")
public class ComponentServiceImpl implements ComponentService {
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(ComponentServiceImpl.class);
	@Autowired
	private ComponentDao componentDao;
	@Override
	public List<ComponentInfo> queryComponentList(String nodeId) throws PaasWebException {
		List<ComponentInfo> list = new ArrayList<ComponentInfo>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.monitorOperationRestUrl(
					"queryComponentListUrl", nodeId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = componentDao.get(url,
					ComponentAssemblyList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			ComponentAssemblyList componentAssemblyList = (ComponentAssemblyList) responseInfo
					.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (componentAssemblyList != null
						&& componentAssemblyList.getComponentList() != null) {
					for (int i = 0; i < componentAssemblyList.getComponentList()
							.size(); i++) {
						ComponentAssemblyEntity componentAssemblyEntity = componentAssemblyList
								.getComponentList().get(i);
						ComponentInfo componentInfo = translateComponentInfo(componentAssemblyEntity);
						list.add(componentInfo);
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询minion上的组件）错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_COMPONENT_INST_ERROR, ex.getMessage());
			}
		}
		return list;
	}
	
	@Override
	public void operationComponent(String nodeId,String name,String command)
			throws PaasWebException {
		// 状态码
		String retCode = "";
		// 返回信息
		String msg = "";
		try {
			String url = RestUtils.monitorOperationRestUrl(
					"operationComponentUrl", command,nodeId,name);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = componentDao.update(url,ComponentAssemblyEntity.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.CONTROLLER_CONN_ERROR,
						"无法访问操作组件的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用操作组件的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用操作组件的rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			logger.error("操作组件错误", e);
			if (e instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.OPERATION_COMPONENT_ERROR,
						e.getMessage());
			}
		}

	}
	/**
	 * 转换组件.
	 * 
	 * @param ComponentInfo
	 *            控制中心组件bean
	 * @return 前台使用的组件bean
	 */
	private ComponentInfo translateComponentInfo(ComponentAssemblyEntity componentAssemblyEntity) {
		ComponentInfo componentInfo = new ComponentInfo();
		componentInfo.setId(componentAssemblyEntity.getId());
		componentInfo.setNodeId(componentAssemblyEntity.getNodeId());
		componentInfo.setName(componentAssemblyEntity.getComponentName());
		componentInfo.setStatus(componentAssemblyEntity.getComponentStatus());
		return componentInfo;
	}
	/*
	 * 通过minion IP 查询minion上的基础服务以及服务下的实例
	 */
//	@Override
//	public List<IpaasServiceDetail> queryIpaasServiceByMinionIp(String minionIp)
//			throws PaasWebException {
//		List<IpaasServiceDetail> list = new ArrayList<IpaasServiceDetail>();
//		String retCode = "";
//		String msg = "";
//		try {
//			String url = RestUtils.restUrlTransform(
//					"queryIpaasServiceInstsListUrl", minionIp);
//			logger.info("开始调用Rest接口：" + url);
//			ResponseInfo responseInfo = componentDao.get(url,
//					IpaasServiceList.class);
//
//			retCode = responseInfo.getRetCode();
//			msg = responseInfo.getMsg();
//			IpaasServiceList ipaasServiceList = (IpaasServiceList) responseInfo
//					.getData();
//
//			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
//				if (ipaasServiceList != null
//						&& ipaasServiceList.getIpaasList() != null) {
//					for (int i = 0; i < ipaasServiceList.getIpaasList().size(); i++) {
//						IpaasServiceEntity ipaasServiceEntity = ipaasServiceList
//								.getIpaasList().get(i);
//						IpaasServiceDetail ipaasServiceDetail = translateIpaasServiceEntity(ipaasServiceEntity);
//						list.add(ipaasServiceDetail);
//					}
//				}
//			} else {
//				throw new PaasWebException(retCode, msg);
//			}
//		} catch (Exception ex) {
//			logger.error("查询minion上的基础服务（包括实例）错误", ex);
//			if (ex instanceof PaasWebException) {
//				throw new PaasWebException(retCode, msg);
//			} else {
//				throw new PaasWebException(
//						Constants.QUERY_IPAASSERVICE_INST_ERROR,
//						ex.getMessage());
//			}
//		}
//		return list;
//	}
//
//	/*
//	 * 通过minion IP 查询minion上的应用服务以及服务下的实例
//	 */
//	@Override
//	public List<AppServiceDetail> queryAppServiceByMinionIp(String minionIp)
//			throws PaasWebException {
//		List<AppServiceDetail> list = new ArrayList<AppServiceDetail>();
//		String retCode = "";
//		String msg = "";
//		try {
//			String url = RestUtils.restUrlTransform(
//					"queryAppServiceInstsListUrl", minionIp);
//			logger.info("开始调用Rest接口：" + url);
//			ResponseInfo responseInfo = componentDao.get(url,
//					AppServiceList.class);
//
//			retCode = responseInfo.getRetCode();
//			msg = responseInfo.getMsg();
//			AppServiceList appServiceList = (AppServiceList) responseInfo
//					.getData();
//
//			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
//				if (appServiceList != null
//						&& appServiceList.getAppServiceList() != null) {
//					for (int i = 0; i < appServiceList.getAppServiceList()
//							.size(); i++) {
//						AppServiceEntity appServiceEntity = appServiceList
//								.getAppServiceList().get(i);
//						AppServiceDetail appServiceDetail = translateAppServiceEntity(appServiceEntity);
//						list.add(appServiceDetail);
//					}
//				}
//			} else {
//				throw new PaasWebException(retCode, msg);
//			}
//		} catch (Exception ex) {
//			logger.error("查询minion上的应用服务（包括实例）错误", ex);
//			if (ex instanceof PaasWebException) {
//				throw new PaasWebException(retCode, msg);
//			} else {
//				throw new PaasWebException(
//						Constants.QUERY_APPSERVICE_INST_ERROR, ex.getMessage());
//			}
//		}
//		return list;
//	}
//
//	/**
//	 * 把控制中心的应用bean转为前台用的bean（包括应用的实例）
//	 * 
//	 * @param appEntity
//	 *            控制中心应用bean
//	 * @return 前台使用的应用bean
//	 */
//	private AppServiceDetail translateAppServiceEntity(
//			AppServiceEntity appServiceEntity) {
//		AppServiceDetail appServiceDetail = new AppServiceDetail();
//
//		appServiceDetail.setId(appServiceEntity.getId() + "");
//		appServiceDetail.setAppName(appServiceEntity.getAppName());
//		appServiceDetail.setCreateBy(appServiceEntity.getCreateBy());
//		appServiceDetail.setCreateTime(DateUtil.tranformDate(appServiceEntity
//				.getCreateTime().toString()));
//		appServiceDetail.setName(appServiceEntity.getName());
//		appServiceDetail.setStatus(appServiceEntity.getStatus() + "");
//		appServiceDetail.setOperateType(appServiceEntity.getOperateType() + "");
//		appServiceDetail.setAppId(appServiceEntity.getAppId() + "");
//		appServiceDetail.setDescription(appServiceEntity.getDescription());
//		appServiceDetail.setClusterId(appServiceEntity.getClusterId() + "");
//		appServiceDetail.setRunningInstNum(appServiceEntity.getRunningInstanceNum()
//				+ "");
//		appServiceDetail.setExistGray(appServiceEntity.getExistGray()); 
//		// 实例伸缩
//		String inst_num = appServiceEntity.getRunningInstanceNum() + "";
//		ScalerEntity scaler = appServiceEntity.getScaler();
//		if (inst_num.equals("0") && scaler != null) {
//			appServiceDetail.setCpu_target(scaler.getCpuTarget() + "");
//			appServiceDetail.setInst_min(scaler.getInstMin() + "");
//			appServiceDetail.setInst_max(scaler.getInstMax() + "");
//			appServiceDetail.setInst_scale_type("2");
//		} else {
//			appServiceDetail.setInstanceNum(inst_num);
//			appServiceDetail.setInst_scale_type("1");
//		}
//		List<Instance> instances = new ArrayList<Instance>();
//		List<com.cmsz.paas.common.model.controller.entity.Instance> instanceList = appServiceEntity
//				.getInstances();
//		if (instanceList != null) {
//			for (int i = 0; i < instanceList.size(); i++) {
//				Instance inst = translateInsts(instanceList
//						.get(i));
//				instances.add(inst);
//			}
//		}
//		appServiceDetail.setInsts(instances);
//
//		return appServiceDetail;
//	}
//
//	/**
//	 * 把控制中心的应用bean转为前台用的bean（包括应用的实例）
//	 * 
//	 * @param appEntity
//	 *            控制中心应用bean
//	 * @return 前台使用的应用bean
//	 */
//	private IpaasServiceDetail translateIpaasServiceEntity(
//			IpaasServiceEntity ipaasServiceEntity) {
//		IpaasServiceDetail ipaasServiceDetail = new IpaasServiceDetail();
//
//		ipaasServiceDetail.setId(ipaasServiceEntity.getId() + "");
//		ipaasServiceDetail.setAppName(ipaasServiceEntity.getAppName());
//		ipaasServiceDetail.setCreateBy(ipaasServiceEntity.getCreateBy());
//		ipaasServiceDetail.setCreateTime(DateUtil
//				.tranformDate(ipaasServiceEntity.getCreateTime().toString()));
//		ipaasServiceDetail.setName(ipaasServiceEntity.getName());
//		ipaasServiceDetail.setStatus(ipaasServiceEntity.getStatus() + "");
//		ipaasServiceDetail.setOperateType(ipaasServiceEntity.getOperateType()
//				+ "");
//		ipaasServiceDetail.setNodeNum(ipaasServiceEntity.getNodeNum() + "");
//		ipaasServiceDetail.setRunningNode(ipaasServiceEntity.getRunningNode()
//				+ "");
//		ipaasServiceDetail.setServiceType(ipaasServiceEntity.getServiceType()
//				+ "");
//		List<Instance> instances = new ArrayList<Instance>();
//		List<com.cmsz.paas.common.model.controller.entity.Instance> instanceList = ipaasServiceEntity
//				.getInsts();
//		if (instanceList != null) {
//			for (int i = 0; i < instanceList.size(); i++) {
//				Instance inst = translateInsts(instanceList
//						.get(i));
//				instances.add(inst);
//			}
//		}
//		ipaasServiceDetail.setInsts(instances);
//
//		return ipaasServiceDetail;
//	}
//	@Override
//	public Instance translateInsts(com.cmsz.paas.common.model.controller.entity.Instance instance) {
//		Instance inst = new Instance();
//
//		inst.setInstanceId(instance.getId());
//		inst.setContainerId(instance.getContainerId());
//		inst.setStatus(instance.getStatus());
//		inst.setCreateTime(DateUtil.tranformDate(instance.getCreateTime()
//				.toString()));
//		if (instance.getStartedAt().getTime() != 0) {
//			inst.setLastTime(DateUtil.tranformDate(instance.getStartedAt()
//					.toString()));
//		} else {
//			inst.setLastTime("-");
//		}
//		inst.setHostIP(instance.getHostIp());
//		inst.setRestartNum(instance.getRestartCount() + "");
//		inst.setSuggestMsg(instance.getSuggestMsg());
//
//		return inst;
//	}

}
