package com.cmsz.paas.web.cicd.model;
/**
 * 单元测试报表实体（内）
 * 
 * @author ls
 * @date 2017-11-23
 */
public class UnitTestReportInfo {
	
	private String executeRecord; //执行记录
	private int useCaseNums; //用例数
	private int successNums;  //成功次数
	private int failNums;  //失败次数
	private int errorNums;  //错误次数
	private String instructCoverage;  //指令覆盖率
	private String branchCoverage;//分支覆盖率
	private String complexityCoverage; //圈复杂度覆盖率
	private String lineCoverage; //行覆盖率
	private String methodCoverage;//方法覆盖率
	private String classCoverage;//类覆盖率
	
	public String getExecuteRecord() {
		return executeRecord;
	}
	public void setExecuteRecord(String executeRecord) {
		this.executeRecord = executeRecord;
	}
	public int getUseCaseNums() {
		return useCaseNums;
	}
	public void setUseCaseNums(int useCaseNums) {
		this.useCaseNums = useCaseNums;
	}
	public int getSuccessNums() {
		return successNums;
	}
	public void setSuccessNums(int successNums) {
		this.successNums = successNums;
	}
	public int getFailNums() {
		return failNums;
	}
	public void setFailNums(int failNums) {
		this.failNums = failNums;
	}
	public int getErrorNums() {
		return errorNums;
	}
	public void setErrorNums(int errorNums) {
		this.errorNums = errorNums;
	}
	public String getInstructCoverage() {
		return instructCoverage;
	}
	public void setInstructCoverage(String instructCoverage) {
		this.instructCoverage = instructCoverage;
	}
	public String getBranchCoverage() {
		return branchCoverage;
	}
	public void setBranchCoverage(String branchCoverage) {
		this.branchCoverage = branchCoverage;
	}
	public String getComplexityCoverage() {
		return complexityCoverage;
	}
	public void setComplexityCoverage(String complexityCoverage) {
		this.complexityCoverage = complexityCoverage;
	}
	public String getLineCoverage() {
		return lineCoverage;
	}
	public void setLineCoverage(String lineCoverage) {
		this.lineCoverage = lineCoverage;
	}
	public String getMethodCoverage() {
		return methodCoverage;
	}
	public void setMethodCoverage(String methodCoverage) {
		this.methodCoverage = methodCoverage;
	}
	public String getClassCoverage() {
		return classCoverage;
	}
	public void setClassCoverage(String classCoverage) {
		this.classCoverage = classCoverage;
	}

	
	
	

}
