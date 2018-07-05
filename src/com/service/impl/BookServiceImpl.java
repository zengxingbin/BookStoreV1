package com.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.BookDao;
import com.dao.impl.BookDaoImpl;
import com.domain.Book;
import com.domain.Page;
import com.domain.QueryCondition;
import com.execption.BookException;
import com.service.BookService;

public class BookServiceImpl implements BookService {

	private BookDao bookDAO = new BookDaoImpl();
	
	@Override
	public Book getBook(String isbn) throws BookException {
		Book book = null;
		
		try {
			book = bookDAO.queryBook(isbn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BookException("获取图书失败！");
		}		
		return book; 
	}

	@Override
	public List<Book> getBooks() throws BookException {
		
		try {
			return bookDAO.queryBooks();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BookException("获取图书列表失败！");
		}

	}

	public void getBooksByPage(Page page) throws BookException {
		/*try {
			// 获取图书总数
			int bookNumber = bookDAO.count();
			page.setBookNumber(bookNumber);
			page.setPageNumber((int) (Math.ceil(bookNumber * 1.0 / page.getPageSize())));
			int resultNumber = page.getPageSize();
			int beginIndex = (page.getCurrentPage() - 1) * resultNumber;
			List<Book> books = bookDAO.queryBooks(beginIndex, resultNumber);
			page.setBooks(books);
			page.setCurrentPageSize(books.size());
			page.setRowNumber((int) (Math.ceil(books.size() * 1.0 / page.getColumnNumber())));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BookException("分页查询失败！");
		}*/
		getBooksByCategory(null, page);
		
	}

	@Override
	public void getBooksByCategory(String category, Page page) throws BookException {
		try {
			// 获取图书总数
			int bookNumber = bookDAO.count(null, null, category, null, null);
			page.setBookNumber(bookNumber);
			int resultNumber = page.getPageSize();
			page.setPageNumber((int) (Math.ceil(bookNumber * 1.0 / resultNumber)));
			int beginIndex = (page.getCurrentPage() - 1) * resultNumber;
			List<Book> books = bookDAO.queryBooks(null, null, category, null, null, beginIndex, resultNumber);
			page.setBooks(books);
			page.setCurrentPageSize(books.size());
			page.setRowNumber((int) (Math.ceil(books.size() * 1.0 / page.getColumnNumber())));			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BookException("分页查询失败！");
		}
		
	}

	@Override
	public void getBooksBySearchName(String searchName, Page page) throws BookException {
		try {
			// 获取图书总数
			int bookNumber = bookDAO.count(null, searchName, null, null, null);
			page.setBookNumber(bookNumber);
			int resultNumber = page.getPageSize();
			page.setPageNumber((int) (Math.ceil(bookNumber * 1.0 / resultNumber)));
			int beginIndex = (page.getCurrentPage() - 1) * resultNumber;
			List<Book> books = bookDAO.queryBooks(null, searchName, null, null, null, beginIndex, resultNumber);
			page.setBooks(books);
			page.setCurrentPageSize(books.size());
			page.setRowNumber((int) (Math.ceil(books.size() * 1.0 / page.getColumnNumber())));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BookException("分页查询失败！");
		}
		
	}

	public List<Book> queryBooks(String isbn, String name, String category,
			String minPrice, String maxPrice) throws BookException {
		
		try {
			return bookDAO.queryBooks(isbn, name, category, minPrice, maxPrice);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BookException("查询图书失败！");
		}
	}

	@Override
	public List<Book> queryBooks(String name) throws BookException {
	
		try {
			return bookDAO.queryBooks(null, name, null, null, null);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BookException("查询图书失败！");
		}
	}

	public void queryBooks(String isbn, String name, String category,
			String minPrice, String maxPrice, Page page) throws BookException {
		
		try {			
			int bookNumber = bookDAO.count(isbn, name, category, minPrice, maxPrice);
			page.setBookNumber(bookNumber);
			page.setPageNumber((int) (Math.ceil(bookNumber * 1.0 / page.getPageSize())));
			int resultNumber = page.getPageSize();
			int beginIndex = (page.getCurrentPage() - 1) * resultNumber;
			List<Book> books = bookDAO.queryBooks(isbn, name, category, minPrice, maxPrice, beginIndex, resultNumber);
			page.setBooks(books);
			page.setCurrentPageSize(books.size());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BookException("查询图书失败！");
		}
		
	}

	public void queryBooks(QueryCondition queryCondition, Page page) throws BookException {
		
		queryBooks(queryCondition.getBookIsbn(), queryCondition.getName(), queryCondition.getCategory(), 
				queryCondition.getMinPrice(), queryCondition.getMaxPrice(), page);		
	}

	@Override
	public void addBook(Book newBook) throws BookException {
		
		try {
			bookDAO.insertBook(newBook);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BookException("添加图书失败！");
		}
	}

	@Override
	public Book modifyBook(Book newBook) throws BookException {
		Book oldBook = null;
		

		oldBook = getBook(newBook.getIsbn());
		if(oldBook != null) {
			try {
				bookDAO.updateBook(newBook);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BookException("修改图书信息失败！");
			}
		}
	
		return oldBook;
	}

	@Override
	public Book deleteBook(String isbn) throws BookException {
		Book oldBook = null;
		
		oldBook = getBook(isbn);
		if(oldBook != null)
			try {
				bookDAO.deleteBook(isbn);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BookException("删除图书失败！");
			}
	
		return oldBook;
	}

	@Override
	public List<Book> deleteBooks(String[] isbns) {
		List<Book> books = new ArrayList<Book>();
		
		try {
			for (String isbn : isbns) {
				books.add(bookDAO.queryBook(isbn));
			}
			
			bookDAO.deleteBooks(isbns);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return books; 
	}

}
