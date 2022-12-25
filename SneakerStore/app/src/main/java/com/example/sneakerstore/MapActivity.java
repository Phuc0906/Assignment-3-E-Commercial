package com.example.sneakerstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity {

    GoogleMap googleMap;
    ImageButton searchBtn;
    Button enterBtn;
    ListView locationList;



    double shopLatitude;
    double shopLongitude;

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    EditText locationInfo;
    float addressDistance;
    String placeName;
    LatLng locationCoordinator;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                locationInfo.setText(place.getAddress());

                placeName = place.getName();

                locationCoordinator = place.getLatLng();
            }else if (requestCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        locationList = findViewById(R.id.resultPreview);

        locationInfo = findViewById(R.id.locationInfo);
        locationCoordinator = new LatLng(0, 0);

        searchBtn = findViewById(R.id.searchBtn);
        enterBtn = findViewById(R.id.enterButton);
        addressDistance = 0;
        placeName = "";

        Places.initialize(getApplicationContext(), "AIzaSyD5ariVVie2nXj8xcFQrLhaY8gf1Dpc4v0");

        locationInfo.setFocusable(false);
        locationInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldList).build(MapActivity.this);

                startActivityForResult(intent, 100);
            }
        });








        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String insertedPlaced = locationInfo.getText().toString();
                if (insertedPlaced == null) {
                    Toast.makeText(MapActivity.this, "You need to enter your place", Toast.LENGTH_LONG);
                }else {
                    Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(locationCoordinator.latitude, locationCoordinator.longitude, 1);
                        if (addresses.size() > 0) {
                            float results[] = new float[10];
                            Location.distanceBetween(shopLatitude, shopLongitude, addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), results);
                            System.out.println("Distance: " + results[0] + " m");
                            LatLng searchlg = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                            pointToPos(searchlg);

                            placeName = addresses.get(0).getAddressLine(0);
                            addressDistance = results[0];


                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent resultIntent = new Intent();
                resultIntent.putExtra("distance", addressDistance);
                resultIntent.putExtra("place", placeName);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });


        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }else {
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }



    }

    private void pointToPos(LatLng latLng) {
        Task<Location> task = client.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {

                            MarkerOptions options = new MarkerOptions().position(latLng).title("I am there");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 30));
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    private void getCurrentLocation() {
        Task<Location> task = client.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            shopLatitude = location.getLatitude();
                            shopLongitude = location.getLongitude();
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions options = new MarkerOptions().position(latLng).title("I am there");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 30));
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

}