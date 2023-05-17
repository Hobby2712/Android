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
import com.example.cuoiki.Activity.User.CartActivity;
import com.example.cuoiki.Model.Order;
import com.example.cuoiki.Model.Product;
import com.example.cuoiki.R;
import com.example.cuoiki.RoomDatabase.ProductDatabase;
import com.example.cuoiki.RoomDatabase.RoomProduct;
import com.example.cuoiki.Utils.contants;

import java.util.ArrayList;
import java.util.List;

public class UserOrderDangGAdapter extends RecyclerView.Adapter<UserOrderDangGAdapter.ViewHolder> {
    List<Order> orders, check;
    Context context;

    public UserOrderDangGAdapter(List<Order> check, Context context) {
        List<Order> orders = new ArrayList<>();
        for(Order i: check){
            if(i.getStatus()==3){
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

        Glide.with(context)
                .load(contants.ROOT_URL+"Web"+orders.get(position).getP().getImage())
                .into(holder.ivImage);

        holder.tvCancel.setVisibility(View.INVISIBLE);
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

            tvCancel = itemView.findViewById(R.id.tvCancel);
            tvBuy = itemView.findViewById(R.id.tvBuy);
        }
    }
}
