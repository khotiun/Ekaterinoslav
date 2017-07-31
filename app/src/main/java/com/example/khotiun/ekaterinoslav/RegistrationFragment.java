package com.example.khotiun.ekaterinoslav;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by hotun on 30.07.2017.
 */

public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText etEmail, etPassword;
    private Button btnSignIn, btnRegistration;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        //метод для того что бы слушать состояние пользователя
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {//срабатывает когда пользователь вошел или вышел
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        etEmail = (EditText) view.findViewById(R.id.et_email);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        btnSignIn = (Button) view.findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);
        btnRegistration = (Button) view.findViewById(R.id.btn_registration);
        btnRegistration.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sign_in){
            signInAccount(etEmail.getText().toString(), etPassword.getText().toString());
        } else if (v.getId() == R.id.btn_registration){
            registrationAccount(etEmail.getText().toString(), etPassword.getText().toString());
        }
    }
    //метод авторизации
    public void signInAccount(String email, String password) {
        //addOnCompleteListener - слушатель авторизации пользователя
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {//слушатель выполненого входа
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {//отрабатывает при попытке авторизации
                //успешная авторизация
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Авторизация успешна", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MapActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Авторизация провалена", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void registrationAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Регистрация успешна", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MapActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Регистрация провалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
