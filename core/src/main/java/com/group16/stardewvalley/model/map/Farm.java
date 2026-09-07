package com.group16.stardewvalley.model.map;


import com.group16.stardewvalley.controller.CheatCodeController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.Crop;
import com.group16.stardewvalley.model.agriculture.Tree;
import com.group16.stardewvalley.model.food.FoodFactory;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.weather.Greenhouse;

import java.util.ArrayList;
import java.util.HashMap;

public class Farm {
    private FarmType type;
    private Pos startPosition;
    private HashMap<FoodIngredient, Integer> refrigerator;
    private ArrayList<Crop> plantedCrops;
    private ArrayList<Tree> plantedTrees;
    private Greenhouse greenhouse;

    public Farm(FarmType type) {
        this.type = type;
        this.refrigerator = new HashMap<>();
        refrigerator.put(new FoodIngredient(Ingredient.SUGAR.getName(), 0, Ingredient.SUGAR), 1);
        refrigerator.put(new FoodIngredient(Ingredient.EGG.getName(), 1, Ingredient.EGG), 1);
        refrigerator.put(new FoodIngredient(Ingredient.SALMON.getName(), 2, Ingredient.SALMON), 1);
        refrigerator.put(new FoodIngredient(Ingredient.MYSTIC_SYRUP.getName(), 3, Ingredient.MYSTIC_SYRUP), 1);
        refrigerator.put(new FoodIngredient(Ingredient.PUMPKIN.getName(), 4, Ingredient.PUMPKIN), 1);
        refrigerator.put(new FoodIngredient(Ingredient.LEEK.getName(), 5, Ingredient.LEEK), 1);
        CheatCodeController controller2 = new CheatCodeController();
        for (Ingredient ingredient : FoodFactory.pizza().getIngredients().keySet()) {
           refrigerator.put(new FoodIngredient(ingredient.getName(), 90, ingredient), 2);
        }
        this.plantedCrops = new ArrayList<>();
        this.plantedTrees = new ArrayList<>();
    }

    public Greenhouse getGreenhouse() {
        return greenhouse;
    }

    public void setGreenhouse(Greenhouse greenhouse) {
        this.greenhouse = greenhouse;
    }

    public void addPlantedCrop(Crop crop) {
        plantedCrops.add(crop);
    }

    public void addPlantedTree(Tree tree) {
        plantedTrees.add(tree);
    }

    public ArrayList<Crop> getPlantedCrops() {
        return plantedCrops;
    }


    public ArrayList<Tree> getPlantedTrees() {
        return plantedTrees;
    }

    public HashMap<FoodIngredient, Integer> getRefrigerator() {
        return refrigerator;
    }

    public FoodIngredient getIngredientInRef(Ingredient ingredient) {
        for (FoodIngredient food : refrigerator.keySet()) {
            if (food.getType().equals(ingredient)) {
                return food;
            }
        }
        return null;
    }

    public void setRefrigerator(HashMap<FoodIngredient, Integer> refrigerator) {
        this.refrigerator = refrigerator;
    }

    public void addToRefrigerator(FoodIngredient ingredient, int amount) {
        refrigerator.put(ingredient, amount);
    }

    public FarmType getType() {
        return type;
    }

    public void setType(FarmType type) {
        this.type = type;
    }

    public Pos getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Pos startPosition) {
        this.startPosition = startPosition;
    }

    public Pos getCottageStart() {
        Pos cottageStart = new Pos(0, 0);
        switch (type){
            case small -> cottageStart = new Pos(10, 43);
            case big -> cottageStart = new Pos(10, 10);
        }
        return cottageStart;
    }

}
