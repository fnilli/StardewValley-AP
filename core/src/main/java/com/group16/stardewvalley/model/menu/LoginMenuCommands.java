package com.group16.stardewvalley.model.menu;

public enum LoginMenuCommands implements CommandsInterface {


    Register("\\s*register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+(?<passwordConfirm>\\S+)\\s+" +
            "-n\\s+(?<nickName>\\S+)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<gender>\\S+)\\s*"),
        Username( "\\s*[a-zA-Z0-9-]+\\s*"),
        Password( "\\s*[a-zA-Z0-9!@#$%^&*()=+{}\\[\\]|\\\\/:;'\",<>?]+\\s*"),
        Email("\\s*[a-zA-Z0-9](?:[a-zA-Z0-9_-]*\\.?[a-zA-Z0-9_-]+)*" +
                "@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}\\s*"),
    PickSecurityQuestion("\\s*pick\\s+question\\s+-q\\s+(?<questionNumber>\\S+)\\s+-a\\s+(?<answer>\\S+)\\s+-c\\s+(?<answerConfirm>\\S+)\\s*"),
    Login("\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s*(?:-stay-logged-in)?\\s*"),
        ForgetPassword("\\s*forget\\s+password\\s+-u\\s+(?<username>\\S+)\\s*"),
        ForgetPasswordAnswer("\\s*answer\\s+-a\\s+(?<answer>\\S+)\\s*"),
        GetNewPassword("\\s*(?<password>\\S+)\\s*"),
    ChangeMenu("\\s*menu enter (?<MenuName>)\\s*"),
    ShowCurrentMenu("\\s*show\\s+current\\s+menu\\s*");


    private final String pattern;

    LoginMenuCommands(String pattern){
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }



}
