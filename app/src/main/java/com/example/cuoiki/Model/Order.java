package com.example.cuoiki.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("orderId")
    @Expose
    private int orderId;

    @SerializedName("p")
    @Expose
    private Product p;

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("totalprice")
    @Expose
    private int totalprice;

    @SerializedName("status")
    @Expose
    private int status;

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public Product getP() {
        return p;
    }

    public int getCount() {
        return count;
    }

    public int getTotalprice() {
        return totalprice;
    }


    public int getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setP(Product p) {
        this.p = p;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
