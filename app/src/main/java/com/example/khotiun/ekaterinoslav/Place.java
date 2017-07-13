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
    Calendar dateCreate;
    Location mLocation;
    Architect mArchitect;
    List<String> mOldPhotos;
    List<String> mNewPhotos;
    String source;

    public Place() {
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

    public Calendar getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Calendar dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
