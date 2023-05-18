package com.example.cuoiki.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuoiki.Activity.User.ProductCategoryActivity;
import com.example.cuoiki.Model.Categories;
import com.example.cuoiki.R;
import com.example.cuoiki.Utils.contants;


import java.io.File;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<Categories> categories;
    Context context;


    public CategoryAdapter(List<Categories> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String id= String.valueOf(categories.get(position).getId());
        holder.categoryId.setText(id);
        holder.categoryName.setText(categories.get(position).getName());
        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.background_category));
        Glide.with(context)
                .load(contants.ROOT_URL+"Web/image?fname="+categories.get(position).getImages())
                .into(holder.categoryPic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ProductCategoryActivity.class);
                intent.putExtra("idCategory", holder.categoryId.getText().toString());
                intent.putExtra("CategoryName", holder.categoryName.getText().toString());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        TextView categoryId;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            categoryId = itemView.findViewById(R.id.categoryId);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
