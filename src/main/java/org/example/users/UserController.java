package org.example.users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    public static void main(String[] args) {
        User newUser = new User("kitten", 1);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonNew = gson.toJson(newUser);
        System.out.println("" + jsonNew);
    }
}
