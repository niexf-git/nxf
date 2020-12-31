package com.cmsz.paas.common.restdao.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.restdao.ResponseInfoRestDao;
import com.cmsz.paas.common.utils.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

//@Repository("responseInfoRestDao")
public class ResponseInfoRestDaoImpl implements ResponseInfoRestDao {
	
	public static String username;
	
	public static String password;

	@Override
	public ResponseInfo create(String url, Class<?> cls) throws Exception {
		String jsonStr = null;
		try {
			//jsonStr = RestClient.post(url);
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(username, password));
			WebResource resource = client.resource(url);
			jsonStr = resource.type(MediaType.APPLICATION_JSON).post(String.class);
		} catch (Exception e) {
			throw e;
		}
		if (null == jsonStr) {
			throw new Exception("Execute Rest Post Error.");
		}

		return json2ResponseInfoBean(jsonStr, cls);
	}

	@Override
	public ResponseInfo get(String url, Class<?> cls) throws Exception {
		String jsonStr = null;
		try {
			//jsonStr = RestClient.get(url);
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(username, password));
			WebResource resource = client.resource(url);
			jsonStr = resource.type(MediaType.APPLICATION_JSON).get(String.class);
		} catch (Exception e) {
			throw e;
		}
		if (null == jsonStr) {
			throw new Exception("Execute Rest Get Error.");
		}
		return json2ResponseInfoBean(jsonStr, cls);
	}

	@Override
	public ResponseInfo delete(String url, Class<?> cls) throws Exception {
		String jsonStr = null;
		try {
			//jsonStr = RestClient.delete(url);
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(username, password));
			WebResource resource = client.resource(url);
			jsonStr = resource.type(MediaType.APPLICATION_JSON).delete(String.class);
		} catch (Exception e) {
			throw e;
		}
		if (null == jsonStr) {
			throw new Exception("Execute Rest Delete Error.");
		}
		return json2ResponseInfoBean(jsonStr, cls);
	}

	@Override
	public ResponseInfo update(String url, Class<?> cls) throws Exception {
		String jsonStr = null;
		try {
			//jsonStr = RestClient.put(url);
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(username, password));
			WebResource resource = client.resource(url);
			jsonStr = resource.type(MediaType.APPLICATION_JSON).put(String.class);
		} catch (Exception e) {
			throw e;
		}
		if (null == jsonStr) {
			throw new Exception("Execute Rest Put Error.");
		}
		return json2ResponseInfoBean(jsonStr, cls);
	}

	@Override
	public ResponseInfo create(String url, Object res, Class<?> cls)
			throws Exception {
		String jsonStr = null;
		try {
			//jsonStr = RestClient.post(url, JsonUtil.parseObjectToJSON(res));
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(username, password));
			WebResource resource = client.resource(url);
			jsonStr = resource.type(MediaType.APPLICATION_JSON).post(
					String.class, JsonUtil.parseObjectToJSON(res));
		} catch (Exception e) {
			throw e;
		}
		if (null == jsonStr) {
			throw new Exception("Execute Rest Post Error.");
		}
		return json2ResponseInfoBean(jsonStr, cls);
	}

	@Override
	public ResponseInfo delete(String url, Object res, Class<?> cls)
			throws Exception {
		String jsonStr = null;
		try {
			//jsonStr = RestClient.delete(url,JsonUtil.parseObjectToJSON(res));
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(username, password));
			WebResource resource = client.resource(url);
			jsonStr = resource.type(MediaType.APPLICATION_JSON).delete(
					String.class, JsonUtil.parseObjectToJSON(res));
		} catch (Exception e) {
			throw e;
		}
		if (null == jsonStr) {
			throw new Exception("Execute Rest Delete Error.");
		}
		return json2ResponseInfoBean(jsonStr, cls);
	}

	@Override
	public ResponseInfo update(String url, Object res, Class<?> cls)
			throws Exception {
		String jsonStr = null;
		try {
			//jsonStr = RestClient.put(url, JsonUtil.parseObjectToJSON(res));
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(username, password));
			WebResource resource = client.resource(url);
			jsonStr = resource.type(MediaType.APPLICATION_JSON).put(
					String.class, JsonUtil.parseObjectToJSON(res));
		} catch (Exception e) {
			throw e;
		}
		if (null == jsonStr) {
			throw new Exception("Execute Rest Put Error.");
		}
		return json2ResponseInfoBean(jsonStr, cls);
	}

	private ResponseInfo json2ResponseInfoBean(String jsonStr, Class<?> cls) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		JSONObject jsonObjectData = jsonObject.getJSONObject("data");
		ResponseInfo responseInfo = (ResponseInfo) JSONObject.toBean(
				jsonObject, ResponseInfo.class);
		
//		Gson gson = new Gson();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); 
		responseInfo.setData(gson.fromJson(JsonUtil.parseTimeObjectToJson(jsonObjectData), cls));

//		Object data = JSONObject.toBean(jsonObjectData, cls);
//		responseInfo.setData(data);
		return responseInfo;
	}

	@Override
	public BufferedReader create(String url) throws Exception {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(username, password));
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(
				ClientResponse.class);
		InputStream in = response.getEntity(InputStream.class);
		BufferedReader reader = new BufferedReader(new InputStreamReader(     
                in,"utf-8")); 
        return reader;
	}

	@Override
	public BufferedReader update(String url) throws Exception {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(username, password));
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).put(
				ClientResponse.class);
		InputStream in = response.getEntity(InputStream.class);
		BufferedReader reader = new BufferedReader(new InputStreamReader(     
                in,"utf-8")); 
        return reader;
	}

	@Override
	public BufferedReader delete(String url) throws Exception {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(username, password));
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).delete(
				ClientResponse.class);
		InputStream in = response.getEntity(InputStream.class);
		BufferedReader reader = new BufferedReader(new InputStreamReader(     
                in,"utf-8")); 
        return reader;
	}

	@Override
	public BufferedReader get(String url) throws Exception {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(username, password));
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).get(
				ClientResponse.class);
		InputStream in = response.getEntity(InputStream.class);
		BufferedReader reader = new BufferedReader(new InputStreamReader(     
                in,"utf-8")); 
        return reader;
	}

	@Override
	public BufferedReader create(String url, Object resource) throws Exception {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(username, password));
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(
				ClientResponse.class,
				JsonUtil.parseObjectToJSON(resource));
		InputStream in = response.getEntity(InputStream.class);
		BufferedReader reader = new BufferedReader(new InputStreamReader(     
                in,"utf-8")); 
        return reader;
	}

	@Override
	public BufferedReader delete(String url, Object resource) throws Exception {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(username, password));
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).delete(
				ClientResponse.class,
				JsonUtil.parseObjectToJSON(resource));
		InputStream in = response.getEntity(InputStream.class);
		BufferedReader reader = new BufferedReader(new InputStreamReader(     
                in,"utf-8")); 
        return reader;
	}

	@Override
	public BufferedReader update(String url, Object resource) throws Exception {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(username, password));
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).put(
				ClientResponse.class,
				JsonUtil.parseObjectToJSON(resource));
		InputStream in = response.getEntity(InputStream.class);
		BufferedReader reader = new BufferedReader(new InputStreamReader(     
                in,"utf-8")); 
        return reader;
	}

	@Override
	public ResponseInfo create(String userName, String password, String url,
			String jsonStr) throws Exception {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(userName, password));
		WebResource resource = client.resource(url);
		ClientResponse response = null;
		try {
			response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, jsonStr);
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			throw e;
		}
		
		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setRetCode(response.getStatus()+"");
		responseInfo.setMsg(response.getEntity(String.class));
		
		return responseInfo;
	}

	@Override
	public ResponseInfo delete(String userName, String password, String url)
			throws Exception {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(userName, password));
		WebResource resource = client.resource(url);
		ClientResponse response = null;
		try {
			response = resource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			throw e;
		}
		
		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setRetCode(response.getStatus()+"");
		responseInfo.setMsg(response.getEntity(String.class));
		
		return responseInfo;
	}

	@Override
	public ResponseInfo get(String userName, String password, String url)
			throws Exception {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(userName, password));
		WebResource resource = client.resource(url);
		ClientResponse response = null;
		try {
			response = resource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			throw e;
		}
		
		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setRetCode(response.getStatus()+"");
		responseInfo.setMsg(response.getEntity(String.class));
		
		return responseInfo;
	}
	
}
