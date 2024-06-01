package org.example.users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
            for (User user : users) {
                System.out.println(user.getName() + " " + user.getAge());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<User> users = readUsersFromFile(filePath);
            for (User user : users) {
                System.out.println(user.getName() + " " + user.getAge());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        User newUser = new User("kitten", 1);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonNew = gson.toJson(newUser);
        System.out.println("" + jsonNew);

    }

    public static byte[] readUsersFromFileAsBytes(Path filePath) throws IOException {
        return Files.readAllBytes(filePath);
    }

    public static List<User> readUsersFromFile(Path filePath) throws IOException {
        byte[] fileBytes = readUsersFromFileAsBytes(filePath);
        String fileContent = new String(fileBytes); // Convert bytes to string

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
            System.out.println(user.getName() + " " + user.getAge());
        }
    }
}
