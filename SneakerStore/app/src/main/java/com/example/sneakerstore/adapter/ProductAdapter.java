package com.example.sneakerstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sneakerstore.MainActivity;
import com.example.sneakerstore.ProductDetailActivity;
import com.example.sneakerstore.R;
import com.example.sneakerstore.model.Product;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductItemHolder> {
    Context context;
    List<Product> list;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Product> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductAdapter.ProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        return new ProductItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductItemHolder holder, int position) {
        Product product = list.get(position);

        if (product != null) {
            holder.brandName.setText(product.getBrand());
            holder.shoesName.setText(product.getName());
            Glide.with(context).load(product.getPicture()).into(holder.imageView);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("product_id", String.valueOf(product.getId()));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ProductItemHolder extends RecyclerView.ViewHolder {
        TextView brandName, shoesName;
        ImageView imageView;
        CardView cardView;
        public ProductItemHolder(@NonNull View itemView) {
            super(itemView);
            brandName = itemView.findViewById(R.id.explore_brand);
            shoesName = itemView.findViewById(R.id.explore_name);
            imageView = itemView.findViewById(R.id.explore_image);
            cardView  = itemView.findViewById(R.id.explore_card);
        }
    }
}