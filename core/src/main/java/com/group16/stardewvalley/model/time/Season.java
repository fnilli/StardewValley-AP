package com.group16.stardewvalley.model.time;

public enum Season {
    Spring(0, "Spring"),
    Summer(1, "Summer"),
    Fall(2, "Fall"),
    Winter(3, "Winter"),
    Special(4, "Special");


    private final int index;
    private final String name;

    Season(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

}