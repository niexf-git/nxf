package com.cmsz.paas.common.model.monitor.response;

import java.util.List;

import com.cmsz.paas.common.model.monitor.entity.SysAlarmConfEntity;

public class SysAlarmConfList {

	private List<SysAlarmConfEntity> sysAlarmConfList;

	public List<SysAlarmConfEntity> getSysAlarmConfList() {
		return sysAlarmConfList;
	}

	public void setSysAlarmConfList(List<SysAlarmConfEntity> sysAlarmConfList) {
		this.sysAlarmConfList = sysAlarmConfList;
	}

}
