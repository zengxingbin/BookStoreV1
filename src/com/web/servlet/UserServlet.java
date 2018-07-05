package com.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.domain.User;
import com.execption.UserException;
import com.service.UserService;
import com.service.impl.UserServiceImpl;

public class UserServlet extends BaseServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 7129328464010236221L;

    /**
     * Constructor of the object.
     */
    public UserServlet() {
        super();
    }

    public void checkEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        String email = request.getParameter("email");
        
        UserService userService = new UserServiceImpl();
        try {
            User user = userService.findUserByEmail(email);
            if(user != null)
                writer.write("false");
            else
                writer.write("true");
        } catch (UserException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        
        writer.flush();
        writer.close();
    }
    
    public void checkUserName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        String username = request.getParameter("username");
        
        UserService userService = new UserServiceImpl();
        try {
            User user = userService.findUserByUsername(username);
            if(user != null) {
                writer.write("false");
            } else {
                writer.write("true");
            }
        } catch (UserException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        
        writer.flush();
        writer.close();
    }
        
    @SuppressWarnings("unchecked")
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User newUser = new User();
        Map<String, String[]> paramMap = request.getParameterMap();
        try {
            BeanUtils.populate(newUser, paramMap);
            newUser.setActiveCode(UUID.randomUUID().toString());
            UserService userService = new UserServiceImpl();
            userService.register(newUser);
            request.getRequestDispatcher("/registersuccess.jsp").forward(request, response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (UserException e) {
            request.setAttribute("registerErrorMsg", e.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
    }
    
    public void activerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String activeCode = request.getParameter("activeCode");
        if(activeCode != null && !"".equals(activeCode)) {
            UserService userService = new UserServiceImpl();
            try {
                userService.activerUser(activeCode);
                request.getRequestDispatcher("/activesuccess.jsp").forward(request, response);
            } catch (UserException e) {
                PrintWriter writer = response.getWriter();
                writer.write(e.getMessage());
                writer.flush();
                writer.close();
            }
        } else { // 无法识别的请求
            
        }
    }
    
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        UserService userService = new UserServiceImpl();
        try {           
            // 是否记住用户名
            String isRememberUsername = request.getParameter("isRememberUsername");
            createCookie(response, Integer.MAX_VALUE, "rememberUserName", username, Boolean.valueOf(isRememberUsername));
            
            User loginUser = userService.login(username, password);
            if(loginUser != null) {
                if(loginUser.getState() == 1) {
                    HttpSession session = request.getSession();
                    session.setAttribute("loginUser", loginUser);
                    
                    // 是否自动登录操作
                    String isAutoLogin = request.getParameter("isAutoLogin");
                    createCookie(response, 60 * 60 * 24 * 7, "autoLogin", username + "&" + password, Boolean.valueOf(isAutoLogin));
                    
                    if(!"管理员".equals(loginUser.getRole())) { // 普通用户登录
                        String url = (String) request.getSession().getAttribute("url");
                        if(url != null) { // 跳转到指定页面
                            request.getSession().removeAttribute("url");
                            writer.write(url);
                        } else { // 跳转到首页
                            writer.write("/index.jsp");
                        }
                    }
                    else {  // 管理员登录                
                            writer.write("/admin/login/home.jsp");                                      
                    }
                    
                    session.setAttribute("loginStatus", true);
                } else { //用户没有激活
                    writer.write("该用户还没有激活，请先去激活！");
                }
            } else { // 登录失败
                writer.write("false");
            }
        } catch (UserException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        
        writer.flush();
        writer.close();
    }
    
    public void modifyUserInformation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取表单数据
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        String newPassword = request.getParameter("password");
        if(!"".equals(newPassword))
            loginUser.setPassword(newPassword );
        String newGender = request.getParameter("gender");
        if(!"".equals(newGender))
            loginUser.setGender(newGender);
        String newTelephone = request.getParameter("telephone");
        if(!"".equals(newTelephone))
            loginUser.setTelephone(newTelephone);
        
        UserService userService = new UserServiceImpl();
        try {
            userService.modifyUserInfo(loginUser);
            request.getSession().removeAttribute("loginUser");
            request.getRequestDispatcher("/modifyUserInfoSuccess.jsp").forward(request, response);
            return;
        } catch (UserException e) {
            request.setAttribute("modifyUserInfoErrorMsg", e.getMessage());
            request.getRequestDispatcher("/modifyuserinfo.jsp").forward(request, response);
            return;
        }
    }
    
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if(loginUser != null) {
            // 删除会话
            request.getSession().removeAttribute("loginUser");
            request.getSession().setAttribute("loginStatus", false);
            /*// 删除自动登录Cookie
            Cookie[] cookies = request.getCookies();
            Cookie cookie = null;
            if(cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    if("autoLogin".equals(cookies[i].getName())) {
                        cookie = cookies[i];
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        break;
                    }
                }
            }*/
        }
                
        // 跳转到首页
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    private void createCookie(HttpServletResponse response, int time,
            String name, String value, boolean condition) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if(condition) {         
            cookie.setMaxAge(time); 
        } else {
            cookie.setMaxAge(0);
        }
        response.addCookie(cookie);
    }
}
