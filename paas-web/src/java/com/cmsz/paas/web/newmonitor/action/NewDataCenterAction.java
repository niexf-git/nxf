package com.cmsz.paas.web.newmonitor.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.newmonitor.model.NewDataCenter;
import com.cmsz.paas.web.newmonitor.service.NewDataCenterService;

/**
 * @author jiayz
 * @version 创建时间：2018年5月8日 
 */
public class NewDataCenterAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(NewDataCenterAction.class);

	@Autowired
	private NewDataCenterService dataCenterService;

	private String id; // 数据中心Id
	private String name = ""; // 数据中心名称
	private String isMainDataCenter = ""; // 是否主数据中心
//	private String ipaasHaproxy; // ipaasHaproxy地址
//	private String appHaproxy; // apaasHaproxy地址
//	private String harborUrl; // harbor地址
//	private String registryUrl; // registry地址
//	private String harborUser; // harbor用户名
//	private String harborPasswd; // 密码
//	private String harborId;
	private String description = ""; // 数据中心描述
	private String insertTime; // 创建时间

	/**
	 * 
	 * @throws Exception
	 */
	@UnLogging
	@SuppressWarnings("unchecked")
	public void queryDataCenterList() throws Exception {

		logger.info("开始执行查询数据中心");
		Page<NewDataCenter> page = this.getJqGridPage("id");
		try {
			String appIds = getSessionMap().get("appPermissionId").toString();
			String operType = getSessionMap().get("opertype").toString();
			List<NewDataCenter> list = dataCenterService.queryDataCenterList(appIds,operType);
			page.setResult(list);
			page.setTotalCount(list.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("查询数据中心列表完成，返回条数：" + page.getTotalCount());
		} catch (PaasWebException ex) {
			logger.error("查询数据中心异常", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsMainDataCenter() {
		return isMainDataCenter;
	}

	public void setIsMainDataCenter(String isMainDataCenter) {
		this.isMainDataCenter = isMainDataCenter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

}
