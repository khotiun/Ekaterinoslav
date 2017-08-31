package com.example.khotiun.ekaterinoslav.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hotun on 28.08.2017.
 */
//класс на основе синглтона
public class PlaceLab {
    private static PlaceLab sPlaceLab;
    private List<Place> mPlaces;

    public static PlaceLab getPlaceLab() {
        if (sPlaceLab == null) {
            sPlaceLab = new PlaceLab();
        }
        return sPlaceLab;
    }

    private PlaceLab() {
        mPlaces = new ArrayList<>();
    }

    public List<Place> getPlaces() {
        return mPlaces;
    }

    public Place getPlace(int placeId) {
        for (Place place : mPlaces) {
            if (place.getId() == placeId) {
                return place;
            }
        }
        return null;
    }

    public void addPlaceList(List<Place> places) {
        this.mPlaces.addAll(places);
    }
}
