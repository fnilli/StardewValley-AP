package com.group16.stardewvalley.model.agriculture;

import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.time.Season;
import com.group16.stardewvalley.model.time.TimeDate;

public class Tree {
    private final TreeType treeType;
    private final int totalHarvestTime;
    private int fruitSellPrice;
    private int dayPastFromLastHarvest;
    private int dayPastFromLastStage;
    private int dayPastFromPlanting;
    private int stage;
    private boolean isMature;
    private boolean isBurned;
    private boolean isWatered;
    private boolean isWateredYesterday;
    private boolean isFertilized;
    private int daySinceLastWater;
    private boolean hasFruit;
    private int daySincePickFruit;


    public Tree(TreeType type) {
        this.treeType = type;
        this.totalHarvestTime = type.getTotalGrowthTime();
        this.fruitSellPrice = type.getFruitSellPrice();
        this.dayPastFromLastHarvest = 0;
        this.dayPastFromLastStage = 0;
        this.dayPastFromPlanting = 0;
        this.daySinceLastWater = 0;
        this.stage = 1;
        this.isMature = false;
        this.isBurned = false;
        this.isWatered = false;
        this.isFertilized = false;
        this.isWateredYesterday = true;
        this.hasFruit = false;
        this.daySincePickFruit = 0;
    }

    public boolean isWateredYesterday() {
        return isWateredYesterday;
    }

    public void setWateredYesterday(boolean wateredYesterday) {
        isWateredYesterday = wateredYesterday;
    }

    public boolean isFertilized() {
        return isFertilized;
    }

    public void setFertilized(boolean fertilized) {
        isFertilized = fertilized;
    }

    public void setDayPastFromPlanting(int dayPastFromPlanting) {
        this.dayPastFromPlanting = dayPastFromPlanting;
    }

    public int getDaySinceLastWater() {
        return daySinceLastWater;
    }

    public void setDaySinceLastWater(int daySinceLastWater) {
        this.daySinceLastWater = daySinceLastWater;
    }

    public boolean isWatered() {
        return isWatered;
    }

    public void setWatered(boolean watered) {
        isWatered = watered;
    }

    public TreeType getTreeType() {
        return treeType;
    }

    public boolean isBurned() {
        return isBurned;
    }

    public void setBurned(boolean burned) {
        isBurned = burned;
    }

    public boolean isMature() {
        return isMature;
    }

    public void setMature(boolean mature) {
        isMature = mature;
    }

    public int getDayPastFromLastStage() {
        return dayPastFromLastStage;
    }

    public void setDayPastFromLastStage(int dayPastFromLastStage) {
        this.dayPastFromLastStage = dayPastFromLastStage;
    }

    public int getDayPastFromLastHarvest() {
        return dayPastFromLastHarvest;
    }

    public void setDayPastFromLastHarvest(int dayPastFromLastHarvest) {
        this.dayPastFromLastHarvest = dayPastFromLastHarvest;
    }

    public int getDayPastFromPlanting() {
        return dayPastFromPlanting;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public int getFruitSellPrice() {
        return fruitSellPrice;
    }

    public void setFruitSellPrice(int fruitSellPrice) {
        this.fruitSellPrice = fruitSellPrice;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public boolean HasFruit() {
        return hasFruit;
    }

    public void setHasFruit(boolean hasFruit) {
        this.hasFruit = hasFruit;
    }

    public void advanceStage() {
        if (!isMature) {
            dayPastFromLastStage++;
            dayPastFromPlanting++;
            if (dayPastFromLastStage >= 7) {
                stage++;
                if (stage == 5) {
                    isMature = true;
                }
                dayPastFromLastStage = 0;
            }
        }
    }

    public void handpickFruit() {
        hasFruit = false;
        daySincePickFruit = 0;
    }

    public void manageFruit() {
        daySincePickFruit ++;
        if (daySincePickFruit == treeType.getFruitCycleDays() &&
            treeType.getSeason().equals(TimeDate.getInstance(App.getActiveGame()).getSeason()) || treeType.getSeason().equals(Season.Special)) {
            hasFruit = true;
            daySincePickFruit = 0;
        }
        if (!treeType.getSeason().equals(App.getActiveGame().getTimeDate().getSeason())) {
            hasFruit = false;
        }
    }
}
