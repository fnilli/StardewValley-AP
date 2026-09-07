package com.group16.stardewvalley.view.menu;



import com.group16.stardewvalley.controller.menu.MainMenuController;
import com.group16.stardewvalley.model.menu.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements MenuInterface {
    private final MainMenuController controller = new MainMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;

        if ((matcher = GameMenuCommands.Logout.getMatcher(input)) != null) {
            System.out.println(controller.logout());
        }else if ((matcher = GameMenuCommands.ChangeMenu.getMatcher(input)) != null) {
            System.out.println(controller.changeMenu(matcher.group("MenuName")));
        }else if((matcher = GameMenuCommands.ShowCurrentMenu.getMatcher(input)) != null ){
            System.out.println(controller.showCurrentMenu());


        }else{
            System.out.println("invalid command!");
        }
    }
}
