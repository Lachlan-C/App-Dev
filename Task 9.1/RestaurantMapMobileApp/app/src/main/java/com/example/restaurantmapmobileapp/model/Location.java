package com.example.restaurantmapmobileapp.model;

public class Location {
    private int location_id;
    private String name;
    private float lng;
    private float lat;

    public Location() {
    }

    public Location(String _name, float _lat, float _lng)
    {
        this.name = _name;
        this.lng = _lng;
        this.lat = _lat;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }
}


