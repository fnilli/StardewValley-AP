package com.group16.stardewvalley.controller.menu;


// Import your other Views for Settings, Profile, Scoreboard, Talent, Login etc.

import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.menu.Menu;
import com.group16.stardewvalley.view.menuGraphics.MainMenuView;
import com.group16.stardewvalley.view.menuGraphics.StartMenuView;

public class MainMenuController {
    private MainMenuView view;


    public void setView(MainMenuView view) {
        this.view = view;
    }


    public Result changeMenu(String menuName){
        return switch (menuName) {
            case "Profile Menu" -> {
                App.setCurrentMenu(Menu.ProfileMenu);
                yield new Result(true, "you are in the profile menu!");
            }
            case "Game Menu" -> {
                App.setCurrentMenu(Menu.GameMenu);
                yield new Result(true, "you are in the game menu!");
            }
            case "Avatar Menu" -> {
                //TODO
                yield new Result(true, "you are in the avatar menu!");
            }
            default -> new Result(false, "wrong menu name!");
        };
    }

    public Result logout(){
        App.setLoggedInUser(null);
        App.setCurrentPlayer(null);
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true, "you are in login menu now!");
    }

    public Result showCurrentMenu(){
        return new Result(true, App.getCurrentMenu().getName());
    }
}
