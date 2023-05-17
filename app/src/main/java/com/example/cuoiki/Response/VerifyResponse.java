package com.example.cuoiki.Response;

import com.google.gson.annotations.SerializedName;

public class VerifyResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private VerifyData data;

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public VerifyData getData() {
        return data;
    }
}
