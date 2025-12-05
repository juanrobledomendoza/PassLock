package com.example.passlock.util;

import java.util.regex.Pattern;

public class PasswordStrengthUtil {

    // Common weak passwords list
    private static final String[] COMMON_PASSWORDS = {
            "password", "123456", "123456789", "qwerty", "abc123",
            "password1", "111111", "letmein", "iloveyou"
    };

    public static int scorePassword(String password) {

        if (password == null || password.isEmpty()) return 0;

        int score = 0;
        int length = password.length();

        // 1. Length scoring (entropy approximation)
        if (length <= 6) score += 5;
        else if (length <= 8) score += 15;
        else if (length <= 12) score += 30;
        else score += 40;

        // 2. Character type variety
        boolean hasLower = Pattern.compile("[a-z]").matcher(password).find();
        boolean hasUpper = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasDigit = Pattern.compile("\\d").matcher(password).find();
        boolean hasSymbol = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();

        if (hasLower) score += 10;
        if (hasUpper) score += 10;
        if (hasDigit) score += 15;
        if (hasSymbol) score += 20;

        // 3. Deduct points for repetition
        if (password.matches(".*(.)\\1{2,}.*")) {
            score -= 10;
        }

        // 4. Deduct points for sequences (abc, 123, qwerty)
        if (isSequential(password)) {
            score -= 15;
        }

        // 5. Deduct if password contains common weak substring
        for (String common : COMMON_PASSWORDS) {
            if (password.toLowerCase().contains(common)) {
                score -= 25;
                break;
            }
        }

        // Normalize score to 0–100
        if (score < 0) score = 0;
        if (score > 100) score = 100;

        return score;
    }


    // Check if password contains obvious sequences
    private static boolean isSequential(String password) {
        String lower = password.toLowerCase();
        String keyboardSeq = "qwertyuiopasdfghjklzxcvbnm";
        String numSeq = "0123456789";

        // Check for 3 sequential characters
        for (int i = 0; i < lower.length() - 2; i++) {
            String slice = lower.substring(i, i + 3);

            if (keyboardSeq.contains(slice) || numSeq.contains(slice)) {
                return true;
            }
        }
        return false;
    }


    public static String generateFeedback(int score) {
        if (score <= 20)
            return "Very Weak — Avoid common patterns and increase length.";

        if (score <= 40)
            return "Weak — Add symbols, numbers, and mix uppercase/lowercase.";

        if (score <= 70)
            return "Fair — Strengthen with longer length and symbols.";

        if (score <= 90)
            return "Strong — Your password is solid.";

        return "Very Strong — Excellent password!";
    }
}

