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
import com.group16.stardewvalley.controller.shops.ShopController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.crafting.CraftingRecipes;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.shops.BuildingType;
import com.group16.stardewvalley.model.shops.CarpentersShop;
import com.group16.stardewvalley.model.user.Player;

import java.util.*;
import java.util.List;

public class CarpenterMenu extends Window {
    private final Skin skin;
    private static  Window tooltip = new com.badlogic.gdx.scenes.scene2d.ui.Window("", GameAssetManager.getGameAssetManager().getSkin());
    ;
    private Set<Item> availableItems;
    private Player player;
    private CarpentersShop shop = new CarpentersShop();
    private ShopController shopController = new ShopController();

    private final Map<String, Texture> carpenterTextures = new HashMap<>();

    private final Map<Item, Table> tooltipCache = new HashMap<>();
    private Label resultLabel;
    private String productFilter = "All Products"; // default

    public static String pendingBuildingName = null;  // null means no building placement in progress


    public CarpenterMenu(Skin skin) {
        super("Carpenter's Shop", skin);
        this.skin = skin;
        this.availableItems = shop.getAvailableItems();
        this.player = App.getActiveGame().getCurrentPlayer();

        tooltip = new com.badlogic.gdx.scenes.scene2d.ui.Window("", skin);
        tooltip.setMovable(true);
        tooltip.setVisible(false);
        tooltip.setKeepWithinStage(true);
        tooltip.pad(10);

        setSize(800, 600);
        setPosition(Gdx.graphics.getWidth() / 2f - getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - getHeight() / 2f);

// Create filter select box
        SelectBox<String> filterSelect = new SelectBox<>(skin);

// Copy and tweak the style
        SelectBox.SelectBoxStyle style = new SelectBox.SelectBoxStyle(filterSelect.getStyle());

// Increase font size
        style.font.getData().setScale(1.2f);

// Adjust the selection drawable padding to give each item more height
        if (style.listStyle.selection != null) {
            style.listStyle.selection.setTopHeight(10);   // top padding
            style.listStyle.selection.setBottomHeight(10); // bottom padding
        }

// Optionally adjust background padding for the list items
        if (style.listStyle.background != null) {
            style.listStyle.background.setTopHeight(10);
            style.listStyle.background.setBottomHeight(10);
        }

// Apply modified style
        filterSelect.setStyle(style);

// Set items
        filterSelect.setItems("All Products", "Available Products");
        filterSelect.setSelected(productFilter);

        filterSelect.addListener(event -> {
            if (event.toString().equals("changed")) {
                productFilter = filterSelect.getSelected();
                carpenterGrid(); // rebuild grid with new filter
            }
            return false;
        });
        // Create Close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CarpenterMenu.this.remove();  // Remove this window from the stage (close it)
            }
        });

// Add filter at the top
        Table topRow = new Table();
        topRow.add(filterSelect).left().width(300).height(50);
        topRow.add(closeButton).right().width(250).height(50);

        add(topRow).expandX().fillX().pad(10).row();

        carpenterGrid();
        GameScreen.getGameScreen().getStage().addActor(tooltip);
    }


    private void carpenterGrid() {
        Table grid = new Table();
        grid.defaults().pad(10);

        Set<Item> allProducts;
        if (productFilter.equals("Available Products")) {
            allProducts = shop.getAvailableItems();
        } else {
            allProducts = shop.getAllProducts();
        }
        int cols = 4;
        int count = 0;
        availableItems = shop.getAvailableItems();

        // First, put wood and stone explicitly at the beginning
        List<String> priorityItems = Arrays.asList("Wood", "Stone");

        List<Item> sortedItems = new ArrayList<>();

        // Add priority items first
        for (String name : priorityItems) {
            for (Item item : allProducts) {
                if (item.getName().equalsIgnoreCase(name)) {
                    sortedItems.add(item);
                    break; // prevent duplicates if multiple items have same name
                }
            }
        }

        // Add the rest of the items (excluding already added ones)
        for (Item item : allProducts) {
            if (!priorityItems.contains(item.getName())) {
                sortedItems.add(item);
            }
        }

        for (Item item : sortedItems) {
            boolean isAvailable = isAvailableProduct(item);
            Texture texture = getTexture(item);
            Image img = new Image(texture);

            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = img.getDrawable();
            ImageButton btn = new ImageButton(style);

            if (!isAvailable) {
                btn.getImage().setColor(0.3f, 0.3f, 0.3f, 0.7f);
            }

            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!isAvailable) return;

                    showBuyProductWindow(item);
                }
            });


            btn.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    tooltip.clear();
                    Table content = tooltipCache.computeIfAbsent(item, itemKey -> getDescriptionTable(itemKey));
                    tooltip.add(content).pad(50);
                    tooltip.pack();
                    tooltip.setVisible(true);
                    tooltip.toFront();

                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    tooltip.setVisible(false);
                }
            });

            // Make Wood and Stone larger
            if (item.getName().equalsIgnoreCase("Wood") || item.getName().equalsIgnoreCase("Stone")) {
                grid.add(btn).size(96);
            } else {
                grid.add(btn).size(64);
            }

            count++;
            if (count % cols == 0) grid.row();
        }

        ScrollPane scroll = new ScrollPane(grid, skin);

//        clear();
        add(scroll).expand().fill().row();

        resultLabel = new Label("", skin);
        resultLabel.setColor(Color.LIGHT_GRAY);
        resultLabel.setWrap(true);
        add(resultLabel).padBottom(15).padLeft(200).fillX().height(50);
    }


    private Table getDescriptionTable(Item item) {
        Table content = new Table();
        content.defaults().left().padBottom(5);

        Label title = new Label(item.getName(), skin, "button");
        title.setColor(Color.ORANGE);
        content.add(title).row();

        Label buyingLabel = new Label("Buy", skin);
        buyingLabel.setColor(Color.SALMON);
        content.add(buyingLabel).row();

        content.add(new Label("----------------------------", skin)).row();

        // Try to get the building type for this item
        BuildingType buildingType = BuildingType.buildingFromName(item.getName());
        if (buildingType != null) {
            // Example description using enum fields
            String description = String.format(
                    "Cost: %d gold, %d wood, %d stone\nSize: %dx%d\nDaily Sale Limit: %d\nAnimal Limit: %d",
                    buildingType.getCost(),
                    buildingType.getWoodCost(),
                    buildingType.getStoneCost(),
                    buildingType.getLength(),
                    buildingType.getWidth(),
                    buildingType.getDailySaleLimit(),
                    buildingType.getAnimalLimit()
            );
            content.add(new Label(description, skin)).row();
        } else {
            content.add(new Label("No building information available.", skin)).row();
        }

        return content;
    }


    private void showBuyProductWindow(Item item) {
        Window buyWindow = new Window("Buy Product", skin);
        buyWindow.setSize(700, 500);
        buyWindow.setPosition(
            Gdx.graphics.getWidth() / 2f - buyWindow.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - buyWindow.getHeight() / 2f);
        buyWindow.setModal(true);
        buyWindow.setMovable(true);

        Label nameLabel = new Label(item.getName(), skin);
        Label quantityLabel = new Label("Quantity: 1", skin);
        Label priceLabel = new Label("Price: "+item.getPrice()+"$", skin);
        quantityLabel.setColor(Color.WHITE);
        priceLabel.setColor(Color.WHITE);

        final int[] quantity = {1};

        TextButton minusBtn = new TextButton("-", skin);
        TextButton plusBtn = new TextButton("+", skin);

        minusBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (quantity[0] > 1) {
                    quantity[0]--;
                    quantityLabel.setText("Quantity: " + quantity[0]);
                }
            }
        });

        plusBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                quantity[0]++;
                quantityLabel.setText("Quantity: " + quantity[0]);
            }
        });

        TextButton submitBtn = new TextButton("Submit", skin);
        submitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = null;
                for (int i = 0; i < quantity[0]; i++) {

                    result = shop.buildCoop_Barn(item.getName(), -1, -1);
                    if (result.isSuccessful()) {
                        pendingBuildingName = item.getName();  // remember the building to place
                        System.out.println("Select location to place " + item.getName());
                    } else {
                        System.out.println("Cannot start building: " + result.toString());
                        break;
                    }


                }
                if (result != null) {
                    resultLabel.setText(result.message());
                    System.out.println(result.message());
                }
                buyWindow.remove(); // close window
            }
        });

        TextButton cancelBtn = new TextButton("Cancel", skin);
        cancelBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buyWindow.remove(); // just close the window without doing anything
            }
        });

        Table buttonsTable = new Table();
        buttonsTable.add(minusBtn).size(100);
        buttonsTable.add(quantityLabel).padLeft(10).padRight(10);
        buttonsTable.add(plusBtn).size(100);


        buyWindow.add(nameLabel).colspan(3).center().padBottom(15).row();
        buyWindow.add(priceLabel).colspan(3).center().padBottom(15).row();
        buyWindow.row();
        buyWindow.add(buttonsTable).colspan(3).center().padBottom(15).row();
        buyWindow.row();

        Table submitCancelTable = new Table();
        submitCancelTable.add(submitBtn).width(300).padRight(20);
        submitCancelTable.add(cancelBtn).width(300);
        buyWindow.add(submitCancelTable).colspan(3).center();

        GameScreen.getGameScreen().getStage().addActor(buyWindow);
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        if (tooltip.isVisible()) {
            Vector2 mousePos = GameScreen.getGameScreen().getStage().screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            tooltip.setPosition(mousePos.x + 10, mousePos.y - 10);
        }
    }

    public static Window getTooltip() {
        return tooltip;
    }


    private boolean isAvailableProduct(Item item) {
        for (Item i : availableItems) {
            if (i.getName().equals(item.getName())) {
                return true;
            }
        }
        return false;
    }

    public Texture getTexture(Item item) {
        String name = item.getName().replace(" ", "_");
        if (!carpenterTextures.containsKey(name)) {
            try {
                Texture texture = new Texture("Shops/Carpenter/" + name + ".png");
                carpenterTextures.put(name, texture);
            } catch (Exception e) {
                carpenterTextures.put(name, GameAssetManager.getGameAssetManager().getBasicItemTexture());
            }
        }
        return carpenterTextures.get(name);
    }


}

