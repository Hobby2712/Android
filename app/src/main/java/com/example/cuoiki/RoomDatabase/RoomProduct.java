package com.example.cuoiki.RoomDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

@Entity(tableName = "product")
public class RoomProduct implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String image;
    private int quantity;
    private int price;

    public RoomProduct() {
    }

    public RoomProduct(int id, String name, int quantity, int price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public RoomProduct(int id, String name, String image, int quantity, int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String Currency(int price) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);

        String tienvnd = vn.format(price);
        return tienvnd +"đ";
    }
}

