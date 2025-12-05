package com.example.passlock;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SuggestPasswordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_passwords);

        TextView passwordOne = findViewById(R.id.password_one);
        TextView passwordTwo = findViewById(R.id.password_two);
        TextView passwordThree = findViewById(R.id.password_three);

        Button copyButtonOne = findViewById(R.id.copy_button_one);
        Button copyButtonTwo = findViewById(R.id.copy_button_two);
        Button copyButtonThree = findViewById(R.id.copy_button_three);

        String pass1 = generateStrongPassword();
        String pass2 = generateStrongPassword();
        String pass3 = generateStrongPassword();

        passwordOne.setText(pass1);
        passwordTwo.setText(pass2);
        passwordThree.setText(pass3);

        copyButtonOne.setOnClickListener(v -> copyToClipboard(pass1));
        copyButtonTwo.setOnClickListener(v -> copyToClipboard(pass2));
        copyButtonThree.setOnClickListener(v -> copyToClipboard(pass3));
    }

    private void copyToClipboard(String password) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Password", password);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    private String generateStrongPassword() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String symbols = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        String allChars = upper + lower + digits + symbols;

        SecureRandom random = new SecureRandom();
        List<Character> passwordChars = new ArrayList<>();

        // Ensure the password has at least one of each character type
        passwordChars.add(lower.charAt(random.nextInt(lower.length())));
        passwordChars.add(upper.charAt(random.nextInt(upper.length())));
        passwordChars.add(digits.charAt(random.nextInt(digits.length())));
        passwordChars.add(symbols.charAt(random.nextInt(symbols.length())));

        // Fill the rest of the password length with random characters
        int passwordLength = 16;
        for (int i = 4; i < passwordLength; i++) {
            passwordChars.add(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle the characters to avoid a predictable pattern
        Collections.shuffle(passwordChars, random);

        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }
        return password.toString();
    }
}
