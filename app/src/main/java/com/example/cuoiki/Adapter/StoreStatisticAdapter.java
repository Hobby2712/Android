package com.example.cuoiki.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuoiki.Activity.Vendor.EditProductActivity;
import com.example.cuoiki.Model.Product;
import com.example.cuoiki.Model.ThongKe;
import com.example.cuoiki.R;
import com.example.cuoiki.Utils.contants;

import java.util.List;

public class StoreStatisticAdapter extends RecyclerView.Adapter<StoreStatisticAdapter.ViewHolder> {
    List<ThongKe> foodDomains;
    Context context;

    public StoreStatisticAdapter(List<ThongKe> FoodDomains, Context context) {
        this.foodDomains = FoodDomains;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_statistic, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.uName.setText(foodDomains.get(position).getuName());
        holder.uAddress.setText(foodDomains.get(position).getuAddress());
        holder.pId.setText(String.valueOf(foodDomains.get(position).getpId()));
        holder.pQuantity.setText(String.valueOf(foodDomains.get(position).getQuantity()));
        holder.date.setText(foodDomains.get(position).getCreatedDate());
        holder.price.setText(ThongKe.Currency(foodDomains.get(position).getTotal()));
    }


    @Override
    public int getItemCount() {
        return foodDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView uName, uAddress, pId, pQuantity, date, price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            uName = itemView.findViewById(R.id.tvStatUser);
            uAddress = itemView.findViewById(R.id.tvStatAddress);
            pId = itemView.findViewById(R.id.tvStatProduct);
            pQuantity = itemView.findViewById(R.id.tvStatQuantity);
            date = itemView.findViewById(R.id.tvStatDate);
            price = itemView.findViewById(R.id.tvStatPrice);
        }
    }
}
