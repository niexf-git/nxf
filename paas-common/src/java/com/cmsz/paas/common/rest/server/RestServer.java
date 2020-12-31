package com.cmsz.paas.common.rest.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;

import com.sun.jersey.api.container.ContainerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

/**
 * 发布REST服务
 * 
 * @author liaoxl
 *
 */
public class RestServer {

	/**
	 * 启动REST服务
	 * 
	 * @param port
	 *            提供REST服务的端口，eg：9998
	 * @param resource
	 *            提供REST服务的资源包， eg：com.cmsz.common.rest.example.resource
	 * @throws Exception
	 */
	public void startServer(String ip,int port,AuthFilter auth
			,int threadPoolMax,int threadPoolMin, String... resource) throws Exception {
		URI uri = getBaseURI(ip, port);
		System.out.println("Begin to start http server...");
		ResourceConfig config = new PackagesResourceConfig(resource);

		if(auth != null){
			config.getProperties().put(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS
				, auth);
		}
		
		HttpHandler handler = (HttpHandler)ContainerFactory.createContainer(HttpHandler.class, config);
	       
		HttpServer sers= RestServer.createHttpServer(uri, handler, false, null,threadPoolMax,threadPoolMin);
		
		System.out
		.println(String
				.format("Jersey app started with WADL available at "
						+ "RestRequest.java%sapplication.wadl\nAnd the rest url is: %s\n",
						uri, uri));
		
		while (true) {
			Thread.sleep(1000);
		}
		
		// System.in.read();
		// httpServer.stop();
	}
	
	
	
	public static HttpServer createHttpServer(URI u, HttpHandler handler, boolean secure, SSLEngineConfigurator sslEngineConfigurator,int threadPoolMax,int threadPoolMin)
	        throws IOException, IllegalArgumentException, NullPointerException
	    {
	        if(u == null)
	            throw new NullPointerException("The URI must not be null");
	        String scheme = u.getScheme();
	        if(!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https"))
	            throw new IllegalArgumentException((new StringBuilder()).append("The URI scheme, of the URI ").append(u).append(", must be equal (ignoring case) to 'http' or 'https'").toString());
	        String host = u.getHost() != null ? u.getHost() : "0.0.0.0";
	        int port = u.getPort() != -1 ? u.getPort() : 80;
	        HttpServer server = new HttpServer();
	        NetworkListener listener = new NetworkListener("grizzly", host, port);
	        listener.getTransport().getWorkerThreadPoolConfig()
	        .setCorePoolSize(threadPoolMin)
	        .setMaxPoolSize(threadPoolMax)
	        .setQueueLimit(-1);
	      
	        listener.setSecure(secure);
	        if(sslEngineConfigurator != null)
	            listener.setSSLEngineConfig(sslEngineConfigurator);
	        server.addListener(listener);
	        ServerConfiguration config = server.getServerConfiguration();
	        if(handler != null)
	            config.addHttpHandler(handler, new String[] {
	                u.getPath()
	            });
	        server.start();
	        return server;
	    }


	private URI getBaseURI(String ip, int port) {
		if ( StringUtils.isBlank(ip) ) {
			try {
				InetAddress addr = InetAddress.getLocalHost();
				ip = addr.getHostAddress();
			} catch (UnknownHostException e) {
				System.err.println("UnknownHostException: " + e.getMessage());
				System.exit(-1);
			}
		}

		return UriBuilder.fromUri("http://" + ip + "/").port(port).build();
	}

	public static void main(String[] args) {
		try {
			RestServer rs = new RestServer();
			//rs.startServer("127.0.0.1", 9998, "com.cmsz.test.rest.resource");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
