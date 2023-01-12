package com.example.sneakerstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakerstore.BillingDetailPage;
import com.example.sneakerstore.R;
import com.example.sneakerstore.model.Billing;

import java.util.List;

public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.BillHolder> {
    Context context;
    List<Billing> billingList;

    public BillingAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Billing> billingList) {
        this.billingList = billingList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BillingAdapter.BillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.billing_card, parent, false);
        return new BillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillingAdapter.BillHolder holder, int position) {
        Billing billing = billingList.get(position);
        if (billing != null) {
            if (billing.getStatus()) {
                // true => shipping
                holder.statusView.setImageResource(R.drawable.shipping_icon);
            }else {
                holder.statusView.setImageResource(R.drawable.ic_baseline_store_24);
            }

            holder.billingNo.setText("No: " + Integer.toString(billing.getBillingNo()));
            holder.customerName.setText("Customer: " + billing.getCustomerName());
            holder.billingPrice.setText("Price: " + Double.toString(billing.getBillingPrice()) + " $");

            holder.toDetailBilling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // load billing page
                    Intent intent = new Intent(context, BillingDetailPage.class);
                    intent.putExtra("billing_id", billing.getBillingNo());
                    intent.putExtra("customer", billing.getCustomerName());
                    intent.putExtra("total", billing.getBillingPrice());
                    intent.putExtra("status", billing.getStatus() ? 1 : 0);
                    intent.putExtra("address", billing.getBillingAddress());
                    intent.putExtra("payment", billing.isPayment() ? 1 : 0);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return billingList.size();
    }

    public class BillHolder extends RecyclerView.ViewHolder {
        TextView billingNo, customerName, billingPrice;
        ImageView statusView;
        ImageButton toDetailBilling;
        public BillHolder(@NonNull View itemView) {
            super(itemView);
            billingNo = itemView.findViewById(R.id.billingNumber);
            customerName = itemView.findViewById(R.id.billingCustomerName);
            billingPrice = itemView.findViewById(R.id.billingPrice);
            statusView = itemView.findViewById(R.id.statusImg);
            toDetailBilling = itemView.findViewById(R.id.toBillingDetail);
        }
    }
}
