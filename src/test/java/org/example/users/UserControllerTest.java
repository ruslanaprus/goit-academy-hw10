package org.example.users;

import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.example.users.UserController.readUsersFromFile;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private static final Path TEST_JSON_FILE = Paths.get("src/test/resources/test_users.json");
    private static final Path TEST_OUTPUT_FILE = Paths.get("src/test/resources/test_output.txt");

    @BeforeEach
    void setUp() throws IOException {
        Files.createDirectories(TEST_JSON_FILE.getParent());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TEST_JSON_FILE);
        Files.deleteIfExists(TEST_OUTPUT_FILE);
    }

    @Test
    public void testReadUsersFromFile_Success() throws IOException {
        Path file = Paths.get("src/test/java/org/example/users/testfiles/users_example.txt");
        Files.write(file, Arrays.asList("Name Age ", "Peach 2", "Whiskers 3"));

        List<User> expected = Arrays.asList(new User("Peach", 2), new User("Whiskers", 3));
        List<User> actual = readUsersFromFile(file);

        assertEquals(expected, actual);
    }

    @Test
    public void testReadUsersFromFile_EmptyFile() throws IOException {
        Path file = Paths.get("src/test/java/org/example/users/testfiles/users_empty.txt");
        Files.write(file, Arrays.asList("Name Age"));

        List<User> expected = Arrays.asList();
        List<User> actual = readUsersFromFile(file);

        assertEquals(expected, actual);
    }

    @Test
    public void testReadUsersFromFile_IncorrectFormat() throws IOException {
        Path file = Paths.get("src/test/java/org/example/users/testfiles/users_incorrect.txt");
        Files.write(file, Arrays.asList("Name Age", "Pawsters Thirty", "Purrito 5"));

        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> readUsersFromFile(file));
        assertTrue(exception.getMessage().contains("For input string: \"Thirty\""));
    }

    @Test
    public void testReadUsersFromFile_MissingValues() throws IOException {
        Path file = Paths.get("src/test/java/org/example/users/testfiles/users_missing_value.txt");
        Files.write(file, Arrays.asList("Name Age", "Peach"));

        ArrayIndexOutOfBoundsException exception = assertThrows(ArrayIndexOutOfBoundsException.class, () -> readUsersFromFile(file));
        assertTrue(exception.getMessage().contains("Index 1 out of bounds for length 1"));
    }

    @Test
    public void testReadUsersFromFile_HeaderOnly() throws IOException {
        Path file = Paths.get("src/test/java/org/example/users/testfiles/users_header_only.txt");
        Files.write(file, Arrays.asList("Name Age"));

        List<User> expected = Arrays.asList();
        List<User> actual = readUsersFromFile(file);

        assertEquals(expected, actual);
    }

    @Test
    public void testWriteJsonToFile_Success() throws IOException {
        List<User> users = Arrays.asList(new User("Peach", 2), new User("Meowskers", 2));
        Path file = Paths.get("src/test/java/org/example/users/testfiles/users.json");

        UserController.writeJsonToFile(users, file.toString());

        List<User> actual = UserController.readJsonFromFile(file.toString());

        assertEquals(users, actual);
    }

    @Test
    public void testWriteJsonToFile_EmptyList() throws IOException {
        List<User> users = Arrays.asList();
        Path file = Paths.get("src/test/java/org/example/users/testfiles/empty_users.json");

        UserController.writeJsonToFile(users, file.toString());

        List<User> actual = UserController.readJsonFromFile(file.toString());

        assertTrue(actual.isEmpty());
    }

    @Test
    void testReadJsonAndWriteToFile_HappyPath() throws IOException {
        String jsonContent = "[{\"name\":\"alice\",\"age\":21},{\"name\":\"bob\",\"age\":42},{\"name\":\"charlie\",\"age\":17}]";
        Files.write(TEST_JSON_FILE, jsonContent.getBytes());

        UserController.writeUserToFile(TEST_JSON_FILE.toString(), TEST_OUTPUT_FILE.toString());

        List<String> lines = Files.readAllLines(TEST_OUTPUT_FILE);
        assertEquals(3, lines.size());
        assertEquals("alice 21", lines.get(0));
        assertEquals("bob 42", lines.get(1));
        assertEquals("charlie 17", lines.get(2));
    }

    @Test
    void testWriteUserToFile_EmptyJsonFile() throws IOException {
        Files.write(TEST_JSON_FILE, "[]".getBytes());

        UserController.writeUserToFile(TEST_JSON_FILE.toString(), TEST_OUTPUT_FILE.toString());

        List<String> lines = Files.readAllLines(TEST_OUTPUT_FILE);
        assertEquals(0, lines.size());
    }

    @Test
    void testWriteUserToFile_InvalidJsonFile() throws IOException {
        Files.write(TEST_JSON_FILE, "invalid json content".getBytes());

        assertThrows(JsonSyntaxException.class, () -> {
            UserController.writeUserToFile(TEST_JSON_FILE.toString(), TEST_OUTPUT_FILE.toString());
        });
    }

}