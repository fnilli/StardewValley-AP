
//package com.group16.stardewvalley;
//
//import com.badlogic.gdx.Game;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.group16.stardewvalley.controller.menu.StartMenuController;
//import com.group16.stardewvalley.model.graphics.GameAssetManager;
//import com.group16.stardewvalley.view.menuGraphics.StartMenuView;
//
//public class Main extends Game {
//    private static Main main;
//    private static SpriteBatch batch;
//
//    @Override
//    public void create() {
//        main = this;
//        batch = new SpriteBatch();
//        getMain().setScreen(new StartMenuView(new StartMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
//    }
//
//    @Override
//    public void render() {
//        super.render();
//    }
//
//    @Override
//    public void dispose() {
//        batch.dispose();
//    }
//
//    public static Main getMain() {
//        return main;
//    }
//
//    public static void setMain(Main main) {
//        Main.main = main;
//    }
//
//    public static SpriteBatch getBatch() {
//        return batch;
//    }
//
//    public static void setBatch(SpriteBatch batch) {
//        Main.batch = batch;
//    }
//}

package com.group16.stardewvalley;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.PlayerGraphics;
import com.group16.stardewvalley.model.tools.Gadget;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.view.graphics.GameScreen;

import java.util.ArrayList;

public class Main extends Game {
    GameMenuController controller = new GameMenuController();
    private final MapController mapController = new MapController();
    private static Main main;
    private static SpriteBatch batch;
    private GameScreen gameScreen;

    @Override
    public void create() {
//        for (Gadget gadget : App.getActiveGame().getCurrentPlayer().getInventory().getTools().keySet()) {
//            System.out.println(gadget.getName());
//        }
//        if (App.getActiveGame().getCurrentPlayer().getInventory().getTools().isEmpty()){
//            System.out.println("em");
//        }
        main = this;
        batch = new SpriteBatch();
        String[] users = new String[3];
        users[0] = "atena";
        users[1] = "david";
        users[2] = "daniel";

        App.setLoggedInUser(new User("Boss", "ee", "h", "a@gmail", "woman"));

        ArrayList<Player> gamePlayers = new ArrayList<>();
        gamePlayers.add(new Player(App.getLoggedInUser()));
        for (String user : users) {
            gamePlayers.add(new Player(new User(user, "ee", "ff", "a@gmail", "woman")));

        }
        com.group16.stardewvalley.model.app.Game newGame = new com.group16.stardewvalley.model.app.Game(new Player(App.getLoggedInUser()), gamePlayers);
        App.setActiveGame(newGame);
        App.games.add(newGame);

        String[] characterPaths = {
            "Character/maidnpc.png",
            "Character/gardenernpc.png",
            "Character/woman_016_npc.png",
            "Character/man_002_npc.png"
        };

        for (Player player : App.getActiveGame().getPlayers()) {
            controller.chooseFarm(player, "2");
        }
        mapController.createMap();
        int index = 0;
        for (Player player : App.getActiveGame().getPlayers()) {
            player.setPlayerGraphics(characterPaths[index], 48, 64);
            index++;
        }
        gameScreen = GameScreen.getGameScreen();

        setScreen(gameScreen);
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }



    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static Main getMain() {
        return main;
    }


    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }
}
