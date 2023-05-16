package com.example.cuoiki.Response;

import com.example.cuoiki.Model.Store;
import com.google.gson.annotations.SerializedName;

public class StoreData {
    @SerializedName("store")
    private Store store;

    public Store getStore() {
        return store;
    }
}
