package com.cmsz.paas.common.model.controller.response;

import java.io.Serializable;

public class UnitTests implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**单元测试用例总数*/
	private Integer uniTotal;
	
	/**单元测试成功数*/
	private Integer uniSucNumber;
	
	/**单元测试失败数*/
	private Integer uniFaiNumber;
	
	/**单元测试错误数*/
	private Integer uniErrNum;
	
	/**单元测试行覆盖率*/
	private String uniLineCovRate;
	
	public String getUniLineCovRate() {
		return uniLineCovRate;
	}

	public void setUniLineCovRate(String uniLineCovRate) {
		this.uniLineCovRate = uniLineCovRate;
	}

	public Integer getUniTotal() {
		return uniTotal;
	}

	public void setUniTotal(Integer uniTotal) {
		this.uniTotal = uniTotal;
	}

	public Integer getUniSucNumber() {
		return uniSucNumber;
	}

	public void setUniSucNumber(Integer uniSucNumber) {
		this.uniSucNumber = uniSucNumber;
	}

	public Integer getUniFaiNumber() {
		return uniFaiNumber;
	}

	public void setUniFaiNumber(Integer uniFaiNumber) {
		this.uniFaiNumber = uniFaiNumber;
	}

	public Integer getUniErrNum() {
		return uniErrNum;
	}

	public void setUniErrNum(Integer uniErrNum) {
		this.uniErrNum = uniErrNum;
	}

}
