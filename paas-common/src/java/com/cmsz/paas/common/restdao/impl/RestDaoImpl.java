package com.cmsz.paas.common.restdao.impl;

import java.io.BufferedReader;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.constant.Constant;
import com.cmsz.paas.common.rest.client.RestClient;
import com.cmsz.paas.common.restdao.RestDao;
import com.sun.jersey.api.client.ClientResponse;

@Repository("restDao")
public class RestDaoImpl implements RestDao {

	@Override
	public Object create(String url, Class<?> response) throws Exception {
		return RestGeneralModel.generalRest(url,null,response,Constant.POST);
	}

	@Override
	public Object get(String url, Class<?> response) throws Exception {
		return RestGeneralModel.generalRest(url,null,response,Constant.GET);
	}
	
	@Override
	public Object get(String url, Class<?> response,String user,String pwd) throws Exception {
		return RestGeneralModel.generalRest(url,null,response,Constant.GET,user,pwd);
	}

	@Override
	public Object delete(String url, Class<?> response) throws Exception {
		return RestGeneralModel.generalRest(url,null,response,Constant.DELETE);
	}

	@Override
	public Object update(String url, Class<?> response) throws Exception {
		return RestGeneralModel.generalRest(url,null,response,Constant.UPDATE);
	}

	@Override
	public Object create(String url, Object resource, Class<?> response)
			throws Exception {
		return RestGeneralModel.generalRest(url,resource,response,Constant.POST);
	}

	@Override
	public Object delete(String url, Object resource, Class<?> response)
			throws Exception {
		return RestGeneralModel.generalRest(url,resource,response,Constant.DELETE);
	}
	
	@Override
	public Object delete(String url, Object resource, Class<?> response,String user,String pwd)
			throws Exception {
		return RestGeneralModel.generalRest(url,resource,response,Constant.DELETE,user,pwd);
	}

	@Override
	public Object update(String url, Object resource, Class<?> response)
			throws Exception {
		return RestGeneralModel.generalRest(url,resource,response,Constant.UPDATE);
	}

	@Override
	public BufferedReader create(String url) throws Exception {
		return RestGeneralModel.generalRest(url,null,0,Constant.POST);
	}

	@Override
	public BufferedReader update(String url) throws Exception {
		return RestGeneralModel.generalRest(url,null,0,Constant.UPDATE);
	}

	@Override
	public BufferedReader delete(String url) throws Exception {
		return RestGeneralModel.generalRest(url,null,0,Constant.DELETE); 
	}

	@Override
	public BufferedReader get(String url,int readTimeout) throws Exception {
		return RestGeneralModel.generalRest(url,null,readTimeout,Constant.GET);
	}

	@Override
	public BufferedReader create(String url, Object resource) throws Exception {
		return RestGeneralModel.generalRest(url,resource,0,Constant.POST);
	}

	@Override
	public BufferedReader delete(String url, Object resource) throws Exception {
		return RestGeneralModel.generalRest(url,resource,0,Constant.DELETE);
	}

	@Override
	public BufferedReader update(String url, Object resource) throws Exception {
		return RestGeneralModel.generalRest(url,resource,0,Constant.UPDATE);
	}

	@Override
	public String get( String user, String pswd, String url )
			throws Exception {
		return RestGeneralModel.generalAuthRest(user,pswd,url,null,Constant.GET,Constant.REST_MODE_XML);
	}

	@Override
	public String create(String user, String pwd, String url, String resource)
			throws Exception {
		return RestGeneralModel.generalAuthRest(user,pwd,url,resource,Constant.POST,Constant.REST_MODE_XML);
	}
	
	@Override
	public String create(String user, String pswd, String url, String resource,
			String model) throws Exception {
		return RestGeneralModel.generalAuthRest(user,pswd,url,resource,Constant.POST,model);
	}
	
	
	@Override
	public ClientResponse delete(String user, String pwd, String url, String resource) throws Exception {
		
		return RestGeneralModel.generalAuthRest(user,pwd,url,resource,Constant.DELETE);
	}

	@Override
	public ClientResponse getResponse(String user, String pwd, String url)
			throws Exception {
		return RestGeneralModel.generalAuthRest(user,pwd,url,null,Constant.GET);
	}

	@Override
	public String update(String user, String pwd, String url, String resource, String model) throws Exception {
		
		return RestGeneralModel.generalAuthRest(user,pwd,url,resource,Constant.UPDATE,model);
	}
	@Override
	public String update(String user, String pwd, String url, String resource, String model, int readTimeout) throws Exception {
		
		return RestGeneralModel.generalAuthRest(user,pwd,url,resource,Constant.UPDATE,model, readTimeout);
	}

	@Override
	public boolean checkConnection(String url) {
		return RestClient.checkConnection( url );
	}

	@Override
	public String create(String url, Object object,String resource,String model) throws Exception {
		return RestGeneralModel.generalAuthRest(url,object,resource,Constant.POST,model);
	}
	
	

}
