package com.example.khotiun.ekaterinoslav.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.khotiun.ekaterinoslav.FirebaseAccount;
import com.example.khotiun.ekaterinoslav.R;

/**
 * Created by hotun on 30.07.2017.
 */

public class RegistrationFragment extends Fragment {

    private static final String TAG = "RegistrationFragment";
    private EditText etEmail, etPassword;
    private Button btnRegistration;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        etEmail = (EditText) view.findViewById(R.id.et_email);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        btnRegistration = (Button) view.findViewById(R.id.btn_registration);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAccount.registrationAccount(getActivity() ,etEmail.getText().toString(), etPassword.getText().toString());
            }
        });
        return view;
    }
}
