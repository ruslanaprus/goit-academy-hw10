package org.example.users;

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
}