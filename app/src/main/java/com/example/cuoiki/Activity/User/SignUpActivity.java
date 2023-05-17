package com.example.cuoiki.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.cuoiki.R;
import com.example.cuoiki.Response.VerifyResponse;
import com.example.cuoiki.Retrofit.APIService;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.Utils.contants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private String TAG = LoginActivity.class.getSimpleName();
    EditText etUsername, etPassword, etRePass, etEmail;
    LinearLayout btnRedirectLogin;

    AppCompatButton registerButton;

    APIService apiService;

    void setContent() {
        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnRedirectLogin = findViewById(R.id.iv_back);
        registerButton = findViewById(R.id.btnSignUp);
        etEmail = findViewById(R.id.etEmail);
        etRePass = findViewById(R.id.etRPassword);
    }

    void doRegister() {
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        final String rePassword = etRePass.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Please enter email");
            etEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Please enter username");
            etUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Please enter password");
            etPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(rePassword)) {
            etRePass.setError("Please enter repeat password");
            etRePass.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return;
        }
        apiService = RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
        apiService.signup(email, username, password, rePassword).enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                try {
                    VerifyResponse signUpResponse = response.body();
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (!signUpResponse.isError()) {
                            Intent intent = new Intent(SignUpActivity.this, VerifySignUpActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("user", username);
                            intent.putExtra("pass", password);
                            intent.putExtra("otp", response.body().getData().getOtp());
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setContent();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRegister();
            }
        });
        btnRedirectLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
