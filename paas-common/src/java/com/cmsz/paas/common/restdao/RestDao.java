package com.cmsz.paas.common.restdao;

import java.io.BufferedReader;

import com.sun.jersey.api.client.ClientResponse;

/**
 * REST DAO操作，封装REST客户端发送REST请求
 * 
 * @author liaoxl
 *
 * @param <REPONSE>
 *            操作的返回类型
 */
public interface RestDao {

	/**
	 * 请求创建资源(不带报文)
	 * 
	 * @param url
	 *            请求URL
	 * @param response
	 *            返回类型
	 */
	Object create(String url, Class<?> response) throws Exception;
	
	/**
	 * 请求资源(带hander)
	 * 
	 * @param url
	 *            请求URL
	 * @param response
	 *            返回类型
	 */
	String create(String url, Object object,String resource,String model) throws Exception;

	/**
	 * 请求创建资源（带凭证）
	 * @param user
	 * @param pswd
	 * @param url
	 * @return
	 * @throws Exception
	 */
	String create(String user, String pswd, String url, String resource) throws Exception;
	
	/**
	 * 请求创建资源（带凭证）
	 * @param user
	 * @param pswd
	 * @param url
	 * @return
	 * @throws Exception
	 */
	String create(String user, String pswd, String url, String resource,String model) throws Exception;

	/**
	 * 请求更新资源(带凭证)
	 * @param user
	 * @param pswd
	 * @param url
	 * @param resource
	 * @param model 类型（json/xml）
	 * @return
	 * @throws Exception
	 */
	String update (String user, String pswd, String url, String resource,String model) throws Exception;
	
	/**
	 * 请求更新资源(带凭证)
	 * @param user
	 * @param pswd
	 * @param url
	 * @param resource
	 * @param model 类型（json/xml）
	 * @param readTimeout
	 * @return
	 * @throws Exception
	 */
	String update(String user, String pwd, String url, String resource,
			String model, int readTimeout) throws Exception;
	
	/**
	 * 请求查询资源（带凭证）
	 * @param user
	 * @param pswd
	 * @param url
	 * @return
	 * @throws Exception
	 */
	String get(String user, String pswd, String url) throws Exception;

	/**
	 * 请求查询资源（带凭证）
	 * @param user
	 * @param pswd
	 * @param url
	 * @return
	 * @throws Exception
	 */
	ClientResponse getResponse(String user, String pswd, String url) 
			throws Exception;
	
	/**
	 * 请求删除资源（带凭证）
	 * @param user
	 * @param pswd
	 * @param url
	 * @return
	 * @throws Exception
	 */
	ClientResponse delete(String user, String pwd, String url, String resource) throws Exception;

	/**
	 * 查询资源
	 * 
	 * @param url
	 *            请求URL
	 * @param response
	 *            返回类型
	 */
	Object get(String url, Class<?> response) throws Exception;

	/**
	 * 查询资源(带凭证)
	 * 
	 * @param url
	 *            请求URL
	 * @param response
	 *            返回类型
	 */
	Object get(String url, Class<?> response,String user,String pwd) throws Exception;
	
	/**
	 * 更新资源
	 * 
	 * @param url
	 *            请求URL
	 * @param response
	 *            返回类型
	 */
	Object update(String url, Class<?> response) throws Exception;

	/**
	 * 删除资源
	 * 
	 * @param url
	 *            请求URL
	 * @param response
	 *            返回类型
	 */
	Object delete(String url, Class<?> response) throws Exception;

	/**
	 * 请求创建资源(带报文)
	 * 
	 * @param url
	 *            请求URL
	 * @param reousrce
	 *            某种资源(eg: minion, pod, app, instance, etc)
	 * @param response
	 *            返回类型
	 */
	Object create(String url, Object resource, Class<?> response)
			throws Exception;

	/**
	 * 删除资源(带报文)
	 * 
	 * @param url
	 *            请求URL
	 * @param reousrce
	 *            某种资源(eg: minion, pod, app, instance, etc)
	 * @param response
	 *            返回类型
	 */
	Object delete(String url, Object resource, Class<?> response)
			throws Exception;
	
	/**
	 * 删除资源(带凭证)
	 * 
	 * @param url
	 *            请求URL
	 * @param reousrce
	 *            某种资源(eg: minion, pod, app, instance, etc)
	 * @param response
	 *            返回类型
	 */
	Object delete(String url, Object resource, Class<?> response,String user,String pwd)
			throws Exception;

	/**
	 * 更新资源(带报文)
	 * 
	 * @param url
	 *            请求URL
	 * @param reousrce
	 *            某种资源(eg: minion, pod, app, instance, etc)
	 * @param response
	 *            返回类型
	 * @throws Exception
	 */
	Object update(String url, Object resource, Class<?> response)
			throws Exception;

	/**
	 * 创建资源（不带报文，长连接）
	 * @param url
	 * @return
	 * @throws Exception
	 */
	BufferedReader create(String url) throws Exception;

	/**
	 * 更新资源（不带报文，长连接）
	 * @param url
	 * @return
	 * @throws Exception
	 */
	BufferedReader update( String url ) throws Exception;

	/**
	 * 删除资源（不带报文，长连接）
	 * @param url
	 * @return
	 * @throws Exception
	 */
	BufferedReader delete( String url ) throws Exception;

	/**
	 * 获取资源（不带报文，长连接）
	 * @param url
	 * @return
	 * @throws Exception
	 */
	BufferedReader get( String url ,int readTimeout) throws Exception;

	/**
	 * 创建资源（带报文，长连接）
	 * @param url
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	BufferedReader create(String url, Object resource)throws Exception;
	
	/**
	 * 删除资源（带报文，长连接）
	 * @param url
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	BufferedReader delete(String url, Object resource) throws Exception;
	
	/**
	 * 更新资源（带报文，长连接）
	 * @param url
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	BufferedReader update(String url, Object resource) throws Exception;
	
	/**
	 * 检查连接是否有效
	 * @param url
	 * @return
	 * @throws Exception
	 */
	boolean checkConnection( String url );

	
}
