package com.markova.darya.simplechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

        if (firebaseAuth.getCurrentUser() != null) {
            startMainActivity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.sign_in_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void tryLogin(View btnLogin) {
        String email = txtEmail.getText().toString();
        String password =  txtPassword.getText().toString();

        final LoginActivity context = this;

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                   startMainActivity();
                } else {
                    Toast.makeText(context,
                            getResources().getString(R.string.failure_auth),
                            Toast.LENGTH_LONG)
                            .show();
                }
                }
            });
    }

    public void trySignUp(View btnSignUp) {
        startSignUpActivity();
    }

    private void startSignUpActivity() {
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(signUpIntent);
    }

    private void startMainActivity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
