package com.group16.stardewvalley.model.shops;

public enum BuildingType {

    Barn("barn",6000, 350, 150, 7, 4, 1, 4),
    Big_Barn("big barn",12000, 450, 200, 7, 4, 1, 8),
    Deluxe_Barn("deluxe barn",25000, 550, 300, 7, 4, 1, 12),
    Coop("coop",4000, 300, 100, 6, 3, 1, 4),
    Big_Coop("big coop", 10000, 400, 150, 6, 3, 1, 8),
    Deluxe_Coop("deluxe coop",20000, 500, 200, 6, 3, 1, 12),
    Well("well", 1000, 0, 75, 3, 3, 1, 0),
    Shipping_Bin("shipping bin", 250, 150, 0, 1, 1, Integer.MAX_VALUE, 0);

    private final String name;
    private final int cost;
    private final int woodCost;
    private final int stoneCost;
    private final int length;
    private final int width;
    private final int dailySaleLimit;
    private final int animalLimit;

    BuildingType(String name, int cost, int woodCost, int stoneCost, int length, int width, int dailySaleLimit, int animalLimit) {
        this.name = name;
        this.cost = cost;
        this.woodCost = woodCost;
        this.stoneCost = stoneCost;
        this.length = length;
        this.width = width;
        this.dailySaleLimit = dailySaleLimit;
        this.animalLimit = animalLimit;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getWoodCost() {
        return woodCost;
    }

    public int getStoneCost() {
        return stoneCost;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getDailySaleLimit() {
        return dailySaleLimit;
    }

    public int getAnimalLimit() {
        return animalLimit;
    }

    public static BuildingType buildingFromName(String name) {
        for (BuildingType type : BuildingType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null; // or throw an IllegalArgumentException if you prefer
    }

}
