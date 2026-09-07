package com.group16.stardewvalley.model.transaction;

import com.group16.stardewvalley.model.menu.CommandsInterface;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public enum TransactionCommands implements CommandsInterface {
    SHOW_ALL_PRODUCTS("^\\s*show\\s*all\\s*products\\s*$"),
    SHOW_AVAILABLE_PRODUCTS("^\\s*show\\s*all\\s*available\\*products\\s*$"),
    PURCHASE("^\\s*purchase\\s*(?<productName>\\S+)(?:\\s+-n\\s*(?<count>\\d+))?\\s*$"),
    SELL("^\\s*sell\\s*(?<productName>\\S+)\\s*-n\\s*(?<count>\\d+)\\s*$");



    private final String pattern;

    TransactionCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}
