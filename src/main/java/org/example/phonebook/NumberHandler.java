/**
 * A utility class to handle phone numbers. This class provides methods to read, validate, print, and add phone numbers
 * to a file.
 */
package org.example.phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * The NumberHandler class provides functionality to handle phone numbers. It ensures that only valid phone numbers are
 * added to the file and provides methods to interact with them.
 */
public class NumberHandler {
    private static NumberHandler instance;
    private String filePath;
    private List<String> numbers;

    /**
     * Constructs a NumberHandler object with the specified file path and initializes the list of phone numbers by
     * loading them from the file.
     *
     * @param filePath the file path where phone numbers are stored
     */
    private NumberHandler(String filePath) {
        this.filePath = filePath;
        numbers = new ArrayList<>();
        loadNumbersFromFile();
    }

    /**
     * Returns a singleton instance of NumberHandler with the specified file path. If an instance doesn't exist, a new
     * one is created. This method ensures thread safety by synchronizing access.
     *
     * @param filePath the file path where phone numbers are stored
     * @return the singleton instance of NumberHandler
     */
    public static NumberHandler getInstance(String filePath) {
        if (instance == null) {
            synchronized (NumberHandler.class) {
                instance = new NumberHandler(filePath);
            }
        }
        return instance;
    }

    /**
     * Loads phone numbers from the file specified by the file path. It reads each line from the file and adds valid
     * phone numbers to the numbers list.
     */
    private synchronized void loadNumbersFromFile() {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            List<String> fileContent = Files.readAllLines(path);

            for (String line : fileContent) {
                if (!line.isBlank()) {
                    numbers.add(line.trim());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading numbers from file", e);
        }
    }

    /**
     * Reads valid phone numbers from the file specified by the file path. It validates each line of the file and
     * returns a list of valid phone numbers.
     *
     * @param filePath the file path where phone numbers are stored
     * @return the list of valid phone numbers
     */
    public static List<String> readValidPhoneNumbers(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            Iterator<String> iterator = lines.iterator();
            while (iterator.hasNext()) {
                String line = iterator.next().trim();
                if (!NumberValidator.isValid(line)) {
                    iterator.remove();
                }
            }
            return new ArrayList<>(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * Prints the valid phone numbers stored in the file specified by the file path.
     */
    public void printValidPhoneNumbers() {
        List<String> validNumbers = readValidPhoneNumbers(this.filePath);
        for (String number : validNumbers) {
            System.out.println(number);
        }
    }

    /**
     * Adds a valid phone number to the list of phone numbers and saves it to the file specified by the file path.
     * Throws an InvalidPhoneNumberException if the provided number is invalid.
     *
     * @param number the valid phone number to be added
     * @throws InvalidPhoneNumberException if the provided phone number is invalid
     */
    public void addValidPhoneNumber(String number) throws InvalidPhoneNumberException {
        if (NumberValidator.isValid(number)) {
            numbers.add(number);
            saveNumber();
        } else {
            throw new InvalidPhoneNumberException("Invalid phone number: " + number);
        }
    }

    /**
     * Saves the list of phone numbers to the file specified by the file path.
     */
    private void saveNumber() {
        try {
            Files.write(Paths.get(filePath), numbers, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the list of phone numbers.
     *
     * @return the list of phone numbers
     */
    public List<String> getNumbers() {
        return new ArrayList<>(numbers);
    }
}
