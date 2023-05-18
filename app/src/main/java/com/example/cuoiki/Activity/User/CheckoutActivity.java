package com.example.cuoiki.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cuoiki.Adapter.CheckoutAdapter;
import com.example.cuoiki.Adapter.ProductCartAdapter;
import com.example.cuoiki.Model.User;
import com.example.cuoiki.R;
import com.example.cuoiki.Response.VerifyResponse;
import com.example.cuoiki.Retrofit.APIService;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.RoomDatabase.ProductDatabase;
import com.example.cuoiki.RoomDatabase.RoomProduct;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.Utils.contants;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    private String TAG = CheckoutActivity.class.getSimpleName();
    private AppCompatButton appCompatButton;
    private EditText etName, etAddress, etPhone;
    private ImageView iv_back;
    private TextView tvTotalPrice;
    private RecyclerView rc_list;
    private CheckoutAdapter checkoutAdapter;
    private List<RoomProduct> productList;

    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        AnhXa();
        User user = SharedPrefManager.getInstance(this).getUser();
        etName.setText(user.getFullName());
        etAddress.setText(user.getAddress());
        etPhone.setText(user.getPhone());

        productList = new ArrayList<>();
        checkoutAdapter = new CheckoutAdapter(productList, this);
        loadData();
        rc_list.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rc_list.setLayoutManager(linearLayoutManager);
        rc_list.setAdapter(checkoutAdapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CheckoutActivity.this, CartActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrder();
                ProductDatabase.getInstance(CheckoutActivity.this).productDao().deleteAll();
                Intent intent1 = new Intent(CheckoutActivity.this, OrderActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }



    private void loadData() {
        //lấy danh sách product trong Room
        productList = ProductDatabase.getInstance(this).productDao().getAll();
        checkoutAdapter.setData(productList);
        int t = 0;
        for (RoomProduct i : productList) {
            if (i != null) {
                t += i.getQuantity() * i.getPrice();
            }
        }
        if (t > 0) {
            tvTotalPrice.setText(Currency(t));
        } else {
            tvTotalPrice.setText("0đ");
        }
    }

    public String Currency(int price) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);

        String tienvnd = vn.format(price);
        return tienvnd +"đ";
    }

    public void AnhXa() {
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);

        rc_list = (RecyclerView) findViewById(R.id.imview);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        iv_back = findViewById(R.id.iv_back);
        appCompatButton = findViewById(R.id.appCompatButton);
    }

    public void addOrder(){
        User user = SharedPrefManager.getInstance(this).getUser();

        apiService = RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
        apiService.addOrder(user.getId(), etName.toString(),etPhone.toString(),etAddress.toString()).enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                try {
                    VerifyResponse signUpResponse = response.body();
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<VerifyResponse> call, Throwable t) {
                t.printStackTrace();
                //Response failed
                Log.e(TAG, "error: " + t.getMessage());
            }
        });
    }

}
