package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.crafting.CraftItem;
import com.group16.stardewvalley.model.crafting.CraftingRecipes;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.items.Item;

import java.util.List;
import java.util.Map;

public class ArtisanMenu extends Window {

    private final Label feedbackLabel;

    public ArtisanMenu(Skin skin, CraftItem craftItem, DragAndDrop dragAndDrop, Stage stage) {
        super("Artisan Menu", skin);

        // Main layout for this window
        Table mainTable = new Table();
        mainTable.pad(10).top().left();

        // 1. Show recipe ingredients at the top
        Table recipeTable = new Table();
        recipeTable.defaults().pad(5);

        recipeTable.add(new Label("Required Ingredients:", skin)).left().row();
        Map<Ingredient, Integer> recipeIngredients = craftItem.getRecipe().getNeededIngredients();
        for (Map.Entry<Ingredient, Integer> entry : recipeIngredients.entrySet()) {
            Ingredient ingredient = entry.getKey();
            int quantity = entry.getValue();


            Item item = App.getActiveGame().getCurrentPlayer().getInventory().findItemByName(ingredient.getName());

            Texture texture = GameAssetManager.getGameAssetManager().getItemTexture(item);
            Image icon = new Image(texture);
            icon.setSize(32, 32);

            Label qtyLabel = new Label("x" + quantity, skin);

            Table ingredientRow = new Table();
            ingredientRow.add(icon).size(32).padRight(5);
            ingredientRow.add(new Label(ingredient.getName(), skin)).padRight(10);
            ingredientRow.add(qtyLabel);

            recipeTable.add(ingredientRow).left().row();
        }
        mainTable.add(recipeTable).left().row();

        // 2. Drop area for ingredients
        Table dropTable = new Table(skin);
        dropTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Inventory/InventorySlotFrame.png")))));
        dropTable.defaults().pad(5);
        dropTable.setSize(200, 100);

        Label dropLabel = new Label("Drop Ingredients Here", skin);
        dropLabel.setAlignment(Align.center);
        dropTable.add(dropLabel).center();

        mainTable.add(dropTable).padTop(10).row();

        // 3. Feedback label
        feedbackLabel = new Label("", skin);
        feedbackLabel.setAlignment(Align.center);
        mainTable.add(feedbackLabel).padTop(10).row();

        this.add(mainTable).expand().fill();

        // 4. Drag-and-drop target for the artisan drop table
        dragAndDrop.addTarget(new DragAndDrop.Target(dropTable) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload,
                                float x, float y, int pointer) {
                return payload.getObject() instanceof Item;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload,
                             float x, float y, int pointer) {
                Item droppedItem = (Item) payload.getObject();

                if (recipeIngredients.containsKey(droppedItem)) {
                    feedbackLabel.setText("✅ " + droppedItem.getName() + " accepted!");
                    App.getActiveGame().getCurrentPlayer().getInventory().removeItem(droppedItem, 1);
                } else {
                    feedbackLabel.setText("❌ This item is not part of the recipe!");
                }
            }
        });

        // Window size & position
        this.setSize(350, 300);
        this.setMovable(true);
        this.setPosition(
            (Gdx.graphics.getWidth() - getWidth()) / 2f,
            (Gdx.graphics.getHeight() - getHeight()) / 2f
        );

        stage.addActor(this);
    }
}
