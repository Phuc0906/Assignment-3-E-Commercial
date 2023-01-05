package com.example.sneakerstore.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakerstore.R;

import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.SizeHolder> {
    private Context context;
    private List<String> list;

    public SizeAdapter(Context context) {
        this.context = context;
    }

    public void setAdapter(List<String>list){
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
    public void onBindViewHolder(@NonNull SizeHolder holder, int position) {
        String s = list.get(position);
        if (s != null) {
            holder.sizeText.setText(s);
            holder.sizeText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  if (!holder.isSelected) {
                      holder.sizeText.setBackgroundColor(Color.parseColor("#F9A825"));
                      holder.isSelected = true;
                  } else {
                      holder.sizeText.setBackgroundColor(Color.parseColor("#B5B5B5"));
                      holder.isSelected = false;
                  }
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
        boolean isSelected;
        public SizeHolder(@NonNull View itemView) {
            super(itemView);
            sizeText = itemView.findViewById(R.id.size_text);
        }
    }
}
