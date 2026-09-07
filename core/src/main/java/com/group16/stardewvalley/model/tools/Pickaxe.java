package com.group16.stardewvalley.model.tools;

import com.group16.stardewvalley.model.agriculture.Mineral;
import com.group16.stardewvalley.model.items.Ore;
import com.group16.stardewvalley.model.items.OreType;
import com.group16.stardewvalley.model.items.Stone;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.user.Player;

public class Pickaxe extends Gadget{
    public Pickaxe(String name, int price, String material) {
        super(name, price);
        this.material = material;
    }

    public int getPrice() {
         return ToolDataManager.getToolPrice("pickaxe", this.material);
    }

    public int getConsumptionEnergy() {
        return ToolDataManager.getEnergyConsumption("pickaxe", this.material);
    }

    public Result use(Tile targetTile, Game game) {
        Player player = game.getCurrentPlayer();
        int optionForNerds = 0;
        if (player.getMiningAbilityLevel() == 4) {
            optionForNerds = 1;
        }

        if (game.getCurrentPlayer().getEnergy() < this.getConsumptionEnergy()) {
            player.decreaseEnergy(this.getConsumptionEnergy() - 1);
            if (this.getConsumptionEnergy() - 1 != 0) {
                player.increaseEnergy(1);
            }
            player.faint();
            return new Result(false, "Have you not eaten bread today?");
        }
        // زمانی که بیل خورده است
        if (targetTile.getType() == TileType.Plowed) {
            player.decreaseEnergy(this.getConsumptionEnergy() - optionForNerds);
            targetTile.setType(TileType.Ground);
            return new Result(true, "The earth returns to its natural state.");
        }
        // زمانی که سنگ هست
        if (targetTile.getItem() instanceof Stone) {
            player.addMiningAbilityScore(10);
            player.decreaseEnergy(this.getConsumptionEnergy() - optionForNerds);
            if (player.getMiningAbilityLevel() == 4) {
                player.getInventory().addItem(targetTile.getItem(), 10);
            } else player.getInventory().addItem(targetTile.getItem(), 1);
            targetTile.setItem(null);
            return new Result(true, "Stone shattered! The shovel proves its might.");
        }

        // زمانی که کانی است
        if (targetTile.getItem() instanceof Ore) {
            player.addMiningAbilityScore(10);
            if (this.material.equalsIgnoreCase("iridium") ||
                    this.material.equalsIgnoreCase("gold") ||
                    this.material.equalsIgnoreCase("iron")) {
                player.decreaseEnergy(this.getConsumptionEnergy() - optionForNerds);
                targetTile.setItem(null);
            }  else if (this.material.equalsIgnoreCase("copper")) {
                    if (((Ore) targetTile.getItem()).getOreType() != OreType.GOLD_ORE) {
                        player.decreaseEnergy(this.getConsumptionEnergy() - optionForNerds);
                        targetTile.setItem(null);
                    }
                    else {
                        player.decreaseEnergy(this.getConsumptionEnergy() - optionForNerds);
                    }
            }

        }

        if (targetTile.getItem() instanceof Mineral) {
            player.addMiningAbilityScore(10);
            targetTile.setItem(null);
            player.getInventory().addItem(targetTile.getItem(), 1);
            return new Result(true, "Mineral added to the inventory.");
        }

        if (targetTile.getCrop() != null) {
            player.addNatureTourismAbilityScore(10);
        }

        // از بین بردن ایتم هایی که بازیکن روی زمین گذاشته است
        if (targetTile.getItem() != null) {
            player.decreaseEnergy(this.getConsumptionEnergy() - optionForNerds);
            targetTile.setItem(null);
            return new Result(true, "Cleared! Every player-placed block has been removed.");
        }

        // برای استفاده ی ناموفق یک واحد کمتر از چیزی که میگیری باید مصرف کنی
        player.decreaseEnergy(this.getConsumptionEnergy() - 1 - optionForNerds);

    return new Result(true, "");
    }
}
