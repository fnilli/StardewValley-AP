package com.group16.stardewvalley.view.menu;



import com.group16.stardewvalley.controller.menu.ProfileMenuController;
import com.group16.stardewvalley.model.menu.LoginMenuCommands;
import com.group16.stardewvalley.model.menu.ProfileMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements MenuInterface {
    private final ProfileMenuController controller = new ProfileMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;
        if( (matcher = ProfileMenuCommands.ChangeUsername.getMatcher(input)) != null ) {
            System.out.println(controller.changeUsername(matcher.group("username")));

        }else if((matcher = ProfileMenuCommands.ChangeNickName.getMatcher(input)) != null ) {
            System.out.println(controller.changeNickName(matcher.group("nickname")));

        }else if( (matcher = ProfileMenuCommands.ChangeEmail.getMatcher(input)) != null ) {
            System.out.println(controller.changeEmail(matcher.group("email")));

        }else if((matcher = ProfileMenuCommands.ChangePassword.getMatcher(input)) != null ) {
//            System.out.println(controller.changePassword(matcher.group("newPassword"), matcher.group("oldPassword")));

        }else if((matcher = ProfileMenuCommands.ShowInfo.getMatcher(input)) != null ) {
            System.out.println(controller.showUserInfo());
        }else if((matcher = LoginMenuCommands.ShowCurrentMenu.getMatcher(input)) != null ){
            System.out.println(controller.showCurrentMenu());

        }else if(( matcher = ProfileMenuCommands.ExitMenu.getMatcher(input)) != null ) {
            System.out.println(controller.exitMenu());
        }else {
            System.out.println("invalid command!");
        }
    }
}
