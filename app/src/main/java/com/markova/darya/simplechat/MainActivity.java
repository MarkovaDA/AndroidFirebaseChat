package com.markova.darya.simplechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.markova.darya.simplechat.model.ChatMessage;
import com.markova.darya.simplechat.model.MessageListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mainToolbar;
    private FloatingActionButton sendButton;
    private TextView messageText;
    private ListView messageListView;

    private DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Message");
    private ArrayList<ChatMessage> messageList = new ArrayList<>();
    private MessageListAdapter messageListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainToolbar = findViewById(R.id.tlb_main);
        sendButton = findViewById(R.id.btn_send);
        messageText = findViewById(R.id.input_message);
        messageListView = findViewById(R.id.list_message);

        setSupportActionBar(mainToolbar);
        sendButton.setOnClickListener(this);

        messageListAdapter = new MessageListAdapter(this, messageList);
        messageListView.setAdapter(messageListAdapter);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item_sign_out) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        return false;
    }

    private void displayChatMessages() {
        databaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                messageList.add(message);
                messageListAdapter.notifyDataSetChanged();
                messageListView.setSelection(messageListAdapter.getCount() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        messageText.setEnabled(false);

        databaseRef.push()
                .setValue(new ChatMessage(messageText.getText().toString(),
                        FirebaseAuth.getInstance()
                                .getCurrentUser()
                                .getEmail())
                ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                messageText.setEnabled(true);
                messageText.clearFocus();

                if (task.isSuccessful()) {
                    messageText.setText("");
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

    public void onListViewClick(View view) {
        //установить обработчик в конце
        messageText.clearFocus();
    }
}
