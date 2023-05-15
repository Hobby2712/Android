package com.example.cuoiki.Response;

import com.example.cuoiki.Model.Categories;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryData {
    @SerializedName("categories1")
    private List<Categories> categories1;

    public List<Categories> getCategories1() {
        return categories1;
    }
}
