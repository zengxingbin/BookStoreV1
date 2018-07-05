package com.domain;

public class QueryCondition {
	private String bookIsbn;
	private String name;
	private String category;
	private String minPrice;
	private String maxPrice;
	
	
	public String getBookIsbn() {
		return bookIsbn;
	}
	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	public String getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	
	@Override
	public String toString() {
		return "QueryCondition [bookIsbn=" + bookIsbn + ", name=" + name
				+ ", category=" + category + ", minPrice=" + minPrice
				+ ", maxPrice=" + maxPrice + "]";
	}	
		
}
