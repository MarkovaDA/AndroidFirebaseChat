package com.markova.darya.simplechat;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.markova.darya.simplechat.model.ChatMessage;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private FloatingActionButton sendButton;
    private TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.tlb_main);
        sendButton = findViewById(R.id.btn_send);
        messageTextView = findViewById(R.id.input_message);

        setSupportActionBar(toolbar);
        sendButton.setOnClickListener(this);

        displayChatMessages();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(0).setTitle(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        return super.onPrepareOptionsMenu(menu);
    }

    private void displayChatMessages() {
    }

    @Override
    public void onClick(View v) {
        messageTextView.setEnabled(false);

        FirebaseDatabase.getInstance()
                .getReference("Message")
                .push()
                .setValue(new ChatMessage(messageTextView.getText().toString(),
                        FirebaseAuth.getInstance()
                                .getCurrentUser()
                                .getEmail())
                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            messageTextView.setText("");
                        } else {
                            Toast.makeText(
                                    MainActivity.this,
                                    getResources().getString(R.string.insert_data_error),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }
}
