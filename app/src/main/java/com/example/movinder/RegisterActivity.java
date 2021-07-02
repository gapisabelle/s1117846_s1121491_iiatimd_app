package com.example.movinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView toLogin = findViewById(R.id.toLogin);
        Button registerBtn = findViewById(R.id.registerBtn);
        toLogin.setOnClickListener(RegisterActivity.this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("[Movinder RegisterActivity] REGISTERING...");
                Map<String, String> params = new HashMap();
                EditText registerUsername = findViewById(R.id.registerUsername);
                EditText registerEmail = findViewById(R.id.registerEmail);
                EditText registerPassword = findViewById(R.id.registerPassword);
                EditText registerPasswordConfirmation = findViewById(R.id.registerPasswordConfirmation);
                params.put("name", registerUsername.getText().toString());
                params.put("email", registerEmail.getText().toString());
                params.put("password", registerPassword.getText().toString());
                params.put("password_confirmation", registerPasswordConfirmation.getText().toString());

                JSONObject details = new JSONObject(params);

                Api.register(getApplicationContext(), details, new ApiCallback() {
                    @Override
                    public void onRegister(JSONObject result) {
                        try {
                            gotoLoginPage();
                            System.out.printf("[Movinder RegisterActivity] onRegister %s\n", result.get("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void gotoLoginPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent toLogin = new Intent(this, MainActivity.class);
        startActivity(toLogin);
    }
}