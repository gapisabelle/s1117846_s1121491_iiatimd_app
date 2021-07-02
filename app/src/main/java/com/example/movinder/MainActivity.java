package com.example.movinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;


import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class MainActivity extends AppCompatActivity {
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
//        this.gotoSwipeActivity();

        Api.register(getApplicationContext());
    }

    public void setUpFirebase(){
        if (!FirebaseApp.getApps(this).get(0).getName().equals("Movinder")) {
            FirebaseOptions fbOptions = new FirebaseOptions.Builder()
                    .setProjectId("iiatimd-roy-isabelle").setApplicationId("1:948383510167:web:3f1aebc24b2f621e3a03d2").setApiKey("AIzaSyBSLidGsDuVHu8Poi5kQ_D_bhHm_fK48T8").setDatabaseUrl("https://iiatimd-roy-isabelle-default-rtdb.europe-west1.firebasedatabase.app").build();
            FirebaseApp.initializeApp(this, fbOptions, "Movinder");
        }


        // Instantiate the RequestQueue with the cache and network.
//        requestQueue = new RequestQueue(cache, network);
//        // Start the queue
//        requestQueue.start();

//        Api.getCards(getApplicationContext(), new ApiCallback() {
//            @Override
//            public void onCards(Card[] cards) {
//                System.out.println("[Movinder] APICALLBACK");
//                for (Card card : cards) {
//                    System.out.println("[Movinder ApiTest] " + card.getTitle());
//                }
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "card").fallbackToDestructiveMigration().build();
//                        AppDatabase dbSwiped = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "cardSwiped").fallbackToDestructiveMigration().build();
//
//                        List<Card> cardListSwiped = dbSwiped.cardDao().getAll();
//                        List<Card> cardList = db.cardDao().getAll();
//                        if (cardList.size() == 0) {
//                            db.cardDao().insertAll(cards);
//                        }
//                        for (Card card : cardListSwiped) {
//                            System.out.println("[Movinder CardSWIPED]" + card.getTitle() + " liked:" + (card.getLiked() == 1 ? "yes" : "no"));
//                        }
//
//                        db.close();
//                        dbSwiped.close();
//
//                        gotoSwipeActivity();
//                    }
//                }).start();
//            }
//        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                        AppDatabase.class, "card").fallbackToDestructiveMigration().build();
//                AppDatabase dbSwiped = Room.databaseBuilder(getApplicationContext(),
//                        AppDatabase.class, "cardSwiped").fallbackToDestructiveMigration().build();
//
//
//                List<Card> cardList = db.cardDao().getAll();
//                List<Card> cardListSwiped = dbSwiped.cardDao().getAll();
//
//                System.out.println("[Movinder CardExists]" + db.cardDao().exists(333));
//
//
//                for (Card card : cardList) {
//                    System.out.println("[Movinder Card]" + card.getTitle());
//                }
//                System.out.println("");
//                System.out.println("");
//                for (Card card : cardListSwiped) {
//                    System.out.println("[Movinder CardSWIPED]" + card.getTitle());
//                }
//                if (cardList.size() == 0) {
//                    System.out.println("[Movinder Card] NO ITEMS");
//                    Card[] dataSet = new Card[]{
//                            new Card(0, "Test1", "1", "en", "2019", "Action, Test", "https://source.unsplash.com/random?sig=" + new Random(99999).nextInt()),
//                            new Card(1, "Test2", "2", "en", "9111", "Action, Test, Test2", "https://source.unsplash.com/random?sig=2" + new Random(12345).nextInt()),
//                            new Card(2, "Test3", "3", "ja", "9911", "Action, Test, Test3", "https://source.unsplash.com/random?sig=3" + new Random(234567).nextInt()),
//                            new Card(3, "Test4", "4", "es", "9991", "Action, Test, Test4", "https://source.unsplash.com/random?sig=4" + new Random(45678).nextInt()),
//                            new Card(4, "Test5", "5", "fr", "1111", "Action, Test, Test5", "https://source.unsplash.com/random?sig=5" + new Random(567890).nextInt()),
//                            new Card(5, "Test6", "6", "en", "1119", "Action, Test, Test6", "https://source.unsplash.com/random?sig=6" + new Random(6789).nextInt()),
//                    };
//                    db.cardDao().insertAll(dataSet);
//                }
//
//                db.close();
//                dbSwiped.close();
//
//                gotoSwipeActivity();
////                getMovieData();
//            }
//        }).start();
//        ULocale loc;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            loc = ULocale.forLanguageTag("en");
//            System.out.println("[Movinder] " + loc.getDisplayLanguageWithDialect()); // Engels
//        }
    }

    public void gotoSwipeActivity() {
        Intent intent = new Intent(this, SwipeActivity.class);
        startActivity(intent);
    }
}