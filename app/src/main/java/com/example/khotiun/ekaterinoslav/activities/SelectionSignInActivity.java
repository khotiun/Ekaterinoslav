package com.example.khotiun.ekaterinoslav.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khotiun.ekaterinoslav.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
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

/**
 * Created by hotun on 30.07.2017.
 */

public class SelectionSignInActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SelectionSignInActivity";
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

    public static Intent newIntent(Context context) {
        return new Intent(context, SelectionSignInActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initializing facebook SDK before setContentView - otherwise the app will crash
//        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_selection_sign_in);

        Log.d(TAG, "onCreate");
        //facebook
        mCallbackManager = CallbackManager.Factory.create();

        //для того что бы слушать состояние пользователя
        mAuth = FirebaseAuth.getInstance();//инициализация обьекта
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {//срабатывает когда пользователь вошел или вышел
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = MapActivity.newIntent(SelectionSignInActivity.this);
                    startActivity(intent);
                    finish();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        etEmail = (EditText) findViewById(R.id.frafment_selection_sign_in_et_email);
        etPassword = (EditText) findViewById(R.id.frafment_selection_sign_in_et_password);
        btnSignIn = (Button) findViewById(R.id.frafment_selection_sign_in_btn_login);
        btnSignIn.setOnClickListener(this);
        btnSignInGoogle = (SignInButton) findViewById(R.id.btn_sign_in_google);
        btnSignInGoogle.setOnClickListener(this);
        btnSignInFacebook = (LoginButton) findViewById(R.id.frafment_selection_sign_in_btn_sign_in_facebook);
        btnSignInFacebook.setReadPermissions("email");
        tvRegestration = (TextView) findViewById(R.id.frafment_selection_sign_in_tv_regestration);
        tvRegestration.setOnClickListener(this);
        signInFacebook();

        //google sign in
        // Configure Google Sign In
        //запрос пользовательских данных
        //GoogleSignInOptions для запроса идентификатора пользователя и базовой информации профиля
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))//передать свой клиентский идентификатор сервера к requestIdTokenметоду. Чтобы найти идентификатор клиента OAuth 2.0:
                .requestEmail()
                .build();
//        google sign in
//        методе активности входа в систему , создайте GoogleApiClientобъект с доступом к API входа в Google и указанными вами параметрами
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d(TAG, "GoogleApiClient onConnectionFailed");
                        Toast.makeText(SelectionSignInActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    //добавление и удаление слушателя
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frafment_selection_sign_in_btn_login) {
            signInAccount(etEmail.getText().toString(), etPassword.getText().toString());

        } else if (v.getId() == R.id.btn_sign_in_google) {
            Log.d(TAG, "btn_sign_in_google_presed");
            signInGoogle();

        } else if (v.getId() == R.id.frafment_selection_sign_in_tv_regestration) {
            Intent intent = RegistrationActivity.newIntent(this);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult");
        if (resultCode != Activity.RESULT_OK) {
            Log.d(TAG, "resultCode != Activity.RESULT_OK");
            return;
        }
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
                Log.d(TAG, "Google Sign In failed");
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
        // Pass the activity result back to the Facebook SDK
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        super.onBackPressed();
    }

    //google sign in
    //операция входа
    //Запуск намерения позволяет пользователю выбрать учетную запись Google для входа. Если вы запросили дополнительные возможности profile, emailи openid, пользователю также будет предложено предоставить доступ к запрошенным ресурсам
    private void signInGoogle() {
        Log.d(TAG, "signInGoogle");
        Log.d(TAG, mGoogleApiClient.toString());
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //google sign in
    //пользователь успешно выполнит вход, получите идентификационный токен от GoogleSignInAccountобъекта, обменять его на учетные данные Firebase и выполнить проверку подлинности с помощью Firebase с использованием учетных данных Firebase:
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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
                            Toast.makeText(SelectionSignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signInFacebook() {
        btnSignInFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            //вход выполнин успешно
            @Override
            public void onSuccess(LoginResult loginResult) {
//                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                Log.d(TAG, "facebook:onSuccess:" + loginResult + profile.getFirstName());
                handleFacebookAccessToken(loginResult.getAccessToken());
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
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SelectionSignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //метод авторизации
    public void signInAccount( String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //addOnCompleteListener - слушатель авторизации пользователя
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {//слушатель выполненого входа
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {//отрабатывает при попытке авторизации
                //успешная авторизация
                if (task.isSuccessful()) {
                    Toast.makeText(SelectionSignInActivity.this, R.string.authorization_successful, Toast.LENGTH_SHORT).show();
                    Intent intent = MapActivity.newIntent(SelectionSignInActivity.this);
                    startActivity(intent);
                } else {
                    Toast.makeText(SelectionSignInActivity.this, R.string.authorization_failed, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
