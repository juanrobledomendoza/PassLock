package com.example.passlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LandingPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        boolean isAdmin = prefs.getBoolean("isAdmin", false);
        String username = prefs.getString("username", "User");

        if (isAdmin) {
            setContentView(R.layout.landing_page_admin);
        } else {
            setContentView(R.layout.landing_page_user);
        }

        TextView welcomeTextView = findViewById(R.id.textView);
        welcomeTextView.setText("Welcome " + username);

        Button BtnLogOut = findViewById(R.id.logoutBtn);
        BtnLogOut.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            // Navigate to LoginActivity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        Button BtnNewPassLock = findViewById(R.id.newPassLockBtn);
        BtnNewPassLock.setOnClickListener(v -> {
            startActivity(new Intent(this, NewPassLockActivity.class));
        });


        Button viewPassLocksButton = findViewById(R.id.previousPassLockBtn);
        viewPassLocksButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ViewPassLockActivity.class));
        });

        Button suggestPassLockBtn = findViewById(R.id.suggestPassLockBtn);
        suggestPassLockBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, SuggestPasswordsActivity.class));
        });
    }
}
