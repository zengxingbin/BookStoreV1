package com.web.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import com.domain.User;

public class BookStoreSevletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final List<HttpSession> sessionList = Collections.synchronizedList(new ArrayList<HttpSession>());
        sce.getServletContext().setAttribute("sessionList", sessionList);
        
        // 注册定时器
        new Timer().schedule(new TimerTask() {
            
            @Override
            public void run() {
                for (Iterator<HttpSession> iterator = sessionList.iterator(); iterator
                        .hasNext();) {
                    HttpSession session = iterator.next();
                    User loginUser = null;
                    try {
                        loginUser = (User) session.getAttribute("loginUser");
                    } catch (Exception e) {
                        return;
                    }
                    if(loginUser != null) {
                        long lastAccessedTime = session.getLastAccessedTime();
                        long currentTime = System.currentTimeMillis();
                        if((currentTime - lastAccessedTime) > 10 * 60 * 1000) {
                            session.removeAttribute("loginUser");
                            session.setAttribute("loginStatus", false);
                        }
                    }                                       
                }
                
            }
        }, 1 * 1000, 5 * 1000);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub

    }

}
