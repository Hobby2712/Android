package com.example.cuoiki.ShipperFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuoiki.Adapter.LayHangShipperAdapter;
import com.example.cuoiki.Adapter.OrderShipperAdapter;
import com.example.cuoiki.Model.Order;
import com.example.cuoiki.Model.User;
import com.example.cuoiki.R;
import com.example.cuoiki.Response.OrderResponse;
import com.example.cuoiki.Retrofit.APIService;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.Utils.contants;
import com.example.cuoiki.databinding.FragmentNeworderBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickupFragment extends Fragment {
    FragmentNeworderBinding binding;

    private RecyclerView rc_list;
    private OrderShipperAdapter orderAdapter;
    private LayHangShipperAdapter layhangShipperAdapter;
    APIService apiService;
    private List<Order> orderList;
    public PickupFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_neworder, container, false);
            User user = SharedPrefManager.getInstance(getContext()).getUser();

            Log.d("logg",String.valueOf(user.getId()));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rc_list = view.findViewById(R.id.imview);
            rc_list.setLayoutManager(linearLayoutManager);
            //Get API
            apiService= RetrofitClient.getInstance().getRetrofit(contants.URL_SHIPPER).create(APIService.class);
            apiService.getShipperOrders("2").enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    if(response.isSuccessful()){
                        Log.d("logg",String.valueOf(response.body().getData().getOrders().size()));
                        orderList=response.body().getData().getOrders();
                        layhangShipperAdapter = new LayHangShipperAdapter(orderList, getContext());
                        rc_list.setAdapter(layhangShipperAdapter);
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

            return view;
    }
}
