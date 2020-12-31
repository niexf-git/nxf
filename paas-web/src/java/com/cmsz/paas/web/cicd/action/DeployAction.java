package com.cmsz.paas.web.cicd.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.model.DepScanEntity;
import com.cmsz.paas.web.cicd.service.DeploymentService;

/***
 * 部署Action
 * @author jiangwei
 *
 */
public class DeployAction extends AbstractAction{

	private static final long serialVersionUID = -3347348048309394138L;
	
	private static final Logger logger = LoggerFactory
			.getLogger(DeployAction.class);
	
	@Autowired
	private DeploymentService deploymentService;
	
	/** 流水Id */
	private String flowId;
	
	private DepScanEntity depScanEntity;
	
	/***
	 * 查询部署详情
	 * 代码内容和
	 */
	@UnLogging
	public void queryDepEntity(){
		logger.info("查询部署详情...");
		try {
			String url = RestUtils.restUrlTransform("queryOrSvaeDepEntityUrl", flowId);
			DepScanEntity entity = deploymentService.queryDepScanEntity(flowId,"dep",url);
			logger.info("查询部署成功！");
			sendSuccessReslult(entity);
		} catch (PaasWebException e) {
			logger.error("查询部署错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	/***
	 * 修改部署信息
	 */
	public void modifyDep(){
		logger.info("修改部署配置信息...");
		try {
			String url = RestUtils.restUrlTransform("queryOrSvaeDepEntityUrl", flowId);
			deploymentService.updateDepScan(depScanEntity,"dep",url);
			logger.info("修改部署配置信息成功！");
			sendSuccessReslult();
		} catch (PaasWebException e) {
			logger.error("修改部署配置信息错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public DepScanEntity getDepScanEntity() {
		return depScanEntity;
	}

	public void setDepScanEntity(DepScanEntity depScanEntity) {
		this.depScanEntity = depScanEntity;
	}

	
}
