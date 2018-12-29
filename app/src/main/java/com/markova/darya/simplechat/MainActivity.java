package com.markova.darya.simplechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

//https://code.tutsplus.com/ru/tutorials/how-to-create-an-android-chat-app-using-firebase--cms-27397
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayChatMessages();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_out_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void displayChatMessages() {

    }
}
