package com.group16.stardewvalley.model.crafting;

import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.items.Item;

public class CraftingIngredients extends Item {

    private Ingredient ingredientType;

    public CraftingIngredients(String name, int price) {
        super(name, price);
    }

    public Ingredient getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(Ingredient ingredientType) {
        this.ingredientType = ingredientType;
    }
}
