package com.group16.stardewvalley.model.menu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface CommandsInterface {
    String getPattern();

    default Matcher getMatcher(String input){
        Matcher matcher = Pattern.compile(this.getPattern()).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}


