package com.example.cuoiki.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuoiki.Activity.User.CartActivity;
import com.example.cuoiki.Activity.User.OrderActivity;
import com.example.cuoiki.Activity.Vendor.ManageOrderActivity;
import com.example.cuoiki.Model.Order;
import com.example.cuoiki.Model.Product;
import com.example.cuoiki.R;
import com.example.cuoiki.Response.OrderResponse;
import com.example.cuoiki.Retrofit.APIService;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.RoomDatabase.ProductDatabase;
import com.example.cuoiki.RoomDatabase.RoomProduct;
import com.example.cuoiki.Utils.contants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorOrderXNAdapter extends RecyclerView.Adapter<VendorOrderXNAdapter.ViewHolder> {
    List<Order> orders, check;
    Context context;
    APIService apiService;

    public VendorOrderXNAdapter(List<Order> check, Context context) {
        List<Order> orders = new ArrayList<>();
        for(Order i: check){
            if(i.getStatus()==1){
                orders.add(i);
            }
        }
        this.check = check;
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Order order = orders.get(position);
        holder.name.setText(orders.get(position).getP().getName());
        holder.price.setText(String.valueOf(order.getP().Currency(orders.get(position).getP().getPrice()*orders.get(position).getCount())));
        holder.quantity.setText(String.valueOf(orders.get(position).getCount()));
        holder.status.setText("Chờ xác nhận");
        Glide.with(context)
                .load(contants.ROOT_URL+"Web/image?fname="+orders.get(position).getP().getImage())
                .into(holder.ivImage);

        holder.tvCancel.setVisibility(View.INVISIBLE);
        holder.tvBuy.setText("Xác nhận");
        holder.tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //Get API
                apiService= RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
                apiService.changeStatus(order.getId(),6).enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        if(response.isSuccessful()){
                            new AlertDialog.Builder(holder.itemView.getContext())
                                    .setTitle("Confirm?")
                                    .setMessage("Are you sure")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(holder.itemView.getContext(), "Đã xác nhận", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(holder.itemView.getContext(), ManageOrderActivity.class);
                                            holder.itemView.getContext().startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
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
            }
        });



    }


    @Override
    public int getItemCount() {
        if(orders!= null){
            return orders.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity, status, tvCancel, tvBuy;
        ImageView ivImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvProductName);
            price = itemView.findViewById(R.id.tvTotalPrice);
            quantity = itemView.findViewById(R.id.tvQuantity);
            ivImage = itemView.findViewById(R.id.ivImage);
            status = itemView.findViewById(R.id.tvStatus);

            tvCancel = itemView.findViewById(R.id.tvRed);
            tvBuy = itemView.findViewById(R.id.tvGreen);
        }
    }
}
