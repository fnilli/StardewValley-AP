package com.group16.stardewvalley.model.map;

import com.group16.stardewvalley.model.shops.Shop;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;

public enum Location {

    NPCFarm,
    //no special place:
    Game,
    Farm,
    MiningLevel,
    FarmingLevel,
    Foraging,
    PierresGeneralStore,
    FishShop,
    Blacksmith,
    CarpentersShop,
    JojaMart,
    MarniesRanch,
    TheStardropSaloon;


    private final Game game = App.getActiveGame();

    public Shop getShopByLocation() {
        return switch (this) {
            case PierresGeneralStore -> game.getPierresGeneralStore();
            case FishShop -> game.getFishShop();
            case Blacksmith -> game.getBlacksmith();
            case CarpentersShop -> game.getCarpentersShop();
            case JojaMart -> game.getJojaMart();
            case MarniesRanch -> game.getMarniesRanch();
            case TheStardropSaloon -> game.getTheStardropSaloon();
            default -> null;
        };
    }

    public boolean isShop() {
        return switch (this) {
            case PierresGeneralStore,
                 FishShop,
                 Blacksmith,
                 CarpentersShop,
                 JojaMart,
                 MarniesRanch,
                 TheStardropSaloon -> true;
            default -> false;
        };
    }

    public static Location getLocationByName(String name) {
        if (name.equalsIgnoreCase("blacksmith")) {
            return Location.Blacksmith;
        }

        if (name.equalsIgnoreCase("CarpentersShop")) {
            return Location.CarpentersShop;
        }

        if (name.equalsIgnoreCase("TheStardropSaloon")) {
            return Location.TheStardropSaloon;
        }

        if (name.equalsIgnoreCase("MarniesRanch")) {
            return Location.MarniesRanch;
        }

        if (name.equalsIgnoreCase("FishShop")) {
            return Location.FishShop;
        }

        if (name.equalsIgnoreCase("JojaMart")) {
            return Location.JojaMart;
        }

        if (name.equalsIgnoreCase("PierresGeneralStore")) {
            return Location.PierresGeneralStore;
        }
        return null;
    }
}
