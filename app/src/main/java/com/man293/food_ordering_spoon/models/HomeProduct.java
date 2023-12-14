package com.man293.food_ordering_spoon.models;

import java.io.Serializable;

public class HomeProduct implements Serializable {
    private String homeProductId, homeProductName, homeProductDescription, homeProductImg, homeProductCateId;
    private double homeProductPrice;

    public HomeProduct(String homeProductId, String homeProductName, String homeProductDescription, double homeProductPrice, String homeProductImg, String homeProductCateId) {
        this.homeProductId = homeProductId;
        this.homeProductName = homeProductName;
        this.homeProductDescription = homeProductDescription;
        this.homeProductPrice = homeProductPrice;
        this.homeProductImg = homeProductImg;
        this.homeProductCateId = homeProductCateId;
    }

    public String getHomeProductName() {
        return homeProductName;
    }

    public void setHomeProductName(String homeProductName) {
        this.homeProductName = homeProductName;
    }

    public String getHomeProductDescription() {
        return homeProductDescription;
    }

    public void setHomeProductDescription(String homeProductDescription) {
        this.homeProductDescription = homeProductDescription;
    }

    public double getHomeProductPrice() {
        return homeProductPrice;
    }

    public void setHomeProductPrice(double homeProductPrice) {
        this.homeProductPrice = homeProductPrice;
    }

    public String getHomeProductImg() { return homeProductImg; }

    public void setHomeProductImg(String homeProductImg) {
        this.homeProductImg = homeProductImg;
    }

    public String getHomeProductId() {
        return homeProductId;
    }

    public void setHomeProductId(String homeProductId) {
        this.homeProductId = homeProductId;
    }

    public String getHomeProductCateId() {
        return homeProductCateId;
    }

    public void setHomeProductCateId(String homeProductCateId) {
        this.homeProductCateId = homeProductCateId;
    }
}
