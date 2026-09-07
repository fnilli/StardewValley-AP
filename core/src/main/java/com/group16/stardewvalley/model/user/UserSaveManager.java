package com.group16.stardewvalley.model.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.group16.stardewvalley.model.app.App;

import java.io.File;
import java.util.ArrayList;

public class UserSaveManager {
    private static final String FILE_PATH = "users.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        // Optional: Make JSON pretty and readable
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static void saveUsers() {
        try {
            mapper.writeValue(new File(FILE_PATH), App.getUsers());
            System.out.println("Users saved successfully.");
        } catch (Exception e) {
            System.err.println("Error saving users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void loadUsers() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                ArrayList<User> users = mapper.readValue(file,
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, User.class));
                App.setUsers(users);
                System.out.println("Users loaded successfully.");
            } else {
                System.out.println("No saved users found.");
            }
        } catch (Exception e) {
            System.err.println("Error loading users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean isUsernameTaken(String username) {
        return App.getUsers().stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    public static void addUserAndSave(User user) {
        loadUsers(); // refresh in-memory list
        if (!isUsernameTaken(user.getUsername())) {
            App.getUsers().add(user);
            saveUsers();
        }
    }
}
