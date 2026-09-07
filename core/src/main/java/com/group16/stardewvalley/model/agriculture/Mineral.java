package com.group16.stardewvalley.model.agriculture;

import com.group16.stardewvalley.model.items.Item;

public class Mineral extends Item {
    private final MineralType type;
    public Mineral(String name, int price, MineralType type) {
        super(name, price);
        this.type = type;
    }
    public MineralType getType() {
        return type;
    }
}
