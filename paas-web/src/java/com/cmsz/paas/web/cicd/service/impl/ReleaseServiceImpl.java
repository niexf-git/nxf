package com.cmsz.paas.web.cicd.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.response.Release;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.dao.ReleaseDao;
import com.cmsz.paas.web.cicd.model.ReleaseEntity;
import com.cmsz.paas.web.cicd.service.ReleaseService;
import com.cmsz.paas.web.constants.Constants;

/**
 * 发布 Service.
 * 
 * @author liaohw
 * @date 2017-11-27
 */
@Service("releaseService")
public class ReleaseServiceImpl implements ReleaseService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ReleaseServiceImpl.class);
	
	@Autowired
	private ReleaseDao releaseDao;

	@Override
	public ReleaseEntity queryRelease(String flowId) throws Exception {
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryReleaseUrl", flowId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = releaseDao.get(url, Release.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			Release release = (Release) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				 if (release != null && release.getRelease() != null) {
					 return translateToReleaseEntity(release.getRelease());
				 }else{
					 return null;
				 }
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询发布的配置信息错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_RELEASE_ERROR,
						ex.getMessage());
			}
		}
	}
	
	/*
	 * 把控制中心的bean转换成前台ReleaseEntity对象
	 */
	private ReleaseEntity translateToReleaseEntity(com.cmsz.paas.common.model.controller.entity.ReleaseEntity releaseEntity){
		ReleaseEntity WebReleaseEntity = new ReleaseEntity();
		WebReleaseEntity.setDestination(releaseEntity.getType()+"");
		WebReleaseEntity.setVersionNumber(releaseEntity.getVersionNum());
		WebReleaseEntity.setType(releaseEntity.getExecMode()+"");
		return WebReleaseEntity;
	}

	@Override
	public void modifyRelease(String flowId, ReleaseEntity releaseEntity)
			throws Exception {
		String retCode = "";
		String msg = "";
		try{
			com.cmsz.paas.common.model.controller.entity.ReleaseEntity controllerReleaseEntity = translateToControllerReleaseEntity(releaseEntity);
			String url = RestUtils.restUrlTransform("modifyReleaseUrl", flowId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = releaseDao.update(url, controllerReleaseEntity, ResponseInfo.class); 
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
		
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("修改发布的配置信息错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.MODIFY_RELEASE_ERROR,ex.getMessage());
			}
		}
	}
	
	/*
	 * 把前台数据转换成控制中心需要的bean
	 */
	private com.cmsz.paas.common.model.controller.entity.ReleaseEntity translateToControllerReleaseEntity(ReleaseEntity releaseEntity){
		com.cmsz.paas.common.model.controller.entity.ReleaseEntity controllerReleaseEntity = new com.cmsz.paas.common.model.controller.entity.ReleaseEntity();
		controllerReleaseEntity.setType(Integer.parseInt(releaseEntity.getDestination()));
		controllerReleaseEntity.setVersionNum(releaseEntity.getVersionNumber());
		controllerReleaseEntity.setExecMode(Integer.parseInt(releaseEntity.getType()));
		return controllerReleaseEntity;
	}

	@Override
	public void releaseProduction(String flowId) throws Exception {
		String retCode = "";
		String msg = "";
		try{
			String url = RestUtils.restUrlTransform("releaseProductUrl", flowId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = releaseDao.update(url, ResponseInfo.class); 
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
		
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("镜像版本由开发直接发布生产错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.RELEASE_PRODUCT_ERROR,ex.getMessage());
			}
		}
	}

}
