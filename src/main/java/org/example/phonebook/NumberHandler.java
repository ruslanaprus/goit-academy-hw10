package org.example.phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NumberHandler {

//    private static final String FILE_PATH = "/Users/ruslanaprus/IdeaProjects/goit-academy-hw10/src/main/resources/file.txt";
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
            Files.write(Paths.get(filePath), numbers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getNumbers() {
        System.out.println("nums" + numbers);
//        return new ArrayList<>(numbers);
        return numbers;
    }
}