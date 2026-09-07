package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodFactory;
import com.group16.stardewvalley.model.food.FoodRecipe;
import com.group16.stardewvalley.model.map.PlaceType;

import java.util.ArrayList;

public class TheStardropSaloon extends Shop{
    private static TheStardropSaloon instance;

    public TheStardropSaloon() {
        super("The Stardrop Saloon", "Gus", 12, 24, PlaceType.TheStardropSaloon);
        initializeItems();
    }

    public static TheStardropSaloon getInstance() {
        if (instance == null) {
            instance = new TheStardropSaloon();
        }
        return instance;
    }

    public void initializeItems() {
        addItem(FoodFactory.salad(), Integer.MAX_VALUE);
        addItem(FoodFactory.bread(), Integer.MAX_VALUE);
        addItem(FoodFactory.spaghetti(), Integer.MAX_VALUE);
        addItem(FoodFactory.pizza(), Integer.MAX_VALUE);
        addItem(FoodFactory.tripleShotEspresso(), Integer.MAX_VALUE);
        addItem(new FoodRecipe("Hashbrowns Recipe", 50, FoodFactory.hashBrowns()), 1);
        addItem(new FoodRecipe("Omelet Recipe", 100, FoodFactory.omelet()), 1);
        addItem(new FoodRecipe("Pancakes Recipe", 100, FoodFactory.pancakes()), 1);
        addItem(new FoodRecipe("Bread Recipe", 100, FoodFactory.bread()), 1);
        addItem(new FoodRecipe("Tortilla Recipe", 100, FoodFactory.tortilla()), 1);
        addItem(new FoodRecipe("Pizza Recipe", 150, FoodFactory.pizza()), 1);
        addItem(new FoodRecipe("Maki Roll Recipe", 300, FoodFactory.makiRoll()), 1);
        addItem(new FoodRecipe("Triple Shot Espresso Recipe", 5000, FoodFactory.tripleShotEspresso()), 1);
        addItem(new FoodRecipe("Cookie Recipe", 300, FoodFactory.cookie()), 1);
    }
}
