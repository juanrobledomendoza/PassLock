package com.example.passlock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passlock.data.UserRepository;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private TextView tvErrorMessage;
    private Button btnCreateAccount;

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        userRepository = new UserRepository(this);

        etUsername = findViewById(R.id.et_username_create);
        etPassword = findViewById(R.id.et_password_create);
        tvErrorMessage = findViewById(R.id.tv_error_message_create);
        btnCreateAccount = findViewById(R.id.btn_create_account_submit);

        btnCreateAccount.setOnClickListener(v -> handleCreateAccount());
    }

    private void handleCreateAccount() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password.");
            return;
        }

        // Check if user already exists
        if (userRepository.getUserByUsername(username) != null) {
            showError("Username already taken. Please choose another.");
            return;
        }

        long userId = userRepository.createUser(username, password, false); // false for non-admin user

        if (userId == -1) {
            showError("An error occurred. Please try again.");
        } else {
            Toast.makeText(this, "Account for " + username + " created successfully", Toast.LENGTH_SHORT).show();

            // Navigate back to LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void showError(String message) {
        tvErrorMessage.setText(message);
        tvErrorMessage.setVisibility(View.VISIBLE);
    }
}
