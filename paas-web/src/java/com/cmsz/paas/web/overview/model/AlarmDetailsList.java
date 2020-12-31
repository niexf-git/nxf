package com.cmsz.paas.web.overview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 告警统计列表实体
 * @author heym 2018-05-08
 *
 */

public class AlarmDetailsList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 告警数据详细列表 */
	private List<AlarmDetails> alarmDetailsList;
	
	public AlarmDetailsList(){
		alarmDetailsList = new ArrayList<AlarmDetails>();
	}

	public List<AlarmDetails> getAlarmDetailsList() {
		return alarmDetailsList;
	}

	public void setAlarmDetailsList(List<AlarmDetails> alarmDetailsList) {
		this.alarmDetailsList = alarmDetailsList;
	}


}