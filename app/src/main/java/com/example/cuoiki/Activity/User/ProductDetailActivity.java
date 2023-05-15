package com.example.cuoiki.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cuoiki.Model.Product;
import com.example.cuoiki.R;
import com.example.cuoiki.Retrofit.APIService;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.RoomDatabase.ProductDatabase;
import com.example.cuoiki.RoomDatabase.RoomProduct;
import com.example.cuoiki.Response.ProductResponse;
import com.example.cuoiki.Utils.contants;


import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    String productId;
    TextView txtpName, txtpPrice, pIntructions, txtpQuantity, btnTang, btnGiam, btnAdd;
    ImageView pImage, tvBackHome;
    APIService apiService;
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        anhxa();
        loadProductDetails();
        tvBackHome = findViewById(R.id.ivBackHome);
        tvBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(ProductDetailActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
        btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soluong = txtpQuantity.getText().toString();
                int quantity = Integer.valueOf(soluong)+1;
                txtpQuantity.setText(String.valueOf(quantity));
            }
        });
        btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soluong = txtpQuantity.getText().toString();
                int quantity = Integer.valueOf(soluong);
                if(quantity<=1){
                    return;
                }
                quantity = quantity-1;
                txtpQuantity.setText(String.valueOf(quantity));
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });
    }

    private void anhxa() {
        txtpName = findViewById(R.id.tvTitle);
        txtpPrice = findViewById(R.id.tvPrice);
        pIntructions = findViewById(R.id.tvDescription);
        pImage = findViewById(R.id.imageProduct);
        btnGiam = findViewById(R.id.btn_Giam);
        btnTang = findViewById(R.id.btn_Tang);
        btnAdd = findViewById(R.id.tvAddToCart);
        txtpQuantity = findViewById(R.id.txt_pQuantity);
    }

    private void loadProductDetails() {
        productId = (String) getIntent().getSerializableExtra("id");
        Log.e("Product detail id:", productId + "====================");

        apiService= RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
        apiService.getProductById(productId).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(response.isSuccessful()){
                    product = response.body().getData().getProducts().get(0);
                    Log.e("Product detail id:", product.getName() + "====================");
                    txtpName.setText(product.getName());
                    txtpPrice.setText(product.getPrice());
                    txtpQuantity.setText("1");
                    pIntructions.setText(product.getDescription());
                    Glide.with(getApplicationContext()).load("http://192.168.0.103:8080/Web"+product.getImage()).into(pImage);
                }
            }


            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("logg fail",t.getMessage());
            }
        });
    }
    private void addProduct(){
        String pName = txtpName.getText().toString().trim();
        String pPrice = txtpPrice.getText().toString().trim();
        String pQuantity = txtpQuantity.getText().toString().trim();
        String image = product.getImage();
        Log.d(pQuantity, "addProduct: ");

        int quantity = Integer.valueOf(pQuantity);
        double price = Double.valueOf(pPrice);

        if(TextUtils.isEmpty(pName) || TextUtils.isEmpty(pQuantity)){
            return;
        }
        productId = (String) getIntent().getSerializableExtra("id");

        RoomProduct products = new RoomProduct(Integer.parseInt (productId), pName,image,quantity, price);
        int check = isCheckExist(products);
        if(check>0){
            Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();
            products.setQuantity(check+quantity);
            ProductDatabase.getInstance(this).productDao().update(products);
            return;
        }
        //add vào room
        ProductDatabase.getInstance(this).productDao().insertAll(products);
        Toast.makeText(this, "Thêm product thành công", Toast.LENGTH_SHORT).show();
    }
    private int isCheckExist(@NotNull RoomProduct product){
        RoomProduct list = ProductDatabase.getInstance(this).productDao().checkProduct(product.getId());
        if(list!=null){
            return list.getQuantity();
        }
        return 0;
    }
}
