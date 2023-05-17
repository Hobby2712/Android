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

public class VerifyForgotPassActivity extends AppCompatActivity {

    private String TAG = VerifyForgotPassActivity.class.getSimpleName();
    String email, newpass, otp, otpSend;
    TextView tvAccount, tvCancel, tvContinue, tvResend;
    EditText etOtp, etNewPass;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass_verify);
        anhXa();
        email = (String) getIntent().getSerializableExtra("email");
        tvAccount.setText("Mã OTP đã được gửi đến " + email);

        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPass();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(VerifyForgotPassActivity.this, LoginActivity.class);
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

    private void ForgotPass() {
        otp = etOtp.getText().toString().trim();
        newpass = etNewPass.getText().toString().trim();

        if (TextUtils.isEmpty(otp)){
            etOtp.setError("Please enter your otp");
            etOtp.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(newpass)){
            etNewPass.setError("Please enter your pass");
            etNewPass.requestFocus();
            return;
        }

        apiService= RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
        apiService.forgotPass(email,newpass,otp,otpSend).enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                try {
                    VerifyResponse signUpResponse = response.body();
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (!signUpResponse.isError()) {
                            finish();
                            Intent intent = new Intent(VerifyForgotPassActivity.this, LoginActivity.class);
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
            }
        });
    }


    private void ReSend(){
        apiService = RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
        apiService.findAccount(email).enqueue(new Callback<VerifyResponse>() {
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
        otpSend = (String) getIntent().getSerializableExtra("otp");
        tvAccount = findViewById(R.id.tvAccount);
        tvCancel = findViewById(R.id.tvCancel);
        tvContinue = findViewById(R.id.tvContinue);
        tvResend = findViewById(R.id.tvResend);
        etOtp = findViewById(R.id.etOtp);
        etNewPass = findViewById(R.id.etNewPass);
    }
}
