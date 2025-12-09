package com.example.passlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button loginButton, createAccountButton;
    public static final String PREFS_NAME = "PassLockPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createAccountButton);

//         check if already logged in
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
//
//        if (isLoggedIn) {
//            startActivity(new Intent(this, LandingPage.class));
//            finish();
//        }

        loginButton.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));
        createAccountButton.setOnClickListener(v ->
                startActivity(new Intent(this, CreateAccountActivity.class)));


//        createAccountButton.setOnClickListener(v ->
////                startActivity(new Intent(this, CreateAccountActivity.class)));
//    }
    }

}
