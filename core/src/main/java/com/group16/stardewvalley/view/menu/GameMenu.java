package com.group16.stardewvalley.view.menu;


import com.group16.stardewvalley.controller.CheatCodeController;
import com.group16.stardewvalley.controller.agriculture.AgricultureController;
import com.group16.stardewvalley.controller.AnimalController;
import com.group16.stardewvalley.controller.energy.EnergyController;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.controller.menu.HomeMenuController;
import com.group16.stardewvalley.controller.playercontroller.PlayerController;
import com.group16.stardewvalley.controller.relationship.RelationshipController;
import com.group16.stardewvalley.controller.shops.ShopController;
import com.group16.stardewvalley.controller.tools.GadgetController;
import com.group16.stardewvalley.controller.trade.TradeController;
import com.group16.stardewvalley.controller.weather.WeatherController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.crafting.Crafting;
import com.group16.stardewvalley.model.crafting.artisan.ArtisanController;
import com.group16.stardewvalley.model.energy.EnergyCommands;
import com.group16.stardewvalley.model.menu.GameMenuCheatCodeCommands;
import com.group16.stardewvalley.model.menu.GameMenuCommands;
import com.group16.stardewvalley.model.menu.LoginMenuCommands;
import com.group16.stardewvalley.model.menu.ProfileMenuCommands;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.tools.GadgetsCommands;
import com.group16.stardewvalley.model.user.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements MenuInterface {
    private final GameMenuController controller = new GameMenuController();
    private final MapController mapController = new MapController();
    private final Crafting craft = new Crafting();
    private final TimeDate timeDate = new TimeDate();
    private final AnimalController animalController = new AnimalController();
    private final AgricultureController agricultureController = new AgricultureController();
    private final CheatCodeController cheatCodeController = new CheatCodeController();
    private final HomeMenuController homeMenuController = new HomeMenuController();
    private final EnergyController energyController = new EnergyController();
    private final GadgetController gadgetController = new GadgetController();
    private final ShopController shopController = new ShopController();
    private final ArtisanController artisanController = new ArtisanController();
    private final WeatherController weatherController = new WeatherController();
    private final PlayerController playerController = new PlayerController();
    private final RelationshipController relationshipController = new RelationshipController();
    private final TradeController tradeController = new TradeController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();

        Matcher matcher = null;
        boolean isAtHome = App.getActiveGame() != null && MapController.isPlayerInCottage(App.getActiveGame().getCurrentPlayer());


        //new game
        if((matcher = GameMenuCommands.NewGame.getMatcher(input)) != null){ //after new game, player must choose farm and cant do anything else
            Result result = controller.newGame(matcher.group("usernames"));
            System.out.println(result);

            if(result.isSuccessful()){
                Matcher matcher2 = null;
                for (Player player : App.getActiveGame().getPlayers()) {
                    while (player.getFarm() == null) {
                        String input2 = scanner.nextLine();
                        if ((matcher2 = GameMenuCommands.ChooseMap.getMatcher(input2)) != null) {
                            System.out.println(controller.chooseFarm(player, matcher2.group("mapNumber")));
                        }
                        else System.out.println("now you must choose a map");
                    }
                }
                mapController.createMap();
            }

        } else if( (matcher = GameMenuCommands.LoadGame.getMatcher(input)) != null){
            System.out.println(controller.loadGame());
            //load game

        }else if((matcher = GameMenuCommands.Exit.getMatcher(input)) != null){
            System.out.println(controller.exit());

        }
        else if(App.getActiveGame() != null && (matcher = GameMenuCommands.ForceTerminateVote.getMatcher(input)) != null){
            Map<Player, Boolean> votes = new HashMap<Player, Boolean>();
            votes.put(App.getActiveGame().getCurrentPlayer(), true);
            System.out.println("vote in turn! (true/false)");
            for (int i = 0; i < 3; i++) {
                //TODO next turn
                System.out.println("player " + (i + 2) + " please vote.");
                String input2 = scanner.nextLine();
                if (input2.equals("true")) {
                    votes.put(App.getActiveGame().getCurrentPlayer(), true);
                }else if (input2.equals("false")) {
                    votes.put(App.getActiveGame().getCurrentPlayer(), false);
                }
            }
            System.out.println(controller.forceTerminateGame(votes));

        }
        else if((matcher = GameMenuCommands.NextTurn.getMatcher(input)) != null){
//            App.getActiveGame().nextTurn();

        }else if((matcher = GameMenuCommands.CurrentTurn.getMatcher(input)) != null){
            if(App.getActiveGame() != null){
                System.out.println(App.getActiveGame().getCurrentPlayer().getUser().getUsername());
            }
            else{
                System.out.println("game hasn't started yet");
            }

        } else if ((matcher = GameMenuCommands.ChangeMenu.getMatcher(input)) != null) {
            System.out.println(controller.changeMenu(matcher.group("MenuName")));
            System.out.println(controller.showHomeMenus());

        }else if(( matcher = ProfileMenuCommands.ExitMenu.getMatcher(input)) != null ) {
            //back to main menu
            System.out.println(controller.exitMenu());
        }else if((matcher = LoginMenuCommands.ShowCurrentMenu.getMatcher(input)) != null ) {
            System.out.println(controller.showCurrentMenu());

        } else if ((matcher = GameMenuCommands.Walk.getMatcher(input)) != null){
        Result result = mapController.askWalking(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
        System.out.println(result);
        if (result.isSuccessful()){
            String answer = scanner.nextLine();
            if (answer.equals("yes")) {
                System.out.println(mapController.walk(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            }
        }
        } else if ((matcher = GameMenuCommands.PrintMap.getMatcher(input)) != null){
                System.out.println(mapController.printMap(Integer.parseInt(matcher.group("x")),
                        Integer.parseInt(matcher.group("y")), Integer.parseInt(matcher.group("size"))));
        } else if ((matcher = GameMenuCommands.HelpReadingMap.getMatcher(input)) != null){
            System.out.println(mapController.helpReadingMap());
        } else if ((matcher = GameMenuCommands.CraftInfo.getMatcher(input)) != null){
            System.out.println(agricultureController.craftInfo(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.TreeInfo.getMatcher(input)) != null){
            System.out.println(agricultureController.treeInfo(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.PlantSeed.getMatcher(input)) != null){
          //  System.out.println(agricultureController.planting(matcher.group("seed"), matcher.group("dir")));
        } else if ((matcher = GameMenuCommands.ShowPlant.getMatcher(input)) != null){
            System.out.println(agricultureController.showPlant(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        } else if ((matcher = GameMenuCommands.Fertilize.getMatcher(input)) != null){
            System.out.println(agricultureController.fertilizePlant(matcher.group("fertilizer"), matcher.group("dir")));
        } else if ((matcher = GameMenuCommands.HowMuchWater.getMatcher(input)) != null){
            System.out.println(agricultureController.howMuchWater());
        }

        //cheat add coin
        else if ((matcher = GameMenuCheatCodeCommands.CheatAddCoin.getMatcher(input)) != null) {
            System.out.println(playerController.cheatAddCoin(matcher));
        }

        //player
        else if ((matcher = GameMenuCommands.Show_Coin.getMatcher(input)) != null) {
            System.out.println(playerController.showCoin());
        } else if ((matcher = GameMenuCommands.Show_Inventory.getMatcher(input)) != null) {
            System.out.println(playerController.showInventory());
        }


        //Cooking
        else if (isAtHome && (matcher = GameMenuCommands.PutFood.getMatcher(input)) != null){
            System.out.println(homeMenuController.putItemInRefrigerator(matcher.group("food")));
        } else if (isAtHome && (matcher = GameMenuCommands.PickFood.getMatcher(input)) != null){
            System.out.println(homeMenuController.pickItemInRefrigerator(matcher.group("food")));
        } else if (isAtHome && (matcher = GameMenuCommands.CookingRecipes.getMatcher(input)) != null){
            System.out.println(homeMenuController.showRecipeOfFood());
        } else if (isAtHome && (matcher = GameMenuCommands.PrepareFood.getMatcher(input)) != null){
            //System.out.println(homeMenuController.cooking(matcher.group("food")));
        } else if (isAtHome && (matcher = GameMenuCommands.EatFood.getMatcher(input)) != null){
            System.out.println(homeMenuController.eat(matcher.group("food")));
        }

        //crafting
        else if ((matcher = GameMenuCommands.ShowRecipes.getMatcher(input)) != null) {
            System.out.println(craft.showRecipes());

        } else if ((matcher = GameMenuCommands.Craft.getMatcher(input)) != null) {
            System.out.println("your available recipes:\n" + craft.showRecipes());
//            System.out.println(craft.craft(matcher.group("itemName")));

        }else if((matcher = GameMenuCommands.PlaceItem.getMatcher(input)) != null) {
            System.out.println(craft.placeItems(matcher.group("itemName"), matcher.group("direction")));

        }else if((matcher = GameMenuCommands.CheatAddItem.getMatcher(input)) != null) {
            System.out.println(craft.cheatAddItem(matcher.group("itemName"), Integer.parseInt(matcher.group("count"))));

        }else if((matcher = GameMenuCommands.LearnCraftingRecipe.getMatcher(input)) != null) {
            System.out.println(craft.learnCraftingRecipes(matcher.group("itemName")));
        }

    //Time and Date Commands
        else if ((matcher = GameMenuCommands.Time.getMatcher(input)) != null) {
            System.out.println(timeDate.getTime());

        }else if ((matcher = GameMenuCommands.Date.getMatcher(input)) != null) {
            System.out.println(timeDate.getDate());

        }else if((matcher = GameMenuCommands.DateTime.getMatcher(input)) != null ){
            System.out.println(timeDate.getDateTime());

        }else if((matcher = GameMenuCommands.DayOfWeek.getMatcher(input)) != null ){
            System.out.println(timeDate.getDayOfWeek());

        } else if ((matcher = GameMenuCommands.CheatAdvanceTime.getMatcher(input)) != null ) {
            System.out.println(timeDate.advanceTimeCheat(Integer.parseInt(matcher.group("amount"))));

        } else if ((matcher = GameMenuCommands.CheatAdvanceDate.getMatcher(input)) != null ) {
            System.out.println(timeDate.advanceDateCheat(Integer.parseInt(matcher.group("amount"))));

        }else if((matcher = GameMenuCommands.Season.getMatcher(input)) != null ){
            System.out.println(timeDate.getSeason());
        }


//Trade
 else if((matcher = GameMenuCommands.StartTrade.getMatcher(input)) != null){
        System.out.println(tradeController.startTrade()); }
 else if((matcher = GameMenuCommands.StartTrade.getMatcher(input)) != null){     System.out.println(tradeController);
    } else if((matcher = GameMenuCommands.StartTrade.getMatcher(input)) != null){
        System.out.println(tradeController.startTrade()); }
 else if((matcher = GameMenuCommands.StartTrade.getMatcher(input)) != null){     System.out.println(tradeController.startTrade());
    }

        //Shop
        else if ((matcher = GameMenuCommands.ShopBuildCoopBarn.getMatcher(input)) != null ){
                System.out.println(App.getActiveGame().getCarpentersShop().buildCoop_Barn(matcher.group("buildingName"), Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));

        }else if ((matcher = GameMenuCommands.BuyAnimal.getMatcher(input)) != null ){
            System.out.println(App.getActiveGame().getMarniesRanch().buyAnimal(matcher.group("animal"), matcher.group("name")));
        } else if ((matcher = GameMenuCommands.ShowAllProducts.getMatcher(input)) != null) {
            System.out.println(shopController.handleShowAllProducts());
        } else if ((matcher = GameMenuCommands.ShowAllAvailableProducts.getMatcher(input)) != null) {
            System.out.println(shopController.handleShowAvailableProducts());
        } else if ((matcher = GameMenuCommands.Purchase.getMatcher(input)) != null) {
            System.out.println(shopController.handleCommand(input, matcher));
        } else if ((matcher = GameMenuCommands.Sell.getMatcher(input)) != null) {
            System.out.println(shopController.handleSellProduct(matcher));
        }

        //Animal commands
        else if((matcher = GameMenuCommands.Pet.getMatcher(input)) != null ){
            System.out.println(animalController.pet(matcher.group("name")));
        }
        else if((matcher = GameMenuCommands.CheatSetAnimalFriendship.getMatcher(input)) != null ){
            System.out.println(animalController.cheatSetAnimalFriendship(matcher.group("animal"), Integer.parseInt(matcher.group("count"))));
        }
        else if((matcher = GameMenuCommands.ShowAnimalInfo.getMatcher(input)) != null ){
            System.out.println(animalController.showAnimalInfo());
        }
        else if((matcher = GameMenuCommands.ShepherdAnimals.getMatcher(input)) != null){
            System.out.println(animalController.shepherdAnimals(matcher.group("name"),
                    Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        }
        else if((matcher = GameMenuCommands.FeedHay.getMatcher(input)) != null){
            System.out.println(animalController.feedHay(matcher.group("name")));
        }
        else if((matcher = GameMenuCommands.AnimalProduces.getMatcher(input)) != null ){
            System.out.println(animalController.animalProduces());
        }
        //TODO
        /*
        else if((matcher = GameMenuCommands.CollectProduct.getMatcher(input)) != null){
            System.out.println(animalController.collectProduct(matcher.group("name")));
        }


         */
        else if((matcher = GameMenuCommands.SellAnimal.getMatcher(input)) != null){
            System.out.println(animalController.sellAnimal(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.Cheat_Set_Position.getMatcher(input)) != null) {
            System.out.println(playerController.cheatSetPos(matcher));
        }

        //Artisan commands
    else if((matcher = GameMenuCommands.ArtisanUse.getMatcher(input)) != null ){
        System.out.println(artisanController.use(matcher.group("artisanName"), matcher.group("itemsName")));
    }
    else if((matcher = GameMenuCommands.ArtisanGet.getMatcher(input)) != null){
        System.out.println(artisanController.get(matcher.group("artisanName")));
    }


        //fishing
        else if ((matcher = GameMenuCommands.Fishing.getMatcher(input)) != null){
            System.out.println(gadgetController.fishing(matcher.group("fishingPole")));
        }

        //gadget
        else if ((matcher = GadgetsCommands.EQUIP.getMatcher(input)) != null) {
            System.out.println(gadgetController.equip(matcher));
        } else if ((matcher = GadgetsCommands.AVAILABLE_TOOLS.getMatcher(input)) != null) {
            System.out.println(gadgetController.showAvailableTools());
        } else if ((matcher = GadgetsCommands.UPGRADE_TOOLS.getMatcher(input)) != null) {
            System.out.println(shopController.upgradeTool(matcher));
        } else if ((matcher = GadgetsCommands.USE_TOOL.getMatcher(input)) != null) {
            System.out.println(gadgetController.useTool(matcher));
        }
        // relation

        else if ((matcher = GameMenuCommands.Talk.getMatcher(input)) != null) {
            System.out.println(relationshipController.meet(matcher));
        } else if ((matcher = GameMenuCommands.TalkHistory.getMatcher(input)) != null) {
            System.out.println(relationshipController.showTalkHistory(matcher));
        } else if ((matcher = GameMenuCommands.Friendship.getMatcher(input)) != null) {
            System.out.println(relationshipController.showFriendship());
        } else if ((matcher = GameMenuCommands.AskMarriage.getMatcher(input)) != null) {
            System.out.println(relationshipController.askMarriage(matcher) != null);
        } else if ((matcher = GameMenuCommands.Show_Coin.getMatcher(input)) != null) {
         System.out.println(playerController.showCoin());
        } else if ((GameMenuCommands.GiftNPC.getMatcher(input)) != null) {
            System.out.println(relationshipController.giftNPC(matcher));
        } else if ((matcher = GameMenuCommands.Flower.getMatcher(input)) != null) {
            System.out.println(relationshipController.flower(matcher));
        } else if ((matcher = GameMenuCommands.Hug.getMatcher(input)) != null) {
            System.out.println(relationshipController.hug(matcher));
        } else if ((matcher = GameMenuCommands.FriendshipNPC.getMatcher(input)) != null) {
            System.out.println(relationshipController.meetNPC(matcher));
        } else if ((matcher = GameMenuCommands.Show_Notification.getMatcher(input)) != null) {
            System.out.println(relationshipController.showNotifications());
        } else if ((matcher = GameMenuCommands.Gift.getMatcher(input)) != null) {
            System.out.println(relationshipController.gift(matcher));
        } else if ((matcher = GameMenuCommands.Hug.getMatcher(input)) != null) {
            System.out.println(relationshipController.hug(matcher));
        } else if ((matcher = GameMenuCommands.Flower.getMatcher(input)) != null) {
            System.out.println(relationshipController.flower(matcher));
        } else if ((matcher = GameMenuCommands.GiftRate.getMatcher(input)) != null) {
            System.out.println(relationshipController.rateGift(matcher));
        } else if ((matcher = GameMenuCommands.GiftList.getMatcher(input)) != null) {
            System.out.println(relationshipController.showGiftList());
        }


        //cheat code
//        else if ((matcher = GameMenuCheatCodeCommands.AddSeed.getMatcher(input)) != null){
//            System.out.println(agricultureController.cheatAdd(matcher.group("seed")));
//        }
        else if ((matcher = GameMenuCheatCodeCommands.AddTool.getMatcher(input)) != null){
            System.out.println(cheatCodeController.addTool(matcher.group("tool")));
        } else if ((matcher = GameMenuCheatCodeCommands.AddFertilizer.getMatcher(input)) != null){
            System.out.println(cheatCodeController.addFertilizer(matcher.group("fertilizer")));
        } else if ((matcher = GameMenuCheatCodeCommands.ShowPosition.getMatcher(input)) != null){
            System.out.println(cheatCodeController.showPosition());
        } else if ((matcher = GameMenuCheatCodeCommands.AddIngredient.getMatcher(input)) != null){
            System.out.println(cheatCodeController.addIngredient(matcher.group("ingredient")));
        } else if ((matcher = GameMenuCheatCodeCommands.LearnRecipe.getMatcher(input)) != null){
            System.out.println(cheatCodeController.learnRecipe(matcher.group("recipe")));
        } else if ((matcher = GameMenuCheatCodeCommands.CookFood.getMatcher(input)) != null){
            System.out.println(cheatCodeController.cookFood(matcher.group("food")));
        } else if ((matcher = GameMenuCommands.Cheat_Set_Location.getMatcher(input)) != null) {
            System.out.println(playerController.cheatSetLocation(matcher));
        } else if ((matcher = GameMenuCommands.Cheat_Add_Flower.getMatcher(input)) != null) {
            System.out.println(playerController.cheatAddFlower());
        } else if ((matcher = GameMenuCommands.Cheat_Set_friendship.getMatcher(input)) != null) {
            System.out.println(playerController.setFriendship(matcher));
        }

        //ENERGY
        else if ((matcher = EnergyCommands.SHOW_ENERGY.getMatcher(input)) != null){
            System.out.println(energyController.show());
        } else if ((matcher = EnergyCommands.SET_ENERGY.getMatcher(input)) != null){
            System.out.println(energyController.setEnergy(matcher));
        } else if ((matcher = EnergyCommands.INVENTORY_SHOW.getMatcher(input)) != null){
            System.out.println(energyController.inventoryShow());
        } else if ((matcher = EnergyCommands.ENERGY_UNLIMITED.getMatcher(input)) != null){
            System.out.println(energyController.unlimitedEnergy());
        }

        //weather
       else if ((matcher = GameMenuCommands.Weather.getMatcher(input)) != null) {
            System.out.println(weatherController.showWeather());
        } else if ((matcher = GameMenuCommands.ChangeWeather.getMatcher(input)) != null) {
            System.out.println(weatherController.changeWeather(matcher));
        } else if ((matcher = GameMenuCommands.BuildGreenHouse.getMatcher(input)) != null) {
            System.out.println(weatherController.buildGreenhouse());
        } else if ((matcher = GameMenuCommands.Thor.getMatcher(input)) != null) {
            System.out.println(weatherController.applyFirelight(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        } else if ((matcher = GameMenuCommands.WeatherForecast.getMatcher(input)) != null) {
            System.out.println(weatherController.weatherForecast());
        }








        else{
            System.out.println("invalid command!");

        }
    }
}
