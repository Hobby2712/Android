package com.example.cuoiki.Activity.Vendor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cuoiki.R;
import com.example.cuoiki.Response.ChartResponse;
import com.example.cuoiki.Retrofit.APIService;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.Utils.contants;
import com.example.cuoiki.databinding.ActivityVendorManagepBinding;
import com.example.cuoiki.databinding.ActivityVendorStatisticBinding;
import com.example.cuoiki.Model.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongKeActivity extends AppCompatActivity {

    private ActivityVendorStatisticBinding binding;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVendorStatisticBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolBar);
        drawChart();
    }

    private void drawChart(){
        RetrofitClient.getInstance()
                .getRetrofit(contants.URL_PRODUCT2)
                .create(APIService.class)
                .getChart(SharedPrefManager.getInstance(this).getStoreInfo().getId())
                .enqueue(new Callback<ChartResponse>() {
            @Override
            public void onResponse(Call<ChartResponse> call, Response<ChartResponse> response) {
                if (response.isSuccessful()) {
                    List<Chart> chartItems = response.body().getData().getChart();

                    List<Entry> entries = new ArrayList<>();
                    for (Chart item : chartItems) {
                        float month = Float.parseFloat(item.getMonth());
                        float total = Float.parseFloat(item.getTotal());
                        entries.add(new Entry(month, total));
                    }

                    LineDataSet dataSet = new LineDataSet(entries, "Doanh thu theo tháng");
                    dataSet.setColor(Color.BLUE);
                    dataSet.setLineWidth(2f);
                    dataSet.setValueTextSize(12f);

                    LineData lineData = new LineData(dataSet);

                    LineChart chart = findViewById(R.id.chart);
                    chart.setDrawBorders(true);
                    chart.setBorderColor(Color.BLACK);
                    chart.setData(lineData);
                    chart.getDescription().setEnabled(false);
                    chart.animateXY(1000, 1000);
                }
            }

            @Override
            public void onFailure(Call<ChartResponse> call, Throwable t) {
                Log.e( "Chart", "gọi ap thất bại");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuStatistic:
                Toast.makeText(this, "Bạn đang ở mục thống kê", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuProductManage:
                Toast.makeText(this, "Bạn chọn quản lý sản phẩm", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ThongKeActivity.this, ManageProductActivity.class);
                startActivity(intent);
                break;
            case R.id.menuOrderManage:
                Toast.makeText(this, "Bạn đang chọn quản lý đơn hàng", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(ThongKeActivity.this, ManageOrderActivity.class);
                startActivity(intent2);
                break;
            case R.id.menuLogOut:
                Toast.makeText(this, "Bạn chọn đăng xuất", Toast.LENGTH_SHORT).show();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
