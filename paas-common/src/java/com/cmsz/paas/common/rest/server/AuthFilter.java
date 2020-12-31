package com.cmsz.paas.common.rest.server;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.core.util.Base64;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class AuthFilter implements ContainerRequestFilter{

	private String username;
	private String password;
	
	public AuthFilter(String _username , String _password){
		username = _username;
		password = _password;
	}
	
	// Exception thrown if user is unauthorized.
	private final static WebApplicationException unauthorized = new WebApplicationException(
			Response.status(Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE,"Basic realm=\"realm\"")
					.entity("no authorized.").build());

	@Override
	public ContainerRequest filter(ContainerRequest containerRequest)
			throws WebApplicationException {
		String method = containerRequest.getMethod();
		String path = containerRequest.getPath(true);
		// 当请求application.wadl时直接往下走
		if (method.equals("GET") && path.equals("application.wadl"))
			return containerRequest;

		// Get the authentication passed in HTTP headers parameters
		String auth = containerRequest.getHeaderValue("authorization");
		// 没有提供用户名密码
		if (auth == null)
			throw unauthorized;

		auth = auth.replaceFirst("[Bb]asic ", "");
		String userColonPass = Base64.base64Decode(auth);

		if (!userColonPass.equals(username+":"+password))
			throw unauthorized; 
			//throw new EntityNotFoundException();

		return containerRequest;
	}
	
}
