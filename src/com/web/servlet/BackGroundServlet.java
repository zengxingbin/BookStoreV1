package com.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.domain.Book;
import com.domain.Page;
import com.domain.QueryCondition;
import com.execption.BookException;
import com.service.impl.BookServiceImpl;
import com.util.FileUploadUtils;

public class BackGroundServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -6310525756951641191L;

    /**
     * Constructor of the object.
     */
    public BackGroundServlet() {
        super();
    }

    /**
     * The doGet method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to get.
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doMethod(request, response);
    }

    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to post.
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doMethod(request, response);
    }
    
    private void doMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String uri = request.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        if(methodName != null && !"".equals(methodName)) {
            try {
                Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
                method.invoke(this, request, response);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                if("javax.servlet.ServletException".equals(e.getClass().getName())) {
                    throw new ServletException(e.getTargetException());
                } else if("java.io.IOException".equals(e.getClass().getName())) {
                    throw new IOException(e.getTargetException());
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);           
        }
    }

    public void listBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPageStr = request.getParameter("currentPage");
        Page page = new Page(10);
        if(currentPageStr == null) {
            page.setCurrentPage(1);
        } else {            
            page.setCurrentPage(Integer.valueOf(currentPageStr));
        }
        
        try {
            new BookServiceImpl().getBooksByPage(page);
        } catch (BookException e) {
            e.printStackTrace();
        }
        request.setAttribute("page", page);
        String path = "/admin/products/list.jsp";
        request.getRequestDispatcher(path).forward(request, response);
    }
    
    @SuppressWarnings("unchecked")
    public void addBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Map<String, Object[]> paramMap = null;
            Map<String, String[]> paramMap2 = null;
            if(ServletFileUpload.isMultipartContent(request)) {             
                paramMap = new HashMap<String, Object[]>();
                
                String storeDirectoryPath = this.getServletContext().getRealPath("/upload/bookcover");
                FileUploadUtils.fileUpload(request, paramMap, storeDirectoryPath);
            } else {
                // ��ȡ������
                //paramMap = request.getParameterMap();
                paramMap2 = request.getParameterMap();
                
            }
            
            Book newBook = new Book();
            BeanUtils.populate(newBook, paramMap2);
            // �����µ�isbn
            String isbn = UUID.randomUUID().toString();
            newBook.setIsbn(isbn);
            // ����ҵ���߼�
            new BookServiceImpl().addBook(newBook);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        listBooks(request, response);
    }

    @SuppressWarnings("unchecked")
    public void queryBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Page page = new Page(10);
        String currentPageStr = request.getParameter("currentPage");
        if(currentPageStr != null && !"".equals(currentPageStr)) {
            page.setCurrentPage(Integer.valueOf(currentPageStr));
        } else {
            page.setCurrentPage(1);
        }
        QueryCondition queryCondition = null;
        Object attrValue = request.getAttribute("queryCondition");
        if(attrValue != null)
            queryCondition = (QueryCondition) attrValue;
        else {
            queryCondition = new QueryCondition();
            //Map<String, String> paramMap = request.getParameterMap();
            Map<String,String[]> paramMap = request.getParameterMap();
            try {
                BeanUtils.populate(queryCondition, paramMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        // ����ҵ���߼�       
        //new BookServiceImpl().queryBooks(isbn, name, category, minPrice, maxPrice, page);
        try {
            new BookServiceImpl().queryBooks(queryCondition, page);
        } catch (BookException e) {
            e.printStackTrace();
        }
        // �ַ�ת��
        request.setAttribute("page", page);
        request.setAttribute("queryCondition", queryCondition);
        String path = "/admin/products/list.jsp";
        request.getRequestDispatcher(path).forward(request, response);
    }
    
    @SuppressWarnings("unchecked")
    public void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        //String isbn = paramMap.get("isbn");
        System.out.println(isbn);
        Book book = null;
        try {
            book = new BookServiceImpl().getBook(isbn);
        } catch (BookException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println(book.getName());
        request.setAttribute("book", book);
        
        //Map<String, String> paramMap = request.getParameterMap();
        Map<String, String[]> paramMap = request.getParameterMap();
        QueryCondition queryCondition = new QueryCondition();
        try {
            BeanUtils.populate(queryCondition, paramMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("queryCondition", queryCondition);
        // ��ȡ��ǰҳ��
        String currentPage = request.getParameter("currentPage"); 
        request.setAttribute("currentPage", currentPage);
        String path = "/admin/products/edit.jsp";
        // URL��д
        path = response.encodeURL(path);
        request.getRequestDispatcher(path).forward(request, response);
    }
    
    @SuppressWarnings("unchecked")
    public void editBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object[]> paramMap = null;
        Map<String, String[]> paramMap2 = null;
        try {
            if(ServletFileUpload.isMultipartContent(request)) {
                paramMap = new HashMap<String, Object[]>();             
                String storeDirectoryPath = this.getServletContext().getRealPath("/upload/bookcover");              
                FileUploadUtils.fileUpload(request, paramMap, storeDirectoryPath);
            } else {
                //paramMap = request.getParameterMap();
                paramMap2 = request.getParameterMap();
            }
            Book newBook = new Book();
            BeanUtils.populate(newBook, paramMap2);
            new BookServiceImpl().modifyBook(newBook);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // ��ȡ��ǰҳ��
        String currentPage = (String) paramMap.get("currentPage")[0]; 
        String path = "/background/queryBooks?currentPage=" + currentPage;
        // ��ȡ�Ự
        HttpSession session = request.getSession();
        Object queryCondition = session.getAttribute("queryCondition");
        session.removeAttribute("queryCondition");
        request.setAttribute("queryCondition", queryCondition);
        request.getRequestDispatcher(path).forward(request, response);
        
        /*// ��ȡ�Ự
        HttpSession session = request.getSession();
        Object queryCondition = session.getAttribute("queryCondition");
        session.removeAttribute("queryCondition");
        request.setAttribute("queryCondition", queryCondition);
        queryBooks(request, response);*/
    }

    @SuppressWarnings({ "unchecked" })
    public void deleteBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ��ȡisbn
        String[] isbns = request.getParameterValues("isbn");
        BookServiceImpl bookService = new BookServiceImpl();
        try {
            if(isbns != null)
                bookService.deleteBook(isbns[0]);
            else {
                isbns = request.getParameterValues("isbns");
                if(isbns != null)
                    bookService.deleteBooks(isbns);
            }
        } catch (BookException e1) {
            e1.printStackTrace();
        }
        
        // ��ȡ��ǰҳ��
        int currentPage = Integer.valueOf(request.getParameter("currentPage")); 
        // ��ȡ��ǰҳ�еļ�¼��
        int currentPageSize = Integer.valueOf(request.getParameter("currentPageSize")); 
        if(isbns != null && isbns.length == currentPageSize && currentPage > 1)
            currentPage--;
         
        Map<String, String[]> paramMap = request.getParameterMap();
        QueryCondition queryCondition = new QueryCondition();
        try {
            BeanUtils.populate(queryCondition, paramMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        request.setAttribute("queryCondition", queryCondition);
        String path = "/background/queryBooks?currentPage=" + currentPage;
        request.getRequestDispatcher(path).forward(request, response);
    }
}

