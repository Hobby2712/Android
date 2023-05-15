package com.example.cuoiki.Response;

import com.google.gson.annotations.SerializedName;

public class CategoryResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private CategoryData data;

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public CategoryData getData() {
        return data;
    }
}
