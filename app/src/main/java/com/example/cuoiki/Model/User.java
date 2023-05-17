package com.example.cuoiki.Model;

import java.io.Serializable;

public class User implements Serializable {
    private int id, role;
    private String userName, fullName, email, address, phone, images;


    public User(int id, int role, String userName, String fullName, String email, String address, String phone, String images) {
        this.id = id;
        this.role = role;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getImages() {
        return images;
    }

    public int getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", images='" + images + '\'' +
                '}';
    }
}
