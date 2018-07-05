package com.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.BookDao;
import com.dao.OrderDao;
import com.dao.OrderItemDao;
import com.dao.impl.BookDaoImpl;
import com.dao.impl.OrderDaoImpl;
import com.dao.impl.OrderItemDaoImpl;
import com.domain.Book;
import com.domain.Order;
import com.domain.OrderItem;
import com.execption.OrderException;
import com.service.OrderService;

public class OrderServiceImpl implements OrderService {

    OrderDao orderDao = new OrderDaoImpl(); 
    OrderItemDao orderItemDao = new OrderItemDaoImpl();
    BookDao bookDao = new BookDaoImpl();
    @Override
    public void addOrder(Order order) throws OrderException {
        // 添加订单到数据库
        try {
            orderDao.addOder(order);
            orderItemDao.addOrderItems(order.getOrderItems());
            bookDao.updateBook(order.getOrderItems());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderException("订单生成失败！");
        }
        
    }
    @Override
    public List<Order> findOrdersByUserId(int id) throws OrderException {
        
        try {
            return orderDao.findOrdersByUserId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderException("订单查看失败");
        }
    }
    @Override
    public Order findOrderByOrderId(String  id) throws OrderException {
        Order order = null;
        try {
            order = orderDao.findOrderByOrderId(id);
            order.setOrderItems(new ArrayList<OrderItem>());
            List<Map<String, Object>> orderItemList = orderItemDao.findorderItemDaosByOrderId(id);
            for (Map<String, Object> map : orderItemList) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setBuynum((Integer) map.get("buynum"));
                Book book = bookDao.queryBook((String) map.get("book_isbn"));
                orderItem.setBook(book);
                order.getOrderItems().add(orderItem);
            }
            return order;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderException("订单详情查看失败");
        }
    }
    
    @Override
    public void deleteOrder(String orderId) throws OrderException {
        // 获取订单中的图书及数量
        try {
            List<Map<String, Object>> orderItemList = orderItemDao.findorderItemDaosByOrderId(orderId);
            Map<String, Integer> bookMap = new HashMap<String, Integer>();
            for (Map<String, Object> map : orderItemList) {
                bookMap.put((String) map.get("book_isbn"), (Integer) map.get("buynum"));
            }
            bookDao.updateBook(bookMap);
            orderItemDao.deleteOrderItem(orderId);
            orderDao.deleteOrder(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderException("删除订单失败！");
        }
        
    }
    @Override
    public void modifyOrderPaystate(String orderId, int paystate) throws OrderException {
        try {
            orderDao.modifyOrderPaystate(orderId, paystate);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderException("修改订单支付状态失败！");
        }
        
    }

}
