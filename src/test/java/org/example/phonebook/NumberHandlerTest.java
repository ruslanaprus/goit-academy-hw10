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

        numberHandler = NumberHandler.getInstance(tempFile.toString());
    }

    @Test
    // test if the method runs without exceptions
    public void testReadFile() {
        numberHandler.readFile(tempFile.toString());
    }

    @Test
    void testReadFileWithValidNumbers() throws Exception {
        Files.write(tempFile, List.of("(123) 777-3456"));
        NumberHandler.readFile(tempFile.toString());
        List<String> numbers = numberHandler.getNumbers();
 //       assertEquals(numbers.get(0), "(123) 777-3456");
        assertTrue(numbers.contains("(123) 777-3456"));
    }

    @Test
    void testAddValidNumber() throws Exception {
        numberHandler.add("(123) 456-7890");
        List<String> numbers = numberHandler.getNumbers();
        assertTrue(numbers.contains("(123) 456-7890"));
    }

    @Test
    void testAddInvalidNumberThrowsException() {
        assertThrows(InvalidPhoneNumberException.class, () -> numberHandler.add("123-45-7890"));
        assertThrows(InvalidPhoneNumberException.class, () -> numberHandler.add("(123)-45-7890"));
        assertThrows(InvalidPhoneNumberException.class, () -> numberHandler.add("1-4-70"));
        assertThrows(InvalidPhoneNumberException.class, () -> numberHandler.add("123 45 7890"));
        assertThrows(InvalidPhoneNumberException.class, () -> numberHandler.add("123-(45)-7890"));
        assertThrows(InvalidPhoneNumberException.class, () -> numberHandler.add("1232-45-7890"));
    }
}