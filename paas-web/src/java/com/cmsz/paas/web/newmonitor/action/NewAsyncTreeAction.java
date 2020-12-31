package com.cmsz.paas.web.newmonitor.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.newmonitor.model.NewAsyncTreeItem;
import com.cmsz.paas.web.newmonitor.service.NewAsyncTreeService;

import net.sf.json.JSONArray;


/**
 * 普通用户监控运维-左侧异步树Action.
 * 
 * @author ccl
 * @date 2018-5-7
 */
public class NewAsyncTreeAction extends AbstractAction{
	
	private static final long serialVersionUID = 7496730128567732382L;

	private static final Logger logger = LoggerFactory
			.getLogger(NewAsyncTreeAction.class);

	@Autowired
	private NewAsyncTreeService newAsyncTreeService;

	/**
	 * 左侧树异步加载请求的方法
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void loadTree() throws Exception {
		String id = getRequest().getParameter("id"); // 父节点编号
		String level = getRequest().getParameter("level");// 树层级，从0开始
		String dataCenterType = getRequest().getParameter("dataCenterType"); // 数据中心类型
		String serviceType = getRequest().getParameter("serviceType"); // 服务类型
		String path = getRequest().getContextPath();
		String appIds = getSessionMap().get("appPermissionId").toString();
		String operType = getSessionMap().get("opertype").toString();
		List<NewAsyncTreeItem> list = null;
		logger.info("开始加载监控运维-左侧树信息");
		try {
			if (level == null) {// 第一层默认节点全部
				list = newAsyncTreeService.queryAll(path);
			} else if ("0".equals(level)) {// 查询所有数据中心
				list = newAsyncTreeService.queryDataCenter(path,appIds,operType);
			} else if ("1".equals(level)) {// 根据数据中心查询集群
				list = newAsyncTreeService.queryCluster(path, id, dataCenterType,appIds,operType);
			} else if ("2".equals(level)) {// 根据集群查询主机
				list = newAsyncTreeService.queryHost(path, id, serviceType);
			} else if ("3".equals(level)) {// 根据主机查询服务以及实例
				list = newAsyncTreeService.queryService(path, id, serviceType,appIds,operType);
			} else if ("4".equals(level)) {// 暂无

			}
			// 将List对象转换为json对象
			JSONArray jsonArray = JSONArray.fromObject(list);
			renderText(jsonArray.toString());
			logger.info("监控运维-左侧树加载完成");
		} catch (PaasWebException ex) {
			logger.error("监控运维-左侧树加载失败", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

}
