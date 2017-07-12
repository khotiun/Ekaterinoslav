package com.example.khotiun.ekaterinoslav;

import java.util.List;

/**
 * Created by hotun on 12.07.2017.
 */

public class Architect {
    String mName;
    String mBiography;
    Place mPlace;
    List<String> mPhotos;

    public Architect(String name, String biography, Place place, List<String> photos) {
        mName = name;
        mBiography = biography;
        mPlace = place;
        mPhotos = photos;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String biography) {
        mBiography = biography;
    }

    public Place getPlace() {
        return mPlace;
    }

    public void setPlace(Place place) {
        mPlace = place;
    }

    public List<String> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(List<String> photos) {
        mPhotos = photos;
    }
}
