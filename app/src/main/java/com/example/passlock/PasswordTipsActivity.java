package com.example.passlock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PasswordTipsActivity extends AppCompatActivity {

    public static Intent intentFactory(Context context) {
        return new Intent(context, PasswordTipsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_tips);

        RecyclerView recyclerView = findViewById(R.id.tipsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String[] tips = {
                "Use at least 12 characters whenever possible.",
                "Mix uppercase, lowercase letters, digits, and symbols.",
                "Avoid using dictionary words (e.g., 'password', 'dragon').",
                "Never reuse passwords across multiple sites.",
                "Enable two-factor authentication when available.",
                "Don't use personal info like your birthday or pet’s name.",
                "Use a passphrase with unrelated words.",
                "Update old passwords regularly.",
                "Avoid sequences like '123456' or 'abcdef'.",
                "Use a password manager to store complex passwords."
        };

        PasswordTipsAdapter adapter = new PasswordTipsAdapter(tips);
        recyclerView.setAdapter(adapter);

        // Set up the back button
        Button backButton = findViewById(R.id.btn_back_to_landing_from_tips);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(PasswordTipsActivity.this, LandingPage.class);
            startActivity(intent);
            finish(); // Finish this activity so the user can't navigate back to it
        });
    }
}
