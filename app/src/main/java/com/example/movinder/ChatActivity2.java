package com.example.movinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity2 extends AppCompatActivity {

    private DatabaseReference myDatabase;
    private ChatMessageAdapter chatMessageAdapter;
    private String chatId = null;
    private int userId;
    private String otherUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        userId = Utils.getUserId(getApplicationContext());

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            chatId = extras.getString("chatId");
            otherUserName = extras.getString("username");
            System.out.println("[Movinder ChatActivity2] chatId extras: " + chatId);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(otherUserName);
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        if (chatId == null) return;


        chatMessageAdapter = new ChatMessageAdapter(getApplicationContext());
        chatMessageAdapter.setUserId(userId);
        RecyclerView recyclerView = findViewById(R.id.chatRecyclerview);
        recyclerView.setAdapter(chatMessageAdapter);

        myDatabase = FirebaseDatabase.getInstance(FirebaseApp.getInstance("Movinder")).getReference(chatId);
//        TextView myText = findViewById(R.id.textMessage2);

        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                myText.setText("");
                chatMessageAdapter.setLocalDataSet(new ArrayList<ChatMsg>());
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    ChatMsg chatMsg = ds.getValue(ChatMsg.class);

                    chatMessageAdapter.addToLocalDataSet(chatMsg);

                }
                chatMessageAdapter.notifyDataSetChanged();

                recyclerView.scrollToPosition(chatMessageAdapter.getItemCount()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                myText.setText("CANCELLED");

            }
        });

        EditText editText = findViewById(R.id.editMessage);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    sendMessage(textView);
                    return i == EditorInfo.IME_ACTION_SEND ? true : false; // Why????
                }
                return false;
            }
        });
    }

    public void sendMessage(View view){
        Date date =  new Date();
        EditText myEditText = findViewById(R.id.editMessage);
        ChatMsg chatMsg = new ChatMsg(myEditText.getText().toString(), date, userId);
        myDatabase.push().setValue(chatMsg);

        Map<String, String> params = new HashMap();
        params.put("chatid", chatId);
        params.put("msg", myEditText.getText().toString());
        JSONObject details = new JSONObject(params);
        Api.notifyMsg(getApplicationContext(), details);
        myEditText.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}