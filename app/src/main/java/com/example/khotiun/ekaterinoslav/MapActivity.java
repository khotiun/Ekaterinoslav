package com.example.khotiun.ekaterinoslav;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mikepenz.iconics.utils.Utils;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import static android.R.attr.fragment;

public class MapActivity extends AppCompatActivity {

    private Drawer drawer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        AccountHeader accountHeader = new AccountHeaderBuilder()//шапка панели навигации
                .withActivity(this)
//                .withHeaderBackground(R.drawable.header)
                .build();

        drawer = new DrawerBuilder(this)
                .withActivity(MapActivity.this)
                .withAccountHeader(accountHeader)
                .withDisplayBelowStatusBar(true)//панель навигации под статус баром
//                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)//анимация кнопки на тул баре
                .withSavedInstance(savedInstanceState)//сохранение состояния
//                .addDrawerItems(primaryDrawerItems)//пункты меню
                .addStickyDrawerItems(
                        new SecondaryDrawerItem()//секцция для второстепенного меню
//                                .withName(getString(R.string.about))
//                                .withIdentifier(channelId.length - 1)//id
                                .withSelectable(false)//свойство выделение при нажатии сбрасываем
                )
                //слушатель для нажатия пунктов списка
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

//                        selectedDrawerItem = position;
//                        if (drawerItem != null) {
//                            if (drawerItem.getIdentifier() >= 0 && selectedDrawerItem != -1) {
//
//                                setToolbarAndSelectedDrawerItem(
//                                        channelNames[selectedDrawerItem-1],
//                                        (selectedDrawerItem-1)
//                                );
//                                //действия при нажатии на айтем
//                                Bundle bundle = new Bundle();
//                                //ложим в бандл тип видео
//                                bundle.putString(Utils.TAG_VIDEO_TYPE,
//                                        videoTypes[selectedDrawerItem-1]);
//                                //ложим в бандл id видео
//                                bundle.putString(Utils.TAG_CHANNEL_ID,
//                                        channelId[selectedDrawerItem-1]);
//
//                                fragment = new FragmentChannelVideo();
//                                fragment.setArguments(bundle);
//
//                                getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.fragment_container, fragment)
//                                        .commit();
//                                //вызов активити about
//                            } else if (selectedDrawerItem == -1) {
//                                Intent aboutIntent = new Intent(getApplicationContext(),
//                                        ActivityAbout.class);
//                                startActivity(aboutIntent);
//                                overridePendingTransition(R.anim.open_next, R.anim.close_main);
//                            }
//                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)//показ навигации при первом запуске приложения
                .build();

    }
}
