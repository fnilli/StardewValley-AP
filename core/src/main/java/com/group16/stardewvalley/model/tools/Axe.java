package com.group16.stardewvalley.model.tools;

import com.group16.stardewvalley.model.items.Wood;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;

public class Axe extends Gadget{
    public Axe(String name, int price, String material) {
        super(name, price);
        this.material = material;
    }

    public int getPrice() {
        return ToolDataManager.getToolPrice("Axe", this.material);
    }


    public int getConsumptionEnergy() {
        return ToolDataManager.getEnergyConsumption("axe", this.material);
    }

    public Result use(Tile targetTile, Game game) {
        Player player = game.getCurrentPlayer();
        int optionForNerds = 0;
        if (player.getForagingAbilityLevel() == 4) {
            optionForNerds = 1;
        }
        // در واقع اینا استفاده های ناموفق هستند:
        // خطای انرزی
        if (player.getEnergy() < this.getConsumptionEnergy()) {
            player.decreaseEnergy(this.getConsumptionEnergy() - 1);
            if (!(player.getForagingAbilityLevel() == 4 && this.getConsumptionEnergy() - 1 == 0)) {
                player.increaseEnergy(1);
            }
            player.faint();
            return new Result(false, "Have you not eaten bread today?");
        }

        // اگر درخت باشه
        if (targetTile.getTree() != null) {
            player.decreaseEnergy(this.getConsumptionEnergy() - optionForNerds);
            targetTile.setTree(null);
            return new Result(true, "The tree falls with a satisfying crash.");
        }

        // اگر چوب روی زمین باشه
        if (targetTile.getItem() instanceof Wood) {
            player.decreaseEnergy(this.getConsumptionEnergy() - optionForNerds);
            targetTile.setItem(null);
            return new Result(true, "The scattered branches vanish, leaving the ground clean.");
        }
        // اگر زمین خالی است

            player.decreaseEnergy(this.getConsumptionEnergy() - optionForNerds - 1);
            return new Result(false, "Hmmmm! You can't do anything with an axe here.");

    }



}
