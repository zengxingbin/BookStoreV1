package com.service.impl;

import java.sql.SQLException;

import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.domain.User;
import com.execption.UserException;
import com.service.UserService;
import com.util.SendJMail;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    @Override
    public void register(User newUser) throws UserException {
        
        try {
            userDao.addUser(newUser);
            String emailMsg = "注册成功,请<a href = 'http://192.168.1.105:8080/mybookstore/user?method=activeCode&activeCode=\"+ user.getActiveCode()+\"'>激活</a>后登录";
            SendJMail.sendMail(newUser.getEmail(), emailMsg );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("注册失败");
        }
    }
    @Override
    public void activerUser(String activeCode) throws UserException {
        try {
            User user = userDao.findUserByActiveCode(activeCode);
            if(user != null) {
                // �жϼ������Ƿ����
                userDao.activeUser(user.getId());
            } else {
                throw new UserException("�û�����ʧ�ܣ�");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("�û�����ʧ�ܣ�");
        }
        
    }
    @Override
    public User login(String username, String password) throws UserException {
        try {
            return userDao.findUserByUserNameAndPassword(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("�û���¼ʧ�ܣ�");
        }
        
    }
    @Override
    public void modifyUserInfo(User loginUser) throws UserException {
        try {
            userDao.modifyUserInfo(loginUser);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("�û���Ϣ�޸�ʧ�ܣ����Ժ����ԣ�");
        }
        
    }
    @Override
    public User findUserByEmail(String email) throws UserException {
        
        try {
            return userDao.findUserByEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("�����û�ʧ�ܣ�");
        }
    }
    @Override
    public User findUserByUsername(String username) throws UserException {
        
        try {
            return userDao.findUserByUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("�����û�ʧ�ܣ�");
        }
    }

}
