package com.example.sneakerstore.sneaker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakerstore.R;

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

            // convert bitmap image here ---------------------------------------
            holder.sneakerImage.setImageBitmap(base64toBitmap(sneaker.getResourceImage()));
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
        private ImageView sneakerImage;
        public sneakerHolder(@NonNull View itemView) {
            super(itemView);
            sneakerName = itemView.findViewById(R.id.sneakerNameText);
            brandName = itemView.findViewById(R.id.brandText);
            sneakerImage = itemView.findViewById(R.id.sneakerImg);
        }
    }

    private Bitmap base64toBitmap(String base64Img) {
        byte[] decodedString = Base64.decode(base64Img, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
