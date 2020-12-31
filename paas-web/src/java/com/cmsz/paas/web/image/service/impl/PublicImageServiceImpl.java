package com.cmsz.paas.web.image.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.PublicImageEntity;
import com.cmsz.paas.common.model.controller.entity.PublicImageVersionEntity;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.model.controller.response.PublicImageDetail;
import com.cmsz.paas.common.model.controller.response.PublicImageList;
import com.cmsz.paas.common.model.response.PublishIntermedMsg;
import com.cmsz.paas.common.utils.JsonUtil;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.image.dao.PublicImageDao;
import com.cmsz.paas.web.image.model.PublicImage;
import com.cmsz.paas.web.image.model.PublicImageVersion;
import com.cmsz.paas.web.image.service.PublicImageService;

/**
 * 公共镜像服务接口
 * @author lin.my 2016-4-15
 */
@Service("publicImageService")
public class PublicImageServiceImpl implements PublicImageService {
	
	private static final Logger logger = LoggerFactory.getLogger(PublicImageServiceImpl.class);
	
	@Autowired
	private PublicImageDao publicImageDao;
	
	String msg = ""; //rest接口返回信息
	String retCode = ""; //rest接口返回码

	/**
	 * 查询公共镜像列表
	 */
	@Override
	public List<PublicImage> queryPublicImageList(String name, String roleType, String imageType, String publicImageId) throws PaasWebException{
		
		//接收控制中心返回转换数据
		List<PublicImage> publicImageList = new ArrayList<PublicImage>();
		
		PublicImageList listJson = new PublicImageList(); //接收rest接口返回数据
		
		//拼接接口请求地址
		String url = RestUtils.restUrlTransform("queryPublicImageListUrl", name, roleType, imageType, publicImageId);
		
		logger.debug("开始调用查询公共镜像列表rest接口："+url);
		
		try {
			ResponseInfo responseInfo = publicImageDao.get(url, PublicImageList.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			listJson = (PublicImageList) responseInfo.getData();
			
			logger.debug("调用查询公共镜像列表rest接口返回码："+retCode+", 返回信息："+msg);
			
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (!listJson.equals(null)) {
					for(PublicImageEntity publicImageEntity : listJson.getPublicImageList()){
						if(publicImageEntity.getVersions().size() > 0){
							publicImageList.add(transformPublicImage(publicImageEntity));
						}
					}
				}
				logger.info("调用查询公共镜像列表rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("["+retCode+"]查询公共镜像集合出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("["+Constants.QUERY_PUBLIC_IMAGE_ERROR+"]查询公共镜像集合出错", ex);
				throw new PaasWebException(Constants.QUERY_PUBLIC_IMAGE_ERROR, ex.getMessage());
			}
		}
		return publicImageList;
	}
	
	/**
	 * 控制中心返回数据转换成前台所需数据
	 * @param publicImageEntity
	 * @return
	 */
	private PublicImage transformPublicImage(PublicImageEntity publicImageEntity){
		
		PublicImage publicImage = new PublicImage();
		
		publicImage.setId(publicImageEntity.getId()+"");
		publicImage.setName(publicImageEntity.getName());
		//publicImage.setDeployPath(publicImageEntity.getDeployPath());
		publicImage.setStartCmd(publicImageEntity.getStartCmd());
		publicImage.setLogDir(publicImageEntity.getLogDir());
//		publicImage.setRegistryUrl(publicImageEntity.getRegistryUrl());
		publicImage.setType(publicImageEntity.getType());
		publicImage.setDescription(publicImageEntity.getDescription());
		publicImage.setConfigFilePath(publicImageEntity.getConfigDir());
		
		if(publicImageEntity.getVersions() != null){
			//如果公共镜像版本有多个，只取第一个（即下标为0）
			String version = publicImageEntity.getVersions().get(0).getVersion();
			String status = publicImageEntity.getVersions().get(0).getStatus()+"";
			String versionId = publicImageEntity.getVersions().get(0).getId()+"";
			String url = publicImageEntity.getVersions().get(0).getUrl();
			String isImported = publicImageEntity.getVersions().get(0).getLoadStatus()+"";
			publicImage.setVersion(version);
			publicImage.setStatus(status);
			publicImage.setVersionId(versionId);
			publicImage.setRegistryUrl(url);
			publicImage.setIsImported(isImported);
		}
		
		List<PublicImageVersion> list = new ArrayList<PublicImageVersion>();
		if(publicImageEntity.getVersions() != null){
			for(PublicImageVersionEntity entity : publicImageEntity.getVersions()){
				list.add(transformPublicImageVersion(entity));
			}
		}
		publicImage.setVersions(list);
		
		return publicImage;
	}
	
	/**
	 * 公共镜像版本转换（控制中心到前台）
	 * @param entity
	 * @return
	 */
	private PublicImageVersion transformPublicImageVersion(PublicImageVersionEntity entity){
		
		PublicImageVersion version = new PublicImageVersion();
		
		version.setId(entity.getId()+"");
		version.setPublicImageId(entity.getPublicImageId()+"");
		version.setStatus(entity.getStatus()+"");
		version.setTag(entity.getTag());
		//version.setUrl(entity.getUrl());
		version.setVersion(entity.getVersion());
		version.setDescription(entity.getDescription());
		
		return version;
	}
	
	/**
	 * 公共镜像版本转换（前台到控制中心）
	 * @param version
	 * @return
	 */
	private PublicImageVersionEntity transformPublicImageVersionEntity(PublicImageVersion version){
		
		PublicImageVersionEntity entity = new PublicImageVersionEntity();
		
		entity.setId(Long.parseLong(version.getId()));
		entity.setPublicImageId(Long.parseLong(version.getPublicImageId()));
		entity.setStatus(Integer.parseInt(version.getStatus()));
		entity.setTag(version.getTag());
		//entity.setUrl(version.getUrl());
		entity.setVersion(version.getVersion());
		entity.setDescription(version.getDescription());
		
		return entity;
	}

	/**
	 * 根据ID查询公共镜像
	 */
	@Override
	public PublicImage queryPublicImageById(String id) throws PaasWebException {
		PublicImage publicImage = new PublicImage();
		try {
			//组装访问控制中心的链接，以及参数
			String url = RestUtils.restUrlTransform("queryPublicImageByIdUrl", id);
			logger.debug("根据ID查询公共镜像rest接口："+url);
			// 调用接口，获取数据
			ResponseInfo responseInfo = publicImageDao.get(url, PublicImageDetail.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("根据ID查询公共镜像rest接口返回码："+retCode+", 返回信息："+msg);
			
			if(Constants.REST_CODE_SUCCESS.equals(retCode)){
				PublicImageDetail detail = (PublicImageDetail) responseInfo.getData();
				if(null != detail.getPublicImageDetail()){
					publicImage = transformPublicImage(detail.getPublicImageDetail());
				}
				logger.info("根据ID查询公共镜像rest接口返回成功！");
				return publicImage;
			}else {
				logger.error("根据ID查询公共镜像异常");
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("根据ID查询公共镜像",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_PUBLIC_IMAGE_BY_ID_ERROR, ex.getMessage());
			}
		}
	}

	/**
	 * 修改公共镜像信息
	 */
	@Override
	public void updatePublicImage(PublicImage publicImage) throws PaasWebException {
		try {
			PublicImageEntity entity = new PublicImageEntity();
			entity.setId(Long.parseLong(publicImage.getId()));
//			entity.setName(publicImage.getName());
//			entity.setRegistryUrl(publicImage.getRegistryUrl());
//			entity.setType(publicImage.getType());
			
			//切换公共镜像可见状态on/off用到
			if(publicImage.getVersions() != null && publicImage.getVersions().size() > 0){
				List<PublicImageVersionEntity> versions = new ArrayList<PublicImageVersionEntity>();
				for(PublicImageVersion version : publicImage.getVersions()){
					versions.add(transformPublicImageVersionEntity(version));
				}
				entity.setVersions(versions);
			}
			
			//entity.setDeployPath(publicImage.getDeployPath());
			entity.setStartCmd(publicImage.getStartCmd());
			entity.setLogDir(publicImage.getLogDir());
			entity.setDescription(publicImage.getDescription());
			entity.setConfigDir(publicImage.getConfigFilePath());
			//组装访问控制中心的链接，以及参数
			String url = RestUtils.restUrlTransform("updatePublicImageUrl", publicImage.getId());
			logger.debug("修改公共镜像信息rest接口："+url);
			// 调用接口，获取数据
			ResponseInfo responseInfo = publicImageDao.update(url, entity, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("修改公共镜像信息rest接口返回码："+retCode+", 返回信息："+msg);
			
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				logger.error("修改公共镜像信息异常");
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("修改公共镜像信息异常",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.UPDATE_PUBLIC_IMAGE_ERROR, ex.getMessage());
			}
		}
	}

	/**
	 * 公共镜像-发布生产
	 */
	@Override
	public void publish2Product(String publicImageId, String versionId,
			String dataCenterIds) throws PaasWebException {
		
		BufferedReader reader = null;
		ResponseInfo responseInfo = null;
		try {
			//组装访问控制中心的链接，以及参数
			String url = RestUtils.restUrlTransform("publicImagePublish2Product", publicImageId, versionId, dataCenterIds);
			logger.debug("发布生产rest接口：" + url);
			
			String currentLine = null;
			reader = publicImageDao.update(url);
			while((currentLine = reader.readLine()) != null){
				//长连接
				//中间消息data用common下的实体解析
				//paas-common\src\java\com\cmsz\paas\common\model\responsev3\PublishIntermedMsg.java
				responseInfo = JsonUtil.json2ResponseInfoBean(currentLine, PublishIntermedMsg.class);
				retCode = responseInfo.getRetCode();
				msg = responseInfo.getMsg();
				logger.info("发布生产rest接口返回码：" + retCode + ", 返回信息：" + msg);
			}
			
			if (!retCode.contains("PAAS-00") && !retCode.equals(Constants.REST_CODE_SUCCESS)){//不是PAAS-00开头的都是错误码
				throw new PaasWebException(retCode, msg);
			}
			
		} catch (Exception ex) {
			logger.error("发布生产异常",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.PUBLIC_IMAGE_PUBLISHED2PRODUCT_ERROR, ex.getMessage());
			}
		} finally {
			try {
				if(reader != null){
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new PaasWebException(Constants.PUBLISHED2PRODUCT_WEB_SOCKET_STREAM_CLOSE_ERROR, e.getMessage());
			}
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	
}
