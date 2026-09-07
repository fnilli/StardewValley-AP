package com.group16.stardewvalley.model.items;

public class Ore extends Item {
    private OreType oreType;

    public Ore(String name, OreType type, int price) {
        super(name, price);
        this.oreType = type;
    }

    public OreType getOreType() {
        return oreType;
    }


}
