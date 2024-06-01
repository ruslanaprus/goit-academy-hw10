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
            byte[] readContent = readUsersFromFile(filePath);
            System.out.println(new String(readContent));
        } catch (IOException e) {
            e.printStackTrace();
        }

        User newUser = new User("kitten", 1);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonNew = gson.toJson(newUser);
        System.out.println("" + jsonNew);
    }

    public static byte[] readUsersFromFile(Path filePath) throws IOException {
        return Files.readAllBytes(filePath);
    }
}
