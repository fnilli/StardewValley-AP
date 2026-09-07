package com.group16.stardewvalley.model.shops;

public enum UpgradeType {
    COPPER_TOOL(1),
    STEEL_TOOL(1),
    GOLD_TOOL(1),
    IRIDIUM_TOOL(1),
    COPPER_TRASHCAN(1),
    STEEL_TRASHCAN(1),
    GOLD_TRASHCAN(1),
    IRIDIUM_TRASHCAN(1);

    private final int maxUpgrades;

    UpgradeType(int maxUpgrades) {
        this.maxUpgrades = 1;
    }

    public int getMaxUpgrades() {
        return maxUpgrades;
    }
}