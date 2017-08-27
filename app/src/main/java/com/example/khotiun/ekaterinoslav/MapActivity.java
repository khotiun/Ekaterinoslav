package com.example.khotiun.ekaterinoslav;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
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

    //представляет внешний вид окна активити со всем его оформлением и содержимым

    private int selectedDrawerItem = 0;//пункт меню по умолчанию

    public static Intent newIntent(Context context) {
        return new Intent(context, MapActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

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
                        if (drawerItem != null) {
                            if (position == 1) {
                                addPlace1();
                                Intent intent = PlaceListActivity.newIntent(MapActivity.this);
                                startActivity(intent);
                            } else if (position == 3) {
                                Intent intent = ActivityHome.newIntent(MapActivity.this);
                                startActivity(intent);
                                //вызов активити about
                            } else if (position == 5) {
                                Intent aboutIntent = ActivityAbout.newIntent(MapActivity.this);
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

    private void addPlace1() {
        List<String> architectSources = new ArrayList<>();
        architectSources.add("https://ru.wikipedia.org/wiki/%D0%97%D0%B0%D1%85%D0%B0%D1%80%D0%BE%D0%B2,_%D0%90%D0%BD%D0%B4%D1%80%D0%B5%D1%8F%D0%BD_%D0%94%D0%BC%D0%B8%D1%82%D1%80%D0%B8%D0%B5%D0%B2%D0%B8%D1%87");
        List <Integer> architectPlaces = new ArrayList<>();
        architectPlaces.add(1);
        Architect architect = new Architect(
                "Андриан Дмитриевич Захаров",
                "Родился 8 августа 1761 года в семье мелкого служащего Адмиралтейств-коллегии. В раннем возрасте был отдан отцом в художественное училище при петербургской Академии Художеств, где проучился до 1782 года. Его учителями были А. Ф. Кокоринов, И. Е. Старов и Ю. М. Фельтен. В 1778 году Андреян Захаров получил серебряную медаль за проект загородного дома, в 1780 году — Большую серебряную медаль за «архитектурную композицию, представляющую дом принцев». При окончании училища получил большую золотую медаль и право на пенсионерскую поездку за границу для продолжения образования. Продолжал учиться в Париже с 1782 года по 1786 год у Ж. Ф. Шальгрена.\n" +
                        "\n" +
                        "В 1786 году вернулся в Петербург и начал работать преподавателем в Академии художеств, одновременно начав заниматься проектированием. Через некоторое время Захарова назначили архитектором всех недостроенных строений Академии Художеств.\n" +
                        "\n" +
                        "После этого он работал в Санкт-Петербурге, достиг звания главного архитектора Морского ведомства.\n" +
                        "\n" +
                        "С 1787 года Захаров преподавал в Академии Художеств, среди его учеников был архитектор А. И. Мельников.\n" +
                        "\n" +
                        "С 1794 года Захаров стал академиком петербургской Академии Художеств.\n" +
                        "\n" +
                        "В конце 1799 года указом Павла I Захаров был назначен главным архитектором Гатчины, где проработал почти два года. " +
                        "По его проекту построен в 1835 г. кафедральный Спасо-Преображенский собор в Екатеринославе (Днепропетровске). ",
                new GregorianCalendar(1761, 8, 8),
                "https://upload.wikimedia.org/wikipedia/commons/4/4e/Shukin-Zaharov.jpg?uselang=ru",
                architectPlaces,
                architectSources

        );

        List <String> oldPhotos = new ArrayList<>();
        oldPhotos.add("https://www.shukach.com/sites/default/files/imagecache/node-gallery-display-clean/post_images/3_3.jpg");
        oldPhotos.add("https://www.shukach.com/sites/default/files/imagecache/node-gallery-display-clean/post_images/sobor.jpg");

        List <String> newPhotos = new ArrayList<>();
        newPhotos.add("https://oktv.ua/img/article/dnepr_preobrag1.jpg");
        newPhotos.add("http://lopata.in.ua/wp-content/uploads/2015/02/171.jpg");
        newPhotos.add("http://mistaua.com/filesup/dost_foto/853_1_2.jpg");
        newPhotos.add("http://eparhia.dp.ua/contents/images/1797752a0840d9a481.jpg");

        List <String> placeSourses = new ArrayList<>();
        placeSourses.add("http://gorod.dp.ua/out/attractions/oneplace/?place_id=1199");
        placeSourses.add("https://www.shukach.com/sites/default/files/imagecache/node-gallery-display-clean/post_images/3_3.jpg");
        placeSourses.add("https://www.shukach.com/sites/default/files/imagecache/node-gallery-display-clean/post_images/sobor.jpg");
        placeSourses.add("https://oktv.ua/img/article/dnepr_preobrag1.jpg");
        placeSourses.add("http://lopata.in.ua/wp-content/uploads/2015/02/171.jpg");
        placeSourses.add("http://mistaua.com/filesup/dost_foto/853_1_2.jpg");
        placeSourses.add("http://eparhia.dp.ua/contents/images/1797752a0840d9a481.jpg");

        Place place = new Place(
                1,
                "Спасо-Преображенский кафедральный собор",
                "Наиболее замечательным сооружением Екатеринослава эпохи классицизма является Преображенский собор, увенчавший место закладки города.\n" +
                        "Проект собора, разработанный и утвержденный в 1786 г., не был осуществлен. Не исключено, что И. Старов, занимаясь строительством Потемкинского дворца и генеральным планом Екатеринослава, предполагал построить городской кафедральный собор по собственному проекту. Ведь в те времена близилось окончание строительства одного из наиболее выдающихся и престижных сооружений мастера — Троицкого собора Александро-Невской лавры в Петербурге и логично было продолжить подобную работу в Екатеринославе. На эту мысль наводит находка в архиве Научно-исследовательского института теории, истории и перспективных проблем советской архитектуры в г. Киеве неизвестного офорта 1790-х годов, изображающего (согласно позднейшей атрибутации) интерьер собора в Екатеринославе. На рисунке видим центральный неф грандиозного многокупольного сооружения с базиликальным раскрытием внутреннего пространства. От боковых нефов он отделен торжественным рядом спаренных колонн римско-коринфского ордера. На пилонах входной части храма расположены монументальные горельефы.\n" +
                        "Особенности построения интерьера сооружения и графического дополнения листа во многом напоминают изображения интерьеров Троицкого собора Александро-Невской лавры, выполненные в свое время художником Ф. Даниловым, коллегой Огарева по строительству петербургского собора. Однако первоначальные проекты екатеринославского собора не были осуществлены.\n" +
                        "\n" +
                        "С 1930-го по 1988 г службы в Соборе не велись. С 1975 по 1988 гг. в помещении храма размещался музей религии и атеизма. \n" +
                        "\n" +
                        "Богослужения и требы в соборе совершаются ежедневно.\n" +
                        "\n" +
                        "Престольный праздник – в День Преображения Господня, 19 августа.",
                new PlaceLocation(48.458442, 35.066612),
                architect,
                oldPhotos,
                newPhotos,
                placeSourses,
                "площадь Соборная, 1"
        );


    }

}
