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
import com.group16.stardewvalley.controller.shops.ShopController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.shops.MarniesRanch;
import com.group16.stardewvalley.model.shops.MarniesRanchAnimals;
import com.group16.stardewvalley.model.user.Player;

import java.util.*;
import java.util.List;

public class MarniesRanchMenu extends Window {
    private final Skin skin;
    private static  Window tooltip = new com.badlogic.gdx.scenes.scene2d.ui.Window("", GameAssetManager.getGameAssetManager().getSkin());
    ;
    private Set<Item> availableItems;
    private Player player;
    private MarniesRanch shop = new MarniesRanch();
    private ShopController shopController = new ShopController();

    private final Map<String, Texture> marnieTextures = new HashMap<>();

    private final Map<MarniesRanchAnimals, Table> tooltipCache = new HashMap<>();
    private Label resultLabel;
    private String productFilter = "All Products"; // default


    public MarniesRanchMenu(Skin skin) {
        super("Marie's Ranch", skin);
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
                marnieGrid(); // rebuild grid with new filter
            }
            return false;
        });
        // Create Close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MarniesRanchMenu.this.remove();  // Remove this window from the stage (close it)
            }
        });

// Add filter at the top
        Table topRow = new Table();
        topRow.add(filterSelect).left().width(300).height(50);
        topRow.add(closeButton).right().width(250).height(50);
        add(topRow).expandX().fillX().pad(10).row();

        marnieGrid();
        GameScreen.getGameScreen().getStage().addActor(tooltip);
    }


    private void marnieGrid() {
        Table grid = new Table();
        grid.defaults().pad(10);

        Set<MarniesRanchAnimals> allProducts;
        allProducts = Set.of(MarniesRanchAnimals.values());

        int cols = 4;
        int count = 0;
        availableItems = shop.getAvailableItems();


        for (MarniesRanchAnimals animal : allProducts) {

            System.out.println(animal.getName());
            boolean isAvailable = true;
            Texture texture = getTexture(animal);
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

                    showBuyProductWindow(animal);
                }
            });


            btn.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    tooltip.clear();
                    Table content = tooltipCache.computeIfAbsent(animal, itemKey -> getDescriptionTable(itemKey));
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


            grid.add(btn).size(128);


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


    private Table getDescriptionTable(MarniesRanchAnimals animal) {
        Table content = new Table();
        content.defaults().left().padBottom(5);

        // Title
        Label title = new Label(animal.getName(), skin, "button");
        title.setColor(Color.ORANGE);
        content.add(title).row();
        content.add(new Label("----------------------------", skin)).row();


        // Price
        Label priceLabel = new Label("Price: " + animal.getPrice() + "g", skin);
        priceLabel.setColor(Color.GOLD);
        content.add(priceLabel).row();

        // Animal type
        Label typeLabel = new Label("Type: " + animal.getAnimalType().name(), skin);
        typeLabel.setColor(Color.WHITE);
        content.add(typeLabel).row();

        // Buildings required
        String buildingList = String.join(", ",
            animal.getBuildingRequired().stream()
                .map(Enum::name)
                .toList()
        );
        Label buildingLabel = new Label("Required Building(s): " + buildingList, skin);
        buildingLabel.setColor(Color.SKY);
        content.add(buildingLabel).row();

        // Daily limit
        Label dailyLimitLabel = new Label("Daily Purchase Limit: " + animal.getDailyLimit(), skin);
        dailyLimitLabel.setColor(Color.LIGHT_GRAY);
        content.add(dailyLimitLabel).row();

        // Daily sold
        Label dailySoldLabel = new Label("Sold today: " + animal.getDailySold(), skin);
        dailySoldLabel.setColor(Color.LIGHT_GRAY);
        content.add(dailySoldLabel).row();


        return content;
    }

    private void showBuyProductWindow(MarniesRanchAnimals animal) {
        Window buyWindow = new Window("Buy Product", skin);
        buyWindow.setSize(700, 550); // slightly taller to fit text field
        buyWindow.setPosition(
            Gdx.graphics.getWidth() / 2f - buyWindow.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - buyWindow.getHeight() / 2f);
        buyWindow.setModal(true);
        buyWindow.setMovable(true);

        Label nameLabel = new Label(animal.getName(), skin);
        Label quantityLabel = new Label("Quantity: 1", skin);
        Label priceLabel = new Label("Price: "+animal.getPrice()+"$", skin);
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
                if (quantity[0] < 2) {
                    quantity[0]++;
                    quantityLabel.setText("Quantity: " + quantity[0]);
                }
            }
        });

        // --- TextField for animal name ---
        Label animalNameLabel = new Label("Enter Name:", skin);
        TextField animalNameField = new TextField("", skin);
        animalNameField.setMessageText("Animal's Name"); // placeholder text

        TextButton submitBtn = new TextButton("Submit", skin);
        submitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String enteredName = animalNameField.getText().trim();
                if (enteredName.isEmpty()) {
                    resultLabel.setText("Please enter a name!");
                    return;
                }

                Result result = null;
                for (int i = 0; i < quantity[0]; i++) {
                    result = shop.buyAnimal(animal.getName(), enteredName);
                    if (!result.isSuccessful()) break;
                }

                if (result != null) {
                    resultLabel.setText(result.message());
                    System.out.println(result.message());
                }
                buyWindow.remove();
            }
        });

        TextButton cancelBtn = new TextButton("Cancel", skin);
        cancelBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buyWindow.remove();
            }
        });

        Table buttonsTable = new Table();
        buttonsTable.add(minusBtn).size(100);
        buttonsTable.add(quantityLabel).padLeft(10).padRight(10);
        buttonsTable.add(plusBtn).size(100);

        buyWindow.add(nameLabel).colspan(3).center().padBottom(15).row();
        buyWindow.add(priceLabel).colspan(3).center().padBottom(15).row();
        buyWindow.add(buttonsTable).colspan(3).center().padBottom(15).row();

        // Add text field below + / - buttons
        Table nameInputTable = new Table();
        nameInputTable.add(animalNameLabel).padRight(10);
        nameInputTable.add(animalNameField).width(300);
        buyWindow.add(nameInputTable).colspan(3).center().padBottom(15).row();

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



    public Texture getTexture(MarniesRanchAnimals animal) {
        String name = animal.getName();
        if (!marnieTextures.containsKey(name)) {
            try {
                Texture texture = new Texture("Shops/Marnie/" + name + ".png");
                marnieTextures.put(name, texture);
            } catch (Exception e) {
                marnieTextures.put(name, GameAssetManager.getGameAssetManager().getBasicItemTexture());
            }
        }
        return marnieTextures.get(name);
    }


}


