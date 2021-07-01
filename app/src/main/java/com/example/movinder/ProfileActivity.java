package com.example.movinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigatin_view);
        bottomNavigationView.setSelectedItemId(R.id.profile);
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
//                        overridePendingTransition(0, 0);
//                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        return false;
                    case R.id.chat:
                        overridePendingTransition(0, 0);
                        startActivity(new Intent(getApplicationContext(), ChatActivity2.class));
                        return true;
                }
                return false;
            }
        });

        Button btnClearSwipedDb = findViewById(R.id.btnClearSwipedDb);
        Button btnClearPageDb = findViewById(R.id.btnClearPageDb);
        Button btnClearCardDb = findViewById(R.id.btnClearCardDb);

        btnClearSwipedDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase dbSwiped = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "cardSwiped").fallbackToDestructiveMigration().build();
                        dbSwiped.cardDao().deleteAll();
                        dbSwiped.close();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Cleared the Swiped cards.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

        btnClearPageDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPref.edit().remove("tvPage").apply();
                Toast.makeText(getApplicationContext(), "Cleared the page db.", Toast.LENGTH_SHORT).show();
            }
        });

        btnClearCardDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "card").fallbackToDestructiveMigration().build();
                        db.cardDao().deleteAll();
                        db.close();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Cleared the cards from DB.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });
    }
}