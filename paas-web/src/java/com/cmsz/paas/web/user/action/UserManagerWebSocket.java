package com.cmsz.paas.web.user.action;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmsz.paas.web.base.action.GetHttpSessionConfigurator;
import com.cmsz.paas.web.user.dao.UserUtils;
import com.cmsz.paas.web.user.entity.User;

@WebListener
@ServerEndpoint(value = "/websocket/userManager", configurator = GetHttpSessionConfigurator.class)
public class UserManagerWebSocket {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(UserManagerWebSocket.class);
	
	
	private HttpSession httpSession;
	
	String userLoginName;
	
	@OnMessage
	public void onMessage(String message, Session session) {
		try {
			Log.info("检测用户登录是否异地登录消息：" + message);
			String user_sessionId = httpSession.getId();
			String database_sessionId = httpSession.getId();
			User user = (User)httpSession.getAttribute("CURRENT_USER");
			while (database_sessionId.equals(user_sessionId)) {
				session.getBasicRemote().sendText("0");
				database_sessionId = UserUtils.getNewUserSessionId(user.getId());
				Thread.sleep(1500);
			}
			session.getBasicRemote().sendText("用户名："+user.getLoginName()+",已在其他地方上线,如非本人操作请修改密码！");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * On open.
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		this.httpSession = (HttpSession) config.getUserProperties().get(
				HttpSession.class.getName());
		this.userLoginName = (String) httpSession.getAttribute("loginName");
		
		System.out.println("Websocket Client connected by " + userLoginName);
	}

	/**
	 * On close.
	 */
	@OnClose
	public void onClose() {
		System.out.println("Websocket Connection closed by ");
	}
}
