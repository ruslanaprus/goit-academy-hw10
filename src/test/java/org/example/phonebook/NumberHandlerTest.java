package org.example.phonebook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumberHandlerTest {

    private NumberHandler numberHandler;
    private Path tempFile;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws Exception { //@TempDir is used to create a temporary directory for testing, ensuring that each test has a clean file environment.
        tempFile = tempDir.resolve("file.txt");
        Files.createFile(tempFile);

        org.example.phonebook.NumberHandlerTestHelper.setFilePath(NumberHandler.getInstance(), tempFile.toString());
        numberHandler = NumberHandler.getInstance();
    }

    @Test
    void getInstance() {
    }

    @Test
    void testReadFileWithValidNumbers() throws Exception {
        Files.write(tempFile, List.of("123-456-7890", "(123) 777-3456"));
        numberHandler.readFile(tempFile.toString());
        List<String> numbers = numberHandler.getNumbers();
        assertTrue(numbers.contains("(123) 777-3456"));
        assertTrue(numbers.contains("123-456-7890"));
    }

    @Test
    void add() {
    }

    @Test
    void getNumbers() {
    }
}