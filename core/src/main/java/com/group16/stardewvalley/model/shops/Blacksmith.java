package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.items.Ore;
import com.group16.stardewvalley.model.items.OreType;
import com.group16.stardewvalley.model.map.PlaceType;

import java.util.EnumMap;

public class Blacksmith extends Shop{
    private static Blacksmith instance;
    private final EnumMap<UpgradeType, Integer> upgrades = new EnumMap<>(UpgradeType.class);

    public Blacksmith() {
        super("Blacksmith", "Clint", 9, 16, PlaceType.Blacksmith);
        initializeUpgrades();
        initializeItems();
    }

    public static Blacksmith getInstance() {
        if (instance == null) {
            instance = new Blacksmith();
        }
        return instance;
    }

    private void initializeUpgrades() {
        for (UpgradeType type : UpgradeType.values()) {
            upgrades.put(type, 0); // مقدار اولیه
        }
    }

    public boolean applyUpgrade(UpgradeType type) {
        int current = upgrades.get(type);
        if (current < type.getMaxUpgrades()) {
            upgrades.put(type, current + 1);
            return true;
        }
        return false;
    }

    public void resetDailyUpgrades() {
        upgrades.replaceAll((k, v) -> 0); // ریست روزانه
    }


    public void initializeItems() {
        addItem(new Ore("Coal", OreType.COAL, 75), OreType.COAL.getDailyLimit());
        addItem(new Ore("Copper Ore", OreType.COPPER_ORE, 75), OreType.COPPER_ORE.getDailyLimit());
        addItem(new Ore("Iron Ore", OreType.IRON_ORE, 75), OreType.IRON_ORE.getDailyLimit());
        addItem(new Ore("Gold Ore", OreType.GOLD_ORE, 75), OreType.GOLD_ORE.getDailyLimit());
    }

    public EnumMap<UpgradeType, Integer> getUpgrades() {
        return upgrades;
    }

    public boolean cabUpgradeToday(UpgradeType type) {
        return upgrades.get(type) < type.getMaxUpgrades();
    }

}