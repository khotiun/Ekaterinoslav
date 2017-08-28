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
    private  List<Place> places;
    private Context mContext;

    public static PlaceLab getPlaceLab(Context context) {
        if (sPlaceLab == null){
            sPlaceLab = new PlaceLab(context);
        }
        return sPlaceLab;
    }

    private PlaceLab(Context context) {
        mContext = context.getApplicationContext();
        places = new ArrayList<>();
    }

    public List<Place> getPlace() {
        return places;
    }

    public void addPlaceList(List <Place> places){

    }
}
