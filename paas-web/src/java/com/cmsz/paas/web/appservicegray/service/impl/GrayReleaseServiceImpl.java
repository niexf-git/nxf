package com.cmsz.paas.web.appservicegray.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.AppServiceEntity;
import com.cmsz.paas.common.model.controller.entity.AppServiceVersionEntity;
import com.cmsz.paas.common.model.controller.entity.ConfigFileEntity;
import com.cmsz.paas.common.model.controller.entity.EnvConfigEntity;
import com.cmsz.paas.common.model.controller.response.AppServiceDetail;
import com.cmsz.paas.common.model.controller.response.IdValue;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.utils.BasePropertiesUtil;
import com.cmsz.paas.common.utils.JsonUtil;
import com.cmsz.paas.web.appservice.model.EnvConfig;
import com.cmsz.paas.web.appservicegray.dao.GrayReleaseDao;
import com.cmsz.paas.web.appservicegray.model.GrayEntity;
import com.cmsz.paas.web.appservicegray.model.GrayRelease;
import com.cmsz.paas.web.appservicegray.service.GrayReleaseService;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;

@Service("grayReleaseService")
public class GrayReleaseServiceImpl implements GrayReleaseService{

	@Autowired
	private GrayReleaseDao grayReleaseDao;
	private static final Logger logger = LoggerFactory
			.getLogger(GrayReleaseServiceImpl.class);
	
	public GrayReleaseDao getGrayReleaseDao() {
		return grayReleaseDao;
	}

	public void setGrayReleaseDao(GrayReleaseDao grayReleaseDao) {
		this.grayReleaseDao = grayReleaseDao;
	}
	
	private GrayRelease genGrayVersion(AppServiceEntity appServiceEntity){
		GrayRelease grayRelease = null;
		if( appServiceEntity.getVersions().size()!=0){
			grayRelease = new GrayRelease();
			AppServiceVersionEntity versionEntity = appServiceEntity.getVersions().get(0);
			grayRelease.setId(versionEntity.getId());
			//1为公共镜像 2为私有镜像
			grayRelease.setImageUrl(versionEntity.getImageType()==1?versionEntity.getPublicImage().getName():versionEntity.getPrivateImage().getName());
			grayRelease.setImageversion(versionEntity.getImageType()==1?versionEntity.getPublicImage().getRunningVersion().getVersion():
				versionEntity.getPrivateImage().getRunningVersion().getVersion());
			grayRelease.setInstanceCount(appServiceEntity.getTotalInstanceNum()+"");
			grayRelease.setRunInstance(appServiceEntity.getRunningInstanceNum()+"/"+appServiceEntity.getGrayInstanceNum());
			grayRelease.setState(appServiceEntity.getGrayVersionStatus()+"");
			grayRelease.setUpdateTime(simpleDateToStr(appServiceEntity.getCreateTime()));
		}
		return grayRelease;
		
	}
	
	private String simpleDateToStr(Date date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	

	@Override
	public List<GrayRelease> queryGrayList(String appId) {
		List<GrayRelease> grayReleaseList = new ArrayList<GrayRelease>();
		String msg = "";
		String retCode = "";
		AppServiceDetail appServiceDetail = null;
		
		String url = RestUtils
				.restUrlTransform("queryGrayVersionUrl", appId);
		logger.debug("开始调用查询灰度列表的rest接口：" + url);

		try {
			ResponseInfo responseInfo = grayReleaseDao.get(url, AppServiceDetail.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			appServiceDetail =  (AppServiceDetail) responseInfo.getData();
			logger.debug("调用查询灰度列表的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (!appServiceDetail.equals(null)) {
					AppServiceEntity appServiceEntity = appServiceDetail.getAppServiceDetail();
					GrayRelease release = genGrayVersion(appServiceEntity);
					if(release!=null){
						grayReleaseList.add(release);
					}
				}
				logger.info("调用查询灰度列表的rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询灰度列表出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_GRAYRELEASELIST_ERROR
						+ "]查询灰度列表出错", ex);
				throw new PaasWebException(
						Constants.QUERY_GRAYRELEASELIST_ERROR, ex.getMessage());
			}
		}
		return grayReleaseList;
	}
	
	private GrayEntity genGrayEntity(AppServiceEntity appServiceEntity){
		GrayEntity grayEntity = new GrayEntity();
		grayEntity.setAppServiceName(appServiceEntity.getName());
		grayEntity.setOper_type(appServiceEntity.getOperateType());
		grayEntity.setGray(appServiceEntity.getIsGrayVer());
		grayEntity.setDeploymentType(appServiceEntity.getDeployMode());
		grayEntity.setId(appServiceEntity.getAppId());
		grayEntity.setGrayInstanceNum(appServiceEntity.getGrayInstanceNum());
		grayEntity.setTotalInstanceNum(appServiceEntity.getTotalInstanceNum());
		grayEntity.setAppId(appServiceEntity.getId());
		grayEntity.setState(String.valueOf(appServiceEntity.getStatus()));
		int index = 0;
		if((index = getGrayVersionIndex(appServiceEntity.getVersions(),true))!= -1){
			AppServiceVersionEntity versionEntity = appServiceEntity.getVersions().get(index);
			grayEntity.setImageType(versionEntity.getImageType());
			grayEntity.setImageId(versionEntity.getImageId());
			grayEntity.setImageVersionId(versionEntity.getRunningVersion());
			grayEntity.setLogDir(versionEntity.getLogDir());
			grayEntity.setEnvConfig(versionEntity.getEnvs());
			grayEntity.setConfigFilePath(versionEntity.getConfigFile() == null?null:versionEntity.getConfigFile().getConfigDir());
			grayEntity.setConfigFileInfo(versionEntity.getConfigFile() == null?null:versionEntity.getConfigFile().getContent());
			grayEntity.setRunningVersion(versionEntity.getRunningVersion());
			if(appServiceEntity.getVersions().size()>1){
				AppServiceVersionEntity serviceVersionEntity = appServiceEntity.getVersions().get(getGrayVersionIndex(appServiceEntity.getVersions(),false));
				grayEntity.setRunVersion(serviceVersionEntity.getImageType()==1?serviceVersionEntity.getPublicImage().getRunningVersion().getVersion():serviceVersionEntity.getPrivateImage().getRunningVersion().getVersion());
			}
		}else{
			if(appServiceEntity.getVersions().size()>0){
				AppServiceVersionEntity serviceVersionEntity = appServiceEntity.getVersions().get(0);
				grayEntity.setRunVersion(serviceVersionEntity.getImageType()==1?serviceVersionEntity.getPublicImage().getRunningVersion().getVersion():serviceVersionEntity.getPrivateImage().getRunningVersion().getVersion());
			}
		}
		return grayEntity;
	}
	
	
	private int getGrayVersionIndex(List<AppServiceVersionEntity> versions,boolean flag){
		int index = -1;
		if(versions.size()<1){
			return -1;
		}
		for (int i = 0; i < versions.size(); i++) {
			if(versions.get(i).getType()== (flag?2:1)){
				index = i;
				break;
			}
		}
		return index;
	}
	@Override
	public GrayEntity queryGrayById(String appId, String type) {
		logger.info("收到查询灰度服务详情请求，appId：" + appId);
		String url = RestUtils.restUrlTransform("update".equals(type)?"queryGrayVersionUrl":"queryAppServiceById", appId);
		ResponseInfo responseInfo;
		AppServiceDetail appServiceDetail;
		GrayEntity grayEntity = null;
		String retCode = "";
		String msg = "";
		try {
			logger.debug("开始调用查询灰度服务详情的rest接口：" + url);
			responseInfo = grayReleaseDao.get(url, AppServiceDetail.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.CONTROLLER_CONN_ERROR,
						"无法访问查询灰度服务详情的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			appServiceDetail = (AppServiceDetail) responseInfo.getData();
			logger.debug("调用查询灰度服务详情的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用查询灰度服务详情的rest接口返回成功！应用服务ID:" + appId);
				if (!appServiceDetail.getAppServiceDetail().equals(null)) {
					AppServiceEntity appServiceEntity = appServiceDetail.getAppServiceDetail();
					grayEntity = genGrayEntity(appServiceEntity);
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("[" + retCode + "]查询灰度服务详情出错", ex);
			throw new PaasWebException(retCode, msg);
		}
		return grayEntity;
	}

	@Override
	public String queryCheckResult(String appId) {
		logger.info("收到查询灰度服务校验请求，appId：" + appId);
		String result = "";
		String url = RestUtils.restUrlTransform("queryGrayCheckUrl", appId);
		ResponseInfo responseInfo;
		String retCode = "";
		String msg = "";
		try {
			logger.debug("开始调用查询灰度服务详情的rest接口：" + url);
			responseInfo = grayReleaseDao.get(url, String.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.CONTROLLER_CONN_ERROR,
						"无法访问查询灰度服务详情的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用查询灰度服务校验的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用查询灰度服务校验的rest接口返回成功！应用服务ID:" + appId);
				result = msg;
			} else {
				result = BasePropertiesUtil.getValue(retCode);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询灰度服务详情出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_GRAYRELEASEDETAIL_ERROR
						+ "]查询灰度服务详情出错", ex);
				throw new PaasWebException(Constants.QUERY_GRAYRELEASEDETAIL_ERROR,
						ex.getMessage());
			}
		}
		return result;
	}
	

}
