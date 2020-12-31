package com.cmsz.paas.web.alarm.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.web.alarm.dao.AlarmDao;
import com.cmsz.paas.web.alarm.entity.AlarmDetail;
import com.cmsz.paas.web.alarm.service.AlarmService;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.constants.Constants;

/**
 * 系统告警服务类
 * 
 * @author li.lv 2015-4-14
 */
@Service("alarmService")
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	private AlarmDao alarmDao;

	private static final Logger logger = LoggerFactory
			.getLogger(AlarmServiceImpl.class);
	/***
	 * 查询系统告警列表
	 */
	@Override
	public Page<AlarmDetail> findPage(PageContext pc) throws Exception {
		Page<AlarmDetail> page = new Page<AlarmDetail>();
		try {
			page = alarmDao.findPage(pc);
			return page;
		} catch (PaasWebException ex) {
			logger.error("分页查询系统告警信息异常", ex);
			throw new PaasWebException(Constants.QUERY_ALARM_ERROR,
					ex.getMessage());
		}
	}
	/***
	 * 查询系统告警
	 */
	@Override
	public List<AlarmDetail> findByTime(String selectAppName)
			throws PaasWebException {
		try {
			// 告警提示数据时间范围
			int timeRange = Integer.parseInt(PropertiesUtil
					.getValue("alarmTime"));
			Date date = new Date();
			DateTime dt = new DateTime(date);
			String maxDate = DateUtil.tranformDate(date.toString());
			DateTime preDate = dt.minusSeconds(timeRange);
			String minDate = DateUtil.tranformDate(preDate.toDate().toString());
			Map<String, String> map = new HashMap<String, String>();
			// 资源类型
			String eventItem = "('3','4')";
			map.put("insertTimeStart", minDate);
			map.put("insertTimeEnd", maxDate);
//			// 数据权限
//			map.put("dataPermission", dataPermission);
			map.put("eventItem", eventItem);
			String strName="";
			String [] arr = selectAppName.split(",");
			for(int i=0;i<arr.length;i++){
				strName+="'"+arr[i]+"'";
				if(i!=arr.length-1){
					strName+=",";
				}
			}
			map.put("appName", strName);
			return alarmDao.findBy(map);
		} catch (Exception ex) {
			logger.error("查询系统告警信息异常", ex);
			throw new PaasWebException(Constants.QUERY_ALARM_ERROR,
					ex.getMessage());
		}
	}
	/***
	 * 删除系统
	 */
//	@Override
//	public void deleteByAlarmDetail(String clusterLabel, String groupName,
//			String appName) throws Exception {
//		AlarmDetail alarmDetail = new AlarmDetail();
//		alarmDetail.setClusterLabel(clusterLabel);
//		alarmDetail.setGroupName(groupName);
//		alarmDetail.setAppName(appName);
//		try {
//			alarmDao.delete(alarmDetail);
//		} catch (Exception ex) {
//			logger.error("删除应用同时删除告警信息异常", ex);
//			throw new PaasWebException(Constants.DELETE_ALARM_ERROR,
//					ex.getMessage());
//		}
//	}

}
