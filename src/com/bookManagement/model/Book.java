package com.bookManagement.model;

public class Book {
  protected int id;
  protected String title;
  protected String author;
  protected float price;
  public Book(int id) {
	  this.id = id;
  }
  public Book(String title,String author,float price) {
	  this.title = title;
	  this.author = author;
	  this.price = price;
  }
  public Book(int id,String title,String author,float price) {
	  this(title,author,price);
	  this.id = id;
  }
  public int getId() {
	  return this.id;
  }
  public void setId(int id) {
	  this.id = id;
  }
  public String getTitle() {
	  return this.title;
  }
  public void setTitle(String title) {
	  this.title = title;
  }
  public String getAuthor() {
	  return this.author;
  }
  public void setAuthor(String author) {
	  this.author = author;
  }
  public float getPrice() {
	  return this.price;
  }
  public void setPrice(float price) {
	  this.price = price;
  }
}
