package com.example.passlock;

import static org.junit.Assert.assertEquals;
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
        int score = PasswordStrengthUtil.scorePassword("Hello123");
        assertTrue(score >= 40 && score <= 70);
    }

    @Test
    public void testStrongPassword() {
        int score = PasswordStrengthUtil.scorePassword("G@laxy2024!");
        assertTrue(score >= 70);
    }
}
