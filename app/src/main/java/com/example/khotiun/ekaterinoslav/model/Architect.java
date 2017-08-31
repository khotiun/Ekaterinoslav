package com.example.khotiun.ekaterinoslav.model;

import java.util.List;

/**
 * Created by hotun on 12.07.2017.
 */

public class Architect {
    private int mId;
    private String mName;
    private String mBiography;
    private String mDate;
    private String mPhotos;
    private List<Integer> mPlaces;
    private String mSource;

    public Architect() {
    }

    public Architect(int id, String name, String biography, String date, String photos, List<Integer> places, String source) {
        mId = id;
        mName = name;
        mBiography = biography;
        mDate = date;
        mPhotos = photos;
        mPlaces = places;
        mSource = source;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
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
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
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

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }
}
