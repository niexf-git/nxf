package com.cmsz.paas.web.image.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.PrivateImageEntity;
import com.cmsz.paas.common.model.controller.entity.PrivateImageVersionEntity;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.model.controller.response.PrivateImageDetail;
import com.cmsz.paas.common.model.controller.response.PrivateImageList;
import com.cmsz.paas.common.model.controller.response.PrivateImageVersionList;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.common.utils.JsonUtil;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.image.dao.PrivateImageDao;
import com.cmsz.paas.web.image.model.PrivateImage;
import com.cmsz.paas.web.image.model.PrivateImageVersion;
import com.cmsz.paas.web.image.service.PrivateImageService;

/**
 * 私有镜像服务接口
 * 
 * @author lin.my 2016-4-1
 */
@Service("privateImageService")
public class PrivateImageServiceImpl implements PrivateImageService {

	private static final Logger logger = LoggerFactory
			.getLogger(PrivateImageServiceImpl.class);

	@Autowired
	private PrivateImageDao privateImageDao;
	
	private String retCode = ""; // rest接口返回码
	private String msg = ""; // rest接口返回信息

	/**
	 * 查询私有镜像列表
	 */
	public List<PrivateImage> queryPrivateImageList(String name, String appIds, String imageId, String type) throws PaasWebException{
		
		//接收控制中心返回转换数据
		List<PrivateImage> privateImageList = new ArrayList<PrivateImage>();
		PrivateImageList listJson = new PrivateImageList(); // 接收rest接口返回数据

//		if (name == null) {
//			name = "";
//		}
//		if (imageId == null) {
//			imageId = "";
//		}
		// 拼接接口请求地址
		String url = RestUtils.restUrlTransform("queryPrivateImageListUrl",
				name, appIds, imageId, type);

		logger.debug("开始调用查询私有镜像列表rest接口：" + url);

		try {
			ResponseInfo responseInfo = privateImageDao.get(url,
					PrivateImageList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			listJson = (PrivateImageList) responseInfo.getData();

			logger.debug("调用查询私有镜像列表rest接口返回码：" + retCode + ", 返回信息：" + msg);

			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (!listJson.equals(null)) {
					for (PrivateImageEntity privateImageEntity : listJson
							.getPrivateImageList()) {
						privateImageList
								.add(genPrivateImage(privateImageEntity));
					}
				}
				logger.info("调用查询私有镜像列表rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询私有镜像集合出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_PRIVATE_IMAGE_ERROR
						+ "]查询私有镜像集合出错", ex);
				throw new PaasWebException(Constants.QUERY_PRIVATE_IMAGE_ERROR,
						ex.getMessage());
			}
		}
		return privateImageList;
	}

	/**
	 * 查询私有镜像版本列表
	 */
	public List<PrivateImageVersion> queryPrivateImageVersionList(String privateImageId, String version) throws PaasWebException{
		//接收控制中心返回转换数据
		List<PrivateImageVersion> privateImageVersionList = new ArrayList<PrivateImageVersion>();
		PrivateImageVersionList listJson = new PrivateImageVersionList(); // 接收rest接口返回数据

		// 拼接接口请求地址
		String url = RestUtils.restUrlTransform(
				"queryPrivateImageVersionListUrl", privateImageId, version);

		logger.debug("开始调用查询私有镜像版本列表rest接口：" + url);

		try {
			ResponseInfo responseInfo = privateImageDao.get(url,
					PrivateImageVersionList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			listJson = (PrivateImageVersionList) responseInfo.getData();

			logger.debug("调用查询私有镜像版本列表rest接口返回码：" + retCode + ", 返回信息：" + msg);

			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (!listJson.equals(null)) {
					for (PrivateImageVersionEntity privateImageVersionEntity : listJson
							.getPrivateImageVersionList()) {
						privateImageVersionList
								.add(genPrivateImageVersion(privateImageVersionEntity));
					}
				}
				logger.info("调用查询私有镜像版本列表rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询私有镜像版本集合出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_PRIVATE_IMAGE_VERSION_ERROR
						+ "]查询私有镜像版本集合出错", ex);
				throw new PaasWebException(
						Constants.QUERY_PRIVATE_IMAGE_VERSION_ERROR,
						ex.getMessage());
			}
		}
		return privateImageVersionList;
	}
	
	/**
	 * 查询私有镜像版本列表(校验版本号)
	 */
	public List<PrivateImageVersion> queryTheSamePrivateImageVersion(String privateImageId, String version) throws PaasWebException{
		//接收控制中心返回转换数据
		List<PrivateImageVersion> privateImageVersionList = new ArrayList<PrivateImageVersion>();
		PrivateImageVersionList listJson = new PrivateImageVersionList(); // 接收rest接口返回数据

		if (version == null) {
			version = "";
		}

		// 拼接接口请求地址
		String url = RestUtils.restUrlTransform(
				"queryTheSamePrivateImageVersionUrl", privateImageId, version);

		logger.debug("开始调用查询私有镜像版本列表rest接口：" + url);

		try {
			ResponseInfo responseInfo = privateImageDao.get(url,
					PrivateImageVersionList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			listJson = (PrivateImageVersionList) responseInfo.getData();

			logger.debug("调用查询私有镜像版本列表rest接口返回码：" + retCode + ", 返回信息：" + msg);

			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (!listJson.equals(null)) {
					for (PrivateImageVersionEntity privateImageVersionEntity : listJson
							.getPrivateImageVersionList()) {
						privateImageVersionList
								.add(genPrivateImageVersion(privateImageVersionEntity));
					}
				}
				logger.info("调用查询私有镜像版本列表rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询私有镜像版本集合出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_PRIVATE_IMAGE_VERSION_ERROR
						+ "]查询私有镜像版本集合出错", ex);
				throw new PaasWebException(
						Constants.QUERY_PRIVATE_IMAGE_VERSION_ERROR,
						ex.getMessage());
			}
		}
		return privateImageVersionList;
	}

	/**
	 * 控制中心返回数据转换成前台所需数据
	 * 
	 * @param privateImageEntity
	 * @return
	 */
	private PrivateImage genPrivateImage(PrivateImageEntity privateImageEntity) {

		PrivateImage privateImage = new PrivateImage();

		privateImage.setId(privateImageEntity.getId() + "");
		privateImage.setName(privateImageEntity.getName());
		privateImage.setBuildName(privateImageEntity.getBuildName());
		privateImage.setRegistryUser(privateImageEntity.getRegistryUser() + "/" 
		          + privateImageEntity.getRegistryPswd());
		privateImage.setAppId(String.valueOf(privateImageEntity.getAppId()));
		privateImage.setAppName(privateImageEntity.getAppName());
		privateImage.setCreateTime(DateUtil.tranformDate(privateImageEntity
				.getCreateTime().toString()));
		privateImage.setDescription(privateImageEntity.getDescription());

		return privateImage;
	}

	/**
	 * 控制中心返回数据转换成前台所需数据
	 */
	private PrivateImageVersion genPrivateImageVersion(
			PrivateImageVersionEntity privateImageVersionEntity) {

		PrivateImageVersion privateImageVersion = new PrivateImageVersion();

		privateImageVersion.setId(privateImageVersionEntity.getId() + "");
		privateImageVersion.setPrivateImageId(privateImageVersionEntity
				.getPrivateImageId() + "");
		privateImageVersion.setBuildRecordId(privateImageVersionEntity
				.getBuildRecordId() + "");
		privateImageVersion.setStatus(privateImageVersionEntity.getStatus()
				+ "");
		privateImageVersion.setTag(privateImageVersionEntity.getTag());
		privateImageVersion.setUrl(privateImageVersionEntity.getUrl());
		privateImageVersion.setVersion(privateImageVersionEntity.getVersion());
		privateImageVersion.setCreateTime(DateUtil
				.tranformDate(privateImageVersionEntity.getCreateTime()
						.toString()));
		privateImageVersion
				.setCreateBy(privateImageVersionEntity.getCreateBy());
		privateImageVersion.setDescription(privateImageVersionEntity
				.getDescription());
		privateImageVersion.setLogdir(privateImageVersionEntity.getLogDir());
		privateImageVersion.setConfigFilePath(privateImageVersionEntity.getConfigDir());
		privateImageVersion.setIsImported(privateImageVersionEntity.getLoadStatus() + "");
		return privateImageVersion;
	}

	/**
	 * 根据私有镜像名称查询
	 */

	/**
	 * 部署私有镜像
	 */

	/**
	 * 删除私有镜像
	 */
	@Override
	public void deletePrivateImage(String id, String currentContext) throws PaasWebException {
		try {
			//组装访问控制中心的链接，以及参数
			String url = RestUtils.restUrlTransform("deletePrivateImageUrl", id, currentContext);
			logger.debug("删除私有镜像rest接口：" + url);
			// 调用接口，获取数据
			ResponseInfo responseInfo = privateImageDao.delete(url, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("删除私有镜像rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				logger.error("删除私有镜像信息异常");
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("删除私有镜像信息异常",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.DELETE_PRIVATE_IMAGE_ERROR,
						ex.getMessage());
			}
		}
	}
	
	/**
	 * 根据ID查询私有镜像
	 */
	@Override
	public PrivateImage queryPrivateImageById(String id) throws PaasWebException {
		
		PrivateImage privateImage = new PrivateImage();
		
		try {
			//组装访问控制中心的链接，以及参数
			String url = RestUtils.restUrlTransform("queryPrivateImageByIdUrl", id);
			logger.debug("根据ID查询私有镜像rest接口："+url);
			// 调用接口，获取数据
			ResponseInfo responseInfo = privateImageDao.get(url, PrivateImageDetail.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("根据ID查询私有镜像rest接口返回码："+retCode+", 返回信息："+msg);
			
			if(Constants.REST_CODE_SUCCESS.equals(retCode)){
				PrivateImageDetail detail = (PrivateImageDetail) responseInfo.getData();
				if(null != detail.getPrivateImageDetail()){
					privateImage = genPrivateImage(detail.getPrivateImageDetail());
				}
				logger.info("根据ID查询私有镜像rest接口返回成功！");
				return privateImage;
			}else {
				logger.error("根据ID查询私有镜像异常");
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("根据ID查询私有镜像",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_PRIVATE_IMAGE_BY_ID_ERROR, ex.getMessage());
			}
		}
	}
	
	/**
	 * 修改私有镜像
	 */
	@Override
	public void updatePrivateImage(PrivateImage privateImage) throws PaasWebException {
		
		try {
			PrivateImageEntity entity = new PrivateImageEntity();
			entity.setId(Long.parseLong(privateImage.getId()));
			entity.setDescription(privateImage.getDescription());
			
			//组装访问控制中心的链接，以及参数
			String url = RestUtils.restUrlTransform("updatePrivateImageUrl", privateImage.getId());
			logger.debug("修改私有镜像信息rest接口："+url);
			// 调用接口，获取数据
			ResponseInfo responseInfo = privateImageDao.update(url, entity, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("修改私有镜像信息rest接口返回码："+retCode+", 返回信息："+msg);
			
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				logger.error("修改私有镜像信息异常");
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("修改私有镜像信息异常",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.UPDATE_PRIVATE_IMAGE_ERROR, ex.getMessage());
			}
		}
	}
	
	/**
	 * 删除私有镜像版本
	 */
	@Override
	public void deletePrivateImageVersion(String privateImageId,
			String privateImageVersionId, String currentContext) throws PaasWebException {
		try {
			//组装访问控制中心的链接，以及参数
			String url = RestUtils.restUrlTransform("deletePrivateImageVersionUrl", privateImageId, privateImageVersionId, currentContext);
			logger.debug("删除私有镜像版本rest接口：" + url);
			// 调用接口，获取数据
			ResponseInfo responseInfo = privateImageDao.delete(url, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("删除私有镜像版本rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				logger.error("删除私有镜像版本信息异常");
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("删除私有镜像版本信息异常",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.DELETE_PRIVATE_IMAGE_VERSION_ERROR,
						ex.getMessage());
			}
		}
	}

	/**
	 * 私有镜像版本-发布测试
	 */
	@Override
	public void publish2Test(String privateImageId, String privateImageVersionId)
			throws PaasWebException {
		try {
			//组装访问控制中心的链接，以及参数
			String url = RestUtils.restUrlTransform("privateImageVersionPublish2Test", privateImageId, privateImageVersionId);
			logger.debug("私有镜像版本-发布测试rest接口：" + url);
			// 调用接口，获取数据
			// HTTP请求方式:PUT
			// 是否长连接:否
			ResponseInfo responseInfo = privateImageDao.update(url, ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("私有镜像版本-发布测试rest接口返回码：" + retCode + ", 返回信息：" + msg);
			
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				logger.error("私有镜像版本-发布测试异常");
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("私有镜像版本-发布测试异常",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.PRIVATE_IMAGE_VERSION_PUBLISHED2TEST_ERROR, ex.getMessage());
			}
		}
	}

	/**
	 * 私有镜像版本-发布生产
	 */
	@Override
	public void publish2Product(String privateImageId,
			String privateImageVersionId, String version) throws PaasWebException {
		
		BufferedReader reader = null;
		ResponseInfo responseInfo = null;
		try {
			//组装访问控制中心的链接，以及参数
			String url = RestUtils.restUrlTransform("privateImageVersionPublish2Product", privateImageId, privateImageVersionId, version);
			logger.debug("发布生产rest接口：" + url);
			
			String currentLine = null;
			// HTTP请求方式:PUT
			// 是否长连接:是
			reader = privateImageDao.update(url);
			while((currentLine = reader.readLine()) != null){
				//长连接
				responseInfo = JsonUtil.json2ResponseInfoBean(currentLine, ResponseInfo.class);
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
				throw new PaasWebException(Constants.PRIVATE_IMAGE_VERSION_PUBLISHED2PRODUCT_ERROR, ex.getMessage());
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
