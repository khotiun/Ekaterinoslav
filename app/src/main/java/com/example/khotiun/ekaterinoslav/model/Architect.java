package com.example.khotiun.ekaterinoslav.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by hotun on 12.07.2017.
 */

public class Architect {
    private String mName;
    private String mBiography;
    private String date;
    private String mPhotos;
    private List<Integer> mPlaces;
    private String mSource;

    public Architect() {
    }

    public Architect(String name, String biography, String date, String photos, List<Integer> places, String source) {
        mName = name;
        mBiography = biography;
        this.date = date;
        mPhotos = photos;
        mPlaces = places;
        mSource = source;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getSources() {
        return mSource;
    }

    public void setSources(String sources) {
        mSource = sources;
    }
}
