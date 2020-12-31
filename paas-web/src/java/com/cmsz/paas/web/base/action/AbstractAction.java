package com.cmsz.paas.web.base.action;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.common.utils.JsonUtil;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.user.entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public abstract class AbstractAction extends ActionSupport {

	private static final long serialVersionUID = 913434303332533550L;

	private static final Logger logger = LoggerFactory
			.getLogger(AbstractAction.class);

	/**
	 * 分页参数 page : 第几页 rows : 每页几行 sord : 升序还是降序 sidx : 按什么排序
	 */
	protected Integer page = 1;
	protected Integer rows = 10;
	protected String sord = null;
	protected String sidx = null;
	

	protected String resultMessage;
	protected String resultCode;
	protected String handleResult, handleOpinion;
	protected String userdata;

	/**
	 * 基本数据类型字符串数据常量定义.
	 */
	public static final String[] BASE_DATA_TYPE = new String[] { "Integer",
			"int", "Long", "long", "Boolean", "bool", "BigDecimal", "String",
			"Date", "Short", "short", "Double", "double" };

	/**
	 * 检查返回类型是否为基本数据类型.
	 * 
	 * @param returnType
	 * @return
	 */
	private boolean checkBaseDataType(String returnType) {

		for (int i = 0; i < BASE_DATA_TYPE.length; i++) {
			if (BASE_DATA_TYPE[i].equals(returnType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 利用反射机制转换entity为json格式 生成的json数据只包含BASE_DATA_TYPE中配置的简单对象类型
	 * 
	 * */
	public JSONObject entityToJsonObject(Object entity) {

		JSONObject jo = new JSONObject();
		Field[] fields = entity.getClass().getDeclaredFields();
		wrap(entity, jo, fields);
		fields = entity.getClass().getSuperclass().getDeclaredFields();
		wrap(entity, jo, fields);
		return jo;
	}

	public String findClientIp() {

		HttpServletRequest request = getRequest();
		String clientIp = request.getRemoteAddr();
		return clientIp;
	}

	public Map<String, Object> getApplicationMap() {
		return ActionContext.getContext().getApplication();
	}

	public String getBasePath() {
		return getRequest().getRealPath("/");
	}

	public User getCurrentUser() {
		return (User) getSessionMap().get(Constants.CURRENT_USER);
	}

	public String getCurrentUserName() {

		String loginName = null;
		User user = getCurrentUser();
		if (null != user) {
			loginName = user.getUserName();
		}
		return loginName;
	}

	public String getHandleOpinion() {

		return handleOpinion;
	}

	public String getHandleResult() {

		return handleResult;
	}

	@SuppressWarnings("rawtypes")
	public Page getJqGridPage(String defaultSidx) {

		page = (page == null) ? 1 : page;
		sidx = (sidx == null) ? defaultSidx : sidx;
		sord = (sord == null) ? "DESC" : sord;
		Page pageResult = new Page(rows);
		pageResult.setPageNo(page);
		pageResult.setOrder(sord);
		pageResult.setOrderBy(sidx);
		return pageResult;
	}

	/**
	 * 组装jqGrid分页信息参数
	 * 
	 * @throws JSONException
	 * */
	public JSONObject getJsonPage(Page page) throws JSONException {

		JSONObject jsonPage = new JSONObject();
		jsonPage.put("page", page.getPageNo());
		jsonPage.put("total", page.getTotalPages());
		jsonPage.put("records", page.getTotalCount());
		return jsonPage;
	}

	public Integer getPage() {

		return page;
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public String getResultCode() {

		return resultCode;
	}

	public String getResultMessage() {

		return resultMessage;
	}

	public Integer getRows() {

		return rows;
	}

	public HttpSession getSession() {
		return getRequest().getSession();
	}

	public Map<String, Object> getSessionMap() {
		return ActionContext.getContext().getSession();
	}

	public String getSidx() {

		return sidx;
	}

	public String getSord() {

		return sord;
	}

	/**
	 * 以UTF-8编码方式，把数据发送到客户端
	 * 
	 * @param text
	 */
	public void renderText(String text) {

		getResponse().setCharacterEncoding("UTF-8");
		try {
			getResponse().getWriter().write(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected String sendFailReslutl(String s) {

		resultCode = "1";
		resultMessage = s;
		getRequest().setAttribute(Constants.OPERATE_IS_ERROR, "Y");
		sendResult();
		return null;
	}
	
	protected String sendFailResult(String errCode, String errMsg) {

		resultCode = errCode;
		resultMessage = errMsg;
		getRequest().setAttribute("errCode",errCode);
		getRequest().setAttribute("errorMsg",errMsg);
		getRequest().setAttribute(Constants.OPERATE_IS_ERROR, "Y");
		sendResult();
		return null;
	}

	/*
	 * 服务端异常时给jqgir的userdata赋值
	 * add by fubl
	 * 
	 */
	protected void sendFailResult(String erroMsg) {
		getRequest().setAttribute(Constants.OPERATE_IS_ERROR, "Y");
		userdata =JsonUtil.parseObjectToJSON( "{'userdata':{'errorCode':'error','errorMsg':'" + erroMsg + "'}}");		
		renderText(userdata);
		
	}
	
	protected void sendFailResult2jqGrid(String errCode,String erroMsg) {
		getRequest().setAttribute(Constants.OPERATE_IS_ERROR, "Y");
		userdata =JsonUtil.parseObjectToJSON( "{'userdata':{'errorCode':'"+errCode+"','errorMsg':'" + erroMsg + "'}}");		
		renderText(userdata);
		
	}	
	
	protected void sendFailResult4jqGrid(String errCode,String erroMsg) {
		String errorMsg = "";
		if(erroMsg.contains("'")){ //如果字符串中包含单引号，将其转义
			errorMsg = erroMsg.replace("'", "\\'");
		}else{
			errorMsg = erroMsg;
		}
		getRequest().setAttribute(Constants.OPERATE_IS_ERROR, "Y");
		userdata =JsonUtil.parseObjectToJSON( "{'userdata':{'errorCode':'"+errCode+"','errorMsg':'" + errorMsg + "'}}");		
		renderText(userdata);
	}

	protected void sendResult() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("resultCode", resultCode);
			obj.put("resultMessage", resultMessage);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		getSession().setAttribute("resultMessage", resultMessage);
		renderText(obj.toString());
	}
	
	/***
	 * 重写方法
	 * 新增json字段data
	 * @param data
	 */
	protected void sendResult(String data) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("resultCode", resultCode);
			obj.put("resultMessage", resultMessage);
			obj.put("data", data);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		getSession().setAttribute("data", data);
		getSession().setAttribute("resultMessage", resultMessage); 
		renderText(obj.toString());
	}

	protected String sendSuccessReslult(String s) {

		resultCode = SUCCESS;
		resultMessage = s;
		sendResult();
		return null;
	}
	
	/***
	 * 重写以前方法
	 * 推送成功消息到前台
	 * 格式:{"resultCode":"200","resultMessage":"success","data":obj}
	 * @param obj 可选
	 * @return
	 */
	protected String sendSuccessReslult(Object... obj) {
		resultCode = "200";
		resultMessage = SUCCESS;
		sendResult(obj.length>0?JackJson.fromObjectToJson(obj[0]):"");
		return null;
	}
	
	
	/**
	 * 验证登录参数的时候添加的
	 * @param code
	 * @param message
	 * @return
	 */
	protected String sendAjaxReslult(String code, String message) {
		resultCode = code;
		resultMessage = message;
		sendResult();
		return null;
	}

	protected String sendSuccessReslutl() {

		resultCode = SUCCESS;
		resultMessage = "保存成功！";
		sendResult();
		return null;
	}
	
	protected String sendErrorReslutl() {

		resultCode = ERROR;
		resultMessage = "返回失败！";
		sendResult();
		return null;
	}

	public void setApplicationAttribute(String key, Object value) {
		getApplicationMap().put(key, value);
	}

	public void setHandleOpinion(String handleOpinion) {

		this.handleOpinion = handleOpinion;
	}

	public void setHandleResult(String handleResult) {

		this.handleResult = handleResult;
	}

	public void setPage(Integer page) {

		this.page = page;
	}

	public void setRequestAttribute(String key, Object value) {
		getRequest().setAttribute(key, value);
	}

	public void setResultCode(String resultCode) {

		this.resultCode = resultCode;
	}

	public void setResultMessage(String resultMessage) {

		this.resultMessage = resultMessage;
	}

	public void setRows(Integer rows) {

		this.rows = rows;
	}

	public void setSessionAttribute(String key, Object value) {
		getSessionMap().put(key, value);
	}

	public void setSidx(String sidx) {

		this.sidx = sidx;
	}

	public void setSord(String sord) {

		this.sord = sord;
	}

	private void wrap(Object entity, JSONObject jo, Field[] fields) {

		for (Field field : fields) {
			try {
				if (field.getName().equalsIgnoreCase("serialVersionUID")) {
					continue;
				}
				String getMethodName = "get"
						+ field.getName().substring(0, 1).toUpperCase()
						+ field.getName().substring(1);
				Method getMethod = entity.getClass().getMethod(getMethodName,
						new Class[] {});
				String returnType = getMethod.getReturnType().getSimpleName();
				if (checkBaseDataType(returnType)) {
					Object fieldValue = getMethod.invoke(entity,
							new Object[] {});
					//tranformDate(YYYY-MM-DD hh:mm:ss) by lilv
					if("Date".equalsIgnoreCase(returnType)){
						fieldValue = DateUtil.tranformDate(fieldValue.toString());
					}
					if (fieldValue != null) {
						jo.put(field.getName(), fieldValue);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
