package com.domain;

import java.io.Serializable;

public class Book implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String isbn;
	private String name;
	private float price;
	private int pnum;
	private String category;
	private String description;
	private String bookCoverPath;
	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBookCoverPath() {
		return bookCoverPath;
	}
	public void setBookCoverPath(String bookCoverPath) {
		this.bookCoverPath = bookCoverPath;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", name=" + name + ", price=" + price
				+ ", pnum=" + pnum + ", category=" + category
				+ ", description=" + description + ", bookCoverPath="
				+ bookCoverPath + "]";
	}
		
}
