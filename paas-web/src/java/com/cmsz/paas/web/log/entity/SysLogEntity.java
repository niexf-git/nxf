/**
 * @author li.lv
 */
package com.cmsz.paas.web.log.entity;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class SysLogEntity.
 *
 * @author li.lv
 * 2015-4-16
 */
public class SysLogEntity {
	/** 审计日志ID */
	private long id;
	/** 操作人 */
	private String operator;
	/** 客户端IP */
	private String clientIp;
	/** 操作时间 */
	private Date operateTime;
	/** 操作链接 */
	private String operatePath;
	/** 操作类型 */
	private String operateType;
	/** 操作功能 */
	private String operateFunc;
	/** 操作结果 */
	private String operateResult;
	/** 详情（集群+应用） */
	private String detail;
	/** 描述 */
	private String description;
	/** 会话id */
	private String sessionId;
	/** 输入参数 **/
	private String inputArgs;
	/** 输出参数 **/
	private String outputArgs;
	
	public String getInputArgs() {
		return inputArgs;
	}

	public void setInputArgs(String inputArgs) {
		this.inputArgs = inputArgs;
	}

	public String getOutputArgs() {
		return outputArgs;
	}

	public void setOutputArgs(String outputArgs) {
		this.outputArgs = outputArgs;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getClientIp() {
		return clientIp;
	}


	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}


	public Date getOperateTime() {
		return operateTime;
	}


	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}


	public String getOperatePath() {
		return operatePath;
	}


	public void setOperatePath(String operatePath) {
		this.operatePath = operatePath;
	}


	public String getOperateType() {
		return operateType;
	}


	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}


	public String getOperateFunc() {
		return operateFunc;
	}


	public void setOperateFunc(String operateFunc) {
		this.operateFunc = operateFunc;
	}


	public String getOperateResult() {
		return operateResult;
	}


	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}


	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public String toString() {
		return "SysLogEntity [id=" + id + ", operator=" + operator
				+ ", clientIp=" + clientIp + ", operateTime=" + operateTime
				+ ", operatePath=" + operatePath + ", operateType="
				+ operateType + ", operateFunc=" + operateFunc
				+ ", operateResult=" + operateResult + ", detail=" + detail
				+ ", description=" + description + ", sessionId=" + sessionId + "]";
	}
	
	
}
