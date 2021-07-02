package com.example.movinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Api {
    public Api() {

    }

    // TODO: Mix tv shows and movies together

    static void getCards(Context context, ApiCallback callback) {
        // TODO: Get shared preferences page
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        int tvPage = sharedPref.getInt("tvPage", -1);
        if (tvPage == -1) {
            tvPage = 1;
            sharedPref.edit().putInt("tvPage", 1).apply();
        }

        getCards(context, String.valueOf(tvPage), callback);
    }

    static void getCards(Context context, String page, ApiCallback callback) {
        if (!Utils.hasNetworkConnection(context)) {
            System.out.println("[Movinder] DONT HAVE NETWORK CONNECTION");
            callback.onCards(new Card[0]);
            return; // TODO: return cards from local database.
        }

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://192.168.1.120:8000/api/tv/popular/" + page;
        System.out.println("[Movinder] GET MOVIE DATA");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("[Movinder]" + response.getString("page"));
//                            Card[] cards = new Card[0];
                            List<Card> cards = new ArrayList<Card>();

//                            response.getJSONArray()
                            if (response.has("results")) {
                                JSONArray results = response.getJSONArray("results");
                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject obj = results.getJSONObject(i);
                                    System.out.println(obj);
                                    Card card = new Card();
                                    card.setId(obj.getInt("id"));
                                    card.setImageURI("https://image.tmdb.org/t/p/w500" + obj.getString("poster_path"));
                                    card.setCategories("TODO"); // TODO
                                    card.setDate(obj.getString("first_air_date").split("-")[0]);
                                    card.setLanguage(obj.getString("original_language"));
                                    card.setRating(obj.getString("vote_average"));
                                    card.setTitle(obj.getString("name"));

                                    cards.add(card);
                                }
                            }

                            callback.onCards(cards.toArray(new Card[0]));
                        } catch (JSONException e) {
                            callback.onCards(new Card[0]);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onCards(new Card[0]);
                        // TODO: Get from local database
                        System.out.println("[MovinderERR]" + error.getMessage());
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }


    static void register(Context context, JSONObject details, ApiCallback callback) {
        String url = "http://192.168.1.120:8000/api/auth/register";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onRegister(response);
                System.out.println(response.toString());
                //TODO: handle success
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onRegister(new JSONObject());
                error.printStackTrace();
                System.out.printf("[MovinderError] %s\n", error.getMessage());
                //TODO: handle failure
            }
        });

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    static void login(Context context, JSONObject details, ApiCallback callback) {
        String url = "http://192.168.1.120:8000/api/auth/login";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onLogin(response);
                System.out.println(response.toString());
                //TODO: handle success
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onLogin(new JSONObject());
                error.printStackTrace();
                System.out.printf("[MovinderError] %s\n", error.getMessage());
                //TODO: handle failure
            }
        });

        Volley.newRequestQueue(context).add(jsonRequest);
    }
}
