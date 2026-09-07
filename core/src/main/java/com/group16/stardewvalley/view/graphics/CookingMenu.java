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
import com.group16.stardewvalley.controller.menu.HomeMenuController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodFactory;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.graphics.GameAssetManager;

import java.util.List;
import java.util.Set;

public class CookingMenu extends Window {
    private final Skin skin;
    private Set<Food> knownRecipes;

    private final Window tooltip;


    public CookingMenu(Skin skin, Set<Food> knownRecipes) {
        super("Cooking Menu", skin);
        this.skin = skin;
        this.knownRecipes = knownRecipes;


        tooltip = new Window("", skin);
        tooltip.setMovable(false);
        tooltip.setVisible(false);
        tooltip.setKeepWithinStage(true);
        tooltip.pad(10);

        setSize(800, 600);
        setPosition(Gdx.graphics.getWidth() / 2f - getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f - getHeight() / 2f);

        createFoodGrid();
    }

    private void createFoodGrid() {
        Table grid = new Table();
        grid.defaults().pad(10);

        List<Food> allFoods = FoodFactory.getAllFoods();
        int cols = 5;
        int count = 0;
        knownRecipes = App.getActiveGame().getCurrentPlayer().getKnownRecipes();

        for (Food food : allFoods) {
            boolean isKnown = knowRecipe(food);
            Texture texture = GameAssetManager.getGameAssetManager().getFoodTexture(food);
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
                    HomeMenuController controller = new HomeMenuController();
                    Result result = controller.cooking(food);
                    if (result.isSuccessful()) {
                        GameScreen.getGameScreen().getController().showErrorPopup("✅ Cooked: " + food.getName());
                    } else {
                        GameScreen.getGameScreen().getController().showErrorPopup(result.message());
                    }
                }
            });

            btn.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    tooltip.clear();

                    Table content = getDescriptionTable(food);

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
        add(scroll).expand().fill();
    }

    private Table getDescriptionTable(Food food) {
        Table content = new Table();
        content.defaults().left().padBottom(5);

        Label title = new Label(food.getName(), skin, "button");
        title.setColor(Color.ORANGE);
        content.add(title).row();

        Label cookingLabel = new Label("Cooking", skin);
        cookingLabel.setColor(Color.SALMON);
        content.add(cookingLabel).row();

        content.add(new Label("------------------------", skin)).row();

        content.add(new Label("Ingredients:", skin)).row();

        for (Ingredient ing : food.getIngredients().keySet()) {
            Table ingRow = new Table();
            Texture tex = GameAssetManager.getGameAssetManager().getIngredientTexture(ing);
            ingRow.add(new Image(tex)).size(25);
            ingRow.add(new Label(" ", skin));
            ingRow.add(new Label(ing.getName(), skin));
            content.add(ingRow).left().row();
        }

        content.add(new Label("This is very nutritious.", skin)).padTop(5).row();

        Table energyRow = new Table();
        energyRow.add(new Image(new Texture("Crafting/Energy.png"))).size(25);
        energyRow.add(new Label(" +" + food.getEnergy() + " Energy", skin));
        content.add(energyRow).left().row();
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

    private boolean knowRecipe(Food food) {
        for (Food f : knownRecipes) {
            if (f.getName().equals(food.getName())) {
                return true;
            }
        }
        return false;
    }
}


