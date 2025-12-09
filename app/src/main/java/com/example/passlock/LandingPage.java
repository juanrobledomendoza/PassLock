package com.example.passlock;

import android.annotation.SuppressLint;
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

        // Checks and loads appropriate layout/landing page.
        if (isAdmin) {
            setContentView(R.layout.landing_page_admin);

            // Admin-only buttons should be handled here
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
                        startActivity(new Intent(this, CompareUserWalletActivity.class))
                );
            }

            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button managePasswordTipsBtn = findViewById(R.id.managePasswordTipsBtn);
            if (managePasswordTipsBtn != null) {
                managePasswordTipsBtn.setOnClickListener(v ->
                        startActivity(new Intent(this, ManagePasswordTipsActivity.class))
                );
            }

        } else {
            // User Layout
            setContentView(R.layout.landing_page_user);

            Button testPasswordBtn = findViewById(R.id.testPasswordBtn);
            if (testPasswordBtn != null) {
                testPasswordBtn.setOnClickListener(v ->
                        startActivity(PasswordTestActivity.intentFactory(this))
                );
            }
            Button tipsBtn = findViewById(R.id.viewPasswordTipsBtn);
            if (tipsBtn != null) {
                tipsBtn.setOnClickListener(v ->
                        startActivity(PasswordTipsActivity.intentFactory(this))
                );
            }

            Button suggestPasswordBtn = findViewById(R.id.suggestPassLockBtn);
            if (suggestPasswordBtn != null) {
                suggestPasswordBtn.setOnClickListener(v ->
                        startActivity(new Intent(this, SuggestPasswordsActivity.class))
                );
            }

            Button viewPassLocksBtn = findViewById(R.id.previousPassLockBtn);
            if (viewPassLocksBtn != null) {
                viewPassLocksBtn.setOnClickListener(v ->
                        startActivity(new Intent(this, ViewPassLockActivity.class))
                );
            }

            Button compareBtn = findViewById(R.id.comparePassLockBtn);
            if (compareBtn != null) {
                compareBtn.setOnClickListener(v ->
                        startActivity(new Intent(this, ComparePasswordsActivity.class))
                );
            }
        }

        // Shared UI (both pages have this)
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

        Button managePasswordTipsBtn = findViewById(R.id.managePasswordTipsBtn);
        if (managePasswordTipsBtn != null) {
            managePasswordTipsBtn.setOnClickListener(v ->
                    startActivity(new Intent(this, PasswordTestActivity.class))
            );
        }
    }
}
