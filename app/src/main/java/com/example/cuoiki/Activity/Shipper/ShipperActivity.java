package com.example.cuoiki.Activity.Shipper;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cuoiki.Activity.User.MainActivity;
import com.example.cuoiki.Activity.User.OrderActivity;
//import com.example.cuoiki.Adapter.OrderShipperAdapter;
import com.example.cuoiki.Activity.Vendor.ManageOrderActivity;
import com.example.cuoiki.Activity.Vendor.ManageProductActivity;
import com.example.cuoiki.Activity.Vendor.ThongKeActivity;
import com.example.cuoiki.Adapter.ViewPager2Adapter;
//import com.example.cuoiki.Adapter.ViewPager2AdapterShipper;
import com.example.cuoiki.Adapter.ViewPager2AdapterShipper;
import com.example.cuoiki.R;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.databinding.ActivityOrderBinding;
import com.example.cuoiki.databinding.ActivityShipperBinding;
import com.google.android.material.tabs.TabLayout;


    public class ShipperActivity extends AppCompatActivity {
        private ActivityShipperBinding binding;
        private ViewPager2AdapterShipper viewPager2Adapter;

        private TabLayout tabLayout;
        public void setTabLayout(TabLayout tabLayout) {
            this.tabLayout = tabLayout;
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //viewBinding
            binding = ActivityShipperBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            setSupportActionBar(binding.toolBar);


            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Nhận đơn"));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Lấy hàng"));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Đang giao"));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Đã giao"));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Trả hàng"));

            FragmentManager fragmentManager = getSupportFragmentManager();
            viewPager2Adapter = new ViewPager2AdapterShipper(fragmentManager, getLifecycle());
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

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_shipper, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            switch (id) {
                case R.id.menuLogOut:
                    Toast.makeText(this, "Bạn chọn đăng xuất", Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

    }




