package com.example.movinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import org.json.JSONObject;

import java.util.Random;

public class SwipeActivity extends AppCompatActivity implements CardStackListener {
    private CardStackView cardStackView;
    private CardStackLayoutManager cardStackLayoutManager;
    private CardStackAdapter cardStackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigatin_view);
        bottomNavigationView.setSelectedItemId(R.id.swipeActivity);
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

        String url = "http://192.168.1.120:8000/api/tv/popular";
        System.out.println("[Movinder] GET MOVIE DATA");

        Card[] dataSet = new Card[]{
                new Card(0, "Test1", "1", "3h 1m", "2019", "Action, Test", "https://source.unsplash.com/random?sig=" + new Random(99999).nextInt()),
                new Card(1, "Test2", "2", "99h 1m", "9111", "Action, Test, Test2", "https://source.unsplash.com/random?sig=2" + new Random(12345).nextInt()),
                new Card(1, "Test3", "3", "99h 2m", "9911", "Action, Test, Test3", "https://source.unsplash.com/random?sig=3" + new Random(234567).nextInt()),
                new Card(1, "Test4", "4", "99h 3m", "9991", "Action, Test, Test4", "https://source.unsplash.com/random?sig=4" + new Random(45678).nextInt()),
                new Card(1, "Test5", "5", "99h 4m", "1111", "Action, Test, Test5", "https://source.unsplash.com/random?sig=5" + new Random(567890).nextInt()),
                new Card(1, "Test6", "6", "99h 5m", "1119", "Action, Test, Test6", "https://source.unsplash.com/random?sig=6" + new Random(6789).nextInt()),
        };

        // ENABLE BACK BUTTON IN TOP NAVIGATION
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        cardStackView = findViewById(R.id.card_stack_view);
        cardStackLayoutManager = new CardStackLayoutManager(this, this);
        cardStackLayoutManager.setCanScrollHorizontal(true);
        cardStackLayoutManager.setCanScrollVertical(false);
        cardStackLayoutManager.setSwipeThreshold(0.5f);

        this.setupButtons();

        cardStackView.setLayoutManager(cardStackLayoutManager);
        cardStackView.setAdapter(new CardStackAdapter(dataSet));
    }

    public void setupButtons() {
        MaterialButton buttonCross = findViewById(R.id.button_cross);
        MaterialButton buttonHeart = findViewById(R.id.button_heart);

        buttonCross.setOnClickListener(event -> {
            SwipeAnimationSetting swipeSettings = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Normal.duration)
                    .build();
            cardStackLayoutManager.setSwipeAnimationSetting(swipeSettings);

            cardStackView.swipe();
        });

        buttonHeart.setOnClickListener(event -> {
            SwipeAnimationSetting swipeSettings = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .build();
            cardStackLayoutManager.setSwipeAnimationSetting(swipeSettings);

            cardStackView.swipe();
        });

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

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        System.out.println("[Movinder] " + direction.name());
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }
}