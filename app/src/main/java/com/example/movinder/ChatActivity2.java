package com.example.movinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    }

    public void sendMessage(View view){
        Date date =  new Date();
        EditText myEditText = findViewById(R.id.editTextTextPersonName4);
        ChatMsg chatMsg = new ChatMsg(myEditText.getText().toString(), date, 1);
        myDatabase.push().setValue(chatMsg);
        myEditText.setText("");
    }
}