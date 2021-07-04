package com.example.movinder;

import androidx.room.TypeConverter;

import org.json.JSONException;
import org.json.JSONObject;

public class DbConverters {
    @TypeConverter
    public static String fromCard(Card card) {
        return card.toStringObject();
    }

    @TypeConverter
    public static Card stringToCard(String _card) {
        System.out.println("Converter initialData:" + _card);
        JSONObject obj;
        try {
            obj = new JSONObject(_card);

            Card card = new Card(
                obj.optInt("id"),
                obj.optString("title"),
                obj.optString("rating"),
                obj.optString("language"),
                obj.optString("date"),
                obj.optString("categories"),
                obj.optString("imageURI"),
                obj.optInt("liked"),
                obj.optInt("userId"),
                obj.optString("username")
            );
            System.out.println("Converter " + card.toString());
            return card;
        } catch (JSONException e) {
            System.out.println("Converter " + e.getMessage());
            e.printStackTrace();
        }
        return new Card();
    }
}
