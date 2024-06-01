package org.example.phonebook;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumberHandlerTest {

    private NumberHandler numberHandler;
    private Path tempFile;
    private String originalContent;

    @BeforeEach
    void setUp() throws Exception {
        String filePath = "/Users/ruslanaprus/IdeaProjects/goit-academy-hw10/src/main/resources/file.txt";
        numberHandler = NumberHandler.getInstance(filePath);
        tempFile = Path.of(filePath);
        originalContent = Files.readString(tempFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        // Restore the original content of the file
        Files.writeString(tempFile, originalContent);
    }

    @Test
    void testReadValidPhoneNumbersRunsWithoutException() {
        numberHandler.readValidPhoneNumbers(tempFile.toString());
    }

    @Test
    void testReadFileWithValidNumbers() throws Exception {
        List<String> numbers = numberHandler.readValidPhoneNumbers(tempFile.toString());
        assertEquals(numbers.get(0), "123-456-7890");
        assertTrue(numbers.contains("123-456-7890"));
        assertTrue(numbers.contains("(123) 777-3456"));
        assertFalse(numbers.contains("1-4-66"));
        assertFalse(numbers.contains("123-987-345"));
        assertFalse(numbers.contains("123 46-67"));
    }

    @Test
    void testAddValidNumber() throws Exception {
        numberHandler.addValidPhoneNumber("(111) 456-7890");
        List<String> numbers = numberHandler.getNumbers();
        assertTrue(numbers.contains("(111) 456-7890"));
    }

    @Test
    void testAddInvalidNumberThrowsException() {
        assertThrows(InvalidPhoneNumberException.class, () -> numberHandler.addValidPhoneNumber("123-45-7890"));
        assertThrows(InvalidPhoneNumberException.class, () -> numberHandler.addValidPhoneNumber("(123)-45-7890"));
        assertThrows(InvalidPhoneNumberException.class, () -> numberHandler.addValidPhoneNumber("1-4-70"));
        assertThrows(InvalidPhoneNumberException.class, () -> numberHandler.addValidPhoneNumber("123 45 7890"));
        assertThrows(InvalidPhoneNumberException.class, () -> numberHandler.addValidPhoneNumber("123-(45)-7890"));
        assertThrows(InvalidPhoneNumberException.class, () -> numberHandler.addValidPhoneNumber("1232-45-7890"));
    }
}