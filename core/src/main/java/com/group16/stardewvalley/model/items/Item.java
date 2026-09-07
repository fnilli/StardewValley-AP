package com.group16.stardewvalley.model.items;

public abstract class Item {
    private final String name;
    private final int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getAssetPath() {
        return "items/" + name + ".png";
    }

}
