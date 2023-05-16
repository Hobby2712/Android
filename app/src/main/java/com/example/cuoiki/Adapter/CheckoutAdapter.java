package com.example.cuoiki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuoiki.R;
import com.example.cuoiki.RoomDatabase.RoomProduct;
import com.example.cuoiki.Utils.contants;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ProductHolder> {
    private List<RoomProduct> productList;
    private  Context context;

    public CheckoutAdapter(List<RoomProduct> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public void setData(List<RoomProduct> list){
        this.productList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_checkout,
                parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        RoomProduct products = productList.get(position);
        holder.tvProductName.setText(products.getName());
        holder.tvQuantity.setText(String.valueOf(products.getQuantity()));
        holder.tvPrice.setText(products.Currency(products.getPrice()*products.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if(productList!= null){
            return productList.size();
        }
        return 0;
    }
    public class ProductHolder extends RecyclerView.ViewHolder{
        private TextView tvProductName, tvQuantity, tvPrice;

        public ProductHolder(@NonNull View itemView){
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProduct);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }

}
