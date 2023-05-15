package com.example.cuoiki.Model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String userName , email, gender, images;

    public User(int id, String name, String email, String gender, String images) {
        this.id = id;
        this.userName = name;
        this.email = email;
        this.gender = gender;
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

    public String getGender() {
        return gender;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", images='" + images + '\'' +
                '}';
    }
}
