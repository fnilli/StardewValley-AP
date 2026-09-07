package com.group16.stardewvalley.view.menu;



import com.group16.stardewvalley.controller.menu.LoginMenuController;
import com.group16.stardewvalley.model.menu.LoginMenuCommands;
import com.group16.stardewvalley.model.Result;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements MenuInterface {

    private final LoginMenuController controller = new LoginMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;
        //register
        if ((matcher = LoginMenuCommands.Register.getMatcher(input)) != null) {
//            Result result = controller.register(matcher.group("username"),
//                    matcher.group("password"), matcher.group("passwordConfirm"), matcher.group("nickName"),
//                    matcher.group("email"), matcher.group("gender"));
//
//            System.out.println(result);
//            boolean success = result.isSuccessful();


            //random password handling

//            if (success) {
//                //security questions have been shown
//                //get security question
//                String input2 = scanner.nextLine();
//                Matcher matcher2 = LoginMenuCommands.PickSecurityQuestion.getMatcher(input2);
//                if (matcher2 != null) {
//                    System.out.println(controller.setSecurityQuestion(matcher.group("username"), matcher2.group("questionNumber"),
//                            matcher2.group("answer"), matcher2.group("answerConfirm")));
//                }
//
//            }

        }else if((matcher = LoginMenuCommands.Login.getMatcher(input)) != null) {
            boolean logged_in_flag = false;
            if(input.contains("-stay-logged-in")) {
                logged_in_flag = true;
            }
            System.out.println(controller.login(matcher.group("username"), matcher.group("password"), logged_in_flag));
            System.out.println(controller.showMenus());


        }else if((matcher = LoginMenuCommands.ForgetPassword.getMatcher(input)) != null){

            Result result = controller.forgetPassword(matcher.group("username"));
            System.out.println(result);
            if(result.isSuccessful()) {

                String input2 = scanner.nextLine();
                Matcher matcher2 = LoginMenuCommands.ForgetPasswordAnswer.getMatcher(input2);
                if(matcher2 != null) {
                    Result result1 = controller.checkSecurityAnswer(matcher.group("username"), matcher2.group("answer"));
                    System.out.println(result1);
                    if (result1.isSuccessful()) {

                        String input3 = scanner.nextLine();
                        Matcher matcher3 = LoginMenuCommands.GetNewPassword.getMatcher(input3);
                        if(matcher3 != null) {
//                            System.out.println(controller.getNewPassword( matcher.group("username") ,matcher3.group("password")));
                        }else {
                            System.out.println("invalid password!");

                        }
                    }
                }
            }

        }else if((matcher = LoginMenuCommands.ShowCurrentMenu.getMatcher(input)) != null ){
            System.out.println(controller.showCurrentMenu());


        }

        else{
            System.out.println("invalid command!");
        }
    }
}
