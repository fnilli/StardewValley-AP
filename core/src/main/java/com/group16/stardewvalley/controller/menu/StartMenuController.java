package com.group16.stardewvalley.controller.menu;


import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.view.menuGraphics.LoginMenuView;
import com.group16.stardewvalley.view.menuGraphics.SignUpMenuView;
import com.group16.stardewvalley.view.menuGraphics.StartMenuView;

public class StartMenuController {
    private StartMenuView view;

    public void setView(StartMenuView view) {
        this.view = view;
    }

    public void onRegisterClicked() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SignUpMenuView(new SignUpMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void onLoginClicked() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }





}
