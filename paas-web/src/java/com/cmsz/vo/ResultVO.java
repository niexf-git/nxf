package com.cmsz.vo;

import java.io.Serializable;
/**
 * 操作结果定义
 * 
 * @author 林绵炎 
 * @version 创建时间：2016年11月18日 下午5:27:34
 */
public class ResultVO implements Serializable{

	private static final long serialVersionUID = 1769267743657268115L;

	private String result;  //操作结果：0 成功、1 失败
	
	private String resultDesc;  //操作结果描述
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getResultDesc() {
		return resultDesc;
	}
	
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

}