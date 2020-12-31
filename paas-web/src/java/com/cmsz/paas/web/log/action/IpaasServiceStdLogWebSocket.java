package com.cmsz.paas.web.log.action;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.web.base.action.GetHttpSessionConfigurator;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.DateUtil;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
@ServerEndpoint(value = "/websocket/IpaasServiceStdLogWebSocket",configurator=GetHttpSessionConfigurator.class)
public class IpaasServiceStdLogWebSocket {
	/** 页面Session */
	private HttpSession httpSession;
	private static final Logger logger = LoggerFactory.getLogger(IpaasServiceStdLogWebSocket.class);
	/** 记录日志行数 */
	private int count;
	/** 登录用户名 */
	private String userLoginName ;
	/** rest接口基类对象类 */
	ResponseInfoRestDaoImpl restCliet = new ResponseInfoRestDaoImpl();
	/** rest接口返回的代码，如错误码或者中间码 */
	String retCode = "";

	/** rest接口返回的代码描述 */
	String msg = "";
	@OnMessage(maxMessageSize=600000)
    public void onMessage(String message, Session session) 
    	throws Exception {
		System.out.println("接收到的字符串："+message);
		String time = "";
		String ipaasServiceId="";
		String instanceId="";
		String hostIp="";
		String lastTime = "";//上一次暂停时的时间
		String[] split = message.split("_");
		//第一次建立连接
		if(message.startsWith("start_")){
			Date date = DateUtil.toDate(split[1], "yyyy-MM-dd HH:mm:ss");
			time =Long.toString(date.getTime()/1000);
		}else{//暂停后恢复连接
			count = Integer.parseInt(split[0]);
			lastTime = split[1];
			String date = lastTime.substring(0, split[1].lastIndexOf("."));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			format.setTimeZone(TimeZone.getTimeZone( "UTC"));
			time = Long.toString(format.parse(date).getTime()/1000);
		}
		ipaasServiceId=split[2];
		instanceId=split[3];
		hostIp=split[4];
		String url=queryIpaasServiceStdLog(ipaasServiceId,instanceId,hostIp,time);
		System.out.println("时间："+time+"\r\n"+url);
		readStreamData(url,lastTime,session);
	}
	
	public String queryIpaasServiceStdLog(String ipaasServiceId,
			String instanceId, String hostIp, String since) throws Exception {
		try {
			String url = RestUtils.restUrlTransform("queryIpaasServiceStdLogUrl",
					ipaasServiceId,instanceId,hostIp,since);
			// 调用接口，获取数据
			ResponseInfo responseInfo = restCliet.get(url, com.cmsz.paas.common.model.response.StdlogList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			// 对返回码进行判断，正确的会对数据进行解析
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				com.cmsz.paas.common.model.response.StdlogList stdlogList = (com.cmsz.paas.common.model.response.StdlogList) responseInfo.getData();
				return stdlogList.getUrl();
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (PaasWebException ex) {
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.CONTROLLER_CONN_ERROR,
						ex.getMessage());
			}
		}
	}
	/**
	 * 读取日志数据
	 * @param url
	 * @param lastTime
	 * @param session
	 * @throws Exception
	 */
	public void readStreamData(String url, String lastTime, Session session) throws Exception{
		BufferedReader br = null;
		try {
			URL httpUrl = new URL(url);
			InputStream inputStream = httpUrl.openStream();
			InputStreamReader isr = new InputStreamReader(inputStream);
			br = new BufferedReader(isr);
			String line = null;
			int fixCount = Integer.parseInt(PropertiesUtil.getValue("stdlog.line"));
			
			//新启一个线程发送数据
			String intervalTime = PropertiesUtil.getValue("stdlog.time");
			SendDataTimerTask task = new SendDataTimerTask(session);
        	Timer timer = new Timer();
    		timer.schedule(task, 0, Integer.parseInt(intervalTime));
    		
			//第一次加载日志（页面没有日志输出时）
			if("".equals(lastTime)){
				while((line = br.readLine()) != null){
					count++;
					//处理每行前面的乱码
		        	if(line.length() > 8){
		        		line = line.substring(8);
		        	}
		        	task.setData(count,line);
		        	if(count % fixCount == 0){
		        		task.sendData();
		        	}
		        	
		        	if(task.isException()){
		        		timer.cancel();
		        		task.setException(false);
		        		logger.info("timer close!");
		        		break;
		        	}
				}
			}else{
				boolean isSendMsg = false;
				while((line = br.readLine()) != null){
					if(isSendMsg){
						count++;
						if(line.length() > 8){
			        		line = line.substring(8);
						}
						task.setData(count,line);
						if(count % fixCount == 0){
			        		task.sendData();
			        	}
						
						if(task.isException()){
			        		timer.cancel();
			        		task.setException(false);
			        		logger.info("timer close!");
			        		break;
			        	}
					}
					if(line.contains(lastTime)){
						isSendMsg = true;
					}
				}
			}
		} finally{
			if(br != null){
				br.close();
			}
		}
	}
	
	/**
	 * On open.
	 */
	@OnOpen
	public void onOpen (Session session, EndpointConfig config) {
		this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
        this.userLoginName =(String) httpSession.getAttribute("loginName");       
        System.out.println("Websocket Client connected by "+userLoginName);
	}

    /**
     * On close.
     */
    @OnClose
    public void onClose () {
    	System.out.println("Websocket Connection closed by "+userLoginName);
    }
}
