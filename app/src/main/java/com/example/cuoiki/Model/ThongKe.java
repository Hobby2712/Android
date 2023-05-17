package com.example.cuoiki.Model;

import com.google.gson.annotations.SerializedName;

import java.text.NumberFormat;
import java.util.Locale;

public class ThongKe {

    @SerializedName("uName")
    private String uName;
    @SerializedName("uAddress")
    private String uAddress;
    @SerializedName("pId")
    private int pId;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("creatDate")
    private String createdDate;
    @SerializedName("total")
    private int total;


    public ThongKe() {
        super();
    }


    public ThongKe(String uName, String uAddress, int pId, int quantity, String createdDate, int total) {
        this.uName = uName;
        this.uAddress = uAddress;
        this.pId = pId;
        this.quantity = quantity;
        this.createdDate = createdDate;
        this.total = total;
    }


    public String getuName() {
        return uName;
    }


    public void setuName(String uName) {
        this.uName = uName;
    }


    public String getuAddress() {
        return uAddress;
    }


    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }


    public int getpId() {
        return pId;
    }


    public void setpId(int pId) {
        this.pId = pId;
    }


    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public String getCreatedDate() {
        return createdDate;
    }


    public void setCreatDate(String createdDate) {
        this.createdDate = createdDate;
    }


    public int getTotal() {
        return total;
    }


    public void setTotal(int total) {
        this.total = total;
    }


    @Override
    public String toString() {
        return "ThongKe [uName=" + uName + ", uAddress=" + uAddress + ", pId=" + pId + ", quantity=" + quantity
                + ", creatDate=" + createdDate + ", total=" + total + "]";
    }

    public static String Currency(int price) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);

        String tienvnd = vn.format(price);
        return tienvnd +"đ";
    }
}
