package com.cmsz.paas.web.cicd.model;

import java.util.List;

/**
 * 折线图数据实体
 * 
 * @author ccl
 * @date 2017-11-23
 */
public class LineChartEntity {

	/** 流水名称 */
	private String flowName;
	/** 构建次数集合 */
	private List<Integer> xList;
	/** 单次构建失败数量集合 */
	private List<Integer> yList;

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public List<Integer> getxList() {
		return xList;
	}

	public void setxList(List<Integer> xList) {
		this.xList = xList;
	}

	public List<Integer> getyList() {
		return yList;
	}

	public void setyList(List<Integer> yList) {
		this.yList = yList;
	}



}
