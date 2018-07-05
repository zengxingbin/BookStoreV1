package com.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/*import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.domain.User;
import com.service.impl.UserServiceImpl;*/
import javax.servlet.http.HttpServletResponse;

import com.domain.User;
import com.execption.UserException;
import com.service.impl.UserServiceImpl;

public class AutoLoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String uri = httpRequest.getRequestURI();
		String urlPattern = uri.substring(httpRequest.getContextPath().length());
		List<String> urlPatterns = new ArrayList<String>();
		
		urlPatterns.add("/cart.jsp");
		urlPatterns.add("/index.jsp");
		urlPatterns.add("/product_info.jsp");
		urlPatterns.add("/product_list.jsp");
		
		if(urlPatterns.indexOf(urlPattern) != -1) {
			HttpSession session = httpRequest.getSession();
			Object loginStatus = session.getAttribute("loginStatus");
			User loginUser = (User) session.getAttribute("loginUser");
			if(loginStatus == null && loginUser == null) {
				// 获取cookie
				Cookie[] cookies = httpRequest.getCookies();
				if(cookies != null) {
					for (Cookie cookie : cookies) {
						if("autoLogin".equals(cookie.getName())) {
							String valuesStr = cookie.getValue();
							if(!"".equals(valuesStr)) {
								String[] values = valuesStr.split("&");
								try {
									loginUser = new UserServiceImpl().login(values[0], values[1]);
									if(!"管理员".equals(loginUser.getRole())) {
										session = httpRequest.getSession();
										session.setAttribute("loginUser", loginUser);
									}
									
								} catch (UserException e) {
									e.printStackTrace();
								}								
							}						
							break;
						}
					}
				}
			}			
		}
		
		chain.doFilter(httpRequest, httpResponse);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
