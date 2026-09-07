package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.CheatCodeController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.crafting.CraftItem;
import com.group16.stardewvalley.model.crafting.Crafting;
import com.group16.stardewvalley.model.crafting.CraftingRecipes;
import com.group16.stardewvalley.model.crafting.CraftingSource;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.graphics.GameAssetManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.group16.stardewvalley.model.crafting.Crafting.pendingCraftItemName;

public class CraftMenu extends Window {
    private final Skin skin;
    private ArrayList<CraftingRecipes> knownCraftingRecipes;
    private final Window tooltip;
    private Crafting craftingController = new Crafting();
    private CheatCodeController CheatController = new CheatCodeController();
    private final Map<CraftingRecipes, Table> tooltipCache = new HashMap<>();
    private Label resultLabel;

    public CraftMenu(Skin skin, ArrayList<CraftingRecipes> knownCraftingRecipes){
        super("Crafting Menu", skin);
        this.skin = skin;
        this.knownCraftingRecipes = knownCraftingRecipes;


        tooltip = new com.badlogic.gdx.scenes.scene2d.ui.Window("", skin);
        tooltip.setMovable(true);
        tooltip.setVisible(false);
        tooltip.setKeepWithinStage(true);
        tooltip.pad(10);

        setSize(800, 600);
        setPosition(Gdx.graphics.getWidth() / 2f - getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - getHeight() / 2f);

        craftGrid();
    }


    private void craftGrid() {
        Table grid = new Table();
        grid.defaults().pad(10);

        List<CraftingRecipes> allCraftingItems = List.of(CraftingRecipes.values());
        int cols = 5;
        int count = 0;
        knownCraftingRecipes = App.getActiveGame().getCurrentPlayer().getInventory().getCraftingRecipes();

        for (CraftingRecipes craftItem : allCraftingItems) {
            boolean isKnown = isKnownRecipe(craftItem);
            Texture texture = GameAssetManager.getGameAssetManager().getCraftingTexture(craftItem);
            Image img = new Image(texture);

            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = img.getDrawable();
            ImageButton btn = new ImageButton(style);

            if (!isKnown) {
                btn.getImage().setColor(0.3f, 0.3f, 0.3f, 0.7f);
            }


            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!isKnown) return;

                    for(Ingredient ingredient: craftItem.getNeededIngredients().keySet()){
                        CheatController.addIngredient(ingredient.getName());
                    }

                    Result result = craftingController.craft(craftItem.getName(), -1, -1);
                    if (result.isSuccessful()) {
                        pendingCraftItemName = craftItem.getName();
                        System.out.println("Select location to place " + craftItem.getName());
                    }
                    resultLabel.setText(result.message());

                }
            });


            btn.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    tooltip.clear();

                    Table content = tooltipCache.computeIfAbsent(craftItem, item -> getDescriptionTable(item));

                    tooltip.add(content).pad(50);
                    tooltip.pack();
                    tooltip.setVisible(true);
                }


                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    tooltip.setVisible(false);
                }
            });

            grid.add(btn).size(64);
            count++;
            if (count % cols == 0) grid.row();
        }

        ScrollPane scroll = new ScrollPane(grid, skin);

        // Clear any previous children from the window and start clean layout
        clear(); // clear previous layout children (like multiple scrolls if called again)

        add(scroll).expand().fill().row(); // put scrollpane and go to next row

        resultLabel = new Label("", skin);
        resultLabel.setColor(Color.LIGHT_GRAY);
        resultLabel.setWrap(true); // wrap text for long messages

        add(resultLabel).padBottom(15).padLeft(200).fillX().height(50);

    }
    private Table getDescriptionTable(CraftingRecipes craftingItem) {
        Table content = new Table();
        content.defaults().left().padBottom(5);

        // Title
        Label title = new Label(craftingItem.getName(), skin, "button");
        title.setColor(Color.ORANGE);
        content.add(title).row();

        // Section label
        Label craftingLabel = new Label("Crafting", skin);
        craftingLabel.setColor(Color.SALMON);
        content.add(craftingLabel).row();

        // Separator
        content.add(new Label("------------------------", skin)).row();

        // === Extra Recipe Info ===
        if (!craftingItem.getSource().isEmpty()) {
            for (Map.Entry<CraftingSource, Integer> entry : craftingItem.getSource().entrySet()) {
                String srcText = String.format("Requires: %s level %d",
                    entry.getKey().name(), entry.getValue());
                content.add(new Label(srcText, skin)).row();
            }
        } else {
            content.add(new Label("No special requirement", skin)).row();
        }



        // Another separator before ingredients
        content.add(new Label("------------------------", skin)).row();

        // Ingredients list
        content.add(new Label("Ingredients:", skin)).row();
        for (Ingredient ing : craftingItem.getNeededIngredients().keySet()) {
            Table ingRow = new Table();
            Texture tex = GameAssetManager.getGameAssetManager().getCraftingIngredientTexture(ing);
            ingRow.add(new Image(tex)).size(25);
            ingRow.add(new Label(" ", skin));
            ingRow.add(new Label(ing.getName() + " x" + craftingItem.getNeededIngredients().get(ing), skin));
            content.add(ingRow).left().row();
        }

        return content;
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        if (tooltip.isVisible()) {
            Vector2 mousePos = GameScreen.getGameScreen().getStage().screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            tooltip.setPosition(mousePos.x + 10, mousePos.y - 10);
        }
    }

    public Window getTooltip() {
        return tooltip;
    }

    private boolean isKnownRecipe(CraftingRecipes craftItem) {
        for (CraftingRecipes f : knownCraftingRecipes) {
            if (f.equals(craftItem)) {
                return true;
            }
        }
        return false;
    }
}
