package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.agriculture.Seed;
import com.group16.stardewvalley.model.agriculture.Seeds;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.crafting.CraftItem;
import com.group16.stardewvalley.model.crafting.CraftingRecipes;
import com.group16.stardewvalley.model.food.*;
import com.group16.stardewvalley.model.items.Flower;
import com.group16.stardewvalley.model.items.MarriageRing;
import com.group16.stardewvalley.model.map.PlaceType;
import com.group16.stardewvalley.model.time.Season;

public class PierresGeneralStore extends Shop{
    private static PierresGeneralStore instance;

    public PierresGeneralStore() {
        super("Pierr's General Store", "Pierre", 9, 17, PlaceType.PierresGeneralStore);
        initializeItems();
    }

    public static PierresGeneralStore getInstance() {
        if (instance == null) {
            instance = new PierresGeneralStore();
        }
        return instance;
    }

    public void initializeItems() {
        addItem(new FoodIngredient("rice", 200, Ingredient.RICE), Integer.MAX_VALUE);
        addItem(new FoodIngredient("wheat flour",100, Ingredient.WHEAT_FLOUR), Integer.MAX_VALUE);
        addItem(new Flower("Flower", 1000), 2);
        addItem(new MarriageRing("Marriage Ring", 10000), 2);
        addItem(new CraftItem("dehydrator Recipe", 10000, CraftingRecipes.Dehydrator), 1);
        addItem(new CraftItem("Grass starter recipe", 1000, CraftingRecipes.GrassStarter), 1);
        addItem(new FoodIngredient("sugar", 100, Ingredient.SUGAR), Integer.MAX_VALUE);


        Season currentSeason = App.getActiveGame().getTimeDate().getSeason();
        for (Seed seed : Seeds.getAllSeeds()) {
            if (seed.isAvailableInSeason(currentSeason)) {
                addItem(seed, seed.getDailyLimit());
            }
        }


        addItem(Seeds.JOJA_COLA, Seeds.JOJA_COLA.getDailyLimit());
        addItem(Seeds.GRASS_STARTER, Seeds.GRASS_STARTER.getDailyLimit());

    }

}
