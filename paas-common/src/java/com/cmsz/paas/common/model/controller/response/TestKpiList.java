package com.cmsz.paas.common.model.controller.response;


import java.util.List;

import com.cmsz.paas.common.model.controller.entity.TestKpiEntity;

public class TestKpiList {
	private List<TestKpiEntity> testKpiList;

    public List<TestKpiEntity> getTestKpiList()
    {
        return testKpiList;
    }

    public void setTestKpiList(List<TestKpiEntity> testKpiList)
    {
        this.testKpiList = testKpiList;
    }

    @Override
    public String toString()
    {
        return "TestKpiList [testKpiList=" + testKpiList + "]";
    }

    

	
}
