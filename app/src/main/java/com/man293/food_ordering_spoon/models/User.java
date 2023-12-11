package com.man293.food_ordering_spoon.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.SendTokenToServerTask;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String address;
    private String password;
    private String picture;
    private int role = 0;

    public User() {
    }

    public User(String phoneNum, String password) {
        this.phoneNum = phoneNum;
        this.password = password;
    }

    public User(String id, String firstName, String lastName, String phoneNum, String address, int role, String picture) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.role = role;
        this.picture = picture;
    }

    public User(String id, String firstName, String lastName, String phoneNum, String address, String password, int role, String picture) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.password = password;
        this.role = role;
        this.picture = picture;
    }
    public static User getCurrentUser(Context context) {
        if(context == null) return  null;
        SharedPreferences sharedPreferences = context.getSharedPreferences("auth_info", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("current_user", null );
        if(userJson != null) {
            Gson gson = new Gson();
            return gson.fromJson(userJson, User.class);
        }
        return  null;
    }

    public static boolean removeCurrentUser(Context context) {
        try {
            if(context == null) return false;
            SharedPreferences fbTokenShare = context.getSharedPreferences("firebase_token", Context.MODE_PRIVATE);
            String token = fbTokenShare.getString("fb_token", null);
            User currentUser= User.getCurrentUser(context);
            if(currentUser != null ) {
                if(currentUser.isAdmin()  && token != null) {
                    Map<String, Object> payload = new HashMap<>();
                    payload.put("_token", token);
                    String json = new Gson().toJson(payload);
                    String userId = currentUser.getId();
                    String url = context.getString(R.string.BASE_URL) + context.getString(R.string.API_REMOVE_TOKEN__POST, userId);
                    new SendTokenToServerTask(json).execute(url);
                }
                SharedPreferences sharedPreferences = context.getSharedPreferences("auth_info", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("current_user");
                editor.apply();
                Log.i("AFTER_LOGOUT", "CURRENT_USER: " + sharedPreferences.getString("current_user", "Nothing!"));
            }
            return true;
        }catch (Exception ex) {
            Log.e("LOGOUT", ex.getMessage());
            return false;
        }
    }

    public static User saveCurrentUser( Context context ,User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("auth_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        editor.putString("current_user", userJson);
        editor.apply();
        return user;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", picture='" + picture + '\'' +
                ", role=" + role +
                '}';
    }
}