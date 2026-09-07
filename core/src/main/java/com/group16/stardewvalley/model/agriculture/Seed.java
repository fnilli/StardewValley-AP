package com.group16.stardewvalley.model.agriculture;

import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.time.Season;
import java.util.List;

public class Seed extends Item {
    private final int outOfSeasonPrice;
    private final List<Season> availableSeasons;
    private final String type;
    private final int dailyLimit;
    private final int price;

    public Seed(String name, int price, int dailyLimit,
                int outOfSeasonPrice, List<Season> availableSeasons, String type) {
        super(name, price);
        this.outOfSeasonPrice = outOfSeasonPrice;
        this.availableSeasons = availableSeasons;
        this.type = type;
        this.price = price;
        this.dailyLimit = dailyLimit;
    }

    public int getOutOfSeasonPrice() {
        return outOfSeasonPrice;
    }

    public List<Season> getAvailableSeasons() {
        return availableSeasons;
    }

    public String getType() {
        return type;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public boolean isAvailableInSeason(Season season) {
        return availableSeasons.contains(season) ||
                availableSeasons.size() == Season.values().length;
    }
}