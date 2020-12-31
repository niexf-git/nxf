package com.cmsz.paas.web.user.Filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.util.Log;

import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.permission.entity.OperPermission;

public class LoginFilter implements Filter {

	private static String[] params = null;
	private final static String LOGIN_PAGE = "/login.jsp";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request_, ServletResponse response_,
			FilterChain chain) throws IOException, ServletException {
		Log.info("进入过滤器");
		// 获得在下面代码中要用的request,response,session对象
		HttpServletRequest request = (HttpServletRequest) request_;
		HttpServletResponse response = (HttpServletResponse) response_;
		HttpSession session = request.getSession();

		ModifyParametersWrapper mParametersWrapper = new ModifyParametersWrapper(
				request);
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		uri = uri.substring(contextPath.length());
		if (uri.equals("/jsp/update4APassword.jsp")||uri.equals("/jsp/reset4APassword.jsp")||uri.equals("/jsp/account.jsp")) {
			chain.doFilter(request_, response);
			return;
		}
		Object user = session.getAttribute(Constants.CURRENT_USER);
		List<OperPermission> unOperPerList = (List<OperPermission>) session
				.getAttribute("unOperPermission");

		for (String p : params) {
			// CICD迭代中不再用index.jsp，直接访问该页面时跳转到login.jsp
			if (uri.startsWith("/jsp/index.jsp")) {
				break;
			}
			// 修复包含以下前缀的的无效地址，直接访问该页面时跳转到login.jsp
			if (user == null && uri.startsWith("/cicd/index")) {
				break;
			}
			/*if(uri.startsWith("/jsp/alert/alertProgressConfirm.jsp")) {
				chain.doFilter(request, response);
				return;
			}*/
			if (uri.startsWith(p)) {
				if (uri.indexOf("websocket")==-1) {
					chain.doFilter(mParametersWrapper, response);
				}else{
					chain.doFilter(request_, response);
				}
				return;
			}
			if (user != null) {
				if (isAuth(uri, unOperPerList)) {
					if (uri.indexOf("websocket")==-1) {
						chain.doFilter(mParametersWrapper, response);
					}else{
						chain.doFilter(request_, response);
					}
					return;
				}
			}
		}

		request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);

	}

	/**
	 * 继承HttpServletRequestWrapper，创建装饰类，以达到修改HttpServletRequest参数的目的
	 */
	private class ModifyParametersWrapper extends HttpServletRequestWrapper {
		private Map<String, String[]> parameterMap; // 所有参数的Map集合

		public ModifyParametersWrapper(HttpServletRequest request) {
			super(request);
			parameterMap = request.getParameterMap();
		}

		// 重写几个HttpServletRequestWrapper中的方法
		/**
		 * 获取所有参数名
		 * 
		 * @return 返回所有参数名
		 */
		@Override
		public Enumeration<String> getParameterNames() {
			Vector<String> vector = new Vector<String>(parameterMap.keySet());
			return vector.elements();
		}

		/**
		 * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值 接收一般变量 ，如text类型
		 * 
		 * @param name
		 *            指定参数名
		 * @return 指定参数名的值
		 */
		@Override
		public String getParameter(String name) {
			String[] results = parameterMap.get(name);
			if (results == null || results.length <= 0)
				return null;
			else {
				return modify(results[0], name);
			}
		}

		/**
		 * 获取指定参数名的所有值的数组，如：checkbox的所有数据 接收数组变量 ，如checkobx类型
		 */
		@Override
		public String[] getParameterValues(String name) {
			String[] results = parameterMap.get(name);
			if (results == null || results.length <= 0)
				return null;
			else {
				int length = results.length;
				for (int i = 0; i < length; i++) {
					results[i] = modify(results[i], name);
				}
				return results;
			}
		}

		/**
		 * 自定义的一个简单修改原参数的方法，即：给原来的参数值前面添加了一个修改标志的字符串
		 * 
		 * @param string
		 *            原参数值
		 * @return 修改之后的值
		 */
		private String modify(String string, String key) {
			string = string.replaceAll("%3b", "").replaceAll("%2f", "")
					.replaceAll(";", "").replaceAll("//", "")
					.replaceAll("</iframe>", "").replaceAll("<", "&lt")
					.replaceAll(">", "&gt");

			/*
			 * // 格式 %:%25;?:%3F;.replaceAll("[", "%5B").replaceAll("]", "%5D")
			 * String _v = PropertiesUtil.getValue("jsKeyWords"); String[] keys
			 * = _v.split(";"); for (int i = 0; i < keys.length; i++) {
			 * if(string.indexOf(keys[i]) > -1) { string =
			 * string.replaceAll(keys[i], ""); } } if
			 * (string.indexOf("&lt;br")!=-1) {
			 * string=string.replaceAll("&lt;br",
			 * "<br").replaceAll("&lt;/iframe>", ""); }
			 */

			return string;
		}
	}

	private boolean isAuth(String uri, List<OperPermission> unOperPerList) {
		String[] url = uri.split("/");
		if (url.length < 3) {
			return true;
		}
		String currUrl = "/" + url[1] + "/" + url[2];
		for (OperPermission per : unOperPerList) {
			String unPerUrl = per.getUrl();
			String[] temp = unPerUrl.split("/");
			if (temp.length >= 4) {
				String tempUrl = "/" + temp[2] + "/" + temp[3];
				if (currUrl.equals(tempUrl)) {
					return false;
				}
			} else {
				String tempUrl = "/" + temp[1] + "/" + temp[2];
				if (currUrl.equals(tempUrl)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		params = filterConfig.getInitParameter("p").split(",");
	}

}
