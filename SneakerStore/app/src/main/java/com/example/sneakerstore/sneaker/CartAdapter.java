package com.example.sneakerstore.sneaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakerstore.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.cartHolder> {
    private Context context;
    private List<CartSneaker> cartSneakerList;

    public CartAdapter(Context context) {
        this.context = context;

    }

    public void setData(List<CartSneaker> cartSneakerList) {
        this.cartSneakerList = cartSneakerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartAdapter.cartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new cartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.cartHolder holder, int position) {
        CartSneaker cartSneaker = cartSneakerList.get(position);

        if (cartSneaker != null) {
            holder.itemImg.setImageResource(cartSneaker.getResourceImage());
            holder.title.setText(cartSneaker.getName());
            holder.priceText.setText(""+cartSneaker.getPrice());
        }
    }

    @Override
    public int getItemCount() {
        if (cartSneakerList != null) {
            return cartSneakerList.size();
        }

        return 0;
    }

    public class cartHolder extends RecyclerView.ViewHolder {
        private TextView title, priceText;
        private ImageView itemImg;
        private TextView quantityText;

        public cartHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cartTitleText);
            priceText = itemView.findViewById(R.id.cartPriceText);
            itemImg = itemView.findViewById(R.id.cartImg);
            quantityText = itemView.findViewById(R.id.cartQuantity);
        }
    }
}
