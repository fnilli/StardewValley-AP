package com.group16.stardewvalley.model.agriculture;


import com.group16.stardewvalley.model.map.Pos;

public class Crop {
    private CropType cropType;
    private int sellPrice;
    private int energy;
    private int stage;
    private final int finalStage;
    private int dayPastFromLastStage;
    private int daysSinceLastHarvest;
    private int daySinceLastWater;
    private int daysSincePlanting;
    private boolean isHarvested;
    private boolean isWatered;
    private boolean isWateredYesterday;
    private boolean isMature;
    boolean isColossal;
    boolean isPartOfColossal = false;
    Pos rootOfColossal = null; // مختصاتی که محصول غول‌پیکر واقعی اونجاست (مثلاً (5, 5))
    private boolean isFertilized;
    private Pos position;

    public Crop(CropType cropType) {
        this.cropType = cropType;
        this.sellPrice = cropType.getBaseSellPrice();
        this.energy = cropType.getEnergy();
        this.finalStage = cropType.getStages().length + 1;

        // متغیرهای زمان بازی
        this.stage = 1;
        this.dayPastFromLastStage = 0;
        this.daysSinceLastHarvest = 0;
        this.daySinceLastWater = 0;
        this.daysSincePlanting = 0;
        this.isWatered = false;
        this.isMature = false;
        this.isHarvested = false;
        this.isColossal = false;
        this.isFertilized = false;
        this.isWateredYesterday = true;
    }

    public Pos getPosition() {
        return position;
    }

    public void setPosition(Pos position) {
        this.position = position;
    }

    public boolean isWateredYesterday() {
        return isWateredYesterday;
    }

    public void setWateredYesterday(boolean wateredYesterday) {
        isWateredYesterday = wateredYesterday;
    }

    public void setColossal(boolean colossal) {
        isColossal = colossal;
    }

    public boolean isPartOfColossal() {
        return isPartOfColossal;
    }

    public void setPartOfColossal(boolean partOfColossal) {
        isPartOfColossal = partOfColossal;
    }

    public Pos getRootOfColossal() {
        return rootOfColossal;
    }

    public void setRootOfColossal(Pos rootOfColossal) {
        this.rootOfColossal = rootOfColossal;
    }

    public boolean isFertilized() {
        return isFertilized;
    }

    public void setFertilized(boolean fertilized) {
        isFertilized = fertilized;
    }

    public boolean isHarvested() {
        return isHarvested;
    }

    public void setHarvested(boolean harvested) {
        if (harvested) {
            stage = finalStage + 1;
        }
        isHarvested = harvested;
    }

    public int getDaySinceLastWater() {
        return daySinceLastWater;
    }

    public void setDaySinceLastWater(int daySinceLastWater) {
        this.daySinceLastWater = daySinceLastWater;
    }

    public void setDaysSincePlanting(int daysSincePlanting) {
        this.daysSincePlanting = daysSincePlanting;
    }

    public boolean isColossal() {
        return isColossal;
    }

    public int getDaysSincePlanting() {
        return daysSincePlanting;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

    public int getDaysSinceLastHarvest() {
        return daysSinceLastHarvest;
    }

    public void setDaysSinceLastHarvest(int daysSinceLastHarvest) {
        this.daysSinceLastHarvest = daysSinceLastHarvest;
    }

    public boolean isMature() {
        return isMature;
    }

    public void setMature(boolean mature) {
        isMature = mature;
    }

    public boolean isWatered() {
        return isWatered;
    }

    public void setWatered(boolean watered) {
        isWatered = watered;
    }

    public int getDayPastFromLastStage() {
        return dayPastFromLastStage;
    }

    public void setDayPastFromLastStage(int dayPastFromLastStage) {
        this.dayPastFromLastStage = dayPastFromLastStage;
    }

    public int getFinalStage() {
        return finalStage;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void advanceStage() {
        if (!isMature) {
            dayPastFromLastStage++;
            daysSincePlanting++;
            if (dayPastFromLastStage >= cropType.getStages()[stage - 1]) {
                stage++;
                if (stage == finalStage) {
                    isMature = true;
                }
                dayPastFromLastStage = 0;
            }
        }
        if (!cropType.isOneTime()) {
            if (isHarvested) {
                daysSinceLastHarvest++;
                if (daysSinceLastHarvest >= getCropType().getRegrowthTime()) {
                    isMature = true;
                    stage = finalStage;
                    isHarvested = false;
                    daysSinceLastHarvest = 0;
                }
            }
        }

    }




}

