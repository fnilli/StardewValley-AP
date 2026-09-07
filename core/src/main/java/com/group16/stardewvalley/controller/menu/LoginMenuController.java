package com.group16.stardewvalley.controller.menu;


import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.menu.LoginMenuCommands;
import com.group16.stardewvalley.model.menu.Menu;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.model.user.UserSaveManager;
import com.group16.stardewvalley.view.menuGraphics.LoginMenuView;
import com.group16.stardewvalley.view.menuGraphics.StartMenuView;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.group16.stardewvalley.model.user.User.getUserByUsername;


public class LoginMenuController  {
    private LoginMenuView view;
    public void setView(LoginMenuView view) {
        this.view = view;
    }


    public com.badlogic.gdx.scenes.scene2d.ui.Skin getSkin() {
        return GameAssetManager.getGameAssetManager().getSkin();
    }

    //login  methods
    public Result login(String username, String password, boolean stayLoggedIn){
        User user = getUserByUsername(username);
        if(user == null) {
            return new Result(false, "username doesn't exist!");
        }
        if(!(user.getPassword().equals(password))) {
            return new Result(false, "password is incorrect!");
        }

        // log in and set user as logged in
        App.setLoggedInUser(user);
        App.getLoggedInUser().setLogged_in_flag(stayLoggedIn);
        System.out.println(stayLoggedIn);

        //change menu to main menu
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "logged in successfully!");
    }

    public Result forgetPassword(String username){
        User user = getUserByUsername(username);
        if(user == null) {
            return new Result(false, "username doesn't exist!");
        }
        return new Result(true, "answer to your security question:\n" + user.getUserSecurityQuestion().getQuestion());
    }

    public Result checkSecurityAnswer(String username, String answer){
        User user = getUserByUsername(username);

        if(!user.getSecurityAnswer().equals(answer)) {
            return new Result(false, "your answer is incorrect!");
        }
        return new Result(true, "enter a new password, or 'random' if you want a random password.");
    }

    public Result getNewPassword(User user, String password){
        //validate password


        if(password.isEmpty()) {
            return new Result(false, "Password cannot be empty.");
        }
        if (LoginMenuCommands.Password.getMatcher(password) == null) {
            return new Result(false, "password format is invalid!");
        }
        if (password.length() < 8) {
            return new Result(false, "weak password! password must be at least 8 characters.");
        }
        if (!password.matches(".*[a-z].*")){
            return new Result(false, "weak password! password should contains at least one lowercase letter.");
        }
        if (!password.matches(".*[A-Z].*")){
            return new Result(false, "weak password! password should contains at least one uppercase letter.");
        }
        if (!password.matches(".*[0-9].*")){
            return new Result(false, "weak password! password should contains at least one number.");
        }
        if (!password.matches(".*[!#$%^&*()_+\\-=\\[\\]{};':\"|,.<>/?].*")){
            return new Result(false, "weak password! password should contains at least one special character.");
        }

        //Strong password -> set as new password
        user.setPassword(password);
        UserSaveManager.saveUsers();

        return new Result(true, "password changed successfully!");

    }

    public String generateRandomPassword() {
        final String LOWER = "abcdefghijklmnopqrstuvwxyz";
        final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String DIGITS = "0123456789";
        final String SPECIAL = "!#$%^&*()=+{}[]|\\/:;'\",<>?"; // No whitespace
        final String ALL_CHARS = LOWER + UPPER + DIGITS + SPECIAL;
        final SecureRandom random = new SecureRandom();

        int length = 8 + random.nextInt(13); // 8-20 chars

        List<Character> password = new ArrayList<>();

        // Ensure at least one of each type (lower, upper, digit, special)
        password.add(LOWER.charAt(random.nextInt(LOWER.length())));
        password.add(UPPER.charAt(random.nextInt(UPPER.length())));
        password.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.add(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        // Fill the rest with random allowed chars
        for (int i = 4; i < length; i++) {
            password.add(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        Collections.shuffle(password); // Mix the characters

        // Convert to String
        StringBuilder sb = new StringBuilder();
        for (char c : password) {
            sb.append(c);
        }

        return sb.toString();
    }

    public Result showMenus() {
        String output = "you can go to these menus from Main menu:\n1- Profile Menu\n2- Login Menu\n3- Game Menu";
        return new Result(true, output);
    }

    public Result showCurrentMenu(){
        return new Result(true, App.getCurrentMenu().getName());
    }





}
