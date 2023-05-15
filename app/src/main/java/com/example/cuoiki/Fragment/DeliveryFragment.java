package com.example.cuoiki.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cuoiki.databinding.FragmentNeworderBinding;

public class DeliveryFragment extends Fragment {
    FragmentNeworderBinding binding;

    public DeliveryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNeworderBinding.inflate(inflater, container, false);
        //recyclerView
        return binding.getRoot();
    }
}
