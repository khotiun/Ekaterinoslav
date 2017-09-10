package com.example.khotiun.ekaterinoslav.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.khotiun.ekaterinoslav.R;
import com.example.khotiun.ekaterinoslav.activities.AboutActivity;
import com.example.khotiun.ekaterinoslav.activities.SelectionSignInActivity;
import com.example.khotiun.ekaterinoslav.model.Place;
import com.example.khotiun.ekaterinoslav.model.PlaceLab;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by hotun on 18.08.2017.
 */

public class MapFragment extends SupportMapFragment {
    public static final String TAG = "MapFragment";
    private GoogleApiClient mClient;
    private GoogleMap mMap;
    private Location mCurrentLocation;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);//удержание фрагмента
        setHasOptionsMenu(true);// должен получить вызов onCreateOptionsMenu(…).
        //Когда клиент будет создан, к нему необходимо подключиться. Google рекомендует всегда подключаться к клиенту в методе onStart()
        // и отключаться в onStop(). Вызов connect() для клиента также изменит возможности кнопки меню, поэтому мы вызовем invalidateOptionsMenu()
        // для обновления ее визуального состояния. (Позднее этот метод будет вызван еще один раз: после того, как мы получим информацию о создании подключения.)
        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)//конкретное апи которое будет использоваться
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                    }
                })
                .build();

        //полностью соответствует его имени: метод асинхронно получает объект карты
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                updateUI();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().invalidateOptionsMenu();//перерисовать меню
        mClient.connect();//подключить клиента
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();//отключение клиента
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_map, menu);

        MenuItem searchItem = menu.findItem(R.id.action_locate);
        searchItem.setEnabled(mClient.isConnected());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_locate:
                findMe();
                return true;
            case R.id.action_about_app:
                Intent aboutIntent = AboutActivity.newIntent(getActivity());
                startActivity(aboutIntent);
                return true;
            case R.id.action_exit_login_out:
                FirebaseAuth.getInstance().signOut();//инициализация обьекта
                Intent intent = SelectionSignInActivity.newIntent(getActivity());
                startActivity(intent);
                getActivity().finish();
                return true;
            case R.id.action_exit_app:
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void findMe() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);//сколько раз обновлять
        request.setInterval(0);//бновления должны происходить быстро
        //отправка запроса и прослушивание возвращаемых объектов Location
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.i(TAG, "Got a fix: " + location);
                        mCurrentLocation = location;
                        updateUI();
                    }
                });
    }


    private void updateUI() {
        if (mMap == null || mCurrentLocation == null) {
            return;
        }
        mMap.clear();//очищение карты от маркеров
        for (Place place : PlaceLab.getPlaceLab().getPlaces()) {
            LatLng itemPoint = new LatLng(place.getLocation().getLongitude(), place.getLocation().getLatitude());
            LatLng myPoint = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

            MarkerOptions itemMarker = new MarkerOptions()
                    .title(place.getTitle())
                    .snippet(place.getAddress())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(itemPoint);

            MarkerOptions myMarker = new MarkerOptions().position(myPoint);
            myMarker.title("Вы здесь");

            //При вызове addMarker(MarkerOptions) объект GoogleMap строит экземпляр Marker и добавляет его на карту
            mMap.addMarker(itemMarker);
            mMap.addMarker(myMarker);
            //В данном случае мы создаем обновление, которое наводит камеру на конкретный объект LatLngBounds.
            LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(itemPoint)
                    .include(myPoint)
                    .build();

            int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
            //тобы перемещать GoogleMap по карте, мы строим объект CameraUpdate
            CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
            mMap.animateCamera(update);//бновление карты с помощью анимации
        }


    }


}
