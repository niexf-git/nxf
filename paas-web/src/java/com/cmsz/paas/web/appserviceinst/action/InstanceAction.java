package com.cmsz.paas.web.appserviceinst.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.appservice.service.ApplicationService;
import com.cmsz.paas.web.appserviceinst.model.Instance;
import com.cmsz.paas.web.appserviceinst.model.InstanceEntity;
import com.cmsz.paas.web.appserviceinst.service.InstanceService;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.base.util.RestResult;

/**
 * 实例管理Action.
 *
 * @author liaohw
 */
public class InstanceAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8442316563630282550L;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(InstanceAction.class);

	/** The instance service. */
	@Autowired
	private InstanceService instanceService;
	
	/** The application service. */
	@Autowired
	private ApplicationService appServiceService;
	
	/** 应用服务id. */
	private String appServiceId;
	
	/** 容器Id. */
	private String containerId;
	
	/** 节点IP. */
	private String hostIP;
	
	//灰度版本
	private String type;
	
	private String minionIp;
	
	private int index;
	/** 实例id */
	private String instanceId;
	
	/** 实例信息 */
	private Instance instance;
	
	/** 实例详情 */
	private InstanceEntity instanceEntity;
	
	/** 实例详情 */
	private String namespace;
	
	/** 实例详情 */
	private String podName;






	/**
	 * 查询实例列表
	 *
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public void queryInst() throws Exception {
		logger.info("开始执行查询实例，应用服务id：" + appServiceId);
		Page<Instance> page = this.getJqGridPage("createTime");
		try {
			List<Instance> list = instanceService.queryInstByAppServiceId(appServiceId);
			page.setResult(list);
			page.setTotalCount(list.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("查询实例完成，实例数："+list.size());
		} catch (PaasWebException ex) {
			logger.error("查询实例错误 ", ex);
			this.sendFailResult2jqGrid(ex.getKey(),ex.toString());
		}
	}
	
	

	/**
	 * 重启实例
	 *
	 * @throws Exception the exception
	 */
	public void restartInst() throws Exception {
		HttpServletRequest request = getRequest();
		String instanceId = request.getParameter("instanceId");
		String type = request.getParameter("type");
		logger.info("开始执行重启实例，实例id："+instanceId);

		try {
			RestResult result = instanceService.restartInst(appServiceId,instanceId,type);

			if (RestResult.SUCCESS_CODE.equals(result.getRetCode())) {
				logger.info("重启实例成功！");
				sendSuccessReslult("SUCCESS");
			} else {
				logger.info("重启实例失败 retCode:" + result.getRetCode() + ",msg:" + result.getMsg());
				this.sendFailResult(result.getRetCode(),result.getMsg());
			}
		} catch (PaasWebException ex) {
			logger.error("重启实例错误 ", ex);
			this.sendFailResult(ex.getKey(),ex.toString());
		}
	}

	/**
	 *  强制重启实例，和重启是同样的接口，为了记录审计日志才写的这个方法
	 *
	 * @throws Exception the exception
	 */
	public void forcedRestartInst() throws Exception {
		this.restartInst();
	}
	
	/**
	 *  实例状态是waitting和termination时，诊断镜像返回诊断信息
	 *
	 * @throws Exception 
	 */
	public void diagnosisAppService() throws Exception {
		logger.info("开始执行诊断方法，应用服务id：" + appServiceId);
		try {
			String diagnosisMsg = instanceService.diagnosisAppService(appServiceId,type);
			this.sendSuccessReslult(diagnosisMsg);
			logger.info("诊断方法完成");
		} catch (PaasWebException ex) {
			logger.error("诊断方法错误 ", ex);
			this.sendFailResult(ex.getKey(),ex.toString());
		}
	}
	
	/**
	 * 查询实例容器详情
	 * @throws Exception
	 */
	public void dialogContainerDetails() throws Exception {
		logger.info("开始查询容器详情，容器Id：" + containerId + ",节点IP：" + hostIP);
		try {
			String dialogContainerMsg = instanceService.dialogContainerDetails(containerId, hostIP);
			this.sendSuccessReslult(format4ContainerMsg(dialogContainerMsg));
			logger.info("查询容器详情完成");
		} catch (PaasWebException ex) {
			logger.error("查询容器详情错误 ", ex);
			this.sendFailResult(ex.getKey(),ex.toString());
		}
	}
	
	/**
	 * 格式化容器详情信息
	 * 
	 * @param str
	 * @return
	 */
	private String format4ContainerMsg(String str) {
		if (null == str || "".equals(str)){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		char current = ' ';
		int index = 0;
		for (int i = 0; i < str.length(); i++) {
			current = str.charAt(i);
			if(current=='{'||current=='['){
				sb.append(current);
				sb.append('\n');
				index++;
				addIndexBlank(sb, index);
			}else if(current=='}'||current==']'){
				sb.append('\n');
				index--;
				addIndexBlank(sb, index);
				sb.append(current);
			}else if(current==','){
				sb.append(current);
				sb.append('\n');
				addIndexBlank(sb, index);
			}else{
				sb.append(current);
			}
		}
		return sb.toString();
	}
	/**
	 * 查询某个minion节点上某个服务的实例
	 * 
	 * @throws Exception
	 */
//	public void queryAppServiceInst() throws Exception {
//		List<Instance> list = new ArrayList<Instance>();
//		Page<Instance> page = this.getJqGridPage("createTime");
//		logger.info("开始执行系统监控-右侧列表-查询实例，节点Ip：" + minionIp);
//		try {
//			if ("1".equals(type)) {
//				list = appServiceService.queryAppServiceAndInstance(minionIp).get(index).getInstances();
//			} else if ("2".equals(type)) {
//				list = appServiceService.queryAppServiceAndInstance(minionIp).get(index).getInstances();
//			}
//			page.setResult(list);
//			page.setTotalCount(list.size());
//			this.renderText(JackJson.fromObjectToJson(page));
//			logger.info("系统监控-右侧列表-查询实例完成，实例数：" + list.size());
//		} catch (PaasWebException ex) {
//			logger.error("系统监控-右侧列表-查询实例错误", ex);
//			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
//		}
//	}
	public String queryAppServiceInstById() throws Exception {
		logger.info("开始执行查询实例，实例id：" + instanceId);
		List<Instance> list = appServiceService.queryAppServiceAndInstance(minionIp)
				.get(index).getInstances();
		// 循环查找点击的实例
		for (int i = 0; i < list.size(); i++) {
			Instance inst = list.get(i);
			if (inst.getInstanceId().equals(instanceId)) {
				instance = inst;
				break;
			}
		}
		logger.info("查询实例结束，实例信息：" + instance);
		return SUCCESS;
	}
	
	public String queryNewAppServiceInstById() throws Exception {
		logger.info("开始执行查询实例，实例id：" + instanceId);
		String appIds = getSessionMap().get("appPermissionId").toString();
		String operType = getSessionMap().get("opertype").toString();
		List<Instance> list = appServiceService.queryNewAppServiceAndInstance(minionIp, appIds, operType)
				.get(index).getInstances();
		// 循环查找点击的实例
		for (int i = 0; i < list.size(); i++) {
			Instance inst = list.get(i);
			if (inst.getInstanceId().equals(instanceId)) {
				instance = inst;
				break;
			}
		}
		logger.info("查询实例结束，实例信息：" + instance);
		return SUCCESS;
	}
	
	public String queryPodDetail() throws Exception {
		logger.info("开始执行查询实例详情");
		try {
			instanceEntity = instanceService.queryPodDetail(namespace, podName);
			logger.info("查询实例详情结束");
		} catch (PaasWebException ex) {
			logger.error("查询实例详情错误 ", ex);
			this.sendFailResult2jqGrid(ex.getKey(),ex.toString());
		}
		return SUCCESS;
	}

	private void addIndexBlank(StringBuilder sb, int index) {
		for (int i = 0; i < index; i++) {
			sb.append('\t');
		}
	}

	public String getAppServiceId() {
		return appServiceId;
	}

	public void setAppServiceId(String appServiceId) {
		this.appServiceId = appServiceId;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public String getHostIP() {
		return hostIP;
	}

	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}
	public String getMinionIp() {
		return minionIp;
	}



	public void setMinionIp(String minionIp) {
		this.minionIp = minionIp;
	}



	public int getIndex() {
		return index;
	}



	public void setIndex(int index) {
		this.index = index;
	}


	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}
	public String getInstanceId() {
		return instanceId;
	}



	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}



	public Instance getInstance() {
		return instance;
	}



	public void setInstance(Instance instance) {
		this.instance = instance;
	}



	public InstanceEntity getInstanceEntity() {
		return instanceEntity;
	}



	public void setInstanceEntity(InstanceEntity instanceEntity) {
		this.instanceEntity = instanceEntity;
	}



	public String getNamespace() {
		return namespace;
	}



	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}



	public String getPodName() {
		return podName;
	}



	public void setPodName(String podName) {
		this.podName = podName;
	}
	
	
	
	

}
