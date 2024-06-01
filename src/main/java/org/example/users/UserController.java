package org.example.users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    public static void main(String[] args) {

        String file = "/Users/ruslanaprus/IdeaProjects/goit-academy-hw10/src/main/resources/users.txt";
        Path filePath = Paths.get(file);

        try {
            List<User> users = readUsersFromFile(filePath);
            UserController.convertToJson(users);
            writeJsonToFile(users, "/Users/ruslanaprus/IdeaProjects/goit-academy-hw10/src/main/resources/user.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeJsonToFile(List<User> users, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readUsersFromFileAsBytes(Path filePath) throws IOException {
        return Files.readAllBytes(filePath);
    }

    public static List<User> readUsersFromFile(Path filePath) throws IOException {
        byte[] fileBytes = readUsersFromFileAsBytes(filePath);
        String fileContent = new String(fileBytes);

        String[] lines = fileContent.split("\\r?\\n");

        List<User> users = new ArrayList<>();

        // Skip the first line (header)
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split(" ");
            String name = parts[0];
            int age = Integer.parseInt(parts[1]);
            users.add(new User(name, age));
        }

        return users;
    }

    public static void convertToJson(List<User> users) {
        for (User user : users) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonNew = gson.toJson(user);
            System.out.println("" + jsonNew);
        }
    }
}
