package com.example.movinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

public class SwipeActivity extends AppCompatActivity implements CardStackListener {
    private CardStackView cardStackView;
    private CardStackLayoutManager cardStackLayoutManager;
    private CardStackAdapter cardStackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        Card[] dataSet = new Card[]{
                new Card(0, "Test1", "1", "3h 1m", "2019", "Action, Test", ""),
                new Card(1, "Test2", "2", "99h 1m", "9999", "Action, Test, Test2", ""),
                new Card(1, "Test2", "2", "99h 1m", "9999", "Action, Test, Test2", ""),
                new Card(1, "Test2", "2", "99h 1m", "9999", "Action, Test, Test2", ""),
                new Card(1, "Test2", "2", "99h 1m", "9999", "Action, Test, Test2", ""),
                new Card(1, "Test2", "2", "99h 1m", "9999", "Action, Test, Test2", ""),
        };

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        cardStackView = findViewById(R.id.card_stack_view);
        cardStackView.setLayoutManager(new CardStackLayoutManager(this, this));
        cardStackView.setAdapter(new CardStackAdapter(dataSet));
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