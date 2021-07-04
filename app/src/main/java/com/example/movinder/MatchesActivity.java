package com.example.movinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
                        overridePendingTransition(0, 0);
                        startActivity(new Intent(getApplicationContext(), SwipeActivity.class));
                        return true;
                    case R.id.matches:
//                        overridePendingTransition(0, 0);
//                        startActivity(new Intent(getApplicationContext(), MatchesActivity.class));
                        return false;
                    case R.id.profile:
                        overridePendingTransition(0, 0);
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        return true;
                    case R.id.chat:
                        overridePendingTransition(0, 0);
                        startActivity(new Intent(getApplicationContext(), ChatActivity2.class));
                        return true;
                }
                return false;
            }
        });


        RecyclerView matchesList = findViewById(R.id.matchesList);
        matchesAdapter = new MatchesAdapter(new MatchesAdapter.ClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity2.class);
                Match match = matchesAdapter.getMatch(position);
                System.out.printf("[Movinder MatchesActivity] matchInfo (%s, %s, %s)", match.getChatId(), match.getUserImage(), match.getUsername());
                intent.putExtra("chatId", match.getChatId());
                intent.putExtra("userImage", match.getUserImage());
                intent.putExtra("username", match.getUsername());
                System.out.println("[Movinder MatchesActivity] clickedCard");
                startActivity(intent);
            }
        });
        matchesList.setAdapter(matchesAdapter);

        this.getMatches();
    }

    public void getMatches() {
        TextView textView = findViewById(R.id.nomatchestext);
        textView.setVisibility(View.VISIBLE);
        textView.setText("Loading... Please wait");
        Api.getMatches(getApplicationContext(), new ApiCallback() {
            @Override
            public void onMatches(Match[] matches) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                AppDatabase.class, "matches").build();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                matchesAdapter.setLocalDataSet(matches);
                            }
                        });
                        int dbMatchesSize = db.matchDao().getAll().size();
                        if (matches.length == 0 && dbMatchesSize == 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText("You don't have any matches.");
                                }
                            });
                        } else if (matches.length != 0) {
                            db.matchDao().deleteAll();
                            db.matchDao().insertAll(matches);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    matchesAdapter.setLocalDataSet(matches);
                                    textView.setVisibility(View.GONE);
                                }
                            });
                            db.close();
                        } else if (dbMatchesSize != 0) {
                            Match[] matches1 = db.matchDao().getAll().toArray(new Match[0]);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    matchesAdapter.setLocalDataSet(matches1);
                                    textView.setVisibility(View.GONE);
                                }
                            });
                        }

                    }
                }).start();

            }
        });

    }
}