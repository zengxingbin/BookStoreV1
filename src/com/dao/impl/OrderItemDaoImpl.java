package com.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.domain.OrderItem;
import com.util.JNDIUtils;
import com.util.ManageThreadLocal;

public class OrderItemDaoImpl implements com.dao.OrderItemDao {

	@Override
	public void addOrderItems(List<OrderItem> orderItems) throws SQLException {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "INSERT INTO orderitem VALUES(?, ?, ?)";
		Object[][] params = new Object[orderItems.size()][];
		for (int i = 0; i < params.length; i++) {
			OrderItem orderItem = orderItems.get(i);
			params[i] = new Object[]{orderItem.getOrder().getId(), orderItem.getBook().getIsbn(), orderItem.getBuynum()};
		}
		queryRunner.batch(ManageThreadLocal.getConnection(), sql, params);
		
	}

	@Override
	public List<Map<String,Object>> findorderItemDaosByOrderId(String id) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JNDIUtils.getDs());
		String sql = "SELECT book_isbn, buynum FROM orderitem WHERE order_id=?";
		return queryRunner.query(sql, new MapListHandler(), id);
	}

	@Override
	public void deleteOrderItem(String orderId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "DELETE FROM orderitem WHERE order_id=?";
		queryRunner.update(ManageThreadLocal.getConnection(), sql, orderId);
		
	}

}
