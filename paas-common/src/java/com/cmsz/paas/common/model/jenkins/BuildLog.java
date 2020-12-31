/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File BuildLog.java
 */
package com.cmsz.paas.common.model.jenkins;

/**
 * @author hehm
 * 2016-4-8
 */
public class BuildLog {

	private String log;

	private boolean haveMore;

	private long index;

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public boolean isHaveMore() {
		return haveMore;
	}

	public void setHaveMore(boolean haveMore) {
		this.haveMore = haveMore;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	} 
}
