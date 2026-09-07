package com.group16.stardewvalley.model.menu;

import java.util.regex.Pattern;

public enum ProfileMenuCommands implements CommandsInterface {
    ShowInfo("\\s*user\\s+info\\s*"),
    ChangeUsername("\\s*change\\s+username\\s+-u\\s+(?<username>\\S+)\\s*"),
        Username( "\\s*[a-zA-Z0-9-]+\\s*"),
    ChangeNickName("\\s*change\\s+nickname\\s+-n\\s+(?<nickname>\\S+)\\s*"),
    ChangeEmail("\\s*change\\s+email\\s+-e\\s+(?<email>\\S+)\\s*"),
        Email("\\s*[a-zA-Z0-9](?:[a-zA-Z0-9_-]*\\.?[a-zA-Z0-9_-]+)*" +
            "@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}\\s*"),
    ChangePassword("\\s*change\\s+password\\s+-p\\s+(?<newPassword>\\S+)\\s+-o\\s+(?<oldPassword>\\S+)\\s*"),
        Password( "\\s*[a-zA-Z0-9!#$%^&*()=+{}\\[\\]|\\\\/:;'\",<>?]+\\s*"),   // doesnt include @
    ShowCurrentMenu("\\s*show\\s+current\\s+menu\\s*"),

    ExitMenu("\\s*menu\\s+exit\\s*");

    private final String pattern;
    ProfileMenuCommands(String pattern) {
        this.pattern = pattern;
    }



    public static final Pattern UsernamePattern = Pattern.compile("");
    public static final Pattern PasswordPattern = Pattern.compile("");
    public static final Pattern EmailPattern = Pattern.compile("");
    public static final Pattern nickNamePattern = Pattern.compile("");

    @Override
    public String getPattern() {
        return pattern;
    }

}
