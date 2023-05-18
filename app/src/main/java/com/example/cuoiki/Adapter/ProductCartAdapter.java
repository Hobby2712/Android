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

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ProductHolder> {
    private List<RoomProduct> productList;
    private  Context context;
    private  iClickListener listener;
    public ProductCartAdapter(List<RoomProduct> productList, Context context, iClickListener listener) {
        this.productList = productList;
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<RoomProduct> list){
        this.productList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,
                parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        RoomProduct products = productList.get(position);
        holder.tvProductName.setText(products.getName());
        holder.tvQuantity.setText(String.valueOf(products.getQuantity()));
        holder.tvPrice.setText(products.Currency(products.getPrice()));
        holder.tvTotalPrice.setText(products.Currency(products.getPrice()*products.getQuantity()));
        Glide.with(context)
                .load(contants.ROOT_URL+"Web/image?fname="+products.getImage())
                .into(holder.ivImage);

        //xử lí sự kiện
        holder.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.minusQuantity(products);
            }
        });

        holder.ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.plusQuantity(products);
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.deleteProduct(products);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(productList!= null){
            return productList.size();
        }
        return 0;
    }
    public class ProductHolder extends RecyclerView.ViewHolder{
        private ImageView ivImage, ivPlus, ivMinus;
        private TextView tvProductName, tvQuantity, tvPrice, tvTotalPrice, tvDelete;

        public ProductHolder(@NonNull View itemView){
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivPlus = itemView.findViewById(R.id.ivPlus);
            ivMinus = itemView.findViewById(R.id.ivMinus);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            tvDelete = itemView.findViewById(R.id.tvDelele);
        }
    }

    public interface iClickListener{
        void plusQuantity(RoomProduct product);
        void minusQuantity(RoomProduct product);

        void deleteProduct(RoomProduct product);
    }
}
