package com.markova.darya.simplechat.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.text.format.DateFormat;
import com.markova.darya.simplechat.R;

import java.util.ArrayList;

public class MessageListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<ChatMessage> messages;

    public MessageListAdapter(Context context, ArrayList<ChatMessage> messages) {
        this.context = context;
        this.messages = messages;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.message_item, parent, false);
        }

        ChatMessage message = messages.get(position);

        TextView messageTextView = view.findViewById(R.id.message_text);
        TextView userTextView = view.findViewById(R.id.message_user);
        TextView timeTextView = view.findViewById(R.id.message_time);

        messageTextView.setText(message.getMessageText());
        userTextView.setText(message.getMessageUser());
        timeTextView.setText(DateFormat.format("dd/MM/yyyy kk:mm:ss", message.getMessageTime()));

        return view;
    }
}
