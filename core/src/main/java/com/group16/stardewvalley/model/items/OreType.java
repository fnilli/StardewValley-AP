package com.group16.stardewvalley.model.items;

public enum OreType {
    COPPER_ORE("Copper Ore",
            "A common ore that can be smelted into bars",
            75,
            Integer.MAX_VALUE),
    IRON_ORE("Iron Ore",
            "A fairly common ore that can be smelted into bars",
            150,
            Integer.MAX_VALUE),
    COAL("Coal",
            "A combustible rock that is useful for crafting and smelting",
            150,
            Integer.MAX_VALUE),
    GOLD_ORE("Gold Ore",
            "A precious ore that can be smelted into bars",
            400,
            Integer.MAX_VALUE);

    private final String name;
    private final String description;
    private final int purchasePrice;
    private final int dailyLimit;

    OreType(String name, String description, int price, int dailyLimit) {
        this.name = name;
        this.description = description;
        this.purchasePrice = price;
        this.dailyLimit = dailyLimit;
    }

    public String getName() {
        return name;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }
}
