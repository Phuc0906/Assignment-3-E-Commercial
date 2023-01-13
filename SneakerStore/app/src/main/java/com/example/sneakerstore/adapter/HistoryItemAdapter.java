package com.example.sneakerstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sneakerstore.MainActivity;
import com.example.sneakerstore.R;
import com.example.sneakerstore.model.Product;
import com.example.sneakerstore.sneaker.CartSneaker;

import java.util.List;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.HistoryItemHolder> {
    private Context context;
    private List<CartSneaker> productList;

    public HistoryItemAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CartSneaker> list) {
        this.productList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryItemHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HistoryItemHolder holder, int position) {
        CartSneaker product = productList.get(position);
        if (product != null) {
            Glide.with(context).load(product.getResourceImage()).into(holder.imageView);
            holder.titleView.setText(product.getName());
            holder.desView.setText(String.valueOf(product.getSize()));
            holder.priceView.setText(String.valueOf(product.getPrice() * product.getQuantity()) + "$");
            holder.quantityView.setText(String.valueOf(product.getQuantity()));
        }
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    public class HistoryItemHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView, desView, priceView, quantityView;
        public HistoryItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.history_image);
            titleView = itemView.findViewById(R.id.history_name);
            desView = itemView.findViewById(R.id.history_des);
            priceView = itemView.findViewById(R.id.history_price);
            quantityView = itemView.findViewById(R.id.history_quantity);

        }
    }
}
