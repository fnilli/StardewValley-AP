package com.group16.stardewvalley.model.menu;



import com.group16.stardewvalley.view.menu.MenuInterface;
import com.group16.stardewvalley.view.menu.*;

import java.util.Scanner;

public enum Menu {
    LoginMenu(new LoginMenu(), "Login Menu"),
    ProfileMenu(new ProfileMenu(), "Profile Menu"),
    GameMenu(new GameMenu(), "Game Menu"),
    MainMenu(new MainMenu(), "Main Menu"),
    HomeMenu(new HomeMenu(), "Home Menu"),
    ExitMenu(new ExitMenu(), "Exit Menu"),;

    private final String name;
    private final MenuInterface menu;

    Menu(MenuInterface menu, String name) {
        this.menu = menu;
        this.name = name;
    }

    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }

    public MenuInterface getMenu() {
        return menu;
    }

    public String getName() {
        return name;
    }
}
