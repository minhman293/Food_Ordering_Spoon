package com.man293.food_ordering_spoon.models;

public class ProductHistory {
    private String id;
    private String imageSrc;
    private String name;
    private String qtt;
    private Double price;

    public ProductHistory() {
    }

    public ProductHistory(String id, String imageSrc, String name,  Double price, String qtt) {
        this.id = id;
        this.imageSrc = imageSrc;
        this.name = name;
        this.price = price;
        this.qtt = qtt;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getQtt() {
        return qtt;
    }

    public void setQtt(String qtt) {
        this.qtt = qtt;
    }
}
