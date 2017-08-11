package com.example.khotiun.ekaterinoslav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.khotiun.ekaterinoslav.MapActivity.newIntent;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by hotun on 08.08.2017.
 */

public class SelectionSignInFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "SelectionSignInFragment";
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;// общий экземпляр FirebaseAuthобъекта
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText etEmail, etPassword;
    private Button btnSignIn;
    private SignInButton btnSignInGoogle;
    private TextView tvRegestration;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;
    private LoginButton btnSignInFacebook;

    public static Fragment newInstance() {
        return new SelectionSignInFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Log.d(TAG, "111");
        //facebook
        mCallbackManager = CallbackManager.Factory.create();
        //для того что бы слушать состояние пользователя
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {//срабатывает когда пользователь вошел или вышел
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = MapActivity.newIntent(getActivity());
                    startActivity(intent);
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
            Intent intent = newIntent(getActivity());
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
        btnSignInFacebook = (LoginButton) view.findViewById(R.id.frafment_selection_sign_in_btn_sign_in_facebook);
        tvRegestration = (TextView) view.findViewById(R.id.frafment_selection_sign_in_tv_regestration);
        tvRegestration.setOnClickListener(this);
        signInFacebook();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frafment_selection_sign_in_btn_login) {
            FirebaseAccount.signInAccount(getActivity(), etEmail.getText().toString(), etPassword.getText().toString());

        } else if (v.getId() == R.id.frafment_selection_sign_in_btn_sign_in_google) {
            signInGoogle();

        } else if (v.getId() == R.id.frafment_selection_sign_in_tv_regestration) {
            Fragment fragment = RegistrationFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)//добавляем фрагмент в контайнер
                    .addToBackStack(null)//добавляем в стек вызовов
                    .commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            Log.d(TAG, "resultCode != Activity.RESULT_OK");
            return;
        }
        Log.d(TAG, "onActivityResult");
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

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
        // Pass the activity result back to the Facebook SDK
    }

    //google sign in
    //операция входа
    //Запуск намерения позволяет пользователю выбрать учетную запись Google для входа. Если вы запросили дополнительные возможности profile, emailи openid, пользователю также будет предложено предоставить доступ к запрошенным ресурсам
    private void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
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

    private void signInFacebook() {

        btnSignInFacebook.setReadPermissions("email", "public_profile");//разрешение на использование email
        btnSignInFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            //вход выполнин успешно
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            //вход отменен
            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            //при входе возникла ошибка
            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
    }

    //После того, как пользователь успешно выполнит вход , в методе обратного вызова LoginButtons onSuccess, получите токен доступа для пользователя с подпиской,
    // обменять его на учетные данные Firebase и выполнить аутентификацию с помощью Firebase с использованием учетных данных Firebase
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

}
