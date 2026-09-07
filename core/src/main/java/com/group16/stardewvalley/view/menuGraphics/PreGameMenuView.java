package com.group16.stardewvalley.view.menuGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.controller.menu.MainMenuController;
import com.group16.stardewvalley.controller.menu.ProfileMenuController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.view.graphics.GameScreen;

import java.util.ArrayList;

public class PreGameMenuView implements Screen {
    private Stage stage;
    private final Skin skin;
    private final GameMenuController controller;
    private final MapController mapController = new MapController();

    private final TextButton startNewGameButton;
    private final TextButton loadLastGameButton;
    private final TextButton existingGamesButton;
    private final TextButton endGameButton;
    private final TextButton backButton;

    private final Label feedbackLabel;

    public PreGameMenuView(GameMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;

        this.startNewGameButton = new TextButton("Start New Game", skin);
        this.loadLastGameButton = new TextButton("Load Last Game", skin);
        this.existingGamesButton = new TextButton("Existing Games", skin);
        this.endGameButton = new TextButton("End Current Game", skin);
        this.backButton = new TextButton("Back", skin);

        this.feedbackLabel = new Label("", skin);
        controller.setView(this); // if needed
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Background
        Texture bgTexture = new Texture(Gdx.files.internal("Background/mainBack.jpeg"));
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        // === Logo ===
        Texture logoTexture = new Texture(Gdx.files.internal("Background/Game-Menu.png"));
        Image logoImage = new Image(logoTexture);
        logoImage.setSize(logoTexture.getWidth() * 0.3f, logoTexture.getHeight() * 0.3f);



        // Root layout
        Table root = new Table();
        root.setFillParent(true);
        root.center().padTop(100); // shift buttons down so they don't overlap logo
        stage.addActor(root);

        // === Title Row ===
        Table titleTable = new Table();
        titleTable.center();
        titleTable.add(logoImage).center().padBottom(20).row();
        root.add(titleTable).colspan(2).center().padBottom(40).row();


        // Buttons column
        Table buttonColumn = new Table();
        buttonColumn.add(startNewGameButton).width(570).padBottom(20).row();
        buttonColumn.add(loadLastGameButton).width(570).padBottom(20).row();
        buttonColumn.add(existingGamesButton).width(570).padBottom(20).row();
        buttonColumn.add(endGameButton).width(570).padBottom(20).row();

        // Add button column to root
        root.add(buttonColumn).center().row();

// === Feedback label above back button ===
        Table feedbackTable = new Table();
        feedbackTable.setFillParent(true);
        feedbackTable.bottom().padBottom(80); // Lift it up from the bottom
        feedbackTable.add(feedbackLabel).center();
        stage.addActor(feedbackTable);

// === Back button in bottom-left ===
        Table backTable = new Table();
        backTable.setFillParent(true);
        backTable.bottom().left().pad(20);
        backTable.add(backButton).width(200);
        stage.addActor(backTable);


        // --- Listeners ---
        startNewGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                Result result = controller.newGame("TODO");
//                feedbackLabel.setText(result.toString());

                String[] users = new String[3];
                users[0] = "atena";
                users[1] = "david";
                users[2] = "daniel";
                ArrayList<Player> gamePlayers = new ArrayList<>();
                gamePlayers.add(new Player(App.getLoggedInUser()));
                for (String user : users) {
                    gamePlayers.add(new Player(new User(user, "ee", "ff", "a@gmail", "female")));

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
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(GameScreen.getGameScreen());
            }
        });

        loadLastGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = controller.loadGame();
                feedbackLabel.setText(result.toString());
            }
        });

        existingGamesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = controller.loadGame();
                feedbackLabel.setText(result.toString());
            }
        });

        endGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = controller.exit();
                feedbackLabel.setText(result.toString());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
