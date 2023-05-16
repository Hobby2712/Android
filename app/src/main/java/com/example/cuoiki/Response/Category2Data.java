package com.example.cuoiki.Response;

import com.example.cuoiki.Model.Categories;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category2Data {
    @SerializedName("categories2")
    private List<Categories> categories2;

    public List<Categories> getCategories2() {
        return categories2;
    }
}
