package com.example.cuoiki.Response;

import com.example.cuoiki.Model.Chart;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChartResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private ChartData data;

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public ChartData getData() {
        return data;
    }

    public class ChartData {
        @SerializedName("chart")
        private List<Chart> chart;

        public List<Chart> getChart() {
            return chart;
        }
    }

}
