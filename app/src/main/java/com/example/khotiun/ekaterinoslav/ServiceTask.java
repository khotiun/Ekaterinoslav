package com.example.khotiun.ekaterinoslav;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.khotiun.ekaterinoslav.model.Place;
import com.example.khotiun.ekaterinoslav.model.PlaceLab;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hotun on 28.08.2017.
 */

public class ServiceTask extends IntentService {
    private static final String TAG = "ServiceTask";

    public ServiceTask() {
        super("MyServiceTask");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();//получаем экземляр FirebaseDatabase и из него ссылку на базу данных
        mDatabaseReference.child("Place").addValueEventListener(new ValueEventListener() {//устанавливаем слушателя на данные, которые хронятся по ссылке
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Place>> t = new GenericTypeIndicator<ArrayList<Place>>() {};
                List<Place> places = dataSnapshot.getValue(t);//DataSnapshot - содержит данные из нашей базы данных
                Log.d(TAG, places.toString());
                Log.d(TAG, 1111 + "");
                PlaceLab placeLab = PlaceLab.getPlaceLab(ServiceTask.this);
                placeLab.addPlaceList(places);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
}