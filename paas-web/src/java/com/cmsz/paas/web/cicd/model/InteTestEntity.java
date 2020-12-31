package com.cmsz.paas.web.cicd.model;

import java.util.List;
import java.util.Map;

import com.cmsz.paas.web.base.model.SelectType;

/**
 * 开发集成测试实体
 * 
 * @author ccl
 * @date 2017-8-25
 * 
 */
public class InteTestEntity {
	
//	/** 仓库类型 */
//	private int codeType;
	/** 0创建，1引用 */
	private String inteType;
	/** 代码库信息 */
	private RepositoryInfo repositoryInfo;
//	/** 测试脚本路径 */
//	private String shellpath;
//	/** 用户名 */
//	private String userName;
//	/** 密码 */
//	private String password;
	/** 测试命令 */
	private String testCmd;
	/** 引用的集成测试名称*/
	private String aliasName;
	/** 失败数 */
	private int fallNumber;
	/** 总共案例数 */
	private int allNumber;
	/** 卡片公共属性实体 */
	private StepDetailInfo stepDetailInfo;
	/** 集成测试下拉列表 */
	private List<Map<String, Object>> relationName;
	/** 时间 */
	private double time;
//	/** 分支名称 */
//	private String branchName; 
	
	/** PAAS系统用户 */
	private String sysUer;

	public String getInteType() {
		return inteType;
	}

	public void setInteType(String inteType) {
		this.inteType = inteType;
	}

	public RepositoryInfo getRepositoryInfo() {
		return repositoryInfo;
	}

	public void setRepositoryInfo(RepositoryInfo repositoryInfo) {
		this.repositoryInfo = repositoryInfo;
	}

	public String getTestCmd() {
		return testCmd;
	}

	public void setTestCmd(String testCmd) {
		this.testCmd = testCmd;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public int getFallNumber() {
		return fallNumber;
	}

	public void setFallNumber(int fallNumber) {
		this.fallNumber = fallNumber;
	}

	public int getAllNumber() {
		return allNumber;
	}

	public void setAllNumber(int allNumber) {
		this.allNumber = allNumber;
	}

	public StepDetailInfo getStepDetailInfo() {
		return stepDetailInfo;
	}

	public void setStepDetailInfo(StepDetailInfo stepDetailInfo) {
		this.stepDetailInfo = stepDetailInfo;
	}

	public List<Map<String, Object>> getRelationName() {
		return relationName;
	}

	public void setRelationName(List<Map<String, Object>> relationName) {
		this.relationName = relationName;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public String getSysUer() {
		return sysUer;
	}

	public void setSysUer(String sysUer) {
		this.sysUer = sysUer;
	}

	@Override
	public String toString() {
		return "InteTestEntity [inteType=" + inteType + ", repositoryInfo="
				+ repositoryInfo + ", testCmd=" + testCmd + ", aliasName="
				+ aliasName + ", fallNumber=" + fallNumber + ", allNumber="
				+ allNumber + ", stepDetailInfo=" + stepDetailInfo
				+ ", relationName=" + relationName + ", time=" + time
				+ ", sysUer=" + sysUer + "]";
	}

}
