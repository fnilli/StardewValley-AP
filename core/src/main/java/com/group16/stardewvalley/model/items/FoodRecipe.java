package com.group16.stardewvalley.model.items;

import com.group16.stardewvalley.model.food.Food;

public class FoodRecipe extends Item{
    private final Food food;
    public FoodRecipe(String name, Food food) {
        super(name, food.getPrice());
        this.food = food;
    }

    public Food getFood() {
        return food;
    }
}
