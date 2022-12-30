package com.example.sneakerstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakerstore.R;
import com.example.sneakerstore.model.ProductList;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductItemHolder> {
    Context context;
    List<ProductList> list;

    public ProductListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ProductList> list) {
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
        ProductList productList = list.get(position);
        if (productList != null) {
            holder.item1Name.setText(productList.getItem1Name());
            holder.item1Price.setText(productList.getItem1Price() + " vnd");
            holder.item1Img.setImageResource(productList.getItem1Img());

            holder.item2Name.setText(productList.getItem2Name());
            holder.item2Price.setText(productList.getItem2Price() + " vnd");
            holder.item2Img.setImageResource(productList.getItem2Img());
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
        TextView item1Name, item2Name, item1Price, item2Price;
        ImageView item1Img, item2Img;
        public ProductItemHolder(@NonNull View itemView) {
            super(itemView);

            item1Name = itemView.findViewById(R.id.productListItem1Name);
            item2Name = itemView.findViewById(R.id.productListItem2Name);
            item1Price = itemView.findViewById(R.id.productListItem1Price);
            item2Price = itemView.findViewById(R.id.productListItem2Price);
            item1Img = itemView.findViewById(R.id.productListImg1);
            item2Img = itemView.findViewById(R.id.productListImg2);

        }
    }
}
