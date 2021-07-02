package com.example.movinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

//    RequestQueue requestQueue;
//
//    // Instantiate the cache
//    Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
//
//    // Set up the network to use HttpURLConnection as the HTTP client.
//    Network network = new BasicNetwork(new HurlStack());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setUpFirebase();

        Button toRegister = findViewById(R.id.toRegister);
        toRegister.setOnClickListener(this);
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent toRegister = new Intent(this, RegisterActivity.class);
        startActivity(toRegister);
//        this.gotoSwipeActivity();

        Api.register(getApplicationContext());
    }

    public void setUpFirebase(){
        if (!FirebaseApp.getApps(this).get(0).getName().equals("Movinder")) {
            FirebaseOptions fbOptions = new FirebaseOptions.Builder()
                    .setProjectId("iiatimd-roy-isabelle").setApplicationId("1:948383510167:web:3f1aebc24b2f621e3a03d2").setApiKey("AIzaSyBSLidGsDuVHu8Poi5kQ_D_bhHm_fK48T8").setDatabaseUrl("https://iiatimd-roy-isabelle-default-rtdb.europe-west1.firebasedatabase.app").build();
            FirebaseApp.initializeApp(this, fbOptions, "Movinder");
        }
    }

    public void gotoSwipeActivity() {
        Intent intent = new Intent(this, SwipeActivity.class);
        startActivity(intent);
    }
}