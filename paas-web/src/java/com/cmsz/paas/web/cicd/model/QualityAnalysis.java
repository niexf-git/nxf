package com.cmsz.paas.web.cicd.model;

public class QualityAnalysis {
	
	/**
	 * 静态代码分析指标
	 */
	private StaticAnalysis staticAnalysis;
	
	/**
	 * 单元测试指标
	 */
	private UnitTests unitTests;
	
	/**
	 * 行覆盖率
	 */
	private String uniLineCovRate;

	public StaticAnalysis getStaticAnalysis() {
		return staticAnalysis;
	}

	public void setStaticAnalysis(StaticAnalysis staticAnalysis) {
		this.staticAnalysis = staticAnalysis;
	}

	public UnitTests getUnitTests() {
		return unitTests;
	}

	public void setUnitTests(UnitTests unitTests) {
		this.unitTests = unitTests;
	}

	public String getUniLineCovRate() {
		return uniLineCovRate;
	}

	public void setUniLineCovRate(String uniLineCovRate) {
		this.uniLineCovRate = uniLineCovRate;
	}
	
	

}
