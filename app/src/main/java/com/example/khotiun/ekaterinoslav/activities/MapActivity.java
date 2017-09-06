package com.example.khotiun.ekaterinoslav.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.khotiun.ekaterinoslav.R;
import com.example.khotiun.ekaterinoslav.fragments.ArchitectListFragment;
import com.example.khotiun.ekaterinoslav.fragments.MapFragment;
import com.example.khotiun.ekaterinoslav.fragments.PlaceListFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

public class MapActivity extends AppCompatActivity {
    private static final String TAG = "MapActivity";
    private static final String MAPVISIBLE = "mapVisible";
    private static final String MAPGONE = "mapGone";
    private Toolbar mToolbar;
    //отступ между списками в альбомной ориентации
    private Drawer mDrawer = null;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabaseReference;
    private FrameLayout mFrameLayoutMap;
    private FrameLayout mFrameLayoutFragmets;
    private int selectedDrawerItem = 0;//пункт меню по умолчанию


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
                    Intent intent = MainActivity.newIntent(MapActivity.this);
                    startActivity(intent);
                }
            }
        };
        FirebaseUser user = mAuth.getCurrentUser();
        Log.d(TAG, user.toString());
        mFrameLayoutMap = (FrameLayout) findViewById(R.id.activity_map_container);
        mFrameLayoutFragmets = (FrameLayout) findViewById(R.id.activity_map_fragments_container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentMap = fm.findFragmentById(R.id.activity_map_container);//если фрагмент находится в списке, нпример когда происходит поворот устройства

        if (fragmentMap == null) {//если фрагмент отсутствует
            fragmentMap = MapFragment.newInstance();//создание фрагмента
            fm.beginTransaction().add(R.id.activity_map_container, fragmentMap, MapFragment.TAG).commit();//начало транзакции и добавление фрагмента в список FragmentManager
        }
        final PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.Map);//свойство выделение при нажатии сбрасываем
        final PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.list_place);
        final PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.list_arhitect);
        final PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.video_ekaterinoslav);
        final PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(6).withName(R.string.about);
        final PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(7).withName(R.string.exit);

        DrawerImageLoader.init(new  AbstractDrawerImageLoader () {
            @Override
            public  void  set (ImageView imageView , Uri uri , Drawable  placeholder ) {
                Picasso.with(imageView . getContext ()).load (uri).placeholder (placeholder) .into(imageView);
            }

            @Override
            public  void  cancel (ImageView  imageView ) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

        });

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        new ProfileDrawerItem().withName(user.getDisplayName()).withEmail(user.getEmail()).withIcon(user.getPhotoUrl())
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(MapActivity.this)
                .withAccountHeader(headerResult)
                .withDisplayBelowStatusBar(true)//панель навигации под статус баром
                .withToolbar(mToolbar)
                .withActionBarDrawerToggleAnimated(true)//анимация кнопки на тул баре
                .withSavedInstance(savedInstanceState)//сохранение состояния
                .addDrawerItems(item1, item2, item3, item4, new DividerDrawerItem(), item5, item6)//пункты меню
                //слушатель для нажатия пунктов списка
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            if (position == item1.getIdentifier()) {
                                if (mFrameLayoutMap.getVisibility() == View.GONE) {
                                    updateUI(MAPVISIBLE);
                                }
                            } else if (position == item2.getIdentifier()) {
                                FragmentManager fm = getSupportFragmentManager();
                                Fragment fragment = PlaceListFragment.newInstance();
                                fm.beginTransaction().replace(R.id.activity_map_fragments_container, fragment).commit();
                                updateUI(MAPGONE);
                            } else if (position == item3.getIdentifier()) {
                                FragmentManager fm = getSupportFragmentManager();
                                Fragment fragment = ArchitectListFragment.newInstance();
                                fm.beginTransaction().replace(R.id.activity_map_fragments_container, fragment).commit();
                                updateUI(MAPGONE);
                            } else if (position == item4.getIdentifier()) {
                                Intent intent = HomeActivity.newIntent(MapActivity.this);
                                startActivity(intent);
                            } else if (position == item5.getIdentifier()) {
                                Intent aboutIntent = AboutActivity.newIntent(MapActivity.this);
                                startActivity(aboutIntent);
                                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                            } else if (position == item6.getIdentifier()) {
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

    private void updateUI(String namePosition) {
        if (namePosition.equals(MAPGONE)) {
            mFrameLayoutFragmets.setVisibility(View.VISIBLE);
            mFrameLayoutMap.setVisibility(View.GONE);
        } else if (namePosition.equals(MAPVISIBLE)){
            mFrameLayoutFragmets.setVisibility(View.GONE);
            mFrameLayoutMap.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            if (mFrameLayoutMap.getVisibility() == View.GONE) {
                updateUI(MAPVISIBLE);
            } else {
                super.onBackPressed();
            }
        }
    }


}
