package com.example.sneakerstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sneakerstore.ProductDetailActivity;
import com.example.sneakerstore.R;
import com.example.sneakerstore.sneaker.Sneaker;

import java.util.List;

public class SneakerAdapter extends RecyclerView.Adapter<SneakerAdapter.sneakerHolder> {
    private Context context;
    private List<Sneaker> sneakerList;

    public SneakerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Sneaker> list) {
        this.sneakerList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public sneakerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sneaker_item, parent, false);
        return new sneakerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sneakerHolder holder, int position) {
        Sneaker sneaker = sneakerList.get(position);
        if (sneaker != null) {
            holder.sneakerName.setText(sneaker.getName());
            holder.brandName.setText(sneaker.getBrand());

            holder.cartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("product_id", Integer.toString(sneaker.getSneakerId()));
                    context.startActivity(intent);
                }
            });

            switch (sneaker.getBrand()) {
                case "Nike":
                    holder.backgroundImage.setImageResource(R.drawable.nike_logo_bg);
                    break;
                case "Adidas":
                    holder.backgroundImage.setImageResource(R.drawable.adidas_logo_bg);
                    break;
                case "Puma":
                    holder.backgroundImage.setImageResource(R.drawable.puma_logo_bg);
                    break;
                case "Under Armour":
                    holder.backgroundImage.setImageResource(R.drawable.under_armoure_logo_bg);
                    break;
            }

            Glide.with(context).load(sneaker.getResourceImage()).into(holder.sneakerImage);

        }
    }

    @Override
    public int getItemCount() {
        if(sneakerList != null) {
            return sneakerList.size();
        }
        return 0;
    }

    public class sneakerHolder extends RecyclerView.ViewHolder{
        private TextView sneakerName, brandName;
        private ImageView sneakerImage, backgroundImage;
        private Button cartBtn;
        public sneakerHolder(@NonNull View itemView) {
            super(itemView);
            sneakerName = itemView.findViewById(R.id.sneakerNameText);
            brandName = itemView.findViewById(R.id.brandText);
            sneakerImage = itemView.findViewById(R.id.sneakerImg);
            backgroundImage = itemView.findViewById(R.id.background_image);
            cartBtn = itemView.findViewById(R.id.btnCart);
        }
    }

}
