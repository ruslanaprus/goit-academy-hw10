package org.example.phonebook;

import java.util.regex.Pattern;

class NumberValidator {
    private static final Pattern PATTERN1 = Pattern.compile("\\(\\d{3}\\) \\d{3}-\\d{4}");
    private static final Pattern PATTERN2 = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");

    public static boolean isValid(String number) {
        return PATTERN1.matcher(number).matches() || PATTERN2.matcher(number).matches();
    }
}