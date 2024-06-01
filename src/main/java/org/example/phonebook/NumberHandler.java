package org.example.phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NumberHandler {

    private static final String FILE_PATH = "/Users/ruslanaprus/IdeaProjects/goit-academy-hw10/src/main/resources/file.txt";
    private List<String> numbers;

    private static final NumberHandler instance = new NumberHandler();

    public static NumberHandler getInstance() {
        return instance;
    }

    private NumberHandler() {
        numbers = new ArrayList<>();

        try {
            Path path = Paths.get(FILE_PATH);
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
            throw new RuntimeException(e);
        }
    }

    public static void readFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                if (NumberValidator.isValid(line.trim())) {
                    System.out.println(line.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(String number) throws InvalidPhoneNumberException {
        if (NumberValidator.isValid(number)) {
            numbers.add(number);
            saveNumbers();
        } else {
            throw new InvalidPhoneNumberException("Invalid phone number: " + number);
        }
    }

    private void saveNumbers() {
        try {
            Files.write(Paths.get(FILE_PATH), numbers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getNumbers() {
        return new ArrayList<>(numbers);
    }
}