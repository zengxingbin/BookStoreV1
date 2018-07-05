package com.domain;

import java.util.List;

public class Page {
    
    private int pageSize = 8;
    private int currentPage = 1;        // 当前页
    private int currentPageSize = 0;    // 当前页显示的图书数
    private int pageNumber = 0;         // 总页数
    private int columnNumber = 4;       // 
    private int rowNumber;              // 行数
    
    private String category;
    private String searchName;
    
    private int  bookNumber = 0;        // 书的数量
    private List<Book> books;           // 
    
    
    public Page() {
        super();
    }

    public Page(int pageSize) {
        super();
        this.pageSize = pageSize;
    }
    
    public Page(int pageSize, int columnNumber) {
        super();
        this.pageSize = pageSize;
        this.columnNumber = columnNumber;
    }

    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPageSize() {
        return currentPageSize;
    }
    public void setCurrentPageSize(int currentPageSize) {
        this.currentPageSize = currentPageSize;
    }
    public int getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    public int getColumnNumber() {
        return columnNumber;
    }
    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }
    public int getRowNumber() {
        return rowNumber;
    }
    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public int getBookNumber() {
        return bookNumber;
    }
    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }
    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Page [pageSize=" + pageSize + ", currentPage=" + currentPage
                + ", currentPageSize=" + currentPageSize + ", pageNumber="
                + pageNumber + ", columnNumber=" + columnNumber
                + ", rowNumber=" + rowNumber + ", category=" + category
                + ", searchName=" + searchName + ", bookNumber=" + bookNumber
                + ", books=" + books + "]";
    }
    
}
