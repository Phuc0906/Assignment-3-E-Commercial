package com.example.sneakerstore.sneaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakerstore.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Category> list) {
        this.categoryList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = categoryList.get(position);
        if (category != null) {
            holder.categoryText.setText(category.getCateName());

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.categoryRec.setLayoutManager(linearLayoutManager);

            SneakerAdapter sneakerAdapter = new SneakerAdapter(context);
            sneakerAdapter.setData(category.getList());
            holder.categoryRec.setAdapter(sneakerAdapter);
        }
    }

    @Override
    public int getItemCount() {
        if(categoryList != null) {
            return categoryList.size();
        }
        return 0;
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        private TextView categoryText;
        private RecyclerView categoryRec;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            categoryRec = itemView.findViewById(R.id.categoryRec);
            categoryText = itemView.findViewById(R.id.categoryName);
        }
    }
}
