package com.cmsz.paas.web.newmonitor.service;

import java.util.List;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.monitoroperation.model.DataCenter;
import com.cmsz.paas.web.newmonitor.model.NewDataCenter;

/** 
 * @author lin.my
 * @version 创建时间：2016年12月20日 下午2:04:54
 */
public interface NewDataCenterService {

	/**
	 * Service层查询数据中心列表
	 * @param operType 
	 * @param appIds 
	 * @return
	 * @throws PaasWebException
	 */
	public List<NewDataCenter> queryDataCenterList(String appIds, String operType) throws PaasWebException;
	

}
