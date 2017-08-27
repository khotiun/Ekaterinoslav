package com.example.khotiun.ekaterinoslav;

import java.util.Calendar;
import java.util.List;

/**
 * Created by hotun on 12.07.2017.
 */

public class Architect {
    String mName;
    String mBiography;
    Calendar date;
    String mPhotos;
    List <Integer> mPlaces;
    List <String> mSources;

    public Architect() {
    }

    public Architect(String name, String biography, Calendar date, String photos, List<Integer> places, List<String> sources) {
        mName = name;
        mBiography = biography;
        this.date = date;
        mPhotos = photos;
        mPlaces = places;
        mSources = sources;
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

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getPhotos() {
        return mPhotos;
    }

    public void setPhotos(String photos) {
        mPhotos = photos;
    }

    public List<Integer> getPlaces() {
        return mPlaces;
    }

    public void setPlaces(List<Integer> places) {
        mPlaces = places;
    }

    public List<String> getSources() {
        return mSources;
    }

    public void setSources(List<String> sources) {
        mSources = sources;
    }
}
