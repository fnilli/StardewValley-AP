package com.group16.stardewvalley.model.crafting;

import com.badlogic.gdx.graphics.Texture;
import com.group16.stardewvalley.controller.CheatCodeController;
import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.map.Direction;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;

import java.util.Iterator;
import java.util.Map;

import static com.group16.stardewvalley.controller.map.MapController.isPlayerInCottage;
import static com.group16.stardewvalley.view.graphics.GameScreen.TILE_SIZE;


public class Crafting {
    private CheatCodeController cheatController = new CheatCodeController();
    public static String pendingCraftItemName = null; // null means no placement pending
    public Result showRecipes() {
        Player player = App.getActiveGame().getCurrentPlayer();
        Inventory inventory = player.getInventory();


        if(!isPlayerInCottage(App.getActiveGame().getCurrentPlayer())){
            return new Result(false, "You are not inside your house !");
        }

        StringBuilder sb = new StringBuilder();

        if(inventory.getCraftingRecipes() == null){
            sb.append("No crafting recipes found!");
        }else{
            sb.append("your recipes:\n");
            for (CraftingRecipes recipe : inventory.getCraftingRecipes()) {
                sb.append(recipe.getName()).append("\n");
            }
        }

        return new Result(true, sb.toString());

    }

    public Result craft(String itemName, int x, int y) {
        Player player = App.getActiveGame().getCurrentPlayer();
        Map<Item, Integer> inventoryItems = player.getInventory().getItems();  // Inventory content

        if (!isPlayerInCottage(player) && x==-1 && y==-1) {
            return new Result(false, "You are not inside your house!");
        }

        boolean found = false;
        for (CraftingRecipes recipes : player.getInventory().getCraftingRecipes()) {
            if (recipes.getName().equals(itemName)) {
                found = true;
            }
        }
        if (!found) {
            return new Result(false, "you still don't have that recipe!");
        }



        for (CraftingRecipes recipe : CraftingRecipes.values()) {
            if (recipe.getName().equalsIgnoreCase(itemName)) {

                //TODO: cheat for needed ingredients
                for(Ingredient ingredient: recipe.getNeededIngredients().keySet()){
                    cheatController.addIngredient(ingredient.getName());
                }

                Map<Ingredient, Integer> neededIngredients = recipe.getNeededIngredients();

                // Check if player has all required ingredients in sufficient quantity
                for (Map.Entry<Ingredient, Integer> entry : neededIngredients.entrySet()) {
                    Ingredient requiredIngredient = entry.getKey();
                    int requiredAmount = entry.getValue();

                    int availableAmount = inventoryItems.entrySet().stream()
                        .filter(e -> e.getKey() instanceof CraftingIngredients)
                        .map(Map.Entry::getKey)
                        .map(item -> (CraftingIngredients) item)
                        .filter(ing -> ing.getIngredientType().equals(requiredIngredient)) // match by Ingredient
                        .mapToInt(inventoryItems::get)
                        .sum();

//                    if (availableAmount < requiredAmount) {
//                        return new Result(false, "you don't have enough ingredients");
//                    }
                }

                // All ingredients are present, proceed to craft
                //CRAFTTTTTT + ADD TO PLAYER'S INVENTORY
                CraftItem newCraftItem = new CraftItem(recipe.getName(), 0, recipe);
                player.getInventory().addItem(newCraftItem, 1);

                if (player.getEnergy() - 2 < 0 && !player.isEnergyUnlimited()) {
                    player.faint();
                }
                player.decreaseEnergy(2);

                // Remove used ingredients from inventory
                for (Map.Entry<Ingredient, Integer> entry : neededIngredients.entrySet()) {
                    Ingredient neededIngredient = entry.getKey();
                    int toRemove = entry.getValue();

                    for (Iterator<Map.Entry<Item, Integer>> iterator = inventoryItems.entrySet().iterator(); iterator.hasNext() && toRemove > 0; ) {
                        Map.Entry<Item, Integer> invEntry = iterator.next();
                        Item item = invEntry.getKey();
                        int quantity = invEntry.getValue();

                        if (item instanceof CraftingIngredients craftingItem &&
                            craftingItem.getIngredientType().equals(neededIngredient)) {

                            int removeCount = Math.min(quantity, toRemove);
                            toRemove -= removeCount;
                            int newCount = quantity - removeCount;

                            if (newCount > 0) {
                                inventoryItems.put(item, newCount);
                            } else {
                                iterator.remove();
                            }
                        }
                    }
                }

                if (x == -1 && y == -1) {
                    return new Result(true, "Item ready to place. Right click to select location.");
                }

                float scale = 0.3f; // same as in render
                Texture texture = GameAssetManager.getGameAssetManager().getCraftingTexture(newCraftItem.getRecipe());

                // Apply scale to pixel dimensions
                float scaledPixelWidth = texture.getWidth() * scale;
                float scaledPixelHeight = texture.getHeight() * scale;

                // Convert to tile dimensions
                int textureWidthTiles = (int) Math.ceil(scaledPixelWidth / TILE_SIZE);
                int textureHeightTiles = (int) Math.ceil(scaledPixelHeight / TILE_SIZE);

                // check if ground is empty
                for (int i = y; i < y + textureHeightTiles; i++) {
                    for (int j = x; j < x + textureWidthTiles; j++) {
                        if (!App.getActiveGame().getMap()[i][j].isTileEmpty()) { // also fixed logic to check "not empty"
                            return new Result(false, "There is something on the ground at (" + j + ", " + i + ")");
                        }
                    }
                }

                // Place crafted item with origin flag
                for (int row = 0; row < textureHeightTiles; row++) {
                    for (int col = 0; col < textureWidthTiles; col++) {
                        Tile tile = App.getActiveGame().getMap()[y + row][x + col];
                        if (row == 0 && col == 0) {
                            tile.setItem(newCraftItem);
                            tile.setBuildingOrigin(true); // only top-left tile is origin
                        } else {
                            tile.setItem(null);
                            tile.setBuildingOrigin(false);
                        }
                    }
                }


                return new Result(true, "successfully crafted " + recipe.getName());
            }
        }

        return new Result(false, "recipe name not found");
    }

    public Result placeItems(String itemName, String inputDirection) {
        Direction dir = Direction.N;
        try {
            dir = Direction.fromString(inputDirection);
        } catch (IllegalArgumentException e) {
            return new Result(false, "Invalid direction entered.");
        }

        Game game = App.getActiveGame();
        Inventory inventory = App.getActiveGame().getCurrentPlayer().getInventory();

        Iterator<Item> iterator = inventory.getItems().keySet().iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getName().equalsIgnoreCase(itemName)) {
                // Place item on the map
                Pos playerPos = game.getCurrentPlayer().getPosition();
                int newY = playerPos.getY() + dir.getyDelta();
                int newX = playerPos.getX() + dir.getxDelta();
                game.getMap()[newY][newX].setItem(item);

                // Decrease count or remove from inventory
                int count = inventory.getItems().get(item);
                if (count > 1) {
                    inventory.getItems().put(item, count - 1);
                } else {
                    iterator.remove(); // Safe removal
                }

                return new Result(true, "Item placed successfully.");
            }
        }

        return new Result(false, "Item not found in inventory.");
    }



    public Result cheatAddItem(String itemName, int count) {
        Player player = App.getActiveGame().getCurrentPlayer();
        Inventory inventory = player.getInventory();

        //create new crafting item
        for (CraftingRecipes recipe : CraftingRecipes.values()) {//find which item is it
            if (recipe.getName().equalsIgnoreCase(itemName)) {
                CraftItem newCraftItem = new CraftItem(recipe.getName(), 0, recipe);
                player.getInventory().addItem(newCraftItem, count);
                return new Result(true, "successfully cheated" );
            }
        }

        return new Result(false, "item not found");
    }

    public Result learnCraftingRecipes(String itemName) {
        Player player = App.getActiveGame().getCurrentPlayer();
        Inventory inventory = player.getInventory();

        //create new crafting item
        for (CraftingRecipes recipe : CraftingRecipes.values()) {//find which item is it
            if (recipe.getName().equalsIgnoreCase(itemName)) {


                if(recipe.getCraftingSourcetype().equals(CraftingSource.farmingAbilityLevel)) {
                    if (player.getFarmingAbilityLevel() >= recipe.getCraftingValueNumber()){

                        return new Result(true, "learned successfully" );

                    }
                }else if(recipe.getCraftingSourcetype().equals(CraftingSource.miningAbilityLevel )&&
                        player.getMiningAbilityLevel() >= recipe.getCraftingValueNumber()){
                    return new Result(true, "learned successfully" );

                }else if((recipe.getCraftingSourcetype().equals(CraftingSource.fishingAbilityLevel))&&
                        player.getFishingAbilityLevel() >= recipe.getCraftingValueNumber()){
                    return new Result(true, "learned successfully" );

                }else if(recipe.getCraftingSourcetype().equals(CraftingSource.foragingAbilityLevel )&&
                        player.getForagingAbilityLevel() >= recipe.getCraftingValueNumber()){
                    return new Result(true, "learned successfully" );
                }

                return new Result(false, "your ability is not enough" );
            }
        }

        return new Result(false, "item not found");
    }



}
