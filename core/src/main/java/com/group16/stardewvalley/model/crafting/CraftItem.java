package com.group16.stardewvalley.model.crafting;

import com.group16.stardewvalley.model.items.Item;

import java.util.Map;

public class CraftItem extends Item {
    public CraftItem(String name, int price) {
        super(name, price);
    }

    private CraftingRecipes recipe;

    public CraftItem(String name, int price, CraftingRecipes recipe) {
        super(name, price);
        this.recipe = recipe;
    }

    public CraftingRecipes getRecipe() {
        return recipe;
    }
}
