package com.example.khotiun.ekaterinoslav.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.khotiun.ekaterinoslav.fragments.PlaceListFragment;

/**
 * Created by hotun on 24.07.2017.
 */

public class PlaceListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, PlaceListActivity.class);
    }
    @Override
    protected Fragment createFragment() {
        return PlaceListFragment.newInstance();
    }
}
