package com.cmsz.paas.web.image.service;

import java.util.List;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.image.model.PrivateImage;
import com.cmsz.paas.web.image.model.PrivateImageVersion;

/**
 * 私有镜像服务接口
 * 
 * @author lin.my
 * 2016-4-1
 */
public interface PrivateImageService {

	/**
	 * 查询私有镜像列表
	 */
	public List<PrivateImage> queryPrivateImageList(String name, String appIds, String imageId, String type) throws PaasWebException;
	
	/**
	 * 查询私有镜像版本列表
	 */
	public List<PrivateImageVersion> queryPrivateImageVersionList(String privateImageId, String version) throws PaasWebException;
	
	public List<PrivateImageVersion> queryTheSamePrivateImageVersion(String privateImageId, String version) throws PaasWebException;
	
	/**
	 * 根据私有镜像名称查询
	 */
	
	
	/**
	 * 部署私有镜像
	 */
	
	/**
	 * 删除私有镜像
	 * @param String id 
	 * @throws Exception
	 */
	public void deletePrivateImage(String id, String currentContext) throws PaasWebException;
	
	/**
	 * 删除私有镜像版本
	 * @param privateImageId
	 * @param privateImageVersionId
	 * @throws PaasWebException
	 */
//	public void deletePrivateImageVersion(String privateImageId, String privateImageVersionId) throws PaasWebException;
	public void deletePrivateImageVersion(String privateImageId, String privateImageVersionId, String currentContext) throws PaasWebException;
	
	/**
	 * 根据ID查询私有镜像
	 * @param id
	 * @return
	 * @throws PaasWebException
	 */
	public PrivateImage queryPrivateImageById(String id) throws PaasWebException;
	
	/**
	 * 修改私有镜像
	 * @param privateImage
	 * @throws PaasWebException
	 */
	public void updatePrivateImage(PrivateImage privateImage) throws PaasWebException;
	
	/**
	 * 发布测试
	 * @param privateImageId
	 * @param privateImageVersionId
	 * @throws PaasWebException
	 */
	public void publish2Test(String privateImageId, String privateImageVersionId) throws PaasWebException;
	
	/**
	 * 发布生产
	 * @param privateImageId
	 * @param privateImageVersionId
	 * @throws PaasWebException
	 */
	public void publish2Product(String privateImageId, String privateImageVersionId, String version) throws PaasWebException;
	
}
