package com.man293.food_ordering_spoon.models;

public class OrderDetailItem {
    private String title, qtt;
    private Double price;

    public OrderDetailItem(){

    }

    public OrderDetailItem(String title, String qtt, Double price) {
        this.title = title;
        this.qtt = qtt;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQtt() {
        return qtt;
    }

    public void setQtt(String qtt) {
        this.qtt = qtt;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
