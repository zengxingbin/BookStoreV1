package com.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.User;

public class UserFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String action = request.getParameter("action");
		if("modifyUserInformation".equals(action)) {
			User loginUser = (User) httpRequest.getSession().getAttribute("loginUser");
			if(loginUser == null) {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
				return;
			}
		}
		
		chain.doFilter(httpRequest, httpResponse);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
