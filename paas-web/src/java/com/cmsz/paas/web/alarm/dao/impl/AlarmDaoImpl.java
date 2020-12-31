/**
 * @author li.lv
 */
package com.cmsz.paas.web.alarm.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.dao.IBatis3DaoSupport;
import com.cmsz.paas.web.alarm.dao.AlarmDao;
import com.cmsz.paas.web.alarm.entity.AlarmDetail;

/**
 * @author li.lv
 * 2015-4-14	
 */
@Repository("alarmDao")
public class AlarmDaoImpl extends IBatis3DaoSupport<AlarmDetail, String> implements AlarmDao {

}
