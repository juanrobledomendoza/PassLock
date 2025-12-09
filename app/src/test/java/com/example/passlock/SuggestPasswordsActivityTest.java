package com.example.passlock;

import junit.framework.TestCase;

public class SuggestPasswordsActivityTest extends TestCase {

    public void testGenerateStrongPassword() {
        String password = SuggestPasswordsActivity.generateStrongPassword();

        assertNotNull(password);
        assertEquals(16, password.length());

        assertTrue(password.matches(".*[A-Z].*"));
        assertTrue(password.matches(".*[a-z].*"));
        assertTrue(password.matches(".*[0-9].*"));

        String symbols = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        assertTrue(containsAny(password, symbols));
    }

    private boolean containsAny(String str, String characterSet) {
        for (char c : str.toCharArray()) {
            if (characterSet.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }
}
