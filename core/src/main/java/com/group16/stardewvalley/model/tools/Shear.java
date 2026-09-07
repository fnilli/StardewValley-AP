package com.group16.stardewvalley.model.tools;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;

public class Shear extends Gadget{
    public Shear(String name, int price) {
        super(name, price);
    }

    public int getPrice() {
        return 1000;
    }

    public int getConsumptionEnergy() {
        return 4;
    }

    @Override
    public Result use(Tile targetTile, Game game) {
        Player player = game.getCurrentPlayer();
        // خطای انرژی
        if (player.getEnergy() < 4) {
            player.decreaseEnergy(4);
            player.faint();
            return new Result(false, "Have you not eaten bread today?");
        }

        /*
        // استفاده از قیچی روی دام
        if () {
            //TODO نیلی
            player.decreaseEnergy(4);
            player.addFarmingAbilityScore(5); // پیام مناسب هم بذار یا بگو خودم بذارم
        }

         */

        //هیچار در این منطقه با قیچی نتواند بکند
        player.decreaseEnergy(4);
        return new Result(false, "You cannot use the Shear in this area!×");


    }
}
