package com.group16.stardewvalley.model.crafting.artisan;

import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.crafting.CraftingIngredients;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.user.Player;

import java.util.HashMap;
import java.util.Map;

public class ArtisanController {

//    public Result use(String artisanName, String itemsName) {
//        Player player = App.getActiveGame().getCurrentPlayer();
//        Map<Item, Integer> inventoryItems = player.getInventory().getItems();
//
//        // Find the artisan good type by name
//        ArtisanGoodType artisanGood = null;
//        for (ArtisanGoodType ag : ArtisanGoodType.values()) {
//            if (ag.getName().equalsIgnoreCase(artisanName)) {
//                artisanGood = ag;
//                break;
//            }
//        }
//
//        if (artisanGood == null) {
//            return new Result(false, "Artisan good '" + artisanName + "' not found.");
//        }
//
//        // Split input items string into individual ingredient names
//        String[] inputItems = itemsName.trim().split("\\s+");
//
//        // Map input strings to ArtisanIngredientType (by matching getName ignoring case)
//        Map<ArtisanIngredientType, Integer> inputCounts = new HashMap<>();
//        for (String inputItem : inputItems) {
//            boolean matched = false;
//            for (ArtisanIngredientType type : ArtisanIngredientType.values()) {
//                if (type.getName().equalsIgnoreCase(inputItem)) {
//                    inputCounts.put(type, inputCounts.getOrDefault(type, 0) + 1);
//                    matched = true;
//                    break;
//                }
//            }
//            if (!matched) {
//                return new Result(false, "Unknown ingredient: " + inputItem);
//            }
//        }
//
//        // Check if player inventory has enough ingredients for artisanGood
//        Map<ArtisanIngredientType, Integer> neededIngredients = artisanGood.getIngredients();
//
//        // 1) Check needed ingredients are present in input
//        for (Map.Entry<ArtisanIngredientType, Integer> needed : neededIngredients.entrySet()) {
//            ArtisanIngredientType neededType = needed.getKey();
//            int neededAmount = needed.getValue();
//
//            int inputAmount = inputCounts.getOrDefault(neededType, 0);
//            if (inputAmount < neededAmount) {
//                return new Result(false, "Not enough " + neededType.getName() + " in input.");
//            }
//
//            // 2) Check if inventory has enough of that ingredient
//            int inventoryAmount = inventoryItems.entrySet().stream()
//                    .filter(e -> e.getKey() instanceof ArtisanIngredientType)
//                    .map(Map.Entry::getKey)
//                    .map(item -> (ArtisanIngredientType) item)
//                    .filter(ing -> ing.getIngredientType() == neededType) // compare enum by ==
//                    .mapToInt(inventoryItems::get)
//                    .sum();
//
//            if (inventoryAmount < neededAmount) {
//                return new Result(false, "Not enough " + neededType.getName() + " in inventory.");
//            }
//        }
//
//        // All checks passed: consume ingredients from inventory
//        for (Map.Entry<ArtisanIngredientType, Integer> needed : neededIngredients.entrySet()) {
//            ArtisanIngredientType neededType = needed.getKey();
//            int toRemove = needed.getValue();
//
//            for (Iterator<Map.Entry<Item, Integer>> it = inventoryItems.entrySet().iterator(); it.hasNext() && toRemove > 0; ) {
//                Map.Entry<Item, Integer> entry = it.next();
//                Item item = entry.getKey();
//                int qty = entry.getValue();
//
//                if (item instanceof CraftingIngredients craftingItem &&
//                        craftingItem.getIngredientType() == neededType) {
//
//                    int removeCount = Math.min(qty, toRemove);
//                    toRemove -= removeCount;
//                    int newQty = qty - removeCount;
//
//                    if (newQty > 0) {
//                        inventoryItems.put(item, newQty);
//                    } else {
//                        it.remove();
//                    }
//                }
//            }
//        }
//
//        // Add the artisan good to inventory (you can customize the class/type for ArtisanGood)
//        CraftItem newCraftItem = new CraftItem(artisanGood.getName(), artisanGood);
//        player.getInventory().addItem(newCraftItem, 1);
//
//        // Decrease energy, etc. (if needed)
//        player.decreaseEnergy(2);
//        if (player.getEnergy() < 0) {
//            player.faint();
//        }
//
//        return new Result(true, "Successfully crafted " + artisanGood.getName());
//    }


    public Result use(String artisanName, String itemsName){
        Player player = App.getActiveGame().getCurrentPlayer();
        Map<Item, Integer> inventoryItems = player.getInventory().getItems();

        // Find the artisan good type by name
        ArtisanGoodType artisanGood = null;
        for (ArtisanGoodType ag : ArtisanGoodType.values()) {
            if (ag.getName().equalsIgnoreCase(artisanName)) {
                artisanGood = ag;
                break;
            }
        }

        if (artisanGood == null) {
            return new Result(false, "Artisan good '" + artisanName + "' not found.");
        }

        // Split input items string into individual ingredient names
        String[] inputItems = itemsName.trim().split("\\s+");

        ArtisanGood newArtisanGood = new ArtisanGood(artisanName, 0,artisanGood, App.getActiveGame().getTimeDate().getHour());
//        player.getInventory().addItem(newArtisanGood, 1);
        return new Result(true, artisanGood.getName() + "artisan started, come back later to pick it.");

    }


    public Result get (String artisanName) {
        Game game = App.getActiveGame();
        Inventory inventory = App.getActiveGame().getCurrentPlayer().getInventory();
        // Find the artisan good type by name
        ArtisanGoodType artisanGood = null;
        for (ArtisanGoodType ag : ArtisanGoodType.values()) {
            if (ag.getName().equalsIgnoreCase(artisanName)) {
                artisanGood = ag;
                break;
            }
        }




        if(!isReady(artisanGood)){
            return new Result(false, "Artisan '" + artisanName + "' not found.");
        }


        return new Result(true, artisanGood.getName() + " added to inventory successfully");


    }


    private boolean isReady(ArtisanGoodType artisanGood) {
        return App.getActiveGame().getTimeDate().getHour() - 11 >= artisanGood.getProcessingTimeHours();
    }
}
