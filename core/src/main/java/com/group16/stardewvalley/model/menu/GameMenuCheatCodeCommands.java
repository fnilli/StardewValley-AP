package com.group16.stardewvalley.model.menu;


public enum GameMenuCheatCodeCommands implements CommandsInterface {
    AddSeed("cheat add seed (?<seed>.+?)"),
    AddTool("cheat add tool (?<tool>.+?)"),
    AddFertilizer("add fertilizer (?<fertilizer>.+?)"),
    ShowPosition("show position"),
    AddIngredient("add ingredient (?<ingredient>.+?)"),
    CookFood("cheat cook food (?<food>.+?)"),
    LearnRecipe("cheat learn recipe (?<recipe>.+?)"),
    CheatAdd("^\\s*cheat\\s*add\\s*(?<count>\\d+)\\s*dollars\\s*$"),

// اضافه کردن پول به بازیکن
    CheatAddCoin("^\\s*cheat\\s*add\\s*coin\\s*(?<amount>\\d+)\\s*$");
    private final String pattern;
    GameMenuCheatCodeCommands(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
