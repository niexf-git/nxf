package com.cmsz.paas.web.cicd.model;

/**
 * 流水步骤详情实体
 * 
 * @author ccl
 * @date 2017-8-25
 */
public class StepDetailEntity {
	/** 最后一个卡片 */
	private String lastStep;
	/** 开发自动化测试实体 */
	private AutoTestEntity autoTestEntity;
	/** 开发部署扫描实体 */
	private DepScanEntity depScanEntity;
	/** 测试自动化测试实体 */
	private AutoTestEntity testAutoTestEntity;
	/** 发布生产实体 */
	private ReleaseEntity testReleaseEntity;
	/** 测试集成测试实体 */
	private InteTestEntity testInteTestEntity;
	/** 测试部署实体 */
	private TestDeployEntity testDeployEntity;
	/** 发布测试实体 */
	private ReleaseEntity releaseEntity;
	/** 编译构建实体 */
	private CompileBuildEntity compileBuildEntity;
	/** 开发集成测试实体 */
	private InteTestEntity inteTestEntity;
	/** 下载扫描实体 */
	private CodeDownloadAndCheckEntity codeDownloadAndCheckEntity;
	/** 开发性能测试实体 */
	private PerformanceTestEntity performanceTestEntity;
	/** 测试性能测试实体 */
	private PerformanceTestEntity testPerformanceTestEntity;
	
	
	
	public String getLastStep() {
		return lastStep;
	}

	public void setLastStep(String lastStep) {
		this.lastStep = lastStep;
	}

	public AutoTestEntity getAutoTestEntity() {
		return autoTestEntity;
	}

	public void setAutoTestEntity(AutoTestEntity autoTestEntity) {
		this.autoTestEntity = autoTestEntity;
	}

	public DepScanEntity getDepScanEntity() {
		return depScanEntity;
	}

	public void setDepScanEntity(DepScanEntity depScanEntity) {
		this.depScanEntity = depScanEntity;
	}



	public AutoTestEntity getTestAutoTestEntity() {
		return testAutoTestEntity;
	}

	public void setTestAutoTestEntity(AutoTestEntity testAutoTestEntity) {
		this.testAutoTestEntity = testAutoTestEntity;
	}

	public ReleaseEntity getTestReleaseEntity() {
		return testReleaseEntity;
	}

	public void setTestReleaseEntity(ReleaseEntity testReleaseEntity) {
		this.testReleaseEntity = testReleaseEntity;
	}

	public InteTestEntity getTestInteTestEntity() {
		return testInteTestEntity;
	}

	public void setTestInteTestEntity(InteTestEntity testInteTestEntity) {
		this.testInteTestEntity = testInteTestEntity;
	}

	public TestDeployEntity getTestDeployEntity() {
		return testDeployEntity;
	}

	public void setTestDeployEntity(TestDeployEntity testDeployEntity) {
		this.testDeployEntity = testDeployEntity;
	}

	public ReleaseEntity getReleaseEntity() {
		return releaseEntity;
	}

	public void setReleaseEntity(ReleaseEntity releaseEntity) {
		this.releaseEntity = releaseEntity;
	}



	public CompileBuildEntity getCompileBuildEntity() {
		return compileBuildEntity;
	}

	public void setCompileBuildEntity(CompileBuildEntity compileBuildEntity) {
		this.compileBuildEntity = compileBuildEntity;
	}

	public InteTestEntity getInteTestEntity() {
		return inteTestEntity;
	}

	public void setInteTestEntity(InteTestEntity inteTestEntity) {
		this.inteTestEntity = inteTestEntity;
	}

	public CodeDownloadAndCheckEntity getCodeDownloadAndCheckEntity() {
		return codeDownloadAndCheckEntity;
	}

	public void setCodeDownloadAndCheckEntity(
			CodeDownloadAndCheckEntity codeDownloadAndCheckEntity) {
		this.codeDownloadAndCheckEntity = codeDownloadAndCheckEntity;
	}

	public PerformanceTestEntity getPerformanceTestEntity() {
		return performanceTestEntity;
	}

	public void setPerformanceTestEntity(PerformanceTestEntity performanceTestEntity) {
		this.performanceTestEntity = performanceTestEntity;
	}

	public PerformanceTestEntity getTestPerformanceTestEntity() {
		return testPerformanceTestEntity;
	}

	public void setTestPerformanceTestEntity(
			PerformanceTestEntity testPerformanceTestEntity) {
		this.testPerformanceTestEntity = testPerformanceTestEntity;
	}

}
