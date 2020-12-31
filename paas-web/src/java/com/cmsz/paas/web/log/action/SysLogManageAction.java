package com.cmsz.paas.web.log.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.model.controller.request.AppIdList;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.common.struts2.Struts2;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.web.appservice.model.AppService;
import com.cmsz.paas.web.appservice.service.ApplicationService;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.model.SelectType;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.ipaasservice.model.IpaasService;
import com.cmsz.paas.web.ipaasservice.service.IpaasServiceService;
import com.cmsz.paas.web.log.entity.SysLogEntity;
import com.cmsz.paas.web.log.service.SysLogService;
import com.cmsz.paas.web.user.entity.User;
import com.cmsz.paas.web.user.service.UserManagerService;

/**
 * 审计日志管理类.
 * 
 * @author li.lv 2015-4-16
 */
@UnLogging
public class SysLogManageAction extends AbstractAction {

	private static final long serialVersionUID = 5283332963205308171L;
	private static final Logger logger = LoggerFactory
			.getLogger(SysLogManageAction.class);

	@Autowired
	private SysLogService sysLogService;

	@Autowired
	private UserManagerService userManagerService;

	@Autowired
	private ApplicationService appServiceService;

	@Autowired
	private IpaasServiceService ipaasServiceService;
	/** 操作结果映射集合 */
	private Map<String, String> operateResultMap = new HashMap<String, String>();
	/** 操作类型映射集合 */
	private Map<String, String> operTypeMap = new HashMap<String, String>();
	/** 操作结果 */
	private String operateResult;
	/** 操作人 */
	private String operator;
	/** 关键字 */
	private String keyWord;
	/** 起始时间 */
	private String insertTimeStart;
	/** 结束时间 */
	private String insertTimeEnd;

	/**
	 * 查询审计日志.
	 * 
	 * @throws Exception
	 */
	public void querySysLog() throws Exception {
		try {
			// 初始化查询时间
			if (null == insertTimeStart && null == insertTimeEnd) {
				Date date = new Date();
				insertTimeEnd = DateUtil.tranformDate(date.toString());
				Date preDate = com.cmsz.paas.web.base.util.DateUtil.plusDays(
						date, -3);
				insertTimeStart = DateUtil.tranformDate(preDate.toString());
			}
			logger.info("查询审计日志信息列表，起始时间：" + insertTimeStart + "，结束时间："
					+ insertTimeEnd + "操作人：" + operator + "操作结果："
					+ operateResult + "关键字：" + keyWord);
			// 对操作用户进行处理，传递过来的字符串有三种情况：空字符串、ALL、操作用户名
			if (operator != null && !"".equals(operator)) {
				String str = "";
				// 当是普通管理员选择全部时
				if ("ALL".equals(operator)) {
					User currentUser = (User) getSessionMap().get(
							Constants.CURRENT_USER);
					// 查询由某个用户创建的所有用户
					List<User> userList = userManagerService
							.queryUserByCreator(currentUser.getId());
					for (int i = 0; i < userList.size(); i++) {
						if (i == userList.size() - 1) {
							str += "'" + userList.get(i).getLoginName() + "'";
						} else {
							str += "'" + userList.get(i).getLoginName() + "',";
						}
					}
					// 加上当前登陆用户名
					if (!"".equals(str)) {
						str = "(" + str + ",'" + currentUser.getLoginName()
								+ "')";
					} else {
						str = "('" + currentUser.getLoginName() + "')";
					}
				} else {
					User cuser = (User)getSessionMap().get(Constants.CURRENT_USER);
					if("admin".equals(cuser.getLoginName())){
						str = "('" + operator + "')";
					}else{
						if(operator.equals(cuser.getLoginName())){
							str = "('" + operator + "')";
						}else{
							str = "('" + " " + "')";
						}
					}
				}
				operator = str;
			}else{
				User cuser = (User)getSessionMap().get(Constants.CURRENT_USER);
				if(!"admin".equals(cuser.getLoginName())){
					operator = "('" + operator + "')";
				}
			}

			if (StringUtils.isNotBlank(keyWord) && keyWord.contains("%")) {
				keyWord = keyWord.replaceAll("%", "");
			}
			// 应用服务map
			Map<String, String> appServiceMap = getAppServiceMap();
			// 基础服务map
			Map<String, String> ipaasServiceMap = getIpaasServiceMap();
			String ipaasIds = "";
			String appIds = "";
			com.cmsz.paas.common.page.Page<SysLogEntity> findPage = new com.cmsz.paas.common.page.Page<SysLogEntity>();
			// 模糊查询id拼接
			if (StringUtils.isNotBlank(keyWord)) {
				for (Map.Entry<String, String> entry : appServiceMap.entrySet()) {
					if (entry.getValue().toLowerCase()
							.indexOf(keyWord.toLowerCase()) != -1) {
						appIds += entry.getKey() + "|";
					}
				}
				for (Map.Entry<String, String> entry : ipaasServiceMap
						.entrySet()) {
					if (entry.getValue().toLowerCase()
							.indexOf(keyWord.toLowerCase()) != -1) {
						ipaasIds += entry.getKey() + "|";
					}
				}
				if (ipaasIds.indexOf("|") != -1) {
					ipaasIds = ipaasIds.substring(0, ipaasIds.lastIndexOf("|"));
				}
				if (appIds.indexOf("|") != -1) {
					appIds = appIds.substring(0, appIds.lastIndexOf("|"));
				}
			}
			@SuppressWarnings("unchecked")
			Page<SysLogEntity> pageData = this.getJqGridPage("operateTime");
			if (!(StringUtils.isNotBlank(keyWord)
					&& StringUtils.isBlank(appIds) && StringUtils
						.isBlank(ipaasIds))) {
				PageContext buildPageContext = Struts2.buildPageContext();
				buildPageContext.addParam("operateResult", operateResult);
				buildPageContext.addParam("operator", operator);
				buildPageContext.addParam("insertTimeStart", insertTimeStart);
				buildPageContext.addParam("insertTimeEnd", insertTimeEnd);
				buildPageContext.addParam("ipaasIds", ipaasIds);
				buildPageContext.addParam("appIds", appIds);
				findPage = sysLogService.findPage(buildPageContext);
				// id转应用,操作类型,服务名称
				for (int i = 0; i < findPage.getResult().size(); i++) {
					String detail = findPage.getResult().get(i).getDetail();
					String details = "";
					if (StringUtils.isNotBlank(detail)) {
						String[] serviceId = detail.split(",");
						if (findPage.getResult().get(i).getOperateFunc()
								.equals("服务管理")
								|| findPage.getResult().get(i).getOperateFunc()
										.equals("服务实例")) {
							for (int j = 0; j < serviceId.length; j++) {
								if (j == serviceId.length - 1) {
									details += appServiceMap.get(serviceId[j]) == null ? ""
											: appServiceMap.get(serviceId[j]);
								} else {
									details += appServiceMap.get(serviceId[j]) == null ? ""
											: appServiceMap.get(serviceId[j])
													+ "|";
								}
							}
						} else if (findPage.getResult().get(i).getOperateFunc()
								.equals("基础服务")
								|| findPage.getResult().get(i).getOperateFunc()
										.equals("基础服务实例")) {
							for (int j = 0; j < serviceId.length; j++) {
								if (j == serviceId.length - 1) {
									details += ipaasServiceMap
											.get(serviceId[j]) == null ? ""
											: ipaasServiceMap.get(serviceId[j]);
								} else {
									details += ipaasServiceMap
											.get(serviceId[j]) == null ? ""
											: ipaasServiceMap.get(serviceId[j])
													+ "|";
								}
							}
						} else {
							details = "";
						}
						findPage.getResult().get(i).setDetail(details);
					}

				}
			}

			pageData.setResult(findPage.getResult());
			pageData.setTotalCount(findPage.getTotalCount());
			JSONObject jsonPage = this.getJsonPage(pageData);
			JSONArray jsonList = new JSONArray();
			if (findPage.getResult() != null) {
				getOperateResultMap();
				for (SysLogEntity sysLogEntity : pageData.getResult()) {
					if (null != sysLogEntity.getOperateResult()) {
						sysLogEntity.setOperateResult(operateResultMap
								.get(sysLogEntity.getOperateResult()));
					}
					JSONObject jo = this.entityToJsonObject(sysLogEntity);
					jsonList.put(jo);
				}
			}
			jsonPage.put("result", jsonList);
			this.renderText(jsonPage.toString());
			logger.info("分页查询审计日志信息成功，查询结果：" + jsonList);
		} catch (PaasWebException ex) {
			logger.error("查询审计日志信息列表异常", ex);
			// this.sendFailResult(ex.getKey(),ex.toString());
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 获取用户名列表，生成下拉框数据
	 */
	public void queryUserList() {
		try {
			logger.info("审计日志初始化页面查询操作用户列表开始");
			List<SelectType> selectList = new ArrayList<SelectType>();
			int roleType = Integer.parseInt(getSessionMap().get("roleType")
					.toString());
			// 0:超级管理员，1普通管理员，2普通用户
			if (roleType == 0) {
				List<User> userList = userManagerService.getUserList();
				// 添加全部选项
				SelectType selectTypeTemp = new SelectType();
				selectTypeTemp.setValue("");
				selectTypeTemp.setText("全部");
				selectList.add(selectTypeTemp);
				// 添加所有用户
				for (User user : userList) {
					SelectType selectType = new SelectType();
					selectType.setValue(user.getLoginName());
					selectType.setText(user.getLoginName());
					selectList.add(selectType);
				}
			} else if (roleType == 1) {
				User currentUser = (User) getSessionMap().get(
						Constants.CURRENT_USER);
				// 查询由某个用户创建的所有用户
				List<User> userList = userManagerService
						.queryUserByCreator(currentUser.getId());
				// 添加全部选项
				SelectType selectTypeTemp = new SelectType();
				selectTypeTemp.setValue("ALL");
				selectTypeTemp.setText("全部");
				selectList.add(selectTypeTemp);
				// 添加当前登陆用户
				String loginName = (String) getSessionMap().get("loginName");
				SelectType selectTypeSelf = new SelectType();
				selectTypeSelf.setValue(loginName);
				selectTypeSelf.setText(loginName);
				selectList.add(selectTypeSelf);
				// 添加由当前用户创建的用户
				for (User user : userList) {
					SelectType selectType = new SelectType();
					selectType.setValue(user.getLoginName());
					selectType.setText(user.getLoginName());
					selectList.add(selectType);
				}
			} else if (roleType == 2) {
				// 添加当前登陆用户
				String loginName = (String) getSessionMap().get("loginName");
				SelectType selectTypeSelf = new SelectType();
				selectTypeSelf.setValue(loginName);
				selectTypeSelf.setText(loginName);
				selectList.add(selectTypeSelf);
			} else {
			}

			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray
					.fromObject(selectList);
			logger.info("审计日志初始化页面操作用户下拉框数据初始化完成：" + jsonArray.toString());
			sendSuccessReslult(jsonArray.toString());
		} catch (PaasWebException ex) {
			logger.error("审计日志初始化页面查询操作用户列表异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	public Map<String, String> getAppServiceMap() {
		Map<String, String> map = new HashMap<String, String>();
		List<AppService> appServieList = new ArrayList<AppService>();
		AppIdList appSrvList = new AppIdList();
		try {
			appServieList = appServiceService.queryAppServiceList(appSrvList,
					"");
			getOperTypeMap();
			for (int i = 0; i < appServieList.size(); i++) {
				map.put(appServieList.get(i).getId().toString(),
						(appServieList.get(i).getApp_name()
								+ ","
								+ operTypeMap.get(appServieList.get(i)
										.getOper_type()) + "," + appServieList
								.get(i).getName()));
			}
		} catch (Exception e) {

		}
		return map;
	}

	public Map<String, String> getIpaasServiceMap() {
		Map<String, String> map = new HashMap<String, String>();
		List<IpaasService> ipaasServieList = new ArrayList<IpaasService>();
		AppIdList serviceList = new AppIdList();
		try {
			ipaasServieList = ipaasServiceService.queryIpaasServiceList(
					serviceList, "", 0);
			getOperTypeMap();
			for (int i = 0; i < ipaasServieList.size(); i++) {
				map.put(ipaasServieList.get(i).getId().toString(),
						(ipaasServieList.get(i).getApp_name()
								+ ","
								+ operTypeMap.get(ipaasServieList.get(i)
										.getOper_type()) + "," + ipaasServieList
								.get(i).getName()));
			}
		} catch (Exception e) {
		}
		return map;
	}

	public void setOperTypeMap(Map<String, String> operTypeMap) {
		this.operTypeMap = operTypeMap;
	}

	public Map<String, String> getOperTypeMap() {
		operTypeMap.put("1", "开发");
		operTypeMap.put("2", "测试");
		operTypeMap.put("3", "运维");
		return operTypeMap;
	}

	public Map<String, String> getOperateResultMap() {
		operateResultMap.put("0", "失败");
		operateResultMap.put("1", "成功");
		return operateResultMap;
	}

	public void setOperateResultMap(Map<String, String> operateResultMap) {
		this.operateResultMap = operateResultMap;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getInsertTimeStart() {
		return insertTimeStart;
	}

	public void setInsertTimeStart(String insertTimeStart) {
		this.insertTimeStart = insertTimeStart;
	}

	public String getInsertTimeEnd() {
		return insertTimeEnd;
	}

	public void setInsertTimeEnd(String insertTimeEnd) {
		this.insertTimeEnd = insertTimeEnd;
	}

}
