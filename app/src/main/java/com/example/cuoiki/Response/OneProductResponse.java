package com.example.cuoiki.Response;

import com.google.gson.annotations.SerializedName;

public class OneProductResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private OneProductData data;

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public OneProductData getData() {
        return data;
    }
}
