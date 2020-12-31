package com.cmsz.paas.web.overview.model;


/**
 * 告警统计详细实体
 * @author heym 2018-05-08
 *
 */

public class AlarmDetails {

	/** 告警数*/
	private String alarmCount;
	
	/** 告警时间*/
	private String alarmTime;

	public String getAlarmCount() {
		return alarmCount;
	}

	public void setAlarmCount(String alarmCount) {
		this.alarmCount = alarmCount;
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
}
