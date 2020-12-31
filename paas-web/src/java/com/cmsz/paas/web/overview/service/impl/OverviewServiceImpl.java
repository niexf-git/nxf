package com.cmsz.paas.web.overview.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.response.OverviewApp;
import com.cmsz.paas.common.model.controller.response.OverviewAssResource;
import com.cmsz.paas.common.model.controller.response.OverviewFlow;
import com.cmsz.paas.common.model.controller.response.OverviewHost;
import com.cmsz.paas.common.model.controller.response.OverviewInstance;
import com.cmsz.paas.common.model.controller.response.OverviewRealResource;
import com.cmsz.paas.common.model.controller.response.OverviewWarn;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.overview.dao.OverviewDao;
import com.cmsz.paas.web.overview.model.ActualResourceRate;
import com.cmsz.paas.web.overview.model.AssignedResourceRate;
import com.cmsz.paas.web.overview.model.FlowBuildState;
import com.cmsz.paas.web.overview.model.InstanceState;
import com.cmsz.paas.web.overview.model.ServiceState;
import com.cmsz.paas.web.overview.model.TotalResource;
import com.cmsz.paas.web.overview.model.AlarmDetails;
import com.cmsz.paas.web.overview.model.AlarmDetailsList;
import com.cmsz.paas.web.overview.service.OverviewService;

/**
 * 总览模块service实现.
 * 
 * @author liaohw
 */
@Service("overviewService")
public class OverviewServiceImpl implements OverviewService {

	private static final Logger logger = LoggerFactory.getLogger(OverviewServiceImpl.class);

	@Autowired
	private OverviewDao overviewDao;

	@Override
	public ServiceState queryServiceState(String appIds, String operType) throws PaasWebException {
		ServiceState serviceState = new ServiceState();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryServiceStateUrl", appIds, operType);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = overviewDao.get(url, OverviewApp.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			OverviewApp overviewApp = (OverviewApp) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (overviewApp != null) {
					serviceState.setOperationing(overviewApp.getOperCount() + "");
					serviceState.setStopped(overviewApp.getStopCount() + "");
					serviceState.setWorking(overviewApp.getRunCount() + "");
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询服务状态统计数据错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_SERVICE_STATE_ERROR, ex.getMessage());
			}
		}
		return serviceState;
	}

	@Override
	public InstanceState queryInstanceState(String appIds, String operType) throws PaasWebException {
		InstanceState instanceState = new InstanceState();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryInstanceStateUrl", appIds, operType);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = overviewDao.get(url, OverviewInstance.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			OverviewInstance overviewInstance = (OverviewInstance) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (overviewInstance != null) {
					instanceState.setRunning(overviewInstance.getRunCount() + "");
					instanceState.setStopped(overviewInstance.getTerminationCount() + "");
					instanceState.setUnassigned(overviewInstance.getUnassignedCount() + "");
					instanceState.setUnknow(overviewInstance.getUnknownCount() + "");
					instanceState.setWaiting(overviewInstance.getWaitCount() + "");
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询实例状态统计数据错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_INSTANCE_STATE_ERROR, ex.getMessage());
			}
		}
		return instanceState;
	}

	@Override
	public AssignedResourceRate queryAssignedResourceRate(String appIds, String operType) throws PaasWebException {
		AssignedResourceRate assignedResourceRate = new AssignedResourceRate();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryAssignedResourceRateUrl", appIds, operType);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = overviewDao.get(url, OverviewAssResource.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			OverviewAssResource overviewAssResource = (OverviewAssResource) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (overviewAssResource != null) {
					assignedResourceRate.setAssignedCpu(overviewAssResource.getAssCpu() + "");
					assignedResourceRate.setAssignedMemory(overviewAssResource.getAssMemory() + "");
					assignedResourceRate.setTotalCpu(overviewAssResource.getTotailCpu() + "");
					assignedResourceRate.setTotalMemory(overviewAssResource.getTotailMemory() + "");
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}

		} catch (Exception ex) {
			logger.error("查询已分配资源利用率数据错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_ASSIGNED_RESOURCE_RATE_ERROR, ex.getMessage());
			}
		}
		return assignedResourceRate;
	}

	@Override
	public ActualResourceRate queryActualResourceRate(String appIds, String operType) throws PaasWebException {
		ActualResourceRate actualResourceRate = new ActualResourceRate();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryActualResourceRateUrl", appIds, operType);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = overviewDao.get(url, OverviewRealResource.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			OverviewRealResource overviewRealResource = (OverviewRealResource) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (overviewRealResource != null) {
					actualResourceRate.setTotalCpu(overviewRealResource.getTotailCpu() + "");
					actualResourceRate.setTotalDisk(overviewRealResource.getTotailDisk() + "");
					actualResourceRate.setTotalMemory(overviewRealResource.getTotailMemory() + "");
					actualResourceRate.setUsedCpu(overviewRealResource.getRealCpu() + "");
					actualResourceRate.setUsedDisk(overviewRealResource.getRealDisk() + "");
					actualResourceRate.setUsedMemory(overviewRealResource.getRealMemory() + "");
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询实际使用资源利用率数据错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_ACTUAL_RESOURCE_RATE_ERROR, ex.getMessage());
			}
		}
		return actualResourceRate;
	}

	@Override
	public FlowBuildState queryFlowBuildState(String appIds, String operType) throws PaasWebException {
		FlowBuildState flowBuildState = new FlowBuildState();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryFlowBuildStateUrl", appIds, operType);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = overviewDao.get(url, OverviewFlow.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			OverviewFlow overviewFlow = (OverviewFlow) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (overviewFlow != null) {
					flowBuildState.setFail(overviewFlow.getFailCount() + "");
					flowBuildState.setSuccess(overviewFlow.getSuccCount() + "");
					flowBuildState.setUnexecuted(overviewFlow.getUnexeCount() + "");
					flowBuildState.setWorking(overviewFlow.getRunCount() + "");
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询流水构建状态数据错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_FLOW_BUILD_STATE_ERROR, ex.getMessage());
			}
		}
		return flowBuildState;
	}

	@Override
	public AlarmDetailsList queryAlarmDetails(String appIds,String operType) throws PaasWebException {
		AlarmDetailsList alarmDetailsList = new AlarmDetailsList();
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryAlarmDetailsUrl", appIds, operType);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = overviewDao.get(url, 
					OverviewWarn.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			OverviewWarn overviewWarn = (OverviewWarn) responseInfo.getData();
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if(overviewWarn != null){
					for(int i=0;i < overviewWarn.getCountList().size();i++) {
						AlarmDetails alarmDetails = new AlarmDetails();
						alarmDetails.setAlarmCount(overviewWarn.getCountList().get(i).getWarnCount()+"");
						alarmDetails.setAlarmTime(dFormat.format(overviewWarn.getCountList().get(i).getWarnTime())+"");
						alarmDetailsList.getAlarmDetailsList().add(alarmDetails);
					}
				}
			}else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询告警统计数据错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_ALARM_DETAILS_ERROR, ex.getMessage());
			}
		}
		return alarmDetailsList;
	}

	@Override
	public TotalResource queryTotalResource(String appIds, String operType) throws PaasWebException {
		TotalResource totalResource = new TotalResource();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryTotalResource", appIds, operType);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = overviewDao.get(url, OverviewHost.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			OverviewHost overviewHost = (OverviewHost) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (overviewHost != null) {
					totalResource.setHost(overviewHost.getHostCount() + "");
					totalResource.setCpu(overviewHost.getCpuCount() + "");
					totalResource.setMemory(overviewHost.getMemoryCount() + "");
					totalResource.setDisk(overviewHost.getDiskCount() + "");
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询资源统计数据错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_TOTAL_RESOURCE_ERROR, ex.getMessage());
			}
		}
		return totalResource;
	}

}
