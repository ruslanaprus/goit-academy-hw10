package org.example.phonebook;

/**
 * The {@code InvalidPhoneNumberException} class represents a custom exception that is thrown
 * when an invalid phone number is encountered. This exception is a specialized {@code Exception}
 * used to indicate that a phone number does not meet the required format or criteria.
 */
class InvalidPhoneNumberException extends Exception {
    /**
     * Constructs a new {@code InvalidPhoneNumberException} with the specified detail message.
     * The message provides more information about the reason for the exception.
     *
     * @param message the detail message explaining why the exception was thrown
     */
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
