package org.example.users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserController {
    public static void main(String[] args) {

        String file = "src/main/resources/users.txt";
        Path filePath = Paths.get(file);

        try {
            List<User> users = readUsersFromFile(filePath);
            writeJsonToFile(users, "src/main/resources/user.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void writeJsonToFile(List<User> users, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static List<User> readJsonFromFile(String fileName) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(fileName)) {
            User[] usersArray = gson.fromJson(reader, User[].class);
            return Arrays.asList(usersArray);
        }
    }

    public static List<User> readUsersFromFile(Path filePath) throws IOException {
        List<User> users = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(filePath);
            // Skip the header
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split("\\s+");
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                users.add(new User(name, age));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}