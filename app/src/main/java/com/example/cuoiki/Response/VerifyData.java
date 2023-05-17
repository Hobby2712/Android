package com.example.cuoiki.Response;

import com.google.gson.annotations.SerializedName;

public class VerifyData {
    @SerializedName("otp")
    private String otp;

    public String getOtp() {
        return otp;
    }
}
