package com.example.cuoiki.RoomDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "product")
public class RoomProduct implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String meal;
    private String strmealthumb;
    private int quantity;
    private double price;

    public RoomProduct() {
    }

    public RoomProduct(int id, String meal, int quantity, double price) {
        this.id = id;
        this.meal = meal;
        this.quantity = quantity;
        this.price = price;
    }

    public RoomProduct(int id, String meal, String strmealthumb, int quantity, double price) {
        this.id = id;
        this.meal = meal;
        this.strmealthumb = strmealthumb;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getStrmealthumb() {
        return strmealthumb;
    }

    public void setStrmealthumb(String strmealthumb) {
        this.strmealthumb = strmealthumb;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

