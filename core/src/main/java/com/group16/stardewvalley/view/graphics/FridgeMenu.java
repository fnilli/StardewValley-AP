package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FridgeMenu extends Window {
    private final Skin skin;
    private final HashMap<FoodIngredient, Integer> refrigerator;
    private final java.util.List<Container<Image>> fridgeContainers = new ArrayList<>();

    private final Window tooltip;

    private final DragAndDrop dragAndDrop;

    public FridgeMenu(Skin skin, HashMap<FoodIngredient, Integer> refrigerator, DragAndDrop dragAndDrop) {
        super("Fridge", skin);
        this.dragAndDrop = dragAndDrop;
        this.skin = skin;
        this.refrigerator = refrigerator;

        tooltip = new Window("", skin);
        tooltip.setMovable(false);
        tooltip.setVisible(false);
        tooltip.setKeepWithinStage(true);
        tooltip.pad(10);

        setPosition(0, Gdx.graphics.getHeight() - getHeight() - 250);

        setSize(400, 400);

        createIngredientGrid();
    }

    private void createIngredientGrid() {
        fridgeContainers.clear();
        Table grid = new Table();
        grid.defaults().pad(10);
        grid.top().left();

        clear();

        int cols = 4;
        int count = 0;

        for (Map.Entry<FoodIngredient, Integer> entry : refrigerator.entrySet()) {
            FoodIngredient ingredient = entry.getKey();
            int quantity = entry.getValue();

            Texture texture = GameAssetManager.getGameAssetManager().getIngredientTexture(ingredient.getType());
            Image icon = new Image(texture);
            icon.setSize(64, 64);

            // Slot frame background
            TextureRegionDrawable defaultBg = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("Inventory/InventorySlotFrame.png"))
            ));
            TextureRegionDrawable selectedBg = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("Inventory/InventorySlotFrame2.png"))
            ));


            // Container for icon with frame
            Container<Image> iconContainer = new Container<>(icon);
            iconContainer.size(64, 64);
            iconContainer.background(defaultBg);

            // Keep track of containers
            fridgeContainers.add(iconContainer);

            Stack stack = new Stack();
            stack.add(iconContainer);

            // Quantity label (bottom-right)
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraftia-Regular.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 14;
            BitmapFont smallFont = generator.generateFont(parameter);
            generator.dispose();

            Label.LabelStyle labelStyle = new Label.LabelStyle(smallFont, Color.BLACK);
            Label quantityLabel = new Label(String.valueOf(quantity), labelStyle);
            quantityLabel.setAlignment(Align.bottomRight);


            Table numberTable = new Table();
            numberTable.add(quantityLabel).expand().bottom().right().pad(3);
            stack.add(numberTable);


            // Click listener
            stack.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Reset backgrounds for all slots
                    for (Container<Image> c : fridgeContainers) {
                        c.background(defaultBg);
                    }
                    // Set selected background for this slot
                    iconContainer.background(selectedBg);

                    App.getActiveGame().getCurrentPlayer().setCurrentThing(ingredient);

                    System.out.println("✅ Selected from fridge: " + ingredient.getName() + " x" + quantity);
                }
            });

            // Tooltip listener
            stack.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    tooltip.clear();
                    Table content = getDescriptionTable(ingredient, quantity);
                    tooltip.add(content).pad(20);
                    tooltip.pack();
                    tooltip.setVisible(true);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    tooltip.setVisible(false);
                }
            });

            // Drag and drop target
            dragAndDrop.addTarget(new DragAndDrop.Target(stack) {
                @Override
                public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    Item item = (Item) payload.getObject();
                    return item instanceof FoodIngredient;
                }

                @Override
                public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    Item item = (Item) payload.getObject();
                    if (item instanceof FoodIngredient fi) {
                        refrigerator.put(fi, refrigerator.getOrDefault(fi, 0) + 1);
                        App.getActiveGame().getCurrentPlayer().getInventory().removeItem(item, 1);
                        createIngredientGrid();
                    } else {
                        GameScreen.getGameScreen().getController().showErrorPopup("You can only put food ingredients into the fridge!");
                    }
                }
            });

            // Drag source
            dragAndDrop.addSource(new DragAndDrop.Source(stack) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject(ingredient);
                    payload.setDragActor(new Image(texture));
                    return payload;
                }
            });

            grid.add(stack).size(64);
            count++;
            if (count % cols == 0) grid.row();
        }


        // In your fridge UI code, AFTER building the grid and before adding to stage:
        dragAndDrop.addTarget(new DragAndDrop.Target(this) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Item item = (Item) payload.getObject();
                if (item instanceof FoodIngredient ingredient) {
                    refrigerator.put(ingredient, refrigerator.getOrDefault(ingredient, 0) + 1);
                    App.getActiveGame().getCurrentPlayer().getInventory().removeItem(item, 1);
                    createIngredientGrid();
                } else {
                    GameScreen.getGameScreen().getController().showErrorPopup("You can only put food ingredients into the fridge!");
                }
            }
        });


        ScrollPane scroll = new ScrollPane(grid, skin);
        add(scroll).expand().fill();
    }

    private Table getDescriptionTable(FoodIngredient ingredient, int quantity) {
        Table content = new Table();
        content.defaults().left().padBottom(5);

        Label title = new Label(ingredient.getName(), skin, "button");
        title.setColor(Color.GOLD);
        content.add(title).row();

        content.add(new Label("------------------------", skin)).row();

        Table qtyRow = new Table();
        qtyRow.add(new Label("Quantity: " + quantity, skin)).left();
        content.add(qtyRow).left().row();

        Table priceRow = new Table();
        priceRow.add(new Label("Sell Price: " + ingredient.getPrice(), skin)).left();
        content.add(priceRow).left().row();

        return content;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (tooltip.isVisible()) {
            Vector2 mousePos = GameScreen.getGameScreen().getStage()
                    .screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            tooltip.setPosition(mousePos.x + 10, mousePos.y - 10);
        }
    }

    public Window getTooltip() {
        return tooltip;
    }
}
