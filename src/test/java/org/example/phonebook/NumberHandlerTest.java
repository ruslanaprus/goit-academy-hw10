package org.example.phonebook;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
        String filePath = "/Users/ruslanaprus/IdeaProjects/goit-academy-hw10/src/main/resources/phoneNumbers.txt";
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
    void testPrintValidNumbers() {
        // Redirecting System.out to capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        numberHandler.printValidPhoneNumbers();

        String printedOutput = outContent.toString().trim();

        assertTrue(printedOutput.contains("123-456-7890"));
        assertTrue(printedOutput.contains("(123) 777-3456"));
        assertTrue(printedOutput.contains("098-765-4321"));

        //resetting System.out
        System.setOut(System.out);
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

    @Test
    void testValidNumbers() {
        assertTrue(NumberValidator.isValid("123-456-7890"));
        assertTrue(NumberValidator.isValid("(123) 777-3456"));
        assertTrue(NumberValidator.isValid("555-123-4567"));
    }

    @Test
    void testInvalidNumbers() {
        assertFalse(NumberValidator.isValid("123-45-6789"));
        assertFalse(NumberValidator.isValid("(123) 456-789"));
        assertFalse(NumberValidator.isValid("12-3456-7890"));
        assertFalse(NumberValidator.isValid("abc-def-ghij"));
        assertFalse(NumberValidator.isValid("1234567890"));
        assertFalse(NumberValidator.isValid("(123) 456 7890"));
    }
}