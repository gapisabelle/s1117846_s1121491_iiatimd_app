package com.example.movinder;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    @Override

    public void onNewToken(String token){
        Log.d("refresh", token);
    }
}