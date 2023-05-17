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
import com.example.cuoiki.Activity.User.ProductDetailActivity;
import com.example.cuoiki.Model.Order;
import com.example.cuoiki.Model.Product;
import com.example.cuoiki.R;
import com.example.cuoiki.Utils.contants;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    List<Order> orders, check;
    Context context;

    public OrderAdapter(List<Order> check, Context context) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.name.setText(orders.get(position).getP().getName());
        holder.price.setText(String.valueOf(order.getP().Currency(orders.get(position).getP().getPrice()*orders.get(position).getCount())));

        Glide.with(context)
                .load(contants.ROOT_URL+"Web"+orders.get(position).getP().getImage())
                .into(holder.ivImage);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (position != RecyclerView.NO_POSITION) {
//                    Product product = orders.get(position);
//                    long id = product.getId();
//                    Intent intent = new Intent(holder.itemView.getContext(), ProductDetailActivity.class);
//                    intent.putExtra("id", String.valueOf(id));
//                    holder.itemView.getContext().startActivity(intent);
//                }
//                Toast.makeText(context,"Bạn đã chọn product"+holder.title.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    @Override
    public int getItemCount() {
        if(orders!= null){
            return orders.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity;
        ImageView ivImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvProductName);
            price = itemView.findViewById(R.id.tvTotalPrice);
            quantity = itemView.findViewById(R.id.tvQuantity);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }
}
