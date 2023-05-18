package com.example.cuoiki.Adapter;

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
import com.example.cuoiki.Model.Product;
import com.example.cuoiki.R;
import com.example.cuoiki.Activity.User.ProductDetailActivity;
import com.example.cuoiki.Utils.contants;

import java.util.List;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.MyViewHolder>{
    List<Product> array;
    Context context;

    public ProductCategoryAdapter(List<Product> array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView images;
        public TextView tenSp,id,fee;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images=itemView.findViewById(R.id.ivImage);
            tenSp=itemView.findViewById(R.id.title);
            fee=itemView.findViewById(R.id.fee);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Product product = array.get(position);
                        long id = product.getId();
                        Intent intent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                        intent.putExtra("id", String.valueOf(id));
                        itemView.getContext().startActivity(intent);
                    }
                    Toast.makeText(context,"Bạn đã chọn product"+tenSp.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position){
        Product product = array.get(position);
        holder.tenSp.setText(product.getName());
        Glide.with(context)
                .load(contants.ROOT_URL+"Web/image?fname="+product.getImage())
                .into(holder.images);
        holder.fee.setText(product.Currency(product.getPrice()));
    }
    @Override
    public int getItemCount(){return array==null?0:array.size();}
}
