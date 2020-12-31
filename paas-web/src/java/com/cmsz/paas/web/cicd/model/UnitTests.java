package com.cmsz.paas.web.cicd.model;

/***
 * 单元测试指标
 * @author jiangwei
 *
 */
public class UnitTests {

	/**
	 * 单元测试用例总数
	 */
	private String uniTotal;
	
	/**
	 * 单元测试成功数
	 */
	private String uniSucNumber;
	
	/**
	 * 单元测试失败数
	 */
	private String uniFaiNumber;
	
	/**
	 * 单元测试错误数
	 */
	private String uniErrNum;

	public String getUniTotal() {
		return uniTotal;
	}

	public void setUniTotal(String uniTotal) {
		this.uniTotal = uniTotal;
	}

	public String getUniSucNumber() {
		return uniSucNumber;
	}

	public void setUniSucNumber(String uniSucNumber) {
		this.uniSucNumber = uniSucNumber;
	}

	public String getUniFaiNumber() {
		return uniFaiNumber;
	}

	public void setUniFaiNumber(String uniFaiNumber) {
		this.uniFaiNumber = uniFaiNumber;
	}

	public String getUniErrNum() {
		return uniErrNum;
	}

	public void setUniErrNum(String uniErrNum) {
		this.uniErrNum = uniErrNum;
	}
	
	
}
