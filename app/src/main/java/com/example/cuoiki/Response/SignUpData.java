package com.example.cuoiki.Response;

import com.example.cuoiki.Model.User;
import com.google.gson.annotations.SerializedName;

public class SignUpData {
    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }
}
