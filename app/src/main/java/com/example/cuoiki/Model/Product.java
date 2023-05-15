package com.example.cuoiki.Model;

import android.util.Log;

import com.example.cuoiki.Retrofit.APIService;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.Utils.contants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product implements Serializable {
    APIService apiService;

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("cateId")
    @Expose
    private int cateId;

    @SerializedName("storeId")
    @Expose
    private int storeId;

    @SerializedName("sold")
    @Expose
    private int sold;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCateId() {
        return cateId;
    }

    public int getStoreId() {
        return storeId;
    }

    public int getSold() {
        return sold;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String Currency(int price) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);

        String tienvnd = vn.format(price);
        return tienvnd +"đ";
    }
}
