package com.example.cuoiki.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Categories implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String images;

    @SerializedName("cIdBig")
    private int cIdBig;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getcIdBig() {
        return cIdBig;
    }

    public void setcIdBig(int cIdBig) {
        this.cIdBig = cIdBig;
    }
}
