package com.cmsz.paas.common.model.controller.response;

import java.io.Serializable;

/**
 * @ClassName: CodeQuaKpiList.java
 * @Description: 代码质量指标实体
 *
 *
 * @author zhongmg
 * @date: 2017年11月22日 下午5:31:33
 * @version: v1.0
 */
public class CodeQuaKpi implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Analysis analysis;
	
	private UnitTests unitTests;

	public Analysis getAnalysis() {
		return analysis;
	}

	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
	}

	public UnitTests getUnitTests() {
		return unitTests;
	}

	public void setUnitTests(UnitTests unitTests) {
		this.unitTests = unitTests;
	}

}
