package com.example.cuoiki.Activity.Vendor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuoiki.Activity.User.CartActivity;
import com.example.cuoiki.Activity.User.MainActivity;
import com.example.cuoiki.Adapter.ProductAdapter;
import com.example.cuoiki.Adapter.ProductStoreAdapter;
import com.example.cuoiki.Model.Product;
import com.example.cuoiki.Model.Store;
import com.example.cuoiki.R;
import com.example.cuoiki.Response.OneProductResponse;
import com.example.cuoiki.Response.ProductResponse;
import com.example.cuoiki.Response.StoreResponse;
import com.example.cuoiki.Retrofit.APIService;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.RoomDatabase.ProductDatabase;
import com.example.cuoiki.RoomDatabase.RoomProduct;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.Utils.contants;
import com.example.cuoiki.databinding.ActivityOrderBinding;
import com.example.cuoiki.databinding.ActivityVendorManagepBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageProductActivity extends AppCompatActivity {

    private ActivityVendorManagepBinding binding;
    private RecyclerView listStoreProducts;
    private ProductStoreAdapter adapter;
    int storeId = -1;
    private List<Product> productList;
    Button btnAddProduct;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVendorManagepBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolBar);

        if(SharedPrefManager.getInstance(this).getStoreInfo() == null){
            // Hiển thị thông báo lỗi hoặc thực hiện các xử lý khác
            getStore(SharedPrefManager.getInstance(this).getUser().getId());
            Log.e("manager", "null");
        } else{
            recyclerViewStoreProducts(SharedPrefManager.getInstance(this).getStoreInfo().getId());
        }
        btnAddProduct = findViewById(R.id.managerAddProductBtn);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageProductActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getStore(int userId){
        APIService apiService = RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
        apiService.getStoreInfoByUserId(userId).enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, retrofit2.Response<StoreResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().isError()){
                        Store store = response.body().getData().getStore();
                        SharedPrefManager.getInstance(getApplicationContext()).saveStoreInfo(store);
                        storeId = SharedPrefManager.getInstance(getApplicationContext()).getStoreInfo().getId();
                        Log.e("Store id:", SharedPrefManager.getInstance(getApplicationContext()).getStoreInfo().getId() + "====================");
                        recyclerViewStoreProducts(storeId);
                    }

                }
            }

            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.d("store api fail",t.getMessage());
            }
        });
    }

    private void recyclerViewStoreProducts(int storeId) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listStoreProducts = findViewById(R.id.rv_store_products);
        listStoreProducts.setLayoutManager(linearLayoutManager);
        //Get API
        RetrofitClient.getInstance()
                .getRetrofit(contants.URL_PRODUCT2)
                .create(APIService.class)
                .getProductByStoreId(storeId)
                .enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(response.isSuccessful()){
                    productList = response.body().getData().getProducts();
                    adapter = new ProductStoreAdapter(productList, ManageProductActivity.this, new ProductStoreAdapter.iClickListener() {
                                @Override
                                public void deleteProduct(long productId) {
                                    clickDeleteProduct(productId);
                                }
                            });
                    listStoreProducts.setAdapter(adapter);
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

    private void clickDeleteProduct(long productId) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete product")
                .setMessage("Are you sure")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RetrofitClient.getInstance()
                                .getRetrofit(contants.URL_PRODUCT2)
                                .create(APIService.class)
                                .deleteProductById(productId)
                                .enqueue(new Callback<OneProductResponse>() {
                                    @Override
                                    public void onResponse(Call<OneProductResponse> call, Response<OneProductResponse> response) {
                                        if(response.isSuccessful()){
                                            if(!response.body().isError()){
                                                Toast.makeText(ManageProductActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                recyclerViewStoreProducts(SharedPrefManager.getInstance(getApplicationContext()).getStoreInfo().getId());
                                            } else{
                                                Toast.makeText(ManageProductActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<OneProductResponse> call, Throwable t) {
                                        Toast.makeText(ManageProductActivity.this, "Gọi API thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .setNegativeButton("No", null)
                .show();
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
                Toast.makeText(this, "Bạn đang chọn thống kê", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManageProductActivity.this, ThongKeActivity.class);
                startActivity(intent);
                break;
            case R.id.menuProductManage:
                Toast.makeText(this, "Bạn đang ở mục quản lý sản phẩm", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuOrderManage:
                Toast.makeText(this, "Bạn đang chọn quản lý đơn hàng", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(ManageProductActivity.this, ManageOrderActivity.class);
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
