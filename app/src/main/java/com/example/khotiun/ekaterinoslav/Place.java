package com.example.khotiun.ekaterinoslav;

import android.location.Location;

import java.util.Calendar;
import java.util.List;

/**
 * Created by hotun on 12.07.2017.
 */

public class Place {
    int mId;
    String mTitle;
    String mDescription;
    PlaceLocation mLocation;
    Architect mArchitect;
    List<String> mOldPhotos;
    List<String> mNewPhotos;
    List<String> source;
    String address;

    public Place() {
    }

    public Place(int id, String title, String description, PlaceLocation location, Architect architect, List<String> oldPhotos, List<String> newPhotos, List<String> source, String address) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mLocation = location;
        mArchitect = architect;
        mOldPhotos = oldPhotos;
        mNewPhotos = newPhotos;
        this.source = source;
        this.address = address;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public PlaceLocation getLocation() {
        return mLocation;
    }

    public void setLocation(PlaceLocation location) {
        mLocation = location;
    }

    public Architect getArchitect() {
        return mArchitect;
    }

    public void setArchitect(Architect architect) {
        mArchitect = architect;
    }

    public List<String> getOldPhotos() {
        return mOldPhotos;
    }

    public void setOldPhotos(List<String> oldPhotos) {
        mOldPhotos = oldPhotos;
    }

    public List<String> getNewPhotos() {
        return mNewPhotos;
    }

    public void setNewPhotos(List<String> newPhotos) {
        mNewPhotos = newPhotos;
    }

    public List<String> getSource() {
        return source;
    }

    public void setSource(List<String> source) {
        this.source = source;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
