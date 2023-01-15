package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sneakerstore.adapter.HistoryListAdapter;
import com.example.sneakerstore.model.HistoryList;
import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.model.User;
import com.example.sneakerstore.sneaker.CartSneaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    User user;
    HistoryListAdapter historyListAdapter;
    RecyclerView recyclerView;
    List<HistoryList> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //init
        historyListAdapter = new HistoryListAdapter(this);
        getData();
        //set up for recycler

        list = new ArrayList<>();
        historyListAdapter.setData(list);
        recyclerView = findViewById(R.id.history_list_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(historyListAdapter);
        new readJSON().execute();


    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            user = (User) bundle.getSerializable("user");
        }
    }

    public class readJSON extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return HttpHandler.getMethod(MainActivity.ROOT_API + "/product/billing/history?userid=1");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONArray obj = new JSONArray(s);
                    String currentDate = "";
                    List<CartSneaker> sneakerList = new ArrayList<>();
                    for (int i = 0; i < obj.length(); i++) {
                        JSONObject currentObj = obj.getJSONObject(i);
                        System.out.println("IN");
                        int currentBuyId = currentObj.getInt("BUYID");
                        String[] dateElement = currentObj.getString("BUY_DATE").split("T")[0].split("-");
                        currentDate = dateElement[2] + "-" + dateElement[1] + "-" + dateElement[0];
                        sneakerList = new ArrayList<>();
                        for (int j = i; j < obj.length(); j++) {
                            currentObj = obj.getJSONObject(j);

                            if (currentObj.getInt("BUYID") != currentBuyId) {
                                System.out.println("IN 2- - -" + " - size: " + sneakerList.size());
                                list.add(new HistoryList(currentDate, sneakerList));

                                i = j - 1;
                                break;
                            }
                            sneakerList.add(new CartSneaker(currentObj.getInt("PRODUCT_ID"), MainActivity.ROOT_IMG + currentObj.getString("PICTURE"), currentObj.getString("BRAND"), currentObj.getString("NAME"), currentObj.getInt("QUANTITY"), currentObj.getDouble("PRODUCT_PRICE"), currentObj.getDouble("SIZE")));

                            if (j == obj.length() - 1) {
                                i = obj.length();
                            }

                        }
                    }
                    list.add(new HistoryList(currentDate, sneakerList));
                    historyListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}