package com.example.restaurantmapmobileapp;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.restaurantmapmobileapp.data.DatabaseHelper;
import com.example.restaurantmapmobileapp.model.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.restaurantmapmobileapp.databinding.ActivityShowAllPlacesBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShowAllPlaces extends FragmentActivity implements OnMapReadyCallback {
    DatabaseHelper db;
    List<Location> locationslist;
    private GoogleMap mMap;
    private ActivityShowAllPlacesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowAllPlacesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadData();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void loadData()
    {
        db = new DatabaseHelper(this);
        locationslist = db.fetchAllLocations();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (int i=0; i<locationslist.size(); i++) {
            LatLng marker = new LatLng(locationslist.get(i).getLat(),locationslist.get(i).getLng());
            mMap.addMarker(new MarkerOptions().position(marker).title(locationslist.get(i).getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
        }
    }
}