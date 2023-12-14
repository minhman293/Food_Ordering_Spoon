package com.man293.food_ordering_spoon.models;

import java.util.ArrayList;
import java.util.List;

public class OrderDetail {
    private String firstName, lastName, orderDate, orderID, phoneNum, address;
    private Double price;
    private List<OrderDetailItem> listItem;

    public OrderDetail() {

    }

    public OrderDetail(String firstName, String lastName, Double price, String orderDate, String orderID, String phoneNum, String address, List<OrderDetailItem> listItem) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.price = price;
        this.orderDate = orderDate;
        this.orderID = orderID;
        this.phoneNum = phoneNum;
        this.address = address;
        this.listItem = listItem;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ArrayList<OrderDetailItem> getListItem() {
        return (ArrayList<OrderDetailItem>) listItem;
    }

    public void setListItem(ArrayList<OrderDetailItem> listItem) {
        this.listItem = listItem;
    }
}
