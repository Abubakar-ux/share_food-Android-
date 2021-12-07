package com.abubakar.share_food;

public class User {
    String id;
    String email;
    String name;
    String address;
    String city;
    String phoneNo;
    String dp;

    public User(String id, String email, String name, String gender, String phoneNo, String bio, String dp) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.city = city;
        this.dp=dp;
    }
    public User(){

    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String bio) {
        this.city = city;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}

