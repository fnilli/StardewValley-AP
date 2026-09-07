package com.group16.stardewvalley.model.agriculture;
import com.group16.stardewvalley.model.time.Season;

import java.util.List;

public enum CropType {
    BLUE_JAZZ("Blue Jazz", SeedType.JAZZ_SEEDS, new int[]{1, 2, 2, 2}, 7, true, -1, 50, true, 45, 20, List.of(Season.Spring), false, false),
    CARROT("Carrot", SeedType.CARROT_SEEDS, new int[]{1, 1, 1}, 3, true, -1, 35, true, 75, 33, List.of(Season.Spring), false, false),
    CAULIFLOWER("Cauliflower", SeedType.CAULIFLOWER_SEEDS, new int[]{1, 2, 4, 4, 1}, 12, true, -1, 175, true, 75, 33, List.of(Season.Spring), true, false),
    COFFEE_BEAN("Coffee Bean", SeedType.COFFEE_BEANS, new int[]{1, 2, 2, 3, 2}, 10, false, 2, 15, false, -1, -1, List.of(Season.Spring, Season.Summer), false, false),
    GARLIC("Garlic", SeedType.GARLIC_SEEDS, new int[]{1, 1, 1, 1}, 4, true, -1, 60, true, 20, 9, List.of(Season.Spring), false, false),
    GREEN_BEAN("Green Bean", SeedType.BEAN_STARTER, new int[]{1, 1, 1, 3, 4}, 10, false, 3, 40, true, 25, 11, List.of(Season.Spring), false, false),
    KALE("Kale", SeedType.KALE_SEEDS, new int[]{1, 2, 2, 1}, 6, true, -1, 110, true, 50, 22, List.of(Season.Spring), false, false),
    PARSNIP("Parsnip", SeedType.PARSNIP_SEEDS, new int[]{1, 1, 1, 1}, 4, true, -1, 35, true, 25, 11, List.of(Season.Spring), false, false),
    POTATO("Potato", SeedType.POTATO_SEEDS, new int[]{1, 1, 1, 2, 1}, 6, true, -1, 80, true, 25, 11, List.of(Season.Spring), false, false),
    RHUBARB("Rhubarb", SeedType.RHUBARB_SEEDS, new int[]{2, 2, 2, 3, 4}, 13, true, -1, 220, false, -1, -1, List.of(Season.Spring), false, false),
    STRAWBERRY("Strawberry", SeedType.STRAWBERRY_SEEDS, new int[]{1, 1, 2, 2, 2}, 8, false, 4, 120, true, 50, 22, List.of(Season.Spring), false, false),
    TULIP("Tulip", SeedType.TULIP_BULB, new int[]{1, 1, 2, 2}, 6, true, -1, 30, true, 45, 20, List.of(Season.Spring), false, false),
    UNMILLED_RICE("Unmilled Rice", SeedType.RICE, new int[]{1, 2, 2, 3}, 8, true, -1, 30, true, 3, 1, List.of(Season.Spring), false, false),
    BLUEBERRY("Blueberry", SeedType.BLUEBERRY_SEEDS, new int[]{1, 3, 3, 4, 2}, 13, false, 4, 50, true, 25, 11, List.of(Season.Summer), false, false),
    CORN("Corn", SeedType.CORN_SEEDS, new int[]{2, 3, 3, 3, 3}, 14, false, 4, 50, true, 25, 11, List.of(Season.Summer, Season.Fall), false, false),
    HOPS("Hops", SeedType.HOPS_STARTER, new int[]{1, 1, 2, 3, 4}, 11, false, 1, 25, true, 45, 20, List.of(Season.Summer), false, false),
    HOT_PEPPER("Hot Pepper", SeedType.PEPPER_SEEDS, new int[]{1, 1, 1, 1, 1}, 5, false, 3, 40, true, 13, 5, List.of(Season.Summer), false, false),
    MELON("Melon", SeedType.MELON_SEEDS, new int[]{1, 2, 3, 3, 3}, 12, true, -1, 250, true, 113, 50, List.of(Season.Summer), true, false),
    POPPY("Poppy", SeedType.POPPY_SEEDS, new int[]{1, 2, 2, 2}, 7, true, -1, 140, true, 45, 20, List.of(Season.Summer), false, false),
    RADISH("Radish", SeedType.RADISH_SEEDS, new int[]{2, 1, 2, 1}, 6, true, -1, 90, true, 45, 20, List.of(Season.Summer), false, false),
    RED_CABBAGE("Red Cabbage", SeedType.RED_CABBAGE_SEEDS, new int[]{2, 1, 2, 2, 2}, 9, true, -1, 260, true, 75, 33, List.of(Season.Summer), false, false),
    STARFRUIT("Starfruit", SeedType.STARFRUIT_SEEDS, new int[]{2, 3, 2, 3, 3}, 13, true, -1, 750, true, 125, 56, List.of(Season.Summer), false, false),
    SUMMER_SPANGLE("Summer Spangle", SeedType.SPANGLE_SEEDS, new int[]{1, 2, 3, 1}, 8, true, -1, 90, true, 45, 20, List.of(Season.Summer), false, false),
    SUMMER_SQUASH("Summer Squash", SeedType.SQUASH_SEEDS, new int[]{1, 1, 1, 2, 1}, 6, false, 3, 45, true, 63, 28, List.of(Season.Summer), false, false),
    SUNFLOWER("Sunflower", SeedType.SUNFLOWER_SEEDS, new int[]{1, 2, 3, 2}, 8, true, -1, 80, true, 45, 20, List.of(Season.Summer, Season.Fall), false, false),
    TOMATO("Tomato", SeedType.TOMATO_SEEDS, new int[]{2, 2, 2, 2, 3}, 11, false, 4, 60, true, 20, 9, List.of(Season.Summer), false, false),
    WHEAT("Wheat", SeedType.WHEAT_SEEDS, new int[]{1, 1, 1, 1}, 4, true, -1, 25, false, -1, -1, List.of(Season.Summer, Season.Fall), false, false),
    AMARANTH("Amaranth", SeedType.AMARANTH_SEEDS, new int[]{1, 2, 2, 2}, 7, true, -1, 150, true, 50, 22, List.of(Season.Fall), false, false),
    ARTICHOKE("Artichoke", SeedType.ARTICHOKE_SEEDS, new int[]{2, 2, 1, 2, 1}, 8, true, -1, 160, true, 30, 13, List.of(Season.Fall), false, false),
    BEET("Beet", SeedType.BEET_SEEDS, new int[]{1, 1, 2, 2}, 6, true, -1, 100, true, 30, 13, List.of(Season.Fall), false, false),
    BOK_CHOYS("Bok Choy", SeedType.BOK_CHOY_SEEDS, new int[]{1, 1, 1, 1}, 4, true, -1, 80, true, 25, 11, List.of(Season.Fall), false, false),
    BROCCOLI("Broccoli", SeedType.BROCCOLI_SEEDS, new int[]{2, 2, 2, 2}, 8, false, 4, 70, true, 63, 28, List.of(Season.Fall), false, false),
    CRANBERRIES("Cranberries", SeedType.CRANBERRY_SEEDS, new int[]{1, 2, 1, 1, 2}, 7, false, 5, 75, true, 38, 17, List.of(Season.Fall), false, false),
    EGGPLANT("Eggplant", SeedType.EGGPLANT_SEEDS, new int[]{1, 1, 1, 1}, 5, false, 5, 60, true, 20, 9, List.of(Season.Fall), false, false),
    FAIRY_ROSE("Fairy Rose", SeedType.FAIRY_SEEDS, new int[]{1, 4, 4, 3}, 12, true, -1, 290, true, 45, 20, List.of(Season.Fall), false, false),
    GRAPE("Grape", SeedType.GRAPE_STARTER, new int[]{1, 1, 2, 3, 3}, 10, false, 3, 80, true, 38, 17, List.of(Season.Fall), false, true),
    PUMPKIN("Pumpkin", SeedType.PUMPKIN_SEEDS, new int[]{1, 2, 3, 4, 3}, 13, true, -1, 320, false, -1, -1, List.of(Season.Fall), true, false),
    YAM("Yam", SeedType.YAM_SEEDS, new int[]{1, 3, 3, 3}, 10, true, -1, 160, true, 45, 20, List.of(Season.Fall), false, false),
    SWEET_GEM_BERRY("Sweet Gem Berry", SeedType.RARE_SEED, new int[]{2, 4, 6, 6, 6}, 24, true, -1, 3000, false, -1, -1, List.of(Season.Fall), false, false),
    POWDERMELON("Powdermelon", SeedType.POWDERMELON_SEEDS, new int[]{1, 2, 1, 2, 1}, 7, true, -1, 60, true, 63, 28, List.of(Season.Winter), true, true),
    ANCIENT_FRUIT("Ancient Fruit", SeedType.ANCIENT_SEED, new int[]{2, 7, 7, 7, 5}, 28, false, 7, 550, false, -1, -1, List.of(Season.Spring, Season.Summer, Season.Fall), false, false),


    // ===================== FORAGING CROPS =====================
    COMMON_MUSHROOM("Common Mushroom", null, new int[]{}, 0, true, -1, 40, true, 38, 0, List.of(Season.Special), false, true),
    DAFFODIL("Daffodil", null, new int[]{}, 0, true, -1, 30, true, 0, 0, List.of(Season.Spring), false, true),
    DANDELION("Dandelion", null, new int[]{}, 0, true, -1, 40, true, 25, 0, List.of(Season.Spring), false, true),
    LEEK("Leek", null, new int[]{}, 0, true, -1, 60, true, 40, 0, List.of(Season.Spring), false, true),
    MOREL("Morel", null, new int[]{}, 0, true, -1, 150, true, 20, 0, List.of(Season.Spring), false, true),
    SALMONBERRY("Salmonberry", null, new int[]{}, 0, true, -1, 5, true, 25, 0, List.of(Season.Spring), false, true),
    SPRING_ONION("Spring Onion", null, new int[]{}, 0, true, -1, 8, true, 13, 0, List.of(Season.Spring), false, true),
    WILD_HORSERADISH("Wild Horseradish", null, new int[]{}, 0, true, -1, 50, true, 13, 0, List.of(Season.Spring), false, true),
    FIDDLEHEAD_FERN("Fiddlehead Fern", null, new int[]{}, 0, true, -1, 90, true, 25, 0, List.of(Season.Summer), false, true),
    RED_MUSHROOM("Red Mushroom", null, new int[]{}, 0, true, -1, 75, true, -50, 0, List.of(Season.Summer), false, true),
    SPICE_BERRY("Spice Berry", null, new int[]{}, 0, true, -1, 80, true, 25, 0, List.of(Season.Summer), false, true),
    SWEET_PEA("Sweet Pea", null, new int[]{}, 0, true, -1, 50, true, 0, 0, List.of(Season.Summer), false, true),
    BLACKBERRY("Blackberry", null, new int[]{}, 0, true, -1, 25, true, 25, 0, List.of(Season.Fall), false, true),
    CHANTERELLE("Chanterelle", null, new int[]{}, 0, true, -1, 160, true, 75, 0, List.of(Season.Fall), false, true),
    HAZELNUT("Hazelnut", null, new int[]{}, 0, true, -1, 40, true, 38, 0, List.of(Season.Fall), false, true),
    PURPLE_MUSHROOM("Purple Mushroom", null, new int[]{}, 0, true, -1, 90, true, 30, 0, List.of(Season.Fall), false, true),
    WILD_PLUM("Wild Plum", null, new int[]{}, 0, true, -1, 80, true, 25, 0, List.of(Season.Fall), false, true),
    CROCUS("Crocus", null, new int[]{}, 0, true, -1, 60, true, 0, 0, List.of(Season.Winter), false, true),
    CRYSTAL_FRUIT("Crystal Fruit", null, new int[]{}, 0, true, -1, 150, true, 63, 0, List.of(Season.Winter), false, true),
    HOLLY("Holly", null, new int[]{}, 0, true, -1, 80, true, -37, 0, List.of(Season.Winter), false, true),
    SNOW_YAM("Snow Yam", null, new int[]{}, 0, true, -1, 100, true, 30, 0, List.of(Season.Winter), false, true),
    WINTER_ROOT("Winter Root", null, new int[]{}, 0, true, -1, 70, true, 25, 0, List.of(Season.Winter), false, true);

    private final String name;
    private final SeedType source;
    private final int[] stages;
    private final int harvestTime;
    private final boolean oneTime;
    private final int regrowthTime;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final int energy;
    private final int baseHealth;
    private final List<Season> season;
    private final boolean canBecomeGiant;
    private final boolean foraging;

    CropType(String name, SeedType source, int[] stages, int harvestTime, boolean oneTime, int regrowthTime,
             int baseSellPrice, boolean isEdible, int energy, int baseHealth, List<Season> season, boolean canBecomeGiant, boolean foraging) {
        this.name = name;
        this.source = source;
        this.stages = stages;
        this.harvestTime = harvestTime;
        this.oneTime = oneTime;
        this.regrowthTime = regrowthTime;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.baseHealth = baseHealth;
        this.season = season;
        this.canBecomeGiant = canBecomeGiant;
        this.foraging = foraging;
    }

    public boolean isForaging() {
        return foraging;
    }

    public String getName() {
        return name;
    }

    public SeedType getSource() {
        return source;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    public int[] getStages() {
        return stages;
    }

    public int getHarvestTime() {
        return harvestTime;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public int getRegrowthTime() {
        return regrowthTime;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public int getEnergy() {
        return energy;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public List<Season> getSeason() {
        return season;
    }

    public boolean canBecomeGiant() {
        return canBecomeGiant;
    }
}
