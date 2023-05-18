package com.example.cuoiki.Activity.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cuoiki.Adapter.ProductCartAdapter;
import com.example.cuoiki.Model.Product;
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

public class CartActivity extends AppCompatActivity {
    private String TAG = CartActivity.class.getSimpleName();
    private AppCompatButton appCompatButton;
    private ImageView iv_back;
    private TextView tvTotalPrice;
    private RecyclerView rc_list;
    private ProductCartAdapter productCartAdapter;
    private List<RoomProduct> productList;

    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        AnhXa();
        productList = new ArrayList<>();
        productCartAdapter = new ProductCartAdapter(productList, this, new ProductCartAdapter.iClickListener() {
            @Override
            public void plusQuantity(RoomProduct product) {
                clickplusQuantity(product);
            }

            @Override
            public void minusQuantity(RoomProduct product) {
                clickminusQuantity(product);
            }

            @Override
            public void deleteProduct(RoomProduct product) {
                clickDeleteProduct(product);
            }
        });
        loadData();
        rc_list.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rc_list.setLayoutManager(linearLayoutManager);
        rc_list.setAdapter(productCartAdapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void deleteAll() {
        ProductDatabase.getInstance(this).productDao().deleteAll();
        loadData();
    }

    private void clickplusQuantity(RoomProduct product) {
        product.setQuantity(product.getQuantity() + 1);
        ProductDatabase.getInstance(this).productDao().update(product);
        loadData();
    }

    private void clickminusQuantity(RoomProduct product) {
        int quantity = product.getQuantity();
        if (quantity <= 1) {
            clickDeleteProduct(product);
            return;
        }
        product.setQuantity(quantity - 1);
        ProductDatabase.getInstance(this).productDao().update(product);
        loadData();
    }

    private void clickDeleteProduct(RoomProduct product) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete product")
                .setMessage("Are you sure")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProductDatabase.getInstance(CartActivity.this).productDao().delete(product);
                        Toast.makeText(CartActivity.this, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    private void loadData() {
        //lấy danh sách product trong Room
        productList = ProductDatabase.getInstance(this).productDao().getAll();
        productCartAdapter.setData(productList);
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
        rc_list = (RecyclerView) findViewById(R.id.imview);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        iv_back = findViewById(R.id.iv_back);
        appCompatButton = findViewById(R.id.appCompatButton);
    }


    public void addToCart(){
        User user = SharedPrefManager.getInstance(this).getUser();

        for (RoomProduct i : productList) {
            APIService apiService1 = RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
            apiService1.insertCart(user.getId(),i.getId(),i.getQuantity()).enqueue(new Callback<VerifyResponse>() {
                @Override
                public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                    try {
                        VerifyResponse signUpResponse = response.body();
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Thanh toán", Toast.LENGTH_SHORT).show();
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



}
