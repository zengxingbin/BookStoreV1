package com.testutils;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.dao.BookDao;
import com.dao.impl.BookDaoImpl;
import com.domain.Book;

public class DatabaseTest {
    BookDao bookDao = new BookDaoImpl();
    @Test
    public void testSelect() throws SQLException {
        List<Book> bookList = bookDao.queryBooks();
        for(Book book : bookList) {
            System.out.println(book);
        }
    }
}
