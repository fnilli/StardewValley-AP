package com.group16.stardewvalley.model.transaction;

import com.group16.stardewvalley.model.menu.CommandsInterface;

import java.util.regex.*;

public enum TransactionCheatcodeCommands implements CommandsInterface {

    ADD("^\\s*cheat\\s*add\\s*(?<count>\\s+)\\s*dollars\\s*$");



    private final String pattern;

    TransactionCheatcodeCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }


}
