package com.cmsz.paas.common.rest.client;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

/**
 * 提供REST客户端的CRUD方法
 * 
 * @author liaoxl
 *
 */
public class RestClient {

	/**
	 * 请求获取REST服务端资源(不带json报文)
	 * 
	 * @param reqUrl
	 *            REST客户端的请求URL
	 * @return 返回服务端响应信息
	 */
	public static String get(String reqUrl) {
		String reponseStr = null;
		try {
			Client client = Client.create();
			WebResource resource = client.resource(reqUrl);
			reponseStr = resource.type(MediaType.APPLICATION_JSON).get(
					String.class);
			//System.out.println(client);
		} catch (UniformInterfaceException e) {
			throw e;
		}
		return reponseStr;
	}

	/**
	 * 请求创建资源（不带json报文）
	 * 
	 * @param reqUrl
	 *            REST客户端的请求URL
	 * @return 返回服务端响应消息
	 */
	public static String post(String reqUrl) {
		String reponseStr = null;
		try {
			Client client = Client.create();
			WebResource resource = client.resource(reqUrl);
			reponseStr = resource.type(MediaType.APPLICATION_JSON).post(
					String.class);
		} catch (UniformInterfaceException e) {
			throw e;
		}
		return reponseStr;
	}

	/**
	 * 请求创建资源（带json报文）
	 * 
	 * @param reqUrl
	 *            REST客户端的请求URL
	 * @param jsonStr
	 *            请求的json格式报文
	 * @return 返回服务端响应消息
	 */
	public static String post(String reqUrl, String jsonStr) {
		String reponseStr = null;
		try {
			Client client = Client.create();
			WebResource resource = client.resource(reqUrl);
			reponseStr = resource.type(MediaType.APPLICATION_JSON).post(
					String.class, jsonStr);
		} catch (UniformInterfaceException e) {
			throw e;
		}
		return reponseStr;
	}
	
	/**
	 * 请求创建资源（带json报文）
	 * 
	 * @param reqUrl
	 *            REST客户端的请求URL
	 * @param jsonStr
	 *            请求的json格式报文
	 * @return 但请求返回的结果为" "，最后返回"\n"
	 */
	public static String ppost(String reqUrl, String jsonStr) {
		String reponseStr = null;
		try {
			Client client = Client.create();
			WebResource resource = client.resource(reqUrl);
			reponseStr = resource.type(MediaType.APPLICATION_JSON).post(
					String.class, jsonStr);
			System.out.println( reponseStr );
		} catch (UniformInterfaceException e) {
			throw e;
		}
		if ( reponseStr.equals("") ){
			return "        \n";
		} else {
			return reponseStr;
		}
	}

	/**
	 * 请求更新资源（不带json报文）
	 * 
	 * @param reqUrl
	 * @return 返回服务端响应消息
	 */
	public static String put(String reqUrl) {
		String reponseStr = null;
		try {
			Client client = Client.create();
			WebResource resource = client.resource(reqUrl);
			reponseStr = resource.type(MediaType.APPLICATION_JSON).put(
					String.class);
		} catch (UniformInterfaceException e) {
			throw e;
		}
		return reponseStr;
	}

	/**
	 * 请求更新资源（带json报文）
	 * 
	 * @param reqUrl
	 *            REST客户端的请求URL
	 * @param jsonStr
	 *            请求的json格式报文
	 * @return 返回服务端响应消息
	 */
	public static String put(String reqUrl, String jsonStr) {
		String reponseStr = null;
		try {
			Client client = Client.create();
			WebResource resource = client.resource(reqUrl);
			reponseStr = resource.type(MediaType.APPLICATION_JSON).put(
					String.class, jsonStr);
		} catch (UniformInterfaceException e) {
			throw e;
		}
		return reponseStr;
	}

	/**
	 * 请求删除资源（不带json报文）
	 * 
	 * @param reqUrl
	 * @return 返回服务端响应消息
	 */
	public static String delete(String reqUrl) {
		String reponseStr = null;
		try {
			Client client = Client.create();
			WebResource resource = client.resource(reqUrl);
			reponseStr = resource.type(MediaType.APPLICATION_JSON).delete(
					String.class);
		} catch (UniformInterfaceException e) {
			throw e;
		}
		return reponseStr;
	}

	/**
	 * 请求删除资源（带json报文）
	 * 
	 * @param reqUrl
	 *            REST客户端的请求URL
	 * @param jsonStr
	 *            请求的json格式报文
	 * @return 返回服务端响应消息
	 */
	public static String delete(String reqUrl, String jsonStr) {
		String reponseStr = null;
		try {
			Client client = Client.create();
			WebResource resource = client.resource(reqUrl);
			reponseStr = resource.type(MediaType.APPLICATION_JSON).delete(
					String.class, jsonStr);
		} catch (UniformInterfaceException e) {
			throw e;
		}
		return reponseStr;
	}

	public static void deleteWithNoResponse( String reqUrl ) throws Exception {
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("content-type", "application/json");
			connection.setRequestMethod("DELETE");
			connection.connect();
		} catch ( Exception e ) {
			throw e;
		}
	}
	
	public static boolean checkConnection( String reqUrl ){
		int code = IsConnFailure(reqUrl);
		if(code == 200){
			return true;
		}
		return false;
	}
	
	private static int IsConnFailure( String reqUrl ){
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			return connection.getResponseCode();
		} catch (Exception e) {
			// TODO: handle exception
			return 201;
		}
	}
}
