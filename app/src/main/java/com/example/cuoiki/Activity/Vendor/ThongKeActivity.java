package com.example.cuoiki.Activity.Vendor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cuoiki.R;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;

public class ThongKeActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_managep);
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
                Toast.makeText(this, "Bạn đang ở mục thống kê", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuProductManage:
                Toast.makeText(this, "Bạn chọn quản lý sản phẩm", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ThongKeActivity.this, ManageProductActivity.class);
                startActivity(intent);
                break;
            case R.id.menuOrderManage:
                Toast.makeText(this, "Bạn đang chọn quản lý đơn hàng", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(ThongKeActivity.this, ManageOrderActivity.class);
                startActivity(intent2);
                break;
            case R.id.menuLogOut:
                Toast.makeText(this, "Bạn chọn đăng xuất", Toast.LENGTH_SHORT).show();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
