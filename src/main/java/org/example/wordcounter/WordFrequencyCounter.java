package org.example.wordcounter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordFrequencyCounter {

    public static void main(String[] args) {
        String filePath = "src/main/resources/words.txt";

        System.out.println(readWordsFromFile(filePath));
    }

    public static List<String> readWordsFromFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            return new ArrayList<>(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
