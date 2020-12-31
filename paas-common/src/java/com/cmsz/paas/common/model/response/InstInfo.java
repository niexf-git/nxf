package com.cmsz.paas.common.model.response;

public class InstInfo {

	private int unassignedNum;
	
	private int waitingNum;

	private int runningNum;

	private int terminationNum;

	private int unknownNum;

	public void setUnassignedNum(int unassignedNum) {
		this.unassignedNum = unassignedNum;
	}

	public int getUnassignedNum() {
		return unassignedNum;
	}

	public void setWaitingNum(int waitingNum) {
		this.waitingNum = waitingNum;
	}

	public int getWaitingNum() {
		return waitingNum;
	}

	public void setRunningNum(int runningNum) {
		this.runningNum = runningNum;
	}

	public int getRunningNum() {
		return runningNum;
	}

	public void setTerminationNum(int terminationNum) {
		this.terminationNum = terminationNum;
	}

	public int getTerminationNum() {
		return terminationNum;
	}

	public void setUnknownNum(int unknownNum) {
		this.unknownNum = unknownNum;
	}

	public int getUnknownNum() {
		return unknownNum;
	}

	
}
