package com.markova.darya.simplechat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SignUpActivity extends AppCompatActivity {
    DataFormFragment dataFormFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dataFormFragment = (DataFormFragment)getSupportFragmentManager()
                .findFragmentById(R.id.frg_data_form);

        dataFormFragment.isLogin = false;
    }
}
