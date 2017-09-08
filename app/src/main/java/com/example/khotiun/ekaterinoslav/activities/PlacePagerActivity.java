package com.example.khotiun.ekaterinoslav.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.khotiun.ekaterinoslav.R;
import com.example.khotiun.ekaterinoslav.fragments.PlaceFragment;
import com.example.khotiun.ekaterinoslav.model.Place;
import com.example.khotiun.ekaterinoslav.model.PlaceLab;

import java.util.List;

/**
 * Created by hotun on 03.07.2017.
 */

public class PlacePagerActivity extends AppCompatActivity {
    private static final String EXTRA_PLACE_ID = "com.example.khotiun.ekaterinoslav.place_id";
    private ViewPager mViewPager;
    private List<Place> mPlaces;

    public static Intent newIntent(Context packageContext, int placeId) {
        Intent intent = new Intent(packageContext, PlacePagerActivity.class);
        intent.putExtra(EXTRA_PLACE_ID, placeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_pager);

        int placeId = getIntent().getIntExtra(EXTRA_PLACE_ID, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//добавить кнопку назад
        getSupportActionBar().setDisplayShowHomeEnabled(true);//сделать кнопку видимой

        mViewPager = (ViewPager) findViewById(R.id.activity_place_pager_view_pager);
        mPlaces = PlaceLab.getPlaceLab().getPlaces();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Place place = mPlaces.get(position);
                return PlaceFragment.newInstance(place.getId());
            }

            @Override
            public int getCount() {
                return mPlaces.size();
            }
        });
        for (int i = 0; i < mPlaces.size(); i++) {
            if (mPlaces.get(i).getId() == (placeId)) {
                mViewPager.setCurrentItem(i);//с какого элемента будет работать viewPager
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragment_place, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
          finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
