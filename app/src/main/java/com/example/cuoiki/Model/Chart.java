package com.example.cuoiki.Model;

import com.google.gson.annotations.SerializedName;

public class Chart {
    @SerializedName("month")
    private String month;
    @SerializedName("total")
    private String total;

    public Chart(String month, String total) {
        this.month = month;
        this.total = total;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMonth() {
        return month;
    }

    public String getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Chart{" +
                "month='" + month + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
