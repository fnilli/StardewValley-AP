package com.group16.stardewvalley.model.shops;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.group16.stardewvalley.controller.CheatCodeController;
import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.items.Stone;
import com.group16.stardewvalley.model.items.Wood;
import com.group16.stardewvalley.model.map.PlaceType;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.BackingStoreException;

import static com.group16.stardewvalley.controller.map.MapController.isPlayerInFarm;

public class CarpentersShop extends Shop{
    private CheatCodeController cheatController = new CheatCodeController();
    private final Map<String, Texture> carpenterTextures = new HashMap<>();

    private static CarpentersShop instance;
    private ArrayList<Item> products= new ArrayList<>();
    public static CarpentersShop getInstance() {
        if (instance == null) {
            instance = new CarpentersShop(); // ایجاد نمونه جدید
        }
        return instance; // برگرداندن نمونه موجود
    }
    public CarpentersShop() {
        super("Carpenter's Shop", "Robin", 9, 8, PlaceType.CarpentersShop);
        initializeItems();
    }

    public void initializeItems() {
        addItem(new Wood("Wood", 10), Integer.MAX_VALUE);
        addItem(new Stone("Stone", 20), Integer.MAX_VALUE);
        addItem(new Building("Barn",6000), 1);
        addItem(new Building("Big Barn", 12000), 1);
        addItem(new Building("Deluxe Barn", 25000), 1);
        addItem(new Building("Coop", 4000), 1);
        addItem(new Building("Big Coop", 10000), 1);
        addItem(new Building("Deluxe Coop", 20000), 1);
        addItem(new Building("Well", 1000), 1);
        addItem(new Building("Shipping Bin", 250), 1);
    }
    private int barn;
    private int bigBarn;
    private int deluxeBarn;
    private int coop;


    public Result buildCoop_Barn(String buildingName, int x, int y) {
        Player player = App.getActiveGame().getCurrentPlayer();
        Game game = App.getActiveGame();
        Map<Item, Integer> items  = player.getInventory().getItems();

        //check if player is inside farm
//        if(!isPlayerInFarm(player)){
//            return new Result(false, "You are not inside farm");
//        }

        if (buildingName.equals("Wood")) {
            // Create a new Wood instance
            Wood woodItem = new Wood("Wood", 10);
            player.getInventory().addItem(woodItem, 1);
            player.decreaseCoin(10);
            return new Result(true, "Wood added to inventory successfully");
        }

        if (buildingName.equals("Stone")) {
            // Create a new Wood instance
            Stone stoneItem = new Stone("Stone", 20);
            player.getInventory().addItem(stoneItem, 1);
            player.decreaseCoin(20);
            return new Result(true, "Stone added to inventory successfully");
        }

        //create building
        BuildingType buildingType = null;
        for (BuildingType type : BuildingType.values()) {
            if (type.getName().equalsIgnoreCase(buildingName)) {
                buildingType = type;
            }
        }

        //check if name is valid
        if (buildingName == null) {
            return new Result(false, "no building found with the name " + buildingName);
        }




        //TODO: cheat for wood and stone
        player.increaseCoin(buildingType.getCost());
        cheatController.addWood(2 * buildingType.getWoodCost());
        cheatController.addStone(buildingType.getStoneCost());


        //check if it has enough money
        if (player.getCoin() < buildingType.getCost()){
            return new Result(false, "you dont have enough coin");
        }

        //check if it has enough wood and stone

        Item woodItem = null;
        for (Item item : items.keySet()) {
            if (item instanceof Wood) {
                woodItem = item;
                int woodAmount = items.get(item);
//                System.out.println(woodAmount);
                if (woodAmount < buildingType.getWoodCost()) {
                    return new Result(false, "You don't have enough wood");
                }
            }
        }

        Item stoneItem =  null;
        for (Item item : items.keySet()) {
            if (item instanceof Stone) {
                stoneItem = item;
                int stoneAmount = items.get(item);
                if (stoneAmount < buildingType.getStoneCost()) {
                    return new Result(false, "You don't have enough stone");
                }
            }
        }

        //everything ok, lets build
        Pos buildingPos = new Pos(x, y);
        Item newBuilding = new Building(buildingType.getName(),buildingType.getCost(),  buildingType, buildingPos);

        //remove wood/stone from inventory

//        items.compute(woodItem, (k, currentAmount) -> currentAmount - 350);
//        items.compute(stoneItem, (k, currentAmount) -> currentAmount - 150);
        player.decreaseCoin(buildingType.getCost());

        if (x == -1 && y == -1) {
            // Do all resource checks, but skip actual placement
            return new Result(true, "Building ready to place. Click to select location.");
        }


        //check if ground is empty
//        for (int i = y; i < y + buildingType.getLength(); i++) {
//            for (int j = x; j < x + buildingType.getWidth(); j++) {
//                if(game.getMap()[i][j].isTileEmpty()){
//                    return new Result(false, "There is something on the ground at (" + j + ", " + i + ")");
//                }
//            }
//        }


        // place building with origin flag
        for (int row = 0; row < buildingType.getWidth(); row++) {
            for (int col = 0; col < buildingType.getLength(); col++) {
                Tile tile = game.getMap()[y + row][x + col];
                if (row == 0 && col == 0) {
                    tile.setItem(newBuilding);
                    tile.setBuildingOrigin(true); // only top-left tile is origin
                } else {
                    tile.setItem(null); // no item stored on other tiles
                    tile.setBuildingOrigin(false);
                }
            }
        }

        game.getBuildings().add((Building) newBuilding);
        player.getInventory().addItem(newBuilding, 1);

        return new Result(true, newBuilding.getName() + " built successfully");

    }


}
