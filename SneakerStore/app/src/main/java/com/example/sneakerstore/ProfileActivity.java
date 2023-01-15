package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;

import com.example.sneakerstore.adapter.SpinnerAdapter;
import com.example.sneakerstore.model.User;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    Spinner spinner;
    SpinnerAdapter spinnerAdapter;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //init
        getData();
        spinner = findViewById(R.id.profile_spinner);

        // set for spinner
        List<String> genderList = new ArrayList<>();
        genderList.add("Male");
        genderList.add("Female");
        spinnerAdapter = new SpinnerAdapter(this, R.layout.spinner_selected, genderList);
        spinner.setAdapter(spinnerAdapter);
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            user = (User) bundle.getSerializable("user");
        }
    }
}