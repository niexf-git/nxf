package com.cmsz.paas.web.appservicegray.service;

import java.util.List;

import com.cmsz.paas.web.appservicegray.model.GrayEntity;
import com.cmsz.paas.web.appservicegray.model.GrayRelease;


public interface GrayReleaseService {

	/***
	 * 查询灰度版本
	 * @param appId
	 * @return
	 */
	public List<GrayRelease> queryGrayList(String appId);	
	
	public GrayEntity queryGrayById(String appId, String type);
	
	public String queryCheckResult(String appId);
}
