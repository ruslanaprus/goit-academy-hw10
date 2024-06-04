package org.example;

import org.example.users.User;
import org.example.users.UserController;
import org.example.wordcounter.WordFrequencyCounter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String fileUsers = "src/main/resources/users.txt";
        Path filePathUsers = Paths.get(fileUsers);
        try {
            List<User> users = UserController.readUsersFromFile(filePathUsers);
            UserController.writeJsonToFile(users, "src/main/resources/user.json");
            UserController.writeUserToFile("src/main/resources/user.json", "src/main/resources/users_from_json.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String filePath = "src/main/resources/words.txt";
        String frequencyFilePath = "src/main/resources/word_frequency.txt";
        List<String> words = WordFrequencyCounter.readWordsFromFile(filePath);
        Map<String, Integer> frequencyMap = WordFrequencyCounter.calculateWordFrequency(words);
        WordFrequencyCounter.writeResultsToFile(WordFrequencyCounter.sortByFrequency(frequencyMap), frequencyFilePath);
    }
}