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

public class UserOrderLHAdapter extends RecyclerView.Adapter<UserOrderLHAdapter.ViewHolder> {
    List<Order> orders, check;
    Context context;
    APIService apiService;

    public UserOrderLHAdapter(List<Order> check, Context context) {
        List<Order> orders = new ArrayList<>();
        for(Order i: check){
            if(i.getStatus()==2){
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
        holder.status.setText("Đang lấy hàng");

        Glide.with(context)
                .load(contants.ROOT_URL+"Web/image?fname="+orders.get(position).getP().getImage())
                .into(holder.ivImage);
        holder.tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != RecyclerView.NO_POSITION) {
                    Order order = orders.get(position);
                    Product product = order.getP();
                    RoomProduct products = new RoomProduct(product.getId(), product.getName(),product.getImage(),1, product.getPrice());
                    RoomProduct list = ProductDatabase.getInstance(holder.itemView.getContext()).productDao().checkProduct(product.getId());
                    int check = 0;
                    if(list!=null){
                        check = list.getQuantity();
                    }
                    if(check>0){
                        Toast.makeText(holder.itemView.getContext(), "Update", Toast.LENGTH_SHORT).show();
                        products.setQuantity(check+1);
                        ProductDatabase.getInstance(holder.itemView.getContext()).productDao().update(products);
                        Intent intent = new Intent(holder.itemView.getContext(), CartActivity.class);
                        holder.itemView.getContext().startActivity(intent);
                        return;
                    }
                    //add vào room
                    ProductDatabase.getInstance(holder.itemView.getContext()).productDao().insertAll(products);
                    Toast.makeText(holder.itemView.getContext(), "Thêm product thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(holder.itemView.getContext(), CartActivity.class);
                    holder.itemView.getContext().startActivity(intent);
                }
                Toast.makeText(context,"Bạn đã chọn product"+holder.name.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //Get API
                apiService= RetrofitClient.getInstance().getRetrofit(contants.URL_PRODUCT2).create(APIService.class);
                apiService.changeStatus(order.getId(),5).enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        if(response.isSuccessful()){
                            new AlertDialog.Builder(holder.itemView.getContext())
                                    .setTitle("Confirm cancel product")
                                    .setMessage("Are you sure")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(holder.itemView.getContext(), "Đã hủy", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(holder.itemView.getContext(), OrderActivity.class);
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
