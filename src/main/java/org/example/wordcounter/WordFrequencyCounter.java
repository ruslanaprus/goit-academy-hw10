package org.example.wordcounter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFrequencyCounter {

    public static void main(String[] args) throws IOException {
        String filePath = "src/main/resources/words.txt";
        String frequencyFilePath = "src/main/resources/word_frequency.txt";
        List<String> words = readWordsFromFile(filePath);
        Map<String, Integer> frequencyMap = calculateWordFrequency(words);
        writeResultsToFile(sortByFrequency(frequencyMap), frequencyFilePath);
    }

    protected static List<String> readWordsFromFile(String filePath) {
        Path path = Path.of(filePath);
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            final Pattern MATCH_ALL_WORDS = Pattern.compile("([\\w-’']+)");
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = MATCH_ALL_WORDS.matcher(line.toLowerCase());
                while (matcher.find()) {
                    words.add(matcher.group());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return words;

    }

    protected static Map<String, Integer> calculateWordFrequency(List<String> words) {
        Map<String, Integer> wordFrequencyMap = new TreeMap<>();
        for (String word : words) {
            wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
        }
        return wordFrequencyMap;
    }

    protected static void writeResultsToFile(Map<String, Integer> wordFrequencyMap, String filePath) {
        Path path = Path.of(filePath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static Map<String, Integer> sortByFrequency(Map<String, Integer> wordCounts) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(wordCounts.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });
        Map<String, Integer> sortedWordCounts = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entries) {
            sortedWordCounts.put(entry.getKey(), entry.getValue());
        }
        return sortedWordCounts;
    }
}
