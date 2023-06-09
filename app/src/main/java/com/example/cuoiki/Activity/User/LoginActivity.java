package com.example.cuoiki.Activity.User;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.cuoiki.Activity.Shipper.ShipperActivity;
import com.example.cuoiki.Activity.Vendor.AddProductActivity;
import com.example.cuoiki.Activity.Vendor.ManageProductActivity;
import com.example.cuoiki.Activity.Vendor.ThongKeActivity;
import com.example.cuoiki.Model.Store;
import com.example.cuoiki.R;
import com.example.cuoiki.Response.StoreResponse;
import com.example.cuoiki.Retrofit.APIService;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.Model.User;
import com.example.cuoiki.Volley.VolleySingle;
import com.example.cuoiki.Utils.contants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class LoginActivity extends AppCompatActivity {
    EditText etname, etPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (SharedPrefManager.getInstance(this).isLoggedIn() && SharedPrefManager.getInstance(this).getUser().getRole()==2){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        else if (SharedPrefManager.getInstance(this).isLoggedIn() && SharedPrefManager.getInstance(this).getUser().getRole()==4){
            finish();
            startActivity(new Intent(this, ManageProductActivity.class));
        }
        else if (SharedPrefManager.getInstance(this).isLoggedIn() && SharedPrefManager.getInstance(this).getUser().getRole()==3){
            finish();
            startActivity(new Intent(this, ShipperActivity.class));
        }
//        progressBar = findViewById(R.id.progressBar);
        etname = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

        findViewById(R.id.tvForgotP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), ForgotPassActivity.class));
            }
        });
    }

    private void userLogin() {
        final String username = etname.getText().toString();
        final String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(username)){
            etname.setError("Please enter your username");
            etname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)){
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, contants.URL_LOGIN,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
//                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONObject userJson = obj.getJSONObject("data").getJSONObject("user");
                                User user = new User(
                                        userJson.optInt("id"),
                                        userJson.optInt("role"),
                                        userJson.optString("userName"),
                                        userJson.optString("fullName"),
                                        userJson.optString("email"),
                                        userJson.optString("address"),
                                        userJson.optString("phone"),
                                        userJson.optString("image")
                                );
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                finish();
                                if(userJson.getInt("role") == 2) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else if(userJson.getInt("role") == 4) {
                                    //Intent intent = new Intent(LoginActivity.this, ThongKeActivity.class);
                                    //getStore(SharedPrefManager.getInstance(getApplicationContext()).getUser().getId());
                                    Intent intent = new Intent(LoginActivity.this, ManageProductActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Intent intent = new Intent(LoginActivity.this, ShipperActivity.class);
                                    startActivity(intent);
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user", username);
                params.put("pass", password);
                return params;
            }
        };
        VolleySingle.getInstance(this).addToRequestQueue(stringRequest);
    }
}
