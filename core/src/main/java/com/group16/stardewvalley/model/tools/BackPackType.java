package com.group16.stardewvalley.model.tools;

public enum BackPackType {
    Base_Pack(12, 200),
    Large_Pack(24, 10000),
    Deluxe_Pack(900719925, 0);

    private final int capacity;
    private final int upgradeCost;

    BackPackType(int capacity, int upgradeCost) {
        this.capacity = capacity;
        this.upgradeCost = upgradeCost;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }
}
