package com.cmsz.paas.web.monitoroperation.service;

import java.util.List;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.monitoroperation.model.DataCenter;

/** 
 * @author lin.my
 * @version 创建时间：2016年12月20日 下午2:04:54
 */
public interface DataCenterService {

	/**
	 * Service层查询数据中心列表
	 * @return
	 * @throws PaasWebException
	 */
	public List<DataCenter> queryDataCenterList() throws PaasWebException;
	
	/**
	 * Service层新增数据中心
	 * @param dataCenter
	 * @throws PaasWebException
	 */
	public void createDataCenter(DataCenter dataCenter) throws PaasWebException;

	/**
	 * Service层删除数据中心
	 * @param id
	 * @throws PaasWebException
	 */
	public void deleteDataCenter(String id) throws PaasWebException;

	public void updateDataCenter(DataCenter dataCenter) throws PaasWebException;

}
