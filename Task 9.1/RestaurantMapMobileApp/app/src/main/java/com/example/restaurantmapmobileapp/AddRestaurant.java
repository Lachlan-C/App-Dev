package com.example.restaurantmapmobileapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantmapmobileapp.data.DatabaseHelper;
import com.example.restaurantmapmobileapp.model.Location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddRestaurant extends AppCompatActivity {
    TextView locationView;
    String lng, lati;
    Double currentlng, currentlati;
    Button save, getcurrentlocation, showonmap;
    EditText placename;
    DatabaseHelper db;
    LocationManager locationManager;
    LocationListener locationListener;
    android.location.Location location;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getlocation();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        locationView = findViewById(R.id.location);
        save = findViewById(R.id.SaveButton);
        placename = findViewById(R.id.PlaceNameButton);
        getcurrentlocation = findViewById(R.id.getCurrentLocationButton);
        showonmap = findViewById(R.id.ShowOnMapButton);
        db = new DatabaseHelper(this);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull android.location.Location location) {
                currentlati = location.getLatitude();
                currentlng = location.getLongitude();
            }
        };

        showonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRestaurant.this, ShowOnePlace.class);
                intent.putExtra("name", placename.getText().toString());
                intent.putExtra("lat", lati);
                intent.putExtra("lng", lng);
                startActivity(intent);
            }
        });

        locationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRestaurant.this, Search.class);
                startActivityForResult(intent, 0);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String place = placename.getText().toString();
                long result = db.insertLoc(new Location(place, Float.parseFloat(lati), Float.parseFloat(lng)));
                if (result > 0) {
                    Toast.makeText(AddRestaurant.this, "Successfully Saved!", Toast.LENGTH_SHORT).show();
                    locationView.setText("location");
                    placename.setText("");
                } else {
                    Toast.makeText(AddRestaurant.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getcurrentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            AddRestaurant.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            1
                    );
                } else {
                    getlocation();
                }
            }
        });
    }

    private void getlocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddRestaurant.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }
        currentlati = location.getLatitude();
        currentlng = location.getLongitude();
        lati = currentlati.toString();
        lng = currentlng.toString();
        locationView.setText(lati + "," + lng);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0)
        {
            String message = data.getStringExtra("longlat");
            locationView.setText(message);
            message = message.replace("lat/lng: (", "");
            message = message.replace(")", "");
            String[] longlat = message.split(",");
            lati = longlat[0];
            lng = longlat[1];
            locationView.setText(lati+ "," + lng);
        }
    }
}