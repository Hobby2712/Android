package com.example.cuoiki.Activity.Vendor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageProductActivity extends AppCompatActivity {

    private RecyclerView listStoreProducts;
    private ProductStoreAdapter adapter;
    int storeId = -1;
    private List<Product> productList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_managep);
        if(SharedPrefManager.getInstance(this).getStoreInfo() == null){
            // Hiển thị thông báo lỗi hoặc thực hiện các xử lý khác
            getStore(SharedPrefManager.getInstance(this).getUser().getId());
            Log.e("manager", "null");
        } else{
            recyclerViewStoreProducts(SharedPrefManager.getInstance(this).getStoreInfo().getId());
        }
        //if(store.getId() != -1){

//                Log.e("manager", "storeid: "+store.getId());
//                Log.e("manager", "storeid: "+SharedPrefManager.getInstance(this).getStoreInfo().getId());
        //}
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
}
