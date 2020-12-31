package com.cmsz.paas.web.group4a.tool;


import javax.swing.text.Document;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

/**
* @author 
* @version 
* 说明:
*/
public class XmlJsonUtil {

	 /**
     * 将xml字符串转换为JSON字符串
     * 
     * @param xmlString
     * @return JSON对象
     */
    public static String xmlToJson(String xmlString) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON json = xmlSerializer.read(xmlString);
        return json.toString();
    }

    /**
     * 将xmlDocument转换为JSON对象
     * 
     * @param xmlDocument
     * @return JSON对象
     */
    public static String xmlToJson(Document xmlDocument) {
        return xmlToJson(xmlDocument.toString());
    }

    /**
     * 将xml字符串转换为JSON对象
     * @param xmlString
     * @return Json对象
     */
    public static JSONObject xmlToJsonObj(String xmlString) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON json = xmlSerializer.read(xmlString);           
        JSONObject jsonObj = (JSONObject)json;      
    	return jsonObj; 
    }
    
    /**
     * JSON(数组)字符串转换成XML字符串
     * @param jsonString
     * @return
     */
    public static String jsonToXml(String jsonString,String rootName) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setRootName(rootName);
        xmlSerializer.setTypeHintsEnabled(false);
        return xmlSerializer.write(JSONSerializer.toJSON(jsonString));
        // return xmlSerializer.write(JSONArray.fromObject(jsonString));//这种方式只支持JSON数组
    }

    public static void main(String[] args) {
    	String Param = "<USERREQ><HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP><SERVICEID>应用标识</SERVICEID></HEAD><BODY><KEY>关键标志</KEY><ERRCODE>错误代码</ERRCODE><ERRDESC>错误描述</ERRDESC></BODY></USERREQ>";
    	//<USERREQ><HEAD><CODE>消息标志</CODE><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP><SERVICEID>应用标识</SERVICEID></HEAD><BODY><KEY>关键标志</KEY><ERRCODE>错误代码</ERRCODE><ERRDESC>错误描述</ERRDESC></BODY></USERREQ>
    	//{"HEAD":{"CODE":"消息标志","SID":"消息序列号","TIMESTAMP":"时间戳","SERVICEID":"应用标识"},"BODY":{"KEY":"关键标志","ERRCODE":"错误代码","ERRDESC":"错误描述"}}
    	//<USERREQ><BODY><ERRCODE>错误代码</ERRCODE><ERRDESC>错误描述</ERRDESC><KEY>关键标志</KEY></BODY><HEAD><CODE>消息标志</CODE><SERVICEID>应用标识</SERVICEID><SID>消息序列号</SID><TIMESTAMP>时间戳</TIMESTAMP></HEAD></USERREQ>
    	JSONObject str = xmlToJsonObj("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+Param);
    	System.out.println(str.toString());
    	
    	String xml = jsonToXml(str.toString(),"USERREQ");
    	System.out.println(xml);
    	
    	/*String s = EncryptInterface.desEncryptData("admin@201903!#");
    	System.out.println("aa   " +s);
    	String s1 = EncryptInterface.desUnEncryptData(s);
    	System.out.println("aa   " +s1);*/
    	
    	
    	
    	
	}
}
