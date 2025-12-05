package com.example.passlock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passlock.util.PasswordStrengthUtil;

public class ComparePasswordsActivity extends AppCompatActivity {

    public static Intent intentFactory(Context context) {
        return new Intent(context, ComparePasswordsActivity.class);
    }

    private EditText inputA, inputB;
    private TextView txtScoreA, txtScoreB, txtComparisonResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_passwords);

        inputA = findViewById(R.id.inputPasswordA);
        inputB = findViewById(R.id.inputPasswordB);
        txtScoreA = findViewById(R.id.txtScoreA);
        txtScoreB = findViewById(R.id.txtScoreB);
        txtComparisonResult = findViewById(R.id.txtComparisonResult);

        Button btnCompare = findViewById(R.id.btnComparePasswords);
        Button btnBack = findViewById(R.id.btnBackToHome);

        btnCompare.setOnClickListener(v -> comparePasswords());
        btnBack.setOnClickListener(v ->
                startActivity(new Intent(this, LandingPage.class))
        );
    }

    private void comparePasswords() {
        String pwdA = inputA.getText().toString();
        String pwdB = inputB.getText().toString();

        if (pwdA.isEmpty() || pwdB.isEmpty()) {
            txtComparisonResult.setText("Please enter both passwords.");
            return;
        }

        int scoreA = PasswordStrengthUtil.scorePassword(pwdA);
        int scoreB = PasswordStrengthUtil.scorePassword(pwdB);

        txtScoreA.setText("Password A Score: " + scoreA);
        txtScoreB.setText("Password B Score: " + scoreB);

        if (scoreA > scoreB) {
            txtComparisonResult.setText("Password A is stronger!");
        } else if (scoreB > scoreA) {
            txtComparisonResult.setText("Password B is stronger!");
        } else {
            txtComparisonResult.setText("Both passwords have equal strength!");
        }
    }
}
