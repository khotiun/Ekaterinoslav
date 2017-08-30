package com.example.khotiun.ekaterinoslav.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hotun on 28.08.2017.
 */
//класс на основе синглтона
public class PlaceLab {
    private static PlaceLab sPlaceLab;
    private List<Place> places;

    public static PlaceLab getPlaceLab() {
        if (sPlaceLab == null) {
            sPlaceLab = new PlaceLab();
        }
        return sPlaceLab;
    }

    private PlaceLab() {
        places = new ArrayList<>();
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void addPlaceList(List<Place> places) {
        this.places.addAll(places);
    }
}
