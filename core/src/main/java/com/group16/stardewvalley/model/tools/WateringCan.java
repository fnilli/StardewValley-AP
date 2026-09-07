package com.group16.stardewvalley.model.tools;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.user.Player;

public class WateringCan extends Gadget{
    private int capacity;
    private int usedWaterCapacity;
    public WateringCan(String name, int price, String material) {
        super(name, price);
        this.material = material;
        this.capacity = 40;
        this.usedWaterCapacity = 0;
    }

    public int getUsedWaterCapacity() {
        return usedWaterCapacity;
    }

    @Override
    public int getPrice() {
       return ToolDataManager.getToolPrice("Watering Can", this.material);
    }

    public int getConsumptionEnergy() {
        return ToolDataManager.getEnergyConsumption("watering can", this.material);
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setMaterial(String material) {
        this.material = material;
        this.capacity = ToolDataManager.getWateringCanCapacity(material);
    }

    private boolean canWater() {
        return capacity > usedWaterCapacity;
    }

    @Override
    public Result use(Tile targetTile, Game game) {
        Player player = game.getCurrentPlayer();
        int requiredEnergy = this.getConsumptionEnergy();
        if (player.getFarmingAbilityLevel() == 4) {
            requiredEnergy--;
        }
        //خطای انرژی
        if (player.getEnergy() < requiredEnergy) {
            player.decreaseEnergy(requiredEnergy - 1);
            player.faint();
            return new Result(false, "Have you not eaten bread today?");
        }

        // محصولی هست پس اب میدهیم
        if (targetTile.getCrop() != null || targetTile.getTree() != null) {
            if (this.canWater()) {
                targetTile.setHasWater(true);
                if (targetTile.getCrop() != null) {
                    targetTile.getCrop().setWatered(true);
                }
                if (targetTile.getTree() != null) {
                    targetTile.getTree().setWatered(true);
                }
                player.decreaseEnergy(requiredEnergy);
                return new Result(true, "The crop has been watered successfully! ^ ^");
            } else {
                player.decreaseEnergy(requiredEnergy - 1);
                return new Result(false, "Your watering can is empty ×");
            }
        }

        // پر کردن ظرف ابیاری
        if (targetTile.getType().equals(TileType.Lake)) {
            player.decreaseEnergy(requiredEnergy);
            this.usedWaterCapacity = 0;
            return new Result(true, "Ready to water! Your can is now full of water =)");
        }

        // هیچکار نتونسته باهاش بکنه
        player.decreaseEnergy(requiredEnergy - 1);
        return new Result(false, "You cannot use the watering can in this area!×");

    }
}
