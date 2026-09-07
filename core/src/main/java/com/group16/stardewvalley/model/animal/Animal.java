package com.group16.stardewvalley.model.animal;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.shops.MarniesRanch;
import com.group16.stardewvalley.model.shops.MarniesRanchAnimals;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.user.Player;

public class Animal {
    private final AnimalType animalType;
    private final MarniesRanchAnimals fromShopType;
    private Pos animalPos;
    private AnimalProductTypes todayProduct;
    private Integer friendship;
    private boolean isFeed = false;
    private boolean isPet = false;
    private final String name;
    private boolean isOut;
    private boolean isAnimalStayOutAllNight = false;
    private boolean haveFedWithHayToday = false;
    private Player owner;
    // Movement fields
    private float pixelX, pixelY; // actual position for rendering
    private float vx, vy; // velocity
    private float minX, minY, maxX, maxY; // movement bounds

    private float pettingEffectTimer = 0f;
    private float feedingEffectTimer = 0f;
    private float shepherdEffectTimer = 0f;
    private float productEffectTimer = 0f;

    private boolean havePickedProducts = false;

    public Animal(MarniesRanchAnimals fromShopType, AnimalType animalType, String name, Player owner ) {
        this.fromShopType = fromShopType;
        this.animalType = animalType;
        this.name = name;
        this.owner = owner;
        this.friendship = 0;
    }

    public float getFeedEffectAlpha() {
        return feedingEffectTimer; // 1 → 0 fade-out
    }
    public void triggerFeedingEffect() {
        feedingEffectTimer = 3.0f; // lasts 1 second
    }
    public boolean isFeedingEffectActive() {
        return feedingEffectTimer > 0;
    }


    public float getPetEffectAlpha() {
        return pettingEffectTimer; // 1 → 0 fade-out
    }
    public void triggerPettingEffect() {
        pettingEffectTimer = 3.0f; // lasts 1 second
    }
    public boolean isPettingEffectActive() {
        return pettingEffectTimer > 0;
    }


    public float getShepherdEffectAlpha() {
        return shepherdEffectTimer;
    }
    public void triggerShepherdEffect() {
        shepherdEffectTimer = 3.0f;
    }
    public boolean isShepherdEffectActive() {
        return shepherdEffectTimer > 0;
    }

    public float getProductEffectAlpha() {
        return productEffectTimer;
    }
    public void triggerProductEffect() {
        productEffectTimer = 3.0f;
    }
    public boolean isProductEffectActive() {
        return productEffectTimer > 0;
    }


    public void setYardBounds(float minX, float minY, float maxX, float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public void setPixelPosition(float x, float y) {
        this.pixelX = x;
        this.pixelY = y;
    }

    public void update(float delta) {
        // Move
        pixelX += vx * delta;
        pixelY += vy * delta;

        // Stay inside yard bounds
        if (pixelX < minX) { pixelX = minX; vx = -vx; }
        if (pixelX > maxX) { pixelX = maxX; vx = -vx; }
        if (pixelY < minY) { pixelY = minY; vy = -vy; }
        if (pixelY > maxY) { pixelY = maxY; vy = -vy; }

        // Occasionally change direction
        if (Math.random() < 0.01) {
            vx = (float)(Math.random() * 40 - 20); // speed -20..20 px/s
            vy = (float)(Math.random() * 40 - 20);
        }

        //pet effect
        if (pettingEffectTimer > 0) {
            pettingEffectTimer -= delta;
        }
        //feed effect
        if (feedingEffectTimer > 0) {
            feedingEffectTimer -= delta;
        }
        if (shepherdEffectTimer > 0) {
            shepherdEffectTimer -= delta;
        }
        if(productEffectTimer > 0) {
            productEffectTimer -= delta;
        }
    }

    public float getPixelX() { return pixelX; }
    public float getPixelY() { return pixelY; }


    public Result increaseFriendship(int amount) {
        if(this.friendship + amount < 1000) {
            this.friendship += amount;
        }else  {
            this.friendship = 1000;
        }

        return new Result(true, "friendship increased");
    }







    //getter and setter


    public boolean haveFedWithHayToday() {
        return haveFedWithHayToday;
    }

    public void setHaveFedWithHayToday(boolean haveFedWithHayToday) {
        this.haveFedWithHayToday = haveFedWithHayToday;
    }

    public boolean havePickedProducts() {
        return havePickedProducts;
    }

    public void setHavePickedProducts(boolean havePickedProducts) {
        this.havePickedProducts = havePickedProducts;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setIsOut(boolean out) {
        isOut = out;
    }

    public Pos getAnimalPos() {
        return animalPos;
    }

    public void setAnimalPos(Pos animalPos) {
        this.animalPos = animalPos;
    }

    public MarniesRanchAnimals getFromShopType() {
        return fromShopType;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public AnimalProductTypes getTodayProduct() {
        return todayProduct;
    }

    public Integer getFriendship() {
        return friendship;
    }

    public boolean isFeed() {
        return isFeed;
    }

    public void setIsFeed(boolean feed) {
        isFeed = feed;
    }

    public boolean isPet() {
        return isPet;
    }

    public void setIsPet(boolean isPet) {
        this.isPet = isPet;
    }

    public String getName() {
        return name;
    }

    public boolean getAnimalStayOutAllNight() {
        return isAnimalStayOutAllNight;
    }

    public Player getOwner() {
        return owner;
    }

    public void setTodayProduct(AnimalProductTypes todayProduct) {
        this.todayProduct = todayProduct;
    }

    public void setFriendship(Integer friendship) {
        this.friendship = friendship;
    }

    public void setAnimalStayOutAllNight(boolean animalStayOutAllNight) {
        isAnimalStayOutAllNight = animalStayOutAllNight;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
