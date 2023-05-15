package com.example.cuoiki.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cuoiki.R;

public class ContactActivity extends AppCompatActivity {
    ImageView tvBackHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        tvBackHome = findViewById(R.id.iv_back);
        tvBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(ContactActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}
