package com.example.cuoiki.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Store implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("isActive")
    @Expose
    private int isActive;

    public Store() {
    }

    public Store(int id, String name, String createdDate, int isActive) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
