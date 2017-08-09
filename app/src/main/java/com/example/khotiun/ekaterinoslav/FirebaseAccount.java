package com.example.khotiun.ekaterinoslav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.khotiun.ekaterinoslav.MapActivity.newIntent;
import static com.example.khotiun.ekaterinoslav.R.string.email;

/**
 * Created by hotun on 08.08.2017.
 */

public class FirebaseAccount {

    private static final String TAG = "FirebaseAccount";

    //метод авторизации
    public static void signInAccount(final Context context, String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //addOnCompleteListener - слушатель авторизации пользователя
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {//слушатель выполненого входа
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {//отрабатывает при попытке авторизации
                //успешная авторизация
                if (task.isSuccessful()) {
                    Toast.makeText(context, R.string.authorization_successful, Toast.LENGTH_SHORT).show();
                    Intent intent = MapActivity.newIntent(context);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, R.string.authorization_failed, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //метод регистрации
    public static void registrationAccount(final Context context, String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, R.string.registration_successful, Toast.LENGTH_SHORT).show();
                    Intent intent = MapActivity.newIntent(context);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, R.string.registration_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    // Configure Google Sign In
//    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build();
}
