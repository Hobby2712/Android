package com.example.cuoiki.Activity.User;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuoiki.Adapter.OrderAdapter;
import com.example.cuoiki.Model.Order;
import com.example.cuoiki.Model.User;
import com.example.cuoiki.R;
import com.example.cuoiki.Response.OrderResponse;
import com.example.cuoiki.Retrofit.APIService;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.Utils.contants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TesttttttttttActivity extends AppCompatActivity {
    private RecyclerView rc_list;
    private OrderAdapter orderAdapter;
    APIService apiService;
    private List<Order> orderList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_neworder);
        User user = SharedPrefManager.getInstance(TesttttttttttActivity.this).getUser();
        Log.d("logg",String.valueOf(user.getId()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TesttttttttttActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rc_list = findViewById(R.id.imview);
        rc_list.setLayoutManager(linearLayoutManager);
        //Get API
        apiService= RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT).create(APIService.class);
        apiService.getOrders(String.valueOf(user.getId())).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if(response.isSuccessful()){
                    Log.d("logg",String.valueOf(response.body().getData().getOrders().size()));
                    orderList=response.body().getData().getOrders();
                    orderAdapter = new OrderAdapter(orderList, TesttttttttttActivity.this);
                    rc_list.setAdapter(orderAdapter);
                }else{
                    Log.d("logg","Lỗi");
                    int statusCode=response.code();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.d("logg",t.getMessage());
            }
        });
    }



}
