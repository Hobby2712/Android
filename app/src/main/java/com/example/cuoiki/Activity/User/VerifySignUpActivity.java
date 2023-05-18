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

public class VerifySignUpActivity extends AppCompatActivity {

    private String TAG = VerifySignUpActivity.class.getSimpleName();
    String email, user, pass, otp, otpSend;
    TextView tvAccount, tvCancel, tvContinue, tvResend;
    EditText etOtp;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        anhXa();
        email = (String) getIntent().getSerializableExtra("email");
        tvAccount.setText("Mã OTP đã được gửi đến " + email);

        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(VerifySignUpActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReSend();
            }
        });
    }

    private void SignUp() {
        otp = etOtp.getText().toString().trim();

        if (TextUtils.isEmpty(otp)){
            etOtp.setError("Please enter your otp");
            etOtp.requestFocus();
            return;
        }

        apiService= RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
        apiService.verifysignup(email,user,pass,otp,otpSend).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                try {
                    SignUpResponse signUpResponse = response.body();
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (!signUpResponse.isError()) {
                            User user = signUpResponse.getData().getUser();
                            user.setAddress("");
                            user.setFullName("");
                            user.setImages("");
                            user.setPhone("");

                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            finish();
                            Intent intent = new Intent(VerifySignUpActivity.this, MainActivity.class);
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
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
            }
        });
    }


    private void ReSend(){

        apiService = RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
        apiService.signup(email, user, pass, pass).enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                try {
                    VerifyResponse signUpResponse = response.body();
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (!signUpResponse.isError()) {
                            otpSend = response.body().getData().getOtp();
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
                Log.e(TAG, "error: " + t.getMessage());
            }
        });

    }

    private void anhXa() {
        email = (String) getIntent().getSerializableExtra("email");
        user = (String) getIntent().getSerializableExtra("user");
        pass = (String) getIntent().getSerializableExtra("pass");
        otpSend = (String) getIntent().getSerializableExtra("otp");
        tvAccount = findViewById(R.id.tvAccount);
        tvCancel = findViewById(R.id.tvCancel);
        tvContinue = findViewById(R.id.tvContinue);
        tvResend = findViewById(R.id.tvResend);
        etOtp = findViewById(R.id.etOtp);
    }
}
