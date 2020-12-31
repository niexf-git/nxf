package com.cmsz.paas.web.monitoroperation.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.base.util.UrlEscapeUtil;
import com.cmsz.paas.web.monitoroperation.model.DataCenter;
import com.cmsz.paas.web.monitoroperation.service.DataCenterService;

/**
 * @author lin.my
 * @version 创建时间：2016年12月20日 上午11:40:40
 */
public class DataCenterAction extends AbstractAction {

	private static final Logger logger = LoggerFactory
			.getLogger(DataCenterAction.class);

	@Autowired
	private DataCenterService dataCenterService;

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
	@SuppressWarnings("unchecked")
	public void queryDataCenterList() throws Exception {

		logger.info("开始执行查询数据中心");
		Page<DataCenter> page = this.getJqGridPage("id");
		try {
			List<DataCenter> list = dataCenterService.queryDataCenterList();

			page.setResult(list);
			page.setTotalCount(list.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("查询数据中心列表完成，返回条数：" + page.getTotalCount());
		} catch (PaasWebException ex) {
			logger.error("查询数据中心异常", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 新增数据中心
	 */
	public void createDataCenter() throws Exception {
		
		logger.info("新增数据中心");
		DataCenter dataCenter = new DataCenter();
		dataCenter.setName(name);
		dataCenter.setIsMainDataCenter(isMainDataCenter);
		dataCenter.setDescription(description);

		try {
			dataCenterService.createDataCenter(dataCenter);
			logger.info("新增数据中心信息：" + dataCenter.toString());
			 sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("新增数据中心异常：" + ex.getMessage(), ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}
	
	/**
	 * 删除数据中心
	 * @throws Exception
	 */
	public void deleteDataCenter() throws Exception {
		try {
			logger.info("删除数据中心，id："+id);
			dataCenterService.deleteDataCenter(id);
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("删除数据中心异常", ex);
			this.sendFailResult(ex.getKey(),ex.toString());
		}
	}
	
	/**
	 * 修改数据中心描述字段并保存
	 * @throws Exception
	 */
	public void updateDescription() throws Exception {
		try {
			logger.info("查询数据中心信息，id：" + id);
			
			// 1、根据ID查询数据中心
//			dataCenter = dataCenterService.queryDataCenterById(id);
//			logger.info("查询数据中心信息，查询结果：" + dataCenter.toString());
//			
			DataCenter dataCenter = new DataCenter();
			dataCenter.setId(id);
			
			// 2、修改数据中心描述信息
			dataCenter.setDescription(description);
			
			// 3、更新数据中心实体
			dataCenterService.updateDataCenter(dataCenter);
			logger.info("修改数据中心描述信息成功");
			
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("修改数据中心描述信息异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
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
