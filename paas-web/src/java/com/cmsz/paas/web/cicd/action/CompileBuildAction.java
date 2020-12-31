package com.cmsz.paas.web.cicd.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.cicd.model.CompileBuildEntity;
import com.cmsz.paas.web.cicd.service.CompileBuildService;

/**
 * 编译&构建 Action.
 * 
 * @author liaohw
 * @date 2017-8-30
 */
public class CompileBuildAction extends AbstractAction {

	private static final long serialVersionUID = -8022992595448015379L;

	private static final Logger logger = LoggerFactory
			.getLogger(CompileBuildAction.class);
	
	@Autowired
	private CompileBuildService compileBuildService;
	
	/** 流水id */
	private String flowId;
	
	/** 接收页面输入的数据 */
	private CompileBuildEntity compileBuildEntity;

	/**
	 * 查询编译&构建的配置信息
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryCompileBuild() throws Exception {
		logger.info("开始执行查询编译&构建的配置信息，流水id：" + flowId);
		try {
			CompileBuildEntity compileBuildEntity = compileBuildService.queryCompileBuild(flowId);
			sendSuccessReslult(compileBuildEntity);
			logger.info("查询编译&构建的配置信息成功！");
		} catch (PaasWebException ex) {
			logger.error("查询编译&构建的配置信息错误 ", ex);
			sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 修改编译&构建的配置信息
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void modifyCompileBuild() throws Exception {
		logger.info("开始执行修改编译&构建的配置信息，流水id：" + flowId);
		logger.info("页面提交的参数：" + compileBuildEntity.toString()+"  流水id："+flowId);// 调试完删除
		try {
			compileBuildService.modifyCompileBuild(flowId, compileBuildEntity);
			sendSuccessReslult();
			logger.info("修改编译&构建的配置信息成功！");
		} catch (PaasWebException e) {
			logger.error("修改编译&构建的配置信息错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public CompileBuildEntity getCompileBuildEntity() {
		return compileBuildEntity;
	}

	public void setCompileBuildEntity(CompileBuildEntity compileBuildEntity) {
		this.compileBuildEntity = compileBuildEntity;
	}

}
