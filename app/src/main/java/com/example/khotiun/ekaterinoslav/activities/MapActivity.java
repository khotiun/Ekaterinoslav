package com.example.khotiun.ekaterinoslav.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.khotiun.ekaterinoslav.model.Architect;
import com.example.khotiun.ekaterinoslav.fragments.MapFragment;
import com.example.khotiun.ekaterinoslav.model.Place;
import com.example.khotiun.ekaterinoslav.model.PlaceLocation;
import com.example.khotiun.ekaterinoslav.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class MapActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    //отступ между списками в альбомной ориентации
    private Drawer mDrawer = null;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabaseReference;


    public static Intent newIntent(Context context) {
        return new Intent(context, MapActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();//инициализация обьекта
        //для того что бы слушать состояние пользователя
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {//срабатывает когда пользователь вошел или вышел
                if (firebaseAuth.getCurrentUser() == null) {
                    Log.d("wwwwwww", "11111");
                    Intent intent = MainActivity.newIntent(MapActivity.this);
                    startActivity(intent);
                }
            }
        };
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentMap = fm.findFragmentById(R.id.activity_map_container);//если фрагмент находится в списке, нпример когда происходит поворот устройства

        if (fragmentMap == null) {//если фрагмент отсутствует
            fragmentMap = MapFragment.newInstance();//создание фрагмента
            fm.beginTransaction().add(R.id.activity_map_container, fragmentMap).commit();//начало транзакции и добавление фрагмента в список FragmentManager
        }

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.list_place);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.list_arhitect);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.video_ekaterinoslav);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.about);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.exit);

        AccountHeader accountHeader = new AccountHeaderBuilder()//шапка панели навигации
                .withActivity(this)
//                .withHeaderBackground(R.drawable.header)
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(MapActivity.this)
                .withAccountHeader(accountHeader)
                .withDisplayBelowStatusBar(true)//панель навигации под статус баром
                .withToolbar(mToolbar)
                .withActionBarDrawerToggleAnimated(true)//анимация кнопки на тул баре
                .withSavedInstance(savedInstanceState)//сохранение состояния
                .addDrawerItems(item1, item2, item3, new DividerDrawerItem(), item4, item5)//пункты меню
                //слушатель для нажатия пунктов списка
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            if (position == 1) {
                                Intent intent = PlaceListActivity.newIntent(MapActivity.this);
                                startActivity(intent);
                            } else if (position == 3) {
                                Intent intent = HomeActivity.newIntent(MapActivity.this);
                                startActivity(intent);
                                //вызов активити about
                            } else if (position == 5) {
                                Intent aboutIntent = AboutActivity.newIntent(MapActivity.this);
                                startActivity(aboutIntent);
                                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                            } else if (position == 6) {
                                mAuth.signOut();
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)//показ навигации при первом запуске приложения
                .build();

    }

    //добавление и удаление слушателя
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
