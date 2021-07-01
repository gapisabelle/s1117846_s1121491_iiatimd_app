package com.example.movinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Arrays;
import java.util.Date;

public class ChatActivity2 extends AppCompatActivity {

    private DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigatin_view);
        bottomNavigationView.setSelectedItemId(R.id.chat);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.swipeActivity:
                        overridePendingTransition(0, 0);
                        startActivity(new Intent(getApplicationContext(), SwipeActivity.class));
                        return true;
                    case R.id.matches:
                        overridePendingTransition(0, 0);
                        startActivity(new Intent(getApplicationContext(), MatchesActivity.class));
                        return true;
                    case R.id.profile:
                        overridePendingTransition(0, 0);
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        return true;
                    case R.id.chat:
                        return false;
//                        overridePendingTransition(0, 0);
//                        startActivity(new Intent(getApplicationContext(), ChatActivity2.class));
//                        return true;
                }
                return false;
            }
        });

        myDatabase = FirebaseDatabase.getInstance(FirebaseApp.getInstance("Movinder")).getReference("Message");
        TextView myText = findViewById(R.id.textMessage2);

        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myText.setText("");
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    String data = ds.getValue(ChatMsg.class).getMessage();

                    myText.append(data + "\n");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                myText.setText("CANCELLED");

            }
        });

        EditText editText = findViewById(R.id.editMessage);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    sendMessage(textView);
                    return i == EditorInfo.IME_ACTION_SEND ? true : false; // ????
                }
                return false;
            }
        });
    }

    public void sendMessage(View view){
        Date date =  new Date();
        EditText myEditText = findViewById(R.id.editMessage);
        ChatMsg chatMsg = new ChatMsg(myEditText.getText().toString(), date, 1);
        myDatabase.push().setValue(chatMsg);
        myEditText.setText("");
    }
}