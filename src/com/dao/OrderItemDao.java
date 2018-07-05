package com.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.domain.OrderItem;

public interface OrderItemDao {

	/**
	 * 添加的订单项
	 * @param orderItems
	 * @throws SQLException 
	 */
	void addOrderItems(List<OrderItem> orderItems) throws SQLException;

	/**
	 * 
	 * @param id
	 * @return 
	 * @throws SQLException 
	 */
	List<Map<String, Object>> findorderItemDaosByOrderId(String id) throws SQLException;

	/**
	 * 
	 * @param orderId
	 * @throws SQLException 
	 */
	void deleteOrderItem(String orderId) throws SQLException;

}
