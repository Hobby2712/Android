package com.example.cuoiki.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.cuoiki.Adapter.ProductCategoryAdapter;
import com.example.cuoiki.Model.User;
import com.example.cuoiki.R;
import com.example.cuoiki.Response.SignUpResponse;

import com.example.cuoiki.Response.VerifyResponse;
import com.example.cuoiki.Retrofit.APIService;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.Utils.contants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassActivity extends AppCompatActivity {

    private String TAG = ForgotPassActivity.class.getSimpleName();
    String email;
    TextView tvCancel, tvContinue;
    EditText etEmail;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findaccount);
        anhXa();

        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindAccount();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ForgotPassActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

    }

    private void FindAccount() {
        email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            etEmail.setError("Please enter your username");
            etEmail.requestFocus();
            return;
        }

        apiService= RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
        apiService.findAccount(email).enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                try {
                    VerifyResponse findAccResponse = response.body();
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), findAccResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (!findAccResponse.isError()) {
                            Intent intent = new Intent(ForgotPassActivity.this, VerifyForgotPassActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("otp", response.body().getData().getOtp());
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), findAccResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<VerifyResponse> call, Throwable t) {
            }
        });
    }


    private void anhXa() {
        tvCancel = findViewById(R.id.tvCancel);
        tvContinue = findViewById(R.id.tvContinue);
        etEmail = findViewById(R.id.etEmail);
    }
}
