package com.group16.stardewvalley.model.crafting.artisan;

public enum ArtisanIngredientType {
    MILK("Milk"),
    LARGE_MILK("Large Milk"),

    GOAT_MILK("Goat Milk"),
    LARGE_GOAT_MILK("Large Goat Milk"),

    WHEAT("Wheat"),
    RICE("Rice"),
    COFFEE_BEAN("Coffee Bean"),

    ANY_VEGETABLE("Any Vegetable"),
    ANY_FRUIT("Any Fruit"),
    HONEY("Honey"),
    HOPS("Hops"),

    ANY_MUSHROOM("Any Mushroom"),
    ANY_FRUIT_EXCEPT_GRAPES("Any Fruit (except Grapes)"),
    GRAPES("Grapes"),

    WOOD("Wood"),
    WOOL("Wool"),

    EGG("Egg"),
    LARGE_EGG("Large Egg"),
    DUCK_EGG("Duck Egg"),
    DINOSAUR_EGG("Dinosaur Egg"),

    TRUFFLE("Truffle"),
    CORN("Corn"),
    SUNFLOWER_SEEDS("Sunflower Seeds"),
    SUNFLOWER("Sunflower"),

    ANY_FISH("Any Fish"),
    COAL("Coal"),
    ANY_ORE("Any Ore");

    private final String name;

    private ArtisanIngredientType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
