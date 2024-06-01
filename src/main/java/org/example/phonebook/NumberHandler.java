package org.example.phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class NumberHandler {
    private static NumberHandler instance;
    private String filePath;
    private List<String> numbers;

    private NumberHandler(String filePath) {
        this.filePath = filePath;
        numbers = new ArrayList<>();

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
            throw new RuntimeException(e);
        }
    }

    public static synchronized NumberHandler getInstance(String filePath) {
        if (instance == null) {
            instance = new NumberHandler(filePath);
        }
        return instance;
    }

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
            System.out.println(lines);
            return new ArrayList<>(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void addValidPhoneNumber(String number) throws InvalidPhoneNumberException {
        if (NumberValidator.isValid(number)) {
            numbers.add(number);
            saveNumber();
        } else {
            throw new InvalidPhoneNumberException("Invalid phone number: " + number);
        }
    }

    private void saveNumber() {
        try {
            Files.write(Paths.get(filePath), numbers, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getNumbers() {
        return new ArrayList<>(numbers);
    }
}