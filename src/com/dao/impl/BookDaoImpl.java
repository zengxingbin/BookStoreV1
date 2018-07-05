package com.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.dao.BookDao;
import com.domain.Book;
import com.domain.OrderItem;
import com.util.C3P0Utils;
//import com.util.C3P0Utils;
import com.util.JNDIUtils;
import com.util.ManageThreadLocal;

public class BookDaoImpl implements BookDao {

	//private static DataSource ds = JNDIUtils.getDs();
    private static DataSource ds = C3P0Utils.getDs();
	@Override
	public Book queryBook(String isbn) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(ds);
		
		String sql = "select * from book where isbn=?";
		return queryRunner.query(sql, new BeanHandler<Book>(Book.class), isbn);
	}
	
	@Override
	public List<Book> queryBooks() throws SQLException {
		
		String sql = "select * from book";
		return queryBooks(sql);

	}
	
	@Override
	public List<Book> queryBooks(String isbn, String name, String category,
			String minPrice, String maxPrice) throws SQLException {		
		
			String sql = "select * from book where 1=1";
			List<Object> params = new ArrayList<Object>();
			sql = getSql(isbn, name, category, minPrice, maxPrice, sql, params);
			return queryBooks(sql, params.toArray());

	}
	
	@Override
	public List<Book> queryBooks(int beginIndex, int resultNumber) throws SQLException {
	
		String sql = "select * from book limit ?, ?";
		return queryBooks(sql, beginIndex, resultNumber);

	}

	@Override
	public List<Book> queryBooks(String isbn, String name, String category,
			String minPrice, String maxPrice, int beginIndex, int resultNumber) throws SQLException {
		String sql = "select * from book where 1=1";
		List<Object> params = new ArrayList<Object>();
		sql = getSql(isbn, name, category, minPrice, maxPrice, sql, params);
		sql += " limit ?, ?";
		params.add(beginIndex);
		params.add(resultNumber);
		return queryBooks(sql, params.toArray());

	}

	@Override
	public void insertBook(Book newBook) throws SQLException {
		String sql = "insert into book values(?, ?, ?, ?, ?, ?, ?)";
		update(sql, 
				newBook.getIsbn(), newBook.getName(), 
				newBook.getPrice(), newBook.getPnum(), 
				newBook.getCategory(), newBook.getDescription(), newBook.getBookCoverPath());

	}
	
	@Override
	public void updateBook(Book newBook) throws SQLException {
			String sql = "update book set name=?, price=?, pnum=?, category=?, description=?, bookCoverPath=? where isbn=?";
			update(sql, 
					newBook.getName(), newBook.getPrice(), 
					newBook.getPnum(), newBook.getCategory(), 
					newBook.getDescription(), newBook.getBookCoverPath(), newBook.getIsbn());
	}

	@Override
	public void updateBook(List<OrderItem> orderItems) throws SQLException {
		String sql = "UPDATE book SET pnum=pnum-? WHERE isbn=?";
		QueryRunner queryRunner = new QueryRunner();
		Object[][] params = new Object[orderItems.size()][];
		for (int i = 0; i < params.length; i++) {
			OrderItem orderItem = orderItems.get(i);
			params[i] = new Object[]{orderItem.getBuynum(), orderItem.getBook().getIsbn()};
		}
		queryRunner.batch(ManageThreadLocal.getConnection(), sql, params);
		
	}

	@Override
	public void updateBook(Map<String, Integer> bookMap) throws SQLException {
		String sql = "UPDATE book SET pnum=pnum+? WHERE isbn=?";
		QueryRunner queryRunner = new QueryRunner();
		Object[][] params = new Object[bookMap.size()][];
		Set<Entry<String,Integer>> entrySet = bookMap.entrySet();
		int i =0;
		for (Entry<String, Integer> entry : entrySet) {
			params[i++] = new Object[]{entry.getValue(), entry.getKey()};			
		}
		queryRunner.batch(ManageThreadLocal.getConnection(), sql, params);
		
	}

	@Override
	public void deleteBook(String isbn) throws SQLException {
		String sql = "delete from book where isbn=?";
		update(sql, isbn);
	
	}
	
	@Override
	public void deleteBooks(String[] isbns) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(ds);

		String sql = "delete from book where isbn=?";
		String[][] params = new String[isbns.length][];
		for (int i = 0; i < isbns.length; i++) {
			params[i] = new String[]{isbns[i]};
		}
		queryRunner.batch(sql, params);
		
	}
	
	@Override
	public int count() throws SQLException {
		String sql = "select count(*) from book";
	
		return count(sql);

	}

	@Override
	public int count(String isbn, String name, String category,
			String minPrice, String maxPrice) throws SQLException {
				

		String sql = "select count(*) from book where 1=1";
		List<Object> params = new ArrayList<Object>();
		sql = getSql(isbn, name, category, minPrice, maxPrice, sql, params);
		return count(sql, params.toArray());
		
	}

	private String getSql(String isbn, String name, String category,
			String minPrice, String maxPrice, String sql, List<Object> params) {
		if(isbn != null && !"".equals(isbn)) {
			sql += " and isbn like ?";
			params.add("%" + isbn + "%");
		}
		if(name != null && !"".equals(name)) {
			sql += " and name like ?";
			params.add("%" + name + "%");
		}
		if(category != null && !"".equals(category)) {
			sql += " and category=?";
			params.add(category);
		}
		if(minPrice != null && !"".equals(minPrice)) {
			sql += " and price > ?";
			params.add(Float.valueOf(minPrice));
		}
		if(maxPrice != null && !"".equals(maxPrice)) {
			sql += " and price < ?";
			params.add(Float.valueOf(maxPrice));
		}
		return sql;
	}
	
	private List<Book> queryBooks(String sql, Object... params) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(ds);
		return queryRunner.query(sql, new BeanListHandler<Book>(Book.class), params);
	}
	
	private int count(String sql, Object... params) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(ds);
		return ((Long)queryRunner.query(sql, new ScalarHandler(), params)).intValue();
		
	}

	private int update(String sql, Object... params) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(ds);
		return queryRunner.update(sql, params);
	}
}
