package com.example.passlock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passlock.UserWalletAdapter.UserWalletSummary;
import com.example.passlock.data.AppDatabase;
import com.example.passlock.data.PassLock;
import com.example.passlock.data.PassLockDao;
import com.example.passlock.data.User;
import com.example.passlock.data.UserDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompareUserWalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_user_wallets);

        RecyclerView recyclerView = findViewById(R.id.recycler_user_wallets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserWalletAdapter adapter = new UserWalletAdapter();
        recyclerView.setAdapter(adapter);

        AppDatabase db = AppDatabase.getInstance(this);
        UserDao userDao = db.userDao();
        PassLockDao passLockDao = db.passLockDao();

        List<UserWalletSummary> summaries = computeUserWalletSummaries(userDao, passLockDao);
        adapter.setItems(summaries);

        Button backButton = findViewById(R.id.btn_back_from_compare_wallets);
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LandingPage.class));
            finish();
        });
    }

    private List<UserWalletSummary> computeUserWalletSummaries(UserDao userDao, PassLockDao passLockDao) {
        List<User> allUsers = userDao.getAllUsers();
        Map<Integer, User> nonAdminUsersById = new HashMap<>();
        for (User u : allUsers) {
            if (!u.isAdmin()) {
                nonAdminUsersById.put(u.getUserId(), u);
            }
        }

        List<PassLock> allPasslocks = passLockDao.getAllPassLocks();
        Map<Integer, List<Integer>> scoresByUserId = new HashMap<>();

        for (PassLock p : allPasslocks) {
            if (!nonAdminUsersById.containsKey(p.getUserId())) {
                continue; // skip admin or unknown users
            }
            int score = p.getScore();
            if (score < 0) {
                continue; // skip untested
            }
            List<Integer> list = scoresByUserId.get(p.getUserId());
            if (list == null) {
                list = new ArrayList<>();
                scoresByUserId.put(p.getUserId(), list);
            }
            list.add(score);
        }

        List<UserWalletSummary> result = new ArrayList<>();
        for (Map.Entry<Integer, User> entry : nonAdminUsersById.entrySet()) {
            int userId = entry.getKey();
            User user = entry.getValue();
            List<Integer> scores = scoresByUserId.get(userId);
            if (scores == null || scores.isEmpty()) {
                continue; // no scored passlocks yet
            }
            double sum = 0;
            for (int s : scores) {
                sum += s;
            }
            double avg = sum / scores.size();
            String grade = mapScoreToGrade(avg);
            result.add(new UserWalletSummary(user.getUsername(), avg, grade));
        }

        result.sort((a, b) -> Double.compare(b.averageScore, a.averageScore));
        return result;
    }

    private String mapScoreToGrade(double score) {
        if (score >= 90) return "A+";
        if (score >= 80) return "A";
        if (score >= 70) return "B";
        if (score >= 60) return "C";
        if (score >= 50) return "D";
        return "F";
    }
}
