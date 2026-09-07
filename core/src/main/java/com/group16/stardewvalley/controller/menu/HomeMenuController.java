package com.group16.stardewvalley.controller.menu;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.BuffType;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.menu.Menu;
import com.group16.stardewvalley.model.user.Player;

import java.util.Objects;
import java.util.Set;

public class HomeMenuController {

    public Result putItemInRefrigerator(String input){
        if(input == null || input.isEmpty()){
            return new Result(false, "empty input!");
        }
        Ingredient ingredient = findIngredient(input);
        if(ingredient == null){
            return new Result(false, "You can't put an uneatable item in refrigerator!");
        }
        FoodIngredient foodIngredient = App.getActiveGame().getCurrentPlayer().getInventory().getFoodIngredient(ingredient);
        if (foodIngredient == null) {
            return new Result(false, "You don't have this in your inventory!");
        }
        App.getActiveGame().getCurrentPlayer().getFarm().addToRefrigerator(foodIngredient, 1);
        return new Result(true, "You put an item in refrigerator!");
    }

    public Result pickItemInRefrigerator(String input){
        if(input == null || input.isEmpty()){
            return new Result(false, "empty input!");
        }
        Ingredient ingredient = findIngredient(input);
        if(ingredient == null){
            return new Result(false, "You can't pick an uneatable item from refrigerator!");
        }
        FoodIngredient foodIngredient = App.getActiveGame().getCurrentPlayer().getFarm().getIngredientInRef(ingredient);
        if (foodIngredient == null) {
            return new Result(false, "You don't have this in your refrigerator!");
        }
        App.getActiveGame().getCurrentPlayer().getInventory().getItems().put(foodIngredient, App.getActiveGame().getCurrentPlayer().getFarm().getRefrigerator().get(foodIngredient));
        App.getActiveGame().getCurrentPlayer().getFarm().getRefrigerator().remove(foodIngredient);
        return new Result(true, "You pick an item in refrigerator!");
    }

    public Result showRecipeOfFood() {
        Set<Food> foods = App.getActiveGame().getCurrentPlayer().getKnownRecipes();
        if (foods == null || foods.isEmpty()){
            return new Result(false, "You don't have any known recipes!");
        }
        StringBuilder output = new StringBuilder();
        for (Food food : foods) {
            output.append(food.getName()).append(" Recipe: ").append(food.getFormattedRecipe()).append("\n");
        }
        return new Result(true, output.toString());
    }

    public Result cooking(Food food) {
        if (food == null) {
            return new Result(false, "You don't know this food recipe!");
        }
        if (!haveIngredient(food)) {
            return new Result(false, "You don't have this food Ingredients!");
        }
        if (!App.getActiveGame().getCurrentPlayer().hasEnoughEnergy(3)) {
            App.getActiveGame().getCurrentPlayer().faint();
            return new Result(false, "You don't have enough energy!\nYou fainted");
        }
        reduceIngredient(food);
        App.getActiveGame().getCurrentPlayer().decreaseEnergy(3);
        Food food2 = App.getActiveGame().getCurrentPlayer().getInventory().getFood(food.getName());
        return App.getActiveGame().getCurrentPlayer().getInventory().addItem(Objects.requireNonNullElseGet(food2, () -> new Food(food)), 1);
    }

    public Result eat(String foodName) {
        Food food = App.getActiveGame().getCurrentPlayer().getInventory().getFood(foodName);
        if (food == null) {
            return new Result(false, "You don't have this food in your inventory!");
        }
        Player player = App.getActiveGame().getCurrentPlayer();
        doBuffer(food.getBuff());
        player.increaseEnergy(food.getEnergy());
        player.getInventory().getItems().put(food, -1);
        return new Result(true, "You have eaten " + foodName + "!");
    }

    public void doBuffer(BuffType buff) {
        Player player = App.getActiveGame().getCurrentPlayer();
        switch (buff) {
            case MAX_ENERGY_100:
                player.setEnergyCeiling(player.getEnergyCeiling() + 100);
                break;
            case FARMING_5H:
                player.addFarmingAbilityScore(2);
                break;
            case FORAGING_11H:
                player.addFarmingAbilityScore(11);
                break;
            case MAX_ENERGY_50:
                player.setEnergyCeiling(player.getEnergyCeiling() + 50);
                break;
            case FORAGING_5H, MINING_5H:
                break;
            case FISHING_5H, FISHING_10H:
                player.addFishingAbilityScore(5);
                break;
        }
        player.setFinalHourBuff(buff.getDurationHours());
        player.setBuffActive(true);
        player.setBuffer(buff);
    }

    private void reduceIngredient(Food food) {
        for (Ingredient ingredient : food.getIngredients().keySet()) {
            FoodIngredient foodIngredient = App.getActiveGame().getCurrentPlayer().getInventory().getFoodIngredient(ingredient);
            App.getActiveGame().getCurrentPlayer().getInventory().getItems().put(foodIngredient, -1);
        }

    }

    private boolean haveIngredient(Food food) {
        for (Ingredient ingredient : food.getIngredients().keySet()) {
            FoodIngredient foodIngredient = App.getActiveGame().getCurrentPlayer().getInventory().getFoodIngredient(ingredient);
            if (foodIngredient == null) {
                return false;
            }
        }
        return true;
    }

    private Food getFoodByName(String name) {
        for (Food food : App.getActiveGame().getCurrentPlayer().getKnownRecipes()) {
            if (food.getName().equals(name)) {
                return food;
            }
        }
        return null;
    }


    public static Ingredient findIngredient(String input){
        for (Ingredient ingredient : Ingredient.values()) {
            if (ingredient.getName().equals(input)) {
                return ingredient;
            }
        }
        return null;
    }

    public Result showCurrentMenu(){
        return new Result(true, App.getCurrentMenu().getName());
    }



    public Result exitMenu(){
        App.setCurrentMenu(Menu.GameMenu);
        return new Result(true, "you are in the game menu!");
    }
}
