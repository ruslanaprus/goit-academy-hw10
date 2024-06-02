/**
 * This package contains classes for performing word frequency analysis on text files.
 * It provides functionality to read words from a file, calculate their frequencies,
 * sort them by frequency, and write the results to a file.
 */
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

/**
 * The {@code WordFrequencyCounter} class provides methods to read words from a file,
 * calculate their frequencies, sort them by frequency, and write the results to a file.
 * This is useful for textual analysis where understanding the frequency of words is required.
 */
public class WordFrequencyCounter {

    /**
     * The main method that orchestrates the reading of words from a file,
     * calculating their frequencies, sorting by frequency, and writing the results to a file.
     *
     * @param args command-line arguments (not used)
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        String filePath = "src/main/resources/words.txt";
        String frequencyFilePath = "src/main/resources/word_frequency.txt";
        List<String> words = readWordsFromFile(filePath);
        Map<String, Integer> frequencyMap = calculateWordFrequency(words);
        writeResultsToFile(sortByFrequency(frequencyMap), frequencyFilePath);
    }

    /**
     * Reads words from a specified file and returns them as a list of strings.
     * This method uses a regular expression to match words, which include alphabets, hyphens, and apostrophes.
     *
     * @param filePath the path of the file to read words from
     * @return a list of words read from the file
     */
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

    /**
     * Calculates the frequency of each word in the given list of words.
     * The frequency count is case-insensitive and stored in a {@code TreeMap} for natural ordering by keys.
     *
     * @param words a list of words for which to calculate the frequency
     * @return a map where keys are words and values are their respective frequencies
     */
    protected static Map<String, Integer> calculateWordFrequency(List<String> words) {
        Map<String, Integer> wordFrequencyMap = new TreeMap<>();
        for (String word : words) {
            wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
        }
        return wordFrequencyMap;
    }

    /**
     * Writes the word frequency results to a specified file.
     * Each line in the output file contains a word and its frequency, separated by a space.
     *
     * @param wordFrequencyMap a map where keys are words and values are their respective frequencies
     * @param filePath         the path of the file to write the results to
     */
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

    /**
     * Sorts the word frequency map by frequency in descending order.
     * Words with higher frequencies appear first in the sorted map.
     *
     * @param wordCounts a map where keys are words and values are their respective frequencies
     * @return a sorted map by frequency in descending order
     */
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
