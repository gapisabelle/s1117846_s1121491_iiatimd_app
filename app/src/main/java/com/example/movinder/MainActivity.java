package com.example.movinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

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
        // Instantiate the RequestQueue with the cache and network.
//        requestQueue = new RequestQueue(cache, network);
//        // Start the queue
//        requestQueue.start();
        getMovieData();
    }

    public void gotoSwipeActivity() {
        Intent intent = new Intent(this, SwipeActivity.class);
        startActivity(intent);
    }

    public void getMovieData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.120:8000/api/tv/popular";
        System.out.println("[Movinder] GET MOVIE DATA");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("[Movinder]" + response.getString("page"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        gotoSwipeActivity();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("[MovinderERR]" + error.getMessage());
                        gotoSwipeActivity();
                    }
                });

        requestQueue.add(jsonObjectRequest);
//        requestQueue.start();
    }
}