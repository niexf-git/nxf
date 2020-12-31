package com.cmsz.paas.web.group4a.tool;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cmsz.paas.web.base.util.PropertiesUtil;

/**
 * @author ccl
 * @date 2019年6月25日
 */
@Component
public class WsUtils {

	/** 打印日志对象. */
	private static final Logger logger = LoggerFactory.getLogger(WsUtils.class);

	//请求webservcie接口方法
	public static String SynService(String synUrl,String method,Object[] arg) {
		Service service = new Service();
		Call call;
		try {
			call = (Call) service.createCall();
			call.setTimeout(10000);
			call.setOperationName(new QName(method));            //接口方法名
			call.setTargetEndpointAddress(new URL(synUrl));      //接口访问url路径
			String result = (String) call.invoke(arg);
			return result;
		} catch (Exception e) {
			logger.error("SynService:", e.toString());
		}
		return null;
	}
	
	 //统一凭证效验方法
    public String credentialValidation(String token, String appAcctId) {
        //请求参数
        Map<String, String> head = new HashMap<String, String>();
        head.put("CODE", "");
        head.put("SID", "");
        head.put("TIMESTAMP", new SimpleDateFormat("YYYYMMDDHHmmss").format(new Date()));
        head.put("SERVICEID", PropertiesUtil.getValue("serviceId"));
        Map<String, String> body = new HashMap<String, String>();
        body.put("APPACCTID", appAcctId);
        body.put("TOKEN", token);
        String requestInfo = packageXml(head, body, "USERREQ");
        String responseInfo = null;//响应参数
        String method = "CheckAiuapTokenSoap";//方法名
        //发送
        try {
            responseInfo = SynService(PropertiesUtil.getValue("authLoginUrl")+"/CheckAiuapTokenSoap", method, new Object[]{requestInfo});
            logger.info("credentialValidation 登录认证返回结果: " +responseInfo);
            return responseInfo;
        } catch (Exception e) {
        	logger.error("credentialValidation validation failed: " + e.getMessage());
        }
        return null;
    }


    //xml数据格式封装
    private static String packageXml(Map<String, String> head, Map<String, String> body, String rootName) {
        //准备的xml的数据格式
        JSONObject userReq = new JSONObject();
        JSONObject headReq = new JSONObject();
        for (String key : head.keySet()) {
            headReq.put(key, head.get(key));
        }
        userReq.put("HEAD", headReq);
        JSONObject bodyReq = new JSONObject();
        for (String key : body.keySet()) {
            bodyReq.put(key, body.get(key));
        }
        userReq.put("BODY", bodyReq);
        return XmlJsonUtil.jsonToXml(userReq.toString(), rootName);
    }
	
	
}
