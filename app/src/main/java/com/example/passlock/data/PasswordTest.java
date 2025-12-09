package com.example.passlock.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "password_tests")
public class PasswordTest {

    @PrimaryKey(autoGenerate = true)
    public int testId;

    public int userId;
    public int score;
    public String feedback;
    public long timestamp;

    public PasswordTest(int userId, int score, String feedback) {
        this.userId = userId;
        this.score = score;
        this.feedback = feedback;
        this.timestamp = System.currentTimeMillis();
    }
}
