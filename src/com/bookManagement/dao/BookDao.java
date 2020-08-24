package com.bookManagement.dao;
import com.bookManagement.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class BookDao {
 private String jdbcURL;
 private String jdbcUsername;
 private String jdbcPassword;
 private Connection jdbcConnection;
 public BookDao(String jdbcURL,String jdbcUsername,String jdbcPassword) {
	 this.jdbcURL = jdbcURL;
	 this.jdbcUsername = jdbcUsername;
	 this.jdbcPassword = jdbcPassword;
 }
 protected void connect() throws SQLException{
	 if(jdbcConnection == null || jdbcConnection.isClosed()) {
		 try {
			 Class.forName("com.mysql.jdbc.Driver");
		 }catch(ClassNotFoundException e) {
			 throw new SQLException(e);
		 }
		 jdbcConnection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
		 
	 }
 }
 protected void disconnect() throws SQLException{
	 if(jdbcConnection != null || !jdbcConnection.isClosed()) {
		 jdbcConnection.close();
	 }
 }
 public boolean insertBook(Book book) throws SQLException{
	 String sql = "INSERT INTO book (title,author,price) VALUES (?,?,?)";
	 this.connect();
	 
	 PreparedStatement statement = jdbcConnection.prepareStatement(sql);
	 statement.setString(1, book.getTitle());
	 statement.setString(2, book.getAuthor());
	 statement.setFloat(3, book.getPrice());
	 
	 boolean rowInserted = statement.executeUpdate() > 0;
	 statement.close();
	 this.disconnect();
	 return rowInserted;
	 
 }
 public List<Book> listAllBooks() throws SQLException{
	 List<Book> listBook = new ArrayList<Book>();
	 String sql = "SELECT * FROM book";
	 this.connect();
	 Statement statement = jdbcConnection.createStatement();
	 ResultSet resultset = statement.executeQuery(sql);
	 while(resultset.next()) {
		 int id = resultset.getInt("book_id");
		 //System.out.println(id);
		 String title = resultset.getString("title");
		 String author = resultset.getString("author");
         float price = resultset.getFloat("price");
          
         Book book = new Book(id, title, author, price);
         listBook.add(book);
	 }
	 resultset.close();
	 statement.close();
	 disconnect();
	 return listBook;
 }
 public Book getBook(int id) throws SQLException{
	 Book book = null;
	 String sql = "SELECT * FROM book WHERE book_id = ?";
	 this.connect();
	 
	 PreparedStatement statement = jdbcConnection.prepareStatement(sql);
	 statement.setInt(1, id);
	 
	 ResultSet resultSet = statement.executeQuery();
	 if(resultSet.next()) {
		 String title = resultSet.getString("title");
		 String author = resultSet.getString("author");
		 float price = resultSet.getFloat("price");
		 
		 book = new Book(id,title,author,price);
	 }
	 resultSet.close();
	 statement.close();
	 return book;
 }
 public boolean deleteBook(Book book) throws SQLException{
	 String sql = "DELETE FROM book WHERE book_id = ?";
	 connect();
	 PreparedStatement statement = jdbcConnection.prepareStatement(sql);
	 statement.setInt(1, book.getId());
	 boolean rowDeleted = statement.executeUpdate() > 0;
	 statement.close();
	 disconnect();
	 return rowDeleted;
 }
 public boolean updateBook(Book book) throws SQLException{
	 String sql = "UPDATE book SET title = ?,author = ?,price = ? WHERE book_id = ?";
	 connect();
	 PreparedStatement statement = jdbcConnection.prepareStatement(sql);
	 statement.setString(1, book.getTitle());
	 statement.setString(2, book.getAuthor());
	 statement.setFloat(3, book.getPrice());
	 statement.setInt(4, book.getId());
	 boolean rowUpdate = statement.executeUpdate() > 0;
	 statement.close();
	 disconnect();
	 return rowUpdate;
 }
}
