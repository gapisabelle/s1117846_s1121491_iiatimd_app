package com.example.movinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setUpFirebase();
        Intent intent = new Intent(this, ChatActivity2.class);
        startActivity(intent);
    }

    public void setUpFirebase(){
        FirebaseOptions fbOptions = new FirebaseOptions.Builder()
                .setProjectId("iiatimd-roy-isabelle").setApplicationId("1:948383510167:web:3f1aebc24b2f621e3a03d2").setApiKey("AIzaSyBSLidGsDuVHu8Poi5kQ_D_bhHm_fK48T8").setDatabaseUrl("https://iiatimd-roy-isabelle-default-rtdb.europe-west1.firebasedatabase.app").build();
        FirebaseApp.initializeApp(this, fbOptions, "Movinder");
    }
}