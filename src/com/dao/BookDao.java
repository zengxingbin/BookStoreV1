package com.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.domain.Book;
import com.domain.OrderItem;

public interface BookDao {
	
	/**
	 * 获取一本图书
	 * @param isbn
	 * @return
	 * @throws SQLException 
	 */
	public Book queryBook(String isbn) throws SQLException;
	
	/**
	 * 获取所有的图书列表
	 * @return
	 * @throws SQLException
	 */
	public List<Book> queryBooks() throws SQLException;
	
	/**
	 * 查询图书
	 * @param isbn
	 * @param name
	 * @param category
	 * @param minPrice
	 * @param maxPrice
	 * @return
	 * @throws SQLException 
	 */
	public List<Book> queryBooks(String isbn, String name, String category,
			String minPrice, String maxPrice) throws SQLException;

	/**
	 * 分页查询
	 * @param beginIndex
	 * @param resultNumber
	 * @return
	 * @throws SQLException 
	 */
	public List<Book> queryBooks(int beginIndex, int resultNumber) throws SQLException;

	/**
	 * 
	 * @param isbn
	 * @param name
	 * @param category
	 * @param minPrice
	 * @param maxPrice
	 * @param beginIndex
	 * @param resultNumber
	 * @return
	 * @throws SQLException 
	 */
	public List<Book> queryBooks(String isbn, String name, String category,
	String minPrice, String maxPrice, int beginIndex, int resultNumber) throws SQLException;

	/**
	 * 添加一本图书
	 * @param newBook
	 * @return
	 * @throws SQLException 
	 */
	public void insertBook(Book newBook) throws SQLException;
	
	/**
	 * 删除图书
	 * @param isbn
	 * @return
	 * @throws SQLException 
	 */
	public void deleteBook(String isbn) throws SQLException;
	
	/**
	 * 更新图书信息
	 * @param newBook
	 * @return
	 * @throws SQLException 
	 */
	public void updateBook(Book newBook) throws SQLException;
	
	/**
	 * 
	 * @param orderItems
	 * @throws SQLException 
	 */
	public void updateBook(List<OrderItem> orderItems) throws SQLException;

	/**
	 * 
	 * @param bookMap
	 * @throws SQLException 
	 */
	public void updateBook(Map<String, Integer> bookMap) throws SQLException;

	/**
	 * 批量删除图书
	 * @param isbns
	 * @return
	 * @throws SQLException 
	 */
	public void deleteBooks(String[] isbns) throws SQLException;

	/**
	 * 获取图书总数
	 * @return
	 * @throws SQLException 
	 */
	public int count() throws SQLException;
	
	/**
	 * 获取图书总数
	 * @param isbn
	 * @param name
	 * @param category
	 * @param minPrice
	 * @param maxPrice
	 * @return
	 * @throws SQLException 
	 */
	public int count(String isbn, String name, String category,
			String minPrice, String maxPrice) throws SQLException;
	
}

