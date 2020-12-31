package com.cmsz.paas.common.model.controller.response;

import java.io.Serializable;
import java.util.Date;

public class OverviewWarnCount implements Serializable {
	
	/**
	 * @date 2018-5-8
	 * @author guyj
	 *  countList
	 */
	private static final long serialVersionUID = 1L;
	
	/** 告警数*/
	private int warnCount;
	
	/** 告警时间*/
	private Date warnTime;

	public int getWarnCount() {
		return warnCount;
	}

	public void setWarnCount(int warnCount) {
		this.warnCount = warnCount;
	}

	public Date getWarnTime() {
		return warnTime;
	}

	public void setWarnTime(Date warnTime) {
		this.warnTime = warnTime;
	}

	@Override
	public String toString() {
		return "OverviewWarnCount [warnCount=" + warnCount + ", warnTime=" + warnTime + "]";
	}
	
	

}
