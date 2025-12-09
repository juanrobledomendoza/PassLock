package com.example.passlock.data;

import android.content.Context;

import java.util.List;

public class PasswordTestRepository {

    private final com.example.passlock.data.PasswordTestDao testDao;

    public PasswordTestRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.testDao = db.passwordTestDao();
    }

    public long saveTest(int userId, int score, String feedback) {
        PasswordTest test = new PasswordTest(userId, score, feedback);
        return testDao.insertTest(test);
    }

    public List<PasswordTest> getTestsForUser(int userId) {
        return testDao.getTestsForUser(userId);
    }
}
