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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.User;

public class LoginFilter implements Filter {

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
		
		urlPatterns.add("/user");
		urlPatterns.add("/order");
		urlPatterns.add("/background");
		urlPatterns.add("/modifyuserinfo.jsp");
		
		if(urlPatterns.indexOf(urlPattern) != -1) {
			User loginUser = (User) httpRequest.getSession().getAttribute("loginUser");
			if("/user".equals(urlPattern)) {
				String action = request.getParameter("action");
				if("modifyUserInformation".equals(action)) {
					if(loginUser == null) {
						httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
						return;
					}
				}
			} else if("/background".equals(urlPattern)) {
				if(loginUser == null || !"π‹¿Ì‘±".equals(loginUser.getRole())) {
					httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
					return;
				}
			} else {
				if(loginUser == null) {
					httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
					return;
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
