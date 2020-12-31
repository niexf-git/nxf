package com.cmsz.paas.web.monitoroperation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.monitor.entity.GlobalMonitorEntity;
import com.cmsz.paas.common.model.monitor.response.GlobalMonitorList;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.monitoroperation.dao.GlobalMonitorDao;
import com.cmsz.paas.web.monitoroperation.service.GlobalMonitorService;
/**
 * 全局监控Service.
 * 
 * @author jiayz
 */
@Service("globalMonitorService")
public class GlobalMonitorServiceImpl implements GlobalMonitorService{
	private static final Logger logger = LoggerFactory
			.getLogger(GlobalMonitorServiceImpl.class);
	@Autowired
	private GlobalMonitorDao globalMonitorDao;
	
	@Override
	public List<GlobalMonitorEntity> queryGlobalMonitorList(String dataCenterId,String clusterId,String nodeId)throws PaasWebException {
		List<GlobalMonitorEntity> list = new ArrayList<GlobalMonitorEntity>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.monitorOperationRestUrl("queryGlobalMonitorUrl",dataCenterId,clusterId,nodeId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = globalMonitorDao.get(url,GlobalMonitorList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			GlobalMonitorList gList = (GlobalMonitorList) responseInfo
					.getData();
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (gList != null
						&& gList.getNodeList()!= null) {
					for (int i = 0; i < gList.getNodeList().size(); i++) {
						GlobalMonitorEntity gm =  gList.getNodeList().get(i);
						list.add(gm);
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询全局监控错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.SYSTEM_MONITOR_QUERY_CLUSTER_ERROR,
						ex.getMessage());
			}
		}
		return list;
	}

}
