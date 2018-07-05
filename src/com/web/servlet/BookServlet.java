package com.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import net.sf.json.JSONArray;

import com.domain.Book;
import com.domain.Page;
import com.execption.BookException;
import com.service.BookService;
import com.service.impl.BookServiceImpl;

public class BookServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 175738641092567745L;

	/**
	 * Constructor of the object.
	 */
	public BookServlet() {
		super();
	}

	public void searchBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchName = request.getParameter("searchname");

		String echo = request.getParameter("echo");
		BookService bookService = new BookServiceImpl();
		if(echo != null && "true".equals(echo)) {
			List<Book> books = null;
			try {
				books = bookService.queryBooks(searchName);
			} catch (BookException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			
			Set<String> bookNameSet = new LinkedHashSet<String>();
			for (Book book : books) {
				bookNameSet.add(book.getName());
			}
			String bookNameList = JSONArray.fromObject(bookNameSet).toString();
			
			PrintWriter out = response.getWriter();
			out.print(bookNameList);
			out.flush();
			out.close();
		} else {
			HttpSession session = request.getSession();
			Page page = (Page) session.getAttribute("page");
			if(page == null) {
				page = new Page(8, 4);
			}
			page.setCategory("全部图书");
			page.setSearchName(searchName);
			
			try {
				bookService.getBooksBySearchName(searchName, page);
			} catch (BookException e) {
				e.printStackTrace();
			}
			
			session.setAttribute("page", page);
			String path = "/product_list.jsp";
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
	
	public void showBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Page page = (Page) session.getAttribute("page");
		if(page == null) {
			page = new Page(8, 4);
		}
		
		String category = request.getParameter("category");
		page.setCategory(category);
		page.setCurrentPage(1);
		BookService bookService = new BookServiceImpl();
		try {
			if("全部图书".equals(category)) {
				bookService.getBooksByPage(page);	
			} else {
				bookService.getBooksByCategory(category, page);
			}
		} catch (BookException e) {
			e.printStackTrace();
		}	
		if(page.getPageNumber() == 0) {
			page.setCurrentPage(0);
		}
		page.setSearchName("");
		session.setAttribute("page", page);
		String path = "/product_list.jsp";
		request.getRequestDispatcher(path).forward(request, response);
	}

	public void changePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Page page = (Page) session.getAttribute("page");
		if(page == null) {
			page = new Page(8, 4);
			page.setCategory("全部图书");
		}
		
		int currentPage = page.getCurrentPage();
		if(request.getParameter("previouspage") != null) {
			if(currentPage > 1) {
				currentPage--;
			}
		} else {
			if(currentPage < page.getPageNumber()) {
				currentPage++;
			}
		}
		page.setCurrentPage(currentPage);
		
		BookService bookService = new BookServiceImpl();
		try {
			String category = page.getCategory();
			if("全部图书".equals(category )) {
				bookService.getBooksByPage(page);	
			} else {
				bookService.getBooksByCategory(category, page);
			}
		} catch (BookException e) {
			e.printStackTrace();
		}
		
		page.setSearchName("");
		session.setAttribute("page", page);
		String path = "/product_list.jsp";
		response.sendRedirect(request.getContextPath() + path);
	}
	
	public void showBookInformation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String isbn = request.getParameter("isbn");
		Book book = null;
		try {
			book = new BookServiceImpl().getBook(isbn);
			request.setAttribute("book", book);
			String path = "/product_info.jsp";
			request.getRequestDispatcher(path).forward(request, response);
			return;
		} catch (BookException e) {
			e.printStackTrace();
			return;
		}				
	}
	
	@SuppressWarnings("unchecked")
	public void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String isbn = request.getParameter("isbn");		
		Book book = new Book();
		book.setIsbn(isbn);
		
		HttpSession session = request.getSession();
		Map<Book, Integer> cart = (Map<Book, Integer>) session.getAttribute("cart");	
		try {
			doAddCart(session, cart, book);
			String path = "/cart.jsp";
			response.sendRedirect(request.getContextPath() + path);
			return;
		} catch (BookException e) {
			e.printStackTrace();
			return;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void changeCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String numberChangeStr = request.getParameter("numberChange");
		
		String isbn = request.getParameter("isbn");		
		Book book = new Book();
		book.setIsbn(isbn);
		
		HttpSession session = request.getSession();
		Map<Book, Integer> cart = (Map<Book, Integer>) session.getAttribute("cart");		
		int numberChange = Integer.parseInt(numberChangeStr);
		doChangeCart(cart, book, numberChange);
		Set<Book> bookSet = cart.keySet();
		float subtotal = 0.0f;
		float total = 0.0f;
		int number = 0;
		for (Book key : bookSet) {
			int value = cart.get(key);
			total += value * key.getPrice();
			if(book.equals(key)) {
				number = value;
				subtotal = key.getPrice() * value;
			}
		}			
		PrintWriter writer = response.getWriter();
		String jsonObj = "{'number':" + number + ", 'subtotal':" + subtotal + ", 'total':" + total + " }"; 
		writer.write(jsonObj);
		writer.flush();
		writer.close();
	}
	
	@SuppressWarnings("unchecked")
	public void deleteCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String isbn = request.getParameter("isbn");
		Book book = new Book();
		book.setIsbn(isbn);
		Map<Book, Integer> cart = (Map<Book, Integer>) request.getSession().getAttribute("cart");
		if(cart.containsKey(book)) {
			cart.remove(book);
			Set<Entry<Book,Integer>> entrySet = cart.entrySet();
			float total = 0.0f;
			for (Entry<Book, Integer> entry : entrySet) {
				total += entry.getValue() * entry.getKey().getPrice();
			}
			PrintWriter writer = response.getWriter();
			writer.write(total + "");
			writer.flush();
			writer.close();
			return;
			
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
	}
	
	private void doAddCart(HttpSession session, Map<Book, Integer> cart, Book book) throws BookException {
		if(cart == null) {
			cart = new LinkedHashMap<Book, Integer>();	
			session.setAttribute("cart", cart);
		} else {
			if(cart.containsKey(book)) {
				int number = cart.get(book);
				cart.put(book, ++number);
				return;
			}
		}
		
		book = new BookServiceImpl().getBook(book.getIsbn());
		cart.put(book, 1);		
	}
	
	private void doChangeCart(Map<Book, Integer> cart, Book book, int numberChange) {
		if(cart != null) {
			if(cart.containsKey(book)) {
				Set<Entry<Book,Integer>> entrySet = cart.entrySet();
				for (Entry<Book, Integer> entry : entrySet) {
					if(book.equals(entry.getKey())) {
						if(numberChange == 1) {
							if(entry.getValue() == entry.getKey().getPnum())
								return;							
						} else {
							if(entry.getValue() == 1)
								return;
						}
					}
				}
				cart.put(book, cart.get(book) + numberChange);
			}
		}		
	}
}
