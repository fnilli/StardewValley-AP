package com.group16.stardewvalley.model.tools;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;

public class MilkPail extends  Gadget{
    public MilkPail(String name, int price) {
        super(name, price);
    }

    public int getPrice() {
        return 1000;
    }

    public int getConsumptionEnergy() {
        return 4;
    }
    public Result use(Tile targetTile, Game game) {
        Player player = game.getCurrentPlayer();
        // خطای انرژی
        if (player.getEnergy() < 4) {
            player.decreaseEnergy(4);
            player.faint();
            return new Result(false, "Have you not eaten bread today?");
        }
        // باید چک بشه که دام درسته برای این کار
        /*
        if () {
            //TODO نیلی
            player.decreaseEnergy(4);
            player.addFarmingAbilityScore(5);
        }
        
         */

        // اگر در این منطقه هیچکار با سطل شیر نشه انجام داد
        player.decreaseEnergy(4);
        return new Result(false, "You cannot use the Milk Pale in this area!×");

    }
}
