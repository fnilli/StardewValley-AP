package com.group16.stardewvalley.model.agriculture;

import com.group16.stardewvalley.model.time.Season;

import java.util.List;

public enum SeedType {
    // Permanent Stock
    JOJA_COLA("Joja Cola",
            75,
            Integer.MAX_VALUE,
            75,
            List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter),
            "CROP"),
    ANCIENT_SEED("Ancient Seed",
            500,
            1,
            500,
            List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter),
            "CROP"),
    GRASS_STARTER("Grass Starter",
            125,
            Integer.MAX_VALUE,
            125,
            List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter),
            "CROP"),
    SUGAR("Sugar",
            125,
            Integer.MAX_VALUE,
            125,
            List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter),
            "CROP"),
    WHEAT_FLOUR("Wheat Flour",
            125,
            Integer.MAX_VALUE,
            125,
            List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter),
            "CROP"),
    RICE("Rice",
            250,
            Integer.MAX_VALUE,
            250,
            List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter),
            "CROP"),

    // Spring Stock
    PARSNIP_SEEDS("Parsnip Seeds", 25, 5, 30,List.of(Season.Spring), "CROP"),
    BEAN_STARTER("Bean Starter", 75, 5, 90, List.of(Season.Spring), "CROP"),
    CAULIFLOWER_SEEDS("Cauliflower Seeds", 100, 5, 120, List.of(Season.Spring), "CROP"),
    POTATO_SEEDS("Potato Seeds", 62, 5, 75, List.of(Season.Spring), "CROP"),
    STRAWBERRY_SEEDS("Strawberry Seeds", 100, 5, -1, List.of(Season.Spring), "CROP"),
    TULIP_BULB("Tulip Bulb", 25, 5, 30, List.of(Season.Spring), "CROP"),
    KALE_SEEDS("Kale Seeds", 87, 5, 105, List.of(Season.Spring), "CROP"),
    COFFEE_BEANS("Coffee Beans", 200, 1, -1, List.of(Season.Spring, Season.Summer), "CROP"),
    CARROT_SEEDS("Carrot Seeds", 5, 10, -1, List.of(Season.Spring), "CROP"),
    RHUBARB_SEEDS("Rhubarb Seeds", 100, 5, -1, List.of(Season.Spring), "CROP"),
    JAZZ_SEEDS("Jazz Seeds", 37, 5, 45, List.of(Season.Spring), "CROP"),
    GARLIC_SEEDS("Garlic Seeds", 50, 5, 60, List.of(Season.Spring), "CROP"),
    RICE_SHOOT("Rice Shoot", 40, 5, 60, List.of(Season.Spring), "CROP"),

    // Summer Stock
    TOMATO_SEEDS("Tomato Seeds", 62, 5, 75, List.of(Season.Summer), "CROP"),
    PEPPER_SEEDS("Pepper Seeds", 50, 5, 60, List.of(Season.Spring), "CROP"),
    WHEAT_SEEDS("Wheat Seeds", 12, 10, 15, List.of(Season.Spring, Season.Fall), "CROP"),
    SQUASH_SEEDS("Summer Squash Seeds", 10, 10, -1, List.of(Season.Summer), "CROP"),
    RADISH_SEEDS("Radish Seeds", 50, 5, 60, List.of(Season.Spring), "CROP"),
    MELON_SEEDS("Melon Seeds", 100, 5, 120, List.of(Season.Spring), "CROP"),
    HOPS_STARTER("Hops Starter", 75, 5, 90, List.of(Season.Spring), "CROP"),
    POPPY_SEEDS("Poppy Seeds", 125, 5, 150, List.of(Season.Spring), "CROP"),
    SPANGLE_SEEDS("Spangle Seeds", 62, 5, 75, List.of(Season.Spring), "CROP"),
    STARFRUIT_SEEDS("Starfruit Seeds", 400, 5, -1, List.of(Season.Spring), "CROP"),
    SUNFLOWER_SEEDS("Sunflower Seeds", 125, 5, 300, List.of(Season.Spring, Season.Fall), "CROP"),
    RED_CABBAGE_SEEDS("red Cabbage Seeds", 100, 5, 150, List.of(Season.Summer), "CROP"),

    // Fall Stock
    // این یارو اولیه هم یبار تو کوجودی بهاره یبار پاییز که اینم اگنور کردم
    CORN_SEEDS("Corn Seeds", 187, 5, 225, List.of(Season.Fall, Season.Summer), "CROP"),
    EGGPLANT_SEEDS("Eggplant Seeds", 25, 5, 30, List.of(Season.Fall), "CROP"),
    PUMPKIN_SEEDS("Pumpkin Seeds", 125, 5, 150, List.of(Season.Fall), "CROP"),
    BROCCOLI_SEEDS("Broccoli Seeds", 15, 5, -1, List.of(Season.Fall), "CROP"),
    AMARANTH_SEEDS("Amaranth Seeds", 87, 5, 105, List.of(Season.Fall), "CROP"),
    GRAPE_STARTER("Grape Starter", 75, 5, 90, List.of(Season.Fall), "CROP"),
    BEET_SEEDS("Beet Seeds", 20, 5, -1, List.of(Season.Fall), "CROP"),
    YAM_SEEDS("Yam Seeds", 75, 5, 90, List.of(Season.Fall), "CROP"),
    BOK_CHOY_SEEDS("Bok Choy Seeds", 62, 5, 75, List.of(Season.Fall), "CROP"),
    CRANBERRY_SEEDS("Cranberry Seeds", 300, 5, 360, List.of(Season.Fall), "CROP"),
    FAIRY_SEEDS("Fairy Seeds", 250, 5, 300, List.of(Season.Fall), "CROP"),
    RARE_SEED("Rare Seed", 1000, 1, -1, List.of(Season.Fall), "CROP"),

    // Winter Stock
    POWDERMELON_SEEDS("Powdermelon Seeds", 20, 10, -1,List.of(Season.Winter), "CROP"),

    //atena add for crop:
    BLUEBERRY_SEEDS("Blueberry Seeds", 100, 5, 120, List.of(Season.Spring), "CROP"),
    PINEAPPLE_SEEDS("Pineapple Seeds", 50, 5, -1, List.of(Season.Spring), "CROP"),
    ARTICHOKE_SEEDS("Archook Seeds", 50, 5, 45, List.of(Season.Spring), "CROP"),
    //tree
    APRICOT_SAPLING("Apricot Sapling", 50, 5, -1, List.of(Season.Spring), "TREE"),
    CHERRY_SAPLING("Cherry Sapling", 50, 5, -1, List.of(Season.Spring), "TREE"),
    BANANA_SAPLING("Banana Sapling", 50, 5, -1, List.of(Season.Summer), "TREE"),
    MANGO_SAPLING("Mango Sapling", 50, 5, -1, List.of(Season.Summer), "TREE"),
    ORANGE_SAPLING("Orange Sapling", 50, 5, -1, List.of(Season.Summer), "TREE"),
    PEACH_SAPLING("Peach Sapling", 50, 5, -1, List.of(Season.Spring), "TREE"),
    APPLE_SAPLING("Apple Sapling", 50, 5, -1, List.of(Season.Spring), "TREE"),
    POMEGRANATE_SAPLING("Pomegranate Sapling", 50, 5, -1, List.of(Season.Spring), "TREE"),
    ACORNS("Acorns", 50, 5, -1, List.of(Season.Spring), "TREE"),
    MAPLE_SEEDS("Maple Seed", 50, 5, -1, List.of(Season.Spring), "TREE"),
    PINE_CONES("Pine Cones", 50, 5, -1, List.of(Season.Spring), "TREE"),
    MAHOGANY_SEEDS("Mahogany Seed", 50, 5, -1, List.of(Season.Spring), "TREE"),
    MUSHROOM_TREE_SEEDS("Mushroom Tree Seed", 50, 5, -1, List.of(Season.Spring), "TREE"),
    MYSTIC_TREE_SEEDS("Mystic Tree Seed", 50, 5, -1, List.of(Season.Spring), "TREE"),
    MIXED_SEED("Mixed seed", 50, 5, -1, List.of(Season.Special), "TREE");


    private final String name;
    private final int price;
    private final int dailyLimit;
    private final int outOfSeasonPrice;
    private final List<Season> availableSeasons;
    private final String type;

    SeedType(String name, int price, int dailyLimit, int outOfSeasonPrice, List<Season> availableSeasons, String type) {
        this.name = name;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.outOfSeasonPrice = outOfSeasonPrice;
        this.availableSeasons = availableSeasons;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public List<Season> getAvailableSeasons() {
        return availableSeasons;
    }

    public int getOutOfSeasonPrice() {
        return outOfSeasonPrice;
    }

}
