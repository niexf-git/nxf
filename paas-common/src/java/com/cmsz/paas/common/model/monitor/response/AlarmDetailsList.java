package com.cmsz.paas.common.model.monitor.response;

import java.util.ArrayList;
import java.util.List;

import com.cmsz.paas.common.model.monitor.entity.AlarmDetailsEntity;

public class AlarmDetailsList {
	private List<AlarmDetailsEntity> list = new ArrayList<AlarmDetailsEntity>();

	public List<AlarmDetailsEntity> getList() {
		return list;
	}

	public void setList(List<AlarmDetailsEntity> list) {
		this.list = list;
	}
}
