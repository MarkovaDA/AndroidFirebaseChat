package com.markova.darya.simplechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public int SIGN_IN_REQUEST_CODE = 10;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            Intent loginIntent =   new Intent(this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivityForResult(
                    loginIntent,
                    SIGN_IN_REQUEST_CODE
            );

        } else {
            Toast.makeText(this,
                    getResources().getString(R.string.greeting)
                            .concat(firebaseAuth
                                    .getCurrentUser()
                                    .getDisplayName()),
                    Toast.LENGTH_LONG)
                    .show();

            displayChatMessages();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,
                        getResources().getString(R.string.success_auth),
                        Toast.LENGTH_LONG)
                        .show();

                displayChatMessages();
            } else {
                Toast.makeText(this,
                        getResources().getString(R.string.failure_auth),
                        Toast.LENGTH_LONG)
                        .show();
                finish();
            }
        }
    }

    private void displayChatMessages() {

    }
}
