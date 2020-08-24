package com.bookManagement.controller;
import com.bookManagement.dao.BookDao;
import com.bookManagement.model.Book;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BookDao bookDao;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init() {
    	String jdbcURL = getServletContext().getInitParameter("jdbcURL");
    	String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
    	String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
    	
    	bookDao = new BookDao(jdbcURL,jdbcUsername,jdbcPassword);
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getServletPath();
		try {
			switch(action) {
			case "/new":
				showNewFrom(request,response);
				break;
			case "/insert":
				insertBook(request,response);
				break;
			case "/delete":
				deleteBook(request,response);
				break;
			case "/edit":
				showEditFrom(request,response);
				break;
			case "/update":
				updateBook(request,response);
				break;
			default:
				System.out.println("default");
				listBook(request,response);
			}
		}catch(SQLException e) {
			throw new ServletException(e);
		}
	}
    private void listBook(HttpServletRequest request,HttpServletResponse response )
    		throws SQLException,IOException,ServletException {
    	List<Book> listBook = bookDao.listAllBooks();
    	request.setAttribute("listBook", listBook);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
    	dispatcher.forward(request, response);
    }
    private void showNewFrom(HttpServletRequest request,HttpServletResponse response)
            throws SQLException,IOException,ServletException{
    	RequestDispatcher dispatcher = request.getRequestDispatcher("BookFrom.jsp");
    	dispatcher.forward(request, response);	
    }
    private void showEditFrom(HttpServletRequest request,HttpServletResponse response)
            throws SQLException,ServletException,IOException{
    	int id = Integer.parseInt(request.getParameter("id"));
    	Book existingBook = bookDao.getBook(id);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("BookFrom.jsp");
    	request.setAttribute("book", existingBook);
    	dispatcher.forward(request, response);
    }
    private void insertBook(HttpServletRequest request,HttpServletResponse response)
            throws SQLException,IOException{
    	String title = request.getParameter("title");
    	String author = request.getParameter("author");
    	float price = Float.parseFloat(request.getParameter("price"));
    	Book newbook = new Book(title,author,price);
    	bookDao.insertBook(newbook);
    	response.sendRedirect("list");
    }
    private void updateBook(HttpServletRequest request,HttpServletResponse response)
            throws IOException,SQLException{
    	int id = Integer.parseInt(request.getParameter("id"));
    	String title = request.getParameter("title");
    	String author = request.getParameter("author");
    	float price = Float.parseFloat(request.getParameter("price"));
    	Book book = new Book(id,title,author,price);
    	bookDao.updateBook(book);
    	response.sendRedirect("list");
    }
    private void deleteBook(HttpServletRequest request,HttpServletResponse response)
    throws SQLException,IOException{
    	int id = Integer.parseInt(request.getParameter("id"));
    	Book book = new Book(id);
    	bookDao.deleteBook(book);
    	response.sendRedirect("list");
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
