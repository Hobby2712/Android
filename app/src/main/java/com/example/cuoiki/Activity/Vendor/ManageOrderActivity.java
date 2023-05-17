package com.example.cuoiki.Activity.Vendor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cuoiki.R;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.databinding.ActivityVendorManagepBinding;
import com.example.cuoiki.databinding.ActivityVendorOrderBinding;

public class ManageOrderActivity extends AppCompatActivity {

    private ActivityVendorOrderBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVendorOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolBar);
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
