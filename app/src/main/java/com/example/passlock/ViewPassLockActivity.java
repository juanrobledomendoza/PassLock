package com.example.passlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passlock.data.AppDatabase;
import com.example.passlock.data.PassLock;
import com.example.passlock.data.PassLockDao;

import java.util.List;

public class ViewPassLockActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PassLockAdapter adapter;
    private List<PassLock> passLockList;
    private PassLockDao passLockDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_passlocks);

        recyclerView = findViewById(R.id.recyclerViewPassLocks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get user ID from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);

        if (userId != -1) {
            // Get database instance and DAO
            AppDatabase db = AppDatabase.getInstance(this);
            passLockDao = db.passLockDao();

            // Fetch PassLocks for the user from the database
            passLockList = passLockDao.getPassLocksForUser(userId);

            // Set up the adapter
            adapter = new PassLockAdapter(passLockList);
            recyclerView.setAdapter(adapter);
        }

        // Set up the back button
        Button backButton = findViewById(R.id.btn_back_to_landing);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewPassLockActivity.this, LandingPage.class);
            startActivity(intent);
            finish(); // Finish this activity so the user can't navigate back to it
        });
    }
}
