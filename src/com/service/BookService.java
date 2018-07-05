package com.service;

import java.util.List;

import com.domain.Book;
import com.domain.Page;
import com.domain.QueryCondition;
import com.execption.BookException;

public interface BookService {
    /**
     * 获取一本图书
     * @param isbn
     * @return
     * @throws BookException 
     */
    public Book getBook(String isbn) throws BookException;
    /**
     * 获取图书列表
     * @return
     * @throws BookException 
     */
    public List<Book> getBooks() throws BookException;
    /**
     * 分页查询
     * @param page
     * @throws BookException 
     */
    public void getBooksByPage(Page page) throws BookException;
    /**
     * 
     * @param searchName
     * @param page
     * @throws BookException 
     */
    public void getBooksBySearchName(String searchName, Page page) throws BookException;
    /**
     * 查询图书
     * @param isbn
     * @param name
     * @param category
     * @param minPrice
     * @param maxPrice
     * @throws BookException 
     */
    public List<Book> queryBooks(String isbn, String name, String category,
            String minPrice, String maxPrice) throws BookException; 
    /**
     * 
     * @param name
     * @return
     * @throws BookException 
     */
    public List<Book> queryBooks(String name) throws BookException;
    /**
     * 
     * @param isbn
     * @param name
     * @param category
     * @param minPrice
     * @param maxPrice
     * @param page
     * @throws BookException 
     */
    public void queryBooks(String isbn, String name, String category,
            String minPrice, String maxPrice, Page page) throws BookException;
    
    
    /**
     * 
     * @param queryCondition
     * @param page
     * @throws BookException 
     */
    public void queryBooks(QueryCondition queryCondition, Page page) throws BookException;
    /**
     * 添加图书
     * @param newBook
     * @return
     * @throws BookException 
     */
    public void addBook(Book newBook) throws BookException;
    
    /**
     * 修改图书信息
     * @param newBook
     * @return
     * @throws BookException 
     */
    public Book modifyBook(Book newBook) throws BookException;
    /**
     * 删除图书
     * @param isbn
     * @return
     * @throws BookException 
     */
    public Book deleteBook(String isbn) throws BookException;
    
    /**
     * 批量删除
     * @param isbn
     * @return
     */
    public List<Book> deleteBooks(String[] isbns);
    
    /**
     * 
     * @param category
     * @param page
     * @throws BookException 
     */
    public void getBooksByCategory(String category, Page page) throws BookException;
}
