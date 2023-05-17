package com.example.cuoiki.Response;

import com.google.gson.annotations.SerializedName;

public class OrderResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private OrderData data;

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public OrderData getData() {
        return data;
    }
}
