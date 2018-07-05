package com.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.dao.OrderDao;
import com.domain.Order;
import com.util.JNDIUtils;
import com.util.ManageThreadLocal;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void addOder(Order order) throws SQLException {
		QueryRunner queryRunner = new QueryRunner();
		
		String sql = "INSERT INTO orders VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		queryRunner.update(ManageThreadLocal.getConnection(), sql, 
				order.getId(), order.getMoney(), order.getReceiverAddress(), order.getReceiverName(),
				order.getReceiverPhone(), order.getPaystate(), order.getOrdertime(), order.getUser().getId());
		
	}

	@Override
	public List<Order> findOrdersByUserId(int id) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JNDIUtils.getDs());
		
		String sql = "SELECT * FROM orders WHERE user_id=?";
		return queryRunner.query(sql, new BeanListHandler<Order>(Order.class), id);
	}

	@Override
	public Order findOrderByOrderId(String id) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JNDIUtils.getDs());
		
		String sql = "SELECT * FROM orders WHERE id=?";
		return queryRunner.query(sql, new BeanHandler<Order>(Order.class), id);
	}

	@Override
	public void deleteOrder(String orderId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "DELETE FROM orders WHERE id=?";
		queryRunner.update(ManageThreadLocal.getConnection(), sql, orderId);
		
	}

	@Override
	public void modifyOrderPaystate(String orderId, int paystate) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JNDIUtils.getDs());
		
		String sql = "UPDATE orders SET paystate=? WHERE id=?";
		queryRunner.update(sql, orderId, paystate);
		
	}

}
