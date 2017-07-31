package com.example.khotiun.ekaterinoslav;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by hotun on 30.07.2017.
 */

public class MainActivity extends SingleFragmentActivity{


    @Override
    protected Fragment createFragment() {
        return RegistrationFragment.newInstance();
    }
}
