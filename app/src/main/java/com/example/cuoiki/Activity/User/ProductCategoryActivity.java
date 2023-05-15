package com.example.cuoiki.Activity.User;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuoiki.Adapter.ProductCategoryAdapter;
import com.example.cuoiki.Model.Product;
import com.example.cuoiki.R;
import com.example.cuoiki.Response.ProductResponse;
import com.example.cuoiki.Retrofit.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCategoryActivity extends AppCompatActivity {
    ImageView iv_back;
    String idCategory, CategoryName;

    TextView tv_product;

    RecyclerView rcProduct;

    APIService apiService;

    List<Product> productList;

    ProductCategoryAdapter productCategoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        anhXa();
        idCategory = (String) getIntent().getSerializableExtra("idCategory");
        CategoryName =(String) getIntent().getSerializableExtra("CategoryName");
        LoadProduct();
        Log.e("ffff", idCategory + "====================");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(ProductCategoryActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }

    private void LoadProduct() {
        tv_product.setText("Category: "+ CategoryName);
        APIService.apiSevice2.getProductByCategoryId(Integer.valueOf(idCategory)).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                productList = response.body().getData().getProducts();
                Log.e("ffff", productList.toString());
                productCategoryAdapter=new ProductCategoryAdapter(productList,ProductCategoryActivity.this);
                rcProduct.setHasFixedSize(true);
                GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
                rcProduct.addItemDecoration(new GridSpacingItemDecoration(2, 30, true));
                rcProduct.setLayoutManager(layoutManager);
                rcProduct.setAdapter(productCategoryAdapter);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
            }
        });
    }

    //Tạo khoảng cách cho các item
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private void anhXa() {
        iv_back = findViewById(R.id.iv_back);
        rcProduct = findViewById(R.id.rc_product);
        tv_product = findViewById(R.id.tv_product);
    }
}
