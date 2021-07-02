package com.example.movinder;

public interface ApiCallback {

    default public void onCards(Card[] cards) {

    }
}
