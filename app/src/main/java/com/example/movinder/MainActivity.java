package com.example.movinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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
        this.messageSubscriber();
//        Intent intent = new Intent(this, ChatActivity2.class);
//        startActivity(intent);



        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String loginToken = sharedPref.getString("token", "");

        if (loginToken != "") {
            gotoSwipeActivity();
        }


        Button toRegister = findViewById(R.id.toRegister);
        toRegister.setOnClickListener(this);
        Button loginBtn = findViewById(R.id.loginBtn);
        EditText loginEmail = findViewById(R.id.loginEmail);
        EditText loginPassword = findViewById(R.id.loginPassword);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> params = new HashMap();
                params.put("email", loginEmail.getText().toString());
                params.put("password", loginPassword.getText().toString());
                JSONObject details = new JSONObject(params);
                Api.login(getApplicationContext(), details, new ApiCallback() {
                    @Override
                    public void onLogin(JSONObject result) {
                        try {
                            System.out.printf("[Movinder RegisterActivity] onRegister %s\n", result.get("user"));
                            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            sharedPref.edit().putInt("page", 1).commit();
                            gotoSwipeActivity();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent toRegister = new Intent(this, RegisterActivity.class);
        startActivity(toRegister);
//        this.gotoSwipeActivity();
    }

    public void setUpFirebase(){
        System.out.println("[Movinder MainActivity] Setting up firebase...");
        if (FirebaseApp.getApps(this).size() == 0 || !FirebaseApp.getApps(this).get(0).getName().equals("Movinder")) {
            System.out.println("[Movinder MainActivity] FirebaseApp not found. Creating...");
            FirebaseOptions fbOptions = new FirebaseOptions.Builder()
                    .setProjectId("iiatimd-roy-isabelle").setApplicationId("1:948383510167:web:3f1aebc24b2f621e3a03d2").setApiKey("AIzaSyBSLidGsDuVHu8Poi5kQ_D_bhHm_fK48T8").setDatabaseUrl("https://iiatimd-roy-isabelle-default-rtdb.europe-west1.firebasedatabase.app").build();
            FirebaseApp.initializeApp(this, fbOptions, "Movinder");
            FirebaseDatabase.getInstance(FirebaseApp.getInstance("Movinder")).setPersistenceEnabled(true);
        }
        System.out.println("[Movinder MainActivity] Firebase setup done.");
    }

    public void gotoSwipeActivity() {
        Intent intent = new Intent(this, SwipeActivity.class);
        startActivity(intent);
    }

    public void messageSubscriber(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    System.out.println("Not succesfull :(( " + task.getException());
                    return;
                }
                String token = task.getResult();
                System.out.println("Messagingstoken " + token);

            }
        });
        FirebaseMessaging.getInstance().subscribeToTopic("Match").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                System.out.println("You've got a match!");
            }

        });
    }


}