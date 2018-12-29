package com.markova.darya.simplechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Bundle bundle = new Bundle();
        bundle.putBoolean("isLogin", false);

        DataFormFragment newDataFormFragment = new DataFormFragment();
        newDataFormFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frg_signup, newDataFormFragment)
                .commit();
    }
}
