package com.man293.food_ordering_spoon.models;

import org.threeten.bp.LocalDateTime;
import java.util.Date;

public class OrderItem {
    private String id;
    private String title;
    private String imageSrc;
    private LocalDateTime orderDate;
    private Double price;

    public OrderItem() {
    }

    public OrderItem(String id, String title, String imageSrc, LocalDateTime orderDate, Double price) {
        this.id = id;
        this.title = title;
        this.imageSrc = imageSrc;
        this.orderDate = orderDate;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                ", orderDate=" + orderDate +
                ", price=" + price +
                '}';
    }
}
