package com.cmsz.paas.web.build.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.model.SelectType;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.build.model.BuildEntity;
import com.cmsz.paas.web.build.model.BuildInfo;
import com.cmsz.paas.web.build.model.BuildRecordInfo;
import com.cmsz.paas.web.build.model.RepositoryInfo;
import com.cmsz.paas.web.build.service.BuildService;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.user.entity.User;
import com.cmsz.paas.web.user.service.UserManagerService;

/**
 * 构建管理Action.
 * 
 * @author liaohw
 * @date 2016-4-1
 */
public class BuildAction extends AbstractAction {

	private static final long serialVersionUID = 1568145297506594018L;

	private static final Logger logger = LoggerFactory
			.getLogger(BuildAction.class);

	/** 用户管理service对象 . */
	@Autowired
	private UserManagerService userManagerService;

	@Autowired
	private BuildService buildService;

	/** 接收页面输入的数据 */
	private BuildEntity buildEntity;

	/** 页面输入的查询字符串 */
	private String token = "";

	/** 构建id */
	private String buildId;

	/** 构建名称，用于在构建记录列表展示 */
	private String buildName;

	/** 代码库信息 */
	private RepositoryInfo repositoryInfo;

	/** 构建记录id */
	private String buildRecordId;

	/**
	 * 查询单个构建
	 * 
	 * @throws Exception
	 */
	public String queryBuild() throws Exception {
		logger.info("开始执行查询构建详情，构建ID：" + buildId);
		// 如果buildId为空，则为创建构建
		if (StringUtils.isBlank(buildId)) {
			// buildEntity = new BuildEntity();
			// 默认展示一个svn库信息
			List<RepositoryInfo> repositoryInfoList = new ArrayList<RepositoryInfo>();
			repositoryInfoList.add(new RepositoryInfo());
			// 默认展示一个执行命令
			List<String> executeCommandList = new ArrayList<String>();
			executeCommandList.add("");

			buildEntity.setRepositoryInfo(repositoryInfoList);
			buildEntity.setExecuteCommand(executeCommandList);
		} else {// 如果buildId不为空，则为修改构建
			try {
				buildEntity = buildService.queryBuild(buildId);
				logger.info("查询构建详情完成");
			} catch (PaasWebException e) {
				logger.error("[" + e.getKey() + "]查询构建详情错误", e);
			}
		}
		// 代码仓库类型 1-svn，2-git
		return Constants.SVN_REPOSITORY.equals(buildEntity.getType()) ? "svnPage"
				: "gitPage";
	}

	/**
	 * 查询构建列表
	 * 
	 * @throws Exception
	 */
	public void queryBuildList() throws Exception {
		logger.info("开始执行查询构建列表，查询字符串：" + token);
		@SuppressWarnings("unchecked")
		Page<BuildInfo> page = this.getJqGridPage("createTime");
		String appIdFiltered = ""; // 保存过滤后的应用id
		try {
			List<BuildInfo> list = new ArrayList<BuildInfo>();
			User user = (User) getSessionMap().get(Constants.CURRENT_USER);
			// 选中的应用id，多个用逗号分隔，选全部时如果有应用会把所有应用逗号分隔，没有应用时为空串
			String appIds = (String) getSessionMap().get("appPerSelectedId");
			if (StringUtils.isNotBlank(appIds)) {
				String[] appId = appIds.split(",");
				for (int i = 0; i < appId.length; i++) {
					// 查询应用对应的操作类型
					String typeIds = userManagerService.queryOperTypeById(user,
							appId[i]);
					if (StringUtils.isNotBlank(typeIds)
							&& typeIds.indexOf("1") > -1) {// 有开发操作类型
						appIdFiltered = appIdFiltered + appId[i] + ",";
					}
				}
				if (StringUtils.isNotBlank(appIdFiltered)) {
					appIdFiltered = appIdFiltered.substring(0,
							appIdFiltered.length() - 1);
					list = buildService.queryBuildList(appIdFiltered, token);
				}
			}
			page.setResult(list);
			page.setTotalCount(list.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("查询构建列表完成，构建条数：" + list.size());
		} catch (PaasWebException ex) {
			logger.error("查询构建列表错误 ", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 创建构建
	 * 
	 * @throws Exception
	 */
	public void createBuild() throws Exception {
		logger.info("开始执行创建构建...");
		try {
			String appId = (String) getSessionMap().get("appPerSelectedId");
			buildEntity.setAppId(appId);
			String loginName = (String) getSessionMap().get("loginName");
			buildEntity.setCreator(loginName);
			RestResult result = buildService.createBuild(buildEntity);
			sendSuccessReslult(result.getData());
			logger.info("创建构建成功！");
		} catch (PaasWebException e) {
			logger.error("创建构建错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	/**
	 * 修改构建
	 * 
	 * @throws Exception
	 */
	public void modifyBuild() throws Exception {
		logger.info("开始执行修改构建...");
		logger.info("页面提交的创建构建参数：" + buildEntity.toString());// 调试完删除
		try {
			buildService.modifyBuild(buildEntity);
			// 直接从前台拿到构建id用于执行构建，控制中心没有返回构建id
			sendSuccessReslult(buildEntity.getId());
			logger.info("修改构建成功！");
		} catch (PaasWebException e) {
			logger.error("修改构建错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	/**
	 * 删除构建
	 * 
	 * @throws Exception
	 */
	public void deleteBuild() throws Exception {
		logger.info("开始执行删除构建，构建ID：" + buildId);
		try {
			buildService.deleteBuild(buildId);
			sendSuccessReslult("SUCCESS");
			logger.info("删除构建成功！");
		} catch (PaasWebException ex) {
			logger.error("删除构建错误", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/**
	 * 执行构建
	 * 
	 * @throws Exception
	 */
	public void executeBuild() throws Exception {
		logger.info("开始执行构建，构建ID：" + buildId);
		try {
			String loginName = (String) getSessionMap().get("loginName");
			buildService.executeBuild(buildId, loginName);
			sendSuccessReslult("SUCCESS");
			logger.info("执行构建成功！");
		} catch (PaasWebException ex) {
			logger.error("执行构建错误", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/**
	 * 查询代码库信息，用于根据输入的url自动填充用户名密码
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void fillRepository() throws Exception {
		logger.info("开始执行查询代码库用户名密码信息，url地址：" + repositoryInfo.getUrl() + "，构建类型："
				+ repositoryInfo.getType());
		try {
			String loginName = (String) getSessionMap().get("loginName");
			RepositoryInfo repositoryInfo = buildService.queryRepository(
					this.repositoryInfo, loginName);
			// 转成json串
			String repositoryInfoJson = JackJson
					.fromObjectToJson(repositoryInfo);
			sendSuccessReslult(repositoryInfoJson);
			logger.info("查询代码库用户名密码完成");
		} catch (PaasWebException ex) {
			logger.error("查询代码库用户名密码错误 ", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 对输入的地址验证输入的代码库用户名和密码
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void verifyRepositoryCertificate() throws Exception {
		logger.info("开始执行验证代码库用户名密码...");
		try {
			String loginName = (String) getSessionMap().get("loginName");
			RepositoryInfo svn = buildService.verifyRepositoryCertificate(
					repositoryInfo, loginName);
			// 返回svn库信息的id
			sendSuccessReslult(svn.getId());
			logger.info("验证代码库用户名密码完成");
		} catch (PaasWebException ex) {
			logger.error("验证代码库用户名密码错误 ", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 查询分支名称（用于页面下拉框展示）
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryBranches() throws Exception {
		logger.info("开始执行查询分支名称...");
		try {
			List<SelectType> branches = buildService.queryBranches(repositoryInfo);
			// 转成json串
			String branchesJson = JackJson.fromObjectToJson(branches);
			sendSuccessReslult(branchesJson);
			logger.info("查询分支名称完成");
		} catch (PaasWebException ex) {
			logger.error("查询分支名称错误 ", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 查询构建记录
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryBuildRecordList() throws Exception {
		logger.info("开始执行查询构建记录列表，构建ID：" + buildId + " 查询字符串：" + token);
		@SuppressWarnings("unchecked")
		Page<BuildRecordInfo> page = this.getJqGridPage("startTime");
		try {
			List<BuildRecordInfo> list = buildService.queryBuildRecordList(
					buildId, token);
			// 构建记录列表需要显示构建名称
			for (BuildRecordInfo buildRecordInfo : list) {
				buildRecordInfo.setName(buildName);
			}
			page.setResult(list);
			page.setTotalCount(list.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("查询构建记录列表完成，构建记录条数：" + list.size());
		} catch (PaasWebException ex) {
			logger.error("查询构建记录列表错误 ", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 查询某次构建的日志（主要用于状态不是构建中）
	 * 
	 * @throws Exception
	 */
	@UnLogging
	public void queryBuildRecordLog() throws Exception {
		logger.info("开始执行查询构建日志，构建ID：" + buildId + ",构建记录ID：" + buildRecordId);
		try {
			RestResult result = buildService.queryBuildRecordLog(buildId,
					buildRecordId);
			sendSuccessReslult(result.getData());
			logger.info("查询构建日志完成");
		} catch (PaasWebException ex) {
			logger.error("查询构建日志错误 ", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	public BuildEntity getBuildEntity() {
		return buildEntity;
	}

	public void setBuildEntity(BuildEntity buildEntity) {
		this.buildEntity = buildEntity;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public RepositoryInfo getRepositoryInfo() {
		return repositoryInfo;
	}

	public void setRepositoryInfo(RepositoryInfo repositoryInfo) {
		this.repositoryInfo = repositoryInfo;
	}

	public String getBuildRecordId() {
		return buildRecordId;
	}

	public void setBuildRecordId(String buildRecordId) {
		this.buildRecordId = buildRecordId;
	}

}
