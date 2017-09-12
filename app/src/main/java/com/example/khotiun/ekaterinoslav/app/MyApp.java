package com.example.khotiun.ekaterinoslav.app;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.ResultReceiver;
import android.util.Log;

import com.example.khotiun.ekaterinoslav.activities.SplashActivity;
import com.example.khotiun.ekaterinoslav.model.Architect;
import com.example.khotiun.ekaterinoslav.model.ArchitectLab;
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

public class MyApp extends Application {
    private static final String TAG = "MyApp";
    DatabaseReference mDatabaseReference;//получаем экземляр FirebaseDatabase и из него ссылку на базу данных

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();//получаем экземляр FirebaseDatabase и из него ссылку на базу данных
        new Loading().execute();
    }

    private void setPlaceList(DatabaseReference mDatabaseReference) {
        mDatabaseReference.child("Place").addValueEventListener(new ValueEventListener() {//устанавливаем слушателя на данные, которые хронятся по ссылке
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange");
                GenericTypeIndicator<ArrayList<Place>> t = new GenericTypeIndicator<ArrayList<Place>>() {
                };
                List<Place> places = dataSnapshot.getValue(t);//DataSnapshot - содержит данные из нашей базы данных
                for (Place place : places) {
                    if (place == null) {
                        Log.d(TAG, "place == null");
                    } else {
                        Log.d(TAG, place.getId() + "");
                    }
                }
                PlaceLab placeLab = PlaceLab.getPlaceLab();
                placeLab.addPlaceList(places);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void setArchitectList(DatabaseReference mDatabaseReference) {
        mDatabaseReference.child("Architect").addValueEventListener(new ValueEventListener() {//устанавливаем слушателя на данные, которые хронятся по ссылке
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Architect>> t = new GenericTypeIndicator<ArrayList<Architect>>() {
                };
                List<Architect> architects = dataSnapshot.getValue(t);//DataSnapshot - содержит данные из нашей базы данных
                ArchitectLab architectLab = ArchitectLab.getArchitectLab();
                architectLab.addArchitectList(architects);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public class Loading extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            setPlaceList(mDatabaseReference);
            setArchitectList(mDatabaseReference);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(SplashActivity.BROADCAST_ACTION);
            sendBroadcast(intent);

        }
    }
}