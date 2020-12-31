package com.cmsz.paas.common.model.controller.response;

import com.cmsz.paas.common.model.controller.entity.AutoTest;
import com.cmsz.paas.common.model.controller.entity.DepScanEntity;
import com.cmsz.paas.common.model.controller.entity.DownloadCheckEntity;
import com.cmsz.paas.common.model.controller.entity.InteTest;
import com.cmsz.paas.common.model.controller.entity.PerformanceTest;
import com.cmsz.paas.common.model.controller.entity.PerformanceTestStatementEntity;
import com.cmsz.paas.common.model.controller.entity.QueryBuild;
import com.cmsz.paas.common.model.controller.entity.Release;
import com.cmsz.paas.common.model.controller.entity.TestAutoTest;
import com.cmsz.paas.common.model.controller.entity.TestDeploy;
import com.cmsz.paas.common.model.controller.entity.TestInteTest;
import com.cmsz.paas.common.model.controller.entity.TestPerformanceTest;
import com.cmsz.paas.common.model.controller.entity.TestRelease;

public class StepDetail {

	private DownloadCheckEntity downloadCheck;

	private QueryBuild queryBuild;

	private DepScanEntity depScan;

	private AutoTest autotest;

	private InteTest inteTest;
	
	private PerformanceTest performanceTest;

	private Release release;

	private TestDeploy testDeploy;

	private TestAutoTest testAutoTest;

	private TestInteTest testInteTest;
	
	private TestPerformanceTest testPerformanceTest;

	private TestRelease testRelease;
	
	private String lastStep;
	
	private double totalsTime;
	public DownloadCheckEntity getDownloadCheck() {
		return downloadCheck;
	}

	public void setDownloadCheck(DownloadCheckEntity downloadCheck) {
		this.downloadCheck = downloadCheck;
	}

	public QueryBuild getQueryBuild() {
		return queryBuild;
	}

	public void setQueryBuild(QueryBuild queryBuild) {
		this.queryBuild = queryBuild;
	}

	public DepScanEntity getDepScan() {
		return depScan;
	}

	public void setDepScan(DepScanEntity depScan) {
		this.depScan = depScan;
	}

	public AutoTest getAutotest() {
		return autotest;
	}

	public void setAutotest(AutoTest autotest) {
		this.autotest = autotest;
	}

	public InteTest getInteTest() {
		return inteTest;
	}

	public void setInteTest(InteTest inteTest) {
		this.inteTest = inteTest;
	}

	public Release getRelease() {
		return release;
	}

	public void setRelease(Release release) {
		this.release = release;
	}

	public TestDeploy getTestDeploy() {
		return testDeploy;
	}

	public void setTestDeploy(TestDeploy testDeploy) {
		this.testDeploy = testDeploy;
	}

	public TestAutoTest getTestAutoTest() {
		return testAutoTest;
	}

	public void setTestAutoTest(TestAutoTest testAutoTest) {
		this.testAutoTest = testAutoTest;
	}

	public TestInteTest getTestInteTest() {
		return testInteTest;
	}

	public void setTestInteTest(TestInteTest testInteTest) {
		this.testInteTest = testInteTest;
	}
	
	public TestPerformanceTest getTestPerformanceTest() {
		return testPerformanceTest;
	}

	public void setTestPerformanceTest(TestPerformanceTest testPerformanceTest) {
		this.testPerformanceTest = testPerformanceTest;
	}

	public TestRelease getTestRelease() {
		return testRelease;
	}

	public void setTestRelease(TestRelease testRelease) {
		this.testRelease = testRelease;
	}

	public String getLastStep() {
		return lastStep;
	}

	public void setLastStep(String lastStep) {
		this.lastStep = lastStep;
	}

	public PerformanceTest getPerformanceTest() {
		return performanceTest;
	}

	public void setPerformanceTest(PerformanceTest performanceTest) {
		this.performanceTest = performanceTest;
	}

	public double getTotalsTime() {
		return totalsTime;
	}

	public void setTotalsTime(double totalsTime) {
		this.totalsTime = totalsTime;
	}

	@Override
	public String toString() {
		return "StepDetail [downloadCheck=" + downloadCheck + ", queryBuild=" + queryBuild + ", depScan=" + depScan
				+ ", autotest=" + autotest + ", inteTest=" + inteTest + ", performanceTest=" + performanceTest
				+ ", release=" + release + ", testDeploy=" + testDeploy + ", testAutoTest=" + testAutoTest
				+ ", testInteTest=" + testInteTest + ", testPerformanceTest=" + testPerformanceTest + ", testRelease="
				+ testRelease + ", lastStep=" + lastStep + ", totalsTime=" + totalsTime + "]";
	}



}
