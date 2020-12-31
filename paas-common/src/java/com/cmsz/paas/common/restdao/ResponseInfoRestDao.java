package com.cmsz.paas.common.restdao;

import java.io.BufferedReader;

import com.cmsz.paas.common.model.response.ResponseInfo;

/**
 * 返回对象为ResponseInfo类型，且带有报文的REST操作
 * 
 * @author liaoxl
 *
 * @param <RESOURCE>
 *            某种资源类型(eg: Minion, Pod, App, Instance, etc)，对应某种具体的报文的类型
 */
public interface ResponseInfoRestDao {

	/**
	 * 请求创建资源(不带报文)
	 * 
	 * @param url
	 *            请求URL
	 * @param cls
	 *            ResponseInfo对象中的data对象类型
	 * @param response
	 *            返回类型
	 */
	ResponseInfo create(String url, Class<?> cls) throws Exception;

	/**
	 * 查询资源
	 * 
	 * @param url
	 *            请求URL
	 * @param cls
	 *            ResponseInfo对象中的data对象类型
	 * @param response
	 *            返回类型
	 */
	ResponseInfo get(String url, Class<?> cls) throws Exception;

	/**
	 * 更新资源
	 * 
	 * @param url
	 *            请求URL
	 * @param cls
	 *            ResponseInfo对象中的data对象类型
	 * @param response
	 *            返回类型
	 */
	ResponseInfo update(String url, Class<?> cls) throws Exception;

	/**
	 * 删除资源
	 * 
	 * @param url
	 *            请求URL
	 * @param response
	 *            返回类型
	 */
	ResponseInfo delete(String url, Class<?> cls) throws Exception;

	/**
	 * 请求创建资源(带报文)
	 * 
	 * @param url
	 *            请求URL
	 * @param reousrce
	 *            某种资源(eg: minion, pod, app, instance, etc)，对应某种具体的报文
	 * @param cls
	 *            ResponseInfo对象中的data对象类型
	 * @param response
	 *            返回类型
	 */
	ResponseInfo create(String url, Object resource, Class<?> cls)
			throws Exception;

	/**
	 * 删除资源(带报文)
	 * 
	 * @param url
	 *            请求URL
	 * @param reousrce
	 *            某种资源(eg: minion, pod, app, instance, etc)，对应某种具体的报文
	 * @param cls
	 *            ResponseInfo对象中的data对象类型
	 * @param response
	 *            返回类型
	 */
	ResponseInfo delete(String url, Object resource, Class<?> cls)
			throws Exception;

	/**
	 * 更新资源(带报文)
	 * 
	 * @param url
	 *            请求URL
	 * @param reousrce
	 *            某种资源(eg: minion, pod, app, instance, etc)，对应某种具体的报文
	 * @param cls
	 *            ResponseInfo对象中的data对象类型
	 * @param response
	 *            返回类型
	 * @throws Exception
	 */
	ResponseInfo update(String url, Object resource, Class<?> cls)
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
	BufferedReader get( String url ) throws Exception;

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
	 * 创建资源，需要权限认证（grafana）
	 * @param userName
	 * @param password
	 * @param url
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	ResponseInfo create(String userName,String password ,String url, String jsonStr) throws Exception;
	
	/**
	 * 删除资源，需要权限认证（grafana）
	 * @param userName
	 * @param password
	 * @param url
	 * @return
	 * @throws Exception
	 */
	ResponseInfo delete(String userName,String password ,String url) throws Exception;
	
	/**
	 * 查询资源，需要权限认证（grafana）
	 * @param userName
	 * @param password
	 * @param url
	 * @return
	 * @throws Exception
	 */
	ResponseInfo get(String userName,String password ,String url) throws Exception;
}
