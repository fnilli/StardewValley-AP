package com.group16.stardewvalley.model.energy;

import com.group16.stardewvalley.model.menu.CommandsInterface;


public enum EnergyCommands implements CommandsInterface {
    SHOW_ENERGY("^\\s*energy\\s*show\\s*$"),
    SET_ENERGY("^\\s*energy\\s*set\\s*-v\\s*(?<value>-?\\d+)\\s*$"),
    ENERGY_UNLIMITED("^\\s*energy\\s*unlimited\\s*$"),
    INVENTORY_SHOW("^\\s*inventory\\s*show\\s*$"),
    INVENTORY_TRASH("^\\s*inventory\\s*trash\\s*-i\\s*(?<itemName>\\S+)" +
            "(\\s*-n\\s*(?<number>\\d+))?\\s*$");



    private final String pattern;

    EnergyCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}
