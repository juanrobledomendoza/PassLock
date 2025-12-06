package com.example.passlock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passlock.data.AppDatabase;
import com.example.passlock.data.PassLock;
import com.example.passlock.data.PassLockDao;
import com.example.passlock.util.PasswordStrengthUtil;

public class PasswordTestActivity extends AppCompatActivity {

    public static final String EXTRA_PASSWORD = "com.example.passlock.PASSWORD_TO_TEST";
    public static final String EXTRA_PASSLOCK_ID = "com.example.passlock.PASSLOCK_ID";

    private EditText inputPassword;
    private TextView txtScore, txtFeedback;
    private PassLockDao passLockDao;
    private int passLockId = -1;

    public static Intent intentFactory(Context context) {
        return new Intent(context, PasswordTestActivity.class);
    }

    public static Intent intentFactory(Context context, String password, int passLockId) {
        Intent intent = new Intent(context, PasswordTestActivity.class);
        intent.putExtra(EXTRA_PASSWORD, password);
        intent.putExtra(EXTRA_PASSLOCK_ID, passLockId);
        return intent;
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

        // Get database DAO
        passLockDao = AppDatabase.getInstance(this).passLockDao();

        // Get PassLock ID and password from intent
        if (getIntent().hasExtra(EXTRA_PASSLOCK_ID)) {
            passLockId = getIntent().getIntExtra(EXTRA_PASSLOCK_ID, -1);
            String passwordToTest = getIntent().getStringExtra(EXTRA_PASSWORD);
            inputPassword.setText(passwordToTest);
            testPassword(); // Automatically run the test
        }

        btnTest.setOnClickListener(v -> testPassword());

        Button btnBack = findViewById(R.id.btnBackToLanding);
        btnBack.setOnClickListener(v -> {
            // Place user back to the landing screen
            Intent intent = new Intent(this, LandingPage.class);
            startActivity(intent);

        });
    }

    private void testPassword() {
        String pwd = inputPassword.getText().toString();

        if (pwd.isEmpty()) {
            txtFeedback.setText("Please enter a password!");
            txtScore.setText("");
            return;
        }

        int score = PasswordStrengthUtil.scorePassword(pwd);
        String feedback = PasswordStrengthUtil.generateFeedback(score);

        // Update UI
        txtScore.setText("Score: " + score);
        txtFeedback.setText("Feedback: " + feedback);

        // If we have a valid PassLock ID, update the record in the database.
        if (passLockId != -1) {
            PassLock passLockToUpdate = passLockDao.getPassLockById(passLockId);
            if (passLockToUpdate != null) {
                passLockToUpdate.setScore(score);
                passLockToUpdate.setFeedback(feedback);
                passLockDao.updatePassLock(passLockToUpdate);
                Toast.makeText(this, "Score saved!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
