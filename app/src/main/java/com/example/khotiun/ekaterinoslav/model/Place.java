package com.example.khotiun.ekaterinoslav.model;

import java.util.List;

/**
 * Created by hotun on 12.07.2017.
 */

public class Place {
    private int mId;
    private String mTitle;
    private String mDescription;
    private PlaceLocation mLocation;
    private Architect mArchitect;
    private List<String> mOldPhotos;
    private List<String> mNewPhotos;
    private String mSource;
    private String address;

    public Place() {
    }

    public Place(int id, String title, String description, PlaceLocation location, Architect architect, List<String> oldPhotos, List<String> newPhotos, String source, String address) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mLocation = location;
        mArchitect = architect;
        mOldPhotos = oldPhotos;
        mNewPhotos = newPhotos;
        mSource = source;
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

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
