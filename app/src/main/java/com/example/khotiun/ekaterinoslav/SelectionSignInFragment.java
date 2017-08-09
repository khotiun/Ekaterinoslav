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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

import static com.example.khotiun.ekaterinoslav.R.id.frafment_selection_sign_in_tv_sign_in_facebook;
import static com.example.khotiun.ekaterinoslav.R.id.frafment_selection_sign_in_tv_sign_in_github;
import static com.example.khotiun.ekaterinoslav.R.id.frafment_selection_sign_in_tv_sign_in_google;

/**
 * Created by hotun on 08.08.2017.
 */

public class SelectionSignInFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "SelectionSignInFragment";
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText etEmail, etPassword;
    private Button btnSignIn;
    SignInButton btnSignInGoogle;
    private TextView tvSignInGoogle, tvSignInFacebook, tvSignInGitHub, tvRegestration;
    private GoogleApiClient mGoogleApiClient;

    public static Fragment newInstance() {
        return new SelectionSignInFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "111");
        //для того что бы слушать состояние пользователя
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

        mAuth = FirebaseAuth.getInstance();//инициализация обьекта
        FirebaseUser user = mAuth.getCurrentUser();//получаем текущего пользователя
        if (user != null) {
            // User is signed in, пользователь вошел
            Intent intent = MapActivity.newIntent(getActivity());
            getActivity().startActivity(intent);
        }

        //google sign in
        // Configure Google Sign In
        //запрос пользовательских данных
        //GoogleSignInOptions для запроса идентификатора пользователя и базовой информации профиля
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))//передать свой клиентский идентификатор сервера к requestIdTokenметоду. Чтобы найти идентификатор клиента OAuth 2.0:
                .requestEmail()
                .build();
        //google sign in
        //методе активности входа в систему , создайте GoogleApiClientобъект с доступом к API входа в Google и указанными вами параметрами
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

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
            signIn();

        } else if (v.getId() == R.id.frafment_selection_sign_in_tv_sign_in_google) {

        } else if (v.getId() == R.id.frafment_selection_sign_in_tv_sign_in_facebook) {

        } else if (v.getId() == R.id.frafment_selection_sign_in_tv_sign_in_github) {

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
        btnSignInGoogle = (SignInButton) view.findViewById(R.id.frafment_selection_sign_in_btn_sign_in_google);
        btnSignInGoogle.setOnClickListener(this);
        tvSignInGoogle = (TextView) view.findViewById(frafment_selection_sign_in_tv_sign_in_google);
        tvSignInGoogle.setOnClickListener(this);
        tvSignInFacebook = (TextView) view.findViewById(frafment_selection_sign_in_tv_sign_in_facebook);
        tvSignInFacebook.setOnClickListener(this);
        tvSignInGitHub = (TextView) view.findViewById(frafment_selection_sign_in_tv_sign_in_github);
        tvSignInGitHub.setOnClickListener(this);
        tvRegestration = (TextView) view.findViewById(R.id.frafment_selection_sign_in_tv_regestration);
        tvRegestration.setOnClickListener(this);

        return view;
    }
    //google sign in
    //операция входа
    //Запуск намерения позволяет пользователю выбрать учетную запись Google для входа. Если вы запросили дополнительные возможности profile, emailи openid, пользователю также будет предложено предоставить доступ к запрошенным ресурсам
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //google sign in
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }
    //google sign in
    //пользователь успешно выполнит вход, получите идентификационный токен от GoogleSignInAccountобъекта, обменять его на учетные данные Firebase и выполнить проверку подлинности с помощью Firebase с использованием учетных данных Firebase:
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //если метод выполнен успешно можете использовать getCurrentUserметод для получения данных учетной записи пользователя
                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
