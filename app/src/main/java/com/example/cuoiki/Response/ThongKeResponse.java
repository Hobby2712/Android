package com.example.cuoiki.Response;

import com.example.cuoiki.Model.Chart;
import com.example.cuoiki.Model.ThongKe;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ThongKeResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private ThongKeData data;

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public ThongKeData getData() {
        return data;
    }

    public class ThongKeData {
        @SerializedName("statistic")
        private List<ThongKe> statistic;

        public List<ThongKe> getStatistic() {
            return statistic;
        }
    }

}
