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
        System.out.println(readWordsFromFile(filePath));

    }

    protected static Set<String> readWordsFromFile(String filePath) {
        Path path = Path.of(filePath);
        Set<String> uniqueWords = new HashSet<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\s+");
                for (String word : words) {
                    word = word.replaceAll("[\"!?,.]", "");
                    uniqueWords.add(word);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uniqueWords;
    }
}
