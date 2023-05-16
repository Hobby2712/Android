package com.example.cuoiki.Response;

import com.google.gson.annotations.SerializedName;

public class Category2Response {
    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private Category2Data data;

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public Category2Data getData() {
        return data;
    }
}
