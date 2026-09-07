package com.group16.stardewvalley.model.items;

import com.group16.stardewvalley.model.agriculture.SeedType;

public class Seed extends Item {
    private SeedType type;

    public Seed(String name, SeedType type) {
        super(name, type.getPrice());
        this.type = type;
    }

    public SeedType getType() {
        return type;
    }

    public void setType(SeedType type) {
        this.type = type;
    }
}
