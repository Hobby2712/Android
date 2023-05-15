package com.example.cuoiki.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cuoiki.Model.User;
import com.example.cuoiki.R;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.Utils.contants;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    TextView name, email, phone, address;
    Button btnLogout, btnEdit;
    ImageView imageViewprofile, iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            name = findViewById(R.id.textViewName);
            email = findViewById(R.id.textViewEmail);
            phone = findViewById(R.id.textViewPhone);
            address = findViewById(R.id.textViewAddress);
            btnLogout = findViewById(R.id.buttonLogout);
            btnEdit = findViewById(R.id.btnEdit);
            imageViewprofile = findViewById(R.id.ivProfile);
            User user = SharedPrefManager.getInstance(this).getUser();
            name.setText(String.valueOf(user.getFullName()));
            email.setText(user.getEmail());
            phone.setText(user.getPhone());
            address.setText(user.getAddress());
            Glide.with(getApplicationContext()).load(contants.ROOT_URL+"Web"+user.getImages()).into(imageViewprofile);
            btnLogout.setOnClickListener(this);
            btnEdit.setOnClickListener(this);
        }
        else{
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.equals(btnLogout)){
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        } else if (view.equals(btnEdit)) {
            startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
        }
    }
}
