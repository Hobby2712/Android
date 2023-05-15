package com.example.cuoiki.Response;

import com.example.cuoiki.Model.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OneProductData {

    @SerializedName("product")
    private Product product;

    public Product get1Product() {
        return product;
    }

}
