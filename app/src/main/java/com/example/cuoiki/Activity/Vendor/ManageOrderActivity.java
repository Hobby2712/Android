package com.example.cuoiki.Activity.Vendor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cuoiki.Adapter.MyCustomVendorPaper2Adapter;
import com.example.cuoiki.Adapter.VendorOrderDangGAdapter;
import com.example.cuoiki.Model.Order;
import com.example.cuoiki.R;
import com.example.cuoiki.Response.OrderResponse;
import com.example.cuoiki.Retrofit.APIService;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.Utils.contants;
import com.example.cuoiki.databinding.ActivityVendorOrderBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageOrderActivity extends AppCompatActivity {

    private ActivityVendorOrderBinding binding;
    private MyCustomVendorPaper2Adapter myVendorPaper2Adapter;
    private List<Order> orderList;
    APIService apiService;
    TextView a,b,c,d,e,f;

    List<Integer> aa = new ArrayList<>();;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVendorOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolBar);

        int storeId = SharedPrefManager.getInstance(ManageOrderActivity.this).getStoreInfo().getId();
        //Get API
        apiService= RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
        apiService.getQuantityStatus(storeId).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if(response.isSuccessful()){
                    Log.d("logg",String.valueOf(response.body().getData().getOrders().size()));
                    orderList=response.body().getData().getOrders();
                    for(Order b: orderList){
                        aa.add(b.getCount());
                    }

                    binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Xác nhận"));
                    binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Nhận đơn"));
                    binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Lấy hàng"));
                    binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Đang giao"));
                    binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Đã giao"));
                    binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Trả hàng"));

                    binding.textView.setText("( " + String.valueOf(aa.get(0))+" )");
                    binding.textView1.setText("( " + String.valueOf(aa.get(5))+" )");
                    binding.textView2.setText("( " + String.valueOf(aa.get(1))+" )");
                    binding.textView3.setText("( " + String.valueOf(aa.get(2))+" )");
                    binding.textView4.setText("( " + String.valueOf(aa.get(3)+aa.get(7))+" )");
                    binding.textView5.setText("( " + String.valueOf(aa.get(6))+" )");


                    FragmentManager fragmentManager = getSupportFragmentManager();
                    myVendorPaper2Adapter = new MyCustomVendorPaper2Adapter(fragmentManager, getLifecycle());
                    binding.viewPager2.setAdapter(myVendorPaper2Adapter);
                }else{
                    Log.d("logg","Lỗi");
                    int statusCode=response.code();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.d("logg",t.getMessage());
            }
        });




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
        getMenuInflater().inflate(R.menu.menu_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuStatistic:
                Toast.makeText(this, "Bạn đang chọn thống kê", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManageOrderActivity.this, ThongKeActivity.class);
                startActivity(intent);
                break;
            case R.id.menuProductManage:
                Toast.makeText(this, "Bạn đang chọn quản lý sản phẩm", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(ManageOrderActivity.this, ManageProductActivity.class);
                startActivity(intent2);
                break;
            case R.id.menuOrderManage:
                Toast.makeText(this, "Bạn đang ở mục quản lý đơn hàng", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuLogOut:
                Toast.makeText(this, "Bạn chọn đăng xuất", Toast.LENGTH_SHORT).show();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
