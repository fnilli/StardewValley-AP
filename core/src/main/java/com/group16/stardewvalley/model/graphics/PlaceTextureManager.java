package com.group16.stardewvalley.model.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.group16.stardewvalley.model.NPC.NPC;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.map.PlaceType;
import com.group16.stardewvalley.model.time.Season;
import com.group16.stardewvalley.model.time.TimeDate;

import java.util.HashMap;
import java.util.Map;

public class PlaceTextureManager {
    private static PlaceTextureManager placeTextureManager;
    private Map<PlaceType, TextureRegion> placesSpring = new HashMap<>();
    private Map<PlaceType, TextureRegion> placesSummer = new HashMap<>();
    private Map<PlaceType, TextureRegion> placesFall = new HashMap<>();
    private Map<PlaceType, TextureRegion> placesWinter = new HashMap<>();


    private Texture shopSpriteSheet;
    private Texture npcSpriteSheet;

    public PlaceTextureManager() {
        shopSpriteSheet = new Texture("Shops/Pelican Town " + TimeDate.getInstance(App.getActiveGame()).getSeason().getName() + ".png");
        npcSpriteSheet = new Texture("NPC/Cabins.png");
        loadShops(placesSpring, Season.Spring);
        loadShops(placesSummer, Season.Summer);
        loadShops(placesFall, Season.Fall);
        loadShops(placesWinter, Season.Winter);
    }

    public static PlaceTextureManager getPlaceTextureManager() {
        if (placeTextureManager == null) {
            placeTextureManager = new PlaceTextureManager();
        }
        return placeTextureManager;
    }

    private void loadShops(Map<PlaceType, TextureRegion> places, Season season) {
        shopSpriteSheet = new Texture("Shops/Pelican Town " + season.getName() + ".png");

        places.put(PlaceType.Blacksmith, new TextureRegion(shopSpriteSheet, 400, 0, 112, 128));
        places.put(PlaceType.JojaMart, new TextureRegion(shopSpriteSheet, 0, 837, 320, 155));
//        places.put(PlaceType.MarniesRanch, new TextureRegion(shopSpriteSheet, 0, 0, 128, 175));
        places.put(PlaceType.MarniesRanch, new TextureRegion(new Texture("Shops/Ranch.png")));
        places.put(PlaceType.PierresGeneralStore, new TextureRegion(shopSpriteSheet, 0, 176, 239, 147));
        places.put(PlaceType.TheStardropSaloon, new TextureRegion(shopSpriteSheet, 239, 176, 113, 144));
//        places.put(PlaceType.CarpentersShop, new TextureRegion(shopSpriteSheet, 190, 640, 195, 161));
        places.put(PlaceType.CarpentersShop, new TextureRegion(new Texture("Shops/Carpenter.png")));
        places.put(PlaceType.FishShop, new TextureRegion(shopSpriteSheet, 384, 665, 128, 136));

        places.put(PlaceType.Abigail, new TextureRegion(npcSpriteSheet, 79, 0, 82, 112));
        places.put(PlaceType.Leah, new TextureRegion(npcSpriteSheet, 160, 112,  80, 113));
        places.put(PlaceType.Robin, new TextureRegion(npcSpriteSheet, 160, 336, 80, 112));
        places.put(PlaceType.Harvey, new TextureRegion(npcSpriteSheet, 160,560, 80, 112));
        places.put(PlaceType.Sebastian, new TextureRegion(npcSpriteSheet, 0, 691, 80, 93));

    }

    public void updateSeason() {
        shopSpriteSheet = new Texture("Shops/Pelican Town " + App.getActiveGame().getTimeDate().getSeason().getName() + ".png");
    }

    public TextureRegion getShopTexture(PlaceType placeType) {
        switch (TimeDate.getInstance(App.getActiveGame()).getSeason()) {
            case Summer -> {
                return placesSummer.get(placeType);
            }
            case Fall -> {
                return placesFall.get(placeType);
            }
            case Winter -> {
                return placesWinter.get(placeType);
            }
            default -> {
                return placesSpring.get(placeType);
            }
        }
    }

    public void dispose() {
        shopSpriteSheet.dispose();
    }
}

