package org.example.phonebook;

import java.util.regex.Pattern;

/**
 * The {@code NumberValidator} class provides a method to validate phone numbers.
 * It uses a regular expression pattern to check if the phone number matches
 * the format "(XXX) XXX-XXXX" or "XXX-XXX-XXXX", where X is a digit from 0-9.
 */
class NumberValidator {
    /**
     * The regular expression pattern used to validate phone numbers.
     * The pattern matches phone numbers in the formats:
     * - (XXX) XXX-XXXX
     * - XXX-XXX-XXXX
     */
    private static final Pattern PATTERN = Pattern.compile("\\(\\d{3}\\) \\d{3}-\\d{4}|\\d{3}-\\d{3}-\\d{4}");

    /**
     * Validates if the given phone number matches the predefined pattern.
     * The phone number is considered valid if it matches the format "(XXX) XXX-XXXX" or "XXX-XXX-XXXX".
     *
     * @param number the phone number to be validated
     * @return {@code true} if the phone number is valid, {@code false} otherwise
     */
    public static boolean isValid(String number) {
        return PATTERN.matcher(number).matches();
    }
}
