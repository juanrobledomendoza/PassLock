package com.example.passlock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.passlock.util.PasswordStrengthUtil;

import org.junit.Test;

public class ComparePasswordsLogicTest {

    @Test
    public void testPasswordComparisonStronger() {
        int scoreA = PasswordStrengthUtil.scorePassword("Hello123");
        int scoreB = PasswordStrengthUtil.scorePassword("G@laxy2024!");

        assertTrue(scoreB > scoreA);
    }

    @Test
    public void testPasswordComparisonEqual() {
        String pwd = "Test123!";
        int scoreA = PasswordStrengthUtil.scorePassword(pwd);
        int scoreB = PasswordStrengthUtil.scorePassword(pwd);

        assertEquals(scoreA, scoreB);
    }
}
