package com.cmsz.paas.web.monitoroperation.service;

import java.util.List;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.monitoroperation.model.ImageSync;
import com.cmsz.paas.web.monitoroperation.model.ImageSyncQueryConditions;

/** 
 * @author lin.my
 * @version 创建时间：2017年1月12日 下午3:47:55
 */
public interface ImageSyncService {

	public List<ImageSync> queryImageSyncList(ImageSyncQueryConditions condition) throws PaasWebException;

	public void loadPublicImages() throws PaasWebException;

}
