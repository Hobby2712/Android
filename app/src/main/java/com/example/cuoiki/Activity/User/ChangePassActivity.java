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

public class ChangePassActivity extends AppCompatActivity {
    private String TAG = EditProfileActivity.class.getSimpleName();
    private AppCompatButton appCompatButton;
    String oldpass, newpass, rnewpass;
    private EditText etOldPass, etNewPass, etRNewPass;
    private ImageView iv_back;

    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_changpass);
        AnhXa();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ChangePassActivity.this, ProfileActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePass();
            }
        });
    }

    public void ChangePass(){
        User user = SharedPrefManager.getInstance(this).getUser();
        oldpass = etOldPass.getText().toString().trim();
        newpass = etNewPass.getText().toString().trim();
        rnewpass = etRNewPass.getText().toString().trim();

        if (TextUtils.isEmpty(oldpass)){
            etOldPass.setError("Please enter your old password");
            etOldPass.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(newpass)){
            etNewPass.setError("Please enter your new password");
            etNewPass.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(rnewpass)){
            etRNewPass.setError("Please enter repeat your new password");
            etRNewPass.requestFocus();
            return;
        }
        apiService = RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
        apiService.changePass(user.getId(), oldpass, newpass, rnewpass).enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                try {
                    VerifyResponse signUpResponse = response.body();
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (!signUpResponse.isError()) {
                            Intent intent = new Intent(ChangePassActivity.this, VerifyChangePassActivity.class);
                            intent.putExtra("id", user.getId());
                            intent.putExtra("oldpass", oldpass);
                            intent.putExtra("pass", newpass);
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


    public void AnhXa() {
        etOldPass = findViewById(R.id.etOldPass);
        etNewPass = findViewById(R.id.etNewPass);
        etRNewPass = findViewById(R.id.etRNewPass);

        iv_back = findViewById(R.id.iv_back);
        appCompatButton = findViewById(R.id.appCompatButton);
    }

}
