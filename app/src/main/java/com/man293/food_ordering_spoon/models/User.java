package com.man293.food_ordering_spoon.models;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String address;
    private String password;
    private int role = 0;

    public User() {
    }

    public User(String phoneNum, String password) {
        this.phoneNum = phoneNum;
        this.password = password;
    }

    public User(String id, String firstName, String lastName, String phoneNum, String address, int role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.role = role;
    }

    public User(String id, String firstName, String lastName, String phoneNum, String address, String password, int role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.password = password;
        this.role = role;
    }

    public boolean isAdmin() {
        return this.role == 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }
}
