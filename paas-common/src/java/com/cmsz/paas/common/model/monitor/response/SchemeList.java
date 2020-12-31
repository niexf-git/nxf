package com.cmsz.paas.common.model.monitor.response;

import java.util.List;

import com.cmsz.paas.common.model.monitor.entity.SchemeEntity;

/**
 * Scheme
 * 
 * @author lixiaofu
 * 
 * @date 2016-12-20
 */
public class SchemeList {

	private List<SchemeEntity> schemeList;

	public List<SchemeEntity> getSchemeList() {
		return schemeList;
	}

	public void setSchemeList(List<SchemeEntity> schemeList) {
		this.schemeList = schemeList;
	}

}
