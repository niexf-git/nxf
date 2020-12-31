package com.cmsz.paas.web.newmonitor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.monitor.entity.DataCenterEntity;
import com.cmsz.paas.common.model.monitor.response.DataCenterList;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.monitoroperation.model.DataCenter;
import com.cmsz.paas.web.newmonitor.dao.NewDataCenterDao;
import com.cmsz.paas.web.newmonitor.model.NewDataCenter;
import com.cmsz.paas.web.newmonitor.service.NewDataCenterService;

/**
 * 
 * @author lin.my
 * @version 创建时间：2016年12月20日 下午2:02:23
 */
@Service("newDataCenterService")
public class NewDataCenterServiceImpl implements NewDataCenterService {

	private static final Logger logger = LoggerFactory.getLogger(NewDataCenterServiceImpl.class);

	@Autowired
	private NewDataCenterDao dataCenterDao; // 监控运维
	
//	@Autowired
//	private DataCenter4ControlDao dataCenter4ControlDao; // 控制中心
	
	private String retCode = ""; // rest接口返回码
	private String msg = ""; // rest接口返回信息

	/**
	 * Service层查询数据中心列表
	 * @return
	 * @throws PaasWebException
	 */
	public List<NewDataCenter> queryDataCenterList(String appIds, String operType) throws PaasWebException{
		
		//接收监控运维返回转换数据
		List<NewDataCenter> dataCenterList = new ArrayList<NewDataCenter>();
		DataCenterList listJson = new DataCenterList(); // 接收rest接口返回数据

		// 拼接接口请求地址
		String url = RestUtils.restUrlTransform("queryNewDataCenterListUrl",appIds,operType);
		try {
			logger.debug("开始调用查询数据中心列表rest接口：" + url);
			ResponseInfo responseInfo = dataCenterDao.get(url, DataCenterList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			listJson = (DataCenterList) responseInfo.getData();

			logger.debug("调用查询数据中心列表rest接口返回码：" + retCode + ", 返回信息：" + msg);

			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (!listJson.equals(null)) {
//				if (null != listJson || !"".equals(listJson)) {
					for (DataCenterEntity dataCenterEntity : listJson.getDataCenterList()) {
						dataCenterList.add(transformDataCenter(dataCenterEntity));
					}
				}
				logger.info("调用查询数据中心列表rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询数据中心出错", ex);
				throw new PaasWebException(retCode, msg);
			} 
			else {
				logger.error("[" + Constants.QUERY_DATACENTER_LIST_ERROR + "]查询数据中心出错", ex);
				throw new PaasWebException(Constants.QUERY_DATACENTER_LIST_ERROR, ex.getMessage());
			}
		}
		return dataCenterList;
	}
	
	/**
	 * 监控运维返回数据转换成前台所需数据
	 * 
	 * @param privateImageEntity
	 * @return
	 */
	private NewDataCenter transformDataCenter(DataCenterEntity dataCenterEntity) {

		NewDataCenter dataCenter = new NewDataCenter();
		
		dataCenter.setId(dataCenterEntity.getId() + "");
		dataCenter.setName(dataCenterEntity.getName());
		dataCenter.setIsMainDataCenter(dataCenterEntity.getIsMain() + "");
		dataCenter.setIpaasHaproxy(dataCenterEntity.getIpaasHaproxyUrl());
		dataCenter.setAppHaproxy(dataCenterEntity.getAppHaproxyUrl());
		dataCenter.setRegistryUrl(dataCenterEntity.getRegistryUrl());
		dataCenter.setHarborUrl(dataCenterEntity.getHarborUrl());
		dataCenter.setHarborUser(dataCenterEntity.getHarborUser());
		dataCenter.setHarborPasswd(dataCenterEntity.getHarborPwd());
		dataCenter.setHarborId(dataCenterEntity.getHarborTargetId() + "");
		dataCenter.setDescription(dataCenterEntity.getDescription());
		// 由Date类型转换为String
		dataCenter.setInsertTime(DateUtil.tranformDate(dataCenterEntity.getInsertTime().toString()));
		
		return dataCenter;
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
