package com.man293.food_ordering_spoon.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String imageSrc;
    private String name;
    private String desc;
    private double price;
    private String categoryId;

    public Product() {
    }

    public Product(String id, String imageSrc, String name, String desc, double price, String categoryId) {
        this.id = id;
        this.imageSrc = imageSrc;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
