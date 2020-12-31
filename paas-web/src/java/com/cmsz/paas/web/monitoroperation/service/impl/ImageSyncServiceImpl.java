package com.cmsz.paas.web.monitoroperation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.model.controller.response.JobList;
import com.cmsz.paas.common.model.harbor.JobStatus;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.monitoroperation.dao.ImageSyncControlCenterDao;
import com.cmsz.paas.web.monitoroperation.dao.ImageSyncMonitorOperationDao;
import com.cmsz.paas.web.monitoroperation.model.ImageSync;
import com.cmsz.paas.web.monitoroperation.model.ImageSyncQueryConditions;
import com.cmsz.paas.web.monitoroperation.service.ImageSyncService;

/**
 * 
 * @author lin.my
 * @version 创建时间：2017年1月12日 下午3:45:25
 */
@Service("imageSyncService")
public class ImageSyncServiceImpl implements ImageSyncService {

	private static final Logger logger = LoggerFactory.getLogger(ImageSyncServiceImpl.class);
	
	@Autowired
	private ImageSyncControlCenterDao controlCenterDao;
	
	@Autowired
	private ImageSyncMonitorOperationDao monitorOperationDao;
	
	private String retCode = ""; // rest接口返回码
	private String msg = ""; // rest接口返回信息
	
	@UnLogging
	@Override
	public List<ImageSync> queryImageSyncList(ImageSyncQueryConditions condition) throws PaasWebException {
		List<ImageSync> list = new ArrayList<ImageSync>();
		try {
			String url = RestUtils.restUrlTransform("syncJobUrl", condition.getAppId(), 
					condition.getDataCenterId(), condition.getStatus(), condition.getStartTime(), condition.getEndTime());
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = controlCenterDao.get(url, JobList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			
			JobList jobList = (JobList) responseInfo.getData();
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (jobList != null && jobList.getJobList() != null) {
					for (int i = 0; i < jobList.getJobList().size(); i++) {
						JobStatus jobStatus = jobList.getJobList().get(i);
						ImageSync imageSync = translateJobs(jobStatus);
						list.add(imageSync);
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("镜像同步错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_JOB_LIST_ERROR, ex.getMessage());
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @param jobStatus
	 * @return
	 */
	private ImageSync translateJobs(JobStatus jobStatus){
		ImageSync job = new ImageSync();
		job.setId(jobStatus.getId()+"");
		job.setRepository(jobStatus.getRepository());
		job.setOperation(jobStatus.getOperation());
		job.setPolicyId(jobStatus.getPolicy_id()+"");
		job.setStatus(jobStatus.getStatus());
		job.setCreationTime(jobStatus.getCreation_time());
		job.setUpdateTime(jobStatus.getUpdate_time());
		job.setDataCenter(jobStatus.getDataCenrer_name());
		job.setImageVersion(jobStatus.getTags().length>0?jobStatus.getTags()[0] : "");
		return job;
	}

	/**
	 * 加载公共镜像
	 */
	@Override
	public void loadPublicImages() throws PaasWebException {
		try {
			//组装访问监控运维的链接，以及参数
			String url = RestUtils.monitorOperationRestUrl("loadPublicImagesUrl");
			logger.debug("开始调用加载公共镜像rest接口：" + url);
			// 调用接口，获取数据
			// HTTP请求方式:PUT
			// 是否长连接:否
			ResponseInfo responseInfo = monitorOperationDao.update(url, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("加载公共镜像rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				logger.error("加载公共镜像异常");
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("加载公共镜像异常",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.LOAD_PUBLIC_IMAGES__ERROR, ex.getMessage());
			}
		}
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
