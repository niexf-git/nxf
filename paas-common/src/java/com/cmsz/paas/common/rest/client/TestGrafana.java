package com.cmsz.paas.common.rest.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class TestGrafana {

	public static final String GRAFANA_FILE_NAME = "D:/paas_mylin/workspace_monitor/paas-common/src/java/com/cmsz/paas/common/rest/client/lhw";
	public static final String API_KEY ="admin";
	//eyJrIjoieXFjc0U0Sm82UTVYZ2VtV3A0SkRNb2J0bk9WU2U0blMiLCJuIjoibGlhb2h3MSIsImlkIjoxfQ==
	//eyJrIjoiZEx4Smo0VXM5S1lQVURvbHJKTUFWMlpPNm5zdkMycEkiLCJuIjoibGlhb2h3IiwiaWQiOjF9
	public static final String NAME = "admin";
	
	public static void main(String[] args) throws Exception {
		String url ="";
		String json = read(GRAFANA_FILE_NAME);
		System.out.println(json);
		url ="http://192.168.123.131:3000/api/dashboards/db";
		create(NAME,API_KEY,url,json);
		
		//url ="http://192.168.59.55:3000/api/dashboards/db/production-overview";
		//delete("admin","admin",url,null);
		
		url = "http://192.168.123.131:3000/api/dashboards/db/cclts";
		query(NAME,API_KEY,url);
	}
	
	public static void create(String userName,String password ,String url, String jsonStr) {
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
		System.out.println("请求的URL（post）："+url);
		System.out.println("返回的状态码："+response.getStatus());
		System.out.println("返回的内容："+response.getEntity(String.class));
	}
	
	public static void delete(String userName,String password ,String url, Object obj){
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
		System.out.println("请求的URL（post）："+url);
		System.out.println("返回的状态码："+response.getStatus());
		System.out.println("返回的内容："+response.getEntity(String.class));
	}
	
	public static void query(String userName,String password ,String url){
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
		System.out.println("请求的URL（post）："+url);
		System.out.println("返回的状态码："+response.getStatus());
		System.out.println("返回的内容："+response.getEntity(String.class));
	}
	
	public static String read(String filePath) throws Exception {
		String line = null;
		BufferedReader br = null;
		StringBuffer buf = new StringBuffer();
		try {
			// 根据文件路径创建缓冲输入流
			br = new BufferedReader(new FileReader(filePath));
			// 循环读取文件的每一行
			while ((line = br.readLine()) != null) {
				buf.append(line);
				buf.append(System.getProperty("line.separator"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
				}
			}
		}
		return buf.toString();
	}

}
