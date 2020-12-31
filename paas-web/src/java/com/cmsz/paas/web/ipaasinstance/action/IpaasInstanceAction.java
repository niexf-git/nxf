package com.cmsz.paas.web.ipaasinstance.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.appserviceinst.model.Instance;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.model.SelectType;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.ipaasinstance.model.IpaasInstance;
import com.cmsz.paas.web.ipaasinstance.service.IpaasInstanceService;
import com.cmsz.paas.web.ipaasservice.action.IpaasServiceAction;
import com.cmsz.paas.web.ipaasservice.service.IpaasServiceService;

/**
 * 基础服务实例Action.
 * 
 * @author ccl
 */
public class IpaasInstanceAction extends AbstractAction {
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(IpaasServiceAction.class);
	/** The IpaasInstanceService service. */
	@Autowired
	private IpaasInstanceService ipaasInstanceService;
	/** The application service. */
	@Autowired
	private IpaasServiceService ipaasServiceService;

	/** 基础服务ID */
	private String ipaasServiceId = "";
	private String minionIp;

	private int index;
	/** 实例id */
	private String instanceId;
	
	/** 实例信息 */
	private Instance instance;




	/**
	 * 查询基础服务实例列表. 查询条件：ipaasServiceId
	 */
	@SuppressWarnings("unchecked")
	public void queryIpaasServiceInstsById() {
		logger.info("开始执行查询基础服务实例列表");
		Page<IpaasInstance> page = this.getJqGridPage("create_time");
		try {
			List<IpaasInstance> list = ipaasInstanceService
					.queryIpaasServiceInstsById(ipaasServiceId);
			page.setResult(list);
			page.setTotalCount(list.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("查询基础服务实例完成，实例数：" + list.size());
		} catch (PaasWebException ex) {
			logger.error("查询基础服务实例错误 ", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 查询基础服务实例详情
	 * 
	 * @throws Exception
	 */
	public String queryIpaasServiceInstById() throws Exception {
		logger.info("开始执行查询实例，实例id：" + instanceId);
		List<Instance> list = ipaasServiceService.queryIpaasServiceAndInstance(minionIp).get(index).getInstances();
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
	
	/**
	 * 查询基础服务实例详情
	 * 
	 * @throws Exception
	 */
	public String queryNewIpaasServiceInstById() throws Exception {
		logger.info("开始执行查询实例，实例id：" + instanceId);
		String appIds = getSessionMap().get("appPermissionId").toString();
		String operType = getSessionMap().get("opertype").toString();
		List<Instance> list = ipaasServiceService.queryNewIpaasServiceAndInstance(minionIp, appIds, operType).get(index).getInstances();
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


	/**
	 * 标准日志初始化页面实例下拉框数据
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryIpaasServiceInstList() {
		logger.info("标准日志初始化页面实例下拉框数据开始");
		List<IpaasInstance> list = null;
		try {
			list = ipaasInstanceService
					.queryIpaasServiceInstsById(ipaasServiceId);
			logger.info("查询基础服务实例完成，实例数：" + list.size());
		} catch (PaasWebException ex) {
			logger.error("查询基础服务实例错误 ", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
		List<SelectType> selectList = new ArrayList<SelectType>();
		// SelectType selectTypeTemp = new SelectType();
		// selectTypeTemp.setValue("");
		// selectTypeTemp.setText("全部");
		// selectList.add(selectTypeTemp);
		for (int i = 0; i < list.size(); i++) {
			IpaasInstance instance = list.get(i);
			if (StringUtils.isNotBlank(instance.getInstanceId())
					&& StringUtils.isNotBlank(instance.getContainerId())
					&& StringUtils.isNotBlank(instance.getHostIP())) {
				SelectType selectType = new SelectType();
				selectType.setValue(instance.getInstanceId() + "_"
						+ instance.getHostIP());
				selectType.setText(instance.getInstanceId());
				selectList.add(selectType);
			}
		}
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray
				.fromObject(selectList);
		logger.info("标准日志初始化页面实例下拉框数据完成：" + jsonArray.toString());
		sendSuccessReslult(jsonArray.toString());
	}

	/**
	 * 诊断实例
	 * 
	 * @throws Exception
	 */
	public void diagnosisIpass() throws Exception {
		logger.info("开始执行诊断方法，服务id：" + ipaasServiceId);
		try {
			String diagnosisMsg = ipaasInstanceService
					.diagnosisIpass(ipaasServiceId);
			this.sendSuccessReslult(diagnosisMsg);
			logger.info("诊断方法完成");
		} catch (PaasWebException ex) {
			logger.error("诊断方法错误 ", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	public String getIpaasServiceId() {
		return ipaasServiceId;
	}

	public void setIpaasServiceId(String ipaasServiceId) {
		this.ipaasServiceId = ipaasServiceId;
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
}
