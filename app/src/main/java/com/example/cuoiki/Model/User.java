package com.example.cuoiki.Model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String userName , email, address, phone, images;

    public User(int id, String userName, String email, String address, String phone, String images) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getDddress() {
        return address;
    }

    public String getImages() {
        return images;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getUserName() {
        return userName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", images='" + images + '\'' +
                '}';
    }
}
