package com.cmsz.paas.web.appserviceinst.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.Event;
import com.cmsz.paas.common.model.controller.entity.PodEventInfo;
import com.cmsz.paas.common.model.controller.response.PodEventDesc;
import com.cmsz.paas.common.model.response.AppServiceInstDiagnoseMsg;
import com.cmsz.paas.common.model.response.InspectDetail;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.web.appserviceinst.dao.InstanceDao;
import com.cmsz.paas.web.appserviceinst.model.EventEntity;
import com.cmsz.paas.web.appserviceinst.model.Instance;
import com.cmsz.paas.web.appserviceinst.model.InstanceEntity;
import com.cmsz.paas.web.appserviceinst.service.InstanceService;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;

/**
 * 实例管理service实现.
 * 
 * @author liaohw
 */
@Service("instanceService")
public class InstanceServiceImpl implements InstanceService {

	/** The instance dao. */
	@Autowired
	private InstanceDao instanceDao;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(InstanceServiceImpl.class);

	/*
	 * 查询实例列表
	 */
	@Override
	public List<Instance> queryInstByAppServiceId(String appServiceId)
			throws PaasWebException {
		List<Instance> list = new ArrayList<Instance>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryInstListUrl",
					appServiceId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = instanceDao.get(url,
					com.cmsz.paas.common.model.controller.response.InstanceList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			com.cmsz.paas.common.model.controller.response.InstanceList instList = (com.cmsz.paas.common.model.controller.response.InstanceList) responseInfo
					.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (instList != null && instList.getInstanceList() != null) {
					for (int i = 0; i < instList.getInstanceList().size(); i++) {
						com.cmsz.paas.common.model.controller.entity.Instance instance = instList
								.getInstanceList().get(i);
						Instance inst = translateInstance(instance);
						list.add(inst);
					}
				}
				//按时间排序
				Collections.sort(list,new Comparator<Instance>() {
					@Override
					public int compare(Instance o1, Instance o2) {
						// TODO Auto-generated method stub
						return o2.getCreateTime().compareTo(o1.getCreateTime());
					}
				});
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询实例错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_APPSERVICE_INSTANCE_ERROR,
						ex.getMessage());
			}
		}
		return list;
	}

	@Override
	public RestResult restartInst(String appServiceId, String instanceId,String type)
			throws PaasWebException {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";

		try {
			String url = RestUtils.restUrlTransform("restartInstUrl",
					appServiceId, instanceId,type);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = instanceDao.update(url,
					ResponseInfo.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
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
				throw new PaasWebException(
						Constants.RESTART_APPSERVICE_INSTANCE_ERROR,
						ex.getMessage());
			}
		}
		return result;
	}

	@Override
	public String diagnosisAppService(String appServiceId,String type)
			throws PaasWebException {
		String result = ""; // 返回诊断的错误信息
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("diagnosisUrl",
					appServiceId,type);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = instanceDao.update(url,
					AppServiceInstDiagnoseMsg.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			AppServiceInstDiagnoseMsg appImageDiagnose = (AppServiceInstDiagnoseMsg) responseInfo
					.getData();

			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				throw new PaasWebException(retCode, msg);
			} else {
				if (appImageDiagnose != null) {
					result = appImageDiagnose.getResult();
				}
			}
		} catch (Exception ex) {
			logger.error("诊断方法错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.DIAGNOSIS_APPSERVICE_ERROR, ex.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * 容器详情
	 */
	@Override
	public String dialogContainerDetails(String containerId, String hostIP) throws PaasWebException {
		String result = "";
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("dialogContainerDetailsUrl", hostIP, containerId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = instanceDao.get(url, InspectDetail.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			InspectDetail inspectDetail = (InspectDetail) responseInfo.getData();

			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				throw new PaasWebException(retCode, msg);
			} else {
				if (inspectDetail != null) {
					result = inspectDetail.getInspectInfo();
				}
			}
		} catch (Exception ex) {
			logger.error("查询容器详情错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.DIALOG_CONTAINERDETAILS_ERROR, ex.getMessage());
			}
		}
		return result;
	}

	@Override
	public Instance translateInstance(
			com.cmsz.paas.common.model.controller.entity.Instance instance) {
		Instance inst = new Instance();

		inst.setInstanceId(instance.getId());
		inst.setContainerId(instance.getContainerId());
		inst.setStatus(instance.getStatus());
		inst.setCreateTime(DateUtil.tranformDate(instance.getCreateTime()
				.toString()));
		if (instance.getStartedAt().getTime() != 0) {
			inst.setLastTime(DateUtil.tranformDate(instance.getStartedAt()
					.toString()));
		} else {
			inst.setLastTime("-");
		}
		inst.setHostIP(instance.getHostIp());
		inst.setRestartNum(instance.getRestartCount() + "");
		inst.setSuggestMsg(instance.getSuggestMsg());
		inst.setWebsshUrl(instance.getWebsshUrl());
		inst.setType(instance.getType());
		inst.setNamespace(instance.getNamespace());
		return inst;
	}

	@Override
	public Instance translateInsts(
			com.cmsz.paas.common.model.controller.entity.Instance instance) {
		Instance inst = new Instance();

		inst.setInstanceId(instance.getId());
		inst.setContainerId(instance.getContainerId());
		inst.setStatus(instance.getStatus());
		inst.setCreateTime(DateUtil.tranformDate(instance.getCreateTime()
				.toString()));
		if (instance.getStartedAt().getTime() != 0) {
			inst.setLastTime(DateUtil.tranformDate(instance.getStartedAt()
					.toString()));
		} else {
			inst.setLastTime("-");
		}
		inst.setHostIP(instance.getHostIp());
		inst.setRestartNum(instance.getRestartCount() + "");
		inst.setSuggestMsg(instance.getSuggestMsg());
		inst.setType(instance.getType());
		return inst;
	}

	@Override
	public InstanceEntity queryPodDetail(String namespace, String podName)
			throws PaasWebException {
		InstanceEntity instanceEntity=new InstanceEntity();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryPodDetailUrl",namespace,podName);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = instanceDao.get(url,PodEventDesc.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			PodEventDesc instanceInfo = (PodEventDesc) responseInfo
					.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (instanceInfo != null) {
					instanceEntity=translateInstsEntity(instanceInfo);
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询实例详情错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_APPSERVICE_INSTANCE_DETAIL_ERROR,
						ex.getMessage());
			}
		}
		return instanceEntity;
	}
	
	public InstanceEntity translateInstsEntity(PodEventDesc instance) throws ParseException {
		InstanceEntity inst = new InstanceEntity();
		if (instance.getPodEventInfo()!=null) {
			PodEventInfo podEventInfo=instance.getPodEventInfo();
			inst.setNodeName(podEventInfo.getNodeName());
			inst.setNodeSelector(podEventInfo.getNodeSelector());
			inst.setPhase(podEventInfo.getPhase());
			inst.setPodIP(podEventInfo.getPodIP());
//			SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
//			SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			if (podEventInfo.getRunningTime()!=null&&!podEventInfo.getRunningTime().equals("")) {
//				String time = podEventInfo.getRunningTime().replace("Z", " UTC");
//				Date date = utcFormat.parse(time);
//				inst.setRunningTime(defaultFormat.format(date));
//			}
//			if (podEventInfo.getStartTime()!=null&&!podEventInfo.getStartTime().equals("")) {
//				String time = podEventInfo.getStartTime().replace("Z", " UTC");
//				Date date = utcFormat.parse(time);
//				inst.setStartTime(defaultFormat.format(date));
//			}
			inst.setRunningTime(podEventInfo.getRunningTime());
			inst.setStartTime(podEventInfo.getStartTime());
			if (podEventInfo.getEvents()!=null) {
				List<Event> event=podEventInfo.getEvents();
				List<EventEntity> eventList=new ArrayList<EventEntity>();
				for (Event event2 : event) {
					EventEntity eventEntity=new EventEntity();
					eventEntity.setMessage(event2.getMessage());
					eventEntity.setReason(event2.getReason());
					eventEntity.setType(event2.getType());
					eventList.add(eventEntity);
				}
				inst.setEvents(eventList);
			}

		}
		return inst;
	}
	
}
