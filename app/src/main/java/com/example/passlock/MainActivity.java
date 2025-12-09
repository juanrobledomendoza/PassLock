package com.example.passlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passlock.data.AppDatabase;
import com.example.passlock.data.PassLock;
import com.example.passlock.data.PassLockDao;
import com.example.passlock.data.User;
import com.example.passlock.data.UserDao;

public class MainActivity extends AppCompatActivity {

    private Button loginButton, createAccountButton;
    public static final String PREFS_NAME = "PassLockPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createAccountButton);

//         check if already logged in
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        seedSampleDataIfNeeded();
//
//        if (isLoggedIn) {
//            startActivity(new Intent(this, LandingPage.class));
//            finish();
//        }

        loginButton.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));
        createAccountButton.setOnClickListener(v ->
                startActivity(new Intent(this, CreateAccountActivity.class)));


//        createAccountButton.setOnClickListener(v ->
////                startActivity(new Intent(this, CreateAccountActivity.class)));
//    }
    }

    private void seedSampleDataIfNeeded() {
        AppDatabase db = AppDatabase.getInstance(this);
        UserDao userDao = db.userDao();
        PassLockDao passLockDao = db.passLockDao();

        // If we've already inserted our sample users once, don't do it again
        java.util.List<User> existing = userDao.getAllUsers();
        boolean alreadySeeded = false;
        for (User u : existing) {
            if ("StudentAlice".equals(u.getUsername()) ||
                "StudentBob".equals(u.getUsername()) ||
                "StudentCarol".equals(u.getUsername())) {
                alreadySeeded = true;
                break;
            }
        }
        if (alreadySeeded) {
            return;
        }

        User alice = new User("StudentAlice", "password1", false);
        int aliceId = (int) userDao.insertUser(alice);

        User bob = new User("StudentBob", "password2", false);
        int bobId = (int) userDao.insertUser(bob);

        User carol = new User("StudentCarol", "password3", false);
        int carolId = (int) userDao.insertUser(carol);

        PassLock aliceGmail = new PassLock(aliceId, "Gmail", "alice@gmail.com", "Al1ce!StrongPass");
        aliceGmail.setScore(92);
        aliceGmail.setFeedback("Strong password with good variety.");
        passLockDao.insertPassLock(aliceGmail);

        PassLock aliceBank = new PassLock(aliceId, "Bank", "alice_bank", "B@nkAcc0unt!23");
        aliceBank.setScore(88);
        aliceBank.setFeedback("Very strong, could be slightly longer.");
        passLockDao.insertPassLock(aliceBank);

        PassLock bobInsta = new PassLock(bobId, "Instagram", "bob_insta", "bob1234");
        bobInsta.setScore(35);
        bobInsta.setFeedback("Too short and predictable.");
        passLockDao.insertPassLock(bobInsta);

        PassLock bobSchool = new PassLock(bobId, "SchoolPortal", "bob_school", "Sch00l!Pass");
        bobSchool.setScore(65);
        bobSchool.setFeedback("Decent but could use more symbols.");
        passLockDao.insertPassLock(bobSchool);

        PassLock carolNetflix = new PassLock(carolId, "Netflix", "carol_netflix", "netflix");
        carolNetflix.setScore(10);
        carolNetflix.setFeedback("Very weak; common word with no variety.");
        passLockDao.insertPassLock(carolNetflix);

        PassLock carolDiscord = new PassLock(carolId, "Discord", "carol_discord", "D1sc0rd!2024");
        carolDiscord.setScore(78);
        carolDiscord.setFeedback("Strong but consider avoiding year patterns.");
        passLockDao.insertPassLock(carolDiscord);
    }

}
