package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sneakerstore.adapter.HistoryListAdapter;
import com.example.sneakerstore.model.HistoryList;
import com.example.sneakerstore.model.User;
import com.example.sneakerstore.sneaker.CartSneaker;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    User user;
    HistoryListAdapter historyListAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //init
        historyListAdapter = new HistoryListAdapter(this);
        getData();
        //set up for recycler
        historyListAdapter.setData(getList());
        recyclerView = findViewById(R.id.history_list_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(historyListAdapter);


    }
    private List<HistoryList> getList() {
        List<HistoryList> list = new ArrayList<>();
        List<CartSneaker> cList = new ArrayList<>();
        List<CartSneaker> cList1 = new ArrayList<>();
        cList.add(new CartSneaker(1, MainActivity.ROOT_IMG + "4.png", "Nike", "Nike Airmax-1",2,210, 9.5));
        cList.add(new CartSneaker(1, MainActivity.ROOT_IMG + "3.png", "Nike", "Nike Fly5",2,210, 9.5));
        cList.add(new CartSneaker(1, MainActivity.ROOT_IMG + "6.png", "Nike", "Nike Airmax-1",2,210, 9.5));
        cList.add(new CartSneaker(1, MainActivity.ROOT_IMG + "8.png", "Nike", "Nike Fly5",2,210, 9.5));

        cList1.add(new CartSneaker(1, MainActivity.ROOT_IMG + "4.png", "Nike", "Nike Airmax-1",2,210, 9.5));
        cList1.add(new CartSneaker(1, MainActivity.ROOT_IMG + "3.png", "Nike", "Nike Fly5",2,210, 9.5));
        cList1.add(new CartSneaker(1, MainActivity.ROOT_IMG + "6.png", "Nike", "Nike Airmax-1",2,210, 9.5));
        cList1.add(new CartSneaker(1, MainActivity.ROOT_IMG + "8.png", "Nike", "Nike Fly5",2,210, 9.5));

        list.add(new HistoryList("12/01/2023", cList));
        list.add(new HistoryList("13/01/2023", cList1));
        return list;
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            user = (User) bundle.getSerializable("user");
        }
    }
}