package com.example.cuoiki.Activity.User;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.cuoiki.Adapter.CategoryAdapter;
import com.example.cuoiki.Adapter.ProductAdapter;
import com.example.cuoiki.Response.ProductResponse;
import com.example.cuoiki.Retrofit.APIService;

import com.example.cuoiki.Model.Categories;
import com.example.cuoiki.Model.Product;
import com.example.cuoiki.R;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.RoomDatabase.ProductDatabase;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.Model.User;
import com.example.cuoiki.Response.CategoryResponse;
import com.example.cuoiki.Utils.contants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList, recyclerViewBestSellerList;
    private TextView userName, ivSearch, tvQuantity;
    private EditText etSearch;
    private ImageView avatar;
    APIService apiService;
    private List<Categories> categoriesList;
    private  List<Product> productList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user = SharedPrefManager.getInstance(this).getUser();

        etSearch = findViewById(R.id.etSearch);
        ivSearch = findViewById(R.id.ivSearch);
        tvQuantity = findViewById(R.id.tvQuantity);

        int quantity = ProductDatabase.getInstance(this).productDao().getAll().size();
        tvQuantity.setText(String.valueOf(quantity));

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductSearchActivity.class);
                intent.putExtra("txtSearch", etSearch.getText().toString());
                startActivity(intent);
            }
        });

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            userName = findViewById(R.id.tvHiName);

            avatar = findViewById(R.id.ivAvatar);

            userName.setText("Hi "+ user.getUserName());
            if(!user.getImages().isEmpty()) {
                Glide.with(getApplicationContext()).load(contants.ROOT_URL + "Web/image?fname=" + user.getImages()).into(avatar);
            }
            else{
                Glide.with(getApplicationContext()).load(R.drawable.bottom_profile).into(avatar);
            }
        }
        recyclerViewCategory();
        System.out.println("=================================");
        System.out.println(categoriesList);
        recyclerViewNewProducts();
        recyclerViewBestSeller();
        bottomNavigation();
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cardBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        LinearLayout contactBtn = findViewById(R.id.contactBtn);
        LinearLayout orderBtn = findViewById(R.id.orderBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ContactActivity.class));
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,OrderActivity.class));
            }
        });
    }

    private void recyclerViewNewProducts() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.view2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);
        //Get API
        apiService=RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT).create(APIService.class);
        apiService.getNewProducts().enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(response.isSuccessful()){
                    productList=response.body().getData().getProducts();
                    adapter2 = new ProductAdapter(productList, MainActivity.this);
                    recyclerViewPopularList.setAdapter(adapter2);
                }else{
                    int statusCode=response.code();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("logg",t.getMessage());
            }
        });
    }


    private void recyclerViewBestSeller() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBestSellerList = findViewById(R.id.view3);
        recyclerViewBestSellerList.setLayoutManager(linearLayoutManager);
        //Get API
        apiService=RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT).create(APIService.class);
        apiService.getBestSeller().enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(response.isSuccessful()){
                    productList=response.body().getData().getProducts();
                    adapter2 = new ProductAdapter(productList, MainActivity.this);
                    recyclerViewBestSellerList.setAdapter(adapter2);
                }else{
                    int statusCode=response.code();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("logg",t.getMessage());
            }
        });
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.view1);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
        //Get API
        apiService= RetrofitClient.getInstance().getRetrofit(contants.URL_CATEGORY).create(APIService.class);
        apiService.getCategoryAll().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful()){
                    categoriesList=response.body().getData().getCategories1();
                    adapter = new CategoryAdapter(categoriesList, MainActivity.this);
                    recyclerViewCategoryList.setAdapter(adapter);
                }else{
                    int statusCode= response.code();
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.d("logg",t.getMessage());
            }
        });
    }


}