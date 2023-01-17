package com.example.sneakerstore.logo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakerstore.MainActivity;
import com.example.sneakerstore.ProductListActivity;
import com.example.sneakerstore.R;

import java.util.List;

public class LogoAdapter extends RecyclerView.Adapter<LogoAdapter.LogoHolder> {
    Context context;
    private List<Logo> list;

    public LogoAdapter(List<Logo> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LogoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.logo_item, parent, false);
        return new LogoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogoHolder holder, int position) {
        Logo logo = list.get(position);
        if (logo != null) {
            holder.imageView.setImageResource(logo.getResourceID());
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productListIntent = new Intent(context, ProductListActivity.class);
                    productListIntent.putExtra("role", 0); // 1: admin, 0: user
                    productListIntent.putExtra("id", -1); // -1 for searching by brand
                    productListIntent.putExtra("brand", MainActivity.brandsHashMap.get(logo.getName()));
                    System.out.println("ID: " + MainActivity.brandsHashMap.get(logo.getName()));

                    productListIntent.putExtra("cat", logo.getName());
                    context.startActivity(productListIntent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(list != null)
            return list.size();
        return 0;
    }

    public static class LogoHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public LogoHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.logo_image);
        }
    }
}
