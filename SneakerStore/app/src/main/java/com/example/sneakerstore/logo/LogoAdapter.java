package com.example.sneakerstore.logo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakerstore.R;

import java.util.List;

public class LogoAdapter extends RecyclerView.Adapter<LogoAdapter.LogoHolder> {
    private List<Logo> list;

    public LogoAdapter(List<Logo> list) {
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
