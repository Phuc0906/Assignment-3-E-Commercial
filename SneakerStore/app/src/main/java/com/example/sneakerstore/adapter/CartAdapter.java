package com.example.sneakerstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
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

import com.bumptech.glide.Glide;
import com.example.sneakerstore.MainActivity;
import com.example.sneakerstore.ProductDetailActivity;
import com.example.sneakerstore.R;
import com.example.sneakerstore.fragment.CartFragment;
import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.sneaker.CartSneaker;

import org.json.JSONArray;
import org.json.JSONObject;

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
    public void onBindViewHolder(@NonNull CartAdapter.cartHolder holder, @SuppressLint("RecyclerView") int position) {
        CartSneaker cartSneaker = cartSneakerList.get(position);

        if (cartSneaker != null) {
            System.out.println(cartSneaker.getResourceImage());

            new HttpHandler.DownloadImageFromInternet(holder.itemImg).execute(MainActivity.ROOT_IMG + cartSneaker.getResourceImage());
            holder.brandText.setText(cartSneaker.getBrand());

            holder.priceText.setText(Double.toString(cartSneaker.getPrice()) + " $");
            holder.quantityText.setText(Integer.toString(cartSneaker.getQuantity()));

            holder.sizeText.setText("Size: " + cartSneaker.getSize());

            holder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartSneaker.setQuantity(cartSneaker.getQuantity() + 1);
                    int currentTotalPrice = Integer.parseInt(CartFragment.totalPrice.getText().toString().split(" ")[0]);
                    currentTotalPrice += cartSneaker.getPrice();
                    notifyDataSetChanged();
                    CartFragment.totalPrice.setText(Integer.toString(currentTotalPrice) + " $");

                }
            });

            holder.minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cartSneaker.getQuantity() - 1 > 0) {
                        cartSneaker.setQuantity(cartSneaker.getQuantity() - 1);
                        int currentTotalPrice = Integer.parseInt(CartFragment.totalPrice.getText().toString().split(" ")[0]);
                        currentTotalPrice -= cartSneaker.getPrice();
                        notifyDataSetChanged();
                        CartFragment.totalPrice.setText(Integer.toString(currentTotalPrice) + " $");

                    }
                }
            });


            holder.cartDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartSneakerList.remove(position);
                    notifyDataSetChanged();

                    DeleteProduct deleteProduct = new DeleteProduct();
                    deleteProduct.execute(MainActivity.ROOT_API + "/product/cart?userid=" + "1" + "&productid=" + cartSneaker.getSneakerId() + "&size=" + cartSneaker.getSize());
                }
            });
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
        private TextView brandText, priceText, sizeText;
        private ImageView itemImg;
        private TextView quantityText;
        private ImageButton addButton, minusButton;
        private Button cartDeleteBtn;

        public cartHolder(@NonNull View itemView) {
            super(itemView);
            brandText = itemView.findViewById(R.id.cartTitleText);
            priceText = itemView.findViewById(R.id.cartPriceText);
            itemImg = itemView.findViewById(R.id.cartImg);
            quantityText = itemView.findViewById(R.id.cartQuantity);
            addButton = itemView.findViewById(R.id.addButton);
            minusButton = itemView.findViewById(R.id.minusButton);
            sizeText = itemView.findViewById(R.id.cartProductSize);
            cartDeleteBtn = itemView.findViewById(R.id.cartItemDeleteBtn);

        }
    }

    public class DeleteProduct extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            System.out.println(urls[0]);
            return HttpHandler.deleteMethod(urls[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
