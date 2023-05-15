package com.example.cuoiki.Response;

import com.google.gson.annotations.SerializedName;

public class ProductsResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private ProductsData data;

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public ProductsData getData() {
        return data;
    }
}
