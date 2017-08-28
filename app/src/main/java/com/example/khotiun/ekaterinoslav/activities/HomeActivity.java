package com.example.khotiun.ekaterinoslav.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.khotiun.ekaterinoslav.R;
import com.example.khotiun.ekaterinoslav.fragments.FragmentChannelVideo;
import com.example.khotiun.ekaterinoslav.fragments.FragmentVideo;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public final class HomeActivity extends AppCompatActivity implements YouTubePlayer.OnFullscreenListener,
        FragmentChannelVideo.OnVideoSelectedListener{

    //отступ между списками в альбомной ориентации
    private static final int LANDSCAPE_VIDEO_PADDING_DP = 5;
    //код запроса, для востановления ошибки после API
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private FragmentVideo fragmentVideo;
    //переменная для обработки полноэкранного состояния
    private boolean isFullscreen;

    private Toolbar toolbar;
    //представляет внешний вид окна активити со всем его оформлением и содержимым
    private View decorView;

    private String channelNames;

    private FrameLayout layoutList;

    private Fragment fragment;

    public static Intent newIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        layoutList = (FrameLayout) findViewById(R.id.fragment_container);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        decorView = getWindow().getDecorView();
        fragmentVideo = (FragmentVideo) getFragmentManager().findFragmentById(R.id.video_fragment_container);

        channelNames = getResources().getString(R.string.video_ekaterinoslav);
        toolbar.setTitle(channelNames);
        //нужен для проверки подключения ютуб апи в случае не удачи оповещает всплывающим сообщением
        checkYouTubeApi();

//        setToolbarAndSelectedDrawerItem(channelNames, 0);

        fragment = new FragmentChannelVideo();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    //нужен для проверки подключения ютуб апи в случае не удачи оповещает всплывающим сообщением
    private void checkYouTubeApi() {
        YouTubeInitializationResult errorReason =
                YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else if (errorReason != YouTubeInitializationResult.SUCCESS) {
            String errorMessage =
                    String.format(getString(R.string.error_player),
                            errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
    //вызывается при смене фрагментов
    private void setToolbarAndSelectedDrawerItem(String title, int selectedDrawerItem){
        //устанавливает заголовок окна
        toolbar.setTitle(title);
    }

    //создает меню доступное через 3 точки в тулбаре
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }
    //обрабатывает нажатие пунктов меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.menuAbout:
//                Intent aboutIntent = new Intent(getApplicationContext(),
//                        AboutActivity.class);
//                startActivity(aboutIntent);
//                overridePendingTransition(R.anim.open_next, R.anim.close_main);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //восстанавливает активити если пользователь нажал кнопку повтора попытки подключения
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            recreate();
        }
    }
    //отслеживает такие события как поворот экрана
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //происходит программная настройка макета для 3-х различных состояний
        layout();
    }

    @Override
    public void onFullscreen(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        //происходит программная настройка макета для 3-х различных состояний
        layout();
    }

    //происходит программная настройка макета для 3-х различных состояний
    private void layout() {
        boolean isPortrait =
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        //осуществляется быстрый переход между состояниями поэтому xml ресурсы не перезагружаем
        //если полноэкранный режим
        if (isFullscreen) {

            toolbar.setVisibility(View.GONE);//скрыть туллбар
            layoutList.setVisibility(View.GONE);//скрыть список видео
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            setLayoutSize(fragmentVideo.getView(), MATCH_PARENT, MATCH_PARENT);


        } else if (isPortrait) {

            toolbar.setVisibility(View.VISIBLE);
            layoutList.setVisibility(View.VISIBLE);
            setLayoutSize(fragmentVideo.getView(), WRAP_CONTENT, WRAP_CONTENT);


        } else {

            toolbar.setVisibility(View.VISIBLE);
            layoutList.setVisibility(View.VISIBLE);
            int screenWidth = dpToPx(getResources().getConfiguration().screenWidthDp);
            int videoWidth = screenWidth - screenWidth / 4 - dpToPx(LANDSCAPE_VIDEO_PADDING_DP);
            setLayoutSize(fragmentVideo.getView(), videoWidth, WRAP_CONTENT);
        }
    }
    //создает фрагмент для отображения видео и передает ему id видео
    @Override
    public void onVideoSelected(String ID) {
        FragmentVideo fragmentVideo =
                (FragmentVideo) getFragmentManager().findFragmentById(R.id.video_fragment_container);
        fragmentVideo.setVideoId(ID);
    }
    //метод для преобразования dp в px
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }
    //метод установки размера макета
    private static void setLayoutSize(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    @Override
    public void onBackPressed() {
        //если полноэкранный режим возвращает в обычный
        if (isFullscreen){
            fragmentVideo.backnormal();
        } else{
            super.onBackPressed();
        }
    }
}
