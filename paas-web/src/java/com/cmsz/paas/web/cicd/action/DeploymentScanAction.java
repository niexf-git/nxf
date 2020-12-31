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
 * 
 * @author 部署&扫描Action  
 * jiangwei
 */
public class DeploymentScanAction extends AbstractAction{

	private static final long serialVersionUID = -3636152340555929163L;
	
	private static final Logger logger = LoggerFactory
			.getLogger(DeploymentScanAction.class);

	@Autowired
	private DeploymentService deploymentService;

	/** 流水Id */
	private String flowId;
	
	/** 是否开启  0:开启 1:关闭  */
	private int isCheck;
	
	private DepScanEntity depScanEntity;
	
	
	/***
	 * 修改是否安全状态
	 * @throws Exception
	 */
	public void modifyIsCheckSecurity() throws Exception {
		logger.info("开始执行保存是否安全状态...");
		try {
			deploymentService.saveCheckSecurity(flowId, isCheck);
			logger.info("修改保存是否安全状态成功！");
			sendSuccessReslult();
		} catch (PaasWebException e) {
			logger.error("修改保存是否安全状态错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	/***
	 * 查询部署&扫描详情
	 */
	@UnLogging
	public void queryDepScanEntity(){
		logger.info("查询部署&扫描详情...");
		try {
			String url = RestUtils.restUrlTransform("queryOrSvaeDepScanEntityUrl", flowId);
			DepScanEntity entity = deploymentService.queryDepScanEntity(flowId,"depScan",url);
			//部署和部署扫描的提示内容不相同，没有手动部署
			//entity.setGrayInfo(entity.getGrayInfo().replaceAll("以及手动", ""));
			logger.info("查询部署&扫描成功！");
			sendSuccessReslult(entity);
		} catch (PaasWebException e) {
			logger.error("查询部署&扫描错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	/***
	 * 修改部署&扫描信息
	 */
	public void modifyDepScan(){
		logger.info("修改部署&扫描配置信息...");
		try {
			String url = RestUtils.restUrlTransform("queryOrSvaeDepScanEntityUrl", flowId);
			deploymentService.updateDepScan(depScanEntity,"depScan",url);
			logger.info("修改部署&扫描配置信息成功！");
			sendSuccessReslult();
		} catch (PaasWebException e) {
			logger.error("修改部署&扫描配置信息错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public int getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

	public DepScanEntity getDepScanEntity() {
		return depScanEntity;
	}

	public void setDepScanEntity(DepScanEntity depScanEntity) {
		this.depScanEntity = depScanEntity;
	}
	
	
	
	
}
