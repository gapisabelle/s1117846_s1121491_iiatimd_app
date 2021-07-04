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
import android.widget.Button;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SwipeActivity extends AppCompatActivity implements CardStackListener {
    private CardStackView cardStackView;
    private CardStackLayoutManager cardStackLayoutManager;
    private CardStackAdapter cardStackAdapter;
    MaterialButton buttonCross;
    MaterialButton buttonHeart;
    TextView swipeFeedbackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        cardStackAdapter = new CardStackAdapter(getApplicationContext());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigatin_view);
        bottomNavigationView.setSelectedItemId(R.id.swipeActivity);

        MyFirebaseMessagingService.setBottomNavigation(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.swipeActivity:
//                        overridePendingTransition(0, 0);
//                        startActivity(new Intent(getApplicationContext(), SwipeActivity.class));
                        return false;
                    case R.id.matches:
                        overridePendingTransition(0, 0);
                        startActivity(new Intent(getApplicationContext(), MatchesActivity.class));
                        return true;
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

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                        AppDatabase.class, "card").build();
//
//                List<Card> cardList = db.cardDao().getAll();
//                if (cardList.size() <= 5) { // If app got 5 or less cards, fetch some new ones
//                    // TODO: Get new cards from API
//                }
//
//                cardStackAdapter.setLocalDataSet(cardList.toArray(new Card[0]));
//                db.close();
//            }
//        }).start();



        // ENABLE BACK BUTTON IN TOP NAVIGATION
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        cardStackView = findViewById(R.id.card_stack_view);
        cardStackLayoutManager = new CardStackLayoutManager(this, this);
        cardStackLayoutManager.setCanScrollHorizontal(true);
        cardStackLayoutManager.setCanScrollVertical(false);
        cardStackLayoutManager.setSwipeThreshold(0.5f);

        this.setupButtons();
        swipeFeedbackText = findViewById(R.id.swipeFeedbackText);
        swipeFeedbackText.setText("Loading... Please wait!");
        swipeFeedbackText.setVisibility(View.VISIBLE);
        buttonCross.setVisibility(View.GONE);
        buttonHeart.setVisibility(View.GONE);
        this.getCards();

        cardStackView.setLayoutManager(cardStackLayoutManager);
        cardStackView.setAdapter(cardStackAdapter);
    }

    public void getCards() {
        Api.getCards(getApplicationContext(), new ApiCallback() {
            @Override
            public void onCards(Card[] cards) {
                Api.getSwipes(getApplicationContext(), new ApiCallback() {
                    @Override
                    public void onSwipes(JSONArray result) {
                        List<Card> cardList = new ArrayList<>();
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject obj = result.optJSONObject(i);
                            if (!obj.has("id")) break;
                            Card card = new Card();
                            card.setId(obj.optInt("filmid"));
                            card.setLiked(obj.optInt("liked"));
                            cardList.add(card);
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Utils.setSwipedCardDb(getApplicationContext(), cardList.toArray(new Card[0]));
                                Utils.filterCards(getApplicationContext(), cards, new ApiCallback() {
                                    @Override
                                    public void onCards(Card[] cards) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
//                                List<Card> localCards = new ArrayList<Card>(Arrays.asList(cardStackAdapter.getLocalDataSet()));
//                                Card[] localCards = cardStackAdapter.getLocalDataSet();
                                                List<Card> localCards = Arrays.asList(cards);
//                                localCards.addAll(Arrays.asList(cards));


                                                cardStackAdapter.addToLocalDataSet(localCards);
                                                buttonCross.setVisibility(View.VISIBLE);
                                                buttonHeart.setVisibility(View.VISIBLE);
                                                swipeFeedbackText.setVisibility(View.GONE);
                                            }
                                        });
                                    }
                                }, cardStackAdapter.getLocalDataSet());
                            }
                        }).start();
                    }
                });
            }
        });
    }

    public void setupButtons() {
        buttonCross = findViewById(R.id.button_cross);
        buttonHeart = findViewById(R.id.button_heart);

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
        System.out.println("[Movinder SwipeActivity] onCardSwiped");
        int position = cardStackLayoutManager.getTopPosition()-1;
        Card card = cardStackAdapter.getCard(position);

        AsyncTask.execute(() -> {

            System.out.println("[Movinder SwipeActivity] cardList size:" + cardStackAdapter.getItemCount());
            if (cardStackAdapter.getItemCount() <= 5) {
                this.getCards();
            }

            int index = cardStackAdapter.removeCardById(card.getId());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    cardStackAdapter.notifyItemRangeChanged(index,cardStackAdapter.getItemCount());
                    cardStackAdapter.notifyItemRemoved(index);
                }
            });

            Utils.addToSwipedCardDb(getApplicationContext(), card);
            Utils.removeFromCardDb(getApplicationContext(), card);
            card.setLiked(direction == Direction.Left ? -1 : 1);
            Api.pushSwipe(getApplicationContext(), card, new ApiCallback() {
                @Override
                public void onSwipeAdded(JSONObject result) {
                    System.out.println("[Movinder SwipeActivity] Swipe added to DB? " + result.toString());
                }
            });
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