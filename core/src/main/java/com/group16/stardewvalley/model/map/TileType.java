package com.group16.stardewvalley.model.map;

public enum TileType {
    NPCHouse('⌂', "\033[37;41m"),
    Shop('$', "\033[31;46m"),
    Tree('♣', "\033[32;40m"),
    Stone('o', "\033[30;47m"),
    Rock('o', "\033[30;47m"),
    Forage('F', "\033[32;40m"),
    MineralForage('M', "\033[32;41m"),
    Ground('◼', "\033[33;40m"),
    Lake('≈', "\033[97;44m"),
    GreenHouse('+', "\033[30;106m"),
    Cottage('⌂', "\033[30;45m"),
    CottageStartPos('⌂', "\033[30;45m"),
    Quarry('▓', "\033[30;107m"),
    Grass('.', "\033[30;46m"),
    StonePath('.', "\033[30;47m"),
    Fence('.', "\033[30;46m"),
    Plowed('∷', "\033[33;43m");


    private final char symbol;
    private final String colorCode;

    TileType(char symbol, String colorCode) {
        this.symbol = symbol;
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }

    public char getSymbol() {
        return symbol;
    }
}
