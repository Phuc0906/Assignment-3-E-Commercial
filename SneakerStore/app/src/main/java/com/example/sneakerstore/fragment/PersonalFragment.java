package com.example.sneakerstore.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sneakerstore.HistoryActivity;
import com.example.sneakerstore.MainActivity;
import com.example.sneakerstore.OrderActivity;
import com.example.sneakerstore.R;
import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;


public class PersonalFragment extends Fragment {
    ImageView userImage;
    TextView userName, userRank, userPoint;
    ConstraintLayout historyLayout, infoLayout, contactLayout, logoutLayout;
    String userID;
    User user;
    ConstraintLayout con;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // init
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        userImage = view.findViewById(R.id.user_image);
        userName = view.findViewById(R.id.user_name);
        userPoint = view.findViewById(R.id.user_point);
        userRank = view.findViewById(R.id.user_rank);
        historyLayout = view.findViewById(R.id.history_layout);
        infoLayout = view.findViewById(R.id.info_layout);
        contactLayout = view.findViewById(R.id.contact_layout);
        logoutLayout = view.findViewById(R.id.logout_layout);
        con = view.findViewById(R.id.container);
        progressBar = view.findViewById(R.id.progressBar);
        //testing userID
        userID = "1";

        //reading json
        new readJSON().execute();

        //set event
        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    private String getRank(int point) {
        if (point >= 0 && point < 300) {
            return "Copper Member";
        } else if (point >= 300 && point < 600) {
            return "Silver Member";
        } else if (point >= 600 && point < 800) {
            return "Golden Member";
        } else  {
            return "Diamond Member";
        }
    }

    public class readJSON extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            return HttpHandler.getMethod(MainActivity.ROOT_API + "/user/infomation?id=" + userID);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONObject obj = new JSONObject(s);
                    user = new User(obj.getInt("ID"),
                            obj.getString("NAME"),
                            obj.getString("ADDRESS"),
                            obj.getString("PHONE_NUMBER"),
                            obj.getString("GENDER"),
                            obj.getString("DATE_OF_BIRTH"),
                            obj.getInt("POINT"),
                            obj.getString("PASSWORD"),
                            obj.getString("ROLE"),
                            obj.getString("EMAIL"));

                    userName.setText(user.getName());
                    userRank.setText(getRank(user.getPoint()));
                    userPoint.setText(String.valueOf(user.getPoint()));
                    con.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}