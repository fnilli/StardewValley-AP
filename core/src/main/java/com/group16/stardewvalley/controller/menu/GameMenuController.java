package com.group16.stardewvalley.controller.menu;

import com.group16.stardewvalley.model.app.*;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.menu.GameMenuCommands;
import com.group16.stardewvalley.model.menu.Menu;
import com.group16.stardewvalley.model.map.Farm;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.model.map.FarmType;
import com.group16.stardewvalley.view.menuGraphics.PreGameMenuView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static com.group16.stardewvalley.model.user.User.getUserByUsername;


public class GameMenuController {
    private PreGameMenuView view;


    public void setView(PreGameMenuView view) {
        this.view = view;
    }




    public Result newGame(String input){
        if(input == null || input.isEmpty()){
            return new Result(false, "empty usernames!");
        }

        String[] users = input.split("\\s+");

        if(users.length > 3){
            return new Result(false, "too many usernames!");
        }

        for (String username : users) {
            if (GameMenuCommands.Username.getMatcher(username) == null) {
                return new Result(false, "invalid username format!");
            }
            User user = getUserByUsername(username);
            if(user == null){
                return new Result(false, "user not found!");
            }
            if(user.getHasActiveGame()){
                return new Result(false, "username already in an active game!");
            }
        }

        ArrayList<Player> gamePlayers = new ArrayList<>();
        gamePlayers.add(new Player(App.getLoggedInUser()));
        for (String user : users) {
            gamePlayers.add(new Player(getUserByUsername(user)));
        }

        Game newGame = new Game(new Player(App.getLoggedInUser()), gamePlayers);

        App.games.add(newGame);


        return new Result(true, "new game created! now choose your farm in turn.");
    }

//بازیکن ها بصورت نوبتی و همه از یک سیستم مزرعه ی خود را انتخاب میکنند
    public Result chooseFarm(Player player, String farmNumber){
        Game game = App.getActiveGame();

        if (farmNumber.matches("\\d+")) {
            int farmNum = Integer.parseInt(farmNumber);

            return switch (farmNum) {
                case 1 -> {
                    player.setFarm(new Farm(FarmType.small));
                    randomItems(player.getFarm());
                    yield new Result(true, "small farm has been chosen!");
                }
                case 2 -> {
                    player.setFarm(new Farm(FarmType.big));
                    randomItems(player.getFarm());
                    yield new Result(true, "big farm has been chosen!");
                }
                default -> new Result(false, "farm number must be between 1 and 2");
            };
        } else {
            return new Result(false, "invalid farm number!");
        }
    }

    public void randomItems(Farm farm) {
        Random random = new Random();
        int totalTiles = farm.getType().getHeight() * farm.getType().getWidth();

        // موقعیت کلبه
        Pos cottageStart = farm.getCottageStart();
        int cottageX = cottageStart.getX();
        int cottageY = cottageStart.getY();

        int itemCount = (random.nextInt(totalTiles / 5) + totalTiles / 20);

        for (int k = 0; k < itemCount; k++) {
            int i = random.nextInt(farm.getType().getWidth());
            int j = random.nextInt(farm.getType().getHeight());

            if (isNearCottage(i, j, cottageX, cottageY)) {
                k--;
                continue;
            }

            if (farm.getType().getTiles()[j][i].equals(TileType.Ground)) {
                farm.getType().getTiles()[j][i] = TileType.Tree;
            } else if (farm.getType().getTiles()[j][i].equals(TileType.Quarry)) {
                farm.getType().getTiles()[j][i] = TileType.Stone;
            }
        }

        for (int k = 0; k < itemCount / 4; k++) {
            int i = random.nextInt(farm.getType().getWidth());
            int j = random.nextInt(farm.getType().getHeight());

            if (isNearCottage(i, j, cottageX, cottageY)) {
                k--;
                continue;
            }

            if (farm.getType().getTiles()[j][i] == TileType.Ground) {
                farm.getType().getTiles()[j][i] = TileType.Rock;
            } else if (farm.getType().getTiles()[j][i].equals(TileType.Quarry)) {
                farm.getType().getTiles()[j][i] = TileType.MineralForage;
            }
        }
    }

    private boolean isNearCottage(int x, int y, int cottageX, int cottageY) {
        // محدوده‌ی ممنوعه: بافر 1 تایل اطراف کلبه 4x4 → کل محدوده: 6x6
        return (x >= cottageX - 1 && x <= cottageX + 4) &&
            (y >= cottageY - 1 && y <= cottageY + 4);
    }


    public Result loadGame(){
//        if(App.getActiveGame() == null){
//            return new Result(false, "no active game!");
//        }
//        Game game = App.getActiveGame();
        GameData loaded = LoadManager.load("savefile.json");
        if (loaded != null) {
//            System.out.println("Welcome back, " + loaded.user.getUsername());
            return new Result(true, "Welcome back, " + loaded.user.getUsername());
        }
        Game game = App.getActiveGame();
        game.setLoader(game.getCurrentPlayer());

        loaded = LoadManager.load("savefile.json");
        if (loaded != null) {
            System.out.println("Welcome back, " + loaded.user.getUsername());
        }

        return new Result(true, game.getCurrentPlayer().getUser().getUsername() + " loaded the game successfully!");

    }

    public Result exit(){
        Game game = App.getActiveGame();
        if(game.getLoader() != null && game.getCurrentPlayer().getUser().getUsername().equals(game.getLoader().getUser().getUsername())){
            //TODO save game : done
            Game currentGame = App.getActiveGame();
            GameData data = new GameData(currentGame.getCurrentPlayer().getUser(), currentGame.getCurrentPlayer(), currentGame);
            SaveManager.save(data, "savefile.json");

            App.setCurrentMenu(Menu.ExitMenu);
            return new Result(true, "bye bye");

        }
        if(game.getCurrentPlayer().getUser().getUsername().equals(game.getCreator().getUser().getUsername())){
            //TODO save game : done
            Game currentGame = App.getActiveGame();
            GameData data = new GameData(currentGame.getCurrentPlayer().getUser(), currentGame.getCurrentPlayer(), currentGame);
            SaveManager.save(data, "savefile.json");

            App.setCurrentMenu(Menu.ExitMenu);
            return new Result(true, "bye bye");
        }
        return new Result(false, "wrong user entered exit command. try again!");
    }

    public Result forceTerminateGame(Map<Player, Boolean>votes){
        Game game = App.getActiveGame();

        boolean result = true;
        for(boolean vote : votes.values()){
            result = result & vote;
        }

        if(result){
            //terminating won the election

//            App.setActiveGame(null);
            App.setCurrentMenu(Menu.GameMenu);
            return new Result(true, "terminated game!");
            //TODO; how to delete the game

        }else{
            //terminating did not win the election
            return new Result(false, "terminating the game did not win the election!");
        }


    }

    public Result showCurrentMenu(){
        return new Result(true, App.getCurrentMenu().getName());
    }


//next turn have been handled in Game class


    public Result exitMenu(){
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "you are in the main menu!");
    }


    public Result changeMenu(String menuName){
        return switch (menuName) {
            case "Home Menu" -> {
                App.setCurrentMenu(Menu.HomeMenu);
                yield new Result(true, "you are in the home menu!");
            }

            default -> new Result(false, "wrong menu name!");
        };
    }

    public Result showHomeMenus() {
        String output = "you can do these from Home menu:\n1- Crafting\n2- Cooking\n3- ";
        return new Result(true, output);
    }
}
