package com.example.cuoiki.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cuoiki.Adapter.CheckoutAdapter;
import com.example.cuoiki.Adapter.ProductCartAdapter;
import com.example.cuoiki.Model.User;
import com.example.cuoiki.R;
import com.example.cuoiki.RoomDatabase.ProductDatabase;
import com.example.cuoiki.RoomDatabase.RoomProduct;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {
    private AppCompatButton appCompatButton;
    private EditText etName, etAddress, etPhone;
    private ImageView iv_back;
    private TextView tvTotalPrice;
    private RecyclerView rc_list;
    private CheckoutAdapter checkoutAdapter;
    private List<RoomProduct> productList;



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
                //add Order
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

}
