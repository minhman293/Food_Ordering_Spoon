package com.man293.food_ordering_spoon.models;

public class Product {
    private int id;
    private int imageSrc;
    private String name;
    private String desc;
    private double price;
    private int categoryId;

    public Product() {
    }

    public Product(int id, int imageSrc, String name, String desc, double price, int categoryId) {
        this.id = id;
        this.imageSrc = imageSrc;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(int imageSrc) {
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
