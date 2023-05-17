package com.example.cuoiki.ShipperFragment.Shipper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cuoiki.Activity.User.MainActivity;
import com.example.cuoiki.Adapter.ViewPager2Adapter;
import com.example.cuoiki.databinding.ActivityOrderBinding;
import com.google.android.material.tabs.TabLayout;


    public class ShipperActivity extends AppCompatActivity {
        private ActivityOrderBinding binding;
        private ViewPager2Adapter viewPager2Adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //viewBinding
            binding = ActivityOrderBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            binding.ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(ShipperActivity.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                }
            });


            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Xác nhận"));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Lấy hàng"));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Dang giao"));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Đánh giá"));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Huy"));

            FragmentManager fragmentManager = getSupportFragmentManager();
            viewPager2Adapter = new ViewPager2Adapter(fragmentManager, getLifecycle());
            binding.viewPager2.setAdapter(viewPager2Adapter);

            binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    binding.viewPager2.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

            binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
                }
            });
        }
    }



