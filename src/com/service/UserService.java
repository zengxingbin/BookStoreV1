package com.service;

import com.domain.User;
import com.execption.UserException;

public interface UserService {

	/**
	 * 新用户注册
	 * @param newUser
	 * @throws UserException 
	 */
	void register(User newUser) throws UserException;

	/**
	 * 激活用户
	 * @param activeCode
	 * @throws UserException 
	 */
	void activerUser(String activeCode) throws UserException;

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return 
	 * @throws UserException 
	 */
	User login(String username, String password) throws UserException;

	/**
	 * 修改用户信息
	 * @param loginUser
	 * @throws UserException 
	 */
	void modifyUserInfo(User loginUser) throws UserException;

	/**
	 * 通过邮箱查找用户
	 * @param email
	 * @return 
	 * @throws UserException 
	 */
	User findUserByEmail(String email) throws UserException;

	/**
	 * 通过用户名查找用户
	 * @param username
	 * @return
	 * @throws UserException 
	 */
	User findUserByUsername(String username) throws UserException;

}
