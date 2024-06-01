package org.example.phonebook;

import java.util.regex.Pattern;

class NumberValidator {
    private static final Pattern PATTERN = Pattern.compile("\\(\\d{3}\\) \\d{3}-\\d{4}|\\d{3}-\\d{3}-\\d{4}");

    public static boolean isValid(String number) {
        return PATTERN.matcher(number).matches();
    }
}