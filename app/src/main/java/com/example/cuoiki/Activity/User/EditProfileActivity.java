package com.example.cuoiki.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class EditProfileActivity extends AppCompatActivity {
    private String TAG = EditProfileActivity.class.getSimpleName();
    private AppCompatButton appCompatButton;
    String name, address, phone;
    private EditText etName, etAddress, etPhone;
    private ImageView iv_back;

    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        AnhXa();
        User user = SharedPrefManager.getInstance(this).getUser();
        etName.setText(user.getFullName());
        etAddress.setText(user.getAddress());
        etPhone.setText(user.getPhone());

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfile();
            }
        });
    }

    public void EditProfile(){
        User user = SharedPrefManager.getInstance(this).getUser();
        name = etName.getText().toString().trim();
        address = etAddress.getText().toString().trim();
        phone = etPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            etName.setError("Please enter your full name");
            etName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(address)){
            etAddress.setError("Please enter your address");
            etAddress.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)){
            etPhone.setError("Please enter your address");
            etPhone.requestFocus();
            return;
        }
        apiService = RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
        apiService.editProfile(user.getId(), name, address, phone).enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                try {
                    VerifyResponse signUpResponse = response.body();
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (!signUpResponse.isError()) {
                            User user1 = new User(
                                    user.getId(),
                                    user.getRole(),
                                    user.getUserName(),
                                    name,
                                    user.getEmail(),
                                    address,
                                    phone,
                                    user.getImages()
                            );
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user1);
                            finish();
                            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        }
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


    public void AnhXa() {
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);

        iv_back = findViewById(R.id.iv_back);
        appCompatButton = findViewById(R.id.appCompatButton);
    }

}
