package com.example.movinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView toLogin = findViewById(R.id.toLogin);
        toLogin.setOnClickListener(RegisterActivity.this);
    }

    @Override
    public void onClick(View v) {
        Intent toLogin = new Intent(this, MainActivity.class);
        startActivity(toLogin);
    }
}