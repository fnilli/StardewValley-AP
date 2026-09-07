package com.group16.stardewvalley.model.user;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.group16.stardewvalley.model.app.App;

import java.util.ArrayList;

public class UserDatabase {

    private static final String USER_DB_PATH = "users.json"; // path relative to assets folder or local storage
    private static final Json json = new Json();

    public static void loadUsers() {
        FileHandle file = Gdx.files.local(USER_DB_PATH);

        if (file.exists()) {
            try {
                JsonReader reader = new JsonReader();
                JsonValue jsonValue = reader.parse(file.readString());
                ArrayList<User> loadedUsers = new ArrayList<>();

                for (JsonValue userJson : jsonValue) {
                    User user = json.fromJson(User.class, userJson.toString());
                    loadedUsers.add(user);
                }

                App.setUsers(loadedUsers);
                System.out.println("Users loaded: " + loadedUsers.size());

            } catch (Exception e) {
                System.err.println("Failed to load users: " + e.getMessage());
            }
        } else {
            App.setUsers(new ArrayList<>()); // if no file, init empty list
        }
    }

    public static void saveUsers() {
        try {
            String jsonString = json.prettyPrint(App.getUsers());
            FileHandle file = Gdx.files.local(USER_DB_PATH);
            file.writeString(jsonString, false); // overwrite
            System.out.println("Users saved.");
        } catch (Exception e) {
            System.err.println("Failed to save users: " + e.getMessage());
        }
    }

    public static boolean register(String username, String password, String securityAnswer) {
        if (isUsernameTaken(username)) return false;

        // Add default dummy values for missing fields (nickName, email, gender)
        User newUser = new User(username, password, "nickname", "email@example.com", "unspecified");
        newUser.setSecurityAnswer(securityAnswer);

        App.users.add(newUser);
        saveUsers();
        return true;
    }

    public static boolean isUsernameTaken(String username) {
        return User.getUserByUsername(username) != null;
    }
}
