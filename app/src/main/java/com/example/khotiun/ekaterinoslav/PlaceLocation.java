package com.example.khotiun.ekaterinoslav;

/**
 * Created by hotun on 27.08.2017.
 */

public class PlaceLocation {

    double longitude;
    double latitude;

    public PlaceLocation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
