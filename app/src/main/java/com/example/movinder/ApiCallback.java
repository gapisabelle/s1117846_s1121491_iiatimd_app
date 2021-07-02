package com.example.movinder;

import org.json.JSONObject;

public interface ApiCallback {

    default public void onCards(Card[] cards) {

    }

    default public void onRegister(JSONObject result) {

    }

    default public void onLogin(JSONObject result) {

    }
}
