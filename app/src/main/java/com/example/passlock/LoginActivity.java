package com.example.passlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passlock.data.User;
import com.example.passlock.data.UserRepository;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private TextView tvErrorMessage;

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        userRepository = new UserRepository(this);

        // Seed Admin1 and Test1 users
        seedUsers();

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        tvErrorMessage = findViewById(R.id.tv_error_message);
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void seedUsers() {
        // Check if users already exist to avoid duplicates
        User admin1 = userRepository.login("Admin1", "admin123");
        User test1 = userRepository.login("Test1", "test123");

        if (admin1 == null) {
            userRepository.createUser("Admin1", "admin123", true);
        }
        if (test1 == null) {
            userRepository.createUser("Test1", "test123", false);
        }
    }

    private void handleLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password.");
            return;
        }

        User user = userRepository.login(username, password);

        if (user == null) {
            showError("Invalid username or password.");
        } else {
            tvErrorMessage.setVisibility(View.GONE);
            Toast.makeText(this, "Welcome " + user.getUsername(), Toast.LENGTH_SHORT).show();

            // Save login state
            SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.putString("username", user.getUsername());
            editor.putBoolean("isAdmin", user.isAdmin());
            editor.putInt("userId", user.getUserId()); // <-- Save the User ID
            editor.apply();

            // Navigate to LandingPage
            Intent intent = new Intent(this, LandingPage.class);
            startActivity(intent);
            finish();
        }
    }

    private void showError(String message) {
        tvErrorMessage.setText(message);
        tvErrorMessage.setVisibility(View.VISIBLE);
    }
}
