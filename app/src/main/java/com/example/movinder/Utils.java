package com.example.movinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import androidx.room.Room;

import java.util.Collections;
import java.util.List;

public class Utils {

    public static void removeFromCardDb(Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "card").fallbackToDestructiveMigration().build();
// TODO
            }
        }).start();
    }

    public static void addToSwipedCardDb(Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase dbSwiped = Room.databaseBuilder(context, AppDatabase.class, "cardSwiped").fallbackToDestructiveMigration().build();
// TODO
            }
        }).start();
    }

    public static void filterCards(Context context, Card[] cards, ApiCallback callback) {
        filterCards(context, cards, callback, new Card[0]);
    }
    public static void filterCards(Context context, Card[] cards, ApiCallback callback, Card[] extra) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "card").fallbackToDestructiveMigration().build();
                AppDatabase dbSwiped = Room.databaseBuilder(context, AppDatabase.class, "cardSwiped").fallbackToDestructiveMigration().build();

                System.out.println("[Movinder Utils] card list: " + db.cardDao().getAll().size());
                db.cardDao().insertAll(cards);
//                for (int i = 0; i < cards.length-2; i++) {
//                    db.cardDao().insertAll(cards[i]);
//                }

                List<Card> cardListSwiped = dbSwiped.cardDao().getAll();

                for (Card card : cardListSwiped) {
                    db.cardDao().deleteById(card.getId());
                }
                List<Card> cardList = db.cardDao().getAll();
                for (Card card : extra) {
                    cardList.remove(card);
//                    db.cardDao().deleteById(card.getId());
                }

                db.close();
                dbSwiped.close();

                if (cardList.size() <= 5) {
                    System.out.println("[Movinder Utils] cardList size lower then 5. Getting new cards...");
                    // TODO: set page in sharedpreferences to +1
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
                    int tvPage = sharedPref.getInt("tvPage", -1);
                    if (tvPage == -1) {
                        sharedPref.edit().putInt("tvPage", 1).commit();
                    } else {
                        sharedPref.edit().putInt("tvPage", tvPage + 1).commit();
                    }
                    Api.getCards(context, new ApiCallback() {
                        @Override
                        public void onCards(Card[] cards2) {
                            filterCards(context, cards2, callback);
                        }
                    });
                    return;
                }

                callback.onCards(cardList.toArray(new Card[0]));
            }
        }).start();


    }

    public static boolean hasNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
