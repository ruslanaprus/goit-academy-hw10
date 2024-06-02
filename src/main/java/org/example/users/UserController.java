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

/**
 * The {@code UserController} class provides functionality to read user data from a text file,
 * convert it to a JSON format, and write it to a JSON file. This class utilizes Google's Gson library
 * for JSON processing and Java NIO for file operations.
 */
public class UserController {

    /**
     * The main method that serves as the entry point of the application.
     * It reads user data from a text file and writes it to a JSON file.
     *
     * @param args Command line arguments (not used in this application).
     */
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

    /**
     * Writes a list of users to a JSON file.
     *
     * @param users    The list of users to be written to the JSON file.
     * @param fileName The name of the JSON file where the user data will be written.
     */
    protected static void writeJsonToFile(List<User> users, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a list of users from a JSON file.
     *
     * @param fileName The name of the JSON file from which the user data will be read.
     * @return A list of users read from the JSON file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    protected static List<User> readJsonFromFile(String fileName) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(fileName)) {
            User[] usersArray = gson.fromJson(reader, User[].class);
            return Arrays.asList(usersArray);
        }
    }

    /**
     * Reads user data from a text file and returns a list of users.
     * The text file is expected to have a header line followed by lines of user data.
     * Each line of user data should contain the user's name and age separated by whitespace.
     *
     * @param filePath The path to the text file containing the user data.
     * @return A list of users read from the text file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
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
