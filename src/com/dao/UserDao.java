package com.dao;

import java.sql.SQLException;

import com.domain.User;

public interface UserDao {

	/**
	 * 添加新用户
	 * @param newUser
	 * @throws SQLException 
	 */
	void addUser(User newUser) throws SQLException;

	/**
	 * 通过激活码查找用户
	 * @param activeCode
	 * @return
	 * @throws SQLException 
	 */
	User findUserByActiveCode(String activeCode) throws SQLException;

	/**
	 * 激活用户
	 * @param id
	 * @throws SQLException 
	 */
	void activeUser(int id) throws SQLException;

	/**
	 * 通过用户名和密码查找用户
	 * @param username
	 * @param password
	 * @return 
	 * @throws SQLException 
	 */
	User findUserByUserNameAndPassword(String username, String password) throws SQLException;

	/**
	 * 修改用户信息
	 * @throws SQLException 
	 */
	void modifyUserInfo(User loginUser) throws SQLException;

	/**
	 * 通过邮箱查找用户
	 * @param email
	 * @return
	 * @throws SQLException 
	 */
	User findUserByEmail(String email) throws SQLException;

	/**
	 * 通过用户名查找用户
	 * @param username
	 * @return
	 * @throws SQLException 
	 */
	User findUserByUsername(String username) throws SQLException;

}
