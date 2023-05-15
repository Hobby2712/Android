package com.example.cuoiki.Response;

import com.example.cuoiki.Model.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductData {
    @SerializedName("product")
    private List<Product> products;


    public List<Product> getProducts() {
        return products;
    }

}
