package com.group16.stardewvalley.controller;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.shops.Building;
import com.group16.stardewvalley.model.weather.WeatherCondition;
import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.animal.AnimalProductTypes;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.user.Player;

import java.util.Objects;

import static com.group16.stardewvalley.model.animal.ProductQuality.getProductQuality;
import static com.group16.stardewvalley.model.map.Pos.isNear;

public class AnimalController {

    public Result pet(String name) {
        Game game = App.getActiveGame();
        Player player = game.getCurrentPlayer();

        for (Animal animal: game.getGameAnimals()){
            if(animal.getName().equals(name)){ //that animal exists

                if(isNear(player.getPosition(), animal.getAnimalPos())){    //check if player is in the 8 neighboring tiles of animal

                    animal.increaseFriendship(15);
                    animal.setIsPet(true);
                    return new Result(true, "done with pet");

                }
                return new Result(false, "go near that animal");
            }
        }
        return new Result(false, "no animal with that name");


    }


    public Result cheatSetAnimalFriendship(String name, int amount) {
        Animal animal = animalFromName(name);
        if(animal != null){
            animal.increaseFriendship(amount);
            return new Result(true, name + " friendship cheated to " + amount);
        }
        return new Result(false, "no animal with that name");
    }

    private Animal animalFromName(String name){
        Game game = App.getActiveGame();

        for(Animal animal : game.getGameAnimals()){
            if(animal.getName().equals(name)){
                return animal;
            }
        }
        return null;
    }


    public Result showAnimalInfo() {
        Game game = App.getActiveGame();

        StringBuilder sb = new StringBuilder();

        for (Animal animal : game.getGameAnimals()) {
            if (animal.getOwner().equals(game.getCurrentPlayer())) {
                sb.append("animal type: ").append(animal.getAnimalType().getName());
                sb.append("\nname: ").append(animal.getName());
                sb.append("\nFriendship score: ").append(animal.getFriendship());
                sb.append("\nPet: ").append(animal.isPet() ? "yes" : "no");
                sb.append("\nFed: ").append(animal.isFeed() ? "yes" : "no").append("\n");
            }
        }

        return new Result(true, sb.toString());
    }

    public Result shepherdAnimals(String name, int x, int y) {
        Game game = App.getActiveGame();
        Animal animal = animalFromName(name);

        if(animal == null){
            return new Result(false, "no animal with that name");
        }

        if(!animal.isOut()){//animal wants to go out
            if(game.getWeatherCondition() != WeatherCondition.SUNNY){
                return new Result(false, "cant Shepherding in storm, rain or snow. feed animals with hay");
            }
            //sunny -> go out
            if(game.getMap()[y][x].getType().equals(TileType.Forage) || game.getMap()[y][x].getType().equals(TileType.Ground) || game.getMap()[y][x].getType().equals(TileType.GreenHouse)) {

                animal.setIsOut(true);
                //increase friendship score
                animal.increaseFriendship(8);
                animal.setIsFeed(true);
                return new Result(true, "done with shepherd");
            }else {
                return new Result(false, "no plant to eat in this position, try somewhere else");
            }

        }
        //animal wants to go inside
        animal.setIsOut(false);
        return new Result(true, "end of shepherd, lets bring animals  inside");

    }

    public Result feedHay(String name) {
        Game game = App.getActiveGame();
        Animal animal = animalFromName(name);

        if(animal == null){
            return new Result(false, "no animal with that name");
        }

        if(animal.isFeed()){
            return new Result(false, "already fed today");
        }
        if(animal.haveFedWithHayToday()) {
            return new Result(false, "no more hay for today");
        }


        animal.setIsFeed(true);
        animal.setHaveFedWithHayToday(true);
        return new Result(true, "done with feed");
    }


    public void checkAnimalAtTheEndOfDay(String name) {
        Game game = App.getActiveGame();
        Animal animal = animalFromName(name);


        int decreaseAmount = 0;

        if(!animal.isFeed())    decreaseAmount -= 20;
        if(!animal.isPet())     decreaseAmount -= 10;
//        if(!animal.getAnimalStayOutAllNight()) decreaseAmount -= 20;

        animal.increaseFriendship(decreaseAmount);
    }


    public Result animalProduces(){
        Game game = App.getActiveGame();

        StringBuilder sb = new StringBuilder();

        for (Animal animal : game.getGameAnimals()) {
            if( !animal.havePickedProducts() ){ //محصولاتی که جمع نشده
                sb.append("animal type: ").append(animal.getAnimalType().getName());
                sb.append("\nname: ").append(animal.getName());
                sb.append("\nproducts: ");
                for(AnimalProductTypes productTypes: animal.getAnimalType().getProductList()){
                    sb.append(productTypes.getName()).append(", ");
                    sb.append("product quality: ").append(Objects.requireNonNull(getProductQuality(animal)).toString());
                }
            }
        }

        return new Result(true, sb.toString());
    }


    private double probability(Animal animal){
        if(animal.getFriendship() < 100)    return 1;

        double random = 0.5 + Math.random();

        return( (animal.getFriendship() + (150 * random)) / 1500);
    }

    //TODO nilli
    /*
    public Result collectProduct(String name){//برداشت محصول حیوان

    }
     */

    public Result sellAnimal(String name){
        Game game = App.getActiveGame();
        Animal animal = animalFromName(name);
        if(animal == null){
            return new Result(false, "no animal with that name");
        }

        double price =  (animal.getAnimalType().getPrice() * (((double) animal.getFriendship() / 1000 ) + 0.3));
        int sellPrice = (int) (Objects.requireNonNull(getProductQuality(animal)).getPriceCoefficient() * price);

        //sell animal
        //delete from game list
        game.getGameAnimals().removeIf(gameAnimal -> gameAnimal.getName().equals(animal.getName()));
        //delete from building
        for(Building b : game.getBuildings()) {
            b.getBuildingAnimals().remove(animal);
        }
        //earn money
        game.getCurrentPlayer().increaseCoin(sellPrice);

        return new Result(true, "animal sold successfully, you earned " + sellPrice + " coin.");

    }


    public void resetDailyAnimalStuff(Animal animal){
        animal.setIsFeed(false);
        animal.setIsPet(false);
        animal.setHaveFedWithHayToday(false);
        animal.setHavePickedProducts(false);

    }
}
