package com.example.passlock;

import static org.junit.Assert.assertTrue;

import com.example.passlock.util.PasswordStrengthUtil;

import org.junit.Test;

public class PasswordStrengthUtilTest {

    @Test
    public void testWeakPassword() {
        int score = PasswordStrengthUtil.scorePassword("abc");
        assertTrue(score <= 20);
    }

    @Test
    public void testModeratePassword() {
        int score = PasswordStrengthUtil.scorePassword("hello123!");
        assertTrue(score > 20 && score < 90);
    }

    @Test
    public void testStrongPassword() {
        int score = PasswordStrengthUtil.scorePassword("G@laxy2024!");
        assertTrue(score >= 70);
    }
}
