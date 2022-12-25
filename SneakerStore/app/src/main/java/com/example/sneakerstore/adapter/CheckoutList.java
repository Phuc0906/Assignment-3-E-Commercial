package com.example.sneakerstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakerstore.R;
import com.example.sneakerstore.model.CheckoutSneaker;

import java.util.List;

public class CheckoutList extends RecyclerView.Adapter<CheckoutList.CheckoutAdapter> {
    Context context;
    List<CheckoutSneaker> list;

    public CheckoutList(Context context) {
        this.context = context;
    }

    public void setData(List<CheckoutSneaker> list ) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CheckoutList.CheckoutAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item_card, parent, false);
        return new CheckoutAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutList.CheckoutAdapter holder, int position) {
        CheckoutSneaker checkoutSneaker = list.get(position);
        if (checkoutSneaker != null) {
            holder.name.setText(checkoutSneaker.getName());
            holder.price.setText(Double.toString(checkoutSneaker.getPrice()));
            holder.size.setText(Double.toString(checkoutSneaker.getSize()));
            holder.quantity.setText("Quantity: " + Integer.toString(checkoutSneaker.getQuantity()));
            holder.itemImg.setImageResource(checkoutSneaker.getResourceImage());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class CheckoutAdapter extends RecyclerView.ViewHolder {
        TextView name, price, size;
        TextView quantity;
        ImageView itemImg;
        ImageButton addBtn, minusBtn;

        public CheckoutAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sneakerName);
            price = itemView.findViewById(R.id.sneakerPrice);
            size = itemView.findViewById(R.id.shoesSize);
            quantity = itemView.findViewById(R.id.quantityView);
            itemImg = itemView.findViewById(R.id.shoesCheckOutImg);

        }
    }
}

