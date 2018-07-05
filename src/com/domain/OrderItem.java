package com.domain;

public class OrderItem {
    private Order order;    // 订单
    private Book book;      // 商品
    private int buynum;     // 购物数量
    
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public int getBuynum() {
        return buynum;
    }
    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }
    
}
