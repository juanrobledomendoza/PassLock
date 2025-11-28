package com.example.passlock;

public class PasswordTest {

    // We use simple public fields for now to keep it easy for beginners
    public int testId;
    public int userId;
    public int passwordScore;
    public String feedback;
    public long timestamp;

    public PasswordTest(int userId, int passwordScore, String feedback) {
        this.userId = userId;
        this.passwordScore = passwordScore;
        this.feedback = feedback;
        this.timestamp = System.currentTimeMillis();
        // We will fake the testId for now
        this.testId = (int) (Math.random() * 1000);
    }


}
