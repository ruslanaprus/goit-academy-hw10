package org.example.users;

import java.util.Objects;

/**
 * The {@code User} class represents a user with a name and an age.
 * This class provides methods to access the user's details, as well as
 * methods to compare users and obtain a string representation of the user.
 */
public class User {
    private String name;
    private int age;

    /**
     * Constructs a new {@code User} with the specified name and age.
     *
     * @param name The name of the user.
     * @param age  The age of the user.
     */
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Returns the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the age of the user.
     *
     * @return The age of the user.
     */
    public int getAge() {
        return age;
    }

    /**
     * Compares this user to the specified object. The result is {@code true} if and only if the
     * argument is not {@code null} and is a {@code User} object that has the same name and age as this object.
     *
     * @param obj The object to compare this {@code User} against.
     * @return {@code true} if the given object represents a {@code User} equivalent to this user, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return age == user.age && Objects.equals(name, user.name);
    }

    /**
     * Returns a hash code value for the user. This method is supported for the benefit of
     * hash tables such as those provided by {@link java.util.HashMap}.
     *
     * @return A hash code value for this user.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    /**
     * Returns a string representation of the user. The string representation consists of
     * the user's name and age in the format: [name, age].
     *
     * @return A string representation of the user.
     */
    @Override
    public String toString() {
        return "[" + name + ", " + age + "]";
    }
}
