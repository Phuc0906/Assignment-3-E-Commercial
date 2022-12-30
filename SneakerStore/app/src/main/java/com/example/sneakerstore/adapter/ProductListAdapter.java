package com.example.sneakerstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakerstore.R;
import com.example.sneakerstore.model.ProductExplore;

import org.json.JSONObject;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductItemHolder> {
    Context context;
    List<ProductExplore> list;

    public ProductListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ProductExplore> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductListAdapter.ProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        return new ProductItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ProductItemHolder holder, int position) {
        ProductExplore product = list.get(position);
        if (product != null) {
            holder.brandName.setText(product.getBrandName());
            holder.shoesName.setText(product.getShoesName());
            holder.imageView.setImageResource(product.getResourceImage());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, product.getShoesName(), Toast.LENGTH_LONG).show();
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
