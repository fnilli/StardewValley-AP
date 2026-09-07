package com.group16.stardewvalley.model.crafting.artisan;

import com.group16.stardewvalley.model.items.Item;

public class ArtisanGood extends Item {
    ArtisanGoodType type;
    int startHour;

    public ArtisanGood(String name, int price, ArtisanGoodType type, int startHour) {
        super(name, price);
        this.type = type;
        this.startHour = startHour;
    }

    public ArtisanGoodType getType() {
        return type;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }
}
