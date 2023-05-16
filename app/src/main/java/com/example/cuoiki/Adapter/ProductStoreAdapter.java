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
import com.example.cuoiki.Activity.Vendor.EditProductActivity;
import com.example.cuoiki.Model.Product;
import com.example.cuoiki.R;
import com.example.cuoiki.RoomDatabase.RoomProduct;
import com.example.cuoiki.Utils.contants;

import java.util.List;

public class ProductStoreAdapter extends RecyclerView.Adapter<ProductStoreAdapter.ViewHolder> {
    List<Product> foodDomains;
    Context context;
    private iClickListener listener;

    public ProductStoreAdapter(List<Product> FoodDomains, Context context, iClickListener listener) {
        this.foodDomains = FoodDomains;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_managep, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = foodDomains.get(position);
        holder.productName.setText(foodDomains.get(position).getName());
        holder.productQuantity.setText(String.valueOf(foodDomains.get(position).getQuantity()));
        holder.productPrice.setText(String.valueOf(foodDomains.get(position).getPrice()) + "đ");

        Glide.with(context)
                .load(contants.ROOT_URL+"Web"+foodDomains.get(position).getImage())
                .into(holder.productImg);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != RecyclerView.NO_POSITION) {
                    Product product = foodDomains.get(position);
                    long id = product.getId();
                    Intent intent = new Intent(holder.btnEdit.getContext(), EditProductActivity.class);
                    intent.putExtra("id", String.valueOf(id));
                    holder.btnEdit.getContext().startActivity(intent);
                }
                Toast.makeText(context,"Bạn đã chọn product"+holder.productName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != RecyclerView.NO_POSITION) {
                    Product product = foodDomains.get(position);
                    long id = product.getId();
                    listener.deleteProduct(id);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return foodDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productQuantity, productPrice;
        ImageView productImg, btnEdit, btnDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tvProductName);
            productQuantity = itemView.findViewById(R.id.tv_manager_Quantity);
            productPrice = itemView.findViewById(R.id.tvPrice);
            productImg = itemView.findViewById(R.id.ivImage);
            btnEdit = itemView.findViewById(R.id.iv_edit_product);
            btnDelete = itemView.findViewById(R.id.iv_delete_product);
        }
    }

    public interface iClickListener{
        void deleteProduct(long productId);
    }
}
