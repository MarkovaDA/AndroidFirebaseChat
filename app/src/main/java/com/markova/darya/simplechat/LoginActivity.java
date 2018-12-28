package com.markova.darya.simplechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextView txtEmail;
    private TextView txtPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txt_login);
        txtPassword = findViewById(R.id.txt_password);
    }

    public void tryLogin(View btnLogin) {
        String email = txtEmail.getText().toString();
        String password =  txtPassword.getText().toString();

        if (email == "" || password == "") {
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Intent backIntent = new Intent();
                    if (task.isSuccessful()) {
                        backIntent.putExtra("currentUser",  firebaseAuth.getCurrentUser());
                        setResult(RESULT_OK, backIntent);
                    } else {
                        setResult(RESULT_CANCELED, backIntent);
                    }
                    finish();
                }
            });
    }
}
