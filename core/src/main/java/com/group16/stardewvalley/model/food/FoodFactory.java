package com.group16.stardewvalley.model.food;

import java.util.List;
import java.util.Map;

public class FoodFactory {

    public static Food friedEgg() {
        return new Food("Fried Egg", 75, Map.of(Ingredient.EGG, 1), 50, BuffType.NONE, "Starter", 35);
    }

    public static Food bakedFish() {
        return new Food("Baked Fish", 200, Map.of(Ingredient.SARDINE, 1, Ingredient.SALMON, 1, Ingredient.WHEAT, 1), 75, BuffType.NONE, "Starter", 100);
    }

    public static Food salad() {
        return new Food("Salad", 220, Map.of(Ingredient.LEEK, 1, Ingredient.DANDELION, 1), 113, BuffType.NONE, "Starter", 110);
    }

    public static Food omelet() {
        return new Food("Omelet", 180, Map.of(Ingredient.EGG, 1, Ingredient.MILK, 1), 100, BuffType.NONE, "Stardrop Saloon", 125);
    }

    public static Food pumpkinPie() {
        return new Food("Pumpkin Pie", 120, Map.of(Ingredient.PUMPKIN, 1, Ingredient.WHEAT_FLOUR, 1, Ingredient.MILK, 1, Ingredient.SUGAR, 1), 225, BuffType.NONE, "Stardrop Saloon", 385);
    }

    public static Food spaghetti() {
        return new Food("Spaghetti", 240, Map.of(Ingredient.WHEAT_FLOUR, 1, Ingredient.TOMATO, 1), 75, BuffType.NONE, "Stardrop Saloon", 120);
    }

    public static Food pizza() {
        return new Food("Pizza", 600, Map.of(Ingredient.WHEAT_FLOUR, 1, Ingredient.TOMATO, 1, Ingredient.CHEESE, 1), 150, BuffType.NONE, "Stardrop Saloon", 300);
    }

    public static Food tortilla() {
        return new Food("Tortilla", 100, Map.of(Ingredient.CORN, 1), 50, BuffType.NONE, "Stardrop Saloon", 50);
    }

    public static Food makiRoll() {
        return new Food("Maki Roll", 210, Map.of(Ingredient.FISH, 1, Ingredient.RICE, 1, Ingredient.FIBER, 1), 100, BuffType.NONE, "Stardrop Saloon", 220);
    }

    public static Food tripleShotEspresso() {
        return new Food("Triple Shot Espresso", 300, Map.of(Ingredient.COFFEE, 3), 200, BuffType.MAX_ENERGY_100, "Stardrop Saloon", 450);
    }

    public static Food cookie() {
        return new Food("Cookie", 100, Map.of(Ingredient.WHEAT_FLOUR, 1, Ingredient.SUGAR, 1, Ingredient.EGG, 1), 90, BuffType.NONE, "Stardrop Saloon", 140);
    }

    public static Food hashBrowns() {
        return new Food("Hash Browns", 230, Map.of(Ingredient.POTATO, 1, Ingredient.OIL, 1), 90, BuffType.FARMING_5H, "Stardrop Saloon", 120);
    }

    public static Food pancakes() {
        return new Food("Pancakes", 210, Map.of(Ingredient.WHEAT_FLOUR, 1, Ingredient.EGG, 1), 90, BuffType.FORAGING_11H, "Stardrop Saloon", 80);
    }

    public static Food fruitSalad() {
        return new Food("Fruit Salad", 100, Map.of(Ingredient.BLUEBERRY, 1, Ingredient.MELON, 1, Ingredient.APRICOT, 1), 263, BuffType.NONE, "Stardrop Saloon", 450);
    }

    public static Food redPlate() {
        return new Food("Red Plate", 100, Map.of(Ingredient.RED_CABBAGE, 1, Ingredient.RADISH, 1), 240, BuffType.MAX_ENERGY_50, "Stardrop Saloon", 400);
    }

    public static Food bread() {
        return new Food("Bread", 120, Map.of(Ingredient.WHEAT_FLOUR, 1), 50, BuffType.NONE, "Stardrop Saloon", 60);
    }

    public static Food salmonDinner() {
        return new Food("Salmon Dinner", 200, Map.of(Ingredient.SALMON, 1, Ingredient.AMARANTH, 1, Ingredient.KALE, 1), 125, BuffType.NONE, "Leah reward", 300);
    }

    public static Food vegetableMedley() {
        return new Food("Vegetable Medley", 120, Map.of(Ingredient.TOMATO, 1, Ingredient.BEET, 1), 165, BuffType.NONE, "Foraging Level 2", 120);
    }

    public static Food farmersLunch() {
        return new Food("Farmers Lunch", 120, Map.of(Ingredient.OMELET, 1, Ingredient.PARSNIP, 1), 200, BuffType.FARMING_5H, "Farming level 1", 150);
    }

    public static Food survivalBurger() {
        return new Food("Survival Burger", 100, Map.of(Ingredient.BREAD, 1, Ingredient.CARROT, 1, Ingredient.EGGPLANT, 1), 125, BuffType.FORAGING_11H, "Foraging level 3", 180);
    }

    public static Food dishOTheSea() {
        return new Food("Dish O The Sea", 100, Map.of(Ingredient.SARDINE, 2, Ingredient.HASH_BROWN, 1), 150, BuffType.FISHING_5H, "Fishing level 2", 220);
    }

    public static Food seafoamPudding() {
        return new Food("Seafoam Pudding", 100, Map.of(Ingredient.FLOUNDER, 1, Ingredient.MIDNIGHT_CARP, 1), 175, BuffType.FISHING_10H, "Fishing level 3", 300);
    }

    public static Food minersTreat() {
        return new Food("Miners Treat", 100, Map.of(Ingredient.CARROT, 2, Ingredient.SUGAR, 1, Ingredient.MILK, 1), 125, BuffType.MINING_5H, "Mining level 1", 200);
    }

    public static List<Food> getAllFoods() {
        return List.of(
                friedEgg(),
                bakedFish(),
                salad(),
                omelet(),
                pumpkinPie(),
                spaghetti(),
                pizza(),
                tortilla(),
                makiRoll(),
                tripleShotEspresso(),
                cookie(),
                hashBrowns(),
                pancakes(),
                fruitSalad(),
                redPlate(),
                bread(),
                salmonDinner(),
                vegetableMedley(),
                farmersLunch(),
                survivalBurger(),
                dishOTheSea(),
                seafoamPudding(),
                minersTreat()
        );
    }
}
