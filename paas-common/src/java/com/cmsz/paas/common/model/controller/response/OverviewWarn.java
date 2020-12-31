package com.cmsz.paas.common.model.controller.response;

import java.io.Serializable;
import java.util.List;

/**
 * @date 2018-5-8
 * @author guyj
 *  warnstat
 */
public class OverviewWarn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 应用ID */
	private String appId;
	
	/** 告警总数 */
	private int allCount;
	
	/** 操作类型，1-开发，2-测试*/
	private String type;
	
	/** 告警数据详细列表*/
	private List<OverviewWarnCount> countList;

	

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public int getAllCount() {
		return allCount;
	}

	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<OverviewWarnCount> getCountList() {
		return countList;
	}

	public void setCountList(List<OverviewWarnCount> countList) {
		this.countList = countList;
	}

	@Override
	public String toString() {
		return "WarnStat [appId=" + appId + ", allCount=" + allCount + ", type=" + type + ", countList=" + countList
				+ "]";
	}
	
	
}
