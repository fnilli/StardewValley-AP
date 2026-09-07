package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.crafting.CraftItem;
import com.group16.stardewvalley.model.crafting.CraftingRecipes;
import com.group16.stardewvalley.model.map.PlaceType;
import com.group16.stardewvalley.model.tools.FishingPole;

public class FishShop extends Shop{

    private static FishShop instance;

    public FishShop() {
        super("Fish Shop", "Willy", 9, 17, PlaceType.FishShop);
        initializeItems();
    }

    public static FishShop getInstance() {
        if (instance == null) {
            instance = new FishShop();
        }
        return instance;
    }

    public void initializeItems() {
        addItem(new CraftItem("fish smoker", 10000, CraftingRecipes.FishSmoker), 1);
        addItem(new FishingPole("fishing pole",500, "bamboo"), 1);
        addItem(new FishingPole("fishing pole", 25,"training"), 1);
        addItem(new FishingPole("fishing pole", 1800,"fiberglass"), 1);
        addItem(new FishingPole("fishing pole", 7500,"iridium"), 1);
    }
}
