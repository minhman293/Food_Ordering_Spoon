package com.man293.food_ordering_spoon.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class History implements Serializable {

    private String orderID, orderDate, orderAdd;
    private Double price;
    private List<ProductHistory> listProduct;

    public History() {

    }

    public History(String orderID, String orderDate, Double price, List<ProductHistory> listProduct, String orderAdd){
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.price = price;
        this.listProduct = listProduct;
        this.orderAdd = orderAdd;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ArrayList<ProductHistory> getListProduct() {
        return (ArrayList<ProductHistory>) listProduct;
    }

    public void setListProduct(ArrayList<ProductHistory> listProduct) {
        this.listProduct = listProduct;
    }

    public String getOrderAdd() {
        return orderAdd;
    }

    public void setOrderAdd(String orderAdd) {
        this.orderAdd = orderAdd;
    }
}
