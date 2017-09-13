package com.example.khotiun.ekaterinoslav.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khotiun.ekaterinoslav.R;

public class SplashActivity extends AppCompatActivity {
    private final static String TAG = "SplashActivity";
    public final static String BROADCAST_ACTION = "com.example.khotiun.ekaterinoslav.broadcast";
    public final static String BROADCAST_GOT_RESULT = "BroadcatGotResult";
    public final static String ASYNCTASKFINISHED = "AsyncTaskFinished";
    public final static String NOT_RESULT = "NotResult";
    private ViewGroup vgOld, vgNew;
    private String result;
    BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //Текущее окно будет загружаться на весь экран, приминимо к апи ниже 21
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        result = NOT_RESULT;
        vgOld = (ViewGroup) findViewById(R.id.activity_splash_containerOld);
        vgNew = (ViewGroup) findViewById(R.id.activity_splash_containerNew);
        Animation animOld = AnimationUtils.loadAnimation(this, R.anim.alpha_ekaterinoslav);
        Animation animNew = AnimationUtils.loadAnimation(this, R.anim.alpha_dnipro);
        animNew.setAnimationListener(animationOldListener);
        vgOld.startAnimation(animOld);
        vgNew.startAnimation(animNew);
        new Loading().execute();

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(result.equals(NOT_RESULT)){
                    Log.d(TAG, "onReceive "  + result);
                    result = BROADCAST_GOT_RESULT;
                } else if(result.equals(ASYNCTASKFINISHED)){
                    Log.d(TAG, "onReceive "  + result);
                    Intent i = SelectionSignInActivity.newIntent(SplashActivity.this);
                    startActivity(i);
                    finish();
                }
            }
        };
        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(br, intFilt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // дерегистрируем (выключаем) BroadcastReceiver
        unregisterReceiver(br);
    }

    Animation.AnimationListener animationOldListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationEnd(Animation animation) {
            vgNew.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub
        }
    };

    public class Loading extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Thread.sleep(4000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (result.equals(BROADCAST_GOT_RESULT)){
                Log.d(TAG, "onPostExecute "  + result);
                Intent intent = SelectionSignInActivity.newIntent(SplashActivity.this);
                startActivity(intent);
                finish();
            }
            else if(result.equals(NOT_RESULT)){
                Log.d(TAG, "onPostExecute "  + result);
                result = ASYNCTASKFINISHED;
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (hasFocus) {
                //набор флагов который скрывает статус бар, навигацию
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }
}
