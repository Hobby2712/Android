package com.example.cuoiki.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.cuoiki.R;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.Model.User;
import com.example.cuoiki.Volley.VolleySingle;
import com.example.cuoiki.Utils.contants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    EditText etname, etPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
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
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();;
                                JSONObject userJson = obj.getJSONObject("data").getJSONObject("user");
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("userName"),
                                        userJson.getString("email"),
                                        userJson.getString("address"),
                                        userJson.getString("image")
                                );
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                finish();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
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
