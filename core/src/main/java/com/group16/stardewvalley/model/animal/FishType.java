package com.group16.stardewvalley.model.animal;

import com.group16.stardewvalley.model.time.Season;

public enum FishType {

    SALMON("Salmon", 75, Season.Fall, true),
    SARDINE("Sardine", 40, Season.Fall, true),
    SHAD("Shad", 60, Season.Fall, true),
    BLUE_DISCUS("Blue Discus", 120, Season.Fall, true),
    MIDNIGHT_CARP("Midnight Carp", 150, Season.Winter, true),
    SQUID("Squid", 80, Season.Winter, true),
    TUNA("Tuna", 100, Season.Winter, true),
    PERCH("Perch", 55, Season.Winter, true),
    FLOUNDER("Flounder", 100, Season.Spring, true),
    LIONFISH("Lionfish", 100, Season.Spring, true),
    HERRING("Herring", 30, Season.Spring, true),
    GHOSTFISH("Ghostfish", 45, Season.Spring, true),
    TILAPIA("Tilapia", 75, Season.Summer, true),
    DORADO("Dorado", 100, Season.Summer, true),
    SUNFISH("Sunfish", 30, Season.Summer, true),
    RAINBOW_TROUT("Rainbow Trout", 65, Season.Summer, true),
    Legend("Legend", 5000, Season.Spring, false),
    GLACIERFISH("Glacierfish", 1000, Season.Winter, false   ),
    ANGLER("Angler", 900, Season.Fall, false),
    CRIMSONFISH("Crimsonfish", 1500, Season.Summer, false);

    private final String name;
    private final Integer price;
    private final Season season;
    private final Boolean isNormal;  //true: normal - false : legendary

    FishType(String name, int price, Season season, Boolean isLegendary) {
        this.name = name;
        this.price = price;
        this.season = season;
        this.isNormal = isLegendary;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Season getSeason() {
        return season;
    }

    public Boolean getIsNormal() {
        return isNormal;
    }


    @Override
    public String toString() {
        return "name: " + name + ", price: " + price + ", season: " + season + ", isNormal: " + isNormal;
    }
}