package com.example.khotiun.ekaterinoslav;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import static android.R.attr.fragment;

/**
 * Created by hotun on 30.07.2017.
 */

public class MainActivity extends FragmentActivity {

    private SelectionSignInFragment mSelectionSignInFragment;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mSelectionSignInFragment = new SelectionSignInFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, mSelectionSignInFragment).commit();
        } else {
            // Or set the fragment from restored state info
            mSelectionSignInFragment = (SelectionSignInFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (mSelectionSignInFragment != null) {
//            mSelectionSignInFragment.onActivityResult(requestCode, resultCode, data);
//        } else {
//            mSelectionSignInFragment = (SelectionSignInFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//            mSelectionSignInFragment.onActivityResult(requestCode, resultCode, data);
//        }
//
//    }
}
