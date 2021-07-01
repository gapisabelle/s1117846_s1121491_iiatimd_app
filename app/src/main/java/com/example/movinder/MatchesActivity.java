package com.example.movinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {
    MatchesAdapter matchesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigatin_view);
        bottomNavigationView.setSelectedItemId(R.id.matches);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.swipeActivity:
                        startActivity(new Intent(getApplicationContext(), SwipeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.matches:
                        startActivity(new Intent(getApplicationContext(), MatchesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        TextView textView = findViewById(R.id.nomatchestext);
        textView.setText("Loading... Please wait");

        RecyclerView matchesList = findViewById(R.id.matchesList);
        matchesAdapter = new MatchesAdapter();
        matchesList.setAdapter(matchesAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "cardMatches").build();

                List<Card> cardList = db.cardDao().getAll();

                // TODO: Load matches from API

                if (cardList.size() == 0) textView.setText("You don't have any matches.");

                matchesAdapter.setLocalDataSet(cardList.toArray(new Card[0]));
                db.close();
            }
        }).start();
    }
}