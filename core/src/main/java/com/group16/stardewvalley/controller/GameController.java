package com.group16.stardewvalley.controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.agriculture.AgricultureController;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.controller.menu.HomeMenuController;
import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.Seed;
import com.group16.stardewvalley.model.agriculture.Seeds;
import com.group16.stardewvalley.model.agriculture.Tree;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.*;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.map.Direction;
import com.group16.stardewvalley.model.map.HomeMap;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.tools.Hoe;
import com.group16.stardewvalley.model.tools.Scythe;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.view.graphics.CookingMenu;
import com.group16.stardewvalley.view.graphics.CraftMenu;
import com.group16.stardewvalley.view.graphics.GameScreen;
import com.group16.stardewvalley.view.graphics.FridgeMenu;

import static com.group16.stardewvalley.controller.menu.HomeMenuController.findIngredient;
import static com.group16.stardewvalley.view.graphics.GameScreen.*;

public class GameController {
    private final PlayersController playersController;
    private final MapController mapController;
    private final AgricultureController agricultureController;
    private final HomeMenuController homeMenuController;
    private CookingMenu cookingMenu;
    private boolean isCookingMenuOpen = false;
    private CraftMenu craftMenu;
    private boolean isCraftingMenuOpen = false;
    public static final float ENERGYSCALE = 0.0005f;
    private boolean upPressed, downPressed, leftPressed, rightPressed;

    private FridgeMenu fridgeMenu;
    private boolean isFridgeMenuOpen = false;

    private final DragAndDrop dragAndDrop = new DragAndDrop();


    public GameController() {
        this.agricultureController = new AgricultureController();
        this.playersController = new PlayersController();
        this.mapController = new MapController();
        this.homeMenuController = new HomeMenuController();
    }

    public void update(float delta) {
        if (isCookingMenuOpen) return;

        handleContinuousMovement(delta);
        playersController.update(delta);
    }



    public void render(float delta) {
        Player player = App.getActiveGame().getCurrentPlayer();
        if (player.isAtHome()){
            player.getHomeMap().render(Main.getBatch());
        } else {
            mapController.drawMap(Main.getBatch(), delta);
        }

        playersController.render();

        drawPlayerBuff();

    }

    private void drawPlayerBuff() {
        Player player = App.getActiveGame().getCurrentPlayer();
        BuffType buff = player.getBuffer();

        if (buff != BuffType.NONE) {
            Texture buffTexture = GameAssetManager.getGameAssetManager().getTexture(buff.getTexturePath());

            // موقعیت نمایش: وسط بالای صفحه
            float x = 20;
            float y = Gdx.graphics.getHeight() - 84;  // از بالا 64 پیکسل پایین‌تر، با فاصله


            Main.getBatch().draw(buffTexture, x, y, 64, 64);
        }
    }

    public boolean handleInput(int keycode) {
        Player player = App.getActiveGame().getCurrentPlayer();

        float nextX = player.getX();
        float nextY = player.getY();
        boolean up = false, down = false, left = false, right = false;
        float speed = player.getSpeed();

        switch (keycode) {
            case Input.Keys.NUM_0:
            case Input.Keys.NUM_1:
            case Input.Keys.NUM_2:
            case Input.Keys.NUM_3:
            case Input.Keys.NUM_4:
            case Input.Keys.NUM_5:
            case Input.Keys.NUM_6:
            case Input.Keys.NUM_7:
            case Input.Keys.NUM_8:
            case Input.Keys.NUM_9:
                GameScreen.getGameScreen().toggleShowInventory();
                return true;
            case Input.Keys.UP:
                player.setCurrentDirection(Direction.UP);
                nextY += speed;
                up = true;
                break;
            case Input.Keys.RIGHT:
                player.setCurrentDirection(Direction.RIGHT);
                nextX += speed;
                right = true;
                break;
            case Input.Keys.DOWN:
                player.setCurrentDirection(Direction.DOWN);
                nextY -= speed;
                down = true;
                break;
            case Input.Keys.LEFT:
                player.setCurrentDirection(Direction.LEFT);
                nextX -= speed;
                left = true;
                break;

                //MOVEMENT-2
            case Input.Keys.W:
                player.setCurrentDirection(Direction.UP);
                nextY += speed;
                up = true;
                break;
            case Input.Keys.D:
                player.setCurrentDirection(Direction.RIGHT);
                nextX += speed;
                right = true;
                break;
            case Input.Keys.S:
                player.setCurrentDirection(Direction.DOWN);
                nextY -= speed;
                down = true;
                break;
            case Input.Keys.A:
                player.setCurrentDirection(Direction.LEFT);
                nextX -= speed;
                left = true;
                break;


            case Input.Keys.M:
                //minimize and maximize map
                showMiniMap = !showMiniMap;
                return true;

            case Input.Keys.V:
                int targetY = player.getPosition().getY() + player.getCurrentDirection().getyDelta();
                int targetX = player.getPosition().getX() + player.getCurrentDirection().getxDelta();
                if (targetX < 0 || targetY < 0 || targetX > App.getActiveGame().getMapWidth() || targetY > App.getActiveGame().getMapHeight()) {
                    return false;
                }
                Tile targetTile = App.getActiveGame().getMap()[targetY][targetX];
                if (player.getCurrentEquipment() != null) {
                    Result resultOfUsingGadget = player.getCurrentEquipment().use(targetTile, App.getActiveGame());
                    System.out.println(resultOfUsingGadget);
                }
                if (player.getCurrentThing() != null) {
                    Item item = player.getCurrentThing();
                    if (item instanceof Seed seed) {
                        Result result = agricultureController.planting(seed, targetX, targetY);
                        if (!result.isSuccessful()) {
                            System.out.println(result);
                        }
                    }
                    else targetTile.setItem(player.getCurrentThing());
                    player.setCurrentThing(null);
                } else {
                    if (targetTile.getItem() != null) {
                        player.setCurrentThing(targetTile.getItem());
                        targetTile.setItem(null);
                    }
                }
                return true;
            case Input.Keys.C:
                //cooking menu
                if (player.isAtHome()) {
                    if (!isCookingMenuOpen) {
                        cookingMenu = new CookingMenu(
                                GameAssetManager.getGameAssetManager().getSkin(),
                                App.getActiveGame().getCurrentPlayer().getKnownRecipes()
                        );
                        GameScreen.getGameScreen().getStage().addActor(cookingMenu);
                        GameScreen.getGameScreen().getStage().addActor(cookingMenu.getTooltip());
                        isCookingMenuOpen = true;
                    } else {
                        cookingMenu.remove();
                        cookingMenu = null;
                        isCookingMenuOpen = false;
                    }
                }

                return true;
            case Input.Keys.B:
                //Crafting menu
                if (!isCraftingMenuOpen) {
                    craftMenu = new CraftMenu(
                        GameAssetManager.getGameAssetManager().getSkin(),
                        App.getActiveGame().getCurrentPlayer().getInventory().getCraftingRecipes());
                    GameScreen.getGameScreen().getStage().addActor(craftMenu);
                    GameScreen.getGameScreen().getStage().addActor(craftMenu.getTooltip());
                    isCraftingMenuOpen = true;
                } else {
                    craftMenu.remove();
                    craftMenu = null;
                    isCraftingMenuOpen = false;
                }
                return true;
            case Input.Keys.CAPS_LOCK:
                //next turn cheat code
                App.getActiveGame().nextTurn();
                return true;
            case Input.Keys.G:
                //energy cheat code +200
                App.getActiveGame().getCurrentPlayer().increaseEnergy(200);
                return true;
            case Input.Keys.X:
                if (player.getCurrentEquipment() == null) {
                    player.equip(new Hoe("hoe",0, "base"));
                } else {
                    player.equip(null);
                    player.setCurrentThing(Seeds.MIXED_SEED);
                }
                return true;
            case Input.Keys.Y:
                player.equip(new Scythe("scythe", 0, "base"));
                return true;
            case Input.Keys.E:
            case Input.Keys.ESCAPE:
                GameScreen.getGameScreen().toggleShowInventory();
                return true;
            case Input.Keys.F:
                System.out.println(agricultureController.fertilizePlant("speed gro", "up"));
                return true;
            case Input.Keys.TAB:
                App.getActiveGame().getTimeDate().advanceDateCheat(1);
                TimeDate.getInstance(App.getActiveGame()).advanceDateCheat(1);
                return true;
            case Input.Keys.O:
                if (player.getCurrentThing() != null &&
                    player.getCurrentThing() instanceof Food food) {
                    Result result = homeMenuController.eat(food.getName());
                    if (result.isSuccessful()) {
                        GameAssetManager.getGameAssetManager().getEatingSound().play();
                        showEatEffect(food.getName(), food.getBuff().getDescription(), food.getEnergy());
                        player.getPlayerGraphics().startEating();
                    }
                    return true;
                }
                return false;
            case Input.Keys.T:
                GameScreen.getGameScreen().toggleShowTools();
                return true;
            case Input.Keys.P:
                player.learnRecipe(FoodFactory.pizza());
                return true;
            case Input.Keys.F4:
                return true;
            default:
                return false;
        }

        if (player.isAtHome()) {
            return false;
        }

        Result result = mapController.walk((int) nextX, (int) nextY);
        if (result.isSuccessful()) {
            playersController.move(player, speed, up, down, left, right);
            player.decreaseEnergy(player.getEnergy() * ENERGYSCALE);
            return true;
        }
        System.out.println(result.message());
        if (result.message().contains("You fainted")) {
            player.setFaintStatus(true);
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                upPressed = true;
                return true;
            case Input.Keys.S:
                downPressed = true;
                return true;
            case Input.Keys.A:
                leftPressed = true;
                return true;
            case Input.Keys.D:
                rightPressed = true;
                return true;
        }
        return handleInput(keycode); // Still handle one-time actions
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                upPressed = false;
                return true;
            case Input.Keys.S:
                downPressed = false;
                return true;
            case Input.Keys.A:
                leftPressed = false;
                return true;
            case Input.Keys.D:
                rightPressed = false;
                return true;
        }
        return false;
    }


    private void handleContinuousMovement(float delta) {
        Player player = App.getActiveGame().getCurrentPlayer();
        float speed = player.getSpeed();

        float nextX = player.getX();
        float nextY = player.getY();

        boolean moved = false;
        if (upPressed) {
            player.setCurrentDirection(Direction.UP);
            nextY += speed;
            moved = true;
        }
        if (downPressed) {
            player.setCurrentDirection(Direction.DOWN);
            nextY -= speed;
            moved = true;
        }
        if (leftPressed) {
            player.setCurrentDirection(Direction.LEFT);
            nextX -= speed;
            moved = true;
        }
        if (rightPressed) {
            player.setCurrentDirection(Direction.RIGHT);
            nextX += speed;
            moved = true;
        }

        if (moved && !player.isAtHome()) {
            Result result = mapController.walk((int) nextX, (int) nextY);
            if (result.isSuccessful()) {
                playersController.move(player, speed, upPressed, downPressed, leftPressed, rightPressed);
                player.decreaseEnergy(player.getEnergy() * ENERGYSCALE);
            } else if (result.message().contains("You fainted")) {
                player.setFaintStatus(true);
            }
        }
    }


    public void showEatEffect(String foodName, String buff, int energy) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.YELLOW;

        String message = " Ate " + foodName + "\n+" + energy + " Energy";

        if (!buff.isEmpty()) {
            message += "\n" + buff + " Buff Activated!";
        }

        final Label label = new Label(message, labelStyle);
        label.setFontScale(1.2f);
        label.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        label.setAlignment(Align.center);
        label.addAction(Actions.sequence(
            Actions.parallel(
                Actions.moveBy(0, 100, 2f),
                Actions.fadeOut(2f)
            ),
            Actions.removeActor()
        ));

        GameScreen.getGameScreen().getStage().addActor(label);
    }


    public void handleLeftClick(int screenX, int screenY) {
        Player player = App.getActiveGame().getCurrentPlayer();

        // فقط وقتی بازیکن داخل خونه است
        if (player.isAtHome()) {

            Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
            Vector2 clickPos = new Vector2(worldCoordinates.x, worldCoordinates.y);


            if (player.getHomeMap().isOnFridge(clickPos)) {
                if (!isFridgeMenuOpen) {
                    fridgeMenu = new FridgeMenu(
                            GameAssetManager.getGameAssetManager().getSkin(),
                            player.getFarm().getRefrigerator(),
                            dragAndDrop
                    );
                    GameScreen.getGameScreen().getStage().addActor(fridgeMenu);
                    isFridgeMenuOpen = true;
                } else {
                    fridgeMenu.remove();
                    fridgeMenu = null;
                    isFridgeMenuOpen = false;
                }
            }
        }
    }

    public DragAndDrop getDragAndDrop() {
        return dragAndDrop;
    }

    public void showErrorPopup(String message) {
        Dialog dialog = new Dialog("error", GameAssetManager.getGameAssetManager().getSkin());
        dialog.text(message);
        dialog.button("Ok");
        dialog.show(GameScreen.getGameScreen().getStage());
    }

}
