package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.GameController;
import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.Tree;
import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.crafting.CraftItem;
import com.group16.stardewvalley.model.crafting.Crafting;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.graphics.TileRenderer;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.map.*;
import com.group16.stardewvalley.model.map.TileTextureManager;
import com.group16.stardewvalley.model.shops.Building;
import com.group16.stardewvalley.model.shops.CarpentersShop;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.tools.Gadget;
import com.group16.stardewvalley.view.menuGraphics.PreGameMenuView;



import static com.group16.stardewvalley.controller.map.MapController.isPlayerInsidePlace;
import static com.group16.stardewvalley.controller.menu.HomeMenuController.findIngredient;
import static com.group16.stardewvalley.model.crafting.Crafting.pendingCraftItemName;
import static com.group16.stardewvalley.view.graphics.CarpenterMenu.pendingBuildingName;

public class GameScreen implements Screen, InputProcessor {
    public static GameScreen gameScreen;
    private GameController controller;
    private SpriteBatch batch;
    private TileTextureManager textureManager;
    public static OrthographicCamera camera;
    private Viewport viewport;
    TileRenderer tileRenderer;
    public static float totalGameTime = 0f;
    private static int tenMinuteCounter = 0;
    private static float oneHourGameTime = 60f;
    public static boolean showMiniMap = false;
    private OrthographicCamera miniMapCamera;
    private Viewport miniMapViewport;
    private ShopMenuManager shopMenuManager;
    private CarpentersShop carpentersShop = new CarpentersShop();
    private Crafting craftingController = new Crafting();
    Stage uiStage = new Stage(new ScreenViewport());

    private Skin skin = GameAssetManager.getGameAssetManager().getSkin();
    Table toolTable = new Table(skin);
    private Stage stage;

    private Table pauseMenu;
    private boolean isPaused = false;
    private GameHUD gameHUD;


    public static final int TILE_SIZE = 17;
    private static boolean showTools;
    private static boolean showInventory;
    private Stage toolStage;
    private Stage inventoryStage;
    private boolean inventoryBuilt = false;

    private GameScreen() {
        this.controller = new GameController();
        camera = new OrthographicCamera();
        viewport = new FillViewport(30 * TILE_SIZE, 20 * TILE_SIZE, camera);
        viewport.apply();
        miniMapCamera = new OrthographicCamera();
        int mapPixelWidth = App.getActiveGame().getMapWidth() * TILE_SIZE;
        int mapPixelHeight = App.getActiveGame().getMapHeight() * TILE_SIZE;

        miniMapViewport = new FillViewport(mapPixelWidth, mapPixelHeight, miniMapCamera);
        miniMapViewport.apply();
        toolTable.top().left().pad(10);
        toolTable.setFillParent(true);
        toolStage = new Stage(new ScreenViewport());
        toolStage.addActor(toolTable);
        inventoryStage = new Stage(new ScreenViewport());
    }

    public static GameScreen getGameScreen(){
        if (gameScreen == null){
            gameScreen = new GameScreen();
        }
        return gameScreen;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        batch = Main.getBatch();
        textureManager = new TileTextureManager();
        tileRenderer = new TileRenderer();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(toolStage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

//        Gdx.input.setInputProcessor(new InputMultiplexer(this, stage = new Stage()));

        gameHUD = new GameHUD();


        shopMenuManager = new ShopMenuManager(GameScreen.getGameScreen().getStage(), GameAssetManager.getGameAssetManager().getSkin());



        // === Pause Button ===
//        TextButton pauseButton = new TextButton("Pause", GameAssetManager.getGameAssetManager().getSkin());
//        pauseButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                togglePause();
//            }
//        });
//
//        Table topLeft = new Table();
//        topLeft.top().left().setFillParent(true);
//        topLeft.add(pauseButton).pad(10).padLeft(50).padTop(50);
//        stage.addActor(topLeft);
//
//        createPauseMenu();
    }

    public void toggleShowTools() {
        showTools = !showTools;
        if (showTools) {
            buildToolTable();
        }
    }

    public void toggleShowInventory() {
        showInventory = !showInventory;
        if (!showInventory) {
            App.getActiveGame().getCurrentPlayer().getInventory().removeInventory();
            inventoryBuilt = false;
        }
    }

    @Override
    public void render(float delta) {
//        if (!isPaused) {
//            controller.update(delta);
//            updateAnimals(delta);
//            handleTurn();
//        }
        controller.update(delta);
        Location currentLocation = App.getActiveGame().getCurrentPlayer().getLocationLocation();
        if (currentLocation != null && currentLocation.isShop()) {
            graphicDisplayOfStores(currentLocation);
        }
        handleTurn();


        // Choose camera
        if (App.getActiveGame().getCurrentPlayer().isAtHome()) {
            setCameraForHouse();
        }
        else if (showMiniMap) {
            setCameraForMiniMap();
        } else {
            setCameraForMap();
        }

        // Clear screen
        Gdx.gl.glClearColor(0.2f, 0.5f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // === Draw game world ===
        batch.setProjectionMatrix((showMiniMap ? miniMapCamera : camera).combined);
        batch.begin();
        controller.render(delta);
        batch.end();

        // === Draw GameHUD in screen space and scaled ===
        batch.setProjectionMatrix(stage.getCamera().combined); // Switch to screen-space
        batch.begin();


        // Apply a scaling transform (e.g., 2x size)
        Matrix4 originalTransform = batch.getTransformMatrix().cpy();
        Matrix4 scaled = new Matrix4().setToScaling(2f, 2f, 1f); // Scale 2x
        batch.setTransformMatrix(scaled);

        // Adjust HUD position because scaling affects it (divide by scale)
        float scale = 2f;
        float hudX = (Gdx.graphics.getWidth() - 170) / scale;
        float hudY = (Gdx.graphics.getHeight() - 130) / scale;
        float barx = (Gdx.graphics.getWidth() - 35 - 15) / scale;
        float bary = 30 / scale;

        gameHUD.render(batch, hudX, hudY, barx, bary);

        // Restore original transform
        batch.setTransformMatrix(originalTransform);
        batch.end();
        // === Draw stage UI ===
        if (showTools) {
            App.getActiveGame().getCurrentPlayer().getInventory().showTools(stage, new Skin(Gdx.files.internal("skin.json")));
        }

        if (showInventory) {
            if (!inventoryBuilt) {
                App.getActiveGame().getCurrentPlayer().getInventory().showInventory(stage, new Skin(Gdx.files.internal("skin.json")), controller.getDragAndDrop());
                inventoryBuilt = true;
            }
        }

        stage.act(delta);
        stage.draw();

    }


    private void setCameraForHouse() {
        camera.position.set(camera.viewportWidth + 200, camera.viewportHeight + 100, 0);
        camera.zoom = 3.5f;

        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }


    private void setCameraForMiniMap() {
        miniMapCamera.position.set(
            App.getActiveGame().getMapWidth() * TILE_SIZE / 2f,
            App.getActiveGame().getMapHeight() * TILE_SIZE / 2f,
            0
        );
        miniMapCamera.update();
        batch.setProjectionMatrix(miniMapCamera.combined);
    }

    private static void handleTurn() {
        totalGameTime += Gdx.graphics.getDeltaTime();

        if (totalGameTime >= oneHourGameTime / 6f) {
            TimeDate.getInstance(App.getActiveGame()).advanceTenMinutes();

            tenMinuteCounter++;
            Main.getMain().getGameScreen().getStage().clear();
            totalGameTime = 0f;

            if (tenMinuteCounter >= 6) {
                App.getActiveGame().nextTurn();
                tenMinuteCounter = 0;
            }
        }
    }

    private void setCameraForMap() {
        float viewportWidth = camera.viewportWidth;
        float viewportHeight = camera.viewportHeight;

        float mapPixelHeight = App.getActiveGame().getMapHeight() * TILE_SIZE;
        float mapPixelWidth = App.getActiveGame().getMapWidth() * TILE_SIZE;

        float cameraX = MathUtils.clamp(
            App.getActiveGame().getCurrentPlayer().getX() * TILE_SIZE,
            viewportWidth / 2f,
            mapPixelWidth - viewportWidth / 2f);

        float cameraY = MathUtils.clamp(
            App.getActiveGame().getCurrentPlayer().getY() * TILE_SIZE,
            viewportHeight / 2f,
            mapPixelHeight - viewportHeight / 2f);

        camera.position.set(cameraX, cameraY, 0);
        camera.zoom = 1f;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }


    @Override public void dispose() {
        batch.dispose();
        stage.dispose();
        gameHUD.dispose();


        textureManager.dispose();
        for (Player player : App.getActiveGame().getPlayers()) {
            player.getPlayerGraphics().dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    public void graphicDisplayOfStores(Location shop) {
        Texture storeTexture = null;
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        switch (shop) {
            case JojaMart :
                storeTexture = new Texture(Gdx.files.internal("Shops/JojaMart.png"));
                break;
            case Blacksmith:
                storeTexture = new Texture(Gdx.files.internal("Shops/Blacksmith.png"));
                break;
            case PierresGeneralStore :
                storeTexture = new Texture(Gdx.files.internal("Shops/PierresGeneralStore.png"));
                break;
            case FishShop:
                storeTexture = new Texture(Gdx.files.internal("Shops/FishShop.png"));
                break;
            case MarniesRanch:
                storeTexture = new Texture(Gdx.files.internal("Shops/MarniesRanch.png"));
                break;
            case CarpentersShop:
                storeTexture = new Texture(Gdx.files.internal("Shops/CarpentersShop.png"));
                break;
            default:
                return;
        }

        float storeDrawX = currentPlayer.getX() * TILE_SIZE;
        float storeDrawY = (currentPlayer.getY() + 1) * TILE_SIZE;
        batch.draw(storeTexture, storeDrawX, storeDrawY, TILE_SIZE * 4, TILE_SIZE * 4);


    }

    @Override
    public boolean keyDown(int keycode) {
        return controller.keyDown(keycode); // this will now handle both continuous and one-time keys
    }

    @Override
    public boolean keyUp(int keycode) {
        return controller.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT) {
            handleRightClick(screenX, screenY);
            return true;
        } else if (button == Input.Buttons.LEFT) {
            controller.handleLeftClick(screenX, screenY);
            return true;
        }
        return false;
    }



    private void handleRightClick(int screenX, int screenY) {
        Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
        int tileX = (int) worldCoordinates.x / TILE_SIZE;
        int tileY = (int) worldCoordinates.y / TILE_SIZE;
        float clickX = worldCoordinates.x;
        float clickY = worldCoordinates.y;
        //tileX and tileY are the coordination which user clicked on it

        Player player = App.getActiveGame().getCurrentPlayer();
        int playerX = player.getX();
        int playerY = player.getY();

        int dx = Math.abs(playerX - tileX);
        int dy = Math.abs(playerY - tileY);
        boolean isAdjacent = (dx <= 1 && dy <= 1) && (dx + dy > 0);

        for (Building building : App.getActiveGame().getBuildings()) {
            for (Animal animal : building.getBuildingAnimals()) {
                float size = TILE_SIZE;
                if (clickX >= animal.getPixelX() && clickX <= animal.getPixelX() + size &&
                    clickY >= animal.getPixelY() && clickY <= animal.getPixelY() + size) {

                    AnimalMenu menu = new AnimalMenu(skin, animal);
                    menu.setPosition(
                        (stage.getWidth() - menu.getWidth()) / 2,
                        (stage.getHeight() - menu.getHeight()) / 2
                    );
                    stage.addActor(menu);
                    return;
                }
            }
        }

        //  Check CraftItems for ArtisanMenu
        Tile clickedTile = App.getActiveGame().getMap()[tileY][tileX];
        if (clickedTile.getItem() instanceof CraftItem craftItem && clickedTile.isBuildingOrigin()) {
            if (isAdjacent) {
                ArtisanMenu artisanMenu = new ArtisanMenu(skin, craftItem, controller.getDragAndDrop(), stage);
                artisanMenu.setPosition(
                    (stage.getWidth() - artisanMenu.getWidth()) / 2,
                    (stage.getHeight() - artisanMenu.getHeight()) / 2
                );
                stage.addActor(artisanMenu);
                return;
            }
        }


        Tree tree = App.getActiveGame().getMap()[tileY][tileX].getTree();
        if (isAdjacent && tree != null) {
            if (tree.HasFruit()) {
                tree.handpickFruit();
                String fruitName = tree.getTreeType().getFruitName().toUpperCase().replace(" ", "_");
                Ingredient ingredient = findIngredient(fruitName);
                if (ingredient != null) {
                    Result result = player.getInventory().addItem(new FoodIngredient(fruitName, tree.getFruitSellPrice(), ingredient), 4);
                }
            }
        }
        else if (App.getActiveGame().getMap()[tileY][tileX].getType().equals(TileType.Cottage) &&
            !player.isAtHome()) {
            player.setHomeMap(new HomeMap(player));
            player.setAtHome(true);
        } else if (player.isAtHome()) {
            player.setAtHome(false);
        }

        if (pendingBuildingName != null) {
            // Attempt to place building here
            Result buildResult = carpentersShop.buildCoop_Barn(pendingBuildingName, tileX, tileY);
            if (buildResult.isSuccessful()) {
                System.out.println(buildResult.toString());
                pendingBuildingName = null;  // Clear pending building after success
            } else {
                System.out.println("Cannot place building here: " + buildResult.toString());
            }
            return; // Skip other right-click logic during building mode
        }

        if (pendingCraftItemName != null) {
            Result craftResult = craftingController.craft(pendingCraftItemName, tileX, tileY);
            if (craftResult.isSuccessful()) {
                System.out.println(craftResult.toString());
                pendingCraftItemName = null; // clear pending
            } else {
                System.out.println("Cannot place crafted item here: " + craftResult.toString());
            }
            return; // skip other click logic
        }


        if (isPlayerInsidePlace(player, PlaceType.CarpentersShop)) {
            if (shopMenuManager.isMenuOpenFor(PlaceType.CarpentersShop)) {
                shopMenuManager.closeMenu();
            } else {
                shopMenuManager.openMenu(PlaceType.CarpentersShop);
            }
        }
        if (isPlayerInsidePlace(player, PlaceType.MarniesRanch)) {
            if (shopMenuManager.isMenuOpenFor(PlaceType.MarniesRanch)) {
                shopMenuManager.closeMenu();
            } else {
                shopMenuManager.openMenu(PlaceType.MarniesRanch);
            }
        }
    }



    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    private void createPauseMenu() {
        pauseMenu = new Table();
        pauseMenu.setFillParent(true);
        pauseMenu.center();

        TextButton continueButton = new TextButton("Continue", skin);
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                togglePause(); // resume game
            }
        });

        TextButton mainMenuButton = new TextButton("Main Menu", skin);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();

                // ✅ Dispose current screen and switch to PreGameMenuView
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new PreGameMenuView(
                    new GameMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin()
                ));
            }
        });

        pauseMenu.add(new Label("Game Paused", skin)).padBottom(20).row();
        pauseMenu.add(continueButton).width(250).pad(10).row();
        pauseMenu.add(mainMenuButton).width(250).pad(10).row();

        pauseMenu.setVisible(false);
        stage.addActor(pauseMenu);
    }


    private void togglePause() {
        isPaused = !isPaused;
        pauseMenu.setVisible(isPaused);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void updateAnimals(float delta) {
        for (Building building : App.getActiveGame().getBuildings()) {
            for (Animal animal : building.getBuildingAnimals()) {
                animal.update(delta);
            }
        }
    }


    private void buildToolTable() {
        toolTable.clear();

        for (Gadget tool : App.getActiveGame().getCurrentPlayer().getInventory().getTools().keySet()) {
            Texture texture = GameAssetManager.getGameAssetManager().getItemTexture(tool);
            Image image = new Image(texture);
            toolTable.add(image).pad(10);
        }
    }

    public GameController getController() {
        return controller;
    }
}

