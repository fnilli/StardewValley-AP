package com.group16.stardewvalley.model.agriculture;

import com.group16.stardewvalley.model.items.Item;

public class Fertilizer extends Item {
    String name;
    FertilizerType fertilizerType;
    public Fertilizer(String name, int price, FertilizerType fertilizerType) {
        super(name, price);
        this.fertilizerType = fertilizerType;
    }

    public FertilizerType getFertilizerType() {
        return fertilizerType;
    }
}
