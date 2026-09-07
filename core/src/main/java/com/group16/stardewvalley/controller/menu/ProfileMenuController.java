package com.group16.stardewvalley.controller.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.graphics.AnimatedSpriteActor;
import com.group16.stardewvalley.model.graphics.Heros;
import com.group16.stardewvalley.model.menu.Menu;
import com.group16.stardewvalley.model.menu.ProfileMenuCommands;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.model.user.UserSaveManager;
import com.group16.stardewvalley.view.menuGraphics.ProfileMenuView;

public class ProfileMenuController {
    //graphical elements
    private ProfileMenuView view;
    private final User currentUser;


    public ProfileMenuController() {
        this.currentUser = App.getLoggedInUser();
    }

    public void setView(ProfileMenuView view) {
        this.view = view;
    }



    //main elements

    public Result changeUsername(String username){
        User user = App.getLoggedInUser();
        if (ProfileMenuCommands.Username.getMatcher(username) == null) {
            return new Result(false, "username format is invalid!");
        }
        if(user.getUsername().equals(username)){
            return new Result(false, "enter a new username!");
        }
        if(!doesUsernameExists(username)){
            return new Result(false, "username is already taken");
        }

        user.setUsername(username);
        UserSaveManager.saveUsers();
        return new Result(true, "username successfully changed!");
    }

    public Result changeNickName(String nickname){
        User user = App.getLoggedInUser();

        if(user.getNickName().equals(nickname)){
            return new Result(false, "enter a new nickname!");
        }

        user.setNickName(nickname);
        UserSaveManager.saveUsers();

        return new Result(true, "nickname successfully changed!");
    }

    public Result changeEmail(String email){
        User user = App.getLoggedInUser();
        if(ProfileMenuCommands.Email.getMatcher(email) == null) {
            return new Result(false, "email format is invalid!");
        }
        if(user.getEmail().equals(email)){
            return new Result(false, "enter a new email!");
        }
        user.setEmail(email);
        UserSaveManager.saveUsers();

        return new Result(true, "email successfully changed!");
    }

    public Result changePassword(String oldPassword, String newPassword ){
        User user = App.getLoggedInUser();
        if(!user.getPassword().equals(oldPassword)){
            return new Result(false, "enter your old password correctly!");
        }
        if (newPassword.length() < 8) {
            return new Result(false, "weak password! password must be at least 8 characters.");
        }
        if (!newPassword.matches(".*[a-z].*")){
            return new Result(false, "weak password! password should contains at least one lowercase letter.");
        }
        if (!newPassword.matches(".*[A-Z].*")){
            return new Result(false, "weak password! password should contains at least one uppercase letter.");
        }
        if (!newPassword.matches(".*[0-9].*")){
            return new Result(false, "weak password! password should contains at least one number.");
        }
        if (!newPassword.matches(".*[!#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")){
            return new Result(false, "weak password! password should contains at least one special character.");
        }
        if(user.getPassword().equals(newPassword)){
            return new Result(false, "enter a new password!");
        }


        user.setPassword(newPassword);
        UserSaveManager.saveUsers();

        return new Result(true, "password successfully changed!");
    }

    public Result setAvatar(Heros hero) {
        User user = App.getLoggedInUser();

        if (hero == null ) {
            return new Result(false, "Hero name cannot be empty.");
        }



        user.setHero(hero);
        UserSaveManager.saveUsers();

        return new Result(true, "Avatar successfully changed to " + hero.toString() );
    }


    public Result showUserInfo(){
        User user = App.getLoggedInUser();
        Player player1 = null;
        for(Game game: App.getGames()){
            for (Player player : game.getPlayers()) {
                if(player.getUser().equals(user)){
                    player1 = player;
                }
            }
        }

        int money = 0;
        if (player1 != null) {
            money = player1.getCoin();
        }


        String output =
                "username: " + user.getUsername() + "\nnickname: " + user.getNickName() +
                "\nmost money reached: " + money + "\ngame played: " + user.getGamePlayed();

        return new Result(true, output);
    }

    public Result exitMenu(){
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "you are in the main menu!");
    }




    //private methods


    private boolean doesUsernameExists(String username) {
        for (User user : App.users){
            if(user.getUsername().equals(username) ){
                return false;
            }
        }
        return true;
    }

    public Result showCurrentMenu(){
        return new Result(true, App.getCurrentMenu().getName());
    }

    private AnimatedSpriteActor createHero(Heros hero) {
        Texture texture = new Texture(Gdx.files.internal(hero.getTexturePath()));
        return new AnimatedSpriteActor(texture, hero.getFrameWidth(), hero.getFrameHeight(), hero.getUpRow(), 0.3f);
    }


}

