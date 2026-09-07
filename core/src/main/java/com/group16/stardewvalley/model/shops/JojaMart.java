package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.agriculture.Seeds;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.map.PlaceType;
import com.group16.stardewvalley.model.time.Season;
import com.group16.stardewvalley.model.agriculture.*;
import com.group16.stardewvalley.model.time.TimeDate;

import java.util.Set;
import java.util.HashSet;

public class JojaMart extends Shop {
    private static JojaMart instance;

    private JojaMart() {
        super("JojaMart", "Morris", 9, 23, PlaceType.JojaMart);
        initializeItems();
    }

    public static JojaMart getInstance() {
        if (instance == null) {
            instance = new JojaMart();
        }
        return instance;
    }

    public void initializeItems() {
        // اضافه کردن دانه‌های فصلی در زمان اولیه‌سازی
        Season currentSeason = App.getActiveGame().getTimeDate().getSeason();
        for (Seed seed : Seeds.getAllSeeds()) {
            if (seed.isAvailableInSeason(currentSeason)) {
                addItem(seed, seed.getDailyLimit());
            }
        }


        addItem(Seeds.JOJA_COLA, Seeds.JOJA_COLA.getDailyLimit());
        addItem(Seeds.GRASS_STARTER, Seeds.GRASS_STARTER.getDailyLimit());
        addItem(new FoodIngredient("sugar",125,  Ingredient.SUGAR), Integer.MAX_VALUE);
        addItem(new FoodIngredient("wheat flour", 125, Ingredient.WHEAT_FLOUR), Integer.MAX_VALUE);
        addItem(new FoodIngredient("Rice", 25, Ingredient.RICE), Integer.MAX_VALUE);

    }

    @Override
    public Set<Item> getAvailableItems() {
        Set<Item> availableItems = new HashSet<>();
        Season currentSeason = TimeDate.getInstance(App.getActiveGame()).getSeason();

        // فقط دانه‌های فصلی + آیتم‌های دائمی
        dailyLimit.keySet().stream()
                .filter(item -> !(item instanceof Seed) ||
                        ((Seed)item).isAvailableInSeason(currentSeason))
                .forEach(availableItems::add);

        return availableItems;
    }
}
