package com.cmsz.paas.common.restdao.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.core.MediaType;



import com.cmsz.paas.common.constant.Constant;
import com.cmsz.paas.common.lang.Strings;
import com.cmsz.paas.common.model.controller.entity.DockerUser;
import com.cmsz.paas.common.rest.client.RestClient;
import com.cmsz.paas.common.utils.JsonUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class RestGeneralModel {
	
	protected static BufferedReader generalRest(String url, Object resource,int readTimeout,String operaType ) throws Exception{
		Client client = Client.create();
		if(readTimeout !=0){
			client.setReadTimeout(readTimeout);
		}
		WebResource webResource = client.resource(url);
		return generalRestOpera(webResource,url,JsonUtil.parseObjectToJSON(resource),
					readTimeout,operaType);
	}
	
	protected static String generalAuthRest(String user, String pswd, String url,
			String resource,String operatype,String model,int readTimeout) throws Exception{
		Client client = Client.create();
		if(readTimeout !=0){
			client.setReadTimeout(readTimeout);
		}
		client.addFilter(new HTTPBasicAuthFilter(user, pswd));
		WebResource rs = client.resource(url);

		if(model.equals(Constant.REST_MODE_XML)){
			return generaAuthXmlRest(rs,resource,operatype,model);
		}else{
			return generaAuthJsonRest(rs,resource,operatype,model);
		}
	}
	
	protected static String generalAuthRest(String user, String pswd, String url,
			String resource,String operatype,String model) throws Exception{
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(user, pswd));
		WebResource rs = client.resource(url);
		DockerUser dockerUser = new DockerUser();
		dockerUser.setPassword("Harbor12345");
		dockerUser.setUsername("admin");
		rs.header("X-Registry-Auth", dockerUser);
		if(model.equals(Constant.REST_MODE_XML)){
			return generaAuthXmlRest(rs,resource,operatype,model);
		}else{
			return generaAuthJsonRest(rs,resource,operatype,model);
		}
	}
	
	protected static String generalAuthRest(String url,Object object,String resource,
			String operatype,String model) throws Exception{
		Client client = Client.create();
		WebResource rs = client.resource(url);
		rs.header("X-Registry-Auth", object);
		if(model.equals(Constant.REST_MODE_XML)){
			return generaAuthXmlRest(rs,resource,operatype,model);
		}else{
			return generaAuthJsonRest(rs,resource,operatype,model);
		}
	}
	
	protected static ClientResponse generalAuthRest(String user, String pwd, String url,
			String resource,String operatype) throws Exception{
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(user,pwd));
		WebResource webResource = client.resource(url);
		return generaAuthRest(webResource,resource,operatype);
	}
	
    protected static Object generalRest(String url, Object resource, Class<?> response,String operaType) throws Exception{
		String jsonStr = null;
		try {
			switch (operaType) {
			case Constant.POST:
				if(resource != null){
					jsonStr = RestClient.post(url, JsonUtil.gsonParseObjToJson(resource));
				}else{
					jsonStr = RestClient.post(url);
				}
				break;
			case Constant.GET:
				jsonStr = RestClient.get(url);
				break;
			case Constant.UPDATE:
				if(resource != null){
					jsonStr = RestClient.put(url, JsonUtil.gsonParseObjToJson(resource));		
				}else{
					jsonStr = RestClient.put(url);
				}
				break;
			case Constant.DELETE:
				if(resource !=null){
					jsonStr = RestClient.delete(url,JsonUtil.gsonParseObjToJson(resource));
				}else{
					jsonStr = RestClient.delete(url);
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			throw e;
		}
		
		if (null == jsonStr) {
			throw new Exception("Execute Rest "+operaType+" Error.");
		}
		
		if(response != null){
			return JsonUtil.format(jsonStr, response);
		}else{
			return jsonStr;
		}
	}
    
    protected static Object generalRest(String url, Object resource, Class<?> response,String operaType,String user,String pwd) throws Exception{
    	Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(user, pwd));
		WebResource rs = client.resource(url);
    	String jsonStr = null;
		try {
			switch (operaType) {
			case Constant.POST:
				if(resource != null){
					jsonStr = rs.type(MediaType.APPLICATION_JSON).post(
							String.class,JsonUtil.gsonParseObjToJson(resource));
				}else{
					jsonStr = rs.type(MediaType.APPLICATION_JSON).post(
							String.class);
				}
				break;
			case Constant.GET:
				jsonStr = rs.type(MediaType.APPLICATION_JSON).get(
						String.class);
				break;
			case Constant.UPDATE:
				if(resource != null){
					jsonStr = rs.type(MediaType.APPLICATION_JSON).put(
							String.class,JsonUtil.gsonParseObjToJson(resource));		
				}else{
					jsonStr = rs.type(MediaType.APPLICATION_JSON).put(
							String.class);
				}
				break;
			case Constant.DELETE:
				if(resource !=null){
					jsonStr = rs.type(MediaType.APPLICATION_JSON).delete(
							String.class,JsonUtil.gsonParseObjToJson(resource));
				}else{
					jsonStr = rs.type(MediaType.APPLICATION_JSON).delete(
							String.class);
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			throw e;
		}
		
		if (null == jsonStr) {
			throw new Exception("Execute Rest "+operaType+" Error.");
		}
		
		return JsonUtil.format(jsonStr, response);
	}
	
	private static BufferedReader generalRestOpera(WebResource webResource,
			String url, String resource,
			int readTimeout,String operaType) throws Exception{
		
		ClientResponse response=null;
		
		BufferedReader reader=null;
		
		try {
			switch (operaType) {
			case Constant.POST:
				if(Strings.isNotBlank(resource) && !resource.equals("null")){
					response = webResource.type(MediaType.APPLICATION_JSON).post(
							ClientResponse.class,resource);
				}else{
					response = webResource.type(MediaType.APPLICATION_JSON).post(
							ClientResponse.class);
				}
				break;
			case Constant.GET:
					response = webResource.type(MediaType.APPLICATION_JSON).get(
							ClientResponse.class);
				break;
			case Constant.UPDATE:
				if(Strings.isNotBlank(resource) && !resource.equals("null")){
					 response = webResource.type(MediaType.APPLICATION_JSON).put(
								ClientResponse.class,resource);
				}else{
					response = webResource.type(MediaType.APPLICATION_JSON).put(
							ClientResponse.class);
				}
				break;
			case Constant.DELETE:
				if(Strings.isNotBlank(resource) && !resource.equals("null")){
					response = webResource.type(MediaType.APPLICATION_JSON).delete(
							ClientResponse.class,resource);
				}else{
					response = webResource.type(MediaType.APPLICATION_JSON).delete(
							ClientResponse.class);
				}
				break;
			default:
				break;
			}
			InputStream in = response.getEntity(InputStream.class);
			reader = new BufferedReader(new InputStreamReader(     
	                in,"utf-8")); 
		} catch (Exception e) {
			throw e;
		}
		return reader;
	}
	
	private static String generaAuthXmlRest(WebResource rs,String resource,String type,String model) throws Exception{
		String response=null;
		try {
			switch (type) {
			case Constant.POST:
				response = rs.type(MediaType.APPLICATION_XML).post(String.class,resource);
				break;
			case Constant.GET:
				response = rs.type(MediaType.APPLICATION_XML).get(String.class);
				break;
			case Constant.UPDATE:
				response = rs.type(MediaType.APPLICATION_XML).put(String.class,resource);
				break;
			case Constant.DELETE:
				response = rs.type(MediaType.APPLICATION_XML).delete(String.class,resource);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			throw e;
		}
		return response;
	}
	
	private static String generaAuthJsonRest(WebResource rs,String resource,String type,String model) throws Exception{
		String response=null;
		try {
			switch (type) {
			case Constant.POST:
				System.out.println(rs.getHeadHandler().toString());
				response = rs.type(MediaType.APPLICATION_JSON).post(String.class,resource);
				
				break;
			case Constant.GET:
				response = rs.type(MediaType.APPLICATION_JSON).get(String.class);
				break;
			case Constant.UPDATE:
				response = rs.type(MediaType.APPLICATION_JSON).put(String.class,resource);
				break;
			case Constant.DELETE:
				response = rs.type(MediaType.APPLICATION_JSON).delete(String.class,resource);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			throw e;
		}
		return response;
	}
	
	private static ClientResponse generaAuthRest(WebResource rs,String resource,String type) throws Exception{
		ClientResponse response=null;
		try {
			switch (type) {
			case Constant.POST:
				response = rs.type(MediaType.APPLICATION_XML).post(ClientResponse.class);
				break;
			case Constant.GET:
				response = rs.type(MediaType.APPLICATION_XML).get(ClientResponse.class);
				break;
			case Constant.UPDATE:
				response = rs.type(MediaType.APPLICATION_XML).put(ClientResponse.class,resource);
				break;
			case Constant.DELETE:
				if(Strings.isNotBlank(resource) && !resource.equals("null")){
					response = rs.type(MediaType.APPLICATION_XML).delete(ClientResponse.class,resource);
				}else{
					response = rs.type(MediaType.APPLICATION_XML).delete(ClientResponse.class);
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			throw e;
		}
		return response;
	}
	
}
