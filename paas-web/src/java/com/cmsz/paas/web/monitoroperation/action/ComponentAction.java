package com.cmsz.paas.web.monitoroperation.action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.monitoroperation.model.ComponentInfo;
import com.cmsz.paas.web.monitoroperation.service.ComponentService;

/**
 * 监控运维模块Action.
 * 
 * @author ccl
 */
public class ComponentAction extends AbstractAction {
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(SysAlarmAction.class);
	/** 节点Id */
	private String minionIp;
	/** 组件名称 */
	private String name="";
	/** 操作命令 */
	private String command;

	@Autowired
	private ComponentService componentService;
	/** 错误信息. */
	private String errorMsg;

	/** 错误编码. */
	private String errorCode;
	






	/**
	 * 根据组件ID查询组件列表
	 * 
	 * @throws Exception
	 */
	public void queryComponentInfoList() throws Exception {
		Page<ComponentInfo> page = this.getJqGridPage("createTime");
		logger.info("开始执行系统监控-右侧列表-查询服务，节点Ip：" + minionIp);
		try {
			List<ComponentInfo> list = componentService
					.queryComponentList(minionIp);
			page.setResult(list);
			page.setTotalCount(list.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("监控运维-右侧列表-查询组件列表完成 ，组件数：" + list.size());
		} catch (PaasWebException ex) {
			logger.error("系统监控-右侧列表-查询组件列表错误", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 启动组件/主机
	 * 
	 * @throws Exception
	 */
	public void startComponent() throws Exception {
		logger.info("开始执行启动组件，节点id：" + minionIp + "组件名称:" + name);
		try {
			componentService.operationComponent(minionIp, name, command);
			logger.info("启动组件成功！");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]启动组件出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
			errorCode = ex.getErrorCode();
			errorMsg = ex.toString();
		}
	}

	/**
	 * 重启组件/主机
	 * 
	 * @throws Exception
	 */
	public void restartComponent() throws Exception {
		logger.info("开始执行重启组件，节点id：" + minionIp + "组件名称:" + name);
		try {
			componentService.operationComponent(minionIp, name, command);
			logger.info("重启组件成功！");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]重启组件出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
			errorCode = ex.getErrorCode();
			errorMsg = ex.toString();
		}
	}

	/**
	 * 停止组件/主机
	 * 
	 * @throws Exception
	 */
	public void stopComponent() throws Exception {
		logger.info("开始执行停止组件，节点id：" + minionIp + "组件名称:" + name);
		try {
			componentService.operationComponent(minionIp, name, command);
			logger.info("停止组件成功！");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]停止组件出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
			errorCode = ex.getErrorCode();
			errorMsg = ex.toString();
		}
	}

	/**
	 * 重装组件/主机
	 * 
	 * @throws Exception
	 */
	public void reinstallComponent() throws Exception {
		logger.info("开始执行重装组件，节点id：" + minionIp + "组件名称:" + name);
		try {
			componentService.operationComponent(minionIp, name, command);
			logger.info("重装组件成功！");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]重装组件出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
			errorCode = ex.getErrorCode();
			errorMsg = ex.toString();
		}
	}




	public String getMinionIp() {
		return minionIp;
	}

	public void setMinionIp(String minionIp) {
		this.minionIp = minionIp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}




}
