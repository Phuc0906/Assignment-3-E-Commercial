package com.example.sneakerstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakerstore.R;
import com.example.sneakerstore.model.HistoryList;
import com.example.sneakerstore.sneaker.CartSneaker;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryListHolder> {
    private Context context;
    private List<HistoryList> list;

    public HistoryListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HistoryList> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HistoryListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false);
        return new HistoryListHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HistoryListHolder holder, int position) {
        HistoryList hl = list.get(position);
        if (hl != null) {
            int total = 0;
            holder.date.setText(hl.getDate());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            holder.recyclerView.setLayoutManager(linearLayoutManager);
            HistoryItemAdapter historyItemAdapter = new HistoryItemAdapter(context);
            historyItemAdapter.setData(hl.getList());
            holder.recyclerView.setAdapter(historyItemAdapter);
            for (int i = 0; i < hl.getList().size(); i++) {
                CartSneaker cartSneaker = hl.getList().get(i);
                total += cartSneaker.getPrice() * cartSneaker.getQuantity();
            }
            holder.totalPrice.setText(holder.totalPrice.getText() + String.valueOf(total) + "$");
        }
    }

    @Override
    public int getItemCount() {
        if (this.list != null) {
            return list.size();
        }
        return 0;
    }

    public class HistoryListHolder extends RecyclerView.ViewHolder {
        private TextView date, totalPrice;
        private RecyclerView recyclerView;
        public HistoryListHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.history_list_date);
            recyclerView = itemView.findViewById(R.id.history_recycler);
            totalPrice = itemView.findViewById(R.id.history_totalPrice);
        }
    }
}
