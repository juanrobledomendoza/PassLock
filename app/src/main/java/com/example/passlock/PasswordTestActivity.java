package com.example.passlock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passlock.data.PasswordTestRepository;
import com.example.passlock.util.PasswordStrengthUtil;

public class PasswordTestActivity extends AppCompatActivity {

    private EditText inputPassword;
    private TextView txtScore, txtFeedback;
    private PasswordTestRepository repo;
    private int currentUserId;

    public static Intent intentFactory(Context context) {
        return new Intent(context, PasswordTestActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_test);

        // UI references
        inputPassword = findViewById(R.id.inputPassword);
        txtScore = findViewById(R.id.txtScore);
        txtFeedback = findViewById(R.id.txtFeedback);
        Button btnTest = findViewById(R.id.btnTestPassword);

        // Repository
        repo = new PasswordTestRepository(this);

        // Get logged-in user
        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        currentUserId = prefs.getInt("userId", -1);

        // Button logic
        btnTest.setOnClickListener(v -> testPassword());

        Button btnBackToLanding = findViewById(R.id.btnBackToLanding);
        btnBackToLanding.setOnClickListener(v ->
                startActivity(new Intent(this, LandingPage.class))
        );

    }

    private void testPassword() {
        String pwd = inputPassword.getText().toString();

        if (pwd.isEmpty()) {
            txtFeedback.setText("Please enter a password!");
            txtScore.setText("");
            return;
        }

        // Score password
        int score = PasswordStrengthUtil.scorePassword(pwd);

        // Generate feedback
        String feedback = PasswordStrengthUtil.generateFeedback(score);

        // Update UI
        txtScore.setText("Score: " + score);
        txtFeedback.setText("Feedback: " + feedback);

        // Save to database
        repo.saveTest(currentUserId, score, feedback);
    }
}

