package com.example.cuoiki.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cuoiki.ShipperFragment.CancelFragment;
import com.example.cuoiki.ShipperFragment.TransportedFragment;
import com.example.cuoiki.ShipperFragment.DeliveryFragment;
import com.example.cuoiki.ShipperFragment.NewOrderFragment;
import com.example.cuoiki.ShipperFragment.PickupFragment;

public class ViewPager2AdapterShipper extends FragmentStateAdapter {
    public ViewPager2AdapterShipper(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new PickupFragment();
            case 2:
                return new DeliveryFragment();
            case 3:
                return new TransportedFragment();
            case 4:
                return new CancelFragment();
            default:
                return new NewOrderFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}

