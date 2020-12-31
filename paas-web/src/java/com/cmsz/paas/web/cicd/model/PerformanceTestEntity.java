package com.cmsz.paas.web.cicd.model;

/**
 * 开发性能测试实体
 * 
 * @author lixin
 * @date 2017-11-23
 */
public class PerformanceTestEntity {
	private String flowId;
	/** 仓库类型 */
//	private String codeType;
	/** 测试脚本路径 */
//	private String shellath;
	/** 用户名 */
//	private String userName;
	/** 密码 */
//	private String password;
	/** 测试命令 */
	private String testCmd;
	/** 失败数 */
	private String fallNumber;
	/** 总共案例数 */
	private String allNumber;
	/** 分支名称 */
//	private String branchName;
	
	/** 最短响应时间 */
	private String miniRespTime;
	/** 平均响应时间  */
	private String averRespTime;
	/** 最长响应时间  */
	private String maxRespTime;
	
	/** 卡片公共属性实体 */
	private StepDetailInfo stepDetailInfo;
	
	/**
	 * 上面五个注释在repositoryInfo共用
	 */
	private RepositoryInfo repositoryInfo;
	
	/** 当前登录用户 */
	private String sysUer;

	public StepDetailInfo getStepDetailInfo() {
		return stepDetailInfo;
	}

	public void setStepDetailInfo(StepDetailInfo stepDetailInfo) {
		this.stepDetailInfo = stepDetailInfo;
	}

	public String getTestCmd() {
		return testCmd;
	}

	public void setTestCmd(String testCmd) {
		this.testCmd = testCmd;
	}

	public String getFallNumber() {
		return fallNumber;
	}

	public void setFallNumber(String fallNumber) {
		this.fallNumber = fallNumber;
	}

	public String getAllNumber() {
		return allNumber;
	}

	public void setAllNumber(String allNumber) {
		this.allNumber = allNumber;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public RepositoryInfo getRepositoryInfo() {
		return repositoryInfo;
	}

	public void setRepositoryInfo(RepositoryInfo repositoryInfo) {
		this.repositoryInfo = repositoryInfo;
	}

	public String getSysUer() {
		return sysUer;
	}

	public void setSysUer(String sysUer) {
		this.sysUer = sysUer;
	}

	public String getMiniRespTime() {
		return miniRespTime;
	}

	public void setMiniRespTime(String miniRespTime) {
		this.miniRespTime = miniRespTime;
	}

	public String getAverRespTime() {
		return averRespTime;
	}

	public void setAverRespTime(String averRespTime) {
		this.averRespTime = averRespTime;
	}

	public String getMaxRespTime() {
		return maxRespTime;
	}

	public void setMaxRespTime(String maxRespTime) {
		this.maxRespTime = maxRespTime;
	}
	

}
