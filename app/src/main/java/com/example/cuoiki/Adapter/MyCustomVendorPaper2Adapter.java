package com.example.cuoiki.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cuoiki.Fragment.VendorDaGiaoFragment;
import com.example.cuoiki.Fragment.VendorDangGiaoFragment;
import com.example.cuoiki.Fragment.VendorLayHangFragment;
import com.example.cuoiki.Fragment.VendorNhanDonFragment;
import com.example.cuoiki.Fragment.VendorTraHangFragment;
import com.example.cuoiki.Fragment.VendorXacNhanFragment;

public class MyCustomVendorPaper2Adapter extends FragmentStateAdapter {
    public MyCustomVendorPaper2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new VendorXacNhanFragment();
            case 1:
                return new VendorNhanDonFragment();
            case 2:
                return new VendorLayHangFragment();
            case 3:
                return new VendorDangGiaoFragment();
            case 4:
                return new VendorDaGiaoFragment();
            case 5:
                return new VendorTraHangFragment();
            default:
                return new VendorXacNhanFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}

