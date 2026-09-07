package com.group16.stardewvalley.model.tools;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;

import static com.group16.stardewvalley.controller.menu.HomeMenuController.findIngredient;

public class Scythe extends Gadget{
    public Scythe(String name, int price, String material) {
        super(name, price);
        this.material = material;
    }

    public int getPrice() {
        return ToolDataManager.getToolPrice("Scythe", this.material);
    }

    public int getConsumptionEnergy() {
        return ToolDataManager.getEnergyConsumption("Scythe", this.material);
    }

    @Override
    public Result use(Tile targetTile, Game game) {
        Player player = game.getCurrentPlayer();
        // خطای انرژی
        if (player.getEnergy() < 2) {
            player.decreaseEnergy(2);
            player.faint();
            return new Result(false, "Have you not eaten bread today?");
        }

        // فقط برداشت محصولات
        if (targetTile.getCrop() != null) {
            if (!targetTile.getCrop().isMature() || targetTile.getCrop().isHarvested()) {
                return new Result(false, "The crop is not mature");
            }
            if (!targetTile.getCrop().isColossal()) {
                player.getInventory().addCrop(targetTile.getCrop(), 1);
                if (targetTile.getCrop().getCropType().isOneTime()){
                    player.getInventory().addCrop(targetTile.getCrop(), 1);
                    String fruitName = targetTile.getCrop().getCropType().getName().toUpperCase().replace(" ", "_");
                    Ingredient ingredient = findIngredient(fruitName);
                    player.getInventory().addItem(new FoodIngredient(fruitName, targetTile.getCrop().getSellPrice(), ingredient), 1);
                    targetTile.setCrop(null);
                } else {
                    targetTile.getCrop().setHarvested(true);
                    String fruitName = targetTile.getCrop().getCropType().getName().toUpperCase().replace(" ", "_");
                    Ingredient ingredient = findIngredient(fruitName);
                    player.getInventory().addItem(new FoodIngredient(fruitName, targetTile.getCrop().getSellPrice(), ingredient), 1);
                }
                player.decreaseEnergy(2);
                player.addFarmingAbilityScore(5);
            } else {
                player.getInventory().addCrop(targetTile.getCrop(), 10);
                if (targetTile.getCrop().getCropType().isOneTime()){
                    String fruitName = targetTile.getCrop().getCropType().getName().toUpperCase().replace(" ", "_");
                    Ingredient ingredient = findIngredient(fruitName);
                    player.getInventory().addItem(new FoodIngredient(fruitName, targetTile.getCrop().getSellPrice(), ingredient), 10);
                    targetTile.setCrop(null);
                } else {
                    targetTile.getCrop().setHarvested(true);
                    String fruitName = targetTile.getCrop().getCropType().getName().toUpperCase().replace(" ", "_");
                    Ingredient ingredient = findIngredient(fruitName);
                    player.getInventory().addItem(new FoodIngredient(fruitName, targetTile.getCrop().getSellPrice(), ingredient), 10);
                }
                player.decreaseEnergy(2);
            }
            return new Result(true, "harvested the crop");
        }

        // هیچکار نمیتونسته باهاش بکنه
        player.decreaseEnergy(2);
        return new Result(false, "You cannot use the Scythe in this area!×");


    }
}
