package com.example.khotiun.ekaterinoslav;

import android.support.v4.app.Fragment;

/**
 * Created by hotun on 24.07.2017.
 */

public class PlaceListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return PlaceListFragment.newInstance();
    }
}
