package com.cmsz.paas.web.monitoroperation.service;

import java.util.List;

import com.cmsz.paas.common.model.monitor.entity.GlobalMonitorEntity;
import com.cmsz.paas.web.base.exception.PaasWebException;

/**
 * 全局监控Service.
 * 
 * @author jiayz
 */
public interface GlobalMonitorService {
	List<GlobalMonitorEntity> queryGlobalMonitorList(String dataCenterId,String clusterId,String nodeId)throws PaasWebException;
}
