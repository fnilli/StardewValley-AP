package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.map.PlaceType;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.view.graphics.GameScreen;

import java.util.Objects;

import static com.group16.stardewvalley.model.shops.MarniesRanchAnimals.PIG;
import static com.group16.stardewvalley.model.shops.MarniesRanchAnimals.sellAnimalFromName;
import static com.group16.stardewvalley.model.animal.AnimalType.animalTypeFromName;

public class MarniesRanch extends Shop {
    private static MarniesRanch instance;
    public MarniesRanch() {
        super("Marnie's Ranch", "Marnie", 9, 16, PlaceType.MarniesRanch);

    }

    public static MarniesRanch getInstance() {
        if (instance == null) {
            instance = new MarniesRanch();
        }
        return instance;
    }


    private MarniesRanchAnimals animals;

    public Result buyAnimal(String animal, String name) {
        Game game = App.getActiveGame();
        Player player= App.getActiveGame().getCurrentPlayer();

        if(sellAnimalFromName(animal) == null){
            return new Result(false, "no animal with that name");
        }


        //TODO: cheat building
//        Item newBuilding = new Building(newAnimal.getFromShopType().getBuildingRequired().get(0).getName(),
//            newAnimal.getFromShopType().getBuildingRequired().get(0).getCost(),  newAnimal.getFromShopType().getBuildingRequired().get(0), new Pos(164, 64));
//        player.getInventory().addItem(newBuilding, 1);
//        game.getBuildings().add((Building) newBuilding);


//        for (Item item : player.getInventory().getItems().keySet()){
        for (Building building : game.getBuildings()) {

                for (BuildingType requiredBuilding : Objects.requireNonNull(sellAnimalFromName(animal)).getBuildingRequired()) {
                    if (building.getBuildingType().equals(requiredBuilding)) { //check if a suitable building for that animal exist.

                        System.out.println("found one");


                        //make new animal
                        Animal newAnimal = new Animal( sellAnimalFromName(animal), animalTypeFromName(animal), name, game.getCurrentPlayer()  );


                        if (building.getCapacity() < requiredBuilding.getAnimalLimit()) { //چک کن قفس جا داره یا نه
                            //decrease money
                            player.decreaseCoin(newAnimal.getAnimalType().getPrice());

                            //add animal to game animal
                            game.getGameAnimals().add(newAnimal);

                            //add to list of this building
                            building.addAnimal(newAnimal);

                            //decrease building capacity
                            building.increaseCapacity();

                            // Yard bottom-left position in tile coordinates
                            int yardStartX = building.getStartPosition().getX();
                            int yardStartY = building.getStartPosition().getY() - 3;

// Convert yard start to pixel coordinates
                            float yardPixelX = yardStartX * GameScreen.TILE_SIZE;
                            float yardPixelY = yardStartY * GameScreen.TILE_SIZE;
                            float yardWidth = 3 * GameScreen.TILE_SIZE;
                            float yardHeight = 3 * GameScreen.TILE_SIZE;

// Set tile-based position (keeps your original Pos logic)
                            newAnimal.setAnimalPos(new Pos(yardStartX, yardStartY));

// NEW: Set wandering pixel position
                            float startPixelX = yardPixelX + (float)(Math.random() * (yardWidth - GameScreen.TILE_SIZE));
                            float startPixelY = yardPixelY + (float)(Math.random() * (yardHeight - GameScreen.TILE_SIZE));
                            newAnimal.setPixelPosition(startPixelX, startPixelY);

// NEW: Set yard movement bounds
                            newAnimal.setYardBounds(
                                yardPixelX,
                                yardPixelY,
                                yardPixelX + yardWidth - GameScreen.TILE_SIZE,
                                yardPixelY + yardHeight - GameScreen.TILE_SIZE
                            );


                            return new Result(true, "animal added to building");
                        }

                    }
                }

        }
        return new Result(false, " no suitable building for animal found");

    }


    public Result showAllProducts() {
        StringBuilder output = new StringBuilder();
        for (MarniesRanchAnimals value : MarniesRanchAnimals.values()) {
            output.append(value.toString()).append(" ").append(value.getPrice()).append(" g\n");
        }
        return new Result(true,  output.toString());
    }

    public Result showAvailableItems() {
        StringBuilder output = new StringBuilder();
        for (MarniesRanchAnimals value : MarniesRanchAnimals.values()) {
            if (!Objects.equals(value.getDailyLimit(), value.getDailySold())) {
                output.append(value.toString()).append(" ").append(value.getPrice()).append(" g\n");
            }
        }
        return new Result(true, output.toString());
    }


//    public static model.structure.stores.MarnieShopAnimal getFromName(String name){
//        for (model.structure.stores.MarnieShopAnimal value : model.structure.stores.MarnieShopAnimal.values()) {
//            if (value.name.equalsIgnoreCase(name)){
//                return value;
//            }
//        }
//        return null;
//    }

    public Result resetDailySold() {
        animals.setDailySold(0);
        return new Result(true, "daily sold set to zero");
    }
}
