package com.dao.impl;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.dao.UserDao;
import com.domain.User;
import com.util.C3P0Utils;
import com.util.JNDIUtils;

public class UserDaoImpl implements UserDao {

	private static DataSource ds = C3P0Utils.getDs();//JNDIUtils.getDs();
	@Override
	public void addUser(User newUser) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(ds);
		
		String sql = "INSERT INTO USER(username, PASSWORD, gender, email, telephone, introduce, activeCode, state) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		queryRunner.update(sql, 
				newUser.getUsername(), newUser.getPassword(), 
				newUser.getGender(), newUser.getEmail(), 
				newUser.getTelephone(), newUser.getIntroduce(), 
				newUser.getActiveCode(), newUser.getState());
		
	}
	
	@Override
	public User findUserByActiveCode(String activeCode) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(ds);
		
		String sql = "SELECT * FROM USER WHERE activeCode=?";
		User user = queryRunner.query(sql, new BeanHandler<User>(User.class), activeCode);
		return user;
	}

	@Override
	public void activeUser(int id) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(ds);
		
		String sql = "UPDATE USER SET state=? WHERE id=?";
		queryRunner.update(sql, 1, id);
		
	}

	@Override
	public User findUserByUserNameAndPassword(String username, String password) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(ds);
		
		String sql = "SELECT * FROM USER WHERE username=? AND PASSWORD=?";
		User loginUser = queryRunner.query(sql, new BeanHandler<User>(User.class), username, password);
		return loginUser;
		
	}

	@Override
	public void modifyUserInfo(User loginUser) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(ds);
		
		String sql = "UPDATE USER SET PASSWORD=?, gender=?, telephone=? WHERE id=?";
		queryRunner.update(sql, loginUser.getPassword(), loginUser.getGender(), loginUser.getTelephone(), loginUser.getId());
		
	}

	@Override
	public User findUserByEmail(String email) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(ds);
		
		String sql = "SELECT * FROM USER WHERE email=?";
		return queryRunner.query(sql, new BeanHandler<User>(User.class), email);
	}

	@Override
	public User findUserByUsername(String username) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(ds);
		String sql = "SELECT * FROM USER WHERE username=?";
		return queryRunner.query(sql, new BeanHandler<User>(User.class), username);
	}

}
