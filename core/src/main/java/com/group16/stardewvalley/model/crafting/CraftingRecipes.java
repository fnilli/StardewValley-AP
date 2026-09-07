package com.group16.stardewvalley.model.crafting;

import java.util.Map;
import com.group16.stardewvalley.model.food.Ingredient;

public enum CraftingRecipes {
    CherryBomb("Cherry Bomb", Map.of(
        Ingredient.COPPER_ORE, 4,
        Ingredient.COAL, 1
    ), Map.of(CraftingSource.miningAbilityLevel, 1)),

    Bomb("Bomb", Map.of(
        Ingredient.IRON_ORE, 4,
        Ingredient.COAL, 1
    ), Map.of(CraftingSource.miningAbilityLevel, 2)),

    MegaBomb("Mega Bomb", Map.of(
        Ingredient.GOLD_ORE, 4,
        Ingredient.COAL, 1
    ), Map.of(CraftingSource.miningAbilityLevel, 3)),

    Sprinkler("Sprinkler", Map.of(
        Ingredient.COPPER_BAR, 1,
        Ingredient.IRON_BAR, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 1)),

    QualitySprinkler("Quality Sprinkler", Map.of(
        Ingredient.IRON_BAR, 1,
        Ingredient.GOLD_BAR, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 2)),

    IridiumSprinkler("Iridium Sprinkler", Map.of(
        Ingredient.GOLD_BAR, 1,
        Ingredient.IRIDIUM_BAR, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 3)),

    CharcoalKiln("Charcoal Kiln", Map.of(
        Ingredient.WOOD, 20,
        Ingredient.COPPER_BAR, 2
    ), Map.of(CraftingSource.foragingAbilityLevel, 1)),

    Furnace("Furnace", Map.of(
        Ingredient.COPPER_ORE, 20,
        Ingredient.STONE, 25
    ), Map.of()),

    Scarecrow("Scarecrow", Map.of(
        Ingredient.WOOD, 50,
        Ingredient.COAL, 1,
        Ingredient.FIBER, 20
    ), Map.of()),

    DeluxeScarecrow("Deluxe Scarecrow", Map.of(
        Ingredient.WOOD, 50,
        Ingredient.COAL, 1,
        Ingredient.FIBER, 20,
        Ingredient.IRIDIUM_ORE, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 2)),

    BeeHouse("Bee House", Map.of(
        Ingredient.WOOD, 40,
        Ingredient.COAL, 8,
        Ingredient.IRON_BAR, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 1)),

    CheesePress("Cheese Press", Map.of(
        Ingredient.WOOD, 45,
        Ingredient.STONE, 45,
        Ingredient.COPPER_BAR, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 2)),

    Keg("Keg", Map.of(
        Ingredient.WOOD, 30,
        Ingredient.COPPER_BAR, 1,
        Ingredient.IRON_BAR, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 3)),

    Loom("Loom", Map.of(
        Ingredient.WOOD, 60,
        Ingredient.FIBER, 30
    ), Map.of(CraftingSource.farmingAbilityLevel, 3)),

    MayonnaiseMachine("Mayonnaise Machine", Map.of(
        Ingredient.WOOD, 15,
        Ingredient.STONE, 15,
        Ingredient.COPPER_BAR, 1
    ), Map.of()),

    OilMaker("Oil Maker", Map.of(
        Ingredient.WOOD, 100,
        Ingredient.GOLD_BAR, 1,
        Ingredient.IRON_BAR, 1
    ), Map.of(CraftingSource.farmingAbilityLevel, 3)),

    PreservesJar("Preserves Jar", Map.of(
        Ingredient.WOOD, 50,
        Ingredient.STONE, 40,
        Ingredient.COAL, 8
    ), Map.of(CraftingSource.farmingAbilityLevel, 2)),

    Dehydrator("Dehydrator", Map.of(
        Ingredient.WOOD, 30,
        Ingredient.STONE, 20,
        Ingredient.FIBER, 30
    ), Map.of(CraftingSource.PierresGeneralStore, 0)),

    GrassStarter("Grass Starter", Map.of(
        Ingredient.WOOD, 1,
        Ingredient.FIBER, 1
    ), Map.of(CraftingSource.PierresGeneralStore, 0)),

    FishSmoker("Fish Smoker", Map.of(
        Ingredient.WOOD, 50,
        Ingredient.IRON_BAR, 3,
        Ingredient.COAL, 10
    ), Map.of(CraftingSource.FishShop, 0)),

    MysticTreeSeed("Mystic Tree Seed", Map.of(
        Ingredient.ACORN, 5,
        Ingredient.MAPLE_SEED, 5,
        Ingredient.PINE_CONE, 5,
        Ingredient.MAHOGANY_SEED, 5
    ), Map.of(CraftingSource.foragingAbilityLevel, 4));


    private final String name;
    private final Map<Ingredient, Integer> neededIngredients;
    private final Map<CraftingSource, Integer> source;

    CraftingRecipes(String name, Map<Ingredient, Integer> neededIngredients, Map<CraftingSource, Integer> source) {
        this.name = name;
        this.neededIngredients = neededIngredients;
        this.source = source;
    }

    public Map<Ingredient, Integer> getNeededIngredients() {
        return neededIngredients;
    }

    public String getName() {
        return name;
    }

    public Map<CraftingSource, Integer> getSource() {
        return source;
    }

    public CraftingSource getCraftingSourcetype() {
        Map.Entry<CraftingSource, Integer> sourceEntry = this.getSource().entrySet().iterator().next();
        return sourceEntry.getKey();
    }

    public Integer getCraftingValueNumber() {
        Map.Entry<CraftingSource, Integer> sourceEntry = this.getSource().entrySet().iterator().next();
        return sourceEntry.getValue();
    }
}
