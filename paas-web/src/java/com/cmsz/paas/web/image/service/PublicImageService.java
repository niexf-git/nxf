package com.cmsz.paas.web.image.service;

import java.util.List;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.image.model.PublicImage;

/**
 * 公共镜像服务接口
 * 
 * @author lin.my 2016-4-15
 */
public interface PublicImageService {

	/**
	 * 查询公共镜像列表
	 * @param name
	 * @param roleType
	 * @param imageType
	 * @param publicImageId 
	 * @return
	 * @throws Exception
	 */
	public List<PublicImage> queryPublicImageList(String name, String roleType, String imageType, String publicImageId) throws PaasWebException;
	
	/**
	 * 根据ID查询公共镜像
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PublicImage queryPublicImageById(String id) throws PaasWebException;
	
	/**
	 * 修改公共镜像信息
	 * @param publicImage
	 * @throws PaasWebException
	 */
	public void updatePublicImage(PublicImage publicImage) throws PaasWebException;
	
	/**
	 * 部署私有镜像
	 */
	
	/**
	 * 公共镜像-发布生产
	 * @param publicImageId
	 * @param versionId
	 * @param dataCenterIds
	 * @throws PaasWebException
	 */
	public void publish2Product(String publicImageId, String versionId, String dataCenterIds) throws PaasWebException;
	
}
