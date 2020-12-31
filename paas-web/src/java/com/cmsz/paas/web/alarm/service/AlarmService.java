package com.cmsz.paas.web.alarm.service;

import java.util.List;

import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.web.alarm.entity.AlarmDetail;

/**
 * 系统告警服务接口
 * 
 * @author li.lv 2015-4-14
 */
public interface AlarmService {

	/**
	 * 分页查询系统告警信息.
	 * 
	 * @param PageContext
	 * @return Page<AlarmDetail>
	 * @throws Exception
	 */
	public Page<AlarmDetail> findPage(PageContext pc) throws Exception;

	/**
	 * 根据时间和资源查询系统告警信息.
	 * @param selectAppName 
	 * 
	 * @param dataPermission
	 * @return List<AlarmDetail>
	 * @throws Exception
	 */
	public List<AlarmDetail> findByTime(String selectAppName) throws Exception;

	/**
	 * 删除系统告警信息.
	 * 
	 * @param clusterLabel 集群
	 * @param groupName 应用组
	 * @param appName 应用
	 * @throws Exception
	 */
//	public void deleteByAlarmDetail(String clusterLabel, String groupName,
//			String appName) throws Exception;
}
