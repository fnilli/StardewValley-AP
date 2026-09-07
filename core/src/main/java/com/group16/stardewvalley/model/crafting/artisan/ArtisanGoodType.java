package com.group16.stardewvalley.model.crafting.artisan;

import com.group16.stardewvalley.model.crafting.CraftingRecipes;

import java.util.Map;

public enum ArtisanGoodType {
    HONEY("Honey", CraftingRecipes.BeeHouse, "It's a sweet syrup produced by bees.", 75, 96,
            Map.of(), 350),

    CHEESE("Cheese", CraftingRecipes.CheesePress, "It's your basic cheese.", 100, 3,
            Map.of(ArtisanIngredientType.MILK, 1, ArtisanIngredientType.LARGE_MILK, 1), 230),
    GOAT_CHEESE("Goat_Cheese", CraftingRecipes.CheesePress, "Soft cheese made from goat's milk.", 100, 3,
            Map.of(ArtisanIngredientType.GOAT_MILK, 1, ArtisanIngredientType.LARGE_GOAT_MILK, 1), 400),

    BEER("Beer", CraftingRecipes.Keg, "Drink in moderation.", 50, 24,
            Map.of(ArtisanIngredientType.WHEAT, 1), 200),
    VINEGAR("Vinegar", CraftingRecipes.Keg, "An aged fermented liquid used in many cooking recipes.", 13, 10,
            Map.of(ArtisanIngredientType.RICE, 1), 100),
    COFFEE("Coffee", CraftingRecipes.Keg, "It smells delicious. This is sure to give you a boost.", 75, 2,
            Map.of(ArtisanIngredientType.COFFEE_BEAN, 5), 150),
    JUICE("Juice", CraftingRecipes.Keg, "A sweet, nutritious beverage.", 0, 96,
            Map.of(ArtisanIngredientType.ANY_VEGETABLE, 1), 0),
    MEAD("Mead", CraftingRecipes.Keg, "A fermented beverage made from honey. Drink in moderation.", 100, 10,
            Map.of(ArtisanIngredientType.HONEY, 1), 300),
    PALE_ALE("Pale_Ale", CraftingRecipes.Keg, "Drink in moderation.", 50, 72,
            Map.of(ArtisanIngredientType.HOPS, 1), 300),
    WINE("Wine", CraftingRecipes.Keg, "Drink in moderation.", 0, 168,
            Map.of(ArtisanIngredientType.ANY_FRUIT, 1), 0),

    DRIED_MUSHROOMS("Dried_Mushrooms", CraftingRecipes.Dehydrator, "A package of gourmet mushrooms.", 50, 8,
            Map.of(ArtisanIngredientType.ANY_MUSHROOM, 5), 0),
    DRIED_FRUIT("Dried_Fruit", CraftingRecipes.Dehydrator, "Chewy pieces of dried fruit.", 75, 8,
            Map.of(ArtisanIngredientType.ANY_FRUIT_EXCEPT_GRAPES, 5), 0),
    RAISINS("Raisins", CraftingRecipes.Dehydrator, "It's said to be the Junimos' favorite food.", 125, 8,
            Map.of(ArtisanIngredientType.GRAPES, 5), 600),

    COAL("Coal", CraftingRecipes.CharcoalKiln, "Turns 10 pieces of wood into one piece of coal.", 0, 1,
            Map.of(ArtisanIngredientType.WOOD, 10), 50),

    CLOTH("Cloth", CraftingRecipes.Loom, "A bolt of fine wool cloth.", 0, 4,
            Map.of(ArtisanIngredientType.WOOL, 1), 470),

    MAYONNAISE("Mayonnaise", CraftingRecipes.MayonnaiseMachine, "It looks spreadable.", 50, 3,
            Map.of(ArtisanIngredientType.EGG, 1, ArtisanIngredientType.LARGE_EGG, 1), 190),
    DUCK_MAYONNAISE("Duck_Mayonnaise", CraftingRecipes.MayonnaiseMachine, "It's a rich, yellow mayonnaise.", 75, 3,
            Map.of(ArtisanIngredientType.DUCK_EGG, 1), 37),
    DINOSAUR_MAYONNAISE("Dinosaur_Mayonnaise", CraftingRecipes.MayonnaiseMachine, "It's thick and creamy, with a vivid green hue. It smells like grass and leather.", 125, 3,
            Map.of(ArtisanIngredientType.DINOSAUR_EGG, 1), 800),

    TRUFFLE_OIL("Truffle_Oil", CraftingRecipes.OilMaker, "A gourmet cooking ingredient.", 38, 6,
            Map.of(ArtisanIngredientType.TRUFFLE, 1), 1065),
    OIL("Oil", CraftingRecipes.OilMaker, "All purpose cooking oil.", 13, 6,
            Map.of(ArtisanIngredientType.CORN, 1, ArtisanIngredientType.SUNFLOWER_SEEDS, 1, ArtisanIngredientType.SUNFLOWER, 1), 100),

    PICKLES("Pickles", CraftingRecipes.PreservesJar, "A jar of your home-made pickles.", 0, 6,
            Map.of(ArtisanIngredientType.ANY_VEGETABLE, 1), 0),
    JELLY("Jelly", CraftingRecipes.PreservesJar, "Gooey.", 0, 72,
            Map.of(ArtisanIngredientType.ANY_FRUIT, 1), 0),

    SMOKED_FISH("Smoked_Fish", CraftingRecipes.FishSmoker, "A whole fish, smoked to perfection.", 0, 1,
            Map.of(ArtisanIngredientType.ANY_FISH, 1, ArtisanIngredientType.COAL, 1), 0),

    METAL_BAR("Any_Metal_Bar", CraftingRecipes.Furnace, "Turns ore and coal into metal bars.", 0, 4,
            Map.of(ArtisanIngredientType.ANY_ORE, 5, ArtisanIngredientType.COAL, 1), 0);

    private final String name;
    private final CraftingRecipes craftMachine;
    private final String description;
    private final int energy;
    private final int processingTimeHours;
    private final Map<ArtisanIngredientType, Integer> ingredients;
    private final int sellPrice;

    ArtisanGoodType(String name, CraftingRecipes craftMachine, String description, int energy, int processingTimeHours, Map<ArtisanIngredientType, Integer> ingredients, int sellPrice) {
        this.name = name;
        this.craftMachine = craftMachine;
        this.description = description;
        this.energy = energy;
        this.processingTimeHours = processingTimeHours;
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public CraftingRecipes getCraftMachine() {
        return craftMachine;
    }

    public String getDescription() {
        return description;
    }

    public int getEnergy() {
        return energy;
    }

    public int getProcessingTimeHours() {
        return processingTimeHours;
    }

    public Map<ArtisanIngredientType, Integer> getIngredients() {
        return ingredients;
    }

    public int getSellPrice() {
        return sellPrice;
    }
}
