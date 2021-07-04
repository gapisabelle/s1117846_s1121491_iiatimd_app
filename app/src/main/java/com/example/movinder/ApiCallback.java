package com.example.movinder;

import org.json.JSONArray;
import org.json.JSONObject;

public interface ApiCallback {

    default public void onCards(Card[] cards) {

    }

    default public void onRegister(JSONObject result) {

    }

    default public void onLogin(JSONObject result) {

    }

    default public void onSwipeAdded(JSONObject result) {

    }

    default public void onSwipes(JSONArray result) {

    }

    default public void onMatches(Match[] matches) {

    }
}
