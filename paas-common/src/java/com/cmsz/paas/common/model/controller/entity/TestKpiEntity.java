package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

public class TestKpiEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private int useCaseNum;//用例数
	private int defectNum;//缺陷数
	private String flowId; //流水id
    private String flowName; //流水名 
	
	public int getUseCaseNum() {
		return useCaseNum;
	}
	public void setUseCaseNum(int useCaseNum) {
		this.useCaseNum = useCaseNum;
	}
	public int getDefectNum() {
		return defectNum;
	}
	public void setDefectNum(int defectNum) {
		this.defectNum = defectNum;
	}
    public String getFlowId()
    {
        return flowId;
    }
    public void setFlowId(String flowId)
    {
        this.flowId = flowId;
    }
    public String getFlowName()
    {
        return flowName;
    }
    public void setFlowName(String flowName)
    {
        this.flowName = flowName;
    }
    @Override
    public String toString()
    {
        return "TestKpiEntity [useCaseNum=" + useCaseNum + ", defectNum=" + defectNum
               + ", flowId=" + flowId + ", flowName=" + flowName + "]";
    }
	 
	
}
