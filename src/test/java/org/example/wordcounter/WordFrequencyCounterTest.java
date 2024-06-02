package org.example.wordcounter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WordFrequencyCounterTest {

    private static final String TEST_INPUT_FILE = "src/test/java/org/example/users/testfiles/test_words.txt";
    private static final String TEST_OUTPUT_FILE = "src/test/java/org/example/users/testfiles/test_word_frequency.txt";

    @BeforeEach
    void setUp() throws IOException {
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_INPUT_FILE));
        Files.deleteIfExists(Path.of(TEST_OUTPUT_FILE));
    }

    @Test
    void testReadWordsFromFile() throws IOException {
        Files.writeString(Path.of(TEST_INPUT_FILE),
                "meow, meow, adventure, paw's");
        List<String> testWods = List.of("meow", "meow", "adventure", "paw's");
        List<String> actualWords = WordFrequencyCounter.readWordsFromFile(TEST_INPUT_FILE);
        assertEquals(testWods, actualWords, "expected: adventure, meow, paw's");
    }

    @Test
    void testReadWordsFromFileUsingVariousCharacters() throws IOException {
        Files.writeString(Path.of(TEST_INPUT_FILE),
                "meow-meow,\ncat purrs on the tree!\nkitten's paw\nmeow (hello) to you\n");
        assertEquals(12, WordFrequencyCounter.readWordsFromFile(TEST_INPUT_FILE).size());
    }

    @Test
    void testWriteResultsToFile() throws IOException {
        Map<String, Integer> frequencyMap = new LinkedHashMap<>();
        frequencyMap.put("whiskers", 3);
        frequencyMap.put("meow", 2);

        WordFrequencyCounter.writeResultsToFile(frequencyMap, TEST_OUTPUT_FILE);

        List<String> lines = Files.readAllLines(Path.of(TEST_OUTPUT_FILE));
        assertEquals("whiskers 3", lines.get(0));
        assertEquals("meow 2", lines.get(1));
    }

    @Test
    void testSortByFrequency() {
        Map<String, Integer> frequencyMap = new TreeMap<>();
        frequencyMap.put("purr", 3);
        frequencyMap.put("kitten", 2);
        frequencyMap.put("meow", 5);

        Map<String, Integer> sortedFrequencyMap = WordFrequencyCounter.sortByFrequency(frequencyMap);

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(sortedFrequencyMap.entrySet());
        assertEquals("meow", entries.get(0).getKey());
        assertEquals(5, entries.get(0).getValue());
        assertEquals("purr", entries.get(1).getKey());
        assertEquals(3, entries.get(1).getValue());
        assertEquals("kitten", entries.get(2).getKey());
        assertEquals(2, entries.get(2).getValue());
    }
}