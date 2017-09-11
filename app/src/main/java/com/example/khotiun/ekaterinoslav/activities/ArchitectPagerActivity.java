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
import com.example.khotiun.ekaterinoslav.fragments.ArchitectFragment;
import com.example.khotiun.ekaterinoslav.model.Architect;
import com.example.khotiun.ekaterinoslav.model.ArchitectLab;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by hotun on 03.07.2017.
 */

public class ArchitectPagerActivity extends AppCompatActivity {
    private static final String EXTRA_ARCHITECT_ID = "com.example.khotiun.ekaterinoslav.architect_id";
    private ViewPager mViewPager;
    private List<Architect> mArchitects;

    public static Intent newIntent(Context packageContext, int architectId) {
        Intent intent = new Intent(packageContext, ArchitectPagerActivity.class);
        intent.putExtra(EXTRA_ARCHITECT_ID, architectId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_architect_pager);

        int architectId = getIntent().getIntExtra(EXTRA_ARCHITECT_ID, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//добавить кнопку назад
        getSupportActionBar().setDisplayShowHomeEnabled(true);//сделать кнопку видимо

        mViewPager = (ViewPager) findViewById(R.id.activity_architect_pager_view_pager);
        mArchitects = ArchitectLab.getArchitectLab().getArchitects();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Architect architect = mArchitects.get(position);
                return ArchitectFragment.newInstance(architect.getId());
            }

            @Override
            public int getCount() {
                return mArchitects.size();
            }
        });
        for (int i = 0; i < mArchitects.size(); i++) {
            if (mArchitects.get(i).getId() == (architectId)) {
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            case R.id.action_other_about_app:
                Intent aboutIntent = AboutActivity.newIntent(this);
                startActivity(aboutIntent);
                return true;
            case R.id.action_other_exit_login_out:
                FirebaseAuth.getInstance().signOut();//инициализация обьекта
                LoginManager.getInstance().logOut();//выход с учетной записи фейсбук
                Intent intent = SelectionSignInActivity.newIntent(this);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_other_exit_app:
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
