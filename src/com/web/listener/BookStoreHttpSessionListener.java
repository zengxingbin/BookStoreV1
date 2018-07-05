package com.web.listener;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class BookStoreHttpSessionListener implements HttpSessionListener {

	@SuppressWarnings("unchecked")
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		List<HttpSession> sessionList = (List<HttpSession>) se.getSession().getServletContext().getAttribute("sessionList");
//		se.getSession().setMaxInactiveInterval(Integer.MIN_VALUE);
		sessionList.add(se.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub

	}

}
