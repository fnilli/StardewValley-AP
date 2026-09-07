package com.group16.stardewvalley.model.food;

import com.group16.stardewvalley.model.items.Item;

public class FoodIngredient extends Item {
    private Ingredient type;

    public FoodIngredient(String name, int price, Ingredient type) {
        super(name, price);
        this.type = type;
    }
    public Ingredient getType() {
        return type;
    }
    public void setType(Ingredient type) {
        this.type = type;
    }
}
