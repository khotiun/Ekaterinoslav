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
                            } else if (position == 2) {
                                Intent intent = ArchitectListActivity.newIntent(MapActivity.this);
                                startActivity(intent);
                            } else if (position == 3) {
                                Intent intent = HomeActivity.newIntent(MapActivity.this);
                                startActivity(intent);
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

    private void addPlace0() {
        List <Integer> architectPlaces = new ArrayList<>();
        architectPlaces.add(1);
        Architect architect = new Architect(
                0,
                "Захаров Андриан Дмитриевич",
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
                "8 августа 1761",
                "https://upload.wikimedia.org/wikipedia/commons/4/4e/Shukin-Zaharov.jpg?uselang=ru",
                architectPlaces,
                "https://ru.wikipedia.org/wiki/%D0%97%D0%B0%D1%85%D0%B0%D1%80%D0%BE%D0%B2,_%D0%90%D0%BD%D0%B4%D1%80%D0%B5%D1%8F%D0%BD_%D0%94%D0%BC%D0%B8%D1%82%D1%80%D0%B8%D0%B5%D0%B2%D0%B8%D1%87"

        );

        List <String> oldPhotos = new ArrayList<>();
        oldPhotos.add("https://www.shukach.com/sites/default/files/imagecache/node-gallery-display-clean/post_images/3_3.jpg");
        oldPhotos.add("https://www.shukach.com/sites/default/files/imagecache/node-gallery-display-clean/post_images/sobor.jpg");

        List <String> newPhotos = new ArrayList<>();
        newPhotos.add("https://oktv.ua/img/article/dnepr_preobrag1.jpg");
        newPhotos.add("http://lopata.in.ua/wp-content/uploads/2015/02/171.jpg");
        newPhotos.add("http://mistaua.com/filesup/dost_foto/853_1_2.jpg");
        newPhotos.add("http://eparhia.dp.ua/contents/images/1797752a0840d9a481.jpg");

        Place place = new Place(
                0,
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
                "http://gorod.dp.ua/out/attractions/oneplace/?place_id=1199",
                "площадь Соборная, 1"
        );
        mDatabaseReference.child("Place").child(0 + "").setValue(place);
        mDatabaseReference.child("Architect").child(0 + "").setValue(architect);
    }






    private void addPlace1() {
        List <Integer> architectPlaces = new ArrayList<>();
        architectPlaces.add(2);
        Architect architect = new Architect(
                1,
                "Миклашевский Александр Иванович",
                "Список зданий, спроектированных архитектором Александром Миклашевским в Екатеринославе, сравнительно невелик. Однако несколько его работ вполне заслуженно считаются знаковыми для города. Сегодня сохранилось два объекта, к которым имел отношение зодчий, – городской почтамт и жилой комплекс «Первозвановский». Главное творение Миклашевского – Вознесенская, или Казанская церковь – безжалостно уничтожено в 30-е годы XX века." +
                        "Точные даты рождения и смерти зодчего неизвестны. В разных источниках его называют то Александром, то Алексеем. Вероятно, архитектор принадлежал к знаменитому роду, давшему Екатеринославу одного губернатора и нескольких предводителей дворянства. Гражданский инженер Александр (?) Иванович Миклашевский работал «младшим архитектором» в строительном отделе Екатеринославского губернского правления с конца 1890-х до середины 1910-х годов. Воспоминания о нем оставил архитектор А. Юхилевич: «А. И. Миклашевский по душевному складу тихий, вежливый, являл собой тип старого русского интеллигента, что дополнялось и его внешним видом: невысокий, чеховская бородка, пенсне на шнурке, чесучовый пиджак и соломенная шляпа. В таком, примерно, виде я однажды узрел его согбенную фигуру с кельмой (инструмент строителя) в руке возле входной двери в наш почтамт. Он чинил цементом что-то, начавшее деформироваться. Для меня это было неожиданно, но, зная его характер, я не удивился. В его большой квартире, в доме, построенном по его собственному проекту на пересечении улиц Комсомольской и Короленко, значительное место занимали «музейные» стеллажи с образчиками редких минералов. С большой увлеченностью он рассказывал мне о своей коллекции, собранной за многие годы. Это говорило о разносторонних интересах А. И. Миклашевского». («Днепр вечерний». – 1987. – 10 сент.). Несмотря на «тихий» характер, зодчему удалось найти свое место в насыщенной архитектурно-строительной жизни Екатеринослава начала XX века.",
                "Точная дата рождения не известна",
                "http://www.tourdnepr.com/images/stories/Famous_People/Melnikov_Avraham_Abram_1.jpg",
                architectPlaces,
                "http://www.realnest.com.ua/information/newspaper/2010/08/2061"

        );

        List <String> oldPhotos = new ArrayList<>();
        oldPhotos.add("http://www.tourdnepr.com/images/stories/the_work_of_architects/Ekaterynoslavsky_Post_Of_1.jpg");
        oldPhotos.add("http://dnepr.info/wp-content/uploads/2017/04/1910.jpg");

        List <String> newPhotos = new ArrayList<>();
        newPhotos.add("http://mistaua.com/filesup/dost_foto/802_1_2.jpg");
        newPhotos.add("http://visitdnipro.com/wp-content/uploads/2015/11/glavpochtamt.jpg");
        newPhotos.add("http://delmargroups.com/wp-content/themes/delmar/assets/img/recons/post-13.jpg");
        newPhotos.add("http://static.panoramio.com/photos/large/77338302.jpg");

        Place place = new Place(
                1,
                "Спасо-Преображенский кафедральный собор",
                "Одно из самых известных сохранившихся зданий Екатеринослава – городской почтамт (пр. Карла Маркса, 62). Новое здание «почтово-телеграфной конторы» было построено в 1904-1905 гг. возле старого здания середины XIX в. Здание почтамта выделялось импозантной архитектурой, а своим обликом напоминало здания Центральной Европы. Двухэтажный основной объем акцентирован сильно выступающей частью с островерхой башней. Проект выполнил петербургский архитектор В. Бочаров. Миклашевский осуществлял общий надзор за строительством, а также выполнил проект интерьера операционного зала. Огромный свод зала украшен сочным декором в стиле модерн, с растительными мотивами. Возможно, Миклашевский спроектировал и декор лестницы почтамта, выполненной в одном стиле с залом. Прекрасно сохранившийся интерьер почтамта – ценнейший памятник интерьеров екатеринославского модерна. Конкуренцию ему может составить только зал заседаний Городской Думы (ныне училище культуры, пр. К. Маркса, 49).",
                new PlaceLocation(48.467789, 35.040961),
                architect,
                oldPhotos,
                newPhotos,
                "http://www.realnest.com.ua/information/newspaper/2010/08/2061",
                "проспект Дмитра Яворницького 62 (пр. Карла Маркса)"
        );
        mDatabaseReference.child("Place").child(1 + "").setValue(place);
        mDatabaseReference.child("Architect").child(1 + "").setValue(architect);
    }
}
