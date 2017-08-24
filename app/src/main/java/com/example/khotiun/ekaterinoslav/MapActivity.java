package com.example.khotiun.ekaterinoslav;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.khotiun.ekaterinoslav.fragments.FragmentChannelVideo;
import com.example.khotiun.ekaterinoslav.fragments.FragmentVideo;
import com.example.khotiun.ekaterinoslav.utils.Utils;
import com.google.android.youtube.player.YouTubePlayer;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MapActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    //отступ между списками в альбомной ориентации
    private Drawer mDrawer = null;

    //представляет внешний вид окна активити со всем его оформлением и содержимым

    private int selectedDrawerItem = 0;//пункт меню по умолчанию

    public static Intent newIntent(Context context) {
        return new Intent(context, MapActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
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
//                .addStickyDrawerItems(
//                        new SecondaryDrawerItem()//секцция для второстепенного меню
//                                .withName(getString(R.string.about))
//                                .withIdentifier(itemNamesUp.length - 2)//id
//                                .withSelectable(false)//свойство выделение при нажатии сбрасываем
//                                .withName(getString(R.string.exit))
//                                .withIdentifier(itemNamesUp.length - 1)//id
//                                .withSelectable(false)//свойство выделение при нажатии сбрасываем
//                )
                //слушатель для нажатия пунктов списка
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        selectedDrawerItem = position;
                        if (drawerItem != null) {
                            if (position == 1) {
                                Intent intent = PlaceListActivity.newIntent(MapActivity.this);
                                startActivity(intent);
                            } else if (position == 3) {
                                Intent intent = ActivityHome.newIntent(MapActivity.this);
                                startActivity(intent);
                                //вызов активити about
                            } else if (selectedDrawerItem == -1) {
//                                Intent aboutIntent = new Intent(getApplicationContext(),
//                                        ActivityAbout.class);
//                                startActivity(aboutIntent);
//                                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)//показ навигации при первом запуске приложения
                .build();

    }
}
