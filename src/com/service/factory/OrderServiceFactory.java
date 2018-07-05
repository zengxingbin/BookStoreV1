package com.service.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.service.OrderService;
import com.service.impl.OrderServiceImpl;
import com.util.ManageThreadLocal;

public class OrderServiceFactory {
	public OrderService createOrderService() {
		final OrderService orderService = new OrderServiceImpl();
		
		OrderService proxyOS = (OrderService) Proxy.newProxyInstance(orderService.getClass().getClassLoader(), orderService.getClass().getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				Object result = null;
				if("addOrder".equals(method.getName()) || "deleteOrder".equals(method.getName())) {
					try {
						ManageThreadLocal.setAutoCommit(false);
						result = method.invoke(orderService, args);
						ManageThreadLocal.commit();
					} catch (Exception e) {
						try {
							ManageThreadLocal.rollback();
						} catch (Exception e1) {
							e1.printStackTrace();
						} 
						e.printStackTrace();
					} finally {
						ManageThreadLocal.close();
					}
				} else {
					result = method.invoke(orderService, args);
				}
			
				return result;
			}
		});
		return proxyOS;
	}
}
