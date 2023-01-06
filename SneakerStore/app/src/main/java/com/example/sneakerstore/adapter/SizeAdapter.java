package com.example.sneakerstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakerstore.ProductDetailActivity;
import com.example.sneakerstore.R;
import com.example.sneakerstore.model.SneakerSize;

import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.SizeHolder> {
    private Context context;
    private List<SneakerSize> list;

    public SizeAdapter(Context context) {
        this.context = context;
    }

    public void setAdapter(List<SneakerSize>list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SizeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_item, parent, false);
        return new SizeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeHolder holder, @SuppressLint("RecyclerView") int position) {
        SneakerSize s = list.get(position);
        if (s != null) {
            holder.sizeText.setText(s.getSize());
            if (s.isSelected()) {
                holder.sizeText.setBackgroundColor(Color.parseColor("#F9A825"));
            }else {
                holder.sizeText.setBackgroundColor(Color.parseColor("#B5B5B5"));
            }
            holder.sizeText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  s.setSelected(!s.isSelected());
                  for (int i = 0; i < list.size(); i++) {
                      if (i == position) continue;
                      list.get(i).setSelected(false);
                  }
                  notifyDataSetChanged();
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

    public class SizeHolder extends RecyclerView.ViewHolder{
        TextView sizeText;
        public SizeHolder(@NonNull View itemView) {
            super(itemView);
            sizeText = itemView.findViewById(R.id.size_text);
        }
    }
}
