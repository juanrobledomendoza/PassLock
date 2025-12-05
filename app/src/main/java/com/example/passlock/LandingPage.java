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
//        Checks and loads appropriate layout/landing page.
        if (isAdmin) {
            setContentView(R.layout.landing_page_admin);
        } else {
            setContentView(R.layout.landing_page_user);
        }
//        Shared UI (both pages have this)
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
        if (BtnNewPassLock != null) {
            BtnNewPassLock.setOnClickListener(v -> {
                startActivity(new Intent(this, NewPassLockActivity.class));
            });
        }
//        buttons for admin
        Button viewUserPasslocksBtn = findViewById(R.id.viewUserPasslocksBtn);
        if (viewUserPasslocksBtn != null) {
            viewUserPasslocksBtn.setOnClickListener(v ->
                    startActivity(new Intent(this, ViewUserPasslocksActivity.class))
            );
        }

        Button editSuggestedPasslocksBtn = findViewById(R.id.editSuggestedPasslocksBtn);
        if (editSuggestedPasslocksBtn != null) {
            editSuggestedPasslocksBtn.setOnClickListener(v ->
                    startActivity(new Intent(this, EditSuggestedPasslocksActivity.class))
            );
        }

        Button compareUserPasslocksBtn = findViewById(R.id.compareUserPasslocksBtn);
        if (compareUserPasslocksBtn != null) {
            compareUserPasslocksBtn.setOnClickListener(v ->
                    startActivity(new Intent(this, ComparePasslocksActivity.class))
            );
        }

        Button managePasswordTipsBtn = findViewById(R.id.managePasswordTipsBtn);
        if (managePasswordTipsBtn != null) {
            managePasswordTipsBtn.setOnClickListener(v ->
                    startActivity(new Intent(this, ManagePasswordTipsActivity.class))
            );
        }
    }
}
