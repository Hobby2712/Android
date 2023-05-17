package com.example.cuoiki.Response;

import com.example.cuoiki.Model.Order;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderData {
    @SerializedName("orders")
    private List<Order> orders;


    public List<Order> getOrders() {
        return orders;
    }

}
