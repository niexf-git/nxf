package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.TestEntity;

public class TestList {
	private List< TestEntity> testList;

	public List<TestEntity> getTestList() {
		return testList;
	}

	public void setTestList(List<TestEntity> testList) {
		this.testList = testList;
	}

	@Override
	public String toString() {
		return "TestEntity [testList=" + testList + "]";
	}
}
