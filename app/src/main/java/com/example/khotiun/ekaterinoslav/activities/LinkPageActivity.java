package com.example.khotiun.ekaterinoslav.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.example.khotiun.ekaterinoslav.fragments.LinkPageFragment;

/**
 * Created by hotun on 13.08.2017.
 */

public class LinkPageActivity extends SingleFragmentActivity {
    private static final String LINK_STRING = "link_string";
    public static Intent newIntent(Context context, String photoPageUri) {
        Intent i = new Intent(context, LinkPageActivity.class);
        i.putExtra(LINK_STRING, photoPageUri);//данные с которыми что-то надо будет сделать
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return LinkPageFragment.newInstance(getIntent().getStringExtra(LINK_STRING));
    }
}
