package org.example.wordcounter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class WordFrequencyCounter {

    public static void main(String[] args) throws IOException {
        String filePath = "src/main/resources/words.txt";
        System.out.println(calculateWordFrequencyFromFile(filePath));

    }

    protected static Map<String, Integer> calculateWordFrequencyFromFile(String filePath) {
        Path path = Path.of(filePath);
        Map<String, Integer> wordFrequencyMap = new HashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\b[\\W-]+\\b");
                for (String word : words) {
                    wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return wordFrequencyMap;
    }
}
