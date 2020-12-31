package com.cmsz.paas.common.model.controller.response;

public class Totalbuilds {

	private int todayBuildNum;
	
	private int lastSevenDayNum;
	
	private int lastFourteenDayNum;

	public int getTodayBuildNum() {
		return todayBuildNum;
	}

	public void setTodayBuildNum(int todayBuildNum) {
		this.todayBuildNum = todayBuildNum;
	}

	public int getLastSevenDayNum() {
		return lastSevenDayNum;
	}

	public void setLastSevenDayNum(int lastSevenDayNum) {
		this.lastSevenDayNum = lastSevenDayNum;
	}

	public int getLastFourteenDayNum() {
		return lastFourteenDayNum;
	}

	public void setLastFourteenDayNum(int lastFourteenDayNum) {
		this.lastFourteenDayNum = lastFourteenDayNum;
	}

	@Override
	public String toString() {
		return "Totalbuilds [todayBuildNum=" + todayBuildNum + ", lastSevenDayNum=" + lastSevenDayNum
				+ ", lastFourteenDayNum=" + lastFourteenDayNum + "]";
	}
}
