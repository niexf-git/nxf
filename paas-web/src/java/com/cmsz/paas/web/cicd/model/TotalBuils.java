package com.cmsz.paas.web.cicd.model;

public class TotalBuils {

	private String todayCount;//当天构建次数
	
	private String weekCount;//一周构建次数
	
	private String fourteenDaysCount;//14天构建次数

	public String getTodayCount() {
		return todayCount;
	}

	public void setTodayCount(String todayCount) {
		this.todayCount = todayCount;
	}

	public String getWeekCount() {
		return weekCount;
	}

	public void setWeekCount(String weekCount) {
		this.weekCount = weekCount;
	}

	public String getFourteenDaysCount() {
		return fourteenDaysCount;
	}

	public void setFourteenDaysCount(String fourteenDaysCount) {
		this.fourteenDaysCount = fourteenDaysCount;
	}
	
	
}
