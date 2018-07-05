package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.domain.Order;

public interface OrderDao {

	/**
	 * 添加订单
	 * @param order
	 * @throws SQLException 
	 */
	void addOder(Order order) throws SQLException;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	List<Order> findOrdersByUserId(int id) throws SQLException;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	Order findOrderByOrderId(String id) throws SQLException;

	/**
	 * 
	 * @param orderId
	 * @throws SQLException 
	 */
	void deleteOrder(String orderId) throws SQLException;

	/**
	 * 
	 * @param orderId
	 * @param paystate
	 * @throws SQLException 
	 */
	void modifyOrderPaystate(String orderId, int paystate) throws SQLException;

}
