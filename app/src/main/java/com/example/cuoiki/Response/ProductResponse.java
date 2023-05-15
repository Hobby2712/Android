package com.example.cuoiki.Response;

import com.google.gson.annotations.SerializedName;

public class ProductResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private ProductData data;

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public ProductData getData() {
        return data;
    }
}
