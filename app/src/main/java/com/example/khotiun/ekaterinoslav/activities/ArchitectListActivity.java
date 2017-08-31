package com.example.khotiun.ekaterinoslav.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.khotiun.ekaterinoslav.fragments.ArchitectListFragment;
import com.example.khotiun.ekaterinoslav.fragments.PlaceListFragment;
import com.example.khotiun.ekaterinoslav.model.Architect;

/**
 * Created by hotun on 31.08.2017.
 */

public class ArchitectListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, ArchitectListActivity.class);
    }
    @Override
    protected Fragment createFragment() {
        return ArchitectListFragment.newInstance();
    }
}
