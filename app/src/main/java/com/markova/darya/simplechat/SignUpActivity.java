package com.markova.darya.simplechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;

public class SignUpActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = findViewById(R.id.tlb_sign_up);
        setSupportActionBar(toolbar);

        Bundle bundle = new Bundle();
        bundle.putBoolean("isLogin", false);

        DataFormFragment newDataFormFragment = new DataFormFragment();
        newDataFormFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frg_sign_up, newDataFormFragment)
                .commit();
    }
}
