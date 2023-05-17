package com.example.cuoiki.Response;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private SignUpData data;

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public SignUpData getData() {
        return data;
    }
}
