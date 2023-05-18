package com.example.cuoiki.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.example.cuoiki.Activity.Shipper.ShipperActivity;
import com.example.cuoiki.Activity.User.CartActivity;
import com.example.cuoiki.Activity.User.ProductDetailActivity;
import com.example.cuoiki.Model.Order;
import com.example.cuoiki.Model.Product;
import com.example.cuoiki.R;
import com.example.cuoiki.Response.OrderResponse;
import com.example.cuoiki.Retrofit.APIService;
import com.example.cuoiki.Retrofit.RetrofitClient;
import com.example.cuoiki.RoomDatabase.ProductDatabase;
import com.example.cuoiki.RoomDatabase.RoomProduct;
import com.example.cuoiki.SharedPrefManager.SharedPrefManager;
import com.example.cuoiki.Utils.contants;
import com.example.cuoiki.databinding.ActivityShipperBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.List;



import retrofit2.Callback;


public class TraHangShipperAdapter extends RecyclerView.Adapter<TraHangShipperAdapter.ViewHolder> {
    List<Order> orders;
    Context context;
    APIService apiService;

    public TraHangShipperAdapter(List<Order> orders, Context context) {
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
        holder.quantity.setText(String.valueOf(orders.get(position).getCount()));
        switch (orders.get(position).getStatus()) {
            case 1:
                break;
            case 2:
                holder.status.setText("Chờ lấy hàng");
                holder.nextAction.setText("Đã lấy");
                break;
            case 3:
                holder.status.setText("Chờ đi giao");
                holder.cancel.setText("Trả hàng");
                holder.nextAction.setText("Đã giao");
                break;
            case 4:
                holder.status.setText("Đã giao");
                break;
            case 7:
                holder.status.setText("Đã trả hàng");
                break;
            case 6:
                holder.status.setText("Chờ nhận đơn");
                holder.nextAction.setText("Nhận đơn");
                holder.cancel.setText("Hủy");
                break;
            default:
                holder.status.setText("Chờ nhận đơn");
                holder.nextAction.setText("Nhận đơn");
                holder.cancel.setText("Hủy");
                break;
        }
        Glide.with(context)
                .load(contants.ROOT_URL+"Web/image?fname="+orders.get(position).getP().getImage())
                .into(holder.ivImage);

        holder.nextAction.setVisibility(View.INVISIBLE);
        holder.cancel.setVisibility(View.INVISIBLE);
    }


    @Override
    public int getItemCount() {
        if(orders!= null){
            return orders.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity,status,cancel,nextAction;
        ImageView ivImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvProductName);
            price = itemView.findViewById(R.id.tvTotalPrice);
            quantity = itemView.findViewById(R.id.tvQuantity);
            ivImage = itemView.findViewById(R.id.ivImage);
            status = itemView.findViewById(R.id.tvStatus);
            cancel = itemView.findViewById(R.id.tvRed);
            nextAction=itemView.findViewById(R.id.tvGreen);

        }
    }
}
