package com.example.movinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
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

import java.util.List;
import java.util.Random;

public class SwipeActivity extends AppCompatActivity implements CardStackListener {
    private CardStackView cardStackView;
    private CardStackLayoutManager cardStackLayoutManager;
    private CardStackAdapter cardStackAdapter = new CardStackAdapter();

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "card").build();

                List<Card> cardList = db.cardDao().getAll();
                if (cardList.size() <= 5) { // If app got 5 or less cards, fetch some new ones
                    // TODO: Get new cards from API
                }

                cardStackAdapter.setLocalDataSet(cardList.toArray(new Card[0]));
                db.close();
            }
        }).start();

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
        cardStackView.setAdapter(cardStackAdapter);
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
        System.out.println("[Movinder optionSelected]");
        return true;
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        AsyncTask.execute(() -> {
            AppDatabase dbCards = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "card").build();

            AppDatabase dbSwiped = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "cardSwiped").build();


            int position = cardStackLayoutManager.getTopPosition()-1;
            Card card = dbCards.cardDao().findById(cardStackAdapter.getCard(position).getId());

            dbCards.cardDao().delete(card);
            if (!dbSwiped.cardDao().exists(card.getId())) {
                card.setLiked(direction == Direction.Left ? -1 : 1);
                dbSwiped.cardDao().insertAll(card);
            }
        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int position = cardStackLayoutManager.getTopPosition()-1;
//                Card card = cardStackAdapter.getCard(position);
//                AppDatabase dbSwiped = Room.databaseBuilder(getApplicationContext(),
//                        AppDatabase.class, "cardSwiped").build();
//                dbSwiped.cardDao().delete(card);
//                System.out.println("[Movinder] " + direction.name());
//                System.out.println("[Movinder swipe] " + cardStackAdapter.getCard(position).getId());
//                System.out.println("[Movinder swipe] " + cardStackAdapter.getCard(position).getTitle());
//            }
//        }).start();

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