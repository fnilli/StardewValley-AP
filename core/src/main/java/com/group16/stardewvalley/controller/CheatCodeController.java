package com.group16.stardewvalley.controller;

import com.group16.stardewvalley.controller.menu.HomeMenuController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.Fertilizer;
import com.group16.stardewvalley.model.agriculture.FertilizerType;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodFactory;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.items.Stone;
import com.group16.stardewvalley.model.items.Wood;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.tools.*;

import javax.swing.text.Position;

public class CheatCodeController {
    public Result addTool(String tool) {
        switch (tool) {
            case "axe":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new Axe(
                    "axe",
                    0,
                    "base"),
                    1);
                System.out.println("Axe added");
                break;
            case "fishing pole":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new FishingPole(
                    "fishing pole",
                    25,
                    "training"),
                    1);
                break;
            case "hoe":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new Hoe(
                    "hoe",
                    0,
                    "base"),
                    1);
                break;
            case "milk pail":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new MilkPail(
                    "milk pail",
                    0),
                    1);
                break;
            case "scythe":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new Scythe(
                    "scythe",
                    0,
                    "base"),
                    1);
                break;
            case "pickaxe":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new Pickaxe(
                    "pickaxe",
                    0,
                    "base"),
                    1);
                break;
            case "shear":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new Shear(
                    "shear",
                    1000),
                    1);
                break;
            case "watering can":
                App.getActiveGame().getCurrentPlayer().getInventory().addTool(new WateringCan(
                    "watering can",
                    0,
                    "base"),
                    1);
                break;
            default:
                return new Result(false, "Unknown tool");
        }
        return new Result(true, "added tool");
    }

    public Result addFertilizer(String fertilizer) {
        switch (fertilizer) {
            case "speed":
//                Fertilizer fertilizer1 = new Fertilizer("speed gro", FertilizerType.SPEED_GRO);
//                App.getActiveGame().getCurrentPlayer().getInventory().addItem(fertilizer1, 1);
                break;
            case "deluxe":
//                Fertilizer fertilizer2 = new Fertilizer("deluxe retaining soil", FertilizerType.DELUXE_RETAINING_SOIL);
//                App.getActiveGame().getCurrentPlayer().getInventory().addItem(fertilizer2, 1);
                break;
        }
        return new Result(true, "added fertilizer");
    }

    public Result showPosition() {
        Pos position = App.getActiveGame().getCurrentPlayer().getPosition();
        return new Result(true, position.toString());
    }

    public Result addIngredient(String name) {
        Ingredient ingredient = findIngredient(name);
        if (ingredient == null) {
            return new Result(false, "Ingredient not found");
        }
        FoodIngredient foodIngredient = App.getActiveGame().getCurrentPlayer().getInventory().getFoodIngredient(name);
        if (foodIngredient == null) {
            foodIngredient = new FoodIngredient(name, 500, ingredient);
        }
        App.getActiveGame().getCurrentPlayer().getInventory().addItem(foodIngredient, 1);
        return new Result(true, "added ingredient");
    }

    public Result addWood(int quantity) {
        Wood wood = new Wood("Wood", 10);
        App.getActiveGame().getCurrentPlayer().getInventory().addItem(wood,quantity);
        return new Result(true, "wood added.");
    }

    public Result addStone(int quantity) {
        Stone stone = new Stone("Stone", 20);
        App.getActiveGame().getCurrentPlayer().getInventory().addItem(stone,quantity);
        return new Result(true, "stone added.");

    }
    public Result learnRecipe(String foodName) {
        Food food = getFoodByName(foodName);
        if (food == null) {
            return new Result(false, "Food not found");
        }
        App.getActiveGame().getCurrentPlayer().learnRecipe(food);
        return new Result(true, "learned recipe");
    }

    public Result cookFood(String foodName) {
        Food food = getFoodByName(foodName);
        if (food == null) {
            return new Result(false, "Food not found");
        }
        App.getActiveGame().getCurrentPlayer().getInventory().addItem(food, 1);
        return new Result(true, "cooked food");
    }

    private Food getFoodByName(String name) {
        for (Food food : FoodFactory.getAllFoods()) {
            if (food.getName().equals(name)) {
                return food;
            }
        }
        return null;
    }

    private Ingredient findIngredient(String input){
        return HomeMenuController.findIngredient(input);
    }
}
