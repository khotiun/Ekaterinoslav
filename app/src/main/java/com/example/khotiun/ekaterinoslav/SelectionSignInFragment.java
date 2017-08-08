package com.example.khotiun.ekaterinoslav;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.khotiun.ekaterinoslav.R.id.frafment_selection_sign_in_btn_sign_in_facebook;
import static com.example.khotiun.ekaterinoslav.R.id.frafment_selection_sign_in_btn_sign_in_github;
import static com.example.khotiun.ekaterinoslav.R.id.frafment_selection_sign_in_btn_sign_in_google;

/**
 * Created by hotun on 08.08.2017.
 */

public class SelectionSignInFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "SelectionSignInFragmen";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText etEmail, etPassword;
    private Button btnSignIn;
    private TextView tvSignInGoogle, tvSignInFacebook, tvSignInGitHub, tvRegestration;

    public static Fragment newInstance() {
        return new SelectionSignInFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "111");
        //для того что бы слушать состояние пользователя
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {//срабатывает когда пользователь вошел или вышел
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//            }
//        };
        mAuth = FirebaseAuth.getInstance();//инициализация обьекта
        FirebaseUser user = mAuth.getCurrentUser();//получаем текущего пользователя
        if (user != null) {
            // User is signed in, пользователь вошел
            Intent intent = MapActivity.newIntent(getActivity());
            getActivity().startActivity(intent);
        }
    }
    //добавление и удаление слушателя
//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frafment_selection_sign_in_btn_login) {
            FirebaseAccount.signInAccount(getActivity(), etEmail.getText().toString(), etPassword.getText().toString());
        } else if (v.getId() == R.id.frafment_selection_sign_in_btn_sign_in_google) {

        } else if (v.getId() == R.id.frafment_selection_sign_in_btn_sign_in_facebook) {

        } else if (v.getId() == R.id.frafment_selection_sign_in_btn_sign_in_github) {

        } else if (v.getId() == R.id.frafment_selection_sign_in_tv_regestration) {
            Fragment fragment = RegistrationFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)//добавляем фрагмент в контайнер
                    .addToBackStack(null)//добавляем в стек вызовов
                    .commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selection_sign_in, container, false);
        etEmail = (EditText) view.findViewById(R.id.frafment_selection_sign_in_et_email);
        etPassword = (EditText) view.findViewById(R.id.frafment_selection_sign_in_et_password);
        btnSignIn = (Button) view.findViewById(R.id.frafment_selection_sign_in_btn_login);
        btnSignIn.setOnClickListener(this);
        tvSignInGoogle = (TextView) view.findViewById(frafment_selection_sign_in_btn_sign_in_google);
        tvSignInGoogle.setOnClickListener(this);
        tvSignInFacebook = (TextView) view.findViewById(frafment_selection_sign_in_btn_sign_in_facebook);
        tvSignInFacebook.setOnClickListener(this);
        tvSignInGitHub = (TextView) view.findViewById(frafment_selection_sign_in_btn_sign_in_github);
        tvSignInGitHub.setOnClickListener(this);
        tvRegestration = (TextView) view.findViewById(R.id.frafment_selection_sign_in_tv_regestration);
        tvRegestration.setOnClickListener(this);

        return view;
    }


}
